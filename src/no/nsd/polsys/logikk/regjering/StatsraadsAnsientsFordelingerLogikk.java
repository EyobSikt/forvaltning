package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.Statsraadsarkivet;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 24.nov.2010
 * Time: 12:27:57
 * To change this template use File | Settings | File Templates.
 */
public class StatsraadsAnsientsFordelingerLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatsraadsAnsientsFordelingerLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

        public Statsraadsarkivet[] getLengstStatsraader_1814() throws Exception {
           String condition = "  ";
          return this.getLengstStatsraader_1814(condition, null);
     }

    public Statsraadsarkivet[] getKortestStatsraader_1814() throws Exception {
              String condition = "  ";
             return this.getKortestStatsraader_1814(condition, null);
        }

      public Statsraadsarkivet[] getLengstStatsraader_1884() throws Exception {
              String condition = "  ";
             return this.getLengstStatsraader_1884(condition, null);
        }

     public Statsraadsarkivet[] getKortestStatsraader_1884() throws Exception {
              String condition = "  ";
             return this.getKortestStatsraader_1884(condition, null);
        }
    
     public Statsraadsarkivet[] getLengstStatsraader_1945() throws Exception {
              String condition = "  ";
             return this.getLengstStatsraader_1945(condition, null);
        }

      public Statsraadsarkivet[] getKortestStatsraader_1945() throws Exception {
              String condition = "  ";
             return this.getKortestStatsraader_1945(condition, null);
        }

     public Statsraadsarkivet[] getSumStorstStatsraader_1814() throws Exception {
              String condition = "  ";
             return this.getSumStorstStatsraader_1814(condition, null);
        }

     public Statsraadsarkivet[] getSumMinstStatsraader_1814() throws Exception {
              String condition = "  ";
             return this.getSumMinstStatsraader_1814(condition, null);
        }

    public Statsraadsarkivet[] getSumStorstStatsraader_1884() throws Exception {
              String condition = "  ";
             return this.getSumStorstStatsraader_1884(condition, null);
        }

     public Statsraadsarkivet[] getSumMinstStatsraader_1884() throws Exception {
              String condition = "  ";
             return this.getSumMinstStatsraader_1884(condition, null);
        }

     public Statsraadsarkivet[] getSumStorstStatsraader_1945() throws Exception {
              String condition = "  ";
             return this.getSumStorstStatsraader_1945(condition, null);
        }

     public Statsraadsarkivet[] getSumMinstStatsraader_1945() throws Exception {
              String condition = "  ";
             return this.getSumMinstStatsraader_1945(condition, null);
        }

    public Statsraadsarkivet[] getSumStatsraader_dagens() throws Exception {
              String condition = "  ";
             return this.getSumStatsraader_dagens(condition, null);
        }

