package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.Statsraadsarkivet;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 24.nov.2010
 * Time: 12:27:57
 * To change this template use File | Settings | File Templates.
 */
public class EldestYngsteStatsraaderLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;

   
    public EldestYngsteStatsraaderLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

        public Statsraadsarkivet[] getEldsteStatsraader_1814() throws Exception {
           String condition = "  ";
          return this.getEldesteStatsraader_1814(condition, null);
     }

    public Statsraadsarkivet[] getYngsteStatsraader_1814() throws Exception {
              String condition = "  ";
             return this.getYngsteStatsraader_1814(condition, null);
        }

      public Statsraadsarkivet[] getEldsteStatsraader_1884() throws Exception {
              String condition = "  ";
             return this.getEldsteStatsraader_1884(condition, null);
        }

     public Statsraadsarkivet[] getYngsteStatsraader_1884() throws Exception {
              String condition = "  ";
             return this.getYngsteStatsraader_1884(condition, null);
        }
    
     public Statsraadsarkivet[] getEldsteStatsraader_1945() throws Exception {
              String condition = "  ";
             return this.getEldsteStatsraader_1945(condition, null);
        }

      public Statsraadsarkivet[] getYngsteStatsraader_1945() throws Exception {
              String condition = "  ";
             return this.getYngsteStatsraader_1945(condition, null);
        }

     public Statsraadsarkivet[] getEldsteStatsraader_dagens() throws Exception {
              String condition = "  ";
             return this.getEldsteStatsraader_dagens(condition, null);
        }

     public Statsraadsarkivet[] getYngsteStatsraader_dagens() throws Exception {
              String condition = "  ";
             return this.getYngsteStatsraader_dagens(condition, null);
        }

