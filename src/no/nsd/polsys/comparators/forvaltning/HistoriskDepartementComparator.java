package no.nsd.polsys.comparators.forvaltning;

import java.util.Comparator;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class HistoriskDepartementComparator implements Comparator<Enhet> {



    public int compare(Enhet o1, Enhet o2) {
        if (o2 == null) return -1;
        if (o1 == null) return 1;
        // o2 er nedlagt.
        if (!o1.isNedlagt() && o2.isNedlagt()) {
            return -1;
        }
        // o1 er nedlagt.
        if (o1.isNedlagt() && !o2.isNedlagt()) {
            return 1;
        }
        // begge er nedlagt.
        if (o1.isNedlagt() && o2.isNedlagt()) {
            if (o1.getTidspunktNedlagt() != null) {
                int i = o1.getTidspunktNedlagt().compareTo(o2.getTidspunktNedlagt());
                if (i != 0) {
                    return i * -1; // skal sortere omvendt kronologisk.
                }
            }
        }
        // ingen er nedlagt, eller begge er nedlagt samtidig.
        return o1.compareTo(o2);
    }


}
