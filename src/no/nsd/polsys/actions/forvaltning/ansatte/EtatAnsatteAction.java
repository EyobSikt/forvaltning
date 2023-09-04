package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.ansatte.LandAnsatteCacheFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.*;
import no.nsd.polsys.modell.forvaltning.Ansatte;
import no.nsd.polsys.modell.forvaltning.Enhet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class EtatAnsatteAction {

   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Connection conn = null;
      EtatAnsatteLogikk logikkEtat = new EtatAnsatteLogikk();
      AnsatteLogikk logikkAnsatte = new AnsatteLogikk();
      KommuneAnsatteLogikk logikkKommune = new KommuneAnsatteLogikk();
      FylkeAnsatteLogikk logikkFylke = new FylkeAnsatteLogikk();
      DepartementAnsatteLogikk logikkDepartement = new DepartementAnsatteLogikk();
      EnhetLogikk logikkEnhet = new EnhetLogikk();
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

      List<Ansatte> etatAgg = null;
      List<Ansatte> etatFylke = null;
      List<Ansatte> etatKommune = null;
      List<Integer> alleAar = null;
      List<Ansatte> kommuner = null;
      List<Ansatte> sted = null;

      Map<Integer, Ansatte> fylker = null;
      List<Ansatte> ansatteFylker = null;

      Ansatte ansatte = new Ansatte();
      boolean depom = false;

      List<Enhet> koblingEnheter = null;

      String uri = request.getRequestURI();
      int i = uri.lastIndexOf("/");
      String etatkode = uri.substring(i + 1);

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
         logikkEtat.setConn(conn);
         logikkAnsatte.setConn(conn);
         logikkFylke.setConn(conn);
         logikkKommune.setConn(conn);
         logikkDepartement.setConn(conn);
         logikkEnhet.setConn(conn);


         alleAar = logikkEtat.getAlleAarForEtat(etatkode);

         if (alleAar == null || alleAar.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }

         if (valgtAar == null) {
            valgtAar = alleAar.get(0);
         }

         ansatte.setEtatkode(etatkode);

         logikkAnsatte.beregnAnsatteTilEtat(etatkode, lonnskategori, valgtAar);

         total = logikkAnsatte.getTotal();
         mennTotal = logikkAnsatte.getMennTotal();
         kvinnerTotal = logikkAnsatte.getKvinnerTotal();

         heltid = logikkAnsatte.getHeltid();
         mennHeltid = logikkAnsatte.getMennHeltid();
         kvinnerHeltid = logikkAnsatte.getKvinnerHeltid();

         deltid = logikkAnsatte.getDeltid();
         mennDeltid = logikkAnsatte.getMennDeltid();
         kvinnerDeltid = logikkAnsatte.getKvinnerDeltid();

         // total
         totalLand = LandAnsatteCacheFactory.getData(conn, lonnskategori);

         logikkEtat.leggTilNavn(valgtAar, ansatte);
         if (ansatte.getEtatkode().equals(ansatte.getDepkode())) {
            depom = true;
            if (ansatte.getEtat() == null) {
               ansatte.setEtat("");
            } else {
               ansatte.setEtat("Departementsområdet " + ansatte.getEtat());
            }
         }

         fylker = logikkFylke.getFylker();

         sted = logikkEtat.getAnsatteForSted(etatkode, valgtAar, lonnskategori);

         etatKommune = logikkAggreger.getAggregertEtatOgKommune(sted);
         etatFylke = logikkAggreger.getAggregertEtatOgFylke(sted);
         etatAgg = logikkAggreger.getAggregertEtat(sted);
         kommuner = logikkAggreger.getAggregertKommune(sted);
         ansatteFylker = logikkAggreger.getAggregertFylke(sted);

         koblingEnheter = logikkEnhet.getEnheterTilEtatAnsattKobling(etatkode, valgtAar);

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

      request.setAttribute("totalLand", totalLand);
      request.setAttribute("kommuner", kommuner);
      request.setAttribute("etatAgg", etatAgg);
      request.setAttribute("etatFylke", etatFylke);
      request.setAttribute("etatKommune", etatKommune);
      request.setAttribute("ansatte", ansatte);
      request.setAttribute("valgtAar", valgtAar);
      request.setAttribute("alleAar", alleAar);
      request.setAttribute("depom", depom);
      request.setAttribute("koblingEnheter", koblingEnheter);

      request.setAttribute("total", total);
      request.setAttribute("mennTotal", mennTotal);
      request.setAttribute("kvinnerTotal", kvinnerTotal);

      request.setAttribute("heltid", heltid);
      request.setAttribute("mennHeltid", mennHeltid);
      request.setAttribute("kvinnerHeltid", kvinnerHeltid);

      request.setAttribute("deltid", deltid);
      request.setAttribute("mennDeltid", mennDeltid);
      request.setAttribute("kvinnerDeltid", kvinnerDeltid);

      request.setAttribute("fylker", fylker);
      request.setAttribute("ansatteFylker", ansatteFylker);
      request.setAttribute("sted", sted);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/etat.jsp");
      rd.forward(request, response);
   }
}
