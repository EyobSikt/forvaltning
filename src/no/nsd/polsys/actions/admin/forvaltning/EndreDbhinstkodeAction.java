package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.AarsmeldingLogikk;
import no.nsd.polsys.logikk.forvaltning.DbhdbLinkLogikk;
import no.nsd.polsys.modell.forvaltning.Aarsmelding;
import no.nsd.polsys.modell.forvaltning.DbhdbLink;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Kontroll-logikk for endring eller sletting av årsmeldinger.
 * @author hvb
 */
public class EndreDbhinstkodeAction {

   /**
    * Called by the controller servlet.
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // modell
      Integer id;
      Integer idnum;
      DbhdbLink ny = new DbhdbLink();
      String action;
      // logikk
      DbhdbLinkLogikk logikk = new DbhdbLinkLogikk();
      // kontroll
      Connection conn = null;

      try {
         action = request.getParameter("action");
         id = Integer.valueOf(request.getParameter("id"));
         idnum = Integer.valueOf(request.getParameter("idnum"));
         ny.setId(id);
         ny.setIdnum(idnum);
         ny.setAar(Util.strengTilInteger(request.getParameter("aar")));
         ny.setDbh_instkode(Util.tomStrengTilNull(request.getParameter("dbhinstkode")));
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
            logikk.slettDbh_instkode(id);
         } else {
            logikk.oppdaterDbh_instkode(ny);
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
