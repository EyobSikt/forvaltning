
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;
import no.nsd.polsys.logikk.forvaltning.OppgaveLogikk;
import no.nsd.polsys.modell.forvaltning.OppgaveEnhet;

/**
 *
 * @author hvb
 */
public class OppgavelisteAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        EnhetDivLogikk enhetLogikk = new EnhetDivLogikk();
        OppgaveLogikk oppgaveLogikk = new OppgaveLogikk();

        // mapping: oppgave-kode --> liste med enheter.
        SortedMap<Integer, List<OppgaveEnhet>> oppgaveEnheter = null;
        Integer valgtAar = null;
        Integer vis = null;
        List<Integer> alleAar = null;
        // mapping: oppgave-kode --> oppgave-tekst.
        Map<Integer, String> oppgaver = null;
        
        try {
            valgtAar = new Integer(request.getParameter("y"));
        } catch (Exception e) {
            valgtAar = null;
        }

        try {
            vis = new Integer(request.getParameter("vis"));
        } catch (Exception e) {
            vis = null;
        }
        
        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            enhetLogikk.setConn(conn);
            oppgaveLogikk.setConn(conn);
            if (engelsk) {
                enhetLogikk.brukEngelsk();
            }
            
            alleAar = oppgaveLogikk.getAlleOppgaveAar();
            oppgaver = oppgaveLogikk.getOppgaver();

            if (valgtAar == null && alleAar != null && !alleAar.isEmpty()) {
                valgtAar = alleAar.get(0);
            }

            oppgaveEnheter = enhetLogikk.getOppgaveEnheter(valgtAar, vis);


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

        request.setAttribute("oppgaveEnheter", oppgaveEnheter);
        request.setAttribute("oppgaver", oppgaver);
        request.setAttribute("valgtAar", valgtAar);
        request.setAttribute("alleAar", alleAar);
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/oppgave_liste.jsp");
        rd.forward(request, response);
    }


}
