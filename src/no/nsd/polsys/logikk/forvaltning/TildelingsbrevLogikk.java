package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.TildelingsbrevCacheFactory;
import no.nsd.polsys.modell.forvaltning.Tildelingsbrev;

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
public class TildelingsbrevLogikk {

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
   public List<Tildelingsbrev> getTildelingsbrevTilRegEnhet(Integer idnum) throws SQLException {
      // mapping: idnum --> liste med årsmeldinger for alle år for gitt idnum.
      Map<Integer, List<Tildelingsbrev>> alleTildelingsbrev = TildelingsbrevCacheFactory.getTildelingsbrev(this.conn);
      return alleTildelingsbrev.get(idnum);
   }

   
   public List<Tildelingsbrev> getTildelingsbrevTilEnhetNoCache(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_tildelingsbrev where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<Tildelingsbrev> tildelingsbrev = new ArrayList<Tildelingsbrev>();
      SortedMap[] rader = result.getRows();
      
      if (rader == null || rader.length == 0) {
         return null;
      }
      
      for (SortedMap rad : rader) {
         Tildelingsbrev a = new Tildelingsbrev();
         a.setId((Integer) rad.get("id"));
         a.setIdnum((Integer) rad.get("idnum"));
         a.setAar((Integer) rad.get("aar"));
         a.setTildelingsbrev((String) rad.get("tildelingsbrev"));
         a.setEngelskTildelingsbrev((String) rad.get("eng_tildelingsbrev"));
         a.setSisteUrl((String) rad.get("siste_url"));
         tildelingsbrev.add(a);
      }

      return tildelingsbrev;
   }
   
   
   /**
    * Sletter en gitt årsmelding.
    * @param id
    * @throws SQLException
    */
   public void slettTildelingsbrev(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_tildelingsbrev where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny tildelingsbrev.
    * @param tildelingsbrev
    * @throws SQLException
    */
   public void registrerNyTildelingsbrev(Tildelingsbrev tildelingsbrev) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_tildelingsbrev (aar, idnum, tildelingsbrev, eng_tildelingsbrev, siste_url) values (?, ?, ?, ?, ?)";
      List values = new ArrayList();

      values.add(tildelingsbrev.getAar());
      values.add(tildelingsbrev.getIdnum());
      values.add(tildelingsbrev.getTildelingsbrev());
      values.add(tildelingsbrev.getEngelskTildelingsbrev());
      values.add(tildelingsbrev.getSisteUrl());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer tildelingsbrev.
    * @param tildelingsbrev
    * @throws SQLException
    */
   public void oppdaterTildelingsbrev(Tildelingsbrev tildelingsbrev) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_tildelingsbrev set "
              + "aar = ?, "
              + "idnum = ?, "
              + "tildelingsbrev = ?, "
              + "eng_tildelingsbrev = ?, "
              + "siste_url = ? "
              + "where id = ?";
      List values = new ArrayList();

      values.add(tildelingsbrev.getAar());
      values.add(tildelingsbrev.getIdnum());
      values.add(tildelingsbrev.getTildelingsbrev());
      values.add(tildelingsbrev.getEngelskTildelingsbrev());
      values.add(tildelingsbrev.getSisteUrl());

      values.add(tildelingsbrev.getId());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   

}
