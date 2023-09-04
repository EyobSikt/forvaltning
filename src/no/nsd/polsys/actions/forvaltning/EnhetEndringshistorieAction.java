
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.*;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.modell.forvaltning.Cofog;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EnhetEndringshistorieAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        EnhetLogikk enhetLogikk = new EnhetLogikk();
        List<Enhet> endringer = null;
        Map<Integer, DokCache> endringskoder = null;
        Map<Integer, DokCache> tilknytninger = null;
        Map<Integer, DokCache> nivaaer = null;
        Map<Integer, DokCache> grunnenheter = null;
        Map<String, Cofog> cofog = null;
        String navn = null;

        Integer idnum = (Integer) request.getAttribute("idnum");

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            enhetLogikk.setConn(conn);
            if (engelsk) {
                enhetLogikk.brukEngelsk();
            }
            endringer = enhetLogikk.getEndringer(idnum, true);
            if (endringer == null || endringer.isEmpty()) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
                rd.forward(request, response);
                return;
            }

            if (engelsk) {
                endringskoder = EndringskodeCacheFactory.getDokdataEngelsk(conn);
                tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
                nivaaer = NivaaCacheFactory.getDokdataEngelsk(conn);
                grunnenheter = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
                cofog = CofogCacheFactory.getDokdataEngelsk(conn);
            } else {
                endringskoder = EndringskodeCacheFactory.getDokdata(conn);
                tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
                nivaaer = NivaaCacheFactory.getDokdata(conn);
                grunnenheter = GrunnenhetCacheFactory.getDokdata(conn);
                cofog = CofogCacheFactory.getDokdata(conn);
            }

            // finner siste navn.
            for (int i = endringer.size() - 1; i >= 0; i-- ) {
                if (endringer.get(i).getLangtNavn() != null) {
                    navn = endringer.get(i).getLangtNavn();
                    break;
                }
            }

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

        request.setAttribute("navn", navn);
        request.setAttribute("endringer", endringer);

        request.setAttribute("endringskoder", endringskoder);
        request.setAttribute("tilknytninger", tilknytninger);
        request.setAttribute("nivaaer", nivaaer);
        request.setAttribute("grunnenheter", grunnenheter);
        request.setAttribute("cofog", cofog);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_endringshistorie.jsp");
        rd.forward(request, response);
    }


}
