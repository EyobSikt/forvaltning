
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.KrdEnhetLogikk;
import no.nsd.polsys.modell.forvaltning.KrdEnhet;

/**
 *
 * @author hvb
 */
public class KrdEnhetAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        KrdEnhetLogikk logikk = new KrdEnhetLogikk();
        KrdEnhet enhet = null;
        List<Integer[]> ansatte = null;

        Integer krdid = null;

        String uri = request.getRequestURI();
        try {
            int i = uri.lastIndexOf("/");
            String s = uri.substring(i + 1);
            krdid = new Integer(s);
        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }


        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            enhet = logikk.getKrdEnhet(krdid);
            if (enhet == null) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
                rd.forward(request, response);
                return;
            }
            ansatte = logikk.getAntallAnsatte(krdid);

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
        request.setAttribute("ansatte", ansatte);
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/krd_enhet.jsp");
        rd.forward(request, response);
    }


}
