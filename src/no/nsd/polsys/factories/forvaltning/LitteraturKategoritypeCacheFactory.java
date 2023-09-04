package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class LitteraturKategoritypeCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   // endringskode --> endringskode.
   private static List<DokCache> data = null;
   private static long oppdatertEndring = 0;

   /**
    * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized List<DokCache> getDokdata(Connection conn) throws SQLException {
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

      data = new ArrayList<DokCache>();

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_litteratur_kategoritype order by kategori";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            DokCache e = new DokCache();
            e.setKode((Integer) rs.getObject("kategori"));
            e.setTekstEntall((String) rs.getObject("tekst"));
            data.add(e);
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
}
