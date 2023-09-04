package no.nsd.polsys.modell.admin.regjering;

/**
 * Klassen representerer en enhet eller en endring (en rad i tabellen endring).
 * @author hvb
 */
public class Personnavn {

    // ============================================================== Variabler

    /** ID-nummer til denne enheten. */
    private Integer personkode;
    private String etternavn;
    private String  fornavn;
    private Integer aar;
    private String partinavnforkorting;
    private String dokumentasjon;
    private Integer foedtaar;
    private Integer doedsaar;
    private String parti;


    public Integer getFoedtaar() {
        return foedtaar;
    }

    public void setFoedtaar(Integer foedtaar) {
        this.foedtaar = foedtaar;
    }

    public Integer getDoedsaar() {
        return doedsaar;
    }

    public void setDoedsaar(Integer doedsaar) {
        this.doedsaar = doedsaar;
    }

    public String getParti() {
        return parti;
    }

    public void setParti(String parti) {
        this.parti = parti;
    }


    public Integer getPersonkode() {
        return personkode;
    }

    public void setPersonkode(Integer personkode) {
        this.personkode = personkode;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
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
