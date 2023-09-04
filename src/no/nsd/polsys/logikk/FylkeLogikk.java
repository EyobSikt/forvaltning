package no.nsd.polsys.logikk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class FylkeLogikk {


    // ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;


    // ============================================================ Konstrukt�r

    /**
     * Tom konstrukt�r.
     */
    public FylkeLogikk() {
    }



    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }


    public Map<Integer, DokCache> getFylker() throws SQLException {
        // returneres fra denne metoden.
        Map<Integer, DokCache> fylker = new HashMap<Integer, DokCache>();
        // resultat fra sql-sp�rring.
        Result result = null;
        // objekt som brukes til � utf�re sql-sp�rring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sp�rring.
        SortedMap[] rader = null;
        // SQL-sp�rring.
        String sqlSelect = "select * from t_felles_fylke";

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect);
        result = sqlCB.executeQuery();
        rader = result.getRows();

        if (rader == null || rader.length == 0) {
            return null;
        }

        for (SortedMap rad : rader) {
            DokCache f = new DokCache();
            f.setKode((Integer) rad.get("kode"));
            f.setTekstEntall((String) rad.get("tekst_entall"));
            fylker.put(f.getKode(), f);
        }

        return fylker;
    }



}
