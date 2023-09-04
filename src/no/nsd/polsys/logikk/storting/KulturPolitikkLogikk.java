package no.nsd.polsys.logikk.storting;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.storting.KulturPolitikk;

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
public class KulturPolitikkLogikk {
// ============================================================== Variabler

    /** Forbindelse til databasen. */
    private Connection conn;
    /** Settes til true hvis en vil ha engelsk. */
    private boolean engelsk = false;


    public KulturPolitikkLogikk() {
    }

    // ================================================================ Metoder

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void brukEngelsk() {
        this.engelsk = true;
    }


    // Lese metoder ------------------------------------------------------------------------------------

    public KulturPolitikk[] getPartiNavn() throws Exception {
           String condition = "";
           return this.getPartiNavn(condition, null);
       }

    public KulturPolitikk[] SokKulturPolitikk(String[] partiid, String[] sitatkat, Integer fra, Integer til, String sok1, String sok2, String sok3) throws Exception {
           String condition = " WHERE storting_kultsitat.sitatnr_id !=-1 ";

       if(partiid != null){
           condition +=   " AND storting_kultsitat.parti in ( " ;
            for(int i = 0; i < partiid.length; i++){
           condition +=    partiid[i] + ", ";
           }
           condition +=    "  -1 )";
         }
        if(sitatkat != null){
           condition +=   " AND storting_kultsitat_tema.idsitatkat in ( " ;
            for(int i = 0; i < sitatkat.length; i++){
           condition +=    sitatkat[i] + ", ";
           }
           condition +=    "  -1 )";
         }
       
         condition += " AND storting_kultsitat.aar >= ? ";
         condition += " AND storting_kultsitat.aar <= ? ";
         condition += " AND storting_kultsitat.sitatHTML LIKE ? ";
         condition += " AND storting_kultsitat.sitatHTML LIKE ? ";
        condition += " AND storting_kultsitat.sitatHTML LIKE ? ";
        condition += " ORDER BY storting_kultsitat.doktittel " ;
          List values = new ArrayList();
          values.add(fra);
          values.add(til);
          values.add("%"+sok1+"%");
          values.add("%"+sok2+"%");
          values.add("%"+sok3+"%");

           return this.SokKulturPolitikk(condition, values);
       }

  /*
  * kulturpolitikkfrekvens tabell
  * */

    public KulturPolitikk[] KulturPolitikkFrekvens_ab() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 2)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ac() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 3)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ad() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 4)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ae() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 5)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_af() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 6)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ag() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 7)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ah() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 8)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ai() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 9)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_aj() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 10)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ak() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 11)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_al() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 12)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_am() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 13)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_an() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 14)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ao() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar = 1863) AND (storting_kultsitat_tema.idsitatkat = 15)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bb() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 2)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bc() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 3)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bd() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 4)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_be() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 5)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bf() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 6)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bg() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 7)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bh() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 8)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bi() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 9)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bj() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 10)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bk() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 11)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bl() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 12)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bm() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 13)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bn() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 14)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_bo() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1924) AND (storting_kultsitat.aar < 1930)  AND (storting_kultsitat_tema.idsitatkat = 15)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cb() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 2)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cc() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 3)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cd() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 4)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ce() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 5)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cf() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 6)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cg() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 7)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ch() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 8)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ci() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 9)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cj() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 10)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ck() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 11)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cl() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 12)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cm() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 13)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_cn() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 14)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_co() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1930) AND (storting_kultsitat.aar < 1940)  AND (storting_kultsitat_tema.idsitatkat = 15)";
           return this.KulturPolitikkFrekvens(condition, null);
       }

    public KulturPolitikk[] KulturPolitikkFrekvens_db() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 2)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dc() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 3)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dd() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 4)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_de() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 5)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_df() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 6)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dg() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 7)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dh() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 8)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_di() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 9)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dj() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 10)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dk() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 11)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dl() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 12)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_dm() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 13)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_dn() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 14)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_do() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1940) AND (storting_kultsitat.aar < 1950)  AND (storting_kultsitat_tema.idsitatkat = 15)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_eb() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 2)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ec() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 3)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_ed() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 4)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_ee() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 5)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_ef() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 6)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_eg() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 7)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_eh() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 8)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_ei() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 9)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_ej() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 10)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_ek() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 11)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_el() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 12)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_em() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 13)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_en() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 14)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
     public KulturPolitikk[] KulturPolitikkFrekvens_eo() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1950) AND (storting_kultsitat.aar < 1960)  AND (storting_kultsitat_tema.idsitatkat = 15)";
           return this.KulturPolitikkFrekvens(condition, null);
       }

    public KulturPolitikk[] KulturPolitikkFrekvens_fb() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 2)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fc() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 3)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fd() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 4)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fe() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 5)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_ff() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 6)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fg() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 7)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fh() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 8)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fi() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 9)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fj() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 10)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fk() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 11)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fl() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 12)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fm() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 13)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fn() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 14)";
           return this.KulturPolitikkFrekvens(condition, null);
       }
    public KulturPolitikk[] KulturPolitikkFrekvens_fo() throws Exception {
           String condition = "WHERE     (storting_kultsitat.aar >= 1960) AND (storting_kultsitat.aar < 1970)  AND (storting_kultsitat_tema.idsitatkat = 15)";
           return this.KulturPolitikkFrekvens(condition, null);
       }






