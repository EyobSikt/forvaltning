package no.nsd.polsys.logikk.storting;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.storting.PolitikereArkiv;

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
public class PolitikereArkivLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public PolitikereArkivLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // skrive metoder ------------------------------------------------------------------------------------

    public PolitikereArkiv[] getAlleSammensetning(int period_kode) throws Exception {
        String condition = "WHERE storting_sammensetning.periode = ?  ORDER BY t_felles_partinavn.eintaltekst ";
        List values = new ArrayList();
        values.add(period_kode);
        //PolitikereArkiv[] statusskjemaer = this.getSammensetning(condition, values);
        //if (statusskjemaer == null || statusskjemaer.length == 0) return null;
        //return statusskjemaer[0];
         return this.getSammensetning(condition, values);
    }

    public PolitikereArkiv[] getPartiSammensetning() throws Exception {
           String condition = " ORDER BY t_felles_partinavn.eintaltekst ";

            return this.getPartiSammensetning(condition, null);
       }

     public PolitikereArkiv[] getValgaar() throws Exception {
           String condition = " ORDER BY  kode  ";
            return this.getValgaar(condition, null);
       }

    public PolitikereArkiv[] getAarSammensetning() throws Exception {
        String condition = "ORDER BY storting_perioder.valgaar DESC";
        return this.getSammensetning_aar(condition, null);
    }


     public PolitikereArkiv[] getPartiTidsserier(int parti_kode) throws Exception {
        String condition = "WHERE storting_sammensetning.parti = ? ";
        List values = new ArrayList();
        values.add(parti_kode);

         return this.getSammensetning(condition, values);
    }


     public PolitikereArkiv[] getFylkenavn() throws Exception {
           //String condition = " ORDER BY t_storting_person_hist.valkrinsnavn  ";
         String condition = " ORDER BY storting_valgkretskoder.Eintaltekst  ";
            return this.getFylke(condition, null);
       }




