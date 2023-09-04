
package no.nsd.polsys.servlets;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import no.nsd.polsys.actions.admin.LagreParameterAction;
import no.nsd.polsys.actions.admin.ParametreAction;

/**
 *
 * @author hvb
 */
public class AdminUrlMappingFelles {


    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String context = request.getContextPath();

        // URL-mapping
        if (uri.matches(context + "/felles/parametre\\.p")) new ParametreAction().process(request, response);
        else if (uri.matches(context + "/felles/lagreparameter\\.p")) new LagreParameterAction().process(request, response);

        // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }

    }

}
