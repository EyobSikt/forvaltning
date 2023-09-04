package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import no.nsd.polsys.modell.forvaltning.Ansatte;

/**
 *
 * @author hvb
 */
public class EtatnavnAnsatteCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   // etatkode --> ulike navn per kode.
   private static Map<String, Ansatte> data = null;
   private static long oppdatertEndring = 0;

   /**
    * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized Map<String, Ansatte> getDokdata(Connection conn) throws SQLException {
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

      data = new HashMap<String, Ansatte>();

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select u_etatskode, u_ver_1, u_etatnavn from t_forvaltning_ansatte_etat "
              + "where u_etatskode is not null "
              + "order by u_etatskode, u_ver_1";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         // mapping: navn --> navn. Brukes for å unngå dobbeltlagring av like navn.
         Map<String, String> allenavn = new HashMap<String, String>(10000);

         while (rs.next()) {
            String s = (String) rs.getObject("u_etatnavn");
            String gammel = allenavn.get(s);
            if (gammel == null) {
               allenavn.put(s, s);
               gammel = s;
            }

            Ansatte a = new Ansatte();
            a.setAar((Integer) rs.getObject("u_ver_1"));
            a.setEtatkode((String) rs.getObject("u_etatskode"));
            a.setEtat(gammel);

            Ansatte eksisterende = data.get(a.getEtatkode());
            if (eksisterende == null) {
               Map<Integer, Ansatte> navn = new HashMap<Integer, Ansatte>();
               a.setNavn(navn);
               data.put(a.getEtatkode(), a);
               eksisterende = a;
            }
            Map<Integer, Ansatte> navn = eksisterende.getNavn();
            navn.put(a.getAar(), a);
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
