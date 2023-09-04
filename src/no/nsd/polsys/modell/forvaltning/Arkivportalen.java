package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en årsmelding.
 *
 * @author hvb
 */
public final class Arkivportalen implements Serializable, Comparable<Arkivportalen> {

   // ============================================================== Variabler
   /**
    * id til denne årsmeldingen.
    */
   private Integer id;
   /**
    * idnum til enheten denne årsmeldingen gjelder.
    */
   private Integer idnum;
   private String tidsrom;
   private String navn;
   private String forvaltningsomrade;
   private String url;


   // ================================================================ Metoder
   public String getTidsrom() {
      return tidsrom;
   }

   public void setTidsrom(String tidsrom) {
      this.tidsrom = tidsrom;
   }

   public String getNavn() {
      return navn;
   }

   public void setNavn(String navn) {
      this.navn = navn;
   }

   public String getForvaltningsomrade() {
      return forvaltningsomrade;
   }

   public void setForvaltningsomrade(String forvaltningsomrade) {
      this.forvaltningsomrade = forvaltningsomrade;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getIdnum() {
      return idnum;
   }

   public void setIdnum(Integer idnum) {
      this.idnum = idnum;
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
      if (!(obj instanceof Arkivportalen)) {
         return false;
      }
      final Arkivportalen other = (Arkivportalen) obj;
      return (this.id != null ? this.id.equals(other.id) : false);
   }

   @Override
   public int hashCode() {
      return (this.id != null ? this.id.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.tidsrom != null ? this.tidsrom.toString() : "");
   }

   public int compareTo(Arkivportalen o) {
      if (o == null) {
         return -1;
      }
      if (o.tidsrom == null) {
         return -1;
      }
      if (this.tidsrom == null) {
         return 1;
      }
      int i = this.tidsrom.compareTo(o.tidsrom);
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
