package no.nsd.polsys.modell.storting;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 05.jan.2011
 * Time: 08:45:45
 * To change this template use File | Settings | File Templates.
 */
public class KulturPolitikk {

  String partinavn;
  int partikode;
  String doktittel;
  String fornavn;
  String etternavn;
  String sitathtml;
  String dato;
  int personId;    

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }



    public String getSitathtml() {
        return sitathtml;
    }

    public void setSitathtml(String sitathtml) {
        this.sitathtml = sitathtml;
    }



    public String getDoktittel() {
        return doktittel;
    }

    public void setDoktittel(String doktittel) {
        this.doktittel = doktittel;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getPartinavn() {
        return partinavn;
    }

    public void setPartinavn(String partinavn) {
        this.partinavn = partinavn;
    }

    public int getPartikode() {
        return partikode;
    }

    public void setPartikode(int partikode) {
        this.partikode = partikode;
    }

}
