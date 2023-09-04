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
public class NivaaLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<DokCache> getNivaaer() throws SQLException {
      List<DokCache> nivaaer = new ArrayList<DokCache>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_nivaa order by kode";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         DokCache nivaa = new DokCache();

         nivaa.setKode((Integer) rad.get("kode"));

         nivaa.setTekstEntall((String) rad.get("tekst_entall"));
         nivaa.setTekstFlertall((String) rad.get("tekst_flertall"));
         nivaa.setTekstFlertallForkorting((String) rad.get("tekst_flertall_forkorting"));

         nivaa.setTekstEntallEngelsk((String) rad.get("eng_tekst_entall"));
         nivaa.setTekstFlertallEngelsk((String) rad.get("eng_tekst_flertall"));
         nivaa.setTekstFlertallForkortingEngelsk((String) rad.get("eng_tekst_flertall_forkorting"));

         nivaaer.add(nivaa);
      }

      return nivaaer;
   }
}
