package no.nsd.polsys.modell.regjering;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 16.mar.2011
 * Time: 08:59:14
 * To change this template use File | Settings | File Templates.
 */
public class RegjeringsDepartment {

     String eintaltekst_no;
     String eintaltekst_eng;
     String fra_dato;
     String til_dato;
     int dep_kode;
     String stilling_avvik_no;
     String stilling_avvik_eng;
     String initialer;
     String navn;
     String fornavn;
     String Eintaltekst_forkorting_no;
     String Eintaltekst_forkorting_eng;
    int person_id;
    String eksternkommentar;

    public String getEksternkommentar() {
        return eksternkommentar;
    }

    public void setEksternkommentar(String eksternkommentar) {
        this.eksternkommentar = eksternkommentar;
    }


    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }



    public String getEintaltekst_eng() {
        return eintaltekst_eng;
    }

    public void setEintaltekst_eng(String eintaltekst_eng) {
        this.eintaltekst_eng = eintaltekst_eng;
    }

    public String getStilling_avvik_no() {
        return stilling_avvik_no;
    }

    public void setStilling_avvik_no(String stilling_avvik_no) {
        this.stilling_avvik_no = stilling_avvik_no;
    }

    public String getStilling_avvik_eng() {
        return stilling_avvik_eng;
    }

    public void setStilling_avvik_eng(String stilling_avvik_eng) {
        this.stilling_avvik_eng = stilling_avvik_eng;
    }

    public String getInitialer() {
        return initialer;
    }

    public void setInitialer(String initialer) {
        this.initialer = initialer;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEintaltekst_forkorting_no() {
        return Eintaltekst_forkorting_no;
    }

    public void setEintaltekst_forkorting_no(String eintaltekst_forkorting_no) {
        Eintaltekst_forkorting_no = eintaltekst_forkorting_no;
    }

    public String getEintaltekst_forkorting_eng() {
        return Eintaltekst_forkorting_eng;
    }

    public void setEintaltekst_forkorting_eng(String eintaltekst_forkorting_eng) {
        Eintaltekst_forkorting_eng = eintaltekst_forkorting_eng;
    }


    public String getEintaltekst_no() {
        return eintaltekst_no;
    }

    public void setEintaltekst_no(String eintaltekst_no) {
        this.eintaltekst_no = eintaltekst_no;
    }

    public String getFra_dato() {
        return fra_dato;
    }

    public void setFra_dato(String fra_dato) {
        this.fra_dato = fra_dato;
    }

    public String getTil_dato() {
        return til_dato;
    }

    public void setTil_dato(String til_dato) {
        this.til_dato = til_dato;
    }

    public int getDep_kode() {
        return dep_kode;
    }

    public void setDep_kode(int dep_kode) {
        this.dep_kode = dep_kode;
    }



    


}
