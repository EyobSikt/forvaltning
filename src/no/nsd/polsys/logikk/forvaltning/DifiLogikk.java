package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import no.nsd.polsys.comparators.forvaltning.DifiComparator;
import no.nsd.polsys.modell.forvaltning.Difi;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class DifiLogikk {

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

   
   
   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
      this.enhetLogikk.setConn(conn);
   }

   public void brukEngelsk() {
      this.engelsk = true;
      this.enhetLogikk.brukEngelsk();
   }

   /**
    * Returnerer statistikk for DIFI.
    *
    * @param dato
    * @return
    * @throws SQLException
    */
   public Difi getDifiStat(Date dato) throws SQLException {
      // mapping idnum --> enhet.
      this.enhetLogikk.setEnheter(dato);

      EnhetUtilLogikk enhetUtil = new EnhetUtilLogikk();
      enhetUtil.setEnheter(this.enhetLogikk.enheter);

      Difi difi = new Difi();
      List<Enhet> liste = new ArrayList<Enhet>(4000);
      // Statistikk grunnenheter: mapping: grunnenhet --> antall enheter.
      Map<String, Integer> statGrunnenhet = new HashMap<String, Integer>();
      // Statistikk tilknytningsformer: mapping: tilknytningsform --> antall enheter.
      Map<String, Integer> statTilknytning = new HashMap<String, Integer>();

      Map<Integer, Integer> kommuner = this.enhetLogikk.getKommuneTilEnheter();

      difi.setStatGrunnenhet(statGrunnenhet);
      difi.setStatTilknytning(statTilknytning);
      difi.setEnheter(liste);

      for (Enhet e : this.enhetLogikk.enheter.values()) {
         if (e.isNedlagt() && e.getTidspunktNedlagt().before(dato)) {
            continue;
         }
         if (this.erUnntatt(e)) {
            continue;
         }
         if (enhetUtil.erTomEnhet(e)) {
            continue;
         }
         if (enhetUtil.erInternDepEnhet(e)) {
            continue;
         }

         if (e.getKommunenummer() != null) {
            // Henter rett kommunenr hvis aktuelt
            Integer kommunenr = kommuner.get(e.getIdnum());
            if (kommunenr != null) {
               e.setKommunenummer(kommunenr);
            }

            e.setFylkenummer(e.getKommunenummer() / 100);
         }

         String t = (e.getTilknytningsform() != null ? e.getTilknytningsform().toString() : "");
         String g = (e.getGrunnenhet() != null ? e.getGrunnenhet().toString() : "");

         if (enhetUtil.erDepartement(e)) {
            this.plussEn(statTilknytning, t);
            continue;
         }

         if (this.erRelevantTilknytning(e)) {
            if (g.equals("21")) {
               if (enhetUtil.erLokalEtatEnhet(e)) {
                  g = "21E";
               } else {
                  g = "21G";
               }
            }

            this.plussEn(statGrunnenhet, g);
         }

         if (!g.equals("21E")) {
            this.plussEn(statTilknytning, t);
            if (this.erRelevantTilknytning(e)) {
               enhetUtil.setOverordnetDepartement(e);
               liste.add(e);
            }
         }

      }

      Collections.sort(liste, new DifiComparator());

      return difi;
   }

   // avgjør om enheten er unntatt statistikken.
   private boolean erUnntatt(Enhet e) {
      int idnum = e.getIdnum();
      return (idnum == 10900 || idnum == 5813 || idnum == 3600 || idnum == 10901 || idnum == 5615 || idnum == 5635);
   }

   private boolean erRelevantTilknytning(Enhet e) {
      if (e.getTilknytningsform() == null) {
         return false;
      }
      int t = e.getTilknytningsform();
      return (t == 20 || t == 31 || t == 32 || t == 33 || t == 35);
   }

   private void plussEn(Map<String, Integer> map, String key) {
      Integer i = map.get(key);
      if (i == null) {
         i = 0;
      }
      map.put(key, i + 1);
   }
}
