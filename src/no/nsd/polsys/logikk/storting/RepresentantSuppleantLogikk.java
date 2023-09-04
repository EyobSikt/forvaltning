package no.nsd.polsys.logikk.storting;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.storting.NorskePolitikere;

import javax.servlet.jsp.jstl.sql.Result;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 25.nov.2010
 * Time: 10:08:04
 * To change this template use File | Settings | File Templates.
 */
public class RepresentantSuppleantLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public RepresentantSuppleantLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


// Skrive metoder ------------------------------------------------------------------------------------

    /**
     * Setter databaseforbindelsen for denne bean'en.
     *
     * @param conn databaseforbindelsen.
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
      /*get fylke navn 1945-d.d.*/
     public NorskePolitikere[] getRepersentanerSuppleanterFylke1945(Integer periode, String parti) throws Exception {
        /*
         String condition = " HAVING  t_storting_stortingsperioder.kode = ? AND  t_storting_person_hist.parti_kortnavn=? AND t_storting_fylke.sortering is not null ";
          condition +=" ORDER BY t_storting_fylke.navn";
         */
         String condition = " HAVING (storting_perioder.id = ?) AND  (t_felles_partinavn.kode = ? ) \n" +
                 "               ORDER BY storting_valgkretskoder.Eintaltekst ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(periode);
         values.add(parti);
         return this.getAlleRepersentanerSuppleanterFylke(condition, values);
     }
    
       /*get representat og suplemetat person navn 1945- d.d.*/
      public NorskePolitikere[] getRepersentanerSuppleanter1945(Integer periode, String parti, Integer fylke_id) throws Exception {
        /*
          String condition = "HAVING  t_storting_stortingsperioder.kode = ? AND t_storting_person_hist.parti_kortnavn=? AND t_storting_person_hist.storting_fylke_id=? ";
          condition +=" ORDER BY t_storting_fylke.navn,  t_storting_person_hist.ofylkesreprnr, t_felles_person.navn";
          */
          String condition = "  HAVING (storting_perioder.id = ?) AND (t_felles_partinavn.kode = ? ) AND ( storting_valgkretskoder.kode = ? ) \n" +
                  "               ORDER BY storting_valgkretskoder.sortering, storting_representanter.representant_nr, storting_representanter.parti " ;
          List<Serializable> values = new ArrayList<Serializable>();
        values.add(periode);
        values.add(parti);
        values.add(fylke_id);

       return this.getAlleRepersentanerSuppleanter(condition, values);
    }

     /*getRepersentant_fylke1905_1940*/
     public NorskePolitikere[] getRepersentantFylke1905_1940(Integer periode, Integer land_by, String parti) throws Exception {
         /*
           String condition = " WHERE t_storting_person_hist.stortingperiode_kode = ? AND t_storting_valgkretskoder.land_eller_by=? AND t_felles_partinavn.kode=? ";
             condition +=" ORDER BY t_storting_person_hist.valkrinsnavn";
        */
         String condition = "  WHERE (storting_representanter.periode = ?) AND (storting_valgkretskoder.land_eller_by = ?) AND (t_felles_partinavn.eintaltekst_forkorting = ?)\n" +
                 "               ORDER BY storting_valgkretskoder.Eintaltekst ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(periode);
           values.add(land_by);
           values.add(parti);
          return this.getAlleRepersentanerFylke1905_1940(condition, values);
       }
      /*getRepersentant1905_1940*/
     public NorskePolitikere[] getRepersentant1905_1940(Integer periode, Integer land_by, String parti, Integer fylke_id) throws Exception {
         /*
           String condition = " WHERE t_storting_person_hist.stortingperiode_kode = ? AND t_storting_valgkretskoder.land_eller_by=? AND t_felles_partinavn.kode=? AND t_storting_person_hist.storting_fylke_id=? ";
             condition +=" ORDER BY t_storting_valgkretskoder.Sortering,  t_storting_person_hist.sup_nr, t_storting_person_hist.rep_nr,  t_felles_person.navn";
           */
         String condition =  " WHERE (storting_representanter.periode = ?) AND (storting_valgkretskoder.land_eller_by = ?) AND (t_felles_partinavn.eintaltekst_forkorting = ?) AND ( storting_representanter.valgkrets = ? )  \n" +
                 "              ORDER BY storting_valgkretskoder.sortering, storting_representanter.suppleant_nr, storting_representanter.representant_nr ";
         List<Serializable> values = new ArrayList<Serializable>();
           values.add(periode);
           values.add(land_by);
           values.add(parti);
           values.add(fylke_id);
          return this.getAlleRepersentaner1905_1940(condition, values);
       }



       /*getRepersentantfylke1814 - 1904*/
       public NorskePolitikere[] getRepersentantFylke1814_1904(Integer periode, Integer land_by) throws Exception {
          /*
           String condition = " WHERE t_storting_person_hist.stortingperiode_kode = ? AND t_storting_valgkretskoder.land_eller_by=?  ";
             condition +=" ORDER BY t_storting_person_hist.valkrinsnavn";
           */
           String condition =  " WHERE  (storting_representanter.periode = ?) AND (storting_valgkretskoder.land_eller_by = ?) " +
                               " ORDER BY storting_valgkretskoder.sortering ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(periode);
           values.add(land_by);
          return this.getAlleRepersentanerFylke1814_1905(condition, values);
       }

       /*getRepersentant1814 - 1904*/
       public NorskePolitikere[] getRepersentant1814_1904(Integer periode, Integer land_by, Integer fylke_id) throws Exception {
           /*
           String condition = " WHERE t_storting_person_hist.stortingperiode_kode = ? AND t_storting_valgkretskoder.land_eller_by=? AND t_storting_valgkretskoder.Sortering=? ";
             condition +=" ORDER BY t_storting_valgkretskoder.Sortering, t_storting_person_hist.sup_nr, t_storting_person_hist.rep_nr,  t_felles_person.navn";
           */
           String condition = " WHERE  (storting_representanter.periode = ?) AND (storting_valgkretskoder.land_eller_by = ?) AND ( storting_valgkretskoder.sortering=?) \n" +
                              " ORDER BY storting_valgkretskoder.sortering, storting_representanter.suppleant_nr, storting_representanter.representant_nr ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(periode);
           values.add(land_by);
           values.add(fylke_id);
          return this.getAlleRepersentaner1814_1905(condition, values);
       }



        public NorskePolitikere[] getPartiKortnavn(String parti) throws Exception {
        String condition = " WHERE kode = ? ";
         List<Serializable> values = new ArrayList<Serializable>();
         values.add(parti);
        return this.getParti(condition, values);
    }

     public NorskePolitikere[] getPartinavn(String parti) throws Exception {
        String condition = " WHERE eintaltekst_forkorting = ? ";
         List<Serializable> values = new ArrayList<Serializable>();
        values.add(parti);
        return this.getPartinavn(condition, values);
    }

     public NorskePolitikere[] getPartiaar(Integer periode) throws Exception {
        String condition = " WHERE id = ? ";
         List<Serializable> values = new ArrayList<Serializable>();
        values.add(periode);
        return this.getPartiperiodeaar(condition, values);
    }

   //lese metoder

