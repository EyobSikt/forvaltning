package no.nsd.polsys.logikk.forvaltning.ansatte;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author hvb
 */
public class FylkeAnsatteLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   // mapping: fylkenr --> fylke
   public Map<Integer, Ansatte> getFylker() throws SQLException {
      Map<Integer, Ansatte> fylker = new HashMap<Integer, Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_ansatte_fylke order by fylkenr";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Ansatte f = new Ansatte();
         f.setFylkenummer((Integer) rad.get("fylkenr"));
         f.setFylke((String) rad.get("fylke"));
         fylker.put(f.getFylkenummer(), f);
      }

      return fylker;
   }

   public String getFylke(Integer fylkenr) throws SQLException {
      Ansatte a = this.getFylker().get(fylkenr);
      return (a != null ? a.getFylke() : null);
   }

   // mapping: fylkenr --> (map: år --> ansatte.)
   public SortedMap<Integer, SortedMap<Integer, Ansatte>> getAnsatteForFylker() throws SQLException {
      SortedMap<Integer, SortedMap<Integer, Ansatte>> map = new TreeMap<Integer, SortedMap<Integer, Ansatte>>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_ver_1, u_kommnr/100 as fylkenr, sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_ver_1, u_kommnr/100 "
              + "order by fylkenr, u_ver_1 desc";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag

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

         Number fylkenr = (Number) rad.get("fylkenr");
         a.setFylkenummer((fylkenr != null ? fylkenr.intValue() : null));

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : null));

         Number alder = (Number) rad.get("alder");
         a.setAlder((alder != null ? alder.intValue() : null));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : null));

         SortedMap<Integer, Ansatte> data = map.get(a.getFylkenummer());
         if (data == null) {
            data = new TreeMap<Integer, Ansatte>();
            map.put(a.getFylkenummer(), data);
         }
         data.put(a.getAar(), a);
      }

      return map;
   }

   public List<Ansatte> getAnsatteForFylker(Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr/100 as fylkenr, "
              + "sum(antall) as ansatte, "
              + " CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_ver_1 = ? and u_ver_1 < 2015 "
              + (statgrl != null ? "and u_stat_grl = " + statgrl : "and u_stat_grl in (2, 3, 4)") + " "
              + "group by u_kommnr/100, u_tjforh_2 "
              + "order by fylkenr ";

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
         Ansatte current = null;

         Number fylkenr = (Number) rad.get("fylkenr");

         if (fylkenr == null) {
            continue;
         }

         for (Ansatte a : liste) {
            if (a.getFylkenummer().equals(fylkenr.intValue())) {
               current = a;
               break;
            }
         }

         if (current == null) {
            current = new Ansatte();
            current.setFylkenummer(fylkenr.intValue());
            liste.add(current);
         }

         int ans = current.getAnsatte() != null ? current.getAnsatte() : 0;
         float verk = current.getAarsverk() != null ? current.getAarsverk() : 0;

         Number ansatte = (Number) rad.get("ansatte");
         if (ansatte != null) {
            current.setAnsatte(ans + ansatte.intValue());
         }

         Number aarsverk = (Number) rad.get("aarsverk");
         if (aarsverk != null) {
            current.setAarsverk(verk + aarsverk.floatValue());
         }
      }

      return liste;
   }

   // kjønn: 1=menn, 2=kvinner, hdkode: H=heltid, D=deltid.
   public SortedMap<Integer, Ansatte> getTotal(Integer fylkenr, Integer kjonn1, Integer kjonn2, String hdkode1, String hdkode2) throws SQLException {
      SortedMap<Integer, Ansatte> total = new TreeMap<Integer, Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_ver_1, sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_kommnr/100 = ? and u_ver_1 < 2015 and (u_kjonn = ? or u_kjonn = ?) and (u_hdkode = ? or u_hdkode = ?) "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_ver_1 "
              + "order by u_ver_1";
      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(fylkenr);
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

   /**
    * Returnerer antall i spesifiserte kommuner i Norge.
    *
    * @return
    * @throws SQLException
    */
   public SortedMap<Integer, Ansatte> getTotalUtenforOslo() throws SQLException {
      SortedMap<Integer, Ansatte> total = new TreeMap<Integer, Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_ver_1, sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_kommnr/100 != ? and u_ver_1 < 2015 and u_kommnr/100 != ? and u_kommnr/100 != ? "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_ver_1 "
              + "order by u_ver_1";
      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(3); // ikke Oslo
      values.add(25); // ikke utlandet
      values.add(99); // ikke uspesifisert

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

   public List<Ansatte> getAnsatteForFylkerGittEtat(String etatkode, Integer aar) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr/100 as fylkenr, "
              + "sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? "
              + "and (u_etatskode = ? or u_etatskode like ?) "
              + "and u_ver_1 = ? and u_ver_1 < 2015 "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_kommnr/100 "
              + "order by fylkenr ";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(etatkode);
      values.add(etatkode + ":%");
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
         Ansatte a = new Ansatte();

         Number fylkenr = (Number) rad.get("fylkenr");
         a.setFylkenummer((fylkenr != null ? fylkenr.intValue() : null));

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : null));

         Number alder = (Number) rad.get("alder");
         a.setAlder((alder != null ? alder.intValue() : null));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : null));

         liste.add(a);
      }

      return liste;
   }

   /**
    *
    * @param etatkode
    * @param aar
    * @param kjonn 1=menn, 2=kvinner.
    * @return
    * @throws SQLException
    */
   public Map<Integer, Ansatte> getAnsatteForFylkerGittEtatGittKjonn(String etatkode, Integer aar, Integer kjonn) throws SQLException {
      Map<Integer, Ansatte> liste = new HashMap<Integer, Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr/100 as fylkenr, "
              + "sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? "
              + "and (u_etatskode = ? or u_etatskode like ?) "
              + "and u_ver_1 = ? "
              + "and u_kjonn = ? and u_ver_1 < 2015 "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_kommnr/100 "
              + "order by fylkenr ";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(etatkode);
      values.add(etatkode + ":%");
      values.add(aar);
      values.add(kjonn);

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

         Number fylkenr = (Number) rad.get("fylkenr");
         a.setFylkenummer((fylkenr != null ? fylkenr.intValue() : null));

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : null));

         Number alder = (Number) rad.get("alder");
         a.setAlder((alder != null ? alder.intValue() : null));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : null));

         liste.put(a.getFylkenummer(), a);
      }

      return liste;
   }

   public List<Ansatte> getAnsatteForFylkerGittDep(Integer depkode, Integer aar) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr/100 as fylkenr, "
              + "sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_etatskode like ? and u_ver_1 = ? and u_ver_1 < 2015 "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_kommnr/100 "
              + "order by fylkenr ";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(depkode + ":%");
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
         Ansatte a = new Ansatte();

         Number fylkenr = (Number) rad.get("fylkenr");
         a.setFylkenummer((fylkenr != null ? fylkenr.intValue() : null));

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : null));

         Number alder = (Number) rad.get("alder");
         a.setAlder((alder != null ? alder.intValue() : null));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : null));

         liste.add(a);
      }

      return liste;
   }
}
