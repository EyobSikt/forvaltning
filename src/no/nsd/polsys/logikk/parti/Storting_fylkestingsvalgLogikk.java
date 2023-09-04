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
public class Storting_fylkestingsvalgLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public Storting_fylkestingsvalgLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public Parti[] getPartiList(int aar_kode, int f_kode/*, int valgtype*/) throws Exception {
           String condition = "  WHERE  s.aar = ? and s.fylkesnummer=? AND s.aar BETWEEN p.fra AND p.til \n" +
                   "ORDER BY eintaltekst ";
          List values = new ArrayList();
         /* if(valgtype==1){values.add("%"+'T'+"%");}else if(valgtype==2){values.add("%"+'F'+"%");}
         values.add(aar_kode);
         if(valgtype==1){values.add("%"+'T'+"%");}else if(valgtype==2){values.add("%"+'F'+"%");}
        values.add(aar_kode);*/
         values.add(aar_kode);
         values.add(f_kode);
          return this.getPartiList(condition, values);
       }

    public Parti[] getPartiList(int aar_kode) throws Exception {
        String condition = " WHERE    s.aar = ? AND s.aar BETWEEN p.fra AND p.til \n" +
                "ORDER BY eintaltekst ";
        List values = new ArrayList();
        values.add(aar_kode);
        return this.getAllePartiList(condition, values);
    }

      public Parti[] getFylkeList(int aar_kode) throws Exception {
          /* String condition = " WHERE     (t_parti_partilister.aar = ?) \n" +
                   "ORDER BY t_felles_partinavn.eintaltekst ";*/
          String condition = " WHERE    s.aar = ? \n" +
                  "ORDER BY s.fylkesnummer ";
          List values = new ArrayList();
          values.add(aar_kode);
           return this.getFylkeList(condition, values);
       }

    /* public Parti[] getFylkeList(int aar_kode, int p_kode) throws Exception {
           String condition = " WHERE     (t_parti_partilister.aar = ?) AND (t_parti_partilister.parti_id = ?)\n" +
                   "ORDER BY t_felles_fylke.tekst_entall, t_parti_partilister.fylkesnummer, t_parti_partilister.parti_id ";
          List values = new ArrayList();
          values.add(aar_kode);
          values.add(p_kode);
           return this.getFylkeList(condition, values);
       }*/

    public Parti[] getNavnList(int aar_kode, int p_kode, int f_kode, int valgtype) throws Exception {
           String condition = " WHERE     (s.aar = ?) AND (s.parti_id = ? AND s.aar BETWEEN p.fra AND p.til) AND (f.kode =?)\n" +
                   "ORDER BY nummer ";
          List values = new ArrayList();
        if(valgtype==1){values.add("%"+'T'+"%");}else if(valgtype==2){values.add("%"+'F'+"%");}
        values.add(aar_kode);
        if(valgtype==1){values.add("%"+'T'+"%");}else if(valgtype==2){values.add("%"+'F'+"%");}
        values.add(aar_kode);
          values.add(aar_kode);
         values.add(p_kode);
          values.add(f_kode);
           return this.getNavnList(condition, values);
       }


