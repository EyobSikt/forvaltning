package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatsradLogikk;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SletteStatsradAction extends HttpServlet {

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
        RedigereStatsradLogikk logikk = new RedigereStatsradLogikk();


        Integer personid = Util.strengTilInteger(request.getParameter("person_id"));
        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            conn.setAutoCommit(false);

            logikk.SleteStatsrad(personid);
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

           String url = request.getContextPath() + "/regjering/statsrad.p?slettet";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
        }

}