package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.comparators.forvaltning.EnhetTilknytningsformComparator;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Enhetsregisteret;


import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author hvb
 */
public class EnhetsregisteretLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;
   private boolean fortsettPass = false;



   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }





   /*
   * another test made by eyob
   * */

    public List<Enhet> getDepartementer2(Integer aar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();

        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct * from t_forvaltning_bronnoysund_enhethierarki where  overordnet_idnum = 0 and aar = ? order by navn ";
        List values = new ArrayList();
        values.add(aar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();

            dep.setLangtNavn((String) rad.get("navn"));
            dep.setIdnum((Integer) rad.get("idnum"));

            enheter.add(dep);

        }

        return enheter;

    }

    /********************************************/
    public List<Enhet> getHierarki3(Integer idnum, Integer aar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();

        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        Result result2 = null;
        SqlCommandBean sqlCB2 = new SqlCommandBean();
        SortedMap[] rader2 = null;
        String sqlSelect = " select * from t_forvaltning_bronnoysund_enhethierarki where idnum = ? and aar = ? order by navn ";
        List values2 = new ArrayList();
        values2.add(idnum);
        values2.add(aar);
        sqlCB.setConnection(this.conn);
        sqlCB.setValues(values2);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            enheter.add(getHierarki2((Integer) rad.get("idnum"), (Integer) rad.get("aar")));
        }
        return enheter;
    }

    /******************** alle aar *****************************/
    public List<Integer> getAlleAar() throws SQLException {

        List<Integer> enheter = new ArrayList<Integer>();

        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        Result result2 = null;
        SqlCommandBean sqlCB2 = new SqlCommandBean();
        SortedMap[] rader2 = null;
        String sqlSelect = " select distinct aar from t_forvaltning_bronnoysund_enhethierarki  order by aar desc ";
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            enheter.add(((Integer) rad.get("aar")));
        }
        return enheter;
    }

public Enhet getHierarki2(Integer idnum, Integer aar) throws SQLException {
    // returneres fra denne metoden.
    Enhet[] organ = null;
    // mapping idnum --> enhet.
    HashMap<Integer, Enhet> enheter = new HashMap<Integer, Enhet>(4000, 0.95f);

    List<Enhet> endringer = EndringCacheFactory.getEndringer2(this.conn, aar);

    for (Enhet endringCache : endringer) {
        Enhet enhet = new Enhet();
        enhet.setIdnum(endringCache.getIdnum());
        enhet.setForvaltningsidnum(endringCache.getForvaltningsidnum());
        enhet.setAar(endringCache.getAar());
        enhet.setLangtNavn(endringCache.getLangtNavn());
        enhet.setOverordnetIdnum(endringCache.getOverordnetIdnum());
        enhet.setTilknytningsform(endringCache.getTilknytningsform());
        Enhet eksisterende = enheter.get(enhet.getIdnum());

        if (eksisterende == null) {
            enheter.put(enhet.getIdnum(), enhet);
            eksisterende = enhet;
        }
        if (enhet.getLangtNavn() != null) {
            eksisterende.setLangtNavn(enhet.getLangtNavn());
        }
        if (enhet.getOverordnetIdnum() != null) {
            eksisterende.setOverordnetIdnum(enhet.getOverordnetIdnum());
        }
        if (enhet.getTilknytningsform() != null) {
            eksisterende.setTilknytningsform(enhet.getTilknytningsform());
        }
    }


    Enhet gittEnhet = enheter.get(idnum);
    if (gittEnhet == null) {
        return null;
    }

    Collection<Enhet> collection = enheter.values();

    // alle unike enheter før gitt tidspunkt.
    Enhet[] unike = collection.toArray(new Enhet[0]);

    // aktuelle organ. mapping idnum --> enhet.
    Map<Integer, Enhet> organer = new HashMap<Integer, Enhet>(500, 0.95f);

    organer.put(idnum, gittEnhet);

    boolean pass = true;
    while (pass) {
        pass = this.organpass(unike, organer, gittEnhet);
    }


    collection = organer.values();
    organ = collection.toArray(new Enhet[0]);

// Lager hierarki.
    pass = true;
    while (pass) {
        pass = this.overordnetpass(organ, gittEnhet);
    }

    // finner overordnete enheter.
    Enhet u = gittEnhet;
    //System.out.println( u + "-" + u.getOverordnetIdnum()); // overordentid 0 1 6
    Enhet overordnet = enheter.get(u.getOverordnetIdnum());
    while (overordnet != null) {
        Set<Enhet> underordnet = new HashSet<Enhet>();
        underordnet.add(u);
        overordnet.setUnderordnet(underordnet);
         u = overordnet;
        Enhet nyOver = enheter.get(u.getOverordnetIdnum());
        if (nyOver != null) {
            overordnet = nyOver;
        } else {
            break;
        }
    }
    //overordnet vil nå være null (altså gittEnhet er staten) eller staten.
    if (overordnet == null) {
        return gittEnhet;
    } else {
        return overordnet;
    }

}

