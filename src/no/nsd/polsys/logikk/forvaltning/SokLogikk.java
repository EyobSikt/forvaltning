package no.nsd.polsys.logikk.forvaltning;

import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class SokLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   private boolean engelsk = false;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }

   /**
    *
    * @param s
    * @return
    * @throws SQLException
    */
   public Enhet[] sok(String s) throws SQLException {
      // mapping idnum --> enhet.
      Map<Integer, Enhet> enheter = new HashMap<Integer, Enhet>();
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);

      s = s.toLowerCase();

      for (EndringCache endringCache : endringer) {
         Enhet e = new Enhet();
         e.setTidspunkt(endringCache.getTidspunkt());
         e.setIdnum(endringCache.getIdnum());
         if (this.engelsk) {
            e.setLangtNavn(endringCache.getEngelskLangtNavn());
         } else {
            e.setLangtNavn(endringCache.getLangtNavn());
            e.setKortNavn(endringCache.getKortNavn());
         }

         // Ikke en navneendring.
         if (e.getLangtNavn() == null) {
            continue;
         }

         String langtNavn = e.getLangtNavn();
         String kortNavn = e.getKortNavn();
         if (langtNavn != null) {
            langtNavn = langtNavn.toLowerCase();
         }
         if (kortNavn != null) {
            kortNavn = kortNavn.toLowerCase();
         }

         // Ikke treff på s.
         if (!langtNavn.contains(s) && (kortNavn == null || !kortNavn.contains(s))) {
            // Men legger likevel til navnet hvis vi har fått treff på enheten før.
            Enhet ex = enheter.get(e.getIdnum());
            if (ex != null) {
               Set<String> andreNavn = ex.getTidligereNavn();
               if (ex.getLangtNavn().toLowerCase().contains(s) && !langtNavn.equalsIgnoreCase(ex.getLangtNavn())) {
                  andreNavn.add(ex.getLangtNavn());
               }
               ex.setLangtNavn(e.getLangtNavn());
            }
            continue;
         }

         Enhet ex = enheter.get(e.getIdnum());
         if (ex == null) {
            enheter.put(e.getIdnum(), e);
            Set<String> andreNavn = new TreeSet<String>();
            e.setTidligereNavn(andreNavn);
            if ((kortNavn != null && kortNavn.contains(s)) && (!langtNavn.contains(kortNavn))) {
               andreNavn.add(e.getKortNavn());
            }
         } else {
            Set<String> andreNavn = ex.getTidligereNavn();
            if (ex.getLangtNavn().toLowerCase().contains(s) && !langtNavn.equalsIgnoreCase(ex.getLangtNavn())) {
               andreNavn.add(ex.getLangtNavn());
            }
            ex.setLangtNavn(e.getLangtNavn());
            if ((kortNavn != null && kortNavn.contains(s)) && (!langtNavn.contains(kortNavn))) {
               andreNavn.add(e.getKortNavn());
            }
         }
      }

      Collection<Enhet> c = enheter.values();
      Enhet[] sortert = c.toArray(new Enhet[0]);
      Arrays.sort(sortert);
      return sortert;
   }
}
