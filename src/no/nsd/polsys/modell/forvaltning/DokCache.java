package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en endringskode, eller grunnenhet, eller nivå eller
 * tilknytning.
 *
 * @author hvb
 */
public final class DokCache implements Serializable, Comparable<DokCache> {

   // ============================================================== Variabler
   /**
    * Kode til denne variabelen.
    */
   private Integer kode;
   /**
    * Entallstekst til denne endringskoden.
    */
   private String tekstEntall;
   /**
    * Flertallstekst til denne endringskoden.
    */
   private String tekstFlertall;
   /**
    * Flertallstekst, forkorting, til denne endringskoden.
    */
   private String tekstFlertallForkorting;
   /**
    * Kort dokumentasjon til denne endringskoden.
    */
   private String kortDokumentasjon;
   /**
    * Tekst som vises i nettsidene, for relasjonendefinisjon for endringskode.
    */
   private String relasjontekst;
   private String tekstEntallEngelsk;
   private String tekstFlertallEngelsk;
   private String tekstFlertallForkortingEngelsk;
   private String kortDokumentasjonEngelsk;
   private String relasjontekstEngelsk;


   // ================================================================ Metoder
   public String getTekstFlertallForkorting() {
      return tekstFlertallForkorting;
   }

   public void setTekstFlertallForkorting(String tekstFlertallForkorting) {
      this.tekstFlertallForkorting = tekstFlertallForkorting;
   }

   public String getTekstFlertallForkortingEngelsk() {
      return tekstFlertallForkortingEngelsk;
   }

   public void setTekstFlertallForkortingEngelsk(String tekstFlertallForkortingEngelsk) {
      this.tekstFlertallForkortingEngelsk = tekstFlertallForkortingEngelsk;
   }

   public String getKortDokumentasjonEngelsk() {
      return kortDokumentasjonEngelsk;
   }

   public void setKortDokumentasjonEngelsk(String kortDokumentasjonEngelsk) {
      this.kortDokumentasjonEngelsk = kortDokumentasjonEngelsk;
   }

   public String getRelasjontekstEngelsk() {
      return relasjontekstEngelsk;
   }

   public void setRelasjontekstEngelsk(String relasjontekstEngelsk) {
      this.relasjontekstEngelsk = relasjontekstEngelsk;
   }

   public Integer getKode() {
      return kode;
   }

   public void setKode(Integer kode) {
      this.kode = kode;
   }

   public String getTekstEntall() {
      return tekstEntall;
   }

   public void setTekstEntall(String tekstEntall) {
      this.tekstEntall = tekstEntall;
   }

   public String getTekstEntallEngelsk() {
      return tekstEntallEngelsk;
   }

   public void setTekstEntallEngelsk(String tekstEntallEngelsk) {
      this.tekstEntallEngelsk = tekstEntallEngelsk;
   }

   public String getTekstFlertall() {
      return tekstFlertall;
   }

   public void setTekstFlertall(String tekstFlertall) {
      this.tekstFlertall = tekstFlertall;
   }

   public String getTekstFlertallEngelsk() {
      return tekstFlertallEngelsk;
   }

   public void setTekstFlertallEngelsk(String tekstFlertallEngelsk) {
      this.tekstFlertallEngelsk = tekstFlertallEngelsk;
   }

   public String getKortDokumentasjon() {
      return kortDokumentasjon;
   }

   public void setKortDokumentasjon(String kortDokumentasjon) {
      this.kortDokumentasjon = kortDokumentasjon;
   }

   public String getRelasjontekst() {
      return relasjontekst;
   }

   public void setRelasjontekst(String relasjontekst) {
      this.relasjontekst = relasjontekst;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      DokCache other = (DokCache) obj;
      if (this.kode == null || !this.kode.equals(other.kode)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return (this.kode != null ? this.kode.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.kode + " " + this.tekstEntall);
   }

   public int compareTo(DokCache o) {
      if (o == null) {
         return -1;
      }
      Integer k = o.getKode();
      if (k == null) {
         return -1;
      }
      if (this.kode == null) {
         return 1;
      }
      return this.kode.compareTo(k);
   }
}
