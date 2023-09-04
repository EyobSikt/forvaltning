package no.nsd.polsys.factories.forvaltning;

import no.nsd.polsys.modell.forvaltning.Instruks;

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
 * @author Sikt
 */
public class InstruksCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> liste av instrukser for gitt idnum.
    */
   private static Map<Integer, List<Instruks>> instruks;
   private static long oppdatert = 0;

   public static synchronized Map<Integer, List<Instruks>> getInstrukser(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (instruks == null) {
         brukCache = false;
      }
      if ((oppdatert + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return instruks;
      }

      instruks = new HashMap<Integer, List<Instruks>>(600, 0.95f);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_instruks where aarsinstruks is not null or eng_aarsinstruks is not null order by idnum, aar";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Instruks a = new Instruks();
            a.setId((Integer) rs.getObject("id"));
            a.setIdnum((Integer) rs.getObject("idnum"));
            a.setAar((Integer) rs.getObject("aar"));
            a.setInstruks((String) rs.getObject("aarsinstruks"));
            a.setEngelskInstruks((String) rs.getObject("eng_aarsinstruks"));
            a.setSisteUrl((String) rs.getObject("siste_url"));

            List<Instruks> liste = instruks.get(a.getIdnum());
            if (liste == null) {
               liste = new ArrayList<Instruks>();
               instruks.put(a.getIdnum(), liste);
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
      return instruks;
   }
}
