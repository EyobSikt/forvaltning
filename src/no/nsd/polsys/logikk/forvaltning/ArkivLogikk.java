package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.ArkivEnhet;

/**
 * Logikk for å hente ut lenker til arkivportalen.no.
 * @author hvb
 */
public class ArkivLogikk {

   
   private Connection conn;

   
   public void setConn(Connection conn) {
      this.conn = conn;
   }
   
   
   public List<ArkivEnhet> getArkivEnheter(Integer idnum) throws SQLException {
      List<ArkivEnhet> arkivenheter = new ArrayList<ArkivEnhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_arkivportalen where idnum = ? or idnum2 = ? or idnum3 = ?";

      List<Integer> values = new ArrayList<Integer>();
      
      values.add(idnum);
      values.add(idnum);
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
         ArkivEnhet a = new ArkivEnhet();
         arkivenheter.add(a);

         a.setId((Integer) rad.get("id"));
         a.setNavn((String) rad.get("navn"));
         a.setTidsrom((String) rad.get("tidsrom"));
         a.setForvaltningsomrade((String) rad.get("forvaltningsomrade"));
         a.setUrl((String) rad.get("url"));
      }
      return arkivenheter;
   }
   
   

   
}
