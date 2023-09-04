package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.AarsmeldingLogikk;
import no.nsd.polsys.modell.forvaltning.Aarsmelding;

/**
 * Kontroll-logikk for endring eller sletting av årsmeldinger.
 * @author hvb
 */
public class EndreAarsmeldingAction {

   /**
    * Called by the controller servlet.
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // modell
      Integer id;
      Integer idnum;
      Aarsmelding ny = new Aarsmelding();
      String action;
      // logikk
      AarsmeldingLogikk logikk = new AarsmeldingLogikk();
      // kontroll
      Connection conn = null;

      try {
         action = request.getParameter("action");
         id = Integer.valueOf(request.getParameter("id"));
         idnum = Integer.valueOf(request.getParameter("idnum"));
         ny.setId(id);
         ny.setIdnum(idnum);
         ny.setAar(Util.strengTilInteger(request.getParameter("aar")));
         ny.setAarsmelding(Util.tomStrengTilNull(request.getParameter("norsk")));
         ny.setEngelskAarsmelding(Util.tomStrengTilNull(request.getParameter("engelsk")));
         ny.setSisteUrl(Util.tomStrengTilNull(request.getParameter("url")));
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
            logikk.slettAarsmelding(id);
         } else {
            logikk.oppdaterAarsmelding(ny);
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
