package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.GrunnenhetCacheFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Ansatte;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class TempEnheterAction {

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
      EnhetLogikk enhetAnsatteLogikk = new EnhetLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();

      Map<Integer, DokCache> tilknytninger = null;
      Map<Integer, DokCache> grunnenheter = null;
      // de aktuelle enheter.
      List<Enhet> enheter = null;
      // år bruker har oppgitt.
      Integer aar = null;
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      Date dato = null;

      // idnum --> ansatte.
      Map<Integer, Ansatte> ansatte = new HashMap<Integer, Ansatte>();

      Integer totaltAnsatte = 0;
      Float totaltAarsverk = 0f;

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
         enhetAnsatteLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
            enhetAnsatteLogikk.brukEngelsk();
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
         dato = cal.getTime();

         enheter = enhetLogikk.getTempEnheter(dato);

         for (Enhet e : enheter) {
            Ansatte a = enhetAnsatteLogikk.getAnsatteTilEnhet(e.getIdnum(), aar);
            ansatte.put(e.getIdnum(), a);

            totaltAnsatte += (a != null && a.getAnsatte() != null ? a.getAnsatte().intValue() : 0);
            totaltAarsverk += (a != null && a.getAarsverk() != null ? a.getAarsverk().floatValue() : 0f);
         }

         if (engelsk) {
            tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
         } else {
            tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdata(conn);
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
      request.setAttribute("dato", dato);
      request.setAttribute("enheter", enheter);
      request.setAttribute("ansatte", ansatte);
      request.setAttribute("totaltAnsatte", totaltAnsatte);
      request.setAttribute("totaltAarsverk", totaltAarsverk);
      request.setAttribute("tilknytninger", tilknytninger);
      request.setAttribute("grunnenheter", grunnenheter);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/temp_enheter.jsp");
      rd.forward(request, response);
   }
}
