package no.nsd.polsys.modell.forvaltning;

import no.nsd.polsys.modell.Kommune;

import java.io.Serializable;

/**
 * Klassen representerer et utvalg.
 *
 * @author hvb
 */
public class Utvalgmedlemskap implements Serializable {

   // ============================================================== Variabler
   private Integer aar;
   private Utvalg utvalg;
   private Utvalgperson person;
   private Integer fylkenummer;
   private Kommune kommune;
   private String verv;
   private String oppnevningsmaate;
   private String stilling;
   private String fradato;
   private String tildato;

   // ================================================================ Metoder

   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public Utvalgperson getPerson() {
      return person;
   }

   public void setPerson(Utvalgperson person) {
      this.person = person;
   }

   public Utvalg getUtvalg() {
      return utvalg;
   }

   public void setUtvalg(Utvalg utvalg) {
      this.utvalg = utvalg;
   }

   public Integer getFylkenummer() {
      return fylkenummer;
   }

   public void setFylkenummer(Integer fylkenummer) {
      this.fylkenummer = fylkenummer;
   }

   public Kommune getKommune() {
      return kommune;
   }

   public void setKommune(Kommune kommune) {
      this.kommune = kommune;
   }

   public String getOppnevningsmaate() {
      return oppnevningsmaate;
   }

   public void setOppnevningsmaate(String oppnevningsmaate) {
      this.oppnevningsmaate = oppnevningsmaate;
   }

   public String getStilling() {
      return stilling;
   }

   public void setStilling(String stilling) {
      this.stilling = stilling;
   }

   public String getVerv() {
      return verv;
   }

   public void setVerv(String verv) {
      this.verv = verv;
   }

   public String getFradato() {
      return fradato;
   }

   public void setFradato(String fradato) {
      this.fradato = fradato;
   }

   public String getTildato() {
      return tildato;
   }

   public void setTildato(String tildato) {
      this.tildato = tildato;
   }

   // Overskrevne metoder
   @Override
   public String toString() {
      return "medlemskap";
   }
}
