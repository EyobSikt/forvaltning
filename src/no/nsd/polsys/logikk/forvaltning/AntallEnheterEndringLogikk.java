package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.AntallEnheterEndring;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class AntallEnheterEndringLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;
   private EnhetLogikk enhetLogikk = new EnhetLogikk();
   private EnhetUtilLogikk enhetUtil = new EnhetUtilLogikk();
   // 0=total, 1=tilknytningsform, 2=grunnenhet, 3=COFOG.
   private int uttaktype = 0;
   private int[] koder;
   private Map<Date, Set<Integer>> gruppemapping;

   
   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
      this.enhetLogikk.setConn(conn);
   }

   public void brukEngelsk() {
      this.engelsk = true;
      this.enhetLogikk.brukEngelsk();
   }

   // 0=total, 1=tilknytningsform, 2=grunnenhet, 3=COFOG.
   public List<AntallEnheterEndring> getEnheter(int uttaktype, int... kode) throws SQLException {

      this.uttaktype = uttaktype;
      this.koder = kode;

      List<AntallEnheterEndring> liste = new ArrayList<AntallEnheterEndring>();

      liste.add(this.getPeriode(1947, 1970));
      liste.add(this.getPeriode(1971, 1981));
      liste.add(this.getPeriode(1982, 1990));
      liste.add(this.getPeriode(1991, 1996));
      liste.add(this.getPeriode(1997, 2001));
      liste.add(this.getPeriode(2002, 2005));
      liste.add(this.getPeriode(2006, 2012));

      return liste;
   }

   // Denne metoden preprosesserer listen over endringer og finner relevante grupper for gitt dato.
   public void beregnRelevanteGrupper() throws SQLException {
      this.gruppemapping = new HashMap<Date, Set<Integer>>();

      // mapping idnum --> enhet.
      Map<Integer, Enhet> enheter = new HashMap<Integer, Enhet>(4000, 0.95f);
      // alle endringer i databasen.
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);

      this.enhetUtil.setEnheter(enheter);

      Date dato = null;

      // for-løkken finner aktuelle enheter.
      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet(endringCache);
         if (dato == null) {
            dato = enhet.getTidspunkt();
         }
         if (enhet.getTidspunkt().after(dato)) {
            this.oppdaterGrupper(dato, enheter.values());
            dato = enhet.getTidspunkt();
         }
         this.oppdaterEksisterende(enhet, enheter);
      }
      this.oppdaterGrupper(dato, enheter.values());
   }

   private void oppdaterEksisterende(Enhet enhet, Map<Integer, Enhet> enheter) {
      Enhet eksisterende = enheter.get(enhet.getIdnum());
      if (eksisterende == null) {
         enheter.put(enhet.getIdnum(), enhet);
         eksisterende = enhet;
      }
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
      if (enhet.getKommunenummer() != null) {
         eksisterende.setKommunenummer(enhet.getKommunenummer());
         eksisterende.setFylkenummer(enhet.getKommunenummer() / 100);
      }
   }

   private void oppdaterGrupper(Date dato, Collection<Enhet> enheter) {
      Set<Integer> set = new HashSet<Integer>();
      this.gruppemapping.put(dato, set);
      for (Enhet e : enheter) {
         if (this.erRiktigNivaa(e)) {
            set.add(e.getIdnum());
         }
      }
   }

   private boolean erRiktigNivaa(Enhet e) {
      if (e.getGrunnenhet() == null) {
         return false;
      }
      if (e.getGrunnenhet().equals(20)) {
         int i = this.enhetUtil.getHierarkiskNivaaUnderDep(e);
         return (i == 1);
      }
      return false;
   }

   private AntallEnheterEndring getPeriode(int fra, int til) throws SQLException {
      Dato d = null;

      d = new Dato(fra - 1, 12, 31);
      Date inn = d.getDate();

      d = new Dato(fra, 1, 1);
      Date fom = d.getDate();

      d = new Dato(til, 12, 31);
      Date tom = d.getDate();


      // unntak for 1947
      if (fra == 1947) {
         d = new Dato(fra, 1, 1);
         inn = d.getDate();

         d = new Dato(fra, 1, 2);
         fom = d.getDate();
      }

      if (til == 2012) {
         d = new Dato(til, 1, 1);
         tom = d.getDate();
      }


      AntallEnheterEndring data = new AntallEnheterEndring();

      this.enhetLogikk.setEnheter(inn);

      Map<Integer, Enhet> enheter = this.enhetLogikk.enheter;
      this.enhetUtil.setEnheter(enheter);

      data.setFromYear(fra);
      data.setToYear(til);
      data.setNumberAtStart(this.getAntallRelevanteEnheter(enheter, inn));

      // alle endringer i databasen.
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);

      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet(endringCache);
         if (this.engelsk) {
            enhet.setLangtNavn(enhet.getEngelskLangtNavn());
         }


         Enhet eksisterende = enheter.get(enhet.getIdnum());
         if (eksisterende == null) {
            enheter.put(enhet.getIdnum(), enhet);
            eksisterende = enhet;
         }

         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().before(fom)) {
            this.oppdaterVerdier(eksisterende, enhet);
            continue;
         }

         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().after(tom)) {
            break;
         }


         boolean gammelRelevant = this.erEnhetRelevant(eksisterende, eksisterende.getTidspunkt());
         boolean gammelDep = this.erIdepartement(eksisterende);
         boolean gammelForv = this.erForvaltning(eksisterende);
         boolean nyRelevant = this.erEnhetRelevant(enhet, enhet.getTidspunkt());

         int kode = enhet.getEndringskode().intValue();

         // -------------------------------------------------- terminations
         if (gammelRelevant && kode == 312) {
            data.setEmigrationDown(data.getEmigrationDown() + 1);
         } else if (gammelRelevant && (kode / 100) == 3) {
            data.setTerminations(data.getTerminations() + 1);
         }


         // ----------------------------------------------------- foundings
         if (nyRelevant && (kode / 100) == 1) {
            data.setFoundings(data.getFoundings() + 1);
         }


         this.oppdaterVerdier(eksisterende, enhet);


         if ((kode / 100) != 2) {
            continue;
         }
         nyRelevant = this.erEnhetRelevant(eksisterende, eksisterende.getTidspunkt());
         boolean nyDep = this.erIdepartement(eksisterende);
         boolean nyForv = this.erForvaltning(eksisterende);

         // --------------------------------------------------- immigration
         if (!gammelRelevant && gammelDep && nyRelevant) { // imm down
            data.setImmigrationDown(data.getImmigrationDown() + 1);
         } else if (!gammelRelevant && !gammelDep && !gammelForv && nyRelevant) { // imm up
            data.setImmigrationUp(data.getImmigrationUp() + 1);
         } else if (!gammelRelevant && !gammelDep && gammelForv && nyRelevant) { // imm "fra siden"
            data.setImmigration(data.getImmigration() + 1);
         } // --------------------------------------------------- emigration
         else if (gammelRelevant && !nyRelevant && nyDep) { // em up
            data.setEmigrationUp(data.getEmigrationUp() + 1);
         } else if (gammelRelevant && !nyRelevant && !nyDep && !nyForv) { // em down
            data.setEmigrationDown(data.getEmigrationDown() + 1);
         } else if (gammelRelevant && !nyRelevant && !nyDep && nyForv) { // em "til siden"
            data.setEmigration(data.getEmigration() + 1);
         }

      }

      data.setNumberAtEnd(this.getAntallRelevanteEnheter(enheter, tom));

      return data;
   }

   private void oppdaterVerdier(Enhet eksisterende, Enhet enhet) {
      eksisterende.setTidspunkt(enhet.getTidspunkt());
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
      if (enhet.getKommunenummer() != null) {
         eksisterende.setKommunenummer(enhet.getKommunenummer());
      }

   }

   private int getAntallRelevanteEnheter(Map<Integer, Enhet> enheter, Date dato) {
      int antall = 0;

      for (Enhet e : enheter.values()) {
         if (this.erEnhetRelevant(e, dato)) {
            antall++;
         }
      }

      return antall;
   }

   private boolean erEnhetRelevant(Enhet e, Date dato) {
      // tar ikke med Børser 3600 og Norges Bank 1614
      if (e.getIdnum().equals(3600) || e.getIdnum().equals(1614)) {
         return false;
      }
      // nedlagt
      if (e.isNedlagt()) {
         return false;
      }
      // grunntype er null.
      if (e.getGrunnenhet() == null) {
         return false;
      }
      int g = e.getGrunnenhet();
      // tilknytning er null.
      if (e.getTilknytningsform() == null) {
         return false;
      }
      int t = e.getTilknytningsform();

      if (g == 20) {
         Set<Integer> set = this.gruppemapping.get(dato);
         if (set == null || !set.contains(e.getIdnum())) {
            return false;
         }
      }

      if (this.uttaktype == 0) {
         return this.erEnhetForvaltningTotal(e);
      }
      if (this.uttaktype == 1) {
         return this.erEnhetForvaltningTilknytning(e, t);
      }
      if (this.uttaktype == 2) {
         return this.erEnhetForvaltningGrunnenhet(e, g);
      }
      if (this.uttaktype == 3) {
         return this.erEnhetForvaltningCofog(e);
      }

      // skal aldri skje.
      return false;
   }

   private boolean erEnhetForvaltningTotal(Enhet e) {
      if (!this.erRelevaltGrunnenhet(e)) {
         return false;
      }
      return this.erForvaltning(e);
   }

   private boolean erEnhetForvaltningTilknytning(Enhet e, int t) {
      if (!this.erRelevaltGrunnenhet(e)) {
         return false;
      }
      for (int k : this.koder) {
         if (k == t) {
            return true;
         }
      }
      return false;
   }

   private boolean erEnhetForvaltningGrunnenhet(Enhet e, int g) {
      if (!this.erForvaltning(e)) {
         return false;
      }
      for (int k : this.koder) {
         if (k == g) {
            return true;
         }
      }
      return false;
   }

   private boolean erEnhetForvaltningCofog(Enhet e) {
      if (!this.erRelevaltGrunnenhet(e)) {
         return false;
      }
      if (!this.erForvaltning(e)) {
         return false;
      }

      if (e.getCofog() == null) {
         return false;
      }
      int c = Integer.parseInt(e.getCofog());
      for (int k : this.koder) {
         if (k == c) {
            return true;
         }
      }

      return false;
   }

   private boolean erIdepartement(Enhet e) {
      return (e.getTilknytningsform() != null && e.getTilknytningsform().equals(10));
   }

   private boolean erForvaltning(Enhet e) {
      int t = (e.getTilknytningsform() != null ? e.getTilknytningsform().intValue() : 0);
      return (t == 20 || t == 31 || t == 32 || t == 33 || t == 35);
   }

   private boolean erRelevaltGrunnenhet(Enhet e) {
      int g = (e.getGrunnenhet() != null ? e.getGrunnenhet().intValue() : 0);
      return (g == 0 || g == 11 || g == 20);
   }
}
