package no.nsd.polsys.factories.forvaltning;

import no.nsd.polsys.modell.forvaltning.Arkivportalen;

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
 * @author Eyob
 */
public class ArkivportalenCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> liste av årsmeldinger for gitt idnum.
    */
   private static Map<Integer, List<Arkivportalen>> arkivportalen;
   private static long oppdatert = 0;

   public static synchronized Map<Integer, List<Arkivportalen>> getArkivportalen(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (arkivportalen == null) {
         brukCache = false;
      }
      if ((oppdatert + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return arkivportalen;
      }

      arkivportalen = new HashMap<Integer, List<Arkivportalen>>(600, 0.95f);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_arkivportalen where navn is not null or forvaltningsomrade is not null order by idnum, tidsrom";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Arkivportalen a = new Arkivportalen();
            a.setId((Integer) rs.getObject("id"));
            a.setIdnum((Integer) rs.getObject("idnum"));
            a.setTidsrom((String) rs.getObject("tidsrom"));
            a.setNavn((String) rs.getObject("navn"));
            a.setForvaltningsomrade((String) rs.getObject("forvaltningsomrade"));
            a.setUrl((String) rs.getObject("url"));

            List<Arkivportalen> liste = arkivportalen.get(a.getIdnum());
            if (liste == null) {
               liste = new ArrayList<Arkivportalen>();
               arkivportalen.put(a.getIdnum(), liste);
            }
            liste.add(a);
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
      return arkivportalen;
   }
}
