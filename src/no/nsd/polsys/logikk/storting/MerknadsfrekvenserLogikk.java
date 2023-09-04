package no.nsd.polsys.logikk.storting;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.storting.Fraksjonsmerknader;

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
public class MerknadsfrekvenserLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public MerknadsfrekvenserLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------


    public Fraksjonsmerknader[] getMerknadsfrekvenser() throws Exception {
        String condition = "ORDER BY sesjon_id DESC";
        return this.getMerknadsfrekvenser(condition, null);
    }

  public Fraksjonsmerknader[] getFrekvenserSesjon_asc() throws Exception {
        String condition = "ORDER BY storting_fraksjon_frekvenser.sesjon_id ASC";
        return this.getFrekvenserSesjon(condition, null);
    }

    public Fraksjonsmerknader[] getFrekvenserSesjon_desc() throws Exception {
        String condition = "ORDER BY storting_fraksjon_frekvenser.sesjon_id DESC";
        return this.getFrekvenserSesjon(condition, null);
    }

   public Fraksjonsmerknader[] getFrekvenserPeriode_asc() throws Exception {
        String condition = "ORDER BY storting_fraksjon_frekvenser.periode ASC";
        return this.getFrekvenserPeriode(condition, null);
    }

    public Fraksjonsmerknader[] getFrekvenserPeriode_desc() throws Exception {
        String condition = "ORDER BY storting_fraksjon_frekvenser.periode DESC";
        return this.getFrekvenserPeriode(condition, null);
    }

  /*s�k frekvenser*/
  public Fraksjonsmerknader[] getSokFrekvenserPeriode(Integer fra, Integer til) throws Exception {
        String condition = "HAVING (periode >=?  AND periode <= ?) ";        
          condition +=" ORDER BY storting_fraksjon_frekvenser.periode DESC";
        List values = new ArrayList();
        values.add(fra);
        values.add(til); 
         return this.getSokFrekvenserPeriode(condition, values);
  }

  /*s�k frekvenser periode*/
     public Fraksjonsmerknader[] getSokFrekvenserTypePeriode(Integer fra, Integer til, String[] instillingstype) throws Exception {
        String condition = "HAVING (periode >=?  AND periode <= ?) ";

          condition +=" ORDER BY storting_fraksjon_frekvenser.periode DESC";
        List values = new ArrayList();
         values.add(fra);
        values.add(til); 
         return this.getSokFrekvenserTypePeriode(condition, values, instillingstype);
    }
   /*s�k frekvenser sesjon*/
     public Fraksjonsmerknader[] getSokFrekvenserTypeSesjon(Integer fra, Integer til,  String[] instillingstype) throws Exception {
        String condition = "HAVING (storting_fraksjon_frekvenser.sesjon_id >=?  AND storting_fraksjon_frekvenser.sesjon_id <= ?) ";

          condition +=" ORDER BY storting_fraksjon_frekvenser.periode DESC";
        List values = new ArrayList();
         values.add(fra);
        values.add(til);
         return this.getSokFrekvenserTypeSesjon(condition, values, instillingstype);
    }

    public Fraksjonsmerknader[] getPartiNavn() throws Exception {
        String condition = "";
        return this.getPartiNavn(condition, null);
    }
    
    public Fraksjonsmerknader[] getKomite() throws Exception {
           String condition = "";
           return this.getKomite(condition, null);
       }

   /*s�k partiavstand sesjon*/
     public Fraksjonsmerknader[] getPartiavstandSesjon(String parti_a_kortnavn, String parti_b_kortnavn, Integer fra, Integer til, String[] komitekode,String[] instillingstype ) throws Exception {
         String condition = " WHERE storting_fraksjon_parti_omkodet.sesjon ! = 0 ";
        if(instillingstype != null){
			for(int i = 0; i < instillingstype.length; i++){
            //if(instillingstype[i].equalsIgnoreCase("-1")){condition += "AND (nytype =1 OR nytype=2 OR nytype =3 ) ";}
             if(instillingstype[i].equalsIgnoreCase("1") || instillingstype[i].equalsIgnoreCase("2") || instillingstype[i].equalsIgnoreCase("3") ){
             condition += "AND (  ";
            if(instillingstype.length==1){condition += " nytype = "+ instillingstype[0]; }
            if(instillingstype.length==2){condition += " nytype = "+ instillingstype[0] +" OR " + " nytype = "+ instillingstype[1]; }
             if(instillingstype.length==3){condition += " nytype = "+ instillingstype[0] +" OR " + " nytype = " + instillingstype[1]+" OR " + " nytype = "+ instillingstype[2]; }
            condition += " )  ";
             }
            /*if(instillingstype[i].equalsIgnoreCase("1")  ){condition += " nytype =1 ";}
            if(instillingstype[i].equalsIgnoreCase("2")  ){condition += " OR  nytype =2 ";}
            if(instillingstype[i].equalsIgnoreCase("3")  ){condition += " OR nytype =3 ";}
            if(instillingstype[i].equalsIgnoreCase("1") || instillingstype[i].equalsIgnoreCase("2") ){condition += "AND (nytype =1 OR nytype =2 ) ";}
            if(instillingstype[i].equalsIgnoreCase("1") && !instillingstype[i].equalsIgnoreCase("2") && instillingstype[i].equalsIgnoreCase("3")){condition += "AND (nytype =1 OR nytype =3) ";}
            if(!instillingstype[i].equalsIgnoreCase("1") && instillingstype[i].equalsIgnoreCase("2") && instillingstype[i].equalsIgnoreCase("3")){condition += "AND (nytype =2 OR nytype =3) ";}    
            */
            }
        }
         if(komitekode !=null &&  !komitekode.equals("") && komitekode.length>0 ){
             condition += " AND  (    ";
             condition += "   nykomite= ?    ";
             for(int i=1; i<komitekode.length; i++){
                 if(komitekode[i]!=""){
                     condition += " OR nykomite= ?  ";
                 }
             }
             condition += "  )    ";
         }
         /*
         if(komitekode !=-1 ){
         condition += " AND  nykomite =?  ";
           }
         */
                condition += " GROUP BY sesjon, storting_stortingssesjoner.eintaltekst, "+ parti_a_kortnavn +", " +  parti_b_kortnavn  ;
                condition += " HAVING  (sesjon>= ? AND sesjon <= ?) ";
               condition +=" ORDER BY sesjon DESC ";
        List values = new ArrayList();        
         /*if(komitekode !=-1 ){
         values.add(komitekode);
           }*/
         if(komitekode !=null && !komitekode.equals("") && komitekode.length>0){
             values.add(  komitekode[0]+" "  );
             for(int i=1; i<komitekode.length; i++){
                 if(komitekode[i]!=""){ values.add(  komitekode[i]+" "  );}
             }
         }
        values.add(fra);
        values.add(til);
         return this.getPartiavstandSesjon(parti_a_kortnavn, parti_b_kortnavn, condition, values);
    }

    /*s�k partiavstand sesjon*/

     public Fraksjonsmerknader[] getPartiavstandPeriode(String parti_a_kortnavn,String parti_b_kortnavn, Integer fra, Integer til, String[] komitekode,String[] instillingstype ) throws Exception {
         String condition = " WHERE storting_fraksjon_frekvenser.periode ! = 0 ";
        if(instillingstype != null){
			for(int i = 0; i < instillingstype.length; i++){
            //if(instillingstype[i].equalsIgnoreCase("-1")){condition += "AND (nytype =1 OR nytype=2 OR nytype =3 ) ";}
             if(instillingstype[i].equalsIgnoreCase("1") || instillingstype[i].equalsIgnoreCase("2") || instillingstype[i].equalsIgnoreCase("3") ){
             condition += "AND (  ";
            if(instillingstype.length==1){condition += " nytype = "+ instillingstype[0]; }
            if(instillingstype.length==2){condition += " nytype = "+ instillingstype[0] +" OR " + " nytype = "+ instillingstype[1]; }
             if(instillingstype.length==3){condition += " nytype = "+ instillingstype[0] +" OR " + " nytype = " + instillingstype[1]+" OR " + " nytype = "+ instillingstype[2]; }
            condition += " )  ";
             }

            }
        }
         if(komitekode !=null &&  !komitekode.equals("") && komitekode.length>0 ){
             condition += " AND  (    ";
             condition += "   nykomite= ?    ";
             for(int i=1; i<komitekode.length; i++){
                 if(komitekode[i]!=""){
                     condition += " OR nykomite= ?  ";
                 }
             }
             condition += "  )    ";
         }
                condition += " GROUP BY storting_fraksjon_frekvenser.periode, storting_perioder.fleirtaltekst, storting_fraksjon_parti_omkodet."+ parti_a_kortnavn  + ", storting_fraksjon_parti_omkodet." +parti_b_kortnavn;
               condition += " HAVING  (storting_fraksjon_frekvenser.periode >= ? AND storting_fraksjon_frekvenser.periode <= ?) ";
               condition +=" ORDER BY storting_fraksjon_frekvenser.periode DESC ";


        List values = new ArrayList();
         if(komitekode !=null && !komitekode.equals("") && komitekode.length>0){
             values.add(  komitekode[0]+" "  );
             for(int i=1; i<komitekode.length; i++){
                 if(komitekode[i]!=""){ values.add(  komitekode[i]+" "  );}
             }
         }

         values.add(fra);
        values.add(til);
         return this.getPartiavstandPeriode(parti_a_kortnavn,parti_b_kortnavn, condition, values);
    }
 
