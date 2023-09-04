package no.nsd.polsys.servlets;


import no.nsd.polsys.actions.admin.regjering.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author et
 */
public class AdminUrlMappingRegjering {


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
        if (uri.matches(context + "/regjering/index\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/regjering/index.jsp");
            rd.forward(request, response);
        }
        else if (uri.matches(context + "/regjering/statsrad\\.p")) new StatsradNavnListAction().process(request, response);
        else if (uri.matches(context + "/regjering/statsradinfo\\.p")) new ListStatsradInfoAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrevirkeperiode_start\\.p")) new LagreStatsradStartdatoAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrevirkeperiode_slutt\\.p")) new LagreStatsradSluttdatoAction().process(request, response);
        else if (uri.matches(context + "/regjering/registerstatsrad\\.p")) new RegistreStatsradAction().process(request, response);
        else if (uri.matches(context + "/regjering/registerstatsradinfo\\.p")) new RegistreStatsradInfoAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatsradeksternkommentar\\.p")) new LagreStatsradEksternkommentarAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatsradembete\\.p")) new LagreStatsradEmbeteAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatsradfoedt\\.p")) new LagreStatsradFoedtAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatsraddoedt\\.p")) new LagreStatsradDoedtAction().process(request, response);
        else if (uri.matches(context + "/regjering/slettestatsrad\\.p")) new SletteStatsradAction().process(request, response);
        else if (uri.matches(context + "/regjering/slettestatssekretar\\.p")) new SletteStatssekretarAction().process(request, response);

        else if (uri.matches(context + "/regjering/statssekretar\\.p")) new StatssekretarNavnListAction().process(request, response);
        else if (uri.matches(context + "/regjering/statssekretarinfo\\.p")) new ListStatssekretarInfoAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagre_statssekretar_start\\.p")) new LagreStatssekretarStartdatoAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagre_statssekretar_slutt\\.p")) new LagreStatssekretarSluttdatoAction().process(request, response);
        else if (uri.matches(context + "/regjering/registerstatssekretar\\.p")) new RegistreStatssekretarAction().process(request, response);
        else if (uri.matches(context + "/regjering/registerstatssekretarinfo\\.p")) new RegistreStatssekretarInfoAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatssekretareksternkommentar\\.p")) new LagreStatssekretarEksternkommentarAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatssekretarembete\\.p")) new LagreStatssekretarEmbeteAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatssekretarfoedt\\.p")) new LagreStatssekretarFoedtAction().process(request, response);
        else if (uri.matches(context + "/regjering/lagrestatssekretardoedt\\.p")) new LagreStatssekretarDoedtAction().process(request, response);
        //Embete minister
        else if (uri.matches(context + "/regjering/embete\\.p")) new EmbeteStatsradAction().process(request, response);
        else if (uri.matches(context + "/regjering/registerembete\\.p")) new RegisterEmbeteAction().process(request, response);

        //REST
         else  if (uri.matches(context + "/regjering/statsradindex\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/regjering/statsrad_rest.jsp");
            rd.forward(request, response);
        }
        else  if (uri.matches(context + "/regjering/statsradinfoindex\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/regjering/oppdaterestatsradinfo_rest.jsp");
            rd.forward(request, response);
        }

            // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }

    }

}
