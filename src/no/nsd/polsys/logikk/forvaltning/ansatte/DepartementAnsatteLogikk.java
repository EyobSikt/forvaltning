package no.nsd.polsys.logikk.forvaltning.ansatte;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.EtatnavnAnsatteCacheFactory;
import no.nsd.polsys.modell.forvaltning.Ansatte;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author hvb
 */
public class DepartementAnsatteLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<Integer> getDepartementAar(Integer depnr, Integer statgrl) throws SQLException {
      List<Integer> aar = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct u_ver_1 from t_forvaltning_ansatte_kumulativ "
              + "where (u_etatskode = ? or u_etatskode like ?) and u_ver_1 < 2015 "
              + "and u_stat_grl " + (statgrl != null ? "= " + statgrl : "in (2, 3, 4)") + " "
              + "order by u_ver_1 desc";

      List values = new ArrayList();
      values.add(depnr != null ? depnr.toString() : "null");
      values.add(depnr + ":%");

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

   public List<Ansatte> getAnsatteForDep(Integer aar, Integer statgrl) throws SQLException {
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select substring(u_etatskode, 0, position(':' in u_etatskode)) as depomrnr, "
              + "sum(antall) as ansatte, "
              + " CASE u_tjforh_2 WHEN 'T' THEN sum(u_delpros*antall)/162.0 ELSE sum(u_delpros*antall)/100.0 END as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_ver_1 = ? and u_ver_1 < 2015 "
              + "and u_stat_grl " + (statgrl != null ? "= " + statgrl : "in (2, 3, 4)") + " "
              + "group by substring(u_etatskode, 0, position(':' in u_etatskode)), u_tjforh_2 "
              + "order by depomrnr";

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

      Map<String, Ansatte> allenavn = EtatnavnAnsatteCacheFactory.getDokdata(this.conn);

      // vi grupperer i sql-setningen på tjenesteforhold-2 for at årsverk skal bli riktig.
      // må nå slå sammen rader som har samme depkode.
      // sql-data er sortert på depkode, slik at det holder å se på forrige rad.
      Ansatte forrige = null;

      for (SortedMap rad : rader) {
         Ansatte a = new Ansatte();

         a.setAar((Integer) rad.get("u_ver_1"));
         a.setEtatkode((String) rad.get("depomrnr"));

         Ansatte cachetAnsatt = allenavn.get(a.getEtatkode());
         if (cachetAnsatt != null) {
            a.setDep(cachetAnsatt.getNavn(aar));
         }

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
}
