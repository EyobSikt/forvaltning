package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatssekretarLogikk;

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
import java.util.Calendar;


public class LagreStatssekretarFoedtAction extends HttpServlet {

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
        RedigereStatssekretarLogikk logikk = new RedigereStatssekretarLogikk();


        Integer personid = Util.strengTilInteger(request.getParameter("person_id"));
        String foedtdato = Util.tomStrengTilNull(request.getParameter("foedt"));

        Date foedt_dato = null;
        Integer foedtaar =0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c_f = Calendar.getInstance();


        //Calendar calendar = Calendar.getInstance();
        //calendar.clear();
        //calendar.set(Calendar.MONTH, month);
        //calendar.set(Calendar.YEAR, Integer.parseInt(foedtdato));
        //Date date = calendar.getTime();


        try {
            if(foedtdato!=null && foedtdato.length() > 4 ){
                foedt_dato = new Date(formatter.parse(foedtdato).getTime());
                c_f.setTime(formatter.parse(foedtdato));
                foedtaar =  c_f.get(Calendar.YEAR);
            }
            if(foedtdato!=null && foedtdato.length() == 4 ){

                c_f.set(Calendar.YEAR, Integer.parseInt(foedtdato));
                Date date = new Date(c_f.getTimeInMillis());
                //Date date =  c_f.getTime();
                //foedt_dato =  date;
                foedtaar = Integer.valueOf(foedtdato);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            conn.setAutoCommit(false);

            logikk.LagreStatssekretarFoedtdato(personid, foedt_dato, foedtaar);
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {}
            }
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

           String url = request.getContextPath() + "/regjering/statssekretar.p?foedtdato_lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
        }

}