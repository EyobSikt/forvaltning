package no.nsd.polsys.actions.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.Util;

/**
 *
 * @author hvb
 */
public class LagreParameterAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        ParameterLogikk logikk = new ParameterLogikk();

        String parameternavn = Util.tomStrengTilNull(request.getParameter("parameternavn"));
        String parameterverdi = Util.tomStrengTilNull(request.getParameter("parameterverdi"));
        String dokumentasjon = Util.tomStrengTilNull(request.getParameter("dokumentasjon"));
        
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            
            conn.setAutoCommit(false);
            logikk.slettParameter(parameternavn);
            logikk.registrerNyParameter(parameternavn, parameterverdi, dokumentasjon);
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {}
            }
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

        String url = request.getContextPath() + "/felles/parametre.p?lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
