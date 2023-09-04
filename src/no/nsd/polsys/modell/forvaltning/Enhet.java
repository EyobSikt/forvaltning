package no.nsd.polsys.modell.forvaltning;

import no.nsd.polsys.modell.Kommune;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Klassen representerer en enhet eller en endring (en rad i tabellen endring).
 *
 * @author hvb
 */
public class Enhet implements Serializable, Comparable<Enhet> {

   // ============================================================== Variabler
   /**
    * ID-nummer til denne enheten.
    */
   private Integer idnum;
   /**
    * Kort navn til denne enheten.
    */
   private String kortNavn;
   /**
    * Langt navn til denne enheten.
    */
   private String langtNavn;
   /**
    * Engelsk langt navn til denne enheten.
    */
   private String engelskLangtNavn;
   private Integer endringskode;
   private Integer endringsnummer;
    private Integer tilknytningsform;
   private String cofog;
   private Integer nivaa;
   private Integer overordnetIdnum;
   private Integer grunnenhet;
   private Integer flyttbar;
   private Enhet overordnetEnhet;
   private Enhet overordnetDepartement;
   private Enhet overordnetReell;
   private String dok;
   private String engelskDok;
   private String tekniskKommentar;
   private String kommuneNavn;
   private Integer antallAnsatte;
   private Integer antallAnsatte_menn;
   private Integer antallAnsatte_kvinner;
   private Integer arbYrke;
   private String yrkesTittel;
   private String adresse;
   private String organisasjonsform;
   private String level_1_navn;
   private String level_2_navn;;
   private String level_3_navn;;
   private String level_4_navn;;
   private Integer forvaltningsidnum;
   private String postadresse;
   private String registrertdato;
   private String naeringskode;
   private String sektorkode;
   private Integer antall;
   private String organisasjonsgruppe;
   private String organisasjonsgruppebeskrivelse;
   private Integer orgnummer;
   private Integer aar;
   private Integer fraaar;
   private Integer tilaar;
   /**
    * endringstidspunktet for de gitte verdier i denne enheten.
    */
   private Date tidspunkt;
   private Boolean bekreftetDato;
   private String langtNavnOpprettet;

   public Integer getFraaar() {
      return fraaar;
   }

   public void setFraaar(Integer fraaar) {
      this.fraaar = fraaar;
   }

   public Integer getTilaar() {
      return tilaar;
   }

   public void setTilaar(Integer tilaar) {
      this.tilaar = tilaar;
   }

