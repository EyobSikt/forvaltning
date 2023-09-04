package no.nsd.polsys.factories.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class TallgruppeCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> liste av tallgrupper/avdeling for gitt idnum.
    */
   private static Map<Integer, List<Tallgruppe>> tallgruppe;
   private static long oppdatertTallgruppe = 0;

   public static synchronized Map<Integer, List<Tallgruppe>> getTallgruppe(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (tallgruppe == null) {
         brukCache = false;
      }
      if ((oppdatertTallgruppe + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }

      if (brukCache) {
         return tallgruppe;
      }

      tallgruppe = new HashMap<Integer, List<Tallgruppe>>(300, 0.95f);

      getDesentralisert(conn); // tallgrupper 21 og 31.
      getSatellitt(conn); // tallgruppe 41.

      oppdatertTallgruppe = System.currentTimeMillis();
      return tallgruppe;
   }

   private static void getDesentralisert(Connection conn) throws SQLException {
      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_tallgruppe_desentralisert";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Tallgruppe g = new Tallgruppe();
            g.setTallgruppe(true);
            g.setIdnum((Integer) rs.getObject("idnum"));
            g.setLpnr((Integer) rs.getObject("lpnr_idnum"));
            g.setTallgruppekode((Integer) rs.getObject("tallgruppe"));
            g.setNavn((String) rs.getObject("navn"));
            g.setEngelskNavn((String) rs.getObject("eng_navn"));
            g.setAntallEnheter((Integer) rs.getObject("antall_enheter"));
            g.setAar((Integer) rs.getObject("aar"));
            g.setEnheterKartlagt((Integer) rs.getObject("enheter_kartlagt"));
            g.setNivaa((Integer) rs.getObject("nivaa"));
            g.setFraTidspunkt((Date) rs.getObject("fra_tidspunkt"));
            g.setTilTidspunkt((Date) rs.getObject("til_tidspunkt"));

            List<Tallgruppe> grupper = tallgruppe.get(g.getIdnum());
            if (grupper == null) {
               grupper = new ArrayList<Tallgruppe>();
               tallgruppe.put(g.getIdnum(), grupper);
            }
            grupper.add(g);
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

   }

   private static void getSatellitt(Connection conn) throws SQLException {
      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_satellitt";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            Tallgruppe g = new Tallgruppe();
            g.setTallgruppe(false);
            g.setIdnum((Integer) rs.getObject("idnum"));
            g.setNavn((String) rs.getObject("navn"));
            g.setEngelskNavn((String) rs.getObject("eng_navn"));
            g.setFraTidspunkt((Date) rs.getObject("fra_tidspunkt"));
            g.setTilTidspunkt((Date) rs.getObject("til_tidspunkt"));
            Kommune k = new Kommune();
            g.setKommune(k);
            k.setKode((Integer) rs.getObject("kommunenr"));
            List<Tallgruppe> grupper = tallgruppe.get(g.getIdnum());
            if (grupper == null) {
               grupper = new ArrayList<Tallgruppe>();
               tallgruppe.put(g.getIdnum(), grupper);
            }
            grupper.add(g);
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

   }
}
