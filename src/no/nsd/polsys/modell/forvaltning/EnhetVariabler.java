package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen inneholder variabler som sier om en enhet har gjennomgått gitte
 * endringer.
 *
 * @author hvb
 */
public class EnhetVariabler implements Serializable {

   // ============================================================== Variabler
   private int endring102;
   private int endring104;
   private int endring106;
   private int endring111;
   private int endring202;
   private int endring203;
   private int endring211;

   private int endring101;
   private int endring112;
   private int endring207;
   private int endring209;
   private int endring213;
   private int endring221;
   private int endring222;
   private int endring223;
   private int endring291;
   private int endring292;
   private int endring303;
   private int endring304;
   private int endring306;
   private int endring310;
   private int endring311;
   private int endring312;

   // antall underliggende enheter, kun substansielle og ikke satellitter.
   private int antallUnder;
   // antall underliggende enheter inkludert grupper og etater, men ikke satellitter.
   private int antallUnderGruppe;
   // antall underliggende satellitter.
   private int antallSat;


   public int getEndring101() {
      return endring101;
   }

   public void setEndring101(int endring101) {
      this.endring101 = endring101;
   }

   public int getEndring112() {
      return endring112;
   }

   public void setEndring112(int endring112) {
      this.endring112 = endring112;
   }

   public int getEndring207() {
      return endring207;
   }

   public void setEndring207(int endring207) {
      this.endring207 = endring207;
   }

   public int getEndring209() {
      return endring209;
   }

   public void setEndring209(int endring209) {
      this.endring209 = endring209;
   }

   public int getEndring213() {
      return endring213;
   }

   public void setEndring213(int endring213) {
      this.endring213 = endring213;
   }

   public int getEndring221() {
      return endring221;
   }

   public void setEndring221(int endring221) {
      this.endring221 = endring221;
   }

   public int getEndring222() {
      return endring222;
   }

   public void setEndring222(int endring222) {
      this.endring222 = endring222;
   }

   public int getEndring223() {
      return endring223;
   }

   public void setEndring223(int endring223) {
      this.endring223 = endring223;
   }

   public int getEndring291() {
      return endring291;
   }

   public void setEndring291(int endring291) {
      this.endring291 = endring291;
   }

   public int getEndring292() {
      return endring292;
   }

   public void setEndring292(int endring292) {
      this.endring292 = endring292;
   }

   public int getEndring303() {
      return endring303;
   }

   public void setEndring303(int endring303) {
      this.endring303 = endring303;
   }

   public int getEndring304() {
      return endring304;
   }

   public void setEndring304(int endring304) {
      this.endring304 = endring304;
   }

   public int getEndring306() {
      return endring306;
   }

   public void setEndring306(int endring306) {
      this.endring306 = endring306;
   }

   public int getEndring310() {
      return endring310;
   }

   public void setEndring310(int endring310) {
      this.endring310 = endring310;
   }

   public int getEndring311() {
      return endring311;
   }

   public void setEndring311(int endring311) {
      this.endring311 = endring311;
   }

   public int getEndring312() {
      return endring312;
   }

   public void setEndring312(int endring312) {
      this.endring312 = endring312;
   }

   // ================================================================ Metoder
   public int getEndring102() {
      return endring102;
   }

   public void setEndring102(int endring102) {
      this.endring102 = endring102;
   }

   public int getEndring104() {
      return endring104;
   }

   public void setEndring104(int endring104) {
      this.endring104 = endring104;
   }

   public int getEndring106() {
      return endring106;
   }

   public void setEndring106(int endring106) {
      this.endring106 = endring106;
   }

   public int getEndring111() {
      return endring111;
   }

   public void setEndring111(int endring111) {
      this.endring111 = endring111;
   }

   public int getEndring202() {
      return endring202;
   }

   public void setEndring202(int endring202) {
      this.endring202 = endring202;
   }

   public int getEndring203() {
      return endring203;
   }

   public void setEndring203(int endring203) {
      this.endring203 = endring203;
   }

   public int getEndring211() {
      return endring211;
   }

   public void setEndring211(int endring211) {
      this.endring211 = endring211;
   }

   public int getEndringAgg() {
      int endringAgg =
              this.endring102
              + this.endring104
              + this.endring106
              + this.endring111
              + this.endring202
              + this.endring203
              + this.endring211
                      + this.endring101
                      + this.endring112
                      + this.endring207
                      + this.endring209
                      + this.endring213
                      + this.endring221
                      + this.endring222
                      + this.endring223
                      + this.endring291
                      + this.endring292
                      + this.endring303
                      + this.endring304
                      + this.endring306
                      + this.endring310
                      + this.endring311
                      + this.endring312;

      return (endringAgg > 0 ? 1 : 0);
   }

   public int getAntallSat() {
      return antallSat;
   }

   public void setAntallSat(int antallSat) {
      this.antallSat = antallSat;
   }

   public int getAntallUnder() {
      return antallUnder;
   }

   public void setAntallUnder(int antallUnder) {
      this.antallUnder = antallUnder;
   }

   public int getAntallUnderGruppe() {
      return antallUnderGruppe;
   }

   public void setAntallUnderGruppe(int antallUnderGruppe) {
      this.antallUnderGruppe = antallUnderGruppe;
   }
}
