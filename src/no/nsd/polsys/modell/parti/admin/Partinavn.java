package no.nsd.polsys.modell.parti.admin;

/**
 * Klassen representerer en enhet eller en endring (en rad i tabellen endring).
 * @author hvb
 */
public class Partinavn {

    // ============================================================== Variabler

    /** ID-nummer til denne enheten. */
    private Integer partikode;
    private String partinavn;
    private String  partinavnENG;
    private Integer aar;
    private String partinavnforkorting;
    private String dokumentasjon;


    public Integer getPartikode() {
        return partikode;
    }

    public void setPartikode(Integer partikode) {
        this.partikode = partikode;
    }

    public String getPartinavn() {
        return partinavn;
    }

    public void setPartinavn(String partinavn) {
        this.partinavn = partinavn;
    }

    public String getPartinavnENG() {
        return partinavnENG;
    }

    public void setPartinavnENG(String partinavnENG) {
        this.partinavnENG = partinavnENG;
    }

    public Integer getAar() {
        return aar;
    }

    public void setAar(Integer aar) {
        this.aar = aar;
    }

    public String getPartinavnforkorting() {
        return partinavnforkorting;
    }

    public void setPartinavnforkorting(String partinavnforkorting) {
        this.partinavnforkorting = partinavnforkorting;
    }

    public String getDokumentasjon() {
        return dokumentasjon;
    }

    public void setDokumentasjon(String dokumentasjon) {
        this.dokumentasjon = dokumentasjon;
    }





}
