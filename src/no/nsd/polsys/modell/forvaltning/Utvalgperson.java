package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer et utvalg.
 *
 * @author hvb
 */
public class Utvalgperson implements Serializable, Comparable<Utvalgperson> {

   // ============================================================== Variabler
   private Integer pnr;
   private String etternavn;
   private String fornavn;
   private Integer kjoenn;
   private Integer foedselsaar;
   private String Hovedfunksjon;
   private String utvalgnavn;
   private String utvalg2003;
   private String utvalg2004;

   // ================================================================ Metoder
   public String getUtvalg2003() {
      return utvalg2003;
   }

   public void setUtvalg2003(String utvalg2003) {
      this.utvalg2003 = utvalg2003;
   }

   public String getUtvalg2004() {
      return utvalg2004;
   }

   public void setUtvalg2004(String utvalg2004) {
      this.utvalg2004 = utvalg2004;
   }

   public String getHovedfunksjon() {
      return Hovedfunksjon;
   }

   public void setHovedfunksjon(String hovedfunksjon) {
      Hovedfunksjon = hovedfunksjon;
   }

   public String getUtvalgnavn() {
      return utvalgnavn;
   }

   public void setUtvalgnavn(String utvalgnavn) {
      this.utvalgnavn = utvalgnavn;
   }

   public String getEtternavn() {
      return etternavn;
   }

   public void setEtternavn(String etternavn) {
      this.etternavn = etternavn;
   }

   public Integer getFoedselsaar() {
      return foedselsaar;
   }

   public void setFoedselsaar(Integer foedselsaar) {
      this.foedselsaar = foedselsaar;
   }

   public String getFornavn() {
      return fornavn;
   }

   public void setFornavn(String fornavn) {
      this.fornavn = fornavn;
   }

   public Integer getKjoenn() {
      return kjoenn;
   }

   public void setKjoenn(Integer kjoenn) {
      this.kjoenn = kjoenn;
   }

   public Integer getPnr() {
      return pnr;
   }

   public void setPnr(Integer pnr) {
      this.pnr = pnr;
   }

   public String getNavn() {
      if (this.fornavn == null && this.etternavn == null) {
         return null;
      }
      String navn = "";
      if (this.fornavn != null) {
         navn += this.fornavn;
         if (this.etternavn != null) {
            navn += " ";
         }
      }
      if (this.etternavn != null) {
         navn += this.etternavn;
      }
      return navn;
   }

   public String getEtterOgFornavn() {
      if (this.etternavn == null && this.fornavn == null) {
         return null;
      }
      String navn = "";
      if (this.etternavn != null) {
         navn += this.etternavn;
         if (this.fornavn != null) {
            navn += ", ";
         }
      }
      if (this.fornavn != null) {
         navn += this.fornavn;
      }
      return navn;
   }

   // Overskrevne metoder
   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Utvalgperson other = (Utvalgperson) obj;
      if (this.pnr == null || !this.pnr.equals(other.pnr)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return (this.pnr != null ? this.pnr.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.etternavn != null ? this.etternavn : "");
   }

   public int compareTo(Utvalgperson o) {
      if (o == null) {
         return -1;
      }
      if (o.getEtterOgFornavn() == null) {
         return -1;
      }
      if (this.getEtterOgFornavn() == null) {
         return 1;
      }
      int i = this.getEtterOgFornavn().compareToIgnoreCase(o.getEtterOgFornavn());
      if (i != 0) {
         return i;
      }
      if (o.pnr == null) {
         return -1;
      }
      if (this.pnr == null) {
         return 1;
      }
      return this.pnr.compareTo(o.pnr);
   }
}
