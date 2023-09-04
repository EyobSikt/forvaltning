package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.nsd.polsys.modell.forvaltning.Aarsmelding;

/**
 *
 * @author hvb
 */
public class AarsmeldingCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> liste av årsmeldinger for gitt idnum.
    */
   private static Map<Integer, List<Aarsmelding>> aarsmelding;
   private static long oppdatert = 0;

   public static synchronized Map<Integer, List<Aarsmelding>> getAarsmeldinger(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (aarsmelding == null) {
         brukCache = false;
      }
      if ((oppdatert + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return aarsmelding;
      }

      aarsmelding = new HashMap<Integer, List<Aarsmelding>>(600, 0.95f);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_aarsmelding where aarsmeld is not null or eng_aarsmeld is not null order by idnum, aar";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Aarsmelding a = new Aarsmelding();
            a.setId((Integer) rs.getObject("id"));
            a.setIdnum((Integer) rs.getObject("idnum"));
            a.setAar((Integer) rs.getObject("aar"));
            a.setAarsmelding((String) rs.getObject("aarsmeld"));
            a.setEngelskAarsmelding((String) rs.getObject("eng_aarsmeld"));
            a.setSisteUrl((String) rs.getObject("siste_url"));

            List<Aarsmelding> liste = aarsmelding.get(a.getIdnum());
            if (liste == null) {
               liste = new ArrayList<Aarsmelding>();
               aarsmelding.put(a.getIdnum(), liste);
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
      return aarsmelding;
   }
}
