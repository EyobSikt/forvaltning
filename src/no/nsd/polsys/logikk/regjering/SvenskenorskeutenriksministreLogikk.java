package no.nsd.polsys.logikk.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.regjering.Regjeringsadhoc;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 24.nov.2010
 * Time: 12:27:57
 * To change this template use File | Settings | File Templates.
 */
public class SvenskenorskeutenriksministreLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public SvenskenorskeutenriksministreLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Regjeringsadhoc[] getsvenskenorskeutenriksministre() throws Exception {
           String condition = " ORDER BY regjering_svensknorske_utenriksministre.start ";
           return this.getsvenskenorskeutenriksministre(condition, null);
       }



    
/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private Regjeringsadhoc[] getsvenskenorskeutenriksministre(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        Regjeringsadhoc[] regjeringadhoc = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT  regjering_svensknorske_utenriksministre.id, regjering_svensknorske_utenriksministre.person, politikere.fornavn, \n" +
                "        politikere.etternavn, regjering_svensknorske_utenriksministre.foedt, regjering_svensknorske_utenriksministre.doed, \n" +
                "        regjering_svensknorske_utenriksministre.departement, regjering_svensknorske_utenriksministre.tittel, DAY(regjering_svensknorske_utenriksministre.start) AS startday, MONTH(regjering_svensknorske_utenriksministre.start) AS startmonth , YEAR(regjering_svensknorske_utenriksministre.start) AS startyear , \n" +
                "        DAY(regjering_svensknorske_utenriksministre.slutt) AS sluttday, MONTH(regjering_svensknorske_utenriksministre.slutt) AS sluttmonth, YEAR(regjering_svensknorske_utenriksministre.slutt) AS sluttyear,  regjering_svensknorske_utenriksministre.kommentar_ekstern_nb as Eksternkommentar, \n" +
                "        regjering_svensknorske_utenriksministre.kommentar_ekstern_en as Engeksternkommentar, regjering_svensknorske_utenriksministre.kommentar_intern AS Internkommentar\n" +
                "        FROM    regjering_svensknorske_utenriksministre INNER JOIN\n" +
                "                politikere ON regjering_svensknorske_utenriksministre.person = politikere.id "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        regjeringadhoc = new Regjeringsadhoc[rader.length];

    for (int i = 0; i < rader.length; i++) {
        regjeringadhoc[i] = new Regjeringsadhoc();

                regjeringadhoc[i].setUttakskode((Integer) rader[i].get("id"));
                regjeringadhoc[i].setFornavn((String) rader[i].get("fornavn"));
                regjeringadhoc[i].setEtternavn((String) rader[i].get("etternavn"));
                regjeringadhoc[i].setFoedt((Integer) rader[i].get("foedt"));
                regjeringadhoc[i].setDoedsaar((Integer) rader[i].get("doed"));
        if (this.engelsk) {
            regjeringadhoc[i].setEksternkommentar_ENG((String) rader[i].get("Engeksternkommentar"));
        }else{
            regjeringadhoc[i].setEksternkommentar_NO((String) rader[i].get("eksternkommentar"));
        }


        regjeringadhoc[i].setStart(rader[i].get("startday")+"."+rader[i].get("startmonth")+"."+rader[i].get("startyear"));
                regjeringadhoc[i].setSlutt(rader[i].get("sluttday")+"."+rader[i].get("sluttmonth")+"."+rader[i].get("sluttyear"));
           
            }
        return regjeringadhoc;
    }


}

