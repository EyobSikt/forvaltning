package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.ansatte.LandAnsatteCacheFactory;
import no.nsd.polsys.logikk.forvaltning.ansatte.AnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.LandAnsatteLogikk;
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
public class LokaliseringAnsatteAction {

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
      LandAnsatteLogikk logikkLand = new LandAnsatteLogikk();
      AnsatteLogikk logikkAnsatte = new AnsatteLogikk();

      SortedMap<Integer, Ansatte> oslo = null;
      SortedMap<Integer, Ansatte> utlandet = null;
      SortedMap<Integer, Ansatte> uspesifisert = null;
      SortedMap<Integer, Ansatte> resten = null; // spesifiserte kommuner i Norge

      SortedMap<Integer, Ansatte> totalLand = null;

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
         logikkLand.setConn(conn);
         logikkAnsatte.setConn(conn);


         logikkAnsatte.beregnAnsatteTilFylke(3, lonnskategori);
         oslo = logikkAnsatte.getTotal();

         logikkAnsatte.beregnAnsatteTilFylke(25, lonnskategori);
         utlandet = logikkAnsatte.getTotal();

         logikkAnsatte.beregnAnsatteTilFylke(99, lonnskategori);
         uspesifisert = logikkAnsatte.getTotal();

         logikkAnsatte.beregnAnsatteTilAndreFylker(lonnskategori);
         resten = logikkAnsatte.getTotal();

         // total
         totalLand = LandAnsatteCacheFactory.getData(conn, lonnskategori);


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

      request.setAttribute("oslo", oslo);
      request.setAttribute("utlandet", utlandet);
      request.setAttribute("uspesifisert", uspesifisert);
      request.setAttribute("resten", resten);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/lokalisering.jsp");
      rd.forward(request, response);
   }
}
