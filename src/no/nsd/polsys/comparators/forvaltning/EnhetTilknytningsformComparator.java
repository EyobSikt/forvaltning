package no.nsd.polsys.comparators.forvaltning;

import java.util.Comparator;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EnhetTilknytningsformComparator implements Comparator<Enhet> {

    public int compare(Enhet e1, Enhet e2) {
        if (e2 == null) return -1;
        if (e1 == null) return 1;
        
        Integer t1 = e1.getTilknytningsform();
        Integer t2 = e2.getTilknytningsform();

        if (t1 == null && t2 == null) {
            return e1.compareTo(e2);
        }
        
        if (t1 == null) return -1;
        if (t2 == null) return 1;
        
        if (t1.equals(t2)) {
            return e1.compareTo(e2);
        }
        
        return t1.compareTo(t2);
    }

}
