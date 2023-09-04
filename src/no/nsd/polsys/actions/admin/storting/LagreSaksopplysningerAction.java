package no.nsd.polsys.actions.admin.storting;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.admin.storting.VoteringsaksopplysningerLogikk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class LagreSaksopplysningerAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        VoteringsaksopplysningerLogikk logikk = new VoteringsaksopplysningerLogikk();

        Integer id = Util.strengTilInteger(request.getParameter("id"));
        //Integer doknr = Util.strengTilInteger(request.getParameter("partikode"));
        Object typesak = Util.tomStrengTilNull(request.getParameter("typesak"));
        Object vottype  = Util.tomStrengTilNull(request.getParameter("vottype"));
        String sesjon =  Util.tomStrengTilNull(request.getParameter("sesjon"));
        
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            conn.setAutoCommit(false);

            if (id != null) {
                logikk.oppdaterSaksopplysninger(typesak, vottype, id);
            }

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

        String url = request.getContextPath() + "/storting/votering/saksopplysningerlist.p?lagret&sesjon="+sesjon;
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
