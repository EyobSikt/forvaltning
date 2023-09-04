package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.ArkivportalenLogikk;
import no.nsd.polsys.modell.forvaltning.Arkivportalen;
import no.nsd.polsys.modell.forvaltning.Tildelingsbrev;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Kontroll-logikk for endring eller sletting av årsmeldinger.
 * @author hvb
 */
public class EndreArkivportalenAction {

   /**
    * Called by the controller servlet.
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // modell
      Integer id;
      Integer idnum;
      Arkivportalen ny = new Arkivportalen();
      String action;
      // logikk
      ArkivportalenLogikk logikk = new ArkivportalenLogikk();
      // kontroll
      Connection conn = null;

      try {
         action = request.getParameter("action");
         id = Integer.valueOf(request.getParameter("id"));
         idnum = Integer.valueOf(request.getParameter("idnum"));
         ny.setId(id);
         ny.setIdnum(idnum);
         ny.setNavn(Util.tomStrengTilNull(request.getParameter("navn")));
         ny.setTidsrom(Util.tomStrengTilNull(request.getParameter("tidsrom")));
         ny.setForvaltningsomrade(Util.tomStrengTilNull(request.getParameter("forvaltningsomrade")));
         ny.setUrl(Util.tomStrengTilNull(request.getParameter("url")));
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
            logikk.slettArkivportalen(id);
         } else {
            logikk.oppdaterArkivportalen(ny);
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