/***************************************************************************************************

 Private metoder

***************************************************************************************************/
    private KulturPolitikk[] getPartiNavn(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        KulturPolitikk[] kulturpolitkk = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     t_felles_partinavn.eintaltekst AS eintaltekst, t_felles_partinavn.kode AS kode\n" +
                "FROM         t_felles_partinavn INNER JOIN\n" +
                "                      storting_kultsitat ON t_felles_partinavn.kode = storting_kultsitat.parti\n" +
                "GROUP BY t_felles_partinavn.eintaltekst, t_felles_partinavn.kode"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        kulturpolitkk = new KulturPolitikk[rader.length];

    for (int i = 0; i < rader.length; i++) {
                kulturpolitkk[i] = new KulturPolitikk();
               kulturpolitkk[i].setPartinavn((String)rader[i].get("eintaltekst"));
              kulturpolitkk[i].setPartikode((Integer)rader[i].get("kode"));

            }
        return kulturpolitkk;
    }

    private KulturPolitikk[] SokKulturPolitikk(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        KulturPolitikk[] kulturpolitkk = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT    storting_kultsitat.sitatnr_id, storting_kultsitat.partinavn, storting_kultsitat.parti, storting_kultsitat.sitatHTML, politikere.id as person_id, politikere.fornavn, politikere.etternavn as navn, \n" +
                "                      storting_kultsitat.doktittel, DAY(storting_kultsitat.Dato) DAY, MONTH(storting_kultsitat.Dato) MONTH , YEAR(storting_kultsitat.Dato) YEAR, t_felles_partinavn.eintaltekst\n" +
                "FROM          storting_kultsitat INNER JOIN\n" +
                "                      storting_kultsitat_tema ON storting_kultsitat.sitatnr_id = storting_kultsitat_tema.sitatnr INNER JOIN\n" +
                "                      storting_kultsitat_kat ON storting_kultsitat_tema.idsitatkat = storting_kultsitat_kat.kode INNER JOIN\n" +
                "                      politikere ON storting_kultsitat.person_id = politikere.id INNER JOIN\n" +
                "                      t_felles_partinavn ON storting_kultsitat.parti = t_felles_partinavn.kode"
                + (condition != null ? " " + condition : "");

        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        kulturpolitkk = new KulturPolitikk[rader.length];

    for (int i = 0; i < rader.length; i++) {
                kulturpolitkk[i] = new KulturPolitikk();
              kulturpolitkk[i].setPartinavn((String)rader[i].get("partinavn"));
              kulturpolitkk[i].setEtternavn((String)rader[i].get("navn"));
              kulturpolitkk[i].setFornavn((String)rader[i].get("fornavn"));
              kulturpolitkk[i].setDoktittel((String)rader[i].get("doktittel"));
              kulturpolitkk[i].setSitathtml((String)rader[i].get("sitatHTML"));
              int day =  (Integer)rader[i].get("DAY");
              int month =  (Integer)rader[i].get("MONTH");
              int year =  (Integer)rader[i].get("YEAR");
              kulturpolitkk[i].setDato( day+"/"+month+"/"+year);
              kulturpolitkk[i].setPersonId((Integer)rader[i].get("person_id"));
            }


        return kulturpolitkk;
    }

    /*
    * sporringer for table for kulturpolitikkfrkvens
    * */

     private KulturPolitikk[] KulturPolitikkFrekvens(String condition, List values) throws Exception {
        // tabell som returneres fra denne metoden.
        KulturPolitikk[] kulturpolitkk = null;
        // resultat fra sql-sporring.
        Result result = null;
        // objekt som brukes til aa utfore sql-sporring.
        SqlCommandBean sqlCB = new SqlCommandBean();
        // inneholder data fra sql-sporring.
        SortedMap[] rader = null;
        // SQL-sporring.
        String sqlSelect = "SELECT     storting_kultsitat.sitatnr_id, storting_kultsitat.aar, storting_kultsitat_tema.idsitatkat\n" +
                "FROM         storting_kultsitat_tema INNER JOIN\n" +
                "                      storting_kultsitat ON storting_kultsitat_tema.sitatnr = storting_kultsitat.sitatnr_id\n"
                + (condition != null ? " " + condition : "");
        sqlCB.setConnection(this.conn);
        sqlCB.setSqlValue(sqlSelect); //sporring
        sqlCB.setValues(values); //parameter
        result = sqlCB.executeQuery();
        rader = result.getRows();
        kulturpolitkk = new KulturPolitikk[rader.length];
    for (int i = 0; i < rader.length; i++) {
                kulturpolitkk[i] = new KulturPolitikk();
               kulturpolitkk[i].setPartikode((Integer)rader[i].get("sitatnr_id"));
            }
        return kulturpolitkk;
  }



}

