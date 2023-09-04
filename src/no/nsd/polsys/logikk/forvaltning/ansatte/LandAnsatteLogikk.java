package no.nsd.polsys.logikk.forvaltning.ansatte;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author hvb
 */
public class LandAnsatteLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;

   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   // kjønn: 1=menn, 2=kvinner, hdkode: H=heltid, D=deltid.
   // mapping: år --> ansatte.
   public SortedMap<Integer, Ansatte> getTotal(Integer kjonn1, Integer kjonn2, String hdkode1, String hdkode2) throws SQLException {
      SortedMap<Integer, Ansatte> total = new TreeMap<Integer, Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_ver_1, sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and (u_kjonn = ? or u_kjonn = ?) and (u_hdkode = ? or u_hdkode = ?) "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_ver_1 "
              + "order by u_ver_1";
      List values = new ArrayList();

      values.add(2); // statistikkgrunnlag = 2
      values.add(kjonn1);
      values.add(kjonn2);
      values.add(hdkode1);
      values.add(hdkode2);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         a.setAar((Integer) rad.get("u_ver_1"));

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : null));

         Number alder = (Number) rad.get("alder");
         a.setAlder((alder != null ? alder.intValue() : null));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : null));

         total.put(a.getAar(), a);
      }

      return total;
   }

   // kjønn: 1=menn, 2=kvinner, hdkode: H=heltid, D=deltid.
   // mapping: år --> ansatte.
   public Ansatte getTotalAar(Integer kjonn1, Integer kjonn2, String hdkode1, String hdkode2, Integer aar) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and (u_kjonn = ? or u_kjonn = ?) and (u_hdkode = ? or u_hdkode = ?) and u_ver_1 = ? "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) ";
      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(kjonn1);
      values.add(kjonn2);
      values.add(hdkode1);
      values.add(hdkode2);
      values.add(aar);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      SortedMap rad = rader[0];
      Ansatte a = new Ansatte();

      a.setAar(aar);

      Number ansatte = (Number) rad.get("ansatte");
      a.setAnsatte((ansatte != null ? ansatte.intValue() : null));

      Number alder = (Number) rad.get("alder");
      a.setAlder((alder != null ? alder.intValue() : null));

      Number aarsverk = (Number) rad.get("aarsverk");
      a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : null));

      return a;
   }

   public List<Integer> getAlleAar() throws SQLException {
      List<Integer> aar = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct u_ver_1 from t_forvaltning_ansatte_kumulativ where u_ver_1 < 2015 order by u_ver_1 desc";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (int i = 0; i < rader.length; i++) {
         aar.add((Integer) rader[i].get("u_ver_1"));
      }
      return aar;
   }
}
