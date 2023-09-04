package no.nsd.polsys.logikk.parti;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.parti.Parti;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: et
 * Date: 24.nov.2010
 * Time: 12:27:57
 * To change this template use File | Settings | File Templates.
 */
public class PartisametingsvalgLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public PartisametingsvalgLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Parti[] getPartiList(int aar_kode, int f_kode) throws Exception {

        String kodenavn = null;
      /*  if(aar_kode >=2009){kodenavn= "kode_2009"; }else{kodenavn="kode";}
        String harValglistenavn = " kode=s.parti_id AND valgtype LIKE '%A%' AND ? BETWEEN aar_fra AND aar_til AND f. "+kodenavn+" BETWEEN distrikt_fra AND distrikt_til";*/
        String condition = " WHERE    s.aar = ? AND s.fylkesnummer=? AND s.aar BETWEEN p.fra AND p.til \n" +
                   "ORDER BY eintaltekst ";
          List values = new ArrayList();
          /*values.add(aar_kode);
          values.add(aar_kode);*/
        values.add(aar_kode);
        values.add(f_kode);
           return this.getPartiList(condition, values);
       }


    public Parti[] getPartiList(int aar_kode) throws Exception {
        /*String kodenavn = null;
        if(aar_kode >=2009){kodenavn= "kode_2009"; }else{kodenavn="kode";}
        String harValglistenavn = " kode=s.parti_id AND valgtype LIKE '%A%' AND ? BETWEEN aar_fra AND aar_til AND f. "+kodenavn+" BETWEEN distrikt_fra AND distrikt_til";*/
        String condition = " WHERE    s.aar = ?  AND s.aar BETWEEN p.fra AND p.til \n" +
                "ORDER BY eintaltekst ";
        List values = new ArrayList();
       /* values.add(aar_kode);
        values.add(aar_kode);*/
        values.add(aar_kode);
        return this.getPartiList(condition, values);
    }


      public Parti[] getFylkeList(int aar_kode) throws Exception {
          /* String condition = " WHERE     (t_parti_partilister_samiske.aar = ?) \n" +
                   "ORDER BY t_felles_partinavn.eintaltekst ";*/
          String condition = " WHERE   s.aar = ?  \n" +
                   "ORDER BY f.kode ";
          List values = new ArrayList();
          values.add(aar_kode);
           return this.getFylkeList(condition, values);
       }

     /*public Parti[] getFylkeList(int aar_kode, int p_kode) throws Exception {
           String condition = " WHERE     (t_parti_partilister_samiske.aar = ?) AND (t_parti_partilister_samiske.parti_id = ?)\n" +
                   "ORDER BY t_parti_partilister_samiske.fylkesnummer, t_parti_partilister_samiske.parti_id ";
          List values = new ArrayList();
          values.add(aar_kode);
          values.add(p_kode);
           return this.getFylkeList(condition, values);
       }*/

    public Parti[] getNavnList(int aar_kode, int p_kode, int f_kode) throws Exception {

        String kodenavn = null;
        if(aar_kode >=2009){kodenavn= "kode_2009"; }else{kodenavn="kode";}
        String harValglistenavn = " kode=s.parti_id AND valgtype LIKE '%A%' AND ? BETWEEN aar_fra AND aar_til AND f. "+kodenavn+" BETWEEN distrikt_fra AND distrikt_til";

           String condition = " WHERE     (s.aar = ?) AND (s.parti_id = ?  AND s.aar BETWEEN p.fra AND p.til ) AND (f.kode =?)\n" +
                   "ORDER BY nummer ";
          List values = new ArrayList();
          values.add(aar_kode);
        values.add(aar_kode);
        values.add(aar_kode);
         values.add(p_kode);
          values.add(f_kode);
           return this.getNavnList(condition, values, harValglistenavn);
       }
   /* public Parti[] getNavnList(int aar_kode, int p_kode, int f_kode) throws Exception {
        String condition = " WHERE     (s.aar = ?) AND (s.parti_id = ?) AND (f.kode =?)\n" +
                "ORDER BY nummer ";
        List values = new ArrayList();
        values.add(aar_kode);
        values.add(aar_kode);
        values.add(aar_kode);
        values.add(p_kode);
        values.add(f_kode);
        return this.getNavnList(condition, values);
    }*/


