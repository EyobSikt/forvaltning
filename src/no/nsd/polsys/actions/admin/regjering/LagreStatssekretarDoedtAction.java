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


public class LagreStatssekretarDoedtAction extends HttpServlet {

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        RedigereStatssekretarLogikk logikk = new RedigereStatssekretarLogikk();


        Integer personid = Util.strengTilInteger(request.getParameter("person_id"));
        String doedtdato = Util.tomStrengTilNull(request.getParameter("doedt"));

        Date doedt_dato = null;
        Integer doedtaar =0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c_f = Calendar.getInstance();

        try {
            if(doedtdato!=null){
                doedt_dato = new Date(formatter.parse(doedtdato).getTime());
                c_f.setTime(formatter.parse(doedtdato));
                doedtaar =  c_f.get(Calendar.YEAR);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            conn.setAutoCommit(false);

            logikk.LagreStatssekretarDoedtdato(personid, doedt_dato, doedtaar);
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

        String url = request.getContextPath() + "/regjering/statssekretar.p?doedtdato_lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }

}