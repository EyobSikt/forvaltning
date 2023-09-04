
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.NivaaCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class DepartementsenhetslisteAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        DepartementLogikk departementLogikk = new DepartementLogikk();
        ParameterLogikk parameterLogikk = new ParameterLogikk();
        // dato databasen er sist oppdatert.
        Dato sistOppdatertDato = null;

        List<List<Enhet>> depenheter = null;
        // dato bruker har oppgitt.
        Dato brukerdato = new Dato();

        List<Integer> nivaaer = new ArrayList<Integer>();
        Map<Integer, DokCache> nivaaNavn = null;

        try {
            int y = Integer.parseInt(request.getParameter("y"));
            int m = Integer.parseInt(request.getParameter("m"));
            int d = Integer.parseInt(request.getParameter("d"));
            brukerdato = new Dato(y, m, d);
        } catch (Exception e) {
            brukerdato = new Dato(2010, 1, 1);
        }

        try {
            String[] n = request.getParameterValues("n");
            if (n == null || n.length == 0) {
                throw new Exception();
            }
            for (String s : n) {
                nivaaer.add(new Integer(s));
            }
        } catch (Exception e) {
            nivaaer = new ArrayList<Integer>();
            nivaaer.add(10);
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            departementLogikk.setConn(conn);
            parameterLogikk.setConn(conn);

            sistOppdatertDato = parameterLogikk.getOppdatertInternEnhet();

            if (request.getAttribute("en") != null) {
                nivaaNavn = NivaaCacheFactory.getDokdataEngelsk(conn);
                departementLogikk.brukEngelsk();
            } else {
                nivaaNavn = NivaaCacheFactory.getDokdata(conn);
            }

            depenheter = departementLogikk.getDepartementsenheter(brukerdato.getDate(), nivaaer, true);


        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {
                conn = null;
            }
        }

        request.setAttribute("nivaaNavn", nivaaNavn);
        request.setAttribute("nivaaer", nivaaer);
        request.setAttribute("depenheter", depenheter);
        request.setAttribute("brukerdato", brukerdato);
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/departementsenhetsliste.jsp");
        rd.forward(request, response);
    }


}
