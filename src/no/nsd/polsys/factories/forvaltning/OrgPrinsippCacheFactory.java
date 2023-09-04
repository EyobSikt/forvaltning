package no.nsd.polsys.factories.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.OrgPrinsipp;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author Sikt
 */
public class OrgPrinsippCacheFactory {

   // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
   public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;
   /**
    * Mapping: idnum --> liste av instrukser for gitt idnum.
    */
   private static Map<Integer, List<OrgPrinsipp>> orgprinsipp;
   private static long oppdatert = 0;
   private Connection conn;

/*her brukte jeg ikke dette istedet bruker jeg den som finnes under OrgPrinsippLogikk . getOrgPrinsippTilEnhetNoCache */
   public static synchronized Map<Integer, List<OrgPrinsipp>> getOrgPrinsipp(Connection conn) throws SQLException {
      long tid = System.currentTimeMillis();
      boolean brukCache = true;
      if (orgprinsipp == null) {
         brukCache = false;
      }
      if ((oppdatert + CACHE_GYLDIG) < tid) {
         brukCache = false;
      }
      if (brukCache) {
         return orgprinsipp;
      }

      orgprinsipp = new HashMap<Integer, List<OrgPrinsipp>>(600, 0.95f);
      ResultSet rs = null;
      Statement stmt = null;
      String sqlSelect = "select * from t_forvaltning_organisasjonsprinsipp where orgprinsipp_id is not null order by fratidspunkt desc";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sqlSelect);

         while (rs.next()) {
            OrgPrinsipp a = new OrgPrinsipp();
            a.setId((Integer) rs.getObject("id"));
            a.setIdnum((Integer) rs.getObject("idnum"));
            a.setOrgprinsippId((Integer) rs.getObject("orgprinsipp_id"));
            a.setFraTidspunkt((Date) rs.getObject("fratidspunkt"));
            a.setTilTidspunkt((Date) rs.getObject("tiltidspunkt"));

            Result result2 = null;
            SqlCommandBean sqlCB_select = new SqlCommandBean();
            SortedMap[] rader2 = null;
            String sqlSelect_select = "select * from t_forvaltning_organisasjonsprinsipp_id where orgprinsipp_id = ? ";
            List values_select = new ArrayList();
            values_select.add((Integer) rs.getObject("orgprinsipp_id"));
            sqlCB_select.setConnection(conn);
            sqlCB_select.setSqlValue(sqlSelect_select);
            sqlCB_select.setValues(values_select);
            result2 = sqlCB_select.executeQuery();
            rader2 = result2.getRows();
            for (SortedMap rad2 : rader2) {
               a.setNorskOrgPrinsipp((String) rad2.get("orgprinsipp"));
               a.setEngelskOrgPrinsipp((String) rad2.get("eng_orgprinsipp"));
            }
            a.setKommentar((String) rs.getObject("kommentar"));
            List<OrgPrinsipp> liste = orgprinsipp.get(a.getIdnum());
            if (liste == null) {
               liste = new ArrayList<OrgPrinsipp>();
               orgprinsipp.put(a.getIdnum(), liste);
            }
            liste.add(a);
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
            }
         }
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
            }
         }
      }

      oppdatert = System.currentTimeMillis();
      return orgprinsipp;
   }
}
