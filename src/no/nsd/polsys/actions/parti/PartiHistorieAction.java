package no.nsd.polsys.actions.parti;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.parti.PartiLogikk;
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
public class PartiHistorieAction {

     public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
         PartiLogikk sqlkr = new PartiLogikk();

         Parti[] partihistorie = null;
         Parti[] partibakgrunn = null;
         Parti[] endringshistorie = null;
         Parti[] partinavn = null;

          Integer partikode = null;
       HttpServletRequest httpReq = (HttpServletRequest) request;
       partikode = Util.getId(httpReq.getRequestURI());
       request.setAttribute("partikode", partikode);

         try {
           conn = DatabaseConnectionFactory.getConnection();
           sqlkr.setConn(conn);
           if (partikode == null) {
         partihistorie = sqlkr.getPartiList();
         request.setAttribute("partihistorie", partihistorie);
           }
             else{
         partibakgrunn = sqlkr.getPartibakgrunn(partikode);
         request.setAttribute("partibakgrunn", partibakgrunn);

         endringshistorie = sqlkr.getEndringshistorie(partikode);
         request.setAttribute("endringshistorie", endringshistorie);

         partinavn = sqlkr.getAllePartier(partikode);
         request.setAttribute("partinavn", partinavn);
           }     
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
        }

         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/parti/partihistorie.jsp");
        rd.forward(request, response);
     }

}
