
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
import no.nsd.polsys.factories.forvaltning.GrunnenhetCacheFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class ForvaltningsenhetslisteAction {

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

        List<Integer> tilknytningsformer = new ArrayList<Integer>();
        Map<Integer, DokCache> tilknytningsformerNavn = null;
        Map<Integer, DokCache> grunnenheterNavn = null;

        try {
            int y = Integer.parseInt(request.getParameter("y"));
            int m = Integer.parseInt(request.getParameter("m"));
            int d = Integer.parseInt(request.getParameter("d"));
            brukerdato = new Dato(y, m, d);
        } catch (Exception e) {
            brukerdato = new Dato(2010, 1, 1);
        }

        try {
            String[] n = request.getParameterValues("t");
            if (n == null || n.length == 0) {
                throw new Exception();
            }
            for (String s : n) {
                tilknytningsformer.add(new Integer(s));
            }
        } catch (Exception e) {
            tilknytningsformer = new ArrayList<Integer>();
            tilknytningsformer.add(20);
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            departementLogikk.setConn(conn);
            parameterLogikk.setConn(conn);

            sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();

            if (request.getAttribute("en") != null) {
                tilknytningsformerNavn = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
                departementLogikk.brukEngelsk();
                grunnenheterNavn = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
            } else {
                tilknytningsformerNavn = TilknytningsformCacheFactory.getDokdata(conn);
                grunnenheterNavn = GrunnenhetCacheFactory.getDokdata(conn);
            }

            depenheter = departementLogikk.getDepartementsenheter(brukerdato.getDate(), tilknytningsformer, false);


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

        request.setAttribute("tilknytningsformerNavn", tilknytningsformerNavn);
        request.setAttribute("grunnenheterNavn", grunnenheterNavn);
        request.setAttribute("tilknytningsformer", tilknytningsformer);
        request.setAttribute("depenheter", depenheter);
        request.setAttribute("brukerdato", brukerdato);
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/forvaltningsenhetsliste.jsp");
        rd.forward(request, response);
    }


}
