package no.nsd.polsys.logikk.admin.regjering;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.admin.regjering.Personinfo;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author eyob
 */
public class RedigereStatssekretarLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;


    // ============================================================ Konstrukt�r

    /**
     * Tom konstrukt�r.
     */
    public RedigereStatssekretarLogikk() {
    }


    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }


    public Personinfo[] getPersonAktivitetInfo(Integer personid) throws SQLException {
        // returneres fra denne metoden.
        Personinfo[] allepersoninfo = null;

        Result result = null;
        Result result2 = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;
        SortedMap[] rader2 = null;
        /*
        String sqlSelect = "select distinct Person, Norske_politikere_Fornavn, Norske_politikere_Navn, Foedt, Parti_ved_start from t_regjering_norske_statsraader_ny where Person=?\n" +
                "order by Norske_politikere_Navn, Norske_politikere_Fornavn";
        */

         /*
        String sqlSelect = "SELECT \t\tNorske_politikere.person_id, Norske_politikere.fornavn, Norske_politikere.navn, Norske_statssekretaerer_ny.Slutt,\n" +
                "\t\t\tt_felles_unike_dep.eintaltekst as Eintaltekst, Norske_statssekretaerer_ny.Start, Norske_statssekretaerer_ny.Slutt, \n" +
                "\t\t\tNorske_statssekretaerer_ny.Stilling_avvik as Stilling_avvik, Norske_statssekretaerer_ny.Kode_dep,\n" +
                "\t\t\tNorske_statssekretaerer_ny.Eksternkommentar as Eksternkommentar\n" +
                "FROM \t\t(t_felles_person Norske_politikere INNER JOIN t_regjering_norske_statssekretaerer_ny Norske_statssekretaerer_ny ON Norske_politikere.person_id =\n" +
                "\t\t\tNorske_statssekretaerer_ny.Person) INNER JOIN t_felles_unike_dep ON Norske_statssekretaerer_ny.Kode_dep =\n" +
                "\t\t\tt_felles_unike_dep.kode\n" +
                "WHERE \t\t(person_id = ?) AND (Slutt BETWEEN t_felles_unike_dep.fra_tidspunkt AND \n" +
                "                      t_felles_unike_dep.til_tidspunkt) \n" +
                "ORDER BY \tNorske_statssekretaerer_ny.Start  ";
          */


        String sqlSelect = "SELECT \t\tNorske_politikere.Person, Norske_politikere.Fornavn, Norske_politikere.Navn, Norske_statssekretaerer_ny.Slutt,\n" +
                "\t\t\tDOK_unike_dep.Eintaltekst as Eintaltekst, Norske_statssekretaerer_ny.Start, Norske_statssekretaerer_ny.Slutt, \n" +
                "\t\t\tNorske_statssekretaerer_ny.Stilling_avvik as Stilling_avvik, Norske_statssekretaerer_ny.Kode_dep,\n" +
                "\t\t\tNorske_statssekretaerer_ny.Eksternkommentar as Eksternkommentar\n" +
                "FROM \t\t(Norske_politikere INNER JOIN Norske_statssekretaerer_ny ON Norske_politikere.Person =\n" +
                "\t\t\tNorske_statssekretaerer_ny.Person) INNER JOIN DOK_unike_dep ON Norske_statssekretaerer_ny.Kode_dep =\n" +
                "\t\t\tDOK_unike_dep.Kode\n" +
                "WHERE \t\t(Norske_politikere.Person = ?) AND (Norske_statssekretaerer_ny.Slutt BETWEEN DOK_unike_dep.fra_tidspunkt AND \n" +
                "                      DOK_unike_dep.til_tidspunkt) \n" +
                "ORDER BY \tNorske_statssekretaerer_ny.Start  ";



        List prosjekinfo_values = new ArrayList();
        prosjekinfo_values.add(personid);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(prosjekinfo_values);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        allepersoninfo = new Personinfo[rader.length];

        for (int i = 0; i < rader.length; i++) {
            allepersoninfo[i] = new Personinfo();

            allepersoninfo[i].setPersonkode((Integer) rader[i].get("Person"));
            allepersoninfo[i].setEtternavn((String) rader[i].get("navn"));
            allepersoninfo[i].setFornavn((String) rader[i].get("fornavn"));
            allepersoninfo[i].setEksternkommentar((String) rader[i].get("Eksternkommentar"));
            allepersoninfo[i].setKode_dep((Integer) rader[i].get("Kode_dep"));


            Object startdato =  rader[i].get("Start");
            Object sluttdato =  rader[i].get("Slutt");

            java.text.DateFormat dfYMD_reg = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
            java.text.DateFormat dfDMY_reg = new java.text.SimpleDateFormat("dd-MM-yyyy") ;
            if(startdato !=null){
                try {
                    allepersoninfo[i].setVirkeperiode_start(dfDMY_reg.format(dfYMD_reg.parse(String.valueOf(startdato))));
                } catch (ParseException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            if(sluttdato !=null){
                try {
                    allepersoninfo[i].setVirkeperiode_slutt(dfDMY_reg.format(dfYMD_reg.parse(String.valueOf(sluttdato))));
                } catch (ParseException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            String stilling_avvik =  (String)rader[i].get("Stilling_avvik");
            String eintaltekst =  (String)rader[i].get("Eintaltekst");

            if (stilling_avvik !=null && eintaltekst!="Statsministerembetet" && eintaltekst!="Statsministerens kontor" && eintaltekst!="Statsråd uten portefølje"
                     && eintaltekst!="Statsrådsavdelingen i Christiania" && eintaltekst!="Statsrådsavdelingen i Kristiania" ){

               allepersoninfo[i].setEmbete((String) rader[i].get("Stilling_avvik"));
               allepersoninfo[i].setDepartment((String) rader[i].get("Eintaltekst"));
            }
            else{
                allepersoninfo[i].setEmbete("Statssekretær");
                allepersoninfo[i].setDepartment((String) rader[i].get("Eintaltekst"));
             }
        }
        return allepersoninfo;
    }


    public void LagreStatssekretarStartdato(Integer personid, Integer kode_dep,  Date startdato, Date sluttdato) throws SQLException {
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // SQL-sp�rring.
        String sqlSelect = "update Norske_statssekretaerer_ny set Start = ?  where Person = ? and Kode_dep = ? and Slutt=? ";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(startdato);
        values.add(personid);
        values.add(kode_dep);
        values.add(sluttdato);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }

    public void LagreStatssekretarSluttdato(Integer personid, Integer kode_dep,  Date sluttdato, Date startdato) throws SQLException {
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // SQL-sp�rring.
        String sqlSelect = "update Norske_statssekretaerer_ny set Slutt = ?  where Person = ? and Kode_dep = ? and Start=? ";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(sluttdato);
        values.add(personid);
        values.add(kode_dep);
        values.add(startdato);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }


    public void registrerNyStatssekretar(String fornavn, String etternavn,  Date foedt, Integer foedtaar, Date doedt, Integer doedsaar, Integer parti, Integer kjoenn, String periodestart) throws SQLException {

        SqlCommandBean sqlCB = new SqlCommandBean();
        SqlCommandBean sqlCB_politiker = new SqlCommandBean();

        int personid =0;
        PreparedStatement stmt2 = conn.prepareStatement("select max(Person) as personkode from Norske_politikere");
        ResultSet rs2 = stmt2.executeQuery();
        while (rs2.next()) {
            {
                personid = rs2.getInt("personkode");
            }
        }

        String sqlSelect_politiker = "insert into Norske_politikere (Person, Fornavn, Navn, Fodt, Faar, Doed, Dodsaar, Kjoenn, periodestart ) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // SQL-parametere.
        List values_politiker = new ArrayList();
        values_politiker.add(personid+1);
        values_politiker.add(fornavn);
        values_politiker.add(etternavn);
        values_politiker.add(foedt);
        values_politiker.add(foedtaar);
        values_politiker.add(doedt);
        values_politiker.add(doedsaar);
        values_politiker.add(kjoenn);
        values_politiker.add(periodestart);


        sqlCB_politiker.setConnection(this.conn);
        sqlCB_politiker.setSqlValue(sqlSelect_politiker);
        sqlCB_politiker.setValues(values_politiker);
        sqlCB_politiker.executeUpdate();




        String sqlSelect = "insert into Norske_statssekretaerer_ny (Person, Norske_politikere_Fornavn, Norske_politikere_Navn, Foedt, doedsaar, Parti_ved_start, Kode_dep, Min_reg_HV) values (?, ?, ?, ?, ?, ?, ?, ?)";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(personid+1);
        values.add(fornavn);
        values.add(etternavn);
        values.add(foedtaar);
        values.add(doedsaar);
        values.add(parti);
        values.add(999999);
        values.add(2);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }


    /**
     * Returnerer alle department
     * @return
     * @throws java.sql.SQLException
     */
    public List<Personinfo> getAlleDepartment() throws SQLException {
        // returneres fra denne metoden.
        List<Personinfo> department = new ArrayList<Personinfo>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "SELECT Kode, Eintaltekst FROM DOK_unike_dep where Kode != 199999 and til_tidspunkt = '9999-09-09 00:00:00.000' ORDER BY Eintaltekst";

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }
        for (SortedMap rad : rader) {
            Personinfo e = new Personinfo();
            e.setDepartmentkode((Integer) rad.get("Kode"));
            e.setDepartmentnavn((String) rad.get("Eintaltekst"));
            department.add(e);
        }
        return department;
    }

    /**
     * Returnerer alle department
     * @return
     * @throws java.sql.SQLException
     */
    public List<Personinfo> getAlleEmbete() throws SQLException {
        // returneres fra denne metoden.
        List<Personinfo> emebete = new ArrayList<Personinfo>();

        Result result = null;

        SqlCommandBean sqlCB = new SqlCommandBean();

        SortedMap[] rader = null;

        String sqlSelect = "select distinct Stilling_avvik from Norske_statssekretaerer_ny where Stilling_avvik is not null order by Stilling_avvik \n";

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }
        for (SortedMap rad : rader) {
            Personinfo e = new Personinfo();
            e.setEmbete((String) rad.get("Stilling_avvik"));
            emebete.add(e);
        }
        return emebete;
    }

    public Personinfo[] getPersonInfo(Integer personid) throws SQLException {
        // returneres fra denne metoden.
        Personinfo[] personinfo = null;

        Result result = null;
        SqlCommandBean sqlCB = new SqlCommandBean();
        SortedMap[] rader = null;

        String sqlSelect = "select distinct Person, Norske_politikere_Fornavn, Norske_politikere_Navn, Foedt, Parti_ved_start, Kode_dep \n" +
                "from Norske_statssekretaerer_ny where Person = ? order by Person desc\n  ";

        List prosjekinfo_values = new ArrayList();
        prosjekinfo_values.add(personid);
        sqlCB.setConnection(this.conn);
        sqlCB.setValues(prosjekinfo_values);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();
        personinfo = new Personinfo[rader.length];
        for (int i = 0; i < rader.length; i++) {
            personinfo[i] = new Personinfo();
            personinfo[i].setPersonkode((Integer) rader[i].get("Person"));
            personinfo[i].setEtternavn((String) rader[i].get("Norske_politikere_Navn"));
            personinfo[i].setFornavn((String) rader[i].get("Norske_politikere_Fornavn"));
            personinfo[i].setFoedtaar((Integer) rader[i].get("Foedt"));
            personinfo[i].setParti((Integer) rader[i].get("Parti_ved_start"));
            personinfo[i].setKode_dep((Integer) rader[i].get("Kode_dep"));

        }
        return personinfo;
    }


    public void RegisterUpdateStatssekretarInfo(Integer personid, String fornavn, String etternavn, Integer foedtaar, Integer partikode, Integer kode_dep, Integer departmentkode, String embete, String eksternkommentar, Date startdato, Date sluttdato) throws SQLException {
        SqlCommandBean sqlCB = new SqlCommandBean();

        if(kode_dep == 999999){
            String sqlSelect = "update Norske_statssekretaerer_ny set Kode_dep=?, Stilling_avvik=?, Eksternkommentar=?, Start=?, Slutt = ?  where Person = ? and Kode_dep = ? ";
            List values = new ArrayList();
            values.add(departmentkode);
            values.add(embete);
            values.add(eksternkommentar);
            values.add(startdato);
            values.add(sluttdato);

            values.add(personid);
            values.add(kode_dep);

            sqlCB.setConnection(this.conn);
            sqlCB.setSqlValue(sqlSelect);
            sqlCB.setValues(values);
            sqlCB.executeUpdate();
        }
        else
        {
            String sqlSelect = "insert into Norske_statssekretaerer_ny (Person, Norske_politikere_Fornavn, Norske_politikere_Navn, Foedt, Parti_ved_start, Kode_dep, Stilling_avvik, Eksternkommentar, Start, Slutt, Min_reg_HV ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            // SQL-parametere.
            List values = new ArrayList();

            values.add(personid);
            values.add(fornavn);
            values.add(etternavn);
            values.add(foedtaar);
            values.add(partikode);
            values.add(departmentkode);
            values.add(embete);
            values.add(eksternkommentar);
            values.add(startdato);
            values.add(sluttdato);
            values.add(2);
            sqlCB.setConnection(this.conn);
            sqlCB.setSqlValue(sqlSelect);
            sqlCB.setValues(values);
            sqlCB.executeUpdate();

        }
    }


    public void LagreStatsradEksternkommentar(Integer personid, Integer kode_dep,  Date startdato, Date sluttdato, String eksternkommentar) throws SQLException {
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // SQL-sp�rring.
        String sqlSelect = "update Norske_statssekretaerer_ny set Eksternkommentar = ?  where Person = ? and Kode_dep = ? and Start=? and Slutt=?  ";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(eksternkommentar);
        values.add(personid);
        values.add(kode_dep);
        values.add(startdato);
        values.add(sluttdato);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }

    public void LagreStatsradEmbete(Integer personid, Integer kode_dep,  Date startdato, Date sluttdato, String embete) throws SQLException {
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // SQL-sp�rring.
        String sqlSelect = "update Norske_statssekretaerer_ny set Stilling_avvik = ?  where Person = ? and Kode_dep = ? and Start=? and Slutt=?  ";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(embete);
        values.add(personid);
        values.add(kode_dep);
        values.add(startdato);
        values.add(sluttdato);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();
    }

    public void LagreStatssekretarFoedtdato(Integer personid, Date foedt, Integer foedtaar) throws SQLException {
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        SqlCommandBean sqlCB_politiker = new SqlCommandBean();
        // SQL-sp�rring.
        String sqlSelect = "update Norske_statssekretaerer_ny set Foedt = ?  where Person = ?  ";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(foedtaar);
        values.add(personid);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();

        String sqlSelect_politiker = "update Norske_politikere set Fodt=? , Faar=?  where Person = ?  ";
        // SQL-parametere.
        List values_politiker = new ArrayList();
        values_politiker.add(foedt);
        values_politiker.add(foedtaar);
        values_politiker.add(personid);
        sqlCB_politiker.setConnection(this.conn);
        sqlCB_politiker.setSqlValue(sqlSelect_politiker);
        sqlCB_politiker.setValues(values_politiker);
        sqlCB_politiker.executeUpdate();
    }


    public void LagreStatssekretarDoedtdato(Integer personid, Date doedt, Integer doedtaar) throws SQLException {
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        SqlCommandBean sqlCB_politiker = new SqlCommandBean();
        // SQL-sp�rring.
        String sqlSelect = "update Norske_statssekretaerer_ny set doedsaar = ?  where Person = ?  ";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(doedtaar);
        values.add(personid);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();

        String sqlSelect_politiker = "update Norske_politikere set Doed=? , Dodsaar=?  where Person = ?  ";
        // SQL-parametere.
        List values_politiker = new ArrayList();
        values_politiker.add(doedt);
        values_politiker.add(doedtaar);
        values_politiker.add(personid);
        sqlCB_politiker.setConnection(this.conn);
        sqlCB_politiker.setSqlValue(sqlSelect_politiker);
        sqlCB_politiker.setValues(values_politiker);
        sqlCB_politiker.executeUpdate();
    }


    public void SleteStatssekretar(Integer personid) throws SQLException {
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        SqlCommandBean sqlCB_politiker = new SqlCommandBean();
        // SQL-sp�rring.
        String sqlSelect = "delete from  Norske_statssekretaerer_ny where Person = ?  ";
        // SQL-parametere.
        List values = new ArrayList();
        values.add(personid);
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        sqlCB.setValues(values);
        sqlCB.executeUpdate();

        String sqlSelect_politiker = "delete from  Norske_politikere  where Person = ?  ";
        // SQL-parametere.
        List values_politiker = new ArrayList();
        values_politiker.add(personid);
        sqlCB_politiker.setConnection(this.conn);
        sqlCB_politiker.setSqlValue(sqlSelect_politiker);
        sqlCB_politiker.setValues(values_politiker);
        sqlCB_politiker.executeUpdate();
    }
}
