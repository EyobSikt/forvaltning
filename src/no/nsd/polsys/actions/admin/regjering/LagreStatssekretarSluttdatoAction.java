package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatssekretarLogikk;
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


public class LagreStatssekretarSluttdatoAction extends HttpServlet {

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
        RedigereStatssekretarLogikk logikk = new RedigereStatssekretarLogikk();

        Personinfo[] personinfo = null;

        int  personid = 0;
        int  kode_dep = 0;
        Date sluttdato = null;
        Date startdato = null;
        String dato_start = null;

          if((request.getParameter("person_id") != null && !request.getParameter("person_id").equals("")) &&
            (request.getParameter("virkeperiode_slutt") != null && !request.getParameter("virkeperiode_slutt").equals("")) &&
                  (request.getParameter("kode_dep") != null && !request.getParameter("kode_dep").equals("")) &&
                  (request.getParameter("startdato") != null && !request.getParameter("startdato").equals(""))
             )
		{
            personid = Integer.parseInt(request.getParameter("person_id"));
            kode_dep = Integer.parseInt(request.getParameter("kode_dep"));
            String dato = request.getParameter("virkeperiode_slutt");
            dato_start = request.getParameter("startdato");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatter_2 = new SimpleDateFormat("dd-MM-yyyy");
            try {
                sluttdato = new Date(formatter.parse(dato).getTime());
                startdato =  new Date(formatter_2.parse(dato_start).getTime());
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

            try {
                conn = DatabaseConnectionFactory.getConnection();
                logikk.setConn(conn);

			  logikk.LagreStatssekretarSluttdato(personid, kode_dep, sluttdato, startdato);

            } catch (Exception e) {
                throw new ServletException(e);
            } finally {
                 try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
            }

           String url = request.getContextPath() + "/regjering/statssekretarinfo.p?person_id="+personid+"&kode_dep="+kode_dep+"&startdato="+dato_start;
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
        }

}