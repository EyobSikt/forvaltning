package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en lov.
 *
 * @author hvb
 */
public final class Lov implements Serializable, Comparable<Lov> {

   // ============================================================== Variabler
   /**
    * Lovnummer.
    */
   private Long nummer;
   /**
    * Lovnavn
    */
   private String navn;
   /**
    * URL til lovdata.no
    */
   private String url;


   // ================================================================ Metoder
   public String getNavn() {
      return navn;
   }

   public void setNavn(String navn) {
      this.navn = navn;
   }

   public Long getNummer() {
      return nummer;
   }

   public void setNummer(Long nummer) {
      this.nummer = nummer;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof Lov)) {
         return false;
      }
      final Lov other = (Lov) obj;
      return (this.nummer != null ? this.nummer.equals(other.nummer) : false);
   }

   @Override
   public int hashCode() {
      return (this.nummer != null ? this.nummer.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.navn != null ? this.navn : "");
   }

   public int compareTo(Lov o) {
      if (o == null) {
         return -1;
      }
      if (o.nummer == null) {
         return -1;
      }
      if (this.nummer == null) {
         return 1;
      }
      return this.nummer.compareTo(o.nummer);
   }
}
