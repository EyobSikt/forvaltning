package no.nsd.polsys.actions.forvaltning.litteratur;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.AarsmeldingCacheFactory;
import no.nsd.polsys.factories.forvaltning.LitteraturEnhetCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.logikk.forvaltning.SokLogikk;
import no.nsd.polsys.modell.forvaltning.Aarsmelding;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class LitteraturEnhetSokAction {

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
      DepartementLogikk departementLogikk = new DepartementLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      SokLogikk sokLogikk = new SokLogikk();
      Enhet[] enheter = null;
      Map<Integer, List<Aarsmelding>> aarsmeldinger = null;
      Map<Integer, Integer> litteratur = null;
      // søk
      String paramSok = request.getParameter("s");

      try {
         conn = DatabaseConnectionFactory.getConnection();
         departementLogikk.setConn(conn);
         sokLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (request.getAttribute("en") != null) {
            sokLogikk.brukEngelsk();
         }

         if (paramSok != null && paramSok.length() != 0) {
            enheter = sokLogikk.sok(paramSok);
            aarsmeldinger = AarsmeldingCacheFactory.getAarsmeldinger(conn);
            litteratur = LitteraturEnhetCacheFactory.getLitteratur(conn);
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

      request.setAttribute("enheter", enheter);
      request.setAttribute("aarsmeldinger", aarsmeldinger);
      request.setAttribute("litteratur", litteratur);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur/litteratur_enhet.jsp");
      rd.forward(request, response);
   }
}
