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
public class AnsatteLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   // mapping: år --> ansatte
   private SortedMap<Integer, Ansatte> total;
   private SortedMap<Integer, Ansatte> mennTotal;
   private SortedMap<Integer, Ansatte> kvinnerTotal;
   private SortedMap<Integer, Ansatte> heltid;
   private SortedMap<Integer, Ansatte> mennHeltid;
   private SortedMap<Integer, Ansatte> kvinnerHeltid;
   private SortedMap<Integer, Ansatte> deltid;
   private SortedMap<Integer, Ansatte> mennDeltid;
   private SortedMap<Integer, Ansatte> kvinnerDeltid;



   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public SortedMap<Integer, Ansatte> getDeltid() {
      return deltid;
   }

   public SortedMap<Integer, Ansatte> getHeltid() {
      return heltid;
   }

   public SortedMap<Integer, Ansatte> getKvinnerDeltid() {
      return kvinnerDeltid;
   }

   public SortedMap<Integer, Ansatte> getKvinnerHeltid() {
      return kvinnerHeltid;
   }

   public SortedMap<Integer, Ansatte> getKvinnerTotal() {
      return kvinnerTotal;
   }

   public SortedMap<Integer, Ansatte> getMennDeltid() {
      return mennDeltid;
   }

   public SortedMap<Integer, Ansatte> getMennHeltid() {
      return mennHeltid;
   }

   public SortedMap<Integer, Ansatte> getMennTotal() {
      return mennTotal;
   }

   public SortedMap<Integer, Ansatte> getTotal() {
      return total;
   }

   /**
    * Returnerer alle ansattekoblinger til gitt enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public List<Ansatte> getAnsatteKoblingTilEnhet(Integer idnum) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_enhet_ansatte_kobling where idnum = ? order by ansatte_etatskode";
      List values = new ArrayList();

      values.add(idnum);

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
         liste.add(a);

         a.setId((Integer) rad.get("id"));
         a.setEtatkode((String) rad.get("ansatte_etatskode"));
         a.setFraAar((Integer) rad.get("fra_aar"));
         a.setTilAar((Integer) rad.get("til_aar"));
         a.setDok((String) rad.get("dok"));
      }

      return liste;
   }

   public void beregnAnsatteLand(Integer statgrl) throws SQLException {
      this.beregnAnsatte(statgrl, null);
   }

   public void beregnAnsatteTilFylke(Integer fylkenr, Integer statgrl) throws SQLException {
      String condition = "u_kommnr/100 = " + fylkenr;
      this.beregnAnsatte(statgrl, condition);
   }

   public void beregnAnsatteTilKommune(Integer kommunenr, Integer statgrl) throws SQLException {
      String condition = "u_kommnr = " + kommunenr;
      this.beregnAnsatte(statgrl, condition);
   }

   public void beregnAnsatteTilEtat(String etatkode, Integer statgrl, Integer aar) throws SQLException {
      String condition = "u_ver_1 = " + aar + " and (u_etatskode = '" + etatkode + "' or u_etatskode like '" + etatkode + ":%')";
      this.beregnAnsatte(statgrl, condition);
   }

   public void beregnAnsatteTilEtatkode(String etatkode, Integer statgrl) throws SQLException {
      String condition = "(u_etatskode = '" + etatkode + "' or u_etatskode like '" + etatkode + ":%')";
      this.beregnAnsatte(statgrl, condition);
   }

   public void beregnAnsatteTilAndreFylker(Integer statgrl) throws SQLException {
      String condition = "u_kommnr/100 != 3 and u_kommnr/100 != 25 and u_kommnr/100 != 99";
      this.beregnAnsatte(statgrl, condition);
   }

   /**
    * Beregner ansatte til gitt enhet for gitt statistikkgrunnlag.
    *
    * @param idnum - gitt enhet.
    * @param statgrl - statistikkgrunnlaget, null betyr alle.
    * @throws SQLException
    */
   public void beregnAnsatteTilEnhet(Integer idnum, Integer statgrl) throws SQLException {
      List<Ansatte> liste = this.getAnsatteKoblingTilEnhet(idnum);

      if (liste == null || liste.isEmpty()) {
         return;
      }

      String condition = "(";

      for (int i = 0; i < liste.size(); i++) {
         Ansatte a = liste.get(i);
         if (i > 0) {
            condition += " or ";
         }
         condition += " (u_etatskode like '" + a.getEtatkode() + "'";
         condition += (a.getFraAar() != null ? " and u_ver_1 >= " + a.getFraAar() : "");
         condition += (a.getTilAar() != null ? " and u_ver_1 <= " + a.getTilAar() : "");
         condition += ")";
      }

      condition += ")";

      this.beregnAnsatte(statgrl, condition);
   }

   private void beregnAnsatte(Integer statgrl, String condition) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;

      String sqlSelect = "select "
              + "u_ver_1, u_kjonn, 'H' as hd, "
              + "sum(antall) as ansatte, "
              + "sum(u_delpros*antall)/100.0 as aarsverk, "
              + "sum(u_alder*antall) as alder "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where "
              + (condition != null ? condition + " and " : "")
              + "u_stat_grl" + (statgrl != null ? " = " + statgrl : " in (2, 3, 4)") + " "
              + "and u_delpros >= 100 and u_ver_1 < 2015 "
              + "and (u_tjforh_2 is null or u_tjforh_2 != 'T') "
              + "group by u_ver_1, u_kjonn "
              + "union "
              + "select "
              + "u_ver_1, u_kjonn, 'D' as hd, "
              + "sum(antall) as ansatte, "
              + "sum(u_delpros*antall)/100.0 as aarsverk, "
              + "sum(u_alder*antall) as alder "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where "
              + (condition != null ? condition + " and " : "")
              + "u_stat_grl" + (statgrl != null ? " = " + statgrl : " in (2, 3, 4)") + " "
              + "and u_delpros < 100 and u_ver_1 < 2015 "
              + "and (u_tjforh_2 is null or u_tjforh_2 != 'T') "
              + "group by u_ver_1, u_kjonn "
              + "union "
              + "select "
              + "u_ver_1, u_kjonn, 'D' as hd, "
              + "sum(antall) as ansatte, "
              + "sum(u_delpros*antall)/162.0 as aarsverk, "
              + "sum(u_alder*antall) as alder "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where "
              + (condition != null ? condition + " and " : "")
              + "u_stat_grl" + (statgrl != null ? " = " + statgrl : " in (2, 3, 4)") + " "
              + "and (u_tjforh_2 = 'T') and u_ver_1 < 2015 "
              + "group by u_ver_1, u_kjonn ";


      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return;
      }

      this.opprettNyeMap();

      for (SortedMap rad : rader) {
         Integer aar = (Integer) rad.get("u_ver_1");
         Integer kjonn = (Integer) rad.get("u_kjonn");
         String hd = (String) rad.get("hd");

         Number ansatte = (Number) rad.get("ansatte");
         int ans = (ansatte != null ? ansatte.intValue() : 0);

         Number aarsverk = (Number) rad.get("aarsverk");
         float verk = (aarsverk != null ? aarsverk.floatValue() : 0);

         Number alder = (Number) rad.get("alder");
         int ald = (alder != null ? alder.intValue() : 0);

         this.updateMap(this.total, aar, ans, verk, ald);
         if (kjonn != null && kjonn.equals(1)) {
            this.updateMap(this.mennTotal, aar, ans, verk, ald);
         }
         if (kjonn != null && kjonn.equals(2)) {
            this.updateMap(this.kvinnerTotal, aar, ans, verk, ald);
         }

         if (hd != null && hd.equals("H")) {
            this.updateMap(this.heltid, aar, ans, verk, ald);
            if (kjonn != null && kjonn.equals(1)) {
               this.updateMap(this.mennHeltid, aar, ans, verk, ald);
            }
            if (kjonn != null && kjonn.equals(2)) {
               this.updateMap(this.kvinnerHeltid, aar, ans, verk, ald);
            }
         }

         if (hd != null && hd.equals("D")) {
            this.updateMap(this.deltid, aar, ans, verk, ald);
            if (kjonn != null && kjonn.equals(1)) {
               this.updateMap(this.mennDeltid, aar, ans, verk, ald);
            }
            if (kjonn != null && kjonn.equals(2)) {
               this.updateMap(this.kvinnerDeltid, aar, ans, verk, ald);
            }
         }

      }

      this.fiksAlder(this.total);
      this.fiksAlder(this.mennTotal);
      this.fiksAlder(this.kvinnerTotal);

      this.fiksAlder(this.heltid);
      this.fiksAlder(this.mennHeltid);
      this.fiksAlder(this.kvinnerHeltid);

      this.fiksAlder(this.deltid);
      this.fiksAlder(this.mennDeltid);
      this.fiksAlder(this.kvinnerDeltid);

   }

   private void updateMap(SortedMap<Integer, Ansatte> map, Integer aar, int ans, float verk, int ald) {
      Ansatte a = map.get(aar);
      if (a == null) {
         a = new Ansatte();
         map.put(aar, a);
      }

      a.setAar(aar);
      a.setAnsatte((a.getAnsatte() != null ? a.getAnsatte().intValue() + ans : ans));
      a.setAarsverk((a.getAarsverk() != null ? a.getAarsverk().floatValue() + verk : verk));
      a.setAlder((a.getAlder() != null ? a.getAlder().intValue() + ald : ald));
   }

   private void fiksAlder(SortedMap<Integer, Ansatte> map) {
      for (Ansatte a : map.values()) {
         Integer alder = a.getAlder();
         Integer ansatte = a.getAnsatte();
         if (alder == null || ansatte == null || ansatte.equals(0)) {
            continue;
         }
         a.setAlder(alder / ansatte);
      }
   }

   private void opprettNyeMap() {
      this.total = new TreeMap<Integer, Ansatte>();
      this.mennTotal = new TreeMap<Integer, Ansatte>();
      this.kvinnerTotal = new TreeMap<Integer, Ansatte>();
      this.heltid = new TreeMap<Integer, Ansatte>();
      this.mennHeltid = new TreeMap<Integer, Ansatte>();
      this.kvinnerHeltid = new TreeMap<Integer, Ansatte>();
      this.deltid = new TreeMap<Integer, Ansatte>();
      this.mennDeltid = new TreeMap<Integer, Ansatte>();
      this.kvinnerDeltid = new TreeMap<Integer, Ansatte>();
   }

   /**
    * Returnerer mapping med ansatte/årsverk til enheter for gitt år.
    *
    * @param aar
    * @return mapping: var --> idnum --> ansatte.
    * @throws SQLException
    */
   public Map<String, Map<Integer, Ansatte>> getEnhetAnsatte(Integer aar) throws SQLException {
      
      Map<String, Map<Integer, Ansatte>> map = new HashMap<String, Map<Integer, Ansatte>>();

      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_enhet_ansatte where aar = ?";
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

      String[] kjonn = {"1", "2", "A"};
      String[] lonnkat = {"2", "3", "4", "A"};
      String[] stilling = {"H", "D", "T", "A"};

      for (String k : kjonn) {
         for (String l : lonnkat) {
            for (String hd : stilling) {
               
               for (SortedMap rad : rader) {
                  Integer idnum = (Integer) rad.get("idnum");

                  Number ansatte = (Number) rad.get("ansatte");
                  int ans = (ansatte != null ? ansatte.intValue() : 0);

                  Number aarsverk = (Number) rad.get("aarsverk");
                  float verk = (aarsverk != null ? aarsverk.floatValue() : 0);

                  Integer aldersum = (Integer) rad.get("aldersum");
                  
                  String kBase = rad.get("kjonn").toString();
                  String lBase = rad.get("stat_grl").toString();
                  String hdBase = (String) rad.get("hd");
                  
                  if (!k.equals("A") && !k.equals(kBase)) {
                     continue;
                  }
                  if (!l.equals("A") && !l.equals(lBase)) {
                     continue;
                  }
                  if (!hd.equals("A") && !hd.equals(hdBase)) {
                     continue;
                  }
                  
                  String varKey = "k" + k + "-l" + l + "-hd" + hd;
                  varKey = varKey.toLowerCase();
                  
                  Map<Integer, Ansatte> enhetMap = map.get(varKey);
                  if (enhetMap == null) {
                     enhetMap = new HashMap<Integer, Ansatte>();
                     map.put(varKey, enhetMap);
                  }
                  Ansatte a = enhetMap.get(idnum);
                  if (a == null) {
                     a = new Ansatte();
                     enhetMap.put(idnum, a);
                  }
                  // ansatte
                  Integer ansDelsum = a.getAnsatte();
                  if (ansDelsum == null) {
                     ansDelsum = 0;
                  }
                  a.setAnsatte(ansDelsum + ans);
                  // årsverk
                  Float verkDelsum = a.getAarsverk();
                  if (verkDelsum == null) {
                     verkDelsum = 0f;
                  }
                  a.setAarsverk(verkDelsum + verk);
                  // aldersum
                  Integer aldersumDelsum = a.getAlder();
                  if (aldersumDelsum == null) {
                     aldersumDelsum = 0;
                  }
                  a.setAlder(aldersumDelsum + aldersum);
               }
            }
         }
      }
      

      return map;
   }
   
   
}
