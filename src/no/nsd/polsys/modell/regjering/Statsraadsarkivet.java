package no.nsd.polsys.modell.regjering;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 13.jan.2011
 * Time: 13:29:37
 * To change this template use File | Settings | File Templates.
 */
public class Statsraadsarkivet {

    String regjeringsnavn_NO;
     String regjeringsnavn_ENG;
    String start;
    String slutt;
    int regjering_reg_kode;
    String fornavn;
    String etternavn;
    String stilling_avvik_NO;
    String stilling_avvik_ENG;
    String eintaltekst_NO;
    String eintaltekst_ENG;
    int doedsaar;
    int foedt;
    String eksternkommentar_NO;
    String eksternkommentar_ENG;
    int startaar;


    int sluttaar;
    int uttakskode;
    int antdag;
    int antdag_stolpe;
    String  partikode;
    String stortingperiodekode;



    int menn;
    int kvinner;
    int blokk;
    int min_reg;
    BigDecimal antalder;
    BigDecimal  antaldertotal;
    Object talposisjon;
    Object regtalposisjon;
    String fleirtaltekst;
    int personId;
    int avgaatte_statsraader_menn;
    int avgaatte_statsraader_kvinner;
    ArrayList<String> fleirtaltekstArray = new ArrayList<String>();
    ArrayList<Double> talposisjonArray = new ArrayList<Double>();



    public ArrayList<Double> getTalposisjonArray() {
        return talposisjonArray;
    }

    public void setTalposisjonArray(ArrayList<Double> talposisjonArray) {
        this.talposisjonArray = talposisjonArray;
    }

    public ArrayList<String> getFleirtaltekstArray() {
        return fleirtaltekstArray;
    }

    public void setFleirtaltekstArray(ArrayList<String> fleirtaltekstArray) {
        this.fleirtaltekstArray = fleirtaltekstArray;
    }


    public int getAvgaatte_statsraader_menn() {
        return avgaatte_statsraader_menn;
    }

    public void setAvgaatte_statsraader_menn(int avgaatte_statsraader_menn) {
        this.avgaatte_statsraader_menn = avgaatte_statsraader_menn;
    }

    public int getAvgaatte_statsraader_kvinner() {
        return avgaatte_statsraader_kvinner;
    }

    public void setAvgaatte_statsraader_kvinner(int avgaatte_statsraader_kvinner) {
        this.avgaatte_statsraader_kvinner = avgaatte_statsraader_kvinner;
    }



    public Object getRegtalposisjon() {
        return regtalposisjon;
    }

    public void setRegtalposisjon(Object regtalposisjon) {
        this.regtalposisjon = regtalposisjon;
    }


    public int getSluttaar() {
           return sluttaar;
       }

       public void setSluttaar(int sluttaar) {
           this.sluttaar = sluttaar;
       }
    

    public int getAldersdager_ved_slutt_eksakt() {
        return aldersdager_ved_slutt_eksakt;
    }

    public void setAldersdager_ved_slutt_eksakt(int aldersdager_ved_slutt_eksakt) {
        this.aldersdager_ved_slutt_eksakt = aldersdager_ved_slutt_eksakt;
    }

