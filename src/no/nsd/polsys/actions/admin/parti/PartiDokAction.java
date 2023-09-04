package no.nsd.polsys.actions.admin.parti;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.parti.admin.PartiDokLogikk;
import no.nsd.polsys.modell.parti.admin.PartiDok;

/**
 *
 * @author hvb
 */
public class PartiDokAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PartiDokLogikk logikk = new PartiDokLogikk();

        List<PartiDok> enheter = null;
        List<PartiDok> partilist = null;

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            enheter = logikk.getAlleRelasjonAndreEnheter();
             partilist = logikk.getAllePartier();
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
         request.setAttribute("partilist", partilist);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/parti/partidok.jsp");
        rd.forward(request, response);
    }


}
