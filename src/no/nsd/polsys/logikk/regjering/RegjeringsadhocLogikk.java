package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.Regjeringsadhoc;

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
public class RegjeringsadhocLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public RegjeringsadhocLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Regjeringsadhoc[] getRegjeringadhoc() throws Exception {
           String condition = " ORDER BY _regjering_adhoc.id asc ";
           return this.getRegjeringadhoc(condition, null);
       }

     public Regjeringsadhoc[] getRegjeringadhocBeskrivelse(int reg_kode) throws Exception {
           String condition = " WHERE  _regjering_adhoc.id=? ORDER BY _regjering_adhoc.id asc ";
          List values = new ArrayList();
          values.add(reg_kode);
          return this.getRegjeringadhocBeskrivelse(condition, values);
     }

     public Regjeringsadhoc[] getStatsraadadhocBeskrivelse(int reg_kode) throws Exception {
         /*
           String condition = " WHERE \t(((t_regjering_statsraader_adhoc.slutt) Between [DOK_unike_dep].[fra_tidspunkt] And [DOK_unike_dep].[til_tidspunkt]) AND ((t_regjering_statsraader_adhoc.kode)=?)) OR (((t_regjering_statsraader_adhoc.kode_dep) Is Null) AND ((t_regjering_statsraader_adhoc.kode)=?))\n" +
                   "ORDER BY DOK_unike_dep.Sortering_1, t_regjering_statsraader_adhoc.stilling_sortert ";
         */
         String condition = " WHERE \t(((_regjering_medlemmer_adhoc.slutt) Between regjering_departement.[fra_tidspunkt] And regjering_departement.[til_tidspunkt]) AND ((_regjering_medlemmer_adhoc.kode)=?)) OR (((_regjering_medlemmer_adhoc.kode_dep) Is Null) AND ((_regjering_medlemmer_adhoc.kode)=?))\n" +
                 "        ORDER BY regjering_departement.sortering, _regjering_medlemmer_adhoc.stilling_sortert ";

         List values = new ArrayList();
          values.add(reg_kode);
          values.add(reg_kode);
          return this.getStatsraadadhocBeskrivelse(condition, values);
     }

    
