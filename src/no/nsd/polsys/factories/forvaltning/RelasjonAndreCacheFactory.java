package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class RelasjonAndreCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   // endringsid --> liste av enheter (relasjonenheter).
   private static Map<Integer, List<Enhet>> relasjoner = null;
   private static long oppdatertTidspunkt = 0;

   /**
    * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
    * fremdeles er gyldig.
    *
    * @param conn
    * @return
    * @throws SQLException
    */
   public static synchronized Map<Integer, List<Enhet>> getRelasjoner(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;

      if (relasjoner == null) {
         brukCache = false;
      }
      if ((oppdatertTidspunkt + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }
      if (brukCache) {
         return relasjoner;
      }
      relasjoner = new HashMap<Integer, List<Enhet>>();

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select r.endringsid, e.id, e.navn, e.eng_navn "
              + "from t_forvaltning_relasjon_andre as r inner join t_forvaltning_relasjon_andre_enhet as e on r.id = e.id";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Integer id = (Integer) rs.getObject("endringsid");
            Enhet e = new Enhet();
            e.setIdnum((Integer) rs.getObject("id"));
            e.setLangtNavn((String) rs.getObject("navn"));
            e.setEngelskLangtNavn((String) rs.getObject("eng_navn"));
            List<Enhet> liste = relasjoner.get(id);
            if (liste == null) {
               liste = new ArrayList<Enhet>();
               relasjoner.put(id, liste);
            }
            liste.add(e);
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

      oppdatertTidspunkt = System.currentTimeMillis();
      return relasjoner;
   }
}
