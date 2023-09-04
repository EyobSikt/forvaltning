package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.Enhet;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;


public class StortingetSaksnummerLogikk {

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
   public List<Integer> getStortingetsaksnummer(Integer id) throws SQLException {
      List<Integer> relasjoner = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_stortingetsaksnummer where endringsid = ?";
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
         Integer stortingetsaksnummer = (Integer) rad.get("stortingetsaksnummer");
         relasjoner.add(stortingetsaksnummer);
      }
      return relasjoner;
   }

   /**
    * Slett relasjoner for gitt endringsid.
    *
    * @param id - endringsid.
    * @throws SQLException
    */
   public void slettStortingetSaksnummer(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_stortingetsaksnummer where endringsid = ?";
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
   public void registrerNyeStortingetSaksnummer(Integer endringsid, Integer idnum , List<Integer> idnums) throws SQLException {
      PreparedStatement st = null;
      String sql = "insert into t_forvaltning_stortingetsaksnummer (endringsid, idnum, stortingetsaksnummer) values (?, ?, ?)";

      try {
         st = this.conn.prepareStatement(sql);
         for (Integer i : idnums) {
            st.setInt(1, endringsid);
            st.setInt(2, idnum);
            st.setInt(3, i);
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

}
