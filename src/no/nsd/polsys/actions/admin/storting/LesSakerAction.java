package no.nsd.polsys.actions.admin.storting;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LesSakerAction extends HttpServlet {


    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/storting/votering/lesfil.jsp");
        rd.forward(request, response);
    }
}