package no.nsd.polsys.servlets;


import no.nsd.polsys.actions.admin.parti.LagrePartiDokAction;
import no.nsd.polsys.actions.admin.parti.LagrePartinavnAction;
import no.nsd.polsys.actions.admin.parti.PartiDokAction;
import no.nsd.polsys.actions.admin.parti.PartinavnAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author hvb
 */
public class AdminUrlMappingParti {


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
        if (uri.matches(context + "/parti/index\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/parti/index.jsp");
            rd.forward(request, response);
        }
        else if (uri.matches(context + "/parti/partidok\\.p")) new PartiDokAction().process(request, response);
        else if (uri.matches(context + "/parti/lagrepartidok\\.p")) new LagrePartiDokAction().process(request, response);
        else if (uri.matches(context + "/parti/partilist\\.p")) new PartinavnAction().process(request, response);
          else if (uri.matches(context + "/parti/lagrepartilist\\.p")) new LagrePartinavnAction().process(request, response);
        // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }

    }

}
