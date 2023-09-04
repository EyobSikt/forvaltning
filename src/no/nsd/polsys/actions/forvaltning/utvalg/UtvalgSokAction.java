
package no.nsd.polsys.actions.forvaltning.utvalg;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.UtvalgLogikk;
import no.nsd.polsys.modell.forvaltning.Utvalg;

/**
 *
 * @author hvb
 */
public class UtvalgSokAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        UtvalgLogikk logikk = new UtvalgLogikk();
        List<Utvalg> utvalg = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        String sok = request.getParameter("s");

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            if (sok != null && sok.length() != 0) {
                if (sok.length() > 100) {
                    sok = sok.substring(0, 100);
                }
                utvalg = logikk.sokUtvalg(sok);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {
                conn = null;
            }
        }

        request.setAttribute("utvalg", utvalg);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/utvalg/utvalg_sok.jsp");
        rd.forward(request, response);
    }


}
