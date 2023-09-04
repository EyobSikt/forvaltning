
package no.nsd.polsys.actions.forvaltning.litteratur;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.LitteraturLogikk;
import no.nsd.polsys.modell.forvaltning.Litteratur;

/**
 *
 * @author hvb
 */
public class LitteraturSokAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        LitteraturLogikk logikk = new LitteraturLogikk();
        List<Litteratur> litteratur = null;

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
                litteratur = logikk.sokLitteratur(sok);
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

        request.setAttribute("litteratur", litteratur);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur/litteratur_sok.jsp");
        rd.forward(request, response);
    }


}
