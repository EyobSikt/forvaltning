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
public class KommuneAnsatteLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<Ansatte> getAnsatteForKommuner(Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr, u_navn, "
              + "sum(antall) as ansatte, "
              + " CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_ver_1 = ? and u_ver_1 < 2015 "
              + (statgrl != null ? "and u_stat_grl = " + statgrl : "and u_stat_grl in (2, 3, 4)") + " "
              + "group by u_kommnr, u_navn, u_tjforh_2 "
              + "order by u_kommnr";

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

      // vi grupperer i sql-setningen på tjenesteforhold-2 for at årsverk skal bli riktig.
      // må nå slå sammen rader som har samme kommune.
      // sql-data er sortert på kommune, slik at det holder å se på forrige rad.
      Ansatte forrige = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         Number kommunenr = (Number) rad.get("u_kommnr");
         a.setKommunenummer((kommunenr != null ? kommunenr.intValue() : null));

         a.setKommune((String) rad.get("u_navn"));

         a.setFylkenummer(a.getKommunenummer() / 100);

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : 0));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : 0f));

         if (forrige == null || !forrige.getKommunenummer().equals(a.getKommunenummer())) {
            liste.add(a);
            forrige = a;
         } else {
            forrige.setAnsatte(forrige.getAnsatte() + a.getAnsatte());
            forrige.setAarsverk(forrige.getAarsverk() + a.getAarsverk());
         }
      }

      return liste;
   }

   public List<Ansatte> getAnsatteForKommuner(Integer aar, Integer statgrl, Integer depkode) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr, u_navn, "
              + "sum(antall) as ansatte, "
              + "aarsverk = CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_etatskode like ? "
              + "and u_ver_1 = ? and u_ver_1 < 2015 "
              + (statgrl != null ? "and u_stat_grl = " + statgrl : "and u_stat_grl in (2, 3, 4)") + " "
              + "group by u_kommnr, u_navn, u_tjforh_2 "
              + "order by u_kommnr";

      List values = new ArrayList();
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

      // vi grupperer i sql-setningen på tjenesteforhold-2 for at årsverk skal bli riktig.
      // må nå slå sammen rader som har samme kommune.
      // sql-data er sortert på kommune, slik at det holder å se på forrige rad.
      Ansatte forrige = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         Number kommunenr = (Number) rad.get("u_kommnr");
         a.setKommunenummer((kommunenr != null ? kommunenr.intValue() : null));

         a.setKommune((String) rad.get("u_navn"));

         a.setFylkenummer(a.getKommunenummer() / 100);

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : 0));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : 0f));

         if (forrige == null || !forrige.getKommunenummer().equals(a.getKommunenummer())) {
            liste.add(a);
            forrige = a;
         } else {
            forrige.setAnsatte(forrige.getAnsatte() + a.getAnsatte());
            forrige.setAarsverk(forrige.getAarsverk() + a.getAarsverk());
         }
      }

      return liste;
   }

   // kjønn: 1=menn, 2=kvinner, hdkode: H=heltid, D=deltid.
   public SortedMap<Integer, Ansatte> getTotal(Integer kommunenr, Integer kjonn1, Integer kjonn2, String hdkode1, String hdkode2) throws SQLException {
      SortedMap<Integer, Ansatte> total = new TreeMap<Integer, Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_ver_1, sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and u_kommnr = ? and (u_kjonn = ? or u_kjonn = ?) and (u_hdkode = ? or u_hdkode = ?) "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_ver_1 "
              + "order by u_ver_1";
      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(kommunenr);
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

   public String getKommune(Integer kommunenr) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct u_ver_1, u_navn from t_forvaltning_ansatte_kumulativ where u_kommnr = ? and u_ver_1 < 2015 order by u_ver_1 desc";
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

      return (String) rader[0].get("u_navn");
   }
}
