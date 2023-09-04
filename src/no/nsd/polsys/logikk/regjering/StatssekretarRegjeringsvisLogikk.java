package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.MinisterRegjering;

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
public class StatssekretarRegjeringsvisLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public StatssekretarRegjeringsvisLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public MinisterRegjering[] getNorskeministerier() throws Exception {
           String condition = " WHERE min_reg=1 ORDER BY kode asc ";
           return this.getStatssekretarRegjeringsvis(condition, null);
       }
     public MinisterRegjering[] getStatssekretarRegjeringsvis() throws Exception {
           String condition = " where type in (4,5) and regjering.id >= 36 ORDER BY regjering.id asc ";
           return this.getStatssekretarRegjeringsvis(condition, null);
       }


    public MinisterRegjering[] getStatssekretarRegjeringsvisBeskrivelse(String fradato, String tildato) throws Exception {
              String condition = " WHERE   (regjering_statssekretaerer.start BETWEEN regjering.start AND regjering.slutt) AND \n" +
                      "               (regjering_statssekretaerer.slutt BETWEEN regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt) AND\n" +
                      "               (regjering.start = CONVERT(DATETIME, ?, 102)) AND \n" +
                      "               (regjering.slutt = CONVERT(DATETIME, ?, 102)) OR\n" +
                      "               (regjering_statssekretaerer.slutt BETWEEN regjering.start AND regjering.slutt) AND \n" +
                      "               (regjering.start = CONVERT(DATETIME, ?, 102)) AND \n" +
                      "               (regjering.slutt = CONVERT(DATETIME, ?, 102)) AND (regjering_statssekretaerer.slutt BETWEEN \n" +
                      "               regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt)\n" +
                      "               ORDER BY regjering_departement.sortering, regjering_departement.navn_nb, regjering_statssekretaerer.stilling, regjering_statssekretaerer.start desc ";

               List<Serializable> values = new ArrayList<Serializable>();
              values.add(fradato);
              values.add(tildato);
              values.add(fradato);
              values.add(tildato);
              return this.getStatssekretarRegjeringsvisBeskrivelse(condition, values);
          }

       public MinisterRegjering[] getStatssekretarRegjeringsvis2(String fradato, String tildato) throws Exception {
                String condition = " WHERE regjering.id not BETWEEN 101 AND 112 AND  (((regjering.start)=CONVERT(DATETIME, ?, 102)) AND ((regjering.slutt)=CONVERT(DATETIME, ?, 102))) ";
                 List<Serializable> values = new ArrayList<Serializable>();
                values.add(fradato);
                values.add(tildato);
                return this.getStatssekretarRegjeringsvis2(condition, values);
            }
    


  /*

    public MinisterRegjering[] getStortingperioder(String stortingperiode) throws Exception {
         String condition = "";
          ArrayList<String> list = new ArrayList<String>();
        if(stortingperiode != null){
        StringTokenizer st = new StringTokenizer(stortingperiode, ",");
        while (st.hasMoreTokens()) {
        list.add(st.nextToken());
        }
          if(!list.isEmpty()){
             condition +=   " WHERE kode in ( " ;
            for(int i = 0; i < list.size(); i++){
           condition +=    list.get(i) + ", ";
           }
           condition +=    "  -1111 )";
         }
        }
           return this.getStortingperioder(condition, null);
     }


          // getSumpPosisjon


            public MinisterRegjering[] getSumPosisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (t_storting_person_hist.rep_nr >= 1) AND (t_storting_person_hist.stortingperiode_kode >= 32) ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null){
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND (t_storting_person_hist.stortingperiode_kode IN ( " ;
                    for(int i = 0; i < list.size(); i++){
                   condition +=    list.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                }
              }
               if(list_partikode !=null){
                StringTokenizer st_partikode = new StringTokenizer(list_partikode, ",");
                while (st_partikode.hasMoreTokens()) {
                listpartikode.add(st_partikode.nextToken());
                }
                  if(!listpartikode.isEmpty()){
                     condition +=   " AND  (t_storting_person_hist.parti_kode IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                 }
               }
                 condition += " GROUP BY t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_person_hist.stortingperiode_kode\n" +
                         "ORDER BY t_storting_person_hist.stortingperiode_kode ";

                   return this.getSumposisjon(condition,  null);
              }

            // getSumpOPosisjon


            public MinisterRegjering[] getSumOposisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (t_storting_person_hist.rep_nr >= 1) AND (t_storting_person_hist.stortingperiode_kode >= 32) ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null){
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND (t_storting_person_hist.stortingperiode_kode IN ( " ;
                    for(int i = 0; i < list.size(); i++){
                   condition +=    list.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                 }
                }
               if(list_partikode !=null){
                StringTokenizer st_partikode = new StringTokenizer(list_partikode, ",");
                while (st_partikode.hasMoreTokens()) {
                listpartikode.add(st_partikode.nextToken());
                }
                  if(!listpartikode.isEmpty()){
                     condition +=   " AND ( NOT (t_storting_person_hist.parti_kode IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 ))) ";
                 }
               }
                 condition += " GROUP BY t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_person_hist.stortingperiode_kode\n" +
                         "ORDER BY t_storting_person_hist.stortingperiode_kode ";

                   return this.getSumposisjon(condition,  null);
              }


     // getPosisjon

        public MinisterRegjering[] getPosisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (t_storting_person_hist.rep_nr >= 1) AND (t_storting_person_hist.stortingperiode_kode >= 32) AND t_storting_person_hist.parti is not null ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null) {
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND (t_storting_person_hist.stortingperiode_kode IN ( " ;
                    for(int i = 0; i < list.size(); i++){
                   condition +=    list.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                }
               }
                if(list_partikode != null){
                StringTokenizer st_partikode = new StringTokenizer(list_partikode, ",");
                while (st_partikode.hasMoreTokens()) {
                listpartikode.add(st_partikode.nextToken());
                }
                  if(!listpartikode.isEmpty()){
                     condition +=   " AND  (t_storting_person_hist.parti_kode IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                 }
                }
                 condition += " GROUP BY t_storting_person_hist.parti, t_felles_partinavn.parti_bruksnavn, t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_person_hist.stortingperiode_kode \n" +
                         " ORDER BY t_storting_person_hist.stortingperiode_kode, COUNT(t_storting_person_hist.parti_kode) DESC ";

                   return this.getposisjon(condition,  null);
              }

     // getOposisjon

        public MinisterRegjering[] getOposisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (t_storting_person_hist.rep_nr >= 1) AND (t_storting_person_hist.stortingperiode_kode >= 32) AND t_storting_person_hist.parti is not null ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null){
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND (t_storting_person_hist.stortingperiode_kode IN ( " ;
                    for(int i = 0; i < list.size(); i++){
                   condition +=    list.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                }
                }
               if(list_partikode !=null){ 
                StringTokenizer st_partikode = new StringTokenizer(list_partikode, ",");
                while (st_partikode.hasMoreTokens()) {
                listpartikode.add(st_partikode.nextToken());
                }
                  if(!listpartikode.isEmpty()){
                     condition +=   " AND (NOT (t_storting_person_hist.parti_kode IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 ))) ";
                 }
               }
                 condition += " GROUP BY t_storting_person_hist.parti, t_felles_partinavn.parti_bruksnavn, t_storting_stortingsperioder_before1945.fleirtaltekst, t_storting_person_hist.stortingperiode_kode \n" +
                         " ORDER BY t_storting_person_hist.stortingperiode_kode, COUNT(t_storting_person_hist.parti_kode) DESC ";

                   return this.getposisjon(condition,  null);
              }
    */




