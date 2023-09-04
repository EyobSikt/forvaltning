package no.nsd.polsys.logikk.forvaltning.ansatte;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.comparators.forvaltning.AnsatteEtatkodeComparator;
import no.nsd.polsys.factories.forvaltning.EtatnavnAnsatteCacheFactory;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author hvb
 */
public class EtatAnsatteLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public boolean eksistererEtat(String etatkode) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select count(id) as antall from t_forvaltning_ansatte_kumulativ "
              + "where u_ver_1 < 2015 and (u_etatskode = ? or u_etatskode like ? ) ";

      List values = new ArrayList();
      values.add(etatkode);
      values.add(etatkode + ":%");

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return false;
      }
      Number n = (Number) rader[0].get("antall");
      if (n == null) {
         return false;
      }
      int i = n.intValue();
      return (i > 0);
   }

   public List<Integer> getAlleAarForEtat(String etatkode) throws SQLException {
      List<Integer> aar = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct u_ver_1 from t_forvaltning_ansatte_kumulativ "
              + "where u_ver_1 < 2015 and (u_etatskode = ? or u_etatskode like ?) "
              + "order by u_ver_1 desc";

      List values = new ArrayList();
      values.add(etatkode);
      values.add(etatkode + ":%");

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         aar.add((Integer) rad.get("u_ver_1"));
      }

      return aar;
   }

   // kjønn: 1=menn, 2=kvinner, hdkode: H=heltid, D=deltid.
   public SortedMap<Integer, Ansatte> getTotal(String etatkode, Integer kjonn1, Integer kjonn2, String hdkode1, String hdkode2) throws SQLException {
      SortedMap<Integer, Ansatte> total = new TreeMap<Integer, Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_ver_1, sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? "
              + "and (u_etatskode = ? or u_etatskode like ?) and u_ver_1 < 2015 "
              + "and (u_kjonn = ? or u_kjonn = ?) and (u_hdkode = ? or u_hdkode = ?) "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_ver_1 "
              + "order by u_ver_1";
      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(etatkode);
      values.add(etatkode + ":%");
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
   public Ansatte getTotal(String etatkode, Integer kjonn1, Integer kjonn2, String hdkode1, String hdkode2, Integer aar) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? "
              + "and (u_etatskode = ? or u_etatskode like ?) and u_ver_1 < 2015 "
              + "and (u_kjonn = ? or u_kjonn = ?) and (u_hdkode = ? or u_hdkode = ?) "
              + "and u_ver_1 = ? "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T'))";
      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(etatkode);
      values.add(etatkode + ":%");
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

   public List<Ansatte> getAnsattePerEtatOgKommune(Integer fylkenr, Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_etatnavn, u_kommnr, u_navn, "
              + "substring(u_etatskode, 0, position (':' in u_etatskode)) as depkode, "
              + "sum(antall) as ansatte, "
              + "  CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_kommnr/100 = ? and u_ver_1 = ? and u_ver_1 < 2015 "
              + (statgrl != null ? "and u_stat_grl = " + statgrl : "and u_stat_grl in (2, 3, 4)") + " "
              + "group by u_etatnavn, substring(u_etatskode, 0, position (':' in u_etatskode)), u_kommnr, u_navn, u_tjforh_2 "
              + "order by u_etatnavn, u_kommnr";

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

      // vi grupperer i sql-setningen på tjenesteforhold-2 for at årsverk skal bli riktig.
      // må nå slå sammen rader som har samme etat og kommune.
      // sql-data er sortert på etat og kommune, slik at det holder å se på forrige rad.
      Ansatte forrige = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         a.setAar((Integer) rad.get("u_ver_1"));
         a.setEtat((String) rad.get("u_etatnavn"));
         a.setEtatkode((String) rad.get("depkode"));

         Number kommunenr = (Number) rad.get("u_kommnr");
         a.setKommunenummer((kommunenr != null ? kommunenr.intValue() : null));

         a.setKommune((String) rad.get("u_navn"));

         a.setFylkenummer(a.getKommunenummer() / 100);

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : 0));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : 0f));

         if (forrige == null
                 || !forrige.getKommunenummer().equals(a.getKommunenummer())
                 || !forrige.getEtat().equals(a.getEtat())) {
            liste.add(a);
            forrige = a;
         } else {
            forrige.setAnsatte(forrige.getAnsatte() + a.getAnsatte());
            forrige.setAarsverk(forrige.getAarsverk() + a.getAarsverk());
         }
      }

      return liste;
   }

   public List<Ansatte> getAnsatteForEtatOgFylke(String etatkode, Integer aar) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_etatnavn, u_kommnr/100 as fylkenr "
              + ", sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder "
              + ", sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and u_ver_1 = ? and (u_etatskode like ? or u_etatskode like ?) "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_etatnavn, u_kommnr/100 "
              + "order by u_kommnr/100, u_etatnavn";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(aar);
      values.add(etatkode);
      values.add(etatkode + ":%");

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
         a.setEtat((String) rad.get("u_etatnavn"));

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

   public List<Ansatte> getAnsatteForEtatOgKommune(String etatkode, Integer aar) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_etatnavn, u_kommnr, u_navn "
              + ", sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder "
              + ", sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and u_ver_1 = ? and (u_etatskode like ? or u_etatskode like ?) "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_etatnavn, u_kommnr, u_navn "
              + "order by u_kommnr/100, u_navn, u_etatnavn";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(aar);
      values.add(etatkode);
      values.add(etatkode + ":%");

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
         a.setEtat((String) rad.get("u_etatnavn"));

         Number kommunenr = (Number) rad.get("u_kommnr");
         a.setKommunenummer((kommunenr != null ? kommunenr.intValue() : null));

         a.setKommune((String) rad.get("u_navn"));

         a.setFylkenummer(a.getKommunenummer() / 100);

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

   public List<Ansatte> getAnsatteForEtatAggregertForFylke(Integer fylkenr, Integer aar) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_etatnavn, substring(u_etatskode, 0, charindex(':', u_etatskode)) as depkode "
              + ", sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder "
              + ", sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and u_kommnr/100 = ? and u_ver_1 = ? "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_etatnavn, substring(u_etatskode, 0, charindex(':', u_etatskode)) "
              + "order by u_etatnavn";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
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
         Ansatte a = new Ansatte();

         a.setAar((Integer) rad.get("u_ver_1"));
         a.setEtatkode((String) rad.get("depkode"));
         a.setEtat((String) rad.get("u_etatnavn"));

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

   public List<Ansatte> getAnsatteForEtatAggregert(String etatkode, Integer aar) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select min(u_etatskode) as etatkode, u_etatnavn "
              + ", sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder "
              + ", sum(u_delpros*antall)/100.0 as aarsverk "
              + ", count(distinct u_etatskode) as avdelinger "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and u_ver_1 = ? and (u_etatskode like ? or u_etatskode like ?) "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_etatnavn "
              + "order by u_etatnavn";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(aar);
      values.add(etatkode);
      values.add(etatkode + ":%");

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

         String ek = (String) rad.get("etatkode");
         Number antallEtatkode = (Number) rad.get("avdelinger");
         ek = fiksEtatkode(ek, antallEtatkode);
         a.setEtatkode(ek);

         a.setAar((Integer) rad.get("u_ver_1"));
         a.setEtat((String) rad.get("u_etatnavn"));
         a.setDep((String) rad.get("navn"));

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

   public List<Ansatte> getAnsatteForEtatForKommune(Integer kommunenr, Integer aar) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_etatnavn, substring(u_etatskode, 0, charindex(':', u_etatskode)) as depkode "
              + ", sum(antall) as ansatte, sum(u_alder*antall)/sum(antall) as alder "
              + ", sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl = ? and u_ver_1 < 2015 and u_kommnr = ? and u_ver_1 = ? "
              + "and (u_tjforh_2 is null or (u_tjforh_2 != 'T')) "
              + "group by u_etatnavn, substring(u_etatskode, 0, charindex(':', u_etatskode)) "
              + "order by u_etatnavn";

      List values = new ArrayList();
      values.add(2); // statistikkgrunnlag
      values.add(kommunenr);
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

         a.setAar((Integer) rad.get("u_ver_1"));
         a.setEtatkode((String) rad.get("depkode"));
         a.setEtat((String) rad.get("u_etatnavn"));

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

   public List<Ansatte> getAnsattePerSted(Integer kommunenr, Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_etatskode, u_etatnavn, u_arbnavn, "
              + "sum(antall) as ansatte, "
              + " CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_kommnr = ? and u_ver_1 = ? and u_ver_1 < 2015 "
              + (statgrl != null ? "and u_stat_grl = " + statgrl : "and u_stat_grl in (2, 3, 4)") + " "
              + "group by u_etatskode, u_etatnavn, u_arbnavn, u_tjforh_2 "
              + "order by u_etatnavn, u_arbnavn, u_etatskode";

      List values = new ArrayList();
      values.add(kommunenr);
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
      // må nå slå sammen rader som har samme etatkode og sted.
      // sql-data er sortert på etat og sted, slik at det holder å se på forrige rad.
      Ansatte forrige = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         a.setAar((Integer) rad.get("u_ver_1"));
         a.setEtatkode((String) rad.get("u_etatskode"));
         a.setEtat((String) rad.get("u_etatnavn"));
         a.setArbeidssted((String) rad.get("u_arbnavn"));

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : 0));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : 0f));

         if (forrige == null
                 || !forrige.getEtatkode().equals(a.getEtatkode())
                 || !forrige.getEtat().equals(a.getEtat())
                 || (forrige.getArbeidssted() != null && a.getArbeidssted() == null)
                 || (forrige.getArbeidssted() == null && a.getArbeidssted() != null)
                 || (forrige.getArbeidssted() != null && a.getArbeidssted() != null && !forrige.getArbeidssted().equals(a.getArbeidssted()))) {
            liste.add(a);
            forrige = a;
         } else {
            forrige.setAnsatte(forrige.getAnsatte() + a.getAnsatte());
            forrige.setAarsverk(forrige.getAarsverk() + a.getAarsverk());
         }
      }

      return liste;
   }

   public List<Ansatte> getAnsatteForSted(String etatkode, Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr, u_navn, u_etatskode, u_etatnavn, u_arbnavn, "
              + "sum(antall) as ansatte, "
              + " CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where "
              + "(u_etatskode = ? or u_etatskode like ?) "
              + "and u_ver_1 = ? and u_ver_1 < 2015 "
              + "and u_stat_grl " + (statgrl != null ? "= " + statgrl : "in (2, 3, 4)") + " "
              + "group by u_kommnr, u_navn, u_etatskode, u_etatnavn, u_arbnavn, u_tjforh_2 "
              + "order by u_kommnr, u_etatskode, u_arbnavn";

      List values = new ArrayList();
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

      // vi grupperer i sql-setningen på tjenesteforhold-2 for at årsverk skal bli riktig.
      // må nå slå sammen rader som har samme etatkode, kommunenr og arbeidssted.
      // sql-data er sortert på kommune, slik at det holder å se på forrige rad.
      Ansatte forrige = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         Number kommunenr = (Number) rad.get("u_kommnr");
         a.setKommunenummer((kommunenr != null ? kommunenr.intValue() : null));

         a.setKommune((String) rad.get("u_navn"));
         a.setEtatkode((String) rad.get("u_etatskode"));
         a.setEtat((String) rad.get("u_etatnavn"));
         a.setArbeidssted((String) rad.get("u_arbnavn"));
         a.setFylkenummer(a.getKommunenummer() / 100);

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : 0));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : 0f));

         if (forrige == null
                 || !forrige.getEtatkode().equals(a.getEtatkode())
                 || !forrige.getKommunenummer().equals(a.getKommunenummer())
                 || (forrige.getArbeidssted() != null && a.getArbeidssted() == null)
                 || (forrige.getArbeidssted() == null && a.getArbeidssted() != null)
                 || (forrige.getArbeidssted() != null && a.getArbeidssted() != null && !forrige.getArbeidssted().equals(a.getArbeidssted()))) {
            liste.add(a);
            forrige = a;
         } else {
            forrige.setAnsatte(forrige.getAnsatte() + a.getAnsatte());
            forrige.setAarsverk(forrige.getAarsverk() + a.getAarsverk());
         }
      }

      return liste;
   }

   public List<Ansatte> getAggregertAnsatteForEtat(Integer aar, Integer statgrl, Integer depkode) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      if((this.beregnAggregertAnsatteForEtat(aar, statgrl, 1, depkode)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtat(aar, statgrl, 1, depkode));
      }
      if((this.beregnAggregertAnsatteForEtat(aar, statgrl, 2, depkode)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtat(aar, statgrl, 2, depkode));
      }
      Collections.sort(liste, new AnsatteEtatkodeComparator());
      this.leggTilEtatnavn(aar, liste);
      return liste;
   }

   public List<Ansatte> getAggregertAnsatteForEtatOgKommune(Integer aar, Integer statgrl, Integer depkode) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      if((this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 1, depkode)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 1, depkode));
      }
      if((this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 2, depkode)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 2, depkode));
      }
      Collections.sort(liste, new AnsatteEtatkodeComparator());
      return liste;
   }

   public List<Ansatte> getAnsatteForEtat(Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      if((this.beregnAggregertAnsatteForEtat(aar, statgrl, 2, null)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtat(aar, statgrl, 2, null));
      }
      Collections.sort(liste, new AnsatteEtatkodeComparator());
      this.leggTilEtatnavn(aar, liste);
      return liste;
   }

   public List<Ansatte> getAggregertAnsatteForEtat(Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      if((this.beregnAggregertAnsatteForEtat(aar, statgrl, 1, null)) !=null) {
      liste.addAll(this.beregnAggregertAnsatteForEtat(aar, statgrl, 1, null));
   }
      if((this.beregnAggregertAnsatteForEtat(aar, statgrl, 2, null)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtat(aar, statgrl, 2, null));
      }
      Collections.sort(liste, new AnsatteEtatkodeComparator());
      this.leggTilEtatnavn(aar, liste);
      return liste;
   }

   public List<Ansatte> getAggregertAnsatteForEtatOgKommune(Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      if((this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 1, null)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 1, null));
      }
      if((this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 2, null)) !=null) {
         liste.addAll(this.beregnAggregertAnsatteForEtatOgKommune(aar, statgrl, 2, null));
      }
      Collections.sort(liste, new AnsatteEtatkodeComparator());
      return liste;
   }

   private List<Ansatte> beregnAggregertAnsatteForEtat(Integer aar, Integer statgrl, Integer del, Integer depkode) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select fn_forvaltning_ansatte_etatkode_del(u_etatskode, " + del + ") as kode, "
              + "sum(antall) as ansatte, "
              + " CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_ver_1 = ? and u_ver_1 < 2015 "
              + (depkode != null ? "and u_etatskode like '" + depkode + ":%' " : "")
              + "and u_stat_grl " + (statgrl != null ? "= " + statgrl : "in (2, 3, 4)") + " "
              + "group by fn_forvaltning_ansatte_etatkode_del(u_etatskode, " + del + "), u_tjforh_2 "
              + "order by kode";

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
      // må nå slå sammen rader som har samme etatkode.
      // sql-data er sortert på etatkode, slik at det holder å se på forrige rad.
      Ansatte forrige = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         a.setAar(aar);
         a.setEtatkode((String) rad.get("kode"));

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : 0));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : 0f));

         if (forrige == null || !forrige.getEtatkode().equals(a.getEtatkode())) {
            liste.add(a);
            forrige = a;
         } else {
            forrige.setAnsatte(forrige.getAnsatte() + a.getAnsatte());
            forrige.setAarsverk(forrige.getAarsverk() + a.getAarsverk());
         }
      }

      return liste;
   }

   private List<Ansatte> beregnAggregertAnsatteForEtatOgKommune(Integer aar, Integer statgrl, Integer del, Integer depkode) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_kommnr, u_navn, fn_forvaltning_ansatte_etatkode_del(u_etatskode, " + del + ") as kode, "
              + "sum(antall) as ansatte, "
              + "aarsverk = CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_ver_1 = ? and u_ver_1 < 2015 "
              + (depkode != null ? "and u_etatskode like '" + depkode + ":%' " : "")
              + "and u_stat_grl " + (statgrl != null ? "= " + statgrl : "in (2, 3, 4)") + " "
              + "group by fn_forvaltning_ansatte_etatkode_del(u_etatskode, " + del + "), u_kommnr, u_navn, u_tjforh_2 "
              + "order by kode, u_kommnr";

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


      String tempKode = null;
      Ansatte tempAnsatt = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         a.setAar(aar);
         a.setEtatkode((String) rad.get("kode"));
         a.setKommunenummer((Integer) rad.get("u_kommnr"));
         a.setKommune((String) rad.get("u_navn"));
         a.setFylkenummer(a.getKommunenummer() / 100);

         Number ansatte = (Number) rad.get("ansatte");
         a.setAnsatte((ansatte != null ? ansatte.intValue() : 0));

         Number aarsverk = (Number) rad.get("aarsverk");
         a.setAarsverk((aarsverk != null ? aarsverk.floatValue() : 0f));

         if (tempKode == null || !tempKode.equals(a.getEtatkode())) {
            a.setAnsatteKommune(new HashMap<Integer, Ansatte>());
            liste.add(a);
            tempAnsatt = a;
            tempKode = a.getEtatkode();
         }

         // mapping: kommunenr --> ansatte.
         Map<Integer, Ansatte> map = tempAnsatt.getAnsatteKommune();
         Ansatte eksisterende = map.get(a.getKommunenummer());
         if (eksisterende != null) {
            eksisterende.setAnsatte(eksisterende.getAnsatte() + a.getAnsatte());
            eksisterende.setAarsverk(eksisterende.getAarsverk() + a.getAarsverk());
         } else {
            map.put(a.getKommunenummer(), a);
         }
      }

      return liste;
   }

   public void leggTilNavn(Integer aar, Ansatte ansatt) throws SQLException {
      if (ansatt == null) {
         return;
      }
      List<Ansatte> l = new ArrayList<Ansatte>();
      l.add(ansatt);
      this.leggTilEtatnavn(aar, l);
   }

   public void leggTilEtatnavn(Integer aar, List<Ansatte> koder) throws SQLException {
      if (koder == null) {
         return;
      }
      Map<String, Ansatte> allenavn = EtatnavnAnsatteCacheFactory.getDokdata(this.conn);

      for (Ansatte k : koder) {
         // etatnavn
         Ansatte etatnavn = allenavn.get(k.getEtatkode());
         if (etatnavn != null) {
            k.setEtat(etatnavn.getNavn(aar));
            k.setNavn(etatnavn.getNavn());
         }
         // depnavn
         Ansatte depnavn = allenavn.get(k.getDepkode());
         if (depnavn != null) {
            k.setDep(depnavn.getNavn(aar));
         }
      }
   }

   public void leggTilDepnavn(Integer aar, List<Ansatte> koder) throws SQLException {
      if (koder == null) {
         return;
      }
      Map<String, Ansatte> allenavn = EtatnavnAnsatteCacheFactory.getDokdata(this.conn);

      for (Ansatte k : koder) {
         Ansatte depnavn = allenavn.get(k.getDepkode());
         if (depnavn != null) {
            k.setDep(depnavn.getNavn(aar));
         }
      }
   }

   public static String fiksEtatkode(String etatkode, Number antallEtatkode) {
      if (etatkode == null || antallEtatkode == null) {
         return null;
      }
      int a = antallEtatkode.intValue();
      if (a == 1) {
         return etatkode;
      }
      int index = etatkode.lastIndexOf(":");
      if (index == -1) {
         return etatkode;
      }
      return etatkode.substring(0, index);
   }
}
