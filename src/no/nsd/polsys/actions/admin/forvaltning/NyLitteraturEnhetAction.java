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
import no.nsd.polsys.logikk.forvaltning.LitteraturLogikk;
import no.nsd.polsys.modell.forvaltning.Litteratur;

/**
 *
 * @author hvb
 */
public class NyLitteraturEnhetAction {

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
      Integer idnum = null;
      Litteratur nyLitt = new Litteratur();
      String tillitt = null;
      // logikk
      LitteraturLogikk logikk = new LitteraturLogikk();
      // kontroll
      Connection conn = null;

      try {
         idnum = new Integer(request.getParameter("idnum"));
         nyLitt.setPubId(new Integer(request.getParameter("pub_id")));
         nyLitt.setFraAar(Util.strengTilInteger(request.getParameter("fra_aar")));
         nyLitt.setTilAar(Util.strengTilInteger(request.getParameter("til_aar")));
         tillitt = request.getParameter("tillitt");
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
         logikk.registrerNyLitteraturEnhet(nyLitt, idnum);
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

      String url = request.getContextPath() + "/forvaltning/endringliste.p?idnum=" + idnum;
      if (tillitt != null) {
         url = request.getContextPath() + "/forvaltning/litteratur.p?pub_id=" + nyLitt.getPubId();
      }

      if (feil) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
      } else {
         url = response.encodeRedirectURL(url);
         response.sendRedirect(url);
      }
   }
}
