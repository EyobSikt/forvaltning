package no.nsd.polsys.logikk.forvaltning.ansatte;

import java.util.ArrayList;
import java.util.List;
import no.nsd.polsys.modell.forvaltning.Ansatte;

/**
 *
 * @author hvb
 */
public class AggregerAnsatteLogikk {


   // ================================================================ Metoder
   public List<Ansatte> getAggregertEtat(List<Ansatte> liste) {
      return this.getAggregertListe(liste, "etat");
   }

   public List<Ansatte> getAggregertDep(List<Ansatte> liste) {
      return this.getAggregertListe(liste, "dep");
   }

   public List<Ansatte> getAggregertFylke(List<Ansatte> liste) {
      return this.getAggregertListe(liste, "fylke");
   }

   public List<Ansatte> getAggregertKommune(List<Ansatte> liste) {
      return this.getAggregertListe(liste, "kommune");
   }

   public List<Ansatte> getAggregertEtatOgKommune(List<Ansatte> liste) {
      return this.getAggregertListe(liste, "etatkommune");
   }

   public List<Ansatte> getAggregertEtatOgFylke(List<Ansatte> liste) {
      return this.getAggregertListe(liste, "etatfylke");
   }

   private List<Ansatte> getAggregertListe(List<Ansatte> liste, String type) {
      List<Ansatte> aggListe = new ArrayList<Ansatte>();

      if (liste == null) {
         return null;
      }

      for (Ansatte a : liste) {
         this.aggreger(a, aggListe, type);
      }

      return aggListe;
   }

   private void aggreger(Ansatte ny, List<Ansatte> liste, String type) {
      Ansatte gammel = null;
      for (Ansatte a : liste) {
         if (this.sjekkLikhet(a, ny, type)) {
            gammel = a;
            break;
         }
      }
      if (gammel == null) {
         Ansatte a = this.getNyAnsatte(ny);
         liste.add(a);
      } else {
         gammel.setAnsatte(gammel.getAnsatte() + ny.getAnsatte());
         gammel.setAarsverk(gammel.getAarsverk() + ny.getAarsverk());
      }
   }

   private boolean sjekkLikhet(Ansatte agg, Ansatte ny, String type) {
      if (type.equals("etat")) {
         if (agg.getEtat().equals(ny.getEtat())) {
            return true;
         }
      }
      if (type.equals("dep")) {
         if (agg.getDepkode().equals(ny.getDepkode())) {
            return true;
         }
      }
      if (type.equals("fylke")) {
         if (agg.getFylkenummer().equals(ny.getFylkenummer())) {
            return true;
         }
      }
      if (type.equals("kommune")) {
         if (agg.getKommunenummer().equals(ny.getKommunenummer())) {
            return true;
         }
      }
      if (type.equals("etatkommune")) {
         if (agg.getEtat().equals(ny.getEtat()) && agg.getKommunenummer().equals(ny.getKommunenummer())) {
            return true;
         }
      }
      if (type.equals("etatfylke")) {
         if (agg.getEtat().equals(ny.getEtat()) && agg.getFylkenummer().equals(ny.getFylkenummer())) {
            return true;
         }
      }

      return false;
   }

   private Ansatte getNyAnsatte(Ansatte a) {
      Ansatte ny = new Ansatte();
      ny.setAar(a.getAar());
      ny.setEtat(a.getEtat());
      ny.setEtatkode(a.getEtatkode());
      ny.setFylkenummer(a.getFylkenummer());
      ny.setKommunenummer(a.getKommunenummer());
      ny.setKommune(a.getKommune());
      ny.setAnsatte(a.getAnsatte());
      ny.setAarsverk(a.getAarsverk());
      return ny;
   }
}
