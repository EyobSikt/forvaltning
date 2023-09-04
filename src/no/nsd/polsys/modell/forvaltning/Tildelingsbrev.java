package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en årsmelding.
 *
 * @author hvb
 */
public final class Tildelingsbrev implements Serializable, Comparable<Tildelingsbrev> {

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
   private String tildelingsbrev;
   private String engelskTildelingsbrev;
   private String sisteUrl;


   // ================================================================ Metoder
   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public String getTildelingsbrev() {
      return tildelingsbrev;
   }

   public void setTildelingsbrev(String tildelingsbrev) {
      this.tildelingsbrev = tildelingsbrev;
   }

   public String getEngelskTildelingsbrev() {
      return engelskTildelingsbrev;
   }

   public void setEngelskTildelingsbrev(String engelskTildelingsbrev) {
      this.engelskTildelingsbrev = engelskTildelingsbrev;
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
      if (!(obj instanceof Tildelingsbrev)) {
         return false;
      }
      final Tildelingsbrev other = (Tildelingsbrev) obj;
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

   public int compareTo(Tildelingsbrev o) {
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