private boolean overordnetpass(Enhet[] organ, Enhet enhet) {
        this.fortsettPass = false;
        for (Enhet e : organ) {
            this.leggTilUnderordnet(enhet, e);
        }
        return this.fortsettPass;
    }


private void leggTilUnderordnet(Enhet o, Enhet u) {
        boolean leggTil = false;
        if (u.getOverordnetIdnum() != null && u.getOverordnetIdnum().equals(o.getIdnum())) {
            leggTil = true;
        }
        if (leggTil) {
            Set<Enhet> s = o.getUnderordnet();
            if (s == null) {
                s = new TreeSet<Enhet>(new EnhetTilknytningsformComparator());
                o.setUnderordnet(s);
            }
            if (s.contains(u)) {
                return;
            }
            s.add(u);
            this.fortsettPass = true;
            return;
        }
        Set<Enhet> s = o.getUnderordnet();
        if (s == null) {
            return;
        }
        Iterator<Enhet> i = s.iterator();
        while (i.hasNext()) {
            this.leggTilUnderordnet(i.next(), u);
        }
    }

private boolean organpass(Enhet[] unike, Map<Integer, Enhet> organer, Enhet gittEnhet) {
        boolean pass = false;
        for (Enhet e : unike) {
            Integer id = e.getIdnum();
            Integer over = e.getOverordnetIdnum();
            if (e.isNedlagt() || organer.containsKey(id) || over == null) {
                continue;
            }

            if (organer.containsKey(over)) {
                organer.put(id, e);
                pass = true;
            }
        }
        return pass;
    }


/******deksripsjon på enhet.. tillegsopplysyining om orgnisasjon***************/
    /******deksripsjon på enhet.. tillegsopplysyining om orgnisasjon***************/
    /******deksripsjon på enhet.. tillegsopplysyining om orgnisasjon***************/
    /******deksripsjon på enhet.. tillegsopplysyining om orgnisasjon***************/



