package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en enhet i arkivportalen.
 * @author hvb
 */
public class ArkivEnhet implements Serializable {

   private Integer id;
   private String navn;
   private String tidsrom;
   private String forvaltningsomrade;
   private String url;

   
   
   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getNavn() {
      return navn;
   }

   public void setNavn(String navn) {
      this.navn = navn;
   }

   public String getTidsrom() {
      return tidsrom;
   }

   public void setTidsrom(String tidsrom) {
      this.tidsrom = tidsrom;
   }

   public String getForvaltningsomrade() {
      return forvaltningsomrade;
   }

   public void setForvaltningsomrade(String forvaltningsomrade) {
      this.forvaltningsomrade = forvaltningsomrade;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @Override
   public int hashCode() {
      return this.id;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final ArkivEnhet other = (ArkivEnhet) obj;
      if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return this.navn;
   }



}
