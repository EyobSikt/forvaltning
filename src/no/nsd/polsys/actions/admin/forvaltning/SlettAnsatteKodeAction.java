package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;

/**
 *
 * @author hvb
 */
public class SlettAnsatteKodeAction {

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
      String etatkode = null;
      String nyEtatkode = null;
      Integer fraAar = null;
      Integer tilAar = null;
      String dok = null;
      String action = null;

      // logikk
      EnhetLogikk logikk = new EnhetLogikk();
      // kontroll
      Connection conn = null;

      try {
         id = new Integer(request.getParameter("id"));
         idnum = new Integer(request.getParameter("idnum"));
         etatkode = Util.tomStrengTilNull(request.getParameter("etatkode"));
         nyEtatkode = Util.tomStrengTilNull(request.getParameter("ny_etatkode"));
         fraAar = Util.strengTilInteger(request.getParameter("fra_aar"));
         tilAar = Util.strengTilInteger(request.getParameter("til_aar"));
         dok = Util.tomStrengTilNull(request.getParameter("dok"));
         action = request.getParameter("action");
      } catch (Exception e) {
         request.setAttribute("e", e.toString());
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
         return;
      }

      // Settes til true hvis det oppstår en databasefeil.
      boolean feil = false;

      try {
         conn = DatabaseConnectionFactory.getConnection();
         conn.setAutoCommit(false);
         logikk.setConn(conn);
         if (action != null && action.equals("slett")) {
            logikk.slettAnsatteKoblingTilEnhet(id);
         } else {
            logikk.slettAnsatteKoblingTilEnhet(id);
            logikk.registrerNyAnsatteKoblingTilEnhet(idnum, nyEtatkode, fraAar, tilAar, dok);
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
         String url = request.getContextPath() + "/forvaltning/endringliste.p?idnum=" + idnum;
         url = response.encodeRedirectURL(url);
         response.sendRedirect(url);
      }
   }
}
