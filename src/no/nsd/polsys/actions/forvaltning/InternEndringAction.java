
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
public class InternEndringAction {

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
        Map<Integer, DokCache> nivaaerMapping = null;
        Map<Integer, DokCache> endringskoderMapping = null;
        List<DokCache> nivaaer = null;
        List<DokCache> endringskoder = null;
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
            sistOppdatertDato = parameterLogikk.getOppdatertInternEnhet();

            if (engelsk) {
                nivaaerMapping = NivaaCacheFactory.getDokdataEngelsk(conn);
                endringskoderMapping = EndringskodeCacheFactory.getDokdataEngelsk(conn);
            } else {
                nivaaerMapping = NivaaCacheFactory.getDokdata(conn);
                endringskoderMapping = EndringskodeCacheFactory.getDokdata(conn);
            }

            nivaaer = new ArrayList<DokCache>(nivaaerMapping.values());
            Collections.sort(nivaaer);
            endringskoder = new ArrayList<DokCache>(endringskoderMapping.values());
            Collections.sort(endringskoder);

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
        request.setAttribute("nivaaer", nivaaer);
        request.setAttribute("endringskoder", endringskoder);
        request.setAttribute("departementer", departementer);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/intern_endring.jsp");
        rd.forward(request, response);
    }


}
