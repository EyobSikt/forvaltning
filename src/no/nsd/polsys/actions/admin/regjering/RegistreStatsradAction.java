package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatsradLogikk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class RegistreStatsradAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        RedigereStatsradLogikk logikk = new RedigereStatsradLogikk();

        String fornavn = Util.tomStrengTilNull(request.getParameter("fornavn"));
        String etternavn = Util.tomStrengTilNull(request.getParameter("etternavn"));
        //Integer foedtaar = Util.strengTilInteger(request.getParameter("foedtaar"));
        //Integer doedaar = Util.strengTilInteger(request.getParameter("doedaar"));
        Integer partikode = Util.strengTilInteger(request.getParameter("partikode"));
        String foedtdato = Util.tomStrengTilNull(request.getParameter("foedt"));
        String doedtdato = Util.tomStrengTilNull(request.getParameter("doedt"));
        Integer kjoenn = Util.strengTilInteger(request.getParameter("kjoenn"));
        String periodestart = Util.tomStrengTilNull(request.getParameter("periodestart"));

        Date foedt_dato = null;
        Date doedt_dato = null;
        Integer foedtaar =null;
        Integer doedaar =null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter_2 = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c_f = Calendar.getInstance();
        Calendar c_d = Calendar.getInstance();
        try {
            if(foedtdato!=null && foedtdato.length() > 4 ){
                foedt_dato = new Date(formatter.parse(foedtdato).getTime());
                c_f.setTime(formatter.parse(foedtdato));
                foedtaar =  c_f.get(Calendar.YEAR);
            }
            if(foedtdato!=null && foedtdato.length() == 4 ){

                c_f.set(Calendar.YEAR, Integer.parseInt(foedtdato));
                Date date = new Date(c_f.getTimeInMillis());
                foedtaar = Integer.valueOf(foedtdato);
            }
            if(doedtdato!=null){
                doedt_dato =  new Date(formatter_2.parse(doedtdato).getTime());
                c_d.setTime(formatter.parse(doedtdato));
                doedaar =  c_d.get(Calendar.YEAR);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            
            conn.setAutoCommit(false);

                logikk.registrerNyStatsrad(fornavn, etternavn, foedt_dato, foedtaar, doedt_dato, doedaar, partikode, kjoenn, periodestart);
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

        String url = request.getContextPath() + "/regjering/statsrad.p?lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
