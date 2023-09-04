package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.Date;

/**
 * Klassen en endring (en rad i tabellen endring) og brukes til caching.
 * @author hvb
 */
public final class EndringCache implements Serializable, Comparable<EndringCache> {

    // ============================================================== Variabler

    /** ID, løpenummer til endringen. */
    private Integer id;

    /** ID-nummer til denne enheten. */
    private Integer idnum;

    /** Kort navn til denne enheten. */
    private String kortNavn;

    /** Langt navn til denne enheten. */
    private String langtNavn;

    /** Engelsk langt navn til denne enheten. */
    private String engelskLangtNavn;

    private Integer endringskode;

    private Integer tilknytningsform;

    private String cofog;

    private Integer nivaa;

    private Integer overordnetIdnum;

    private Integer grunnenhet;

    /** endringstidspunktet for de gitte verdier i denne enheten. */
    private Date tidspunkt;

    private Boolean bekreftetDato;
    
    private Integer kommunenummer;



    // ============================================================ Konstruktør

    /**
     * Tom konstruktør.
     */
    public EndringCache() {
    }



    // ================================================================ Metoder

    public String getEngelskLangtNavn() {
        return engelskLangtNavn;
    }

    public void setEngelskLangtNavn(String engelskLangtNavn) {
        this.engelskLangtNavn = engelskLangtNavn;
    }

    public Integer getIdnum() {
        return idnum;
    }

    public void setIdnum(Integer idnum) {
        this.idnum = idnum;
    }

    public String getKortNavn() {
        return kortNavn;
    }

    public void setKortNavn(String kortNavn) {
        this.kortNavn = kortNavn;
    }

    public String getLangtNavn() {
        return langtNavn;
    }

    public void setLangtNavn(String langtNavn) {
        this.langtNavn = langtNavn;
    }

    public Integer getEndringskode() {
        return endringskode;
    }

    public void setEndringskode(Integer endringskode) {
        this.endringskode = endringskode;
    }

    public Integer getNivaa() {
        return nivaa;
    }

    public void setNivaa(Integer nivaa) {
        this.nivaa = nivaa;
    }

    public Integer getTilknytningsform() {
        return tilknytningsform;
    }

    public void setTilknytningsform(Integer tilknytningsform) {
        this.tilknytningsform = tilknytningsform;
    }

    public Date getTidspunkt() {
        return tidspunkt;
    }

    public void setTidspunkt(Date tidspunkt) {
        this.tidspunkt = tidspunkt;
    }

    public Integer getGrunnenhet() {
        return grunnenhet;
    }

    public void setGrunnenhet(Integer grunnenhet) {
        this.grunnenhet = grunnenhet;
    }

    public Integer getKommunenummer() {
        return kommunenummer;
    }

    public void setKommunenummer(Integer kommunenummer) {
        this.kommunenummer = kommunenummer;
    }

    public String getCofog() {
        return cofog;
    }

    public void setCofog(String cofog) {
        this.cofog = cofog;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOverordnetIdnum() {
        return overordnetIdnum;
    }

    public void setOverordnetIdnum(Integer overordnetIdnum) {
        this.overordnetIdnum = overordnetIdnum;
    }

    public Boolean getBekreftetDato() {
        return bekreftetDato;
    }

    public void setBekreftetDato(Boolean bekreftetDato) {
        this.bekreftetDato = bekreftetDato;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EndringCache other = (EndringCache) obj;
        if (this.idnum == null || !this.idnum.equals(other.idnum)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return (this.idnum != null ? this.idnum.hashCode() : 7);
    }


    @Override
    public String toString() {
        return (this.langtNavn != null ? this.langtNavn : "enhet");
    }

    public int compareTo(EndringCache o) {
        if (o == null) {
            return -1;
        }
        if (o.langtNavn == null) {
            return -1;
        }
        if (this.langtNavn == null) {
            return 1;
        }
        int i = this.langtNavn.compareToIgnoreCase(o.langtNavn);
        if (i != 0) {
            return i;
        }
        if (o.idnum == null) {
            return -1;
        }
        if (this.idnum == null) {
            return 1;
        }
        return this.idnum.compareTo(o.idnum);
    }



}
