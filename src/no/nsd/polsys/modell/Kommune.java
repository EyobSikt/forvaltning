package no.nsd.polsys.modell;

import java.io.Serializable;

/**
 * Klassen representerer en kommune.
 *
 * @author hvb
 */
public final class Kommune implements Serializable {

   // ============================================================== Variabler
   /**
    * Kommunenummer.
    */
   private Integer kode;
   /**
    * Entallstekst til denne kommunen.
    */
   private String tekstEntall;
   /**
    * Entallstekst til denne kommunen.
    */
   private String tekstEntallEngelsk;
   /**
    * Fra og med år.
    */
   private Integer fomAar;
   /**
    * Til og med år.
    */
   private Integer tomAar;


   // ================================================================ Metoder
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

   public Integer getFomAar() {
      return fomAar;
   }

   public void setFomAar(Integer fomAar) {
      this.fomAar = fomAar;
   }

   public Integer getTomAar() {
      return tomAar;
   }

   public void setTomAar(Integer tomAar) {
      this.tomAar = tomAar;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Kommune other = (Kommune) obj;
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
      return (this.tekstEntall != null ? this.tekstEntall : "");
   }
}
