package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetHierarkiLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EnhetHierarkiAction {

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
      EnhetHierarkiLogikk hierarkiLogikk = new EnhetHierarkiLogikk();
      Enhet staten = null;
      // Dato for når forvaltninghierarkiet skal vises.
      Date nedlagtDato = null;
      Date opprettetDato = null;

      // default dato, enten sist oppdatert eller nedlagt dato.
      Dato defaultDato = null;
      // dato bruker har oppgitt.
      Dato brukerdato = new Dato();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      // de faktiske http-parametrene.
      String paramAar = request.getParameter("y");
      String paramMaaned = request.getParameter("m");
      String paramDag = request.getParameter("d");


      Integer idnum = (Integer) request.getAttribute("idnum");
      Enhet enhet = (Enhet) request.getAttribute("enhet");

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         hierarkiLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (engelsk) {
            hierarkiLogikk.brukEngelsk();
         }

         opprettetDato = enhet.getTidspunkt();
         nedlagtDato = enhet.getTidspunktNedlagt();

         Dato datoYtre = parameterLogikk.getOppdatertYtreEnhet();
         Dato datoIntern = parameterLogikk.getOppdatertInternEnhet();

         sistOppdatertDato = datoYtre;
         if (datoIntern.getDate().before(datoYtre.getDate())) {
            sistOppdatertDato = datoIntern;
         }

         defaultDato = sistOppdatertDato;
         if (nedlagtDato != null) {
            defaultDato = new Dato(nedlagtDato);
         }

         brukerdato = Dato.getDato(paramAar, paramMaaned, paramDag, defaultDato);

         // hvis dato er OK --> finner hierarki.
         if (brukerdato.isGyldig() && !brukerdato.isEtter(sistOppdatertDato)
                 && !brukerdato.getDate().before(opprettetDato)
                 && (nedlagtDato == null || !brukerdato.getDate().after(nedlagtDato))) {
            staten = hierarkiLogikk.getHierarki(idnum, brukerdato.getDate());
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
      request.setAttribute("opprettetDato", new Dato(opprettetDato));
      request.setAttribute("nedlagtDato", (nedlagtDato != null ? new Dato(nedlagtDato) : null));
      request.setAttribute("staten", staten);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_hierarki.jsp");
      rd.forward(request, response);
   }
}
