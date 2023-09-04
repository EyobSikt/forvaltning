/*
package no.nsd.polsys.servlets;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 *
 * @author hvb
 *//*

public class UrlMappingStorting {

    // Tid cachen skal v�re gyldig, 120min * 60 sek * 1000 millis.
    public static long CACHE_GYLDIG = 120L * 60L * 1000L;


    */
/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     *//*

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String context = request.getContextPath();

        // URL-mapping
        if (uri.matches(context + "(/en)?/storting/")) new OmStortingAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/sammensetning/")) new PolitikereArkivAction().process(request, response);

        //else if (uri.matches(context + "(/en)?/storting/norskepolitikere_g/")) new NorskePolitikeregammelAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/representant/")) new RepresentantSuppleantAction().process(request, response);

        else if (uri.matches(context + "(/en)?/storting/merknadsfrekvenser/")) new MerknadsfrekvenserAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/frekvenser/")) new FrekvenserAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/partiavstand/")) new PartiavstandAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/dokumentarkiv/")) new DokumentArkivAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/kulturpolitikk/")) new KulturPolitikkAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/kulturpolitikkfrekvens/")) new KulturPolitikkFrekvensAction().process(request, response);

        else if (uri.matches(context + "(/en)?/storting/votering/bakgrunn/")) new OmVoteringAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/lastnedfil/")) new LastnedstatsraadAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/lastned/")) new LastnefilstatsraadAction().process(request, response);

        else if (uri.matches(context + "(/en)?/storting/omdataset/")) new OmDatasetAction().process(request, response);

            // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }

    }

}
*/
