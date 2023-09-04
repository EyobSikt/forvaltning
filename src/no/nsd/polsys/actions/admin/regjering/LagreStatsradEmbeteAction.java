package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatsradLogikk;
import no.nsd.polsys.modell.admin.regjering.Personinfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class LagreStatsradEmbeteAction extends HttpServlet {

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
        RedigereStatsradLogikk logikk = new RedigereStatsradLogikk();

        Personinfo[] personinfo = null;

        int  personid = 0;
        int  kode_dep = 0;
        Date startdato = null;
        Date sluttdato = null;
        String dato_start = null;
        String dato_slutt = null;
        String embete = null;
          if((request.getParameter("person_id") != null && !request.getParameter("person_id").equals("")) &&
            (request.getParameter("startdato") != null && !request.getParameter("startdato").equals("")) &&
                  (request.getParameter("kode_dep") != null && !request.getParameter("kode_dep").equals("")) &&
                  (request.getParameter("sluttdato") != null && !request.getParameter("sluttdato").equals(""))
             )
		{
            personid = Integer.parseInt(request.getParameter("person_id"));
            kode_dep = Integer.parseInt(request.getParameter("kode_dep"));
            embete = request.getParameter("embete");

            dato_start = request.getParameter("startdato");
            dato_slutt = request.getParameter("sluttdato");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatter_2 = new SimpleDateFormat("dd-MM-yyyy");
            try {
                startdato = new Date(formatter.parse(dato_start).getTime());
                sluttdato =  new Date(formatter_2.parse(dato_slutt).getTime());
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

            try {
                conn = DatabaseConnectionFactory.getConnection();
                logikk.setConn(conn);

			  logikk.LagreStatsradEmbete(personid, kode_dep, startdato, sluttdato, embete);

            } catch (Exception e) {
                throw new ServletException(e);
            } finally {
                 try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
            }

           String url = request.getContextPath() + "/regjering/statsradinfo.p?person_id="+personid+"&kode_dep="+kode_dep+ "&startdato="+dato_start + "&sluttdato="+dato_slutt+"&embete_lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
        }

}