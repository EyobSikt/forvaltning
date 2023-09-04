package no.nsd.polsys.actions.forvaltning.ansatte;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.ansatte.LandAnsatteCacheFactory;
import no.nsd.polsys.logikk.forvaltning.ansatte.EtatAnsatteLogikk;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author hvb
 */
public class EtaterAnsatteAction {

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
      EtatAnsatteLogikk logikk = new EtatAnsatteLogikk();

      List<Ansatte> etater = null;
      List<Integer> alleAar = new ArrayList<Integer>();
      SortedMap<Integer, Ansatte> total = null;

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

         total = LandAnsatteCacheFactory.getData(conn, lonnskategori);

         alleAar.addAll(total.keySet());
         Collections.reverse(alleAar);

         if (valgtAar == null) {
            valgtAar = alleAar.get(0);
         }

         etater = logikk.getAnsatteForEtat(valgtAar, lonnskategori);

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

      request.setAttribute("etater", etater);
      request.setAttribute("alleAar", alleAar);
      request.setAttribute("valgtAar", valgtAar);
      request.setAttribute("total", total);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte/etater.jsp");
      rd.forward(request, response);
   }
}
