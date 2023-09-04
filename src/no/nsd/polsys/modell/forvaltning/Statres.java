package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en rad fra statres-data.
 *
 * @author hvb
 */
public final class Statres implements Serializable {

   // ============================================================== Variabler
   private Integer aar;
   private Integer statresId;
   private String statresNavn;
   private Integer egenproduksjon;
   private Float lonnskostEgenprod;
   private Float varerTjenester;
   private Integer investeringer;
   private Integer overforinger;
   private Integer totaleUtgifter;
   private Integer premieSp;
   private Integer avtalteAarsverk;


   // ================================================================ Metoder
   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public Integer getAvtalteAarsverk() {
      return avtalteAarsverk;
   }

   public void setAvtalteAarsverk(Integer avtalteAarsverk) {
      this.avtalteAarsverk = avtalteAarsverk;
   }

   public Integer getEgenproduksjon() {
      return egenproduksjon;
   }

   public void setEgenproduksjon(Integer egenproduksjon) {
      this.egenproduksjon = egenproduksjon;
   }

   public Integer getInvesteringer() {
      return investeringer;
   }

   public void setInvesteringer(Integer investeringer) {
      this.investeringer = investeringer;
   }

   public Float getLonnskostEgenprod() {
      return lonnskostEgenprod;
   }

   public void setLonnskostEgenprod(Float lonnskostEgenprod) {
      this.lonnskostEgenprod = lonnskostEgenprod;
   }

   public Integer getOverforinger() {
      return overforinger;
   }

   public void setOverforinger(Integer overforinger) {
      this.overforinger = overforinger;
   }

   public Integer getPremieSp() {
      return premieSp;
   }

   public void setPremieSp(Integer premieSp) {
      this.premieSp = premieSp;
   }

   public Integer getStatresId() {
      return statresId;
   }

   public void setStatresId(Integer statresId) {
      this.statresId = statresId;
   }

   public String getStatresNavn() {
      return statresNavn;
   }

   public void setStatresNavn(String statresNavn) {
      this.statresNavn = statresNavn;
   }

   public Integer getTotaleUtgifter() {
      return totaleUtgifter;
   }

   public void setTotaleUtgifter(Integer totaleUtgifter) {
      this.totaleUtgifter = totaleUtgifter;
   }

   public Float getVarerTjenester() {
      return varerTjenester;
   }

   public void setVarerTjenester(Float varerTjenester) {
      this.varerTjenester = varerTjenester;
   }
}
