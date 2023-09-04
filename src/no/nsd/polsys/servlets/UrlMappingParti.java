package no.nsd.polsys.servlets;

import no.nsd.polsys.actions.parti.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UrlMappingParti {

    // Tid cachen skal v?re gyldig, 120min * 60 sek * 1000 millis.
    public static long CACHE_GYLDIG = 120L * 60L * 1000L;


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String context = request.getContextPath();

        // URL-mapping
        if (uri.matches(context + "(/en)?/parti/")) new OmPartiAction().process(request, response);
       else if (uri.matches(context + "(/en)?/parti/partihistorie/")) new PartiHistorieAction().process(request, response); 
        else if (uri.matches(context + "(/en)?/parti/partihistorie/\\d+")) new PartiHistorieAction().process(request, response);
        else if (uri.matches(context + "(/en)?/parti/stortingfylkestingsvalg/")) new Storting_fylkestingsvalgAction().process(request, response);
        else if (uri.matches(context + "(/en)?/parti/sametingsvalg/")) new PartisametingsvalgAction().process(request, response);
        else if (uri.matches(context + "(/en)?/parti/partidokumentarkivet/")) new SokResultatAction().process(request, response);

        // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }

    }

}
