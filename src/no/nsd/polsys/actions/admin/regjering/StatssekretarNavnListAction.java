package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.admin.regjering.StatssekretarNavnListLogikk;
import no.nsd.polsys.modell.admin.regjering.Personinfo;
import no.nsd.polsys.modell.admin.regjering.Personnavn;

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
public class StatssekretarNavnListAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        StatssekretarNavnListLogikk logikk = new StatssekretarNavnListLogikk();

        List<Personnavn> statssekretarlist = null;
        List<Personinfo> partnavnilist = null;

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            statssekretarlist = logikk.getAlleStatssekretar();
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

        request.setAttribute("statssekretarlist", statssekretarlist);
        request.setAttribute("partnavnilist", partnavnilist);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/regjering/statssekretarlist.jsp");
        rd.forward(request, response);
    }


}
