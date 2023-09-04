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
import no.nsd.polsys.logikk.forvaltning.LovdataLogikk;
import no.nsd.polsys.modell.forvaltning.Lov;

/**
 *
 * @author hvb
 */
public class SlettLovdataAction {

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
      Lov lov = new Lov();
      // logikk
      LovdataLogikk logikk = new LovdataLogikk();
      // kontroll
      Connection conn = null;

      String action = request.getParameter("action");

      try {
         lov.setNummer(new Long(request.getParameter("lovnummer")));
         lov.setUrl(Util.tomStrengTilNull(request.getParameter("url")));
         lov.setNavn(Util.tomStrengTilNull(request.getParameter("navn")));
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
         logikk.setConn(conn);

         if (action.equals("slett")) {
            logikk.slettLovdata(lov.getNummer());
         } else if (action.equals("endre")) {
            logikk.oppdaterLovdata(lov);
         }
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
         String url = request.getContextPath() + "/forvaltning/lovdata.p";
         url = response.encodeRedirectURL(url);
         response.sendRedirect(url);
      }
   }
}
