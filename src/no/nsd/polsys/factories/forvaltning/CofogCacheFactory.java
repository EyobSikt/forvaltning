package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import no.nsd.polsys.modell.forvaltning.Cofog;

/**
 *
 * @author hvb
 */
public class CofogCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   // cofog-kode --> cofog.
   private static Map<String, Cofog> data = null;
   private static Map<String, Cofog> dataEngelsk = null;
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
   public static synchronized Map<String, Cofog> getDokdata(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (data == null) {
         brukCache = false;
      }
      if ((oppdatertEndring + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return data;
      }

      data = new HashMap<String, Cofog>();

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_cofog";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Cofog c = new Cofog();
            c.setKode((String) rs.getObject("kode"));
            c.setTekst((String) rs.getObject("tekst"));
            data.put(c.getKode(), c);
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
      return data;
   }

   /**
    * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized Map<String, Cofog> getDokdataEngelsk(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (dataEngelsk == null) {
         brukCache = false;
      }
      if ((oppdatertEndringEngelsk + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return dataEngelsk;
      }

      dataEngelsk = new HashMap<String, Cofog>();

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_cofog";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Cofog c = new Cofog();
            c.setKode((String) rs.getObject("kode"));
            c.setTekst((String) rs.getObject("eng_tekst"));
            dataEngelsk.put(c.getKode(), c);
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
      return dataEngelsk;
   }
}
