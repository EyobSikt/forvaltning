package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.Date;

/**
 * Klassen representerer en gitt relasjon mellom to enheter til.
 *
 * @author hvb
 */
public class EnhetRelasjon implements Serializable, Comparable<EnhetRelasjon> {

   // ============================================================== Variabler
   
   private Integer id;
   
   private Date tidspunkt;
   
   private Enhet enhetA;
   private Enhet enhetB;

   private Integer endringskode;
   
   private boolean valgt;
   
   // ============================================================ Konstruktør
   public EnhetRelasjon() {
   }


   // ================================================================ Metoder

   public Integer getEndringskode() {
      return endringskode;
   }

   public void setEndringskode(Integer endringskode) {
      this.endringskode = endringskode;
   }

   public Date getTidspunkt() {
      return tidspunkt;
   }

   public void setTidspunkt(Date tidspunkt) {
      this.tidspunkt = tidspunkt;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Enhet getEnhetA() {
      return enhetA;
   }

   public void setEnhetA(Enhet enhetA) {
      this.enhetA = enhetA;
   }

   public Enhet getEnhetB() {
      return enhetB;
   }

   public void setEnhetB(Enhet enhetB) {
      this.enhetB = enhetB;
   }

   public boolean isValgt() {
      return valgt;
   }

   public void setValgt(boolean valgt) {
      this.valgt = valgt;
   }
   
   
   
   
   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      EnhetRelasjon other = (EnhetRelasjon) obj;
      if (this.id == null || !this.id.equals(other.id)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return (this.id != null ? this.id.hashCode() : 7);
   }

   @Override
   public String toString() {
      return "enhetrelasjon";
   }

   @Override
   public int compareTo(EnhetRelasjon o) {
      if (o == null) {
         return -1;
      }
      if (this.equals(o)) {
         return 0;
      }
      
      
      if (o.tidspunkt == null) {
         return -1;
      }
      if (this.tidspunkt == null) {
         return 1;
      }
      int t = this.tidspunkt.compareTo(o.tidspunkt);
      if (t != 0) {
         return t;
      }
      
      if (o.endringskode == null) {
         return 1;
      }
      if (this.endringskode == null) {
         return -1;
      }
      int e = this.endringskode.compareTo(o.endringskode);
      if (e != 0) {
         return e * -1;
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
