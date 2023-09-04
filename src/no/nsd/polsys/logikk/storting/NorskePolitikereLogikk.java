package no.nsd.polsys.logikk.storting;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.storting.NorskePolitikere;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 25.nov.2010
 * Time: 08:46:35
 * To change this template use File | Settings | File Templates.
 */
public class NorskePolitikereLogikk {
    /** Forbindelse til databasen. */
       private Connection conn;
       /** Settes til true hvis en vil ha engelsk. */
       private boolean engelsk = false;


       public NorskePolitikereLogikk() {
       }

       // ================================================================ Metoder

       public void setConn(Connection conn) {
           this.conn = conn;
       }

       public void brukEngelsk() {
           this.engelsk = true;
       }

// Skriv metoder ------------------------------------------------------------------------------------

    /**
     * Setter databaseforbindelsen for denne bean'en.
     *
     * @param conn databaseforbindelsen.
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    /*skrive metoder*/


    public NorskePolitikere[] getPartiSammensetning() throws Exception {
        String condition = " ORDER BY t_felles_partinavn.eintaltekst ";

        return this.getPartiSammensetning(condition, null);
    }

    public NorskePolitikere[] getAarSammensetning() throws Exception {
        String condition = "ORDER BY storting_perioder.valgaar DESC";
        return this.getSammensetning_aar(condition, null);
    }

