
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.TallgruppeLogikk;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class EnhetSatellittAction {

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
        List<Tallgruppe> tallgrupper = null;
        boolean engelsk = false;
        int antallNaa = 0;
        int antallNedlagt = 0;

        Integer idnum = (Integer) request.getAttribute("idnum");

        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            tallgruppeLogikk.setConn(conn);

            if (engelsk) {
                tallgruppeLogikk.brukEngelsk();
            }

            tallgrupper = tallgruppeLogikk.getSatellitter(idnum);

            if (tallgrupper != null) {
                for (Tallgruppe t : tallgrupper) {
                    if (t.getTilTidspunkt() == null) {
                        antallNaa++;
                    } else {
                        antallNedlagt++;
                    }
                }
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

        request.setAttribute("tallgrupper", tallgrupper);
        request.setAttribute("antallNaa", antallNaa);
        request.setAttribute("antallNedlagt", antallNedlagt);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_satellitt.jsp");
        rd.forward(request, response);
    }


}
