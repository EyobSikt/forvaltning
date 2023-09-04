package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en instruks.
 *
 * @author hvb
 */
public final class Instruks implements Serializable, Comparable<Instruks> {

   // ============================================================== Variabler
   /**
    * id til denne instruksen.
    */
   private Integer id;
   /**
    * idnum til enheten denne instruksen gjelder.
    */
   private Integer idnum;
   private Integer aar;
   private String instruks;
   private String engelskInstruks;
   private String sisteUrl;


   // ================================================================ Metoder
   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public String getInstruks() {
      return instruks;
   }

   public void setInstruks(String instruks) {
      this.instruks = instruks;
   }

   public String getEngelskInstruks() {
      return engelskInstruks;
   }

   public void setEngelskInstruks(String engelskInstruks) {
      this.engelskInstruks = engelskInstruks;
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
      if (!(obj instanceof Instruks)) {
         return false;
      }
      final Instruks other = (Instruks) obj;
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

   public int compareTo(Instruks o) {
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
