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
public class GrunnenhetLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<DokCache> getGrunnenheter() throws SQLException {
      List<DokCache> grunnenheter = new ArrayList<DokCache>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_grunnenhet order by kode";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         DokCache grunnenhet = new DokCache();

         grunnenhet.setKode((Integer) rad.get("kode"));

         grunnenhet.setTekstEntall((String) rad.get("tekst_entall"));
         grunnenhet.setTekstFlertall((String) rad.get("tekst_flertall"));

         grunnenhet.setTekstEntallEngelsk((String) rad.get("eng_tekst_entall"));
         grunnenhet.setTekstFlertallEngelsk((String) rad.get("eng_tekst_flertall"));

         grunnenheter.add(grunnenhet);
      }

      return grunnenheter;
   }
}
