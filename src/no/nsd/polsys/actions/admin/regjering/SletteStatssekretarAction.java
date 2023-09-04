package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatsradLogikk;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatssekretarLogikk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class SletteStatssekretarAction extends HttpServlet {

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
        RedigereStatssekretarLogikk logikk = new RedigereStatssekretarLogikk();


        Integer personid = Util.strengTilInteger(request.getParameter("person_id"));
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            conn.setAutoCommit(false);

            logikk.SleteStatssekretar(personid);
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

           String url = request.getContextPath() + "/regjering/statssekretar.p?slettet";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
        }

}