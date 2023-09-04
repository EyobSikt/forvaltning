package no.nsd.polsys.modell.admin.votering;

/**
 * Created with IntelliJ IDEA.
 * User: et
 * Date: 30.05.13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */


public class stortingsperiode {

    String stortingperiode = null;
    int sesjon = 0;
    String  id;
    public String getStortingperiode() {
        return stortingperiode;
    }

    //this makes it an attribute for the parent node
    public void setStortingperiode(String stortingperiode) {
        this.stortingperiode = stortingperiode;
    }

    public String getId() {
        return id;
    }

    //this makes it an attribute for the parent node
    public void setId(String id) {
        this.id = id;
    }

    public int getSesjon() {
        return sesjon;
    }
    public void setsesjon(int sesjon) {
        this.sesjon = sesjon;
    }

}
