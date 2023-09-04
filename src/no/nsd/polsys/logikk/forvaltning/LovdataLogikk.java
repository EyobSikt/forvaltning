package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.factories.forvaltning.LovdataCacheFactory;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Lov;

/**
 *
 * @author hvb
 */
public class LovdataLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }

   /**
    * Returnerer alle lovdata fra databasen.
    *
    * @return
    * @throws SQLException
    */
   public List<Lov> getLovdata() throws SQLException {
      List<Lov> lovdata = new ArrayList<Lov>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_lovdata order by lovnummer";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Lov lov = new Lov();
         lovdata.add(lov);
         lov.setNummer((Long) rad.get("lovnummer"));
         lov.setNavn((String) rad.get("lovnavn"));
         lov.setUrl((String) rad.get("url"));
      }
      return lovdata;
   }

   /**
    * Sletter en gitt lovdata.
    *
    * @param lovnummer
    * @throws SQLException
    */
   public void slettLovdata(Long lovnummer) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_lovdata where lovnummer = ?";
      List values = new ArrayList();

      values.add(lovnummer);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny lovdata.
    *
    * @param lov
    * @throws SQLException
    */
   public void registrerNyLovdata(Lov lov) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_lovdata (lovnummer, url, lovnavn) values (?, ?, ?)";
      List values = new ArrayList();

      values.add(lov.getNummer());
      values.add(lov.getUrl());
      values.add(lov.getNavn());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer lovdata.
    *
    * @param lov
    * @throws SQLException
    */
   public void oppdaterLovdata(Lov lov) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_lovdata set url = ?, lovnavn = ? where lovnummer = ?";
      List values = new ArrayList();

      values.add(lov.getUrl());
      values.add(lov.getNavn());

      values.add(lov.getNummer());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Sletter lovdata enhet.
    *
    * @param lovnummer
    * @param idnum
    * @throws SQLException
    */
   public void slettLovdataEnhet(Long lovnummer, Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_lovdata_enhet where lovnummer = ? and idnum = ?";
      List values = new ArrayList();

      values.add(lovnummer);
      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny lovdata.
    *
    * @param lovnummer
    * @param idnum
    * @throws SQLException
    */
   public void registrerNyLovdataEnhet(Long lovnummer, Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_lovdata_enhet (lovnummer, idnum) values (?, ?)";
      List values = new ArrayList();

      values.add(lovnummer);
      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Returnerer lover til gitt enhet hvis er registrert p� gitt idnum.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public List<Lov> getLovdataTilRegEnhet(Integer idnum) throws SQLException {
      // mapping: idnum --> liste med årsmeldinger for alle år for gitt idnum.
      Map<Integer, List<Lov>> lovdata = LovdataCacheFactory.getLovdata(this.conn);
      return lovdata.get(idnum);
   }

   /**
    * Returnerer Lovdata til en enhet inkludert etat/gruppe-lovdata.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public List<Lov> getLovdataTilEnhet(Integer idnum) throws SQLException {
      // returneres fra denne metoden.
      List<Lov> lovdata = new ArrayList<Lov>();
      // mapping: idnum --> liste med lovdata for alle idnum.
      Map<Integer, List<Lov>> alleLovdata = LovdataCacheFactory.getLovdata(this.conn);
      // Alle endringer.
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);

      Set<Lov> templover = new HashSet<Lov>();

      Enhet e = new Enhet();
      e.setIdnum(idnum);

      this.leggTilLovdata(e, endringer, templover, alleLovdata);

      Iterator<Lov> iter = templover.iterator();
      while (iter != null && iter.hasNext()) {
         lovdata.add(iter.next());
      }
      Collections.sort(lovdata);

      return lovdata;
   }

   private void leggTilLovdata(Enhet enhet, List<EndringCache> endringer, Set<Lov> templover, Map<Integer, List<Lov>> alleLovdata) {

      // finner lovdata for gitt enhet.
      List<Lov> liste = alleLovdata.get(enhet.getIdnum());
      if (liste != null) {
         templover.addAll(liste);
      }

      Set<Enhet> go = new HashSet<Enhet>();
      Integer currentG = null;

      // Finner alle overordnede grupper/etater for gitt år.
      for (EndringCache e : endringer) {
         if (e.getIdnum() == null || !e.getIdnum().equals(enhet.getIdnum())) {
            continue;
         }

         if (e.getGrunnenhet() != null) {
            currentG = e.getGrunnenhet();
         }
         if (currentG == null) {
            continue;
         }
         // grunnenhet = (11,21)
         if (e.getOverordnetIdnum() != null && (currentG.equals(11) || currentG.equals(21))) {
            Enhet recur = new Enhet();
            recur.setIdnum(e.getOverordnetIdnum());
            go.add(recur);
         }
      }

      // Kaller rekursivt enheter i go.
      for (Enhet recur : go) {
         this.leggTilLovdata(recur, endringer, templover, alleLovdata);
      }

   }
}
