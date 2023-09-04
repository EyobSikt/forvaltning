package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.OrgPrinsippCacheFactory;
import no.nsd.polsys.modell.forvaltning.OrgPrinsipp;
import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Logikk-klasse for å hente ut og oppdatere info om OrgPrinsipp til enhetene.
 *
 */
public class OrgPrinsippLogikk {

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
    * Returnerer OrgPrinsipp som er registrert på gitt idnum.
    * @param idnum gitt idnum til enhet.
    * @return liste med OrgPrinsipp.
    * @throws SQLException
    */
   public List<OrgPrinsipp> getOrgPrinsippTilRegEnhet(Integer idnum) throws SQLException {
      // mapping: idnum --> liste med OrgPrinsipp for alle år for gitt idnum.
      Map<Integer, List<OrgPrinsipp>> alleOrgPrinsipp = OrgPrinsippCacheFactory.getOrgPrinsipp(this.conn);
      return alleOrgPrinsipp.get(idnum);
   }

   
   public List<OrgPrinsipp> getOrgPrinsippTilEnhetNoCache(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_organisasjonsprinsipp where idnum = ? order by fratidspunkt desc ";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<OrgPrinsipp> orgprinsipp = new ArrayList<OrgPrinsipp>();
      SortedMap[] rader = result.getRows();
      
      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         OrgPrinsipp a = new OrgPrinsipp();
         a.setId((Integer) rad.get("id"));
         a.setIdnum((Integer) rad.get("idnum"));
         a.setOrgprinsippId((Integer) rad.get("orgprinsipp_id"));

         Result result2 = null;
         SqlCommandBean sqlCB_select = new SqlCommandBean();
         SortedMap[] rader2 = null;
         String sqlSelect_select = "select * from t_forvaltning_organisasjonsprinsipp_id where orgprinsipp_id = ? ";
         List values_select = new ArrayList();
         values_select.add((Integer) rad.get("orgprinsipp_id"));
         sqlCB_select.setConnection(this.conn);
         sqlCB_select.setSqlValue(sqlSelect_select);
         sqlCB_select.setValues(values_select);
         result2 = sqlCB_select.executeQuery();
         rader2 = result2.getRows();

         for (SortedMap rad2 : rader2) {
            a.setNorskOrgPrinsipp((String) rad2.get("orgprinsipp"));
            a.setEngelskOrgPrinsipp((String) rad2.get("eng_orgprinsipp"));
         }
         a.setFraTidspunkt((Date) rad.get("fratidspunkt"));
         a.setTilTidspunkt((Date) rad.get("tiltidspunkt"));
         a.setKommentar((String) rad.get("kommentar"));
         orgprinsipp.add(a);
      }

      return orgprinsipp;
   }
   
   
   /**
    * Sletter en gitt instruks.
    * @param id
    * @throws SQLException
    */
   public void slettOrgPrinsipp(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_organisasjonsprinsipp where id = ?";
      List values = new ArrayList();
      values.add(id);
      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer ny instrukser.
    * @param e
    * @throws SQLException
    */
   public Integer registrerNyOrgPrinsipp(OrgPrinsipp e) throws SQLException {
     /* SqlCommandBean sqlCB = new SqlCommandBean();*/
      PreparedStatement st = null;
      ResultSet rs = null;
      String sql = "insert into t_forvaltning_organisasjonsprinsipp (idnum, orgprinsipp_id, fratidspunkt, tiltidspunkt, kommentar) values (?, ?, ?, ?, ?)";

      try {
        st = this.conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

         st.setObject(1, e.getIdnum());
         st.setObject(2, e.getOrgprinsippId());
         if(e.getFraTidspunkt()!=null) {
         st.setObject(3, new java.sql.Date(e.getFraTidspunkt().getTime()));
      }
         else{
            st.setObject(3, null);
         }
         if(e.getTilTidspunkt()!=null) {
            st.setObject(4, new java.sql.Date(e.getTilTidspunkt().getTime()));
         }
         else{
            st.setObject(4, null);
         }
         st.setObject(5, e.getKommentar());


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

   /**
    * Oppdaterer instruks.
    * @param e
    * @throws SQLException
    */
   public Integer oppdaterOrgPrinsipp(OrgPrinsipp e) throws SQLException {
      PreparedStatement st = null;
      ResultSet rs = null;
      String sql = "update t_forvaltning_organisasjonsprinsipp set "
              + "idnum = ?, "
              + "orgprinsipp_id = ?, "
              + "fratidspunkt = ?, "
              + "tiltidspunkt = ?, "
              + "kommentar = ? "
              + "where id = ?";

      try {
         st = this.conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
         st.setObject(1, e.getIdnum());
         st.setObject(2, e.getOrgprinsippId());
         if(e.getFraTidspunkt()!=null) {
            st.setObject(3, new java.sql.Date(e.getFraTidspunkt().getTime()));
         }
         else{
            st.setObject(3, null);
         }
         if(e.getTilTidspunkt()!=null) {
            st.setObject(4, new java.sql.Date(e.getTilTidspunkt().getTime()));
         }
         else{
            st.setObject(4, null);
         }
         st.setObject(5, e.getKommentar());
         st.setObject(6, e.getId());

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

/** This method gets all orgprinsip from t_forvaltning_organisasjonsprinsipp_id table **/
   public List<OrgPrinsipp> getAllOrgPrinsippTilEnhetNoCache() throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_organisasjonsprinsipp_id order by id";
      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      Result result = sqlCB.executeQuery();
      List<OrgPrinsipp> allorgprinsipp = new ArrayList<OrgPrinsipp>();
      SortedMap[] rader = result.getRows();
      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         OrgPrinsipp a = new OrgPrinsipp();
          a.setId((Integer) rad.get("id"));
         a.setOrgprinsippId((Integer) rad.get("orgprinsipp_id"));
         a.setNorskOrgPrinsipp((String) rad.get("orgprinsipp"));
         a.setEngelskOrgPrinsipp((String) rad.get("eng_orgprinsipp"));
         a.setKommentar((String) rad.get("kommentar"));
         allorgprinsipp.add(a);
      }
      return allorgprinsipp;
   }

}
