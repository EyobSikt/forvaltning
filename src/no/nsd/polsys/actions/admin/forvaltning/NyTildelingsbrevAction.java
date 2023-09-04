package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.TildelingsbrevLogikk;
import no.nsd.polsys.modell.forvaltning.Tildelingsbrev;

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
public class NyTildelingsbrevAction {

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

      Tildelingsbrev ny = new Tildelingsbrev();

      // logikk
      TildelingsbrevLogikk logikk = new TildelingsbrevLogikk();
      // kontroll
      Connection conn = null;

      try {
         idnum = new Integer(request.getParameter("idnum"));
         ny.setIdnum(idnum);
         ny.setAar(Util.strengTilInteger(request.getParameter("aar")));
         ny.setTildelingsbrev(Util.tomStrengTilNull(request.getParameter("norsk")));
         ny.setEngelskTildelingsbrev(Util.tomStrengTilNull(request.getParameter("engelsk")));
         ny.setSisteUrl(Util.tomStrengTilNull(request.getParameter("url")));
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
         logikk.registrerNyTildelingsbrev(ny);
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