public static Integer getId(String url) {
    String[] split = url.split("/");
    for (String s : split) {
        try {
            return new Integer(s);
        } catch (Exception e) {
        }
    }
    return null;
}



    public List<Enhetsregisteret> getEnhet(Integer idnum, Integer valgtAar) throws SQLException {

        List<Enhetsregisteret> enheter = new ArrayList<Enhetsregisteret>();

        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct * from t_forvaltning_bronnoysund_enhethierarki where  idnum = ? and aar=? ";
        List values = new ArrayList();
        values.add(idnum);
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhetsregisteret dep = new Enhetsregisteret();
            if(rad.get("navn") !=null ) {dep.setNavn((String) rad.get("navn"));}
            dep.setIdnum((Integer) rad.get("idnum"));
            dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            /*
            dep.setAntallAnsatte_menn((Integer) rad.get("sst_antall_menn"));
            dep.setAntallAnsatte_kvinner((Integer) rad.get("sst_antall_kvinner"));
            */
            dep.setOrganisasjonsform((String) rad.get("organisasjonsform"));
            if((String) rad.get("forretningsadresse_adresse")!=null) {
                dep.setForretingsadresse((String) rad.get("forretningsadresse_adresse") + ", " + (String) rad.get("forretningsadresse_postnummer") + " " + (String) rad.get("forretningsadresse_poststed"));
            }
            if((String) rad.get("forretningsadresse_kommune")!=null) { dep.setForretingskommune((String) rad.get("forretningsadresse_kommune"));}
                if((String) rad.get("forretningsadresse_land")!=null) {dep.setForretingsland((String) rad.get("forretningsadresse_land"));}
            if((String) rad.get("postadresse_adresse")!=null) {
                dep.setPostadresse_adresse((String) rad.get("postadresse_adresse") + (String) ", " + (String) rad.get("postadresse_postnummer") + " " + (String) rad.get("postadresse_poststed"));
            }
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
            dep.setRegistrertdato(DATE_FORMAT.format(rad.get("registreringsdato")));
            if((String) rad.get("naringskode_1")!=null) {
                dep.setNaringskode((String) rad.get("naringskode_1") + " - " + (String) rad.get("naringskode_1_beskrivelse"));
            }
            if((String) rad.get("institusjonellsektorkode_kode")!=null) {
                dep.setSektorkode((String) rad.get("institusjonellsektorkode_kode") + " - " + (String) rad.get("institusjonellsektorkode_beskrivelse"));
            }
            if((Integer) rad.get("forvaltningsidnum")!=null) {
                dep.setForvaltningsidnum((Integer) rad.get("forvaltningsidnum"));
            }
            enheter.add(dep);
        }
        return enheter;
    }

    public List<Enhetsregisteret> getEnhetnew(Integer idnum, Integer valgtAar) throws SQLException {

        List<Enhetsregisteret> enheter = new ArrayList<Enhetsregisteret>();

        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct top 1 idnum  from t_forvaltning_bronnoysund_enhethierarki where forvaltningsidnum = ? and aar = ? ";
        List values = new ArrayList();
        values.add(idnum);
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhetsregisteret dep = new Enhetsregisteret();
            if(rad.get("navn") !=null ) {dep.setNavn((String) rad.get("navn"));}
            dep.setIdnum((Integer) rad.get("forvaltningsidnum"));
            dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            /*
            dep.setAntallAnsatte_menn((Integer) rad.get("sst_antall_menn"));
            dep.setAntallAnsatte_kvinner((Integer) rad.get("sst_antall_kvinner"));
            */
            dep.setOrganisasjonsform((String) rad.get("organisasjonsform"));
            if((String) rad.get("forretningsadresse_adresse")!=null) {
                dep.setForretingsadresse((String) rad.get("forretningsadresse_adresse") + ", " + (String) rad.get("forretningsadresse_postnummer") + " " + (String) rad.get("forretningsadresse_poststed"));
            }
            if((String) rad.get("forretningsadresse_kommune")!=null) { dep.setForretingskommune((String) rad.get("forretningsadresse_kommune"));}
            if((String) rad.get("forretningsadresse_land")!=null) {dep.setForretingsland((String) rad.get("forretningsadresse_land"));}
            if((String) rad.get("postadresse_adresse")!=null) {
                dep.setPostadresse_adresse((String) rad.get("postadresse_adresse") + (String) ", " + (String) rad.get("postadresse_postnummer") + " " + (String) rad.get("postadresse_poststed"));
            }
            //SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
            //dep.setRegistrertdato(DATE_FORMAT.format(rad.get("registreringsdato")));
            if((String) rad.get("naringskode_1")!=null) {
                dep.setNaringskode((String) rad.get("naringskode_1") + " - " + (String) rad.get("naringskode_1_beskrivelse"));
            }
            if((String) rad.get("institusjonellsektorkode_kode")!=null) {
                dep.setSektorkode((String) rad.get("institusjonellsektorkode_kode") + " - " + (String) rad.get("institusjonellsektorkode_beskrivelse"));
            }
            if((Integer) rad.get("forvaltningsidnum")!=null) {
                dep.setForvaltningsidnum((Integer) rad.get("forvaltningsidnum"));
            }
            enheter.add(dep);
        }
        return enheter;
    }

 /*arbeide yrke */
 public List<Enhet> getEnhet_arb_yrke(Integer idnum, Integer valgtAar) throws SQLException {

     List<Enhet> arbeidyrke = new ArrayList<Enhet>();

     EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
     Result result = null;
     SqlCommandBean sqlCB = new SqlCommandBean();
     SortedMap[] rader = null;

     //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
     String sqlSelect = "select distinct t_forvaltning_bronnoysund_arb_yrke.arb_yrke, t_forvaltning_bronnoysund_arb_yrke.yrkestittel, t_forvaltning_bronnoysund_arb_yrke.antall_menn, t_forvaltning_bronnoysund_arb_yrke.antall_kvinner from t_forvaltning_bronnoysund_arb_yrke " +
             "inner join t_forvaltning_bronnoysund_enhethierarki on t_forvaltning_bronnoysund_arb_yrke.organisasjonsnummer = t_forvaltning_bronnoysund_enhethierarki.organisasjonsnummer " +
             "where  t_forvaltning_bronnoysund_arb_yrke.organisasjonsnummer = ? and  t_forvaltning_bronnoysund_enhethierarki.aar <2015 and t_forvaltning_bronnoysund_enhethierarki.aar = ? and t_forvaltning_bronnoysund_arb_yrke.aar = ? order by t_forvaltning_bronnoysund_arb_yrke.yrkestittel ";
     List values = new ArrayList();
     values.add(idnum);
     values.add(valgtAar);
     values.add(valgtAar);
     sqlCB.setConnection(this.conn);
     sqlCB.setSqlValue(sqlSelect);
     sqlCB.setValues(values);
     result = sqlCB.executeQuery();
     rader = result.getRows();
     for (SortedMap rad : rader) {
         Enhet yrkekode = new Enhet();
         yrkekode.setArbYrke((Integer) rad.get("arb_yrke"));
         yrkekode.setYrkesTittel((String) rad.get("yrkestittel"));
         yrkekode.setAntallAnsatte_menn((Integer) rad.get("antall_menn"));
         yrkekode.setAntallAnsatte_kvinner((Integer) rad.get("antall_kvinner"));
         arbeidyrke.add(yrkekode);
     }
     return arbeidyrke;
 }

    /* alle arbeide yrke  */
    public List<Enhet> getAlleEnhet_arb_yrke(Integer valgtAar) throws SQLException {

        List<Enhet> arbeidyrke = new ArrayList<Enhet>();

        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct t_forvaltning_bronnoysund_arb_yrke.arb_yrke, t_forvaltning_bronnoysund_arb_yrke.yrkestittel,  sum(antall_menn) as menn, sum(antall_kvinner) as kvinner, count(t_forvaltning_bronnoysund_arb_yrke.organisasjonsnummer) as antallorg from t_forvaltning_bronnoysund_arb_yrke " +
                " inner join t_forvaltning_bronnoysund_enhethierarki on t_forvaltning_bronnoysund_arb_yrke.organisasjonsnummer = t_forvaltning_bronnoysund_enhethierarki.organisasjonsnummer  \n" +
                " where t_forvaltning_bronnoysund_enhethierarki.aar = ? and  t_forvaltning_bronnoysund_enhethierarki.aar <2015 and t_forvaltning_bronnoysund_arb_yrke.aar = ? " +
                "group by t_forvaltning_bronnoysund_arb_yrke.arb_yrke, t_forvaltning_bronnoysund_arb_yrke.yrkestittel\n" +
                "order by t_forvaltning_bronnoysund_arb_yrke.arb_yrke desc";
        List values = new ArrayList();
        values.add(valgtAar);
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet yrkekode = new Enhet();

            yrkekode.setArbYrke((Integer) rad.get("arb_yrke"));
            yrkekode.setYrkesTittel((String) rad.get("yrkestittel"));
            yrkekode.setAntallAnsatte_menn((Integer) rad.get("menn"));
            yrkekode.setAntallAnsatte_kvinner((Integer) rad.get("kvinner"));
            yrkekode.setAntall((Integer) rad.get("antallorg"));
            yrkekode.setAar(valgtAar);

            arbeidyrke.add(yrkekode);

        }
        return arbeidyrke;
    }


    public List<Enhet> getForvaltningsEnhet(Integer idnum, Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();

        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct  idnum as orgnr, forvaltningsidnum  from t_forvaltning_bronnoysund_enhethierarki where  idnum = ? and aar=? ";
        List values = new ArrayList();
        values.add(idnum);
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setIdnum((Integer) rad.get("forvaltningsidnum"));
            dep.setOrgnummer((Integer) rad.get("orgnr"));
            enheter.add(dep);

        }

        return enheter;

    }

    /****************************** statistikk side *****************************/

    /*ORGANISASJONSFORM*/
    public List<Enhet> getOrganisasjonsform(Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select organisasjonsform,  count(*) as antall,  sum(antallansatte) as antallansatte  from t_forvaltning_bronnoysund_enhethierarki where aar = ? group by organisasjonsform order by organisasjonsform ";
        List values = new ArrayList();
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            if(rad.get("organisasjonsform").equals("ORGL")){dep.setOrganisasjonsgruppebeskrivelse("Organisasjonsledd");}
            if(rad.get("organisasjonsform").equals("BEDR")){dep.setOrganisasjonsgruppebeskrivelse("Bedrift");}
            if(rad.get("organisasjonsform").equals("STAT")){dep.setOrganisasjonsgruppebeskrivelse("Staten");}
            if(rad.get("organisasjonsform").equals("STI")){dep.setOrganisasjonsgruppebeskrivelse("Stiftelse");}
            if(rad.get("organisasjonsform").equals("SÆR")){dep.setOrganisasjonsgruppebeskrivelse("Annet foretak ifølge særskilt lov");}
            if(rad.get("organisasjonsform").equals("AS")){dep.setOrganisasjonsgruppebeskrivelse("Aksjeselskap");}
            if(rad.get("organisasjonsform").equals("FLI")){dep.setOrganisasjonsgruppebeskrivelse("Forening/lag/innretning");}
            if(rad.get("organisasjonsform").equals("KIRK")){dep.setOrganisasjonsgruppebeskrivelse("Den norske kirke");}
            if(rad.get("organisasjonsform").equals("SF")){dep.setOrganisasjonsgruppebeskrivelse("Statsforetak");}
            dep.setOrganisasjonsgruppe(((String) rad.get("organisasjonsform")));
            dep.setAar(valgtAar);
            //dep.setAntall((Integer) rad.get("antall"));
            dep.setAntall((int) ((long)rad.get("antall")));
            //dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            dep.setAntallAnsatte((int) ((long)rad.get("antallansatte")));
            enheter.add(dep);
        }
        return enheter;
    }

    /*NÆRINGSKODE*/
    public List<Enhet> getNaeringskode(Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select naringskode_1, naringskode_1_beskrivelse, count(*) as antall, sum(antallansatte) as antallansatte   from t_forvaltning_bronnoysund_enhethierarki where aar = ?  group by naringskode_1, naringskode_1_beskrivelse order by naringskode_1 ";
        List values = new ArrayList();
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setOrganisasjonsgruppe(((String) rad.get("naringskode_1")));
            dep.setOrganisasjonsgruppebeskrivelse((String) rad.get("naringskode_1_beskrivelse"));
            //dep.setAntall((Integer) rad.get("antall"));
            dep.setAntall((int) ((long)rad.get("antall")));
            dep.setAar(valgtAar);
            //dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            dep.setAntallAnsatte((int) ((long)rad.get("antallansatte")));
            enheter.add(dep);
        }
        return enheter;
    }

    /*SEKTORKODE*/
    public List<Enhet> getSektorkode(Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select institusjonellsektorkode_kode, institusjonellsektorkode_beskrivelse,  count(*) as antall, sum(antallansatte) as antallansatte  from t_forvaltning_bronnoysund_enhethierarki where institusjonellsektorkode_kode is not null and aar = ?  group by institusjonellsektorkode_kode, institusjonellsektorkode_beskrivelse order by institusjonellsektorkode_kode\n ";
        List values = new ArrayList();
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setOrganisasjonsgruppe(((String) rad.get("institusjonellsektorkode_kode")));
            dep.setOrganisasjonsgruppebeskrivelse((String) rad.get("institusjonellsektorkode_beskrivelse"));
            //dep.setAntall((Integer) rad.get("antall"));
            dep.setAntall((int) ((long)rad.get("antall")));
            dep.setAar(valgtAar);
            //dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            dep.setAntallAnsatte((int) ((long)rad.get("antallansatte")));
            enheter.add(dep);
        }
        return enheter;
    }

    /*Forretningskommuneskode*/
    public List<Enhet> getForretningskommuneskode(Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "\n" +
                "select count(*) as antall, forretningsadresse_kommune, forretningsadresse_kommunenr, sum(antallansatte) as antallansatte   from t_forvaltning_bronnoysund_enhethierarki where aar = ? group by  forretningsadresse_kommune, forretningsadresse_kommunenr order by forretningsadresse_kommune ";
        List values = new ArrayList();
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            if(rad.get("forretningsadresse_kommune")==null){
                dep.setOrganisasjonsgruppe("Ikke spesifisert");
                dep.setOrganisasjonsgruppebeskrivelse(String.valueOf(rad.get("forretningsadresse_kommunenr")));
                }
                else {
                dep.setOrganisasjonsgruppe(((String) rad.get("forretningsadresse_kommune")));
                dep.setOrganisasjonsgruppebeskrivelse(String.valueOf(rad.get("forretningsadresse_kommunenr")));
            }
            //dep.setAntall((Integer) rad.get("antall"));
            dep.setAntall((int) ((long)rad.get("antall")));
            dep.setAar(valgtAar);
            //dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            dep.setAntallAnsatte((int) ((long)rad.get("antallansatte")));
            enheter.add(dep);
        }
        return enheter;
    }



    /****************************** statistikk on søk side detaljer *****************************/

    /*ORGANISASJONSFORM*/
    public List<Enhet> getOrganisasjonsformgruppe(String orggruppekode, Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct * from t_forvaltning_bronnoysund_enhethierarki where organisasjonsform like ? and aar=? order by navn  ";
        List values = new ArrayList();
        values.add(orggruppekode);
        values.add(valgtAar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setLangtNavn((String) rad.get("navn"));
            dep.setOrgnummer((Integer) rad.get("idnum"));
            dep.setAar(valgtAar);
            dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            enheter.add(dep);
        }
        return enheter;
    }

    /*NÆRINGSKODE*/
    public List<Enhet> getNaeringskodegruppe(String orggruppekode, Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct * from t_forvaltning_bronnoysund_enhethierarki where naringskode_1 like ? and aar= ? order by navn ";
        sqlCB.setConnection(this.conn);
        List values = new ArrayList();
        values.add(orggruppekode);
        values.add(valgtAar);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setLangtNavn((String) rad.get("navn"));
            dep.setOrgnummer((Integer) rad.get("idnum"));
            dep.setAar(valgtAar);
            dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            enheter.add(dep);
        }
        return enheter;
    }

    /*SEKTORKODE*/
    public List<Enhet> getSektorkodegruppe(String orggruppekode, Integer valgtAar) throws SQLException {
        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = " select distinct * from t_forvaltning_bronnoysund_enhethierarki where institusjonellsektorkode_kode =? and aar= ? order by navn ";
        sqlCB.setConnection(this.conn);
        List values = new ArrayList();
        values.add(orggruppekode);
        values.add(valgtAar);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setLangtNavn((String) rad.get("navn"));
            dep.setOrgnummer((Integer) rad.get("idnum"));
            dep.setAar(valgtAar);
            dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            enheter.add(dep);
        }
        return enheter;
    }

    /*Forretningskommuneskode2 */
    public List<Enhet> getForretningskommunekodegruppe(String orggruppekode, Integer valgtAar) throws SQLException {

        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct * from t_forvaltning_bronnoysund_enhethierarki where forretningsadresse_kommunenr::text like ? and aar= ? order by navn ";
        sqlCB.setConnection(this.conn);
        List values = new ArrayList();
        values.add(orggruppekode);
        values.add(valgtAar);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setLangtNavn((String) rad.get("navn"));
            dep.setOrgnummer((Integer) rad.get("idnum"));
            dep.setAar(valgtAar);
            dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            dep.setOrganisasjonsgruppe(((String) rad.get("organisasjonsform")));
            if(rad.get("organisasjonsform").equals("ORGL")){dep.setOrganisasjonsgruppebeskrivelse("Organisasjonsledd");}
            if(rad.get("organisasjonsform").equals("BEDR")){dep.setOrganisasjonsgruppebeskrivelse("Bedrift");}
            if(rad.get("organisasjonsform").equals("STAT")){dep.setOrganisasjonsgruppebeskrivelse("Staten");}
            if(rad.get("organisasjonsform").equals("STI")){dep.setOrganisasjonsgruppebeskrivelse("Stiftelse");}
            if(rad.get("organisasjonsform").equals("SÆR")){dep.setOrganisasjonsgruppebeskrivelse("Annet foretak ifølge særskilt lov");}
            if(rad.get("organisasjonsform").equals("AS")){dep.setOrganisasjonsgruppebeskrivelse("Aksjeselskap");}
            if(rad.get("organisasjonsform").equals("FLI")){dep.setOrganisasjonsgruppebeskrivelse("Forening/lag/innretning");}
            if(rad.get("organisasjonsform").equals("KIRK")){dep.setOrganisasjonsgruppebeskrivelse("Den norske kirke");}
            if(rad.get("organisasjonsform").equals("SF")){dep.setOrganisasjonsgruppebeskrivelse("Statsforetak");}
            enheter.add(dep);
        }
        return enheter;
    }


    /*SEKTORKODE*/
    public List<Enhet> getOrgArbeidsyrkekodegruppe(Integer yrkekode, Integer valgtAar) throws SQLException {
        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = " select distinct t_forvaltning_bronnoysund_arb_yrke.arb_yrke, t_forvaltning_bronnoysund_arb_yrke.organisasjonsnummer, navn   \n" +
                "from t_forvaltning_bronnoysund_arb_yrke \n" +
                "inner join t_forvaltning_bronnoysund_enhethierarki on t_forvaltning_bronnoysund_arb_yrke.organisasjonsnummer = t_forvaltning_bronnoysund_enhethierarki.organisasjonsnummer\n" +
                "where t_forvaltning_bronnoysund_arb_yrke.arb_yrke = ? and t_forvaltning_bronnoysund_enhethierarki.aar = ? and t_forvaltning_bronnoysund_arb_yrke.aar = ? order by navn ";
        sqlCB.setConnection(this.conn);
        List values = new ArrayList();
        values.add(yrkekode);
        values.add(valgtAar);
        values.add(valgtAar);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setOrgnummer((Integer) rad.get("organisasjonsnummer"));
            dep.setLangtNavn((String) rad.get("navn"));
            dep.setAar(valgtAar);
            enheter.add(dep);
        }
        return enheter;
    }


    /*Department list*/
    public List<Enhet> getDepartmentlist(Integer valgtAar) throws SQLException {
        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = " select * from t_forvaltning_bronnoysund_enhethierarki where overordnet_idnum = 0 and aar=? order by navn ";
        sqlCB.setConnection(this.conn);
        List values = new ArrayList();
        values.add(valgtAar);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            dep.setLangtNavn((String) rad.get("navn"));
            dep.setOrgnummer((Integer) rad.get("idnum"));
            enheter.add(dep);
        }
        return enheter;
    }

    /*år list*/
    public List<Enhet> getRegistrertaar() throws SQLException {
        List<Enhet> enheter = new ArrayList<Enhet>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = " \n" +
               /* "select distinct YEAR(registreringsdato) as aar from t_forvaltning_bronnoysund_enhethierarki order by YEAR(registreringsdato) ";*/
        "select distinct DATE_PART('year', registreringsdato::date) as aar from t_forvaltning_bronnoysund_enhethierarki\n" +
                " order by DATE_PART('year', registreringsdato::date) ";
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhet dep = new Enhet();
            /*dep.setOrgnummer((Integer) rad.get("aar"));*/
            double dnum = (double) rad.get("aar");
            dep.setOrgnummer((int) dnum);
            enheter.add(dep);
        }
        return enheter;
    }

    /*Søk basert på Department og år*/
    public List<Enhetsregisteret> getAntallDepartementsenhet(String sokefelt, List<Integer> valg, Integer valgtAar) throws SQLException {
        List<Enhetsregisteret> enheter = new ArrayList<Enhetsregisteret>();
        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        //String sqlSelect = " select * from a_enheter_i_hirarchi where YEAR(registreringsdato) >= ? and YEAR(registreringsdato) <= ? ";
/*
        String sqlSelect2 = " \n" +
                "with cte as (\n" +
                "    select distinct \n" +
                "    ff.idnum, ff.navn, ff.overordnetEnhet,ff.registreringsdato, ff.organisasjonsform, ff.institusjonellsektorkode_kode, ff.institusjonellsektorkode_beskrivelse,\n" +
                "    ff.naringskode_1, ff.naringskode_1_beskrivelse, ff.sst_antall_menn, ff.sst_antall_kvinner,ff.level_1_navn,ff.level_2_navn,ff.level_3_navn,ff.level_4_navn, ff.forvaltningsidnum \n" +
                "    from    \n" +
                " t_forvaltning_bronnoysund_enhethierarki ff\n";
        if(valg.size()>0) {
            sqlSelect2 +=  " where ff.level_1 IN ( ";
            for (int i = 0; i < valg.size(); i++){
                sqlSelect2 +=  valg.get(i);
                sqlSelect2 +=  " , ";
            }
            sqlSelect2 +=  " 999999999 ) ";
        }
        sqlSelect2 +=
                "    union all\n" +
                        "    select  child.idnum, child.navn, child.overordnetEnhet,child.registreringsdato, child.organisasjonsform, child.institusjonellsektorkode_kode, child.institusjonellsektorkode_beskrivelse,\n" +
                        "    child.naringskode_1, child.naringskode_1_beskrivelse, child.sst_antall_menn, child.sst_antall_kvinner,child.level_1_navn,child.level_2_navn,child.level_3_navn,child.level_4_navn, child.forvaltningsidnum \n" +
                        "     \n" +
                        "    from    t_forvaltning_bronnoysund_enhethierarki child\n" +
                        "     join    cte overordnetEnhet\n" +
                        "    on      overordnetEnhet.idnum = child.overordnetEnhet\n" +
                        ")\n" +
                        "select distinct * from cte  order by idnum\n  ";

        // "select * from cte where  YEAR(registreringsdato) >= ? and YEAR(registreringsdato) <= ? order by navn\n  ";
        */

        String sqlSelect = "select  * from t_forvaltning_bronnoysund_enhethierarki ff \n";
        if(valg.size()>0) {
            sqlSelect +=  " where ff.level_1 IN ( ";
            for (int i = 0; i < valg.size(); i++){
                sqlSelect +=  valg.get(i);
                sqlSelect +=  " , ";
            }
            sqlSelect +=  " 999999999 ) ";
            sqlSelect +=  "  and  ( (COALESCE(lower(navn), '') LIKE ? OR COALESCE(lower(forretningsadresse_kommune), '') LIKE ? ) and aar = ?) ";
        }
        else{sqlSelect +=  "  where  ( (COALESCE(lower(navn), '') LIKE ? OR COALESCE(lower(forretningsadresse_kommune), '') LIKE ? ) and aar = ?) ";}
        sqlSelect +=  " order by ff.navn ";


        sqlCB.setConnection(this.conn);
        List values = new ArrayList();
        values.add("%"+sokefelt.toLowerCase()+"%");
        values.add("%"+sokefelt.toLowerCase()+"%");
        values.add(valgtAar);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            Enhetsregisteret dep = new Enhetsregisteret();
            dep.setIdnum((Integer) rad.get("idnum"));
            dep.setNavn((String) rad.get("navn"));
            dep.setOverordnetEnhet((Integer) rad.get("overordnetEnhet"));
            dep.setOrganisasjonsform((String) rad.get("organisasjonsform"));
            dep.setAntallAnsatte((Integer) rad.get("antallansatte"));
            /*
            dep.setAntallAnsatte_menn((Integer) rad.get("sst_antall_menn"));
            dep.setAntallAnsatte_kvinner((Integer) rad.get("sst_antall_kvinner"));
            */
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(((java.sql.Timestamp) rad.get("registreringsdato")).getTime()));
            int year = cal.get(Calendar.YEAR);
            dep.setYear(Integer.valueOf((Integer) year));
            if((String) rad.get("naringskode_1")!=null) {dep.setNaringskode((String) rad.get("naringskode_1") + "(" + (String) rad.get("naringskode_1_beskrivelse") + ")");
            }
            if((String) rad.get("institusjonellsektorkode_kode")!=null) {dep.setSektorkode((String) rad.get("institusjonellsektorkode_kode") + "(" + (String) rad.get("institusjonellsektorkode_beskrivelse") + ")");}
            dep.setLevel_1((String) rad.get("level_1_navn"));
            dep.setLevel_2((String) rad.get("level_2_navn"));
            dep.setLevel_3((String) rad.get("level_3_navn"));
            dep.setLevel_4((String) rad.get("level_4_navn"));
            dep.setNsd_idnum((Integer) rad.get("forvaltningsidnum"));
            dep.setForretningskommune((String) rad.get("forretningsadresse_kommune"));
            dep.setForretningsfulladresse((String) rad.get("forretningsadresse_adresse") + ", " +  (String) rad.get("forretningsadresse_postnummer") + " " + (String) rad.get("forretningsadresse_poststed")  );
            //forretningsadresse_adresse   forretningsadresse_postnummer  forretningsadresse_poststed

            enheter.add(dep);
        }
        return enheter;
    }


    public Integer getOrgnr(Integer forvaltningsidnum, Integer aar) throws SQLException {

        Integer enheter = null;

        EnhetHierarkiLogikk log = new EnhetHierarkiLogikk();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        //String sqlSelect = "select distinct * from a_enheter_i_hirarchi where overordnet_idnum = 0 order by navn ";
        String sqlSelect = "select distinct idnum  from t_forvaltning_bronnoysund_enhethierarki where forvaltningsidnum = ? and aar = ? limit 1  ";
        List values = new ArrayList();
        values.add(forvaltningsidnum);
        values.add(aar);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        for (SortedMap rad : rader) {
            enheter = (Integer) rad.get("idnum");
        }
           return enheter;
    }


}