/***************************************************************************************************
   Private metoder
  ***************************************************************************************************/
  private Parti[] getPartiList(String condition, List values) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        /*String sqlSelect = "SELECT     t_parti_partilister.parti_id as partikode, t_felles_partinavn.eintaltekst as partinavn_bm, t_parti_partilister.aar as valgaar,\n" +
                "                      t_felles_partinavn.eintaltekst_forkorting\n" +
                "FROM         t_parti_partilister INNER JOIN\n" +
                "                      t_felles_partinavn ON t_parti_partilister.parti_id = t_felles_partinavn.kode\n" +
                "GROUP BY t_parti_partilister.parti_id, t_felles_partinavn.eintaltekst, t_parti_partilister.aar,\n" +
                "                      t_felles_partinavn.eintaltekst_forkorting "*/
      String sqlSelect = "SELECT  distinct s.Valgtype, s.fylkesnummer, s.parti_id as partikode, " +
             /* "  CASE WHEN (SELECT COUNT(*) FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE ? AND ? BETWEEN aar_fra AND aar_til ) = 1 THEN\n" +
              "      (SELECT navn FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE ? AND ? BETWEEN aar_fra AND aar_til )\n" +
              "      ELSE p.eintaltekst\n" +
              "      END as eintaltekst, " +*/
              "     p.eintaltekst,   " +
              "      s.aar as valgaar\n" +
              "      FROM  t_parti_partilister s INNER JOIN\n" +
              "      t_felles_partinavn p ON s.parti_id = p.kode " +
              "      LEFT JOIN t_felles_fylke f ON f.kode = s.fylkesnummer  "
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
                partilist[i].setValgtype((Integer)rader[i].get("Valgtype"));
    }
        return partilist;
    }

    private Parti[] getAllePartiList(String condition, List values) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        /*String sqlSelect = "SELECT     t_parti_partilister.parti_id as partikode, t_felles_partinavn.eintaltekst as partinavn_bm, t_parti_partilister.aar as valgaar,\n" +
                "                      t_felles_partinavn.eintaltekst_forkorting\n" +
                "FROM         t_parti_partilister INNER JOIN\n" +
                "                      t_felles_partinavn ON t_parti_partilister.parti_id = t_felles_partinavn.kode\n" +
                "GROUP BY t_parti_partilister.parti_id, t_felles_partinavn.eintaltekst, t_parti_partilister.aar,\n" +
                "                      t_felles_partinavn.eintaltekst_forkorting "*/
        String sqlSelect = "SELECT  distinct  s.Valgtype, s.fylkesnummer, s.parti_id as partikode, p.eintaltekst, s.aar as valgaar\n" +
                "      FROM  t_parti_partilister s INNER JOIN\n" +
                "      t_felles_partinavn p ON s.parti_id = p.kode   "
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
            partilist[i].setValgtype((Integer)rader[i].get("Valgtype"));
        }
        return partilist;
    }


  private Parti[] getFylkeList(String condition, List values) throws Exception {
       Parti[] fylkelist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
       /* String sqlSelect = "SELECT  DISTINCT   t_parti_partilister.parti_id as partikode, t_felles_partinavn.eintaltekst as partinavn_bm, t_parti_partilister.fylkesnummer, t_parti_partilister.aar as valgaar,\n" +
                "                      t_parti_partilister.Valgtype, t_felles_fylke.kode, t_felles_fylke.tekst_entall\n" +
                "FROM         t_parti_partilister INNER JOIN\n" +
                "                      t_felles_partinavn ON t_parti_partilister.parti_id = t_felles_partinavn.kode INNER JOIN\n" +
                "                      t_felles_fylke ON t_parti_partilister.fylkesnummer = t_felles_fylke.kode "*/
      String sqlSelect = "SELECT  distinct s.Valgtype, s.fylkesnummer, f.tekst_entall, s.aar as valgaar, f.kode\n" +
              "                FROM  t_parti_partilister s INNER JOIN\n" +
              "                       t_felles_fylke f ON s.fylkesnummer = f.kode  "

                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        fylkelist = new Parti[rader.length];
    for (int i = 0; i < rader.length; i++) {
                fylkelist[i] = new Parti();
                fylkelist[i].setPartinavn((String)rader[i].get("tekst_entall"));
                fylkelist[i].setFylke_kode((Integer)rader[i].get("kode"));
                fylkelist[i].setParti_id((Integer)rader[i].get("fylkesnummer"));
                fylkelist[i].setAarstal((Integer)rader[i].get("valgaar"));
                fylkelist[i].setValgtype((Integer)rader[i].get("Valgtype"));
    }
        return fylkelist;
    }

  private Parti[] getNavnList(String condition, List values) throws Exception {
        Parti[] navnlist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        /*String sqlSelect = "SELECT     t_parti_partilister.fylkesnummer, t_parti_partilister.parti_id, t_felles_partinavn.eintaltekst as partinavn_bm, t_parti_partilister.nummer, t_parti_partilister.navn, t_parti_partilister.stilling, t_parti_partilister.bosted,\n" +
                "                      t_parti_partilister.aar, t_felles_fylke.kode, t_felles_fylke.tekst_entall\n" +
                "FROM         t_parti_partilister INNER JOIN\n" +
                "                      t_felles_fylke ON t_parti_partilister.fylkesnummer = t_felles_fylke.kode "  +
                " INNER JOIN   t_felles_partinavn ON t_parti_partilister.parti_id = t_felles_partinavn.kode "
                + (condition != null ? " " + condition : "");*/

      String sqlSelect = "SELECT     s.fylkesnummer, s.parti_id,  s.nummer, s.navn, s.stilling, s.bosted,\n" +
              "                      s.aar, f.kode, f.tekst_entall, \n" +
              "  CASE WHEN (SELECT COUNT(*) FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE ? AND ? BETWEEN aar_fra AND aar_til ) = 1 THEN\n" +
              "      (SELECT navn FROM t_valglistenavn WHERE kode=s.parti_id AND valgtype LIKE ? AND ? BETWEEN aar_fra AND aar_til )\n" +
              "      ELSE p.eintaltekst\n" +
              "      END as partinavn_bm " +
              "FROM         t_parti_partilister s INNER JOIN\n" +
              "                      t_felles_fylke f ON s.fylkesnummer = f.kode "  +
              " INNER JOIN   t_felles_partinavn p ON s.parti_id = p.kode "
              + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        navnlist = new Parti[rader.length];
    for (int i = 0; i < rader.length; i++) {
                navnlist[i] = new Parti();
                 navnlist[i].setNummer((Integer)rader[i].get("nummer"));
                navnlist[i].setNavn((String)rader[i].get("navn"));
                navnlist[i].setStilling((String)rader[i].get("stilling"));
                navnlist[i].setBosted((String)rader[i].get("bosted"));
                navnlist[i].setPartinavn((String)rader[i].get("partinavn_bm"));
                navnlist[i].setFylkenavn((String)rader[i].get("tekst_entall"));
    }
        return navnlist;
    }

    public Parti[] getSistevalgaar() throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = " select max(aar) as sistevalgaar  from t_parti_partilister ";
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

