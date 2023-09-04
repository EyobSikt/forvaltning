package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.ansatte.LandAnsatteCacheFactory;
import no.nsd.polsys.logikk.forvaltning.ansatte.AnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.EtatAnsatteLogikk;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.SortedMap;

/**
 *
 * @author hvb
 */
public class EtatAlleAarAnsatteAction {

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
      EtatAnsatteLogikk logikkEtat = new EtatAnsatteLogikk();
      AnsatteLogikk logikkAnsatte = new AnsatteLogikk();

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

      Ansatte ansatte = new Ansatte();
      boolean depom = false;

      String uri = request.getRequestURI();
      int i = uri.lastIndexOf("/");
      String etatkode = uri.substring(i + 1);

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

         if (!logikkEtat.eksistererEtat(etatkode)) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }

         ansatte.setEtatkode(etatkode);

         logikkAnsatte.beregnAnsatteTilEtatkode(etatkode, lonnskategori);

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

         logikkEtat.leggTilNavn(null, ansatte);
         if (ansatte.getEtatkode().equals(ansatte.getDepkode())) {
            depom = true;
            if (ansatte.getEtat() == null) {
               ansatte.setEtat("");
            } else {
               ansatte.setEtat("Departementsområdet " + ansatte.getEtat());
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

      request.setAttribute("totalLand", totalLand);
      request.setAttribute("ansatte", ansatte);
      request.setAttribute("depom", depom);

      request.setAttribute("total", total);
      request.setAttribute("mennTotal", mennTotal);
      request.setAttribute("kvinnerTotal", kvinnerTotal);

      request.setAttribute("heltid", heltid);
      request.setAttribute("mennHeltid", mennHeltid);
      request.setAttribute("kvinnerHeltid", kvinnerHeltid);

      request.setAttribute("deltid", deltid);
      request.setAttribute("mennDeltid", mennDeltid);
      request.setAttribute("kvinnerDeltid", kvinnerDeltid);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/etat_alle_aar.jsp");
      rd.forward(request, response);
   }
}
