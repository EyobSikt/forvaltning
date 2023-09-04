
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
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
public class EnhetDesentralisertAction {

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
        String navn = null;
        EnhetLogikk enhetLogikk = new EnhetLogikk();
        List<Tallgruppe> tallgrupper = null;
        boolean engelsk = false;

        Integer idnum = (Integer) request.getAttribute("idnum");
        Enhet enhet = (Enhet) request.getAttribute("enhet");

        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            enhetLogikk.setConn(conn);
            tallgruppeLogikk.setConn(conn);

            if (engelsk) {
                enhetLogikk.brukEngelsk();
                tallgruppeLogikk.brukEngelsk();
            }

            if (!enhetLogikk.eksistererEnhet(idnum)) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
                rd.forward(request, response);
                return;
            }
            navn = enhetLogikk.getSisteNavnTilEnhet(idnum);
            enhet.setIdnum(idnum);
            enhet.setLangtNavn(navn);

            tallgrupper = tallgruppeLogikk.getTallgrupper(idnum);

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

        request.setAttribute("tallgrupper", tallgrupper);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_desentralisert.jsp");
        rd.forward(request, response);
    }


}
