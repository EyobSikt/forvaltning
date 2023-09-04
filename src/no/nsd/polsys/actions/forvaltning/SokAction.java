package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.logikk.forvaltning.SokLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class SokAction {

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
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
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

         sistOppdatertDato = parameterLogikk.getOppdatertInternEnhet();

         if (paramSok != null) {
            enheter = sokLogikk.sok(paramSok);
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

      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("enheter", enheter);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/sok.jsp");
      rd.forward(request, response);
   }
}
