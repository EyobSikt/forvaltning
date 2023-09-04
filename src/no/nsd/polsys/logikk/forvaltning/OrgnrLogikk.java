package no.nsd.polsys.logikk.forvaltning;

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
public class OrgnrLogikk {

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
    * Returnerer alle orgnr fra basen.
    * @return mapping: idnum --> orgnr.
    * @throws SQLException 
    */
   public Map<Integer, Integer> getAlleOrgnr() throws SQLException {
      // returneres fra denne metoden.
      Map<Integer, Integer> orgnr = new HashMap<Integer, Integer>();
      Result result;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader;
      String sqlSelect = "select * from t_forvaltning_orgnr";
      List values = new ArrayList();

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer i = (Integer) rad.get("idnum");
         Integer o = (Integer) rad.get("orgnr");
         orgnr.put(i, o);
      }
      return orgnr;
   }
   
   
   /**
    * Sletter en gitt orgnr for gitt idnum.
    *
    * @param idnum
    * @param orgnr
    * @throws SQLException
    */
   public void slettOrgnr(Integer idnum, Integer orgnr) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_orgnr where idnum = ? and orgnr = ?";
      List values = new ArrayList();

      values.add(idnum);
      values.add(orgnr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }
   /**
    * Sletter en gitt Virksomhetsnr for gitt idnum.
    *
    * @param idnum
    * @param orgnr
    * @throws SQLException
    */
   public void slettVirksomhetsnr(Integer idnum, Integer virksomhetsnummer) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update  t_forvaltning_orgnr set virksomhetsnummer = null  where idnum = ? and virksomhetsnummer = ? ";
      List values = new ArrayList();

      values.add(idnum);
      values.add(virksomhetsnummer);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer nytt orgnr.
    *
    * @param idnum
    * @param orgnr
    * @throws SQLException
    */
   public void registrerNyttOrgnr(Integer idnum, Integer orgnr) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_orgnr (idnum, orgnr) values (?, ?)";
      List values = new ArrayList();

      values.add(idnum);
      values.add(orgnr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer nytt Virksomhetsnr.
    *
    * @param idnum
    * @param orgnr
    * @throws SQLException
    */
   public void registrerNyttVirksomhetsnr(Integer idnum,  Integer virksomhetsnummer) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_orgnr set virksomhetsnummer = ?  where idnum = ? and virksomhetsnummer is null ";
      List values = new ArrayList();

      values.add(virksomhetsnummer);
      values.add(idnum);
      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

}
