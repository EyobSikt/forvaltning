package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.MinisterRegjering;

import javax.servlet.jsp.jstl.sql.Result;
import java.io.Serializable;
import java.sql.Connection;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 24.nov.2010
 * Time: 12:27:57
 * To change this template use File | Settings | File Templates.
 */
public class MinisterRegjeringLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;

    // ============================================================ Konstrukt�r
    /**
     * Tom konstrukt�r.
     */
    public MinisterRegjeringLogikk() {
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
           String condition = " WHERE type=4 ORDER BY regjering.id asc ";
           return this.getNorskeministerRegjering(condition, null);
       }
     public MinisterRegjering[] getNorskeRegjeringer() throws Exception {
           String condition = " WHERE type=5 ORDER BY regjering.id asc ";
           return this.getNorskeministerRegjering(condition, null);
       }

    public MinisterRegjering[] getStortingperioder(String stortingperiode) throws Exception {
         String condition = "";
          ArrayList<String> list = new ArrayList<String>();
        if(stortingperiode != null){
        StringTokenizer st = new StringTokenizer(stortingperiode, ",");
        while (st.hasMoreTokens()) {
        list.add(st.nextToken());
        }
          if(!list.isEmpty()){
             condition +=   " WHERE id in ( " ;
            for(int i = 0; i < list.size(); i++){
           condition +=    list.get(i) + ", ";
           }
           condition +=    "  -1111 )";
         }
        }
           return this.getStortingperioder(condition, null);
     }

            /**
             * getSumpPosisjon
             * */

            public MinisterRegjering[] getSumPosisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (storting_representanter.representant_nr >= 1) AND (storting_representanter.periode >= 32) ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null){
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND ( storting_representanter.periode IN ( " ;
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
                     condition +=   " AND  ( storting_representanter.parti IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                 }
               }
                 condition += " GROUP BY storting_perioder.fleirtaltekst, storting_representanter.periode\n" +
                         "      ORDER BY storting_representanter.periode ";

                   return this.getSumposisjon(condition,  null);
              }
     /**
             * getSumpOPosisjon
             * */

            public MinisterRegjering[] getSumOposisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (storting_representanter.representant_nr >= 1) AND (storting_representanter.periode >= 32) ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null){
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND (storting_representanter.periode IN ( " ;
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
                     condition +=   " AND ( NOT (storting_representanter.parti IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 ))) ";
                 }
               }
                 condition += " GROUP BY storting_perioder.fleirtaltekst, storting_representanter.periode \n" +
                         "ORDER BY storting_representanter.periode ";

                   return this.getSumposisjon(condition,  null);
              }

    /**
     * getPosisjon
    * */
        public MinisterRegjering[] getPosisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (storting_representanter.representant_nr >= 1) AND (storting_representanter.periode >= 32) AND storting_representanter.parti is not null ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null) {
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND (storting_representanter.periode IN ( " ;
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
                     condition +=   " AND  (storting_representanter.parti IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 )) ";
                 }
                }
                 condition += " GROUP BY storting_representanter.parti, t_felles_partinavn.parti_bruksnavn, t_felles_partinavn.ENGparti_bruksnavn, storting_perioder.fleirtaltekst, storting_representanter.periode \n" +
                         "      ORDER BY storting_representanter.periode, COUNT(storting_representanter.parti) DESC ";

                   return this.getposisjon(condition,  null);
              }
   /**
     * getOposisjon
    * */
        public MinisterRegjering[] getOposisjon(String stortingperiode, String list_partikode) throws Exception {
                String condition = " WHERE  (storting_representanter.representant_nr >= 1) AND (storting_representanter.periode >= 32) AND storting_representanter.parti is not null ";
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> listpartikode = new ArrayList<String>();
                if(stortingperiode !=null){
                StringTokenizer st = new StringTokenizer(stortingperiode, ",");
                while (st.hasMoreTokens()) {
                list.add(st.nextToken());
                }
                  if(!list.isEmpty()){
                     condition +=   " AND (storting_representanter.periode IN ( " ;
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
                     condition +=   " AND (NOT (storting_representanter.parti IN ( " ;
                    for(int i = 0; i < listpartikode.size(); i++){
                   condition +=    listpartikode.get(i) + ", ";
                   }
                   condition +=    "  -1111 ))) ";
                 }
               }
                 condition += " GROUP BY storting_representanter.parti, t_felles_partinavn.parti_bruksnavn, t_felles_partinavn.ENGparti_bruksnavn, storting_perioder.fleirtaltekst, storting_representanter.periode \n" +
                         " ORDER BY storting_representanter.periode, COUNT(storting_representanter.parti) DESC ";

                   return this.getposisjon(condition,  null);
              }

     public MinisterRegjering[] getStatsraadsbeskrivelse(String fradato, String tildato) throws Exception {
           String condition = " WHERE  (regjering_medlemmer.start BETWEEN regjering.start AND regjering.slutt) AND \n" +
                   "                                         (regjering_medlemmer.slutt BETWEEN regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt) AND \n" +
                   "                                         ( regjering.start = ? ) AND \n" +
                   "                                         ( regjering.slutt = ? ) OR\n" +
                   "                                         (regjering_medlemmer.slutt BETWEEN regjering.start AND regjering.slutt) AND \n" +
                   "                                         ( regjering.start = ? ) AND \n" +
                   "                                         ( regjering.slutt = ? ) AND (regjering_medlemmer.slutt BETWEEN \n" +
                   "                                         regjering_departement.fra_tidspunkt AND regjering_departement.til_tidspunkt)\n" +
                   "                   ORDER BY regjering_departement.sortering, regjering_departement.navn_nb,regjering_departement.navn_en, regjering_medlemmer.stilling, regjering_medlemmer.start ";

            List<Serializable> values = new ArrayList<Serializable>();
           values.add(fradato);
           values.add(tildato);
           values.add(fradato);
           values.add(tildato);
           return this.getStatsraadsbeskrivelse(condition, values);
       }

    public MinisterRegjering[] getRegjeringsbeskrivelse(String fradato, String tildato) throws Exception {
             String condition = " WHERE    start = ? AND slutt = ? ";
              List<Serializable> values = new ArrayList<Serializable>();
             values.add(fradato);
             values.add(tildato);
             return this.getRegjeringsbeskrivelse(condition, values);
         }

