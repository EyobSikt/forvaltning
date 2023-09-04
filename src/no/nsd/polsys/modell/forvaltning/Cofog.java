package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en COFOG-kode.
 *
 * @author hvb
 */
public final class Cofog implements Serializable, Comparable<Cofog> {

   // ============================================================== Variabler
   private String kode;
   private String tekst;
   private String tekstEngelsk;


   
   // ================================================================ Metoder
   public String getKode() {
      return kode;
   }

   public void setKode(String kode) {
      this.kode = kode;
   }

   public String getTekst() {
      return tekst;
   }

   public void setTekst(String tekst) {
      this.tekst = tekst;
   }

   public String getTekstEngelsk() {
      return tekstEngelsk;
   }

   public void setTekstEngelsk(String tekstEngelsk) {
      this.tekstEngelsk = tekstEngelsk;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Cofog other = (Cofog) obj;
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
      return (this.kode + " " + this.tekst);
   }

   public int compareTo(Cofog o) {
      if (o == null) {
         return -1;
      }
      String k = o.kode;
      if (k == null) {
         return -1;
      }
      if (this.kode == null) {
         return 1;
      }
      return this.kode.compareTo(k);
   }
}
