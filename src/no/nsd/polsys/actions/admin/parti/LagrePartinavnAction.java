package no.nsd.polsys.actions.admin.parti;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.parti.admin.PartinavnLogikk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class LagrePartinavnAction {

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

        Integer id = Util.strengTilInteger(request.getParameter("partikode"));
        //Integer doknr = Util.strengTilInteger(request.getParameter("partikode"));
        String partinavn = Util.tomStrengTilNull(request.getParameter("partinavn"));
        String partinavneng = Util.tomStrengTilNull(request.getParameter("partinavneng"));
        String partinavnforkorting = Util.tomStrengTilNull(request.getParameter("partinavnforkorting"));
        String dokumentasjon = Util.tomStrengTilNull(request.getParameter("dokumentasjon"));

        
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            
            conn.setAutoCommit(false);
            if (id == null) {
                logikk.registrerNyParti(partinavn, partinavneng, partinavnforkorting, dokumentasjon);
            } else {
                logikk.oppdaterParti(partinavn, partinavneng, partinavnforkorting, dokumentasjon, id);
            }
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

        String url = request.getContextPath() + "/parti/partilist.p?lagret";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
