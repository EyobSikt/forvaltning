package no.nsd.polsys.modell.storting;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 05.jan.2011
 * Time: 08:45:45
 * To change this template use File | Settings | File Templates.
 */
public class Fraksjonsmerknader implements Comparable<Fraksjonsmerknader>{


 int periode;
 Object periodesum_innst;
 Object periodesum_innst_m_merkn;   
 String periodetekst;

 Object periodesum_totalt_merknader;

 String sesjon;
 Object innstillinger;
  Object innst_m_merknader;
 Object prosent_innst_m_m;
 int totalt_merknader;
 int SesjonId;
  Object prs_innst_m_merkn;
  Object prs_merknader_pr_instilling;
  Object prs_merknader_pr_instilling_merkn;

  String partinavn;
  String partikortnavn;
  int fra_aar;
  int til_aar;
  int komitekode;
  int beggedeltar;
  int ingendeltar;
  int deltar_1;
  int deltar_2;
  Object prs_innst_s_m_merkn ;
  int instillinglengde;
  String instilling;
  Object prs_merknader_s_pr_instilling;
  Object prs_merknader_s_pr_instilling_merkn;

    public int getDeltar_1() {
        return deltar_1;
    }

    public void setDeltar_1(int deltar_1) {
        this.deltar_1 = deltar_1;
    }

    public int getDeltar_2() {
        return deltar_2;
    }

    public void setDeltar_2(int deltar_2) {
        this.deltar_2 = deltar_2;
    }

    public int getInstillinglengde() {
        return instillinglengde;
    }

    public void setInstillinglengde(int instillinglengde) {
        this.instillinglengde = instillinglengde;
    }

    public String getInstilling() {
        return instilling;
    }

    public void setInstilling(String instilling) {
        this.instilling = instilling;
    }

    public Object getPrs_merknader_s_pr_instilling() {
        return prs_merknader_s_pr_instilling;
    }

    public void setPrs_merknader_s_pr_instilling(Object prs_merknader_s_pr_instilling) {
        this.prs_merknader_s_pr_instilling = prs_merknader_s_pr_instilling;
    }

    public Object getPrs_merknader_s_pr_instilling_merkn() {
        return prs_merknader_s_pr_instilling_merkn;
    }

    public void setPrs_merknader_s_pr_instilling_merkn(Object prs_merknader_s_pr_instilling_merkn) {
        this.prs_merknader_s_pr_instilling_merkn = prs_merknader_s_pr_instilling_merkn;
    }

    public Object getPrs_innst_s_m_merkn() {
        return prs_innst_s_m_merkn;
    }

    public void setPrs_innst_s_m_merkn(Object prs_innst_s_m_merkn) {
        this.prs_innst_s_m_merkn = prs_innst_s_m_merkn;
    }



   public int getIngendeltar() {
       return ingendeltar;
    }

    public void setIngendeltar(int ingendeltar) {
        this.ingendeltar = ingendeltar;
    }

    public int getBeggedeltar() {
        return beggedeltar;
    }

    public void setBeggedeltar(int beggedeltar) {
        this.beggedeltar = beggedeltar;
    }

    public int getKomitekode() {
        return komitekode;
    }

    public void setKomitekode(int komitekode) {
        this.komitekode = komitekode;
    }



    public int getFra_aar() {
        return fra_aar;
    }

    public void setFra_aar(int fra_aar) {
        this.fra_aar = fra_aar;
    }

    public int getTil_aar() {
        return til_aar;
    }

    public void setTil_aar(int til_aar) {
        this.til_aar = til_aar;
    }



    public String getPartinavn() {
        return partinavn;
    }

    public String getPartikortnavn() {
        return partikortnavn;
    }

    public void setPartikortnavn(String partikortnavn) {
        this.partikortnavn = partikortnavn;
    }

    public void setPartinavn(String partinavn) {
        this.partinavn = partinavn;
    }


    public Object getPrs_merknader_pr_instilling() {
        return prs_merknader_pr_instilling;
    }

    public void setPrs_merknader_pr_instilling(Object prs_merknader_pr_instilling) {
        this.prs_merknader_pr_instilling = prs_merknader_pr_instilling;
    }

    public Object getPrs_merknader_pr_instilling_merkn() {
        return prs_merknader_pr_instilling_merkn;
    }

    public void setPrs_merknader_pr_instilling_merkn(Object prs_merknader_pr_instilling_merkn) {
        this.prs_merknader_pr_instilling_merkn = prs_merknader_pr_instilling_merkn;
    }


    
    public Object getPrs_innst_m_merkn() {
        return prs_innst_m_merkn;
    }

    public void setPrs_innst_m_merkn(Object prs_innst_m_merkn) {
        this.prs_innst_m_merkn = prs_innst_m_merkn;
    }


    public String getSesjon() {
        return sesjon;
    }

    public void setSesjon(String sesjon) {
        this.sesjon = sesjon;
    }

    public Object getInnstillinger() {
        return innstillinger;
    }

    public void setInnstillinger(Object innstillinger) {
        this.innstillinger = innstillinger;
    }

    public Object getInnst_m_merknader() {
        return innst_m_merknader;
    }

    public void setInnst_m_merknader(Object innst_m_merknader) {
        this.innst_m_merknader = innst_m_merknader;
    }

    public Object getProsent_innst_m_m() {
        return prosent_innst_m_m;
    }

    public void setProsent_innst_m_m(Object prosent_innst_m_m) {
        this.prosent_innst_m_m = prosent_innst_m_m;
    }

    public int getTotalt_merknader() {
        return totalt_merknader;
    }

    public void setTotalt_merknader(int totalt_merknader) {
        this.totalt_merknader = totalt_merknader;
    }

    public int getSesjonId() {
        return SesjonId;
    }

    public void setSesjonId(int sesjonId) {
        SesjonId = sesjonId;
    }




    public int getPeriode() {
        return periode;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public Object getPeriodesum_innst() {
        return periodesum_innst;
    }

    public void setPeriodesum_innst(Object periodesum_innst) {
        this.periodesum_innst = periodesum_innst;
    }

    public Object getPeriodesum_innst_m_merkn() {
        return periodesum_innst_m_merkn;
    }

    public void setPeriodesum_innst_m_merkn(Object periodesum_innst_m_merkn) {
        this.periodesum_innst_m_merkn = periodesum_innst_m_merkn;
    }

    public String getPeriodetekst() {
        return periodetekst;
    }

    public void setPeriodetekst(String periodetekst) {
        this.periodetekst = periodetekst;
    }


    public Object getPeriodesum_totalt_merknader() {
        return periodesum_totalt_merknader;
    }

    public void setPeriodesum_totalt_merknader(Object periodesum_totalt_merknader) {
        this.periodesum_totalt_merknader = periodesum_totalt_merknader;
    }



    @Override
    public int compareTo(Fraksjonsmerknader o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
