package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hvb
 */
public class RelasjonCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   // endringsid --> liste av idnum.
   private static Map<Integer, List<Integer>> relasjoner = null;
   private static long oppdatertEndring = 0;

   /**
    * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized Map<Integer, List<Integer>> getRelasjoner(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;

      if (relasjoner == null) {
         brukCache = false;
      }
      if ((oppdatertEndring + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }
      if (brukCache) {
         return relasjoner;
      }
      relasjoner = new HashMap<Integer, List<Integer>>(5000, 0.95f);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_relasjon";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Integer id = (Integer) rs.getObject("endringsid");
            Integer idnum = (Integer) rs.getObject("idnum");
            List<Integer> liste = relasjoner.get(id);
            if (liste == null) {
               liste = new ArrayList<Integer>();
               relasjoner.put(id, liste);
            }
            liste.add(idnum);
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
      return relasjoner;
   }
}
