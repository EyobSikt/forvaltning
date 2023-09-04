package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.AntallEnheterEndring;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class AntallSelskapEndringLogikk {

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

      AntallEnheterEndring data = new AntallEnheterEndring();

      this.enhetLogikk.setEnheter(inn);

      Map<Integer, Enhet> enheter = this.enhetLogikk.enheter;
      this.enhetUtil.setEnheter(enheter);

      data.setFromYear(fra);
      data.setToYear(til);
      data.setNumberAtStart(this.getAntallRelevanteEnheter(enheter));

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

         boolean gammelRelevant = this.erEnhetRelevant(eksisterende);
         boolean gammelDepForv = this.erDepEllerForv(eksisterende);
         boolean gammelSelskap = this.erSelskap(eksisterende);
         boolean nyRelevant = this.erEnhetRelevant(enhet);

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
         nyRelevant = this.erEnhetRelevant(eksisterende);
         boolean nyDepForv = this.erDepEllerForv(eksisterende);
         boolean nySelskap = this.erSelskap(eksisterende);

         // --------------------------------------------------- immigration
         if (!gammelRelevant && gammelDepForv && nyRelevant) { // imm down
            data.setImmigrationDown(data.getImmigrationDown() + 1);
         }
         if (!gammelRelevant && !gammelDepForv && !gammelSelskap && nyRelevant) { // imm up
            data.setImmigrationUp(data.getImmigrationUp() + 1);
         }
         if (!gammelRelevant && !gammelDepForv && gammelSelskap && nyRelevant) { // imm "fra siden"
            data.setImmigration(data.getImmigration() + 1);
         }

         // --------------------------------------------------- emigration
         if (gammelRelevant && !nyRelevant && nyDepForv) { // em up
            data.setEmigrationUp(data.getEmigrationUp() + 1);
         }
         if (gammelRelevant && !nyRelevant && !nyDepForv && !nySelskap) { // em down
            data.setEmigrationDown(data.getEmigrationDown() + 1);
         }
         if (gammelRelevant && !nyRelevant && !nyDepForv && nySelskap) { // em "til siden"
            data.setEmigration(data.getEmigration() + 1);
         }

      }


      data.setNumberAtEnd(this.getAntallRelevanteEnheter(enheter));

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

   private int getAntallRelevanteEnheter(Map<Integer, Enhet> enheter) {
      int antall = 0;

      for (Enhet e : enheter.values()) {
         if (this.erEnhetRelevant(e)) {
            antall++;
         }
      }

      return antall;
   }

   private boolean erEnhetRelevant(Enhet e) {
      // nedlagt
      if (e.isNedlagt()) {
         return false;
      }
      // tilknytning er null.
      if (e.getTilknytningsform() == null) {
         return false;
      }
      int t = e.getTilknytningsform();

      if (this.uttaktype == 0) {
         return this.erEnhetSelskapTotal(e);
      }
      if (this.uttaktype == 1) {
         return this.erEnhetSelskapTilknytning(e, t);
      }
      if (this.uttaktype == 3) {
         return this.erEnhetSelskapCofog(e);
      }

      // skal aldri skje.
      return false;
   }

   private boolean erEnhetSelskapTotal(Enhet e) {
      return this.erSelskap(e);
   }

   private boolean erEnhetSelskapTilknytning(Enhet e, int t) {
      for (int k : this.koder) {
         if (k == t) {
            return true;
         }
      }
      return false;
   }

   private boolean erEnhetSelskapCofog(Enhet e) {
      if (!this.erSelskap(e)) {
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

   private boolean erDepEllerForv(Enhet e) {
      if (e.getTilknytningsform() == null) {
         return false;
      }
      int t = e.getTilknytningsform() / 10;
      return (t == 1 || t == 2 || t == 3);
   }

   private boolean erSelskap(Enhet e) {
      int t = (e.getTilknytningsform() != null ? e.getTilknytningsform().intValue() : 0);
      return (t / 10) == 4;
   }
}
