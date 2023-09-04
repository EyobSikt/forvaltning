/*
package no.nsd.polsys.factories;

import java.sql.Connection;
import java.sql.DriverManager;

*/
/**
 * Factory-klasse for å generere databaseforbindelser.
 *//*

public final class DatabaseConnectionFactory2 {

   private DatabaseConnectionFactory2() {
   }


   */
/**
    * Returnerer en databaseforbindelse.
    * @return en databaseforbindelse.
    * @throws Exception hvis et exception oppstår.
    *//*

   public static Connection getConnection() throws Exception {


       //String sqlConnectionString;
      //ServletContextEvent contextEvent = null;
      //String sqlConnectionString = (String)request.getSession().getServletContext().getInitParameter("sqlserverpolsysdata");
     // System.out.println("wwwwwww = " + "sqlConnectionString");

      //String url = request.getRequestURL().toString();
      //StringBuffer requestURL = request.getRequestURL();
      //System.out.println("eeeeeeee = " + url + "rrrr" + requestURL);

      //Context ctx = new InitialContext();
      //javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/polsysintern");
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      Connection c =  DriverManager.getConnection("jdbc:sqlserver://bastian;databaseName=polsysdata", "www_user" ,"a1tsW");

      return c;

   }



}
*/
