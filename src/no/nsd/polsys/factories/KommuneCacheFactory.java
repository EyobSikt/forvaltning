package no.nsd.polsys.factories;

import no.nsd.polsys.modell.Kommune;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvb
 */
public class KommuneCacheFactory {

   private static List<Kommune> kommuner = null;
   private static long oppdatertEndring = 0;
   // 48 timer * 60 min * 60 sek * 1000 millis.
   //private static final long gyldig = 2L * 60L * 60L * 1000L;
   private static final long gyldig = 0;

   /**
    * Returnerer alle kommuner fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized List<Kommune> getKommuner(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (kommuner == null) {
         brukCache = false;
      }
      if ((oppdatertEndring + gyldig) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return kommuner;
      }

      kommuner = new ArrayList<Kommune>(1000);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_felles_kommune order by kode, tom_aar desc";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Kommune k = new Kommune();
            k.setKode((Integer) rs.getObject("kode"));
            k.setTekstEntall((String) rs.getObject("tekst_entall"));
            k.setTekstEntallEngelsk((String) rs.getObject("eng_tekst_entall"));
            k.setFomAar((Integer) rs.getObject("fom_aar"));
            k.setTomAar((Integer) rs.getObject("tom_aar"));
            kommuner.add(k);
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
      return kommuner;
   }
}
