package no.nsd.polsys.actions.parti;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.parti.Storting_fylkestingsvalgLogikk;
import no.nsd.polsys.modell.parti.Parti;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 25.nov.2010
 * Time: 08:44:56
 * To change this template use File | Settings | File Templates.
 */
public class Storting_fylkestingsvalgAction {

     public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
         Storting_fylkestingsvalgLogikk sqlkr = new Storting_fylkestingsvalgLogikk();

         Parti[] partier = null;
         Parti[] fylker = null;
          Parti[] parti_fylkekode = null;
         Parti[] navnlister = null;

        int f_kode = 0;
        int aar=0;
        int pkode=0;
        int valgtype=0;

         try {
           conn = DatabaseConnectionFactory.getConnection();
           sqlkr.setConn(conn);

           if (request.getParameter("aar") != null) {
              try {
            aar = Integer.parseInt(request.getParameter("aar"));
         } catch (Exception e) {
         }

              }
             else{
               Parti[]  maxvalgaar = sqlkr.getSistevalgaar();
               aar = maxvalgaar[0].getAarstal();
             }

         /*get parti_id to get fylke list*/
             parti_fylkekode = sqlkr.getPartiList(aar);
             request.setAttribute("parti_fylkekode", parti_fylkekode );
              if (request.getParameter("fid") == null && parti_fylkekode.length > 0)
             {
                 f_kode = parti_fylkekode[0].getFylke_kode();
             }
             else if(request.getParameter("fid") != null )
              {
               try {
            f_kode = Integer.parseInt(request.getParameter("fid"));

         } catch (Exception e) {
         }
       }

             for (int i = 0; i < parti_fylkekode.length; i++) {
                valgtype =  parti_fylkekode[i].getValgtype();
             }

         partier = sqlkr.getPartiList(aar,  f_kode/*, valgtype*/);
         request.setAttribute("partier", partier);


         fylker = sqlkr.getFylkeList(aar);
         request.setAttribute("fylker", fylker);


             if (request.getParameter("pid") == null && partier.length > 0)
             { pkode = partier[0].getPartikode();}
          else if(request.getParameter("pid") != null)
          {
                try {
           pkode = Integer.parseInt(request.getParameter("pid"));
         } catch (Exception e) {
         }

          }
         request.setAttribute("pkode", pkode);
         request.setAttribute("fkode", f_kode);
         navnlister = sqlkr.getNavnList(aar, pkode, f_kode, valgtype);
         request.setAttribute("navnlister", navnlister);
             
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
        }

         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/parti/storting_fylkestingsvalg.jsp");
        rd.forward(request, response);
     }

}
