package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.CofogCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Cofog;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class CofogAction {

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

      Map<String, Cofog> cofog = null;
      // mapping: cofog-kode --> liste med enheter.
      SortedMap<String, List<Enhet>> enheter = null;
      // Cofog-kode.
      String kode = null;
      // dato bruker har oppgitt.
      Dato brukerdato = new Dato();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      // de faktiske http-parametrene.
      String paramAar = request.getParameter("y");
      String paramMaaned = request.getParameter("m");
      String paramDag = request.getParameter("d");

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      String uri = request.getRequestURI();
      try {
         int i = uri.lastIndexOf("/");
         kode = uri.substring(i + 1);
      } catch (Exception e) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
         rd.forward(request, response);
         return;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }
         sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();
         brukerdato = Dato.getDato(paramAar, paramMaaned, paramDag, sistOppdatertDato);

         // så lenge tidspunkt ikke er etter oppdatert og dato er gyldig --> OK.
         if (brukerdato.isGyldig() && !brukerdato.isEtter(sistOppdatertDato)) {
            enhetLogikk.beregnEnheterPerCofog(brukerdato.getDate());
            enheter = enhetLogikk.getCofog();

            Collection<List<Enhet>> coll = enheter.values();
            Iterator<List<Enhet>> iter = coll.iterator();
            while (iter.hasNext()) {
               List<Enhet> l = iter.next();
               Collections.sort(l);
            }

         }

         if (engelsk) {
            cofog = CofogCacheFactory.getDokdataEngelsk(conn);
         } else {
            cofog = CofogCacheFactory.getDokdata(conn);
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

      request.setAttribute("enheter", enheter);
      request.setAttribute("cofog", cofog);
      request.setAttribute("kode", kode);
      request.setAttribute("brukerdato", brukerdato);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/cofog.jsp");
      rd.forward(request, response);
   }
}
