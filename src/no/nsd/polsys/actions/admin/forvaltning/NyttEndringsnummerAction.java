package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EndringLogikk;

/**
 *
 * @author hvb
 */
public class NyttEndringsnummerAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        EndringLogikk logikk = new EndringLogikk();

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            logikk.registrerNyttEndringsnummer();
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

        String url = request.getContextPath() + "/forvaltning/endringsnummer.p";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
