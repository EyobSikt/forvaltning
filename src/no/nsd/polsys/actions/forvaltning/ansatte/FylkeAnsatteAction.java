package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.ansatte.LandAnsatteCacheFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.ansatte.*;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

/**
 *
 * @author hvb
 */
public class FylkeAnsatteAction {

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
      FylkeAnsatteLogikk logikk = new FylkeAnsatteLogikk();
      AnsatteLogikk logikkAnsatte = new AnsatteLogikk();
      EtatAnsatteLogikk logikkEtat = new EtatAnsatteLogikk();
      AggregerAnsatteLogikk logikkAggreger = new AggregerAnsatteLogikk();

      DepartementAnsatteLogikk logikkDepartement = new DepartementAnsatteLogikk();
      SysselsatteAnsatteLogikk logikkSysselsatte = new SysselsatteAnsatteLogikk();

      SortedMap<Integer, Ansatte> total = null;
      SortedMap<Integer, Ansatte> mennTotal = null;
      SortedMap<Integer, Ansatte> kvinnerTotal = null;

      SortedMap<Integer, Ansatte> heltid = null;
      SortedMap<Integer, Ansatte> mennHeltid = null;
      SortedMap<Integer, Ansatte> kvinnerHeltid = null;

      SortedMap<Integer, Ansatte> deltid = null;
      SortedMap<Integer, Ansatte> mennDeltid = null;
      SortedMap<Integer, Ansatte> kvinnerDeltid = null;

      SortedMap<Integer, Ansatte> totalLand = null;

      List<Ansatte> etatAgg = null;
      List<Ansatte> etat = null;
      List<Integer> alleAar = null;
      List<Ansatte> kommuner = null;
      List<Ansatte> depAgg = null;

      // mapping: år --> sysselsatte.
      Map<Integer, Integer> sysselsatte = null;
      // mapping: kommunenr --> sysselsatte.
      Map<Integer, Integer> sysselsatteKommune = null;

      String fylke = null;
      Integer fylkenr = Util.getId(request.getRequestURI());
      if (fylkenr == null) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
         rd.forward(request, response);
         return;
      }

      Integer valgtAar = null;
      try {
         valgtAar = new Integer(request.getParameter("aar"));
      } catch (Exception ignored) {
      }

      Integer lonnskategori = null;
      if(request.getParameterMap().containsKey("lk"))
      try {
         lonnskategori = new Integer(request.getParameter("lk"));
      } catch (Exception ignored) {
         // ved alle lønnskategorier er lk = a, altså lonnskategori vil forbli null.
         lonnskategori = null;
      }
      else{lonnskategori = 2;}


      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);
         logikkAnsatte.setConn(conn);
         logikkEtat.setConn(conn);
         logikkDepartement.setConn(conn);
         logikkSysselsatte.setConn(conn);

         fylke = logikk.getFylke(fylkenr);
         if (fylke == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }

         logikkAnsatte.beregnAnsatteTilFylke(fylkenr, lonnskategori);

         total = logikkAnsatte.getTotal();
         mennTotal = logikkAnsatte.getMennTotal();
         kvinnerTotal = logikkAnsatte.getKvinnerTotal();

         heltid = logikkAnsatte.getHeltid();
         mennHeltid = logikkAnsatte.getMennHeltid();
         kvinnerHeltid = logikkAnsatte.getKvinnerHeltid();

         deltid = logikkAnsatte.getDeltid();
         mennDeltid = logikkAnsatte.getMennDeltid();
         kvinnerDeltid = logikkAnsatte.getKvinnerDeltid();

         totalLand = LandAnsatteCacheFactory.getData(conn, lonnskategori);

         alleAar = new ArrayList<Integer>();
         if (total != null) {
            alleAar.addAll(total.keySet());
         }
         Collections.reverse(alleAar);
         if (valgtAar == null && !alleAar.isEmpty()) {
            valgtAar = alleAar.get(0);
         }

         etat = logikkEtat.getAnsattePerEtatOgKommune(fylkenr, valgtAar, lonnskategori);

         etatAgg = logikkAggreger.getAggregertEtat(etat);
         depAgg = logikkAggreger.getAggregertDep(etat);
         kommuner = logikkAggreger.getAggregertKommune(etat);

         logikkEtat.leggTilEtatnavn(valgtAar, depAgg);
         logikkEtat.leggTilDepnavn(valgtAar, etatAgg);

         sysselsatte = logikkSysselsatte.getSysselsatteFylke(fylkenr);
         sysselsatteKommune = logikkSysselsatte.getSysselsatteFylke(fylkenr, valgtAar);

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

      request.setAttribute("fylkenr", fylkenr);
      request.setAttribute("fylke", fylke);
      request.setAttribute("totalLand", totalLand);
      request.setAttribute("kommuner", kommuner);
      request.setAttribute("etatAgg", etatAgg);
      request.setAttribute("etat", etat);
      request.setAttribute("valgtAar", valgtAar);
      request.setAttribute("alleAar", alleAar);
      request.setAttribute("depAgg", depAgg);
      request.setAttribute("sysselsatte", sysselsatte);
      request.setAttribute("sysselsatteKommune", sysselsatteKommune);

      request.setAttribute("total", total);
      request.setAttribute("mennTotal", mennTotal);
      request.setAttribute("kvinnerTotal", kvinnerTotal);

      request.setAttribute("heltid", heltid);
      request.setAttribute("mennHeltid", mennHeltid);
      request.setAttribute("kvinnerHeltid", kvinnerHeltid);

      request.setAttribute("deltid", deltid);
      request.setAttribute("mennDeltid", mennDeltid);
      request.setAttribute("kvinnerDeltid", kvinnerDeltid);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/fylke.jsp");
      rd.forward(request, response);
   }
}
