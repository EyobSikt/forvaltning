package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.KrdEnhet;

/**
 *
 * @author hvb
 */
public class KrdEnhetLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   /**
    * Returnerer alle krd-enheter.
    *
    * @return
    * @throws SQLException
    */
   public List<KrdEnhet> getKrdEnheter() throws SQLException {
      // returneres fra denne metoden.
      List<KrdEnhet> enheter = new ArrayList<KrdEnhet>();
      // Alle kommuner.
      List<Kommune> alleKommuner = KommuneCacheFactory.getKommuner(this.conn);
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_adhoc_krd_flytting_enhet";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         KrdEnhet krdEnhet = new KrdEnhet();
         enheter.add(krdEnhet);

         krdEnhet.setKrdid((Integer) rad.get("krd_id"));
         krdEnhet.setIdnum((Integer) rad.get("idnum"));
         krdEnhet.setVirksomhet((String) rad.get("virksomhet"));
         krdEnhet.setStillinger((Integer) rad.get("stillinger"));
         krdEnhet.setKompetansenivaa((String) rad.get("kompetansenivaa"));
         krdEnhet.setAar((Integer) rad.get("aar"));
         Integer kommunenr = (Integer) rad.get("kommunenr");
         krdEnhet.setSelvstendig((Boolean) rad.get("selvstendig"));
         krdEnhet.setEtablering((String) rad.get("etablering"));
         krdEnhet.setType((String) rad.get("type"));
         krdEnhet.setNedlagt((Boolean) rad.get("nedlagt"));
         krdEnhet.setKommentar((String) rad.get("kommentar"));

         if (kommunenr != null) {
            Kommune k = new Kommune();
            k.setKode(kommunenr);
            Kommune komplett = this.getKommune(alleKommuner, kommunenr, krdEnhet.getAar());
            if (komplett != null) {
               krdEnhet.setKommune(komplett);
            } else {
               krdEnhet.setKommune(k);
            }
         }
      }

      return enheter;
   }

   private Kommune getKommune(List<Kommune> alleKommuner, Integer kommunenr, int aar) {
      for (Kommune k : alleKommuner) {
         if (aar != -1) {
            if (k.getFomAar() != null && k.getFomAar() > aar) {
               continue;
            }
            if (k.getTomAar() != null && k.getTomAar() < aar) {
               continue;
            }
         }
         if (kommunenr.equals(k.getKode())) {
            return k;
         }
      }
      return null;
   }

   public KrdEnhet getKrdEnhet(Integer krdid) throws SQLException {
      List<KrdEnhet> enheter = this.getKrdEnheter();
      for (KrdEnhet e : enheter) {
         if (e.getKrdid().equals(krdid)) {
            return e;
         }
      }
      return null;
   }

   /**
    * Returnerer antall ansatte for gitt krd-enhet.
    *
    * @param krdid
    * @return
    * @throws SQLException
    */
   public List<Integer[]> getAntallAnsatte(Integer krdid) throws SQLException {
      // liste med (år, ansatte).
      List<Integer[]> ansatte = new ArrayList<Integer[]>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_adhoc_krd_flytting_aar where krd_id = ? order by aar";
      List values = new ArrayList();

      values.add(krdid);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer[] perAar = new Integer[2];
         ansatte.add(perAar);

         perAar[0] = (Integer) rad.get("aar");
         perAar[1] = (Integer) rad.get("ansatte");
      }

      return ansatte;
   }
}
