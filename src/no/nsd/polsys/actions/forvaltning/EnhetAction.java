package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.factories.forvaltning.*;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.OppgaveLogikk;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Cofog;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.OrgPrinsipp;

/**
 *
 * @author hvb
 */
public class EnhetAction {

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
      Connection conn = null;
      EnhetLogikk enhetLogikk = new EnhetLogikk();
      OppgaveLogikk oppgaveLogikk = new OppgaveLogikk();

      List<Integer> orgnr = null;
      Map<Integer, DokCache> endringskoder = null;
      Map<Integer, DokCache> tilknytninger = null;
      Map<Integer, DokCache> nivaaer = null;
      Map<Integer, DokCache> grunnenheter = null;
      Map<String, Cofog> cofog = null;
      // mapping: oppgavekode --> oppgavenavn.
      Map<Integer, String> oppgavenavn = null;
      Kommune kommune = null;
      String navn = null;

      Integer idnum = (Integer) request.getAttribute("idnum");
      Enhet enhet = (Enhet) request.getAttribute("enhet");

      List<OrgPrinsipp> orgprinsipp = null;

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         oppgaveLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }

         orgnr = enhetLogikk.getOrgnrTilEnhet(idnum);
         Integer kommunenr = enhetLogikk.getKommuneTilEnhet(idnum);
         List<Kommune> kommuner = KommuneCacheFactory.getKommuner(conn);
         if (enhet.getKommune() != null) {
            enhet.setKommune(enhetLogikk.getKommune(kommuner, enhet.getKommune().getKode(), enhet.getTidspunktNedlagt()));
         }
         if (kommunenr != null) {
            kommune = enhetLogikk.getKommune(kommuner, kommunenr, enhet.getTidspunktNedlagt());
         }

         if (engelsk) {
            endringskoder = EndringskodeCacheFactory.getDokdataEngelsk(conn);
            tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
            nivaaer = NivaaCacheFactory.getDokdataEngelsk(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
            cofog = CofogCacheFactory.getDokdataEngelsk(conn);
         } else {
            endringskoder = EndringskodeCacheFactory.getDokdata(conn);
            tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
            nivaaer = NivaaCacheFactory.getDokdata(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdata(conn);
            cofog = CofogCacheFactory.getDokdata(conn);
         }

         // i = id, j = navn.
         Object[][] n = enhetLogikk.getAndreNavnTilEnhet(idnum);
         if (n != null && n.length != 0) {
            navn = (String) n[0][1];
            for (int i = 1; i < n.length; i++) {
               navn += ", " + n[i][1];
            }
         }

         oppgavenavn = oppgaveLogikk.getOppgaver();
         orgprinsipp = enhetLogikk.getOrgPrinsippTilEnhet(idnum);

      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception ignored) {
            conn = null;
         }
      }
      request.setAttribute("orgnr", orgnr);
      request.setAttribute("navn", navn);
      request.setAttribute("endringskoder", endringskoder);
      request.setAttribute("tilknytninger", tilknytninger);
      request.setAttribute("nivaaer", nivaaer);
      request.setAttribute("grunnenheter", grunnenheter);
      request.setAttribute("cofog", cofog);
      request.setAttribute("kommune", kommune);
      request.setAttribute("oppgavenavn", oppgavenavn);
      request.setAttribute("orgprinsipp", orgprinsipp);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet.jsp");
      rd.forward(request, response);
   }
}
