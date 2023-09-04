package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.MinisterRegjering;
import no.nsd.polsys.modell.regjering.Statsraadsarkivet;

import javax.servlet.jsp.jstl.sql.Result;
import java.math.BigDecimal;
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
public class LastnedLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public LastnedLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Statsraadsarkivet[] getStatsraadsdatasett() throws Exception {
           String condition = "  ";
           return this.getStatsraadsdatasett(condition, null);
       }

    public Statsraadsarkivet[] getStatssekretardatasett() throws Exception {
        String condition = "  ";
        return this.getStatssekretardatasett(condition, null);
    }

/***************************************************************************************************

 Private metoder

***************************************************************************************************/


private Statsraadsarkivet[] getStatsraadsdatasett(String condition, List values) throws Exception {
    // tabell som returneres fra denne metoden.
    Statsraadsarkivet[] statsraaddatasett = null;

    // resultat fra sql-sporring.
    Result result = null;
    // objekt som brukes til aa utfore sql-sporring.
    SqlCommandBean sqlCB = new SqlCommandBean();
    // inneholder data fra sql-sporring.
    SortedMap[] rader = null;
    // SQL-sporring.

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
    statsraaddatasett = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
        statsraaddatasett[i] = new Statsraadsarkivet();

        statsraaddatasett[i].setRegjering_reg_kode((Integer)rader[i].get("kode"));
        if (this.engelsk) {statsraaddatasett[i].setRegjeringsnavn_NO((String)rader[i].get("ENGstatsraadsbetegnelse"));}
        else{statsraaddatasett[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));}
        //statsraaddatasett[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
        statsraaddatasett[i].setStartaar((Integer)rader[i].get("startaar"));
        statsraaddatasett[i].setSluttaar((Integer)rader[i].get("sluttaar"));
        statsraaddatasett[i].setBlokk((Integer)rader[i].get("blokk"));
        statsraaddatasett[i].setAntalder((BigDecimal)rader[i].get("tt"));
        statsraaddatasett[i].setMin_reg((Integer)rader[i].get("min_reg"));
        statsraaddatasett[i].setPartikode((String)rader[i].get("partierkode"));
        statsraaddatasett[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
        statsraaddatasett[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
        statsraaddatasett[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));

        BigDecimal x = (BigDecimal)rader[i].get("tt");
        BigDecimal mimic_1 = new BigDecimal(3);
        statsraaddatasett[i].setAntaldertotal(x.multiply(mimic_1));

    }
    return statsraaddatasett;
}



    /*Statsraader datasett getStatsraadRegjeringsvisBeskrivelsedatasett */

    public MinisterRegjering[] getStatsraadRegjeringsvisBeskrivelsedatasett( String[] regjeringsperiode) throws Exception {

        int depkode = 0;
        int regjeringkode =0;

        MinisterRegjering[] ministerregjering = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        // SQL-sporring.
        String sqlSelect = " SELECT  regjering.navn_nb as Statsraadsbetegnelse,regjering.navn_en as engStatsraadsbetegnelse, regjering.id as regjeringkode,   YEAR(politikere.fodt) as foedtaar, YEAR(politikere.doed) as doedsaar, regjering_departement.navn_nb as eintaltekst, regjering_departement.navn_en as engeintaltekst, regjering_departement.Kode AS depkode, regjering_medlemmer.stilling_avvik_nb as stilling_avvik,\n" +
                "        DAY(regjering_medlemmer.start)AS startday,  MONTH(regjering_medlemmer.start)AS startmonth, YEAR(regjering_medlemmer.start)AS startyear, DAY(regjering_medlemmer.slutt) AS sluttday , MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, regjering_medlemmer.kommentar_ekstern_nb as eksternkommentar, politikere.fornavn,\n" +
                "        politikere.etternavn as navn, politikere.kjoenn, regjering_medlemmer.parti_ved_start, t_felles_partinavn.eintaltekst_forkorting as eintaltekst_forkorting, \n" +
                "        t_felles_partinavn.eintaltekst as eintaltekst_no , t_felles_partinavn.ENGeintaltekst as eintaltekst_en, regjering.antall_partier_i_reg, regjering.id AS regkode, regjering_medlemmer.person as person_id, \n" +
                "        politikere.initialer\n" +
                "        FROM     politikere\n" +
                "        RIGHT OUTER JOIN regjering_medlemmer \n" +
                "        LEFT OUTER JOIN t_felles_partinavn ON regjering_medlemmer.parti_ved_start = t_felles_partinavn.kode ON \n" +
                "        politikere.id = regjering_medlemmer.person \n" +
                "        LEFT OUTER JOIN regjering_departement ON regjering_medlemmer.kode_dep = regjering_departement.kode \n" +
                "        LEFT OUTER JOIN regjering ON regjering_medlemmer.type = regjering.type " +
                "        WHERE   (regjering_medlemmer.start BETWEEN regjering.start AND regjering.slutt) AND \n" +
                "                                         (regjering_medlemmer.slutt BETWEEN regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt)  \n";
/*
        if(fraaar !=-1){
            sqlSelect+=  " AND ( YEAR(regjering.start) >= ?)  ";
        }
        if(tilaar !=-1){
            sqlSelect+=  " AND ( YEAR(regjering.slutt) <= ?) ";
        }

        sqlSelect+=  " OR (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt) ";

        if(fraaar !=-1){
            sqlSelect+=  " AND ( YEAR(regjering.start) >= ?)  ";
        }
        if(tilaar !=-1){
            sqlSelect+=  " AND ( YEAR(regjering.slutt) <= ?) ";
        }
        */
        if(regjeringsperiode !=null &&  !regjeringsperiode.equals("") && regjeringsperiode.length>0 ){
            sqlSelect += " AND  (    ";
            sqlSelect += "   CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?    ";
            for(int i=1; i<regjeringsperiode.length; i++){
                if(regjeringsperiode[i]!=""){
                    sqlSelect += " OR CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?  ";
                }
            }
            sqlSelect += "  )    ";
        }
        sqlSelect+=  " OR (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt) ";

        if(regjeringsperiode !=null &&  !regjeringsperiode.equals("") && regjeringsperiode.length>0 ){
            sqlSelect += " AND  (    ";
            sqlSelect += "   CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?    ";
            for(int i=1; i<regjeringsperiode.length; i++){
                if(regjeringsperiode[i]!=""){
                    sqlSelect += " OR CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?  ";
                }
            }
            sqlSelect += "  )    ";
        }

        sqlSelect+=  " AND  (regjering_medlemmer.slutt BETWEEN  regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt) ";
        sqlSelect+=      " ORDER BY regjering.id, regjering_departement.sortering, regjering_departement.navn_nb, regjering_medlemmer.stilling, regjering_medlemmer.start ";

        // PreparedStatement sta = conn.prepareStatement(sqlSelect);

        /*
        if(fraaar !=-1){
        sta.setInt(1,fraaar);
        }
        if(tilaar !=-1){
        sta.setInt(2,tilaar);
        }
        if(fraaar !=-1){
        sta.setInt(3,fraaar);
        }
        if(tilaar !=-1){
        sta.setInt(4,tilaar);
        }
        */
        List values = new ArrayList();
        if(regjeringsperiode !=null && !regjeringsperiode.equals("") && regjeringsperiode.length>0){
            values.add(  regjeringsperiode[0]+" "  );
            for(int i=1; i<regjeringsperiode.length; i++){
                if(regjeringsperiode[i]!=""){ values.add(  regjeringsperiode[i]+" "  );}
            }
        }
        if(regjeringsperiode !=null && !regjeringsperiode.equals("") && regjeringsperiode.length>0){
            values.add(  regjeringsperiode[0]+" "  );
            for(int i=1; i<regjeringsperiode.length; i++){
                if(regjeringsperiode[i]!=""){ values.add(  regjeringsperiode[i]+" "  );}
            }
        }
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];
        //int kode = 0;
        for (int i = 0; i < rader.length; i++) {
            ministerregjering[i] = new MinisterRegjering();
            if(depkode !=(Integer)rader[i].get("depkode")){
                if (this.engelsk) {
                    ministerregjering[i].setStatsraadnavn((String) rader[i].get("engeintaltekst"));
                }else{
                    ministerregjering[i].setStatsraadnavn((String) rader[i].get("eintaltekst"));
                }
            }
            depkode =(Integer) rader[i].get("depkode");


            if(regjeringkode != (Integer)rader[i].get("regjeringkode")){
                if (this.engelsk) {
                    ministerregjering[i].setMinisterium((String) rader[i].get("engStatsraadsbetegnelse"));
                }else{
                    ministerregjering[i].setMinisterium((String) rader[i].get("statsraadsbetegnelse"));
                }
            }
            regjeringkode = (Integer) rader[i].get("regjeringkode");

            ministerregjering[i].setFornavn((String)rader[i].get("fornavn"));
            ministerregjering[i].setEtternavn((String)rader[i].get("navn"));
            if((Integer) rader[i].get("kjoenn")==1){if (this.engelsk) {ministerregjering[i].setKjoenn("Man");}else{ministerregjering[i].setKjoenn("Mann");}}
            if((Integer) rader[i].get("kjoenn")==2){if (this.engelsk) {ministerregjering[i].setKjoenn("Woman");}else{ministerregjering[i].setKjoenn("Kvinne");}}
            if((Integer)rader[i].get("foedtaar")==null){}else{ ministerregjering[i].setFoedt((Integer)rader[i].get("foedtaar"));}
            if((Integer)rader[i].get("doedsaar")==null){}else{ministerregjering[i].setDoed((Integer)rader[i].get("doedsaar"));}
            if (this.engelsk) {ministerregjering[i].setPartinavn((String)rader[i].get("eintaltekst_en"));}else{ministerregjering[i].setPartinavn((String)rader[i].get("eintaltekst_no"));}
            ministerregjering[i].setStartdato((Integer)rader[i].get("startday")+"."+(Integer)rader[i].get("startmonth")+"."+(Integer)rader[i].get("startyear"));
            if(rader[i].get("sluttyear").equals(9999)){ministerregjering[i].setSlutt("  ");}
            else {
                ministerregjering[i].setSluttdato((Integer)rader[i].get("sluttday")+"."+(Integer)rader[i].get("sluttmonth")+"."+(Integer)rader[i].get("sluttyear"));
            }

        }
        return ministerregjering;
    }






 /*statssekretær regjeringer*/

    private Statsraadsarkivet[] getStatssekretardatasett(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] statssekretardatasett = null;

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
        statssekretardatasett = new Statsraadsarkivet[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statssekretardatasett[i] = new Statsraadsarkivet();

            statssekretardatasett[i].setRegjering_reg_kode((Integer)rader[i].get("kode"));
            if (this.engelsk) {
                statssekretardatasett[i].setRegjeringsnavn_NO((String) rader[i].get("ENGstatsraadsbetegnelse"));
            }else{
                statssekretardatasett[i].setRegjeringsnavn_NO((String) rader[i].get("NOstatsraadsbetegnelse"));
            }
            //statssekretardatasett[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
            //statssekretardatasett[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
            statssekretardatasett[i].setStartaar((Integer)rader[i].get("startaar"));
            statssekretardatasett[i].setSluttaar((Integer)rader[i].get("sluttaar"));
            statssekretardatasett[i].setBlokk((Integer)rader[i].get("blokk"));
            statssekretardatasett[i].setAntalder((BigDecimal)rader[i].get("tt"));
            statssekretardatasett[i].setMin_reg((Integer)rader[i].get("min_reg"));
            statssekretardatasett[i].setPartikode((String)rader[i].get("partierkode"));
            statssekretardatasett[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
            statssekretardatasett[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
            statssekretardatasett[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));


            BigDecimal x = (BigDecimal)rader[i].get("tt");
            BigDecimal mimic_1 = new BigDecimal(3);
            statssekretardatasett[i].setAntaldertotal(x.multiply(mimic_1));

        }
        return statssekretardatasett;
    }


    /**
     * statssekretær download datasett getStatssekretarRegjeringsvisBeskrivelsedatasett
     * */

    public MinisterRegjering[] getStatssekretarRegjeringsvisBeskrivelsedatasett(String[] regjeringsperiode) throws Exception {

        int depkode = 0;
        int regjeringkode =0;

        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT regjering.navn_nb as statsraadsbetegnelse, regjering.navn_en as engStatsraadsbetegnelse, regjering.id as regjeringkode, politikere.foedtaar as foedtaar, YEAR(politikere.doed) as doedsaar, regjering_departement.navn_nb as eintaltekst_no, regjering_departement.navn_en as eintaltekst_en, regjering_departement.kode as Kode, regjering_statssekretaerer.stilling_avvik_nb as stilling_avvik_no, regjering_statssekretaerer.stilling_avvik_en as stilling_avvik_en, \n" +
                "       DAY(regjering_statssekretaerer.start)AS startday,  MONTH(regjering_statssekretaerer.start)AS startmonth, YEAR(regjering_statssekretaerer.start)AS startyear, DAY(regjering_statssekretaerer.slutt) AS sluttday , MONTH(regjering_statssekretaerer.slutt) AS sluttmonth, YEAR(regjering_statssekretaerer.slutt) AS sluttyear, regjering_statssekretaerer.kommentar_ekstern_nb as Eksternkommentar, regjering_statssekretaerer.kommentar_ekstern_en as engEksternkommentar,  politikere.fornavn, \n" +
                "       politikere.etternavn as navn, politikere.kjoenn, regjering_statssekretaerer.parti_ved_start, t_felles_partinavn.eintaltekst_forkorting as Eintaltekst_forkorting, \n" +
                "       t_felles_partinavn.eintaltekst as partieintaltekst_no , t_felles_partinavn.ENGeintaltekst as partieintaltekst_en, regjering.antall_partier_i_reg, regjering.id AS regkode, regjering_statssekretaerer.person as person_id, \n" +
                "       politikere.initialer\n" +
                "       FROM    politikere \n" +
                "       RIGHT OUTER JOIN  regjering_statssekretaerer \n" +
                "       LEFT OUTER JOIN   t_felles_partinavn ON regjering_statssekretaerer.parti_ved_start = t_felles_partinavn.kode ON \n" +
                "       politikere.id = regjering_statssekretaerer.person\n" +
                "       LEFT OUTER JOIN\n" +
                "       regjering_departement ON regjering_statssekretaerer.kode_dep = regjering_departement.kode \n" +
                "       LEFT OUTER JOIN\n" +
                "       regjering ON regjering_statssekretaerer.min_reg_HV = regjering.min_reg  \n";
        sqlSelect+= " WHERE ";
        sqlSelect+= "  (regjering_statssekretaerer.start BETWEEN regjering.start AND regjering.slutt) AND " +
                "     (regjering_statssekretaerer.slutt BETWEEN regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt) \n ";

        if(regjeringsperiode !=null &&  !regjeringsperiode.equals("") && regjeringsperiode.length>0 ){
            sqlSelect += " AND  (    ";
            sqlSelect += "   CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?    ";
            for(int i=1; i<regjeringsperiode.length; i++){
                if(regjeringsperiode[i]!=""){
                    sqlSelect += " OR CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?  ";
                }
            }
            sqlSelect += "  )    ";
        }
        sqlSelect+=  " OR (regjering_statssekretaerer.slutt BETWEEN regjering.start AND regjering.slutt) ";

        if(regjeringsperiode !=null &&  !regjeringsperiode.equals("") && regjeringsperiode.length>0 ){
            sqlSelect += " AND  (    ";
            sqlSelect += "   CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?    ";
            for(int i=1; i<regjeringsperiode.length; i++){
                if(regjeringsperiode[i]!=""){
                    sqlSelect += " OR CONVERT(varchar(50), YEAR(regjering.start))  + '-' + CONVERT(varchar(50), YEAR(regjering.slutt))= ?  ";
                }
            }
            sqlSelect += "  )    ";
        }
            sqlSelect+=  " AND  (regjering_statssekretaerer.slutt BETWEEN  regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt) ";
            sqlSelect+=      " ORDER BY regjering.id, regjering_departement.sortering, regjering_departement.navn_nb, regjering_statssekretaerer.stilling, regjering_statssekretaerer.start ";
               // "               (regjering.start = CONVERT(DATETIME, ?, 102)) AND \n" +
               // "               (regjering.slutt = CONVERT(DATETIME, ?, 102)) OR\n" +
               // "               (regjering_statssekretaerer.slutt BETWEEN regjering.start AND regjering.slutt) AND \n" +
               // "               (regjering.start = CONVERT(DATETIME, ?, 102)) AND \n" +
               // "               (regjering.slutt = CONVERT(DATETIME, ?, 102)) AND (regjering_statssekretaerer.slutt BETWEEN \n" +
               // "               regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt)\n" +
               // "               ORDER BY regjering_departement.sortering, regjering_departement.navn_nb, regjering_statssekretaerer.stilling, regjering_statssekretaerer.start desc ";

            List values = new ArrayList();
            if(regjeringsperiode !=null && !regjeringsperiode.equals("") && regjeringsperiode.length>0){
                values.add(  regjeringsperiode[0]+" "  );
                for(int i=1; i<regjeringsperiode.length; i++){
                    if(regjeringsperiode[i]!=""){ values.add(  regjeringsperiode[i]+" "  );}
                }
            }
            if(regjeringsperiode !=null && !regjeringsperiode.equals("") && regjeringsperiode.length>0){
                values.add(  regjeringsperiode[0]+" "  );
                for(int i=1; i<regjeringsperiode.length; i++){
                    if(regjeringsperiode[i]!=""){ values.add(  regjeringsperiode[i]+" "  );}
                }
            }
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];
        //int kode = 0;
        for (int i = 0; i < rader.length; i++) {
            ministerregjering[i] = new MinisterRegjering();

            if(regjeringkode != (Integer)rader[i].get("regjeringkode")){

                if (this.engelsk) {
                    ministerregjering[i].setMinisterium((String) rader[i].get("engStatsraadsbetegnelse"));
                }else{
                    ministerregjering[i].setMinisterium((String) rader[i].get("statsraadsbetegnelse"));
                }
            }
            regjeringkode = (Integer) rader[i].get("regjeringkode");

            if (this.engelsk) {
                ministerregjering[i].setStilling((String) rader[i].get("stilling_avvik_en"));
                ministerregjering[i].setEksternkommentar((String)rader[i].get("engEksternkommentar"));
                if(depkode !=(Integer)rader[i].get("kode")){
                    ministerregjering[i].setStatsraadnavn((String)rader[i].get("eintaltekst_en"));
                }
            }else{
                ministerregjering[i].setStilling((String) rader[i].get("stilling_avvik_no"));
                ministerregjering[i].setEksternkommentar((String)rader[i].get("eksternkommentar"));
                if(depkode !=(Integer)rader[i].get("kode")){
                    ministerregjering[i].setStatsraadnavn((String)rader[i].get("eintaltekst_no"));
                }
            }
            depkode =(Integer)rader[i].get("kode");

            ministerregjering[i].setFornavn((String)rader[i].get("fornavn"));
            ministerregjering[i].setEtternavn((String)rader[i].get("navn"));
            //ministerregjering[i].setPersonId((Integer)rader[i].get("person_id"));
            if((Integer) rader[i].get("kjoenn")==1){if (this.engelsk) {ministerregjering[i].setKjoenn("Man");}else{ministerregjering[i].setKjoenn("Mann");}}
            if((Integer) rader[i].get("kjoenn")==2){if (this.engelsk) {ministerregjering[i].setKjoenn("Woman");}else{ministerregjering[i].setKjoenn("Kvinne");}}






            if((Integer)rader[i].get("foedtaar")==null){}else{ ministerregjering[i].setFoedt((Integer)rader[i].get("foedtaar"));}
            if((Integer)rader[i].get("doedsaar")==null){}else{ministerregjering[i].setDoed((Integer)rader[i].get("doedsaar"));}
            if (this.engelsk) {ministerregjering[i].setPartinavn((String)rader[i].get("partieintaltekst_en"));}else{ministerregjering[i].setPartinavn((String)rader[i].get("partieintaltekst_no"));}











            ministerregjering[i].setAntall_parti_i_reg((Integer)rader[i].get("antall_partier_i_reg"));
            ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("regkode"));
            ministerregjering[i].setPartikortnavn((String)rader[i].get("eintaltekst_forkorting"));




            ministerregjering[i].setStartdato(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
            //ministerregjering[i].setSlutt(rader[i].get("sluttday")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttyear"));
            if(rader[i].get("sluttyear").equals(9999)){ministerregjering[i].setSluttdato("  ");}
            else {
                ministerregjering[i].setSluttdato(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
            }






                    /*
                   ministerregjering[i].setAar((Integer)rader[i].get("aar"));
                  ministerregjering[i].setBlokk((Integer)rader[i].get("blokk"));
                   ministerregjering[i].setMinisterium((String)rader[i].get("statsraadsbetegnelse"));
                   ministerregjering[i].setRegjeringparti((String)rader[i].get("partier"));
                   ministerregjering[i].setRegjeringtype((String)rader[i].get("flertall"));
                   ministerregjering[i].setAarsakavgang((String)rader[i].get("aarsak_avgang"));
                 ministerregjering[i].setStart(rader[i].get("startday")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startyear"));
                    ministerregjering[i].setSlutt(rader[i].get("sluttday")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttyear"));
                  ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("min_reg"));
                   */
        }
        return ministerregjering;
    }




}

