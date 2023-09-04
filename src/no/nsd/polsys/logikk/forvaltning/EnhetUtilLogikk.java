package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EnhetUtilLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   // mapping idnum --> enhet.
   private Map<Integer, Enhet> enheter;

   private boolean engelsk;

   
   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void setEnheter(Map<Integer, Enhet> enheter) {
      this.enheter = enheter;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }
   
   
   public void beregnEnheter(Date date) throws SQLException {
      EnhetLogikk logikk = new EnhetLogikk();
      logikk.setConn(this.conn);
      if (this.engelsk) {
         logikk.brukEngelsk();
      }
      logikk.setEnheter(date);
      this.enheter = logikk.enheter;
   }

   public boolean erEvalEnhet(Enhet e) {
      if (this.erDepartement(e)) {
         return true;
      }
      if (this.erInternDepEnhet(e)) {
         return false;
      }
      if (this.erFeilEvalIdnum(e)) {
         return false;
      }
      if (this.erFeilEvalTilknytning(e)) {
         return false;
      }
      if (this.erEtat(e)) {
         return false;
      }
      if (this.erFeilEvalHierarkiskNivaa(e)) {
         return false;
      }
      return true;
   }

   public boolean erDepartement(Enhet e) {
      if (e == null || e.getTilknytningsform() == null || e.getNivaa() == null) {
         return false;
      }
      if (e.getTilknytningsform().equals(10) && e.getNivaa().equals(0)) {
         return true;
      }
      return false;
   }

   public boolean erInternDepEnhet(Enhet e) {
      if (e == null || e.getTilknytningsform() == null || e.getNivaa() == null) {
         return false;
      }
      if (e.getTilknytningsform().equals(10) && !e.getNivaa().equals(0)) {
         return true;
      }
      return false;
   }

   private boolean erFeilEvalIdnum(Enhet e) {
      if (e.getIdnum().equals(3600)) {
         return true; // Børser er feil.
      }
      if (e.getIdnum().equals(-600)) {
         return false; // Stortinget er ikke feil.
      }
      if (e.getIdnum() < 0) {
         return true; // Andre minus-enheter er feil.
      }
      return false; // Ellers er alle idnum ok.
   }

   private boolean erFeilEvalTilknytning(Enhet e) {
      if (e.getTilknytningsform() == null) {
         return false; // ok å ikke ha tilknytning.
      }
      if (e.getTilknytningsform().equals(42)) {
         return false; // 42 er ikke feil.
      }
      int til = e.getTilknytningsform() / 10;
      if (til == 4 || til == 5) {
         return true; // ellers er selskap og stiftelser feil.
      }
      return false; // ellers er alle ok.
   }

   public boolean erNasjonal(Enhet e) {
      return (e != null && e.getGrunnenhet() != null && e.getGrunnenhet().equals(0));
   }

   public boolean erEtat(Enhet e) {
      return (e != null && e.getGrunnenhet() != null && e.getGrunnenhet().equals(10));
   }

   public boolean erHovedkontor(Enhet e) {
      return (e != null && e.getGrunnenhet() != null && e.getGrunnenhet().equals(11));
   }

   public boolean erGruppe(Enhet e) {
      return (e != null && e.getGrunnenhet() != null && e.getGrunnenhet().equals(20));
   }

   /**
    * Metoden sjekker kun om grunnenhet er lik 21, altså ikke om enheten faktisk
    * ligger under en gruppe.
    *
    * @param e
    * @return true hviss e ikke er null og grunnenhet er lik 21.
    */
   public boolean tilhorerGruppe(Enhet e) {
      return (e != null && e.getGrunnenhet() != null && e.getGrunnenhet().equals(21));
   }

   public boolean erSubstansiell(Enhet e) {
      if (e == null) {
         return false;
      }
      if (e.getGrunnenhet() == null) {
         return true; // Enheten er her definert som substansiell hvis den mangler grunnenhet.
      }
      if (this.erNasjonal(e) || this.erHovedkontor(e) || this.tilhorerGruppe(e)) {
         return true;
      }
      return false;
   }

   public boolean erTomEnhet(Enhet e) {
      return this.erEtat(e) || this.erGruppe(e);
   }

   private boolean erFeilEvalHierarkiskNivaa(Enhet e) {
      if (e.getGrunnenhet() == null) {
         return false;
      }
      // Vi skal her kun se på grunnenheter 20 og 21.
      if (!e.getGrunnenhet().equals(20) && !e.getGrunnenhet().equals(21)) {
         return false;
      }
      if (this.getHierarkiskNivaaUnderDep(e) > 2) {
         return true; // enheten ligger for langt ned i hierarkiet, og har dermed feil nivå.
      }
      return false; // ellers ok nivå.
   }

   /**
    * Returnerer antall nivåer gitt enhet er under selve departementet. Denne
    * metoden bruker overordnetIdnum-variabel. Alle enheter, inkl. "tomme"
    * teller med.
    *
    * @param e gitt enhet.
    * @return 0 hvis gitt enhet er selv et departement, 1 hvis den ligger rett
    * under, 2 hvis det er en enhet i mellom, osv. Metoden returnerer -1 hvis
    * den ligger over departement, eller er null.
    */
   public int getHierarkiskNivaaUnderDep(Enhet e) {
      int nivaa = 0;
      while (e != null) {
         if (this.erDepartement(e)) {
            return nivaa;
         }
         nivaa++;
         e = this.enheter.get(e.getOverordnetIdnum());
      }
      return -1;
   }

   public void brukEtatnavnHvisHovedkontor(Enhet e) {
      // For hovedkontor, skal vi bruke navnet til etaten.
      if (this.erHovedkontor(e)) {
         Enhet over = this.enheter.get(e.getOverordnetIdnum());
         if (this.erEtat(over)) {
            e.setLangtNavn(over.getLangtNavn());
            e.setKortNavn(over.getKortNavn());
         }
      }
   }

   public void setOverordnetDepartement(Enhet e) {
      if (e == null) {
         return;
      }
      if (this.erDepartement(e)) {
         return;
      }
      Enhet overordnet = this.enheter.get(e.getOverordnetIdnum());
      while (overordnet != null && !this.erDepartement(overordnet)) {
         overordnet = this.enheter.get(overordnet.getOverordnetIdnum());
      }
      e.setOverordnetDepartement(overordnet);
   }


   
   /**
    * Returnerer overordnet etat til gitt enhet.
    * @param e gitt enhet.
    * @return overordnet etat, eller null hvis enheten er selv en etat, eller 
    * enheten ikke ligger hierarkisk under en etat.
    */
   public Enhet getOverordnetEtat(Enhet e) {
      if (e == null) {
         return null;
      }
      e = this.enheter.get(e.getOverordnetIdnum());
      while (e != null) {
         if (this.erEtat(e)) {
            return e;
         }
         e = this.enheter.get(e.getOverordnetIdnum());
      }
      return null;
   }
   
   
   
   /**
    * Sjekker om gitt enhet ligger under et hovedkontor i etat.
    *
    * @param e gitt enhet.
    * @return true hviss enheten ligger hierarkisk under et hovedkontor.
    */
   public boolean erLokalEtatEnhet(Enhet e) {
      if (e == null) {
         return false;
      }
      e = this.enheter.get(e.getOverordnetIdnum());
      while (e != null) {
         if (this.erHovedkontor(e)) {
            return true;
         }
         e = this.enheter.get(e.getOverordnetIdnum());
      }
      return false;
   }

   public void arvEndringer(Enhet enhet, List<Enhet> endringerDag, List<Enhet> endringerEnhet) {
      if (!this.erHovedkontor(enhet) && !this.erGruppe(enhet) && !this.tilhorerGruppe(enhet)) {
         return; // vi er ferdig hvis enheten ikke er i etat/gruppe - eller er gruppe.
      }
      Enhet over = this.enheter.get(enhet.getOverordnetIdnum());
      if (this.erSubstansiell(over)) {
         return; // vi er ferdig hvis overordnet er substansiell. Da trenger vi ikke arve.
      }

      while (over != null) {
         Enhet overover = this.enheter.get(over.getOverordnetIdnum());
         if (overover == null) {
            break;
         }
         if (this.erTomEnhet(overover)) {
            over = overover; // hvis overover er tom må vi gå videre opp i hierarkiet.
            continue;
         }
         this.leggTilArvEndringer(enhet.getIdnum(), over, overover, endringerDag, endringerEnhet);
         break;
      }
   }

   private void leggTilArvEndringer(Integer idnum, Enhet over, Enhet overover, List<Enhet> endringerDag, List<Enhet> endringerEnhet) {
      for (Enhet e : endringerDag) {
         if (e.getIdnum().equals(over.getIdnum()) && e.getOverordnetIdnum() != null && e.getOverordnetIdnum().equals(overover.getIdnum())) {
            if (e.getEndringskode().equals(221) || e.getEndringskode().equals(223) || e.getEndringskode().equals(291)) {

               Enhet arvEnhet = new Enhet();
               arvEnhet.setIdnum(idnum);
               arvEnhet.setEndringsid(e.getEndringsid());
               arvEnhet.setTidspunkt(e.getTidspunkt());
               arvEnhet.setBekreftetDato(e.getBekreftetDato());
               arvEnhet.setEndringskode(e.getEndringskode());
               arvEnhet.setOverordnetIdnum(e.getOverordnetIdnum());

               arvEnhet.setOverordnetReell(new Enhet(overover));

               endringerEnhet.add(arvEnhet);
            }
         }
      }
   }
}
