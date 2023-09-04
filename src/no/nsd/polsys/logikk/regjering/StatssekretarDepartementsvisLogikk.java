package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.RegjeringsDepartment;

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
public class StatssekretarDepartementsvisLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatssekretarDepartementsvisLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public RegjeringsDepartment[] getDagensDepartment() throws Exception {
           String condition = " WHERE     (til_tidspunkt > CONVERT(DATETIME, '1945-06-24 00:00:00', 102)) AND (eksisterende_dep = 1)\n" +
                   "ORDER BY Eintaltekst_no ";
           return this.getDagensDepartment(condition, null);
       }
    
      public RegjeringsDepartment[] getNedlagteDepartment() throws Exception {
           String condition = " WHERE     (til_tidspunkt > CONVERT(DATETIME, '1945-06-24 00:00:00', 102)) AND (eksisterende_dep = 2)\n" +
                   "ORDER BY Eintaltekst_no ";
           return this.getNedlagteDepartment(condition, null);
       }


/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private RegjeringsDepartment[] getDagensDepartment(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        RegjeringsDepartment[] regjeringsdepartment = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  Kode, navn_nb as Eintaltekst_no, navn_en as Eintaltekst_en, DAY(fra_tidspunkt) AS startday, MONTH(fra_tidspunkt) AS startmonth , YEAR(fra_tidspunkt) AS startyear , DAY(til_tidspunkt) AS sluttday, MONTH(til_tidspunkt) AS sluttmonth, YEAR(til_tidspunkt) AS sluttyear, eksisterende_dep, sortering\n" +
                "        FROM regjering_departement "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringsdepartment = new RegjeringsDepartment[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringsdepartment[i] = new RegjeringsDepartment();
        if (this.engelsk) {
            regjeringsdepartment[i].setEintaltekst_no((String) rader[i].get("Eintaltekst_en"));
        }else{
            regjeringsdepartment[i].setEintaltekst_no((String) rader[i].get("Eintaltekst_no"));
        }

                regjeringsdepartment[i].setFra_dato(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                //regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
                 if(rader[i].get("sluttyear").equals(9999)){regjeringsdepartment[i].setTil_dato("  ");}
                else {
                regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
                 }
               regjeringsdepartment[i].setDep_kode((Integer)rader[i].get("Kode"));
            }
        return regjeringsdepartment;
    }

 private RegjeringsDepartment[] getNedlagteDepartment(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        RegjeringsDepartment[] regjeringsdepartment = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  Kode, navn_nb as Eintaltekst_no, navn_en as Eintaltekst_en, DAY(fra_tidspunkt) AS startday, MONTH(fra_tidspunkt) AS startmonth , YEAR(fra_tidspunkt) AS startyear , DAY(til_tidspunkt) AS sluttday, MONTH(til_tidspunkt) AS sluttmonth, YEAR(til_tidspunkt) AS sluttyear, eksisterende_dep, Sortering\n" +
                "        FROM regjering_departement "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringsdepartment = new RegjeringsDepartment[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringsdepartment[i] = new RegjeringsDepartment();
        if (this.engelsk) {
            regjeringsdepartment[i].setEintaltekst_no((String) rader[i].get("Eintaltekst_en"));
        }else{
            regjeringsdepartment[i].setEintaltekst_no((String) rader[i].get("Eintaltekst_no"));
        }

                regjeringsdepartment[i].setFra_dato(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
               regjeringsdepartment[i].setDep_kode((Integer)rader[i].get("Kode"));

            }
        return regjeringsdepartment;
    }

}

