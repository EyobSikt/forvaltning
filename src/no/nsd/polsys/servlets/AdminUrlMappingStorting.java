package no.nsd.polsys.servlets;


import no.nsd.polsys.actions.admin.storting.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AdminUrlMappingStorting {


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
        // URL-mapping
        if (uri.matches(context + "/storting/index\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/storting/index.jsp");
            rd.forward(request, response);
        }
        else if (uri.matches(context + "/storting/votering/votering\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/storting/votering/votering.jsp");
            rd.forward(request, response);
        }
        else if (uri.matches(context + "/storting/votering/lesfil\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/storting/votering/lesfil.jsp");
            rd.forward(request, response);
        }
        //else if (uri.matches(context + "(/en)?/storting/votering/uploadfil\\.p")) new LesVoteringFilAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/votering/lessaker\\.p")) new LesSakerAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/votering/uploadfil\\.p")) new ImportereSakerVoteringerFilAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/votering/sjekksaksopplysningertable\\.p")) new SjekkSaksopplysningerAction().doGet(request, response);
        else if (uri.matches(context + "(/en)?/storting/votering/importerealleemner\\.p")) new ImportereAlleEmnerAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/votering/uploadsaksopplysningertable\\.p")) new ImportereVoteringResultaterAction().process(request, response);


        else if (uri.matches(context + "(/en)?/storting/votering/saksopplysningerlist\\.p")) new VoteringsaksopplysningerAction().process(request, response);
        else if (uri.matches(context + "(/en)?/storting/votering/lagresaksopplysninger\\.p")) new LagreSaksopplysningerAction().process(request, response);
        else if (uri.matches(context + "/storting/votering/lessaksopplysningerlist\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/storting/votering/saksopplysningersesjon.jsp");
            rd.forward(request, response);
        }
            // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }
    }

}
