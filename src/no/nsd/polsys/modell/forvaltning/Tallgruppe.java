package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.Date;
import no.nsd.polsys.modell.Kommune;

/**
 * Klassen representerer en tallgruppe.
 *
 * @author hvb
 */
public final class Tallgruppe implements Serializable, Comparable<Tallgruppe> {

   // ============================================================== Variabler
   /**
    * ID til denne tallgruppen.
    */
   private Integer id;
   /**
    * ID-nummer til enheten denne tallgruppen tilhører.
    */
   private Integer idnum;
   /**
    * Enheten denne tallgruppen tilhører.
    */
   private Enhet enhet;
   /**
    * Løpenummer til denne tallgruppen internt for idnum.
    */
   private Integer lpnr;
   /**
    * Kort navn på denne enheten.
    */
   private String navn;
   /**
    * Engelsk langt navn på denne enheten.
    */
   private String engelskNavn;
   /**
    * 0=Bekreftet, 1=Ubekreftet, 2=Estimert
    */
   private Integer enheterKartlagt;
   private Integer nivaa;
   private Integer antallEnheter;
   private Integer aar;
   /**
    * Fra-tidspunkt for når denne gruppen gjelder.
    */
   private Date fraTidspunkt;
   /**
    * Til-tidspunkt for når denne gruppen gjelder.
    */
   private Date tilTidspunkt;
   // true hviss tallgruppe, false hviss avdeling.
   private boolean tallgruppe;
   // 21 eller 31.
   private Integer tallgruppekode;
   /**
    * Selve lokaliseringsdokumentasjonen.
    */
   private String dokumentasjon;
   /**
    * Selve lokaliseringsdokumentasjonen.
    */
   private String engelskDokumentasjon;
   /**
    * Plussenheter fra forrige år, elle kommentarEndring.
    */
   private String plussEnhet;
   /**
    * Minusenheter fra forrige år, elle kommentarEndring.
    */
   private String minusEnhet;
   /**
    * Refererer til pluss- og minusenheter.
    */
   private String kommentarEndring;
   /**
    * Kommentar til kilde.
    */
   private String kommentarKilde;
   /**
    * Annen kommentar.
    */
   private String kommentarAnnet;
   /**
    * Kommentarer (fotnoter) til dokumentasjonen.
    */
   private String kommentarNote;
   /**
    * Refererer til forrige år hvor lokaliseringene (og ikke bare antallet)
    * faktisk er kartlagt.
    */
   private Integer forrigeAar;
   /**
    * Refererer til neste år hvor lokaliseringene (og ikke bare antallet)
    * faktisk er kartlagt.
    */
   private Integer nesteAar;
   private Kommune kommune;
   private String ansatteKoder;


   
   // ================================================================ Metoder
   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public Integer getAntallEnheter() {
      return antallEnheter;
   }

   public void setAntallEnheter(Integer antallEnheter) {
      this.antallEnheter = antallEnheter;
   }

   public String getEngelskNavn() {
      return engelskNavn;
   }

   public void setEngelskNavn(String engelskNavn) {
      this.engelskNavn = engelskNavn;
   }

   public Enhet getEnhet() {
      return enhet;
   }

   public void setEnhet(Enhet enhet) {
      this.enhet = enhet;
   }

   public Date getFraTidspunkt() {
      return fraTidspunkt;
   }

   public void setFraTidspunkt(Date fraTidspunkt) {
      this.fraTidspunkt = fraTidspunkt;
   }

   public Integer getIdnum() {
      return idnum;
   }

   public void setIdnum(Integer idnum) {
      this.idnum = idnum;
   }

   public String getNavn() {
      return navn;
   }

   public void setNavn(String navn) {
      this.navn = navn;
   }

   public Integer getEnheterKartlagt() {
      return enheterKartlagt;
   }

   public void setEnheterKartlagt(Integer enheterKartlagt) {
      this.enheterKartlagt = enheterKartlagt;
   }

   public Integer getNivaa() {
      return nivaa;
   }

   public void setNivaa(Integer nivaa) {
      this.nivaa = nivaa;
   }

   public Date getTilTidspunkt() {
      return tilTidspunkt;
   }

   public void setTilTidspunkt(Date tilTidspunkt) {
      this.tilTidspunkt = tilTidspunkt;
   }

   public boolean isTallgruppe() {
      return tallgruppe;
   }

   public void setTallgruppe(boolean tallgruppe) {
      this.tallgruppe = tallgruppe;
   }

   public Integer getTallgruppekode() {
      return tallgruppekode;
   }

   public void setTallgruppekode(Integer tallgruppekode) {
      this.tallgruppekode = tallgruppekode;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getLpnr() {
      return lpnr;
   }

   public void setLpnr(Integer lpnr) {
      this.lpnr = lpnr;
   }

   public String getDokumentasjon() {
      return dokumentasjon;
   }

   public void setDokumentasjon(String dokumentasjon) {
      this.dokumentasjon = dokumentasjon;
   }

   public Integer getForrigeAar() {
      return forrigeAar;
   }

   public void setForrigeAar(Integer forrigeAar) {
      this.forrigeAar = forrigeAar;
   }

   public String getKommentarAnnet() {
      return kommentarAnnet;
   }

   public void setKommentarAnnet(String kommentarAnnet) {
      this.kommentarAnnet = kommentarAnnet;
   }

   public String getKommentarEndring() {
      return kommentarEndring;
   }

   public void setKommentarEndring(String kommentarEndring) {
      this.kommentarEndring = kommentarEndring;
   }

   public String getKommentarKilde() {
      return kommentarKilde;
   }

   public void setKommentarKilde(String kommentarKilde) {
      this.kommentarKilde = kommentarKilde;
   }

   public String getKommentarNote() {
      return kommentarNote;
   }

   public void setKommentarNote(String kommentarNote) {
      this.kommentarNote = kommentarNote;
   }

   public String getMinusEnhet() {
      return minusEnhet;
   }

   public void setMinusEnhet(String minusEnhet) {
      this.minusEnhet = minusEnhet;
   }

   public Integer getNesteAar() {
      return nesteAar;
   }

   public void setNesteAar(Integer nesteAar) {
      this.nesteAar = nesteAar;
   }

   public String getPlussEnhet() {
      return plussEnhet;
   }

   public void setPlussEnhet(String plussEnhet) {
      this.plussEnhet = plussEnhet;
   }

   public Kommune getKommune() {
      return kommune;
   }

   public void setKommune(Kommune kommune) {
      this.kommune = kommune;
   }

   public String getEngelskDokumentasjon() {
      return engelskDokumentasjon;
   }

   public void setEngelskDokumentasjon(String engelskDokumentasjon) {
      this.engelskDokumentasjon = engelskDokumentasjon;
   }

   public String getAnsatteKoder() {
      return ansatteKoder;
   }

   public void setAnsatteKoder(String ansatteKoder) {
      this.ansatteKoder = ansatteKoder;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Tallgruppe other = (Tallgruppe) obj;
      if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return (this.id != null ? this.id.hashCode() : 11);
   }

   @Override
   public String toString() {
      return (this.navn != null ? this.navn : "");
   }

   @Override
   public int compareTo(Tallgruppe o) {
      if (o == null) {
         return -1;
      }
      if (o.navn == null) {
         return -1;
      }
      if (this.navn == null) {
         return 1;
      }
      int i = this.navn.compareToIgnoreCase(o.navn);
      if (i != 0) {
         return i;
      }
      if (o.id == null) {
         return -1;
      }
      if (this.id == null) {
         return 1;
      }
      return this.id.compareTo(o.id);
   }
}
