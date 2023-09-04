package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class TallgruppeLogikk {

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
    * Returnerer om en enhet har tallgrupper.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public boolean harTallgrupper(Integer idnum) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      Object[][] data = null;
      String sqlSelect = "select count(*) from t_forvaltning_tallgruppe_desentralisert where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      data = result.getRowsByIndex();

      if (data == null || data.length == 0 || data[0].length == 0) {
         return false;
      }

      Number n = (Number) data[0][0];
      return (n == null ? false : n.intValue() > 0);
   }

   /**
    * Returnerer om en enhet har satellitt.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public boolean harSatellitt(Integer idnum) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      Object[][] data = null;
      String sqlSelect = "select count(*) from t_forvaltning_satellitt where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      data = result.getRowsByIndex();

      if (data == null || data.length == 0 || data[0].length == 0) {
         return false;
      }

      Number n = (Number) data[0][0];
      return (n == null ? false : n.intValue() > 0);
   }

   /**
    * Returnerer alle tallgrupper til en enhet.
    *
    * @param dag
    * @return
    * @throws SQLException
    */
   public List<Tallgruppe> getTallgrupper(Integer idnum) throws SQLException {
      List<Tallgruppe> tallgrupper = new ArrayList<Tallgruppe>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_tallgruppe_desentralisert where idnum = ? order by lpnr_idnum, aar, fra_tidspunkt";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Tallgruppe tallgruppe = new Tallgruppe();

         tallgruppe.setTallgruppe(true);
         tallgruppe.setId((Integer) rad.get("id"));
         tallgruppe.setIdnum((Integer) rad.get("idnum"));
         tallgruppe.setLpnr((Integer) rad.get("lpnr_idnum"));
         tallgruppe.setTallgruppekode((Integer) rad.get("tallgruppe"));
         if (this.engelsk) {
            tallgruppe.setNavn((String) rad.get("eng_navn"));
         } else {
            tallgruppe.setNavn((String) rad.get("navn"));
         }
         tallgruppe.setAar((Integer) rad.get("aar"));
         tallgruppe.setEnheterKartlagt((Integer) rad.get("enheter_kartlagt"));
         tallgruppe.setAntallEnheter((Integer) rad.get("antall_enheter"));
         tallgruppe.setFraTidspunkt((Date) rad.get("fra_tidspunkt"));
         tallgruppe.setTilTidspunkt((Date) rad.get("til_tidspunkt"));

         tallgrupper.add(tallgruppe);
      }
      return tallgrupper;
   }

   /**
    * Returnerer en bestemt tallgruppe.
    *
    * @param id
    * @return
    * @throws SQLException
    */
   public Tallgruppe getTallgruppe(Integer id) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_tallgruppe_desentralisert where id = ?";
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
      Tallgruppe tallgruppe = new Tallgruppe();

      tallgruppe.setTallgruppe(true);
      tallgruppe.setId((Integer) rad.get("id"));
      tallgruppe.setIdnum((Integer) rad.get("idnum"));
      tallgruppe.setLpnr((Integer) rad.get("lpnr_idnum"));
      tallgruppe.setTallgruppekode((Integer) rad.get("tallgruppe"));
      if (this.engelsk) {
         tallgruppe.setNavn((String) rad.get("eng_navn"));
         tallgruppe.setDokumentasjon((String) rad.get("eng_dokumentasjon"));
         tallgruppe.setKommentarEndring((String) rad.get("eng_kommentar_endring"));
      } else {
         tallgruppe.setNavn((String) rad.get("navn"));
         tallgruppe.setDokumentasjon((String) rad.get("dokumentasjon"));
         tallgruppe.setKommentarEndring((String) rad.get("kommentar_endring"));
      }
      tallgruppe.setAar((Integer) rad.get("aar"));
      tallgruppe.setEnheterKartlagt((Integer) rad.get("enheter_kartlagt"));
      tallgruppe.setAntallEnheter((Integer) rad.get("antall_enheter"));
      tallgruppe.setFraTidspunkt((Date) rad.get("fra_tidspunkt"));
      tallgruppe.setTilTidspunkt((Date) rad.get("til_tidspunkt"));

      tallgruppe.setPlussEnhet((String) rad.get("pluss_enhet"));
      tallgruppe.setMinusEnhet((String) rad.get("minus_enhet"));
      tallgruppe.setKommentarKilde((String) rad.get("kommentar_kilde"));
      tallgruppe.setKommentarAnnet((String) rad.get("kommentar_annet"));
      tallgruppe.setKommentarNote((String) rad.get("kommentar_note"));

      tallgruppe.setForrigeAar((Integer) rad.get("forrige_aar"));
      tallgruppe.setNesteAar((Integer) rad.get("neste_aar"));

      return tallgruppe;
   }

   /**
    * Returnerer alle satellitter til en enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public List<Tallgruppe> getSatellitter(Integer idnum) throws SQLException {
      List<Tallgruppe> tallgrupper = new ArrayList<Tallgruppe>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_satellitt where idnum = ? order by fra_tidspunkt, navn";
      List<Kommune> alleKommuner = KommuneCacheFactory.getKommuner(this.conn);
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Tallgruppe tallgruppe = new Tallgruppe();

         tallgruppe.setTallgruppe(true);
         tallgruppe.setId((Integer) rad.get("id"));
         tallgruppe.setIdnum((Integer) rad.get("idnum"));
         tallgruppe.setEngelskNavn((String) rad.get("eng_navn"));
         tallgruppe.setNavn((String) rad.get("navn"));
         tallgruppe.setFraTidspunkt((Date) rad.get("fra_tidspunkt"));
         tallgruppe.setTilTidspunkt((Date) rad.get("til_tidspunkt"));
         if (this.engelsk) {
            tallgruppe.setDokumentasjon((String) rad.get("eng_dokumentasjon"));
         } else {
            tallgruppe.setDokumentasjon((String) rad.get("dokumentasjon"));
            tallgruppe.setEngelskDokumentasjon((String) rad.get("eng_dokumentasjon"));
         }
         Integer kommunenr = (Integer) rad.get("kommunenr");

         if (kommunenr != null) {
            Kommune k = new Kommune();
            k.setKode(kommunenr);
            Kommune komplett = this.getKommune(alleKommuner, kommunenr, tallgruppe.getTilTidspunkt());
            if (komplett != null) {
               tallgruppe.setKommune(komplett);
            } else {
               tallgruppe.setKommune(k);
            }
         }

         tallgrupper.add(tallgruppe);
      }
      return tallgrupper;
   }

   /**
    * Returnerer alle ansattekoblinger til gitt satellitt.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public String getSatellittAnsatteKobling(Integer satellittId) throws SQLException {
      String koder = "";
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select ansatte_etatskode from t_forvaltning_satellitt_ansatte_kobling where satellitt_id = ? order by ansatte_etatskode";
      List values = new ArrayList();

      values.add(satellittId);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      koder = (String) rader[0].get("ansatte_etatskode");

      for (int i = 1; i < rader.length; i++) {
         koder += " ";
         koder += (String) rader[i].get("ansatte_etatskode");
      }

      return koder;
   }

   private Kommune getKommune(List<Kommune> alleKommuner, Integer kommunenr, Date date) {
      int aar = -1;
      if (date != null) {
         Calendar cal = new GregorianCalendar();
         cal.setTime(date);
         aar = cal.get(Calendar.YEAR);
      }
      for (Kommune k : alleKommuner) {
         if (aar != -1) {
            if (k.getFomAar() != null && k.getFomAar() > aar) {
               continue;
            }
            if (k.getTomAar() != null && k.getTomAar() < aar) {
               continue;
            }
         }
         if (kommunenr.equals(k.getKode())) {
            return k;
         }
      }
      return null;
   }

   /**
    * Returnerer antall desentraliserte enheter på gitt år.
    *
    * @param tallgruppe, enten 21 eller 31.
    * @param aar
    * @return
    * @throws SQLException
    */
   public Integer getAntallDesentraliserte(Integer tallgruppe, Integer aar) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      Object[][] data = null;
      String sqlSelect = "select sum(antall_enheter) from t_forvaltning_tallgruppe_desentralisert where tallgruppe = ? and aar = ?";
      List values = new ArrayList();

      values.add(tallgruppe);
      values.add(aar);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      data = result.getRowsByIndex();

      if (data == null || data.length == 0) {
         return 0;
      }

      Number n = (Number) data[0][0];
      return (n != null ? n.intValue() : 0);
   }

   /**
    * Returnerer antall satellitter på gitt år.
    *
    * @param aar
    * @return
    * @throws SQLException
    */
   public Integer getAntallSatellitter(Date dato) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      Object[][] data = null;
      String sqlSelect = "select count(*) from t_forvaltning_satellitt "
              + "where (fra_tidspunkt is null or fra_tidspunkt <= ?) and (til_tidspunkt is null or til_tidspunkt >= ?)";
      List values = new ArrayList();

      values.add(new java.sql.Date(dato.getTime()));
      values.add(new java.sql.Date(dato.getTime()));

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      data = result.getRowsByIndex();

      if (data == null || data.length == 0) {
         return 0;
      }

      Number n = (Number) data[0][0];
      return (n != null ? n.intValue() : 0);
   }

   /**
    * Registrerer en ny satellitt i databasen og returnerer den nye id.
    *
    * @throws java.sql.SQLException
    */
   public Integer registrerNySatellitt(Integer idnum) throws SQLException {
      PreparedStatement st = null;
      ResultSet rs = null;
      String sql = "insert into t_forvaltning_satellitt (idnum) values (?)";

      try {
         st = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
         st.setInt(1, idnum);
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
            } catch (SQLException e) {
            }
         }
         if (st != null) {
            try {
               st.close();
            } catch (SQLException e) {
            }
         }
      }
      return null;
   }

   public void oppdaterSatellitt(Tallgruppe s) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();

      String sql = "update t_forvaltning_satellitt set "
              + "fra_tidspunkt = ?, "
              + "til_tidspunkt = ?, "
              + "navn = ?, "
              + "eng_navn = ?, "
              + "kommunenr = ?, "
              + "dokumentasjon = ?, "
              + "eng_dokumentasjon = ? "
              + "where id = ?";

      List values = new ArrayList();

      values.add((s.getFraTidspunkt() != null) ? new java.sql.Date(s.getFraTidspunkt().getTime()) : null);
      values.add((s.getTilTidspunkt() != null) ? new java.sql.Date(s.getTilTidspunkt().getTime()) : null);
      values.add(s.getNavn());
      values.add(s.getEngelskNavn());
      values.add(s.getKommune().getKode());
      values.add(s.getDokumentasjon());
      values.add(s.getEngelskDokumentasjon());

      values.add(s.getId());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   
   public void slettSatellitt(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sql = "delete from t_forvaltning_satellitt where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void slettAnsatteKoblingTilSatellitt(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_satellitt_ansatte_kobling where satellitt_id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void registrerNyAnsatteKoblingTilSatellitt(Integer id, String kode) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_satellitt_ansatte_kobling (satellitt_id, ansatte_etatskode) values (?, ?)";
      List values = new ArrayList();

      values.add(id);
      values.add(kode);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }
}
