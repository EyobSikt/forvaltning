package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.Statsraadsarkivet;

import javax.servlet.jsp.jstl.sql.Result;
import java.math.BigDecimal;
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
public class StatsraadsAlderLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatsraadsAlderLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Statsraadsarkivet[] getStatsraadsAldersfordeling() throws Exception {
           String condition = "  ";
           return this.getStatsraadsAldersfordeling(condition, null);
       }



/***************************************************************************************************

 Private metoder

***************************************************************************************************/


    private Statsraadsarkivet[] getStatsraadsAldersfordeling(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] regjeringadhoc = null;

        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
        String sqlSelect = "SELECT     AVG(CAST(YEAR(t_regjering_norske_regjeringer_ministerier.start) - t_felles_person.faar AS decimal(5, 1))) AS tt, \n" +
                "                      t_regjering_norske_regjeringer_ministerier.kode, t_regjering_norske_regjeringer_ministerier.start, PartierKode, StortingPeriodeKode, t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse as NOstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse as ENGstatsraadsbetegnelse,\t\t \n" +
                "                      COUNT(t_regjering_norske_regjeringer_ministerier.kode) AS Antall, t_regjering_norske_regjeringer_ministerier.Aar AS startaar, YEAR(t_regjering_norske_regjeringer_ministerier.slutt) AS sluttaar, t_regjering_norske_regjeringer_ministerier.Blokk,\n" +
                "\t\t\t\t\t  t_regjering_norske_regjeringer_ministerier.min_reg, t_regjering_norske_regjeringer_ministerier.slutt, PartierKode, StortingPeriodeKode, DAY(t_regjering_norske_regjeringer_ministerier.start) AS startday, MONTH(t_regjering_norske_regjeringer_ministerier.start) AS startmonth , YEAR(t_regjering_norske_regjeringer_ministerier.start) AS startyear , DAY(t_regjering_norske_regjeringer_ministerier.slutt) AS sluttday, MONTH(t_regjering_norske_regjeringer_ministerier.slutt) AS sluttmonth, YEAR(t_regjering_norske_regjeringer_ministerier.slutt) AS sluttyear  \n" +
                "FROM         t_regjering_norske_regjeringer_ministerier INNER JOIN\n" +
                "                      t_felles_statsraader ON t_regjering_norske_regjeringer_ministerier.start >= t_felles_statsraader.start AND \n" +
                "                      t_regjering_norske_regjeringer_ministerier.start <= t_felles_statsraader.slutt INNER JOIN\n" +
                "                      t_felles_person ON t_felles_statsraader.person_id = t_felles_person.person_id\n" +
                "GROUP BY t_regjering_norske_regjeringer_ministerier.kode, t_regjering_norske_regjeringer_ministerier.start, PartierKode, StortingPeriodeKode,\n" +
                "                      t_regjering_norske_regjeringer_ministerier.Statsraadsbetegnelse,t_regjering_norske_regjeringer_ministerier.ENGstatsraadsbetegnelse, t_regjering_norske_regjeringer_ministerier.Aar,\n" +
                "\t\t\t\t\t  t_regjering_norske_regjeringer_ministerier.Blokk, t_regjering_norske_regjeringer_ministerier.min_reg, t_regjering_norske_regjeringer_ministerier.slutt,\n" +
                "\t\t\t\t\t  PartierKode, StortingPeriodeKode\n" +
                "ORDER BY t_regjering_norske_regjeringer_ministerier.kode " */

        String sqlSelect = "SELECT  AVG(CAST(YEAR(regjering.start) - YEAR(politikere.fodt) AS decimal(5, 1))) AS tt, \n" +
                "        regjering.id as kode, regjering.start, regjering.navn_nb as NOstatsraadsbetegnelse, regjering.navn_en as ENGstatsraadsbetegnelse,\n" +
                "        COUNT(regjering.id)  AS Antall, YEAR(regjering.start) AS startaar, YEAR(regjering.slutt) AS sluttaar, regjering.Blokk,\n" +
                "        regjering.type as min_reg, regjering.slutt,  DAY(regjering.start) AS startday, MONTH(regjering.start) AS startmonth , YEAR(regjering.start) AS startyear , DAY(regjering.slutt) AS sluttday, MONTH(regjering.slutt) AS sluttmonth, YEAR(regjering.slutt) AS sluttyear \n" +
                "        , STUFF(( \n" +
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
                "\t\tFROM  regjering INNER JOIN\n" +
                "        regjering_medlemmer ON regjering.start >= regjering_medlemmer.start AND \n" +
                "        regjering.start <= regjering_medlemmer.slutt INNER JOIN\n" +
                "        politikere ON regjering_medlemmer.person = politikere.id\n" +
                "        WHERE regjering.Blokk is not null " +
                "        GROUP BY regjering.id, regjering.start, \n" +
                "        regjering.navn_nb,regjering.navn_en,\n" +
                "        regjering.Blokk, regjering.type, regjering.slutt\n" +
                "        ORDER BY regjering.id "
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
                regjeringadhoc[i].setSluttaar((Integer)rader[i].get("sluttaar"));
                regjeringadhoc[i].setBlokk((Integer)rader[i].get("blokk"));
                regjeringadhoc[i].setAntalder((BigDecimal)rader[i].get("tt"));
                regjeringadhoc[i].setMin_reg((Integer)rader[i].get("min_reg"));
                regjeringadhoc[i].setPartikode((String)rader[i].get("partierkode"));
                regjeringadhoc[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
                regjeringadhoc[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));

                BigDecimal x = (BigDecimal)rader[i].get("tt");
                BigDecimal mimic_1 = new BigDecimal(3);
                regjeringadhoc[i].setAntaldertotal(x.multiply(mimic_1));

            }
        return regjeringadhoc;
    }


    

}

