package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.ansatte.LandAnsatteCacheFactory;
import no.nsd.polsys.logikk.forvaltning.ansatte.AnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.FylkeAnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.LandAnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.SysselsatteAnsatteLogikk;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author hvb
 */
public class FylkerAnsatteAction {

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
      LandAnsatteLogikk logikkLand = new LandAnsatteLogikk();
      AnsatteLogikk logikkAnsatte = new AnsatteLogikk();
      SysselsatteAnsatteLogikk logikkSysselsatte = new SysselsatteAnsatteLogikk();

      Map<Integer, Ansatte> fylker = null;
      List<Ansatte> ansatte = null;

      SortedMap<Integer, Ansatte> total = null;
      List<Integer> alleAar = null;

      // mapping: fylkenr --> sysselsatte.
      Map<Integer, Integer> sysselsatte = null;
      Integer sysselsatteLand = null;

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
         logikkLand.setConn(conn);
         logikkAnsatte.setConn(conn);
         logikkSysselsatte.setConn(conn);

         alleAar = logikkLand.getAlleAar();
         if (valgtAar == null) {
            valgtAar = alleAar.get(0);
         }

         fylker = logikk.getFylker();
         ansatte = logikk.getAnsatteForFylker(valgtAar, lonnskategori);

         total = LandAnsatteCacheFactory.getData(conn, lonnskategori);

         sysselsatte = logikkSysselsatte.getSysselsatteFylker(valgtAar);
         Map<Integer, Integer> sl = logikkSysselsatte.getSysselsatteLand();
         if (sl != null) {
            sysselsatteLand = sl.get(valgtAar);
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

      request.setAttribute("fylker", fylker);
      request.setAttribute("ansatte", ansatte);
      request.setAttribute("total", total);
      request.setAttribute("alleAar", alleAar);
      request.setAttribute("valgtAar", valgtAar);
      request.setAttribute("sysselsatte", sysselsatte);
      request.setAttribute("sysselsatteLand", sysselsatteLand);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/fylker.jsp");
      rd.forward(request, response);
   }
}
