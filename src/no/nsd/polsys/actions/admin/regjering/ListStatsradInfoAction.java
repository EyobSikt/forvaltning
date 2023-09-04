package no.nsd.polsys.actions.admin.regjering;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.admin.regjering.RedigereStatsradLogikk;
import no.nsd.polsys.modell.admin.regjering.Personinfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ListStatsradInfoAction extends HttpServlet {

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
        RedigereStatsradLogikk logikk = new RedigereStatsradLogikk();

        Personinfo[] personinfo = null;
        Personinfo[] personaktivitetinfo = null;
        List<Personinfo> departmentnavnilist = null;
        List<Personinfo> embetenavnilist = null;


            try {
                conn = DatabaseConnectionFactory.getConnection();
                logikk.setConn(conn);

                if((request.getParameter("person_id") != null && !request.getParameter("person_id").equals("")))
		{
			int  person_id = Integer.parseInt(request.getParameter("person_id"));
            personaktivitetinfo = logikk.getPersonAktivitetInfo(person_id);
            personinfo = logikk.getPersonInfo(person_id);
            departmentnavnilist = logikk.getAlleDepartment();
            embetenavnilist = logikk.getAlleEmbete();

		}



            } catch (Exception e) {
                throw new ServletException(e);
            } finally {
                 try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
            }

            request.setAttribute("oppdaterepersoninfo", personaktivitetinfo);
            request.setAttribute("personinfo", personinfo);
           request.setAttribute("departmentnavnilist", departmentnavnilist);
        request.setAttribute("embetenavnilist", embetenavnilist);





            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/regjering/oppdaterestatsradinfo.jsp");
            rd.forward(request, response);
        }


}