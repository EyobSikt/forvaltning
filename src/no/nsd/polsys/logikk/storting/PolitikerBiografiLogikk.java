package no.nsd.polsys.logikk.storting;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.storting.PolitikerBiografi;

import javax.servlet.jsp.jstl.sql.Result;
import java.io.Serializable;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 06.des.2010
 * Time: 15:39:18
 * To change this template use File | Settings | File Templates.
 */
public class PolitikerBiografiLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;



    public PolitikerBiografiLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }

    //skrive metoder

     public PolitikerBiografi[] getPersonInfo(int person_id) throws Exception {
        String condition = " HAVING id = ? ";
         List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getPersonInfo(condition, values);
     }

    public PolitikerBiografi[] getPersonStortingInfo(int person_id) throws Exception {
           String condition = " WHERE storting_representanter.person = ?  ";
             condition +=" ORDER BY politikere.etternavn, storting_representanter.periode ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingInfo(condition, values);
       }

       public PolitikerBiografi[] getPersonStortingspresident(int person_id) throws Exception {
           /*
           String condition = " WHERE t_storting_komitemedlem.type = 21 AND t_storting_komitemedlem.person_id = ? ";
             condition +=" ORDER BY t_storting_komitemedlem.postnummer";
           */
           String condition = " WHERE (Type = 21) AND ( Person = ? )  " +
                   " ORDER BY Postnummer ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingspresident(condition, values);
       }

     public PolitikerBiografi[] getPersonStortingskomite(int person_id) throws Exception {
          /*
           String condition = " WHERE t_storting_komitemedlem.type = 31 AND t_storting_komitemedlem.person_id = ? ";
             condition +=" ORDER BY t_storting_komitemedlem.fra_aar, t_storting_komitemedlem.komitekode";
         */
         String condition = " WHERE     (komite_1905_medlemmer.type = 31) AND (komite_1905_medlemmer.person = ?)\n" +
                 "ORDER BY komite_1905_medlemmer.Fra_aar, komite_1905_medlemmer.komite ";
         List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingskomite(condition, values);
       }
        /*fagkomite*/
       public PolitikerBiografi[] getPersonStortingsfagkomite(int person_id) throws Exception {
           /*
           String condition = " WHERE(t_storting_komite_per_sesjon.periode < 87) AND (t_storting_komite_historikk.type_komite = '3') AND  t_storting_komite_per_sesjon.person_id = ? ";
             condition +=" ORDER BY t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_komite_historikk.type_komite, t_storting_komite_historikk.eintaltekst";
           */
           String condition = " WHERE   (komite_1905_stilling.periode < 87) AND (NOT(komite_2_1905.Type_komite = '3')) AND  (komite_1905_stilling.person = ?)\n" +
                   "ORDER BY storting_perioder.fleirtaltekst, komite_2_1905.Type_komite, komite_2_1905.navn ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingsfagkomite(condition, values);
       }
      /*speisal komite*/
      public PolitikerBiografi[] getPersonStortingsspesialkomite(int person_id) throws Exception {
         /*
           String condition = " WHERE (t_storting_komite_per_sesjon.periode < 87) AND (NOT(t_storting_komite_historikk.type_komite = '3')) AND  (t_storting_komite_per_sesjon.person_id = ? ) ";
             condition +=" ORDER BY t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_komite_historikk.type_komite, t_storting_komite_historikk.eintaltekst";
         */
          String condition = " WHERE  (komite_1905_stilling.periode < 87) AND (NOT(komite_2_1905.Type_komite = '3')) AND  (komite_1905_stilling.person = ? ) ";
          condition +=" ORDER BY storting_perioder.fleirtaltekst, komite_2_1905.Type_komite, komite_2_1905.navn";

           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingsfagkomite(condition, values);
       }

      /*fagkomite 1945d.d*/
       public PolitikerBiografi[] getPersonStortingsfagkomite_1945(int person_id) throws Exception {
           String condition = " WHERE (komite_type.kode = 'FAG' OR komite_type.kode = 'KOMI') AND  politikere.id = ? ";
             condition +=" ORDER BY komite_medlemmer.stortingsperiode ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingsfagkomite_1945(condition, values);
       }
      /*speisal komite  1945d.d*/
      public PolitikerBiografi[] getPersonStortingsspesialkomite_1945(int person_id) throws Exception {
           String condition = " WHERE  (komite_type.kode = 'SKOM') AND  (politikere.id = ? ) ";
             condition +=" ORDER BY  komite_medlemmer.stortingsperiode ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingsfagkomite_1945(condition, values);
       }



    /*delegasjon  1945d.d*/
      public PolitikerBiografi[] getPersonStortingdelegasjon_1945(int person_id) throws Exception {
           String condition = " WHERE  (komite_type.kode = 'DELE' OR komite_type.kode = 'EØS') AND  (politikere.id = ? ) ";
             condition +=" ORDER BY komite_medlemmer.stortingsperiode ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingsfagkomite_1945(condition, values);
       }

   /*delegasjon*/
    public PolitikerBiografi[] getPersonStortingdelegasjon(int person_id) throws Exception {
             /* String condition = " WHERE (t_storting_komitemedlem.type = 51 OR t_storting_komitemedlem.type = 41) AND t_storting_komitemedlem.person_id = ? ";
                condition +=" ORDER BY t_storting_komitemedlem.postnummer";
              */
        String condition = " WHERE (type = 51 OR type = 41) AND (person = ?)\n" +
                "ORDER BY person, type DESC, Postnummer ";
              List<Serializable> values = new ArrayList<Serializable>();
              values.add(person_id);
             return this.getPersonStortingspresident(condition, values);
    }

    /* medlemskap i groupestyr   1945d.d*/
      public PolitikerBiografi[] getPersonPartimedlemskap_1945(int person_id) throws Exception {
           String condition = " WHERE  (komite_type.kode = 'PART') AND  (politikere.id = ? ) ";
             condition +=" ORDER BY  komite_medlemmer.stortingsperiode ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingsfagkomite_1945(condition, values);
       }

    /*getPersonStortingsfoerer lederskap i stortingsgrouper*/

     public PolitikerBiografi[] getPersonStortingsfoerer(int person_id) throws Exception {
              /*String condition = " WHERE vervkode = 271 AND person_id = ? ";*/
                String condition = " WHERE   (verv = 271) AND (person = ?)  ";
                condition +=" ";

         List<Serializable> values = new ArrayList<Serializable>();
              values.add(person_id);
             return this.getPersonStortingsfoerer(condition, values);
    }


      /*regjering komite  1945d.d*/
      public PolitikerBiografi[] getPersonRegjeringmedlemskap_1945(int person_id) throws Exception {
           String condition = " WHERE  (komite_type.kode = 'REGJ') AND  (politikere.id = ? ) ";
             condition +=" ORDER BY  komite_medlemmer.stortingsperiode ";
           List<Serializable> values = new ArrayList<Serializable>();
           values.add(person_id);
          return this.getPersonStortingsfagkomite_1945(condition, values);
       }

    /*getStatsraadstatsSekretaer*/
         public PolitikerBiografi[] getStatsraad(int person_id) throws Exception {
             /*
                 String condition = " WHERE (t_felles_statsraader.slutt BETWEEN t_felles_unike_dep.fra_tidspunkt AND \n" +
                         "                      t_felles_unike_dep.til_tidspunkt) AND t_felles_person.person_id = ? ";
                   condition +=" ORDER BY t_felles_statsraader.start";
             */
             String condition = " WHERE (politikere.id = ?) AND (regjering_medlemmer.slutt BETWEEN regjering_departement.fra_tidspunkt AND \n" +
                     "                      regjering_departement.til_tidspunkt) \n" +
                     "ORDER BY regjering_medlemmer.start  ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getStatsraad(condition, values);
       }

       /*getStatsraadstatsSekretaer*/
         public PolitikerBiografi[] getSekretaer(int person_id) throws Exception {
             /*
             String condition = " WHERE (t_felles_statssekretaerer.slutt BETWEEN t_felles_unike_dep.fra_tidspunkt AND \n" +
                         "                      t_felles_unike_dep.til_tidspunkt) AND t_felles_person.person_id = ? ";
                   condition +=" ORDER BY t_felles_statssekretaerer.start";
             */
             String condition = " WHERE (politikere.id = ?) AND (regjering_statssekretaerer.slutt BETWEEN regjering_departement.fra_tidspunkt AND \n" +
                     "                      regjering_departement.til_tidspunkt) \n" +
                     "ORDER BY regjering_statssekretaerer.start  " ;
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getSekretaer(condition, values);
       }
      /*getPersonligInfo*/

        public PolitikerBiografi[] getPersonligInfo(int person_id) throws Exception {
                 //String condition = " WHERE  p_hist.person_id = ? ";
                String condition = " WHERE B1095_politikere.person=? ";
            condition +=" ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getPersonligInfo(condition, values);
       }


       /*utdyrk*/
             public PolitikerBiografi[] getUtdyrke(int person_id) throws Exception {
                 String condition = " WHERE  Person = ? ";
                   condition +=" ORDER BY Fra_aar, Person ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getUtdyrke(condition, values);
       }

     /*utdanning og yrke*/

          public PolitikerBiografi[] getUtdanningYrke(int person_id) throws Exception {
                 String condition = " WHERE  Person = ? AND type=10 ";
                   condition +=" ORDER BY Fra_aar, Person, Postnummer ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getUtdyrke(condition, values);
       }


    /*utdyrk 1945*/
    public PolitikerBiografi[] getUtdyrke_1945(int person_id) throws Exception {
        String condition = " WHERE  stortingsid.person = ? ";
        condition +=" ORDER BY fraaar, stortingsid.person ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getUtdyrke_1945(condition, values);
    }

     /*utdanning og yrke 1945*/

    public PolitikerBiografi[] getUtdanningYrke_1945(int person_id) throws Exception {
        String condition = " WHERE stortingsid.person = ? AND type=10 ";
        condition +=" ORDER BY fraaar, stortingsid.person ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getUtdyrke_1945(condition, values);
    }


     /*kompol*/

       public PolitikerBiografi[] getKomPol(int person_id) throws Exception {
                /* String condition = " WHERE  t_storting_komfylk.type = N'K' AND t_storting_komfylk.person_id = ? ";
                   condition +=" ORDER BY t_storting_komfylkperioder.eintaltekst, t_storting_komfylk.person_id, t_storting_komfylk.postnummer, t_storting_komfylk.periodekode, \n" +
                           "                      t_storting_komfylk.stillingkode ";
                 */
           String condition = " WHERE     (komfylk_1905.type = N'K') AND (komfylk_1905.person = ?)\n" +
                   "ORDER BY _komfylk_periode.navn, komfylk_1905.person, komfylk_1905.Postnummer, komfylk_1905.periode, \n" +
                   "                      komfylk_1905.Stilling ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getKomPol(condition, values);
       }
      /*kompol 1945 */

    public PolitikerBiografi[] getKomPol_1945(int person_id) throws Exception {
        String condition = " WHERE  komfylk.type = N'K' AND stortingsid.person = ? \n " +
                " ORDER BY komfylk_periode.navn, komfylk.person, komfylk.periode, \n " +
                " komfylk.stilling ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getKomPol_1945(condition, values);
    }
     /*fylpol*/
      public PolitikerBiografi[] getFylPol(int person_id) throws Exception {
                /* String condition = " WHERE   t_storting_komfylk.type = N'F' AND t_storting_komfylk.person_id = ? ";
                   condition +=" ORDER BY  t_storting_komfylk.person_id, t_storting_komfylk.postnummer, t_storting_komfylk.periodekode, t_storting_komfylk.stillingkode ";
                 */
          String condition = " WHERE   (komfylk_1905.type = N'F') AND (komfylk_1905.person = ?)\n" +
                  "ORDER BY komfylk_1905.person, komfylk_1905.Postnummer, komfylk_1905.periode, komfylk_1905.stilling ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getFylPol(condition, values);
       }

    /*fylpol 1945*/
    public PolitikerBiografi[] getFylPol_1945(int person_id) throws Exception {
                /* String condition = " WHERE   t_storting_komfylk.type = N'F' AND t_storting_komfylk.person_id = ? ";
                   condition +=" ORDER BY  t_storting_komfylk.person_id, t_storting_komfylk.postnummer, t_storting_komfylk.periodekode, t_storting_komfylk.stillingkode ";
                 */
        String condition = " WHERE  komfylk.type = N'F' AND stortingsid.person = ? \n " +
                " ORDER BY komfylk_periode.navn, komfylk.person, komfylk.periode, \n " +
                " komfylk.stilling ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getFylPol_1945(condition, values);
    }

      /*getPersonStortingsOffVerv*/
      public PolitikerBiografi[] getPersonStortingsOffVerv(int person_id) throws Exception {
                // String condition = " WHERE  person_id = ? ";
                 //  condition +=" ORDER BY fra_aar, til_aar, person_id, postnummer ";
                String condition = " WHERE    (Person = ?)\n" +
                        "ORDER BY Fra_aar, Til_aar, Person, Postnummer ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getPersonStortingsOffVerv(condition, values);
       }
    /*getPersonStortingsOffVerv*/
    public PolitikerBiografi[] getPersonStortingsOffVerv_1945(int person_id) throws Exception {
        // String condition = " WHERE  person_id = ? ";
        //  condition +=" ORDER BY fra_aar, til_aar, person_id, postnummer ";
        String condition = " WHERE    (stortingsid.person = ?)\n" +
                "ORDER BY fraaar, tilaar, Person ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getPersonStortingsOffVerv_1945(condition, values);
    }

      /*partiverv*/

     public PolitikerBiografi[] getPersonStortingsPartiverv(int person_id) throws Exception {
              //String condition = " WHERE  person_id = ?  ";
                //condition +=" ORDER BY fra_aar, til_aar, person_id, postnummer, vervkode ";
         String condition = " WHERE   (Person = ?) AND (NOT (verv = 271))\n" +
                 "ORDER BY fra, til, Person, Postnummer, verv ";
         List<Serializable> values = new ArrayList<Serializable>();
              values.add(person_id);
             return this.getPersonStortingsPartiverv(condition, values);
    }
     /*partiverv 1945*/
    public PolitikerBiografi[] getPersonStortingsPartiverv_1945(int person_id) throws Exception {
        //String condition = " WHERE  person_id = ?  ";
        //condition +=" ORDER BY fra_aar, til_aar, person_id, postnummer, vervkode ";
        String condition = " WHERE   (stortingsid.person = ?) " +
                "ORDER BY fraaar desc ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getPersonStortingsPartiverv_1945(condition, values);
    }

     /*getPersonStortingsOrgVerv */
      public PolitikerBiografi[] getPersonStortingsOrgVerv(int person_id) throws Exception {
                 //String condition = " WHERE  person_id = ? ";
                  // condition +=" ORDER BY fra_aar, til_aar, person_id, postnummer ";

                String condition = " WHERE   (Person = ?)\n" +
                        "ORDER BY Fra_aar, Til_aar, Person, Postnummer ";
                List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getPersonStortingsOrgVerv(condition, values);
       }
    /*getPersonStortingsOrgVerv 1945*/
    public PolitikerBiografi[] getPersonStortingsOrgVerv_1945(int person_id) throws Exception {
        String condition = " WHERE   (stortingsid.person = ?)\n" +
                "ORDER BY fraaar, tilaar, verv_organisasjoner.person ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getPersonStortingsOrgVerv_1945(condition, values);
    }

    /*getPersonStortingsAdmVerv */
      public PolitikerBiografi[] getPersonStortingsAdmVerv(int person_id) throws Exception {
                 //String condition = " WHERE  person_id = ? ";
                  // condition +=" ORDER BY fra_aar, til_aar, person_id, postnummer ";
                String condition = " WHERE     (Person = ?)\n" +
                        "ORDER BY Fra_aar, Til_aar, Person, Postnummer " ;
                List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getPersonStortingsAdmVerv(condition, values);
       }
    /*getPersonStortingsAdmVerv */
    public PolitikerBiografi[] getPersonStortingsAdmVerv_1945(int person_id) throws Exception {
        //String condition = " WHERE  person_id = ? ";
        // condition +=" ORDER BY fra_aar, til_aar, person_id, postnummer ";
        String condition = " WHERE     (stortingsid.person = ?)\n" +
                "ORDER BY fraaar, tilaar, verv_administrative.person " ;
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getPersonStortingsAdmVerv_1945(condition, values);
    }

    /*getPersonStortingsLitteratur*/
    public PolitikerBiografi[] getPersonStortingsLitteratur(int person_id) throws Exception {
                // String condition = " WHERE  person_id = ? ";
                 //  condition +=" ORDER BY utgaar, person_id, postnummer ";

                String condition = " WHERE     (Person = ?)\n" +
                        "ORDER BY Fra_aar, Til_aar, Person, Postnummer ";

                List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getPersonStortingsLitteratur(condition, values);
       }
    /*getPersonStortingsLitteratur*/
    public PolitikerBiografi[] getPersonStortingsLitteratur_1945(int person_id) throws Exception {
        // String condition = " WHERE  person_id = ? ";
        //  condition +=" ORDER BY utgaar, person_id, postnummer ";

        String condition = " WHERE     (stortingsid.person = ?)\n" +
                "ORDER BY utgaar, litteratur.person ";

        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getPersonStortingsLitteratur_1945(condition, values);
    }

       /*diverse*/

          public PolitikerBiografi[] getDiverse(int person_id) throws Exception {
                 String condition = " WHERE  Person = ? AND  (Kode2 = 1 OR\n" +
                         "                      Kode2 = 3 OR\n" +
                         "                      Kode2 = 4 OR\n" +
                         "                      Kode2 = 5 OR\n" +
                         "                      Kode2 = 6 OR\n" +
                         "                      Kode2 = 7) ";
                   condition +=" ORDER BY aktivitet_1905.Fra_aar, aktivitet_1905.Person, aktivitet_1905.Postnummer ";
                 List<Serializable> values = new ArrayList<Serializable>();
                 values.add(person_id);
                return this.getUtdyrke(condition, values);
       }
     /*diverse 1945*/

    public PolitikerBiografi[] getDiverse_1945(int person_id) throws Exception {
        String condition = " WHERE  stortingsid.person = ? AND  type=30 " ;
        condition +=" ORDER BY fraaar, stortingsid.person ";
        List<Serializable> values = new ArrayList<Serializable>();
        values.add(person_id);
        return this.getUtdyrke_1945(condition, values);
    }


    /*
    *
    *  *******************  Her begynner lese metoder **********************
    *  *******************  Her begynner lese metoder **********************
    *  *******************  Her begynner lese metoder **********************
    *
    * */

     private PolitikerBiografi[] getPersonInfo(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     id, initialer, fornavn, etternavn, foedtaar as faar, YEAR(politikere.doed) as dodsaar, fodt, doed " +
                "FROM         politikere " +
                "GROUP BY id, initialer, fornavn, etternavn, fodt, doed, foedtaar "

                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setEtterNavn((String) rader[i].get("etternavn"));
            norskepolitikere[i].setForNavn((String) rader[i].get("fornavn"));
            norskepolitikere[i].setInitialer((String) rader[i].get("initialer")); 
            Object fodt_dato = rader[i].get("fodt");
		    java.text.DateFormat dfYMD_fodt = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		    java.text.DateFormat dfDMY_fodt = new java.text.SimpleDateFormat("dd-MM-yyyy") ;
		    try {
			 if(rader[i].get("fodt")!=null){
            norskepolitikere[i].setFodt(dfDMY_fodt.format(dfYMD_fodt.parse(String.valueOf(fodt_dato))));
             }else{
                 if(rader[i].get("faar") !=null){norskepolitikere[i].setFodt(Integer.toString((Integer) rader[i].get("faar")) ); }
             }
		} catch (ParseException e) {
			e.printStackTrace();
		}

            Object dod_dato = rader[i].get("doed");
		    java.text.DateFormat dfYMD_dod = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		    java.text.DateFormat dfDMY_dod = new java.text.SimpleDateFormat("dd-MM-yyyy") ;
		    try {
			 if(rader[i].get("doed")!=null){
            norskepolitikere[i].setDod(dfDMY_dod.format(dfYMD_dod.parse(String.valueOf(dod_dato))));
             }else{
                 if(rader[i].get("dodsaar") !=null){norskepolitikere[i].setDod(Integer.toString((Integer) rader[i].get("dodsaar"))); }
             }
		} catch (ParseException e) {
			e.printStackTrace();
		}


       }
        return norskepolitikere;
    }


    private PolitikerBiografi[] getPersonStortingInfo(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT  storting_representanter.person, storting_representanter.stilling, storting_representanter.kommentar_ekstern AS eksternkommentar, \n" +
                "        storting_perioder.id AS Periodekode, storting_perioder.navn_nb AS Periodetekst, storting_perioder.fleirtaltekst,\n" +
                "        t_felles_partinavn.eintaltekst AS Partitekst, t_felles_partinavn.eintaltekst_forkorting ,valgkretser.navn_nb AS valkrinsnavn, storting_representanter.representant_nr AS Rep_nr, \n" +
                "        storting_representanter.suppleant_nr AS Sup_nr, politikere.fornavn, politikere.etternavn \n" +
                "        FROM  storting_representanter INNER JOIN \n" +
                "        t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.kode INNER JOIN \n" +
                "        storting_perioder ON storting_representanter.periode = storting_perioder.id LEFT OUTER JOIN  \n" +
                "        politikere ON storting_representanter.person = politikere.id LEFT OUTER JOIN  \n" +
                "        valgkretser ON storting_representanter.valgkrets = valgkretser.kode "

                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];
        boolean x=false;
         boolean y=false;
         for (int i = 0; i < rader.length; i++) {
          if(rader[i].get("Repnr")!=null){x = true;}
          if(rader[i].get("Supnr")!=null){y = true;}
         }
        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
      
            if(rader[i].get("stilling")==null){}else{
            norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            }
             //norskepolitikere[i].setMerknad((String) rader[i].get("merknad"));
             norskepolitikere[i].setEksternkommentar((String) rader[i].get("eksternkommentar"));
            norskepolitikere[i].setFleirtaltekst((String) rader[i].get("fleirtaltekst"));
            norskepolitikere[i].setPeriode((Integer) rader[i].get("Periodekode"));
             norskepolitikere[i].setPartiNavn((String) rader[i].get("Partitekst"));
             norskepolitikere[i].setPartikortnavn((String) rader[i].get("Eintaltekst_forkorting"));
             if(rader[i].get("valkrinsnavn")!=null)
              {
             norskepolitikere[i].setValkrinsnavn((String) rader[i].get("valkrinsnavn"));
              }
            else if(rader[i].get("fylkenavn")!=null){
               //norskepolitikere[i].setValkrinsnavn((String) rader[i].get("fylkenavn"));
             }
              if(rader[i].get("Rep_nr")!=null)
              {
              norskepolitikere[i].setRep_nr((Integer) rader[i].get("Rep_nr"));
              }else if(rader[i].get("ofylkesreprnr")!=null && !x ){
            //norskepolitikere[i].setRep_nr((Integer) rader[i].get("ofylkesreprnr"));
              }
           if(rader[i].get("Sup_nr") != null)
           {
               norskepolitikere[i].setSup_nr((Integer) rader[i].get("Sup_nr"));
           }
           else if(rader[i].get("ovreprnr") != null && !y){
            //norskepolitikere[i].setSup_nr((Integer) rader[i].get("ovreprnr"));
             }

       }
        return norskepolitikere;
    }

       /*president delegasjon*/
     private PolitikerBiografi[] getPersonStortingspresident(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT t_storting_komitemedlem.person_id, t_storting_komitemedlem.type, t_storting_komitemedlem.merknad ,t_storting_komitemedlem.komitekode, t_storting_komitemedlem.institusjon, \n" +
                "                     t_storting_komitemedlem.postnummer ,t_storting_komitemedlem.til_aar, t_storting_komitemedlem.fra_aar\n" +
                "FROM        t_storting_komitemedlem "
                + (condition != null ? " " + condition : "");
           */
         String sqlSelect = " SELECT   person, type, Stilling, Institusjon, Fra_aar, Til_aar, Merknad, Postnummer\n" +
                 "FROM        komite_1905_medlemmer "
         + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
         if(rader[i].get("Fra_aar") != null){
            norskepolitikere[i].setFraaar((String) rader[i].get("Fra_aar"));
         }
          if(rader[i].get("Til_aar") != null){
            norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
        }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
            norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
       }
        return norskepolitikere;
    }


     private PolitikerBiografi[] getPersonStortingskomite(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT t_storting_komitemedlem.person_id, t_storting_komitemedlem.type, t_storting_komitemedlem.komitekode, t_storting_komitemedlem.institusjon, \n" +
                "                      t_storting_komitemedlem.til_aar, t_storting_komitemedlem.fra_aar, t_storting_stortingsperioder_before1945.fleirtaltekst\n" +
                "FROM        t_storting_komitemedlem INNER JOIN\n" +
                "                      t_storting_stortingsperioder_before1945 ON t_storting_komitemedlem.fra_aar COLLATE DATABASE_DEFAULT = t_storting_stortingsperioder_before1945.eintaltekst COLLATE DATABASE_DEFAULT "

                + (condition != null ? " " + condition : "");
            */

         String sqlSelect = " SELECT     komite_1905_medlemmer.person, komite_1905_medlemmer.type, komite_1905_medlemmer.komite, komite_1905_medlemmer.Institusjon, \n" +
                 "                      komite_1905_medlemmer.Til_aar, komite_1905_medlemmer.Fra_aar, storting_perioder.fleirtaltekst\n" +
                 "FROM         komite_1905_medlemmer INNER JOIN\n" +
                 "                      storting_perioder ON komite_1905_medlemmer.Fra_aar = storting_perioder.navn_nb "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

          norskepolitikere[i].setFraaar((String) rader[i].get("Fra_aar"));
            norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
            norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));

       }
        return norskepolitikere;
    }

      /* fag komitte spesialkomite */
     private PolitikerBiografi[] getPersonStortingsfagkomite(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT t_storting_komite_per_sesjon.person_id, t_storting_komite_per_sesjon.komite_historikk_kode, t_storting_komite_per_sesjon.status, \n" +
                "                      t_storting_komite_per_sesjon.kommentar, t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_komite_historikk.eintaltekst, \n" +
                "                      t_storting_komite_historikk.type_komite, t_storting_komite_historikk.kode\n" +
                "FROM        t_storting_komite_per_sesjon INNER JOIN\n" +
                "                      t_storting_komite_historikk ON t_storting_komite_per_sesjon.komite_historikk_kode = t_storting_komite_historikk.kode INNER JOIN\n" +
                "                      t_storting_stortingsperioder_before1945 ON t_storting_komite_per_sesjon.periode = t_storting_stortingsperioder_before1945.kode "

                + (condition != null ? " " + condition : "");
            */

         String sqlSelect = " SELECT komite_1905_stilling.person, komite_1905_stilling.komite, komite_1905_stilling.stilling, \n" +
                 "       komite_1905_stilling.Kommentar, storting_perioder.fleirtaltekst, komite_2_1905.navn, \n" +
                 "       komite_2_1905.Type_komite, komite_2_1905.id \n" +
                 "       FROM  komite_1905_stilling INNER JOIN\n" +
                 "       komite_2_1905 ON komite_1905_stilling.komite = komite_2_1905.id INNER JOIN\n" +
                 "       storting_perioder ON komite_1905_stilling.periode = storting_perioder.id "
                 + (condition != null ? " " + condition : "");

         sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

             norskepolitikere[i].setFleirtaltekst((String) rader[i].get("fleirtaltekst"));

            norskepolitikere[i].setEintaltekst((String) rader[i].get("navn"));
             if(rader[i].get("status") != null){
            norskepolitikere[i].setStatus((Integer) rader[i].get("stilling"));
             }
            norskepolitikere[i].setKommentar((String) rader[i].get("Kommentar"));

       }
        return norskepolitikere;
    }

