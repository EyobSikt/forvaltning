package no.nsd.polsys.comparators.forvaltning;

import java.util.Comparator;
import no.nsd.polsys.modell.forvaltning.Ansatte;

/**
 *
 * @author hvb
 */
public class AnsatteEtatkodeComparator implements Comparator<Ansatte> {


    public int compare(Ansatte o1, Ansatte o2) {
        if (o2 == null) return -1;
        if (o1 == null) return 1;

        String etat1 = o1.getEtatkode();
        String etat2 = o2.getEtatkode();

        if (etat2 == null) return -1;
        if (etat1 == null) return 1;

        String[] split1 = etat1.split(":");
        String[] split2 = etat2.split(":");

        for (int i = 0; i < Math.max(split1.length, split2.length); i++) {
            if (split1.length - 1 < i) return -1;
            if (split2.length - 1 < i) return 1;

            Integer k1 = new Integer(split1[i]);
            Integer k2 = new Integer(split2[i]);

            int res = k1.compareTo(k2);

            if (res != 0) return res;
        }

        return 0;
    }


}
