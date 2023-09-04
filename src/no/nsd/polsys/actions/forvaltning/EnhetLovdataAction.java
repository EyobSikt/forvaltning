
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.LovdataLogikk;
import no.nsd.polsys.modell.forvaltning.Lov;

/**
 *
 * @author hvb
 */
public class EnhetLovdataAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        LovdataLogikk lovdataLogikk = new LovdataLogikk();
        List<Lov> lovdata = null;

        Integer idnum = (Integer) request.getAttribute("idnum");

        try {
            conn = DatabaseConnectionFactory.getConnection();
            lovdataLogikk.setConn(conn);

            lovdata = lovdataLogikk.getLovdataTilEnhet(idnum);

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

        request.setAttribute("lovdata", lovdata);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_lovdata.jsp");
        rd.forward(request, response);
    }


}