/* fag komitte spesialkomite delegasjon 1945 d.d*/
    private PolitikerBiografi[] getPersonStortingsfagkomite_1945(String condition, List<Serializable> values) throws Exception {
       // tabell som returneres fra denne metoden.
       PolitikerBiografi[] norskepolitikere = null;
       // resultat fra sql-sporring.
       Result result = null;
       // objekt som brukes til aa utfore sql-sporring.
       SqlCommandBean sqlCB = new SqlCommandBean();
       // inneholder data fra sql-sporring.
       SortedMap[] rader = null;
       // SQL-sporring.
        /*
       String sqlSelect = "SELECT DISTINCT person_id, stortingsperiode, fradato, tildato, t_storting_komite_historikk_1945.komnavn, t_storting_komite_historikk_1945.komkortnavn, t_storting_komiteverv_1945.verv, t_storting_komiteverv_1945.tekst as vervtekst, t_storting_komitetype_1945.komtype ,t_storting_komitetype_1945.tekst as typetekst, t_regjering_department_1945.tekst AS deptekst  \n" +
               "FROM t_storting_komitemedlem_1945\n" +
               "INNER JOIN t_storting_komite_historikk_1945 ON t_storting_komite_historikk_1945.komkode = t_storting_komitemedlem_1945.komkode\n" +
               "INNER JOIN t_storting_komiteverv_1945 ON t_storting_komiteverv_1945.verv = t_storting_komitemedlem_1945.verv\n" +
               "INNER JOIN t_storting_komitetype_1945 ON t_storting_komitetype_1945.komtype = t_storting_komitemedlem_1945.komtype " +
               "LEFT OUTER JOIN t_regjering_department_1945 ON t_regjering_department_1945.dep = t_storting_komitemedlem_1945.dep\n"
               + (condition != null ? " " + condition : "");


        String sqlSelect = "SELECT DISTINCT person_id, stortingsperiode, fradato, tildato, REGep_komite.komnavn, REGep_komite.komkortnavn, REGep_verv.verv, REGep_verv.tekst as vervtekst, REGEp_komitetype.komtype ,REGEp_komitetype.tekst as typetekst, REGEp_departement.tekst AS deptekst  \n" +
                "FROM Ep_komitemedlem " +
                "inner join t_felles_person On Ep_komitemedlem.initialer COLLATE DATABASE_DEFAULT = t_felles_person.initialer COLLATE DATABASE_DEFAULT " +
                "INNER JOIN REGep_komite ON REGep_komite.komkode = Ep_komitemedlem.komkode\n" +
                "INNER JOIN REGep_verv ON REGep_verv.verv = Ep_komitemedlem.verv\n" +
                "INNER JOIN REGEp_komitetype ON REGEp_komitetype.komtype = Ep_komitemedlem.komtype " +
                "LEFT OUTER JOIN REGEp_departement ON REGEp_departement.dep = Ep_komitemedlem.dep\n"
                + (condition != null ? " " + condition : "");
         */
        String sqlSelect = " SELECT DISTINCT politikere.id as person, komite_medlemmer.stortingsperiode, komite_medlemmer.fra, DAY(komite_medlemmer.fra) AS startday, MONTH(komite_medlemmer.fra) AS startmonth, YEAR(komite_medlemmer.fra) AS startyear, " +
                " komite_medlemmer.til, DAY(komite_medlemmer.til) AS sluttday, MONTH(komite_medlemmer.til) AS sluttmonth, YEAR(komite_medlemmer.til) AS sluttyear, komite.navn AS komnavn, komite.kode AS komkortnavn, komite_verv.kode, \n" +
                "\t\t\tkomite_verv.navn as vervtekst, komite_type.kode ,komite_verv.navn as typetekst,  regjering_departement.navn_nb AS deptekst  \n" +
                "            FROM komite_medlemmer \n" +
                "            inner join politikere On komite_medlemmer.__referanse_initialer COLLATE DATABASE_DEFAULT = politikere.initialer COLLATE DATABASE_DEFAULT\n" +
                "            INNER JOIN komite ON komite.id = komite_medlemmer.kommite\n" +
                "            INNER JOIN komite_verv ON komite_verv.id = komite_medlemmer.verv\n" +
                "            INNER JOIN komite_type ON komite_type.id = komite_medlemmer.kommite_type \n" +
                "            LEFT OUTER JOIN regjering_departement ON regjering_departement.id = komite_medlemmer.dep "
                + (condition != null ? " " + condition : "");

       sqlCB.setConnection(this.conn);
       sqlCB.setSqlValue(sqlSelect); //sporring
       sqlCB.setValues(values); //parameter
       result = sqlCB.executeQuery();
       rader = result.getRows();
       norskepolitikere = new PolitikerBiografi[rader.length];

       for (int i = 0; i < rader.length; i++) {
           norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setFleirtaltekst((String) rader[i].get("stortingsperiode"));
            norskepolitikere[i].setEintaltekst((String) rader[i].get("komnavn"));
            if(rader[i].get("vervtekst") != null){
           norskepolitikere[i].setKomitevervtekst((String) rader[i].get("vervtekst"));
            }
          if(rader[i].get("deptekst") != null){
           norskepolitikere[i].setRegjeringdepartment((String) rader[i].get("deptekst"));
           }

           if(rader[i].get("fra") != null){
           norskepolitikere[i].setFraaar(rader[i].get("startday") + "." + rader[i].get("startmonth") + "." + rader[i].get("startyear"));
           }
           if(rader[i].get("til") != null){
           norskepolitikere[i].setTilaar(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
           }
      }
       return norskepolitikere;
   }



/*foerer */
    
     private PolitikerBiografi[] getPersonStortingsfoerer(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT person_id, postnummer, stilling, institusjon, fra_aar, til_aar, merknad, vervkode\n" +
                "FROM        t_storting_partiverv "
                + (condition != null ? " " + condition : "");
        */
         String sqlSelect =  " SELECT person, Postnummer, Institusjon, Stilling, fra AS Fra_aar, til AS Til_aar, Merknad, verv AS vervkode\n" +
                 "FROM        verv_parti_1905 "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
             if(rader[i].get("fra_aar") != null){
            norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fra_aar"));
        }
        if(rader[i].get("til_aar") != null){
         norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("til_aar"));
        }
         norskepolitikere[i].setInstitusjon((String) rader[i].get("institusjon"));
          norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
          norskepolitikere[i].setMerknad((String) rader[i].get("merknad"));   
         }
        return norskepolitikere;
    }

 /*statsraad  */

   private PolitikerBiografi[] getStatsraad(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
       /*
        String sqlSelect = "SELECT t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_statsraader.slutt,\n" +
                " t_felles_unike_dep.eintaltekst as eintaltekst, t_felles_statsraader.start,  \n" +
                " t_felles_statsraader.stilling_avvik as stilling, t_felles_statsraader.kode_dep\n" +
                "FROM (t_felles_person INNER JOIN t_felles_statsraader ON t_felles_person.person_id =\n" +
                " t_felles_statsraader.person_id) INNER JOIN t_felles_unike_dep ON t_felles_statsraader.kode_dep =\n" +
                " t_felles_unike_dep.kode "
                + (condition != null ? " " + condition : "");
            */

       String sqlSelect = " SELECT politikere.id, politikere.fornavn, politikere.etternavn, regjering_medlemmer.slutt,\n" +
               "       regjering_departement.navn_nb, regjering_departement.navn_en, regjering_medlemmer.start, regjering_medlemmer.slutt, \n" +
               "       regjering_stilling.navn as Stilling_avvik, regjering_medlemmer.departement\n" +
               "       FROM (politikere INNER JOIN regjering_medlemmer ON politikere.id =\n" +
               "       regjering_medlemmer.person) INNER JOIN regjering_departement ON regjering_medlemmer.departement =\n" +
               "       regjering_departement.id INNER JOIN regjering_stilling ON regjering_medlemmer.stilling =\n" +
               "       regjering_stilling.id "
               + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

             Object start_dato = rader[i].get("start");
		    java.text.DateFormat dfYMD_fodt = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		    java.text.DateFormat dfDMY_fodt = new java.text.SimpleDateFormat("dd-MM-yyyy") ;
		    try {
			 if(rader[i].get("start")!=null){
            norskepolitikere[i].setFraaar(dfDMY_fodt.format(dfYMD_fodt.parse(String.valueOf(start_dato))));
             }
		} catch (ParseException e) {
			e.printStackTrace();
		}

            Object slutt_dato = rader[i].get("slutt");
		    java.text.DateFormat dfYMD_dod = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		    java.text.DateFormat dfDMY_dod = new java.text.SimpleDateFormat("dd-MM-yyyy") ;
		    try {
			 if(rader[i].get("slutt")!=null){
            norskepolitikere[i].setTilaar(dfDMY_dod.format(dfYMD_dod.parse(String.valueOf(slutt_dato))));
             }
		} catch (ParseException e) {
			e.printStackTrace();
		}
            if (this.engelsk) {
                norskepolitikere[i].setEintaltekst((String) rader[i].get("navn_en"));
            }
            else{
                norskepolitikere[i].setEintaltekst((String) rader[i].get("navn_nb"));
            }
          norskepolitikere[i].setStilling((String) rader[i].get("Stilling_avvik"));
       }
        return norskepolitikere;
    }
     /*statssekretaer*/
    private PolitikerBiografi[] getSekretaer(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
        String sqlSelect = "SELECT t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, DAY(t_felles_statssekretaerer.slutt) AS sluttday, MONTH(t_felles_statssekretaerer.slutt) AS sluttmonth, YEAR(t_felles_statssekretaerer.slutt) AS sluttyear, \n" +
                " t_felles_unike_dep.eintaltekst as eintaltekst, DAY(t_felles_statssekretaerer.start) AS startday, MONTH(t_felles_statssekretaerer.start) AS startmonth, YEAR(t_felles_statssekretaerer.start) AS startyear,  \n" +
                " t_felles_statssekretaerer.stilling_avvik as stilling_avvik, t_felles_statssekretaerer.kode_dep\n" +
                "FROM (t_felles_person INNER JOIN t_felles_statssekretaerer ON t_felles_person.person_id =\n" +
                " t_felles_statssekretaerer.person_id) INNER JOIN t_felles_unike_dep ON t_felles_statssekretaerer.kode_dep =\n" +
                " t_felles_unike_dep.kode "
                + (condition != null ? " " + condition : "");
            */

        String sqlSelect = " SELECT politikere.id, politikere.fornavn, politikere.etternavn, DAY(regjering_statssekretaerer.slutt) AS sluttday, MONTH(regjering_statssekretaerer.slutt) AS sluttmonth, YEAR(regjering_statssekretaerer.slutt) AS sluttyear, regjering_statssekretaerer.slutt,\n" +
                "                 regjering_departement.navn_nb, regjering_departement.navn_en, DAY(regjering_statssekretaerer.start) AS startday, MONTH(regjering_statssekretaerer.start) AS startmonth, YEAR(regjering_statssekretaerer.start) AS startyear, regjering_statssekretaerer.start, regjering_statssekretaerer.slutt, \n" +
                "                  regjering_statssekretaerer.stilling_avvik_nb,regjering_statssekretaerer.stilling_avvik_en, regjering_statssekretaerer.stilling_avvik_en, regjering_statssekretaerer.departement,\n" +
                "                  regjering_statssekretaerer.kommentar_ekstern_nb, regjering_statssekretaerer.kommentar_ekstern_en\n" +
                "                FROM (politikere INNER JOIN regjering_statssekretaerer ON politikere.id =\n" +
                "                  regjering_statssekretaerer.person) INNER JOIN regjering_departement ON regjering_statssekretaerer.departement =\n" +
                "                 regjering_departement.id "
                + (condition != null ? " " + condition : "");

                sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setFraaar(rader[i].get("startday") + "-" + rader[i].get("startmonth") + "-" + rader[i].get("startyear"));
            norskepolitikere[i].setTilaar(rader[i].get("sluttday") + "-" + rader[i].get("sluttmonth") + "-" + rader[i].get("sluttyear"));
           if(rader[i].get("slutt") !=null){
           // norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("slutt"));
           }
            if (this.engelsk) {
                norskepolitikere[i].setStilling((String) rader[i].get("stilling_avvik_en"));
                norskepolitikere[i].setEintaltekst((String) rader[i].get("navn_en"));
            }
            else {
                norskepolitikere[i].setStilling((String) rader[i].get("stilling_avvik_nb"));
                norskepolitikere[i].setEintaltekst((String) rader[i].get("navn_nb"));
            }
       }
        return norskepolitikere;
    }


    /*personlig*/

    private PolitikerBiografi[] getPersonligInfo(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /* this one is old
        String sqlSelect_old = "SELECT     p_hist.f_sted, p_hist.far_stilling, p_hist.far_fornamn, p_hist.far_etternamn, \n" +
                "                      p_hist.far_f_dato, p_hist.far_f_maaned, p_hist.far_f_aar, p_hist.far_d_dato, \n" +
                "                      p_hist.far_d_maaned, p_hist.far_d_aar, p_hist.mor_fornamn, p_hist.mor_etternamn, \n" +
                "                      p_hist.mor_f_dato, p_hist.mor_f_maaned, p_hist.mor_f_aar, p_hist.mor_d_dato, \n" +
                "                      p_hist.mor_d_maaned, p_hist.mor_d_aar, t_felles_person.person_id, t_felles_person.fodt ,t_felles_person.fodt_tekstformat, \n" +
                "                      t_felles_person.faar, t_felles_person.doed, t_felles_person.dodsaar, t_felles_person.kjoenn, \n" +
                "                      p_hist.mor_stilling, p_hist.p_etternamn, p_hist.p_fornamn, p_hist.p_stilling, \n" +
                "                      p_hist.p_f_dato, p_hist.p_f_maaned, p_hist.p_f_aar, p_hist.stortingsaktivitet, \n" +
                "                      p_hist.Slektskap, p_hist.d_aar, p_hist.d_maaned, p_hist.d_dato, p_hist.d_sted, \n" +
                "                      p_hist.p_mor_d_aar, p_hist.p_mor_d_maaned, p_hist.p_mor_d_dato, p_hist.p_mor_f_aar, \n" +
                "                      p_hist.p_mor_f_maaned, p_hist.p_mor_f_dato, p_hist.p_mor_etternamn, p_hist.p_mor_fornamn, \n" +
                "                      p_hist.p_mor_stilling, p_hist.p_far_d_aar, p_hist.p_far_d_maaned, p_hist.p_far_d_dato, \n" +
                "                      p_hist.p_far_f_aar, p_hist.p_far_f_maaned, p_hist.p_far_f_dato, p_hist.p_far_etternamn, \n" +
                "                      p_hist.p_far_fornamn, p_hist.p_far_stilling, p_hist.p_g_aar, p_hist.p_g_maaned, \n" +
                "                      p_hist.p_g_dato, p_hist.p_g_sted\n" +
                "FROM         t_felles_person_familie_hist_before1945 as p_hist LEFT OUTER JOIN\n" +
                "                      t_felles_person ON p_hist.person_id = t_felles_person.person_id "
                + (condition != null ? " " + condition : "");
         */

        /*this one is new replaced by the below query*/
/*
         String sqlSelect = "SELECT  p_hist.f_sted, p_hist.far_stilling, p_hist.far_fornamn, p_hist.far_etternamn, \n" +
                 "                                  p_hist.far_f_aar, p_hist.far_d_aar, p_hist.mor_fornamn, p_hist.mor_etternamn, \n" +
                 "                                      p_hist.mor_f_aar,  p_hist.mor_d_aar, t_felles_person.person_id, t_felles_person.fodt ,t_felles_person.fodt_tekstformat, \n" +
                 "                                      t_felles_person.faar, t_felles_person.doed, t_felles_person.dodsaar, t_felles_person.kjoenn, \n" +
                 "                                      p_hist.mor_stilling, p_hist.slektskap\n" +
                 "                FROM         t_felles_person_familie_hist as p_hist LEFT OUTER JOIN\n" +
                 "                                      t_felles_person ON p_hist.person_id = t_felles_person.person_id "
                + (condition != null ? " " + condition : "");
        */

        String sqlSelect = " SELECT     B1095_politikere.f_sted, B1095_politikere.far_stilling, B1095_politikere.far_fornamn, B1095_politikere.far_etternamn, \n" +
                "                      B1095_politikere.far_f_dato, B1095_politikere.far_f_maaned, B1095_politikere.far_f_aar, B1095_politikere.far_d_dato, \n" +
                "                      B1095_politikere.far_d_maaned, B1095_politikere.far_d_aar, B1095_politikere.mor_fornamn, B1095_politikere.mor_etternamn, \n" +
                "                      B1095_politikere.mor_f_dato, B1095_politikere.mor_f_maaned, B1095_politikere.mor_f_aar, B1095_politikere.mor_d_dato, \n" +
                "                      B1095_politikere.mor_d_maaned, B1095_politikere.mor_d_aar, politikere.id, politikere.fodt AS fodt_tekstformat, \n" +
                "                      YEAR(politikere.fodt) AS faar, politikere.doed, YEAR(politikere.doed) AS dodsaar, politikere.kjoenn, \n" +
                "                      B1095_politikere.mor_stilling, B1095_politikere.p_etternamn, B1095_politikere.p_fornamn, B1095_politikere.p_stilling, \n" +
                "                      B1095_politikere.p_f_dato, B1095_politikere.p_f_maaned, B1095_politikere.p_f_aar, B1095_politikere.Stortingsaktivitet, \n" +
                "                      B1095_politikere.Slektskap, B1095_politikere.d_aar, B1095_politikere.d_maaned, B1095_politikere.d_dato, B1095_politikere.d_sted, \n" +
                "                      B1095_politikere.p_mor_d_aar, B1095_politikere.p_mor_d_maaned, B1095_politikere.p_mor_d_dato, B1095_politikere.p_mor_f_aar, \n" +
                "                      B1095_politikere.p_mor_f_maaned, B1095_politikere.p_mor_f_dato, B1095_politikere.p_mor_etternamn, B1095_politikere.p_mor_fornamn, \n" +
                "                      B1095_politikere.p_mor_stilling, B1095_politikere.p_far_d_aar, B1095_politikere.p_far_d_maaned, B1095_politikere.p_far_d_dato, \n" +
                "                      B1095_politikere.p_far_f_aar, B1095_politikere.p_far_f_maaned, B1095_politikere.p_far_f_dato, B1095_politikere.p_far_etternamn, \n" +
                "                      B1095_politikere.p_far_fornamn, B1095_politikere.p_far_stilling, B1095_politikere.p_g_aar, B1095_politikere.p_g_maaned, \n" +
                "                      B1095_politikere.p_g_dato, B1095_politikere.p_g_sted\n" +
                "FROM         B1095_politikere  LEFT OUTER JOIN\n" +
                "                      politikere ON B1095_politikere.person = politikere.id "
                + (condition != null ? " " + condition : "");

         sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setFodtSted((String) rader[i].get("f_sted"));
            norskepolitikere[i].setKjoen((Integer) rader[i].get("kjoenn"));
            norskepolitikere[i].setFar_forNavn((String) rader[i].get("far_fornamn"));
           norskepolitikere[i].setFar_etterNavn((String) rader[i].get("far_etternamn"));
            norskepolitikere[i].setMor_forNavn((String) rader[i].get("mor_fornamn"));
            norskepolitikere[i].setMor_etterNavn((String) rader[i].get("mor_etternamn"));
            norskepolitikere[i].setFar_stilling((String) rader[i].get("far_stilling"));
            norskepolitikere[i].setMor_stilling((String) rader[i].get("mor_stilling"));
            if(rader[i].get("far_f_aar")!=null){
            norskepolitikere[i].setFar_f_aar((Integer) rader[i].get("far_f_aar"));
            }
            if(rader[i].get("far_d_aar")!=null){
            norskepolitikere[i].setFar_d_aar((Integer) rader[i].get("far_d_aar"));
            }
            if(rader[i].get("mor_f_aar")!=null){
            norskepolitikere[i].setMor_f_aar((Integer) rader[i].get("mor_f_aar"));
            }
            if(rader[i].get("mor_d_aar")!=null){
            norskepolitikere[i].setMor_d_aar((Integer) rader[i].get("mor_d_aar"));
            }

             /*Object fodt_dato = rader[i].get("fodt");
		    java.text.DateFormat dfYMD_fodt = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		    java.text.DateFormat dfDMY_fodt = new java.text.SimpleDateFormat("dd-MM-yyyy") ;

		    try {
			 if(rader[i].get("fodt")!=null){
            norskepolitikere[i].setFodt(dfDMY_fodt.format(dfYMD_fodt.parse(String.valueOf(fodt_dato))));
             }else{
                 norskepolitikere[i].setFodt((String) rader[i].get("Faar"));
             }

		} catch (ParseException e) {
			e.printStackTrace();
		} */

            if(rader[i].get("fodt")!=null){
                norskepolitikere[i].setFodt((String) rader[i].get("fodt_tekstformat"));
            }else{
                norskepolitikere[i].setFodt(Integer.toString ((Integer) rader[i].get("Faar")));
            }


       }
        return norskepolitikere;
    }

    /*utdyrke utdanning og  Yrke*/

     private PolitikerBiografi[] getUtdyrke(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT person_id, postnummer, type, type_nsd, aktivitet,  fra_aar, til_aar, kode2, merknad \n" +
                "FROM         t_storting_utdyrke "
                + (condition != null ? " " + condition : "");
           */

        String sqlSelect = " SELECT  Person, Postnummer, type, type_nsd,    Fra_aar, Til_aar, Kode2, Merknad, Institusjon as aktivitet \n" +
                "FROM         aktivitet_1905 "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setType((Integer) rader[i].get("type"));
            if(rader[i].get("Kode2")!=null){
                norskepolitikere[i].setKode2((Integer) rader[i].get("Kode2"));
            }
            if(rader[i].get("Fra_aar")!=null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
            }
            if(rader[i].get("Til_aar")!=null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
            }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("aktivitet"));
            norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
            //norskepolitikere[i].setStilling((String) rader[i].get("aktivitet"));
        }
        return norskepolitikere;
    }
    /*utdyrke utdanning og  Yrke   1945 */
    private PolitikerBiografi[] getUtdyrke_1945(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT person_id, postnummer, type, type_nsd, aktivitet,  fra_aar, til_aar, kode2, merknad \n" +
                "FROM         t_storting_utdyrke "
                + (condition != null ? " " + condition : "");
           */
        String sqlSelect = " select stortingsid.person AS person_id, aktivitet.person AS personid,type, type_nsd,aktivitet,  fraaar, tilaar, tilleggsoppl  from aktivitet\n" +
                "inner join stortingsid on stortingsid.stortingsid = aktivitet.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setType((Integer) rader[i].get("type"));
            if(rader[i].get("kode2")!=null){
                //norskepolitikere[i].setKode2((Integer) rader[i].get("Kode2"));
            }
            if(rader[i].get("fraaar")!=null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fraaar"));
            }
            if(rader[i].get("tilaar")!=null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("tilaar"));
            }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("aktivitet"));
            norskepolitikere[i].setMerknad((String) rader[i].get("tilleggsoppl"));
            //norskepolitikere[i].setStilling((String) rader[i].get("aktivitet"));
        }
        return norskepolitikere;
    }


    /*kompol*/

    private PolitikerBiografi[] getKomPol(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
        String sqlSelect = "SELECT     t_storting_komfylk.person_id, t_storting_komfylk.postnummer, t_storting_komfylk.type, t_storting_komfylk.stillingkode, \n" +
                "                      t_storting_komfylk.institusjon, t_storting_komfylk.institusjonkode, t_storting_komfylk.perdef, t_storting_komfylk.periodekode, \n" +
                "                      t_storting_komfylk.fra_aar, t_storting_komfylk.til_aar, t_storting_komfylk.merknad, t_storting_komfylkstilling.eintaltekst, \n" +
                "                      t_storting_komfylkinstitusjon.eintaltekst AS Expr2, t_storting_komfylkperioder.eintaltekst AS Expr3\n" +
                "FROM         t_storting_komfylk INNER JOIN\n" +
                "                      t_storting_komfylkstilling ON t_storting_komfylk.stillingkode = t_storting_komfylkstilling.kode INNER JOIN\n" +
                "                      t_storting_komfylkinstitusjon ON t_storting_komfylk.institusjonkode = t_storting_komfylkinstitusjon.kode INNER JOIN\n" +
                "                      t_storting_komfylkperioder ON t_storting_komfylk.periodekode = t_storting_komfylkperioder.kode  "
                + (condition != null ? " " + condition : "");
           */
        String sqlSelect = " SELECT   komfylk_1905.Person, komfylk_1905.Postnummer, komfylk_1905.type, komfylk_1905.Stilling AS stillingkode, \n" +
                "           komfylk_1905.Institusjonsnavn AS Institusjon, komfylk_1905.Institusjon AS institusjonkode, komfylk_1905.Perdef, komfylk_1905.periode AS Periodekode, \n" +
                "           komfylk_1905.fra AS Fra_aar, komfylk_1905.til AS Til_aar, komfylk_1905.Merknad, _komfylk_stilling.navn AS Eintaltekst, \n" +
                "           _komfylk_institusjon.navn AS Expr2, _komfylk_periode.navn AS Expr3\n" +
                "           FROM  komfylk_1905 INNER JOIN\n" +
                "           _komfylk_stilling ON komfylk_1905.Stilling = _komfylk_stilling.kode INNER JOIN\n" +
                "           _komfylk_institusjon ON komfylk_1905.Institusjon = _komfylk_institusjon.kode INNER JOIN\n" +
                "           _komfylk_periode ON komfylk_1905.Periode = _komfylk_periode.kode "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
           
             norskepolitikere[i].setEintaltekst((String) rader[i].get("Eintaltekst"));
             norskepolitikere[i].setExpr2((String) rader[i].get("Expr2"));
             norskepolitikere[i].setExpr3((String) rader[i].get("Expr3"));
            if(rader[i].get("Perdef") !=null){
            norskepolitikere[i].setPeriode((Integer) rader[i].get("Perdef"));
            }
             if(rader[i].get("Periodekode") !=null){
            norskepolitikere[i].setPeriodekode((Integer) rader[i].get("Periodekode"));
             }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
             norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
              if(rader[i].get("Fra_aar")!=null){
        norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
        }
          if(rader[i].get("Til_aar")!=null){
            norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
          }
       }
        return norskepolitikere;
    }

     /*kompol 1945*/

    private PolitikerBiografi[] getKomPol_1945(String condition, List<Serializable> values) throws Exception {
        PolitikerBiografi[] norskepolitikere = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = " SELECT   stortingsid.person AS person_id, komfylk.person AS personid,  komfylk.type, komfylk.stilling AS stillingkode, \n" +
                "         komfylk.stedsnavn AS sted, komfylk.institusjon AS institusjonkode,  komfylk.periode AS komperiodekode, \n" +
                "         komfylk.fra AS fraaar, komfylk.til AS tilaar, komfylk.tilleggsoppl, komfylk_stilling.navn AS eintaltekst, \n" +
                "         komfylk_institusjon.navn AS Expr2, komfylk_periode.navn  AS Expr3\n" +
                "         FROM  komfylk INNER JOIN\n" +
                "         komfylk_stilling ON komfylk.stilling = komfylk_stilling.id INNER JOIN\n" +
                "         komfylk_institusjon ON komfylk.institusjon = komfylk_institusjon.id INNER JOIN\n" +
                "         komfylk_periode ON komfylk.periode = komfylk_periode.id\n" +
                "         inner join stortingsid on stortingsid.stortingsid = komfylk.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setEintaltekst((String) rader[i].get("eintaltekst"));
            norskepolitikere[i].setExpr2((String) rader[i].get("Expr2"));
            norskepolitikere[i].setExpr3((String) rader[i].get("Expr3"));
            if(rader[i].get("Perdef") !=null){
                norskepolitikere[i].setPeriode((Integer) rader[i].get("Perdef"));
            }
            if(rader[i].get("komperiodekode") !=null){
                norskepolitikere[i].setPeriodekode((Integer) rader[i].get("komperiodekode"));
            }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("sted"));
            norskepolitikere[i].setMerknad((String) rader[i].get("tilleggsoppl"));
            if(rader[i].get("fraaar")!=null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fraaar"));
            }
            if(rader[i].get("tilaar")!=null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("tilaar"));
            }
        }
        return norskepolitikere;
    }

    /*fylkpol*/
      private PolitikerBiografi[] getFylPol(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
          /*
        String sqlSelect = "SELECT     t_storting_komfylk.person_id, t_storting_komfylk.postnummer, t_storting_komfylk.type, t_storting_komfylk.stillingkode, \n" +
                "                      t_storting_komfylk.institusjon, t_storting_komfylk.institusjonkode, t_storting_komfylk.perdef, t_storting_komfylk.periodekode, \n" +
                "                      t_storting_komfylk.fra_aar, t_storting_komfylk.til_aar, t_storting_komfylk.merknad,  \n" +
                "                      t_storting_komfylkstilling.eintaltekst, t_storting_komfylkinstitusjon.eintaltekst AS Expr2,  t_storting_komfylkperioder.eintaltekst AS Expr3\n" +
                "FROM         t_storting_komfylk INNER JOIN\n" +
                               " t_storting_komfylkstilling ON t_storting_komfylk.stillingkode = t_storting_komfylkstilling.kode INNER JOIN \n" +
                                "      t_storting_komfylkinstitusjon ON t_storting_komfylk.institusjonkode = t_storting_komfylkinstitusjon.kode INNER JOIN \n" +
                "                      t_storting_komfylkperioder ON t_storting_komfylk.periodekode = t_storting_komfylkperioder.kode  "
                + (condition != null ? " " + condition : "");
            */
          String sqlSelect =  " SELECT   komfylk_1905.person, komfylk_1905.Postnummer, komfylk_1905.type, komfylk_1905.stillingsnavn AS Stilling,  \n" +
                  "         komfylk_1905.institusjonsnavn AS Institusjon, komfylk_1905.Perdef, komfylk_1905.periode AS Periodekode, komfylk_1905.fra AS Fra_aar, \n" +
                  "         komfylk_1905.til AS Til_aar, komfylk_1905.Merknad, _komfylk_periode.navn AS Expr3\n" +
                  "         FROM  komfylk_1905 INNER JOIN\n" +
                  "         _komfylk_periode ON komfylk_1905.periode = _komfylk_periode.kode "
                  + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

             //norskepolitikere[i].setEintaltekst((String) rader[i].get("eintaltekst"));
             //norskepolitikere[i].setExpr2((String) rader[i].get("Expr2"));
             norskepolitikere[i].setExpr3((String) rader[i].get("Expr3"));
            if(rader[i].get("Perdef") !=null){
            norskepolitikere[i].setPeriode((Integer) rader[i].get("Perdef"));
            }
             if(rader[i].get("Periodekode") !=null){
            norskepolitikere[i].setPeriodekode((Integer) rader[i].get("Periodekode"));
             }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
             norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
              if(rader[i].get("Fra_aar")!=null){
        norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
        }
          if(rader[i].get("Til_aar")!=null){
            norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
          }
       }
        return norskepolitikere;
    }

    /*fylkpol 1945*/
    private PolitikerBiografi[] getFylPol_1945(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = " SELECT   stortingsid.person AS person_id, komfylk.person,  komfylk.type, komfylk.stilling AS stillingkode, \n" +
                "         komfylk.sted, komfylk.institusjon AS institusjonkode,  komfylk.periode AS komperiodekode, \n" +
                "         komfylk.fra AS fraaar, komfylk.til AS tilaar, komfylk.tilleggsoppl, komfylk_stilling.navn AS eintaltekst, \n" +
                "         komfylk_institusjon.navn  AS Expr2, komfylk_periode.navn AS Expr3\n" +
                "         FROM  komfylk INNER JOIN\n" +
                "         komfylk_stilling ON komfylk.stilling = komfylk_stilling.id INNER JOIN\n" +
                "         komfylk_institusjon ON komfylk.institusjon = komfylk_institusjon.id INNER JOIN\n" +
                "         komfylk_periode ON komfylk.periode = komfylk_periode.id\n" +
                "         inner join stortingsid on stortingsid.stortingsid = komfylk.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {

            norskepolitikere[i] = new PolitikerBiografi();

            norskepolitikere[i].setEintaltekst((String) rader[i].get("eintaltekst"));
            norskepolitikere[i].setExpr2((String) rader[i].get("Expr2"));
            norskepolitikere[i].setExpr3((String) rader[i].get("Expr3"));
            if(rader[i].get("Perdef") !=null){
                norskepolitikere[i].setPeriode((Integer) rader[i].get("Perdef"));
            }
            if(rader[i].get("komperiodekode") !=null){
                norskepolitikere[i].setPeriodekode((Integer) rader[i].get("komperiodekode"));
            }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("sted"));
            norskepolitikere[i].setMerknad((String) rader[i].get("tilleggsoppl"));
            if(rader[i].get("fraaar")!=null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fraaar"));
            }
            if(rader[i].get("tilaar")!=null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("tilaar"));
            }
        }
        return norskepolitikere;
    }

    /*offverv*/
     private PolitikerBiografi[] getPersonStortingsOffVerv(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT DISTINCT person_id, postnummer, stilling, institusjon, fra_aar, til_aar, merknad\n" +
                "FROM         t_storting_offverv "
                + (condition != null ? " " + condition : "");
            */
         String sqlSelect = " SELECT    Person, Postnummer, Stilling, Institusjon, Fra_aar, Til_aar, Merknad\n" +
                 "FROM         verv_offentlige_1905 "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            if(rader[i].get("Fra_aar")!=null){
        norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
        }
          if(rader[i].get("Til_aar")!=null){
            norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
          }
            norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
       norskepolitikere[i].setStilling((String) rader[i].get("Stilling"));
       norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
       }
        return norskepolitikere;
    }

    /*offverv 1945*/
    private PolitikerBiografi[] getPersonStortingsOffVerv_1945(String condition, List<Serializable> values) throws Exception {
        PolitikerBiografi[] norskepolitikere = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = " select  stortingsid.person, stilling, institusjon, fraaar, tilaar, tilleggsoppl \n" +
                "        from verv_offentlige inner join stortingsid on stortingsid.stortingsid = verv_offentlige.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];
        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
            if(rader[i].get("fraaar")!=null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fraaar"));
            }
            if(rader[i].get("tilaar")!=null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("tilaar"));
            }
            norskepolitikere[i].setMerknad((String) rader[i].get("tilleggsoppl"));
            norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            norskepolitikere[i].setInstitusjon((String) rader[i].get("institusjon"));
        }
        return norskepolitikere;
    }


