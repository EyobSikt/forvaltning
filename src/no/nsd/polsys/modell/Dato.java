package no.nsd.polsys.modell;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author hvb
 */
public class Dato implements Serializable {

   // ============================================================== Variabler
   private int aar;
   private int maaned;
   private int dag;

   // ============================================================ Konstruktør
   public Dato() {
   }

   public Dato(int aar, int maaned, int dag) {
      this.aar = aar;
      this.maaned = maaned;
      this.dag = dag;
   }

   public Dato(Date date) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(date);
      this.aar = cal.get(Calendar.YEAR);
      this.maaned = cal.get(Calendar.MONTH) + 1;
      this.dag = cal.get(Calendar.DAY_OF_MONTH);
   }

   // ================================================================ Metoder
   public int getAar() {
      return aar;
   }

   public void setAar(int aar) {
      this.aar = aar;
   }

   public int getDag() {
      return dag;
   }

   public void setDag(int dag) {
      this.dag = dag;
   }

   public int getMaaned() {
      return maaned;
   }

   public void setMaaned(int maaned) {
      this.maaned = maaned;
   }

   public boolean isGyldig() {
      Calendar cal = this.getCalendar();
      try {
         cal.getTime();
      } catch (Exception e) {
         return false;
      }
      return true;
   }

   public Calendar getCalendar() {
      Calendar cal = new GregorianCalendar();
      cal.setLenient(false);
      cal.clear();
      cal.set(this.aar, this.maaned - 1, this.dag);
      return cal;
   }

   public Date getDate() {
      return this.getCalendar().getTime();
   }

   /**
    * Sjekker om denne datoen er etter i tid enn en annen gitt dato.
    *
    * @param dato en annen dato.
    * @return true hviss denne datoen kommer etter den andre gitte dato.
    */
   public boolean isEtter(Dato dato) {
      Calendar cal = this.getCalendar();
      Calendar annet = dato.getCalendar();

      return (cal.after(annet));
   }

   /**
    * Oppretter en ny dato med gitte verdier.
    *
    * @param aar
    * @param maaned
    * @param dag
    * @param def dato med default verdier.
    * @return
    */
   public static Dato getDato(String aar, String maaned, String dag, Dato def) {
      Dato dato = new Dato();

      // setter default tidspunkt (brukerdato) til sist oppdatert.
      dato.setAar(def.getAar());
      dato.setMaaned(def.getMaaned());
      dato.setDag(def.getDag());
      // setter gitte verdier. Koden under er "fail-fast". Dette er OK.
      try {
         dato.setAar(Integer.parseInt(aar));
         dato.setMaaned(Integer.parseInt(maaned));
         dato.setDag(Integer.parseInt(dag));
      } catch (Exception ignored) {
      }
      return dato;
   }

   @Override
   public String toString() {
      try {
         DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
         Calendar cal = this.getCalendar();
         Date date = cal.getTime();
         return df.format(date);
      } catch (Exception e) {
         return this.dag + "." + this.maaned + "." + this.aar;
      }
   }
}
