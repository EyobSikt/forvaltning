
package no.nsd.polsys.modell.forvaltning;

import java.util.Date;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 *
 * @author Eyob
 */
public class Enhetsregisteret {
    
    /** Creates a new instance of Accommodation */
    public Enhetsregisteret() {
    }

    /** Creates a new instance of Accommodation */
    public Enhetsregisteret(
                 Integer topLevel,
                 String level_1,
                 String level_2,
                 String level_3,
                 String level_4,

                 Integer idnum,
                 String navn,

                Integer overordnetEnhet,
                 java.sql.Timestamp  aar,

                 Integer   antallAnsatte_menn,
                 Integer antallAnsatte_kvinner,
                 Integer antallansatte,
                 String organisasjonsform,
                 String sektorkode,
                 String sektorkode_besk,

                String naringskode,
                 String naringskode_besk,
                 Integer nsd_idnum,
                 String forretningskommune,
                 String forretningsadresse,
                 Integer antallAnsatte,
                 String forretingsadresse,
                 String postadresse_adresse,
                 String registrertdato,
                 Integer forvaltningsidnum,
                 String forretingskommune,
                 String forretningsfulladresse,
                 String forretingsland


    ) {
        this.topLevel = topLevel;
       this.level_1 = level_1;
         this.level_2 = level_2;
        this.level_3 = level_3;
        this.level_4 = level_4;

        this.idnum = idnum;
        this.navn = navn;

        this.overordnetEnhet = overordnetEnhet;
        this.aar = aar;
        this.antallAnsatte_menn = antallAnsatte_menn;
        this.antallAnsatte_kvinner = antallAnsatte_kvinner;
        this.antallAnsatte = antallAnsatte;
        this.organisasjonsform = organisasjonsform;
        this.sektorkode =sektorkode;
        this.sektorkode_besk =sektorkode_besk;

        this.naringskode = naringskode;
        this.naringskode_besk = naringskode_besk;
        this.nsd_idnum = nsd_idnum;
        this.forretningskommune = forretningskommune;
        this.forretingsadresse = forretningsadresse;
        this.antallAnsatte = antallAnsatte;
        this.forretingsadresse = forretingsadresse;
        this.postadresse_adresse= postadresse_adresse;
        this.registrertdato = registrertdato;
        this.forvaltningsidnum = forvaltningsidnum;
        this.forretingskommune = forretingskommune;
        this.forretingsland = forretingsland;
        this.forretningsfulladresse = forretningsfulladresse;
    }
    
    /**
     * Holds value of property name.
     */
    private String navn;

    /**
     * Getter for property title.
     * @return Value of property title.
     */
    public String getNavn() {
        return this.navn;
    }

    /**
     * Setter for property title.
     * @param navn New value of property title.
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }

    /**
     * Holds value of property id.
     */
    private Integer idnum;

    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public Integer getIdnum() {
        return this.idnum;
    }

    /**
     * Setter for property id.
     * @param idnum New value of property id.
     */
    public void setIdnum(Integer idnum) {
        this.idnum = idnum;
    }

    /**
     * Holds value of property description.
     */
    private Integer overordnetEnhet;

    /**
     * Getter for property details.
     * @return Value of property details.
     */
    public Integer getOverordnetEnhet() {
        return this.overordnetEnhet;
    }

    /**
     * Setter for property details.
     * @param details New value of property details.
     */
    public void setOverordnetEnhet(Integer overordnetEnhet) {
        this.overordnetEnhet = overordnetEnhet;
    }



    /**
     * Holds value of property naringskode.
     */
    private java.sql.Timestamp  aar;

    /**
     * Getter for property naringskode.
     * @return Value of property naringskode.
     */
    public Date getAar() {
        return this.aar;
    }

    /**
     * Setter for property naringskode.
     * @param aar New value of property naringskode.
     */
    public void setAar(java.sql.Timestamp  aar) {
        this.aar = aar;
    }



    /**
     * Holds value of property naringskode.
     */
    private String naringskode;

    /**
     * Getter for property naringskode.
     * @return Value of property naringskode.
     */
    public String getNaringskode() {
        return this.naringskode;
    }

    /**
     * Setter for property naringskode.
     * @param naringskode New value of property naringskode.
     */
    public void setNaringskode(String naringskode) {
        this.naringskode = naringskode;
    }


private Integer antallAnsatte_menn;
private Integer antallAnsatte_kvinner;
private String level_1;

