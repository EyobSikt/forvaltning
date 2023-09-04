package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.Statsraadsarkivet;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 24.nov.2010
 * Time: 12:27:57
 * To change this template use File | Settings | File Templates.
 */
public class StatsraadskjonnLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatsraadskjonnLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------
/*
    public Statsraadsarkivet[] getStatsraadsKjonnsfordeling() throws Exception {
           String condition = "  ";
           return this.getStatsraadsKjonnsfordeling(condition, null);
       }
*/
    public Statsraadsarkivet[] getStatsraadsbetegnelse() throws Exception {
           String condition = "  ";
           return this.getStatsraadsbetegnelse(condition, null);
       }

    public Statsraadsarkivet[] getDagensregjering() throws Exception {
        String condition = "  ";
        return this.getDagensregjering(condition, null);
    }
 /*
     public Statsraadsarkivet[] getDagensRegjeringenesvarighet() throws Exception {
           String condition = "  ";
           return this.getDagensRegjeringenesvarighet(condition, null);
       }
*/
/***************************************************************************************************

 Private metoder

***************************************************************************************************/

 /*
    private Statsraadsarkivet[] getStatsraadsKjonnsfordeling(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] regjeringadhoc = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     SUM(DATEDIFF(day, t_felles_statsraader.start, t_felles_statsraader.slutt) + 1) AS Antdag, t_regjering_norske_regjeringer_ministerier.kode, \n" +
                "                      t_felles_person.kjoenn\n" +
                "FROM         t_felles_person RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier ON t_felles_statsraader.min_reg = t_regjering_norske_regjeringer_ministerier.min_reg\n" +
                "WHERE     (YEAR(t_regjering_norske_regjeringer_ministerier.slutt) <> 9999) AND (t_felles_statsraader.start BETWEEN \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.min_reg = 2)\n" +
                "GROUP BY t_regjering_norske_regjeringer_ministerier.kode, t_felles_person.kjoenn\n" +
                "ORDER BY t_regjering_norske_regjeringer_ministerier.kode "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringadhoc[i] = new Statsraadsarkivet();

                regjeringadhoc[i].setRegjering_reg_kode((Integer)rader[i].get("kode"));
               regjeringadhoc[i].setAntdag((Integer)rader[i].get("Antdag"));
              //if((Integer)rader[i].get("kjoenn") ==1){ regjeringadhoc[i].setMenn((Integer)rader[i].get("kjoenn"));}
               //if((Integer)rader[i].get("kjoenn") ==2){ regjeringadhoc[i].setKvinner((Integer)rader[i].get("kjoenn"));}
                
           
            }
        return regjeringadhoc;
    }
*/

    private Statsraadsarkivet[] getStatsraadsbetegnelse(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] regjeringadhoc = null;

        // resultat fra sql-sporring.
        Result result = null;
         Result result2 = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        SqlCommandBean sqlCB2 = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
         SortedMap[] rader2 = null;
        // SQL-sporring.
        /*String sqlSelect = "SELECT     \t\tkode, Statsraadsbetegnelse as NOstatsraadsbetegnelse, ENGstatsraadsbetegnelse as ENGtatsraadsbetegnelse, start, slutt, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear ,  PartierKode, StortingPeriodeKode, Aar as startaar, Blokk\n" +
                "FROM         \tt_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     \t\t(YEAR(slutt) <> 9999) AND (min_reg = 2)\n" +
                "GROUP BY \t\tkode, Statsraadsbetegnelse, ENGstatsraadsbetegnelse, start, slutt, PartierKode, StortingPeriodeKode, Aar, Blokk\n" +
                "ORDER BY \t\tkode "
                */
        String sqlSelect = " SELECT  id as kode, navn_nb as NOstatsraadsbetegnelse, navn_en as ENGstatsraadsbetegnelse, start, slutt,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, \n" +
                "         Blokk,  YEAR(start) as startaar\n" +
                "\t\t  , STUFF(( \n" +
                "                        SELECT N'+ ' + CAST([eintaltekst_forkorting] AS VARCHAR(255)) \n" +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti \n" +
                "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS partier , STUFF(( \n" +
                "                        SELECT N', ' + CAST([kode] AS VARCHAR(255)) \n" +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
                "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS partierkode\n" +
                "                        , STUFF(( \n" +
                "                        SELECT N', ' + CAST([stortingperiode] AS VARCHAR(255))\n" +
                "                        FROM regjering_stortingperiode \n" +
                "                        WHERE regjering_stortingperiode.regjering = regjering.id \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS stortingperiodekode \n" +
                "        FROM    regjering\n" +
                "\t    WHERE   (YEAR(slutt) <> 9999) AND (type = 5) \n" +
                "\t\tORDER BY\tkode\t\t "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringadhoc[i] = new Statsraadsarkivet();

                regjeringadhoc[i].setRegjering_reg_kode((Integer)rader[i].get("kode"));
        if (this.engelsk) {
                regjeringadhoc[i].setRegjeringsnavn_NO((String)rader[i].get("ENGstatsraadsbetegnelse"));}
                else {
            regjeringadhoc[i].setRegjeringsnavn_NO((String) rader[i].get("NOstatsraadsbetegnelse"));
        }
                regjeringadhoc[i].setStartaar((Integer)rader[i].get("startaar"));
                regjeringadhoc[i].setBlokk((Integer)rader[i].get("blokk"));
                regjeringadhoc[i].setPartikode((String)rader[i].get("partierkode"));
                regjeringadhoc[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
        regjeringadhoc[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
        regjeringadhoc[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));

               /*String sqlSelect2 = "SELECT     SUM(DATEDIFF(day, t_felles_statsraader.start, t_felles_statsraader.slutt) + 1) AS Antdag, t_regjering_norske_regjeringer_ministerier.kode, \n" +
                "                      t_felles_person.kjoenn\n" +
                "FROM         t_felles_person RIGHT OUTER JOIN\n" +
                "                      t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "                      t_regjering_norske_regjeringer_ministerier ON t_felles_statsraader.min_reg = t_regjering_norske_regjeringer_ministerier.min_reg\n" +
                "WHERE     (YEAR(t_regjering_norske_regjeringer_ministerier.slutt) <> 9999) AND (t_felles_statsraader.start BETWEEN \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND (t_regjering_norske_regjeringer_ministerier.min_reg = 2) AND t_regjering_norske_regjeringer_ministerier.kode=? \n" +
                "GROUP BY t_regjering_norske_regjeringer_ministerier.kode, t_felles_person.kjoenn\n" +
                "ORDER BY t_regjering_norske_regjeringer_ministerier.kode ";
               */
        String sqlSelect2 = "SELECT     SUM(DATEDIFF(day, regjering_medlemmer.start, regjering_medlemmer.slutt) + 1) AS Antdag, regjering.id, \n" +
                "           politikere.kjoenn\n" +
                "           FROM         politikere RIGHT OUTER JOIN\n" +
                "           regjering_medlemmer ON politikere.id = regjering_medlemmer.person LEFT OUTER JOIN\n" +
                "           regjering ON regjering_medlemmer.type = regjering.type\n" +
                "           WHERE     (YEAR(regjering.slutt) <> 9999) AND (regjering_medlemmer.start BETWEEN " +
                "           regjering.start AND regjering.slutt) AND (regjering.type = 5) AND regjering.id= ?" +
                "           GROUP BY regjering.id, politikere.kjoenn\n" +
                "           ORDER BY regjering.id  ";

        List values2 = new ArrayList();
          values2.add((Integer)rader[i].get("kode"));
         sqlCB2.setConnection(this.conn);
         sqlCB2.setSqlValue(sqlSelect2); //sporring
        sqlCB2.setValues(values2); //parameter
        result2 = sqlCB2.executeQuery();
        rader2 = result2.getRows();

              for (int k = 0; k < rader2.length; k++) {
               //regjeringadhoc[k].setAntdag((Integer)rader2[k].get("Antdag"));

              double  menn=0.0;
             double kvinner=0.0;
              if((Integer)rader2[k].get("kjoenn") ==1){ menn = Double.valueOf((rader2[k].get("Antdag").toString())).doubleValue();  regjeringadhoc[i].setMenn((Integer) rader2[k].get("Antdag"));}
              if((Integer)rader2[k].get("kjoenn") ==2){ kvinner = Double.valueOf((rader2[k].get("Antdag").toString())).doubleValue();  regjeringadhoc[i].setKvinner((Integer)rader2[k].get("Antdag"));}

                  //regjeringadhoc[i].setKvinner((Integer)rader2[k].get("Antdag"));}

                //regjeringadhoc[i].setMenn((menn/(menn+kvinner))*100);
                  //regjeringadhoc[i].setKvinner((kvinner / (kvinner + menn)) * 100);

              }


            }
        return regjeringadhoc;
    }




    private Statsraadsarkivet[] getDagensregjering(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] regjeringadhoc = null;

        // resultat fra sql-sporring.
        Result result = null;
        Result result2 = null;
        Result result_Avgaatte_statsraader_menn = null;
        Result result_Avgaatte_statsraader_kvinner = null;
        Result result_statsraader_menn = null;
        Result result_statsraader_kvinner = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        SqlCommandBean sqlCB2 = new SqlCommandBean();
        SqlCommandBean sqlCB_Avgaatte_statsraader_menn = new SqlCommandBean();
        SqlCommandBean sqlCB_Avgaatte_statsraader_kvinner = new SqlCommandBean();
        SqlCommandBean sqlCB_statsraader_menn = new SqlCommandBean();
        SqlCommandBean sqlCB_statsraader_kvinner = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        SortedMap[] rader2 = null;
        SortedMap[] rader_Avgaatte_statsraader_menn = null;
        SortedMap[] rader_Avgaatte_statsraader_kvinner = null;
        SortedMap[] rader_statsraader_menn = null;
        SortedMap[] rader_statsraader_kvinner = null;
        // SQL-sporring.
        /*String sqlSelect = "SELECT     MAX(kode) AS SistePeriode\n" +
                "FROM         t_regjering_norske_regjeringer_ministerier  "
                + (condition != null ? " " + condition : "");
            */
        String sqlSelect = "SELECT  MAX(id) AS SistePeriode\n" +
                "        FROM   regjering  "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Statsraadsarkivet[rader.length];

        for (int k = 0; k < rader.length; k++) {
            regjeringadhoc[k] = new Statsraadsarkivet();

            /*String sqlSelect2 = "SELECT     \t\tkode, Statsraadsbetegnelse as NOstatsraadsbetegnelse, ENGstatsraadsbetegnelse as ENGstatsraadsbetegnelse, start, slutt, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear ,  PartierKode, StortingPeriodeKode, Aar as startaar, Blokk\n" +
                    "FROM         \tt_regjering_norske_regjeringer_ministerier\n" +
                    "WHERE     \t\tkode = ?\n" +
                    "GROUP BY \t\tkode, Statsraadsbetegnelse, ENGstatsraadsbetegnelse, start, slutt, PartierKode, StortingPeriodeKode, Aar, Blokk\n" +
                    "ORDER BY \t\tkode ";
                */
            String sqlSelect2 = "SELECT  id as kode, navn_nb as NOstatsraadsbetegnelse, navn_en as ENGstatsraadsbetegnelse, start, slutt,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, \n" +
                    "  Blokk,  YEAR(start) as startaar\n" +
                    "  , STUFF(( \n" +
                    "                        SELECT N'+ ' + CAST([eintaltekst_forkorting] AS VARCHAR(255)) \n" +
                    "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti \n" +
                    "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                    "                        FOR XML PATH ('')), 1, 1, '') AS partier , STUFF(( \n" +
                    "                        SELECT N', ' + CAST([kode] AS VARCHAR(255)) \n" +
                    "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
                    "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                    "                        FOR XML PATH ('')), 1, 1, '') AS partierkode\n" +
                    "                        , STUFF(( \n" +
                    "                        SELECT N', ' + CAST([stortingperiode] AS VARCHAR(255))\n" +
                    "                        FROM regjering_stortingperiode \n" +
                    "                        WHERE regjering_stortingperiode.regjering = regjering.id \n" +
                    "                        FOR XML PATH ('')), 1, 1, '') AS stortingperiodekode \n" +
                    "   FROM    regjering " +
                    "   WHERE   id = ? " +
                    "   ORDER BY  kode  ";
            List values2 = new ArrayList();
            values2.add((Integer)rader[k].get("SistePeriode"));
            sqlCB2.setConnection(this.conn);
            sqlCB2.setSqlValue(sqlSelect2); //sporring
            sqlCB2.setValues(values2); //parameter
            result2 = sqlCB2.executeQuery();
            rader2 = result2.getRows();

            for (int i= 0; i < rader2.length; i++) {


                regjeringadhoc[i].setRegjering_reg_kode((Integer)rader2[i].get("kode"));
                if (this.engelsk) {
                    regjeringadhoc[i].setRegjeringsnavn_NO((String) rader2[i].get("ENGstatsraadsbetegnelse"));
                }else{
                    regjeringadhoc[i].setRegjeringsnavn_NO((String) rader2[i].get("NOstatsraadsbetegnelse"));
                }
                regjeringadhoc[i].setStartaar((Integer)rader2[i].get("startaar"));
                regjeringadhoc[i].setBlokk((Integer)rader2[i].get("blokk"));
                regjeringadhoc[i].setPartikode((String)rader2[i].get("partierkode"));
                regjeringadhoc[i].setStortingperiodekode((String)rader2[i].get("stortingperiodekode"));
                regjeringadhoc[i].setStart(rader2[i].get("startyear")+"-"+rader2[i].get("startmonth")+"-"+rader2[i].get("startday"));
                regjeringadhoc[i].setSlutt(rader2[i].get("sluttyear")+"-"+rader2[i].get("sluttmonth")+"-"+rader2[i].get("sluttday"));
            }

          /*  String Avgaatte_statsraader_menn = "SELECT    t_felles_person.kjoenn AS Expr1, SUM(DATEDIFF(d, t_felles_statsraader.start, \n" +
                    "                                          t_felles_statsraader.slutt + 1)) AS Antdag_avmenn, t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    FROM         t_felles_statsraader LEFT OUTER JOIN\n" +
                    "                                          t_felles_person ON t_felles_statsraader.person_id = t_felles_person.person_id INNER JOIN\n" +
                    "                                          t_regjering_norske_regjeringer_ministerier ON t_felles_statsraader.min_reg = t_regjering_norske_regjeringer_ministerier.min_reg\n" +
                    "                    WHERE     (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (t_felles_statsraader.slutt BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (NOT (YEAR(t_felles_statsraader.slutt) = 9999)) AND t_felles_person.kjoenn=1\n" +
                    "                    GROUP BY t_felles_person.kjoenn,  t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    HAVING      (t_regjering_norske_regjeringer_ministerier.kode = ?) "; */
            String Avgaatte_statsraader_menn = "SELECT    politikere.kjoenn AS Expr1, SUM(DATEDIFF(d, regjering_medlemmer.start, \n" +
                    "          regjering_medlemmer.slutt + 1)) AS Antdag_avmenn, regjering.id as kode\n" +
                    "          FROM         regjering_medlemmer LEFT OUTER JOIN\n" +
                    "          politikere ON regjering_medlemmer.person = politikere.id INNER JOIN\n" +
                    "          regjering ON regjering_medlemmer.type = regjering.type\n" +
                    "          WHERE     (regjering_medlemmer.start BETWEEN regjering.start AND regjering.slutt) AND \n" +
                    "          (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt) AND \n" +
                    "          (NOT (YEAR(regjering_medlemmer.slutt) = 9999)) AND politikere.kjoenn=1\n" +
                    "          GROUP BY politikere.kjoenn,  regjering.id\n" +
                    "          HAVING      (regjering.id = ? )";

            List value_Avgaatte_statsraader_menn = new ArrayList();
            value_Avgaatte_statsraader_menn.add((Integer) rader[k].get("SistePeriode"));
            sqlCB_Avgaatte_statsraader_menn.setConnection(this.conn);
            sqlCB_Avgaatte_statsraader_menn.setSqlValue(Avgaatte_statsraader_menn); //sporring
            sqlCB_Avgaatte_statsraader_menn.setValues(value_Avgaatte_statsraader_menn); //parameter
            result_Avgaatte_statsraader_menn = sqlCB_Avgaatte_statsraader_menn.executeQuery();
            rader_Avgaatte_statsraader_menn = result_Avgaatte_statsraader_menn.getRows();

            for (int j = 0; j < rader_Avgaatte_statsraader_menn.length; j++) {

                regjeringadhoc[j].setAvgaatte_statsraader_menn((Integer) rader_Avgaatte_statsraader_menn[j].get("Antdag_avmenn"));
            }

          /*  String Avgaatte_statsraader_kvinner = "SELECT    t_felles_person.kjoenn AS Expr1, SUM(DATEDIFF(d, t_felles_statsraader.start, \n" +
                    "                                          t_felles_statsraader.slutt + 1)) AS Antdag_avkvinner, t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    FROM         t_felles_statsraader LEFT OUTER JOIN\n" +
                    "                                          t_felles_person ON t_felles_statsraader.person_id = t_felles_person.person_id INNER JOIN\n" +
                    "                                          t_regjering_norske_regjeringer_ministerier ON t_felles_statsraader.min_reg = t_regjering_norske_regjeringer_ministerier.min_reg\n" +
                    "                    WHERE     (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (t_felles_statsraader.slutt BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (NOT (YEAR(t_felles_statsraader.slutt) = 9999)) AND t_felles_person.kjoenn=2\n" +
                    "                    GROUP BY t_felles_person.kjoenn,  t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    HAVING      (t_regjering_norske_regjeringer_ministerier.kode = ?)"; */
            String Avgaatte_statsraader_kvinner = "SELECT    politikere.kjoenn AS Expr1, SUM(DATEDIFF(d, regjering_medlemmer.start, \n" +
                    "          regjering_medlemmer.slutt + 1)) AS Antdag_avkvinner, regjering.id AS kode\n" +
                    "          FROM         regjering_medlemmer LEFT OUTER JOIN\n" +
                    "          politikere ON regjering_medlemmer.person = politikere.id INNER JOIN\n" +
                    "          regjering ON regjering_medlemmer.type = regjering.type\n" +
                    "          WHERE     (regjering_medlemmer.start BETWEEN regjering.start AND regjering.slutt) AND \n" +
                    "          (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt) AND \n" +
                    "          (NOT (YEAR(regjering_medlemmer.slutt) = 9999)) AND politikere.kjoenn=2\n" +
                    "          GROUP BY politikere.kjoenn,  regjering.id\n" +
                    "          HAVING      (regjering.id = ?) ";

            List value_Avgaatte_statsraader_kvinner = new ArrayList();
            value_Avgaatte_statsraader_kvinner.add((Integer) rader[k].get("SistePeriode"));
            sqlCB_Avgaatte_statsraader_kvinner.setConnection(this.conn);
            sqlCB_Avgaatte_statsraader_kvinner.setSqlValue(Avgaatte_statsraader_kvinner); //sporring
            sqlCB_Avgaatte_statsraader_kvinner.setValues(value_Avgaatte_statsraader_kvinner); //parameter
            result_Avgaatte_statsraader_kvinner = sqlCB_Avgaatte_statsraader_kvinner.executeQuery();
            rader_Avgaatte_statsraader_kvinner = result_Avgaatte_statsraader_kvinner.getRows();

            for (int p = 0; p < rader_Avgaatte_statsraader_kvinner.length; p++) {

                regjeringadhoc[p].setAvgaatte_statsraader_kvinner((Integer) rader_Avgaatte_statsraader_kvinner[p].get("Antdag_avkvinner"));
            }


            /*String statsraader_menn = " SELECT     t_felles_person.kjoenn, t_felles_person.kjoenn AS Expr1, SUM(DATEDIFF(d, t_felles_statsraader.start, GETDATE())) AS Antdagmenn, \n" +
                    "                                          t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    FROM         t_felles_statsraader LEFT OUTER JOIN\n" +
                    "                                          t_felles_person ON t_felles_statsraader.person_id = t_felles_person.person_id INNER JOIN\n" +
                    "                                          t_regjering_norske_regjeringer_ministerier ON t_felles_statsraader.min_reg = t_regjering_norske_regjeringer_ministerier.min_reg\n" +
                    "                    WHERE     (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (t_felles_statsraader.slutt BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (YEAR(t_felles_statsraader.slutt) = 9999) AND t_felles_person.kjoenn=1\n" +
                    "                    GROUP BY t_felles_person.kjoenn, t_felles_person.kjoenn, t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    HAVING      (t_regjering_norske_regjeringer_ministerier.kode = ?)"; */

            String statsraader_menn = " SELECT     politikere.kjoenn, politikere.kjoenn AS Expr1, SUM(DATEDIFF(d, regjering_medlemmer.start, GETDATE())) AS Antdagmenn, \n" +
                    "           regjering.id AS kode\n" +
                    "           FROM   regjering_medlemmer LEFT OUTER JOIN\n" +
                    "           politikere ON regjering_medlemmer.person = politikere.id INNER JOIN\n" +
                    "           regjering ON regjering_medlemmer.type = regjering.type\n" +
                    "           WHERE     (regjering_medlemmer.start BETWEEN regjering.start AND regjering.slutt) AND\n" +
                    "           (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt) AND \n" +
                    "           (YEAR(regjering_medlemmer.slutt) = 9999) AND politikere.kjoenn=1\n" +
                    "           GROUP BY politikere.kjoenn, politikere.kjoenn, regjering.id\n" +
                    "           HAVING      (regjering.id = ? ) ";

            List value_statsraader_menn = new ArrayList();
            value_statsraader_menn.add((Integer) rader[k].get("SistePeriode"));
            sqlCB_statsraader_menn.setConnection(this.conn);
            sqlCB_statsraader_menn.setSqlValue(statsraader_menn); //sporring
            sqlCB_statsraader_menn.setValues(value_statsraader_menn); //parameter
            result_statsraader_menn = sqlCB_statsraader_menn.executeQuery();
            rader_statsraader_menn = result_statsraader_menn.getRows();

            for (int w = 0; w < rader_statsraader_menn.length; w++) {

                regjeringadhoc[w].setMenn((Integer) rader_statsraader_menn[w].get("Antdagmenn"));
            }

            /*String statsraader_kvinner = " SELECT     t_felles_person.kjoenn, t_felles_person.kjoenn AS Expr1, SUM(DATEDIFF(d, t_felles_statsraader.start, GETDATE())) AS Antdagkvinner, \n" +
                    "                                          t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    FROM         t_felles_statsraader LEFT OUTER JOIN\n" +
                    "                                          t_felles_person ON t_felles_statsraader.person_id = t_felles_person.person_id INNER JOIN\n" +
                    "                                          t_regjering_norske_regjeringer_ministerier ON t_felles_statsraader.min_reg = t_regjering_norske_regjeringer_ministerier.min_reg\n" +
                    "                    WHERE     (t_felles_statsraader.start BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (t_felles_statsraader.slutt BETWEEN t_regjering_norske_regjeringer_ministerier.start AND t_regjering_norske_regjeringer_ministerier.slutt) AND \n" +
                    "                                          (YEAR(t_felles_statsraader.slutt) = 9999) AND t_felles_person.kjoenn=2\n" +
                    "                    GROUP BY t_felles_person.kjoenn, t_felles_person.kjoenn, t_regjering_norske_regjeringer_ministerier.kode\n" +
                    "                    HAVING      (t_regjering_norske_regjeringer_ministerier.kode = ?)";
            */
            String statsraader_kvinner = " SELECT     politikere.kjoenn, politikere.kjoenn AS Expr1, SUM(DATEDIFF(d, regjering_medlemmer.start, GETDATE())) AS Antdagkvinner, \n" +
                    "           regjering.id AS kode\n" +
                    "           FROM  regjering_medlemmer LEFT OUTER JOIN\n" +
                    "           politikere ON regjering_medlemmer.person = politikere.id INNER JOIN\n" +
                    "           regjering ON regjering_medlemmer.type = regjering.type\n" +
                    "           WHERE     (regjering_medlemmer.start BETWEEN regjering.start AND regjering.slutt) AND \n" +
                    "           (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt) AND \n" +
                    "           (YEAR(regjering_medlemmer.slutt) = 9999) AND politikere.kjoenn=2\n" +
                    "           GROUP BY politikere.kjoenn, politikere.kjoenn, regjering.id\n" +
                    "           HAVING      (regjering.id = ? )  ";

            List value_statsraader_kvinner = new ArrayList();
            value_statsraader_kvinner.add((Integer) rader[k].get("SistePeriode"));
            sqlCB_statsraader_kvinner.setConnection(this.conn);
            sqlCB_statsraader_kvinner.setSqlValue(statsraader_kvinner); //sporring
            sqlCB_statsraader_kvinner.setValues(value_statsraader_kvinner); //parameter
            result_statsraader_kvinner = sqlCB_statsraader_kvinner.executeQuery();
            rader_statsraader_kvinner = result_statsraader_kvinner.getRows();

            for (int y = 0; y < rader_statsraader_kvinner.length; y++) {

                regjeringadhoc[y].setKvinner((Integer) rader_statsraader_kvinner[y].get("Antdagkvinner"));
            }



        }
        return regjeringadhoc;
    }



