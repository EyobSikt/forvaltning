
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
import no.nsd.polsys.modell.forvaltning.Utvalgmedlemskap;
import no.nsd.polsys.modell.forvaltning.Utvalgperson;

/**
 *
 * @author hvb
 */
public class UtvalgpersonAction {

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
        Integer pnr = null;
        Utvalgperson person = null;
        List<Utvalgmedlemskap> medlem = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        String uri = request.getRequestURI();
        try {
            int i = uri.lastIndexOf("/");
            String s = uri.substring(i + 1);
            pnr = new Integer(s);
        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            person = logikk.getPerson(pnr);
            if (person == null) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
                rd.forward(request, response);
                return;
            }

            medlem = logikk.getPersonMedlem(pnr);

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

        request.setAttribute("person", person);
        request.setAttribute("medlem", medlem);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/utvalg/utvalg_person.jsp");
        rd.forward(request, response);
    }


}
