package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import no.nsd.polsys.modell.Kommune;

/**
 * Klassen representerer en ad hoc KRD-enhet.
 *
 * @author hvb
 */
public class KrdEnhet implements Serializable, Comparable<KrdEnhet> {

   // ============================================================== Variabler
   /**
    * Krd-id til denne enheten.
    */
   private Integer krdid;
   /**
    * ID-nummer til denne enheten.
    */
   private Integer idnum;
   private String virksomhet;
   private Integer stillinger;
   private String kompetansenivaa;
   private Integer aar;
   private Kommune kommune;
   private Boolean selvstendig;
   private String etablering;
   private String type;
   private Boolean nedlagt;
   private String kommentar;


   // ================================================================ Metoder
   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public String getEtablering() {
      return etablering;
   }

   public void setEtablering(String etablering) {
      this.etablering = etablering;
   }

   public Integer getIdnum() {
      return idnum;
   }

   public void setIdnum(Integer idnum) {
      this.idnum = idnum;
   }

   public String getKommentar() {
      return kommentar;
   }

   public void setKommentar(String kommentar) {
      this.kommentar = kommentar;
   }

   public Kommune getKommune() {
      return kommune;
   }

   public void setKommune(Kommune kommune) {
      this.kommune = kommune;
   }

   public String getKompetansenivaa() {
      return kompetansenivaa;
   }

   public void setKompetansenivaa(String kompetansenivaa) {
      this.kompetansenivaa = kompetansenivaa;
   }

   public Integer getKrdid() {
      return krdid;
   }

   public void setKrdid(Integer krdid) {
      this.krdid = krdid;
   }

   public Boolean getNedlagt() {
      return nedlagt;
   }

   public void setNedlagt(Boolean nedlagt) {
      this.nedlagt = nedlagt;
   }

   public Boolean getSelvstendig() {
      return selvstendig;
   }

   public void setSelvstendig(Boolean selvstendig) {
      this.selvstendig = selvstendig;
   }

   public Integer getStillinger() {
      return stillinger;
   }

   public void setStillinger(Integer stillinger) {
      this.stillinger = stillinger;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getVirksomhet() {
      return virksomhet;
   }

   public void setVirksomhet(String virksomhet) {
      this.virksomhet = virksomhet;
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
      KrdEnhet other = (KrdEnhet) obj;
      if (this.krdid == null || !this.krdid.equals(other.krdid)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return (this.krdid != null ? this.krdid.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.virksomhet != null ? this.virksomhet : "enhet");
   }

   public int compareTo(KrdEnhet o) {
      if (o == null) {
         return -1;
      }
      if (o.virksomhet == null) {
         return -1;
      }
      if (this.virksomhet == null) {
         return 1;
      }
      int i = this.virksomhet.compareToIgnoreCase(o.virksomhet);
      if (i != 0) {
         return i;
      }
      if (o.krdid == null) {
         return -1;
      }
      if (this.krdid == null) {
         return 1;
      }
      return this.krdid.compareTo(o.krdid);
   }
}
