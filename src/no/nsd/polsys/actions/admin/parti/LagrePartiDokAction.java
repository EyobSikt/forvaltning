package no.nsd.polsys.actions.admin.parti;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.parti.admin.PartiDokLogikk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author hvb
 */
public class LagrePartiDokAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
       PartiDokLogikk logikk = new PartiDokLogikk();

        Integer id = Util.strengTilInteger(request.getParameter("id"));
        Integer doknr = Util.strengTilInteger(request.getParameter("doknr"));
        String tittel = Util.tomStrengTilNull(request.getParameter("tittel"));
        Integer doktypeid = Util.strengTilInteger(request.getParameter("doktype"));
        Integer partiid = Util.strengTilInteger(request.getParameter("partinavn"));
        Integer aar = Util.strengTilInteger(request.getParameter("aar"));
        
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            
            conn.setAutoCommit(false);
            if (id == null) {
                logikk.registrerNyRelasjonAndreEnhet(doknr, tittel, doktypeid, partiid, aar);
            } else {
                logikk.oppdaterRelasjonAndreEnhet(id, tittel, doktypeid, partiid, aar);
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

        String url = request.getContextPath() + "/parti/partidok.p?lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
