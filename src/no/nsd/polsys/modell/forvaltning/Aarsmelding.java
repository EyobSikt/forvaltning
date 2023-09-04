package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en årsmelding.
 *
 * @author hvb
 */
public final class Aarsmelding implements Serializable, Comparable<Aarsmelding> {

   // ============================================================== Variabler
   /**
    * id til denne årsmeldingen.
    */
   private Integer id;
   /**
    * idnum til enheten denne årsmeldingen gjelder.
    */
   private Integer idnum;
   private Integer aar;
   private String aarsmelding;
   private String engelskAarsmelding;
   private String sisteUrl;


   // ================================================================ Metoder
   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public String getAarsmelding() {
      return aarsmelding;
   }

   public void setAarsmelding(String aarsmelding) {
      this.aarsmelding = aarsmelding;
   }

   public String getEngelskAarsmelding() {
      return engelskAarsmelding;
   }

   public void setEngelskAarsmelding(String engelskAarsmelding) {
      this.engelskAarsmelding = engelskAarsmelding;
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

   public String getSisteUrl() {
      return sisteUrl;
   }

   public void setSisteUrl(String sisteUrl) {
      this.sisteUrl = sisteUrl;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof Aarsmelding)) {
         return false;
      }
      final Aarsmelding other = (Aarsmelding) obj;
      return (this.id != null ? this.id.equals(other.id) : false);
   }

   @Override
   public int hashCode() {
      return (this.id != null ? this.id.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.aar != null ? this.aar.toString() : "");
   }

   public int compareTo(Aarsmelding o) {
      if (o == null) {
         return -1;
      }
      if (o.aar == null) {
         return -1;
      }
      if (this.aar == null) {
         return 1;
      }
      int i = this.aar.compareTo(o.aar);
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
