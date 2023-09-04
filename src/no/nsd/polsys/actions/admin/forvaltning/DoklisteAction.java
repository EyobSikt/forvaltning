package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.DokLogikk;

/**
 *
 * @author hvb
 */
public class DoklisteAction {

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
        String ptabellnavn = request.getParameter("tabellnavn");
        String tabellnavn = null;

        if (ptabellnavn != null && ptabellnavn.equals("t_forvaltning_endringskode")) tabellnavn = ptabellnavn;
        if (ptabellnavn != null && ptabellnavn.equals("t_forvaltning_grunnenhet")) tabellnavn = ptabellnavn;
        if (ptabellnavn != null && ptabellnavn.equals("t_forvaltning_nivaa")) tabellnavn = ptabellnavn;
        if (ptabellnavn != null && ptabellnavn.equals("t_forvaltning_tilknytningsform")) tabellnavn = ptabellnavn;
        if (ptabellnavn != null && ptabellnavn.equals("t_forvaltning_cofog")) tabellnavn = ptabellnavn;

        if (tabellnavn == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            DokLogikk logikk = new DokLogikk();
            logikk.setConn(conn);
            tab = logikk.getData(tabellnavn);
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

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/dokliste.jsp");
        rd.forward(request, response);
    }


}
