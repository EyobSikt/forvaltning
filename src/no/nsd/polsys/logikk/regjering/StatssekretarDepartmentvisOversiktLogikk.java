package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.RegjeringsDepartment;

import javax.servlet.jsp.jstl.sql.Result;
import java.io.Serializable;
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
public class StatssekretarDepartmentvisOversiktLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatssekretarDepartmentvisOversiktLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public RegjeringsDepartment[] getDepartmentOversikt(int d_kode) throws Exception {
           String condition = " WHERE     (regjering_statssekretaerer.slutt BETWEEN regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt) AND \n" +
                   "                      (regjering_departement.Kode = ?)\n" +
                   "ORDER BY regjering_statssekretaerer.start DESC ";
        List<Serializable> values = new ArrayList<Serializable>();
             values.add(d_kode);
           return this.getDepartmentOversikt(condition, values);
       }
    
      public RegjeringsDepartment[] getNedlagteDepartment(int d_kode) throws Exception {
           String condition = " where regjering_departement.Kode = ? " +
                   "ORDER BY Eintaltekst ";
          List<Serializable> values = new ArrayList<Serializable>();
             values.add(d_kode);
           return this.getNedlagteDepartment(condition, values);
       }


/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private RegjeringsDepartment[] getDepartmentOversikt(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        RegjeringsDepartment[] regjeringsdepartment = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT regjering_statssekretaerer.person as person_id,  \n" +
                "       politikere.foedtaar, regjering_statssekretaerer.parti_ved_start,\n" +
                "       regjering_statssekretaerer.kode_dep, regjering_statssekretaerer.Departement, \n" +
                "       regjering_statssekretaerer.stilling_avvik_nb as Stilling_avvik, \n" +
                "       regjering_statssekretaerer.stilling, DAY(regjering_statssekretaerer.start)AS startday,  MONTH(regjering_statssekretaerer.start)AS startmonth, YEAR(regjering_statssekretaerer.start)AS startyear, DAY(regjering_statssekretaerer.slutt) AS sluttday , MONTH(regjering_statssekretaerer.slutt) AS sluttmonth, YEAR(regjering_statssekretaerer.slutt) AS sluttyear, \n" +
                "       regjering_statssekretaerer.kommentar_ekstern_nb as Eksternkommentar,regjering_statssekretaerer.kommentar_ekstern_en as engEksternkommentar,  regjering_statssekretaerer.min_reg_HV, \n" +
                "       regjering_statssekretaerer.kommentar_intern, regjering_departement.Kode, regjering_departement.navn_nb as Eintaltekst, regjering_departement.navn_en as ENGeintaltekst, politikere.fornavn,\n" +
                "       politikere.etternavn as navn, t_felles_partinavn.eintaltekst_forkorting as Eintaltekst_forkorting,t_felles_partinavn.ENGeintaltekst_forkorting as ENGeintaltekst_forkorting,  politikere.initialer\n" +
                "       FROM  politikere LEFT OUTER JOIN regjering_departement \n" +
                "       INNER JOIN regjering_statssekretaerer ON regjering_departement.Kode = regjering_statssekretaerer.kode_dep \n" +
                "       LEFT OUTER JOIN t_felles_partinavn ON regjering_statssekretaerer.parti_ved_start = t_felles_partinavn.kode ON \n" +
                "       politikere.id = regjering_statssekretaerer.person "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringsdepartment = new RegjeringsDepartment[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringsdepartment[i] = new RegjeringsDepartment();
                regjeringsdepartment[i].setEintaltekst_no((String)rader[i].get("Eintaltekst"));
                 regjeringsdepartment[i].setEintaltekst_eng((String)rader[i].get("ENGeintaltekst"));
                regjeringsdepartment[i].setStilling_avvik_no((String)rader[i].get("Stilling_avvik"));
                regjeringsdepartment[i].setStilling_avvik_eng((String)rader[i].get("ENGstilling_avvik"));
                regjeringsdepartment[i].setInitialer((String)rader[i].get("initialer"));
                regjeringsdepartment[i].setFornavn((String)rader[i].get("fornavn"));
                regjeringsdepartment[i].setNavn((String)rader[i].get("navn"));
                regjeringsdepartment[i].setEintaltekst_forkorting_no((String)rader[i].get("Eintaltekst_forkorting"));
                regjeringsdepartment[i].setEintaltekst_forkorting_eng((String)rader[i].get("ENGeintaltekst_forkorting"));
                regjeringsdepartment[i].setFra_dato(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
        if (this.engelsk) {
            regjeringsdepartment[i].setEksternkommentar((String) rader[i].get("engEksternkommentar"));
        }else{
            regjeringsdepartment[i].setEksternkommentar((String) rader[i].get("Eksternkommentar"));
        }
                //regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
                if(rader[i].get("sluttyear").equals(9999)){regjeringsdepartment[i].setTil_dato("  ");}
                else {
                    regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
                 }
               regjeringsdepartment[i].setDep_kode((Integer)rader[i].get("kode"));
               regjeringsdepartment[i].setPerson_id((Integer)rader[i].get("person_id"));

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
        String sqlSelect = "SELECT  Kode as kode_dep, navn_nb as Eintaltekst, navn_en as ENGeintaltekst , DAY(fra_tidspunkt) AS startday, MONTH(fra_tidspunkt) AS startmonth , YEAR(fra_tidspunkt) AS startyear , DAY(til_tidspunkt) AS sluttday, MONTH(til_tidspunkt) AS sluttmonth, YEAR(til_tidspunkt) AS sluttyear, Eksisterende_dep\n" +
                "        FROM regjering_departement  "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringsdepartment = new RegjeringsDepartment[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringsdepartment[i] = new RegjeringsDepartment();
                regjeringsdepartment[i].setEintaltekst_no((String)rader[i].get("Eintaltekst"));
                regjeringsdepartment[i].setEintaltekst_eng((String)rader[i].get("ENGeintaltekst"));
                regjeringsdepartment[i].setFra_dato(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                //regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
        if(rader[i].get("sluttyear").equals(9999)){regjeringsdepartment[i].setTil_dato("  ");}
        else {
            regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
        }
        regjeringsdepartment[i].setDep_kode((Integer)rader[i].get("kode_dep"));

            }
        return regjeringsdepartment;
    }

}

