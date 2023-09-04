package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.ArkivportalenCacheFactory;
import no.nsd.polsys.factories.forvaltning.TildelingsbrevCacheFactory;
import no.nsd.polsys.modell.forvaltning.Arkivportalen;
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
public class ArkivportalenLogikk {

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
   public List<Arkivportalen> getArkivportalenTilRegEnhet(Integer idnum) throws SQLException {
      // mapping: idnum --> liste med årsmeldinger for alle år for gitt idnum.
      Map<Integer, List<Arkivportalen>> alleArkivportalen = ArkivportalenCacheFactory.getArkivportalen(this.conn);
      return alleArkivportalen.get(idnum);
   }

   
   public List<Arkivportalen> getArkivportalenTilEnhetNoCache(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_arkivportalen where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<Arkivportalen> arkivportalen = new ArrayList<Arkivportalen>();
      SortedMap[] rader = result.getRows();
      
      if (rader == null || rader.length == 0) {
         return null;
      }
      
      for (SortedMap rad : rader) {
         Arkivportalen a = new Arkivportalen();
         a.setId((Integer) rad.get("id"));
         a.setIdnum((Integer) rad.get("idnum"));
         a.setTidsrom((String) rad.get("tidsrom"));
         a.setNavn((String) rad.get("navn"));
         a.setForvaltningsomrade((String) rad.get("forvaltningsomrade"));
         a.setUrl((String) rad.get("url"));
         arkivportalen.add(a);
      }

      return arkivportalen;
   }
   
   
   /**
    * Sletter en gitt årsmelding.
    * @param id
    * @throws SQLException
    */
   public void slettArkivportalen(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_arkivportalen where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny tildelingsbrev.
    * @param arkivportalen
    * @throws SQLException
    */
   public void registrerNyArkivportalen(Arkivportalen arkivportalen) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_arkivportalen (navn, tidsrom, forvaltningsomrade, url, idnum) values (?, ?, ?, ?, ?)";
      List values = new ArrayList();

      values.add(arkivportalen.getNavn());
      values.add(arkivportalen.getTidsrom());
      values.add(arkivportalen.getForvaltningsomrade());
      values.add(arkivportalen.getUrl());
      values.add(arkivportalen.getIdnum());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer tildelingsbrev.
    * @param arkivportalen
    * @throws SQLException
    */
   public void oppdaterArkivportalen(Arkivportalen arkivportalen) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_arkivportalen set "
              + "navn = ?, "
              + "tidsrom = ?, "
              + "forvaltningsomrade = ?, "
              + "url = ?, "
              + "idnum = ? "
              + "where id = ?";
      List values = new ArrayList();

      values.add(arkivportalen.getNavn());
      values.add(arkivportalen.getTidsrom());
      values.add(arkivportalen.getForvaltningsomrade());
      values.add(arkivportalen.getUrl());
      values.add(arkivportalen.getIdnum());

      values.add(arkivportalen.getId());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   

}
