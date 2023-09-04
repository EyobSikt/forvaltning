package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.OrgPrinsippLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.OrgPrinsipp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;


public class EndreOrgPrinsippAction {

   /**
    * Called by the controller servlet.
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // modell
      Integer id;
      Integer idnum;
      OrgPrinsipp ny = new OrgPrinsipp();
      String action;
      // logikk
      OrgPrinsippLogikk logikk = new OrgPrinsippLogikk();
      // kontroll
      Connection conn = null;

      try {
         action = request.getParameter("action");
         id = Integer.valueOf(request.getParameter("id"));
         idnum = Integer.valueOf(request.getParameter("idnum"));
         ny.setId(id);
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
         HttpUtil.forward("/WEB-INF/jsp/error/feilmelding.jsp", request, response);
         return;
      }
      // Settes til true hvis det oppstår en databasefeil.
      boolean feil = false;
      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);

         if ("slett".equals(action)) {
            logikk.slettOrgPrinsipp(id);
         } else {
            logikk.oppdaterOrgPrinsipp(ny);
         }

      } catch (Exception e) {
         SqlUtil.rollback(conn);
         request.setAttribute("e", e.getMessage());
         feil = true;
      } finally {
         SqlUtil.close(conn);
      }

      if (feil) {
         HttpUtil.forward("/WEB-INF/jsp/error/feilmelding.jsp", request, response);
      } else {
         HttpUtil.redirect("/forvaltning/endringliste.p?idnum=" + idnum, request, response);
      }
   }
}
