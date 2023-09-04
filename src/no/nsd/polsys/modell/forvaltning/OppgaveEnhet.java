package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer oppgaveporteføljen til enten en gitt enhet eller på
 * gitt et tidspunkt.
 *
 * @author hvb
 */
public final class OppgaveEnhet implements Serializable {

   // ============================================================== Variabler
   private Integer aar;
   private Enhet enhet;
   private Integer hovedoppgave;
   private Integer bioppgave1;
   private Integer bioppgave2;


   // ================================================================ Metoder
   public Integer getBioppgave1() {
      return bioppgave1;
   }

   public void setBioppgave1(Integer bioppgave1) {
      this.bioppgave1 = bioppgave1;
   }

   public Integer getBioppgave2() {
      return bioppgave2;
   }

   public void setBioppgave2(Integer bioppgave2) {
      this.bioppgave2 = bioppgave2;
   }

   public Enhet getEnhet() {
      return enhet;
   }

   public void setEnhet(Enhet enhet) {
      this.enhet = enhet;
   }

   public Integer getHovedoppgave() {
      return hovedoppgave;
   }

   public void setHovedoppgave(Integer hovedoppgave) {
      this.hovedoppgave = hovedoppgave;
   }

   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }
}