/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private Regjeringsadhoc[] getRegjeringadhoc(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Regjeringsadhoc[] regjeringadhoc = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT _regjering_adhoc.id, regjeringtype.tekst AS type_regjering, navn_nb as NOstatsraadsbetegnelse, navn_en as ENGstatsraadsbetegnelse, kommentar_intern AS internkommentar, ledende_nordmann_i_statsraad,\n" +
                "        kommentar_ekstern_nb as NOeksternkommentar, kommentar_ekstern_en as ENGeksternkommentar, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear \n" +
                "        FROM   _regjering_adhoc left outer join _regjering_adhoc_type regjeringtype on  type =  regjeringtype.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Regjeringsadhoc[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringadhoc[i] = new Regjeringsadhoc();

                regjeringadhoc[i].setRegjering_reg_kode((Integer)rader[i].get("id"));
                regjeringadhoc[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                regjeringadhoc[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));

                regjeringadhoc[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
           
            }
        return regjeringadhoc;
    }


     private Regjeringsadhoc[] getRegjeringadhocBeskrivelse(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Regjeringsadhoc[] regjeringadhoc = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT _regjering_adhoc.id, navn_nb as NOstatsraadsbetegnelse, navn_en as ENGstatsraadsbetegnelse, \n" +
                "        kommentar_ekstern_nb as eksternkommentar, kommentar_ekstern_en as ENGeksternkommentar,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear\n" +
                "        FROM        _regjering_adhoc "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Regjeringsadhoc[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringadhoc[i] = new Regjeringsadhoc();

                regjeringadhoc[i].setRegjering_reg_kode((Integer)rader[i].get("id"));
        if (this.engelsk) {
                regjeringadhoc[i].setRegjeringsnavn_NO((String)rader[i].get("ENGstatsraadsbetegnelse"));
        }else {
            regjeringadhoc[i].setRegjeringsnavn_NO((String) rader[i].get("NOstatsraadsbetegnelse"));
        }
                regjeringadhoc[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));

            }
        return regjeringadhoc;
    }

    private Regjeringsadhoc[] getStatsraadadhocBeskrivelse(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Regjeringsadhoc[] regjeringadhoc = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT \t_regjering_medlemmer_adhoc.person, _regjering_medlemmer_adhoc.foedt, _regjering_medlemmer_adhoc.doedsaar,\n" +
                "        _regjering_medlemmer_adhoc.parti_ved_start, _regjering_medlemmer_adhoc.stilling_avvik as NOstilling_avvik, _regjering_medlemmer_adhoc.ENGstilling_avvik as ENGstilling_avvik,  \n" +
                "        DAY(_regjering_medlemmer_adhoc.start) AS startday, MONTH(_regjering_medlemmer_adhoc.start) AS startmonth , YEAR(_regjering_medlemmer_adhoc.start) AS startyear , DAY(_regjering_medlemmer_adhoc.slutt) AS sluttday, MONTH(_regjering_medlemmer_adhoc.slutt) AS sluttmonth, \n" +
                "       YEAR(_regjering_medlemmer_adhoc.slutt) AS sluttyear ,_regjering_medlemmer_adhoc.eksternkommentar, _regjering_medlemmer_adhoc.ENGeksternkommentar, regjering_departement.navn_nb AS eintaltekst,  regjering_departement.navn_en AS engEintaltekst, \n" +
                "        _regjering_medlemmer_adhoc.kode_dep, _regjering_medlemmer_adhoc.person, \n" +
                "        _regjering_medlemmer_adhoc.kode, _regjering_medlemmer_adhoc.stilling_sortert, _regjering_medlemmer_adhoc.uttakskode, \n" +
                "        _regjering_adhoc.id , _regjering_adhoc.kommentar_ekstern_nb AS Expr2, \n" +
                "        _regjering_adhoc.navn_nb AS Statsraadsbetegnelse, _regjering_adhoc.start AS Expr3, _regjering_adhoc.slutt AS Expr4, \n" +
                "        politikere.fornavn, politikere.etternavn\n" +
                "        FROM    regjering_departement RIGHT OUTER JOIN\n" +
                "        _regjering_medlemmer_adhoc LEFT OUTER JOIN\n" +
                "        politikere ON _regjering_medlemmer_adhoc.person = politikere.id ON \n" +
                "        regjering_departement.id = _regjering_medlemmer_adhoc.departement LEFT OUTER JOIN\n" +
                "        _regjering_adhoc ON _regjering_medlemmer_adhoc.kode = _regjering_adhoc.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Regjeringsadhoc[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringadhoc[i] = new Regjeringsadhoc();

                 regjeringadhoc[i].setStilling_avvik_NO((String)rader[i].get("NOstilling_avvik"));
                regjeringadhoc[i].setStilling_avvik_ENG((String)rader[i].get("ENGstilling_avvik"));
                 regjeringadhoc[i].setEintaltekst_NO((String)rader[i].get("eintaltekst"));
                regjeringadhoc[i].setEintaltekst_ENG((String)rader[i].get("engEintaltekst"));
                regjeringadhoc[i].setEtternavn((String)rader[i].get("etternavn"));
                regjeringadhoc[i].setFornavn((String)rader[i].get("fornavn"));
                regjeringadhoc[i].setEksternkommentar_NO((String)rader[i].get("eksternkommentar"));
                regjeringadhoc[i].setEksternkommentar_ENG((String)rader[i].get("ENGeksternkommentar"));
                regjeringadhoc[i].setDoedsaar((Integer)rader[i].get("doedsaar"));
                 regjeringadhoc[i].setFoedt((Integer)rader[i].get("foedt"));
                regjeringadhoc[i].setUttakskode((Integer)rader[i].get("uttakskode"));
                 regjeringadhoc[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));


            }
        return regjeringadhoc;
    }

}

