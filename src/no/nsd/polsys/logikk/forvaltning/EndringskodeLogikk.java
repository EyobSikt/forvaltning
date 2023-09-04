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
public class EndringskodeLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<DokCache> getEndringskoder() throws SQLException {
      List<DokCache> endringskoder = new ArrayList<DokCache>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_endringskode order by kode";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         DokCache endringskode = new DokCache();

         endringskode.setKode((Integer) rad.get("kode"));

         endringskode.setTekstEntall((String) rad.get("tekst_entall"));
         endringskode.setTekstFlertall((String) rad.get("tekst_flertall"));
         endringskode.setKortDokumentasjon((String) rad.get("dokumentasjon_kort"));
         endringskode.setRelasjontekst((String) rad.get("relasjontekst"));

         endringskode.setTekstEntallEngelsk((String) rad.get("eng_tekst_entall"));
         endringskode.setTekstFlertallEngelsk((String) rad.get("eng_tekst_flertall"));
         endringskode.setKortDokumentasjonEngelsk((String) rad.get("eng_dokumentasjon_kort"));
         endringskode.setRelasjontekstEngelsk((String) rad.get("eng_relasjontekst"));

         endringskoder.add(endringskode);
      }

      return endringskoder;
   }
}
