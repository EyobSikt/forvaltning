package no.nsd.polsys.factories.forvaltning;

import no.nsd.polsys.modell.forvaltning.Tildelingsbrev;

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
public class TildelingsbrevCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> liste av årsmeldinger for gitt idnum.
    */
   private static Map<Integer, List<Tildelingsbrev>> tildelingsbrev;
   private static long oppdatert = 0;

   public static synchronized Map<Integer, List<Tildelingsbrev>> getTildelingsbrev(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (tildelingsbrev == null) {
         brukCache = false;
      }
      if ((oppdatert + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return tildelingsbrev;
      }

      tildelingsbrev = new HashMap<Integer, List<Tildelingsbrev>>(600, 0.95f);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_tildelingsbrev where tildelingsbrev is not null or eng_tildelingsbrev is not null order by idnum, aar";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Tildelingsbrev a = new Tildelingsbrev();
            a.setId((Integer) rs.getObject("id"));
            a.setIdnum((Integer) rs.getObject("idnum"));
            a.setAar((Integer) rs.getObject("aar"));
            a.setTildelingsbrev((String) rs.getObject("tildelingsbrev"));
            a.setEngelskTildelingsbrev((String) rs.getObject("eng_tildelingsbrev"));
            a.setSisteUrl((String) rs.getObject("siste_url"));

            List<Tildelingsbrev> liste = tildelingsbrev.get(a.getIdnum());
            if (liste == null) {
               liste = new ArrayList<Tildelingsbrev>();
               tildelingsbrev.put(a.getIdnum(), liste);
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
      return tildelingsbrev;
   }
}
