package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EndringLogikk;

/**
 *
 * @author hvb
 */
public class EndringsnummerBrukAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        List tab = null;
        Integer enummer = null;

        String tabellnavn = "t_forvaltning_endring";

        try {
            enummer = new Integer(request.getParameter("enummer"));
        } catch (Exception ignored) {}

        if (enummer == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            EndringLogikk logikk = new EndringLogikk();
            logikk.setConn(conn);
            tab = logikk.getEndringsnummerBruk(tabellnavn, enummer);
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

        request.setAttribute("tab", tab);
        request.setAttribute("tabellnavn", tabellnavn);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/endringsnummer_bruk.jsp");
        rd.forward(request, response);
    }


}