/* partiverv */

     private PolitikerBiografi[] getPersonStortingsPartiverv(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
        String sqlSelect = "SELECT person_id, postnummer, stilling, institusjon, fra_aar, til_aar, merknad, vervkode\n" +
                "FROM        t_storting_partiverv "
                + (condition != null ? " " + condition : "");
        */
         String sqlSelect = "  SELECT     \tPerson, Postnummer, Stilling, Institusjon, fra AS Fra_aar, til AS Til_aar, Merknad, verv AS vervkode\n" +
                 "FROM         verv_parti_1905 "
                 + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
             if(rader[i].get("Fra_aar") != null){
            norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
        }
        if(rader[i].get("Til_aar") != null){
         norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
        }
         norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
          norskepolitikere[i].setStilling((String) rader[i].get("Stilling"));
          norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
         }
        return norskepolitikere;
    }
    /* partiverv 1945*/

    private PolitikerBiografi[] getPersonStortingsPartiverv_1945(String condition, List<Serializable> values) throws Exception {
        PolitikerBiografi[] norskepolitikere = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "select stortingsid.person, stilling, institusjon, fraaar, tilaar, tilleggsoppl, verv AS vervkode \n" +
                "       from  verv_parti                                            \n" +
                "       inner join stortingsid on stortingsid.stortingsid = verv_parti.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
            if(rader[i].get("fraaar") != null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fraaar"));
            }
            if(rader[i].get("tilaar") != null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("tilaar"));
            }
            norskepolitikere[i].setInstitusjon((String) rader[i].get("institusjon"));
            norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            norskepolitikere[i].setMerknad((String) rader[i].get("merknad"));
        }
        return norskepolitikere;
    }

    /*orgverv*/
    private PolitikerBiografi[] getPersonStortingsOrgVerv(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
        String sqlSelect = "SELECT person_id, postnummer, stilling, institusjon, fra_aar, til_aar, merknad\n" +
                "FROM         t_storting_orgverv "
                + (condition != null ? " " + condition : "");
        */
        String sqlSelect = " SELECT   Person, Postnummer, Stilling, Institusjon, Fra_aar, Til_aar, Merknad\n" +
                "FROM         verv_organisasjoner_1905 "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
              if(rader[i].get("Fra_aar") != null){
            norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
              }
             if(rader[i].get("Til_aar") != null){
            norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
             }
            norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
           norskepolitikere[i].setStilling((String) rader[i].get("Stilling"));
                norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
       }
        return norskepolitikere;
    }

    /*orgverv 1945*/
    private PolitikerBiografi[] getPersonStortingsOrgVerv_1945(String condition, List<Serializable> values) throws Exception {
        PolitikerBiografi[] norskepolitikere = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = " SELECT   stortingsid.person, stilling, institusjon, fraaar, tilaar, tilleggsoppl\n" +
                "         from verv_organisasjoner \n" +
                "         inner join stortingsid on stortingsid.stortingsid = verv_organisasjoner.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();
            if(rader[i].get("fraaar") != null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fraaar"));
            }
            if(rader[i].get("tilaar") != null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("tilaar"));
            }
            norskepolitikere[i].setMerknad((String) rader[i].get("tilleggsoppl"));
            norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            norskepolitikere[i].setInstitusjon((String) rader[i].get("institusjon"));
        }
        return norskepolitikere;
    }

    /*admverv*/
    private PolitikerBiografi[] getPersonStortingsAdmVerv(String condition, List<Serializable> values) throws Exception {
           // tabell som returneres fra denne metoden.
           PolitikerBiografi[] norskepolitikere = null;
           // resultat fra sql-sporring.
           Result result = null;
           // objekt som brukes til aa utfore sql-sporring.
           SqlCommandBean sqlCB = new SqlCommandBean();
           // inneholder data fra sql-sporring.
           SortedMap[] rader = null;
           // SQL-sporring.
        /*
           String sqlSelect = "SELECT DISTINCT person_id, postnummer, stilling, institusjon, fra_aar, til_aar, merknad\n" +
                   "FROM         t_storting_admverv "
                   + (condition != null ? " " + condition : "");
            */
        String sqlSelect = " SELECT   Person, Postnummer, Stilling, Institusjon, Fra_aar, Til_aar, Merknad\n" +
                "FROM         verv_administrative_1905 "
                + (condition != null ? " " + condition : "");
           sqlCB.setConnection(this.conn);
           sqlCB.setSqlValue(sqlSelect); //sporring
           sqlCB.setValues(values); //parameter
           result = sqlCB.executeQuery();
           rader = result.getRows();
           norskepolitikere = new PolitikerBiografi[rader.length];

           for (int i = 0; i < rader.length; i++) {
               norskepolitikere[i] = new PolitikerBiografi();

              if(rader[i].get("Fra_aar")!=null){
        norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
        }
          if(rader[i].get("Til_aar")!=null){
            norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("Til_aar"));
          }
               norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
                norskepolitikere[i].setStilling((String) rader[i].get("Stilling"));
                norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
          }
           return norskepolitikere;
       }

    /*admverv 1945*/
    private PolitikerBiografi[] getPersonStortingsAdmVerv_1945(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
           String sqlSelect = "SELECT DISTINCT person_id, postnummer, stilling, institusjon, fra_aar, til_aar, merknad\n" +
                   "FROM         t_storting_admverv "
                   + (condition != null ? " " + condition : "");
            */
        String sqlSelect =  "SELECT   verv_administrative.person, stilling, institusjon, fraaar, tilaar, tilleggsoppl\n" +
                "         from verv_administrative\n" +
                "         inner join stortingsid on stortingsid.stortingsid = verv_administrative.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();

            if(rader[i].get("fraaar")!=null){
                norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("fraaar"));
            }
            if(rader[i].get("tilaar")!=null){
                norskepolitikere[i].setTilaarInteger((Integer) rader[i].get("tilaar"));
            }
            norskepolitikere[i].setMerknad((String) rader[i].get("tilleggsoppl"));
            norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            norskepolitikere[i].setInstitusjon((String) rader[i].get("institusjon"));
        }
        return norskepolitikere;
    }

    /*litteratur*/
     private PolitikerBiografi[] getPersonStortingsLitteratur(String condition, List<Serializable> values) throws Exception {
           // tabell som returneres fra denne metoden.
           PolitikerBiografi[] norskepolitikere = null;
           // resultat fra sql-sporring.
           Result result = null;
           // objekt som brukes til aa utfore sql-sporring.
           SqlCommandBean sqlCB = new SqlCommandBean();
           // inneholder data fra sql-sporring.
           SortedMap[] rader = null;
           // SQL-sporring.
         /*
           String sqlSelect = "SELECT DISTINCT litteratur_id, person_id, postnummer, type, stilling, forfatter, tittel, forlag, utgsted, utgaar,  merknad\n" +
                   "FROM         t_storting_litteratur "
                   + (condition != null ? " " + condition : "");
            */
         String sqlSelect = " SELECT     Person, Postnummer, type, Stilling, Institusjon, Fra_aar, Til_aar, Merknad\n" +
                 "FROM         litteratur_1905 "
                 + (condition != null ? " " + condition : "");
           sqlCB.setConnection(this.conn);
           sqlCB.setSqlValue(sqlSelect); //sporring
           sqlCB.setValues(values); //parameter
           result = sqlCB.executeQuery();
           rader = result.getRows();
           norskepolitikere = new PolitikerBiografi[rader.length];

           for (int i = 0; i < rader.length; i++) {
               norskepolitikere[i] = new PolitikerBiografi();

            
               //if(rader[i].get("litteratur_id")!=null){
               if(rader[i].get("forfatter")!=null){
               norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
               norskepolitikere[i].setStilling((String) rader[i].get("forfatter"));
               }
               else if(rader[i].get("stilling")==null ){
             //else if(rader[i].get("litteratur_id")==null ){
               norskepolitikere[i].setInstitusjon((String) rader[i].get("Institusjon"));
               norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
               }    
                if(rader[i].get("Fra_aar")!=null){
            norskepolitikere[i].setFraaarInteger((Integer) rader[i].get("Fra_aar"));
            }
            if(rader[i].get("utgsted")!=null){
                //norskepolitikere[i].setUtgsted((String) rader[i].get("utgsted"));
             }
             if(rader[i].get("forlag")!=null){
                //norskepolitikere[i].setForlag((String) rader[i].get("forlag"));
             }

               norskepolitikere[i].setMerknad((String) rader[i].get("Merknad"));
          }
           return norskepolitikere;
       }

    /*litteratur 1945*/
    private PolitikerBiografi[] getPersonStortingsLitteratur_1945(String condition, List<Serializable> values) throws Exception {
        // tabell som returneres fra denne metoden.
        PolitikerBiografi[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         /*
           String sqlSelect = "SELECT DISTINCT litteratur_id, person_id, postnummer, type, stilling, forfatter, tittel, forlag, utgsted, utgaar,  merknad\n" +
                   "FROM         t_storting_litteratur "
                   + (condition != null ? " " + condition : "");
            */
        String sqlSelect = " SELECT  stortingsid.person, type, forfatter, tittel, forlag, utgsted, utgaar, tilleggsoppl\n" +
                "        from litteratur\n" +
                "        inner join stortingsid on stortingsid.stortingsid = litteratur.person "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new PolitikerBiografi[rader.length];

        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new PolitikerBiografi();


            //if(rader[i].get("litteraturid")!=null){
                norskepolitikere[i].setInstitusjon((String) rader[i].get("tittel"));
                norskepolitikere[i].setStilling((String) rader[i].get("forfatter"));
            //}
            /*
            else if(rader[i].get("litteraturid")==null ){
                norskepolitikere[i].setInstitusjon((String) rader[i].get("tittel"));
                norskepolitikere[i].setStilling((String) rader[i].get("stilling"));
            }*/
            if(rader[i].get("utgaar")!=null){
                norskepolitikere[i].setFraaar((String) rader[i].get("utgaar"));
            }
            if(rader[i].get("utgsted")!=null){
                norskepolitikere[i].setUtgsted((String) rader[i].get("utgsted"));
            }
            if(rader[i].get("forlag")!=null){
                norskepolitikere[i].setForlag((String) rader[i].get("forlag"));
            }

            norskepolitikere[i].setMerknad((String) rader[i].get("tilleggsoppl"));


        }
        return norskepolitikere;
    }


    /*

    Last ned filer startert FRA HER LASTNED FILER......................

     */




    public List<PolitikerBiografi> lastNedPersonInfo(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar, List partikode, String sortresult) throws Exception {
        String condition = "HAVING t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL)  AND (t_storting_person_hist.sup_nr is not null or t_storting_person_hist.rep_nr is not null)  ";
          if(navn != null){
               condition += " AND t_felles_person.navn LIKE ?  ";
               }
        if(bs !=null &&  !bs.equals("") && bs.length>0 ){
            condition += " AND  (    ";
            condition += " t_felles_person.navn LIKE ?    ";
            for(int i=1; i<bs.length; i++){
                if(bs[i]!=""){
                    condition += " OR t_felles_person.navn LIKE ?  ";
                }
            }
            condition += "  )    ";
        }
        if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
            condition += " AND  (    ";
            condition += " t_storting_person_hist.stortingperiode_kode = ?    ";
            for(int i=1; i<aar.size(); i++){
                if(aar.get(i)!=" "){
                    condition += " OR t_storting_person_hist.stortingperiode_kode = ?  ";
                }
            }
            condition += "  )    ";
        }
          if(st !=null && st.equalsIgnoreCase("storting")){
           condition += " AND (t_storting_person_hist.stortingperiode_kode) >=-1 ";
          }
           if(sr !=null && sr.equalsIgnoreCase("statsraad")){
           condition += " AND (t_felles_statsraader.person_id) is not null ";
          }
           if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
           condition += " AND (t_felles_statssekretaerer.person_id) is not null ";
          }

        //partikode
        if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
            condition += " AND  (    ";
            condition += " t_storting_person_hist.parti_kode = ?    ";
            for(int i=1; i<partikode.size(); i++){
                if(partikode.get(i)!=" "){
                    condition += " OR t_storting_person_hist.parti_kode = ?  ";
                }
            }
            condition += "  )    ";
        }

        if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

            //length = 1
            if( periode.size() ==1 && periode.get(0).equals("1814")){
                condition += " AND  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==1 && periode.get(0).equals("1903")){
                condition += "AND (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52  ";
            }
            else if( periode.size() ==1 && periode.get(0).equals("1945")){
                condition += " AND  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }

            //length= 2
            for(int i=0; i<periode.size(); i++){
                if( periode.size() ==2 && periode.get(0).equals("1814")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==2 && periode.get(0).equals("1903")){
                    condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==2 && periode.get(0).equals("1945")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==2 && periode.get(1).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
                }
                else if( periode.size() ==2 && periode.get(1).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
                }
                else if( periode.size() ==2 && periode.get(1).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
                }
            }

            //length= 3
            for(int i=0; i<periode.size(); i++){
                if( periode.size() ==3 && periode.get(0).equals("1814")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==3 && periode.get(0).equals("1903")){
                    condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==3 && periode.get(0).equals("1945")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==3 && periode.get(1).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==3 && periode.get(1).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==3 && periode.get(1).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==3 && periode.get(2).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
                }
                else if( periode.size() ==3 && periode.get(2).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
                }
                else if( periode.size() ==3 && periode.get(2).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
                }
            }
        }

        if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY t_felles_person.navn ";}
        if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY t_felles_person.fornavn ";}
        if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY t_felles_person.faar DESC ";}
        if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY t_felles_person.faar ASC ";}

          List values = new ArrayList();

       if(navn !=null){
        values.add("%"+navn+"%");
       }

        if(bs !=null && !bs.equals("") && bs.length>0){
            values.add(  bs[0]+"%"  );
            for(int i=1; i<bs.length; i++){
                if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
            }
        }

        if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
            values.add(  aar.get(0)+" "  );
            for(int i=1; i<aar.size(); i++){
                if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
            }
        }
        if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
            values.add(  partikode.get(0)+" "  );
            for(int i=1; i<partikode.size(); i++){
                if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
            }
        }

        return this.getAlleNorskePolitikere_personinfo(condition, values);
    }



    private List<PolitikerBiografi> getAlleNorskePolitikere_personinfo(String condition, List values) throws Exception {

        ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.initialer, t_felles_person.fodt, t_felles_person.faar,\n" +
                " t_felles_person.doed, t_felles_person.dodsaar, t_felles_statsraader.person_id AS sr_person_id, t_felles_statssekretaerer.person_id AS ss_person_id\n" +
                " ,t_storting_person_hist.rep_nr, t_storting_person_hist.sup_nr,t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_person_hist.valkrinsnavn,\n" +
                " t_storting_person_hist.stilling, t_felles_partinavn.eintaltekst AS Partitekst\n" +
                "                     FROM  t_felles_person LEFT OUTER JOIN\n" +
                "                     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                "                     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "                     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                "                     LEFT OUTER JOIN t_felles_partinavn ON t_felles_partinavn.kode = t_storting_person_hist.parti_kode\n" +
                "                     LEFT OUTER JOIN\n" +
                "                         t_storting_stortingsperioder_before1945 ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder_before1945.kode\n" +
                "                     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.initialer, t_felles_person.fodt, t_felles_person.faar,\n" +
                " t_felles_person.doed, t_felles_person.dodsaar, t_felles_statsraader.person_id, t_felles_statssekretaerer.person_id,t_storting_person_hist.stortingperiode_kode,\n" +
                " t_storting_person_hist.rep_nr, t_storting_person_hist.sup_nr,\n" +
                "                          t_storting_person_hist.parti_kode, t_felles_partinavn.eintaltekst,t_storting_stortingsperioder_before1945.fleirtaltekst,\n" +
                "                          t_storting_person_hist.valkrinsnavn,t_storting_person_hist.stilling   "
                + (condition != null ? " " + condition : "");
       sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();

        for (int i = 0; i < rader.length; i++) {
            PolitikerBiografi personinfo = new PolitikerBiografi();
            personinfo.setEtterNavn((String) rader[i].get("navn"));
            personinfo.setForNavn((String) rader[i].get("fornavn"));
              personinfo.setInitialer((String) rader[i].get("initialer"));


            Object fodt_dato = rader[i].get("fodt");
		    java.text.DateFormat dfYMD_fodt = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		    java.text.DateFormat dfDMY_fodt = new java.text.SimpleDateFormat("dd-MM-yyyy") ;
		    try {
			 if(rader[i].get("fodt")!=null){
            personinfo.setFodt(dfDMY_fodt.format(dfYMD_fodt.parse(String.valueOf(fodt_dato))));
             }else{
                 if(rader[i].get("faar") !=null){personinfo.setFodt(Integer.toString( (Integer) rader[i].get("faar")) ); }
             }
		} catch (ParseException e) {
			e.printStackTrace();
		}

            Object dod_dato = rader[i].get("doed");
		    java.text.DateFormat dfYMD_dod = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
		    java.text.DateFormat dfDMY_dod = new java.text.SimpleDateFormat("dd-MM-yyyy") ;
		    try {
			 if(rader[i].get("doed")!=null){
            personinfo.setDod(dfDMY_dod.format(dfYMD_dod.parse(String.valueOf(dod_dato))));
             }else{
                 if(rader[i].get("dodsaar") !=null){personinfo.setDod(Integer.toString((Integer) rader[i].get("dodsaar"))); }
             }

         personinfo.setFleirtaltekst((String) rader[i].get("fleirtaltekst"));
          if(rader[i].get("rep_nr")!=null) {
         personinfo.setRep_nr((Integer) rader[i].get("rep_nr"));
          }
         if(rader[i].get("sup_nr")!=null){
         personinfo.setSup_nr((Integer) rader[i].get("sup_nr"));
         }
         personinfo.setValkrinsnavn((String) rader[i].get("valkrinsnavn"));
         personinfo.setStilling((String) rader[i].get("stilling"));
         personinfo.setPartiNavn((String) rader[i].get("Partitekst"));

		} catch (ParseException e) {
			e.printStackTrace();
		}
            norskepolitikere.add(personinfo);
        }
        return norskepolitikere;
    }

    /*UTDANNING OG YRKE*/

