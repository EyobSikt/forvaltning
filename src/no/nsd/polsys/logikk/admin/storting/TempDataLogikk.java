package no.nsd.polsys.logikk.admin.storting;

import no.nsd.common.beans.sql.SqlCommandBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TempDataLogikk {

    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;

    // ============================================================ Konstrukt�r
    /**
     * Tom konstrukt�r.
     */
    public TempDataLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /*denne sette in data til t_storting_votering_saksopplysninger_nyimport. tar parameter sessjonid fra URL.
     og Sette inn  PERIODE, DATO, SES, sesjon, SAK, typesak_text, SAKSREFERANSE, SAKSREGISTER, KOMITE, komite_id, komitenavn, EMNE,emnenavn, INTERNKOMMENTAR, Lenke, intern_referanse
   It is helper table to t_storting_votering_saksopplysninger_nyimport_final
   */
    public void InsertSaksOpplysning(Vector sak, Vector periode, Vector dato, Vector sesjon, Vector sesjon_text, Vector saknr, Vector typesak_text, Vector saksreferanse, Vector saksregister, Vector komiteid, Vector komitenavn, Vector<Vector<String>> emnenavn, Vector kildelink, Vector saksopplysininginternreferanse, Vector typsakid ) throws SQLException, ParseException {

        SqlCommandBean sqlCB = new SqlCommandBean();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        Calendar cal = Calendar.getInstance();

        int emnekode = 0;
        float komitekode = 0;
        String komite_id = null;
        String komite_navn = null;
        String emne_navn = null;

        for(int i = 0 ; i < sak.size(); i++) {

            if( i < komitenavn.size()){
                PreparedStatement stmt4 = conn.prepareStatement("SELECT  kode, eintaltekst, Eintaltekst_forkorting " +
                        "FROM t_storting_komite " +
                        "WHERE eintaltekst like ? or Eintaltekst_forkorting  like  ? ");
                stmt4.setString(1, String.valueOf(komitenavn.elementAt(i)));
                stmt4.setString(2,  String.valueOf(komiteid.elementAt(i)));
                ResultSet rs4 = stmt4.executeQuery();

                if(rs4.next()) {
                    komitekode = Integer.parseInt(rs4.getString("kode"));
                }
                else{
                    komitekode = 0;
                }

                komite_id =   String.valueOf(komiteid.elementAt(i));
                komite_navn =   String.valueOf(komitenavn.elementAt(i));
            }


            if (emnenavn.elementAt(i).size() == 0){

                Date parsed = format.parse(String.valueOf(dato.elementAt(i)));
                java.sql.Date sqldato = new java.sql.Date(parsed.getTime());
                String sqlFildeskription = "insert into t_storting_votering_saksopplysninger_nyimport_test2 (PERIODE, DATO, SES, sesjon, SAK, typesak_text, SAKSREFERANSE, SAKSREGISTER, KOMITE, komite_id, komitenavn, EMNE,emnenavn, INTERNKOMMENTAR, Lenke, intern_referanse, TYPSAK) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps1 = conn.prepareStatement(sqlFildeskription);
                ps1.setString(1, String.valueOf(Float.parseFloat(String.valueOf(periode.elementAt(i)))));
                ps1.setDate(2, java.sql.Date.valueOf(String.valueOf(dato.elementAt(i))));
                ps1.setString(3, String.valueOf(Float.parseFloat(String.valueOf(sesjon.elementAt(i)))));
                ps1.setString(4, String.valueOf(sesjon_text.elementAt(i)));
                ps1.setString(5, String.valueOf(Float.parseFloat(String.valueOf(saknr.elementAt(i)))));
                ps1.setString(6, String.valueOf(typesak_text.elementAt(i)));
                ps1.setString(7, String.valueOf(saksreferanse.elementAt(i)));
                ps1.setString(8, String.valueOf(saksregister.elementAt(i)));
                ps1.setString(9, String.valueOf(komitekode));
                ps1.setString(10, komite_id);
                ps1.setString(11, komite_navn);
                ps1.setString(12, String.valueOf(emnekode));
                ps1.setString(13, emne_navn);
                ps1.setString(14, sdf.format(cal.getTime()));
                ps1.setString(15, String.valueOf(kildelink.elementAt(i)));
                ps1.setInt(16, Integer.parseInt(String.valueOf(saksopplysininginternreferanse.elementAt(i))));
                ps1.setString(17, String.valueOf(Float.parseFloat(String.valueOf(typsakid.elementAt(i)))));
                ps1.executeUpdate();


            }
            else {
                for(int e = 0 ; e < emnenavn.elementAt(i).size(); e++) {
                    if(i<  emnenavn.size()){
                        PreparedStatement stmt3 = conn.prepareStatement("SELECT  Kode, Eintaltekst " +
                                "FROM t_storting_votering_emnegruppe_nytt  " +
                                "WHERE Eintaltekst like ? and hovedemne is null ");
                        stmt3.setString(1, String.valueOf(emnenavn.elementAt(i).elementAt(e)));
                        ResultSet rs3 = stmt3.executeQuery();
                        if(rs3.next()) {
                            emnekode = (int) Float.parseFloat(rs3.getString("Kode"));
                        }
                        else{
                            int last_id =  lastIdentityemnegroup();
                            //old ... old//String formatted = String.valueOf(last_id) + "000";
                            //Insert_t_storting_votering_emnegrupp(last_id+1000, emnenavn.elementAt(i).elementAt(e));
                            //emnekode= last_id+1000;
                        }

                        emne_navn =   String.valueOf(emnenavn.elementAt(i).elementAt(e));

                    }




        /*1. insert t_foriss_import tabell*/

                    Date parsed = format.parse(String.valueOf(dato.elementAt(i)));
                    java.sql.Date sqldato = new java.sql.Date(parsed.getTime());
                    String sqlFildeskription = "insert into t_storting_votering_saksopplysninger_nyimport_test2 (PERIODE, DATO, SES, sesjon, SAK, typesak_text, SAKSREFERANSE, SAKSREGISTER, KOMITE, komite_id, komitenavn, EMNE,emnenavn, INTERNKOMMENTAR, Lenke, intern_referanse, TYPSAK) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ps1 = conn.prepareStatement(sqlFildeskription);
                    ps1.setString(1, String.valueOf(Float.parseFloat(String.valueOf(periode.elementAt(i)))));
                    ps1.setDate(2, java.sql.Date.valueOf(String.valueOf(dato.elementAt(i))));
                    ps1.setString(3, String.valueOf(Float.parseFloat(String.valueOf(sesjon.elementAt(i)))));
                    ps1.setString(4, String.valueOf(sesjon_text.elementAt(i)));
                    ps1.setString(5, String.valueOf(Float.parseFloat(String.valueOf(saknr.elementAt(i)))));
                    ps1.setString(6, String.valueOf(typesak_text.elementAt(i)));
                    ps1.setString(7, String.valueOf(saksreferanse.elementAt(i)));
                    ps1.setString(8, String.valueOf(saksregister.elementAt(i)));
                    ps1.setString(9, String.valueOf(komitekode));
                    ps1.setString(10, komite_id);
                    ps1.setString(11, komite_navn);
                    ps1.setString(12, String.valueOf(emnekode));
                    ps1.setString(13, emne_navn);
                    ps1.setString(14, sdf.format(cal.getTime()));
                    ps1.setString(15, String.valueOf(kildelink.elementAt(i)));
                    ps1.setString(16, String.valueOf(saksopplysininginternreferanse.elementAt(i)));
                    ps1.setString(17, String.valueOf(Float.parseFloat(String.valueOf(typsakid.elementAt(i)))));
                    //ps1.setInt(16, Integer.parseInt(String.valueOf(saksopplysininginternreferanse.elementAt(i))));
                    ps1.executeUpdate();
                }
            }
        }
    }

    //method to fetch all sak where sak is not null
    /*
    public  Vector saksIdVotering() throws SQLException{

      Vector sakid = new Vector();
     PreparedStatement stmt3 = conn.prepareStatement("select distinct SAK from t_storting_votering_nyimport_saksopplysninger_final where SAK is not null and intern_referanse = 1 order by SAK DESC ");
       ResultSet rs3 = stmt3.executeQuery();
     while(rs3.next()) {
         sakid.add((int) Float.parseFloat(rs3.getString("SAK")));
     }
     return sakid;

 }
 */

    /*
    * Gets all variables from  t_storting_votering_saksopplysninger_nyimport table and taks SAK to import Voterings.
    * */
    public List<List<String>> saksIdVotering(String sesjon_id) throws SQLException{

        List<List<String>> fourDim = new ArrayList<List<String>>();
        PreparedStatement stmt3 = conn.prepareStatement("select distinct PERIODE, DATO, TID, SES, sesjon, SAL, KART, SAK, VOTNR, TYPSAK, typesak_text, VOTTYP, vottype_text, KOMITE, komite_id, komitenavn, SAKSREFERANSE, SAKSREGISTER, EMNE,emnenavn, PRESIDENT, initialer, fornavn, etternavn, fodselsdato, kjoenn, PRESIDENTPARTI, parti_id, partinavn, INTERNKOMMENTAR, Lenke, intern_referanse, TYPSAK " +
                "from t_storting_votering_saksopplysninger_nyimport_test2 where SAK is not null and intern_referanse like ? ");
        stmt3.setString(1, String.valueOf(sesjon_id));
        ResultSet rs3 = stmt3.executeQuery();
        while(rs3.next()) {
            fourDim.add(Arrays.asList( rs3.getString("PERIODE"), rs3.getString("DATO"), rs3.getString("SES"),rs3.getString("sesjon"),
                    rs3.getString("SAK"),rs3.getString("typesak_text"),rs3.getString("KOMITE"),rs3.getString("komite_id"),
                    rs3.getString("komitenavn"),rs3.getString("SAKSREFERANSE"),rs3.getString("SAKSREGISTER"), rs3.getString("EMNE") ,rs3.getString("emnenavn"),
                    rs3.getString("INTERNKOMMENTAR"),rs3.getString("Lenke"),rs3.getString("intern_referanse"),rs3.getString("TYPSAK") ));
        }
        return fourDim;
    }


    public void deleteData(String sesjon_id) throws SQLException, ParseException {

        PreparedStatement stmt3 = conn.prepareStatement("delete  from t_storting_votering_saksopplysninger_nyimport_test2 where intern_referanse like ? ");
        stmt3.setString(1, String.valueOf(sesjon_id));
        stmt3.executeUpdate();

    }


    /*denne sette in data til t_storting_votering_saksopplysninger_nyimport_final. tar parameter sessjonid fra URL.
     og Sette inn
     PERIODE, DATO, SES, sesjon, KART, SAK, VOTNR,  typesak_text, vottype_text, KOMITE, komite_id, komitenavn, SAKSREFERANSE, SAKSREGISTER, EMNE,emnenavn, PRESIDENT, initialer, fornavn, etternavn, fodselsdato, kjoenn, PRESIDENTPARTI, parti_id, partinavn, INTERNKOMMENTAR, Lenke, intern_referanse
    Thus this table is the real saksopplysninger table
   */

    public void InsertVoteringplace(Vector sak_mer, Vector periode, Vector dato, Vector sesjon, Vector sesjon_text,Vector kartnr_votering, Vector saknr, Vector votnr, Vector typesak_text,Vector vottype_text,Vector komitenr_mer, Vector komiteid_mer, Vector komitenavn_mer,  Vector saksreferanse_mer, Vector saksregister_mer, Vector emnenr_mer,Vector emnenavn_mer, Vector president, Vector presidentinitialer, Vector presidentfornavn, Vector presidentetternavn, Vector presidentfodtdato, Vector presidentkjoenn ,Vector presidentparti, Vector presidentpartiid, Vector presidentpartinavn, Vector internkommentar_mer,  Vector kildelink, Vector saksopplysininginternreferanse, Vector typsakid_mer, Vector vottypid) throws SQLException, ParseException {

        for(int i = 0 ; i < sak_mer.size(); i++) {


            String sqlFildeskription = "insert into t_storting_votering_saksopplysninger_nyimport_test2 (PERIODE, DATO, SES, sesjon, KART, SAK, VOTNR,  typesak_text, vottype_text, KOMITE, komite_id, komitenavn, SAKSREFERANSE, SAKSREGISTER, EMNE,emnenavn, PRESIDENT, initialer, fornavn, etternavn, fodselsdato, kjoenn, PRESIDENTPARTI, parti_id, partinavn, INTERNKOMMENTAR, Lenke, intern_referanse, TYPSAK, VOTTYP) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ;
            PreparedStatement ps1 = conn.prepareStatement(sqlFildeskription);
            ps1.setString(1, String.valueOf(Float.parseFloat(String.valueOf(periode.elementAt(i)))));
            ps1.setString(2, String.valueOf(dato.elementAt(i)));
            ps1.setString(3, String.valueOf(Float.parseFloat(String.valueOf(sesjon.elementAt(i)))));
            ps1.setString(4, String.valueOf(sesjon_text.elementAt(i)));
            ps1.setString(5, String.valueOf(Float.parseFloat(String.valueOf(kartnr_votering.elementAt(i)))));
            ps1.setString(6, String.valueOf(Float.parseFloat(String.valueOf(saknr.elementAt(i)))));
            ps1.setString(7, String.valueOf(Float.parseFloat(String.valueOf(votnr.elementAt(i)))));
            ps1.setString(8, String.valueOf(typesak_text.elementAt(i)));
            ps1.setString(9, String.valueOf(vottype_text.elementAt(i)));
            ps1.setString(10, String.valueOf(Float.parseFloat(String.valueOf(komitenr_mer.elementAt(i)))));
            ps1.setString(11, String.valueOf(komiteid_mer.elementAt(i)));
            ps1.setString(12, String.valueOf(komitenavn_mer.elementAt(i)));
            ps1.setString(13, String.valueOf(saksreferanse_mer.elementAt(i)));
            ps1.setString(14, String.valueOf(saksregister_mer.elementAt(i)));
            ps1.setString(15, String.valueOf(Float.parseFloat(String.valueOf(emnenr_mer.elementAt(i)))));
            ps1.setString(16, String.valueOf(emnenavn_mer.elementAt(i)));
            ps1.setString(17, String.valueOf(Float.parseFloat(String.valueOf(president.elementAt(i)))));
            ps1.setString(18, String.valueOf(presidentinitialer.elementAt(i)));
            ps1.setString(19, String.valueOf(presidentfornavn.elementAt(i)));
            ps1.setString(20, String.valueOf(presidentetternavn.elementAt(i)));
            ps1.setDate(21, java.sql.Date.valueOf(String.valueOf(presidentfodtdato.elementAt(i))));
            ps1.setString(22, String.valueOf(presidentkjoenn.elementAt(i)));
           /* ps1.setInt(23, Integer.parseInt(String.valueOf(presidentparti.elementAt(i))));*/
            ps1.setString(23, String.valueOf(presidentparti.elementAt(i)));
            ps1.setString(24, String.valueOf(presidentpartiid.elementAt(i)));
            ps1.setString(25, String.valueOf(presidentpartinavn.elementAt(i)));
            ps1.setString(26, String.valueOf(internkommentar_mer.elementAt(i)));
            ps1.setString(27, String.valueOf(kildelink.elementAt(i)));
            ps1.setString(28, String.valueOf(saksopplysininginternreferanse.elementAt(i)));
            ps1.setString(29, String.valueOf(typsakid_mer.elementAt(i)));
            ps1.setString(30, String.valueOf(vottypid.elementAt(i)));
            //ps1.setInt(28, Integer.parseInt(String.valueOf(saksopplysininginternreferanse.elementAt(i))));
            ps1.executeUpdate();

        }

    }

     /*
        public  void InsertVotering(Vector kartnr_votering, Vector sak, Vector votnr, Vector vottype_text, Vector president, Vector presidentinitialer, Vector presidentfornavn, Vector presidentetternavn, Vector presidentfodtdato, Vector presidentkjoenn ,Vector presidentparti, Vector presidentpartiid, Vector presidentpartinavn) throws SQLException{

        for(int i = 0 ; i < votnr.size(); i++) {

            //1. insert t_foriss_import tabell
            String sqlFildeskription = "update top (1) t_storting_votering_nyimport_saksopplysninger_final set KART=? , VOTNR= ? , vottype_text=? , PRESIDENT=?, initialer=?, fornavn=?, etternavn=?, fodselsdato=?, kjoenn=? , PRESIDENTPARTI=?, parti_id = ?, partinavn=?   where SAK = ? and intern_referanse = 1 AND VOTNR IS NULL";
            PreparedStatement ps1 = conn.prepareStatement(sqlFildeskription);
            ps1.setString(1, String.valueOf(Float.parseFloat(String.valueOf(kartnr_votering.elementAt(i)))));
            ps1.setString(2, String.valueOf(Float.parseFloat(String.valueOf(votnr.elementAt(i)))));
            ps1.setString(3, String.valueOf(vottype_text.elementAt(i)));
            ps1.setString(4, String.valueOf(Float.parseFloat(String.valueOf(president.elementAt(i)))));
            ps1.setString(5, String.valueOf(presidentinitialer.elementAt(i)));
            ps1.setString(6, String.valueOf(presidentfornavn.elementAt(i)));
            ps1.setString(7, String.valueOf(presidentetternavn.elementAt(i)));
            ps1.setDate(8, java.sql.Date.valueOf(String.valueOf(presidentfodtdato.elementAt(i))));
            ps1.setString(9, String.valueOf(presidentkjoenn.elementAt(i)));

            ps1.setInt(10, Integer.parseInt(String.valueOf(presidentparti.elementAt(i))));
            ps1.setString(11, String.valueOf(presidentpartiid.elementAt(i)));
            ps1.setString(12, String.valueOf(presidentpartinavn.elementAt(i)));

            ps1.setString(13, String.valueOf(Float.parseFloat(String.valueOf(sak.elementAt(i)))));
            ps1.executeUpdate();

        }
    }
    */

    /*
    * Get all emne
    * */
    public  int  emnekode(String emnenavn) throws SQLException{
        int emne_id = 0;
        PreparedStatement stmt3 = conn.prepareStatement("select Kode, Eintaltekst from t_storting_votering_emnegruppe where Eintaltekst like ? ");
        stmt3.setString(1, emnenavn);
        ResultSet rs3 = stmt3.executeQuery();
        while(rs3.next()) {
            emne_id =  rs3.getInt("Kode");
        }
        return emne_id;
    }

    public  float  president(String fornavn, String etternavn, String fodt, String id) throws SQLException{
        float personid = 0;
        PreparedStatement stmt3 = conn.prepareStatement("select person_id from t_felles_person where initialer = ?  ");
        stmt3.setString(1, id);
        //stmt3.setString(2, etternavn);
        //stmt3.setString(3, fodt);
        ResultSet rs3 = stmt3.executeQuery();
        while(rs3.next()) {
            personid =  Float.parseFloat(String.valueOf(rs3.getInt("person_id")));
        }
        return personid;
    }

    public  int  presidentparti(String id) throws SQLException{
        int parti_id = 0;
        PreparedStatement stmt3 = conn.prepareStatement("select kode from t_felles_partinavn where parti_bruksnavn = ? ");
        stmt3.setString(1, id);
        ResultSet rs3 = stmt3.executeQuery();
        while(rs3.next()) {
            parti_id =  rs3.getInt("kode");
        }
        return parti_id;
    }
    ;

    /*Get PERIODE,  SES, KART, SAK, VOTNR from t_storting_votering_saksopplysninger_nyimport_final and use VOTNR to import  voteringsrsulatat into t_storting_votering_personvotering_nyimport */

    public List<List<String>> voteringIdVoteringresultat(String sesjon_id) throws SQLException{
        Vector votid = new Vector();
        List<List<String>> fourDim = new ArrayList<List<String>>();
        PreparedStatement stmt3 = conn.prepareStatement("select distinct PERIODE,  SES, KART, SAK, VOTNR, intern_referanse from t_storting_votering_saksopplysninger_nyimport_test2 where VOTNR is not null and intern_referanse like ? order by VOTNR ASC  ");
        stmt3.setString(1, String.valueOf(sesjon_id));
        ResultSet rs3 = stmt3.executeQuery();
        while(rs3.next()) {
            //votid.add((int) Float.parseFloat(rs3.getString("VOTNR")));
            fourDim.add(Arrays.asList(rs3.getString("PERIODE"), rs3.getString("SES"), rs3.getString("KART"), rs3.getString("SAK"), rs3.getString("VOTNR"), rs3.getString("intern_referanse")));
        }
        return fourDim;
    }

    /*
      Insert into t_storting_votering_personvotering_nyimport.
     */
    public  void Insert_t_storting_votering_import_personvotering(Vector periode_vr, Vector ses_vr, Vector kart_vr, Vector sak_vr, Vector votnr_vr, Vector votering_vr, Vector votering_text, Vector representant_personnavn_vr, Vector representantinitialer, Vector representantfornavn, Vector representantetternavn, Vector representantfodtdato, Vector representantkjoenn, Vector representant_parti_vr, Vector representantpartiid, Vector representantpartinavn, Vector personopplysning_intern_referanse) throws SQLException{

        //System.out.println("size of votering from logigkk class er : " +votnr_vr.size()) ;

        for(int i = 0 ; i < votnr_vr.size(); i++) {

            /*1. insert t_foriss_import tabell*/
            String sqlFildeskription = "insert into t_storting_votering_personvotering_nyimport_test2 (PERIODE, SES, KART, SAK, VOTNR, VOTERING,votering_text, PERSON, initialer, fornavn, etternavn, fodselsdato, kjoenn, PARTI, parti_id, partinavn, intern_referanse) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps1 = conn.prepareStatement(sqlFildeskription);
            ps1.setInt(1, Integer.parseInt(String.valueOf(periode_vr.elementAt(i))));
            ps1.setString(2, String.valueOf(Float.parseFloat(String.valueOf(ses_vr.elementAt(i)))));
            ps1.setString(3, String.valueOf(Float.parseFloat(String.valueOf(kart_vr.elementAt(i)))));
            ps1.setString(4, String.valueOf(Float.parseFloat(String.valueOf(sak_vr.elementAt(i)))));
            ps1.setString(5, String.valueOf(Float.parseFloat(String.valueOf(votnr_vr.elementAt(i)))));
            ps1.setString(6, String.valueOf(Float.parseFloat(String.valueOf(votering_vr.elementAt(i)))));

            ps1.setString(7, String.valueOf(votering_text.elementAt(i)));

            ps1.setString(8, String.valueOf(Float.parseFloat(String.valueOf(representant_personnavn_vr.elementAt(i)))));

            ps1.setString(9, String.valueOf(representantinitialer.elementAt(i)));
            ps1.setString(10, String.valueOf(representantfornavn.elementAt(i)));
            ps1.setString(11, String.valueOf(representantetternavn.elementAt(i)));
            ps1.setDate(12, java.sql.Date.valueOf(String.valueOf(representantfodtdato.elementAt(i))));
            ps1.setString(13, String.valueOf(representantkjoenn.elementAt(i)));

            ps1.setInt(14, Integer.parseInt(String.valueOf(representant_parti_vr.elementAt(i))));

            ps1.setString(15, String.valueOf(representantpartiid.elementAt(i)));
            ps1.setString(16, String.valueOf(representantpartinavn.elementAt(i)));

            ps1.setString(17, String.valueOf(personopplysning_intern_referanse.elementAt(i)));
            ps1.executeUpdate();

        }
    }



    public  int  lastIdentityemnegroup() throws SQLException{
        int last_id = 0;
        PreparedStatement stmt3 = conn.prepareStatement("SELECT Kode from t_storting_votering_emnegruppe_nytt2 WHERE   id = (SELECT MAX(id)  FROM t_storting_votering_emnegruppe)");
        ResultSet rs3 = stmt3.executeQuery();
        while(rs3.next()) {
            last_id =  rs3.getInt("Kode");
        }
        return last_id;
    }

    public  void Insert_t_storting_votering_emnegrupp(int kode, String eintaltekst) throws SQLException{
            /*1. insert t_foriss_import tabell*/
        String sqlFildeskription = "insert into t_storting_votering_emnegruppe_nytt2 (Kode, Eintaltekst) values (?,?)";
        PreparedStatement ps1 = conn.prepareStatement(sqlFildeskription);
        ps1.setInt(1, kode);
        ps1.setString(2, eintaltekst);
        ps1.executeUpdate();
    }





    public void InsertEmne(Vector emneid, Vector emnenavn, Vector hovedemneid) throws SQLException, ParseException {
        for(int i = 0 ; i < emneid.size(); i++) {

            String sqlFildeskription = "insert into t_storting_votering_emnegruppe_nytt (Kode, Eintaltekst, hovedemne) values (?,?,?) " ;
            PreparedStatement ps1 = conn.prepareStatement(sqlFildeskription);
            ps1.setInt(1, Integer.parseInt(String.valueOf(emneid.elementAt(i))));
            ps1.setString(2, String.valueOf(emnenavn.elementAt(i)));
            ps1.setInt(3, Integer.parseInt(String.valueOf(hovedemneid.elementAt(i))));

            ps1.executeUpdate();

        }

    }


}
