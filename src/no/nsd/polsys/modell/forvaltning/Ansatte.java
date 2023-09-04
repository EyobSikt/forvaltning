package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.Map;

/**
 * Klassen representerer en ansatt-enhet.
 *
 * @author hvb
 */
public class Ansatte implements Serializable {

   // ============================================================== Variabler
   // løpenummer-id.
   private Integer id;
   private Integer aar;
   private String etatkode;
   private String dep;
   private String etat;
   private String arbeidssted;
   private Integer kommunenummer;
   private String kommune;
   private Integer fylkenummer;
   private String fylke;
   private Integer ansatte;
   private Integer alder;
   private Float aarsverk;
   // mapping: år --> navn.
   private Map<Integer, Ansatte> navn;
   // mapping: kommunenr --> ansatte.
   private Map<Integer, Ansatte> ansatteKommune;
   private Integer fraAar;
   private Integer tilAar;
   private String dok;


   // ================================================================ Metoder
   public String getArbeidssted() {
      return arbeidssted;
   }

   public void setArbeidssted(String arbeidssted) {
      this.arbeidssted = arbeidssted;
   }

   public String getDep() {
      return dep;
   }

   public void setDep(String dep) {
      this.dep = dep;
   }

   public String getEtat() {
      return etat;
   }

   public void setEtat(String etat) {
      this.etat = etat;
   }

   public String getFylke() {
      return fylke;
   }

   public void setFylke(String fylke) {
      this.fylke = fylke;
   }

   public String getKommune() {
      return kommune;
   }

   public void setKommune(String kommune) {
      this.kommune = kommune;
   }

   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public Integer getAlder() {
      return alder;
   }

   public void setAlder(Integer alder) {
      this.alder = alder;
   }

   public Integer getAnsatte() {
      return ansatte;
   }

   public void setAnsatte(Integer ansatte) {
      this.ansatte = ansatte;
   }

   public Integer getFylkenummer() {
      return fylkenummer;
   }

   public void setFylkenummer(Integer fylkenummer) {
      this.fylkenummer = fylkenummer;
   }

   public Float getAarsverk() {
      return aarsverk;
   }

   public void setAarsverk(Float aarsverk) {
      this.aarsverk = aarsverk;
   }

   public Map<Integer, Ansatte> getAnsatteKommune() {
      return ansatteKommune;
   }

   public void setAnsatteKommune(Map<Integer, Ansatte> ansatteKommune) {
      this.ansatteKommune = ansatteKommune;
   }

   public Integer getKommunenummer() {
      return kommunenummer;
   }

   public void setKommunenummer(Integer kommunenr) {
      this.kommunenummer = kommunenr;
   }

   public String getEtatkode() {
      return etatkode;
   }

   public void setEtatkode(String etatkode) {
      this.etatkode = etatkode;
   }

   public Integer getFraAar() {
      return fraAar;
   }

   public void setFraAar(Integer fraAar) {
      this.fraAar = fraAar;
   }

   public Integer getTilAar() {
      return tilAar;
   }

   public void setTilAar(Integer tilAar) {
      this.tilAar = tilAar;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Map<Integer, Ansatte> getNavn() {
      return navn;
   }

   public void setNavn(Map<Integer, Ansatte> navn) {
      this.navn = navn;
   }

   public String getDok() {
      return dok;
   }

   public void setDok(String dok) {
      this.dok = dok;
   }

   public String getNavn(Integer aar) {
      if (this.navn == null) {
         return null;
      }
      Ansatte a = this.navn.get(aar);
      return a != null ? a.getEtat() : null;
   }

   public String getDepkode() {
      String depkode = this.etatkode;
      if (depkode == null) {
         return null;
      }
      int i = depkode.indexOf(":");
      if (i != -1) {
         return depkode.substring(0, i);
      } else {
         return depkode;
      }
   }
}