public List<PolitikerBiografi> getUtdanningYrke(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar,List partikode, String sortresult) throws Exception {
        String condition = "HAVING politikere.id IS NOT NULL AND (politikere.etternavn IS NOT NULL)   ";
          if(navn != null){
               condition += " AND politikere.etternavn LIKE ?  ";
               }
    if(bs !=null &&  !bs.equals("") && bs.length>0 ){
        condition += " AND  (    ";
        condition += " politikere.etternavn LIKE ?    ";
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){
                condition += " OR politikere.etternavn LIKE ?  ";
            }
        }
        condition += "  )    ";
    }
    if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
        condition += " AND  (    ";
        condition += " storting_representanter.periode = ?    ";
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){
                condition += " OR storting_representanter.periode = ?  ";
            }
        }
        condition += "  )    ";
    }
    if(st !=null && st.equalsIgnoreCase("storting")){
        condition += " AND (storting_representanter.periode) >=-1 ";
    }
    if(sr !=null && sr.equalsIgnoreCase("statsraad")){
        condition += " AND (regjering_medlemmer.person) is not null ";
    }
    if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
        condition += " AND (regjering_medlemmer.person) is not null ";
    }
    //partikode
    if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
        condition += " AND  (    ";
        condition += " storting_representanter.parti = ?    ";
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){
                condition += " OR storting_representanter.parti = ?  ";
            }
        }
        condition += "  )    ";
    }
    if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

        //length = 1
        if( periode.size() ==1 && periode.get(0).equals("1814")){
            condition += " AND  ( storting_representanter.periode) < 52 ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1903")){
            condition += "AND (storting_representanter.periode) < 89 AND ( storting_representanter.periode) > 52  ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1945")){
            condition += " AND  ( storting_representanter.periode) > 89 ";
        }

        //length= 2
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==2 && periode.get(0).equals("1814")){
                condition += " AND ( ( storting_representanter.periode) < 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1903")){
                condition += "AND ( (storting_representanter.periode) < 89 AND ( storting_representanter.periode) > 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1945")){
                condition += " AND ( ( storting_representanter.periode) > 89 ";
            }
            if( periode.size() ==2 && periode.get(1).equals("1814")){
                condition += " OR  ( storting_representanter.periode) < 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1903")){
                condition += " OR  (storting_representanter.periode) < 89 AND ( storting_representanter.periode) > 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1945")){
                condition += " OR  ( storting_representanter.periode) > 89 ) ";
            }
        }

        //length= 3
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==3 && periode.get(0).equals("1814")){
                condition += " AND ( ( storting_representanter.periode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1903")){
                condition += "AND ( (storting_representanter.periode) < 89 AND ( storting_representanter.periode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1945")){
                condition += " AND ( ( storting_representanter.periode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(1).equals("1814")){
                condition += " OR  ( storting_representanter.periode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1903")){
                condition += " OR  (storting_representanter.periode) < 89 AND ( storting_representanter.periode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1945")){
                condition += " OR  ( storting_representanter.periode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(2).equals("1814")){
                condition += " OR  ( storting_representanter.periode) < 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1903")){
                condition += " OR  (storting_representanter.periode) < 89 AND ( storting_representanter.periode) > 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1945")){
                condition += " OR  ( storting_representanter.periode) > 89 ) ";
            }
        }
    }

    if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY politikere.etternavn ";}
    if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY politikere.fornavn ";}
    if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY politikere.fodt DESC ";}
    if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY politikere.fodt ASC ";}
          List values = new ArrayList();

       if(navn !=null){
        values.add("%"+navn+"%");
       }
    if(bs !=null && !bs.equals("") && bs.length>0){
        values.add(  bs[0]+"%"  );
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
        }
    }

    if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
        values.add(  aar.get(0)+" "  );
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
        }
    }
    if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
        values.add(  partikode.get(0)+" "  );
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
        }
    }

         return this.getAllUtdanningYrke(condition, values);
    }



    private List<PolitikerBiografi> getAllUtdanningYrke(String condition, List values) throws Exception {

        ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT   DISTINCT  politikere.id as person_id, politikere.fornavn, YEAR(politikere.fodt) AS faar, politikere.etternavn AS navn, aktivitet.aktivitet, aktivitet.fraaar, aktivitet.tilaar \n" +
                "         FROM  politikere LEFT OUTER JOIN\n" +
                "         storting_representanter ON storting_representanter.person = politikere.id LEFT OUTER JOIN  \n" +
                "         regjering_medlemmer ON politikere.id = regjering_medlemmer.person LEFT OUTER JOIN\n" +
                "         regjering_statssekretaerer ON politikere.id = regjering_statssekretaerer.person\n" +
                "         INNER JOIN aktivitet  on aktivitet.person = politikere.id \n" +
                "         GROUP BY politikere.id, politikere.fornavn, politikere.etternavn, politikere.fodt \n" +
                "         ,aktivitet.aktivitet, aktivitet.fraaar, aktivitet.tilaar,\n" +
                "         regjering_medlemmer.person, regjering_statssekretaerer.person, storting_representanter.periode    "
                + (condition != null ? " " + condition : "");
       sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();

        for (int i = 0; i < rader.length; i++) {
            PolitikerBiografi personinfo = new PolitikerBiografi();
            personinfo.setEtterNavn((String) rader[i].get("navn"));
            personinfo.setForNavn((String) rader[i].get("fornavn"));
            personinfo.setAktivitet((String) rader[i].get("aktivitet"));

             if(rader[i].get("fraaar")!=null){
        personinfo.setFraaarInteger((Integer) rader[i].get("fraaar"));
        }
          if(rader[i].get("tilaar")!=null){
            personinfo.setTilaarInteger((Integer) rader[i].get("tilaar"));
          }

            norskepolitikere.add(personinfo);
        }
        return norskepolitikere;
    }

