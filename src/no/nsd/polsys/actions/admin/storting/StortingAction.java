package no.nsd.polsys.actions.admin.storting;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 24.nov.2010
 * Time: 08:06:39
 * To change this template use File | Settings | File Templates.
 */
public class StortingAction {

     public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/storting/omstorting.jsp");
        rd.forward(request, response);
     }
}
