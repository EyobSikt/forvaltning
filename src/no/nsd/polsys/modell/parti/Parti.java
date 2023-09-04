package no.nsd.polsys.modell.parti;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 03.mar.2011
 * Time: 12:18:38
 * To change this template use File | Settings | File Templates.
 */
public class Parti {

    String partinavn;
    int partikode;
    String eintaltekst_nn;
    String skipingstidspunkt;
    String nedleggjingsaar;
    String valdeltaking;
    int stortingsparti;
    String partihistorikk;
    int aarstal;
    String dokumentasjon;
    String navn;
    String stilling;
    String bosted;
    int nummer;
    int parti_id;
    String tittel;
    int dok_nr;
    int aar;
    int fylke_kode;
    String fylkenavn;
    Object partilinkkode;
   Object partikode2;
    String doktypenavn;
    Object doktypekode;
   Object aarstall;
    int valgtype;

    public int getValgtype() {
        return valgtype;
    }

    public void setValgtype(int valgtype) {
        this.valgtype = valgtype;
    }


    
    public Object getAarstall() {
        return aarstall;
    }

    public void setAarstall(Object aarstall) {
        this.aarstall = aarstall;
    }

    public Object getDoktypekode() {
        return doktypekode;
    }

    public void setDoktypekode(Object doktypekode) {
        this.doktypekode = doktypekode;
    }

    public String getDoktypenavn() {
        return doktypenavn;
    }

    public void setDoktypenavn(String doktypenavn) {
        this.doktypenavn = doktypenavn;
    }

    public Object getPartilinkkode() {
        return partilinkkode;
    }

    public Object getPartikode2() {
        return partikode2;
    }

    public void setPartikode2(Object partikode2) {
        this.partikode2 = partikode2;
    }

    public void setPartilinkkode(Object partilinkkode) {
        this.partilinkkode = partilinkkode;
    }

    public String getFylkenavn() {
        return fylkenavn;
    }

    public void setFylkenavn(String fylkenavn) {
        this.fylkenavn = fylkenavn;
    }

    public int getFylke_kode() {
        return fylke_kode;
    }

    public void setFylke_kode(int fylke_kode) {
        this.fylke_kode = fylke_kode;
    }

    public int getAar() {
        return aar;
    }

    public void setAar(int aar) {
        this.aar = aar;
    }

    public int getDok_nr() {
        return dok_nr;
    }

    public void setDok_nr(int dok_nr) {
        this.dok_nr = dok_nr;
    }

    public int getParti_id() {
        return parti_id;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public void setParti_id(int parti_id) {
        this.parti_id = parti_id;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getStilling() {
        return stilling;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }

    public String getBosted() {
        return bosted;
    }

    public void setBosted(String bosted) {
        this.bosted = bosted;
    }


    public int getAarstal() {
        return aarstal;
    }

    public void setAarstal(int aarstal) {
        this.aarstal = aarstal;
    }

    public String getDokumentasjon() {
        return dokumentasjon;
    }

    public void setDokumentasjon(String dokumentasjon) {
        this.dokumentasjon = dokumentasjon;
    }

    public String getEintaltekst_nn() {
        return eintaltekst_nn;
    }

    public void setEintaltekst_nn(String eintaltekst_nn) {
        this.eintaltekst_nn = eintaltekst_nn;
    }

    public String getSkipingstidspunkt() {
        return skipingstidspunkt;
    }

    public void setSkipingstidspunkt(String skipingstidspunkt) {
        this.skipingstidspunkt = skipingstidspunkt;
    }

    public String getNedleggjingsaar() {
        return nedleggjingsaar;
    }

    public void setNedleggjingsaar(String nedleggjingsaar) {
        this.nedleggjingsaar = nedleggjingsaar;
    }

    public String getValdeltaking() {
        return valdeltaking;
    }

    public void setValdeltaking(String valdeltaking) {
        this.valdeltaking = valdeltaking;
    }

    public int getStortingsparti() {
        return stortingsparti;
    }

    public void setStortingsparti(int stortingsparti) {
        this.stortingsparti = stortingsparti;
    }

    public String getPartihistorikk() {
        return partihistorikk;
    }

    public void setPartihistorikk(String partihistorikk) {
        this.partihistorikk = partihistorikk;
    }

    public String getPartinavn() {
        return partinavn;
    }

    public void setPartinavn(String partinavn) {
        this.partinavn = partinavn;
    }

    public int getPartikode() {
        return partikode;
    }

    public void setPartikode(int partikode) {
        this.partikode = partikode;
    }

    public static String substringBefore(String str, String separator) {
         if (isEmpty(str)) {
             return str;
         }
         if (separator == null) {
             return "";
         }
         int pos = str.indexOf(separator);
         if (pos == -1) {
             return "";
         }
         return str.substring(0, pos);
     }
       public static String substringAfter(String str, String separator) {
         if (isEmpty(str)) {
             return str;
         }
         if (separator == null) {
             return "";
         }
         int pos = str.indexOf(separator);
         if (pos == -1) {
             return "";
         }
         return str.substring(pos + separator.length());
     }
     public static String escapeQueryChars(String s) {
            StringBuilder sb = new StringBuilder();

           for (int i = 0; i < s.length(); i++) {
             char c = s.charAt(i);
             // These characters are part of the query syntax and must be escaped
              if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                || c == '^' || c == '[' || c == ']'  || c == '{' || c == '}' || c == '~'
                 || c == '?' || c == '|' || c == '&'  || c == ';' || Character.isWhitespace(c)
                ) {
                sb.append('\\');
              }
              sb.append(c);
            }
            return sb.toString();
          }
}
