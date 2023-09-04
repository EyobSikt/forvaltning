package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class EndringskodeCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   // endringskode --> endringskode.
   private static Map<Integer, DokCache> endringer = null;
   private static Map<Integer, DokCache> endringerEngelsk = null;
   private static long oppdatertEndring = 0;
   private static long oppdatertEndringEngelsk = 0;

   /**
    * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized Map<Integer, DokCache> getDokdata(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (endringer == null) {
         brukCache = false;
      }
      if ((oppdatertEndring + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return endringer;
      }

      endringer = new HashMap<Integer, DokCache>();

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_endringskode";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            DokCache e = new DokCache();
            e.setKode((Integer) rs.getObject("kode"));
            e.setTekstEntall((String) rs.getObject("tekst_entall"));
            e.setTekstFlertall((String) rs.getObject("tekst_flertall"));
            e.setKortDokumentasjon((String) rs.getObject("dokumentasjon_kort"));
            e.setRelasjontekst((String) rs.getObject("relasjontekst"));
            endringer.put(e.getKode(), e);
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
            }
         }
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
            }
         }
      }

      oppdatertEndring = System.currentTimeMillis();
      return endringer;
   }

   /**
    * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized Map<Integer, DokCache> getDokdataEngelsk(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (endringerEngelsk == null) {
         brukCache = false;
      }
      if ((oppdatertEndringEngelsk + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return endringerEngelsk;
      }

      endringerEngelsk = new HashMap<Integer, DokCache>();

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_endringskode";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            DokCache e = new DokCache();
            e.setKode((Integer) rs.getObject("kode"));
            e.setTekstEntall((String) rs.getObject("eng_tekst_entall"));
            e.setTekstFlertall((String) rs.getObject("eng_tekst_flertall"));
            e.setKortDokumentasjon((String) rs.getObject("eng_dokumentasjon_kort"));
            e.setRelasjontekst((String) rs.getObject("eng_relasjontekst"));
            endringerEngelsk.put(e.getKode(), e);
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
            }
         }
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
            }
         }
      }

      oppdatertEndringEngelsk = System.currentTimeMillis();
      return endringerEngelsk;
   }
}
