
package no.nsd.polsys.actions.forvaltning.litteratur;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.forvaltning.LitteraturLogikk;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Litteratur;

/**
 *
 * @author hvb
 */
public class LitteraturTilknytningsformAction {

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
        Integer tilknytningsform = null;
        Map<Integer, DokCache> tilknytninger = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        String uri = request.getRequestURI();
        try {
            int i = uri.lastIndexOf("/");
            String s = uri.substring(i + 1);
            tilknytningsform = new Integer(s);
        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            litteratur = logikk.getLitteraturTilTilknytningsform(tilknytningsform);
            if (engelsk) {
                tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
            } else {
                tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
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
        request.setAttribute("tilknytningsform", tilknytningsform);
        request.setAttribute("tilknytninger", tilknytninger);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur/litteratur_tilknytningsform.jsp");
        rd.forward(request, response);
    }


}
