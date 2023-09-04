package no.nsd.polsys.comparators.forvaltning;

import java.util.Comparator;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class DifiComparator implements Comparator<Enhet> {



    public int compare(Enhet o1, Enhet o2) {
        if (o2 == null) return -1;
        if (o1 == null) return 1;

        if (o1.getFylkenummer() == null && o2.getFylkenummer() == null) {
            return o1.compareTo(o2);
        }

        if (o2.getFylkenummer() == null) {
            return -1;
        }
        if (o1.getFylkenummer() == null) {
            return 1;
        }
        int sort = o1.getFylkenummer().compareTo(o2.getFylkenummer());
        if (sort != 0) {
            return sort;
        }
        return o1.compareTo(o2);
    }


}
