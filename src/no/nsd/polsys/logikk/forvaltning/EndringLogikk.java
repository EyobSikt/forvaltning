package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EndringLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   
   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List getData(String tabellnavn, Integer idnum) throws SQLException {
      // liste av lister.
      List tab = new ArrayList();

      PreparedStatement st = null;
      ResultSet rs = null;

      try {
         st = this.conn.prepareStatement("select * from " + tabellnavn + " where idnum = ? order by tidspunkt, endringskode, id");
         st.setInt(1, idnum);
         rs = st.executeQuery();
         ResultSetMetaData meta = rs.getMetaData();

         while (rs.next()) {
            List radLabel = new ArrayList();
            List radData = new ArrayList();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
               radLabel.add(meta.getColumnName(i));
               radData.add(rs.getObject(i));
            }
            // kolonnenavn blir addet til tabell først.
            if (tab.size() == 0) {
               tab.add(radLabel);
            }
            tab.add(radData);
         }
      } finally {
         try {
            rs.close();
         } catch (Exception ignored) {
         }
         try {
            st.close();
         } catch (Exception ignored) {
         }
      }
      return tab;
   }

   public List getEndringsnummerBruk(String tabellnavn, Integer enummer) throws SQLException {
      // liste av lister.
      List tab = new ArrayList();

      PreparedStatement st = null;
      ResultSet rs = null;

      try {
         st = this.conn.prepareStatement("select * from " + tabellnavn + " where endringsnummer = ? order by tidspunkt, endringskode, id");
         st.setInt(1, enummer);
         rs = st.executeQuery();
         ResultSetMetaData meta = rs.getMetaData();

         while (rs.next()) {
            List radLabel = new ArrayList();
            List radData = new ArrayList();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
               radLabel.add(meta.getColumnName(i));
               radData.add(rs.getObject(i));
            }
            // kolonnenavn blir addet til tabell først.
            if (tab.size() == 0) {
               tab.add(radLabel);
            }
            tab.add(radData);
         }
      } finally {
         try {
            rs.close();
         } catch (Exception ignored) {
         }
         try {
            st.close();
         } catch (Exception ignored) {
         }
      }
      return tab;
   }

   public Enhet getEndring(Integer id) throws SQLException {
      Enhet enhet = new Enhet();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_endring where id = ?";
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

      SortedMap rad = rader[0];

      enhet.setIdnum((Integer) rad.get("idnum"));
      enhet.setEndringskode((Integer) rad.get("endringskode"));
      enhet.setTidspunkt((Date) rad.get("tidspunkt"));
      enhet.setBekreftetDato((Boolean) rad.get("bekr_dato"));
      enhet.setEndringsnummer((Integer) rad.get("endringsnummer"));
      enhet.setTilknytningsform((Integer) rad.get("tilknytningsform"));
      enhet.setNivaa((Integer) rad.get("nivaa"));
      enhet.setCofog((String) rad.get("cofog"));
      enhet.setKortNavn((String) rad.get("kort_navn"));
      enhet.setLangtNavn((String) rad.get("langt_navn"));
      enhet.setEngelskLangtNavn((String) rad.get("eng_langt_navn"));
      enhet.setOverordnetIdnum((Integer) rad.get("overordnet_idnum"));
      enhet.setTekniskKommentar((String) rad.get("teknisk_kommentar"));
      enhet.setDok((String) rad.get("dok"));
      enhet.setEngelskDok((String) rad.get("eng_dok"));
      enhet.setGrunnenhet((Integer) rad.get("grunnenhet"));
      enhet.setKommunenummer((Integer) rad.get("kommunenr"));
      enhet.setFlyttbar((Integer) rad.get("flyttbar"));

      return enhet;
   }

   /**
    * Sletter en gitt endring i basen.
    *
    * @param id - endringsid.
    * @throws SQLException
    */
   public void slettEndring(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_endring where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public List<int[]> getEndringsnumre() throws SQLException {
      List<int[]> endringsnumre = new ArrayList<int[]>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select n.endringsnummer, count(e.id) as antall "
              + "from t_forvaltning_endringsnummer as n "
              + "left outer join t_forvaltning_endring as e on n.endringsnummer = e.endringsnummer "
              + "group by n.endringsnummer "
              + "order by n.endringsnummer desc";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         int[] i = new int[2];
         i[0] = ((Number) rad.get("endringsnummer")).intValue();
         i[1] = ((Number) rad.get("antall")).intValue();
         endringsnumre.add(i);
      }

      return endringsnumre;
   }

   public void registrerNyttEndringsnummer() throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sql = "insert into t_forvaltning_endringsnummer default values";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.executeUpdate();
   }

   public Integer registrerNyEndring(Enhet e) throws SQLException {
      PreparedStatement st = null;
      ResultSet rs = null;

      String sql = "insert into t_forvaltning_endring ("
              + "idnum, "
              + "endringskode, "
              + "tidspunkt, "
              + "bekr_dato, "
              + "endringsnummer, "
              + "tilknytningsform, "
              + "nivaa, "
              + "cofog, "
              + "kort_navn, "
              + "langt_navn, "
              + "eng_langt_navn, "
              + "overordnet_idnum, "
              + "teknisk_kommentar, "
              + "dok, "
              + "eng_dok, "
              + "grunnenhet, "
              + "kommunenr, "
              + "flyttbar"
              + ") values (?,?,?, CAST(? AS bit(1)) ,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


      try {

         st = this.conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

         st.setObject(1, e.getIdnum());
         st.setObject(2, e.getEndringskode());
         st.setObject(3, new java.sql.Date(e.getTidspunkt().getTime()));
         st.setObject(4, e.getBekreftetDato() ? 1 : 0);
         st.setObject(5, e.getEndringsnummer());
         st.setObject(6, e.getTilknytningsform());
         st.setObject(7, e.getNivaa());
         st.setObject(8, e.getCofog());
         st.setObject(9, e.getKortNavn());
         st.setObject(10, e.getLangtNavn());
         st.setObject(11, e.getEngelskLangtNavn());
         st.setObject(12, e.getOverordnetIdnum());
         st.setObject(13, e.getTekniskKommentar());
         st.setObject(14, e.getDok());
         st.setObject(15, e.getEngelskDok());
         st.setObject(16, e.getGrunnenhet());
         st.setObject(17, e.getKommunenummer());
         st.setObject(18, e.getFlyttbar());

         st.executeUpdate();
         rs = st.getGeneratedKeys();

         if (rs != null && rs.next()) {
            Number n = (Number) rs.getObject(1);
            return (n != null ? n.intValue() : null);
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException ignored) {
            }
         }
         if (st != null) {
            try {
               st.close();
            } catch (SQLException ignored) {
            }
         }
      }
      return null;
   }

   public void oppdaterEndring(Enhet e, Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();

      String sql = "update t_forvaltning_endring set "
              + "idnum = ?, "
              + "endringskode = ?, "
              + "tidspunkt = ?, "
              + "bekr_dato = CAST(? AS bit(1)), "
              + "endringsnummer = ?, "
              + "tilknytningsform = ?, "
              + "nivaa = ?, "
              + "cofog = ?, "
              + "kort_navn = ?, "
              + "langt_navn = ?, "
              + "eng_langt_navn = ?, "
              + "overordnet_idnum = ?, "
              + "teknisk_kommentar = ?, "
              + "dok = ?, "
              + "eng_dok = ?, "
              + "grunnenhet = ?, "
              + "kommunenr = ?, "
              + "flyttbar = ? "
              + "where id = ?";

      List values = new ArrayList();

      values.add(e.getIdnum());
      values.add(e.getEndringskode());
      values.add(new java.sql.Date(e.getTidspunkt().getTime()));
      values.add(e.getBekreftetDato() ? 1 : 0);
      values.add(e.getEndringsnummer());
      values.add(e.getTilknytningsform());
      values.add(e.getNivaa());
      values.add(e.getCofog());
      values.add(e.getKortNavn());
      values.add(e.getLangtNavn());
      values.add(e.getEngelskLangtNavn());
      values.add(e.getOverordnetIdnum());
      values.add(e.getTekniskKommentar());
      values.add(e.getDok());
      values.add(e.getEngelskDok());
      values.add(e.getGrunnenhet());
      values.add(e.getKommunenummer());
      values.add(e.getFlyttbar());

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }
}