    public Integer getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Integer topLevel) {
        this.topLevel = topLevel;
    }

    private Integer topLevel;

    public String getLevel_1() {
        return level_1;
    }

    public void setLevel_1(String level_1) {
        this.level_1 = level_1;
    }

    public String getLevel_2() {
        return level_2;
    }

    public void setLevel_2(String level_2) {
        this.level_2 = level_2;
    }

    public String getLevel_3() {
        return level_3;
    }

    public void setLevel_3(String level_3) {
        this.level_3 = level_3;
    }

    public String getLevel_4() {
        return level_4;
    }

    public void setLevel_4(String level_4) {
        this.level_4 = level_4;
    }

    private String level_2;
private String level_3;
private String level_4;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    private Integer year;

    public Integer getAntallAnsatte_menn() {
        return antallAnsatte_menn;
    }

    public void setAntallAnsatte_menn(Integer antallAnsatte_menn) {
        this.antallAnsatte_menn = antallAnsatte_menn;
    }

    public Integer getAntallAnsatte_kvinner() {
        return antallAnsatte_kvinner;
    }

    public void setAntallAnsatte_kvinner(Integer antallAnsatte_kvinner) {
        this.antallAnsatte_kvinner = antallAnsatte_kvinner;
    }

    public String getOrganisasjonsform() {
        return organisasjonsform;
    }

    public void setOrganisasjonsform(String organisasjonsform) {
        this.organisasjonsform = organisasjonsform;
    }

    public String getSektorkode() {
        return sektorkode;
    }

    public void setSektorkode(String sektorkode) {
        this.sektorkode = sektorkode;
    }

    public Integer getNsd_idnum() {
        return nsd_idnum;
    }

    public void setNsd_idnum(Integer nsd_idnum) {
        this.nsd_idnum = nsd_idnum;
    }

    private String organisasjonsform;
private String sektorkode;

    public String getNaringskode_besk() {
        return naringskode_besk;
    }

    public void setNaringskode_besk(String naringskode_besk) {
        this.naringskode_besk = naringskode_besk;
    }

    public String getSektorkode_besk() {
        return sektorkode_besk;
    }

    public void setSektorkode_besk(String sektorkode_besk) {
        this.sektorkode_besk = sektorkode_besk;
    }

    private String naringskode_besk;
    private String sektorkode_besk;
private Integer  nsd_idnum;
private String forretingsadresse;

    public String getForretingsadresse() {
        return forretingsadresse;
    }

    public void setForretingsadresse(String forretingsadresse) {
        this.forretingsadresse = forretingsadresse;
    }

    public String getPostadresse_adresse() {
        return postadresse_adresse;
    }

    public void setPostadresse_adresse(String postadresse_adresse) {
        this.postadresse_adresse = postadresse_adresse;
    }

    public String getRegistrertdato() {
        return registrertdato;
    }

    public void setRegistrertdato(String registrertdato) {
        this.registrertdato = registrertdato;
    }

    public Integer getForvaltningsidnum() {
        return forvaltningsidnum;
    }

    public void setForvaltningsidnum(Integer forvaltningsidnum) {
        this.forvaltningsidnum = forvaltningsidnum;
    }

    public String getForretingskommune() {
        return forretingskommune;
    }

    public void setForretingskommune(String forretingskommune) {
        this.forretingskommune = forretingskommune;
    }

    public String getForretingsland() {
        return forretingsland;
    }

    public void setForretingsland(String forretingsland) {
        this.forretingsland = forretingsland;
    }

    private String postadresse_adresse;
    private String registrertdato;
    private Integer forvaltningsidnum;
    private String forretingskommune;
    private String forretingsland;

    public String getForretningsfulladresse() {
        return forretningsfulladresse;
    }

    public void setForretningsfulladresse(String forretningsfulladresse) {
        this.forretningsfulladresse = forretningsfulladresse;
    }

    private String forretningsfulladresse;


    public Integer getAntallAnsatte() {
        return antallAnsatte;
    }

    public void setAntallAnsatte(Integer antallAnsatte) {
        this.antallAnsatte = antallAnsatte;
    }

    private Integer  antallAnsatte;



    public String getForretningskommune() {
        return forretningskommune;
    }

    public void setForretningskommune(String forretningskommune) {
        this.forretningskommune = forretningskommune;
    }

    private String forretningskommune;

    public String toString() {
        return "Enhetsregisteret "
               + getIdnum()
               +": "
               + getNavn()
               +" ("
               + getNaringskode()
               +")";
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