/*Kommunalpolitisk*/

    public List<PolitikerBiografi> getKommunalpolitisk(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar,List partikode, String sortresult) throws Exception {
            String condition = " HAVING t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL AND t_storting_komfylk.type = N'K')   ";
              if(navn != null){
                   condition += " AND t_felles_person.navn LIKE ?  ";
                   }
        if(bs !=null &&  !bs.equals("") && bs.length>0 ){
            condition += " AND  (    ";
            condition += " t_felles_person.navn LIKE ?    ";
            for(int i=1; i<bs.length; i++){
                if(bs[i]!=""){
                    condition += " OR t_felles_person.navn LIKE ?  ";
                }
            }
            condition += "  )    ";
        }
        if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
            condition += " AND  (    ";
            condition += " t_storting_person_hist.stortingperiode_kode = ?    ";
            for(int i=1; i<aar.size(); i++){
                if(aar.get(i)!=" "){
                    condition += " OR t_storting_person_hist.stortingperiode_kode = ?  ";
                }
            }
            condition += "  )    ";
        }
        if(st !=null && st.equalsIgnoreCase("storting")){
            condition += " AND (t_storting_person_hist.stortingperiode_kode) >=-1 ";
        }
        if(sr !=null && sr.equalsIgnoreCase("statsraad")){
            condition += " AND (t_felles_statsraader.person_id) is not null ";
        }
        if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
            condition += " AND (t_felles_statssekretaerer.person_id) is not null ";
        }

        //partikode
        if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
            condition += " AND  (    ";
            condition += " t_storting_person_hist.parti_kode = ?    ";
            for(int i=1; i<partikode.size(); i++){
                if(partikode.get(i)!=" "){
                    condition += " OR t_storting_person_hist.parti_kode = ?  ";
                }
            }
            condition += "  )    ";
        }

        if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

            //length = 1
            if( periode.size() ==1 && periode.get(0).equals("1814")){
                condition += " AND  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==1 && periode.get(0).equals("1903")){
                condition += "AND (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52  ";
            }
            else if( periode.size() ==1 && periode.get(0).equals("1945")){
                condition += " AND  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }

            //length= 2
            for(int i=0; i<periode.size(); i++){
                if( periode.size() ==2 && periode.get(0).equals("1814")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==2 && periode.get(0).equals("1903")){
                    condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==2 && periode.get(0).equals("1945")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==2 && periode.get(1).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
                }
                else if( periode.size() ==2 && periode.get(1).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
                }
                else if( periode.size() ==2 && periode.get(1).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
                }
            }

            //length= 3
            for(int i=0; i<periode.size(); i++){
                if( periode.size() ==3 && periode.get(0).equals("1814")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==3 && periode.get(0).equals("1903")){
                    condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==3 && periode.get(0).equals("1945")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==3 && periode.get(1).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==3 && periode.get(1).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==3 && periode.get(1).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==3 && periode.get(2).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
                }
                else if( periode.size() ==3 && periode.get(2).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
                }
                else if( periode.size() ==3 && periode.get(2).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
                }
            }
        }

        if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY t_felles_person.navn ";}
        if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY t_felles_person.fornavn ";}
        if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY t_felles_person.faar DESC ";}
        if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY t_felles_person.faar ASC ";}

              List values = new ArrayList();

           if(navn !=null){
            values.add("%"+navn+"%");
           }
        if(bs !=null && !bs.equals("") && bs.length>0){
            values.add(  bs[0]+"%"  );
            for(int i=1; i<bs.length; i++){
                if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
            }
        }

        if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
            values.add(  aar.get(0)+" "  );
            for(int i=1; i<aar.size(); i++){
                if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
            }
        }
        if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
            values.add(  partikode.get(0)+" "  );
            for(int i=1; i<partikode.size(); i++){
                if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
            }
        }

        return this.getAllKommunalpolitisk(condition, values);
        }



        private List<PolitikerBiografi> getAllKommunalpolitisk(String condition, List values) throws Exception {

            ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
            Result result = null;
            SqlCommandBean sqlCB = new SqlCommandBean();
            SortedMap[] rader = null;
            String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn,  t_felles_person.navn, t_felles_person.faar, t_storting_komfylk.institusjon " +
                    "\t\t,t_storting_komfylkstilling.eintaltekst, t_storting_komfylkinstitusjon.eintaltekst AS Expr2, t_storting_komfylkperioder.eintaltekst AS Expr3\n" +
                    "                     FROM  t_felles_person LEFT OUTER JOIN\n" +
                    "                     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                    "                     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                    "                     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                    "                     Left outer join t_storting_komfylk ON t_felles_person.person_id = t_storting_komfylk.person_id INNER JOIN\n" +
                    "                                      t_storting_komfylkstilling ON t_storting_komfylk.stillingkode = t_storting_komfylkstilling.kode INNER JOIN\n" +
                    "                                      t_storting_komfylkinstitusjon ON t_storting_komfylk.institusjonkode = t_storting_komfylkinstitusjon.kode INNER JOIN\n" +
                    "                                      t_storting_komfylkperioder ON t_storting_komfylk.periodekode = t_storting_komfylkperioder.kode\n" +
                    "                     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn , t_felles_person.faar\n" +
                    "                          ,t_storting_komfylkstilling.eintaltekst, t_storting_komfylkinstitusjon.eintaltekst, t_storting_komfylkperioder.eintaltekst,t_storting_komfylk.type,\n" +
                    "                          t_felles_statsraader.person_id, t_felles_statssekretaerer.person_id, t_storting_person_hist.stortingperiode_kode, t_storting_komfylk.institusjon    "
                    + (condition != null ? " " + condition : "");
           sqlCB.setConnection(this.conn);
            sqlCB.setSqlValue(sqlSelect); //sporring
            sqlCB.setValues(values); //parameter
            result = sqlCB.executeQuery();
            rader = result.getRows();

            for (int i = 0; i < rader.length; i++) {
                PolitikerBiografi personinfo = new PolitikerBiografi();
                personinfo.setEtterNavn((String) rader[i].get("navn"));
                personinfo.setForNavn((String) rader[i].get("fornavn"));
                 personinfo.setFleirtaltekst((String) rader[i].get("Expr3"));
                 personinfo.setValkrinsnavn((String) rader[i].get("Expr2"));
                personinfo.setStilling((String) rader[i].get("eintaltekst"));
                 personinfo.setInstitusjon((String) rader[i].get("institusjon"));

                norskepolitikere.add(personinfo);

            }
            return norskepolitikere;
        }

     /*Fylkespolitisk*/

    public List<PolitikerBiografi> getFylkespolitisk(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar,List partikode, String sortresult) throws Exception {
            String condition = " HAVING t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL AND t_storting_komfylk.type = N'F')   ";
              if(navn != null){
                   condition += " AND t_felles_person.navn LIKE ?  ";
                   }
        if(bs !=null &&  !bs.equals("") && bs.length>0 ){
            condition += " AND  (    ";
            condition += " t_felles_person.navn LIKE ?    ";
            for(int i=1; i<bs.length; i++){
                if(bs[i]!=""){
                    condition += " OR t_felles_person.navn LIKE ?  ";
                }
            }
            condition += "  )    ";
        }
        if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
            condition += " AND  (    ";
            condition += " t_storting_person_hist.stortingperiode_kode = ?    ";
            for(int i=1; i<aar.size(); i++){
                if(aar.get(i)!=" "){
                    condition += " OR t_storting_person_hist.stortingperiode_kode = ?  ";
                }
            }
            condition += "  )    ";
        }
        if(st !=null && st.equalsIgnoreCase("storting")){
            condition += " AND (t_storting_person_hist.stortingperiode_kode) >=-1 ";
        }
        if(sr !=null && sr.equalsIgnoreCase("statsraad")){
            condition += " AND (t_felles_statsraader.person_id) is not null ";
        }
        if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
            condition += " AND (t_felles_statssekretaerer.person_id) is not null ";
        }

        //partikode
        if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
            condition += " AND  (    ";
            condition += " t_storting_person_hist.parti_kode = ?    ";
            for(int i=1; i<partikode.size(); i++){
                if(partikode.get(i)!=" "){
                    condition += " OR t_storting_person_hist.parti_kode = ?  ";
                }
            }
            condition += "  )    ";
        }

        if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

            //length = 1
            if( periode.size() ==1 && periode.get(0).equals("1814")){
                condition += " AND  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==1 && periode.get(0).equals("1903")){
                condition += "AND (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52  ";
            }
            else if( periode.size() ==1 && periode.get(0).equals("1945")){
                condition += " AND  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }

            //length= 2
            for(int i=0; i<periode.size(); i++){
                if( periode.size() ==2 && periode.get(0).equals("1814")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==2 && periode.get(0).equals("1903")){
                    condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==2 && periode.get(0).equals("1945")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==2 && periode.get(1).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
                }
                else if( periode.size() ==2 && periode.get(1).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
                }
                else if( periode.size() ==2 && periode.get(1).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
                }
            }

            //length= 3
            for(int i=0; i<periode.size(); i++){
                if( periode.size() ==3 && periode.get(0).equals("1814")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==3 && periode.get(0).equals("1903")){
                    condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==3 && periode.get(0).equals("1945")){
                    condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==3 && periode.get(1).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
                }
                else if( periode.size() ==3 && periode.get(1).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
                }
                else if( periode.size() ==3 && periode.get(1).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
                }
                if( periode.size() ==3 && periode.get(2).equals("1814")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
                }
                else if( periode.size() ==3 && periode.get(2).equals("1903")){
                    condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
                }
                else if( periode.size() ==3 && periode.get(2).equals("1945")){
                    condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
                }
            }
        }
        if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY t_felles_person.navn ";}
        if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY t_felles_person.fornavn ";}
        if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY t_felles_person.faar DESC ";}
        if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY t_felles_person.faar ASC ";}

              List values = new ArrayList();

           if(navn !=null){
            values.add("%"+navn+"%");
           }
        if(bs !=null && !bs.equals("") && bs.length>0){
            values.add(  bs[0]+"%"  );
            for(int i=1; i<bs.length; i++){
                if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
            }
        }

        if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
            values.add(  aar.get(0)+" "  );
            for(int i=1; i<aar.size(); i++){
                if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
            }
        }
        if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
            values.add(  partikode.get(0)+" "  );
            for(int i=1; i<partikode.size(); i++){
                if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
            }
        }

        return this.getAllKommunalpolitisk(condition, values);
        }



        private List<PolitikerBiografi> getAllFylkespolitisk(String condition, List values) throws Exception {

            ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
            Result result = null;
            SqlCommandBean sqlCB = new SqlCommandBean();
            SortedMap[] rader = null;
            String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar\n" +
                    "\t\t,t_storting_komfylkstilling.eintaltekst, t_storting_komfylkinstitusjon.eintaltekst AS Expr2, t_storting_komfylkperioder.eintaltekst AS Expr3\n" +
                    "                     FROM  t_felles_person LEFT OUTER JOIN\n" +
                    "                     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                    "                     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                    "                     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                    "                     Left outer join t_storting_komfylk ON t_felles_person.person_id = t_storting_komfylk.person_id INNER JOIN\n" +
                    "                                      t_storting_komfylkstilling ON t_storting_komfylk.stillingkode = t_storting_komfylkstilling.kode INNER JOIN\n" +
                    "                                      t_storting_komfylkinstitusjon ON t_storting_komfylk.institusjonkode = t_storting_komfylkinstitusjon.kode INNER JOIN\n" +
                    "                                      t_storting_komfylkperioder ON t_storting_komfylk.periodekode = t_storting_komfylkperioder.kode\n" +
                    "                     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar \n" +
                    "                          ,t_storting_komfylkstilling.eintaltekst, t_storting_komfylkinstitusjon.eintaltekst, t_storting_komfylkperioder.eintaltekst,t_storting_komfylk.type,\n" +
                    "                          t_felles_statsraader.person_id, t_felles_statssekretaerer.person_id, t_storting_person_hist.stortingperiode_kode    "
                    + (condition != null ? " " + condition : "");
           sqlCB.setConnection(this.conn);
            sqlCB.setSqlValue(sqlSelect); //sporring
            sqlCB.setValues(values); //parameter
            result = sqlCB.executeQuery();
            rader = result.getRows();

            for (int i = 0; i < rader.length; i++) {
                PolitikerBiografi personinfo = new PolitikerBiografi();
                personinfo.setEtterNavn((String) rader[i].get("navn"));
                personinfo.setForNavn((String) rader[i].get("fornavn"));
                 personinfo.setFleirtaltekst((String) rader[i].get("Expr3"));
                 personinfo.setValkrinsnavn((String) rader[i].get("Expr2"));
                personinfo.setStilling((String) rader[i].get("eintaltekst"));

                norskepolitikere.add(personinfo);

            }
            return norskepolitikere;
        }

  /*PARTI VERV*/


  /*Parti verv*/

