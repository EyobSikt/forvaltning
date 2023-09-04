package no.nsd.polsys.modell.forvaltning;

/**
 *
 * @author hvb
 */
public class Util {


    public static String getStatGrlTekstNorsk(String statgrl) {
        String s = "";
            
        if (statgrl == null || statgrl.length() == 0 || statgrl.equals("2")) s = "ordinært regulativlønte";
        else if (statgrl != null && statgrl.equals("a")) s = "alle lønnskategorier";
        else if (statgrl != null && statgrl.equals("3")) s = "lønnskategori 3";
        else if (statgrl != null && statgrl.equals("4")) s = "lønnskategori 4";
        else s = "lønnskategori " + statgrl;
        
        return s;
    }

    public static String getStatGrlTekstEngelsk(String statgrl) {
        String s = "";

            if (statgrl == null || statgrl.length() == 0 || statgrl.equals("2")) s = "regular employees";
            else if (statgrl != null && statgrl.equals("a")) s = "all salary categories";
            else if (statgrl != null && statgrl.equals("3")) s = "salary category 3";
            else if (statgrl != null && statgrl.equals("4")) s = "salary category 4";
            else s = "salary category " + statgrl;

        return s;
    }

    

}

