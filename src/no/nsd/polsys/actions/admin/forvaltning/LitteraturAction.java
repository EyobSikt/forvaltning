package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
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
        LitteraturLogikk logikk = new LitteraturLogikk();

        Litteratur litteratur = null;
        List<DokCache> typer = null;
        List<Litteratur> enheter = null;
        List<Litteratur> tilknytningsformer = null;

        Integer pubId = null;
        String paramPubId = request.getParameter("pub_id");

        if (paramPubId != null) {
            // oppdaterer eksisterende publikasjon.
            try {
                pubId = new Integer(paramPubId);
            } catch (Exception e) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
                rd.forward(request, response);
                return;
            }
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            typer = logikk.getLitteraturTyper();
            if (pubId != null) {
                litteratur = logikk.getLitteratur(pubId);
                enheter = logikk.getEnheterTilLitteratur(pubId);
                tilknytningsformer = logikk.getTilknytningsformerTilLitteratur(pubId);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

        request.setAttribute("typer", typer);
        request.setAttribute("litteratur", litteratur);
        request.setAttribute("enheter", enheter);
        request.setAttribute("tilknytningsformer", tilknytningsformer);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur.jsp");
        rd.forward(request, response);
    }


}
