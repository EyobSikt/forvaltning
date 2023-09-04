package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.ansatte.AnsatteLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.SysselsatteAnsatteLogikk;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author hvb
 */
public class LandAnsatteAction {

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
      AnsatteLogikk logikk = new AnsatteLogikk();
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

      // mapping: år --> sysselsatte.
      Map<Integer, Integer> sysselsatte = null;


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
         logikkSysselsatte.setConn(conn);

         logikk.beregnAnsatteLand(lonnskategori);

         total = logikk.getTotal();
         mennTotal = logikk.getMennTotal();
         kvinnerTotal = logikk.getKvinnerTotal();

         heltid = logikk.getHeltid();
         mennHeltid = logikk.getMennHeltid();
         kvinnerHeltid = logikk.getKvinnerHeltid();

         deltid = logikk.getDeltid();
         mennDeltid = logikk.getMennDeltid();
         kvinnerDeltid = logikk.getKvinnerDeltid();

         sysselsatte = logikkSysselsatte.getSysselsatteLand();
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

      request.setAttribute("total", total);
      request.setAttribute("mennTotal", mennTotal);
      request.setAttribute("kvinnerTotal", kvinnerTotal);

      request.setAttribute("heltid", heltid);
      request.setAttribute("mennHeltid", mennHeltid);
      request.setAttribute("kvinnerHeltid", kvinnerHeltid);

      request.setAttribute("deltid", deltid);
      request.setAttribute("mennDeltid", mennDeltid);
      request.setAttribute("kvinnerDeltid", kvinnerDeltid);

      request.setAttribute("sysselsatte", sysselsatte);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/land.jsp");
      rd.forward(request, response);
   }
}
