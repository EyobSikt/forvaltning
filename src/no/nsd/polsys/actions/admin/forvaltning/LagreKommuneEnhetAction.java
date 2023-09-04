package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;

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
public class LagreKommuneEnhetAction {

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
      Integer kommunenr = null;
      String kommunenavn = null;
      Integer fomAar = null;
      Integer tom_Aar = null;
      Integer tomAar = null;
      // logikk
      EnhetLogikk logikk = new EnhetLogikk();
      // kontroll
      Connection conn = null;

      try {
         idnum = new Integer(request.getParameter("idnum"));

         if(request.getParameter("kommunenr")==null || request.getParameter("kommunenr").equals("") ) {
            //ikke gjør noe........
         }
         else {
            kommunenr = new Integer(request.getParameter("kommunenr"));
         }
         if(request.getParameter("kommunenavn")==null || request.getParameter("kommunenavn").equals("") ) {
            //ikke gjør noe........
         }
         else {
            kommunenavn =  request.getParameter("kommunenavn");
         }
         if(request.getParameter("fomAar")==null || request.getParameter("fomAar").equals("") ) {
         //ikke gjør noe........
         }
         else {
            fomAar = new Integer(request.getParameter("fomAar"));
         }
         if(request.getParameter("tomAar")==null || request.getParameter("tomAar").equals("") ) {
            tomAar = 9999;
         }
         else {
            tomAar = Util.strengTilInteger(request.getParameter("tomAar"));
         }
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
         logikk.oppdaterKommuneTilEnhet(idnum, kommunenr, kommunenavn, fomAar, tomAar);
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
