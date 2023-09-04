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
public class StatsraaderDepartmentvisOversiktLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatsraaderDepartmentvisOversiktLogikk() {
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
        /*
        String condition = " WHERE     (t_felles_statsraader.slutt BETWEEN DOK_unike_dep.fra_tidspunkt AND DOK_unike_dep.til_tidspunkt) AND \n" +
                   "                      (DOK_unike_dep.Kode = ?)\n" +
                   "ORDER BY t_felles_statsraader.start DESC ";
          */
        String condition = " WHERE  ( regjering_medlemmer.slutt BETWEEN regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt) AND " +
                           " (regjering_departement.kode = ?) " +
                           "  ORDER BY regjering_medlemmer.start DESC ";
        List<Serializable> values = new ArrayList<Serializable>();
             values.add(d_kode);
           return this.getDepartmentOversikt(condition, values);
       }
    
      public RegjeringsDepartment[] getNedlagteDepartment(int d_kode) throws Exception {
           String condition = " WHERE   regjering_departement.kode = ?  " +
                   "ORDER BY navn_nb ";
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
 /*
        String sqlSelect = "SELECT     t_felles_statsraader.person_id,  \n" +
                "                      t_felles_statsraader.foedt, t_felles_statsraader.doedsaar, t_felles_statsraader.parti_ved_start, \n" +
                "                      t_felles_statsraader.navn_avvik, t_felles_statsraader.kode_dep, t_felles_statsraader.departement_HV, \n" +
                "                      t_felles_statsraader.hv, t_felles_statsraader.tittel_HV, t_felles_statsraader.stilling_avvik as NOstilling_avvik,t_felles_statsraader.ENGstilling_avvik as ENGstilling_avvik, \n" +
                "                      t_felles_statsraader.stilling_sortert, t_felles_statsraader.led_statsrad, DAY(t_felles_statsraader.start)AS startday,  MONTH(t_felles_statsraader.start)AS startmonth, YEAR(t_felles_statsraader.start)AS startyear, DAY(t_felles_statsraader.slutt) AS sluttday , MONTH(t_felles_statsraader.slutt) AS sluttmonth, YEAR(t_felles_statsraader.slutt) AS sluttyear, \n" +
                "                      t_felles_statsraader.eksternkommentar as NOeksternkommentar, t_felles_statsraader.engeksternkommentar as ENGeksternkommentar, t_felles_statsraader.min_reg, \n" +
                "                      t_felles_statsraader.internkommentar, DOK_unike_dep.Kode, DOK_unike_dep.Eintaltekst as NOeintaltekst, DOK_unike_dep.engEintaltekst as ENGeintaltekst, t_felles_person.fornavn, \n" +
                "                      t_felles_person.navn, t_felles_partinavn.eintaltekst_forkorting as NOeintaltekst_forkorting, t_felles_partinavn.ENGeintaltekst_forkorting as ENGeintaltekst_forkorting, t_felles_person.initialer\n" +
                "FROM         t_felles_person  LEFT OUTER JOIN\n" +
                "                      DOK_unike_dep INNER JOIN\n" +
                "                      t_felles_statsraader ON DOK_unike_dep.Kode = t_felles_statsraader.kode_dep LEFT OUTER JOIN\n" +
                "                      t_felles_partinavn ON t_felles_statsraader.parti_ved_start = t_felles_partinavn.kode ON \n" +
                "                      t_felles_person.person_id = t_felles_statsraader.person_id "
                + (condition != null ? " " + condition : "");
*/
        String sqlSelect = "SELECT     regjering_medlemmer.person as person_id, YEAR(politikere.fodt) AS foedt, YEAR(politikere.doed) AS doedsaar, regjering_medlemmer.parti_ved_start, \n" +
                "           regjering_medlemmer.departement,regjering_medlemmer.xxxkode_dep, \n" +
                "           regjering_medlemmer.stilling_avvik_nb as NOstilling_avvik, regjering_medlemmer.stilling_avvik_en as ENGstilling_avvik,\n" +
                "           DAY(regjering_medlemmer.start)AS startday,  MONTH(regjering_medlemmer.start)AS startmonth, YEAR(regjering_medlemmer.start)AS startyear, DAY(regjering_medlemmer.slutt) AS sluttday , MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, \n" +
                "           regjering_medlemmer.kommentar_ekstern_nb as NOeksternkommentar, regjering_medlemmer.kommentar_ekstern_en as ENGeksternkommentar, regjering_medlemmer.type, \n" +
                "           regjering_medlemmer.kommentar_intern, regjering_departement.navn_kort_nb, regjering_departement.kode as Kode, regjering_departement.navn_nb as NOeintaltekst, regjering_departement.navn_en as ENGeintaltekst, politikere.fornavn,\n" +
                "           politikere.etternavn as navn, t_felles_partinavn.eintaltekst_forkorting as NOeintaltekst_forkorting, t_felles_partinavn.ENGeintaltekst_forkorting as ENGeintaltekst_forkorting, politikere.initialer\n" +
                "           FROM  politikere  LEFT OUTER JOIN\n" +
                "           regjering_departement INNER JOIN\n" +
                "           regjering_medlemmer ON regjering_departement.id = regjering_medlemmer.departement LEFT OUTER JOIN\n" +
                "           t_felles_partinavn ON regjering_medlemmer.parti_ved_start = t_felles_partinavn.kode ON \n" +
                "           politikere.id = regjering_medlemmer.person  "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringsdepartment = new RegjeringsDepartment[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringsdepartment[i] = new RegjeringsDepartment();
                regjeringsdepartment[i].setEintaltekst_no((String)rader[i].get("NOeintaltekst"));
                 regjeringsdepartment[i].setEintaltekst_eng((String)rader[i].get("ENGeintaltekst"));
                regjeringsdepartment[i].setStilling_avvik_no((String)rader[i].get("NOstilling_avvik"));
                regjeringsdepartment[i].setStilling_avvik_eng((String)rader[i].get("ENGstilling_avvik"));
                regjeringsdepartment[i].setInitialer((String)rader[i].get("initialer"));
                regjeringsdepartment[i].setFornavn((String)rader[i].get("fornavn"));
                regjeringsdepartment[i].setNavn((String)rader[i].get("navn"));
                regjeringsdepartment[i].setEintaltekst_forkorting_no((String)rader[i].get("NOeintaltekst_forkorting"));
                regjeringsdepartment[i].setEintaltekst_forkorting_eng((String)rader[i].get("ENGeintaltekst_forkorting"));
                regjeringsdepartment[i].setFra_dato(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
               regjeringsdepartment[i].setDep_kode((Integer)rader[i].get("Kode"));
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
        //String sqlSelect = "SELECT     Kode, Eintaltekst, DAY(fra_tidspunkt) AS startday, MONTH(fra_tidspunkt) AS startmonth , YEAR(fra_tidspunkt) AS startyear , DAY(til_tidspunkt) AS sluttday, MONTH(til_tidspunkt) AS sluttmonth, YEAR(til_tidspunkt) AS sluttyear, Eksisterende_dep, Sortering_1\n" +
        //        "FROM         DOK_unike_dep "

     String sqlSelect = "SELECT  kode, navn_nb as NOeintaltekst, navn_en as ENGeintaltekst,  DAY(fra_tidspunkt) AS startday, MONTH(fra_tidspunkt) AS startmonth , YEAR(fra_tidspunkt) AS startyear , DAY(til_tidspunkt) AS sluttday, MONTH(til_tidspunkt) AS sluttmonth, YEAR(til_tidspunkt) AS sluttyear, eksisterende_dep, sortering\n" +
             "            FROM  regjering_departement "
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
               regjeringsdepartment[i].setEintaltekst_no((String)rader[i].get("ENGeintaltekst"));}
            else {
                regjeringsdepartment[i].setEintaltekst_no((String)rader[i].get("NOeintaltekst"));
            }
                regjeringsdepartment[i].setFra_dato(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                regjeringsdepartment[i].setTil_dato(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
               regjeringsdepartment[i].setDep_kode((Integer)rader[i].get("kode"));

            }
        return regjeringsdepartment;
    }

}

