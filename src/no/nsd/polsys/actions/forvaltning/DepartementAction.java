package no.nsd.polsys.actions.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Enhet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author hvb
 */
public class DepartementAction {

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
      List<Enhet> departementer = null;
      // dato bruker har oppgitt.
      Dato brukerdato = new Dato();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      // de faktiske http-parametrene.
      String paramAar = request.getParameter("y");
      String paramMaaned = request.getParameter("m");
      String paramDag = request.getParameter("d");

      try {
         conn = DatabaseConnectionFactory.getConnection();
         departementLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (request.getAttribute("en") != null) {
            departementLogikk.brukEngelsk();
         }
         sistOppdatertDato = parameterLogikk.getOppdatertInternEnhet();
         brukerdato = Dato.getDato(paramAar, paramMaaned, paramDag, sistOppdatertDato);

         // så lenge tidspunkt ikke er etter oppdatert og dato er gyldig --> OK.
         if (brukerdato.isGyldig() && !brukerdato.isEtter(sistOppdatertDato)) {
            // finner departementene for gitt tidspunkt.
            departementer = departementLogikk.getDepartementer(brukerdato.getDate());
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

      request.setAttribute("brukerdato", brukerdato);
      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("departementer", departementer);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/departement.jsp");
      rd.forward(request, response);
   }
}
