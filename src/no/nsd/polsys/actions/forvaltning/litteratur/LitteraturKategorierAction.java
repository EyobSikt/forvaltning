
package no.nsd.polsys.actions.forvaltning.litteratur;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.LitteraturKategoritypeCacheFactory;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class LitteraturKategorierAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        List<DokCache> kategorier = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            kategorier = LitteraturKategoritypeCacheFactory.getDokdata(conn);
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

        request.setAttribute("kategorier", kategorier);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur/litteratur_kategorier.jsp");
        rd.forward(request, response);
    }


}
