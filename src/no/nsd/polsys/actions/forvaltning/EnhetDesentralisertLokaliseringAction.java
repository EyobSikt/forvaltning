
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.TallgruppeLogikk;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class EnhetDesentralisertLokaliseringAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        TallgruppeLogikk tallgruppeLogikk = new TallgruppeLogikk();
        Enhet enhet = new Enhet();
        Integer id = null;
        String navn = null;
        EnhetLogikk enhetLogikk = new EnhetLogikk();
        Tallgruppe tallgruppe = null;
        boolean engelsk = false;
        String uri = request.getRequestURI();
        
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        try {
            int i = uri.lastIndexOf("/");
            String s = uri.substring(i + 1);
            id = new Integer(s);
        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            enhetLogikk.setConn(conn);
            tallgruppeLogikk.setConn(conn);

            if (engelsk) {
                enhetLogikk.brukEngelsk();
                tallgruppeLogikk.brukEngelsk();
            }

            tallgruppe = tallgruppeLogikk.getTallgruppe(id);

            navn = enhetLogikk.getSisteNavnTilEnhet(tallgruppe.getIdnum());
            enhet.setIdnum(tallgruppe.getIdnum());
            enhet.setLangtNavn(navn);

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

        request.setAttribute("enhet", enhet);
        request.setAttribute("tallgruppe", tallgruppe);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_desentralisert_lokalisering.jsp");
        rd.forward(request, response);
    }


}
