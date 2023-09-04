package no.nsd.polsys.actions.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetsregisteretLogikk;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Enhetsregisteret;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvb
 */
public class EnhetsregisteretHerarkiAction {

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
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      Connection conn = null;
      EnhetsregisteretLogikk enhetsregisteretLogikk = new EnhetsregisteretLogikk();
      List<List<Enhet>> staten = new ArrayList<>();
   ///////////////////////////////////////////////////////////////////////
      HttpServletRequest httpReq = (HttpServletRequest) request;
      Integer orgnr = null;
      List<Enhetsregisteret> orginfo = null;
      //List<Enhetsregisteret> enhet = null;
      List<Enhet> arbeideyrke = null;
       List<Enhet> forvaltningsenhet = null;

      //idnum = enhetsregisteretLogikk.getId(httpReq.getRequestURI());
      /*
       if (idnum == null) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
         rd.forward(request, response);
         return;
      }
      */
      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

       Integer idnum = (Integer) request.getAttribute("idnum");
       //Enhet enhet = (Enhet) request.getAttribute("enhet");

      List<Integer> alleAar = new ArrayList<Integer>();

      Integer valgtAar = null;
      try {
         valgtAar = new Integer(request.getParameter("aar"));
      } catch (Exception ignored) {
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetsregisteretLogikk.setConn(conn);
         //oppgaveLogikk.setConn(conn);
         if (engelsk) {
            enhetsregisteretLogikk.brukEngelsk();
         }

         alleAar = enhetsregisteretLogikk.getAlleAar();

         if (valgtAar == null) {
            valgtAar = alleAar.get(0);
         }
        orgnr = enhetsregisteretLogikk.getOrgnr((Integer) request.getAttribute("idnum"), valgtAar);
         //enhet = enhetsregisteretLogikk.getEnhetnew((Integer) request.getAttribute("idnum"), valgtAar);
         orginfo = enhetsregisteretLogikk.getEnhet(orgnr, valgtAar);
         arbeideyrke = enhetsregisteretLogikk.getEnhet_arb_yrke(orgnr, valgtAar);
          staten.add(enhetsregisteretLogikk.getHierarki3(orgnr, valgtAar));
          forvaltningsenhet = enhetsregisteretLogikk.getForvaltningsEnhet(orgnr, valgtAar);
         /*
          if (enhet == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }
        */
      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception ignored) {
            conn = null;
         }
      }


      //request.setAttribute("enhet", enhet);
      request.setAttribute("orginfo", orginfo);
      request.setAttribute("arbeideyrke", arbeideyrke);
       request.setAttribute("forvaltningsenhet", forvaltningsenhet);
       request.setAttribute("staten", staten);
      request.setAttribute("alleAar", alleAar);
      request.setAttribute("valgtAar", valgtAar);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhetregistrerthirarki.jsp");
      rd.forward(request, response);
   }
}
