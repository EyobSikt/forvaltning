package no.nsd.polsys.logikk.forvaltning.ansatte;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;

/**
 *
 * @author hvb
 */
public class SysselsatteAnsatteLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   // mapping: år --> sysselsatte.
   public Map<Integer, Integer> getSysselsatteLand() throws SQLException {
      Map<Integer, Integer> map = new HashMap<Integer, Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select aar, sum(sysselsatte) as total "
              + "from t_forvaltning_ansatte_sysselsatte_kumulativ "
              + "group by aar";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer aar = (Integer) rad.get("aar");
         /*Integer total = (Integer) rad.get("total");*/
         Integer total = (int)((long)rad.get("total"));
         map.put(aar, total);
      }
      return map;
   }

   // mapping: fylkenr --> sysselsatte.
   public Map<Integer, Integer> getSysselsatteFylker(Integer aar) throws SQLException {
      Map<Integer, Integer> map = new HashMap<Integer, Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select kommunenr/100 as fylke, sum(sysselsatte) as total "
              + "from t_forvaltning_ansatte_sysselsatte_kumulativ "
              + "where aar = ? "
              + "group by kommunenr/100";
      List values = new ArrayList();

      values.add(aar);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer fylke = (Integer) rad.get("fylke");
         /*Integer total = (Integer) rad.get("total");*/
         Integer total = (int)((long)rad.get("total"));
         map.put(fylke, total);
      }
      return map;
   }

   // mapping: aar --> sysselsatte.
   public Map<Integer, Integer> getSysselsatteFylke(Integer fylkenr) throws SQLException {
      Map<Integer, Integer> map = new HashMap<Integer, Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select aar, sum(sysselsatte) as total "
              + "from t_forvaltning_ansatte_sysselsatte_kumulativ "
              + "where kommunenr/100 = ? "
              + "group by aar";
      List values = new ArrayList();

      values.add(fylkenr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer aar = (Integer) rad.get("aar");
         /*Integer total = (Integer) rad.get("total");*/
         Integer total = (int)((long)rad.get("total"));
         map.put(aar, total);
      }
      return map;
   }

   // mapping: kommunenr --> sysselsatte.
   public Map<Integer, Integer> getSysselsatteFylke(Integer fylkenr, Integer aar) throws SQLException {
      Map<Integer, Integer> map = new HashMap<Integer, Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select kommunenr, sum(sysselsatte) as total "
              + "from t_forvaltning_ansatte_sysselsatte_kumulativ "
              + "where kommunenr/100 = ? and aar = ? "
              + "group by kommunenr";
      List values = new ArrayList();

      values.add(fylkenr);
      values.add(aar);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer kommunenr = (Integer) rad.get("kommunenr");
        /* Integer total = (Integer) rad.get("total");*/
         Integer total = (int)((long)rad.get("total"));
         map.put(kommunenr, total);
      }
      return map;
   }

   // mapping: kommunenr --> sysselsatte.
   public Map<Integer, Integer> getSysselsatteKommuner(Integer aar) throws SQLException {
      Map<Integer, Integer> map = new HashMap<Integer, Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select kommunenr, sum(sysselsatte) as total "
              + "from t_forvaltning_ansatte_sysselsatte_kumulativ "
              + "where aar = ? "
              + "group by kommunenr";
      List values = new ArrayList();

      values.add(aar);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer kommunenr = (Integer) rad.get("kommunenr");
         /*Integer total = (Integer) rad.get("total");*/
         Integer total = (int)((long)rad.get("total"));
         map.put(kommunenr, total);
      }
      return map;
   }

   // mapping: aar --> sysselsatte.
   public Map<Integer, Integer> getSysselsatteKommune(Integer kommunenr) throws SQLException {
      Map<Integer, Integer> map = new HashMap<Integer, Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select aar, sum(sysselsatte) as total "
              + "from t_forvaltning_ansatte_sysselsatte_kumulativ "
              + "where kommunenr = ? "
              + "group by aar";
      List values = new ArrayList();

      values.add(kommunenr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer aar = (Integer) rad.get("aar");
         /*Integer total = (Integer) rad.get("total");*/
         Integer total = (int)((long)rad.get("total"));
         map.put(aar, total);
      }
      return map;
   }
}
