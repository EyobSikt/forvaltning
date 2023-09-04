package no.nsd.polsys.factories.forvaltning;

import no.nsd.polsys.modell.forvaltning.EndringCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hvb
 */
public class EndringCacheFactory {

    // Default 2 timer. Denne variablen (konstanten) settes typisk av controller-servleten.
    public static long CACHE_GYLDIG = 2L * 60L * 60L * 1000L;

    private static List<EndringCache> endringer = null;
    private static long oppdatertEndring = 0;


    /**
     * Returnerer alle enhet-endringer fra databasen. Bruker cache hvis den
     * fremdeles er gyldig.
     * @param conn
     * @return
     * @throws SQLException
     */
    public static synchronized List<EndringCache> getEndringer(Connection conn) throws SQLException {
        long tid = System.currentTimeMillis();
        boolean brukCache = true;
        if (endringer == null) brukCache = false;
        if ((oppdatertEndring + CACHE_GYLDIG) < tid) brukCache = false;

        if (brukCache) {
            return endringer;
        }

        endringer = new ArrayList<EndringCache>(12000);

        // SQL-objekter
        ResultSet rs = null;
        Statement stmt = null;
        // SQL-spørring.
        String sqlSelect = "select * from t_forvaltning_endring order by tidspunkt";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlSelect);

            while (rs.next()) {
                EndringCache e = new EndringCache();
                e.setId((Integer) rs.getObject("id"));
                e.setIdnum((Integer) rs.getObject("idnum"));
                e.setKortNavn((String) rs.getObject("kort_navn"));
                e.setLangtNavn((String) rs.getObject("langt_navn"));
                e.setEngelskLangtNavn((String) rs.getObject("eng_langt_navn"));
                e.setEndringskode((Integer) rs.getObject("endringskode"));
                e.setOverordnetIdnum((Integer) rs.getObject("overordnet_idnum"));
                e.setGrunnenhet((Integer) rs.getObject("grunnenhet"));
                e.setTilknytningsform((Integer) rs.getObject("tilknytningsform"));
                e.setCofog((String) rs.getObject("cofog"));
                e.setNivaa((Integer) rs.getObject("nivaa"));
                e.setTidspunkt((Date) rs.getObject("tidspunkt"));
                e.setBekreftetDato((Boolean) rs.getObject("bekr_dato"));
                e.setKommunenummer((Integer) rs.getObject("kommunenr"));

                endringer.add(e);
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }

        oppdatertEndring = System.currentTimeMillis();
        return endringer;
    }


    public static synchronized List<Enhet> getEndringer2(Connection conn, Integer aar) throws SQLException {

         List<Enhet> endringer2 = null;
        endringer2 = new ArrayList<>(12000);

        // SQL-objekter
        ResultSet rs = null;
        PreparedStatement stmt = null;
        // SQL-spørring.
        String sqlSelect = "select  * from t_forvaltning_bronnoysund_enhethierarki where  aar = ? order by navn ";

        try {


             stmt = conn.prepareStatement(sqlSelect);
            stmt.setInt(1, aar);
             rs=stmt.executeQuery();

            while (rs.next()) {
                Enhet e = new Enhet();
                e.setIdnum((Integer) rs.getObject("idnum"));
                e.setForvaltningsidnum((Integer) rs.getObject("forvaltningsidnum"));
                e.setAar((Integer) rs.getObject("aar"));
                e.setLangtNavn((String) rs.getObject("navn"));
                //if(rs.getObject("navn_sst") !=null ) {e.setLangtNavn((String) rs.getObject("navn_sst"));}  else {e.setLangtNavn((String) rs.getObject("navn"));}
                e.setOverordnetIdnum((Integer) rs.getObject("overordnet_idnum"));
                e.setTilknytningsform((Integer) rs.getObject("tilknytningsform"));
                e.setKommunenummer((Integer) rs.getObject("postadresse_kommunenr"));
                endringer2.add(e);
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
        return endringer2;
    }


}
