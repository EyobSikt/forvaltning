package no.nsd.polsys.factories.forvaltning.ansatte;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.SortedMap;
import no.nsd.polsys.logikk.forvaltning.ansatte.AnsatteLogikk;
import no.nsd.polsys.modell.forvaltning.Ansatte;

/**
 *
 * @author hvb
 */
public class LandAnsatteCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   private static long oppdatert = 0;
   private static long oppdatert2 = 0;
   private static long oppdatert3 = 0;
   private static long oppdatert4 = 0;
   // mapping: År --> ansatte
   private static SortedMap<Integer, Ansatte> total;
   private static SortedMap<Integer, Ansatte> total2;
   private static SortedMap<Integer, Ansatte> total3;
   private static SortedMap<Integer, Ansatte> total4;

   public static synchronized SortedMap<Integer, Ansatte> getData(Connection conn, Integer lonnskategori) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;

      if (lonnskategori == null) {
         if (total == null) {
            brukCache = false;
         }
         if ((oppdatert + CACHE_GYLDIG) < tid) {
            brukCache = false;
         }

         if (brukCache) {
            return total;
         }

         AnsatteLogikk logikk = new AnsatteLogikk();
         logikk.setConn(conn);
         logikk.beregnAnsatteLand(lonnskategori);
         total = logikk.getTotal();
         return total;
      } else if (lonnskategori.equals(2)) {
         if (total2 == null) {
            brukCache = false;
         }
         if ((oppdatert2 + CACHE_GYLDIG) < tid) {
            brukCache = false;
         }

         if (brukCache) {
            return total2;
         }

         AnsatteLogikk logikk = new AnsatteLogikk();
         logikk.setConn(conn);
         logikk.beregnAnsatteLand(lonnskategori);
         total2 = logikk.getTotal();
         return total2;
      } else if (lonnskategori.equals(3)) {
         if (total3 == null) {
            brukCache = false;
         }
         if ((oppdatert3 + CACHE_GYLDIG) < tid) {
            brukCache = false;
         }

         if (brukCache) {
            return total3;
         }

         AnsatteLogikk logikk = new AnsatteLogikk();
         logikk.setConn(conn);
         logikk.beregnAnsatteLand(lonnskategori);
         total3 = logikk.getTotal();
         return total3;
      } else if (lonnskategori.equals(4)) {
         if (total4 == null) {
            brukCache = false;
         }
         if ((oppdatert4 + CACHE_GYLDIG) < tid) {
            brukCache = false;
         }

         if (brukCache) {
            return total4;
         }

         AnsatteLogikk logikk = new AnsatteLogikk();
         logikk.setConn(conn);
         logikk.beregnAnsatteLand(lonnskategori);
         total4 = logikk.getTotal();
         return total4;
      }

      return null;
   }
}
