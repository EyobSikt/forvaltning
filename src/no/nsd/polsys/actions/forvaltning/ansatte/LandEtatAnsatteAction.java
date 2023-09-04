package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.ansatte.EtatAnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.FylkeAnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.KommuneAnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.LandAnsatteLogikk;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hvb
 */
public class LandEtatAnsatteAction {

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
      LandAnsatteLogikk logikk = new LandAnsatteLogikk();
      FylkeAnsatteLogikk logikkFylke = new FylkeAnsatteLogikk();
      EtatAnsatteLogikk logikkEtat = new EtatAnsatteLogikk();
      KommuneAnsatteLogikk logikkKommune = new KommuneAnsatteLogikk();

      List<Ansatte> ansatte = null;
      List<Ansatte> total = null;

      List<Integer> alleAar = null;
      List<Ansatte> kommuner = null;
      Map<Integer, Ansatte> fylker = null;

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

      String visning = request.getParameter("vis");

      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);
         logikkFylke.setConn(conn);
         logikkEtat.setConn(conn);
         logikkKommune.setConn(conn);

         alleAar = logikk.getAlleAar();

         if (valgtAar == null && !alleAar.isEmpty()) {
            valgtAar = alleAar.get(0);
         }

         kommuner = logikkKommune.getAnsatteForKommuner(valgtAar, lonnskategori);
         fylker = logikkFylke.getFylker();
         total = logikkEtat.getAggregertAnsatteForEtat(valgtAar, lonnskategori);

         if (visning != null && (visning.equals("ans") || visning.equals("verk"))) {
            ansatte = logikkEtat.getAggregertAnsatteForEtatOgKommune(valgtAar, lonnskategori);
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

      request.setAttribute("kommuner", kommuner);
      request.setAttribute("fylker", fylker);
      request.setAttribute("alleAar", alleAar);
      request.setAttribute("valgtAar", valgtAar);

      request.setAttribute("total", total);
      request.setAttribute("ansatte", ansatte);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/land_etat.jsp");
      rd.forward(request, response);
   }
}
