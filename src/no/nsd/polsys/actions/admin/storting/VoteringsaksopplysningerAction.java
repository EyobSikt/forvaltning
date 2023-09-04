package no.nsd.polsys.actions.admin.storting;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.admin.storting.VoteringsaksopplysningerLogikk;
import no.nsd.polsys.modell.admin.storting.Votering;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author eyob
 */
public class VoteringsaksopplysningerAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        VoteringsaksopplysningerLogikk logikk = new VoteringsaksopplysningerLogikk();

        List<Votering> sakerlist = null;
        String sesjon_id = null;


        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            if (request.getParameter("sesjon") != null) {
                sesjon_id = request.getParameter("sesjon");
            }

            sakerlist = logikk.getAllesaksopplysninger(sesjon_id);
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }                        

         request.setAttribute("sakerlist", sakerlist);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/storting/votering/saksopplysningerlist.jsp");
        rd.forward(request, response);
    }


}
