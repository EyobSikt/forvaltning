package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Klassen inneholder statistikk for DIFI.
 *
 * @author hvb
 */
public final class Difi implements Serializable {

   // ============================================================== Variabler
   // Statistikk grunnenheter: mapping: grunnenhet --> antall enheter.
   private Map<String, Integer> statGrunnenhet;
   // Statistikk tilknytningsformer: mapping: tilknytningsform --> antall enheter.
   private Map<String, Integer> statTilknytning;
   List<Enhet> enheter;


   // ================================================================ Metoder
   public Map<String, Integer> getStatGrunnenhet() {
      return statGrunnenhet;
   }

   public void setStatGrunnenhet(Map<String, Integer> statGrunnenhet) {
      this.statGrunnenhet = statGrunnenhet;
   }

   public Map<String, Integer> getStatTilknytning() {
      return statTilknytning;
   }

   public void setStatTilknytning(Map<String, Integer> statTilknytning) {
      this.statTilknytning = statTilknytning;
   }

   public List<Enhet> getEnheter() {
      return enheter;
   }

   public void setEnheter(List<Enhet> enheter) {
      this.enheter = enheter;
   }
}
