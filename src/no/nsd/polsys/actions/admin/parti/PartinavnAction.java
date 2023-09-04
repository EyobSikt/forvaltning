package no.nsd.polsys.actions.admin.parti;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.parti.admin.PartinavnLogikk;
import no.nsd.polsys.modell.parti.admin.Partinavn;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author hvb
 */
public class PartinavnAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PartinavnLogikk logikk = new PartinavnLogikk();

        List<Partinavn> partnavnilist = null;

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            partnavnilist = logikk.getAllePartier();
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }                        

         request.setAttribute("partnavnilist", partnavnilist);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/parti/partilist.jsp");
        rd.forward(request, response);
    }


}
