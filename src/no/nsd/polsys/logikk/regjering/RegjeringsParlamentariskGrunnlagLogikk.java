package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.Statsraadsarkivet;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.text.DecimalFormat;
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
public class RegjeringsParlamentariskGrunnlagLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;

    // ============================================================ Konstrukt�r
    /**
     * Tom konstrukt�r.
     */
    public RegjeringsParlamentariskGrunnlagLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Statsraadsarkivet[] getRegjeringsParlamentariskGrunnlag() throws Exception {
           String condition = "  ";
           return this.getRegjeringsParlamentariskGrunnlag(condition, null);
       }



/***************************************************************************************************

 Private metoder

***************************************************************************************************/


    private Statsraadsarkivet[] getRegjeringsParlamentariskGrunnlag(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Statsraadsarkivet[] regjeringadhoc = null;

        // resultat fra sql-sporring.
        Result result = null;
        Result result2 = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        SqlCommandBean sqlCB2 = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        SortedMap[] rader2 = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  id as kode, regjering.navn_nb as NOstatsraadsbetegnelse, regjering.navn_en as ENGstatsraadsbetegnelse, YEAR(start) as startaar,  Blokk, start, slutt,\n" +
                "        DAY(regjering.start) AS startday, MONTH(regjering.start) AS startmonth , YEAR(regjering.start) AS startyear , DAY(regjering.slutt) AS sluttday, MONTH(regjering.slutt) AS sluttmonth, YEAR(regjering.slutt) AS sluttyear\n" +
                "\t\t, STUFF(( \n" +
                "                        SELECT N'+ ' + CAST([eintaltekst_forkorting] AS VARCHAR(255)) \n" +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti \n" +
                "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS partier , STUFF(( \n" +
                "                        SELECT N', ' + CAST([kode] AS VARCHAR(255)) \n" +
                "                        FROM t_felles_partinavn  inner join regjering_partier on t_felles_partinavn.kode = regjering_partier.parti \n" +
                "                        WHERE regjering_partier.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS partierkode\n" +
                "                        , STUFF(( \n" +
                "                        SELECT N', ' + CAST([stortingperiode] AS VARCHAR(255)) \n" +
                "                        FROM regjering_stortingperiode \n" +
                "                        WHERE regjering_stortingperiode.regjering = regjering.id  \n" +
                "                        FOR XML PATH ('')), 1, 1, '') AS stortingperiodekode  \n" +
                "\t\tFROM  regjering\n" +
                "        WHERE id >= 10 AND (regjering.id not in(101, 102, 103,104,105,106,107,108,109,110,111,112) )\n" +
                "        ORDER BY id\t "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Statsraadsarkivet[rader.length];

    for (int i = 0; i < rader.length; i++) {
                regjeringadhoc[i] = new Statsraadsarkivet();

                regjeringadhoc[i].setRegjering_reg_kode((Integer)rader[i].get("kode"));
                regjeringadhoc[i].setRegjeringsnavn_NO((String)rader[i].get("NOstatsraadsbetegnelse"));
                regjeringadhoc[i].setRegjeringsnavn_ENG((String)rader[i].get("ENGstatsraadsbetegnelse"));
                regjeringadhoc[i].setStartaar((Integer)rader[i].get("startaar"));
                regjeringadhoc[i].setBlokk((Integer)rader[i].get("blokk"));

                regjeringadhoc[i].setPartikode((String)rader[i].get("partierkode"));
                regjeringadhoc[i].setStortingperiodekode((String)rader[i].get("stortingperiodekode"));
                regjeringadhoc[i].setStart(rader[i].get("startyear")+"-"+rader[i].get("startmonth")+"-"+rader[i].get("startday"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttyear")+"-"+rader[i].get("sluttmonth")+"-"+rader[i].get("sluttday"));

        String[] parti = ((String)rader[i].get("partierkode")).split(",");
         String[] stortingparti = ((String)rader[i].get("stortingperiodekode")).split(",");

              String sqlSelect2 = "SELECT storting_perioder.fleirtaltekst, round(COUNT(storting_representanter.parti)*100/ cast(storting_perioder.seter as float) ,1) AS TalPosisjon\n" +
                      "       FROM  storting_perioder INNER JOIN\n" +
                      "       storting_representanter ON storting_perioder.id = storting_representanter.periode ";

                  sqlSelect2 +=    " WHERE (storting_representanter.representant_nr >= 1) ";


             sqlSelect2 +=   " AND storting_representanter.parti IN ( " ;
         for(int k = 0; k < parti.length; k++){
           sqlSelect2 +=    String.valueOf(parti[k]) + ", ";
           }
           sqlSelect2 +=    "  -1 )";



             sqlSelect2 +=   " AND storting_perioder.id IN ( " ;
              for(int w = 0; w < stortingparti.length; w++){
           sqlSelect2 +=    String.valueOf(stortingparti[w]) + ", ";
           }
           sqlSelect2 +=    "  -1 )";

           sqlSelect2 +=   " GROUP BY storting_perioder.fleirtaltekst, storting_perioder.seter ";



        //List values2 = new ArrayList();
        //values2.add(31,52);
        //values2.add(35);
        ArrayList<String> arr3 = new ArrayList<String>();
         ArrayList<String> arr4 = new ArrayList<String>();
         ArrayList<String> arr5 = new ArrayList<String>();
        sqlCB2.setConnection(this.conn);
        sqlCB2.setSqlValue(sqlSelect2); //sporring
        //sqlCB2.setValues(values2); //parameter
        result2 = sqlCB2.executeQuery();
        rader2 = result2.getRows();

              for (int k = 0; k < rader2.length; k++) {
              DecimalFormat twoDForm = new DecimalFormat("#.##");
               Double menn = (Double)rader2[k].get("TalPosisjon");
                  Double kvinner = 100-menn;
               //Double.valueOf(twoDForm.format(menn));
                //regjeringadhoc[i].setTalposisjon(Double.valueOf(twoDForm.format(menn)));
                 //regjeringadhoc[i].setRegtalposisjon(Double.valueOf(twoDForm.format(kvinner)));
                   arr3.add((String)rader2[k].get("fleirtaltekst"));
                  arr4.add(twoDForm.format(menn));
                  arr5.add(twoDForm.format(kvinner));
                 //regjeringadhoc[i].setFleirtaltekst((String)rader2[k].get("fleirtaltekst"));

                  regjeringadhoc[i].getFleirtaltekstArray().add (k, (String) rader2[k].get("fleirtaltekst"));
                  regjeringadhoc[i].getTalposisjonArray().add(k, (Double)rader2[k].get("TalPosisjon"));

              }
/*
            String vefo = "";
			for(int k=0; k<arr3.size(); k++){
				if(arr3.size()>1){
				vefo= vefo  + arr3.get(k) + "\n";
				}
				else{
				vefo = arr3.get(0);
				}
			}
			regjeringadhoc[i].setFleirtaltekst(vefo);
			arr3.clear();

         String vefo2 = "";
			for(int k=0; k<arr4.size(); k++){
				if(arr4.size()>1){
				vefo2= vefo2  + arr4.get(k) + "\n";
				}
				else{
				vefo2 = arr4.get(0);
				}
			}
			regjeringadhoc[i].setTalposisjon(vefo2);
			arr4.clear();


        String vefo3 = "";
			for(int k=0; k<arr5.size(); k++){
				if(arr5.size()>1){
				vefo3= vefo3  + arr5.get(k) + "\n";
				}
				else{
				vefo3 = arr5.get(0);
				}
			}
			regjeringadhoc[i].setRegtalposisjon(vefo3);
			arr5.clear();
*/


            }
        return regjeringadhoc;
    }


    

}