/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private Fraksjonsmerknader[] getMerknadsfrekvenser(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  sesjon_id, aar, innstillinger, innst_m_merknader, CAST(ROUND((innst_m_merknader / innstillinger) * 100, 2) AS DECIMAL(15,2) )  AS prosent_innst_m_m, s_merknader, o_merknader, b_merknader, \n" +
                "     totalt_merknader\n" +
                "FROM  storting_fraksjon_frekvenser"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
            merknadsfrekvenser[i].setSesjon((String)rader[i].get("aar"));
            merknadsfrekvenser[i].setInnstillinger(rader[i].get("innstillinger"));
            merknadsfrekvenser[i].setInnst_m_merknader( rader[i].get("innst_m_merknader"));
            merknadsfrekvenser[i].setProsent_innst_m_m( rader[i].get("prosent_innst_m_m"));
             merknadsfrekvenser[i].setTotalt_merknader((Integer) rader[i].get("totalt_merknader"));
        }

        return merknadsfrekvenser;
    }

  private Fraksjonsmerknader[] getFrekvenserSesjon(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  storting_fraksjon_frekvenser.aar, storting_fraksjon_frekvenser.sesjon_id, storting_fraksjon_frekvenser.innstillinger, storting_fraksjon_frekvenser.innst_m_merknader, \n" +
                "        storting_fraksjon_frekvenser.s_merknader, storting_fraksjon_frekvenser.o_merknader, storting_fraksjon_frekvenser.b_merknader, \n" +
                "        storting_fraksjon_frekvenser.totalt_merknader, storting_fraksjon_frekvenser.periode, storting_perioder.fleirtaltekst AS periodetekst\n" +
                "        FROM storting_fraksjon_frekvenser INNER JOIN\n" +
                "        storting_perioder ON storting_fraksjon_frekvenser.periode = storting_perioder.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
            merknadsfrekvenser[i].setSesjonId((Integer)rader[i].get("sesjon_id"));
            merknadsfrekvenser[i].setSesjon((String)rader[i].get("aar"));

        }
        return merknadsfrekvenser;
    }

    private Fraksjonsmerknader[] getFrekvenserPeriode(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT   DISTINCT storting_fraksjon_frekvenser.periode, storting_perioder.fleirtaltekst AS periodetekst \n" +
                "FROM       storting_fraksjon_frekvenser INNER JOIN\n" +
                "                      storting_perioder ON storting_fraksjon_frekvenser.periode = storting_perioder.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
            merknadsfrekvenser[i].setPeriode((Integer)rader[i].get("periode"));
            merknadsfrekvenser[i].setPeriodetekst((String)rader[i].get("periodetekst"));
        }
        return merknadsfrekvenser;
    }

  private Fraksjonsmerknader[] getSokFrekvenserPeriode(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     storting_fraksjon_frekvenser.periode, SUM(storting_fraksjon_frekvenser.innstillinger) AS periodesum_innst, \n" +
                "                      SUM(storting_fraksjon_frekvenser.innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.totalt_merknader) \n" +
                "                      AS periodesum_totalt_merknad, storting_perioder.fleirtaltekst AS periodetekst, storting_fraksjon_frekvenser.s_innstillinger AS sum_innst\n" +
                "FROM         storting_fraksjon_frekvenser INNER JOIN\n" +
                "              storting_perioder ON storting_fraksjon_frekvenser.periode = storting_perioder.id \n" +
                "GROUP BY storting_fraksjon_frekvenser.periode, storting_perioder.fleirtaltekst, storting_fraksjon_frekvenser.s_innstillinger"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
        }
        return merknadsfrekvenser;
    }

  private Fraksjonsmerknader[] getSokFrekvenserTypePeriode(String condition, List values, String[] instillingstype) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  storting_fraksjon_frekvenser.periode, \n" +
                "                   storting_perioder.fleirtaltekst AS periodetekst \n";

          if(instillingstype !=null){
          if(instillingstype[0].equals("0")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.totalt_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.innst_m_merknader) / SUM(storting_fraksjon_frekvenser.innstillinger) * 100),2) AS DECIMAL(5,2)) AS prs_innst_m_merkn , CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innstillinger)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==3 || instillingstype.length==4){sqlSelect += " , SUM(storting_fraksjon_frekvenser.innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.totalt_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.innst_m_merknader) / SUM(storting_fraksjon_frekvenser.innstillinger) * 100),2) AS DECIMAL(5,2)) AS prs_innst_m_merkn , CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innstillinger)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("1")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.s_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.s_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) / SUM(storting_fraksjon_frekvenser.s_innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_merknader)/SUM(storting_fraksjon_frekvenser.s_innstillinger)),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_merknader)/SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("2")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.o_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.o_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) / SUM(storting_fraksjon_frekvenser.o_innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_merknader)/SUM(storting_fraksjon_frekvenser.o_innstillinger)),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_merknader)/SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("3")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.b_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.b_merknader) AS periodesum_totalt_merknader,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) / SUM(storting_fraksjon_frekvenser.b_innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_merknader)/SUM(storting_fraksjon_frekvenser.b_innstillinger)),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_merknader)/SUM(storting_fraksjon_frekvenser.b_innst_m_merknader)),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("2")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.o_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.s_merknader) + SUM(storting_fraksjon_frekvenser.o_merknader) AS periodesum_totalt_merknader,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.o_innst_m_merknader))  / (SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.o_innstillinger) )*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.o_merknader))/(SUM(storting_fraksjon_frekvenser.s_innstillinger)+SUM(storting_fraksjon_frekvenser.o_innstillinger))),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.o_merknader))/(SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.o_innst_m_merknader))),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn  " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("3")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) AS periodesum_innst,  SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.s_merknader) + SUM(storting_fraksjon_frekvenser.b_merknader) AS periodesum_totalt_merknader, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))  / (SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) )*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.s_innstillinger)+SUM(storting_fraksjon_frekvenser.b_innstillinger))),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("2") && instillingstype[1].equals("3")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.o_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) AS periodesum_innst,  SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.o_merknader) + SUM(storting_fraksjon_frekvenser.b_merknader) AS periodesum_totalt_merknader,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))  / (SUM(storting_fraksjon_frekvenser.o_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) )*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.o_innstillinger)+SUM(storting_fraksjon_frekvenser.b_innstillinger))),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("1")){sqlSelect += " ,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_innstillinger) / SUM(storting_fraksjon_frekvenser.innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_merknader)/SUM(storting_fraksjon_frekvenser.totalt_merknader)*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn" ; }
          if(instillingstype.length==1 && instillingstype[0].equals("2")){sqlSelect += " ,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_innstillinger) / SUM(storting_fraksjon_frekvenser.innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_merknader)/SUM(storting_fraksjon_frekvenser.totalt_merknader)*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn" ; }
          if(instillingstype.length==1 && instillingstype[0].equals("3")){sqlSelect += " ,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_innstillinger) / SUM(storting_fraksjon_frekvenser.innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_innst_m_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_merknader)/SUM(storting_fraksjon_frekvenser.totalt_merknader)*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn" ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("2")){sqlSelect += " ,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.o_innstillinger))  / (SUM(storting_fraksjon_frekvenser.innstillinger)) *100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.o_innst_m_merknader))/(SUM(storting_fraksjon_frekvenser.innst_m_merknader))*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.o_merknader))/(SUM(storting_fraksjon_frekvenser.totalt_merknader))*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn  " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("3")){sqlSelect += " ,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger))  / (SUM(storting_fraksjon_frekvenser.innstillinger)) *100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))/(SUM(storting_fraksjon_frekvenser.innst_m_merknader))*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.totalt_merknader))*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn  " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("2") && instillingstype[1].equals("3")){sqlSelect += " ,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger))  / (SUM(storting_fraksjon_frekvenser.innstillinger)) *100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))/(SUM(storting_fraksjon_frekvenser.innst_m_merknader))*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.totalt_merknader))*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn  " ; }


          }
           sqlSelect +=     " FROM        storting_fraksjon_frekvenser INNER JOIN\n" +
                "                      storting_perioder ON storting_fraksjon_frekvenser.periode = storting_perioder.id \n" +
                "GROUP BY storting_fraksjon_frekvenser.periode, storting_perioder.fleirtaltekst"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
            merknadsfrekvenser[i].setPeriode((Integer)rader[i].get("periode"));
            merknadsfrekvenser[i].setPeriodetekst((String)rader[i].get("periodetekst"));

             // Antall innstillinger , Innstillinger m/merkn. (N)
            merknadsfrekvenser[i].setPeriodesum_innst(rader[i].get("periodesum_innst"));
            merknadsfrekvenser[i].setPeriodesum_innst_m_merkn( rader[i].get("periodesum_innst_m_merkn"));

            //merknadsfrekvenser[i].setPeriodesum_s_innst( rader[i].get("periodesum_s_innst"));
            //merknadsfrekvenser[i].setPeriodesum_s_innst_m_merkn( rader[i].get("periodesum_s_innst_m_merkn"));

            //merknadsfrekvenser[i].setPeriodesum_o_innst(rader[i].get("periodesum_o_innst"));
            //merknadsfrekvenser[i].setPeriodesum_o_innst_m_merkn(rader[i].get("periodesum_o_innst_m_merkn"));

            //merknadsfrekvenser[i].setPeriodesum_b_innst(rader[i].get("periodesum_b_innst"));
            //merknadsfrekvenser[i].setPeriodesum_b_innst_m_merkn(rader[i].get("periodesum_b_innst_m_merkn"));

            // sum Antall fraksjons- merknader
            merknadsfrekvenser[i].setPeriodesum_totalt_merknader(rader[i].get("periodesum_totalt_merknader"));
            //merknadsfrekvenser[i].setPeriodesum_s_merkn(rader[i].get("periodesum_s_merkn"));
            //merknadsfrekvenser[i].setPeriodesum_o_merkn(rader[i].get("periodesum_o_merkn"));
            //merknadsfrekvenser[i].setPeriodesum_b_merkn(rader[i].get("periodesum_b_merkn"));

            //sum  Stortingsinnstillinger + Odelstingsinnstillinger, Stortingsinnstillinger + Budsjettinnstillinger, Odelstingsinnstillinger + Budsjettinnstillinger
             //merknadsfrekvenser[i].setPeriodesum_2_innst(rader[i].get("periodesum_2_innst"));


           // percentage  Alle innstillinger
           merknadsfrekvenser[i].setPrs_innst_m_merkn(rader[i].get("prs_innst_m_merkn"));
           merknadsfrekvenser[i].setPrs_merknader_pr_instilling(rader[i].get("prs_merknader_pr_instilling"));
           merknadsfrekvenser[i].setPrs_merknader_pr_instilling_merkn(rader[i].get("prs_merknader_pr_instilling_merkn"));

            // percentage innstillinger
           merknadsfrekvenser[i].setPrs_innst_s_m_merkn(rader[i].get("prs_innst_s_m_merkn"));
           merknadsfrekvenser[i].setPrs_merknader_s_pr_instilling(rader[i].get("prs_merknader_s_pr_instilling"));
           merknadsfrekvenser[i].setPrs_merknader_s_pr_instilling_merkn(rader[i].get("prs_merknader_s_pr_instilling_merkn"));
            if(instillingstype !=null && !instillingstype.equals("") && instillingstype.length>0){ merknadsfrekvenser[i].setInstillinglengde(instillingstype.length); }
          if(instillingstype !=null && instillingstype.length==1 && instillingstype[0].equals("1")){merknadsfrekvenser[i].setInstilling("stortingsinnstillinger");}
          if(instillingstype !=null && instillingstype.length==1 && instillingstype[0].equals("2")){merknadsfrekvenser[i].setInstilling("odelstingsinnstillinger");}
          if(instillingstype !=null && instillingstype.length==1 && instillingstype[0].equals("3")){merknadsfrekvenser[i].setInstilling("budsjettinnstillinger");}
          if(instillingstype !=null && instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("2")){merknadsfrekvenser[i].setInstilling("stortingsinnstillinger and odelstingsinnstillinger");}
          if(instillingstype !=null && instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("3")){merknadsfrekvenser[i].setInstilling("stortingsinnstillinger and budsjettinnstillinger");}
          if(instillingstype !=null && instillingstype.length==2 && instillingstype[0].equals("2") && instillingstype[1].equals("3")){merknadsfrekvenser[i].setInstilling("odelstingsinnstillinger and budsjettinnstillinger");}


          // percentage Odelstingsinnstillinger
           //merknadsfrekvenser[i].setPrs_innst_o_m_merkn(rader[i].get("prs_innst_o_m_merkn"));
           //merknadsfrekvenser[i].setPrs_merknader_o_pr_instilling(rader[i].get("prs_merknader_o_pr_instilling"));
           //merknadsfrekvenser[i].setPrs_merknader_o_pr_instilling_merkn(rader[i].get("prs_merknader_o_pr_instilling_merkn"));

        // percentage Budsjettinnstillinger
           //merknadsfrekvenser[i].setPrs_innst_b_m_merkn(rader[i].get("prs_innst_b_m_merkn"));
           //merknadsfrekvenser[i].setPrs_merknader_b_pr_instilling(rader[i].get("prs_merknader_b_pr_instilling"));
           //merknadsfrekvenser[i].setPrs_merknader_b_pr_instilling_merkn(rader[i].get("prs_merknader_b_pr_instilling_merkn"));
        }

        return merknadsfrekvenser;
    }

     private Fraksjonsmerknader[] getSokFrekvenserTypeSesjon(String condition, List values, String[] instillingstype) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
         String sqlSelect = "SELECT     storting_fraksjon_frekvenser.aar, storting_fraksjon_frekvenser.periode, \n" +
                "                       storting_perioder.fleirtaltekst AS periodetekst \n";

          if(instillingstype !=null){
          if(instillingstype[0].equals("0")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.totalt_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.innst_m_merknader) / SUM(storting_fraksjon_frekvenser.innstillinger) * 100),2) AS DECIMAL(5,2)) AS prs_innst_m_merkn , CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innstillinger)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==3 || instillingstype.length==4){sqlSelect += " , SUM(storting_fraksjon_frekvenser.innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.totalt_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.innst_m_merknader) / SUM(storting_fraksjon_frekvenser.innstillinger) * 100),2) AS DECIMAL(5,2)) AS prs_innst_m_merkn , CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innstillinger)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.totalt_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)),2) AS DECIMAL(15,2)) AS prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("1")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.s_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.s_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) / SUM(storting_fraksjon_frekvenser.s_innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_merknader)/SUM(storting_fraksjon_frekvenser.s_innstillinger)),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_merknader)/SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("2")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.o_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.o_merknader) AS periodesum_totalt_merknader, CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) / SUM(storting_fraksjon_frekvenser.o_innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_merknader)/SUM(storting_fraksjon_frekvenser.o_innstillinger)),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_merknader)/SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("3")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.b_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.b_merknader) AS periodesum_totalt_merknader,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) / SUM(storting_fraksjon_frekvenser.b_innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_merknader)/SUM(storting_fraksjon_frekvenser.b_innstillinger)),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_merknader)/SUM(storting_fraksjon_frekvenser.b_innst_m_merknader)),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("2")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.o_innstillinger) AS periodesum_innst, SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.s_merknader) + SUM(storting_fraksjon_frekvenser.o_merknader) AS periodesum_totalt_merknader,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.o_innst_m_merknader))  / (SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.o_innstillinger) )*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.o_merknader))/(SUM(storting_fraksjon_frekvenser.s_innstillinger)+SUM(storting_fraksjon_frekvenser.o_innstillinger))),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.o_merknader))/(SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.o_innst_m_merknader))),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn  " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("3")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) AS periodesum_innst,  SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.s_merknader) + SUM(storting_fraksjon_frekvenser.b_merknader) AS periodesum_totalt_merknader, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))  / (SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) )*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.s_innstillinger)+SUM(storting_fraksjon_frekvenser.b_innstillinger))),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("2") && instillingstype[1].equals("3")){sqlSelect += " , SUM(storting_fraksjon_frekvenser.o_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) AS periodesum_innst,  SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader) AS periodesum_innst_m_merkn, SUM(storting_fraksjon_frekvenser.o_merknader) + SUM(storting_fraksjon_frekvenser.b_merknader) AS periodesum_totalt_merknader,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader) + SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))  / (SUM(storting_fraksjon_frekvenser.o_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger) )*100),2) AS DECIMAL(15,2)) AS prs_innst_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.o_innstillinger)+SUM(storting_fraksjon_frekvenser.b_innstillinger))),2) AS DECIMAL(15,2))  AS  prs_merknader_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))),2)  AS  DECIMAL(15,2)) prs_merknader_pr_instilling_merkn " ; }
          if(instillingstype.length==1 && instillingstype[0].equals("1")){sqlSelect += " ,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_innstillinger) / SUM(storting_fraksjon_frekvenser.innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.s_merknader)/SUM(storting_fraksjon_frekvenser.totalt_merknader)*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn" ; }
          if(instillingstype.length==1 && instillingstype[0].equals("2")){sqlSelect += " ,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_innstillinger) / SUM(storting_fraksjon_frekvenser.innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.o_merknader)/SUM(storting_fraksjon_frekvenser.totalt_merknader)*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn" ; }
          if(instillingstype.length==1 && instillingstype[0].equals("3")){sqlSelect += " ,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_innstillinger) / SUM(storting_fraksjon_frekvenser.innstillinger)*100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_innst_m_merknader)/SUM(storting_fraksjon_frekvenser.innst_m_merknader)*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND((SUM(storting_fraksjon_frekvenser.b_merknader)/SUM(storting_fraksjon_frekvenser.totalt_merknader)*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn" ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("2")){sqlSelect += " ,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.o_innstillinger))  / (SUM(storting_fraksjon_frekvenser.innstillinger)) *100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.o_innst_m_merknader))/(SUM(storting_fraksjon_frekvenser.innst_m_merknader))*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.o_merknader))/(SUM(storting_fraksjon_frekvenser.totalt_merknader))*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn  " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("3")){sqlSelect += " ,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger))  / (SUM(storting_fraksjon_frekvenser.innstillinger)) *100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))/(SUM(storting_fraksjon_frekvenser.innst_m_merknader))*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.s_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.totalt_merknader))*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn  " ; }
          if(instillingstype.length==2 && instillingstype[0].equals("2") && instillingstype[1].equals("3")){sqlSelect += " ,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_innstillinger) + SUM(storting_fraksjon_frekvenser.b_innstillinger))  / (SUM(storting_fraksjon_frekvenser.innstillinger)) *100),2) AS DECIMAL(15,2)) AS prs_innst_s_m_merkn,  CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_innst_m_merknader)+SUM(storting_fraksjon_frekvenser.b_innst_m_merknader))/(SUM(storting_fraksjon_frekvenser.innst_m_merknader))*100),2) AS DECIMAL(15,2))  AS  prs_merknader_s_pr_instilling, CAST(ROUND(((SUM(storting_fraksjon_frekvenser.o_merknader)+SUM(storting_fraksjon_frekvenser.b_merknader))/(SUM(storting_fraksjon_frekvenser.totalt_merknader))*100),2)  AS  DECIMAL(15,2)) prs_merknader_s_pr_instilling_merkn  " ; }

          }
           sqlSelect +=    " FROM         storting_fraksjon_frekvenser INNER JOIN\n" +
                "                      storting_perioder ON storting_fraksjon_frekvenser.periode = storting_perioder.id\n" +
                "GROUP BY storting_fraksjon_frekvenser.aar, storting_fraksjon_frekvenser.sesjon_id, storting_fraksjon_frekvenser.periode, storting_perioder.fleirtaltekst"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
            merknadsfrekvenser[i].setPeriode((Integer)rader[i].get("periode"));
            merknadsfrekvenser[i].setPeriodetekst((String)rader[i].get("aar"));
            // Antall innstillinger , Innstillinger m/merkn. (N)
            merknadsfrekvenser[i].setPeriodesum_innst(rader[i].get("periodesum_innst"));
            merknadsfrekvenser[i].setPeriodesum_innst_m_merkn( rader[i].get("periodesum_innst_m_merkn"));
            // sum Antall fraksjons- merknader
            merknadsfrekvenser[i].setPeriodesum_totalt_merknader(rader[i].get("periodesum_totalt_merknader"));
            // percentage  Alle innstillinger
           merknadsfrekvenser[i].setPrs_innst_m_merkn(rader[i].get("prs_innst_m_merkn"));
           merknadsfrekvenser[i].setPrs_merknader_pr_instilling(rader[i].get("prs_merknader_pr_instilling"));
           merknadsfrekvenser[i].setPrs_merknader_pr_instilling_merkn(rader[i].get("prs_merknader_pr_instilling_merkn"));

            // percentage innstillinger
                      merknadsfrekvenser[i].setPrs_innst_s_m_merkn(rader[i].get("prs_innst_s_m_merkn"));
                      merknadsfrekvenser[i].setPrs_merknader_s_pr_instilling(rader[i].get("prs_merknader_s_pr_instilling"));
                      merknadsfrekvenser[i].setPrs_merknader_s_pr_instilling_merkn(rader[i].get("prs_merknader_s_pr_instilling_merkn"));

                     if(instillingstype !=null){ merknadsfrekvenser[i].setInstillinglengde(instillingstype.length);}
                     if(instillingstype !=null && instillingstype.length==1 && instillingstype[0].equals("1")){merknadsfrekvenser[i].setInstilling("stortingsinnstillinger");}
                     if(instillingstype !=null && instillingstype.length==1 && instillingstype[0].equals("2")){merknadsfrekvenser[i].setInstilling("odelstingsinnstillinger");}
                     if(instillingstype !=null && instillingstype.length==1 && instillingstype[0].equals("3")){merknadsfrekvenser[i].setInstilling("budsjettinnstillinger");}
                     if(instillingstype !=null && instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("2")){merknadsfrekvenser[i].setInstilling("stortingsinnstillinger and odelstingsinnstillinger");}
                     if(instillingstype !=null && instillingstype.length==2 && instillingstype[0].equals("1") && instillingstype[1].equals("3")){merknadsfrekvenser[i].setInstilling("stortingsinnstillinger and budsjettinnstillinger");}
                     if(instillingstype !=null && instillingstype.length==2 && instillingstype[0].equals("2") && instillingstype[1].equals("3")){merknadsfrekvenser[i].setInstilling("odelstingsinnstillinger and budsjettinnstillinger");}
         


        }

        return merknadsfrekvenser;
    }

     private Fraksjonsmerknader[] getPartiNavn(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT   kode, stortingsparti_45, eintaltekst, fraksjonsmerknadsarkivet_forkorting\n" +
                "FROM         t_felles_partinavn\n" +
                "WHERE     (stortingsparti_45 > 0)\n" +
                "ORDER BY kode"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
           merknadsfrekvenser[i].setPartinavn((String)rader[i].get("eintaltekst"));
          merknadsfrekvenser[i].setPartikortnavn((String)rader[i].get("fraksjonsmerknadsarkivet_forkorting"));  

        }

        return merknadsfrekvenser;
    }

     private Fraksjonsmerknader[] getKomite(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  storting_komite.kode, storting_komite.eintaltekst, YEAR(storting_komite.fraa_dato) as fra_aar, YEAR(storting_komite.til_dato) AS til_aar\n" +
                "FROM  storting_komite INNER JOIN\n" +
                " storting_fraksjon_parti_omkodet ON storting_komite.kode = storting_fraksjon_parti_omkodet.nykomite\n" +
                "GROUP BY storting_komite.kode, storting_komite.eintaltekst, storting_komite.fraa_dato, storting_komite.til_dato\n" +
                "ORDER BY storting_komite.kode"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
            merknadsfrekvenser[i] = new Fraksjonsmerknader();
           merknadsfrekvenser[i].setPartinavn((String)rader[i].get("eintaltekst"));
          if(rader[i].get("fra_aar") !=null){
           merknadsfrekvenser[i].setFra_aar((Integer)rader[i].get("fra_aar"));
          }
          if(rader[i].get("til_aar") !=null){
          merknadsfrekvenser[i].setTil_aar((Integer)rader[i].get("til_aar"));
          }
          
           merknadsfrekvenser[i].setKomitekode((Integer)rader[i].get("kode"));

        }

        return merknadsfrekvenser;
    }

     private Fraksjonsmerknader[] getPartiavstandPeriode(String partikortnavn_a, String partikortnavn_b, String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
       ArrayList<Integer> arr1 = new ArrayList<Integer>();
         ArrayList<Integer> arr2 = new ArrayList<Integer>();
         ArrayList<String> arr3 = new ArrayList<String>();
        // SQL-sporring.
        String sqlSelect = "SELECT  COUNT(storting_fraksjon_parti_omkodet. "+ partikortnavn_a  +") AS partiAogB_deltatt, storting_fraksjon_parti_omkodet. "+ partikortnavn_a +" AS parti_a, storting_fraksjon_parti_omkodet."+ partikortnavn_b +"  AS parti_b , storting_fraksjon_frekvenser.periode,  storting_perioder.fleirtaltekst \n" +
                "FROM  storting_fraksjon_parti_omkodet " +
                "INNER JOIN storting_fraksjon_frekvenser ON storting_fraksjon_parti_omkodet.sesjon = storting_fraksjon_frekvenser.sesjon_id " +
                "INNER JOIN storting_perioder ON storting_fraksjon_frekvenser.periode = storting_perioder.id  \n"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
           merknadsfrekvenser[i] = new Fraksjonsmerknader();
           if((Double)rader[i].get("parti_a") == 2 && (Double)rader[i].get("parti_b") == 2){
            //merknadsfrekvenser[i].setBeggedeltar((Integer)rader[i].get("partiAogB_deltatt"));
            arr1.add((Integer)rader[i].get("partiAogB_deltatt"));
            arr3.add((String)rader[i].get("fleirtaltekst"));
              //merknadsfrekvenser[i].setPeriodetekst((String)rader[i].get("eintaltekst"));
           }
           if((Double)rader[i].get("parti_a") == 1 && (Double)rader[i].get("parti_b") == 1){
           //merknadsfrekvenser[i].setIngendeltar((Integer)rader[i].get("partiAogB_deltatt"));
           arr2.add((Integer)rader[i].get("partiAogB_deltatt"));
           }
          for(int j=0; j< arr1.size(); j++){
            merknadsfrekvenser[j].setBeggedeltar(arr1.get(j));
          }
          for(int j=0; j< arr1.size(); j++){
            merknadsfrekvenser[j].setPeriodetekst(arr3.get(j));
          }
          for(int j=0; j< arr2.size(); j++){
            merknadsfrekvenser[j].setIngendeltar(arr2.get(j));
          }
        }

        return merknadsfrekvenser;
    }

     private Fraksjonsmerknader[] getPartiavstandSesjon(String partikortnavn_a, String partikortnavn_b, String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Fraksjonsmerknader[] merknadsfrekvenser = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
         ArrayList<Integer> arr1 = new ArrayList<Integer>();
          ArrayList<Double> arr1_per = new ArrayList<Double>();
         ArrayList<Integer> arr2 = new ArrayList<Integer>();
         ArrayList<String> arr3 = new ArrayList<String>();
         ArrayList<Integer> arr21 = new ArrayList<Integer>();
        ArrayList<Integer> arr12 = new ArrayList<Integer>();
        // SQL-sporring.
        String sqlSelect = "SELECT sesjon, COUNT("+ partikortnavn_a  + ") AS partiAogB_deltatt,"+ partikortnavn_a +" AS parti_a, "+ partikortnavn_b +" AS parti_b , storting_stortingssesjoner.eintaltekst\n" +
                "FROM         storting_fraksjon_parti_omkodet INNER JOIN storting_stortingssesjoner ON storting_fraksjon_parti_omkodet.sesjon = storting_stortingssesjoner.kode \n"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        merknadsfrekvenser = new Fraksjonsmerknader[rader.length];

        for (int i = 0; i < rader.length; i++) {
           merknadsfrekvenser[i] = new Fraksjonsmerknader();
           if((Double)rader[i].get("parti_a") == 2 && (Double)rader[i].get("parti_b") == 2){
            //merknadsfrekvenser[i].setBeggedeltar((Integer)rader[i].get("partiAogB_deltatt"));
            arr1.add((Integer)rader[i].get("partiAogB_deltatt"));
             arr3.add((String)rader[i].get("eintaltekst"));
              //merknadsfrekvenser[i].setPeriodetekst((String)rader[i].get("eintaltekst"));
           }
           if((Double)rader[i].get("parti_a") == 1 && (Double)rader[i].get("parti_b") == 1){
           //merknadsfrekvenser[i].setIngendeltar((Integer)rader[i].get("partiAogB_deltatt"));
           arr2.add((Integer)rader[i].get("partiAogB_deltatt"));

           }
          if((Double)rader[i].get("parti_a") == 2 && (Double)rader[i].get("parti_b") == 1){
           //merknadsfrekvenser[i].setIngendeltar((Integer)rader[i].get("partiAogB_deltatt"));
           arr21.add((Integer)rader[i].get("partiAogB_deltatt"));
           }
          if((Double)rader[i].get("parti_a") == 1 && (Double)rader[i].get("parti_b") == 2){
           //merknadsfrekvenser[i].setIngendeltar((Integer)rader[i].get("partiAogB_deltatt"));
           arr12.add((Integer)rader[i].get("partiAogB_deltatt"));
           }  

          for(int j=0; j< arr1.size(); j++){
            merknadsfrekvenser[j].setBeggedeltar(arr1.get(j));
          }
          for(int j=0; j< arr1.size(); j++){
            merknadsfrekvenser[j].setPeriodetekst(arr3.get(j));  
          }
          for(int j=0; j< arr2.size(); j++){
            merknadsfrekvenser[j].setIngendeltar(arr2.get(j));   
          }
         for(int j=0; j< arr21.size(); j++){
            merknadsfrekvenser[j].setDeltar_1(arr21.get(j));
          }
        for(int j=0; j< arr12.size(); j++){
            merknadsfrekvenser[j].setDeltar_2(arr12.get(j));
          }

        }

        return merknadsfrekvenser;
    }
}

