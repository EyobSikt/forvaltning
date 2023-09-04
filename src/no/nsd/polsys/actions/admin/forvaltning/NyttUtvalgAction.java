package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.UtvalgLogikk;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author hvb
 */
public class NyttUtvalgAction {

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
      Integer utvalgnr = null;
      Boolean utvalgaar2003 = null;
      Boolean utvalgaar2004 = null;
      Integer idnum = null;
      // logikk
      UtvalgLogikk utvalgLogikk = new UtvalgLogikk();
      // kontroll
      Connection conn = null;

      try {
         utvalgnr = new Integer(request.getParameter("utvalg"));
         utvalgaar2003 = new Boolean(request.getParameter("utvalgaar2003"));
         utvalgaar2004 = new Boolean(request.getParameter("utvalgaar2004"));
         idnum = new Integer(request.getParameter("idnum"));
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
         utvalgLogikk.setConn(conn);
         utvalgLogikk.registrerNyttUtvalg(utvalgnr, idnum, utvalgaar2003, utvalgaar2004 );
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