public List<PolitikerBiografi> getPartiverv(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar,List partikode, String sortresult) throws Exception {
        String condition = "HAVING t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL)   ";
          if(navn != null){
               condition += " AND t_felles_person.navn LIKE ?  ";
               }
    if(bs !=null &&  !bs.equals("") && bs.length>0 ){
        condition += " AND  (    ";
        condition += " t_felles_person.navn LIKE ?    ";
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){
                condition += " OR t_felles_person.navn LIKE ?  ";
            }
        }
        condition += "  )    ";
    }
    if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.stortingperiode_kode = ?    ";
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){
                condition += " OR t_storting_person_hist.stortingperiode_kode = ?  ";
            }
        }
        condition += "  )    ";
    }
    if(st !=null && st.equalsIgnoreCase("storting")){
        condition += " AND (t_storting_person_hist.stortingperiode_kode) >=-1 ";
    }
    if(sr !=null && sr.equalsIgnoreCase("statsraad")){
        condition += " AND (t_felles_statsraader.person_id) is not null ";
    }
    if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
        condition += " AND (t_felles_statssekretaerer.person_id) is not null ";
    }

    //partikode
    if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.parti_kode = ?    ";
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){
                condition += " OR t_storting_person_hist.parti_kode = ?  ";
            }
        }
        condition += "  )    ";
    }

    if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

        //length = 1
        if( periode.size() ==1 && periode.get(0).equals("1814")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1903")){
            condition += "AND (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52  ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1945")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
        }

        //length= 2
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==2 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==2 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }

        //length= 3
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==3 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(2).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }
    }
    if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY t_felles_person.navn ";}
    if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY t_felles_person.fornavn ";}
    if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY t_felles_person.faar DESC ";}
    if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY t_felles_person.faar ASC ";}

          List values = new ArrayList();

       if(navn !=null){
        values.add("%"+navn+"%");
       }
    if(bs !=null && !bs.equals("") && bs.length>0){
        values.add(  bs[0]+"%"  );
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
        }
    }

    if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
        values.add(  aar.get(0)+" "  );
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
        }
    }
    if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
        values.add(  partikode.get(0)+" "  );
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
        }
    }

    return this.getAllPartiverv(condition, values);
    }



    private List<PolitikerBiografi> getAllPartiverv(String condition, List values) throws Exception {

        ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar, t_storting_partiverv.stilling, t_storting_partiverv.institusjon, t_storting_partiverv.fra_aar, t_storting_partiverv.til_aar \n" +
                "                     FROM  t_felles_person LEFT OUTER JOIN\n" +
                "                     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                "                     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "                     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                "                     INNER JOIN t_storting_partiverv  on t_storting_partiverv.person_id = t_felles_person.person_id \n" +
                "                     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn,t_felles_person.faar \n" +
                "                          ,t_storting_partiverv.stilling, t_storting_partiverv.institusjon, t_storting_partiverv.fra_aar, t_storting_partiverv.til_aar,\n" +
                "                          t_felles_statsraader.person_id, t_felles_statssekretaerer.person_id, t_storting_person_hist.stortingperiode_kode    "
                + (condition != null ? " " + condition : "");
       sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();

        for (int i = 0; i < rader.length; i++) {
            PolitikerBiografi personinfo = new PolitikerBiografi();
            personinfo.setEtterNavn((String) rader[i].get("navn"));
            personinfo.setForNavn((String) rader[i].get("fornavn"));
            personinfo.setStilling((String) rader[i].get("stilling"));
            personinfo.setInstitusjon((String) rader[i].get("institusjon"));

             if(rader[i].get("fra_aar")!=null){
        personinfo.setFraaarInteger((Integer) rader[i].get("fra_aar"));
        }
          if(rader[i].get("til_aar")!=null){
            personinfo.setTilaarInteger((Integer) rader[i].get("til_aar"));
          }

            norskepolitikere.add(personinfo);
        }
        return norskepolitikere;
    }


 /*Offentlige verv*/

