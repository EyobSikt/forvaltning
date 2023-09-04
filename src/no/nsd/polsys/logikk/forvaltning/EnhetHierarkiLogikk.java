package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import no.nsd.polsys.comparators.forvaltning.EnhetTilknytningsformComparator;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.factories.forvaltning.TallgruppeCacheFactory;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

/**
 *
 * @author hvb
 */
public class EnhetHierarkiLogikk {

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
    * Finner hierarki tli gitt enhet.
    *
    * @param departement gitt departement
    * @param dato
    * @param intern om en skal finne interne eller eksterne dep.enheter.
    * @throws SQLException
    */
   public Enhet getHierarki(Integer idnum, Date dato) throws SQLException {
      // returneres fra denne metoden.
      Enhet[] organ = null;
      // mapping idnum --> enhet.
      HashMap<Integer, Enhet> enheter = new HashMap<Integer, Enhet>(4000, 0.95f);

      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);
      Map<Integer, List<Tallgruppe>> tallgruppe = TallgruppeCacheFactory.getTallgruppe(this.conn);

      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet();
         enhet.setTidspunkt(endringCache.getTidspunkt());
         enhet.setIdnum(endringCache.getIdnum());
         if (this.engelsk) {
            enhet.setLangtNavn(endringCache.getEngelskLangtNavn());
         } else {
            enhet.setLangtNavn(endringCache.getLangtNavn());
            enhet.setKortNavn(endringCache.getKortNavn());
         }
         enhet.setEndringskode(endringCache.getEndringskode());
         enhet.setOverordnetIdnum(endringCache.getOverordnetIdnum());
         enhet.setTilknytningsform(endringCache.getTilknytningsform());
         enhet.setGrunnenhet(endringCache.getGrunnenhet());
         enhet.setNivaa(endringCache.getNivaa());

         if (enhet.getTidspunkt() != null && enhet.getTidspunkt().after(dato)) {
            break;
         }
         Enhet eksisterende = enheter.get(enhet.getIdnum());
         if (eksisterende == null) {
            enheter.put(enhet.getIdnum(), enhet);
            eksisterende = enhet;
         }
         if (enhet.getLangtNavn() != null) {
            eksisterende.setKortNavn(enhet.getKortNavn());
            eksisterende.setLangtNavn(enhet.getLangtNavn());
         }
         if (enhet.getEndringskode() != null && (enhet.getEndringskode().intValue() / 100 == 3)
                 && enhet.getTidspunkt() != null && enhet.getTidspunkt().before(dato)) {
            // viktig at enheten eksisterer samme dag som den legges ned.
            eksisterende.setNedlagt(true);
         }
         if (enhet.getOverordnetIdnum() != null) {
            eksisterende.setOverordnetIdnum(enhet.getOverordnetIdnum());
         }
         if (enhet.getTilknytningsform() != null) {
            eksisterende.setTilknytningsform(enhet.getTilknytningsform());
         }
         if (enhet.getGrunnenhet() != null) {
            eksisterende.setGrunnenhet(enhet.getGrunnenhet());
         }
         if (enhet.getNivaa() != null) {
            eksisterende.setNivaa(enhet.getNivaa());
         }
      }

      Enhet gittEnhet = enheter.get(idnum);

      if (gittEnhet == null) {
         return null;
      }

      Collection<Enhet> collection = enheter.values();

      // alle unike enheter før gitt tidspunkt.
      Enhet[] unike = collection.toArray(new Enhet[0]);

      // aktuelle organ. mapping idnum --> enhet.
      Map<Integer, Enhet> organer = new HashMap<Integer, Enhet>(500, 0.95f);

      organer.put(idnum, gittEnhet);

      boolean pass = true;
      while (pass) {
         pass = this.organpass(unike, organer, gittEnhet);
      }

      collection = organer.values();
      organ = collection.toArray(new Enhet[0]);

      // Legger til tallgrupper/avdeling.
      for (Enhet e : organ) {
         List<Tallgruppe> gruppe = tallgruppe.get(e.getIdnum());
         if (gruppe == null) {
            continue;
         }
         Set<Tallgruppe> s = e.getTallgrupper();
         if (s == null) {
            s = new TreeSet<Tallgruppe>();
            e.setTallgrupper(s);
         }
         for (Tallgruppe g : gruppe) {
            if (g.getTallgruppekode() != null && g.getTallgruppekode().equals(21) && !e.getGrunnenhet().equals(20)) {
               continue;
            }
            if (g.getTallgruppekode() != null && g.getTallgruppekode().equals(31) && !e.getGrunnenhet().equals(20)) {
               continue;
            }
            if ((g.getFraTidspunkt() == null || !dato.before(g.getFraTidspunkt()))
                    && (g.getTilTidspunkt() == null || !dato.after(g.getTilTidspunkt()))) {
               s.add(g);
            }
         }
      }

      // Lager hierarki.
      pass = true;
      while (pass) {
         pass = this.overordnetpass(organ, gittEnhet);
      }

      // finner overordnete enheter.
      Enhet u = gittEnhet;
      Enhet overordnet = enheter.get(u.getOverordnetIdnum());
      while (overordnet != null) {
         Set<Enhet> underordnet = new HashSet<Enhet>();
         underordnet.add(u);
         overordnet.setUnderordnet(underordnet);
         u = overordnet;
         Enhet nyOver = enheter.get(u.getOverordnetIdnum());
         if (nyOver != null) {
            overordnet = nyOver;
         } else {
            break;
         }
      }

      //overordnet vil nå være null (altså gittEnhet er staten) eller staten.
      if (overordnet == null) {
         return gittEnhet;
      } else {
         return overordnet;
      }

   }

   private boolean overordnetpass(Enhet[] organ, Enhet enhet) {
      this.fortsettPass = false;


      for (Enhet e : organ) {
         this.leggTilUnderordnet(enhet, e);
      }


      return this.fortsettPass;
   }

   private void leggTilUnderordnet(Enhet o, Enhet u) {
      boolean leggTil = false;
      if (u.getOverordnetIdnum() != null && u.getOverordnetIdnum().equals(o.getIdnum())) {
         leggTil = true;
      }
      if (leggTil) {
         Set<Enhet> s = o.getUnderordnet();
         if (s == null) {
            s = new TreeSet<Enhet>(new EnhetTilknytningsformComparator());
            o.setUnderordnet(s);
         }
         if (s.contains(u)) {
            return;
         }
         s.add(u);
         this.fortsettPass = true;
         return;
      }
      Set<Enhet> s = o.getUnderordnet();
      if (s == null) {
         return;
      }
      Iterator<Enhet> i = s.iterator();
      while (i.hasNext()) {
         this.leggTilUnderordnet(i.next(), u);
      }
   }

   private boolean organpass(Enhet[] unike, Map<Integer, Enhet> organer, Enhet gittEnhet) {
      boolean pass = false;
      for (Enhet e : unike) {
         Integer id = e.getIdnum();
         Integer over = e.getOverordnetIdnum();
         if (e.isNedlagt() || organer.containsKey(id) || over == null) {
            continue;
         }

         if (organer.containsKey(over)) {
            organer.put(id, e);
            pass = true;
         }
      }
      return pass;
   }
}
