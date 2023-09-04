package no.nsd.polsys.modell.admin.regjering;

/**
 * Klassen representerer en enhet eller en endring (en rad i tabellen endring).
 * @author hvb
 */
public class Personinfo {

    // ============================================================== Variabler

    /** ID-nummer til denne enheten. */
    private Integer personkode;
    private String etternavn;
    private String  fornavn;
    private String embete;
    private String department;
    private String virkeperiode_start;
    private String virkeperiode_slutt;
    private int kode_dep;

    private Integer partikode;
    private String partinavn;
    private Integer departmentkode;
    private String departmentnavn;
    private Integer foedtaar;
    private Integer doedsaar;
    private Integer parti;
    private String eksternkommentar;
    private int kode_embete;

    public int getKode_embete() {
        return kode_embete;
    }

    public void setKode_embete(int kode_embete) {
        this.kode_embete = kode_embete;
    }

    public String getEksternkommentar() {
        return eksternkommentar;
    }

    public void setEksternkommentar(String eksternkommentar) {
        this.eksternkommentar = eksternkommentar;
    }


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

    public Integer getParti() {
        return parti;
    }

    public void setParti(Integer parti) {
        this.parti = parti;
    }

    public Integer getDepartmentkode() {
        return departmentkode;
    }

    public void setDepartmentkode(Integer departmentkode) {
        this.departmentkode = departmentkode;
    }

    public String getDepartmentnavn() {
        return departmentnavn;
    }

    public void setDepartmentnavn(String departmentnavn) {
        this.departmentnavn = departmentnavn;
    }


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


    public int getKode_dep() {
        return kode_dep;
    }

    public void setKode_dep(int kode_dep) {
        this.kode_dep = kode_dep;
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

    public String getEmbete() {
        return embete;
    }

    public void setEmbete(String embete) {
        this.embete = embete;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getVirkeperiode_start() {
        return virkeperiode_start;
    }

    public void setVirkeperiode_start(String virkeperiode_start) {
        this.virkeperiode_start = virkeperiode_start;
    }

    public String getVirkeperiode_slutt() {
        return virkeperiode_slutt;
    }

    public void setVirkeperiode_slutt(String virkeperiode_slutt) {
        this.virkeperiode_slutt = virkeperiode_slutt;
    }



}
