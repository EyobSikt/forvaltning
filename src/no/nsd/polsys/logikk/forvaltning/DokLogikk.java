package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvb
 */
public class DokLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   
   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   /**
    * Returnerer kodedata - brukes i adminwebapp.
    *
    * @param tabellnavn
    * @return kodedata.
    * @throws SQLException
    */
   public List getData(String tabellnavn) throws SQLException {
      // liste av lister.
      List tab = new ArrayList();

      Statement st = null;
      ResultSet rs = null;

      try {
         st = this.conn.createStatement();
         rs = st.executeQuery("select * from " + tabellnavn);
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
}
