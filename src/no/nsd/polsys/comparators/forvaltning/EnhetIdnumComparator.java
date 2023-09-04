package no.nsd.polsys.comparators.forvaltning;

import java.util.Comparator;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EnhetIdnumComparator implements Comparator<Enhet> {

    public int compare(Enhet o1, Enhet o2) {
        if (o2 == null) return -1;
        if (o1 == null) return 1;
        Integer i1 = o1.getIdnum();
        Integer i2 = o2.getIdnum();
        if (i2 == null) return -1;
        if (i1 == null) return 1;
        return i1.compareTo(i2);
    }

}