/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private MinisterRegjering[] getStatssekretarRegjeringsvis(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  regjering.id, YEAR(start) AS Aar, type as min_reg,  navn_nb as NOstatsraadsbetegnelse, navn_en as ENGstatsraadsbetegnelse,   DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, tekst_entall_nb as Flertall, Blokk, aarsak_avgangs_nb\n" +
                "        , STUFF(( \n" +
                "          SELECT N', ' + CAST([kode] AS VARCHAR(255)) \n" +
                "          FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti \n" +
                "          WHERE regjering_partier.regjering = regjering.id  \n" +
                "          FOR XML PATH ('')), 1, 1, '') AS partierkode\n" +
                "\t\tFROM  regjering\t   \tleft join regjering_parlamentarisk_grunnlag on regjering_parlamentarisk_grunnlag.id = regjering.grunnlag "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];

    for (int i = 0; i < rader.length; i++) {
                ministerregjering[i] = new MinisterRegjering();
                ministerregjering[i].setAar((Integer)rader[i].get("aar"));
               //ministerregjering[i].setBlokk((Integer)rader[i].get("blokk"));
        if (this.engelsk) {
            ministerregjering[i].setMinisterium((String) rader[i].get("ENGstatsraadsbetegnelse"));
        }else{
            ministerregjering[i].setMinisterium((String) rader[i].get("NOstatsraadsbetegnelse"));
        }
                //ministerregjering[i].setRegjeringparti((String)rader[i].get("partier"));
                //ministerregjering[i].setRegjeringtype((String)rader[i].get("flertall"));
                //ministerregjering[i].setAarsakavgang((String)rader[i].get("aarsak_avgang"));
                ministerregjering[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
                ministerregjering[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));
               //ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("min_reg"));

        if(rader[i].get("partierkode")!=null){
               ministerregjering[i].setPartikode((String)rader[i].get("partierkode"));
             }
             if(rader[i].get("stortingperiodekode")!=null){
               ministerregjering[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
             }
             
            }

        return ministerregjering;
    }

    /**
      * regjeringsbeskrivelse
      * */

     private MinisterRegjering[] getStatssekretarRegjeringsvisBeskrivelse(String condition, List values) throws Exception {
           // tabell som returneres fra denne metoden.
           MinisterRegjering[] ministerregjering = null;
           // resultat fra sql-sporring.
           Result result = null;
           // objekt som brukes til aa utfore sql-sporring.
           SqlCommandBean sqlCB = new SqlCommandBean();
           // inneholder data fra sql-sporring.
           SortedMap[] rader = null;
           // SQL-sporring.
           String sqlSelect = "SELECT politikere.foedtaar as foedt, regjering_departement.navn_nb as eintaltekst_no, regjering_departement.navn_en as eintaltekst_en, regjering_departement.kode as Kode, regjering_statssekretaerer_stilling.navn as stilling_avvik_no, regjering_statssekretaerer_stilling.navn_en as stilling_avvik_en, \n" +
                   "       DAY(regjering_statssekretaerer.start)AS startday,  MONTH(regjering_statssekretaerer.start)AS startmonth, YEAR(regjering_statssekretaerer.start)AS startyear, DAY(regjering_statssekretaerer.slutt) AS sluttday , MONTH(regjering_statssekretaerer.slutt) AS sluttmonth, YEAR(regjering_statssekretaerer.slutt) AS sluttyear, regjering_statssekretaerer.kommentar_ekstern_nb as Eksternkommentar, regjering_statssekretaerer.kommentar_ekstern_en as engEksternkommentar,  politikere.fornavn, \n" +
                   "       politikere.etternavn as navn, regjering_statssekretaerer.parti_ved_start, t_felles_partinavn.eintaltekst_forkorting as Eintaltekst_forkorting, \n" +
                   "       regjering.antall_partier_i_reg, regjering.id AS regkode, regjering_statssekretaerer.person as person_id, \n" +
                   "       politikere.initialer\n" +
                   "       FROM    politikere \n" +
                   "       RIGHT OUTER JOIN  regjering_statssekretaerer \n" +
                   "       LEFT OUTER JOIN   t_felles_partinavn ON regjering_statssekretaerer.parti_ved_start = t_felles_partinavn.kode ON \n" +
                   "       politikere.id = regjering_statssekretaerer.person   " +
                   "      LEFT OUTER JOIN   regjering_statssekretaerer_stilling ON regjering_statssekretaerer.stilling = regjering_statssekretaerer_stilling.id\n" +
                   "       LEFT OUTER JOIN\n" +
                   "       regjering_departement ON regjering_statssekretaerer.kode_dep = regjering_departement.kode \n" +
                   "       LEFT OUTER JOIN\n" +
                   "       regjering ON regjering_statssekretaerer.min_reg_HV = regjering.min_reg  "

                   + (condition != null ? " " + condition : "");

           sqlCB.setConnection(this.conn);
           sqlCB.setSqlValue(sqlSelect); //sporring
           sqlCB.setValues(values); //parameter
           result = sqlCB.executeQuery();
           rader = result.getRows();
           ministerregjering = new MinisterRegjering[rader.length];
              int kode = 0;
       for (int i = 0; i < rader.length; i++) {
                 ministerregjering[i] = new MinisterRegjering();
                 ministerregjering[i].setFornavn((String)rader[i].get("fornavn"));
                 ministerregjering[i].setEtternavn((String)rader[i].get("navn"));
                 ministerregjering[i].setPersonId((Integer)rader[i].get("person_id"));
                 if(rader[i].get("foedt") !=null){
                 ministerregjering[i].setFoedt((Integer)rader[i].get("foedt"));
                 }
               if(rader[i].get("doedsaar") !=null){
                 ministerregjering[i].setDoed((Integer)rader[i].get("doedsaar"));
               }
           if (this.engelsk) {
               ministerregjering[i].setStilling((String) rader[i].get("stilling_avvik_en"));
               ministerregjering[i].setEksternkommentar((String)rader[i].get("engEksternkommentar"));
               if(kode !=(Integer)rader[i].get("kode")){
                   ministerregjering[i].setStatsraadnavn((String)rader[i].get("eintaltekst_en"));
               }
           }else{
               ministerregjering[i].setStilling((String) rader[i].get("stilling_avvik_no"));
               ministerregjering[i].setEksternkommentar((String)rader[i].get("eksternkommentar"));
               if(kode !=(Integer)rader[i].get("kode")){
                   ministerregjering[i].setStatsraadnavn((String)rader[i].get("eintaltekst_no"));
               }
           }

                 ministerregjering[i].setAntall_parti_i_reg((Integer)rader[i].get("antall_partier_i_reg"));
                 ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("regkode"));
                 ministerregjering[i].setPartikortnavn((String)rader[i].get("eintaltekst_forkorting"));


                   kode =(Integer)rader[i].get("kode");

                   ministerregjering[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                    //ministerregjering[i].setSlutt(rader[i].get("sluttday")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttyear"));
                if(rader[i].get("sluttyear").equals(9999)){ministerregjering[i].setSlutt("  ");}
                   else {
                    ministerregjering[i].setSlutt(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
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
    

     private MinisterRegjering[] getStatssekretarRegjeringsvis2(String condition, List values) throws Exception {
       // tabell som returneres fra denne metoden.
       MinisterRegjering[] ministerregjering = null;
       // resultat fra sql-sporring.
       Result result = null;
       // objekt som brukes til aa utfore sql-sporring.
       SqlCommandBean sqlCB = new SqlCommandBean();
       // inneholder data fra sql-sporring.
       SortedMap[] rader = null;
       // SQL-sporring.
       String sqlSelect = "SELECT\tregjering.navn_nb as NOstatsraadsbetegnelse, regjering.navn_en as ENGstatsraadsbetegnelse, DAY(regjering.start)AS startday,  MONTH(regjering.start)AS startmonth, YEAR(regjering.start)AS startyear, DAY(regjering.slutt) AS sluttday , MONTH(regjering.slutt) AS sluttmonth, YEAR(regjering.slutt) AS sluttyear, regjering.id as Regkode,\n" +
               "        regjering.min_reg \n" +
               "\t\t, STUFF(( \n" +
               "                        SELECT N'+ ' + CAST([eintaltekst_forkorting] AS VARCHAR(255)) \n" +
               "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti \n" +
               "                        WHERE regjering_partier.regjering = regjering.id  \n" +
               "                        FOR XML PATH ('')), 1, 1, '') AS partier , STUFF(( \n" +
               "                        SELECT N', ' + CAST([kode] AS VARCHAR(255)) \n" +
               "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
               "                        WHERE regjering_partier.regjering = regjering.id  \n" +
               "                        FOR XML PATH ('')), 1, 1, '') AS partierkode\n" +
               "  , STUFF(( \n" +
               "                        SELECT N', ' + CAST([eintaltekst] AS VARCHAR(255)) \n" +
               "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
               "                        WHERE regjering_partier.regjering = regjering.id  \n" +
               "                        FOR XML PATH ('')), 1, 1, '') AS Partier_tekst\n" +
               "  , STUFF(( \n" +
               "                        SELECT N', ' + CAST([ENGeintaltekst] AS VARCHAR(255)) \n" +
               "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
               "                        WHERE regjering_partier.regjering = regjering.id  \n" +
               "                        FOR XML PATH ('')), 1, 1, '') AS Partier_tekst_en\n" +
               "                        , STUFF(( \n" +
               "                        SELECT N', ' + CAST([stortingperiode] AS VARCHAR(255))\n" +
               "                        FROM regjering_stortingperiode \n" +
               "                        WHERE regjering_stortingperiode.regjering = regjering.id \n" +
               "                        FOR XML PATH ('')), 1, 1, '') AS stortingperiodekode \n" +
               "        FROM regjering\t\t\n"
               + (condition != null ? " " + condition : "");

       sqlCB.setConnection(this.conn);
       sqlCB.setSqlValue(sqlSelect); //sporring
       sqlCB.setValues(values); //parameter
       result = sqlCB.executeQuery();
       rader = result.getRows();
       ministerregjering = new MinisterRegjering[rader.length];

   for (int i = 0; i < rader.length; i++) {
               ministerregjering[i] = new MinisterRegjering();

       if (this.engelsk) {
           ministerregjering[i].setMinisterium((String)rader[i].get("ENGstatsraadsbetegnelse"));
           ministerregjering[i].setPartinavn((String)rader[i].get("partier_tekst_en"));
       }else{
           ministerregjering[i].setMinisterium((String)rader[i].get("NOstatsraadsbetegnelse"));
           ministerregjering[i].setPartinavn((String)rader[i].get("partier_tekst"));
       }

       ministerregjering[i].setStartdato(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
       ministerregjering[i].setSluttdato(rader[i].get("sluttyear") + "-" + rader[i].get("sluttmonth") + "-" + rader[i].get("sluttday"));

              ministerregjering[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
             if(rader[i].get("sluttyear").equals(9999)){ministerregjering[i].setSlutt("  ");}
                else {
                ministerregjering[i].setSlutt(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
            }

                ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("min_reg"));
        if(rader[i].get("partierkode")!=null){
               ministerregjering[i].setPartikode((String)rader[i].get("partierkode"));
             }
       if(rader[i].get("StortingPeriodeKode")!=null){
           ministerregjering[i].setStortingperiodekode((String)rader[i].get("StortingPeriodeKode"));
       }

           }
       return ministerregjering;
   }




    /*

  // stortingperioder


   private MinisterRegjering[] getStortingperioder(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     kode, fleirtaltekst, sumstortingsseter\n" +
                "FROM         t_storting_stortingsperioder_before1945 "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];

    for (int i = 0; i < rader.length; i++) {
                ministerregjering[i] = new MinisterRegjering();
                ministerregjering[i].setFleirtaltekst((String)rader[i].get("fleirtaltekst"));
                ministerregjering[i].setStorting_periodekode((Integer)rader[i].get("kode"));
                ministerregjering[i].setSumstortingsseter((Integer)rader[i].get("sumstortingsseter"));
            }
        return ministerregjering;
    }  



   //  SUMPosisjon


    private MinisterRegjering[] getSumposisjon(String condition,  List values) throws Exception {
        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     t_storting_person_hist.stortingperiode_kode, COUNT(t_storting_person_hist.parti_kode) AS NRepr, \n" +
                "                      t_storting_stortingsperioder_before1945.fleirtaltekst AS Stortingsperiode\n" +
                "FROM         t_storting_person_hist INNER JOIN\n" +
                "                      t_felles_partinavn ON t_storting_person_hist.parti_kode = t_felles_partinavn.kode INNER JOIN\n" +
                "                      t_storting_stortingsperioder_before1945 ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder_before1945.kode "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];

    for (int i = 0; i < rader.length; i++) {
                ministerregjering[i] = new MinisterRegjering();
                ministerregjering[i].setRep_nr((Integer)rader[i].get("NRepr"));
                 ministerregjering[i].setStorting_periodekode((Integer)rader[i].get("stortingperiode_kode"));
            }
        return ministerregjering;
    }  


    //Posisjon

    private MinisterRegjering[] getposisjon(String condition,  List values) throws Exception {
        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT   t_storting_person_hist.stortingperiode_kode,  t_storting_person_hist.parti, t_felles_partinavn.parti_bruksnavn, COUNT(t_storting_person_hist.parti_kode) AS NRepr, \n" +
                "                      t_storting_stortingsperioder_before1945.fleirtaltekst AS Stortingsperiode\n" +
                "FROM         t_storting_person_hist INNER JOIN\n" +
                "                      t_felles_partinavn ON t_storting_person_hist.parti_kode = t_felles_partinavn.kode INNER JOIN\n" +
                "                      t_storting_stortingsperioder_before1945 ON t_storting_person_hist.stortingperiode_kode = t_storting_stortingsperioder_before1945.kode "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];

    for (int i = 0; i < rader.length; i++) {
                ministerregjering[i] = new MinisterRegjering();
                ministerregjering[i].setPartinavn((String)rader[i].get("parti_bruksnavn"));    
                ministerregjering[i].setRep_nr((Integer)rader[i].get("NRepr"));
                 ministerregjering[i].setStorting_periodekode((Integer)rader[i].get("stortingperiode_kode"));
            }
        return ministerregjering;
    }


   */


}

