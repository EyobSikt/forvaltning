
package no.nsd.polsys.actions.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.UtvalgLogikk;
import no.nsd.polsys.modell.forvaltning.Utvalg;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvb
 */
public class EnhetUtvalgAction {

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
        Integer idnum = (Integer) request.getAttribute("idnum");

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            utvalg = logikk.getUtvalg(idnum, null, null, null, null, null);
            List<Utvalg> utvalg2 = logikk.getUtvalgForEnhet(idnum);
            
            // for å unngå nullpointerexception
            if (utvalg == null) {
               utvalg = new ArrayList<Utvalg>();
            }
            if (utvalg2 == null) {
               utvalg2 = new ArrayList<Utvalg>();
            }
            
            utvalg.addAll(utvalg2);
            
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

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_utvalg.jsp");
        rd.forward(request, response);
    }


}
