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
public class RegjeringenesvarighetLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public RegjeringenesvarighetLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Statsraadsarkivet[] getRegjeringenesvarighet() throws Exception {
           String condition = "  ";
           return this.getRegjeringenesvarighet(condition, null);
       }

     public Statsraadsarkivet[] getDagensRegjeringenesvarighet() throws Exception {
           String condition = "  ";
           return this.getDagensRegjeringenesvarighet(condition, null);
       }

/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private Statsraadsarkivet[] getRegjeringenesvarighet(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] regjeringadhoc = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*String sqlSelect = "SELECT  Statsraadsbetegnelse as NOstatsraadsbetegnelse, ENGstatsraadsbetegnelse as ENGtatsraadsbetegnelse, start, slutt,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear  ,PartierKode, StortingPeriodeKode, ((DATEDIFF(day, start, slutt))+1)/11 AS Antdag_stolpe, YEAR(slutt) AS Sluttaar, kode, min_reg, \n" +
                "\t\t\tAar AS startaar, Blokk, Partier, (DATEDIFF(day, start, slutt)+1) AS Antdag\n" +
                "FROM        t_regjering_norske_regjeringer_ministerier\n" +
                "WHERE     \t(YEAR(slutt) <> 9999) AND (min_reg = 2) \n" +
                "ORDER BY\tkode "
                + (condition != null ? " " + condition : "");
        */
        String sqlSelect = "SELECT  navn_nb as NOstatsraadsbetegnelse, navn_en as ENGstatsraadsbetegnelse, start, slutt,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, \n" +
                " ((DATEDIFF(day, start, slutt))+1)/11 AS Antdag_stolpe, YEAR(slutt) AS Sluttaar, id as kode, type as min_reg, " +
                "         Blokk,  (DATEDIFF(day, start, slutt)+1) AS Antdag " +
                "  , STUFF(( " +
                "                        SELECT N'+ ' + CAST([eintaltekst_forkorting] AS VARCHAR(255)) " +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti " +
                "                        WHERE regjering_partier.regjering = regjering.id  " +
                "                        FOR XML PATH ('')), 1, 1, '') AS partier , STUFF(( " +
                "                        SELECT N', ' + CAST([kode] AS VARCHAR(255)) " +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti " +
                "                        WHERE regjering_partier.regjering = regjering.id  " +
                "                        FOR XML PATH ('')), 1, 1, '') AS partierkode " +
                "                        , STUFF(( " +
                "                        SELECT N', ' + CAST([stortingperiode] AS VARCHAR(255)) " +
                "                        FROM regjering_stortingperiode " +
                "                        WHERE regjering_stortingperiode.regjering = regjering.id " +
                "                        FOR XML PATH ('')), 1, 1, '') AS stortingperiodekode " +
                "        FROM    regjering " +
                "       WHERE   (YEAR(slutt) <> 9999) AND (type = 5) " +
                "      ORDER BY kode "
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
            regjeringadhoc[i].setRegjeringsnavn_NO((String) rader[i].get("ENGstatsraadsbetegnelse"));
        }else{
                regjeringadhoc[i].setRegjeringsnavn_NO((String) rader[i].get("NOstatsraadsbetegnelse"));
            }
                regjeringadhoc[i].setStartaar((Integer)rader[i].get("startyear"));
                regjeringadhoc[i].setAntdag((Integer)rader[i].get("Antdag"));
                regjeringadhoc[i].setAntdag_stolpe((Integer)rader[i].get("Antdag_stolpe"));
                regjeringadhoc[i].setPartikode((String)rader[i].get("partierkode"));
                regjeringadhoc[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
                regjeringadhoc[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));
            }
        return regjeringadhoc;
    }

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
        /*String sqlSelect = "SELECT  TOP 1   Statsraadsbetegnelse as NOstatsraadsbetegnelse, ENGstatsraadsbetegnelse as ENGtatsraadsbetegnelse, start, slutt, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear , PartierKode, StortingPeriodeKode, YEAR(slutt) AS Sluttaar, kode, min_reg,\n" +
                "\t\t\tAar AS startaar, Blokk, Partier,  ((DATEDIFF(day, start, GETDATE()))+1)/11 AS Antdag_stolpe , (DATEDIFF(day, start, GETDATE())) AS Antdag\n" +
                "FROM        t_regjering_norske_regjeringer_ministerier\n" +
                "ORDER BY\tkode desc "
                */
         String sqlSelect = "SELECT  TOP 1   navn_nb as  NOstatsraadsbetegnelse, navn_en as ENGstatsraadsbetegnelse, start, slutt, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, \n" +
                 "\t\t YEAR(slutt) AS Sluttaar, id as kode, type as min_reg,\n" +
                 "          Blokk,   ((DATEDIFF(day, start, GETDATE()))+1)/11 AS Antdag_stolpe , (DATEDIFF(day, start, GETDATE())) AS Antdag\n" +
                 "\t\t   , STUFF(( \n" +
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
                 "         FROM       regjering\n" +
                 "         ORDER BY\tkode desc "
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
            regjeringadhoc[i].setRegjeringsnavn_NO((String) rader[i].get("ENGstatsraadsbetegnelse"));
        }else {
            regjeringadhoc[i].setRegjeringsnavn_NO((String) rader[i].get("NOstatsraadsbetegnelse"));
        }
                regjeringadhoc[i].setStartaar((Integer)rader[i].get("startyear"));
                regjeringadhoc[i].setAntdag((Integer)rader[i].get("Antdag"));
                regjeringadhoc[i].setAntdag_stolpe((Integer)rader[i].get("Antdag_stolpe"));
                regjeringadhoc[i].setPartikode((String)rader[i].get("partierkode"));
                regjeringadhoc[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
                regjeringadhoc[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));

            }
        return regjeringadhoc;
    }

}