/* public regjeringsarsakavgang*/
    public MinisterRegjering[] getRregjeringsarsakavgang(int reg_kode) throws Exception {
        String condition = " WHERE   regjering.id = ? ";
        List values = new ArrayList();
        values.add(reg_kode);
        return this.getRregjeringsarsakavgang(condition, values);
    }
   /* public regjeringsarsakavgang*/
    public MinisterRegjering[] getRegjeringsarsakavgangbeskrivelse(String tidskode) throws Exception {
        String condition = " WHERE   ";
        List values = new ArrayList();

        ArrayList<String> list = new ArrayList<String>();
        if(tidskode !=null){
            StringTokenizer st = new StringTokenizer(tidskode, ",");
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
            if(!list.isEmpty()){
                condition +=   "  (Kode IN ( " ;
                for(int i = 0; i < list.size(); i++){
                    condition +=    list.get(i) + ", ";
                }
                condition +=    "  -1111 )) ";
            }
        }
            //values.add(tidskode);
        return this.getRegjeringsarsakavgangbeskrivelse(condition, values);
    }
/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private MinisterRegjering[] getNorskeministerRegjering(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        /*
        String sqlSelect = "SELECT    kode, Aar, min_reg, Statsraadsbetegnelse as statsraadsbetegnelse, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, Flertall as flertall, Blokk, PartierKode, StortingPeriodeKode, Aarsak_avgang as aarsak_avgang,\n" +
                "\t\t Tidstavle_avgang, Partier as partier, partier_tekst as partier_tekst, Ap, H, V, KrF, \n" +
                "                      Sp, Fv, NKP, Mv, SV, Saml, Ad\n" +
                "FROM         t_regjering_norske_regjeringer_ministerier "
                + (condition != null ? " " + condition : "");
        */
        String sqlSelect = "SELECT DISTINCT " +
                "        regjering.id, YEAR(start) as aar, type, navn_nb, navn_en, DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, " +
                "    YEAR(slutt) AS sluttyear, tekst_entall_nb, tekst_entall_en, blokk, aarsak_avgangs_nb,  aarsak_avgangs_en, regjering.tidstavle_avgang " +
                "    , STUFF(( " +
                "        SELECT N'+ ' + CAST([eintaltekst_forkorting] AS VARCHAR(255)) " +
                "        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti " +
                "        WHERE regjering_partier.regjering = regjering.id  " +
                "        FOR XML PATH ('')), 1, 1, '') AS partier , STUFF(( \n" +
                "        SELECT N', ' + CAST([kode] AS VARCHAR(255)) \n" +
                "        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti \n" +
                "        WHERE regjering_partier.regjering = regjering.id  \n" +
                "        FOR XML PATH ('')), 1, 1, '') AS partierkode\n" +
                "        , STUFF(( \n" +
                "        SELECT N', ' + CAST([stortingperiode] AS VARCHAR(255)) \n" +
                "        FROM regjering_stortingperiode \n" +
                "        WHERE regjering_stortingperiode.regjering = regjering.id  \n" +
                "        FOR XML PATH ('')), 1, 1, '') AS stortingperiodekode  " +
                "  FROM regjering left outer join regjering_parlamentarisk_grunnlag on regjering_parlamentarisk_grunnlag.id = regjering.grunnlag "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];

    for (int i = 0; i < rader.length; i++) {
                ministerregjering[i] = new MinisterRegjering();
                ministerregjering[i].setRegjeringskode((Integer)rader[i].get("id"));
                if(rader[i].get("tidstavle_avgang")!=null) {
                 ministerregjering[i].setTidskode( (String) rader[i].get("tidstavle_avgang"));
                }
                ministerregjering[i].setAar((Integer)rader[i].get("aar"));
                ministerregjering[i].setBlokk((Integer)rader[i].get("blokk"));
                if (this.engelsk) {
                ministerregjering[i].setMinisterium((String)rader[i].get("navn_en"));
                ministerregjering[i].setRegjeringtype((String)rader[i].get("tekst_entall_en"));
                ministerregjering[i].setAarsakavgang((String)rader[i].get("aarsak_avgangs_en"));
                } else {
                 ministerregjering[i].setMinisterium((String)rader[i].get("navn_nb"));
                 ministerregjering[i].setRegjeringtype((String)rader[i].get("tekst_entall_nb"));
                 ministerregjering[i].setAarsakavgang((String)rader[i].get("aarsak_avgangs_nb"));
                }
                ministerregjering[i].setRegjeringparti((String)rader[i].get("partier"));
                ministerregjering[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
                ministerregjering[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));
               ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("type"));
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
   * stortingperioder
   * */

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
        String sqlSelect = "SELECT     id as kode, fleirtaltekst, seter\n" +
                "FROM       storting_perioder "
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
                ministerregjering[i].setSumstortingsseter((Integer)rader[i].get("seter"));
            }
        return ministerregjering;
    }  

   /**
    * SUMPosisjon
    * */
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
        String sqlSelect = " SELECT  storting_representanter.periode, COUNT(storting_representanter.parti) AS NRepr, storting_perioder.fleirtaltekst AS Stortingsperiode\n" +
                "                FROM storting_representanter INNER JOIN\n" +
                "                     t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "                     storting_perioder ON storting_representanter.periode = storting_perioder.id "
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
                 ministerregjering[i].setStorting_periodekode((Integer)rader[i].get("periode"));
            }
        return ministerregjering;
    }  

   /**
    * Posisjon
    * */
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
        String sqlSelect = "SELECT storting_representanter.periode,  storting_representanter.parti, t_felles_partinavn.parti_bruksnavn, t_felles_partinavn.ENGparti_bruksnavn,  COUNT(storting_representanter.parti) AS NRepr, \n" +
                "         storting_perioder.fleirtaltekst AS Stortingsperiode\n" +
                "         FROM storting_representanter INNER JOIN\n" +
                "         t_felles_partinavn ON storting_representanter.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "         storting_perioder ON storting_representanter.periode = storting_perioder.id "
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
                ministerregjering[i].setPartinavn_en((String)rader[i].get("ENGparti_bruksnavn"));
                ministerregjering[i].setRep_nr((Integer)rader[i].get("NRepr"));
                ministerregjering[i].setStorting_periodekode((Integer)rader[i].get("periode"));
            }
        return ministerregjering;
    }

 /**
  * regjeringsbeskrivelse
  * */

 private MinisterRegjering[] getStatsraadsbeskrivelse(String condition, List values) throws Exception {
       // tabell som returneres fra denne metoden.
       MinisterRegjering[] ministerregjering = null;
       // resultat fra sql-sporring.
       Result result = null;
       // objekt som brukes til aa utfore sql-sporring.
       SqlCommandBean sqlCB = new SqlCommandBean();
       // inneholder data fra sql-sporring.
       SortedMap[] rader = null;
       // SQL-sporring.
       String sqlSelect = "SELECT    YEAR(politikere.fodt) AS foedt , YEAR(politikere.doed) AS doedsaar, regjering_departement.navn_nb, regjering_departement.navn_en, regjering_departement.id as kode, regjering_medlemmer_stilling.embete as stilling_avvik, \n" +
               "                      regjering_medlemmer_stilling.engembete as stilling_avvik_en, DAY(regjering_medlemmer.start) AS startday,  MONTH(regjering_medlemmer.start) AS startmonth, YEAR(regjering_medlemmer.start) AS startyear, DAY(regjering_medlemmer.slutt) AS sluttday , MONTH(regjering_medlemmer.slutt) AS sluttmonth, YEAR(regjering_medlemmer.slutt) AS sluttyear, regjering_medlemmer.kommentar_ekstern_nb,  regjering_medlemmer.kommentar_ekstern_en, politikere.fornavn, \n" +
               "                                     politikere.etternavn, regjering_medlemmer.parti_ved_start, t_felles_partinavn.eintaltekst_forkorting as eintaltekst_forkorting, \n" +
               "                                     regjering.antall_partier_i_reg, regjering.id AS regkode, regjering_medlemmer.person, \n" +
               "                                     politikere.initialer\n" +
               "                               FROM     politikere \n" +
               "                               RIGHT OUTER JOIN regjering_medlemmer \n" +
               "                           LEFT OUTER JOIN t_felles_partinavn ON regjering_medlemmer.parti_ved_start = t_felles_partinavn.kode ON \n" +
               "                           politikere.id = regjering_medlemmer.person \n" +
               "                           LEFT OUTER JOIN regjering_departement ON regjering_medlemmer.departement = regjering_departement.id \n" +
               "                           LEFT OUTER JOIN regjering_medlemmer_stilling ON regjering_medlemmer.kode_embete = regjering_medlemmer_stilling.id \n" +
               "                          LEFT OUTER JOIN regjering ON regjering_medlemmer.type = regjering.type  "

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
             ministerregjering[i].setEtternavn((String)rader[i].get("etternavn"));
             ministerregjering[i].setPersonId((Integer)rader[i].get("person"));
            if(rader[i].get("foedt") !=null) {
            ministerregjering[i].setFoedt((Integer) rader[i].get("foedt"));
            }
            if(rader[i].get("doedsaar") !=null){
             ministerregjering[i].setDoed((Integer)rader[i].get("doedsaar"));
           }

            if (this.engelsk) {
                ministerregjering[i].setStilling((String)rader[i].get("stilling_avvik_en"));
                ministerregjering[i].setEksternkommentar((String)rader[i].get("kommentar_ekstern_en"));
                if(kode !=(Integer)rader[i].get("kode")) {
                    ministerregjering[i].setStatsraadnavn((String) rader[i].get("navn_en"));
                }
            }
            else{
                ministerregjering[i].setStilling((String)rader[i].get("stilling_avvik"));
                ministerregjering[i].setEksternkommentar((String)rader[i].get("kommentar_ekstern_nb"));
                if(kode !=(Integer)rader[i].get("kode")) {
                    ministerregjering[i].setStatsraadnavn((String) rader[i].get("navn_nb"));
                }
            }
       kode =(Integer)rader[i].get("kode");
             ministerregjering[i].setAntall_parti_i_reg((Integer)rader[i].get("antall_partier_i_reg"));
             ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("regkode"));
             ministerregjering[i].setPartikortnavn((String)rader[i].get("eintaltekst_forkorting"));

              if(kode !=(Integer)rader[i].get("kode")){
               //ministerregjering[i].setStatsraadnavn((String)rader[i].get("navn_nb"));
              }
               kode =(Integer)rader[i].get("kode");

               ministerregjering[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
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


    private MinisterRegjering[] getRegjeringsbeskrivelse(String condition, List values) throws Exception {
       // tabell som returneres fra denne metoden.
       MinisterRegjering[] ministerregjering = null;
       // resultat fra sql-sporring.
       Result result = null;
       // objekt som brukes til aa utfore sql-sporring.
       SqlCommandBean sqlCB = new SqlCommandBean();
       // inneholder data fra sql-sporring.
       SortedMap[] rader = null;
       // SQL-sporring.
       /*
        String sqlSelect = "SELECT Statsraadsbetegnelse as statsraadsbetegnelse,start,  slutt,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, kode as regkode, PartierKode, \n" +
               " min_reg, partier_tekst as partier_tekst, (DATEDIFF(day, start, GETDATE())) AS Antdag\n" +
               "FROM t_regjering_norske_regjeringer_ministerier  "
               + (condition != null ? " " + condition : "");
       */
        String sqlSelect = " SELECT navn_nb, navn_en, start,  slutt,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, id as regkode,  \n" +
                "                 type as min_reg,  (DATEDIFF(day, start, slutt)+1) AS Antdag,\n" +
                "\t\t\t\t  STUFF((  SELECT N'+ ' + CAST([eintaltekst_forkorting] AS VARCHAR(255)) \n" +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
                "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS PartierKode,  " +
                "                       STUFF((  SELECT N', ' + CAST([eintaltekst] AS VARCHAR(255)) \n" +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti\n" +
                "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS partier_tekst   " +
                "                FROM regjering  "
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
           ministerregjering[i].setMinisterium((String)rader[i].get("navn_en"));
       }else {
           ministerregjering[i].setMinisterium((String)rader[i].get("navn_nb"));
       }


       ministerregjering[i].setStartdato(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
       ministerregjering[i].setSluttdato(rader[i].get("sluttyear") + "-" + rader[i].get("sluttmonth") + "-" + rader[i].get("sluttday"));

       ministerregjering[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
              if(rader[i].get("sluttyear").equals(9999)){ministerregjering[i].setSlutt("  ");}
              else {
              ministerregjering[i].setSlutt(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
              }
              ministerregjering[i].setPartinavn((String)rader[i].get("partier_tekst"));
              ministerregjering[i].setRegjering_reg_kode((Integer)rader[i].get("regkode"));
              ministerregjering[i].setRegjering_min_reg((Integer)rader[i].get("min_reg"));
              ministerregjering[i].setPartikode((String)rader[i].get("partierkode"));
       
        //long x ;
       // final long ONE_HOUR = 60 * 60 * 1000L;
       // x = ( (((Date)rader[i].get("slutt")).getTime() - ((Date)rader[i].get("start")).getTime() + ONE_HOUR) / (ONE_HOUR * 24)+1);
         //ministerregjering[i].setDato((int) x);
       Date date = new Date();
       long diffInMillies = date.getTime() - ((Date)rader[i].get("start")).getTime();
       if(rader[i].get("sluttyear").equals(9999)){
           ministerregjering[i].setDato((int) (diffInMillies/(1000 * 60 * 60 * 24)));
       }else {
           ministerregjering[i].setDato((Integer) rader[i].get("Antdag"));
       }
        }
       return ministerregjering;
   }



/*regjeringsarsakavgang*/
    private MinisterRegjering[] getRregjeringsarsakavgang(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  regjering.id, regjering.start, regjering.slutt, DAY(regjering.slutt) as sluttday, MONTH(regjering.slutt) as sluttmonth, YEAR(regjering.slutt) as sluttyear, regjering.navn_nb\n" +
                "FROM    regjering\n"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];

        for (int i = 0; i < rader.length; i++) {
            ministerregjering[i] = new MinisterRegjering();
            ministerregjering[i].setFleirtaltekst((String)rader[i].get("navn_nb"));
            ministerregjering[i].setSluttdato(rader[i].get("sluttday") + "." + rader[i].get("sluttmonth") + "." + rader[i].get("sluttyear"));
        }
        return ministerregjering;
    }
    /*regjeringsarsakavgang*/
    private MinisterRegjering[] getRegjeringsarsakavgangbeskrivelse(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        MinisterRegjering[] ministerregjering = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  Aarstal, Maanad, Dato, Dokumentasjon, Kode\n" +
                "FROM Tidstavle "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        ministerregjering = new MinisterRegjering[rader.length];

        for (int i = 0; i < rader.length; i++) {
            ministerregjering[i] = new MinisterRegjering();
            ministerregjering[i].setFleirtaltekst((String)rader[i].get("Dokumentasjon"));
            ministerregjering[i].setSluttdato(rader[i].get("Dato") + "." + rader[i].get("Maanad") + "." + rader[i].get("Aarstal"));
        }
        return ministerregjering;
    }



  /*  
   public List<MinisterRegjering> getRegjeringsbeskrivelse_lastned(int fraaar, int tilaar) throws Exception {

        ArrayList<MinisterRegjering> list = new ArrayList<MinisterRegjering>();
       Result result = null;
       // objekt som brukes til aa utfore sql-sporring.
       SqlCommandBean sqlCB = new SqlCommandBean();
       // inneholder data fra sql-sporring.
       SortedMap[] rader = null;
       // SQL-sporring.
       String sqlSelect = "SELECT statsraadsbetegnelse as statsraadsbetegnelse,start,  slutt,  DAY(start) AS startday, MONTH(start) AS startmonth , YEAR(start) AS startyear , DAY(slutt) AS sluttday, MONTH(slutt) AS sluttmonth, YEAR(slutt) AS sluttyear, kode as regkode, partierkode, \n" +
               " min_reg, partier_tekst as partier_tekst\n" +
               "FROM t_regjering_regjeringer_ministerier WHERE  start is not null ";

          if(fraaar !=-1){
        sqlSelect+=  " AND ( YEAR(t_regjering_regjeringer_ministerier.start) = ?)  ";
       }
         if(tilaar !=-1){
           sqlSelect+=  " AND ( YEAR(t_regjering_regjeringer_ministerier.slutt) = ?) ";
         }

        PreparedStatement sta = conn.prepareStatement(sqlSelect);
        if(fraaar !=-1){
        sta.setInt(1,fraaar);
        }
        if(tilaar !=-1){
        sta.setInt(2,tilaar);
        }

       ResultSet rs = sta.executeQuery();

   for (int i = 0; i < rader.length; i++) {
          MinisterRegjering   ministerregjering = new MinisterRegjering();

              ministerregjering.setMinisterium(rs.getString("statsraadsbetegnelse"));
               ministerregjering.setStart(rs.getString("startyear")+"-"+rs.getString("startmonth")+"-"+rs.getString("startday"));
                ministerregjering.setSlutt(rs.getString("sluttyear")+"-"+rs.getString("sluttmonth")+"-"+rs.getString("sluttday"));
               ministerregjering.setPartinavn(rs.getString("partier_tekst"));
               ministerregjering.setRegjering_reg_kode(rs.getInt("regkode"));
               ministerregjering.setRegjering_min_reg(rs.getInt("min_reg"));
              ministerregjering.setPartikode(rs.getString("partierkode"));

        long x ;
        final long ONE_HOUR = 60 * 60 * 1000L;
        x = ( (((Date)rs.getDate("slutt")).getTime() - ((Date)rs.getDate("start")).getTime() + ONE_HOUR) / (ONE_HOUR * 24)+1);

         ministerregjering.setDato((int) x);

        list.add(ministerregjering);

        }
       return list;
   }

 */

}

