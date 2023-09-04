package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.AarsmeldingCacheFactory;
import no.nsd.polsys.modell.forvaltning.Aarsmelding;

/**
 * Logikk-klasse for å hente ut og oppdatere info om årsmeldinger til enhetene.
 * @author hvb
 */
public class AarsmeldingLogikk {

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
    * Returnerer årsmeldinger som er registrert på gitt idnum.
    * @param idnum gitt idnum til enhet.
    * @return liste med årsmeldinger.
    * @throws SQLException
    */
   public List<Aarsmelding> getAarsmeldingerTilRegEnhet(Integer idnum) throws SQLException {
      // mapping: idnum --> liste med årsmeldinger for alle år for gitt idnum.
      Map<Integer, List<Aarsmelding>> alleAarsmeldinger = AarsmeldingCacheFactory.getAarsmeldinger(this.conn);
      return alleAarsmeldinger.get(idnum);
   }

   
   public List<Aarsmelding> getAarsmeldingerTilEnhetNoCache(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_aarsmelding where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<Aarsmelding> aarsmeldinger = new ArrayList<Aarsmelding>();
      SortedMap[] rader = result.getRows();
      
      if (rader == null || rader.length == 0) {
         return null;
      }
      
      for (SortedMap rad : rader) {
         Aarsmelding a = new Aarsmelding();
         a.setId((Integer) rad.get("id"));
         a.setIdnum((Integer) rad.get("idnum"));
         a.setAar((Integer) rad.get("aar"));
         a.setAarsmelding((String) rad.get("aarsmeld"));
         a.setEngelskAarsmelding((String) rad.get("eng_aarsmeld"));
         a.setSisteUrl((String) rad.get("siste_url"));
         aarsmeldinger.add(a);
      }

      return aarsmeldinger;
   }
   
   
   /**
    * Sletter en gitt årsmelding.
    * @param id
    * @throws SQLException
    */
   public void slettAarsmelding(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_aarsmelding where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny årsmelding.
    * @param aarsmelding
    * @throws SQLException
    */
   public void registrerNyAarsmelding(Aarsmelding aarsmelding) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_aarsmelding (aar, idnum, aarsmeld, eng_aarsmeld, siste_url) values (?, ?, ?, ?, ?)";
      List values = new ArrayList();

      values.add(aarsmelding.getAar());
      values.add(aarsmelding.getIdnum());
      values.add(aarsmelding.getAarsmelding());
      values.add(aarsmelding.getEngelskAarsmelding());
      values.add(aarsmelding.getSisteUrl());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer årsmelding.
    * @param aarsmelding
    * @throws SQLException
    */
   public void oppdaterAarsmelding(Aarsmelding aarsmelding) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_aarsmelding set "
              + "aar = ?, "
              + "idnum = ?, "
              + "aarsmeld = ?, "
              + "eng_aarsmeld = ?, "
              + "siste_url = ? "
              + "where id = ?";
      List values = new ArrayList();

      values.add(aarsmelding.getAar());
      values.add(aarsmelding.getIdnum());
      values.add(aarsmelding.getAarsmelding());
      values.add(aarsmelding.getEngelskAarsmelding());
      values.add(aarsmelding.getSisteUrl());

      values.add(aarsmelding.getId());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   

}
