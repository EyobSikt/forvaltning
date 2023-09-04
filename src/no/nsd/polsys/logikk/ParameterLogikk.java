package no.nsd.polsys.logikk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.Dato;

/**
 *
 * @author hvb
 */
public class ParameterLogikk {

   /**
    * Forbindelse til databasen.
    */
   private Connection conn;

   public void setConn(Connection conn) {
      this.conn = conn;
   }

   /**
    * Returnerer alle parametre i basen. Listen inneholder tabell på formen:
    * [parameternavn, parameterverdi, dokumentasjon].
    *
    * @return
    * @throws SQLException
    */
   public List<String[]> getParametre() throws SQLException {
      List<String[]> parametre = new ArrayList<String[]>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_felles_parametre";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         String[] p = new String[3];
         p[0] = (String) rad.get("parameternavn");
         p[1] = (String) rad.get("parameterverdi");
         p[2] = (String) rad.get("dokumentasjon");
         parametre.add(p);
      }
      return parametre;
   }

   /**
    * Returnerer siste dato hele basen er oppdatert.
    *
    * @return dato.
    * @throws SQLException
    */
   public Dato getOppdatertDato() throws SQLException {
      Dato i = this.getOppdatertInternEnhet();
      Dato y = this.getOppdatertYtreEnhet();
      if (i.isEtter(y)) {
         return y;
      } else {
         return i;
      }
   }

   /**
    * Returnerer når departementorganene er sist oppdatert i basen.
    *
    * @return dato.
    * @throws SQLException
    */
   public Dato getOppdatertInternEnhet() throws SQLException {
      // returneres fra denne metoden.
      Dato dato = new Dato();

      dato.setAar(2009);
      dato.setMaaned(1);
      dato.setDag(1);

      String verdi = this.getParameterverdi("ForvaltningOppdatertInternEnhet");

      try {
         String[] verdier = verdi.split("\\.");
         dato.setAar(Integer.parseInt(verdier[0]));
         dato.setMaaned(Integer.parseInt(verdier[1]));
         dato.setDag(Integer.parseInt(verdier[2]));
      } catch (Exception ignored) {
      }
      return dato;
   }

   /**
    * Returnerer når departementorganene er sist oppdatert i basen.
    *
    * @return dato.
    * @throws SQLException
    */
   public Dato getOppdatertYtreEnhet() throws SQLException {
      // returneres fra denne metoden.
      Dato dato = new Dato();

      dato.setAar(2009);
      dato.setMaaned(1);
      dato.setDag(1);

      String verdi = this.getParameterverdi("ForvaltningOppdatertYtreEnhet");

      try {
         String[] verdier = verdi.split("\\.");
         dato.setAar(Integer.parseInt(verdier[0]));
         dato.setMaaned(Integer.parseInt(verdier[1]));
         dato.setDag(Integer.parseInt(verdier[2]));
      } catch (Exception ignored) {
      }
      return dato;
   }

   /**
    * Returnerer parameterverdi for gitt parameternavn.
    *
    * @param parameternavn
    * @return
    * @throws SQLException
    */
   private String getParameterverdi(String parameternavn) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select parameterverdi from t_felles_parametre where parameternavn = ?";
      List values = new ArrayList();

      values.add(parameternavn);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      return (String) rader[0].get("parameterverdi");
   }

   public void slettParameter(String parameternavn) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_felles_parametre where parameternavn = ?";
      List values = new ArrayList();

      values.add(parameternavn);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void registrerNyParameter(String pnavn, String pverdi, String dok) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_felles_parametre (parameternavn, parameterverdi, dokumentasjon) values (?,?,?)";
      List values = new ArrayList();

      values.add(pnavn);
      values.add(pverdi);
      values.add(dok);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }
}
