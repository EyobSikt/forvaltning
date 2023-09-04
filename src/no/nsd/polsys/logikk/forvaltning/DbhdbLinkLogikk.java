package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;

import no.nsd.polsys.modell.forvaltning.DbhdbLink;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Logikk-klasse for å hente ut og oppdatere info om årsmeldinger til enhetene.
 * @author hvb
 */
public class DbhdbLinkLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk;

   

   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }


   public List<DbhdbLink> getDbhdbLinkTilEnhet(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_dbh_instkode where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<DbhdbLink> dbhdbLink = new ArrayList<DbhdbLink>();
      SortedMap[] rader = result.getRows();
      
      if (rader == null || rader.length == 0) {
         return null;
      }
      
      for (SortedMap rad : rader) {
         DbhdbLink a = new DbhdbLink();
         a.setId((Integer) rad.get("id"));
         a.setIdnum((Integer) rad.get("idnum"));
         a.setAar((Integer) rad.get("aar"));
         a.setDbh_instkode((String) rad.get("dbh_instkode"));
         a.setKommentar((String) rad.get("kommentar"));
         dbhdbLink.add(a);
      }

      return dbhdbLink;
   }
   
   
   /**
    * Sletter en gitt årsmelding.
    * @param id
    * @throws SQLException
    */
   public void slettDbh_instkode(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_dbh_instkode where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny årsmelding.
    * @param dbhdbLink
    * @throws SQLException
    */
   public void registrerNyDbh_instkode(DbhdbLink dbhdbLink) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_dbh_instkode (aar, idnum, dbh_instkode, kommentar) values (?, ?, ?, ?)";
      List values = new ArrayList();

      values.add(dbhdbLink.getAar());
      values.add(dbhdbLink.getIdnum());
      values.add(dbhdbLink.getDbh_instkode());
      values.add(dbhdbLink.getKommentar());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer årsmelding.
    * @param dbhdbLink
    * @throws SQLException
    */
   public void oppdaterDbh_instkode(DbhdbLink dbhdbLink) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_dbh_instkode set "
              + "aar = ?, "
              + "idnum = ?, "
              + "dbh_instkode = ?, "
              + "kommentar = ? "
              + "where id = ?";
      List values = new ArrayList();

      values.add(dbhdbLink.getAar());
      values.add(dbhdbLink.getIdnum());
      values.add(dbhdbLink.getDbh_instkode());
      values.add(dbhdbLink.getKommentar());

      values.add(dbhdbLink.getId());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   

}