      /**
    * Om enheten er nedlagt.
    */
   private boolean nedlagt;
   /**
    * Tidspunktet enheten ble nedlagt.
    */
   private Date tidspunktNedlagt;
   /**
    * Endringskode ved nedleggelse.
    */
   private Integer endringskodeNedlagt;
   /**
    * Om nedleggelsesdato er bekreftet.
    */
   private Boolean bekreftetDatoNedlagt;
   /**
    * Enheter denne enheten er relatert til, f.eks. denne enheten er sammenslått
    * av disse enhetene.
    */
   private List<Enhet> relasjonerNedlagt;
   /**
    * Relasjonsenheter denne enheten er relatert til, f.eks. denne enheten er
    * sammenslått av disse enhetene.
    */
   private List<Enhet> relasjonerAndreNedlagt;
   // tidspunkt for nåværende tilknytningsform for denne enheten.
   private Date tidspunktTilknytningsform;
   private Integer kommunenummer;
   private Kommune kommune;
   private Integer fylkenummer;
   /**
    * Underordnete enheter, dvs. enheter som har denne enheten som overordnet.
    */
   private Set<Enhet> underordnet;
   /**
    * Underordnete selskaper, dvs. selskaper som har denne enheten som
    * overordnet.
    */
   private List<Enhet> selskaper;
   /**
    * Underordnete stiftinger, dvs. stiftinger som har denne enheten som
    * overordnet.
    */
   private List<Enhet> stiftinger;
   /**
    * Enheter denne enheten er relatert til, f.eks. denne enheten er sammenslått
    * av disse enhetene.
    */
   private List<Enhet> relasjoner;
    /**
    * Relasjonsenheter denne enheten er relatert til, f.eks. denne enheten er
    * sammenslått av disse enhetene.
    */
   private List<Enhet> relasjonerAndre;
   /**
    * Enheter denne enheten er relatert til stortinget saksnummer.
    */
   private List<Enhet> stortingetsaksnummer;
   /**
    * Tallgrupper (og avdelinger).
    */
   private Set<Tallgruppe> tallgrupper;
   /**
    * Tidligere navn denne enheten har hatt.
    */
   private Set<String> tidligereNavn;
   /**
    * Tidligere navn denne enheten har hatt.
    */
   private List<String> tidligereNavnListe;
   private Integer endringsid;
   // Variabel som sier om enheten har gjennomgått gitte endringer.
   private EnhetVariabler variabler;
   private Date tidspunktOpprettetForloper;


   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }


   // ============================================================ Konstruktør
   /**
    * Tom konstruktør.
    */
   public Enhet() {
   }

   public Enhet(Enhet e) {
      this.idnum = e.idnum;

      this.kortNavn = e.kortNavn;
      this.langtNavn = e.langtNavn;
      this.engelskLangtNavn = e.engelskLangtNavn;
      this.endringskode = e.endringskode;
      this.endringsnummer = e.endringsnummer;
      this.tilknytningsform = e.tilknytningsform;
      this.cofog = e.cofog;
      this.nivaa = e.nivaa;
      this.overordnetIdnum = e.overordnetIdnum;
      this.grunnenhet = e.grunnenhet;
      this.flyttbar = e.flyttbar;
      this.dok = e.dok;
      this.engelskDok = e.engelskDok;
      this.tekniskKommentar = e.tekniskKommentar;
      this.tidspunkt = e.tidspunkt;
      this.bekreftetDato = e.bekreftetDato;

      this.kommunenummer = e.kommunenummer;

      this.nedlagt = e.nedlagt;
      this.tidspunktNedlagt = e.tidspunktNedlagt;

      this.endringsid = e.endringsid;

      this.overordnetEnhet = e.overordnetEnhet;

      this.relasjoner = e.relasjoner;
      this.relasjonerAndre = e.relasjonerAndre;
      this.stortingetsaksnummer = e.stortingetsaksnummer;
   }

   public Enhet(EndringCache e) {
      this.endringsid = e.getId();
      this.idnum = e.getIdnum();
      this.kortNavn = e.getKortNavn();
      this.langtNavn = e.getLangtNavn();
      this.engelskLangtNavn = e.getEngelskLangtNavn();
      this.endringskode = e.getEndringskode();
      this.tilknytningsform = e.getTilknytningsform();
      this.cofog = e.getCofog();
      this.nivaa = e.getNivaa();
      this.overordnetIdnum = e.getOverordnetIdnum();
      this.grunnenhet = e.getGrunnenhet();
      this.tidspunkt = e.getTidspunkt();
      this.bekreftetDato = e.getBekreftetDato();
      this.kommunenummer = e.getKommunenummer();
   }

   // ================================================================ Metoder
   public Integer getFlyttbar() {
      return flyttbar;
   }

   public void setFlyttbar(Integer flyttbar) {
      this.flyttbar = flyttbar;
   }

   public Integer getKommunenummer() {
      return kommunenummer;
   }

   public void setKommunenummer(Integer kommunenummer) {
      this.kommunenummer = kommunenummer;
   }

   public String getTekniskKommentar() {
      return tekniskKommentar;
   }

   public void setTekniskKommentar(String tekniskKommentar) {
      this.tekniskKommentar = tekniskKommentar;
   }

   public Integer getEndringsnummer() {
      return endringsnummer;
   }

   public void setEndringsnummer(Integer endringsnummer) {
      this.endringsnummer = endringsnummer;
   }

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

   public boolean isNedlagt() {
      return nedlagt;
   }

   public void setNedlagt(boolean nedlagt) {
      this.nedlagt = nedlagt;
   }

   public Set<Enhet> getUnderordnet() {
      return underordnet;
   }

   public void setUnderordnet(Set<Enhet> underordnet) {
      this.underordnet = underordnet;
   }

   public Integer getGrunnenhet() {
      return grunnenhet;
   }

   public void setGrunnenhet(Integer grunnenhet) {
      this.grunnenhet = grunnenhet;
   }

   public Set<Tallgruppe> getTallgrupper() {
      return tallgrupper;
   }

   public void setTallgrupper(Set<Tallgruppe> tallgrupper) {
      this.tallgrupper = tallgrupper;
   }

   public Set<String> getTidligereNavn() {
      return tidligereNavn;
   }

   public void setTidligereNavn(Set<String> tidligereNavn) {
      this.tidligereNavn = tidligereNavn;
   }

   public List<Enhet> getSelskaper() {
      return selskaper;
   }

   public void setSelskaper(List<Enhet> selskaper) {
      this.selskaper = selskaper;
   }

   public List<Enhet> getStiftinger() {
      return stiftinger;
   }

   public void setStiftinger(List<Enhet> stiftinger) {
      this.stiftinger = stiftinger;
   }

   public String getDok() {
      return dok;
   }

   public void setDok(String dok) {
      this.dok = dok;
   }

   public Boolean getBekreftetDato() {
      return bekreftetDato;
   }

   public void setBekreftetDato(Boolean bekreftetDato) {
      this.bekreftetDato = bekreftetDato;
   }

   public Enhet getOverordnetEnhet() {
      return overordnetEnhet;
   }

   public void setOverordnetEnhet(Enhet overordnetEnhet) {
      this.overordnetEnhet = overordnetEnhet;
   }

   public Kommune getKommune() {
      return kommune;
   }

   public void setKommune(Kommune kommune) {
      this.kommune = kommune;
   }

   public List<Enhet> getRelasjoner() {
      return relasjoner;
   }

   public void setRelasjoner(List<Enhet> relasjoner) {
      this.relasjoner = relasjoner;
   }

   public List<Enhet> getStortingetsaksnummer() {
      return stortingetsaksnummer;
   }

   public void setStortingetsaksnummer(List<Enhet> stortingetsaksnummer) {
      this.stortingetsaksnummer = stortingetsaksnummer;
   }

   public Date getTidspunktNedlagt() {
      return tidspunktNedlagt;
   }

   public void setTidspunktNedlagt(Date tidspunktNedlagt) {
      this.tidspunktNedlagt = tidspunktNedlagt;
   }

   public List<String> getTidligereNavnListe() {
      return tidligereNavnListe;
   }

   public void setTidligereNavnListe(List<String> tidligereNavnListe) {
      this.tidligereNavnListe = tidligereNavnListe;
   }

   public Integer getEndringskodeNedlagt() {
      return endringskodeNedlagt;
   }

   public void setEndringskodeNedlagt(Integer endringskodeNedlagt) {
      this.endringskodeNedlagt = endringskodeNedlagt;
   }

   public Boolean getBekreftetDatoNedlagt() {
      return bekreftetDatoNedlagt;
   }

   public void setBekreftetDatoNedlagt(Boolean bekreftetDatoNedlagt) {
      this.bekreftetDatoNedlagt = bekreftetDatoNedlagt;
   }

   public String getEngelskDok() {
      return engelskDok;
   }

   public void setEngelskDok(String engelskDok) {
      this.engelskDok = engelskDok;
   }

   public List<Enhet> getRelasjonerNedlagt() {
      return relasjonerNedlagt;
   }

   public void setRelasjonerNedlagt(List<Enhet> relasjonerNedlagt) {
      this.relasjonerNedlagt = relasjonerNedlagt;
   }

   public Integer getFylkenummer() {
      return fylkenummer;
   }

   public void setFylkenummer(Integer fylkenummer) {
      this.fylkenummer = fylkenummer;
   }

   public String getCofog() {
      return cofog;
   }

   public void setCofog(String cofog) {
      this.cofog = cofog;
   }

   public String getLangtNavnOpprettet() {
      return langtNavnOpprettet;
   }

   public void setLangtNavnOpprettet(String langtNavnOpprettet) {
      this.langtNavnOpprettet = langtNavnOpprettet;
   }

   public List<Enhet> getRelasjonerAndre() {
      return relasjonerAndre;
   }

   public void setRelasjonerAndre(List<Enhet> relasjonerAndre) {
      this.relasjonerAndre = relasjonerAndre;
   }

   public List<Enhet> getRelasjonerAndreNedlagt() {
      return relasjonerAndreNedlagt;
   }

   public void setRelasjonerAndreNedlagt(List<Enhet> relasjonerAndreNedlagt) {
      this.relasjonerAndreNedlagt = relasjonerAndreNedlagt;
   }

   public Enhet getOverordnetDepartement() {
      return overordnetDepartement;
   }

   public void setOverordnetDepartement(Enhet overordnetDepartement) {
      this.overordnetDepartement = overordnetDepartement;
   }

   public Date getTidspunktTilknytningsform() {
      return tidspunktTilknytningsform;
   }

   public void setTidspunktTilknytningsform(Date tidspunktTilknytningsform) {
      this.tidspunktTilknytningsform = tidspunktTilknytningsform;
   }

   public Integer getOverordnetIdnum() {
      return overordnetIdnum;
   }

   public void setOverordnetIdnum(Integer overordnetIdnum) {
      this.overordnetIdnum = overordnetIdnum;
   }

   public Enhet getOverordnetReell() {
      return overordnetReell;
   }

   public void setOverordnetReell(Enhet overordnetReell) {
      this.overordnetReell = overordnetReell;
   }

   public Integer getEndringsid() {
      return endringsid;
   }

   public void setEndringsid(Integer endringsid) {
      this.endringsid = endringsid;
   }

   public EnhetVariabler getVariabler() {
      return variabler;
   }

   public void setVariabler(EnhetVariabler variabler) {
      this.variabler = variabler;
   }

   public Date getTidspunktOpprettetForloper() {
      return tidspunktOpprettetForloper;
   }

   public void setTidspunktOpprettetForloper(Date tidspunktOpprettetForloper) {
      this.tidspunktOpprettetForloper = tidspunktOpprettetForloper;
   }
   public String getKommuneNavn() {
      return kommuneNavn;
   }
   public void setKommuneNavn(String kommuneNavn) {
      this.kommuneNavn = kommuneNavn;
   }
   public Integer getAntallAnsatte() {
      return antallAnsatte;
   }
   public void setAntallAnsatte(Integer antallAnsatte) {
      this.antallAnsatte = antallAnsatte;
   }
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
   public Integer getArbYrke() {
      return arbYrke;
   }
   public void setArbYrke(Integer arbYrke) {
      this.arbYrke = arbYrke;
   }
   public String getYrkesTittel() {
      return yrkesTittel;
   }
   public void setYrkesTittel(String yrkesTittel) {
      this.yrkesTittel = yrkesTittel;
   }
   public String getAdresse() {
      return adresse;
   }
   public void setAdresse(String adresse) {
      this.adresse = adresse;
   }
   public String getPostadresse() {
      return postadresse;
   }
   public void setPostadresse(String postadresse) {
      this.postadresse = postadresse;
   }
   public String getRegistrertdato() {
      return registrertdato;
   }
   public void setRegistrertdato(String registrertdato) {
      this.registrertdato = registrertdato;
   }
   public String getNaeringskode() {
      return naeringskode;
   }
   public void setNaeringskode(String naeringskode) {
      this.naeringskode = naeringskode;
   }
   public String getSektorkode() {
      return sektorkode;
   }
   public void setSektorkode(String sektorkode) {
      this.sektorkode = sektorkode;
   }
   public String getOrganisasjonsform() {
      return organisasjonsform;
   }
   public void setOrganisasjonsform(String organisasjonsform) {
      this.organisasjonsform = organisasjonsform;
   }
   public String getLevel_1_navn() {
      return level_1_navn;
   }
   public void setLevel_1_navn(String level_1_navn) {
      this.level_1_navn = level_1_navn;
   }
   public String getLevel_2_navn() {
      return level_2_navn;
   }
   public void setLevel_2_navn(String level_2_navn) {
      this.level_2_navn = level_2_navn;
   }
   public String getLevel_3_navn() {
      return level_3_navn;
   }
   public void setLevel_3_navn(String level_3_navn) {
      this.level_3_navn = level_3_navn;
   }
   public String getLevel_4_navn() {
      return level_4_navn;
   }
   public void setLevel_4_navn(String level_4_navn) {
      this.level_4_navn = level_4_navn;
   }
   public Integer getForvaltningsidnum() {
      return forvaltningsidnum;
   }
   public void setForvaltningsidnum(Integer forvaltningsidnum) {
      this.forvaltningsidnum = forvaltningsidnum;
   }
   public Integer getAntall() {
      return antall;
   }
   public void setAntall(Integer antall) {
      this.antall = antall;
   }
   public String getOrganisasjonsgruppe() {
      return organisasjonsgruppe;
   }
   public void setOrganisasjonsgruppe(String organisasjonsgruppe) {
      this.organisasjonsgruppe = organisasjonsgruppe;
   }
   public String getOrganisasjonsgruppebeskrivelse() {
      return organisasjonsgruppebeskrivelse;
   }
   public void setOrganisasjonsgruppebeskrivelse(String organisasjonsgruppebeskrivelse) {
      this.organisasjonsgruppebeskrivelse = organisasjonsgruppebeskrivelse;
   }
   public Integer getOrgnummer() {
      return orgnummer;
   }
   public void setOrgnummer(Integer orgnummer) {
      this.orgnummer = orgnummer;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Enhet other = (Enhet) obj;
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

   public int compareTo(Enhet o) {
      if (o == null) {
         return -1;
      }
      if (this.equals(o)) {
         return 0;
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
