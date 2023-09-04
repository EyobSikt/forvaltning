package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer et utvalg.
 *
 * @author hvb
 */
public class Utvalg implements Serializable, Comparable<Utvalg> {

   // ============================================================== Variabler
   /**
    * Nummer til dette utvalget.
    */
   private Integer unr;
   /**
    * Overordnet departement.
    */
   private Enhet odep;
   private String navn;
   private String hjemmel;
   private String mandat;
   private String utvalgstype;
   private String hovedfunksjon;
   private String geografi;
   private String opprettelsesmaate;
   private Integer opprettelsesaar;
   private Integer opphoersaar;
   private Integer tidsfrist;
   private Integer aar;
   private Integer antallMedlemmer;
   private Integer antallVaramedlemmer;
   private Integer antallMoeter;
   private Integer utgifter;
   private String utgiftsdekning;
   private String utvalg2003;
   private String utvalg2004;
   private Boolean utvalgtom2003;
   private Boolean utvalgfom2004;

 // ================================================================ Metoder

   public Boolean getUtvalgtom2003() {
      return utvalgtom2003;
   }

   public void setUtvalgtom2003(Boolean utvalgtom2003) {
      this.utvalgtom2003 = utvalgtom2003;
   }

   public Boolean getUtvalgfom2004() {
      return utvalgfom2004;
   }

   public void setUtvalgfom2004(Boolean utvalgfom2004) {
      this.utvalgfom2004 = utvalgfom2004;
   }

   public String getUtvalg2003() {
      return utvalg2003;
   }

   public void setUtvalg2003(String utvalg2003) {
      this.utvalg2003 = utvalg2003;
   }

   public String getUtvalg2004() {
      return utvalg2004;
   }

   public void setUtvalg2004(String utvalg2004) {
      this.utvalg2004 = utvalg2004;
   }

   public String getGeografi() {
      return geografi;
   }

   public void setGeografi(String geografi) {
      this.geografi = geografi;
   }

   public String getHjemmel() {
      return hjemmel;
   }

   public void setHjemmel(String hjemmel) {
      this.hjemmel = hjemmel;
   }

   public String getHovedfunksjon() {
      return hovedfunksjon;
   }

   public void setHovedfunksjon(String hovedfunksjon) {
      this.hovedfunksjon = hovedfunksjon;
   }

   public String getMandat() {
      return mandat;
   }

   public void setMandat(String mandat) {
      this.mandat = mandat;
   }

   public String getNavn() {
      return navn;
   }

   public void setNavn(String navn) {
      this.navn = navn;
   }

   public Enhet getOdep() {
      return odep;
   }

   public void setOdep(Enhet odep) {
      this.odep = odep;
   }

   public Integer getOpphoersaar() {
      return opphoersaar;
   }

   public void setOpphoersaar(Integer opphoersaar) {
      this.opphoersaar = opphoersaar;
   }

   public Integer getOpprettelsesaar() {
      return opprettelsesaar;
   }

   public void setOpprettelsesaar(Integer opprettelsesaar) {
      this.opprettelsesaar = opprettelsesaar;
   }

   public String getOpprettelsesmaate() {
      return opprettelsesmaate;
   }

   public void setOpprettelsesmaate(String opprettelsesmaate) {
      this.opprettelsesmaate = opprettelsesmaate;
   }

   public Integer getTidsfrist() {
      return tidsfrist;
   }

   public void setTidsfrist(Integer tidsfrist) {
      this.tidsfrist = tidsfrist;
   }

   public Integer getUnr() {
      return unr;
   }

   public void setUnr(Integer unr) {
      this.unr = unr;
   }

   public String getUtvalgstype() {
      return utvalgstype;
   }

   public void setUtvalgstype(String utvalgstype) {
      this.utvalgstype = utvalgstype;
   }

   public Integer getAntallMedlemmer() {
      return antallMedlemmer;
   }

   public void setAntallMedlemmer(Integer antallMedlemmer) {
      this.antallMedlemmer = antallMedlemmer;
   }

   public Integer getAntallMoeter() {
      return antallMoeter;
   }

   public void setAntallMoeter(Integer antallMoeter) {
      this.antallMoeter = antallMoeter;
   }

   public Integer getAntallVaramedlemmer() {
      return antallVaramedlemmer;
   }

   public void setAntallVaramedlemmer(Integer antallVaramedlemmer) {
      this.antallVaramedlemmer = antallVaramedlemmer;
   }

   public Integer getUtgifter() {
      return utgifter;
   }

   public void setUtgifter(Integer utgifter) {
      this.utgifter = utgifter;
   }

   public String getUtgiftsdekning() {
      return utgiftsdekning;
   }

   public void setUtgiftsdekning(String utgiftsdekning) {
      this.utgiftsdekning = utgiftsdekning;
   }

   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
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
      Utvalg other = (Utvalg) obj;
      if (this.unr == null || !this.unr.equals(other.unr)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return (this.unr != null ? this.unr.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.navn != null ? this.navn : "");
   }

   @Override
   public int compareTo(Utvalg o) {
      if (o == null) {
         return -1;
      }
      if (o.navn == null) {
         return -1;
      }
      if (this.navn == null) {
         return 1;
      }
      int i = this.navn.compareToIgnoreCase(o.navn);
      if (i != 0) {
         return i;
      }
      if (o.unr == null) {
         return -1;
      }
      if (this.unr == null) {
         return 1;
      }
      return this.unr.compareTo(o.unr);
   }
}
