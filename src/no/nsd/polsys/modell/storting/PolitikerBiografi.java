package no.nsd.polsys.modell.storting;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 06.des.2010
 * Time: 15:39:52
 * To change this template use File | Settings | File Templates.
 */
public class PolitikerBiografi {

    String etterNavn;
     String forNavn;
    String initialer;
    String fodt;
    String dod;
    String stilling;
    int rep_nr;
    int sup_nr;
     String valkrinsnavn;
    int periode;
    int kode2;
    int periodekode;
    String fleirtaltekst;
    String eksternkommentar;
    String partiNavn;

    String partikortnavn;
    String fraaar;
    String tilaar;
    int fraaarInteger;
    int tilaarInteger;
    String institusjon;
    String eintaltekst;
    String expr2;
    String expr3;
    int status;
    String kommentar;
    String merknad;
    String fodtSted;
    int kjoen;
    int far_f_aar;
    int far_d_aar;
    int mor_f_aar;
    int mor_d_aar;
    String mor_forNavn;
    String mor_etterNavn;
    String far_etterNavn;
    String far_forNavn;
    String far_stilling;
    String mor_stilling;
    int type;
    String utgsted;
    String forlag;
    String komitevervtekst;
    String regjeringdepartment;
    String aktivitet;
    String bokstave;

    public String getBokstave() {
        return bokstave;
    }

    public void setBokstave(String bokstave) {
        this.bokstave = bokstave;
    }


     public String getPartikortnavn() {
        return partikortnavn;
    }

    public String getAktivitet() {
        return aktivitet;
    }

    public void setAktivitet(String aktivitet) {
        this.aktivitet = aktivitet;
    }

    public void setPartikortnavn(String partikortnavn) {
        this.partikortnavn = partikortnavn;
    }
         
    public String getRegjeringdepartment() {
        return regjeringdepartment;
    }

    public void setRegjeringdepartment(String regjeringdepartment) {
        this.regjeringdepartment = regjeringdepartment;
    }

    public String getKomitevervtekst() {
        return komitevervtekst;
    }

    public void setKomitevervtekst(String komitevervtekst) {
        this.komitevervtekst = komitevervtekst;
    }


     
    public String getUtgsted() {
        return utgsted;
    }

    public void setUtgsted(String utgsted) {
        this.utgsted = utgsted;
    }

    public String getForlag() {
        return forlag;
    }

    public void setForlag(String forlag) {
        this.forlag = forlag;
    }



     public String getInitialer() {
        return initialer;
    }

    public void setInitialer(String initialer) {
        this.initialer = initialer;
    }

    public int getKode2() {
        return kode2;
    }

    public void setKode2(int kode2) {
        this.kode2 = kode2;
    }

    public int getPeriodekode() {
        return periodekode;
    }

    public void setPeriodekode(int periodekode) {
        this.periodekode = periodekode;
    }
    
     public String getExpr2() {
        return expr2;
    }

    public void setExpr2(String expr2) {
        this.expr2 = expr2;
    }

     public String getExpr3() {
        return expr3;
    }

    public void setExpr3(String expr3) {
        this.expr3 = expr3;
    }
    public int getTilaarInteger() {
        return tilaarInteger;
    }

    public void setTilaarInteger(int tilaarInteger) {
        this.tilaarInteger = tilaarInteger;
    }

    public int getFraaarInteger() {
           return fraaarInteger;
       }

       public void setFraaarInteger(int fraaarInteger) {
           this.fraaarInteger = fraaarInteger;
       }
    


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFodtSted() {
        return fodtSted;
    }

    public void setFodtSted(String fodtSted) {
        this.fodtSted = fodtSted;
    }

    public int getKjoen() {
        return kjoen;
    }

    public void setKjoen(int kjoen) {
        this.kjoen = kjoen;
    }

    public int getFar_f_aar() {
        return far_f_aar;
    }

    public void setFar_f_aar(int far_f_aar) {
        this.far_f_aar = far_f_aar;
    }

