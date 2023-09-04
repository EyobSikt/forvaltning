package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EndringLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.RelasjonLogikk;

/**
 *
 * @author hvb
 */
public class SlettEndringAction {

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
      // modell
      Integer id = null;
      Integer idnum = null;
      // logikk
      EnhetLogikk enhetLogikk = new EnhetLogikk();
      EndringLogikk endringLogikk = new EndringLogikk();
      RelasjonLogikk relasjonLogikk = new RelasjonLogikk();
      // kontroll
      Connection conn = null;
      String bekreft = request.getParameter("bekreft");

      try {
         id = new Integer(request.getParameter("id"));
         idnum = new Integer(request.getParameter("idnum"));
      } catch (Exception e) {
         request.setAttribute("e", e.toString());
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
         return;
      }

      if (bekreft == null || bekreft.length() == 0) {
         String url = request.getContextPath() + "/forvaltning/endring.p?id=" + id + "&slett";
         url = response.encodeRedirectURL(url);
         response.sendRedirect(url);
         return;
      }

      // Settes til true hvis det oppstår en databasefeil.
      boolean feil = false;

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         endringLogikk.setConn(conn);
         relasjonLogikk.setConn(conn);

         conn.setAutoCommit(false);

         // Sletter relasjoner.
         relasjonLogikk.slettRelasjoner(id);
         // Sletter record.
         endringLogikk.slettEndring(id);
         // Sletter enhet hvis ingen record.
         List records = endringLogikk.getData("t_forvaltning_endring", idnum);
         if (records == null || records.size() == 0) {
            enhetLogikk.slettEnhet(idnum);
         }

         conn.commit();

      } catch (Exception e) {
         try {
            conn.rollback();
         } catch (SQLException ignored) {
         }
         request.setAttribute("e", e.getMessage());
         feil = true;
      } finally {
         try {
            conn.close();
         } catch (Exception ignored) {
            conn = null;
         }
      }

      if (feil) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
      } else {
         String url = request.getContextPath() + "/forvaltning/velgendring.p?endringslettet";
         url = response.encodeRedirectURL(url);
         response.sendRedirect(url);
      }
   }
}
