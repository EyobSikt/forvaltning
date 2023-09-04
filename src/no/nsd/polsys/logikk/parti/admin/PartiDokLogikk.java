package no.nsd.polsys.logikk.parti.admin;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.parti.admin.PartiDok;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class PartiDokLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;


    public PartiDokLogikk() {
    }



    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }



    /**
     * Returnerer alle relasjoner til en gitt endringsid.
     * @param id - endringsid
     * @return
     * @throws java.sql.SQLException
     */
    public List<Integer> getRelasjoner(Integer id) throws SQLException {
        // returneres fra denne metoden.
        List<Integer> relasjoner = new ArrayList<Integer>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "select * from t_forvaltning_relasjon where endringsid = ?";
        // SQL-parametere.
        List values = new ArrayList();

        values.add(id);

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }

        for (SortedMap rad : rader) {
            Integer idnum = (Integer) rad.get("idnum");
            relasjoner.add(idnum);
        }
        return relasjoner;
    }

    /**
     * Slett relasjoner for gitt endringsid.
     * @param id - endringsid.
     * @throws java.sql.SQLException
     */
    public void slettRelasjoner(Integer id) throws SQLException {

        SqlCommandBean sqlCB = new SqlCommandBean();

        String sqlSelect = "delete from t_forvaltning_relasjon where endringsid = ?";
        // SQL-parametere.
        List values = new ArrayList();

        values.add(id);

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }

    /**
     * Registrerer nye idnums for gitt endringsid i relasjon.
     * @param endringsid
     * @param idnums
     * @throws java.sql.SQLException
     */
    public void registrerNyeRelasjoner(Integer endringsid, List<Integer> idnums) throws SQLException {
        PreparedStatement st = null;
        String sql = "insert into t_forvaltning_relasjon (endringsid, idnum) values (?, ?)";

        try {
            st = this.conn.prepareStatement(sql);
            for (Integer i : idnums) {
                st.setInt(1, endringsid);
                st.setInt(2, i);
                st.executeUpdate();
            }
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }




    /**
     * Returnerer alle (andre) relasjonenheter.
     * @return
     * @throws java.sql.SQLException
     */
    public List<PartiDok> getAlleRelasjonAndreEnheter() throws SQLException {
        // returneres fra denne metoden.
        List<PartiDok> relasjonenheter = new ArrayList<PartiDok>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "SELECT TOP 200  t_parti_dokument_info.tittel, t_parti_dokument_info.dok_nr ,  t_parti_dokument_info.dokumenttype_id,\n" +
                "                     t_parti_dokumenttyper.fleirtaltekst AS Dokumenttypefleirtaltekst, t_parti_dokument_info.partikode, t_felles_partinavn.eintaltekst AS PartiEintaltekst, \n" +
                "                      t_felles_partinavn.eintaltekst_forkorting AS PartiEintaltekst_forkorting, t_parti_dokument_info.aar\n" +
                "FROM    t_parti_dokument_info \n" +
                "LEFT OUTER JOIN t_parti_dokumenttyper ON t_parti_dokument_info.dokumenttype_id = t_parti_dokumenttyper.kode \n" +
                "LEFT OUTER JOIN t_felles_partinavn ON t_parti_dokument_info.partikode = t_felles_partinavn.kode \n" +
                "WHERE t_parti_dokument_info.dok_nr NOT IN (5340, 5372,5572,  5940, 5963, 6253, 6259, 6908 , 7217,7221, 7404, 7405, 7497, 7499, 7516, 7517, 8346, 8620, 8634, 8648, 8911, 8912, 8913, 8914, 9130,\n" +
                "9131,9132,9133,9134,9135,9136,9137,9138,9139,9140,9141,9142,9143,9144,9145, 9146,9166, 9167, 9375, 9447, 9448, 9480, 9550, 9649, 9650, 9651, 9652)\n" +
                "AND t_parti_dokumenttyper.fleirtaltekst  IS NOT null\n" +
                "ORDER BY t_parti_dokument_info.dok_nr desc";

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }

        for (SortedMap rad : rader) {
            PartiDok e = new PartiDok();
            e.setIdnum((Integer) rad.get("dok_nr"));
            e.setTittel((String) rad.get("tittel"));
            e.setDoktype((String) rad.get("Dokumenttypefleirtaltekst"));
             e.setPartinavn((String) rad.get("PartiEintaltekst"));
            e.setAar((Integer) rad.get("aar"));
             e.setDoktypeid((Integer) rad.get("dokumenttype_id"));
             e.setPartikode((Integer) rad.get("partikode"));
            relasjonenheter.add(e);
        }
        return relasjonenheter;
    }


    /**
     * Returnerer alle relasjoner - andre - til en gitt endringsid.
     * @param id - endringsid
     * @return
     * @throws java.sql.SQLException
     */
    public List<Integer> getRelasjonerAndre(Integer id) throws SQLException {
        // returneres fra denne metoden.
        List<Integer> relasjoner = new ArrayList<Integer>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "select * from t_forvaltning_relasjon_andre where endringsid = ?";
        // SQL-parametere.
        List values = new ArrayList();

        values.add(id);

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }

        for (SortedMap rad : rader) {
            Integer idAndre = (Integer) rad.get("id");
            relasjoner.add(idAndre);
        }
        return relasjoner;
    }

    /**
     * Slett relasjoner for gitt endringsid.
     * @param id - endringsid.
     * @throws java.sql.SQLException
     */
    public void slettRelasjonerAndre(Integer id) throws SQLException {

        SqlCommandBean sqlCB = new SqlCommandBean();

        String sqlSelect = "delete from t_forvaltning_relasjon_andre where endringsid = ?";
        // SQL-parametere.
        List values = new ArrayList();

        values.add(id);

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }


    /**
     * Registrerer nye ids (andre-enheter) for gitt endringsid i relasjon-andre
     * @param endringsid
     * @param ids
     * @throws java.sql.SQLException
     */
    public void registrerNyeRelasjonerAndre(Integer endringsid, List<Integer> ids) throws SQLException {
        PreparedStatement st = null;
        String sql = "insert into t_forvaltning_relasjon_andre (endringsid, id) values (?, ?)";

        try {
            st = this.conn.prepareStatement(sql);
            for (Integer i : ids) {
                st.setInt(1, endringsid);
                st.setInt(2, i);
                st.executeUpdate();
            }
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }




    public void registrerNyRelasjonAndreEnhet(Integer doknr, String tittel, Integer doktypeid, Integer  partiid, Integer aar) throws SQLException {

        SqlCommandBean sqlCB = new SqlCommandBean();

        String sqlSelect = "insert into t_parti_dokument_info (dok_nr, tittel, aar, dokumenttype_id, partikode, Prosatittel, Nynorsk) values (?, ?, ?, ?, ?,?,?)";
        // SQL-parametere.
        List values = new ArrayList();

        values.add(doknr);
        values.add(tittel);
        values.add(aar);
        values.add(doktypeid);
        values.add(partiid);
        values.add(0);
        values.add(0);


        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }


   
    public void oppdaterRelasjonAndreEnhet(Integer id, String tittel, Integer doktypeid, Integer  partiid, Integer aar) throws SQLException {

        SqlCommandBean sqlCB = new SqlCommandBean();
    
        String sqlSelect = "update t_parti_dokument_info set tittel = ?, aar = ?, dokumenttype_id=?, partikode=?  where dok_nr = ?";
        // SQL-parametere.
        List values = new ArrayList();

        values.add(tittel);
        values.add(aar);
        values.add(doktypeid);
        values.add(partiid);
        values.add(id);

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }


     public List<PartiDok> getAllePartier() throws SQLException {
        List<PartiDok> partilist = new ArrayList<PartiDok>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT kode, eintaltekst, ENGeintaltekst,parti_bruksnavn,ENGparti_bruksnavn ,fleirtaltekst,eintalstekst_same,dokumentasjon,ENGdokumentasjon,eintaltekst_forkorting,ENGeintaltekst_forkorting,stortingsparti_45,fraksjonsmerknadsarkivet_forkorting \n" +
                "FROM t_felles_partinavn\n" +
                "ORDER BY eintaltekst";
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        if (rader == null || rader.length == 0) {
            return null;
        }
        for (SortedMap rad : rader) {
            PartiDok e = new PartiDok();
            e.setIdnum((Integer) rad.get("kode"));
            e.setPartinavn((String) rad.get("eintaltekst"));
            partilist.add(e);
        }
        return partilist;
    }



}
