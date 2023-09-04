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
public class StatssekretarAlderLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatssekretarAlderLogikk() {
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
        String sqlSelect = "SELECT  AVG(CAST(YEAR(regjering.start) - YEAR(politikere.fodt) AS decimal(5, 1))) AS tt, \n" +
                "        regjering.id AS kode, regjering.start,  regjering.navn_nb as NOstatsraadsbetegnelse, regjering.navn_en as ENGstatsraadsbetegnelse,\n" +
                "        COUNT(regjering.id) AS Antall, YEAR(regjering.start) AS startaar, YEAR(regjering.slutt) AS sluttaar, regjering.Blokk,\n" +
                "        regjering.min_reg, regjering.slutt,  DAY(regjering.start) AS startday, MONTH(regjering.start) AS startmonth , YEAR(regjering.start) AS startyear , DAY(regjering.slutt) AS sluttday, MONTH(regjering.slutt) AS sluttmonth, YEAR(regjering.slutt) AS sluttyear  \n" +
                "         ,  STUFF(( \n" +
                "                        SELECT N', ' + CAST([kode] AS VARCHAR(255)) \n" +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
                "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS partierkode\n" +
                "\t\t\t\t\t\t, STUFF(( \n" +
                "                        SELECT N', ' + CAST([stortingperiode] AS VARCHAR(255))\n" +
                "                        FROM regjering_stortingperiode \n" +
                "                        WHERE regjering_stortingperiode.regjering = regjering.id \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS stortingperiodekode \n" +
                "\t\tFROM regjering INNER JOIN\n" +
                "        regjering_statssekretaerer ON regjering.start +28 >= regjering_statssekretaerer.start AND \n" +
                "        regjering.start <= regjering_statssekretaerer.slutt INNER JOIN\n" +
                "        politikere ON regjering_statssekretaerer.person = politikere.id \n" +
                "        WHERE regjering.min_reg = 2 \n" +
                "        GROUP BY regjering.id, regjering.start, \n" +
                "        regjering.navn_nb, regjering.navn_en, YEAR(regjering.start),\n" +
                "        regjering.Blokk, regjering.min_reg, regjering.slutt\n" +
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