      public NorskePolitikere[] getNorskePolitikere(String navn, String[] bs,  String st, String sr, String ss, List periode, List aar, List partikode, String sortresult) throws Exception {

          String condition = " WHERE politikere.id IS NOT NULL AND (politikere.etternavn IS NOT NULL)   ";
          if(navn != null ){
              condition += " AND politikere.etternavn LIKE ?  ";
          }
          if(st !=null && st.equalsIgnoreCase("storting") && sr.equals("") && ss.equals("") ){
              condition += " AND (storting_representanter.periode) >=-1 ";
          }
          if(sr !=null && sr.equalsIgnoreCase("statsraad") && st.equals("") && ss.equals("") ){
              condition += " AND (regjering_medlemmer.person) is not null ";
          }
          if(ss !=null && ss.equalsIgnoreCase("statssekretar") && st.equals("") && sr.equals("") ){
              condition += " AND (regjering_statssekretaerer.person) is not null ";
          }
          if(st !=null && st.equalsIgnoreCase("storting") && sr !=null && sr.equalsIgnoreCase("statsraad") && ss.equals("")){
              condition += " AND (storting_representanter.periode >=-1 OR regjering_medlemmer.person is not null) ";
          }
          if(st !=null && st.equalsIgnoreCase("storting") && ss !=null && ss.equalsIgnoreCase("statssekretar") && sr.equals("") ){
              condition += " AND (storting_representanter.periode >=-1 OR regjering_statssekretaerer.person is not null) ";
          }
          if(ss !=null && ss.equalsIgnoreCase("statssekretar") && sr !=null && sr.equalsIgnoreCase("statsraad") && st.equals("") ){
              condition += " AND (regjering_statssekretaerer.person is not null OR regjering_medlemmer.person is not null) ";
          }
          if(st !=null && st.equalsIgnoreCase("storting") && sr !=null && sr.equalsIgnoreCase("statsraad") && ss !=null && ss.equalsIgnoreCase("statssekretar")){
              condition += " AND (storting_representanter.periode >=-1 OR regjering_medlemmer.person is not null OR regjering_statssekretaerer.person is not null) ";
          }


          if(bs !=null &&  !bs.equals("") && bs.length>0 ){
              condition += " AND  (    ";
              condition += "   politikere.etternavn LIKE ?    ";
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
          if(sortresult!=null && sortresult.equals("aarstallsynkende")){condition +=" ORDER BY politikere.foedtaar DESC ";}
          if(sortresult!=null && sortresult.equals("aarstallstigende")){condition +=" ORDER BY politikere.foedtaar ASC ";}

       /*
        String condition = " WHERE t_felles_person.person_id IS NOT NULL AND (t_felles_person.navn IS NOT NULL)   ";
          if(navn != null ){
               condition += " AND t_felles_person.navn LIKE ?  ";
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


          if(bs !=null &&  !bs.equals("") && bs.length>0 ){
              condition += " AND  (    ";
               condition += "   t_felles_person.navn LIKE ?    ";
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
*/

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

         return this.getAlleNorskePolitikere(condition, values);
    }

/*
      public NorskePolitikere[] getAlleNorskePolitikere2(String bokstave) throws Exception {
        String condition = "HAVING (t_felles_person.person_id IS NOT NULL) AND  (t_felles_statsraader.person_id IS NULL) \n" +
                "       AND (t_felles_statssekretaerer.person_id IS NULL) AND (t_felles_person.navn LIKE ? ) OR\n" +
                "       (t_felles_statsraader.person_id IS NOT NULL) AND (t_felles_person.navn LIKE ? ) OR\n" +
                "       (t_felles_statssekretaerer.person_id IS NOT NULL) AND (t_felles_person.navn LIKE ? ) ";
          condition +=" ORDER BY t_felles_person.navn, t_felles_person.fornavn";
        List values = new ArrayList();
        values.add(bokstave+"%");
        values.add(bokstave+"%");
        values.add(bokstave+"%");
         return this.getNorskePolitikere(condition, values);
    }
*/
     /*1814-1903*/
    /*
     public NorskePolitikere[] getSokNorskePolitikere_1814(String navn) throws Exception {
        String condition = "HAVING (t_felles_person.navn LIKE ?) AND  MAX(DISTINCT t_storting_person_hist.stortingperiode_kode) < 52 ";

          condition +=" ORDER BY t_storting_person_hist.person_id, MAX(DISTINCT t_storting_person_hist.stortingperiode_kode)";
        List values = new ArrayList();
        values.add("%"+navn+"%");
         return this.getSokNorskePolitikere(condition, values);
    }
    */
     /*1905-1945*/
    /*
    public NorskePolitikere[] getSokNorskePolitikere_1905(String navn) throws Exception {
        String condition = "HAVING (t_felles_person.navn LIKE ?) AND  MAX(DISTINCT t_storting_person_hist.stortingperiode_kode) < 89 AND MAX(DISTINCT t_storting_person_hist.stortingperiode_kode) > 52 ";

          condition +=" ORDER BY t_storting_person_hist.person_id, MAX(DISTINCT t_storting_person_hist.stortingperiode_kode)";
        List values = new ArrayList();
        values.add("%"+navn+"%");
         return this.getSokNorskePolitikere(condition, values);
    }
     */
     /*1945 - */
    /*
    public NorskePolitikere[] getSokNorskePolitikere_1945(String navn) throws Exception {
        String condition = "HAVING (t_felles_person.navn LIKE ?) AND  MAX(DISTINCT t_storting_person_hist.stortingperiode_kode) > 89 ";

          condition +=" ORDER BY t_storting_person_hist.person_id, MAX(DISTINCT t_storting_person_hist.stortingperiode_kode)";
        List values = new ArrayList();
        values.add("%"+navn+"%");
         return this.getSokNorskePolitikere(condition, values);
    }
*/



   /*lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder */
    /*lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder lese metoder */

      private NorskePolitikere[] getAlleNorskePolitikere(String condition, List values) throws Exception {
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
        String sqlSelect = "SELECT   DISTINCT  t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn,  \n" +
                "     t_felles_statsraader.person_id AS sr_person_id, t_felles_statssekretaerer.person_id AS ss_person_id, t_felles_person.initialer, \n" +
                "     t_felles_person.faar, t_felles_person.dodsaar\n" +
                "     FROM  t_felles_person LEFT OUTER JOIN\n" +
                "     t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id LEFT OUTER JOIN  \n" +
                "     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id \n"
                + (condition != null ? " " + condition : "");
        */

          String sqlSelect = " SELECT   DISTINCT  politikere.id, politikere.fornavn, politikere.etternavn,  \n" +
                  "            regjering_medlemmer.person AS sr_person_id, regjering_statssekretaerer.person AS ss_person_id, politikere.initialer, \n" +
                  "            politikere.fodt, YEAR(politikere.fodt) AS faar, politikere.foedtaar, politikere.doed, YEAR(politikere.doed) AS dodsaar    " +
                  "            , STUFF(( SELECT distinct N', ' + CAST([eintaltekst] AS VARCHAR(255)) \n" +
                  "            FROM storting_representanter  inner join t_felles_partinavn on t_felles_partinavn.kode = storting_representanter.parti\n" +
                  "            WHERE storting_representanter.person = politikere.id  \n" +
                  "            FOR XML PATH ('')), 1, 1, '') AS parti \n  " +
                  "            , STUFF(( SELECT distinct N', ' + CAST([ENGeintaltekst] AS VARCHAR(255)) \n" +
                  "            FROM storting_representanter  inner join t_felles_partinavn on t_felles_partinavn.kode = storting_representanter.parti\n" +
                  "            WHERE storting_representanter.person = politikere.id  \n" +
                  "            FOR XML PATH ('')), 1, 1, '') AS parti_en     " +
                  "            , STUFF(( SELECT distinct N', ' + CAST([fleirtaltekst] AS VARCHAR(255)) \n" +
                  "            FROM storting_representanter inner join storting_perioder on storting_perioder.id = storting_representanter.periode\n" +
                  "            WHERE storting_representanter.person = politikere.id  \n" +
                  "            FOR XML PATH ('')), 1, 1, '') AS stortingsrepresentant   " +
                  "            FROM  politikere LEFT OUTER JOIN\n" +
                  "            storting_representanter ON storting_representanter.person = politikere.id LEFT OUTER JOIN  \n" +
                  "            regjering_medlemmer ON politikere.id = regjering_medlemmer.person LEFT OUTER JOIN\n" +
                  "            regjering_statssekretaerer ON politikere.id = regjering_statssekretaerer.person "
                  + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        norskepolitikere = new NorskePolitikere[rader.length];
          for (int i = 0; i < rader.length; i++) {

              byte statssekretar[] = "statssekretær".getBytes("ISO-8859-1");
              byte statsraad[] = "statsråd".getBytes("ISO-8859-1");

              String no_statssekretar = new String(statssekretar, "UTF-8");
              String no_statsraad = new String(statsraad, "UTF-8");

              norskepolitikere[i] = new NorskePolitikere();
              norskepolitikere[i].setEtterNavn((String) rader[i].get("etternavn"));
              norskepolitikere[i].setForNavn((String) rader[i].get("fornavn"));
              norskepolitikere[i].setInitialer((String) rader[i].get("initialer"));

              if(rader[i].get("dodsaar")!=null ){norskepolitikere[i].setDoedsaar((Integer) rader[i].get("dodsaar"));}
              if(rader[i].get("faar")!=null ){ norskepolitikere[i].setFaar((Integer) rader[i].get("faar"));}
              else if (rader[i].get("foedtaar")!=null){norskepolitikere[i].setFaar((Integer) rader[i].get("foedtaar"));}

              norskepolitikere[i].setPersonId((Integer) rader[i].get("id"));
              if(rader[i].get("ss_person_id")==null){norskepolitikere[i].setStatssekretar("");}else{
                  norskepolitikere[i].setSsPersonId((Integer) rader[i].get("ss_person_id"));
                  if (this.engelsk) {norskepolitikere[i].setStatssekretar("State Secretary");}else{norskepolitikere[i].setStatssekretar(no_statssekretar);}
              }
              if(rader[i].get("sr_person_id")==null){norskepolitikere[i].setStatsraad(" ");}else{
                  norskepolitikere[i].setStPersonId((Integer) rader[i].get("sr_person_id"));
                  if (this.engelsk){norskepolitikere[i].setStatsraad("Minister");}else{norskepolitikere[i].setStatsraad(no_statsraad);}
              }
              if (this.engelsk) {
                  if (rader[i].get("parti_en") == null) {
                  } else {
                      norskepolitikere[i].setPartinavn((String) rader[i].get("parti_en"));
                  }
              }else{
                  if (rader[i].get("parti") == null) {
                  } else {
                      norskepolitikere[i].setPartinavn((String) rader[i].get("parti"));
                  }
              }
              if(rader[i].get("stortingsrepresentant")==null){ norskepolitikere[i].setPeriode((String) rader[i].get(" "));}else{
                  norskepolitikere[i].setPeriode((String) rader[i].get("stortingsrepresentant"));
              }

          /*
        for (int i = 0; i < rader.length; i++) {
            norskepolitikere[i] = new NorskePolitikere();
            norskepolitikere[i].setEtterNavn((String) rader[i].get("navn"));
            norskepolitikere[i].setForNavn((String) rader[i].get("fornavn"));
              norskepolitikere[i].setInitialer((String) rader[i].get("initialer"));
             if(rader[i].get("faar")==null){}else{
            norskepolitikere[i].setFaar((Short) rader[i].get("faar"));
             }
            norskepolitikere[i].setPersonId((Integer) rader[i].get("person_id"));
           if(rader[i].get("ss_person_id")==null){}else{
            norskepolitikere[i].setSsPersonId((Integer) rader[i].get("ss_person_id"));
           }
              if(rader[i].get("st_person_id")==null){}else{
            norskepolitikere[i].setStPersonId((Integer) rader[i].get("st_person_id"));
           }*/
            /*
            * if(rader[i].get("eintaltekst")==null){}else{
            norskepolitikere[i].setPartinavn((String) rader[i].get("eintaltekst"));
           }
            * */
        }
        return norskepolitikere;
    }

/*
     private NorskePolitikere[] getNorskePolitikere(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn,  \n" +
                "     t_felles_statsraader.person_id AS sr_person_id, t_felles_statssekretaerer.person_id AS ss_person_id, t_felles_person.initialer, \n" +
                "     t_felles_person.faar\n" +
                "     FROM  t_felles_person LEFT OUTER JOIN\n" +
                "     t_felles_statsraader ON t_felles_person.person_id = t_felles_statsraader.person_id LEFT OUTER JOIN\n" +
                "     t_felles_statssekretaerer ON t_felles_person.person_id = t_felles_statssekretaerer.person_id\n" +
                "     GROUP BY t_felles_person.person_id, t_felles_person.fornavn, t_felles_statsraader.person_id, \n" +
                "     t_felles_person.navn, t_felles_statssekretaerer.person_id, t_felles_person.initialer, t_felles_person.faar\n"
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
              norskepolitikere[i].setInitialer((String) rader[i].get("initialer")); 
             if(rader[i].get("faar")==null){}else{
            norskepolitikere[i].setFaar((Short) rader[i].get("faar"));
             }
            norskepolitikere[i].setPersonId((Integer) rader[i].get("person_id"));
           if(rader[i].get("ss_person_id")==null){}else{
            norskepolitikere[i].setSsPersonId((Integer) rader[i].get("ss_person_id"));
           }
              if(rader[i].get("st_person_id")==null){}else{
            norskepolitikere[i].setStPersonId((Integer) rader[i].get("st_person_id"));
           }
        }
        return norskepolitikere;
    }
*/
   /*sok p� navn norskpolitiker */
/*
     private NorskePolitikere[] getSokNorskePolitikere(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] norskepolitikere = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     MAX(DISTINCT t_storting_person_hist.stortingperiode_kode) AS Expr1, t_felles_person.person_id, t_felles_person.fornavn, \n" +
                "                      t_felles_person.navn, t_felles_person.faar, t_felles_person.dodsaar\n" +
                "FROM         t_felles_person LEFT OUTER JOIN\n" +
                "                      t_storting_person_hist ON t_storting_person_hist.person_id = t_felles_person.person_id\n" +
                "GROUP BY t_storting_person_hist.person_id, t_felles_person.person_id, t_felles_person.fornavn, t_felles_person.navn, \n" +
                "                      t_felles_person.faar, t_felles_person.dodsaar\n"
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

             if(rader[i].get("faar")==null){}else{
            norskepolitikere[i].setFaar((Short) rader[i].get("faar"));
             }
            norskepolitikere[i].setPersonId((Integer) rader[i].get("person_id"));

        }
        return norskepolitikere;
    }
*/


    private NorskePolitikere[] getPartiSammensetning(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        NorskePolitikere[] partilist = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT DISTINCT storting_sammensetning.parti AS partikode, t_felles_partinavn.eintaltekst, t_felles_partinavn.ENGparti_bruksnavn, t_felles_partinavn.eintaltekst_forkorting, t_felles_partinavn.ENGeintaltekst_forkorting\n" +
                "       FROM storting_sammensetning INNER JOIN\n" +
                "       t_felles_partinavn ON storting_sammensetning.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "       storting_perioder ON storting_sammensetning.periode = storting_perioder.id"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new NorskePolitikere[rader.length];

        for (int i = 0; i < rader.length; i++) {
            partilist[i] = new NorskePolitikere();
            if (this.engelsk && (String) rader[i].get("ENGparti_bruksnavn")!=null){partilist[i].setPartinavn((String) rader[i].get("ENGparti_bruksnavn"));}else{partilist[i].setPartinavn((String) rader[i].get("eintaltekst"));}
            partilist[i].setPartikode((Integer) rader[i].get("partikode"));
            //partilist[i].setPartikode((Integer) rader[i].get("partikode"));
            if (this.engelsk){partilist[i].setPartiKortnavn((String) rader[i].get("ENGeintaltekst_forkorting"));}else{partilist[i].setPartiKortnavn((String) rader[i].get("eintaltekst_forkorting"));}
        }
        return partilist;
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
        String sqlSelect = "SELECT DISTINCT storting_sammensetning.periode AS periodekode, storting_perioder.valgaar \n" +
                "       FROM storting_sammensetning INNER JOIN\n" +
                "       t_felles_partinavn ON storting_sammensetning.parti = t_felles_partinavn.kode INNER JOIN\n" +
                "       storting_perioder ON storting_sammensetning.periode = storting_perioder.id"
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
             statusskjemaer[i].setPeriodeAar((Integer) rader[i].get("periodekode"));

        }

        return statusskjemaer;
    }






}
