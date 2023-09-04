
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.EndringskodeCacheFactory;
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
public class LokaliseringAction {

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
        Map<Integer, DokCache> tilknytningsformMapping = null;
        Map<Integer, DokCache> endringskoderMapping = null;
        Map<Integer, DokCache> grunnenhetMapping = null;
        List<DokCache> tilknytningsformer = null;
        List<DokCache> endringskoder = null;
        List<DokCache> grunnenheter = null;
        List<Enhet> departementer = null;
        boolean engelsk = false;

        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            departementLogikk.setConn(conn);
            parameterLogikk.setConn(conn);
            if (engelsk) {
                departementLogikk.brukEngelsk();
            }
            sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();

            if (engelsk) {
                tilknytningsformMapping = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
                endringskoderMapping = EndringskodeCacheFactory.getDokdataEngelsk(conn);
                grunnenhetMapping = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
            } else {
                tilknytningsformMapping = TilknytningsformCacheFactory.getDokdata(conn);
                endringskoderMapping = EndringskodeCacheFactory.getDokdata(conn);
                grunnenhetMapping = GrunnenhetCacheFactory.getDokdata(conn);
            }

            tilknytningsformer = new ArrayList<DokCache>(tilknytningsformMapping.values());
            Collections.sort(tilknytningsformer);
            endringskoder = new ArrayList<DokCache>(endringskoderMapping.values());
            Collections.sort(endringskoder);
            grunnenheter = new ArrayList<DokCache>(grunnenhetMapping.values());
            Collections.sort(grunnenheter);

            departementer = departementLogikk.getHistoriskeDepartementer();

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

        request.setAttribute("sistOppdatertDato", sistOppdatertDato);
        request.setAttribute("tilknytningsformer", tilknytningsformer);
        request.setAttribute("endringskoder", endringskoder);
        request.setAttribute("grunnenheter", grunnenheter);
        request.setAttribute("departementer", departementer);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/lokalisering.jsp");
        rd.forward(request, response);
    }


}
