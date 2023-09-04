package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.InstruksCacheFactory;
import no.nsd.polsys.modell.forvaltning.Instruks;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Logikk-klasse for å hente ut og oppdatere info om instruks til enhetene.
 *
 */
public class InstruksLogikk {

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

   /**
    * Returnerer instruks som er registrert på gitt idnum.
    * @param idnum gitt idnum til enhet.
    * @return liste med instrukser.
    * @throws SQLException
    */
   public List<Instruks> getInstrukserTilRegEnhet(Integer idnum) throws SQLException {
      // mapping: idnum --> liste med instrukser for alle år for gitt idnum.
      Map<Integer, List<Instruks>> alleInstrukser = InstruksCacheFactory.getInstrukser(this.conn);
      return alleInstrukser.get(idnum);
   }

   
   public List<Instruks> getInstrukserTilEnhetNoCache(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_instruks where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<Instruks> instrukser = new ArrayList<Instruks>();
      SortedMap[] rader = result.getRows();
      
      if (rader == null || rader.length == 0) {
         return null;
      }
      
      for (SortedMap rad : rader) {
         Instruks a = new Instruks();
         a.setId((Integer) rad.get("id"));
         a.setIdnum((Integer) rad.get("idnum"));
         a.setAar((Integer) rad.get("aar"));
         a.setInstruks((String) rad.get("aarsinstruks"));
         a.setEngelskInstruks((String) rad.get("eng_aarsinstruks"));
         a.setSisteUrl((String) rad.get("siste_url"));
         instrukser.add(a);
      }

      return instrukser;
   }
   
   
   /**
    * Sletter en gitt instruks.
    * @param id
    * @throws SQLException
    */
   public void slettInstruks(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_instruks where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny instrukser.
    * @param instruks
    * @throws SQLException
    */
   public void registrerNyInstruks(Instruks instruks) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_instruks (aar, idnum, aarsinstruks, eng_aarsinstruks, siste_url) values (?, ?, ?, ?, ?)";
      List values = new ArrayList();

      values.add(instruks.getAar());
      values.add(instruks.getIdnum());
      values.add(instruks.getInstruks());
      values.add(instruks.getEngelskInstruks());
      values.add(instruks.getSisteUrl());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer instruks.
    * @param instruks
    * @throws SQLException
    */
   public void oppdaterInstruks(Instruks instruks) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_instruks set "
              + "aar = ?, "
              + "idnum = ?, "
              + "aarsinstruks = ?, "
              + "eng_aarsinstruks = ?, "
              + "siste_url = ? "
              + "where id = ?";
      List values = new ArrayList();

      values.add(instruks.getAar());
      values.add(instruks.getIdnum());
      values.add(instruks.getInstruks());
      values.add(instruks.getEngelskInstruks());
      values.add(instruks.getSisteUrl());

      values.add(instruks.getId());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   

}
