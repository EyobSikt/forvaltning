
package no.nsd.polsys.actions.forvaltning.litteratur;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.LitteraturKategoritypeCacheFactory;
import no.nsd.polsys.logikk.forvaltning.LitteraturLogikk;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Litteratur;

/**
 *
 * @author hvb
 */
public class LitteraturAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        LitteraturLogikk litteraturLogikk = new LitteraturLogikk();
        Integer pubId = null;
        Litteratur litteratur = null;
        SortedMap<Integer, List<String>> kategorisering = null;
        List<DokCache> kategorityper = null;
        List<Litteratur> LittEnheter = null;
        List<Litteratur> LittTilknytninger = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        String uri = request.getRequestURI();
        try {
            int i = uri.lastIndexOf("/");
            String s = uri.substring(i + 1);
            pubId = new Integer(s);
        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            litteraturLogikk.setConn(conn);
            litteratur = litteraturLogikk.getLitteratur(pubId);
            if (litteratur == null) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
                rd.forward(request, response);
                return;
            }
            kategorisering = litteraturLogikk.getKategorisering(pubId); // >= 5
            kategorityper = LitteraturKategoritypeCacheFactory.getDokdata(conn);
            LittEnheter = litteraturLogikk.getEnheterTilLitteratur(pubId);
            LittTilknytninger = litteraturLogikk.getTilknytningsformerTilLitteratur(pubId);

            // Fikser http foran webadresser.
            if (litteratur.getLenkePublikasjon() != null && !litteratur.getLenkePublikasjon().startsWith("http")) {
                litteratur.setLenkePublikasjon("http://" + litteratur.getLenkePublikasjon());
            }
            if (litteratur.getLenkeOmtale() != null && !litteratur.getLenkeOmtale().startsWith("http")) {
                litteratur.setLenkeOmtale("http://" + litteratur.getLenkeOmtale());
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

        request.setAttribute("litteratur", litteratur);
        request.setAttribute("LittEnheter", LittEnheter); // kategori 1
        request.setAttribute("LittTilknytninger", LittTilknytninger);  // kategori 2
        request.setAttribute("kategorisering", kategorisering);  // kategori >= 5
        request.setAttribute("kategorityper", kategorityper);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur/litteratur.jsp");
        rd.forward(request, response);
    }


}
