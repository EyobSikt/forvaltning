package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.NivaaCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class DepartementsenhetAction {

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
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      Map<Integer, DokCache> nivaaerMapping = null;
      List<DokCache> nivaaer = null;

      try {
         conn = DatabaseConnectionFactory.getConnection();
         parameterLogikk.setConn(conn);
         sistOppdatertDato = parameterLogikk.getOppdatertInternEnhet();

         if (request.getAttribute("en") != null) {
            nivaaerMapping = NivaaCacheFactory.getDokdataEngelsk(conn);
         } else {
            nivaaerMapping = NivaaCacheFactory.getDokdata(conn);
         }

         // Skal ikke ha med nivå 0 for denne siden.
         //nivaaerMapping.remove(0);

         nivaaer = new ArrayList<DokCache>(nivaaerMapping.values());
         Collections.sort(nivaaer);

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

      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("nivaaer", nivaaer);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/departementsenhet.jsp");
      rd.forward(request, response);
   }
}