/*
    private Statsraadsarkivet[] getDagensRegjeringenesvarighet(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] regjeringadhoc = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  TOP 1   Statsraadsbetegnelse as NOstatsraadsbetegnelse, ENGstatsraadsbetegnelse as ENGtatsraadsbetegnelse, start, slutt, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear , PartierKode, StortingPeriodeKode, YEAR(slutt) AS Sluttaar, kode, min_reg,\n" +
                "\t\t\tAar AS startaar, Blokk, Partier,  ((DATEDIFF(day, start, GETDATE()))+1)/11 AS Antdag_stolpe , (DATEDIFF(day, start, GETDATE())+1) AS Antdag\n" +
                "FROM        t_regjering_norske_regjeringer_ministerier\n" +
                "ORDER BY\tkode desc "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringadhoc[i] = new Statsraadsarkivet();

                regjeringadhoc[i].setRegjering_reg_kode((Integer)rader[i].get("kode"));
                regjeringadhoc[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                regjeringadhoc[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                regjeringadhoc[i].setStartaar((Integer)rader[i].get("startaar"));
                regjeringadhoc[i].setAntdag((Integer)rader[i].get("Antdag"));
                regjeringadhoc[i].setAntdag_stolpe((Integer)rader[i].get("Antdag_stolpe"));
                regjeringadhoc[i].setPartikode((String)rader[i].get("partierkode"));
                regjeringadhoc[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
                regjeringadhoc[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));

            }
        return regjeringadhoc;
    }

*/

}

