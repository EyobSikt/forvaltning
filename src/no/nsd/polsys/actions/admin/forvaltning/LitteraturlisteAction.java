package no.nsd.polsys.actions.admin.forvaltning;

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
public class LitteraturlisteAction {

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

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            litteratur = logikk.getAlleLitteratur();
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

        request.setAttribute("litteratur", litteratur);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteraturliste.jsp");
        rd.forward(request, response);
    }


}
