package no.nsd.polsys.comparators.forvaltning;

import java.util.Comparator;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class DepartementsenhetComparator implements Comparator<Enhet> {



    public int compare(Enhet o1, Enhet o2) {
        if (o2 == null) return -1;
        if (o1 == null) return 1;

        if (o1.getOverordnetDepartement() == null && o2.getOverordnetDepartement() == null) {
            return o1.compareTo(o2);
        }

        if (o2.getOverordnetDepartement() == null) {
            return -1;
        }
        if (o1.getOverordnetDepartement() == null) {
            return 1;
        }
        int sortO = o1.getOverordnetDepartement().compareTo(o2.getOverordnetDepartement());
        if (sortO != 0) {
            return sortO;
        }
        return o1.compareTo(o2);
    }


}
