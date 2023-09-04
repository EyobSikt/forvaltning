package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.FylkeLogikk;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.FylkeAnsatteLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Ansatte;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class FylkelisteAction {

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
      FylkeLogikk fylkeLogikk = new FylkeLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      FylkeAnsatteLogikk fylkeAnsattelogikk = new FylkeAnsatteLogikk();

      // mapping: fylkenr --> fylke.
      Map<Integer, DokCache> fylker = null;
      // mapping: fylkenr --> liste med enheter.
      SortedMap<Integer, List<Enhet>> enheter = null;
      // mapping: fylkenr --> liste med satellitter.
      SortedMap<Integer, List<Tallgruppe>> satellitter = null;
      // mapping: fylkenr --> antall ansatte.
      Map<Integer, Ansatte> ansatte = new HashMap<Integer, Ansatte>();
      // dato bruker har oppgitt.
      Dato brukerdato = new Dato();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      // de faktiske http-parametrene.
      String paramAar = request.getParameter("y");
      String paramMaaned = request.getParameter("m");
      String paramDag = request.getParameter("d");
      String sektor = request.getParameter("s");

      if (sektor == null || sektor.length() == 0) {
         sektor = "1";
      }

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         fylkeLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         fylkeAnsattelogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }
         sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();
         brukerdato = Dato.getDato(paramAar, paramMaaned, paramDag, sistOppdatertDato);
         fylker = fylkeLogikk.getFylker();

         // så lenge tidspunkt ikke er etter oppdatert og dato er gyldig --> OK.
         if (brukerdato.isGyldig() && !brukerdato.isEtter(sistOppdatertDato) && sektor != null
                 && (sektor.equals("1") || sektor.equals("2") || sektor.equals("3"))) {
            enhetLogikk.beregnerEnheterPerFylke(brukerdato.getDate(), sektor);
            enheter = enhetLogikk.getFylker();
            satellitter = enhetLogikk.getSatellitter();
            List<Ansatte> liste = fylkeAnsattelogikk.getAnsatteForFylker(brukerdato.getAar(), 2);
            if (liste != null) {
               for (Ansatte a : liste) {
                  ansatte.put(a.getFylkenummer(), a);
               }
            }
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
      request.setAttribute("satellitter", satellitter);
      request.setAttribute("fylker", fylker);
      request.setAttribute("brukerdato", brukerdato);
      request.setAttribute("ansatte", ansatte);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/fylkesliste.jsp");
      rd.forward(request, response);
   }
}
