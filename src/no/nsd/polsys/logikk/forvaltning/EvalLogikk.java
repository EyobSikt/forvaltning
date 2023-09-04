package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.factories.forvaltning.RelasjonAndreCacheFactory;
import no.nsd.polsys.factories.forvaltning.RelasjonCacheFactory;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EvalLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;
   private EnhetUtilLogikk util = new EnhetUtilLogikk();
   // mapping idnum --> enhet.
   private Map<Integer, Enhet> enheter;
   // mapping idnum --> liste med endringer.
   private Map<Integer, List<Enhet>> enheterMedEndring;
   // Inneholder alle relevante enheters sitt idnum.
   private Set<Integer> relevanteIdnum;
   // år "null".
   private Date fra;
   private List<Enhet> tempEnheter;
   private Date tempDato = null;
   // liste med alle endringer i basen.
   private List<EndringCache> alleEndringer;
   // mapping: endringsid --> liste av idnums.
   private Map<Integer, List<Integer>> alleRelasjoner;
   // mapping: endringsid --> liste av enheter (relasjonenheter).
   private Map<Integer, List<Enhet>> alleRelasjonerAndre;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }

   /**
    * Returnerer mapping: idnum --> liste med endringer, der parameter fra er år
    * "null". Det vil si alle endringer som eksisterer fra år "null" vil få en
    * opprettelse på dette tidspunktet, slik enheten er på det tidspunktet.
    *
    * @param fra er år null.
    * @return
    * @throws SQLException
    */
   public Map<Integer, List<Enhet>> getEndringer(Date fra) throws SQLException {

      this.fra = fra;
      this.initierVariabler();

      this.behandleEndringer();

      return this.getRelevantMap();
   }

   private Map<Integer, List<Enhet>> getRelevantMap() {
      // mapping idnum --> liste med endringer.
      Map<Integer, List<Enhet>> map = new HashMap<Integer, List<Enhet>>(4000, 0.95f);
      for (Integer idnum : this.relevanteIdnum) {
         map.put(idnum, this.enheterMedEndring.get(idnum));
      }
      return map;
   }

   private void initierVariabler() throws SQLException {
      enheter = new HashMap<Integer, Enhet>(4000, 0.95f);
      enheterMedEndring = new HashMap<Integer, List<Enhet>>(4000, 0.95f);
      relevanteIdnum = new HashSet<Integer>();
      tempEnheter = new ArrayList<Enhet>();

      util.setEnheter(enheter);

      alleEndringer = EndringCacheFactory.getEndringer(this.conn);
      alleRelasjoner = RelasjonCacheFactory.getRelasjoner(this.conn);
      alleRelasjonerAndre = RelasjonAndreCacheFactory.getRelasjoner(this.conn);
   }

   private void behandleEndringer() {
      boolean forbiFradato = false;

      for (EndringCache endringCache : alleEndringer) {
         Enhet enhet = new Enhet(endringCache);

         if (enhet.getTidspunkt().after(fra) && !forbiFradato) {
            forbiFradato = true;
            this.sjekkEndring(enheter.values());
         }

         this.oppdaterEnheter(enhet);

         // enhet.tidspunkt > fra
         if (enhet.getTidspunkt().after(fra)) {
            this.leggTilRelesjoner(enhet);
            this.leggTilTempEnheter(enhet);
         }
      }

      this.sjekkEndring(tempEnheter);

   }

   private void leggTilRelesjoner(Enhet enhet) {
      List<Integer> relasjoner = alleRelasjoner.get(enhet.getEndringsid());
      if (relasjoner != null) {
         List<Enhet> enhetRel = new ArrayList<Enhet>();
         enhet.setRelasjoner(enhetRel);
         for (Integer i : relasjoner) {
            Enhet e = new Enhet();
            e.setIdnum(i);
            enhetRel.add(e);
         }
      }

      enhet.setRelasjonerAndre(alleRelasjonerAndre.get(enhet.getEndringsid()));
   }

   private void oppdaterEnheter(Enhet enhet) {
      Enhet eksisterende = enheter.get(enhet.getIdnum());
      if (eksisterende == null) {
         eksisterende = new Enhet(enhet);
         if (eksisterende.getTidspunkt().before(fra)) {
            eksisterende.setTidspunkt(fra);
         }
         enheter.put(eksisterende.getIdnum(), eksisterende);
      } else {
         if (enhet.getEndringskode() != null && (enhet.getEndringskode().intValue() / 100 == 3)) {
            eksisterende.setNedlagt(true);
            eksisterende.setTidspunktNedlagt(enhet.getTidspunkt());
         }
         if (enhet.getTilknytningsform() != null) {
            eksisterende.setTilknytningsform(enhet.getTilknytningsform());
         }
         if (enhet.getNivaa() != null) {
            eksisterende.setNivaa(enhet.getNivaa());
         }
         if (enhet.getOverordnetIdnum() != null) {
            eksisterende.setOverordnetIdnum(enhet.getOverordnetIdnum());
         }
         if (enhet.getGrunnenhet() != null) {
            eksisterende.setGrunnenhet(enhet.getGrunnenhet());
         }
         if (enhet.getLangtNavn() != null) {
            eksisterende.setLangtNavn(enhet.getLangtNavn());
         }
         if (enhet.getKortNavn() != null) {
            eksisterende.setKortNavn(enhet.getKortNavn());
         }
         if (enhet.getCofog() != null) {
            eksisterende.setCofog(enhet.getCofog());
         }
      }
   }

   private void leggTilTempEnheter(Enhet enhet) {
      if (tempDato == null) {
         tempDato = enhet.getTidspunkt();
      }

      if (enhet.getTidspunkt().after(tempDato)) {
         this.arv();
         this.sjekkEndring(tempEnheter);
         tempEnheter = new ArrayList<Enhet>();
         tempDato = enhet.getTidspunkt();
      }

      tempEnheter.add(enhet);
   }

   private void arv() {
      for (Enhet e : this.enheter.values()) {
         List<Enhet> arvEnheter = new ArrayList<Enhet>();
         util.arvEndringer(e, this.tempEnheter, arvEnheter);
         this.tempEnheter.addAll(arvEnheter);
      }
   }

   private void sjekkEndring(Collection<Enhet> endringer) {
      for (Enhet e : endringer) {
         Enhet copy = new Enhet(e);
         this.setOverordnetDep(copy);
         util.brukEtatnavnHvisHovedkontor(copy);
         this.leggTilEnheterMedEndring(copy);
         this.sjekkRelevanteEnheter();
      }
   }

   private void setOverordnetDep(Enhet e) {
      util.setOverordnetDepartement(e);
      // må defensivt kopiere fordi underliggende enheter kan endres.
      if (e.getOverordnetDepartement() != null) {
         e.setOverordnetDepartement(new Enhet(e.getOverordnetDepartement()));
      }
   }

   private void leggTilEnheterMedEndring(Enhet e) {
      List<Enhet> l = this.getEndringlisteTilEnhet(e.getIdnum());
      l.add(e);
   }

   private void sjekkRelevanteEnheter() {
      for (Enhet e : this.enheter.values()) {
         if (e.isNedlagt() && e.getTidspunktNedlagt().before(fra)) {
            continue;
         }
         if (util.erEvalEnhet(e)) {
            this.relevanteIdnum.add(e.getIdnum());
         }
      }
   }

   private List<Enhet> getEndringlisteTilEnhet(Integer idnum) {
      List<Enhet> l = enheterMedEndring.get(idnum);
      if (l == null) {
         l = new ArrayList<Enhet>();
         enheterMedEndring.put(idnum, l);
      }
      return l;
   }
}