/***************************************************************************************************

 Private metoder

***************************************************************************************************/
   
   /* 1. lengst_statsraader1814_1884*/
    private Statsraadsarkivet[] getLengstStatsraader_1814(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  politikere.fornavn, politikere.etternavn as navn, politikere.id as person_id, regjering_medlemmer.start, \n" +
                "           regjering_medlemmer.slutt, DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) AS Antdag_lengst, \n" +
                "           YEAR(regjering_medlemmer.slutt) AS Expr1, regjering.id as kode, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear\n" +
                "           FROM  regjering_medlemmer LEFT OUTER JOIN\n" +
                "           politikere ON regjering_medlemmer.person = politikere.id CROSS JOIN\n" +
                "           regjering\n" +
                "           WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id < 10) AND (regjering_medlemmer.slutt BETWEEN\n" +
                "           regjering.start AND regjering.slutt)\n" +
                "           ORDER BY DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag_lengst"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag_lengst");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

 /* 2. kortest_statsraader1814_1884*/
   private Statsraadsarkivet[] getKortestStatsraader_1814(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT politikere.fornavn, politikere.etternavn as navn, politikere.id as person_id, regjering_medlemmer.start, \n" +
                "           regjering_medlemmer.slutt, DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) AS Antdag_lengst, \n" +
                "           YEAR(regjering_medlemmer.slutt) AS Expr1, regjering.id as kode, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear\n" +
                "           FROM    regjering_medlemmer LEFT OUTER JOIN\n" +
                "           politikere ON regjering_medlemmer.person = politikere.id CROSS JOIN\n" +
                "           regjering\n" +
                "           WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id < 10) AND (regjering_medlemmer.slutt BETWEEN\n" +
                "           regjering.start AND regjering.slutt)\n" +
                "           ORDER BY DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) ASC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag_lengst"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag_lengst");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

    /* 3. lengst_statsraader1884_1945*/
   private Statsraadsarkivet[] getLengstStatsraader_1884(String condition, List values) throws Exception {
         // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT politikere.fornavn, politikere.etternavn as navn, politikere.id as person_id, regjering_medlemmer.start, \n" +
                "           regjering_medlemmer.slutt, DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) AS Antdag_lengst,\n" +
                "           YEAR(regjering_medlemmer.slutt) AS Expr1, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, regjering.id AS Regkode, politikere.initialer\n" +
                "           FROM   politikere  RIGHT OUTER JOIN\n" +
                "           regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "           regjering\n" +
                "           WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id BETWEEN 10 AND 35) AND \n" +
                "           (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "           ORDER BY DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag_lengst"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag_lengst");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

   /* 4. kortest_statsraader1884_1945*/
   private Statsraadsarkivet[] getKortestStatsraader_1884(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT politikere.fornavn, politikere.etternavn as navn, politikere.id as person_id, regjering_medlemmer.start, \n" +
                "           regjering_medlemmer.slutt, DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) AS Antdag_lengst, \n" +
                "           YEAR(regjering_medlemmer.slutt) AS Expr1, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear , regjering.id AS Regkode, politikere.initialer\n" +
                "           FROM politikere  RIGHT OUTER JOIN\n" +
                "           regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "           regjering\n" +
                "           WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id BETWEEN 10 AND 35) AND \n" +
                "           (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "           ORDER BY DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) ASC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag_lengst"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag_lengst");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

    /* 5. lengst_statsraader 1945*/
   private Statsraadsarkivet[] getLengstStatsraader_1945(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT politikere.fornavn, politikere.etternavn as navn, politikere.id as person_id, regjering_medlemmer.start, \n" +
                "           regjering_medlemmer.slutt, DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) AS Antdag_lengst, \n" +
                "           YEAR(regjering_medlemmer.slutt) AS Expr1, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear ,regjering.id as kode, politikere.initialer\n" +
                "           FROM    politikere  RIGHT OUTER JOIN\n" +
                "           regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "           regjering\n" +
                "           WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id > 35) AND (regjering.id not in(101, 102, 103,104,105,106,107,108,109,110,111,112) ) AND \n" +
                "           (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "           ORDER BY DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag_lengst"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag_lengst");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

   /* 6. kortest_statsraader1945 ..d.d.*/
   private Statsraadsarkivet[] getKortestStatsraader_1945(String condition, List values) throws Exception {
       // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT politikere.fornavn, politikere.etternavn as navn, politikere.id as person_id, regjering_medlemmer.start, \n" +
                "           regjering_medlemmer.slutt, DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) AS Antdag_lengst,\n" +
                "           YEAR(regjering_medlemmer.slutt) AS Expr1, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear , regjering.id as kode, politikere.initialer\n" +
                "           FROM   politikere  RIGHT OUTER JOIN\n" +
                "           regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "           regjering\n" +
                "           WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id > 35) AND (regjering.id not in(101, 102, 103,104,105,106,107,108,109,110,111,112) ) AND \n" +
                "           (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "           ORDER BY DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) ASC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag_lengst"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag_lengst");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

    /* 7. sum storste statsraader 1814*/
   private Statsraadsarkivet[] getSumStorstStatsraader_1814(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT politikere.id as person_id, SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt + 1)) AS Antdag, \n" +
                "           politikere.fornavn, politikere.etternavn as navn\n" +
                "           FROM regjering_medlemmer LEFT OUTER JOIN\n" +
                "           politikere ON regjering_medlemmer.person = politikere.id CROSS JOIN\n" +
                "           regjering\n" +
                "           WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999)AND (regjering.id < 10) AND (regjering_medlemmer.slutt BETWEEN\n" +
                "           regjering.start AND regjering.slutt)\n" +
                "           GROUP BY politikere.id, politikere.fornavn, politikere.etternavn\n" +
                "           ORDER BY SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt)) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
               eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

   /* 8. sum minst statsraader 1814*/
   private Statsraadsarkivet[] getSumMinstStatsraader_1814(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT politikere.id as person_id, SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt + 1)) AS Antdag, \n" +
                "               politikere.fornavn, politikere.etternavn as navn\n" +
                "               FROM   regjering_medlemmer LEFT OUTER JOIN\n" +
                "               politikere ON regjering_medlemmer.person = politikere.id CROSS JOIN\n" +
                "               regjering\n" +
                "               WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999)AND (regjering.id < 10) AND (regjering_medlemmer.slutt BETWEEN\n" +
                "               regjering.start AND regjering.slutt)\n" +
                "               GROUP BY politikere.id, politikere.fornavn, politikere.etternavn\n" +
                "               ORDER BY SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt)) ASC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
               eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

   /* 9. sum storst statsraader 1884*/
   private Statsraadsarkivet[] getSumStorstStatsraader_1884(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT politikere.id as person_id, SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt + 1)) AS Antdag, \n" +
                "               politikere.fornavn, politikere.etternavn as navn, politikere.initialer\n" +
                "               FROM  politikere RIGHT OUTER JOIN\n" +
                "               regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "               regjering\n" +
                "               WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id BETWEEN 10 AND 35) AND \n" +
                "               (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "               GROUP BY politikere.id, politikere.fornavn, politikere.etternavn, politikere.initialer\n" +
                "               ORDER BY SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt)) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
               eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    } 

   /* 10. sum minst statsraader 1884*/
   private Statsraadsarkivet[] getSumMinstStatsraader_1884(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT politikere.id as person_id, SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt + 1)) AS Antdag, \n" +
                "               politikere.fornavn, politikere.etternavn as navn, politikere.initialer\n" +
                "               FROM   politikere RIGHT OUTER JOIN\n" +
                "               regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "               regjering\n" +
                "               WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id BETWEEN 10 AND 35) AND \n" +
                "               (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "               GROUP BY politikere.id, politikere.fornavn, politikere.etternavn, politikere.initialer\n" +
                "               ORDER BY SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt)) ASC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
               eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }



     /* 11. sum storst statsraader 1945*/
   private Statsraadsarkivet[] getSumStorstStatsraader_1945(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT politikere.id as person_id, SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt + 1)) AS Antdag, \n" +
                "               politikere.fornavn, politikere.etternavn as navn, politikere.initialer\n" +
                "               FROM  politikere RIGHT OUTER JOIN\n" +
                "               regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "               regjering\n" +
                "               WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id > 35) AND (regjering.id not in(101, 102, 103,104,105,106,107,108,109,110,111,112) ) AND \n" +
                "               (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "               GROUP BY politikere.id, politikere.fornavn, politikere.etternavn, politikere.initialer\n" +
                "               ORDER BY SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt)) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
               eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

   /* 12. sum minst statsraader 1945*/
   private Statsraadsarkivet[] getSumMinstStatsraader_1945(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT politikere.id as person_id, SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt + 1)) AS Antdag, \n" +
                "               politikere.fornavn, politikere.etternavn as navn, politikere.initialer\n" +
                "               FROM   politikere RIGHT OUTER JOIN\n" +
                "               regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "               regjering\n" +
                "               WHERE     (YEAR(regjering_medlemmer.slutt) <> 9999) AND (regjering.id > 35) AND (regjering.id not in(101, 102, 103,104,105,106,107,108,109,110,111,112) )  AND \n" +
                "               (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt)\n" +
                "               GROUP BY politikere.id, politikere.fornavn, politikere.etternavn, politikere.initialer\n" +
                "               ORDER BY SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt)) ASC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Antdag"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Antdag");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );
               eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

   /*  13. sum dagens statsraader*/
   private Statsraadsarkivet[] getSumStatsraader_dagens(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT  regjering_medlemmer.person as person_id, politikere.initialer, politikere.fornavn, politikere.etternavn as navn, SUM(DATEDIFF(day, regjering_medlemmer_1.start, \n" +
                "                regjering_medlemmer_1.slutt + 1)) AS Antdag_dagens\n" +
                "                FROM  politikere   RIGHT OUTER JOIN\n" +
                "                regjering_medlemmer INNER JOIN\n" +
                "                regjering_medlemmer AS regjering_medlemmer_1 ON regjering_medlemmer.person = regjering_medlemmer_1.person ON \n" +
                "                politikere.id = regjering_medlemmer_1.person\n" +
                "                WHERE     (YEAR(regjering_medlemmer.slutt) = 9999)\n" +
                "                GROUP BY politikere.initialer, politikere.fornavn, politikere.etternavn, regjering_medlemmer.person\n" +
                "                ORDER BY politikere.etternavn "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("navn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("antdag_dagens"));
                int x=0;
                int y;
                int dd;

                Date d1=new Date();
                Date d2=new Date("9999/09/09");
                int difInDays = (int) ((d2.getTime() - d1.getTime())/(1000*60*60*24)) - 36500;
                x =   (Integer)rader[i].get("antdag_dagens");
                y = (int) (x/difInDays);
       long antdag_fratrekk =  (((d2.getTime() - d1.getTime())/(1000*60*60*24)) ) *y;
        int antdag_ny = (int) ((x - antdag_fratrekk)/Math.sqrt(y));
        int tjenesteaar_eksakt = (int) (antdag_ny / 365.25);

        double tt = 365.25 * tjenesteaar_eksakt;

        double rest_dager_eksakt = antdag_ny -tt;
        int rest_dager = (int)  rest_dager_eksakt;
        
               eldestyngstestatsraader[i].setDoedsaar(tjenesteaar_eksakt);
                eldestyngstestatsraader[i].setAntdag(rest_dager);
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }
}

