package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en litteraturkataegori.
 *
 * @author hvb
 */
public class Litteraturkategori implements Serializable {

   // ============================================================== Variabler
   private Integer kode;
   private String kategori;
   private String dokumentasjon;
   private Integer antall;


   // ================================================================ Metoder
   public Integer getAntall() {
      return antall;
   }

   public void setAntall(Integer antall) {
      this.antall = antall;
   }

   public String getKategori() {
      return kategori;
   }

   public void setKategori(String kategori) {
      this.kategori = kategori;
   }

   public Integer getKode() {
      return kode;
   }

   public void setKode(Integer kode) {
      this.kode = kode;
   }

   public String getDokumentasjon() {
      return dokumentasjon;
   }

   public void setDokumentasjon(String dokumentasjon) {
      this.dokumentasjon = dokumentasjon;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Litteraturkategori other = (Litteraturkategori) obj;
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
      return (this.kategori);
   }
}
