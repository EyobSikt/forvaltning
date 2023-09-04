package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EvalEnhetlisteAction {

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
      EnhetDivLogikk enhetLogikk = new EnhetDivLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();

      Map<Integer, DokCache> tilknytninger = null;
      // de aktuelle enheter.
      List<Enhet> enheter = null;
      // år bruker har oppgitt.
      Integer aar = null;
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      Date fraDato = null;
      Date tilDato = null;

      boolean engelsk = false;

      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         aar = new Integer(request.getParameter("y"));
      } catch (Exception e) {
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }

         sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();
         Calendar cal = new GregorianCalendar();
         cal.setTime(sistOppdatertDato.getDate());
         if (aar == null) {
            aar = cal.get(Calendar.YEAR);
         }

         cal = new GregorianCalendar();
         cal.setLenient(false);
         cal.clear();
         cal.set(aar, 0, 1); // 1.1.aar
         fraDato = cal.getTime();

         cal = new GregorianCalendar();
         cal.setLenient(false);
         cal.clear();
         cal.set(aar, 11, 31); // 31.12.aar
         tilDato = cal.getTime();


         enheter = enhetLogikk.getEvalEnheterGittAar(fraDato, tilDato);

         if (engelsk) {
            tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
         } else {
            tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
         }
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

      request.setAttribute("aar", aar);
      request.setAttribute("fraDato", fraDato);
      request.setAttribute("tilDato", tilDato);
      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("enheter", enheter);
      request.setAttribute("tilknytninger", tilknytninger);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhetliste.jsp");
      rd.forward(request, response);
   }
}
