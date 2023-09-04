package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen inneholder statistikk for DIFI.
 *
 * @author hvb
 */
public final class AntallEnheterEndring implements Serializable {

   // ============================================================== Variabler
   private int fromYear;
   private int toYear;
   private int numberAtStart;
   private int foundings = 0;
   private int pureFoundings = 0;
   private int foundingsOnExisting = 0;
   private int immigration = 0;
   private int immigrationUp = 0;
   private int immigrationDown = 0;
   private int emigration = 0;
   private int emigrationUp = 0;
   private int emigrationDown = 0;
   private int terminations = 0;
   private int pureTerminations = 0;
   private int terminationsIntoExisting = 0;
   private int numberAtEnd;


   // ================================================================ Metoder
   public int getEmigration() {
      return emigration;
   }

   public void setEmigration(int emigration) {
      this.emigration = emigration;
   }

   public int getFoundingsOnExisting() {
      return foundingsOnExisting;
   }

   public void setFoundingsOnExisting(int foundingsOnExisting) {
      this.foundingsOnExisting = foundingsOnExisting;
   }

   public int getFromYear() {
      return fromYear;
   }

   public void setFromYear(int fromYear) {
      this.fromYear = fromYear;
   }

   public int getImmigration() {
      return immigration;
   }

   public void setImmigration(int immigration) {
      this.immigration = immigration;
   }

   public int getNumberAtEnd() {
      return numberAtEnd;
   }

   public void setNumberAtEnd(int numberAtEnd) {
      this.numberAtEnd = numberAtEnd;
   }

   public int getNumberAtStart() {
      return numberAtStart;
   }

   public void setNumberAtStart(int numberAtStart) {
      this.numberAtStart = numberAtStart;
   }

   public int getPureFoundings() {
      return pureFoundings;
   }

   public void setPureFoundings(int pureFoundings) {
      this.pureFoundings = pureFoundings;
   }

   public int getPureTerminations() {
      return pureTerminations;
   }

   public void setPureTerminations(int pureTerminations) {
      this.pureTerminations = pureTerminations;
   }

   public int getTerminationsIntoExisting() {
      return terminationsIntoExisting;
   }

   public void setTerminationsIntoExisting(int terminationsIntoExisting) {
      this.terminationsIntoExisting = terminationsIntoExisting;
   }

   public int getToYear() {
      return toYear;
   }

   public void setToYear(int toYear) {
      this.toYear = toYear;
   }

   public int getEmigrationDown() {
      return emigrationDown;
   }

   public void setEmigrationDown(int emigrationDown) {
      this.emigrationDown = emigrationDown;
   }

   public int getEmigrationUp() {
      return emigrationUp;
   }

   public void setEmigrationUp(int emigrationUp) {
      this.emigrationUp = emigrationUp;
   }

   public int getFoundings() {
      return foundings;
   }

   public void setFoundings(int foundings) {
      this.foundings = foundings;
   }

   public int getImmigrationDown() {
      return immigrationDown;
   }

   public void setImmigrationDown(int immigrationDown) {
      this.immigrationDown = immigrationDown;
   }

   public int getImmigrationUp() {
      return immigrationUp;
   }

   public void setImmigrationUp(int immigrationUp) {
      this.immigrationUp = immigrationUp;
   }

   public int getTerminations() {
      return terminations;
   }

   public void setTerminations(int terminations) {
      this.terminations = terminations;
   }
}
