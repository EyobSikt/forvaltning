/*
package no.nsd.polsys.servlets;


import no.nsd.polsys.actions.regjering.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 *
 * @author et
 *//*

public class UrlMappingRegjering {

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
        if (uri.matches(context + "(/en)?/regjering/")) new OmRegjeringAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/")) new StatsraadsarkivetAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringer/")) new MinisterRegjeringAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsbeskrivelse/")) new RegjeringsBeskrivelseAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsarsakavgang/")) new RegjeringsArsakavgangAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsadhoc/")) new RegjeringadhocAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsadhocbeskrivelse/")) new RegjeringsadhocBeskrivelseAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/svenskenorskeutenriksministre/")) new SvenskenorskeutenriksministreAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsstatsraader/")) new StatsraaderDepartmentsvisAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsstatsraaderoversikt/")) new StatsraaderDepartmentsvisOversiktAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsvarighet/")) new RegjeringenesvarighetAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/statsraadskjonn/")) new StatsraadsKjonnAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/statsraadsalder/")) new StatsraadsAlderAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/regjeringsparlamentariskgrunnlag/")) new RegjeringsParlamentariskGrunnlagAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/eldestyngstestatsraader/")) new EldestYngsteStatsraaderAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statsraadsarkivet/statsraadsansientsfordelinger/")) new StatsraadsAnsientsFordelingerAction().process(request, response);
        //statssekretær
        else if (uri.matches(context + "(/en)?/regjering/statssekretaerarkivet/")) new StatssekretaerarkivetAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statssekretaerarkivet/statssekretarregjeringsvis/")) new StatssekretarRegjeringsvisAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/")) new StatssekretarRegjeringsvisBeskrivelseAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statssekretaerarkivet/statssekretardepartementsvis/")) new StatssekretarDepartementsvisAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statssekretaerarkivet/statssekretardepartementsvisoversikt/")) new StatssekretarDepartmentsvisOversiktAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statssekretaerarkivet/statssekretarskjonn/")) new StatssekretarKjonnAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/statssekretaerarkivet/statssekretarsalder/")) new StatssekretarAlderAction().process(request, response);
        //lastned fil
        else if (uri.matches(context + "(/en)?/regjering/lastnedstatsraad/")) new LastnedstatsraadAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/lastnedfilstatsraad/")) new LastnefilstatsraadAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/lastnedstatssekretar/")) new LastnedstatssekretarAction().process(request, response);
        else if (uri.matches(context + "(/en)?/regjering/lastnedfilstatssekretar/")) new LastnefilstatssekretarAction().process(request, response);

            // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }
    }

}
*/
