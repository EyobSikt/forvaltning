package no.nsd.polsys.actions.admin.storting;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.admin.storting.TempDataLogikk;
import no.nsd.polsys.modell.admin.storting.Votering;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class SjekkSaksopplysningerAction extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Connection conn = null;
       TempDataLogikk logikk = new TempDataLogikk();
       Votering modellvotering = new Votering();
       int antallvoteringer = 0;
        try {

            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            conn.setAutoCommit(false);

            String sesjontid = null;

            if (request.getParameter("sesjontid") != null) {
                sesjontid = request.getParameter("sesjontid");
            }

            List<List<String>> votid = logikk.voteringIdVoteringresultat(sesjontid);
            antallvoteringer = votid.size();
            request.setAttribute("voteringList","test");
            System.out.println("size of ids list22 :"+votid.size());

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


         String url = request.getContextPath() + "/storting/votering/lessaker.p?"+"antall="+antallvoteringer;
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);

    }

}