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
public class KommuneAnsatteAction {

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
      KommuneAnsatteLogikk logikk = new KommuneAnsatteLogikk();
      AnsatteLogikk logikkAnsatte = new AnsatteLogikk();
      EtatAnsatteLogikk logikkEtat = new EtatAnsatteLogikk();
      DepartementAnsatteLogikk logikkDepartement = new DepartementAnsatteLogikk();
      FylkeAnsatteLogikk logikkFylke = new FylkeAnsatteLogikk();
      SysselsatteAnsatteLogikk logikkSysselsatte = new SysselsatteAnsatteLogikk();
      AggregerAnsatteLogikk logikkAggreger = new AggregerAnsatteLogikk();

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

      List<Ansatte> etat = null;
      List<Ansatte> sted = null;
      List<Integer> alleAar = null;
      List<Ansatte> depAgg = null;

      // mapping: kommunenr --> sysselsatte.
      Map<Integer, Integer> sysselsatte = null;

      String kommune = null;
      String fylke = null;
      Integer kommunenr = Util.getId(request.getRequestURI());
      if (kommunenr == null) {
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
         logikkFylke.setConn(conn);
         logikkSysselsatte.setConn(conn);

         kommune = logikk.getKommune(kommunenr);
         if (kommune == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }
         fylke = logikkFylke.getFylke(kommunenr / 100);


         logikkAnsatte.beregnAnsatteTilKommune(kommunenr, lonnskategori);

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


         sted = logikkEtat.getAnsattePerSted(kommunenr, valgtAar, lonnskategori);

         etat = logikkAggreger.getAggregertEtat(sted);
         depAgg = logikkAggreger.getAggregertDep(sted);

         logikkEtat.leggTilDepnavn(valgtAar, sted);
         logikkEtat.leggTilDepnavn(valgtAar, etat);
         logikkEtat.leggTilEtatnavn(valgtAar, depAgg);

         sysselsatte = logikkSysselsatte.getSysselsatteKommune(kommunenr);
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

      request.setAttribute("kommunenr", kommunenr);
      request.setAttribute("kommune", kommune);
      request.setAttribute("fylke", fylke);
      request.setAttribute("totalLand", totalLand);
      request.setAttribute("etat", etat);
      request.setAttribute("sted", sted);
      request.setAttribute("valgtAar", valgtAar);
      request.setAttribute("alleAar", alleAar);
      request.setAttribute("depAgg", depAgg);
      request.setAttribute("sysselsatte", sysselsatte);

      request.setAttribute("total", total);
      request.setAttribute("mennTotal", mennTotal);
      request.setAttribute("kvinnerTotal", kvinnerTotal);

      request.setAttribute("heltid", heltid);
      request.setAttribute("mennHeltid", mennHeltid);
      request.setAttribute("kvinnerHeltid", kvinnerHeltid);

      request.setAttribute("deltid", deltid);
      request.setAttribute("mennDeltid", mennDeltid);
      request.setAttribute("kvinnerDeltid", kvinnerDeltid);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/kommune.jsp");
      rd.forward(request, response);
   }
}