/***************************************************************************************************
 Private metoder
***************************************************************************************************/
    private Parti[] getPartiList(String condition, List values/*, String harValglistenavn */) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
       /* String x = "kode_2009";*/
        SortedMap[] rader = null;
       /* String sqlSelect = "SELECT  DISTINCT t_parti_partilister_samiske.fylkesnummer,   t_parti_partilister_samiske.parti_id as partikode, t_felles_partinavn.eintaltekst, t_parti_partilister_samiske.aar as valgaar\n" +

                "FROM         t_parti_partilister_samiske INNER JOIN\n" +
                "                      t_felles_partinavn ON t_parti_partilister_samiske.parti_id = t_felles_partinavn.kode \n"*/

                //"GROUP BY t_parti_partilister_samiske.parti_id, t_felles_partinavn.eintaltekst, t_parti_partilister_samiske.aar\n"

        String sqlSelect = "SELECT DISTINCT  s.fylkesnummer, \n" +
                "        s.parti_id as partikode,\n" +
               /* "         CASE\n" +
                "    WHEN (SELECT COUNT(*) FROM t_valglistenavn WHERE "+harValglistenavn+") = 1 THEN               \n" +
                "        (SELECT navn FROM t_valglistenavn WHERE "+harValglistenavn+")\n" +
                "    ELSE p.eintaltekst\n" +
                "END as eintaltekst,\n" +*/
               "  p.eintaltekst, " +
                "        s.aar as valgaar\n" +
                "    FROM t_parti_partilister_samiske s\n" +
                "        INNER JOIN t_felles_partinavn p ON s.parti_id = p.kode   \n" +
                "        LEFT JOIN t_felles_fylke_samiske f ON f.kode = s.fylkesnummer \n"

                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new Parti[rader.length];
    for (int i = 0; i < rader.length; i++) {
                partilist[i] = new Parti();
                partilist[i].setPartinavn((String)rader[i].get("eintaltekst"));
               partilist[i].setAarstal((Integer)rader[i].get("valgaar"));
               partilist[i].setPartikode((Integer)rader[i].get("partikode"));
                partilist[i].setFylke_kode((Integer)rader[i].get("fylkesnummer"));
    }
        return partilist;
    }

     private Parti[] getFylkeList(String condition, List values) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
      /*  String sqlSelect = "SELECT  DISTINCT   t_parti_partilister_samiske.parti_id as partikode, t_felles_partinavn.eintaltekst, t_parti_partilister_samiske.fylkesnummer, t_parti_partilister_samiske.aar as valgaar,\n" +
                "                      t_parti_partilister_samiske.valgtype, t_felles_fylke_samiske.kode_2009, t_felles_fylke_samiske.eintaltekst, t_felles_fylke_samiske.kode\n" +
                "FROM         t_parti_partilister_samiske INNER JOIN\n" +
                "                      t_felles_partinavn ON t_parti_partilister_samiske.parti_id = t_felles_partinavn.kode INNER JOIN\n" +
                "                      t_felles_fylke_samiske ON t_parti_partilister_samiske.fylkesnummer = t_felles_fylke_samiske.kode  "*/

         String sqlSelect = "SELECT  distinct s.fylkesnummer, f.eintaltekst, s.aar as valgaar, f.kode, f.kode_2009\n" +
                 "                FROM  t_parti_partilister_samiske s INNER JOIN\n" +
                 "                       t_felles_fylke_samiske f ON s.fylkesnummer = f.kode  "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new Parti[rader.length];
    for (int i = 0; i < rader.length; i++) {
                partilist[i] = new Parti();
                partilist[i].setPartinavn((String)rader[i].get("eintaltekst"));
                partilist[i].setParti_id((Integer)rader[i].get("fylkesnummer"));
                partilist[i].setFylke_kode((Integer)rader[i].get("kode"));
                 partilist[i].setAarstal((Integer)rader[i].get("valgaar"));
                /*if(rader[i].get("valgaar").equals(2009) ){*/
                if((Integer)rader[i].get("valgaar") >=2009){
                partilist[i].setNummer((Integer)rader[i].get("kode_2009"));
                }
               else {
                    partilist[i].setNummer((Integer)rader[i].get("kode"));
                }
    }
        return partilist;
    }

     private Parti[] getNavnList(String condition, List values, String harValglistenavn) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
       /* String sqlSelect = "SELECT     t_parti_partilister_samiske.fylkesnummer, t_parti_partilister_samiske.parti_id, t_felles_partinavn.eintaltekst as partieintaltekst, t_parti_partilister_samiske.nummer, t_parti_partilister_samiske.navn, t_parti_partilister_samiske.stilling, t_parti_partilister_samiske.bosted,\n" +
                "                      t_parti_partilister_samiske.aar, t_felles_fylke_samiske.eintaltekst\n" +
                "FROM         t_parti_partilister_samiske INNER JOIN\n" +
                "                      t_felles_fylke_samiske ON t_parti_partilister_samiske.fylkesnummer = t_felles_fylke_samiske.kode " +
                "INNER JOIN   t_felles_partinavn ON t_parti_partilister_samiske.parti_id = t_felles_partinavn.kode "*/

     /*   String sqlSelect = "SELECT s.fylkesnummer, s.parti_id, s.nummer, s.navn, s.stilling, s.bosted, s.aar, f.eintaltekst,\n" +
                "\t   CASE WHEN (SELECT COUNT(*) FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE '%A%' AND ? BETWEEN aar_fra AND aar_til AND f.kode_2009 BETWEEN distrikt_fra AND distrikt_til) = 1 THEN\n" +
                "       (SELECT navn FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE '%A%' AND ? BETWEEN aar_fra AND aar_til AND f.kode_2009 BETWEEN distrikt_fra AND distrikt_til)\n" +
                "       ELSE p.eintaltekst\n" +
                "       END as partieintaltekst\n" +
                "       FROM  t_parti_partilister_samiske s \n" +
                "\t   INNER JOIN t_felles_fylke_samiske f ON s.fylkesnummer = f.kode \n" +
                "       INNER JOIN t_felles_partinavn p ON s.parti_id = p.kode "

                + (condition != null ? " " + condition : "");*/

         String sqlSelect = "SELECT s.fylkesnummer, s.parti_id, s.nummer, s.navn, s.stilling, s.bosted, s.aar, f.eintaltekst,\n" +

                /* "\t   CASE WHEN (SELECT COUNT(*) FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE '%A%' AND ? BETWEEN aar_fra AND aar_til AND f.kode_2009 BETWEEN distrikt_fra AND distrikt_til) = 1 THEN\n" +
                 "       (SELECT navn FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE '%A%' AND ? BETWEEN aar_fra AND aar_til AND f.kode_2009 BETWEEN distrikt_fra AND distrikt_til)\n" +
                 "       ELSE p.eintaltekst\n" +
                 "       END as partieintaltekst\n" +*/

                 "         CASE\n" +
                 "    WHEN (SELECT COUNT(*) FROM t_valglistenavn WHERE "+harValglistenavn+") = 1 THEN               \n" +
                 "        (SELECT navn FROM t_valglistenavn WHERE "+harValglistenavn+")\n" +
                 "    ELSE p.eintaltekst\n" +
                 "END as partieintaltekst \n" +


                 "       FROM  t_parti_partilister_samiske s \n" +
                 "\t   INNER JOIN t_felles_fylke_samiske f ON s.fylkesnummer = f.kode \n" +
                 "       INNER JOIN t_felles_partinavn p ON s.parti_id = p.kode "

                 + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new Parti[rader.length];

        for (int i = 0; i < rader.length; i++) {
            partilist[i] = new Parti();
            partilist[i].setNummer((Integer)rader[i].get("nummer"));
            partilist[i].setNavn((String)rader[i].get("navn"));
            partilist[i].setStilling((String)rader[i].get("stilling"));
            partilist[i].setBosted((String)rader[i].get("bosted"));
            partilist[i].setPartinavn((String)rader[i].get("partieintaltekst"));
            partilist[i].setFylkenavn((String)rader[i].get("eintaltekst"));
        }
        return partilist;
    }

    public Parti[] getSistevalgaar() throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = " select max(aar) as sistevalgaar  from t_parti_partilister_samiske ";
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new Parti[rader.length];
        for (int i = 0; i < rader.length; i++) {
            partilist[i] = new Parti();
            partilist[i].setAarstal((Integer)rader[i].get("sistevalgaar"));
        }
        return partilist;
    }


}

