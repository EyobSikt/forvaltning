package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.FylkeLogikk;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DifiLogikk;
import no.nsd.polsys.logikk.forvaltning.TallgruppeLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Difi;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class DifiAction {

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
      DifiLogikk enhetLogikk = new DifiLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      FylkeLogikk fylkeLogikk = new FylkeLogikk();
      TallgruppeLogikk tallgruppeLogikk = new TallgruppeLogikk();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      // dato bruker har oppgitt.
      Dato brukerdato = new Dato();
      // de faktiske http-parametrene.
      String paramAar = request.getParameter("y");
      String paramMaaned = request.getParameter("m");
      String paramDag = request.getParameter("d");

      Difi difi = null;
      Map<Integer, DokCache> tilknytninger = null;
      Map<Integer, DokCache> fylker = null;
      int antallDesent21 = 0;
      int antallDesent31 = 0;
      int antallSatellitt = 0;

      boolean engelsk = false;

      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         fylkeLogikk.setConn(conn);
         tallgruppeLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }

         sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();
         brukerdato = Dato.getDato(paramAar, paramMaaned, paramDag, sistOppdatertDato);

         // så lenge tidspunkt ikke er etter oppdatert og dato er gyldig --> OK.
         if (brukerdato.isGyldig() && !brukerdato.isEtter(sistOppdatertDato)) {
            difi = enhetLogikk.getDifiStat(brukerdato.getDate());
            fylker = fylkeLogikk.getFylker();
            antallDesent21 = tallgruppeLogikk.getAntallDesentraliserte(21, brukerdato.getAar());
            antallDesent31 = tallgruppeLogikk.getAntallDesentraliserte(31, brukerdato.getAar());
            antallSatellitt = tallgruppeLogikk.getAntallSatellitter(brukerdato.getDate());
         }

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

      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("brukerdato", brukerdato);
      request.setAttribute("difi", difi);
      request.setAttribute("tilknytninger", tilknytninger);
      request.setAttribute("fylker", fylker);
      request.setAttribute("tallgruppeLogikk", tallgruppeLogikk);
      request.setAttribute("antallDesent21", antallDesent21);
      request.setAttribute("antallDesent31", antallDesent31);
      request.setAttribute("antallSatellitt", antallSatellitt);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/difi.jsp");
      rd.forward(request, response);
   }
}
