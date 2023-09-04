package no.nsd.polsys.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.forvaltning.AarsmeldingCacheFactory;
import no.nsd.polsys.factories.forvaltning.CofogCacheFactory;
import no.nsd.polsys.factories.forvaltning.LovdataCacheFactory;
import no.nsd.polsys.logikk.Util;

/**
 *
 * @author hvb
 */
public class AdminControllerServlet extends HttpServlet {

   /**
    * Processes requests for both HTTP
    * <code>GET</code> and
    * <code>POST</code> methods.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String uri = Util.getUriWithoutJsessionId(request.getRequestURI());
      String context = request.getContextPath();

      // default charcter encoding og content type.
      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html;charset=UTF-8");

      // i tilfellet uri skulle være null.
      if (uri == null) {
         uri = "404";
      }

      // URL-mapping
      // index-side
      if (uri.matches(context + "/index\\.p")) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
         rd.forward(request, response);
      } // felles
      else if (uri.startsWith(context + "/felles/")) {
         new AdminUrlMappingFelles().processRequest(request, response);
      } // forvaltning
      else if (uri.startsWith(context + "/forvaltning/")) {
         new AdminUrlMappingForvaltning().processRequest(request, response);
      } // storting
      else if (uri.startsWith(context + "/storting/")) {
         new AdminUrlMappingStorting().processRequest(request, response);
      } // regjering
      else if (uri.startsWith(context + "/regjering/")) {
         new AdminUrlMappingRegjering().processRequest(request, response);
      } // parti
      else if (uri.startsWith(context + "/parti/")) {
         new AdminUrlMappingParti().processRequest(request, response);
      } // 404
      else {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
         rd.forward(request, response);
      }

   }

   /**
    * Handles the HTTP
    * <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      processRequest(request, response);
   }

   /**
    * Handles the HTTP
    * <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      processRequest(request, response);
   }

   @Override
   public void init() {
      CofogCacheFactory.CACHE_GYLDIG = 0L;
      AarsmeldingCacheFactory.CACHE_GYLDIG = 0L;
      LovdataCacheFactory.CACHE_GYLDIG = 0L;
   }

   @Override
   public void destroy() {
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "";
   }
}