public List<PolitikerBiografi> getOffentligeverv(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar,List partikode, String sortresult) throws Exception {
        String condition = "HAVING t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL)   ";
          if(navn != null){
               condition += " AND t_felles_person.navn LIKE ?  ";
               }
    if(bs !=null &&  !bs.equals("") && bs.length>0 ){
        condition += " AND  (    ";
        condition += " t_felles_person.navn LIKE ?    ";
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){
                condition += " OR t_felles_person.navn LIKE ?  ";
            }
        }
        condition += "  )    ";
    }
    if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.stortingperiode_kode = ?    ";
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){
                condition += " OR t_storting_person_hist.stortingperiode_kode = ?  ";
            }
        }
        condition += "  )    ";
    }
    if(st !=null && st.equalsIgnoreCase("storting")){
        condition += " AND (t_storting_person_hist.stortingperiode_kode) >=-1 ";
    }
    if(sr !=null && sr.equalsIgnoreCase("statsraad")){
        condition += " AND (t_felles_statsraader.person_id) is not null ";
    }
    if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
        condition += " AND (t_felles_statssekretaerer.person_id) is not null ";
    }

    //partikode
    if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.parti_kode = ?    ";
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){
                condition += " OR t_storting_person_hist.parti_kode = ?  ";
            }
        }
        condition += "  )    ";
    }

    if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

        //length = 1
        if( periode.size() ==1 && periode.get(0).equals("1814")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1903")){
            condition += "AND (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52  ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1945")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
        }

        //length= 2
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==2 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==2 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }

        //length= 3
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==3 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(2).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }
    }
    if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY t_felles_person.navn ";}
    if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY t_felles_person.fornavn ";}
    if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY t_felles_person.faar DESC ";}
    if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY t_felles_person.faar ASC ";}

          List values = new ArrayList();

       if(navn !=null){
        values.add("%"+navn+"%");
       }
    if(bs !=null && !bs.equals("") && bs.length>0){
        values.add(  bs[0]+"%"  );
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
        }
    }

    if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
        values.add(  aar.get(0)+" "  );
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
        }
    }

    if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
        values.add(  partikode.get(0)+" "  );
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
        }
    }

    return this.getAllOffentligeverv(condition, values);
    }



    private List<PolitikerBiografi> getAllOffentligeverv(String condition, List values) throws Exception {

        ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar, t_storting_offverv.stilling, t_storting_offverv.institusjon, t_storting_offverv.fra_aar, t_storting_offverv.til_aar \n" +
                "                     FROM  t_felles_person LEFT OUTER JOIN\n" +
                "                     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                "                     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "                     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                "                     INNER JOIN t_storting_offverv  on t_storting_offverv.person_id = t_felles_person.person_id \n" +
                "                     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar \n" +
                "                          ,t_storting_offverv.stilling, t_storting_offverv.institusjon, t_storting_offverv.fra_aar, t_storting_offverv.til_aar,\n" +
                "                          t_felles_statsraader.person_id, t_felles_statssekretaerer.person_id, t_storting_person_hist.stortingperiode_kode    "
                + (condition != null ? " " + condition : "");
       sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();

        for (int i = 0; i < rader.length; i++) {
            PolitikerBiografi personinfo = new PolitikerBiografi();
            personinfo.setEtterNavn((String) rader[i].get("navn"));
            personinfo.setForNavn((String) rader[i].get("fornavn"));
            personinfo.setStilling((String) rader[i].get("stilling"));
            personinfo.setInstitusjon((String) rader[i].get("institusjon"));

             if(rader[i].get("fra_aar")!=null){
        personinfo.setFraaarInteger((Integer) rader[i].get("fra_aar"));
        }
          if(rader[i].get("til_aar")!=null){
            personinfo.setTilaarInteger((Integer) rader[i].get("til_aar"));
          }

            norskepolitikere.add(personinfo);
        }
        return norskepolitikere;
    }


