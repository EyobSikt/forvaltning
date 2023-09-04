package no.nsd.polsys.factories;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Factory-klasse for å generere databaseforbindelser.
 */
public final class DatabaseConnectionFactory  {

   private DatabaseConnectionFactory() {
   }


   /**
    * Returnerer en databaseforbindelse.
    * @return en databaseforbindelse.
    * @throws Exception hvis et exception oppstår.
    */


   public static Connection getConnection() throws Exception {

      /* Context ctx = new InitialContext();
      javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/polsys");
      return ds.getConnection();*/




      /* Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      Connection c =  DriverManager.getConnection("jdbc:sqlserver://sql2014-db;databaseName=polsys", "www_user" ,"HJtFNmuiWQFRcrgXDdjs");
       */

     Class.forName("org.postgresql.Driver");
     Connection c =  DriverManager.getConnection("jdbc:postgresql://uninett-postgres-11.cifui4jyekmu.eu-north-1.rds.amazonaws.com/forvaltningsdatabasen",  "forvaltningsdatabasen" ,"(SJ!_!s})R2Ife~U2,w_yk`#");


     //Connection c =  DriverManager.getConnection("jdbc:postgresql://uninett-postgres-11.cifui4jyekmu.eu-north-1.rds.amazonaws.com/forvaltningsdatabasen?user=forvaltningsdatabasen&password=(SJ!_!s})R2Ife~U2,w_yk`#&ssl=true");


      return c;

   }



}
