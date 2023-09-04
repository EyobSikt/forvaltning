
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.OppgaveLogikk;

/**
 *
 * @author hvb
 */
public class EnhetOppgaveAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        OppgaveLogikk oppgaveLogikk = new OppgaveLogikk();

        // mapping: oppgavekode --> oppgavenavn.
        Map<Integer, String> oppgavenavn = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }
        
        try {
            conn = DatabaseConnectionFactory.getConnection();
            oppgaveLogikk.setConn(conn);

            oppgavenavn = oppgaveLogikk.getOppgaver();

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

        request.setAttribute("oppgavenavn", oppgavenavn);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_oppgave.jsp");
        rd.forward(request, response);
    }


}
