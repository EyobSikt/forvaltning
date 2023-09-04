package no.nsd.polsys.logikk.admin.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.admin.regjering.Personinfo;
import no.nsd.polsys.modell.admin.regjering.Personnavn;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;


public class StatsradNavnListLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;


    public StatsradNavnListLogikk() {
    }



    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }



    /**
     * Returnerer alle (andre) relasjonenheter.
     * @return
     * @throws java.sql.SQLException
     */
    public List<Personnavn> getAlleStatsrader() throws SQLException {
        // returneres fra denne metoden.
        List<Personnavn> person = new ArrayList<Personnavn>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "select distinct Person, Norske_politikere_Fornavn, Norske_politikere_Navn, Foedt, doedsaar, Parti_ved_start from Norske_statsraader_ny\n" +
                "order by Norske_politikere_Navn, Norske_politikere_Fornavn ";

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }

        for (SortedMap rad : rader) {
            Personnavn e = new Personnavn();
            e.setPersonkode((Integer) rad.get("Person"));
            e.setEtternavn((String) rad.get("Norske_politikere_Navn"));
            e.setFornavn((String) rad.get("Norske_politikere_Fornavn"));
             e.setFoedtaar((Integer) rad.get("Foedt"));
             e.setDoedsaar((Integer) rad.get("doedsaar"));


            String partinavn=null;
            PreparedStatement stmt2 = conn.prepareStatement("SELECT distinct kode, eintaltekst from t_felles_partinavn where kode = ?");
            stmt2.setInt(1, (Integer) rad.get("Parti_ved_start"));
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                {
                    partinavn = rs2.getString("eintaltekst");
                }
            }
            e.setParti(partinavn);
            person.add(e);
        }
        return person;
    }


    public List<Personinfo> getAllePartier() throws SQLException {
        // returneres fra denne metoden.
        List<Personinfo> partier = new ArrayList<Personinfo>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "SELECT kode, eintaltekst, ENGeintaltekst, parti_bruksnavn,ENGparti_bruksnavn ,fleirtaltekst,eintalstekst_same,dokumentasjon,ENGdokumentasjon,eintaltekst_forkorting,ENGeintaltekst_forkorting,stortingsparti_45,fraksjonsmerknadsarkivet_forkorting \n" +
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
            Personinfo e = new Personinfo();
            e.setPartikode((Integer) rad.get("kode"));
            e.setPartinavn((String) rad.get("eintaltekst"));
            partier.add(e);
        }
        return partier;
    }


    /*embete list */

    public List<Personinfo> getAlleEmbete() throws SQLException {
        // returneres fra denne metoden.
        List<Personinfo> embete = new ArrayList<Personinfo>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "select distinct id, embete from Norske_statsraader_embete where embete is not null order by embete";

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }

        for (SortedMap rad : rader) {
            Personinfo e = new Personinfo();
            e.setEmbete((String) rad.get("embete"));
            embete.add(e);
        }
        return embete;
    }


    /********************oppdatering registert statsr�d*****************************/






    /*

   public void registrerNyParti(String partinavn, String partinavneng, String forkort, String dokumentasjon) throws SQLException {

       SqlCommandBean sqlCB = new SqlCommandBean();

       String sqlSelect = "insert into t_felles_partinavn (eintaltekst, ENGeintaltekst, eintaltekst_forkorting, dokumentasjon) values (?, ?, ?, ?)";
       // SQL-parametere.
       List values = new ArrayList();

       values.add(partinavn);
       values.add(partinavneng);
       values.add(forkort);
       values.add(dokumentasjon);

       sqlCB.setConnection(this.conn);
       sqlCB.setSqlValue(sqlSelect);
       sqlCB.setValues(values);
       sqlCB.executeUpdate();
   }



   public void oppdaterParti(String partinavn, String partinavneng, String forkort, String dokumentasjon, Integer id) throws SQLException {

       SqlCommandBean sqlCB = new SqlCommandBean();

       String sqlSelect = "update t_felles_partinavn set eintaltekst = ?, ENGeintaltekst = ?, eintaltekst_forkorting=?, dokumentasjon=?  where kode = ?";
       // SQL-parametere.
       List values = new ArrayList();

     values.add(partinavn);
       values.add(partinavneng);
       values.add(forkort);
       values.add(dokumentasjon);
       values.add(id);

       sqlCB.setConnection(this.conn);
       sqlCB.setSqlValue(sqlSelect);
       sqlCB.setValues(values);
       sqlCB.executeUpdate();
   }
    */
     /*
     public List<Partinavn> getAllePartier() throws SQLException {
        List<Partinavn> partilist = new ArrayList<Partinavn>();
        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        String sqlSelect = "SELECT DISTINCT t_parti_dokument_info.partikode, t_felles_partinavn.eintaltekst AS PartiEintaltekst\n" +
                "                FROM    t_parti_dokument_info \n" +
                "                LEFT OUTER JOIN t_parti_dokumenttyper ON t_parti_dokument_info.dokumenttype_id = t_parti_dokumenttyper.kode\n" +
                "                LEFT OUTER JOIN t_felles_partinavn ON t_parti_dokument_info.partikode = t_felles_partinavn.kode\n" +
                "                WHERE t_parti_dokument_info.dok_nr NOT IN (5340, 5372,5572,  5940, 5963, 6253, 6259, 6908,7217,7221, 7404, 7405, 7497, 7499, 7516, 7517, 8346, 8620, 8634, 8648, 8911, 8912, 8913, 8914, 9130,\n" +
                "                9131,9132,9133,9134,9135,9136,9137,9138,9139,9140,9141,9142,9143,9144,9145, 9146,9166, 9167, 9375, 9447, 9448, 9480, 9550, 9649, 9650, 9651, 9652)\n" +
                "                AND t_parti_dokumenttyper.fleirtaltekst  IS NOT null\n" +
                "                ORDER BY t_felles_partinavn.eintaltekst";
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        if (rader == null || rader.length == 0) {
            return null;
        }
        for (SortedMap rad : rader) {
            Partinavn e = new Partinavn();
            e.setIdnum((Integer) rad.get("partikode"));
            e.setPartinavn((String) rad.get("PartiEintaltekst"));
            partilist.add(e);
        }
        return partilist;
    }

     */

}