/* 1945 d.d. flyke navn */
    private NorskePolitikere[] getAlleRepersentanerSuppleanterFylke(String condition, List<Serializable> values) throws Exception {
           // tabell som returneres fra denne metoden.
           NorskePolitikere[] norskepolitikere = null;
           // resultat fra sql-sporring.
           Result result = null;
           // objekt som brukes til aa utfore sql-sporring.
           SqlCommandBean sqlCB = new SqlCommandBean();
           // inneholder data fra sql-sporring.
           SortedMap[] rader = null;
           // SQL-sporring.
        /*
           String sqlSelect = "SELECT DISTINCT  t_storting_stortingsperioder.periode,  t_storting_person_hist.stortingperiode_kode, t_storting_person_hist.storting_fylke_id, t_storting_person_hist.parti_kortnavn, \n" +
                   "                       t_storting_fylke.navn as fylkesnavn, t_storting_fylke.sortering \n" +
                   " FROM t_storting_person_hist INNER JOIN\n" +
                   "                      t_storting_fylke ON t_storting_person_hist.storting_fylke_id = t_storting_fylke.fylke_id INNER JOIN\n" +
                   "                      t_storting_stortingsperioder ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder.kode LEFT OUTER JOIN\n" +
                   "                      t_storting_personperiode ON t_storting_person_hist.person_id = t_storting_personperiode.person_id AND\n" +
                   "                      t_storting_stortingsperioder.periode =  t_storting_personperiode.valgperiode LEFT OUTER JOIN\n" +
                   "                       t_felles_person ON t_storting_person_hist.person_id = t_felles_person.person_id\n" +
                   "GROUP BY t_storting_stortingsperioder.periode, t_storting_person_hist.stortingperiode_kode, t_felles_person.initialer, t_felles_person.person_id, t_storting_person_hist.storting_fylke_id, t_storting_person_hist.parti_kortnavn,\n" +
                   "                      t_storting_person_hist.ofylkesreprnr, t_felles_person.fornavn, t_felles_person.navn, t_storting_person_hist.ovreprnr, t_storting_fylke.navn,\n" +
                   "                      t_storting_fylke.sortering, t_storting_personperiode.stilling1, t_storting_stortingsperioder.kode"
                   + (condition != null ? " " + condition : "");

            */
/* this below is latest
        String sqlSelect = " SELECT  DISTINCT   Ep_person_hist.ValgPeriode, REGEp_fylke.id, Ep_person_hist.fylke, Ep_person_hist.parti, \n" +
                "                      REGEp_fylke.navn as fylkesnavn,  Stortingsperioder.Kode\n" +

                "FROM         Ep_person_hist INNER JOIN\n" +
                "                      Ep_person ON Ep_person_hist.initialer COLLATE SQL_Danish_Pref_CP1_CI_AS= Ep_person.initialer INNER JOIN\n" +
                "                      REGEp_fylke ON Ep_person_hist.fylke COLLATE SQL_Danish_Pref_CP1_CI_AS= REGEp_fylke.fylke INNER JOIN\n" +
                "                      Stortingsperioder ON Ep_person_hist.ValgPeriode COLLATE SQL_Danish_Pref_CP1_CI_AS= Stortingsperioder.Periode INNER JOIN\n" +
                "                      Ep_personperiode ON Ep_person_hist.initialer COLLATE SQL_Danish_Pref_CP1_CI_AS= Ep_personperiode.initialer AND\n" +
                "                      Stortingsperioder.Periode COLLATE SQL_Danish_Pref_CP1_CI_AS= Ep_personperiode.valgperiode LEFT OUTER JOIN\n" +
                "                      Norske_politikere ON Ep_person.initialer COLLATE SQL_Danish_Pref_CP1_CI_AS= Norske_politikere.initialer\n" +
                "GROUP BY Ep_person_hist.ValgPeriode, Norske_politikere.initialer, Norske_politikere.Person, Ep_person_hist.fylke, Ep_person_hist.parti,\n" +
                "                      Ep_person_hist.ofylkesreprnr, Norske_politikere.Fornavn, Norske_politikere.Navn, Ep_person_hist.ovreprnr, REGEp_fylke.navn,\n" +
                "                      REGEp_fylke.sortering, REGEp_fylke.id, Ep_personperiode.stilling1, Stortingsperioder.Kode  "
                + (condition != null ? " " + condition : "");
                */
        String sqlSelect = " SELECT  DISTINCT storting_perioder.fleirtaltekst as ValgPeriode, storting_valgkretskoder.kode as id,  storting_representanter.parti, \n" +
                "        storting_valgkretskoder.Eintaltekst as fylkesnavn,  storting_perioder.id as Kode\n" +
                "        FROM storting_representanter --INNER JOINtialer COLLATE SQL_Danish_Pref_CP1_CI_AS= Ep_person.initialer \n" +
                "\t\tINNER JOIN\n" +
                "       storting_valgkretskoder ON storting_valgkretskoder.kode = storting_representanter.valgkrets  INNER JOIN\n" +
                "        storting_perioder ON storting_representanter.periode = storting_perioder.id INNER JOIN " +
                "       t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.Kode \n" +
                "\t\tLEFT OUTER JOIN\n" +
                "        politikere ON storting_representanter.person = politikere.id\n" +
                "\t\twhere storting_perioder.id >89\n" +
                "        GROUP BY storting_perioder.fleirtaltekst, politikere.initialer, politikere.id, storting_representanter.valgkrets, storting_representanter.parti,\n" +
                "        storting_representanter.representant_nr, politikere.fornavn, politikere.etternavn, storting_representanter.suppleant_nr, storting_valgkretskoder.Eintaltekst,\n" +
                "        storting_valgkretskoder.sortering, storting_valgkretskoder.kode, storting_perioder.id, t_felles_partinavn.kode  "
                + (condition != null ? " " + condition : "");
           sqlCB.setConnection(this.conn);
           sqlCB.setSqlValue(sqlSelect); //sporring
           sqlCB.setValues(values); //parameter
           result = sqlCB.executeQuery();
           rader = result.getRows();
           norskepolitikere = new NorskePolitikere[rader.length];

           for (int i = 0; i < rader.length; i++) {
               norskepolitikere[i] = new NorskePolitikere();
               String flyke = (String) rader[i].get("fylkesnavn");

               norskepolitikere[i].setFylkeNavn(flyke.replace("fylke", ""));
                norskepolitikere[i].setFylkeId((Integer) rader[i].get("id"));
                 norskepolitikere[i].setPeriodekode((Integer) rader[i].get("Kode"));
             //norskepolitikere[i].setPartiKortnavn((String) rader[i].get("parti"));
               norskepolitikere[i].setPartikode((Integer) rader[i].get("parti"));
          }
           return norskepolitikere;
       }

    /*1945- d.d. navn list*/
     private NorskePolitikere[] getAlleRepersentanerSuppleanter(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
         String sqlSelect = "SELECT  DISTINCT t_storting_stortingsperioder.periode,  t_storting_person_hist.stortingperiode_kode, t_storting_person_hist.storting_fylke_id, t_storting_person_hist.parti_kortnavn, t_storting_person_hist.ofylkesreprnr,\n" +
                "                      t_storting_person_hist.ovreprnr, t_storting_fylke.navn as fylkesnavn, t_storting_fylke.sortering, t_storting_personperiode.stilling1, t_storting_stortingsperioder.kode,\n" +
                "                      t_felles_person.person_id, t_felles_person.initialer, t_felles_person.fornavn, t_felles_person.navn\n" +
                " FROM t_storting_person_hist INNER JOIN\n" +
                "                      t_storting_fylke ON t_storting_person_hist.storting_fylke_id = t_storting_fylke.fylke_id INNER JOIN\n" +
                "                      t_storting_stortingsperioder ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder.kode LEFT OUTER JOIN\n" +
                "                      t_storting_personperiode ON t_storting_person_hist.person_id = t_storting_personperiode.person_id AND\n" +
                "                      t_storting_stortingsperioder.periode =  t_storting_personperiode.valgperiode LEFT OUTER JOIN\n" +
                "                       t_felles_person ON t_storting_person_hist.person_id = t_felles_person.person_id\n" +
                "GROUP BY t_storting_stortingsperioder.periode, t_storting_person_hist.stortingperiode_kode, t_felles_person.initialer, t_felles_person.person_id, t_storting_person_hist.storting_fylke_id, t_storting_person_hist.parti_kortnavn,\n" +
                "                      t_storting_person_hist.ofylkesreprnr, t_felles_person.fornavn, t_felles_person.navn, t_storting_person_hist.ovreprnr, t_storting_fylke.navn,\n" +
                "                      t_storting_fylke.sortering, t_storting_personperiode.stilling1, t_storting_stortingsperioder.kode"
                + (condition != null ? " " + condition : "");
            */
          /* this below is latest
         String sqlSelect = " SELECT     Ep_person_hist.ValgPeriode, REGEp_fylke.id, Ep_person_hist.fylke, Ep_person_hist.parti, Ep_person_hist.ofylkesreprnr,\n" +
                 "                      Ep_person_hist.ovreprnr, REGEp_fylke.navn as fylkesnavn, REGEp_fylke.sortering, Ep_personperiode.stilling1, Stortingsperioder.Kode,\n" +
                 "                      Norske_politikere.Person, Norske_politikere.initialer, Norske_politikere.Fornavn, Norske_politikere.Navn\n" +
                 "FROM         Ep_person_hist INNER JOIN\n" +
                 "                      Ep_person ON Ep_person_hist.initialer COLLATE SQL_Danish_Pref_CP1_CI_AS= Ep_person.initialer INNER JOIN\n" +
                 "                      REGEp_fylke ON Ep_person_hist.fylke COLLATE SQL_Danish_Pref_CP1_CI_AS= REGEp_fylke.fylke INNER JOIN\n" +
                 "                      Stortingsperioder ON Ep_person_hist.ValgPeriode COLLATE SQL_Danish_Pref_CP1_CI_AS= Stortingsperioder.Periode INNER JOIN\n" +
                 "                      Ep_personperiode ON Ep_person_hist.initialer COLLATE SQL_Danish_Pref_CP1_CI_AS= Ep_personperiode.initialer AND\n" +
                 "                      Stortingsperioder.Periode COLLATE SQL_Danish_Pref_CP1_CI_AS= Ep_personperiode.valgperiode LEFT OUTER JOIN\n" +
                 "                      Norske_politikere ON Ep_person.initialer COLLATE SQL_Danish_Pref_CP1_CI_AS= Norske_politikere.initialer\n" +
                 "GROUP BY Ep_person_hist.ValgPeriode, Norske_politikere.initialer, Norske_politikere.Person, Ep_person_hist.fylke, Ep_person_hist.parti,\n" +
                 "                      Ep_person_hist.ofylkesreprnr, Norske_politikere.Navn, Norske_politikere.Fornavn, Ep_person_hist.ovreprnr, REGEp_fylke.navn,\n" +
                 "                      REGEp_fylke.sortering, REGEp_fylke.id, Ep_personperiode.stilling1, Stortingsperioder.Kode,REGEp_fylke.fylke "
                 + (condition != null ? " " + condition : "");
                 */
         String sqlSelect = " SELECT  storting_perioder.fleirtaltekst as ValgPeriode, storting_valgkretskoder.kode as id,  storting_representanter.parti, storting_representanter.representant_nr as ofylkesreprnr, \n" +
                 "        storting_representanter.suppleant_nr as ovreprnr, storting_valgkretskoder.Eintaltekst as fylkesnavn, storting_valgkretskoder.sortering, storting_representanter.stilling, storting_perioder.id as kode, \n" +
                 "        politikere.id as Person, politikere.initialer, politikere.fornavn, politikere.etternavn as navn\n" +
                 "        FROM  storting_representanter INNER JOIN\n" +
                 "        storting_valgkretskoder ON storting_valgkretskoder.kode = storting_representanter.valgkrets  INNER JOIN\n" +
                 "        storting_perioder ON storting_representanter.periode = storting_perioder.id " +
                 "        INNER JOIN t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.Kode \n" +
                 "\t\tLEFT OUTER JOIN\n" +
                 "        politikere ON storting_representanter.person = politikere.id\n" +
                 "\t\tGROUP BY storting_perioder.fleirtaltekst, politikere.initialer, politikere.id, storting_representanter.valgkrets, storting_representanter.parti,\n" +
                 "        storting_representanter.representant_nr, politikere.fornavn, politikere.etternavn, storting_representanter.suppleant_nr, storting_valgkretskoder.Eintaltekst,\n" +
                 "        storting_valgkretskoder.sortering,  storting_representanter.stilling, storting_valgkretskoder.kode,  storting_perioder.id, t_felles_partinavn.kode  "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new NorskePolitikere();
            norskepolitikere[i].setFylkeNavn((String) rader[i].get("fylkesnavn"));
             norskepolitikere[i].setFylkeId((Integer) rader[i].get("id"));
             if(rader[i].get("ofylkesreprnr")==null){}else{
            norskepolitikere[i].setFylkeRepresentatNr((Integer) rader[i].get("ofylkesreprnr"));
             }
            norskepolitikere[i].setEtterNavn((String) rader[i].get("navn"));
            norskepolitikere[i].setForNavn((String) rader[i].get("fornavn"));
            if(rader[i].get("stilling1")==null){}else{
            norskepolitikere[i].setStilling((String) rader[i].get("stilling1"));
            }
             if(rader[i].get("ovreprnr")==null){}else{
            norskepolitikere[i].setOverRepresentatNr((Integer) rader[i].get("ovreprnr"));
             }
            norskepolitikere[i].setInitialer((String) rader[i].get("initialer"));
            norskepolitikere[i].setPersonId((Integer) rader[i].get("person"));
            norskepolitikere[i].setPeriode((String) rader[i].get("ValgPeriode"));
             //norskepolitikere[i].setPartiKortnavn((String) rader[i].get("parti"));
            norskepolitikere[i].setPartikode((Integer) rader[i].get("parti"));
       }
        return norskepolitikere;
    }
     /*1905 - 1940 fylkenavn*/
     private NorskePolitikere[] getAlleRepersentanerFylke1905_1940(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT t_storting_person_hist.Amt,  t_storting_person_hist.Internmerknad, t_storting_valgkretskoder.Sortering, t_storting_valgkretskoder.land_eller_by, t_storting_person_hist.storting_valkrinskode, t_storting_person_hist.valkrinsnavn, t_storting_person_hist.storting_fylke_id, t_felles_partinavn.eintaltekst AS PartiNavn, " +
                "               t_storting_stortingsperioder_before1945.eintaltekst, t_storting_person_hist.merknad,  t_felles_partinavn.kode\n" +           
                "               FROM t_storting_person_hist INNER JOIN\n" +
                "               t_storting_stortingsperioder_before1945 ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder_before1945.kode INNER JOIN\n" +
               "                t_felles_partinavn On t_felles_partinavn.kode = t_storting_person_hist.parti INNER JOIN\n	" +
               "				t_storting_valgkretskoder ON t_storting_valgkretskoder.kode = t_storting_person_hist.storting_valkrinskode	LEFT OUTER JOIN\n " +
               "                t_felles_person ON t_storting_person_hist.person_id = t_felles_person.person_id "

                + (condition != null ? " " + condition : "");
            */
          /* this below is latest
         String sqlSelect = " SELECT  DISTINCT   B1905_Perioder.Amt, B1905_Perioder.Internmerknad, DOK_Valgkretskoder.Sortering, \n" +
                 "                      DOK_Valgkretskoder.land_eller_by, B1905_Perioder.Kode, B1905_Perioder.Valgkretsnavn, DOK_Partinavn.Eintaltekst AS Partitekst, \n" +
                 "                      DOK_Stortingsperioder.Eintaltekst AS Periodetekst,  B1905_Perioder.Merknad, \n" +
                 "                      DOK_Partinavn.Kode AS Expr1\n" +

                 "FROM         B1905_Perioder INNER JOIN\n" +
                 "                      DOK_Stortingsperioder ON B1905_Perioder.PERIODE = DOK_Stortingsperioder.Kode INNER JOIN\n" +
                 "                      DOK_Partinavn ON B1905_Perioder.PARTI = DOK_Partinavn.Kode INNER JOIN\n" +
                 "                      DOK_Valgkretskoder ON B1905_Perioder.Kode = DOK_Valgkretskoder.Kode LEFT OUTER JOIN\n" +
                 "                      Norske_politikere ON B1905_Perioder.Person = Norske_politikere.Person"
                 + (condition != null ? " " + condition : "");
                 */
         String sqlSelect = " SELECT  DISTINCT  storting_valgkretskoder.sortering, storting_valgkretskoder.land_eller_by, storting_representanter.valgkrets as Kode, storting_valgkretskoder.Eintaltekst as Valgkretsnavn, t_felles_partinavn.eintaltekst AS Partitekst, \n" +
                 "         storting_perioder.navn_nb AS Periodetekst,   t_felles_partinavn.kode AS Expr1\n" +
                 "         FROM  storting_representanter INNER JOIN\n" +
                 "         storting_perioder ON storting_representanter.periode = storting_perioder.id INNER JOIN\n" +
                 "         t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.Kode INNER JOIN\n" +
                 "         storting_valgkretskoder ON storting_representanter.valgkrets = storting_valgkretskoder.Kode LEFT OUTER JOIN\n" +
                 "         politikere ON storting_representanter.person = politikere.id\n"
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new NorskePolitikere();

             norskepolitikere[i].setFylkeId((Integer) rader[i].get("Kode"));
            //norskepolitikere[i].setFylkeId((String) rader[i].get("storting_fylke_id"));
            //norskepolitikere[i].setPeriode((String) rader[i].get("periode"));
             //norskepolitikere[i].setPartiKortnavn((String) rader[i].get("parti"));
             norskepolitikere[i].setAmt((String) rader[i].get("Amt"));
             norskepolitikere[i].setValkrinsnavn((String) rader[i].get("Valgkretsnavn"));

       }
        return norskepolitikere;
    }
      /*representansupleant navn 1905-1940*/
     private NorskePolitikere[] getAlleRepersentaner1905_1940(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT t_storting_person_hist.Amt, t_storting_person_hist.stilling, t_storting_person_hist.Internmerknad, t_storting_valgkretskoder.Sortering, t_storting_valgkretskoder.land_eller_by, t_storting_person_hist.storting_valkrinskode, t_storting_person_hist.valkrinsnavn, t_storting_person_hist.storting_fylke_id, t_felles_partinavn.eintaltekst AS PartiNavn, " +
                "               t_storting_stortingsperioder_before1945.eintaltekst, t_storting_person_hist.sup_nr, t_storting_person_hist.rep_nr, t_storting_person_hist.merknad,  t_felles_partinavn.kode,\n" +
                "               t_felles_person.person_id, t_felles_person.initialer, t_felles_person.fornavn, t_felles_person.navn\n" +
                "               FROM t_storting_person_hist INNER JOIN\n" +
                "               t_storting_stortingsperioder_before1945 ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder_before1945.kode INNER JOIN\n" +
               "                t_felles_partinavn On t_felles_partinavn.kode = t_storting_person_hist.parti INNER JOIN\n	" +
               "				t_storting_valgkretskoder ON t_storting_valgkretskoder.kode = t_storting_person_hist.storting_valkrinskode	LEFT OUTER JOIN\n " +
               "                t_felles_person ON t_storting_person_hist.person_id = t_felles_person.person_id "

                + (condition != null ? " " + condition : "");
            */
         /* this below is latest
         String sqlSelect = " SELECT   B1905_Perioder.Amt,  B1905_Perioder.Stilling, B1905_Perioder.Internmerknad, DOK_Valgkretskoder.Sortering, DOK_Valgkretskoder.land_eller_by, \n" +
                 "                      B1905_Perioder.Kode, B1905_Perioder.Valgkretsnavn, DOK_Partinavn.Eintaltekst AS Partitekst, \n" +
                 "                      DOK_Stortingsperioder.Eintaltekst AS Periodetekst, B1905_Perioder.Supnr, B1905_Perioder.Repnr, B1905_Perioder.Merknad, \n" +
                 "                      DOK_Partinavn.Kode AS Expr1, Norske_politikere.Person, Norske_politikere.initialer, Norske_politikere.Fornavn, \n" +
                 "                      Norske_politikere.Navn\n" +
                 "FROM         B1905_Perioder INNER JOIN\n" +
                 "                      DOK_Stortingsperioder ON B1905_Perioder.PERIODE = DOK_Stortingsperioder.Kode INNER JOIN\n" +
                 "                      DOK_Partinavn ON B1905_Perioder.PARTI = DOK_Partinavn.Kode INNER JOIN\n" +
                 "                      DOK_Valgkretskoder ON B1905_Perioder.Kode = DOK_Valgkretskoder.Kode LEFT OUTER JOIN\n" +
                 "                      Norske_politikere ON B1905_Perioder.Person = Norske_politikere.Person "
                 + (condition != null ? " " + condition : "");
                 */
         String sqlSelect = " SELECT  storting_representanter.stilling,  storting_valgkretskoder.sortering, storting_valgkretskoder.land_eller_by, \n" +
                 "        storting_valgkretskoder.kode , storting_valgkretskoder.Eintaltekst as Valgkretsnavn, t_felles_partinavn.eintaltekst AS Partitekst, \n" +
                 "        storting_perioder.navn_nb AS Periodetekst, storting_representanter.suppleant_nr as Supnr, storting_representanter.representant_nr as Repnr, \n" +
                 "        t_felles_partinavn.kode AS Expr1, politikere.id as Person, politikere.initialer, politikere.fornavn, politikere.etternavn as Navn\n" +
                 "        FROM storting_representanter INNER JOIN\n" +
                 "        storting_perioder ON storting_representanter.periode = storting_perioder.id INNER JOIN\n" +
                 "        t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.Kode INNER JOIN\n" +
                 "        storting_valgkretskoder ON storting_representanter.valgkrets = storting_valgkretskoder.Kode LEFT OUTER JOIN\n" +
                 "        politikere ON storting_representanter.person = politikere.id "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new NorskePolitikere();

            norskepolitikere[i].setEtterNavn((String) rader[i].get("navn"));
            norskepolitikere[i].setForNavn((String) rader[i].get("fornavn"));

             norskepolitikere[i].setFylkeId((Integer) rader[i].get("Kode"));
            //norskepolitikere[i].setFylkeId((String) rader[i].get("storting_fylke_id"));
            if(rader[i].get("stilling")==null){}else{
            norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            }
            //norskepolitikere[i].setFylkeId((String) rader[i].get("storting_fylke_id"));
            norskepolitikere[i].setInitialer((String) rader[i].get("initialer"));
            norskepolitikere[i].setPersonId((Integer) rader[i].get("person"));
           // norskepolitikere[i].setPeriode((String) rader[i].get("periode"));
            // norskepolitikere[i].setPartiKortnavn((String) rader[i].get("parti"));
             norskepolitikere[i].setAmt((String) rader[i].get("Amt"));
             norskepolitikere[i].setValkrinsnavn((String) rader[i].get("valkrinsnavn"));
              if(rader[i].get("Repnr")==null){}else{
            norskepolitikere[i].setFylkeRepresentatNr((Integer) rader[i].get("Repnr"));
              }
           if(rader[i].get("Supnr") == null){}else{
            norskepolitikere[i].setOverRepresentatNr((Integer) rader[i].get("Supnr"));
             }

       }
        return norskepolitikere;
    }

    /*1814 - 1905 fylke navn*/
     private NorskePolitikere[] getAlleRepersentanerFylke1814_1905(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT t_storting_person_hist.Amt,  t_storting_person_hist.Internmerknad, t_storting_valgkretskoder.Sortering, t_storting_valgkretskoder.land_eller_by,  t_storting_person_hist.valkrinsnavn,   " +
                "               t_storting_stortingsperioder_before1945.eintaltekst,  t_storting_person_hist.merknad \n" +
                "               FROM t_storting_person_hist INNER JOIN\n" +
                "               t_storting_stortingsperioder_before1945 ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder_before1945.kode INNER JOIN\n" +
               "                t_felles_partinavn On t_felles_partinavn.kode = t_storting_person_hist.parti_kode INNER JOIN\n	" +
               "				t_storting_valgkretskoder ON t_storting_valgkretskoder.kode = t_storting_person_hist.storting_valkrinskode	LEFT OUTER JOIN\n " +
               "                t_felles_person ON t_storting_person_hist.person_id = t_felles_person.person_id "

                + (condition != null ? " " + condition : "");
        */
         /* this below is latest
         String sqlSelect = " SELECT  DISTINCT  DOK_Valgkretskoder.Sortering, \n" +
                 "                      Norske_stortingspolitikere.ny_valkrinskode, Norske_stortingspolitikere.VALKRINSNAMN,  \n" +
                 "                      DOK_Stortingsperioder.Eintaltekst AS Periodetekst,  \n" +
                 "                      DOK_Valgkretskoder.land_eller_by\n" +
                 "FROM         Norske_stortingspolitikere INNER JOIN\n" +
                 "                      DOK_Stortingsperioder ON Norske_stortingspolitikere.PERIODE = DOK_Stortingsperioder.Kode INNER JOIN\n" +
                 "                      DOK_Partinavn ON Norske_stortingspolitikere.PARTI = DOK_Partinavn.Kode INNER JOIN\n" +
                 "                      DOK_Valgkretskoder ON Norske_stortingspolitikere.ny_valkrinskode = DOK_Valgkretskoder.Kode LEFT OUTER JOIN\n" +
                 "                      Norske_politikere ON Norske_stortingspolitikere.Person = Norske_politikere.Person "
                 + (condition != null ? " " + condition : "");
                 */
         String sqlSelect = " SELECT  DISTINCT  storting_valgkretskoder.sortering, \n" +
                 "        storting_representanter.valgkrets as ny_valkrinskode, storting_valgkretskoder.Eintaltekst as VALKRINSNAMN, \n" +
                 "        storting_perioder.navn_nb AS Periodetekst,  \n" +
                 "        storting_valgkretskoder.land_eller_by\n" +
                 "        FROM  storting_representanter INNER JOIN\n" +
                 "        storting_perioder ON storting_representanter.periode = storting_perioder.id  INNER JOIN\n" +
                 "        t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.Kode INNER JOIN\n" +
                 "        storting_valgkretskoder ON storting_representanter.valgkrets = storting_valgkretskoder.Kode LEFT OUTER JOIN\n" +
                 "        politikere ON storting_representanter.person = politikere.id "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new NorskePolitikere();
           norskepolitikere[i].setFylkeId((Integer) rader[i].get("Sortering"));
            //norskepolitikere[i].setPeriode((String) rader[i].get("periode"));
             //norskepolitikere[i].setAmt((String) rader[i].get("Amt"));
             norskepolitikere[i].setValkrinsnavn((String) rader[i].get("VALKRINSNAMN"));
       }
        return norskepolitikere;
    }

   /*1814 - 1905 person navn*/
     private NorskePolitikere[] getAlleRepersentaner1814_1905(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT t_storting_person_hist.Amt, t_storting_person_hist.stilling, t_storting_person_hist.Internmerknad, t_storting_valgkretskoder.Sortering, t_storting_valgkretskoder.land_eller_by, t_storting_person_hist.storting_valkrinskode, t_storting_person_hist.valkrinsnavn,  t_felles_partinavn.eintaltekst AS PartiNavn, " +
                "               t_storting_stortingsperioder_before1945.eintaltekst, t_storting_person_hist.sup_nr, t_storting_person_hist.rep_nr, t_storting_person_hist.merknad,  t_felles_partinavn.kode,\n" +
                "               t_felles_person.person_id, t_felles_person.initialer, t_felles_person.fornavn, t_felles_person.navn\n" +
                "               FROM t_storting_person_hist INNER JOIN\n" +
                "               t_storting_stortingsperioder_before1945 ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder_before1945.kode INNER JOIN\n" +
               "                t_felles_partinavn On t_felles_partinavn.kode = t_storting_person_hist.parti_kode INNER JOIN\n	" +
               "				t_storting_valgkretskoder ON t_storting_valgkretskoder.kode = t_storting_person_hist.storting_valkrinskode	LEFT OUTER JOIN\n " +
               "                t_felles_person ON t_storting_person_hist.person_id = t_felles_person.person_id "

                + (condition != null ? " " + condition : "");
            */
         /* this below is latest
         String sqlSelect = " SELECT     Norske_stortingspolitikere.Person, Norske_stortingspolitikere.Stilling, DOK_Valgkretskoder.Sortering, \n" +
                 "                      Norske_stortingspolitikere.ny_valkrinskode, Norske_stortingspolitikere.VALKRINSNAMN, DOK_Partinavn.Eintaltekst AS Partitekst, \n" +
                 "                      DOK_Stortingsperioder.Eintaltekst AS Periodetekst, Norske_stortingspolitikere.Supnr, Norske_stortingspolitikere.Repnr, \n" +
                 "                      DOK_Valgkretskoder.land_eller_by, Norske_politikere.Fornavn, Norske_politikere.Navn\n" +
                 "FROM         Norske_stortingspolitikere INNER JOIN\n" +
                 "                      DOK_Stortingsperioder ON Norske_stortingspolitikere.PERIODE = DOK_Stortingsperioder.Kode INNER JOIN\n" +
                 "                      DOK_Partinavn ON Norske_stortingspolitikere.PARTI = DOK_Partinavn.Kode INNER JOIN\n" +
                 "                      DOK_Valgkretskoder ON Norske_stortingspolitikere.ny_valkrinskode = DOK_Valgkretskoder.Kode LEFT OUTER JOIN\n" +
                 "                      Norske_politikere ON Norske_stortingspolitikere.Person = Norske_politikere.Person "
                 + (condition != null ? " " + condition : "");
         */
         String sqlSelect = " SELECT  storting_representanter.person, storting_representanter.stilling, storting_valgkretskoder.sortering, \n" +
                 "         storting_representanter.valgkrets as ny_valkrinskode, storting_valgkretskoder.Eintaltekst as VALKRINSNAMN, t_felles_partinavn.eintaltekst AS Partitekst, \n" +
                 "         storting_perioder.navn_nb AS Periodetekst, storting_representanter.suppleant_nr as Supnr, storting_representanter.representant_nr as Repnr, \n" +
                 "         storting_valgkretskoder.land_eller_by, politikere.fornavn, politikere.etternavn as Navn\n" +
                 "         FROM   storting_representanter INNER JOIN\n" +
                 "         storting_perioder ON storting_representanter.periode = storting_perioder.id  INNER JOIN\n" +
                 "         t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.Kode INNER JOIN\n" +
                 "         storting_valgkretskoder ON storting_representanter.valgkrets = storting_valgkretskoder.Kode  LEFT OUTER JOIN\n" +
                 "         politikere ON storting_representanter.person = politikere.id "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new NorskePolitikere();

            norskepolitikere[i].setEtterNavn((String) rader[i].get("navn"));
            norskepolitikere[i].setForNavn((String) rader[i].get("fornavn"));
            norskepolitikere[i].setFylkeId((Integer) rader[i].get("Sortering"));
            if(rader[i].get("stilling")==null){}else{
            norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            }

            norskepolitikere[i].setInitialer((String) rader[i].get("initialer"));
            norskepolitikere[i].setPersonId((Integer) rader[i].get("person"));
            //norskepolitikere[i].setPeriode((String) rader[i].get("periode"));
            // norskepolitikere[i].setPartiKortnavn((String) rader[i].get("parti"));
            // norskepolitikere[i].setAmt((String) rader[i].get("Amt"));
             norskepolitikere[i].setValkrinsnavn((String) rader[i].get("VALKRINSNAMN"));
              if(rader[i].get("Repnr")==null){}else{
            norskepolitikere[i].setFylkeRepresentatNr((Integer) rader[i].get("Repnr"));
              }
           if(rader[i].get("Supnr") == null){}else{
            norskepolitikere[i].setOverRepresentatNr((Integer) rader[i].get("Supnr"));
             }

       }
        return norskepolitikere;
    }

    private NorskePolitikere[] getParti(String condition, List values) throws Exception {

      // tabell som returneres fra denne metoden.
        NorskePolitikere[] statusskjemaer = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*String sqlSelect = "SELECT MIN(DISTINCT parti_kortnavn) AS Eintaltekst " +
                "FROM t_storting_person_hist "
                + (condition != null ? " " + condition : "");
        */
        String sqlSelect = "SELECT MIN(DISTINCT eintaltekst_forkorting) AS Eintaltekst,  MIN(DISTINCT ENGeintaltekst_forkorting) AS engEintaltekst " +
                "FROM t_felles_partinavn "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new NorskePolitikere();
            if (this.engelsk) {statusskjemaer[i].setPartiKortnavn((String) rader[i].get("engEintaltekst"));}else{statusskjemaer[i].setPartiKortnavn((String) rader[i].get("Eintaltekst"));}

        }

        return statusskjemaer;

    }

    private NorskePolitikere[] getPartinavn(String condition, List values) throws Exception {

      // tabell som returneres fra denne metoden.
        NorskePolitikere[] statusskjemaer = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT kode,  eintaltekst " +
                "FROM t_felles_partinavn "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new NorskePolitikere();
            statusskjemaer[i].setPartiKortnavn((String) rader[i].get("eintaltekst"));

        }

        return statusskjemaer;

    }


    private NorskePolitikere[] getPartiperiodeaar(String condition, List values) throws Exception {

      // tabell som returneres fra denne metoden.
        NorskePolitikere[] statusskjemaer = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect =
                "SELECT storting_perioder.id as kode,  storting_perioder.navn_nb as eintaltekst, storting_perioder.fleirtaltekst  \n" +
                "        FROM storting_perioder "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new NorskePolitikere();
            statusskjemaer[i].setPartiKortnavn((String) rader[i].get("fleirtaltekst"));

        }

        return statusskjemaer;

    }


    private NorskePolitikere[] getSammensetning_aar(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] statusskjemaer = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT DISTINCT storting_sammensetning.periode as periodekode, storting_perioder.valgaar \n" +
                "       FROM storting_sammensetning INNER JOIN\n" +
                "       t_felles_partinavn ON storting_sammensetning.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "       storting_perioder ON storting_sammensetning.periode = storting_perioder.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        statusskjemaer = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            statusskjemaer[i] = new NorskePolitikere();
            statusskjemaer[i].setValgAar((Integer) rader[i].get("valgaar"));

        }

        return statusskjemaer;
    }

}
