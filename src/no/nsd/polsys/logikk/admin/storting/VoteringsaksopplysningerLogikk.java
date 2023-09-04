package no.nsd.polsys.logikk.admin.storting;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.admin.storting.Votering;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;


public class VoteringsaksopplysningerLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;


    public VoteringsaksopplysningerLogikk() {
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
    public List<Votering> getAllesaksopplysninger(String sesjon_id) throws SQLException {
        // returneres fra denne metoden.
        List<Votering> saksopplysninger = new ArrayList<Votering>();

        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        int sesjonid = 0;
        if(sesjon_id.equals("2010-2011")){sesjonid= 1;}
        else if(sesjon_id.equals("2011-2012")){sesjonid= 2;}
        else if(sesjon_id.equals("2012-2013")){sesjonid= 3;}
        //else if(sesjon_id.equals("155_2010-2011")){sesjonid= 3;}

        String sqlSelect = "SELECT id, SAK, VOTNR, TYPSAK, typesak_text, VOTTYP, vottype_text, SAKSREFERANSE, SAKSREGISTER, intern_referanse \n" +
                "FROM t_xxx_storting_votering_saksopplysninger_nyimport_test2\n" +
                "WHERE intern_referanse = ? and VOTNR is not null and VOTNR in (select distinct VOTNR from t_xxx_storting_votering_personvotering_nyimport_test2 )  " +
                "ORDER BY id desc";

        List values = new ArrayList();

        values.add(sesjon_id);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }

        for (SortedMap rad : rader) {
            Votering e = new Votering();
            e.setSesjon(sesjon_id);
            e.setId((Integer) rad.get("id"));
            e.setSAK(rad.get("SAK"));
            e.setVOTNR( rad.get("VOTNR"));
            e.setTYPSAK( rad.get("TYPSAK"));
            e.setTypesak_text((String) rad.get("typesak_text"));
            e.setVOTTYP( rad.get("VOTTYP"));
            e.setVottype_text((String) rad.get("vottype_text"));
            e.setSAKSREFERANSE((String) rad.get("SAKSREFERANSE"));
            e.setSAKSREGISTER((String) rad.get("SAKSREGISTER"));


            saksopplysninger.add(e);
        }
        return saksopplysninger;
    }




    public void oppdaterSaksopplysninger(Object TYPSAK, Object VOTTYP,  Integer id) throws SQLException {

        SqlCommandBean sqlCB = new SqlCommandBean();
   
        String sqlSelect = "update t_xxx_storting_votering_saksopplysninger_nyimport_test2 set TYPSAK = ?, VOTTYP = ? where id = ?";
        // SQL-parametere.
        List values = new ArrayList();

      values.add(TYPSAK);
        values.add(VOTTYP);
        values.add(id);

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }


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
