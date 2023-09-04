package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.Statres;

/**
 *
 * @author hvb
 */
public class StatresLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<Statres> getStatresTilEnhet(Integer idnum) throws SQLException {
      List<Statres> liste = new ArrayList<Statres>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;

      String sqlSelect = "select d.*, n.statres_navn from t_forvaltning_statres as d "
              + "inner join t_forvaltning_statres_navn as n on d.statres_id = n.statres_id "
              + "where n.idnum = ? "
              + "order by d.aar, d.statres_id";

      List<Integer> values = new ArrayList<Integer>();

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
         Statres s = this.getStatresObjekt(rad);
         liste.add(s);
      }

      return liste;
   }

   /**
    * Returnerer statres-data for gitt år.
    *
    * @param aar
    * @return mapping: idnum --> statres.
    * @throws SQLException
    */
   public Map<Integer, Statres> getStatres(Integer aar) throws SQLException {
      Map<Integer, Statres> mapping = new HashMap<Integer, Statres>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;

      String sqlSelect = "select d.*, n.statres_navn, n.idnum from t_forvaltning_statres as d "
              + "inner join t_forvaltning_statres_navn as n on d.statres_id = n.statres_id "
              + "where d.aar = ? and n.idnum is not null";

      List<Integer> values = new ArrayList<Integer>();

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
         Integer idnum = (Integer) rad.get("idnum");
         Statres s = this.getStatresObjekt(rad);

         mapping.put(idnum, s);
      }

      return mapping;
   }

   private Statres getStatresObjekt(SortedMap rad) {
      Statres s = new Statres();

      s.setAar((Integer) rad.get("aar"));
      s.setStatresId((Integer) rad.get("statres_id"));
      s.setStatresNavn((String) rad.get("statres_navn"));
      s.setEgenproduksjon((Integer) rad.get("egenproduksjon"));

      Number lonn = (Number) rad.get("lonnskost_egenprod");
      Number vt = (Number) rad.get("varer_tjenester");

      s.setLonnskostEgenprod(lonn != null ? lonn.floatValue() : null);
      s.setVarerTjenester(vt != null ? vt.floatValue() : null);

      s.setInvesteringer((Integer) rad.get("investeringer"));
      s.setOverforinger((Integer) rad.get("overforinger"));
      s.setTotaleUtgifter((Integer) rad.get("totale_utgifter"));
      s.setPremieSp((Integer) rad.get("premie_sp"));
      s.setAvtalteAarsverk((Integer) rad.get("avtalte_aarsverk"));
      return s;
   }
}
