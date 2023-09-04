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


public class RegistreStatsradInfoAction {

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

        Integer id = Util.strengTilInteger(request.getParameter("personkode"));
        //String embete = Util.tomStrengTilNull(request.getParameter("embete"));
        Integer embetekode = Util.strengTilInteger(request.getParameter("embetekode"));
        Integer departmentkode = Util.strengTilInteger(request.getParameter("departmentkode"));
        String startdato = Util.tomStrengTilNull(request.getParameter("startdato"));
        String sluttdato = Util.tomStrengTilNull(request.getParameter("sluttdato"));


        String etternavn = Util.tomStrengTilNull(request.getParameter("etternavn"));
        String fornavn = Util.tomStrengTilNull(request.getParameter("fornavn"));
        Integer parti = Util.strengTilInteger(request.getParameter("parti"));
        String eksternkommentar = Util.tomStrengTilNull(request.getParameter("eksternkommentar"));
        Integer foedtaar = Util.strengTilInteger(request.getParameter("foedtaar"));
        Integer doedsaar = Util.strengTilInteger(request.getParameter("doedsaar"));
        Integer kode_dep = Util.strengTilInteger(request.getParameter("kode_dep"));

        Date start_dato = null;
        Date slutt_dato = null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter_2 = new SimpleDateFormat("dd-MM-yyyy");

        try {
            if(startdato!=null) {
                start_dato = new Date(formatter.parse(startdato).getTime());
            }
            if(sluttdato!=null) {
                slutt_dato = new Date(formatter_2.parse(sluttdato).getTime());
            }
            else{
                slutt_dato = new Date(formatter_2.parse("09-09-9999").getTime());
            }

        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            
            conn.setAutoCommit(false);

                logikk.RegisterUpdateStatsradInfo(id, fornavn, etternavn, foedtaar, doedsaar, parti, kode_dep, departmentkode, embetekode, eksternkommentar, start_dato, slutt_dato);
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

        String url = request.getContextPath() + "/regjering/statsradinfo.p?person_id=" + id +"&lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
