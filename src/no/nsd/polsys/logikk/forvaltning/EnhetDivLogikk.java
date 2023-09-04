package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import no.nsd.polsys.comparators.forvaltning.DepartementsenhetComparator;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.factories.forvaltning.TallgruppeCacheFactory;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.OppgaveEnhet;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class EnhetDivLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;
   private EnhetLogikk enhetLogikk = new EnhetLogikk();
   /**
    * mapping: cofog-kode --> liste med enheter.
    */
   SortedMap<String, List<Enhet>> cofog;
   /**
    * mapping: fylkenummer --> liste med enheter.
    */
   SortedMap<Integer, List<Enhet>> fylker;
   /**
    * mapping: fylkenummer --> liste med satellitter.
    */
   SortedMap<Integer, List<Tallgruppe>> satellitter;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
      this.enhetLogikk.setConn(conn);
   }

   public void brukEngelsk() {
      this.engelsk = true;
      this.enhetLogikk.brukEngelsk();
   }

   public SortedMap<String, List<Enhet>> getCofog() {
      return cofog;
   }

   public SortedMap<Integer, List<Enhet>> getFylker() {
      return fylker;
   }

   public SortedMap<Integer, List<Tallgruppe>> getSatellitter() {
      return satellitter;
   }

   public Collection<Enhet> getEnheter() throws SQLException {
      EnhetLogikk l = new EnhetLogikk();
      l.setConn(this.conn);
      l.setEnheter(null);
      Collection<Enhet> coll = l.enheter.values();
      return coll;
   }

   public Map<Integer, Enhet> getMappingIdnumToUnit() throws SQLException {
      this.enhetLogikk.setEnheter(null);
      return this.enhetLogikk.enheter;
   }   
   
   /**
    * Beregner alle enheter kategorisert på cofog.
    *
    * @throws SQLException
    */
   public void beregnEnheterPerCofog(Date dato) throws SQLException {
      // mapping: cofog-kode --> liste med enheter.
      this.cofog = new TreeMap<String, List<Enhet>>();
      // mapping idnum --> enhet.
      this.enhetLogikk.setEnheter(dato);

      Collection<Enhet> coll = this.enhetLogikk.enheter.values();
      Iterator<Enhet> iter = coll.iterator();
      while (iter.hasNext()) {
         Enhet e = iter.next();

         if (e.getCofog() == null) {
            continue;
         }
         if (e.isNedlagt() && e.getTidspunktNedlagt().before(dato)) {
            continue;
         }
         if (e.getGrunnenhet() != null
                 && !(e.getGrunnenhet().equals(0) || e.getGrunnenhet().equals(11) || e.getGrunnenhet().equals(20))) {
            continue;
         }

         List<Enhet> l = this.cofog.get(e.getCofog());
         if (l == null) {
            l = new ArrayList<Enhet>();
            this.cofog.put(e.getCofog(), l);
         }
         l.add(e);
      }
   }

   public SortedMap<Integer, List<OppgaveEnhet>> getOppgaveEnheter(Integer aar, Integer vis) throws SQLException {
      // mapping: oppgave-kode --> liste med enheter.
      SortedMap<Integer, List<OppgaveEnhet>> oppgaver = new TreeMap<Integer, List<OppgaveEnhet>>();

      Dato dato = new Dato(aar, 1, 1);

      // mapping idnum --> enhet.
      this.enhetLogikk.setEnheter(dato.getDate());

      OppgaveLogikk ol = new OppgaveLogikk();
      ol.setConn(this.conn);

      List<OppgaveEnhet> liste = ol.getOppgaverTilEnheter(aar);

      for (OppgaveEnhet e : liste) {
         e.setEnhet(this.enhetLogikk.enheter.get(e.getEnhet().getIdnum()));

         if (vis == null || vis.equals(1)) {
            this.leggTilOppgave(e.getHovedoppgave(), e, oppgaver);
            this.leggTilOppgave(e.getBioppgave1(), e, oppgaver);
            this.leggTilOppgave(e.getBioppgave2(), e, oppgaver);
         }

         if (vis != null && vis.equals(2)) {
            this.leggTilOppgave(e.getHovedoppgave(), e, oppgaver);
         }

         if (vis != null && vis.equals(3)) {
            this.leggTilOppgave(e.getBioppgave1(), e, oppgaver);
            this.leggTilOppgave(e.getBioppgave2(), e, oppgaver);
         }

      }

      return oppgaver;

   }

   private void leggTilOppgave(Integer oppgavekode, OppgaveEnhet oe, SortedMap<Integer, List<OppgaveEnhet>> oppgaver) {
      if (oppgavekode == null) {
         return;
      }

      List<OppgaveEnhet> l = oppgaver.get(oppgavekode);
      if (l == null) {
         l = new ArrayList<OppgaveEnhet>();
         oppgaver.put(oppgavekode, l);
      }

      l.add(oe);
   }

   /**
    * Beregner alle enheter på fylke.
    *
    * @param dato
    * @param sektor 1=forvaltningsorgan (tilknytning 35 eller mindre),
    * 2=foretak,selskap,stiftelser (tilknytning større enn 35), 3=alle enheter.
    * @throws SQLException
    */
   public void beregnerEnheterPerFylke(Date dato, String sektor) throws SQLException {
      // mapping: fylkenummer --> liste med enheter.
      this.fylker = new TreeMap<Integer, List<Enhet>>();
      // mapping: fylkenummer --> liste med satellitter.
      this.satellitter = new TreeMap<Integer, List<Tallgruppe>>();
      // mapping: idnum --> kommunenr
      Map<Integer, Integer> nyKommuneTilEnhet = this.enhetLogikk.getKommuneTilEnheter();
      // alle kommuner i databasen.
      List<Kommune> alleKommuner = KommuneCacheFactory.getKommuner(this.conn);
      // Mapping: idnum --> liste av tallgrupper/avdeling for gitt idnum.
      Map<Integer, List<Tallgruppe>> tallgruppe = TallgruppeCacheFactory.getTallgruppe(this.conn);

      // mapping idnum --> enhet.
      this.enhetLogikk.setEnheter(dato);

      Collection<Enhet> coll = this.enhetLogikk.enheter.values();
      Iterator<Enhet> iter = coll.iterator();
      while (iter.hasNext()) {
         Enhet e = iter.next();

         if (e.isNedlagt() && e.getTidspunktNedlagt().before(dato)) {
            continue;
         }

         if (e.getTilknytningsform() == null) {
            continue;
         }
         // Ikke ta med interne departementsenheter.
         if (e.getTilknytningsform().equals(10) && (e.getNivaa() == null || !e.getNivaa().equals(0))) {
            continue;
         }
         if (sektor != null && sektor.equals("1")) {
            if (e.getTilknytningsform() > 35) {
               continue;
            }
         }
         if (sektor != null && sektor.equals("2")) {
            if (e.getTilknytningsform() <= 35) {
               continue;
            }
         }

         // Ikke ta med "tomme" enheter.
         if (!e.getTilknytningsform().equals(10)
                 && (e.getGrunnenhet() == null
                 || e.getGrunnenhet().equals(10)
                 || e.getGrunnenhet().equals(20))) {
            continue;
         }

         // Ikke ta med lokale enheter.
         if (e.getGrunnenhet() != null && e.getGrunnenhet().equals(21)) {
            this.enhetLogikk.setOverordnetReell(e);
            Enhet over = e.getOverordnetReell();
            if (over == null || over.getTilknytningsform() == null
                    || !over.getTilknytningsform().equals(10)) {
               continue;
            }
         }

         // Sjekker om enheten har satellitter
         List<Tallgruppe> tg = tallgruppe.get(e.getIdnum());
         if (tg != null) {
            for (Tallgruppe t : tg) {
               if (t.isTallgruppe()) {
                  continue;
               }
               if (t.getFraTidspunkt() != null && dato.before(t.getFraTidspunkt())) {
                  continue;
               }
               if (t.getTilTidspunkt() != null && dato.after(t.getTilTidspunkt())) {
                  continue;
               }
               if (t.getKommune() == null || t.getKommune().getKode() == null) {
                  continue;
               }
               Kommune komplett = this.enhetLogikk.getKommune(alleKommuner, t.getKommune().getKode(), dato);
               if (komplett != null) {
                  t.setKommune(komplett);
               }
               int fylkenr = t.getKommune().getKode() / 100;
               t.setEnhet(this.enhetLogikk.enheter.get(t.getIdnum()));
               List<Tallgruppe> l = this.satellitter.get(fylkenr);
               if (l == null) {
                  l = new ArrayList<Tallgruppe>();
                  this.satellitter.put(fylkenr, l);
               }
               l.add(t);
            }
         }

         if (e.getKommunenummer() == null) {
            continue;
         }

         Kommune komplett = this.enhetLogikk.getKommune(alleKommuner, e.getKommunenummer(), dato);
         if (komplett == null) {
            Integer knr = nyKommuneTilEnhet.get(e.getIdnum());
            if (knr != null) {
               e.setKommunenummer(knr);
               komplett = this.enhetLogikk.getKommune(alleKommuner, knr, dato);
            }
            if (komplett == null) {
               komplett = new Kommune();
               komplett.setKode(e.getKommunenummer());
               e.setKommune(komplett);
            }
         }
         e.setKommune(komplett);

         int fylkenr = e.getKommunenummer() / 100;
         this.enhetLogikk.setOverordnetDepartement(e);

         List<Enhet> l = this.fylker.get(fylkenr);
         if (l == null) {
            l = new ArrayList<Enhet>();
            this.fylker.put(fylkenr, l);
         }
         l.add(e);
      }

   }

   /**
    * Returnerer enheter til evalueringsportalen - gitt år.
    *
    * @param fraDato fra 1.1.år
    * @param tilDato til 31.12.år
    * @return
    * @throws SQLException
    */
   public List<Enhet> getEvalEnheterGittAar(Date fraDato, Date tilDato) throws SQLException {
      Date dato = fraDato;
      List<Enhet> liste = new ArrayList<Enhet>(400);
      Set<Enhet> set = new HashSet<Enhet>(400);

      while (!dato.after(tilDato)) {
         this.addEvalEnheter(set, dato);
         dato = this.getDatePlusOneDay(dato);
      }
      for (Enhet e : set) {
         liste.add(e);
      }
      return liste;
   }

   private void addEvalEnheter(Set<Enhet> set, Date dato) throws SQLException {
      this.enhetLogikk.setEnheter(dato);
      EnhetUtilLogikk util = new EnhetUtilLogikk();
      util.setEnheter(this.enhetLogikk.enheter);

      for (Enhet e : this.enhetLogikk.enheter.values()) {
         if (set.contains(e)) {
            continue; // enheten er allerede lagt til.
         }
         if (e.isNedlagt() && e.getTidspunktNedlagt().before(dato)) {
            continue;
         }

         if (!util.erEvalEnhet(e)) {
            continue;
         }
         util.brukEtatnavnHvisHovedkontor(e);
         util.setOverordnetDepartement(e);
         set.add(e);
      }
   }

   private Date getDatePlusOneDay(Date dato) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(dato);
      int year = cal.get(Calendar.YEAR);
      int day = cal.get(Calendar.DAY_OF_YEAR) + 1;

      cal = new GregorianCalendar();
      cal.setLenient(true);
      cal.set(Calendar.YEAR, year);
      cal.set(Calendar.DAY_OF_YEAR, day);
      dato = cal.getTime();

      return dato;
   }

   /**
    * Returnerer alle enheter på gitt tidspunkt, denne brukes for temporære
    * uttak.
    *
    * @param dato
    * @return
    * @throws SQLException
    */
   public List<Enhet> getTempEnheter(Date dato) throws SQLException {
      // mapping idnum --> enhet.
      this.enhetLogikk.setEnheter(dato);

      List<Enhet> liste = new ArrayList<Enhet>(4000);

      Collection<Enhet> coll = this.enhetLogikk.enheter.values();
      Iterator<Enhet> iter = coll.iterator();
      while (iter.hasNext()) {
         Enhet e = iter.next();
         // nedlagt.
         if (e.isNedlagt() && e.getTidspunktNedlagt().before(dato)) {
            continue;
         }
         // Tilknytning = 20.
         if (e.getTilknytningsform() == null || !e.getTilknytningsform().equals(20)) {
            continue;
         }
         // Grunnenhet = 11.
         if (e.getGrunnenhet() == null || (!e.getGrunnenhet().equals(0) && !e.getGrunnenhet().equals(11))) {
            continue;
         }
         liste.add(e);
      }

      // finner overordnet dep (hvis nødvendig).
      for (Enhet e : liste) {
         if (e.getTilknytningsform() != null && e.getTilknytningsform().equals(10)
                 && e.getNivaa() != null && e.getNivaa().equals(0)) {
            continue; // e er et departement, og vi trenger ikke finne overordnet dep.
         }
         // finner overordnet dep.
         Enhet overordnet = this.enhetLogikk.enheter.get(e.getOverordnetIdnum());
         while (overordnet != null) {
            if (overordnet.getTilknytningsform() != null
                    && overordnet.getTilknytningsform().equals(10)
                    && overordnet.getNivaa() != null
                    && overordnet.getNivaa().equals(0)) {
               e.setOverordnetEnhet(overordnet);
               break;
            }
            overordnet = this.enhetLogikk.enheter.get(overordnet.getOverordnetIdnum());
         }
      }

      Collections.sort(liste, new DepartementsenhetComparator());

      return liste;
   }
}
