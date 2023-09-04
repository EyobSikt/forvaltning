package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class RelasjonLogikk {

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
    * Returnerer alle relasjoner til en gitt endringsid.
    *
    * @param id - endringsid
    * @return
    * @throws SQLException
    */
   public List<Integer> getRelasjoner(Integer id) throws SQLException {
      List<Integer> relasjoner = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_relasjon where endringsid = ?";
      List values = new ArrayList();

      values.add(id);

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
         relasjoner.add(idnum);
      }
      return relasjoner;
   }

   /**
    * Slett relasjoner for gitt endringsid.
    *
    * @param id - endringsid.
    * @throws SQLException
    */
   public void slettRelasjoner(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_relasjon where endringsid = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer nye idnums for gitt endringsid i relasjon.
    *
    * @param endringsid
    * @param idnums
    * @throws SQLException
    */
   public void registrerNyeRelasjoner(Integer endringsid, List<Integer> idnums) throws SQLException {
      PreparedStatement st = null;
      String sql = "insert into t_forvaltning_relasjon (endringsid, idnum) values (?, ?)";

      try {
         st = this.conn.prepareStatement(sql);
         for (Integer i : idnums) {
            st.setInt(1, endringsid);
            st.setInt(2, i);
            st.executeUpdate();
         }
      } finally {
         if (st != null) {
            try {
               st.close();
            } catch (SQLException ignored) {
            }
         }
      }
   }

   /**
    * Returnerer alle (andre) relasjonenheter.
    *
    * @return
    * @throws SQLException
    */
   public List<Enhet> getAlleRelasjonAndreEnheter() throws SQLException {
      List<Enhet> relasjonenheter = new ArrayList<Enhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_relasjon_andre_enhet order by id desc";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Enhet e = new Enhet();
         e.setIdnum((Integer) rad.get("id"));
         e.setLangtNavn((String) rad.get("navn"));
         e.setEngelskLangtNavn((String) rad.get("eng_navn"));
         relasjonenheter.add(e);
      }
      return relasjonenheter;
   }

   /**
    * Returnerer alle relasjoner - andre - til en gitt endringsid.
    *
    * @param id - endringsid
    * @return
    * @throws SQLException
    */
   public List<Integer> getRelasjonerAndre(Integer id) throws SQLException {
      List<Integer> relasjoner = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_relasjon_andre where endringsid = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer idAndre = (Integer) rad.get("id");
         relasjoner.add(idAndre);
      }
      return relasjoner;
   }

   /**
    * Slett relasjoner for gitt endringsid.
    *
    * @param id - endringsid.
    * @throws SQLException
    */
   public void slettRelasjonerAndre(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_relasjon_andre where endringsid = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer nye ids (andre-enheter) for gitt endringsid i relasjon-andre
    *
    * @param endringsid
    * @param ids
    * @throws SQLException
    */
   public void registrerNyeRelasjonerAndre(Integer endringsid, List<Integer> ids) throws SQLException {
      PreparedStatement st = null;
      String sql = "insert into t_forvaltning_relasjon_andre (endringsid, id) values (?, ?)";

      try {
         st = this.conn.prepareStatement(sql);
         for (Integer i : ids) {
            st.setInt(1, endringsid);
            st.setInt(2, i);
            st.executeUpdate();
         }
      } finally {
         if (st != null) {
            try {
               st.close();
            } catch (SQLException ignored) {
            }
         }
      }
   }

   /**
    * Registrerer ny relasjonsenhet.
    *
    * @param navn - navn på den nye enheten.
    * @param engelskNavn - engelsk navn på den nye enheten.
    * @throws SQLException
    */
   public void registrerNyRelasjonAndreEnhet(String navn, String engelskNavn) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_relasjon_andre_enhet (navn, eng_navn) values (?, ?)";
      List values = new ArrayList();

      values.add(navn);
      values.add(engelskNavn);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer (navnet på) relasjonsenhet.
    *
    * @param id - id til gitt enhet.
    * @param navn - nytt navn på gitt enhet.
    * @param engelskNavn - nytt engelsk navn på gitt enhet.
    * @throws SQLException
    */
   public void oppdaterRelasjonAndreEnhet(Integer id, String navn, String engelskNavn) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_relasjon_andre_enhet set navn = ?, eng_navn = ? where id = ?";
      List values = new ArrayList();

      values.add(navn);
      values.add(engelskNavn);
      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * sletter gitt relasjonsenhet.
    *
    * @param id - id til gitt relasjon-andre-enhet.
    * @throws SQLException
    */
   public void slettRelasjonAndreEnhet(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_relasjon_andre_enhet where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }





}
