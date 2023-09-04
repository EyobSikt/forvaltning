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


public class StatssekretarNavnListLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;


    public StatssekretarNavnListLogikk() {
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
    public List<Personnavn> getAlleStatssekretar() throws SQLException {
        // returneres fra denne metoden.
        List<Personnavn> person = new ArrayList<Personnavn>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "select distinct Person, Norske_politikere_Fornavn, Norske_politikere_Navn, Foedt, doedsaar, Parti_ved_start from Norske_statssekretaerer_ny\n" +
                "order by Norske_politikere_Navn, Norske_politikere_Fornavn";

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


}
