package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class TilknytningsformLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<DokCache> getTilknytningsformer() throws SQLException {
      List<DokCache> tilknytningsformer = new ArrayList<DokCache>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_tilknytningsform order by kode";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         DokCache tilknytningsform = new DokCache();

         tilknytningsform.setKode((Integer) rad.get("kode"));

         tilknytningsform.setTekstEntall((String) rad.get("tekst_entall"));
         tilknytningsform.setTekstFlertall((String) rad.get("tekst_flertall"));
         tilknytningsform.setTekstFlertallForkorting((String) rad.get("tekst_flertall_forkorting"));

         tilknytningsform.setTekstEntallEngelsk((String) rad.get("eng_tekst_entall"));
         tilknytningsform.setTekstFlertallEngelsk((String) rad.get("eng_tekst_flertall"));
         tilknytningsform.setTekstFlertallForkortingEngelsk((String) rad.get("eng_tekst_flertall_forkorting"));

         tilknytningsformer.add(tilknytningsform);
      }

      return tilknytningsformer;
   }
}
