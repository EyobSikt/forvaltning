package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.OrgnrLogikk;

/**
 *
 * @author hvb
 */
public class SlettOrgnrAction {

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
      Integer orgnr = null;
      Integer idnum = null;
      // logikk
      OrgnrLogikk orgnrLogikk = new OrgnrLogikk();
      // kontroll
      Connection conn = null;

      try {
         orgnr = new Integer(request.getParameter("orgnr"));
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
         orgnrLogikk.setConn(conn);
         orgnrLogikk.slettOrgnr(idnum, orgnr);
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
