package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.Date;

/**
 * Klassen antall enheter per dato og per nivå eller tilknytningsform.
 *
 * @author hvb
 */
public final class Departementsenhet implements Serializable {

   // ============================================================== Variabler
   /**
    * Dato.
    */
   private Date dato;
   /**
    * Antall enheter per nivå eller tilknytningsform.
    */
   private int[] antall;
   /**
    * Totalt antall enheter.
    */
   private int sum;


   // ================================================================ Metoder
   public int[] getAntall() {
      return antall;
   }

   public void setAntall(int[] antall) {
      this.antall = antall;
   }

   public Date getDato() {
      return dato;
   }

   public void setDato(Date dato) {
      this.dato = dato;
   }

   public int getSum() {
      return sum;
   }

   public void setSum(int sum) {
      this.sum = sum;
   }
}
