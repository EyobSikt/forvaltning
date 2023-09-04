package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;

/**
 *
 * @author hvb
 */
public class CofogAlleListeAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;

        EnhetDivLogikk logikk = new EnhetDivLogikk();
        Collection enheter = null;

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            enheter = logikk.getEnheter();
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

        request.setAttribute("enheter", enheter);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/cofog_alle_liste.jsp");
        rd.forward(request, response);
    }


}
