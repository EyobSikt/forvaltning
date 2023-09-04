package no.nsd.polsys.actions.admin.forvaltning;

import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.logikk.forvaltning.*;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Controller-logikk for hovedsiden for en enhet i admin-sidene.
 * @author hvb
 */
public class EndringlisteAction {

   private static final String tabellnavn = "t_forvaltning_endring";
   
   private List tab;
   private Integer idnum;
   private List<Integer> orgnr;
   private List<Integer> virksomhetsnummer;
   private List<Aarsmelding> aarsmeldinger;
   private List<OrgPrinsipp> orgprinsipp;
   private List<OrgPrinsipp> allorgprinsipp;
   private List<Instruks> instrukser;
   private List<DbhdbLink> dbhdbLink;
   private List<Tildelingsbrev> tildelingsbrev;
   private List<Arkivportalen> arkivportalen;
   private List<Utvalg> utvalg;
   private List<Lov> lovdata;
   private List<Litteratur> litteratur;
   private List<Tallgruppe> satellitter;
   private Kommune kommune = new Kommune();
   // i = id, j = navn.
   private Object[][] navn;
   private Integer selskapsdbId;
   private Integer norgenoId;
   private List<Ansatte> ansatteKoder;
   
   
   /**
    * Called by controller servlet.
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
      try {
         idnum = Integer.valueOf(request.getParameter("idnum"));
      } catch (Exception ignored) {
         HttpUtil.forward("/WEB-INF/jsp/error/404.jsp", request, response);
         return;
      }

      if (!this.getData()) {
         HttpUtil.forward("/WEB-INF/jsp/error/404.jsp", request, response);
         return;
      }

      request.setAttribute("tab", tab);
      request.setAttribute("tabellnavn", tabellnavn);
      request.setAttribute("orgnr", orgnr);
      request.setAttribute("virksomhetsnummer", virksomhetsnummer);
      request.setAttribute("dbhdbLink", dbhdbLink);
      request.setAttribute("aarsmeldinger", aarsmeldinger);
      request.setAttribute("orgprinsipp", orgprinsipp);
      request.setAttribute("allorgprinsipp", allorgprinsipp);
      request.setAttribute("instrukser", instrukser);
      request.setAttribute("tildelingsbrev", tildelingsbrev);
      request.setAttribute("arkivportalen", arkivportalen);
      request.setAttribute("utvalg", utvalg);
      request.setAttribute("lovdata", lovdata);
      request.setAttribute("kommune", kommune);
      request.setAttribute("navn", navn);
      request.setAttribute("litteratur", litteratur);
      request.setAttribute("satellitter", satellitter);
      request.setAttribute("selskapsdbId", selskapsdbId);
      request.setAttribute("norgenoId", norgenoId);
      request.setAttribute("ansatteKoder", ansatteKoder);
      
      HttpUtil.forward("/WEB-INF/jsp/forvaltning/endringliste.jsp", request, response);
      
   }
   
   
   private boolean getData() throws ServletException {
      Connection conn = null;
      
      EndringLogikk logikk = new EndringLogikk();
      EnhetLogikk enhetLogikk = new EnhetLogikk();
      DbhdbLinkLogikk dbhdbLinkLogikk = new DbhdbLinkLogikk();
      AarsmeldingLogikk aarsmeldingLogikk = new AarsmeldingLogikk();
      InstruksLogikk instruksLogikk = new InstruksLogikk();
      OrgPrinsippLogikk orgPrinsippLogikk = new OrgPrinsippLogikk();
      TildelingsbrevLogikk tildelingsbrevLogikk = new TildelingsbrevLogikk();
      ArkivportalenLogikk arkivportalenLogikk = new ArkivportalenLogikk();
      UtvalgLogikk utvalgLogikk = new UtvalgLogikk();
      LovdataLogikk lovdataLogikk = new LovdataLogikk();
      LitteraturLogikk litteraturLogikk = new LitteraturLogikk();
      TallgruppeLogikk tallgruppeLogikk = new TallgruppeLogikk();
      
      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);
         enhetLogikk.setConn(conn);
         dbhdbLinkLogikk.setConn(conn);
         aarsmeldingLogikk.setConn(conn);
         instruksLogikk.setConn(conn);
         orgPrinsippLogikk.setConn(conn);
         tildelingsbrevLogikk.setConn(conn);
         arkivportalenLogikk.setConn(conn);
         utvalgLogikk.setConn(conn);
         lovdataLogikk.setConn(conn);
         litteraturLogikk.setConn(conn);
         tallgruppeLogikk.setConn(conn);

         tab = logikk.getData(tabellnavn, idnum);

         if (tab == null || tab.isEmpty()) {
            return false;
         }

         orgnr = enhetLogikk.getOrgnrTilEnhet(idnum);
         virksomhetsnummer = enhetLogikk.getVirksomhetsnummerTilEnhet(idnum);
         dbhdbLink = dbhdbLinkLogikk.getDbhdbLinkTilEnhet(idnum);
         aarsmeldinger = aarsmeldingLogikk.getAarsmeldingerTilEnhetNoCache(idnum);
         orgprinsipp = orgPrinsippLogikk.getOrgPrinsippTilEnhetNoCache(idnum);
         allorgprinsipp = orgPrinsippLogikk.getAllOrgPrinsippTilEnhetNoCache();
         instrukser = instruksLogikk.getInstrukserTilEnhetNoCache(idnum);
         tildelingsbrev = tildelingsbrevLogikk.getTildelingsbrevTilEnhetNoCache(idnum);
         arkivportalen = arkivportalenLogikk.getArkivportalenTilEnhetNoCache(idnum);
         utvalg = utvalgLogikk.getUtvalg_enhet(idnum);
         lovdata = lovdataLogikk.getLovdataTilRegEnhet(idnum);
         navn = enhetLogikk.getAndreNavnTilEnhet(idnum);
         litteratur = litteraturLogikk.getLitteraturTilEnhet(idnum);
         
         selskapsdbId = enhetLogikk.getSelskapsidTilEnhet(idnum);
         norgenoId = enhetLogikk.getNorgeNoIdTilEnhet(idnum);
         ansatteKoder = enhetLogikk.getAnsatteKoblingTilEnhet(idnum);

         
         satellitter = tallgruppeLogikk.getSatellitter(idnum);
         if (satellitter != null) {
            for (Tallgruppe t : satellitter) {
               String koder = tallgruppeLogikk.getSatellittAnsatteKobling(t.getId());
               t.setAnsatteKoder(koder);
            }
         }

         
         kommune.setKode(enhetLogikk.getKommuneTilEnhet(idnum));
         if (kommune.getKode() != null) {
            List<Kommune> k = KommuneCacheFactory.getKommuner(conn);
            kommune = enhetLogikk.getKommune(k, kommune.getKode(), null);
         }

         return true;
         
      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         SqlUtil.close(conn);
      }
   }
   
   
   
}
