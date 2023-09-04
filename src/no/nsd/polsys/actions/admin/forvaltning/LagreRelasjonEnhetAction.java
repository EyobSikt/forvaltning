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
import no.nsd.polsys.logikk.forvaltning.RelasjonLogikk;

/**
 * Kontroll-logikk for opprette/endre/slette en relasjonsenhet.
 * @author hvb
 */
public class LagreRelasjonEnhetAction {

   /**
    * Called by the controller servlet.
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Connection conn = null;
      RelasjonLogikk logikk = new RelasjonLogikk();

      Integer id = Util.strengTilInteger(request.getParameter("id"));
      String navn = Util.tomStrengTilNull(request.getParameter("navn"));
      String engelskNavn = Util.tomStrengTilNull(request.getParameter("eng_navn"));

      String action = request.getParameter("action");
      
      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);

         if ("slett".equals(action)) {
            logikk.slettRelasjonAndreEnhet(id);
         } else if ("endre".equals(action)) {
            logikk.oppdaterRelasjonAndreEnhet(id, navn, engelskNavn);
         } else {
            logikk.registrerNyRelasjonAndreEnhet(navn, engelskNavn);
         }

      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         SqlUtil.close(conn);
      }

      HttpUtil.redirect("/forvaltning/relasjonenhet.p?lagret", request, response);
   }


}