    int aldersdager_ved_slutt_eksakt;




    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }



    public String getFleirtaltekst() {
        return fleirtaltekst;
    }

    public void setFleirtaltekst(String fleirtaltekst) {
        this.fleirtaltekst = fleirtaltekst;
    }

    public Object getTalposisjon() {
        return talposisjon;
    }

    public void setTalposisjon(Object talposisjon) {
        this.talposisjon = talposisjon;
    }

     public BigDecimal getAntaldertotal() {
        return antaldertotal;
    }

    public void setAntaldertotal(BigDecimal antaldertotal) {
        this.antaldertotal = antaldertotal;
    }



    public BigDecimal getAntalder() {
        return antalder;
    }

    public void setAntalder(BigDecimal antalder) {
        this.antalder = antalder;
    }


    public int getMin_reg() {
        return min_reg;
    }

    public void setMin_reg(int min_reg) {
        this.min_reg = min_reg;
    }



    public int getBlokk() {
        return blokk;
    }

    public void setBlokk(int blokk) {
        this.blokk = blokk;
    }


    public Integer getMenn() {
        return menn;
    }

    public void setMenn(int menn) {
        this.menn = menn;
    }

    public Integer getKvinner() {
        return kvinner;
    }

    public void setKvinner(int kvinner) {
        this.kvinner = kvinner;
    }

    public String getPartikode() {
        return partikode;
    }

    public void setPartikode(String partikode) {
        this.partikode = partikode;
    }

    public String getStortingperiodekode() {
        return stortingperiodekode;
    }

    public void setStortingperiodekode(String stortingperiodekode) {
        this.stortingperiodekode = stortingperiodekode;
    }


    public int getAntdag_stolpe() {
        return antdag_stolpe;
    }

    public void setAntdag_stolpe(int antdag_stolpe) {
        this.antdag_stolpe = antdag_stolpe;
    }



    public int getAntdag() {
        return antdag;
    }

    public void setAntdag(int antdag) {
        this.antdag = antdag;
    }



    public int getStartaar() {
        return startaar;
    }

    public void setStartaar(int startaar) {
        this.startaar = startaar;
    }


    public String getEintaltekst_ENG() {
        return eintaltekst_ENG;
    }

    public void setEintaltekst_ENG(String eintaltekst_ENG) {
        this.eintaltekst_ENG = eintaltekst_ENG;
    }

    public String getEksternkommentar_ENG() {
        return eksternkommentar_ENG;
    }

    public void setEksternkommentar_ENG(String eksternkommentar_ENG) {
        this.eksternkommentar_ENG = eksternkommentar_ENG;
    }




    public String getStilling_avvik_ENG() {
        return stilling_avvik_ENG;
    }

    public void setStilling_avvik_ENG(String stilling_avvik_ENG) {
        this.stilling_avvik_ENG = stilling_avvik_ENG;
    }


    public int getFoedt() {
        return foedt;
    }

    public void setFoedt(int foedt) {
        this.foedt = foedt;
    }


    public String getStilling_avvik_NO() {
        return stilling_avvik_NO;
    }

    public void setStilling_avvik_NO(String stilling_avvik_NO) {
        this.stilling_avvik_NO = stilling_avvik_NO;
    }

    public String getEintaltekst_NO() {
        return eintaltekst_NO;
    }

    public void setEintaltekst_NO(String eintaltekst_NO) {
        this.eintaltekst_NO = eintaltekst_NO;
    }

    public int getDoedsaar() {
        return doedsaar;
    }

    public void setDoedsaar(int doedsaar) {
        this.doedsaar = doedsaar;
    }

    public String getEksternkommentar_NO() {
        return eksternkommentar_NO;
    }

    public void setEksternkommentar_NO(String eksternkommentar_NO) {
        this.eksternkommentar_NO = eksternkommentar_NO;
    }

    public int getUttakskode() {
        return uttakskode;
    }

    public void setUttakskode(int uttakskode) {
        this.uttakskode = uttakskode;
    }


    public String getRegjeringsnavn_NO() {
        return regjeringsnavn_NO;
    }

    public void setRegjeringsnavn_NO(String regjeringsnavn_NO) {
        this.regjeringsnavn_NO = regjeringsnavn_NO;
    }

    public String getRegjeringsnavn_ENG() {
        return regjeringsnavn_ENG;
    }

    public void setRegjeringsnavn_ENG(String regjeringsnavn_ENG) {
        this.regjeringsnavn_ENG = regjeringsnavn_ENG;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getSlutt() {
        return slutt;
    }

    public void setSlutt(String slutt) {
        this.slutt = slutt;
    }

    public int getRegjering_reg_kode() {
        return regjering_reg_kode;
    }

    public void setRegjering_reg_kode(int regjering_reg_kode) {
        this.regjering_reg_kode = regjering_reg_kode;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }





}