/*Organisasjon verv*/

public List<PolitikerBiografi> getOrgverv(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar,List partikode, String sortresult) throws Exception {
        String condition = "HAVING t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL)   ";
          if(navn != null){
               condition += " AND t_felles_person.navn LIKE ?  ";
               }
    if(bs !=null &&  !bs.equals("") && bs.length>0 ){
        condition += " AND  (    ";
        condition += " t_felles_person.navn LIKE ?    ";
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){
                condition += " OR t_felles_person.navn LIKE ?  ";
            }
        }
        condition += "  )    ";
    }
    if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.stortingperiode_kode = ?    ";
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){
                condition += " OR t_storting_person_hist.stortingperiode_kode = ?  ";
            }
        }
        condition += "  )    ";
    }
    if(st !=null && st.equalsIgnoreCase("storting")){
        condition += " AND (t_storting_person_hist.stortingperiode_kode) >=-1 ";
    }
    if(sr !=null && sr.equalsIgnoreCase("statsraad")){
        condition += " AND (t_felles_statsraader.person_id) is not null ";
    }
    if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
        condition += " AND (t_felles_statssekretaerer.person_id) is not null ";
    }

    //partikode
    if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.parti_kode = ?    ";
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){
                condition += " OR t_storting_person_hist.parti_kode = ?  ";
            }
        }
        condition += "  )    ";
    }

    if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

        //length = 1
        if( periode.size() ==1 && periode.get(0).equals("1814")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1903")){
            condition += "AND (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52  ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1945")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
        }

        //length= 2
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==2 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==2 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }

        //length= 3
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==3 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(2).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }
    }
    if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY t_felles_person.navn ";}
    if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY t_felles_person.fornavn ";}
    if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY t_felles_person.faar DESC ";}
    if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY t_felles_person.faar ASC ";}

          List values = new ArrayList();

       if(navn !=null){
        values.add("%"+navn+"%");
       }
    if(bs !=null && !bs.equals("") && bs.length>0){
        values.add(  bs[0]+"%"  );
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
        }
    }

    if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
        values.add(  aar.get(0)+" "  );
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
        }
    }
    if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
        values.add(  partikode.get(0)+" "  );
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
        }
    }

    return this.getAllOrgverv(condition, values);
    }



    private List<PolitikerBiografi> getAllOrgverv(String condition, List values) throws Exception {

        ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar, t_storting_orgverv.stilling, t_storting_orgverv.institusjon, t_storting_orgverv.fra_aar, t_storting_orgverv.til_aar \n" +
                "                     FROM  t_felles_person LEFT OUTER JOIN\n" +
                "                     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                "                     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "                     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                "                     INNER JOIN t_storting_orgverv  on t_storting_orgverv.person_id = t_felles_person.person_id \n" +
                "                     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar \n" +
                "                          ,t_storting_orgverv.stilling, t_storting_orgverv.institusjon, t_storting_orgverv.fra_aar, t_storting_orgverv.til_aar,\n" +
                "                          t_felles_statsraader.person_id, t_felles_statssekretaerer.person_id, t_storting_person_hist.stortingperiode_kode    "
                + (condition != null ? " " + condition : "");
       sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();

        for (int i = 0; i < rader.length; i++) {
            PolitikerBiografi personinfo = new PolitikerBiografi();
            personinfo.setEtterNavn((String) rader[i].get("navn"));
            personinfo.setForNavn((String) rader[i].get("fornavn"));
            personinfo.setStilling((String) rader[i].get("stilling"));
            personinfo.setInstitusjon((String) rader[i].get("institusjon"));

             if(rader[i].get("fra_aar")!=null){
        personinfo.setFraaarInteger((Integer) rader[i].get("fra_aar"));
        }
          if(rader[i].get("til_aar")!=null){
            personinfo.setTilaarInteger((Integer) rader[i].get("til_aar"));
          }

            norskepolitikere.add(personinfo);
        }
        return norskepolitikere;
    }

/*Admin verv*/

public List<PolitikerBiografi> getAdminverv(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar,List partikode, String sortresult) throws Exception {
        String condition = "HAVING t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL)   ";
          if(navn != null){
               condition += " AND t_felles_person.navn LIKE ?  ";
               }
    if(bs !=null &&  !bs.equals("") && bs.length>0 ){
        condition += " AND  (    ";
        condition += " t_felles_person.navn LIKE ?    ";
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){
                condition += " OR t_felles_person.navn LIKE ?  ";
            }
        }
        condition += "  )    ";
    }
    if(aar !=null &&  !aar.equals(" ") && !aar.equals(' ') && aar.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.stortingperiode_kode = ?    ";
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){
                condition += " OR t_storting_person_hist.stortingperiode_kode = ?  ";
            }
        }
        condition += "  )    ";
    }
    if(st !=null && st.equalsIgnoreCase("storting")){
        condition += " AND (t_storting_person_hist.stortingperiode_kode) >=-1 ";
    }
    if(sr !=null && sr.equalsIgnoreCase("statsraad")){
        condition += " AND (t_felles_statsraader.person_id) is not null ";
    }
    if(ss !=null && ss.equalsIgnoreCase("statssekretar")){
        condition += " AND (t_felles_statssekretaerer.person_id) is not null ";
    }


    //partikode
    if(partikode !=null &&  !partikode.equals(" ") && !partikode.equals(' ') && partikode.size()>0 ){
        condition += " AND  (    ";
        condition += " t_storting_person_hist.parti_kode = ?    ";
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){
                condition += " OR t_storting_person_hist.parti_kode = ?  ";
            }
        }
        condition += "  )    ";
    }

    if(periode !=null &&  !periode.equals("") && periode.size()>0 ){

        //length = 1
        if( periode.size() ==1 && periode.get(0).equals("1814")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1903")){
            condition += "AND (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52  ";
        }
        else if( periode.size() ==1 && periode.get(0).equals("1945")){
            condition += " AND  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
        }

        //length= 2
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==2 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==2 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==2 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==2 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }

        //length= 3
        for(int i=0; i<periode.size(); i++){
            if( periode.size() ==3 && periode.get(0).equals("1814")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1903")){
                condition += "AND ( (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(0).equals("1945")){
                condition += " AND ( ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(1).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ";
            }
            else if( periode.size() ==3 && periode.get(1).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ";
            }
            if( periode.size() ==3 && periode.get(2).equals("1814")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) < 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1903")){
                condition += " OR  (t_storting_person_hist.stortingperiode_kode) < 89 AND ( t_storting_person_hist.stortingperiode_kode) > 52 ) ";
            }
            else if( periode.size() ==3 && periode.get(2).equals("1945")){
                condition += " OR  ( t_storting_person_hist.stortingperiode_kode) > 89 ) ";
            }
        }
    }

    if(sortresult==null || sortresult.equals("etternavn")){condition +=" ORDER BY t_felles_person.navn ";}
    if(sortresult!=null && sortresult.equals("fornavn")){condition +=" ORDER BY t_felles_person.fornavn ";}
    if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY t_felles_person.faar DESC ";}
    if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY t_felles_person.faar ASC ";}

          List values = new ArrayList();

       if(navn !=null){
        values.add("%"+navn+"%");
       }
    if(bs !=null && !bs.equals("") && bs.length>0){
        values.add(  bs[0]+"%"  );
        for(int i=1; i<bs.length; i++){
            if(bs[i]!=""){ values.add(  bs[i]+"%"  );}
        }
    }

    if(aar !=null && !aar.equals("") && !aar.equals(' ') && aar.size()>0){
        values.add(  aar.get(0)+" "  );
        for(int i=1; i<aar.size(); i++){
            if(aar.get(i)!=" "){ values.add(  aar.get(i)+" "  );}
        }
    }
    if(partikode !=null && !partikode.equals("") && !partikode.equals(' ') && partikode.size()>0){
        values.add(  partikode.get(0)+" "  );
        for(int i=1; i<partikode.size(); i++){
            if(partikode.get(i)!=" "){ values.add(  partikode.get(i)+" "  );}
        }
    }

    return this.getAllAdminverv(condition, values);
    }



    private List<PolitikerBiografi> getAllAdminverv(String condition, List values) throws Exception {

        ArrayList<PolitikerBiografi> norskepolitikere = new ArrayList<PolitikerBiografi>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar, t_storting_admverv.stilling, t_storting_admverv.institusjon, t_storting_admverv.fra_aar, t_storting_admverv.til_aar \n" +
                "                     FROM  t_felles_person LEFT OUTER JOIN\n" +
                "                     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                "                     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "                     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                "                     INNER JOIN t_storting_admverv  on t_storting_admverv.person_id = t_felles_person.person_id \n" +
                "                     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, t_felles_person.faar \n" +
                "                          ,t_storting_admverv.stilling, t_storting_admverv.institusjon, t_storting_admverv.fra_aar, t_storting_admverv.til_aar,\n" +
                "                          t_felles_statsraader.person_id, t_felles_statssekretaerer.person_id, t_storting_person_hist.stortingperiode_kode    "
                + (condition != null ? " " + condition : "");
       sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();

        for (int i = 0; i < rader.length; i++) {
            PolitikerBiografi personinfo = new PolitikerBiografi();
            personinfo.setEtterNavn((String) rader[i].get("navn"));
            personinfo.setForNavn((String) rader[i].get("fornavn"));
            personinfo.setStilling((String) rader[i].get("stilling"));
            personinfo.setInstitusjon((String) rader[i].get("institusjon"));

             if(rader[i].get("fra_aar")!=null){
        personinfo.setFraaarInteger((Integer) rader[i].get("fra_aar"));
        }
          if(rader[i].get("til_aar")!=null){
            personinfo.setTilaarInteger((Integer) rader[i].get("til_aar"));
          }

            norskepolitikere.add(personinfo);
        }
        return norskepolitikere;
    }




}