package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.factories.forvaltning.RelasjonCacheFactory;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.EnhetVariabler;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class TabellLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   private EnhetLogikk enhetLogikk = new EnhetLogikk();
   private EnhetUtilLogikk enhetUtilLogikk = new EnhetUtilLogikk();
   private Date date;
   private Set<Integer> tilknytningsformer;
   private Set<Integer> grunnenheter;
   private Set<Integer> nivaaer;
   private boolean grupperUtenfor;
   private boolean grupperNasjonale;
   private boolean grupperRegionale;
   private List<Enhet> liste;
   // endringsid --> liste av idnum.
   private Map<Integer, List<Integer>> relasjoner;

   // mapping: idnum --> kommunenr.
   private Map<Integer, Integer> sisteKommuner;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
      this.enhetLogikk.setConn(conn);
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public void setGrupperNasjonale(boolean grupperNasjonale) {
      this.grupperNasjonale = grupperNasjonale;
   }

   public void setGrupperRegionale(boolean grupperRegionale) {
      this.grupperRegionale = grupperRegionale;
   }

   public void setGrupperUtenfor(boolean grupperUtenfor) {
      this.grupperUtenfor = grupperUtenfor;
   }

   public void setGrunnenheter(Set<Integer> grunnenheter) {
      this.grunnenheter = grunnenheter;
   }

   public void setNivaaer(Set<Integer> nivaaer) {
      this.nivaaer = nivaaer;
   }

   public void setTilknytningsformer(Set<Integer> tilknytningsformer) {
      this.tilknytningsformer = tilknytningsformer;
   }

   /**
    * Returnerer gitt enheter.
    *
    * @param dato
    * @return
    * @throws SQLException
    */
   public List<Enhet> getEnheter() throws SQLException {
      this.liste = new ArrayList<Enhet>(4000);
      // mapping idnum --> enhet.
      this.enhetLogikk.setEnheter(this.date);
      this.enhetUtilLogikk.setEnheter(this.enhetLogikk.enheter);

      sisteKommuner = this.enhetLogikk.getKommuneTilEnheter();

      this.finnRelevanteEnheter();
      this.leggTilOverordnetDepartement();
      this.leggTilEndringsvariabler();
      this.finnEtableringsaarForloper();
      this.leggTilHierarki();

      this.leggTilSisteKommuner();

      return this.liste;
   }

   private void finnRelevanteEnheter() {
      for (Enhet e : this.enhetLogikk.enheter.values()) {
         if (e.isNedlagt() && e.getTidspunktNedlagt().before(this.date)) {
            continue;
         }
         if (!this.erRiktigTilknytningsform(e)) {
            continue;
         }
         if (!this.erRiktigGrunnenhet(e)) {
            continue;
         }
         if (!this.erRiktigNivaa(e)) {
            continue;
         }
         this.liste.add(e);
         e.setVariabler(new EnhetVariabler());
      }
   }

   private void leggTilOverordnetDepartement() {
      for (Enhet e : this.liste) {
         this.enhetUtilLogikk.setOverordnetDepartement(e);
      }
   }

   private void leggTilEndringsvariabler() throws SQLException {
      // alle endringer i databasen.
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);

      // for-løkken finner aktuelle enheter.
      for (EndringCache c : endringer) {
         int k = c.getEndringskode();
         if (k != 102 && k != 104 && k != 106 && k != 111 && k != 202 && k != 203 && k != 211 && k != 101 && k != 112 && k != 207 && k != 209 && k != 213
                 && k != 221 && k != 222 && k != 223 && k != 291 && k != 292 && k != 303 && k != 304 && k != 306 && k != 310 && k != 311 && k != 312) {
            continue;
         }
         Enhet e = this.enhetLogikk.enheter.get(c.getIdnum());
         if (e == null) {
            continue;
         }
         EnhetVariabler ev = e.getVariabler();
         if (ev == null) {
            continue; // enheten er ikke i listen over relevante enheter.
         }
         if (k == 102) {
            ev.setEndring102(1);
         }
         if (k == 104) {
            ev.setEndring104(1);
         }
         if (k == 106) {
            ev.setEndring106(1);
         }
         if (k == 111) {
            ev.setEndring111(1);
         }
         if (k == 202) {
            ev.setEndring202(1);
         }
         if (k == 203) {
            ev.setEndring203(1);
         }
         if (k == 211) {
            ev.setEndring211(1);
         }
         if (k == 101) {
            ev.setEndring101(1);
         }
         if (k == 112) {
            ev.setEndring112(1);
         }
         if (k == 207) {
            ev.setEndring207(1);
         }
         if (k == 209) {
            ev.setEndring209(1);
         }
         if (k == 213) {
            ev.setEndring213(1);
         }
         if (k == 221) {
            ev.setEndring221(1);
         }
         if (k == 222) {
            ev.setEndring222(1);
         }
         if (k == 223) {
            ev.setEndring223(1);
         }
         if (k == 291) {
            ev.setEndring291(1);
         }
         if (k == 292) {
            ev.setEndring292(1);
         }
         if (k == 303) {
            ev.setEndring303(1);
         }
         if (k == 304) {
            ev.setEndring304(1);
         }
         if (k == 306) {
            ev.setEndring306(1);
         }
         if (k == 310) {
            ev.setEndring310(1);
         }
         if (k == 311) {
            ev.setEndring311(1);
         }
         if (k == 312) {
            ev.setEndring312(1);
         }
      }
   }

   private void finnEtableringsaarForloper() throws SQLException {
      // endringsid --> liste av idnum.
      this.relasjoner = RelasjonCacheFactory.getRelasjoner(this.conn);

      for (Enhet e : this.liste) {
         this.finnEtableringsaarTilEnhet(e);
      }
   }

   private void finnEtableringsaarTilEnhet(Enhet e) {
      // setter etableringstidspunkt lik opprettelsestidspunkt til enheten (uten forløpere).
      e.setTidspunktOpprettetForloper(e.getTidspunkt());
      List<Integer> l = this.relasjoner.get(e.getEndringsid());
      if (l == null) {
         return;
      }
      Set<Integer> nyIdnums = new HashSet<Integer>();
      nyIdnums.addAll(l);

      // for å forhindre evig løkke ved syklkiske relasjoner.
      Set<Integer> unikeIdnums = new HashSet<Integer>();
      unikeIdnums.addAll(l);

      while (!nyIdnums.isEmpty()) {
         Set<Integer> idnums = nyIdnums;
         nyIdnums = new HashSet<Integer>();
         for (Integer idnum : idnums) {
            Enhet r = this.enhetLogikk.enheter.get(idnum);
            if (r == null) {
               continue;
            }
            if (r.getTidspunkt().before(e.getTidspunktOpprettetForloper())) {
               e.setTidspunktOpprettetForloper(r.getTidspunkt());
            }
            List<Integer> nyliste = this.relasjoner.get(r.getEndringsid());
            if (nyliste != null) {
               for (Integer i : nyliste) {
                  if (!unikeIdnums.contains(i)) {
                     unikeIdnums.add(i);
                     nyIdnums.add(i);
                  }
               }
            }
         }
      }
   }

   private void leggTilHierarki() throws SQLException {
      EnhetHierarkiLogikk hierarkiLogikk = new EnhetHierarkiLogikk();
      hierarkiLogikk.setConn(this.conn);
      Enhet staten = hierarkiLogikk.getHierarki(-1, this.date);

      if (staten != null) {
         this.finnUnderordnede(staten);
      }

   }

   private int[] finnUnderordnede(Enhet enhet) {
      // index: 0=antallUnder, 1=antallUnderGruppe, 2=antallSat
      int[] i = new int[3];

      this.leggTilTallgrupper(enhet, i);
      this.leggTilUnderordnede(enhet, i);

      Enhet eks = this.enhetLogikk.enheter.get(enhet.getIdnum());
      if (eks != null && eks.getVariabler() != null) {
         eks.getVariabler().setAntallUnder(i[0]);
         eks.getVariabler().setAntallUnderGruppe(i[1]);
         eks.getVariabler().setAntallSat(i[2]);
      }
      return i;
   }

   private void leggTilTallgrupper(Enhet enhet, int[] i) {
      Set<Tallgruppe> tallgrupper = enhet.getTallgrupper();
      if (tallgrupper == null) {
         return;
      }
      for (Tallgruppe t : tallgrupper) {
         if (t.isTallgruppe() && t.getAntallEnheter() != null) {
            i[0] += t.getAntallEnheter();
            i[1] += t.getAntallEnheter();
         }
         if (!t.isTallgruppe()) {
            i[2]++;
         }
      }
   }

   private void leggTilUnderordnede(Enhet enhet, int[] i) {
      Set<Enhet> under = enhet.getUnderordnet();
      if (under == null) {
         return;
      }
      for (Enhet e : under) {
         if (this.enhetUtilLogikk.erSubstansiell(e)) {
            i[0]++;
         }
         i[1]++;
         int[] u = this.finnUnderordnede(e);
         i[0] += u[0];
         i[1] += u[1];
         i[2] += u[2];
      }
   }

   private void leggTilSisteKommuner() {
      for (Enhet e : this.liste) {
         Integer k = this.sisteKommuner.get(e.getIdnum());
         if (k != null) {
            e.setKommunenummer(k);
         }
      }
   }
   
   
   
   // ========================================================================
   // ========================= Hjelpemetoder ================================
   // ========================================================================
   private boolean erRiktigTilknytningsform(Enhet e) {
      return this.tilknytningsformer.contains(e.getTilknytningsform());
   }

   private boolean erRiktigGrunnenhet(Enhet e) {
      // Departementsenheter skal ikke ta hensyn til grunnenhet.
      if (e.getTilknytningsform() != null && e.getTilknytningsform().equals(10)) {
         return true;
      }

      boolean b = this.grunnenheter.contains(e.getGrunnenhet());
      if (b) {
         return true;
      }
      return this.erRiktigGruppe(e);
   }

   private boolean erRiktigGruppe(Enhet e) {
      if (e.getGrunnenhet() == null) {
         return false;
      }
      if (!e.getGrunnenhet().equals(20)) {
         return false;
      }
      int i = this.enhetUtilLogikk.getHierarkiskNivaaUnderDep(e);
      if (this.grupperUtenfor && i == -1) {
         return true;
      }
      if (this.grupperNasjonale && i == 1) {
         return true;
      }
      if (this.grupperRegionale && i > 1) {
         return true;
      }

      return false;
   }

   private boolean erRiktigNivaa(Enhet e) {
      // Nivå er kun relevant for departementsenheter.
      if (e.getTilknytningsform() != null && !e.getTilknytningsform().equals(10)) {
         return true;
      }

      return this.nivaaer.contains(e.getNivaa());
   }
}
