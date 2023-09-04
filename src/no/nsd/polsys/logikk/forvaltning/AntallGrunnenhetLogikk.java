package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class AntallGrunnenhetLogikk {

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
   private int[][] data = null;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
      this.enhetLogikk.setConn(conn);
   }

   public void brukEngelsk() {
      this.engelsk = true;
      this.enhetLogikk.brukEngelsk();
   }

   public int[][] getEnheter() throws SQLException {
      // i = år, j = grunnenhet.
      this.data = new int[3000][30];

      for (int i = 1947; i <= 2011; i++) {
         this.getEnheter(i);
      }

      return data;
   }

   public void getEnheter(int aar) throws SQLException {
      Dato d = new Dato(aar, 1, 1);
      Date date = d.getDate();

      this.enhetLogikk.setEnheter(date);
      Map<Integer, Enhet> enheter = this.enhetLogikk.enheter;

      Collection<Enhet> col = enheter.values();
      Iterator<Enhet> iter = col.iterator();

      while (iter != null && iter.hasNext()) {
         Enhet e = iter.next();
         if (this.erEnhetForvaltning(e)) {
            if (e.getGrunnenhet() == null) {
               continue;
            }
            this.data[aar][e.getGrunnenhet()]++;
         }
      }
   }

   private boolean erEnhetForvaltning(Enhet e) {

      // tar ikke med Børser 3600 og Norges Bank 1614
      if (e.getIdnum().equals(3600) || e.getIdnum().equals(1614)) {
         return false;
      }
      // nedlagt
      if (e.isNedlagt()) {
         return false;
      }
      // feil grunntype.
      if (e.getGrunnenhet() != null
              && !e.getGrunnenhet().equals(0) && !e.getGrunnenhet().equals(10) && !e.getGrunnenhet().equals(20)) {
         return false;
      }

      // riktig tilknytning.
      if (e.getTilknytningsform() != null
              && (e.getTilknytningsform().equals(15)
              || e.getTilknytningsform().equals(20)
              || e.getTilknytningsform().equals(31)
              || e.getTilknytningsform().equals(32)
              || e.getTilknytningsform().equals(33)
              || e.getTilknytningsform().equals(35))) {
         return true;
      }

      return false;
   }
}
