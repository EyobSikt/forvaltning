package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hvb
 */
public class LitteraturEnhetCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> antall publikasjoner.
    */
   private static Map<Integer, Integer> litteratur;
   private static long oppdatert = 0;

   public static synchronized Map<Integer, Integer> getLitteratur(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (litteratur == null) {
         brukCache = false;
      }
      if ((oppdatert + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return litteratur;
      }

      litteratur = new HashMap<Integer, Integer>(600, 0.95f);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select idnum, count(pub_id) as antall from t_forvaltning_litteratur_enhet group by idnum";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Integer idnum = (Integer) rs.getObject("idnum");
            /*Integer antall = (Integer) rs.getObject("antall");*/
            Integer antall = (int) ((long)rs.getObject("antall"));
            litteratur.put(idnum, antall);
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

      oppdatert = System.currentTimeMillis();
      return litteratur;
   }
}
