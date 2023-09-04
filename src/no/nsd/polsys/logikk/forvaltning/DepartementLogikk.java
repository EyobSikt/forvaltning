package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.comparators.forvaltning.DepartementsenhetComparator;
import no.nsd.polsys.comparators.forvaltning.HistoriskDepartementComparator;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Departementsenhet;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class DepartementLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;
   private boolean fortsettPass = false;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }

   /**
    * Returnerer et gitt departement, basert på id og dato.
    *
    * @param idnum
    * @param dato
    * @return returnerer departement, eller null hvis det ikke finnes.
    * @throws SQLException
    */
   public Enhet getDepartement(Integer idnum, Date dato) throws SQLException {
      Enhet dep = new Enhet();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_endring where idnum = ? and tidspunkt <= ? and langt_navn is not null order by tidspunkt desc";
      List values = new ArrayList();

      values.add(idnum);
      values.add(new java.sql.Date(dato.getTime()));

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      dep.setIdnum((Integer) rader[0].get("idnum"));
      if (this.engelsk) {
         dep.setLangtNavn((String) rader[0].get("eng_langt_navn"));
      } else {
         dep.setKortNavn((String) rader[0].get("kort_navn"));
         dep.setLangtNavn((String) rader[0].get("langt_navn"));
      }

      return dep;
   }

   /**
    * Returnerer alle departement per ditt dato.
    *
    * @param dato
    * @return Alle gyldige departement for gitt dato.
    * @throws SQLException
    */
   public List<Enhet> getDepartementer(Date dato) throws SQLException {
      List<Enhet> departementer = new ArrayList<Enhet>();

      EnhetUtilLogikk utilLogikk = new EnhetUtilLogikk();
      EnhetLogikk enhetLogikk = new EnhetLogikk();

      enhetLogikk.setConn(this.conn);
      if (this.engelsk) {
         enhetLogikk.brukEngelsk();
      }

      enhetLogikk.setEnheter(dato);

      for (Enhet e : enhetLogikk.enheter.values()) {
         if (e.isNedlagt() && e.getTidspunktNedlagt() != null && e.getTidspunktNedlagt().before(dato)) {
            continue;
         }
         if (utilLogikk.erDepartement(e)) {
            departementer.add(e);
         }
      }

      Collections.sort(departementer);

      return departementer;
   }

   /**
    * Metoden beregner både interne og ytre enheter under departement.
    *
    * @param datoer
    * @param valg - enten nivaaer (intern) eller tilknytningsformer (ytre).
    * @param intern
    * @return
    * @throws SQLException
    */
   public List<Departementsenhet> getAntallDepartementsenhet(List<Date> datoer, List<Integer> valg, boolean intern) throws SQLException {
      // Enheter på gitt tidspunkt, mapping idnum --> enhet.
      Map<Integer, Enhet> enheter = new HashMap<Integer, Enhet>(4000, 0.95f);
      // alle endringer i basen.
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);
      // brukes som index i datolisten.
      int datoteller = 0;
      // vil inneholde de relevante antallene.
      List<Departementsenhet> depenhet = new ArrayList<Departementsenhet>();

      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet(endringCache);

         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().after(datoer.get(datoteller))) {
            depenhet.add(this.getDepenhet(datoer, valg, enheter, datoteller, intern));
            datoteller++;
            if (datoteller == datoer.size()) {
               break;
            }
         }

         this.oppdaterEksisterendeEnhet(enheter, enhet);
      }

      if (datoteller < datoer.size()) {
         depenhet.add(this.getDepenhet(datoer, valg, enheter, datoteller, intern));
         datoteller++;
      }

      return depenhet;
   }

   // valg, vil enten være nivaaer (intern) eller tilknytningsformer (ytre).
   private Departementsenhet getDepenhet(List<Date> datoer, List<Integer> valg,
           Map<Integer, Enhet> enheter, int datoteller, boolean intern) {

      int[] antall = new int[valg.size()];
      int sum = 0;

      for (Enhet e : enheter.values()) {
         if (this.erNedlagt(e, datoer.get(datoteller))) {
            continue; // enheten er nedlagt, og skal ikke telles med.
         }

         if (!this.erRelevantDepEnhet(intern, e, enheter)) {
            continue;
         }

         if (intern) {
            for (int i = 0; i < valg.size(); i++) {
               if (e.getNivaa() != null && e.getNivaa().equals(valg.get(i))) {
                  antall[i]++;
                  sum++;
                  break; // ikke vits i å fortsette nå.
               }
            }
         } else {
            for (int i = 0; i < valg.size(); i++) {
               if (e.getTilknytningsform() != null && e.getTilknytningsform().equals(valg.get(i))) {
                  antall[i]++;
                  sum++;
                  break; // ikke vits i å fortsette nå.
               }
            }
         }
      }

      Departementsenhet de = new Departementsenhet();
      de.setDato(datoer.get(datoteller));
      de.setAntall(antall);
      de.setSum(sum);

      return de;
   }

   public List<List<Enhet>> getDepartementsenheter(Date dato, List<Integer> valg, boolean intern) throws SQLException {
      // mapping idnum --> enhet.
      Map<Integer, Enhet> enheter = new HashMap<Integer, Enhet>(4000, 0.95f);

      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);

      // liste over nivåer, der hvert nivå har en liste med enheter, som er depenhetene.
      List<List<Enhet>> depenheter = new ArrayList<List<Enhet>>();
      for (Integer i : valg) {
         depenheter.add(new ArrayList<Enhet>());
      }

      // for-løkken finner aktuelle enheter.
      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet(endringCache);
         if (this.engelsk) {
            enhet.setLangtNavn(enhet.getEngelskLangtNavn());
         }

         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().after(dato)) {
            break;
         }

         this.oppdaterEksisterendeEnhet(enheter, enhet);
      }

      // finner enhetene fordelt på nivå, eller tilknytningsform.
      for (Enhet e : enheter.values()) {
         if (this.erNedlagt(e, dato)) {
            continue; // enheten er nedlagt, og skal ikke telles med.
         }

         if (!this.erRelevantDepEnhet(intern, e, enheter)) {
            continue;
         }

         if (intern) {
            for (int i = 0; i < valg.size(); i++) {
               if (e.getNivaa() != null && e.getNivaa().equals(valg.get(i))) {
                  List<Enhet> de = depenheter.get(i);
                  de.add(e);
                  break; // ikke vits i å fortsette nå.
               }
            }
         } else {
            for (int i = 0; i < valg.size(); i++) {
               if (e.getTilknytningsform() != null && e.getTilknytningsform().equals(valg.get(i))) {
                  List<Enhet> de = depenheter.get(i);
                  de.add(e);
                  break; // ikke vits i å fortsette nå.
               }
            }
         }
      }

      // finner overordnet dep.
      for (List<Enhet> l : depenheter) {
         for (Enhet e : l) {
            EnhetUtilLogikk util = new EnhetUtilLogikk();
            util.setEnheter(enheter);
            util.setOverordnetDepartement(e);
         }
      }

      for (List<Enhet> l : depenheter) {
         Collections.sort(l, new DepartementsenhetComparator());
      }

      return depenheter;
   }

   private boolean erNedlagt(Enhet e, Date dato) {
      return (e.isNedlagt() && e.getTidspunktNedlagt() != null && e.getTidspunktNedlagt().before(dato));
   }

   private boolean erRelevantDepEnhet(boolean intern, Enhet e, Map<Integer, Enhet> enheter) {
      if (intern) { // interne enheter.
         if (e.getTilknytningsform() == null || !e.getTilknytningsform().equals(10)) {
            return false;
         }
      } else { // ytre enheter.
         if (e.getGrunnenhet() == null || (!e.getGrunnenhet().equals(0) && !e.getGrunnenhet().equals(11) && !e.getGrunnenhet().equals(20))) {
            return false;
         }

         if (e.getGrunnenhet().equals(20)) {
            EnhetUtilLogikk util = new EnhetUtilLogikk();
            util.setEnheter(enheter);
            int i = util.getHierarkiskNivaaUnderDep(e);
            if (i != 1 && i != -1) {
               return false;
            }
         }
      }
      return true;
   }

   private void oppdaterEksisterendeEnhet(Map<Integer, Enhet> enheter, Enhet enhet) {
      Enhet eksisterende = enheter.get(enhet.getIdnum());
      if (eksisterende == null) {
         enheter.put(enhet.getIdnum(), enhet);
      } else {
         if (enhet.getEndringskode() != null && (enhet.getEndringskode().intValue() / 100 == 3)) {
            eksisterende.setNedlagt(true);
            eksisterende.setTidspunktNedlagt(enhet.getTidspunkt());
         }
         if (enhet.getTilknytningsform() != null) {
            eksisterende.setTilknytningsform(enhet.getTilknytningsform());
         }
         if (enhet.getNivaa() != null) {
            eksisterende.setNivaa(enhet.getNivaa());
         }
         if (enhet.getOverordnetIdnum() != null) {
            eksisterende.setOverordnetIdnum(enhet.getOverordnetIdnum());
         }
         if (enhet.getGrunnenhet() != null) {
            eksisterende.setGrunnenhet(enhet.getGrunnenhet());
         }
         if (enhet.getLangtNavn() != null) {
            eksisterende.setLangtNavn(enhet.getLangtNavn());
         }
      }
   }

   public List<Enhet> getHistoriskeDepartementer() throws SQLException {
      // mapping idnum --> enhet.
      HashMap<Integer, Enhet> enheter = new HashMap<Integer, Enhet>(4000, 0.95f);
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);
      List<Enhet> departementer = new ArrayList<Enhet>();

      // for-løkken finner alle enheter.
      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet();
         enhet.setTidspunkt(endringCache.getTidspunkt());
         enhet.setIdnum(endringCache.getIdnum());
         enhet.setEndringskode(endringCache.getEndringskode());
         enhet.setTilknytningsform(endringCache.getTilknytningsform());
         enhet.setNivaa(endringCache.getNivaa());
         if (this.engelsk) {
            enhet.setLangtNavn(endringCache.getEngelskLangtNavn());
         } else {
            enhet.setLangtNavn(endringCache.getLangtNavn());
         }

         Enhet eksisterende = enheter.get(enhet.getIdnum());
         if (eksisterende == null) {
            enheter.put(enhet.getIdnum(), enhet);
         } else {
            if (enhet.getEndringskode() != null && (enhet.getEndringskode().intValue() / 100 == 3)) {
               eksisterende.setNedlagt(true);
               eksisterende.setTidspunktNedlagt(enhet.getTidspunkt());
            }
            if (enhet.getTilknytningsform() != null) {
               eksisterende.setTilknytningsform(enhet.getTilknytningsform());
            }
            if (enhet.getNivaa() != null) {
               eksisterende.setNivaa(enhet.getNivaa());
            }
            if (enhet.getLangtNavn() != null) {
               eksisterende.setLangtNavn(enhet.getLangtNavn());
            }
         }
      }

      // Finner enheter som er departementer.
      Collection<Enhet> c = enheter.values();
      Iterator<Enhet> iter = c.iterator();
      while (iter.hasNext()) {
         Enhet e = iter.next();
         DateFormat df = new SimpleDateFormat("d.M.yyyy");
         // Sjekker om enheten er et departement.
         if (e.getTilknytningsform() == null || !e.getTilknytningsform().equals(10)
                 || e.getNivaa() == null || !e.getNivaa().equals(0)) {
            continue; // ikke departement.
         }
         // Enheten er et departement og vi legger til dato til navnet.
         if (e.getTidspunkt() != null) {
            String t = df.format(e.getTidspunkt());
            e.setLangtNavn(e.getLangtNavn() + " (" + t + " - ");
         }
         if (e.getTidspunktNedlagt() != null) {
            String t = df.format(e.getTidspunktNedlagt());
            e.setLangtNavn(e.getLangtNavn() + t);
         }
         e.setLangtNavn(e.getLangtNavn() + ")");
         departementer.add(e);
      }

      Collections.sort(departementer, new HistoriskDepartementComparator());

      return departementer;
   }

   /**
    * Returnerer et gitt departement, med tidligere navn.
    *
    * @param idnum
    * @param fra
    * @param til
    * @return
    * @throws SQLException
    */
   public Enhet getHistoriskDepartement(Integer idnum, Date fra, Date til) throws SQLException {
      // mapping idnum --> enhet.
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);
      Enhet departement = null;

      // for-løkken finner alle enheter.
      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet();
         enhet.setTidspunkt(endringCache.getTidspunkt());
         enhet.setIdnum(endringCache.getIdnum());
         enhet.setEndringskode(endringCache.getEndringskode());
         if (this.engelsk) {
            enhet.setLangtNavn(endringCache.getEngelskLangtNavn());
         } else {
            enhet.setLangtNavn(endringCache.getLangtNavn());
         }

         if (enhet.getIdnum() == null || !enhet.getIdnum().equals(idnum)) {
            continue;
         }
         if (enhet.getLangtNavn() == null) {
            continue;
         }

         DateFormat df = new SimpleDateFormat("d.M.yyyy");
         String t = df.format(enhet.getTidspunkt());
         if (departement == null) {
            departement = enhet;
         } else {
            String gammelt = departement.getLangtNavn();
            gammelt += t + ")";
            List<String> tidligereNavn = departement.getTidligereNavnListe();
            if (tidligereNavn == null) {
               tidligereNavn = new ArrayList<String>();
               departement.setTidligereNavnListe(tidligereNavn);
            }
            tidligereNavn.add(0, gammelt);
         }
         departement.setLangtNavn(enhet.getLangtNavn() + " (" + t + " - ");
      }
      departement.setLangtNavn(departement.getLangtNavn() + ")");

      return departement;
   }

   public List<List<List<Enhet>>> getEndringer(Integer dep, Date fra, Date til,
           List<Integer> endringer, List<Integer> valg, Set<Integer> grunnenheter, boolean intern) throws SQLException {
      // mapping idnum --> enhet.
      HashMap<Integer, Enhet> enheter = new HashMap<Integer, Enhet>(4000, 0.95f);

      List<EndringCache> alleEndringer = EndringCacheFactory.getEndringer(this.conn);

      // liste over endringer, som hver har en liste over nivåer/tilknytning. Nivålistene/Tilknytning inneholder så enhetene.
      List<List<List<Enhet>>> endringsliste = new ArrayList<List<List<Enhet>>>();

      for (Integer i : endringer) {
         List<List<Enhet>> e = new ArrayList<List<Enhet>>();
         endringsliste.add(e);
         for (Integer j : valg) {
            e.add(new ArrayList<Enhet>());
         }
      }

      List<Enhet> tempEnheter = new ArrayList<Enhet>();
      Date tempDato = null;

      // for-løkken finner aktuelle enheter.
      for (EndringCache endringCache : alleEndringer) {
         Enhet enhet = new Enhet();
         enhet.setTidspunkt(endringCache.getTidspunkt());
         enhet.setIdnum(endringCache.getIdnum());
         enhet.setEndringskode(endringCache.getEndringskode());
         enhet.setTilknytningsform(endringCache.getTilknytningsform());
         enhet.setNivaa(endringCache.getNivaa());
         enhet.setOverordnetIdnum(endringCache.getOverordnetIdnum());
         enhet.setGrunnenhet(endringCache.getGrunnenhet());
         if (this.engelsk) {
            enhet.setLangtNavn(endringCache.getEngelskLangtNavn());
         } else {
            enhet.setLangtNavn(endringCache.getLangtNavn());
         }
         enhet.setKommunenummer(endringCache.getKommunenummer());

         Enhet eksisterende = enheter.get(enhet.getIdnum());
         if (eksisterende == null) {
            eksisterende = enhet;
            enheter.put(enhet.getIdnum(), enhet);
         } else {
            if (enhet.getEndringskode() != null && (enhet.getEndringskode().intValue() / 100 == 3)) {
               eksisterende.setNedlagt(true);
            }
            if (enhet.getTilknytningsform() != null) {
               eksisterende.setTilknytningsform(enhet.getTilknytningsform());
            }
            if (enhet.getNivaa() != null) {
               eksisterende.setNivaa(enhet.getNivaa());
            }
            if (enhet.getOverordnetIdnum() != null) {
               eksisterende.setOverordnetIdnum(enhet.getOverordnetIdnum());
            }
            if (enhet.getGrunnenhet() != null) {
               eksisterende.setGrunnenhet(enhet.getGrunnenhet());
            }
            if (enhet.getLangtNavn() != null) {
               eksisterende.setLangtNavn(enhet.getLangtNavn());
            }
            if (enhet.getKommunenummer() != null) {
               eksisterende.setKommunenummer(enhet.getKommunenummer());
            }
         }

         // sjekker tidspunkt
         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().before(fra)) {
            continue;
         }
         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().after(til)) {
            break;
         }

         if (tempDato == null) {
            tempDato = enhet.getTidspunkt();
         }

         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().after(tempDato)) {
            this.sjekkEndring(tempEnheter, enheter, dep, endringer, valg, endringsliste, grunnenheter, intern);
            tempEnheter = new ArrayList<Enhet>();
            tempDato = enhet.getTidspunkt();
         }

         Enhet tempEnhet = new Enhet();

         tempEnhet.setTidspunkt(enhet.getTidspunkt());
         tempEnhet.setEndringskode(enhet.getEndringskode());

         tempEnhet.setIdnum(eksisterende.getIdnum());
         tempEnhet.setTilknytningsform(eksisterende.getTilknytningsform());
         tempEnhet.setNivaa(eksisterende.getNivaa());
         tempEnhet.setOverordnetIdnum(eksisterende.getOverordnetIdnum());
         tempEnhet.setGrunnenhet(eksisterende.getGrunnenhet());
         tempEnhet.setLangtNavn(eksisterende.getLangtNavn());
         tempEnhet.setKommunenummer(eksisterende.getKommunenummer());

         tempEnheter.add(tempEnhet);

      }

      this.sjekkEndring(tempEnheter, enheter, dep, endringer, valg, endringsliste, grunnenheter, intern);

      return endringsliste;
   }

   private void sjekkEndring(List<Enhet> tempEnheter, HashMap<Integer, Enhet> enheter,
           Integer dep, List<Integer> endringer, List<Integer> valg, List<List<List<Enhet>>> endringsliste, Set<Integer> grunnenheter, boolean intern) {

      for (Enhet eksisterende : tempEnheter) {
         // sjekker tilknytningsform.
         if (intern) {
            if (eksisterende.getTilknytningsform() == null || !eksisterende.getTilknytningsform().equals(10)) {
               continue; // enheten er ikke intern, dep.enhet.
            }
         } else { // ytre
            if (eksisterende.getTilknytningsform() == null) {
               continue;
            }
         }

         // sjekker om enhet tilh�rer riktig departement.
         if (dep != null && !eksisterende.getIdnum().equals(dep)) {
            boolean taMed = false;
            Enhet overordnet = enheter.get(eksisterende.getOverordnetIdnum());
            while (overordnet != null) {
               if (overordnet.getIdnum().equals(dep)) {
                  taMed = true;
                  break;
               }
               overordnet = enheter.get(overordnet.getOverordnetIdnum());
            }
            if (!taMed) {
               continue;
            }
         }

         for (int i = 0; i < endringer.size(); i++) {
            if (eksisterende.getEndringskode() == null || !eksisterende.getEndringskode().equals(endringer.get(i))) {
               continue;
            }
            for (int j = 0; j < valg.size(); j++) {
               if (intern) {
                  if (eksisterende.getNivaa() != null && eksisterende.getNivaa().equals(valg.get(j))) {
                     List<Enhet> de = endringsliste.get(i).get(j);
                     Enhet e = new Enhet();
                     e.setIdnum(eksisterende.getIdnum());
                     e.setLangtNavn(eksisterende.getLangtNavn());
                     e.setTidspunkt(eksisterende.getTidspunkt());
                     e.setKommunenummer(eksisterende.getKommunenummer());
                     de.add(e);
                     break; // ikke vits i å fortsette nå.
                  }
               } else { // ytre
                  if (eksisterende.getTilknytningsform().equals(valg.get(j))
                          && grunnenheter.contains(eksisterende.getGrunnenhet())) {
                     List<Enhet> de = endringsliste.get(i).get(j);
                     Enhet e = new Enhet();
                     e.setIdnum(eksisterende.getIdnum());
                     e.setLangtNavn(eksisterende.getLangtNavn());
                     e.setTidspunkt(eksisterende.getTidspunkt());
                     e.setKommunenummer(eksisterende.getKommunenummer());
                     de.add(e);
                     break; // ikke vits i å fortsette nå.
                  }
               }
            }
         }
      }

   }

   public List<Enhet> getLokaliseringerUtenforOslo(Integer dep, Date fra, Date til,
           List<Integer> endringer, List<Integer> valg, Set<Integer> grunnenheter) throws SQLException {

      // liste over endringer, som hver har en liste over nivåer/tilknytning. Nivålistene/Tilknytning inneholder så enhetene.
      List<List<List<Enhet>>> enheter = this.getEndringer(dep, fra, til, endringer, valg, grunnenheter, false);

      List<Kommune> alleKommuner = KommuneCacheFactory.getKommuner(this.conn);

      List<Enhet> ikkeOslo = new ArrayList<Enhet>();

      for (List<List<Enhet>> e : enheter) {
         for (List<Enhet> v : e) {
            for (Enhet x : v) {
               if (x.getKommunenummer() != null && !x.getKommunenummer().equals(-1) && !x.getKommunenummer().equals(301)) {
                  Kommune k = new Kommune();
                  k.setKode(x.getKommunenummer());
                  Kommune komplett = this.getKommune(alleKommuner, x.getKommunenummer(), x.getTidspunkt());
                  if (komplett != null) {
                     x.setKommune(komplett);
                  } else {
                     x.setKommune(k);
                  }
                  ikkeOslo.add(x);
               }
            }
         }
      }

      Collections.sort(ikkeOslo);

      return ikkeOslo;
   }

   private Kommune getKommune(List<Kommune> alleKommuner, Integer kommunenr, Date date) {
      int aar = -1;
      if (date != null) {
         Calendar cal = new GregorianCalendar();
         cal.setTime(date);
         aar = cal.get(Calendar.YEAR);
      }
      for (Kommune k : alleKommuner) {
         if (aar != -1) {
            if (k.getFomAar() != null && k.getFomAar() > aar) {
               continue;
            }
            if (k.getTomAar() != null && k.getTomAar() < aar) {
               continue;
            }
         }
         if (kommunenr.equals(k.getKode())) {
            return k;
         }
      }
      return null;
   }
}
