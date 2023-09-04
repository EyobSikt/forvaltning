package no.nsd.polsys.logikk.parti;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.parti.Parti;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class PartiLogikk {

    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;

    public PartiLogikk() {
    }

      public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }

    // Lese metoder ------------------------------------------------------------------------------------

    public Parti[] getPartiList() throws Exception {
           String condition = " ORDER BY t_felles_partinavn.eintaltekst ";
           return this.getPartiList(condition, null);
       }

      public Parti[] getAllePartier(int p_kode) throws Exception {
           String condition = " WHERE t_felles_partinavn.kode = ?  \n" +
                   "                ORDER BY t_felles_partinavn.kode  ";
           List values = new ArrayList();
          values.add(p_kode);
           return this.getAllePartier(condition, values);
       }
      public Parti[] getPartibakgrunn(int p_kode) throws Exception {
           String condition = " WHERE kode = ? ";
          List values = new ArrayList();
          values.add(p_kode);
           return this.getPartibakgrunn(condition, values);
       }

    public Parti[] getEndringshistorie(int p_kode) throws Exception {
           String condition = " WHERE partikode = ? ORDER BY aarstal asc ";
          List values = new ArrayList();
          values.add(p_kode);
           return this.getEndringshistorie(condition, values);
       }

/***************************************************************************************************
 Private metoder
***************************************************************************************************/

    private Parti[] getPartiList(String condition, List values) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "select distinct t_parti_endringshistorie.partikode, t_felles_partinavn.eintaltekst,  t_parti_bakgrunn.kode from     t_felles_partinavn   \n" +
                "         INNER JOIN  t_parti_endringshistorie ON t_felles_partinavn.kode = t_parti_endringshistorie.partikode\n" +
                "         INNER JOIN t_parti_bakgrunn ON t_felles_partinavn.kode = t_parti_bakgrunn.kode  "
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
                partilist[i].setPartikode((Integer)rader[i].get("partikode"));
    }
        return partilist;
  }

     private Parti[] getPartibakgrunn(String condition, List values) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT     eintaltekst_nn, skipingstidspunkt, merknader, endringar, nedleggjingsaar, partihistorikk, valdeltaking, stortingsparti, kode\n" +
                "FROM         t_parti_bakgrunn "
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new Parti[rader.length];

    for (int i = 0; i < rader.length; i++) {
                partilist[i] = new Parti();
                partilist[i].setEintaltekst_nn((String)rader[i].get("eintaltekst_nn"));
        if(rader[i].get("stortingsparti")!=null){
                partilist[i].setStortingsparti((Integer)rader[i].get("stortingsparti"));
        }
                 partilist[i].setSkipingstidspunkt((String)rader[i].get("skipingstidspunkt"));
                 partilist[i].setNedleggjingsaar((String)rader[i].get("nedleggjingsaar"));
                 partilist[i].setValdeltaking((String)rader[i].get("valdeltaking"));
                partilist[i].setPartihistorikk((String)rader[i].get("partihistorikk"));
    }
        return partilist;
    } 

     private Parti[] getEndringshistorie(String condition, List values) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT    partikode, aarstal, maanad, dato, dokumentasjon\n" +
                "FROM      t_parti_endringshistorie "
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new Parti[rader.length];
    for (int i = 0; i < rader.length; i++) {
                partilist[i] = new Parti();
                partilist[i].setDokumentasjon((String)rader[i].get("dokumentasjon"));
                partilist[i].setAarstal((Integer)rader[i].get("aarstal"));
    }
        return partilist;
    }


     private Parti[] getAllePartier(String condition, List values) throws Exception {
        Parti[] partilist = null;
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "\n" +
                "SELECT distinct t_felles_partinavn.kode, t_felles_partinavn.eintaltekst  AS PartiEintaltekst, \n" +
                "                         t_felles_partinavn.eintaltekst_forkorting AS PartiEintaltekst_forkorting\n" +
                "                FROM   t_felles_partinavn  "

                  + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
           sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        partilist = new Parti[rader.length];

        for (int i = 0; i < rader.length; i++) {
            partilist[i] = new Parti();

              partilist[i].setPartinavn((String)rader[i].get("PartiEintaltekst"));

        }
        return partilist;
    }

    public List<Parti> getAllePartiHistorie() throws Exception {
        ArrayList<Parti> list = new ArrayList<Parti>();
        // SQL-sporring.
        String sqlSelect = " select distinct t_parti_endringshistorie.partikode, t_felles_partinavn.eintaltekst,  t_parti_bakgrunn.kode from     t_felles_partinavn   \n" +
                "         INNER JOIN  t_parti_endringshistorie ON t_felles_partinavn.kode = t_parti_endringshistorie.partikode\n" +
                "         INNER JOIN t_parti_bakgrunn ON t_felles_partinavn.kode = t_parti_bakgrunn.kode \n" +
                "         order by t_felles_partinavn.eintaltekst ";
        PreparedStatement sta = conn.prepareStatement(sqlSelect);
        ResultSet rs = sta.executeQuery();
       while(rs.next()) {
           Parti partihistorie = new Parti();
            partihistorie.setPartilinkkode(rs.getObject("partikode"));
           partihistorie.setPartinavn(rs.getString("eintaltekst"));
           list.add(partihistorie);
        }
        return list;
    }

    public List<Parti> getAlleParti() throws Exception {
           ArrayList<Parti> list = new ArrayList<Parti>();
           // SQL-sporring.
           String sqlSelect = " SELECT     distinct df.partikode, t_felles_partinavn.eintaltekst  AS PartiEintaltekst, \n" +
                   "                      t_felles_partinavn.eintaltekst_forkorting AS PartiEintaltekst_forkorting\n" +
                   "FROM   t_parti_dokument_info as df \n" +
                   "LEFT OUTER JOIN t_parti_dokumenttyper ON df.dokumenttype_id = t_parti_dokumenttyper.kode \n" +
                   "LEFT OUTER JOIN t_felles_partinavn ON df.partikode = t_felles_partinavn.kode \n" +
                   "WHERE df.dok_nr NOT IN (5340, 5372,5572,  5940, 5963, 6253, 6259, 6908,7217,7221, 7404, 7405, 7497, 7499, 7516, 7517, 8346, 8620, 8634, 8648, 8911, 8912, 8913, 8914, 9130,\n" +
                   "9131,9132,9133,9134,9135,9136,9137,9138,9139,9140,9141,9142,9143,9144,9145, 9146,9166, 9167, 9375, 9447, 9448, 9480, 9550, 9649, 9650, 9651, 9652)\n" +
                   "AND t_parti_dokumenttyper.fleirtaltekst  IS NOT null  ";
           PreparedStatement sta = conn.prepareStatement(sqlSelect);
           ResultSet rs = sta.executeQuery();
          while(rs.next()) {
              Parti parti = new Parti();
               parti.setPartikode2(rs.getObject("partikode"));
              parti.setPartinavn(rs.getString("PartiEintaltekst"));
              list.add(parti);
           }
           return list;
       }

    public List<Parti> getAlledokumenter() throws Exception {
           ArrayList<Parti> list = new ArrayList<Parti>();
           // SQL-sporring.
           String sqlSelect = " select  kode, fleirtaltekst from t_parti_dokumenttyper  ";
           PreparedStatement sta = conn.prepareStatement(sqlSelect);
           ResultSet rs = sta.executeQuery();
          while(rs.next()) {
              Parti dokumenttyper = new Parti();
               dokumenttyper.setDoktypekode(rs.getObject("kode"));
              dokumenttyper.setDoktypenavn(rs.getString("fleirtaltekst"));
              list.add(dokumenttyper);
           }
           return list;
       }

    public List<Parti> getAlleaarstall() throws Exception {
               ArrayList<Parti> list = new ArrayList<Parti>();
               // SQL-sporring.
               String sqlSelect = " SELECT distinct df.aar FROM  t_parti_dokument_info as df order by aar desc  ";
               PreparedStatement sta = conn.prepareStatement(sqlSelect);
               ResultSet rs = sta.executeQuery();
              while(rs.next()) {
                  Parti aarstall = new Parti();
                   //aarstall.setDoktypekode(rs.getObject("kode"));
                  aarstall.setAarstall(rs.getString("aar"));
                  list.add(aarstall);
               }
               return list;
           }

    public List<Parti> getSamsikDok() throws Exception {
               ArrayList<Parti> list = new ArrayList<Parti>();
               // SQL-sporring.
               String sqlSelect = " select dok_nr, samiskref from t_parti_fylke_lister_doknr_samiske where samisk = 1  ";
               PreparedStatement sta = conn.prepareStatement(sqlSelect);
               ResultSet rs = sta.executeQuery();
              while(rs.next()) {
                  Parti samiskdok = new Parti();
                   samiskdok.setDoktypekode(rs.getString("dok_nr"));
                  samiskdok.setDoktypenavn(rs.getString("samiskref"));
                  list.add(samiskdok);
               }
               return list;
           }
}

