package no.nsd.polsys.modell.admin.storting;

/**
 * Created with IntelliJ IDEA.
 * User: et
 * Date: 01.08.13
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class Votering {

    private Integer id;
    private Object SAK;
    private Object VOTNR;
    private Object TYPSAK;
    private String typesak_text;
    private Object  VOTTYP;
    private String vottype_text;
    private String SAKSREFERANSE;
    private String SAKSREGISTER;

    public String getSesjon() {
        return Sesjon;
    }

    public void setSesjon(String sesjon) {
        Sesjon = sesjon;
    }

    private String Sesjon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getSAK() {
        return SAK;
    }

    public void setSAK(Object SAK) {
        this.SAK = SAK;
    }

    public Object getVOTNR() {
        return VOTNR;
    }

    public void setVOTNR(Object VOTNR) {
        this.VOTNR = VOTNR;
    }

    public Object getTYPSAK() {
        return TYPSAK;
    }

    public void setTYPSAK(Object TYPSAK) {
        this.TYPSAK = TYPSAK;
    }

    public String getTypesak_text() {
        return typesak_text;
    }

    public void setTypesak_text(String typesak_text) {
        this.typesak_text = typesak_text;
    }

    public Object getVOTTYP() {
        return VOTTYP;
    }

    public void setVOTTYP(Object VOTTYP) {
        this.VOTTYP = VOTTYP;
    }

    public String getVottype_text() {
        return vottype_text;
    }

    public void setVottype_text(String vottype_text) {
        this.vottype_text = vottype_text;
    }

    public String getSAKSREFERANSE() {
        return SAKSREFERANSE;
    }

    public void setSAKSREFERANSE(String SAKSREFERANSE) {
        this.SAKSREFERANSE = SAKSREFERANSE;
    }

    public String getSAKSREGISTER() {
        return SAKSREGISTER;
    }

    public void setSAKSREGISTER(String SAKSREGISTER) {
        this.SAKSREGISTER = SAKSREGISTER;
    }




 public float getSesjon(String sesjon){
  float  sesjonid = 0;
     if(sesjon.equals("2022-2023")){ sesjonid = 167;}
     if(sesjon.equals("2021-2022")){ sesjonid = 166;}
     if(sesjon.equals("2020-2021")){ sesjonid = 165;}
     if(sesjon.equals("2019-2020")){ sesjonid = 164;}
     if(sesjon.equals("2018-2019")){ sesjonid = 163;}
     if(sesjon.equals("2017-2018")){ sesjonid = 162;}
     if(sesjon.equals("2016-2017")){ sesjonid = 161;}
     if(sesjon.equals("2015-2016")){ sesjonid = 160;}
     if(sesjon.equals("2014-2015")){ sesjonid = 159;}
     if(sesjon.equals("2013-2014")){ sesjonid = 158;}
     if(sesjon.equals("2012-2013")){ sesjonid = 157;}
     if(sesjon.equals("2011-2012")){ sesjonid = 156;}
     if(sesjon.equals("2010-2011")){ sesjonid = 155;}
     if(sesjon.equals("2009-2010")){ sesjonid = 154;}
     if(sesjon.equals("2008-2009")){ sesjonid = 153;}
     if(sesjon.equals("2007-2008")){ sesjonid = 152;}
     if(sesjon.equals("2006-2007")){ sesjonid = 151;}
     if(sesjon.equals("2005-2006")){ sesjonid = 150;}
     if(sesjon.equals("2004-2005")){ sesjonid = 149;}
     if(sesjon.equals("2003-2004")){ sesjonid = 148;}
     if(sesjon.equals("2002-2003")){ sesjonid = 147;}
     if(sesjon.equals("2001-2002")){ sesjonid = 146;}
     if(sesjon.equals("2000-2001")){ sesjonid = 145;}
     if(sesjon.equals("1999-2000")){ sesjonid = 144;}
     if(sesjon.equals("1998-1999")){ sesjonid = 143;}
     if(sesjon.equals("1997-1998")){ sesjonid = 142;}
     if(sesjon.equals("1996-1997")){ sesjonid = 141;}
     if(sesjon.equals("1995-1996")){ sesjonid = 140;}
    return  sesjonid;
 }
    public float getPeriod(String sesjon){
        float  sesjonid = 0;

        if(sesjon.equals("2017-2018") || sesjon.equals("2018-2019") || sesjon.equals("2019-2020") || sesjon.equals("2020-2021") ){ sesjonid = 162;}
        if(sesjon.equals("2013-2014") || sesjon.equals("2014-2015") || sesjon.equals("2015-2016") || sesjon.equals("2016-2017") ){ sesjonid = 158;}
        if(sesjon.equals("2009-2010") || sesjon.equals("2010-2011") || sesjon.equals("2011-2012") || sesjon.equals("2012-2013")){ sesjonid = 154;}
        if(sesjon.equals("2005-2006") || sesjon.equals("2006-2007") || sesjon.equals("2007-2008") || sesjon.equals("2008-2009")){ sesjonid = 150;}
        if(sesjon.equals("2001-2002") || sesjon.equals("2002-2003") || sesjon.equals("2003-2004") || sesjon.equals("2004-2005")){ sesjonid = 146;}
        if(sesjon.equals("1997-1998") || sesjon.equals("1998-1999") || sesjon.equals("1999-2000") || sesjon.equals("2001-2002")){ sesjonid = 142;}
        return  sesjonid;
    }

    public float typeSak(String typesak){
        float  typesakid = 0;
        if(typesak.startsWith("proposisjon")){ typesakid = 1;}
        if(typesak.startsWith("melding")){ typesakid = 2;}
        if(typesak.startsWith("dokumentser")){ typesakid = 3;}
        if(typesak.startsWith("grunnlovsforslag")){ typesakid = 3;}
        if(typesak.startsWith("representantforslag")){ typesakid = 3;}
        if(typesak.startsWith("innstillingssaker")) {typesakid = 3;}
        if(typesak.startsWith("redegjørelse")){ typesakid = 8;}
        if(typesak.startsWith("innberetning")){ typesakid = 7;}
        if(typesak.startsWith("ikke_spesifisert")){ typesakid = 9;}

        return  typesakid;
    }

    public float typeVot(String typevot){
        float  typevotid = 0;
        if(typevot.startsWith("Innstilling")){ typevotid = 1;}
        if(typevot.startsWith("Romertall")){ typevotid = 1;}
        if(typevot.startsWith("Votering over innstillingens tilråding")){ typevotid = 1;}
        if(typevot.startsWith("Lovens overskrift")){ typevotid = 1;}
        if(typevot.startsWith("Votering over innstillingens forslag til vedtak")){ typevotid = 1;}
        if(typevot.startsWith("Øvrige paragrafer under romertallene")) {typevotid = 1;}
        if(typevot.startsWith("Det voteres over innstillingen")){ typevotid = 1;}
        if(typevot.startsWith("Innstillingens forslag til vedtak")){ typevotid = 1;}
        if(typevot.startsWith("Sak nr.")){ typevotid = 1;}
        if(typevot.startsWith("Stor bokstav")) {typevotid = 1;}
        if(typevot.startsWith("Kap")){ typevotid = 1;}
        if(typevot.startsWith("Lovvedtak")){ typevotid = 1;}
        if(typevot.startsWith("Lov om")){ typevotid = 1;}
        if(typevot.startsWith("Komite")) {typevotid = 1;}
        if(typevot.startsWith("§")){ typevotid = 1;}
        if(typevot.startsWith("Rammeområde")){ typevotid = 1;}
        if(typevot.startsWith("Forslag nr")){ typevotid = 2;}
        if(typevot.toLowerCase().contains("forslag".toLowerCase())){ typevotid = 2;}
        if(typevot.startsWith("Forslag")) {typevotid = 2;}
        if(typevot.startsWith("Det voteres alternativt")){ typevotid = 3;}
        if(typevot.startsWith("Alternativ votering mellom innstillingen")){ typevotid = 3;}
        if(typevot.startsWith("Votering i sak nr")){ typevotid = 9;}
        if(typevot.startsWith("Redegjørelse")) {typevotid = 9;}

        if(typevot.startsWith("A. lov")){ typevotid = 1;}
        if(typevot.startsWith("Debatt")){ typevotid = 9;}
        if(typevot.startsWith("Endrin")){ typevotid = 1;}
        if(typevot.startsWith("Finansministerens redegjørelse")){ typevotid = 9;}
        if(typevot.startsWith("Forslagene")){ typevotid = 2;}

        if(typevot.startsWith("Kapittel")){ typevotid = 1;}
        if(typevot.startsWith("Ny §")){ typevotid = 1;}
        if(typevot.startsWith("Lovanmerkning")){ typevotid = 1;}
        if(typevot.startsWith("Midlertidig")){ typevotid = 1;}
        if(typevot.startsWith("Presidentskapets")){ typevotid = 1;}
        if(typevot.startsWith("Prop")){ typevotid = 1;}
        if(typevot.startsWith("Punktene")){ typevotid = 1;}
        if(typevot.startsWith("Regjeringens erklæring")){ typevotid = 1;}
        if(typevot.startsWith("Resten av romertall")){ typevotid = 1;}
        if(typevot.startsWith("Statsrådens redegjørelse")) {typevotid = 9;}

        if(typevot.startsWith("Suppleringsvalg til Stortingets")){ typevotid = 1;}
        if(typevot.startsWith("Trontalen og")){ typevotid = 1;}
        if(typevot.startsWith("Utenriksministerens redegjørelse")) {typevotid = 9;}
        if(typevot.startsWith("Øvrige")){ typevotid = 1;}


        return  typevotid;
    }





}