/***************************************************************************************************

 Private metoder

***************************************************************************************************/
   
   /*eldste_statsraader1814_1884*/
    private Statsraadsarkivet[] getEldesteStatsraader_1814(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
        String sqlSelect = "SELECT     \t\t\tDATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.slutt) AS Aldersdager_ved_slutt_eksakt, \n" +
                "                      YEAR(t_felles_statsraader.slutt) - t_felles_person.faar AS Alder_ved_slutt, t_felles_person.person_id, \n" +
                "                      t_felles_statsraader.start, t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, \n" +
                "                      YEAR(t_felles_statsraader.slutt) AS Aarslutt, t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse_kort AS NOstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse_kort AS ENGstatsraadsbetegnelse, \n" +
                "                      t_felles_statsraader.start AS Regstart, t_felles_statsraader.slutt, DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear   \n" +
                "FROM         \t\tt_felles_person RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     \t\t\t(NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.slutt BETWEEN t_regjering_norske_regjeringer_ministerier.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.slutt < CONVERT(DATETIME, '1884-06-26 00:00:00', 102))\n" +
                "ORDER BY \t\t\tDATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.slutt) DESC, YEAR(t_felles_statsraader.slutt) \n" +
                "                      - t_felles_person.faar DESC "
                */
        String sqlSelect = "SELECT DATEDIFF(day, politikere.fodt, regjering_medlemmer.slutt) AS Aldersdager_ved_slutt_eksakt, \n" +
                "       YEAR(regjering_medlemmer.slutt) - YEAR(politikere.fodt) AS Alder_ved_slutt, politikere.id as person_id, \n" +
                "       regjering_medlemmer.start, YEAR(politikere.fodt) as faar, politikere.fornavn, politikere.etternavn, \n" +
                "       YEAR(regjering_medlemmer.slutt) AS Aarslutt, politikere.fodt, regjering.navn_kort_nb AS NOstatsraadsbetegnelse, regjering.navn_kort_en AS ENGstatsraadsbetegnelse, \n" +
                "       regjering_medlemmer.start AS Regstart, regjering_medlemmer.slutt, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear   \n" +
                "       FROM politikere RIGHT OUTER JOIN\n" +
                "       regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
                "       regjering\n" +
                "       WHERE (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.slutt BETWEEN regjering.start AND \n" +
                "       regjering.slutt) AND (regjering.slutt < CONVERT(DATETIME, '1884-06-26 00:00:00', 102))\n" +
                "       ORDER BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.slutt) DESC, YEAR(regjering_medlemmer.slutt) \n" +
                "       - YEAR(politikere.fodt) DESC\t\n"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_ved_slutt_eksakt"));
                int x=0;
                int y;
                int dd;
                if((Integer)rader[i].get("person_id") ==13008){
                 x =   (Integer)rader[i].get("Aldersdager_ved_slutt_eksakt");
                 y = (int) ((x+484)/365.25);
                 dd = (int) ((x+484)- (365.25 * y));
                }else{
                 x =   (Integer)rader[i].get("Aldersdager_ved_slutt_eksakt");
                 y = (int) (x/365.25);
                 dd = (int) (  (x)  -  ((365.25 * y)) );
                }
                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

 /*yngste_statsraader1814_1884*/
   private Statsraadsarkivet[] getYngsteStatsraader_1814(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
       String sqlSelect = "\n" +
                "SELECT     DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start) AS Aldersdager_ved_start_eksakt, \n" +
                "                      YEAR(t_felles_statsraader.start) - t_felles_person.faar AS Alder_ved_start, t_felles_person.person_id, \n" +
                "                      t_felles_statsraader.start, t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, \n" +
                "                      YEAR(t_felles_statsraader.start) AS Aarstart, t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start AS Regstart, t_felles_statsraader.slutt, DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear,\n" +
                "\t\t\t\t\tt_felles_statsraader.person_id AS Expr1, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse_kort AS NOstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse_kort AS ENGstatsraadsbetegnelse\n" +
                "FROM         t_felles_person RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     (NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.start < CONVERT(DATETIME, '1884-06-26 00:00:00', 102)) AND \n" +
                "                      (t_felles_statsraader.person_id <> 4001 AND t_felles_statsraader.person_id <> 4002) \n" +
                "ORDER BY DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start), YEAR(t_felles_statsraader.start) - t_felles_person.faar "
            */
       String sqlSelect = "\n" +
               "SELECT DATEDIFF(day, politikere.fodt, regjering_medlemmer.start) AS Aldersdager_ved_start_eksakt, \n" +
               "       YEAR(regjering_medlemmer.start) - YEAR(politikere.fodt) AS Alder_ved_start, politikere.id as person_id, \n" +
               "       regjering_medlemmer.start, YEAR(politikere.fodt) as faar, politikere.fornavn, politikere.etternavn, \n" +
               "       YEAR(regjering_medlemmer.start) AS Aarstart, politikere.fodt, regjering.navn_nb AS Statsraadsbetegnelse,\n" +
               "       regjering.start AS Regstart, regjering_medlemmer.slutt, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear,\n" +
               "       regjering_medlemmer.person AS Expr1, regjering.navn_kort_nb AS NOstatsraadsbetegnelse, regjering.navn_kort_en AS ENGstatsraadsbetegnelse\n" +
               "       FROM   politikere RIGHT OUTER JOIN\n" +
               "       regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
               "       regjering\n" +
               "       WHERE     (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.start BETWEEN regjering.start AND \n" +
               "       regjering.slutt) AND (regjering.start < CONVERT(DATETIME, '1884-06-26 00:00:00', 102)) AND \n" +
               "       (regjering_medlemmer.person <> 4001 AND regjering_medlemmer.person <> 4002) \n" +
               "       ORDER BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.start), YEAR(regjering_medlemmer.start) - YEAR(politikere.fodt) "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_ved_start_eksakt"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Aldersdager_ved_start_eksakt");
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

    /*yngste_statsraader1884_1945*/
   private Statsraadsarkivet[] getEldsteStatsraader_1884(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
        String sqlSelect = " SELECT     DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.slutt) AS Aldersdager_ved_slutt_eksakt, \n" +
                "                      YEAR(t_felles_statsraader.slutt) - t_felles_person.faar AS Alder_ved_slutt, t_felles_person.person_id, \n" +
                "                      t_felles_statsraader.start, t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, \n" +
                "                      YEAR(t_felles_statsraader.slutt) AS Aarslutt, t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start AS Regstart, t_felles_statsraader.slutt,  DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse_kort AS NOstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse_kort AS ENGstatsraadsbetegnelse, \n" +
                "\t\t\t\t\t\tt_regjering_norske_regjeringer_ministerier.kode AS Regkode, \n" +
                "\t\t\t\t\tt_felles_person.initialer\n" +
                "FROM         t_felles_person  RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     (NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.slutt BETWEEN t_regjering_norske_regjeringer_ministerier.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.slutt BETWEEN CONVERT(DATETIME, '1884-06-26 00:00:00', 102) \n" +
                "                      AND CONVERT(DATETIME, '1945-06-24 00:00:00', 102))\n" +
                "ORDER BY DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.slutt) DESC, YEAR(t_felles_statsraader.slutt) \n" +
                "                      - t_felles_person.faar DESC "
         */
       String sqlSelect = " SELECT DATEDIFF(day, politikere.fodt, regjering_medlemmer.slutt) AS Aldersdager_ved_slutt_eksakt, \n" +
               "       YEAR(regjering_medlemmer.slutt) - YEAR(politikere.fodt) AS Alder_ved_slutt, politikere.id as person_id, \n" +
               "       regjering_medlemmer.start, YEAR(politikere.fodt) as faar, politikere.fornavn, politikere.etternavn, \n" +
               "       YEAR(regjering_medlemmer.slutt) AS Aarslutt, politikere.fodt, regjering.navn_nb as Statsraadsbetegnelse, \n" +
               "       regjering.start AS Regstart, regjering_medlemmer.slutt,  DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, \n" +
               "       regjering.navn_kort_nb AS NOstatsraadsbetegnelse, regjering.navn_kort_en AS ENGstatsraadsbetegnelse, \n" +
               "       regjering.id AS Regkode, \n" +
               "       politikere.initialer\n" +
               "       FROM  politikere  RIGHT OUTER JOIN\n" +
               "       regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
               "       regjering\n" +
               "       WHERE     (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.slutt BETWEEN regjering.start AND \n" +
               "       regjering.slutt) AND (regjering.slutt BETWEEN CONVERT(DATETIME, '1884-06-26 00:00:00', 102)\n" +
               "       AND CONVERT(DATETIME, '1945-06-24 00:00:00', 102))\n" +
               "       ORDER BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.slutt) DESC, YEAR(regjering_medlemmer.slutt)  "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_ved_slutt_eksakt"));
                Integer x=0;
                Integer y;
                Integer dd;
                x =   (Integer)rader[i].get("Aldersdager_ved_slutt_eksakt");
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

   /*yngste_statsraader1814_1884*/
   private Statsraadsarkivet[] getYngsteStatsraader_1884(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
        String sqlSelect = "\n" +
                "SELECT     DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start) AS Aldersdager_ved_start_eksakt, \n" +
                "                      YEAR(t_felles_statsraader.start) - t_felles_person.faar AS Alder_ved_start, t_felles_person.person_id, \n" +
                "                      t_felles_statsraader.start, t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, \n" +
                "                      YEAR(t_felles_statsraader.start) AS Aarstart, t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start AS Regstart, t_felles_statsraader.slutt,  DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear,  t_felles_statsraader.start AS Expr1, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse_kort AS NOstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse_kort AS ENGstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.kode AS Regkode, \n" +
                "\t\t\t\t\tt_felles_person.initialer\n" +
                "FROM         t_felles_person  RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     (NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.start BETWEEN CONVERT(DATETIME, '1884-06-26 00:00:00', 102) \n" +
                "                      AND CONVERT(DATETIME, '1945-06-24 00:00:00', 102))\n" +
                "ORDER BY DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start), YEAR(t_felles_statsraader.start) - t_felles_person.faar "
             */
       String sqlSelect = "SELECT DATEDIFF(day, politikere.fodt, regjering_medlemmer.start) AS Aldersdager_ved_start_eksakt, \n" +
               "       YEAR(regjering_medlemmer.start) - YEAR(politikere.fodt) AS Alder_ved_start, politikere.id as person_id, \n" +
               "       regjering_medlemmer.start, YEAR(politikere.fodt), politikere.fornavn, politikere.etternavn, \n" +
               "       YEAR(regjering_medlemmer.start) AS Aarstart, politikere.fodt, regjering.navn_nb as Statsraadsbetegnelse, \n" +
               "       regjering.start AS Regstart, regjering_medlemmer.slutt,  DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear,  regjering_medlemmer.start AS Expr1,\n" +
               "       regjering.navn_kort_nb AS NOstatsraadsbetegnelse, regjering.navn_kort_en AS ENGstatsraadsbetegnelse, regjering.id AS Regkode,\n" +
               "       politikere.initialer\n" +
               "       FROM  politikere  RIGHT OUTER JOIN\n" +
               "       regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
               "       regjering\n" +
               "       WHERE     (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.start BETWEEN regjering.start AND \n" +
               "       regjering.slutt) AND (regjering.start BETWEEN CONVERT(DATETIME, '1884-06-26 00:00:00', 102)\n" +
               "       AND CONVERT(DATETIME, '1945-06-24 00:00:00', 102))\n" +
               "       ORDER BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.start), YEAR(regjering_medlemmer.start) - YEAR(politikere.fodt) "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_ved_start_eksakt"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Aldersdager_ved_start_eksakt");
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

    /*eldste_statsraader 1945*/
   private Statsraadsarkivet[] getEldsteStatsraader_1945(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
        String sqlSelect = " SELECT     DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.slutt) AS Aldersdager_ved_slutt_eksakt, \n" +
                "                      YEAR(t_felles_statsraader.slutt) - t_felles_person.faar AS Alder_ved_slutt, t_felles_person.person_id, \n" +
                "                      t_felles_statsraader.start, t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, \n" +
                "                      YEAR(t_felles_statsraader.slutt) AS Aarslutt, t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start AS Regstart, t_felles_statsraader.slutt, DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse_kort AS NOstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse_kort AS ENGstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.kode AS Regkode, t_felles_person.initialer\n" +
                "FROM         t_felles_person  RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     (NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.slutt BETWEEN t_regjering_norske_regjeringer_ministerier.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.slutt BETWEEN CONVERT(DATETIME, '1945-06-25 00:00:00', 102) \n" +
                "                      AND GETDATE())\n" +
                "ORDER BY DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.slutt) DESC, YEAR(t_felles_statsraader.slutt) \n" +
                "                      - t_felles_person.faar DESC "
              */
       String sqlSelect = " SELECT DATEDIFF(day, politikere.fodt, regjering_medlemmer.slutt) AS Aldersdager_ved_slutt_eksakt, \n" +
               "       YEAR(regjering_medlemmer.slutt) - YEAR(politikere.fodt) AS Alder_ved_slutt, politikere.id as person_id, \n" +
               "       regjering_medlemmer.start, YEAR(politikere.fodt) as faar, politikere.fornavn, politikere.etternavn, \n" +
               "       YEAR(regjering_medlemmer.slutt) AS Aarslutt, politikere.fodt, regjering.navn_nb as Statsraadsbetegnelse, \n" +
               "       regjering.start AS Regstart, regjering_medlemmer.slutt, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, \n" +
               "       regjering.navn_kort_nb AS NOstatsraadsbetegnelse, regjering.navn_kort_en AS ENGstatsraadsbetegnelse, regjering.id AS Regkode, politikere.initialer\n" +
               "       FROM politikere  RIGHT OUTER JOIN\n" +
               "       regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
               "       regjering\n" +
               "       WHERE     (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.slutt BETWEEN regjering.start AND \n" +
               "       regjering.slutt) AND (regjering.slutt BETWEEN CONVERT(DATETIME, '1945-06-25 00:00:00', 102) \n" +
               "       AND GETDATE())\n" +
               "       ORDER BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.slutt) DESC, YEAR(regjering_medlemmer.slutt) \n" +
               "        - YEAR(politikere.fodt) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_ved_slutt_eksakt"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Aldersdager_ved_slutt_eksakt");
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

   /*yngste_statsraader1945 ..d.d.*/
   private Statsraadsarkivet[] getYngsteStatsraader_1945(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
        String sqlSelect = "\n" +
                "SELECT     DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start) AS Aldersdager_ved_start_eksakt, \n" +
                "                      YEAR(t_felles_statsraader.start) - t_felles_person.faar AS Alder_ved_start, t_felles_person.person_id, \n" +
                "                      t_felles_statsraader.start, t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, \n" +
                "                      YEAR(t_felles_statsraader.start) AS Aarstart, t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start AS Regstart, t_felles_statsraader.start AS Expr1, t_felles_statsraader.slutt, DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse_kort AS NOstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse_kort AS ENGstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.kode AS Regkode, t_felles_person.initialer\n" +
                "FROM         t_felles_person  RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     (NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.start BETWEEN CONVERT(DATETIME, '1945-06-25 00:00:00', 102) \n" +
                "                      AND GETDATE())\n" +
                "ORDER BY DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start), YEAR(t_felles_statsraader.start) - t_felles_person.faar "
              */
       String sqlSelect = "SELECT DATEDIFF(day, politikere.fodt, regjering_medlemmer.start) AS Aldersdager_ved_start_eksakt, \n" +
               "       YEAR(regjering_medlemmer.start) - YEAR(politikere.fodt) AS Alder_ved_start, politikere.id as person_id, \n" +
               "       regjering_medlemmer.start, YEAR(politikere.fodt) as faar, politikere.fornavn, politikere.etternavn, \n" +
               "       YEAR(regjering_medlemmer.start) AS Aarstart, politikere.fodt, regjering.navn_nb as Statsraadsbetegnelse, \n" +
               "       regjering.start AS Regstart, regjering_medlemmer.start AS Expr1, regjering_medlemmer.slutt, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, \n" +
               "       regjering.navn_kort_nb AS NOstatsraadsbetegnelse, regjering.navn_kort_en AS ENGstatsraadsbetegnelse, regjering.id AS Regkode, politikere.initialer\n" +
               "       FROM politikere RIGHT OUTER JOIN\n" +
               "       regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
               "       regjering\n" +
               "       WHERE     (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.start BETWEEN regjering.start AND \n" +
               "       regjering.slutt) AND (regjering.start BETWEEN CONVERT(DATETIME, '1945-06-25 00:00:00', 102) \n" +
               "       AND GETDATE())\n" +
               "       ORDER BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.start), YEAR(regjering_medlemmer.start) - YEAR(politikere.fodt) "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_ved_start_eksakt"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Aldersdager_ved_start_eksakt");
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

    /*eldste_statsraader 1945*/
   private Statsraadsarkivet[] getEldsteStatsraader_dagens(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
        String sqlSelect = " SELECT     DATEDIFF(day, t_felles_person.fodt, GETDATE()) AS Aldersdager_idag_eksakt, t_felles_person.person_id, t_felles_statsraader.start, \n" +
                "                      t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, YEAR(t_felles_statsraader.start) AS Aarstart, \n" +
                "                      t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.kode AS Expr1, \n" +
                "                      t_felles_statsraader.slutt, DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear, t_felles_person.initialer, t_regjering_norske_regjeringer_ministerier.kode\n" +
                "FROM         t_felles_person  RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     (NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND  \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt)  AND (t_felles_statsraader.slutt = '9999-09-09') \n" +
                "GROUP BY DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start), t_felles_person.person_id, t_felles_statsraader.start, \n" +
                "                      t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, YEAR(t_felles_statsraader.start), \n" +
                "                      t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.kode, \n" +
                "                      t_felles_statsraader.slutt, t_felles_person.initialer\n" +
                "ORDER BY DATEDIFF(day, t_felles_person.fodt, GETDATE()) DESC "
         */
       String sqlSelect = " SELECT DATEDIFF(day, politikere.fodt, GETDATE()) AS Aldersdager_idag_eksakt, politikere.id as person_id, regjering_medlemmer.start, \n" +
               "       YEAR(politikere.fodt) as faar, politikere.fornavn, politikere.etternavn, YEAR(regjering_medlemmer.start) AS Aarstart, \n" +
               "       politikere.fodt, regjering.navn_nb as Statsraadsbetegnelse, regjering.id AS Expr1, \n" +
               "       regjering_medlemmer.slutt, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, politikere.initialer, regjering.id\n" +
               "       FROM  politikere  RIGHT OUTER JOIN\n" +
               "       regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
               "       regjering\n" +
               "       WHERE     (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.start BETWEEN regjering.start AND  \n" +
               "       regjering.slutt)  AND (regjering_medlemmer.slutt = '9999-09-09') \n" +
               "       GROUP BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.start), politikere.id, regjering_medlemmer.start, \n" +
               "       YEAR(politikere.fodt), politikere.fornavn, politikere.etternavn, YEAR(regjering_medlemmer.start), \n" +
               "       politikere.fodt, regjering.navn_nb, regjering.id, \n" +
               "       regjering_medlemmer.slutt, politikere.initialer\n" +
               "       ORDER BY DATEDIFF(day, politikere.fodt, GETDATE()) DESC "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_idag_eksakt"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Aldersdager_idag_eksakt");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );

                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("statsraadsbetegnelse"));
                //eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

    /*yngste_statsraader 1945*/
   private Statsraadsarkivet[] getYngsteStatsraader_dagens(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] eldestyngstestatsraader = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
        String sqlSelect = " SELECT     DATEDIFF(day, t_felles_person.fodt, GETDATE()) AS Aldersdager_idag_eksakt, t_felles_person.person_id, t_felles_statsraader.start, \n" +
                "                      t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, YEAR(t_felles_statsraader.start) AS Aarstart, \n" +
                "                      t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.kode AS Expr1, \n" +
                "                      t_felles_statsraader.start AS Expr2, t_felles_statsraader.slutt, DAY(t_felles_statsraader.start) AS startday, MONTH(t_felles_statsraader.start) AS startmonth , YEAR(t_felles_statsraader.start) AS startyear , DAY(t_felles_statsraader.slutt) AS sluttday, MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear, t_felles_person.initialer\n" +
                "FROM         t_felles_person  RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id CROSS JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     (NOT (t_felles_person.fodt IS NULL)) AND (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.slutt) AND (t_felles_statsraader.slutt = '9999-09-09')\n" +
                "GROUP BY DATEDIFF(day, t_felles_person.fodt, t_felles_statsraader.start ), t_felles_person.person_id, t_felles_statsraader.start, t_felles_statsraader.slutt, \n" +
                "                      t_felles_person.faar, t_felles_person.fornavn, t_felles_person.navn, YEAR(t_felles_statsraader.start), \n" +
                "                      t_felles_person.fodt, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.kode, \n" +
                "                      t_felles_statsraader.start, t_felles_person.initialer\n" +
                "ORDER BY DATEDIFF(day, t_felles_person.fodt, GETDATE()) "
        */
       String sqlSelect = " SELECT DATEDIFF(day, politikere.fodt, GETDATE()) AS Aldersdager_idag_eksakt, politikere.id as person_id, regjering_medlemmer.start, \n" +
               "        YEAR(politikere.fodt) as faar, politikere.fornavn, politikere.etternavn, YEAR(regjering_medlemmer.start) AS Aarstart, \n" +
               "        politikere.fodt, regjering.navn_nb as Statsraadsbetegnelse, regjering.id AS Expr1, \n" +
               "        regjering_medlemmer.start AS Expr2, regjering_medlemmer.slutt, DAY(regjering_medlemmer.start) AS startday, MONTH(regjering_medlemmer.start) AS startmonth , YEAR(regjering_medlemmer.start) AS startyear , DAY(regjering_medlemmer.slutt) AS sluttday, MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, politikere.initialer\n" +
               "        FROM  politikere  RIGHT OUTER JOIN\n" +
               "        regjering_medlemmer ON politikere.id = regjering_medlemmer.person CROSS JOIN\n" +
               "        regjering\n" +
               "        WHERE     (NOT (politikere.fodt IS NULL)) AND (regjering_medlemmer.start BETWEEN regjering.start AND \n" +
               "        regjering.slutt) AND (regjering_medlemmer.slutt = '9999-09-09')\n" +
               "        GROUP BY DATEDIFF(day, politikere.fodt, regjering_medlemmer.start ), politikere.id, regjering_medlemmer.start, regjering_medlemmer.slutt, \n" +
               "        YEAR(politikere.fodt), politikere.fornavn, politikere.etternavn, YEAR(regjering_medlemmer.start), \n" +
               "        politikere.fodt, regjering.navn_nb, regjering.id, \n" +
               "        regjering_medlemmer.start, politikere.initialer\n" +
               "        ORDER BY DATEDIFF(day, politikere.fodt, GETDATE()) "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        eldestyngstestatsraader = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                eldestyngstestatsraader[i] = new Statsraadsarkivet();

                eldestyngstestatsraader[i].setEtternavn((String)rader[i].get("etternavn"));
                eldestyngstestatsraader[i].setFornavn((String)rader[i].get("fornavn"));
                eldestyngstestatsraader[i].setPersonId((Integer)rader[i].get("person_id"));
                eldestyngstestatsraader[i].setAldersdager_ved_slutt_eksakt((Integer)rader[i].get("Aldersdager_idag_eksakt"));
                int x=0;
                int y;
                int dd;
                x =   (Integer)rader[i].get("Aldersdager_idag_eksakt");
                y = (int) (x/365.25);
                dd = (int) (  (x)  -  ((365.25 * y)) );

                eldestyngstestatsraader[i].setDoedsaar(y);
                eldestyngstestatsraader[i].setAntdag(dd);
                eldestyngstestatsraader[i].setRegjeringsnavn_NO((String)rader[i].get("statsraadsbetegnelse"));
                //eldestyngstestatsraader[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                eldestyngstestatsraader[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                eldestyngstestatsraader[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
            }
        return eldestyngstestatsraader;
    }

}

