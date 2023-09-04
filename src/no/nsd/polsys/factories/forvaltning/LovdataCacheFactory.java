package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.nsd.polsys.modell.forvaltning.Lov;

/**
 *
 * @author hvb
 */
public class LovdataCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> liste av Lover for gitt idnum.
    */
   private static Map<Integer, List<Lov>> lovdata;
   private static long oppdatert = 0;

   public static synchronized Map<Integer, List<Lov>> getLovdata(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (lovdata == null) {
         brukCache = false;
      }
      if ((oppdatert + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return lovdata;
      }

      lovdata = new HashMap<Integer, List<Lov>>(600, 0.95f);

      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select l.lovnummer, l.url, l.lovnavn, e.idnum "
              + "from t_forvaltning_lovdata as l inner join t_forvaltning_lovdata_enhet as e on l.lovnummer = e.lovnummer "
              + "order by lovnummer";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Lov l = new Lov();
            Integer idnum = (Integer) rs.getObject("idnum");
            l.setNummer((Long) rs.getObject("lovnummer"));
            l.setNavn((String) rs.getObject("lovnavn"));
            l.setUrl((String) rs.getObject("url"));

            List<Lov> liste = lovdata.get(idnum);
            if (liste == null) {
               liste = new ArrayList<Lov>();
               lovdata.put(idnum, liste);
            }
            liste.add(l);
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
      return lovdata;
   }
}
