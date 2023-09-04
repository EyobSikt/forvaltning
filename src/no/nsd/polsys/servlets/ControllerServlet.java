package no.nsd.polsys.servlets;

import no.nsd.polsys.factories.forvaltning.*;
import no.nsd.polsys.factories.forvaltning.ansatte.LandAnsatteCacheFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author eyob
 */
public class ControllerServlet extends HttpServlet {

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
      String uri = request.getRequestURI();
      String context = request.getContextPath();

      // default charcter encoding og content type.
      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html;charset=UTF-8");

      // i tilfellet uri skulle være null.
      if (uri == null) {
         uri = "404";
      }

      if (uri.contains("/en/")) {
         request.setAttribute("en", true);
      } else {
         request.setAttribute("no", true);
      }

      // URL-mapping
      // index-side
      if (uri.matches(context + "(/en)?/")) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
         rd.forward(request, response);
      }
      else if (uri.matches(context + "(/en)?/dokumentasjon/")) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/dokumentasjon.jsp");
         rd.forward(request, response);
      }
      // forvaltning
      else if (uri.startsWith(context + "/data/")
              || uri.startsWith(context + "/en/data/")
              || uri.startsWith(context + "/xml/data/")) {
         new UrlMappingForvaltning().processRequest(request, response);
      }
      // storting
    /*  else if (uri.startsWith(context + "/storting/") || uri.startsWith(context + "/en/storting/")) {
         new UrlMappingStorting().processRequest(request, response);
      } // regjering
      else if (uri.startsWith(context + "/regjering/") || uri.startsWith(context + "/en/regjering/")) {
         new UrlMappingRegjering().processRequest(request, response);
      }
      */
      // parti
      else if (uri.startsWith(context + "/parti/") || uri.startsWith(context + "/en/parti/")) {
         new UrlMappingParti().processRequest(request, response);
      } // felles
     /* else {
         new UrlMappingFelles().processRequest(request, response);
      }*/

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
      long timer = 48L;
      long gyldig = timer * 60L * 60L * 1000L;
      try {
         timer = Long.parseLong(this.getInitParameter("cache"));
      } catch (Exception e) {
         timer = 48L;
      }
      // timer * 60 min * 60 sek * 1000 millis.
      gyldig = timer * 60L * 60L * 1000L;
      AarsmeldingCacheFactory.CACHE_GYLDIG = gyldig;
      CofogCacheFactory.CACHE_GYLDIG = gyldig;
      EndringCacheFactory.CACHE_GYLDIG = gyldig;
      EndringskodeCacheFactory.CACHE_GYLDIG = gyldig;
      EtatnavnAnsatteCacheFactory.CACHE_GYLDIG = gyldig;
      GrunnenhetCacheFactory.CACHE_GYLDIG = gyldig;
      LitteraturEnhetCacheFactory.CACHE_GYLDIG = gyldig;
      LitteraturKategoritypeCacheFactory.CACHE_GYLDIG = gyldig;
      LovdataCacheFactory.CACHE_GYLDIG = gyldig;
      NivaaCacheFactory.CACHE_GYLDIG = gyldig;
      RelasjonAndreCacheFactory.CACHE_GYLDIG = gyldig;
      RelasjonCacheFactory.CACHE_GYLDIG = gyldig;
      TallgruppeCacheFactory.CACHE_GYLDIG = gyldig;
      TilknytningsformCacheFactory.CACHE_GYLDIG = gyldig;
      LandAnsatteCacheFactory.CACHE_GYLDIG = gyldig;
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
