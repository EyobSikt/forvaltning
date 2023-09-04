package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.InstruksLogikk;
import no.nsd.polsys.logikk.forvaltning.OrgPrinsippLogikk;
import no.nsd.polsys.modell.forvaltning.Instruks;
import no.nsd.polsys.modell.forvaltning.OrgPrinsipp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import no.nsd.polsys.modell.Dato;

public class NyOrgPrinsippAction {

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

      OrgPrinsipp ny = new OrgPrinsipp();

      // logikk
      OrgPrinsippLogikk logikk = new OrgPrinsippLogikk();
      // kontroll
      Connection conn = null;

      try {
         idnum = new Integer(request.getParameter("idnum"));
         ny.setIdnum(idnum);
         int fradag = 0;
         int framaaned = 0;
         int fraaar = 0;
         if (request.getParameter("fradag")!="")
         {
         fradag = Integer.parseInt(request.getParameter("fradag"));
         framaaned = Integer.parseInt(request.getParameter("framaaned"));
         fraaar = Integer.parseInt(request.getParameter("fraaar"));
         Dato fradato = new Dato(fraaar, framaaned, fradag);
         ny.setFraTidspunkt(fradato.getDate());
      }
         else{
            ny.setFraTidspunkt(null);
         }
         int tildag= 0;
         int tilmaaned= 0;
         int tilaar = 0;

         if (request.getParameter("tildag")!="")
         {
         tildag = Integer.parseInt(request.getParameter("tildag"));
         tilmaaned = Integer.parseInt(request.getParameter("tilmaaned"));
         tilaar = Integer.parseInt(request.getParameter("tilaar"));
         Dato tildato = new Dato(tilaar, tilmaaned, tildag);
         ny.setTilTidspunkt(tildato.getDate());
         }
         else{
            ny.setTilTidspunkt(null);
         }
         ny.setOrgprinsippId(Util.strengTilInteger(request.getParameter("orgprinsipp_id")));
         ny.setKommentar(Util.tomStrengTilNull(request.getParameter("kommentar")));
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
         logikk.registrerNyOrgPrinsipp(ny);
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