    public int getFar_d_aar() {
        return far_d_aar;
    }

    public void setFar_d_aar(int far_d_aar) {
        this.far_d_aar = far_d_aar;
    }

    public int getMor_f_aar() {
        return mor_f_aar;
    }

    public void setMor_f_aar(int mor_f_aar) {
        this.mor_f_aar = mor_f_aar;
    }

    public int getMor_d_aar() {
        return mor_d_aar;
    }

    public void setMor_d_aar(int mor_d_aar) {
        this.mor_d_aar = mor_d_aar;
    }

    public String getMor_forNavn() {
        return mor_forNavn;
    }

    public void setMor_forNavn(String mor_forNavn) {
        this.mor_forNavn = mor_forNavn;
    }

    public String getMor_etterNavn() {
        return mor_etterNavn;
    }

    public void setMor_etterNavn(String mor_etterNavn) {
        this.mor_etterNavn = mor_etterNavn;
    }

    public String getFar_etterNavn() {
        return far_etterNavn;
    }

    public void setFar_etterNavn(String far_etterNavn) {
        this.far_etterNavn = far_etterNavn;
    }

    public String getFar_forNavn() {
        return far_forNavn;
    }

    public void setFar_forNavn(String far_forNavn) {
        this.far_forNavn = far_forNavn;
    }

    public String getFar_stilling() {
        return far_stilling;
    }

    public void setFar_stilling(String far_stilling) {
        this.far_stilling = far_stilling;
    }

    public String getMor_stilling() {
        return mor_stilling;
    }

    public void setMor_stilling(String mor_stilling) {
        this.mor_stilling = mor_stilling;
    }



    public String getMerknad() {
        return merknad;
    }

    public void setMerknad(String merknad) {
        this.merknad = merknad;
    }



    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public String getEintaltekst() {
        return eintaltekst;
    }

    public void setEintaltekst(String eintaltekst) {
        this.eintaltekst = eintaltekst;
    }




    public String getFraaar() {
        return fraaar;
    }

    public void setFraaar(String fraaar) {
        this.fraaar = fraaar;
    }

    public String getTilaar() {
        return tilaar;
    }

    public void setTilaar(String tilaar) {
        this.tilaar = tilaar;
    }

    public String getInstitusjon() {
        return institusjon;
    }

    public void setInstitusjon(String institusjon) {
        this.institusjon = institusjon;
    }


    public String getStilling() {
        return stilling;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }

    public int getRep_nr() {
        return rep_nr;
    }

    public void setRep_nr(int rep_nr) {
        this.rep_nr = rep_nr;
    }

    public int getSup_nr() {
        return sup_nr;
    }

    public void setSup_nr(int sup_nr) {
        this.sup_nr = sup_nr;
    }

    public String getValkrinsnavn() {
        return valkrinsnavn;
    }

    public void setValkrinsnavn(String valkrinsnavn) {
        this.valkrinsnavn = valkrinsnavn;
    }

    public int getPeriode() {
        return periode;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public String getFleirtaltekst() {
        return fleirtaltekst;
    }

    public void setFleirtaltekst(String fleirtaltekst) {
        this.fleirtaltekst = fleirtaltekst;
    }

    public String getEksternkommentar() {
        return eksternkommentar;
    }

    public void setEksternkommentar(String eksternkommentar) {
        this.eksternkommentar = eksternkommentar;
    }

    public String getPartiNavn() {
        return partiNavn;
    }

    public void setPartiNavn(String partiNavn) {
        this.partiNavn = partiNavn;
    }

    public String getEtterNavn() {
        return etterNavn;
    }

    public void setEtterNavn(String etterNavn) {
        this.etterNavn = etterNavn;
    }

    public String getForNavn() {
        return forNavn;
    }

    public void setForNavn(String forNavn) {
        this.forNavn = forNavn;
    }

    public String getFodt() {
        return fodt;
    }

    public void setFodt(String fodt) {
        this.fodt = fodt;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }


}
