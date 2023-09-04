package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.TallgruppeLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 * Kontroll-logikk for opprette/endre/slette satellitter.
 * @author hvb
 */
public class LagreSatellittAction {

   private final Tallgruppe satellitt = new Tallgruppe();
   private final List<String> ansatteKoder = new ArrayList<String>();
   private HttpServletRequest request;
   private String action;
   
   /**
    * Called by the controller servlet.
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.request = request;
      action = request.getParameter("action");
      
      try {
         this.opprettSatellitt();
         this.opprettAnsatteKoder();
      } catch (Exception e) {
         request.setAttribute("e", e.toString());
         HttpUtil.forward("/WEB-INF/jsp/error/feilmelding.jsp", request, response);
         return;
      }

      try {
         this.oppdaterDatabase();
      } catch (Exception e) {
         request.setAttribute("e", e.toString());
         HttpUtil.forward("/WEB-INF/jsp/error/feilmelding.jsp", request, response);
         return;
      }

      String url = "/forvaltning/endringliste.p?idnum=" + satellitt.getIdnum() + "&lagret";
      HttpUtil.redirect(url, request, response);
   }
   
      
   private void opprettSatellitt() {
      String paramId = request.getParameter("id");

      if (paramId != null) {
         satellitt.setId(Integer.valueOf(paramId));
      }
      // Obligatorisk
      satellitt.setIdnum(Integer.valueOf(request.getParameter("idnum")));
      satellitt.setFraTidspunkt(this.getDato(request.getParameter("fra_tidspunkt")));
      satellitt.setTilTidspunkt(this.getDato(request.getParameter("til_tidspunkt")));

      Kommune kommune = new Kommune();
      kommune.setKode(Util.strengTilInteger(request.getParameter("kommunenr")));
      satellitt.setKommune(kommune);

      satellitt.setNavn(Util.tomStrengTilNull(request.getParameter("navn")));
      satellitt.setEngelskNavn(Util.tomStrengTilNull(request.getParameter("eng_navn")));
      satellitt.setDokumentasjon(Util.tomStrengTilNull(request.getParameter("dokumentasjon")));
      satellitt.setEngelskDokumentasjon(Util.tomStrengTilNull(request.getParameter("eng_dokumentasjon")));
   }
   
   private Date getDato(String d) {
      if (d == null || d.length() == 0) {
         return null;
      }
      
      String[] s = d.split("\\.");
      int dag = Integer.parseInt(s[0]);
      int maaned = Integer.parseInt(s[1]);
      int aar = Integer.parseInt(s[2]);
      Dato dato = new Dato(aar, maaned, dag);
      return dato.getDate();
   }
   
   private void opprettAnsatteKoder() {
      String koder = Util.tomStrengTilNull(request.getParameter("ansattekoder"));
      if (koder == null) {
         return;
      }
      String[] relsplit = koder.split(" ");
      if (relsplit == null) {
         return;
      }
      for (String s : relsplit) {
         if (s != null && s.length() != 0) {
            ansatteKoder.add(s);
         }
      }
   }
   
   private void oppdaterDatabase() throws Exception {
      Connection conn = null;
      TallgruppeLogikk logikk = new TallgruppeLogikk();
      
      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);

         conn.setAutoCommit(false);

         if ("slett".equals(action)) {
            logikk.slettAnsatteKoblingTilSatellitt(satellitt.getId());
            logikk.slettSatellitt(satellitt.getId());
            conn.commit();
            return;
         }
         
         // Lagrer satellitten.
         if (satellitt.getId() == null) {
            satellitt.setId(logikk.registrerNySatellitt(satellitt.getIdnum()));
         }
         logikk.oppdaterSatellitt(satellitt);

         // Lagrer ansattekoder
         logikk.slettAnsatteKoblingTilSatellitt(satellitt.getId());
         for (String s : ansatteKoder) {
            logikk.registrerNyAnsatteKoblingTilSatellitt(satellitt.getId(), s);
         }

         conn.commit();

      } catch (Exception e) {
         SqlUtil.rollback(conn);
         throw e;
      } finally {
         SqlUtil.close(conn);
      }
   }
   
   
}
