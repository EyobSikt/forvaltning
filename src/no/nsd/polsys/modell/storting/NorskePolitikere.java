package no.nsd.polsys.modell.storting;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 25.nov.2010
 * Time: 08:48:43
 * To change this template use File | Settings | File Templates.
 */
public class NorskePolitikere {
     String etterNavn;
    String forNavn;
    int personId;
    int ssPersonId;
    int stPersonId;
    int faar;
    int doedsaar;
    int valgAar;
    String fylkeNavn;
    int fylkeId;
       int fylkeRepresentatNr;
       String stilling;
       int overRepresentatNr;
       String initialer;
       String periode;
       String partiKortnavn;

    String partinavn;
    int partikode;
    String amt;
    String valkrinsnavn;
    int periodeAar;
    int periodekode;
    String statsraad;
    String statssekretar;

    public int getDoedsaar() {
        return doedsaar;
    }

    public void setDoedsaar(int doedsaar) {
        this.doedsaar = doedsaar;
    }

    public String getStatsraad() {
        return statsraad;
    }

    public void setStatsraad(String statsraad) {
        this.statsraad = statsraad;
    }

    public String getStatssekretar() {
        return statssekretar;
    }

    public void setStatssekretar(String statssekretar) {
        this.statssekretar = statssekretar;
    }


    public int getPartikode() {
        return partikode;
    }

    public void setPartikode(int partikode) {
        this.partikode = partikode;
    }



    public String getPartinavn() {
        return partinavn;
    }

    public void setPartinavn(String partinavn) {
        this.partinavn = partinavn;
    }



    public int getPeriodekode() {
        return periodekode;
    }

    public void setPeriodekode(int periodekode) {
        this.periodekode = periodekode;
    }


    
    public Integer getFylkeId() {
        return fylkeId;
    }

    public void setFylkeId(Integer fylkeId) {
        this.fylkeId = fylkeId;
    }


    public int getPeriodeAar() {
        return periodeAar;
    }

    public void setPeriodeAar(int periodeAar) {
        this.periodeAar = periodeAar;
    }

    public String getValkrinsnavn() {
        return valkrinsnavn;
    }

    public void setValkrinsnavn(String valkrinsnavn) {
        this.valkrinsnavn = valkrinsnavn;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

             
    public String getPartiKortnavn() {
        return partiKortnavn;
    }

    public void setPartiKortnavn(String partiKortnavn) {
        this.partiKortnavn = partiKortnavn;
    }

    public String getFylkeNavn() {
        return fylkeNavn;
    }

    public int getFylkeRepresentatNr() {
        return fylkeRepresentatNr;
    }

    public String getStilling() {
        return stilling;
    }

    public int getOverRepresentatNr() {
        return overRepresentatNr;
    }

    public String getInitialer() {
        return initialer;
    }

    public String getPeriode() {
        return periode;
    }


    public void setPeriode(String periode) {
        this.periode = periode;
    }


    public void setFylkeNavn(String fylkeNavn) {
        this.fylkeNavn = fylkeNavn;
    }

    public void setFylkeRepresentatNr(int fylkeRepresentatNr) {
        this.fylkeRepresentatNr = fylkeRepresentatNr;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }

    public void setOverRepresentatNr(int overRepresentatNr) {
        this.overRepresentatNr = overRepresentatNr;
    }

    public void setInitialer(String initialer) {
        this.initialer = initialer;
    }



    public int getValgAar() {
        return valgAar;
    }

    public void setValgAar(int valgAar) {
        this.valgAar = valgAar;
    }


    public int getFaar() {
        return faar;
    }

    public void setFaar(int faar) {
        this.faar = faar;
    }

    public String getForNavn() {
        return forNavn;
    }

    public void setForNavn(String forNavn) {
        this.forNavn = forNavn;
    }


    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getSsPersonId() {
        return ssPersonId;
    }

    public void setSsPersonId(int ssPersonId) {
        this.ssPersonId = ssPersonId;
    }

    public int getStPersonId() {
        return stPersonId;
    }

    public void setStPersonId(int stPersonId) {
        this.stPersonId = stPersonId;
    }



    public String getEtterNavn() {
        return etterNavn;
    }

    public void setEtterNavn(String etterNavn) {
        this.etterNavn = etterNavn;
    }
}
