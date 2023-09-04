package no.nsd.polsys.modell.parti.admin;

import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Klassen representerer en enhet eller en endring (en rad i tabellen endring).
 * @author hvb
 */
public class PartiDok {

    // ============================================================== Variabler

    /** ID-nummer til denne enheten. */
    private Integer idnum;
    private String doktype;
   private String  partinavn;
   private Integer aar;
   private String tittel;
   private Integer doktypeid;
   private Integer partikode;

    public Integer getDoktypeid() {
        return doktypeid;
    }

    public void setDoktypeid(Integer doktypeid) {
        this.doktypeid = doktypeid;
    }

    public Integer getPartikode() {
        return partikode;
    }

    public void setPartikode(Integer partikode) {
        this.partikode = partikode;
    }


    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public String getDoktype() {
        return doktype;
    }

    public void setDoktype(String doktype) {
        this.doktype = doktype;
    }

    public String getPartinavn() {
        return partinavn;
    }

    public void setPartinavn(String partinavn) {
        this.partinavn = partinavn;
    }

    public Integer getAar() {
        return aar;
    }

    public void setAar(Integer aar) {
        this.aar = aar;
    }


  public Integer getIdnum() {
        return idnum;
    }

    public void setIdnum(Integer idnum) {
        this.idnum = idnum;
    }




}