// Private / lese metoder ------------------------------------------------------------------------------------

    private PolitikereArkiv[] getSammensetning_aar(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikereArkiv[] statusskjemaer = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT DISTINCT storting_sammensetning.periode as periodekode, storting_perioder.valgaar \n" +
                "                FROM storting_sammensetning INNER JOIN\n" +
                "                t_felles_partinavn ON storting_sammensetning.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "                storting_perioder ON storting_sammensetning.periode = storting_perioder.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new PolitikereArkiv[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new PolitikereArkiv();
            statusskjemaer[i].setValgAar((Integer) rader[i].get("valgaar"));
             statusskjemaer[i].setPeriodeKode((Integer) rader[i].get("periodekode"));
        }

        return statusskjemaer;
    }



    private PolitikereArkiv[] getSammensetning(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikereArkiv[] statusskjemaer = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT storting_sammensetning.periode as periodekode, storting_perioder.valgaar, storting_sammensetning.parti as partikode, t_felles_partinavn.eintaltekst, t_felles_partinavn.ENGparti_bruksnavn, t_felles_partinavn.eintaltekst_forkorting,\n" +
                "       storting_sammensetning.seter, storting_sammensetning.seter_totalt \n" +
                "       FROM storting_sammensetning INNER JOIN\n" +
                "       t_felles_partinavn ON storting_sammensetning.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "       storting_perioder ON storting_sammensetning.periode = storting_perioder.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new PolitikereArkiv[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new PolitikereArkiv();
            statusskjemaer[i].setValgAar((Integer) rader[i].get("valgaar"));
            //statusskjemaer[i].setPartiNavn((String) rader[i].get("eintaltekst"));
            if (this.engelsk && (String) rader[i].get("ENGparti_bruksnavn")!=null){statusskjemaer[i].setPartiNavn((String) rader[i].get("ENGparti_bruksnavn"));}else{statusskjemaer[i].setPartiNavn((String) rader[i].get("eintaltekst"));}
            statusskjemaer[i].setAntall((Integer) rader[i].get("seter"));
            statusskjemaer[i].setAntallTotalt((Integer) rader[i].get("seter_totalt"));
            statusskjemaer[i].setPeriodeKode((Integer) rader[i].get("periodekode"));
            statusskjemaer[i].setPartiKode((Integer) rader[i].get("partikode"));
             statusskjemaer[i].setPartiKortnavn((String) rader[i].get("eintaltekst_forkorting"));
        }
        return statusskjemaer;
    }

  private PolitikereArkiv[] getPartiSammensetning(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikereArkiv[] statusskjemaer = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT DISTINCT storting_sammensetning.parti as partikode, t_felles_partinavn.eintaltekst, t_felles_partinavn.eintaltekst_forkorting\n" +
                "   FROM storting_sammensetning INNER JOIN\n" +
                "   t_felles_partinavn ON storting_sammensetning.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "   storting_perioder ON storting_sammensetning.periode = storting_perioder.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new PolitikereArkiv[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new PolitikereArkiv();
             statusskjemaer[i].setPartiNavn((String) rader[i].get("eintaltekst"));
            statusskjemaer[i].setPartiKode((Integer) rader[i].get("partikode"));
             statusskjemaer[i].setPartiKortnavn((String) rader[i].get("eintaltekst_forkorting"));
        }
        return statusskjemaer;
    }

    //fylke list
    private PolitikereArkiv[] getFylke(String condition, List values) throws Exception {
        PolitikereArkiv[] statusskjemaer = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        /*
        String sqlSelect = "SELECT DISTINCT t_storting_person_hist.Amt,  t_storting_person_hist.Internmerknad, t_storting_valgkretskoder.Sortering, t_storting_valgkretskoder.land_eller_by, \n" +
                "     t_storting_person_hist.storting_valkrinskode, t_storting_person_hist.valkrinsnavn, t_storting_person_hist.storting_fylke_id         \n" +
                "     FROM t_storting_person_hist \n" +
                "     INNER JOIN t_storting_valgkretskoder ON t_storting_valgkretskoder.kode = t_storting_person_hist.storting_valkrinskode \n" +
                "     WHERE t_storting_valgkretskoder.land_eller_by=1  AND t_storting_person_hist.stortingperiode_kode = 118"
                + (condition != null ? " " + condition : "");
        */
        //changed Norske_stortingspolitikere to t_storting_person_hist
        String sqlSelect = " SELECT DISTINCT  storting_valgkretskoder.Sortering, storting_valgkretskoder.land_eller_by, \n" +
                "      storting_representanter.valgkrets as storting_valkrinskode, storting_valgkretskoder.Eintaltekst as valkrinsnavn   \n" +
                "      FROM storting_representanter \n" +
                "      INNER JOIN storting_valgkretskoder ON storting_valgkretskoder.kode = storting_representanter.valgkrets \n" +
                "      WHERE storting_valgkretskoder.land_eller_by=1  AND storting_representanter.periode = 118 "

                + (condition != null ? " " + condition : "");
       sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new PolitikereArkiv[rader.length];
        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new PolitikereArkiv();
            statusskjemaer[i].setFylkenavn((String) rader[i].get("valkrinsnavn"));
            statusskjemaer[i].setFylkeid((Integer) rader[i].get("storting_valkrinskode"));
            // statusskjemaer[i].setFylkenavn((String) rader[i].get("valkrinsnavn"));
            //statusskjemaer[i].setFylkeid((Integer) rader[i].get("storting_valkrinskode"));
            
        }
        return statusskjemaer;
  }

  private PolitikereArkiv[] getValgaar(String condition, List values) throws Exception {
        PolitikereArkiv[] statusskjemaer = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "select id as kode, valgAar, fleirtaltekst from storting_perioder where valgAar is not null "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new PolitikereArkiv[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new PolitikereArkiv();
            statusskjemaer[i].setValgAar((Integer) rader[i].get("ValgAar"));
            statusskjemaer[i].setPeriodeKode((Integer) rader[i].get("kode"));
            statusskjemaer[i].setFleirtaltekst((String) rader[i].get("fleirtaltekst"));         
        }
        return statusskjemaer;
    }



}

