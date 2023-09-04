package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Litteratur;
import no.nsd.polsys.modell.forvaltning.Litteraturkategori;

/**
 *
 * @author hvb
 */
public class LitteraturLogikk {

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
    * Returnerer alle publikasjoner fra databasen..
    *
    * @return
    * @throws SQLException
    */
   public List<Litteratur> getAlleLitteratur() throws SQLException {
      List<Litteratur> litteratur = new ArrayList<Litteratur>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select pub_id, tittel, aarstall from t_forvaltning_litteratur order by pub_id";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Litteratur l = new Litteratur();
         litteratur.add(l);

         l.setPubId((Integer) rad.get("pub_id"));
         l.setTittel((String) rad.get("tittel"));
         l.setAar((String) rad.get("aarstall"));
      }
      return litteratur;
   }

   /**
    * Returnerer kategorinavn for gitt kategorikode.
    *
    * @return
    * @throws SQLException
    */
   public String getKategori(Integer kategori) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select tekst from t_forvaltning_litteratur_kategoritype where kategori = ?";

      List values = new ArrayList();

      values.add(kategori);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      return (String) rader[0].get("tekst");
   }

   /**
    * Returnerer kodenavn for gitt kategorikode og kode.
    *
    * @return
    * @throws SQLException
    */
   public String getKode(Integer kategori, Integer kode) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select tekst_entall from t_forvaltning_litteratur_kategoriverdi where kategori = ? and kode = ?";

      List values = new ArrayList();

      values.add(kategori);
      values.add(kode);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      return (String) rader[0].get("tekst_entall");
   }

   /**
    * Returnerer all litteratur (alle publikasjoner) til en enhet.
    *
    * @param dag
    * @return
    * @throws SQLException
    */
   public List<Litteratur> getLitteraturTilEnhet(Integer idnum) throws SQLException {
      // returneres fra denne metoden.
      List<Litteratur> litteratur = new ArrayList<Litteratur>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct l.pub_id, l.tittel, l.forfatter, l.aarstall, t.kode, t.tekst_entall, t.tekst_flertall, e.fra_aar, e.til_aar "
              + "from (t_forvaltning_litteratur_enhet as e "
              + "inner join t_forvaltning_litteratur as l on e.pub_id = l.pub_id) "
              + "left join t_forvaltning_litteratur_type as t on l.type_kode = t.kode where e.idnum = ? "
              + "order by t.tekst_flertall, l.aarstall desc";

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
         Litteratur l = new Litteratur();
         DokCache type = new DokCache();
         l.setType(type);
         litteratur.add(l);

         l.setPubId((Integer) rad.get("pub_id"));
         l.setTittel((String) rad.get("tittel"));
         l.setForfatter((String) rad.get("forfatter"));
         l.setAar((String) rad.get("aarstall"));
         type.setKode((Integer) rad.get("kode"));
         type.setTekstEntall((String) rad.get("tekst_entall"));
         type.setTekstFlertall((String) rad.get("tekst_flertall"));
         l.setFraAar((Integer) rad.get("fra_aar"));
         l.setTilAar((Integer) rad.get("til_aar"));
      }
      return litteratur;
   }

   /**
    * Returnerer en bestemt litteratur/publikasjon.
    *
    * @param dag
    * @return
    * @throws SQLException
    */
   public Litteratur getLitteratur(Integer pubId) throws SQLException {
      // returneres fra denne metoden.
      Litteratur litteratur = new Litteratur();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from "
              + "t_forvaltning_litteratur as l left outer join t_forvaltning_litteratur_type as t on l.type_kode = t.kode "
              + "where l.pub_id = ?";

      List values = new ArrayList();

      values.add(pubId);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      SortedMap rad = rader[0];

      litteratur.setPubId((Integer) rad.get("pub_id"));
      litteratur.setTittel((String) rad.get("tittel"));
      litteratur.setUtgiver((String) rad.get("utgiver"));
      litteratur.setAar((String) rad.get("aarstall"));
      litteratur.setForfatter((String) rad.get("forfatter"));
      litteratur.setLenkePublikasjon((String) rad.get("lenke_publikasjon"));
      litteratur.setLenkeOmtale((String) rad.get("lenke_omtale"));
      litteratur.setKommentarEkstern((String) rad.get("kommentar_ekstern"));
      litteratur.setKommentarIntern((String) rad.get("kommentar_intern"));
      litteratur.setSisteRegTid((Date) rad.get("siste_reg_tid"));
      litteratur.setAntallSider((Integer) rad.get("antall_sider"));
      litteratur.setIsbn((String) rad.get("isbn"));
      litteratur.setIssn((String) rad.get("issn"));
      litteratur.setSpraak((String) rad.get("spraak"));
      litteratur.setLand((String) rad.get("land"));
      litteratur.setSammendrag((String) rad.get("sammendrag"));
      litteratur.setTekstfeltUtl((String) rad.get("tekstfelt_utl"));

      Litteratur ref = new Litteratur();
      litteratur.setReferanse(ref);
      ref.setPubId((Integer) rad.get("ref_pub_id"));

      DokCache type = new DokCache();
      litteratur.setType(type);
      type.setKode((Integer) rad.get("kode"));
      type.setTekstEntall((String) rad.get("tekst_entall"));
      type.setTekstFlertall((String) rad.get("tekst_flertall"));

      return litteratur;
   }

   /**
    * Returnerer kategorisering >= 5 til en gitt publikasjon.
    *
    * @param pubId
    * @return
    * @throws SQLException
    */
   public SortedMap<Integer, List<String>> getKategorisering(Integer pubId) throws SQLException {
      // Mapping: kategori --> liste med kategoriverdier.
      SortedMap<Integer, List<String>> kategorisering = new TreeMap<Integer, List<String>>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select k.kategori, v.tekst_entall from "
              + "t_forvaltning_litteratur_kategorisering as k "
              + "inner join t_forvaltning_litteratur_kategoriverdi as v "
              + "on k.kategori = v.kategori and k.kode = v.kode "
              + "where k.kategori >= 5 and k.pub_id = ? "
              + "order by k.kategori";

      List values = new ArrayList();

      values.add(pubId);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer kode = (Integer) rad.get("kategori");
         String verdi = (String) rad.get("tekst_entall");

         List<String> verdier = kategorisering.get(kode);
         if (verdier == null) {
            verdier = new ArrayList<String>();
            kategorisering.put(kode, verdier);
         }
         verdier.add(verdi);
      }
      return kategorisering;
   }

   /**
    * Returnerer alle enheter til gitt publikasjon.
    *
    * @param pubId
    * @return
    * @throws SQLException
    */
   public List<Litteratur> getEnheterTilLitteratur(Integer pubId) throws SQLException {
      List<Litteratur> klassifisering = new ArrayList<Litteratur>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_litteratur_enhet where pub_id = ?";

      List values = new ArrayList();

      values.add(pubId);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      List<EndringCache> alleEndringer = EndringCacheFactory.getEndringer(this.conn);
      EnhetLogikk enhetLogikk = new EnhetLogikk();

      for (SortedMap rad : rader) {
         Litteratur l = new Litteratur();
         Enhet e = new Enhet();

         e.setIdnum((Integer) rad.get("idnum"));
         l.setFraAar((Integer) rad.get("fra_aar"));
         l.setTilAar((Integer) rad.get("til_aar"));
         l.setEnhet(e);
         enhetLogikk.getEnhetNavn(e, alleEndringer);
         klassifisering.add(l);
      }
      return klassifisering;
   }

   /**
    * Returnerer alle tilknytningsformer til gitt publikasjon.
    *
    * @param pubId
    * @return
    * @throws SQLException
    */
   public List<Litteratur> getTilknytningsformerTilLitteratur(Integer pubId) throws SQLException {
      List<Litteratur> klassifisering = new ArrayList<Litteratur>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select l.tilknytningsform, t.tekst_entall, l.fra_aar, l.til_aar "
              + "from t_forvaltning_litteratur_tilknytningsform as l "
              + "left join t_forvaltning_tilknytningsform as t on l.tilknytningsform = t.kode "
              + "where l.pub_id = ?";

      List values = new ArrayList();

      values.add(pubId);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Litteratur l = new Litteratur();
         DokCache t = new DokCache();

         t.setKode((Integer) rad.get("tilknytningsform"));
         t.setTekstEntall((String) rad.get("tekst_entall"));
         l.setFraAar((Integer) rad.get("fra_aar"));
         l.setTilAar((Integer) rad.get("til_aar"));
         l.setTilknytningsform(t);
         klassifisering.add(l);
      }
      return klassifisering;
   }

   /**
    * Returnerer alle publikasjoner for gitt tilknytningsform.
    *
    * @param tilknytningsform
    * @return
    * @throws SQLException
    */
   public List<Litteratur> getLitteraturTilTilknytningsform(Integer tilknytningsform) throws SQLException {
      List<Litteratur> litteratur = new ArrayList<Litteratur>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_litteratur_tilknytningsform as t "
              + "inner join t_forvaltning_litteratur as l on l.pub_id = t.pub_id "
              + "left join t_forvaltning_litteratur_type as ty on l.type_kode = ty.kode "
              + "where t.tilknytningsform = ? "
              + "order by l.type_kode, l.aarstall";

      List values = new ArrayList();

      values.add(tilknytningsform);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Litteratur l = new Litteratur();
         DokCache type = new DokCache();
         l.setType(type);
         litteratur.add(l);

         l.setPubId((Integer) rad.get("pub_id"));
         l.setAar((String) rad.get("aarstall"));
         l.setTittel((String) rad.get("tittel"));
         l.setForfatter((String) rad.get("forfatter"));
         l.setFraAar((Integer) rad.get("fra_aar"));
         l.setTilAar((Integer) rad.get("til_aar"));
         type.setKode((Integer) rad.get("kode"));
         type.setTekstEntall((String) rad.get("tekst_entall"));
      }
      return litteratur;
   }

   /**
    * Returnerer alle publikasjoner for gitt kategori og kode.
    *
    * @param kategori
    * @param kode
    * @return
    * @throws SQLException
    */
   public List<Litteratur> getLitteraturTilKode(Integer kategori, Integer kode) throws SQLException {
      List<Litteratur> litteratur = new ArrayList<Litteratur>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select l.pub_id, l.aarstall, l.tittel, l.forfatter, t.fra_aar, t.til_aar, ty.kode, ty.tekst_entall "
              + "from t_forvaltning_litteratur_kategorisering as t "
              + "inner join t_forvaltning_litteratur as l on l.pub_id = t.pub_id "
              + "left join t_forvaltning_litteratur_type as ty on l.type_kode = ty.kode "
              + "where t.kategori = ? and t.kode = ? "
              + "order by l.type_kode, l.aarstall";

      List values = new ArrayList();

      values.add(kategori);
      values.add(kode);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Litteratur l = new Litteratur();
         DokCache type = new DokCache();
         l.setType(type);
         litteratur.add(l);

         l.setPubId((Integer) rad.get("pub_id"));
         l.setAar((String) rad.get("aarstall"));
         l.setTittel((String) rad.get("tittel"));
         l.setForfatter((String) rad.get("forfatter"));
         l.setFraAar((Integer) rad.get("fra_aar"));
         l.setTilAar((Integer) rad.get("til_aar"));
         type.setKode((Integer) rad.get("kode"));
         type.setTekstEntall((String) rad.get("tekst_entall"));
      }
      return litteratur;
   }

   /**
    * Returnerer alle verdier til gitt kategori, med antall publikasjoner.
    *
    * @param kategori
    * @return
    * @throws SQLException
    */
   public List<Litteraturkategori> getKategoriverdier(Integer kategori) throws SQLException {
      List<Litteraturkategori> data = new ArrayList<Litteraturkategori>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select k.kode, v.tekst_entall, v.dokumentasjon, count(k.pub_id) as antall "
              + "from t_forvaltning_litteratur_kategorisering as k "
              + "inner join t_forvaltning_litteratur_kategoriverdi as v "
              + "on k.kategori = v.kategori and k.kode = v.kode "
              + "where k.kategori = ? "
              + "group by k.kode, v.tekst_entall, v.dokumentasjon "
              + "order by k.kode ";

      List values = new ArrayList();

      values.add(kategori);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Litteraturkategori l = new Litteraturkategori();
         data.add(l);

         l.setKode((Integer) rad.get("kode"));
         l.setKategori((String) rad.get("tekst_entall"));
         l.setDokumentasjon((String) rad.get("dokumentasjon"));
         //l.setAntall((Integer) rad.get("antall"));
         l.setAntall((int) ((long)rad.get("antall")));
      }
      return data;
   }

   /**
    * Returnerer alle litteraturtyper fra databasen.
    *
    * @return
    * @throws SQLException
    */
   public List<DokCache> getLitteraturTyper() throws SQLException {
      List<DokCache> typer = new ArrayList<DokCache>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_litteratur_type order by kode";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         DokCache t = new DokCache();
         typer.add(t);

         t.setKode((Integer) rad.get("kode"));
         t.setTekstEntall((String) rad.get("tekst_entall"));
      }
      return typer;
   }

   /**
    * Returnerer alle litteraturtilknytningsformer fra databasen.
    *
    * @return
    * @throws SQLException
    */
   public List<Litteraturkategori> getLitteraturTilknytningsformer() throws SQLException {
      List<Litteraturkategori> kat = new ArrayList<Litteraturkategori>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select tilknytningsform, count(pub_id) as antall "
              + "from t_forvaltning_litteratur_tilknytningsform "
              + "group by tilknytningsform "
              + "order by tilknytningsform";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Litteraturkategori lk = new Litteraturkategori();
         kat.add(lk);

         lk.setKode((Integer) rad.get("tilknytningsform"));
         /*lk.setAntall((Integer) rad.get("antall"));*/
         lk.setAntall((int)((long)rad.get("antall")));
      }
      return kat;
   }

   /**
    * Returnerer publikasjoner søket treffer på.
    *
    * @param s
    * @return
    * @throws SQLException
    */
   public List<Litteratur> sokLitteratur(String s) throws SQLException {
      List<Litteratur> litteratur = new ArrayList<Litteratur>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select l.pub_id, l.tittel, l.forfatter, l.aarstall, t.kode, t.tekst_entall, t.tekst_flertall "
              + "from t_forvaltning_litteratur as l "
              + "left join t_forvaltning_litteratur_type as t on l.type_kode = t.kode "
              + "where "
              + "lower(tittel) like lower(?) "
              + "or lower(utgiver) like lower(?) "
              + "or lower(forfatter) like lower(?) "
              + "or lower(kommentar_ekstern) like lower(?) "
              + "or lower(sammendrag) like lower(?) "
              + "or lower(tekstfelt_utl) like lower(?) "
              + "order by t.kode, l.aarstall";

      List values = new ArrayList();

      values.add("%" + s + "%");
      values.add("%" + s + "%");
      values.add("%" + s + "%");
      values.add("%" + s + "%");
      values.add("%" + s + "%");
      values.add("%" + s + "%");

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Litteratur l = new Litteratur();
         DokCache type = new DokCache();
         l.setType(type);

         l.setPubId((Integer) rad.get("pub_id"));
         l.setTittel((String) rad.get("tittel"));
         l.setForfatter((String) rad.get("forfatter"));
         l.setAar((String) rad.get("aarstall"));
         type.setKode((Integer) rad.get("kode"));
         type.setTekstEntall((String) rad.get("tekst_entall"));
         type.setTekstFlertall((String) rad.get("tekst_flertall"));

         litteratur.add(l);
      }
      return litteratur;
   }

   /**
    * Oppretter en ny litteratur i databasen og returnerer den nye pub_id.
    *
    * @throws java.sql.SQLException
    */
   public Integer registrerNyLitteratur() throws SQLException {
      Statement st = null;
      ResultSet rs = null;
      String sql = "insert into t_forvaltning_litteratur default values";

      /**
       Result result = null;
       SqlCommandBean sqlCB = new SqlCommandBean();
       SortedMap[] rader = null;
       //String sqlselect = "select MAX(pub_id) as eyob FROM t_forvaltning_litteratur";
       //String sqlselect = "select nextval('t_forvaltning_litteratur_pub_id_seq') as eyob";
       String sqlselect = "SELECT setval('t_forvaltning_litteratur_pub_id_seq', COALESCE((SELECT MAX(pub_id)+1 FROM t_forvaltning_litteratur), 1), false);";
       sqlCB.setConnection(this.conn);
       sqlCB.setSqlValue(sqlselect);
       result = sqlCB.executeQuery();
       rader = result.getRows();
       for (SortedMap rad : rader) {
       System.out.println("RRRRRRRRRRRR" );
       }
       * **/

      try {
         st = this.conn.createStatement();
         st.execute(sql, Statement.RETURN_GENERATED_KEYS);
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

   public void oppdaterLitteratur(Litteratur l) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();

      String sql = "update t_forvaltning_litteratur set "
              + "type_kode = ?, "
              + "tittel = ?, "
              + "utgiver = ?, "
              + "aarstall = ?, "
              + "forfatter = ?, "
              + "lenke_publikasjon = ?, "
              + "lenke_omtale = ?, "
              + "kommentar_ekstern = ?, "
              + "kommentar_intern = ?, "
              + "siste_reg_tid = CURRENT_TIMESTAMP, "
              + "ref_pub_id = ?, "
              + "antall_sider = ?, "
              + "isbn = ?, "
              + "issn = ?, "
              + "spraak = ?, "
              + "land = ?, "
              + "sammendrag = ?, "
              + "tekstfelt_utl = ? "
              + "where pub_id = ?";

      List values = new ArrayList();

      values.add(l.getType().getKode());
      values.add(l.getTittel());
      values.add(l.getUtgiver());
      values.add(l.getAar());
      values.add(l.getForfatter());
      values.add(l.getLenkePublikasjon());
      values.add(l.getLenkeOmtale());
      values.add(l.getKommentarEkstern());
      values.add(l.getKommentarIntern());
      values.add(l.getReferanse().getPubId());
      values.add(l.getAntallSider());
      values.add(l.getIsbn());
      values.add(l.getIssn());
      values.add(l.getSpraak());
      values.add(l.getLand());
      values.add(l.getSammendrag());
      values.add(l.getTekstfeltUtl());

      values.add(l.getPubId());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void registrerNyLitteraturEnhet(Litteratur litteratur, Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();

      String sql = "insert into t_forvaltning_litteratur_enhet (pub_id, idnum, fra_aar, til_aar, reg_tidspunkt) values (?,?,?,?, CURRENT_TIMESTAMP )";

      List values = new ArrayList();
      values.add(litteratur.getPubId());
      values.add(idnum);
      values.add(litteratur.getFraAar());
      values.add(litteratur.getTilAar());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void slettLitteraturEnhet(Integer pubId, Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();

      String sql = "delete from t_forvaltning_litteratur_enhet where pub_id = ? and idnum = ?";

      List values = new ArrayList();
      values.add(pubId);
      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void registrerNyLitteraturTilknytningsform(Litteratur litteratur, Integer tilknytningsform) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();

      String sql = "insert into t_forvaltning_litteratur_tilknytningsform (pub_id, tilknytningsform, fra_aar, til_aar, reg_tidspunkt) values (?,?,?,?, CURRENT_TIMESTAMP )";

      List values = new ArrayList();
      values.add(litteratur.getPubId());
      values.add(tilknytningsform);
      values.add(litteratur.getFraAar());
      values.add(litteratur.getTilAar());

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void slettLitteraturTilknytningsform(Integer pubId, Integer tilknytningsform) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();

      String sql = "delete from t_forvaltning_litteratur_tilknytningsform where pub_id = ? and tilknytningsform = ?";

      List values = new ArrayList();
      values.add(pubId);
      values.add(tilknytningsform);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sql);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }
}
