package no.nsd.polsys.modell.parti;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 03.mar.2011
 * Time: 12:18:38
 * To change this template use File | Settings | File Templates.
 */

public final class Solr  {

Object id;
String tittel;
String partinavn;
String doktype;
String aarstall;
String tekst;
String partinavn_tall;
String doktype_tall;
Object partikode;
String doktypenavn;
String aar_tall;
String aar;
String filename;
 String filenameformat;
Integer partiid;
Integer dokumentid;
Integer dokaar;
Integer partinavntall;
Integer doktypetall;
Integer aartall;
String forekomst;
int  treffnummer;
String parti;
 Integer dokumenttall;
 Object partilinkid;
 Object doktypekode;
Object samiskdokref;

    public Object getSamiskdokref() {
        return samiskdokref;
    }

    public void setSamiskdokref(Object samiskdokref) {
        this.samiskdokref = samiskdokref;
    }
    
    public String getFilenameformat() {
        return filenameformat;
    }

    public void setFilenameformat(String filenameformat) {
        this.filenameformat = filenameformat;
    }


    public Object getDoktypekode() {
        return doktypekode;
    }

    public void setDoktypekode(Object doktypekode) {
        this.doktypekode = doktypekode;
    }


 public static Comparator<Solr> COMPARE_BY_PARTINAVN = new Comparator<Solr>() {
        public int compare(Solr one, Solr other) {
            return one.partinavn.compareTo(other.partinavn);
        }
    };

    public static Comparator<Solr> COMPARE_BY_DOKUMENTTYPE = new Comparator<Solr>() {
        public int compare(Solr one, Solr other) {
            return one.doktypenavn.compareTo(other.doktypenavn);
        }
    };

    public static Comparator<Solr> COMPARE_BY_AAR = new Comparator<Solr>() {
        public int compare(Solr one, Solr other) {
            return other.aar.compareTo(one.aar);
        }
    };
    
 /*
public int compareTo(Solr other) {
        return aarstall.compareTo(other.aarstall);
    }


public int compareTo(Solr other) {
        return partinavn.compareTo(other.partinavn);
    }
 */




    public Object getPartilinkid() {
        return partilinkid;
    }

    public void setPartilinkid(Object partilinkid) {
        this.partilinkid = partilinkid;
    }

    public String getParti() {
        return parti;
    }

    public void setParti(String parti) {
        this.parti = parti;
    }

    public Integer getDokumenttall() {
        return dokumenttall;
    }

    public void setDokumenttall(Integer dokumenttall) {
        this.dokumenttall = dokumenttall;
    }


    public int getTreffnummer() {
        return treffnummer;
    }

    public void setTreffnummer(int treffnummer) {
        this.treffnummer = treffnummer;
    }

    public String getForekomst() {
        return forekomst;
    }

    public void setForekomst(String forekomst) {
        this.forekomst = forekomst;
    }

    public Integer getPartiid() {
        return partiid;
    }

    public void setPartiid(Integer partiid) {
        this.partiid = partiid;
    }

    public Integer getDokumentid() {
        return dokumentid;
    }

    public void setDokumentid(Integer dokumentid) {
        this.dokumentid = dokumentid;
    }

    public Integer getDokaar() {
        return dokaar;
    }

    public void setDokaar(Integer dokaar) {
        this.dokaar = dokaar;
    }




    public Integer getDoktypetall() {
        return doktypetall;
    }

    public void setDoktypetall(Integer doktypetall) {
        this.doktypetall = doktypetall;
    }

    public Integer getAartall() {
        return aartall;
    }

    public void setAartall(Integer aartall) {
        this.aartall = aartall;
    }



    public Integer getPartinavntall() {
        return partinavntall;
    }

    public void setPartinavntall(Integer partinavntall) {
        this.partinavntall = partinavntall;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public String getAar_tall() {
        return aar_tall;
    }

    public void setAar_tall(String aar_tall) {
        this.aar_tall = aar_tall;
    }

    public String getAar() {
        return aar;
    }

    public void setAar(String aar) {
        this.aar = aar;
    }


    public String getDoktypenavn() {
        return doktypenavn;
    }

    public void setDoktypenavn(String doktypenavn) {
        this.doktypenavn = doktypenavn;
    }
    public Object getPartikode() {
        return partikode;
    }

    public void setPartikode(Object partikode) {
        this.partikode = partikode;
    }

    public String getDoktype_tall() {
        return doktype_tall;
    }

    public void setDoktype_tall(String doktype_tall) {
        this.doktype_tall = doktype_tall;
    }



    public String getPartinavn_tall() {
        return partinavn_tall;
    }

    public void setPartinavn_tall(String partinavn_tall) {
        this.partinavn_tall = partinavn_tall;
    }




    public String getPartinavn() {
        return partinavn;
    }

    public void setPartinavn(String partinavn) {
        this.partinavn = partinavn;
    }

    public String getDoktype() {
        return doktype;
    }

    public void setDoktype(String doktype) {
        this.doktype = doktype;
    }

    public String getAarstall() {
        return aarstall;
    }

    public void setAarstall(String aarstall) {
        this.aarstall = aarstall;
    }

    public String getTekst() {
           return tekst;
       }

       public void setTekst(String tekst) {
           this.tekst = tekst;
       }



       public String getTittel() {
           return tittel;
       }

       public void setTittel(String tittel) {
           this.tittel = tittel;
       }



       public Object getId() {
           return id;
       }

       public void setId(Object id) {
           this.id = id;
       }


}
