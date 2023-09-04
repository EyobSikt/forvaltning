package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.*;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author hvb
 */
public class UtvalgLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }

   /**
    * Returnerer et bestemt utvalg.
    *
    * @param unr
    * @return
    * @throws SQLException
    */
   public Utvalg getUtvalg(Integer unr) throws SQLException {
      Utvalg utvalg = new Utvalg();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u.f_odep, u.unavn as unavn, u.hjemmel  as hjemmel, u.mandat  as mandat, u.opprettelsesaar, u.opphoersaar,  "
              + "t.tekst as utvalgstype, h.tekst  as hovedfunksjon, g.tekst as geografi, o.tekst as opprettelsesmaate, u.tidsfrist, 'aar2003' as utvalgaar "
              + "from t_forvaltning_utvalg as u "
              + "left join t_forvaltning_utvalg_dok_utvalgstype as t on u.utvalgstype = t.kode "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode "
              + "left join t_forvaltning_utvalg_dok_geografi as g on u.geografi = g.kode "
              + "left join t_forvaltning_utvalg_dok_opprettelsesmaate as o on u.opprettelsesmaate = o.kode "
              + " left join t_forvaltning_utvalg_tid as td on u.unr = td.unr "
              + "where td.aar < 2004 and u.unr = ? "
              + " union "
              + " select distinct  u.f_odep ,  u.Organets_navn  as unavn, u.Hjemmel, u.Mandat_URL as mandat, u.opprettelsesaar, u.opphoersaar, t.tekst as utvalgstype, u.hovedfunksjon, g.tekst as geografi, null, 0, 'aar2004' as utvalgaar  "
              + " from t_forvaltning_utvalg2018 u "
              + " left join t_forvaltning_utvalg_dok_utvalgstype as t on u.utvalgstype = t.kode "
              + " left join t_forvaltning_utvalg_dok_geografi as g on u.geografi = g.kode "
              + " where u.unr = ? " ;

      List values = new ArrayList();

      values.add(unr);
      values.add(unr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      SortedMap rad = rader[0];

      Enhet odep = new Enhet();
      utvalg.setOdep(odep);

      utvalg.setUnr(unr);

      odep.setIdnum((Integer) rad.get("f_odep"));
      utvalg.setNavn((String) rad.get("unavn"));
      utvalg.setHjemmel((String) rad.get("hjemmel"));
      utvalg.setMandat((String) rad.get("mandat"));
      utvalg.setOpprettelsesaar((Integer) rad.get("opprettelsesaar"));
      utvalg.setOpphoersaar((Integer) rad.get("opphoersaar"));
      utvalg.setTidsfrist((Integer) rad.get("tidsfrist"));

      utvalg.setUtvalgstype((String) rad.get("utvalgstype"));
      utvalg.setHovedfunksjon((String) rad.get("hovedfunksjon"));
      utvalg.setGeografi((String) rad.get("geografi"));
      utvalg.setOpprettelsesmaate((String) rad.get("opprettelsesmaate"));
      if(((String) rad.get("utvalgaar")).equals("aar2003")){
         utvalg.setUtvalg2003((String) rad.get("utvalgaar"));
      }
      if(((String) rad.get("Utvalgaar")).equals("aar2004")){
         utvalg.setUtvalg2004((String) rad.get("utvalgaar"));
      }

      return utvalg;
   }

   /**
    * Returnerer utvalg søket treffer på.
    *
    * @param s
    * @return
    * @throws SQLException
    */
   public List<Utvalg> sokUtvalg(String s) throws SQLException {
      List<Utvalg> utvalg = new ArrayList<Utvalg>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct u.hovedfunksjon, u.unr, u.unavn, u.opprettelsesaar, u.opphoersaar, h.tekst, 'aar2003' as utvalgaar "
              + "from t_forvaltning_utvalg as u "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode "
              + "left join t_forvaltning_utvalg_tid as td on u.unr = td.unr  "
              + "where "
              + " td.aar < 2004 and lower(u.unavn) like lower(?) "
              + "union  "
              + "select distinct u.hovedfunksjon, u.unr, u.Organets_navn, u.opprettelsesaar, u.opphoersaar, h.tekst, 'aar2004' as utvalgaar "
              + " from t_forvaltning_utvalg2018_tid as u "
              + " left join t_forvaltning_utvalg_dok_hovedfunksjon2018 as h on u.hovedfunksjon = h.kode "
              + " where "
              + " lower(u.Organets_navn) like lower(?)   "
              + "order by hovedfunksjon, opprettelsesaar, opphoersaar";

      List values = new ArrayList();

      values.add("%" + s + "%");
      values.add("%" + s + "%");

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Utvalg u = new Utvalg();

         u.setUnr((Integer) rad.get("unr"));
         u.setNavn((String) rad.get("unavn"));
         u.setHovedfunksjon((String) rad.get("tekst"));
         u.setOpprettelsesaar((Integer) rad.get("opprettelsesaar"));
         u.setOpphoersaar((Integer) rad.get("opphoersaar"));
         if(((String) rad.get("utvalgaar")).equals("aar2003")){
            u.setUtvalg2003((String) rad.get("utvalgaar"));
         }
         if(((String) rad.get("Utvalgaar")).equals("aar2004")){
            u.setUtvalg2004((String) rad.get("utvalgaar"));
         }

         utvalg.add(u);
      }
      return utvalg;
   }

   /**
    * Returnerer alle utvalg.
    *
    * @param aar
    * @return
    * @throws SQLException
    */
  // public List<Utvalg> getUtvalg(Integer dep, Integer aar, Integer type, Integer funk, Integer geo) throws SQLException {
   public List<Utvalg> getUtvalg(Integer dep, Integer aar, Integer type, Integer geo, List<Integer> hovedfunksjon_2003, List<Integer> hovedfunksjon_2018) throws SQLException {
      List<Utvalg> utvalg = new ArrayList<Utvalg>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;

     /*
      String sqlSelect = "select distinct u.hovedfunksjon, u.unr, u.unavn, u.opprettelsesaar, u.opphoersaar, "
              + "h.tekst as hovedfunksjon "
              + "from t_forvaltning_utvalg as u "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode "
              + "left join t_forvaltning_utvalg_tid as td on u.unr = td.unr "
              + "where u.unr is not null and td.aar < 2004 " // dummy

              + (dep != null ? "and (u.f_odep = ? or u.unr in (select unr from t_forvaltning_utvalg_tid where f_odep = ?)) " : "")
              + (aar != null ? "and u.opprettelsesaar = ? " : "")
              + (type != null ? "and u.utvalgstype = ? " : "")
              //+ (funk != null ? "and u.hovedfunksjon = ? " : "")
              + (geo != null ? "and u.geografi = ? " : "")
              + " union  "
              + "select distinct u.hovedfunksjon, u.unr, u.Organets_navn collate database_default, u.opprettelsesaar, u.opphoersaar, h.tekst as hovedfunksjon "
              + "from t_forvaltning_utvalg2018_tid u "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon2018 as h on u.hovedfunksjon = h.kode "
              + "where u.unr is not null "

              + (dep != null ? "and (u.f_odep = ? or u.unr in (select unr from t_forvaltning_utvalg_tid where f_odep = ?)) " : "")
              + (aar != null ? "and u.opprettelsesaar = ? " : "")
              + (type != null ? "and u.utvalgstype = ? " : "")
              //+ (funk != null ? "and u.hovedfunksjon = ? " : "")
              + (geo != null ? "and u.geografi = ? " : "")
              + "order by u.hovedfunksjon, u.opprettelsesaar, u.opphoersaar";
      */
      String sqlSelect = "select distinct u.hovedfunksjon, u.unr, u.unavn, u.opprettelsesaar, u.opphoersaar, "
              + "h.tekst as hovedfunksjon, 'aar2003' as utvalgaar "
              + "from t_forvaltning_utvalg as u "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode "
              + "left join t_forvaltning_utvalg_tid as td on u.unr = td.unr "
              + "where u.unr is not null and td.aar < 2004 " // dummy

              + (dep != null ? "and (u.f_odep = ? or u.unr in (select unr from t_forvaltning_utvalg_tid where f_odep = ?)) " : "")
              + (aar != null ? "and u.opprettelsesaar = ? " : "")
              + (type != null ? "and u.utvalgstype = ? " : "")
              //+ (funk != null ? "and u.hovedfunksjon = ? " : "")
              + (geo != null ? "and u.geografi = ? " : "");
      if(hovedfunksjon_2003 != null && hovedfunksjon_2003.size()>0 ) {
         sqlSelect += " and u.hovedfunksjon IN ( ";
         for (int i = 0; i < hovedfunksjon_2003.size(); i++) {
            sqlSelect += hovedfunksjon_2003.get(i);
            sqlSelect += " , ";
         }
         sqlSelect += " 999999999 ) ";
      }
      else if(hovedfunksjon_2003 != null && hovedfunksjon_2003.size()==0 && hovedfunksjon_2018.size()>0){sqlSelect += " and u.hovedfunksjon IN ( 999999999 ) ";;}
         sqlSelect +=
               " union  "
              + "select distinct u.hovedfunksjon, u.unr, u.Organets_navn, u.opprettelsesaar, u.opphoersaar, h.tekst as hovedfunksjon, 'aar2004' as utvalgaar "
              + "from t_forvaltning_utvalg2018_tid u "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon2018 as h on u.hovedfunksjon = h.kode "
              + "where u.unr is not null "

              + (dep != null ? "and (u.f_odep = ? or u.unr in (select unr from t_forvaltning_utvalg2018_tid where f_odep = ?)) " : "")
              + (aar != null ? "and u.opprettelsesaar = ? " : "")
              + (type != null ? "and u.utvalgstype = ? " : "")
              //+ (funk != null ? "and u.hovedfunksjon = ? " : "")
              + (geo != null ? "and u.geografi = ? " : "");
         if(hovedfunksjon_2018!= null && hovedfunksjon_2018.size()>0) {
            sqlSelect += " and u.hovedfunksjon IN ( ";
            for (int i = 0; i < hovedfunksjon_2018.size(); i++) {
               sqlSelect += hovedfunksjon_2018.get(i);
               sqlSelect += " , ";
            }
            sqlSelect += " 999999999 ) ";
         }
         else if(hovedfunksjon_2018 != null && hovedfunksjon_2018.size()==0 && hovedfunksjon_2003.size()>0){sqlSelect += " and u.hovedfunksjon IN ( 999999999 ) ";;}
            sqlSelect +=
               //"order by u.hovedfunksjon, u.opprettelsesaar, u.opphoersaar";
               //"order by u.opprettelsesaar desc, u.opphoersaar, u.hovedfunksjon";
               "order by opprettelsesaar desc, opphoersaar";
      List values = new ArrayList();

      if (dep != null) {
         values.add(dep);
         values.add(dep);
      }
      if (aar != null) {
         values.add(aar);
      }
      if (type != null) {
         values.add(type);
      }
      /*if (funk != null) {
         values.add(funk);
      }*/
      if (geo != null) {
         values.add(geo);
      }
      //union
      if (dep != null) {
         values.add(dep);
         values.add(dep);
      }
      if (aar != null) {
         values.add(aar);
      }
      if (type != null) {
         values.add(type);
      }
      /*if (funk != null) {
         values.add(funk);
      }*/
      if (geo != null) {
         values.add(geo);
      }
      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Utvalg u = new Utvalg();

         u.setUnr((Integer) rad.get("unr"));
         u.setNavn((String) rad.get("unavn"));
         u.setHovedfunksjon((String) rad.get("hovedfunksjon"));
         u.setOpprettelsesaar((Integer) rad.get("opprettelsesaar"));
         u.setOpphoersaar((Integer) rad.get("opphoersaar"));
         if(((String) rad.get("utvalgaar")).equals("aar2003")){
            u.setUtvalg2003((String) rad.get("utvalgaar"));
         }
         if(((String) rad.get("Utvalgaar")).equals("aar2004")){
            u.setUtvalg2004((String) rad.get("utvalgaar"));
         }

         utvalg.add(u);
      }
      return utvalg;
   }

   
   public List<Utvalg> getUtvalgForEnhet(Integer idnum) throws SQLException {
      List<Utvalg> utvalg = new ArrayList<Utvalg>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      /*
      String sqlSelect = "select u.unr, u.unavn, u.opprettelsesaar, u.opphoersaar, h.tekst as hovedfunksjon "
              + "from t_forvaltning_utvalg as u "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode "
              + "inner join t_forvaltning_utvalg_enhet as e on u.unr = e.unr "
              + "where e.idnum = ? "
              + "order by u.hovedfunksjon, u.opprettelsesaar, u.opphoersaar";
         */
      String sqlSelect = "select distinct u.unr, u.unavn, u.opprettelsesaar, u.opphoersaar, h.tekst as hovedfunksjon, 'aar2003' as utvalgaar \n" +
              "       from t_forvaltning_utvalg as u \n" +
              "       left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode \n" +
              "\t   left join t_forvaltning_utvalg_tid as td on u.unr = td.unr \n" +
              "       inner join t_forvaltning_utvalg_enhet as e on u.unr = e.unr \n" +
              "       where e.idnum = ? and td.aar < 2004\n" +
              "\t   union \n" +
              "\t   select u.unr,  u.Organets_navn, u.opprettelsesaar, u.opphoersaar, h.tekst as hovedfunksjon, 'aar2004' as utvalgaar \n" +
              "       from t_forvaltning_utvalg2018_tid as u \n" +
              "       left join t_forvaltning_utvalg_dok_hovedfunksjon2018 as h on u.hovedfunksjon = h.kode \n" +
              "       inner join t_forvaltning_utvalg_enhet as e on u.unr = e.unr \n" +
              "\t   where e.idnum = ?\n" +
              "       order by hovedfunksjon, opprettelsesaar, opphoersaar";
      List values = new ArrayList();
      values.add(idnum);
      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Utvalg u = new Utvalg();

         u.setUnr((Integer) rad.get("unr"));
         u.setNavn((String) rad.get("unavn"));
         u.setHovedfunksjon((String) rad.get("hovedfunksjon"));
         u.setOpprettelsesaar((Integer) rad.get("opprettelsesaar"));
         u.setOpphoersaar((Integer) rad.get("opphoersaar"));
         if(((String) rad.get("utvalgaar")).equals("aar2003")){
            u.setUtvalg2003((String) rad.get("utvalgaar"));
         }
         if(((String) rad.get("Utvalgaar")).equals("aar2004")){
            u.setUtvalg2004((String) rad.get("utvalgaar"));
         }

         utvalg.add(u);
      }
      return utvalg;
   }
   
   
   
   /**
    * Returnerer personene søket treffer på.
    *
    * @param s
    * @return
    * @throws SQLException
    */
   public List<Utvalgperson> sokPerson(String s) throws SQLException {
      List<Utvalgperson> personer = new ArrayList<Utvalgperson>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
     /*
     //thiis is orginal from hovard
      String sqlSelect = "select pnr, kjoenn, foedselsaar, etternavn, fornavn "
              + "from t_forvaltning_utvalg_person "
              + "where lower(etternavn) like lower(?) or lower(fornavn) like lower(?)"
              + "order by etternavn, fornavn, foedselsaar, pnr";
      */

     /*
     //this after uinion with the new data but not included descriptions
      String sqlSelect = "select distinct t_forvaltning_utvalg_person.pnr, kjoenn, foedselsaar, etternavn, fornavn   "
              + "from t_forvaltning_utvalg_person "
              + "left join t_forvaltning_utvalg_medlem on t_forvaltning_utvalg_medlem.pnr = t_forvaltning_utvalg_person.pnr "
              + "left join t_forvaltning_utvalg_tid on t_forvaltning_utvalg_tid.unr = t_forvaltning_utvalg_medlem.unr "
              + "where t_forvaltning_utvalg_tid.aar < 2004 and lower(etternavn) like lower(?) or lower(fornavn) like lower(?) "
              + "union "
              + "select pnr, kjoenn, foedselsaar, etternavn, fornavn "
              + "from t_forvaltning_utvalgperson2018 "
              + "where  lower(etternavn) like lower(?) or lower(fornavn) like lower(?)"
              + "order by etternavn, fornavn, foedselsaar, pnr";
*/

      String sqlSelect = "select distinct t_forvaltning_utvalg_person.pnr, t_forvaltning_utvalg_person.kjoenn, t_forvaltning_utvalg_person.foedselsaar, etternavn, fornavn, "
              + " t_forvaltning_utvalg.unr, t_forvaltning_utvalg.unavn, h.tekst as hovedfunksjon, 'aar2003' as utvalgaar "
              + "from t_forvaltning_utvalg_person "
              + "left join t_forvaltning_utvalg_medlem on t_forvaltning_utvalg_medlem.pnr = t_forvaltning_utvalg_person.pnr "
              + "left join t_forvaltning_utvalg_tid on t_forvaltning_utvalg_tid.unr = t_forvaltning_utvalg_medlem.unr  "
              +  " left join t_forvaltning_utvalg on t_forvaltning_utvalg_medlem.unr = t_forvaltning_utvalg.unr "
              +  " left join t_forvaltning_utvalg_dok_hovedfunksjon as h on t_forvaltning_utvalg.hovedfunksjon = h.kode "
              + "where t_forvaltning_utvalg_tid.aar < 2004 and lower(etternavn) like lower(?) or lower(fornavn) like lower(?) "
              + "union "
              + "select t_forvaltning_utvalgperson2018.pnr, t_forvaltning_utvalgperson2018.kjoenn, t_forvaltning_utvalgperson2018.foedselsaar, t_forvaltning_utvalgperson2018.etternavn, t_forvaltning_utvalgperson2018.fornavn, "
              + "  t_forvaltning_utvalg2018.unr, t_forvaltning_utvalg2018.Organets_navn as unavn, h.tekst as hovedfunksjon, 'aar2004' as utvalgaar  "
              + "from t_forvaltning_utvalgperson2018 "
              + " left join t_forvaltning_utvalgmedlem2018 on t_forvaltning_utvalgmedlem2018.pnr = t_forvaltning_utvalgperson2018.pnr "
              + " left join t_forvaltning_utvalg2018  on t_forvaltning_utvalgmedlem2018.unr = t_forvaltning_utvalg2018.unr "
              + " left join t_forvaltning_utvalg_dok_hovedfunksjon2018 as h on t_forvaltning_utvalg2018.hovedfunksjon_id = h.kode "
              + "where  lower(t_forvaltning_utvalgperson2018.etternavn) like lower(?) or lower(t_forvaltning_utvalgperson2018.fornavn) like lower(?)"
              + "order by etternavn, fornavn, foedselsaar, pnr";

              List values = new ArrayList();

      values.add("%" + s + "%");
      values.add("%" + s + "%");
      values.add("%" + s + "%");
      values.add("%" + s + "%");

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Utvalgperson p = new Utvalgperson();
         personer.add(p);

         p.setPnr((Integer) rad.get("pnr"));
         p.setKjoenn((Integer) rad.get("kjoenn"));
         p.setFoedselsaar((Integer) rad.get("foedselsaar"));
         p.setFornavn((String) rad.get("fornavn"));
         p.setEtternavn((String) rad.get("etternavn"));
         p.setUtvalgnavn((String) rad.get("unavn"));
         p.setHovedfunksjon((String) rad.get("hovedfunksjon"));
         if(((String) rad.get("utvalgaar")).equals("aar2003")){
            p.setUtvalg2003((String) rad.get("utvalgaar"));
         }
         if(((String) rad.get("Utvalgaar")).equals("aar2004")){
            p.setUtvalg2004((String) rad.get("utvalgaar"));
         }

      }
      return personer;
   }

   /**
    * Returnerer tidsavhengig info til et bestemt utvalg.
    *
    * @param unr
    * @return
    * @throws SQLException
    */
   public List<Utvalg> getUtvalgTid(Integer unr) throws SQLException {
      List<Utvalg> tid = new ArrayList<Utvalg>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select t.aar, t.f_odep, t.antall_medlemmer, t.antall_varamedlemmer, t.antall_moeter, t.utgifter, "
              + "ud.tekst as utgiftsdekning "
              + "from t_forvaltning_utvalg_tid as t "
              + "left join t_forvaltning_utvalg_dok_utgiftsdekning as ud on t.utgiftsdekning = ud.kode "
              + "where t.aar < 2004 and t.unr = ? "
              + " union "
              + " select  t.aar, t.f_odep, null, null, null, null, null "
              + " from t_forvaltning_utvalg2018_tid t "
              + "where t.unr = ? "
              + "order by aar";

      List values = new ArrayList();

      values.add(unr);
      values.add(unr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      List<EndringCache> alleEndringer = EndringCacheFactory.getEndringer(this.conn);
      EnhetLogikk logikk = new EnhetLogikk();

      for (SortedMap rad : rader) {
         Utvalg u = new Utvalg();
         Enhet odep = new Enhet();
         u.setOdep(odep);
         tid.add(u);

         u.setUnr(unr);
         odep.setIdnum((Integer) rad.get("f_odep"));
         u.setAar((Integer) rad.get("aar"));
         u.setAntallMedlemmer((Integer) rad.get("antall_medlemmer"));
         u.setAntallVaramedlemmer((Integer) rad.get("antall_varamedlemmer"));
         u.setAntallMoeter((Integer) rad.get("antall_moeter"));
         u.setUtgifter((Integer) rad.get("utgifter"));
         u.setUtgiftsdekning((String) rad.get("utgiftsdekning"));

         Dato dato = new Dato(u.getAar(), 1, 1);
         odep.setTidspunkt(dato.getDate());
         logikk.getEnhetNavn(odep, alleEndringer);
      }

      return tid;
   }

   /**
    * Returnerer alle medlemmer til et bestemt utvalg.
    *
    * @param unr
    * @return
    * @throws SQLException
    */
   public List<Utvalgmedlemskap> getUtvalgMedlem(Integer unr) throws SQLException {
      List<Utvalgmedlemskap> medlem = new ArrayList<Utvalgmedlemskap>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select to_timestamp(m.oppnevnt_foerste_gang) as fra_dato, to_timestamp(m.oppnevnt_siste_gang) as til_dato, m.aar, m.bostedsfylke, m.bostedskommune, m.stilling, "
              + "p.pnr, p.kjoenn, p.foedselsaar, p.etternavn, p.fornavn, "
              + "v.tekst as verv, o.tekst as oppnevningsmaate   "
              + "from t_forvaltning_utvalg_medlem as m "
              + "left join t_forvaltning_utvalg_person as p on m.pnr = p.pnr "
              + "left join t_forvaltning_utvalg_dok_verv as v on m.verv = v.kode "
              + "left join t_forvaltning_utvalg_dok_oppnevningsmaate as o on m.oppnevningsmaate = o.kode "
              + "where m.aar < 2004 and m.unr = ? and (m.aar >= m.oppnevnt_foerste_gang OR m.oppnevnt_foerste_gang IS NULL )  and  (m.oppnevnt_siste_gang >= m.aar  OR m.oppnevnt_siste_gang IS NULL ) "
              + "union "
              + "select distinct m.fra_dato, m.til_dato, m.aar, (null || 0)::INTEGER as bostedflyke, (m.Postnummer || 0)::INTEGER as bostedskommune, m.Stilling  as stilling, p.pnr,  "
              + "p.kjoenn, p.foedselsaar, p.etternavn, p.fornavn, null as verv, td.Oppnevnt_av AS oppnevningsmaate   "
              + "from t_forvaltning_utvalgmedlem2018 m "
              + "left join t_forvaltning_utvalgperson2018 p on p.pnr = m.pnr "
              + "left join t_forvaltning_utvalg2018_tid td on td.unr = m.unr "
              + "where m.unr = ? and m.aar >= extract(year from m.fra_dato) and  (extract(year from m.til_dato) >= m.aar  OR m.til_dato IS NULL )   "
              + "order by aar, bostedskommune";

      List values = new ArrayList();

      values.add(unr);
      values.add(unr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Kommune k = new Kommune();
         Utvalgperson p = new Utvalgperson();
         Utvalgmedlemskap u = new Utvalgmedlemskap();
         u.setKommune(k);
         u.setPerson(p);
         medlem.add(u);

         p.setPnr((Integer) rad.get("pnr"));
         p.setKjoenn((Integer) rad.get("kjoenn"));
         p.setFoedselsaar((Integer) rad.get("foedselsaar"));
         p.setFornavn((String) rad.get("fornavn"));
         p.setEtternavn((String) rad.get("etternavn"));

         k.setKode((Integer) rad.get("bostedskommune"));

         u.setAar((Integer) rad.get("aar"));
         u.setFylkenummer((Integer) rad.get("bostedsfylke"));
         u.setStilling((String) rad.get("stilling"));
         u.setVerv((String) rad.get("verv"));
         u.setOppnevningsmaate((String) rad.get("oppnevningsmaate"));

          String fradato = null;
          String tildato = null;
          String fradato2 = null;
          String tildato2 = null;

          SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
          SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
         if(rad.get("fra_dato")!=null ) {fradato= format.format(rad.get("fra_dato"));}
          if(rad.get("til_dato")!=null ){tildato= format.format(rad.get("til_dato"));}
          if(rad.get("fra_dato")!=null ) {fradato2= formatNowYear.format(rad.get("fra_dato"));}
          if(rad.get("til_dato")!=null ){tildato2= formatNowYear.format(rad.get("til_dato"));}

          if((Integer) rad.get("aar")<2004 ) {
              u.setFradato(fradato2);
              u.setTildato(tildato2);
         }else{
             u.setFradato(fradato);
             u.setTildato(tildato);
         }

         //if(tildato!=null && (tildato=="01.01.1900" ||tildato.equals("01.01.1900"))){u.setTildato("-");}else{ u.setTildato(tildato);}

      }

      return medlem;
   }

   /**
    * Returnerer en gitt person.
    *
    * @param pnr
    * @return
    * @throws SQLException
    */
   public Utvalgperson getPerson(Integer pnr) throws SQLException {
      Utvalgperson person = new Utvalgperson();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select kjoenn, foedselsaar, etternavn, fornavn, 'aar2003' as utvalgaar "
              + "from t_forvaltning_utvalg_person "
              + "where pnr = ? "
              + "union "
              + "select kjoenn, foedselsaar, etternavn, fornavn, 'aar2004' as utvalgaar from t_forvaltning_utvalgperson2018  "
              + "where pnr = ? ";

      List values = new ArrayList();

      values.add(pnr);
      values.add(pnr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      SortedMap rad = rader[0];

      person.setPnr(pnr);
      person.setKjoenn((Integer) rad.get("kjoenn"));
      person.setFoedselsaar((Integer) rad.get("foedselsaar"));
      person.setFornavn((String) rad.get("fornavn"));
      person.setEtternavn((String) rad.get("etternavn"));
      if(((String) rad.get("utvalgaar")).equals("aar2003")){
         person.setUtvalg2003((String) rad.get("utvalgaar"));
      }
      if(((String) rad.get("utvalgaar")).equals("aar2004")){
         person.setUtvalg2004((String) rad.get("utvalgaar"));
      }

      return person;
   }

   /**
    * Returnerer alle medlemmer til en gitt person.
    *
    * @param pnr
    * @return
    * @throws SQLException
    */
   public List<Utvalgmedlemskap> getPersonMedlem(Integer pnr) throws SQLException {
      List<Utvalgmedlemskap> medlem = new ArrayList<Utvalgmedlemskap>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
     /* String sqlSelect = "select m.aar, m.bostedsfylke, m.bostedskommune, m.stilling, "
              + "u.unr, u.unavn, h.tekst as hovedfunksjon, "
              + "v.tekst as verv, o.tekst as oppnevningsmaate "
              + "from t_forvaltning_utvalg_medlem as m "
              + "left join t_forvaltning_utvalg as u on m.unr = u.unr "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode "
              + "left join t_forvaltning_utvalg_dok_verv as v on m.verv = v.kode "
              + "left join t_forvaltning_utvalg_dok_oppnevningsmaate as o on m.oppnevningsmaate = o.kode "
              + "where m.pnr = ? "
              + "order by m.aar, u.unr";
            */
      String sqlSelect = "select distinct to_timestamp(m.oppnevnt_foerste_gang)  as fra_dato, to_timestamp(m.oppnevnt_siste_gang) as til_dato, m.aar, m.bostedsfylke, m.bostedskommune, m.stilling, "
              + "u.unr, u.unavn, h.tekst as hovedfunksjon, "
              + "v.tekst as verv, o.tekst as oppnevningsmaate "
              + "from t_forvaltning_utvalg_medlem as m "
              + "left join t_forvaltning_utvalg as u on m.unr = u.unr "
              + "left join t_forvaltning_utvalg_dok_hovedfunksjon as h on u.hovedfunksjon = h.kode "
              + "left join t_forvaltning_utvalg_dok_verv as v on m.verv = v.kode "
              + "left join t_forvaltning_utvalg_dok_oppnevningsmaate as o on m.oppnevningsmaate = o.kode "
              + "left join t_forvaltning_utvalg_tid as td on u.unr = td.unr  "
              + "where td.aar < 2004 and m.pnr = ? and (m.aar >= m.oppnevnt_foerste_gang OR m.oppnevnt_foerste_gang IS NULL )  and  (m.oppnevnt_siste_gang >= m.aar  OR m.oppnevnt_siste_gang IS NULL ) "
              + "union  "
              + "select m.fra_dato, m.til_dato, m.aar, (null || 0)::INTEGER as bostedsfylke, (m.Postnummer || 0)::INTEGER as bostedskommune, m.Stilling  as stilling, "
              + " u.unr, u.Organets_navn  as unavn, h.tekst as hovedfunksjon, null as verv, o.tekst as oppnevningsmaate "
              + " from t_forvaltning_utvalgmedlem2018 as m "
              + " left join t_forvaltning_utvalg2018 as u on m.unr = u.unr "
              + " left join t_forvaltning_utvalg_dok_hovedfunksjon2018 as h on u.hovedfunksjon_id = h.kode "
              + " left join t_forvaltning_utvalg_dok_oppnevningsmaate as o on m.oppnevningsmaate = o.kode "
              + " where m.pnr = ? and m.aar >= extract(year from m.fra_dato) and  (extract(year from m.til_dato) >= m.aar  OR m.til_dato IS NULL )  "
              + "order by aar, unr";


      List values = new ArrayList();

      values.add(pnr);
      values.add(pnr);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Kommune k = new Kommune();
         Utvalg u = new Utvalg();
         Utvalgmedlemskap m = new Utvalgmedlemskap();
         m.setKommune(k);
         m.setUtvalg(u);
         medlem.add(m);

         u.setUnr((Integer) rad.get("unr"));
         u.setNavn((String) rad.get("unavn"));
         u.setHovedfunksjon((String) rad.get("hovedfunksjon"));

         k.setKode((Integer) rad.get("bostedskommune"));

         m.setAar((Integer) rad.get("aar"));
         m.setFylkenummer((Integer) rad.get("bostedsfylke"));
         m.setStilling((String) rad.get("stilling"));
         m.setVerv((String) rad.get("verv"));
         m.setOppnevningsmaate((String) rad.get("oppnevningsmaate"));

          String fradato = null;
          String tildato = null;
          String fradato2 = null;
          String tildato2 = null;


          SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
          SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
          if(rad.get("fra_dato")!=null ) {fradato= format.format(rad.get("fra_dato"));}
          if(rad.get("til_dato")!=null ){tildato= format.format(rad.get("til_dato"));}
          if(rad.get("fra_dato")!=null ) {fradato2= formatYear.format(rad.get("fra_dato"));}
          if(rad.get("til_dato")!=null ){tildato2= formatYear.format(rad.get("til_dato"));}

          if((Integer) rad.get("aar")<2004 ) {
              m.setFradato(fradato2);
              m.setTildato(tildato2);
          }else{
              m.setFradato(fradato);
              m.setTildato(tildato);
          }
          //if(tildato!=null && (tildato=="01.01.1900" ||tildato.equals("01.01.1900"))){m.setTildato("-");}else{ m.setTildato(tildato);}



      }

      return medlem;
   }

   /**
    * Returnerer departementer fra utavlgsarkivet.
    *
    * @return
    * @throws SQLException
    */
   public List<Enhet> getDepartement() throws SQLException {
      List<Enhet> dep = new ArrayList<Enhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct f_odep from t_forvaltning_utvalg where f_odep is not null "
              + "union "
              + "select distinct f_odep from t_forvaltning_utvalg_tid where f_odep is not null";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      List<EndringCache> alleEndringer = EndringCacheFactory.getEndringer(this.conn);
      EnhetLogikk logikk = new EnhetLogikk();

      for (SortedMap rad : rader) {
         Enhet d = new Enhet();
         dep.add(d);

         d.setIdnum((Integer) rad.get("f_odep"));

         // SQL-spørring fra dato.....
         int fraaar = 0;
         Result result_fra = null;
         SqlCommandBean sqlCB_fra = new SqlCommandBean();
         SortedMap[] rader_fra = null;
         String sqlSelect_fra = "select distinct extract(year from tidspunkt) as fra from t_forvaltning_endring where idnum = ? and endringskode::text like '1%'";
         List values_fra = new ArrayList();
         values_fra.add((Integer) rad.get("f_odep"));
         sqlCB_fra.setConnection(this.conn);
         sqlCB_fra.setSqlValue(sqlSelect_fra);
         sqlCB_fra.setValues(values_fra);
         result_fra = sqlCB_fra.executeQuery();
         rader_fra = result_fra.getRows();
         SortedMap rad_fra = rader_fra[0];

        /* fraaar = (Integer) rad_fra.get("fra");*/
         fraaar = (int)((double)rad_fra.get("fra"));
         d.setFraaar(fraaar);

         // SQL-spørring til dato.....
         Integer tilaar = null;
         Result result_til = null;
         SqlCommandBean sqlCB_til = new SqlCommandBean();
         SortedMap[] rader_til = null;
         String sqlSelect_til = "select distinct extract(year from tidspunkt) as til from t_forvaltning_endring where idnum = ? and endringskode::text like '3%'";
         List values_til = new ArrayList();
         values_til.add((Integer) rad.get("f_odep"));
         sqlCB_til.setConnection(this.conn);
         sqlCB_til.setSqlValue(sqlSelect_til);
         sqlCB_til.setValues(values_til);
         result_til = sqlCB_til.executeQuery();
         rader_til = result_til.getRows();
         try {
         SortedMap rad_til = rader_til[0];
         /*tilaar = (Integer) rad_til.get("til");*/
            tilaar = (int)((double)rad_til.get("til"));
         } catch (ArrayIndexOutOfBoundsException exception) {
         }
         d.setTilaar(tilaar);


         logikk.getEnhetNavn(d, alleEndringer);
      }

      Collections.sort(dep);

      return dep;
   }

   public List<DokCache> getGeografi() throws SQLException {
      return this.getUtvalgDok("t_forvaltning_utvalg_dok_geografi");
   }

   public List<DokCache> getHovedfunksjon() throws SQLException {
      return this.getUtvalgDok("t_forvaltning_utvalg_dok_hovedfunksjon");
   }
   public List<DokCache> getHovedfunksjon2018() throws SQLException {
      return this.getUtvalgDok("t_forvaltning_utvalg_dok_hovedfunksjon2018");
   }

   public List<DokCache> getUtvalgstype() throws SQLException {
      return this.getUtvalgDok("t_forvaltning_utvalg_dok_utvalgstype");
   }

   /**
    * Returnerer alle utvalg.
    *
    * @param aar
    * @return
    * @throws SQLException
    */
   private List<DokCache> getUtvalgDok(String tab) throws SQLException {
      List<DokCache> dok = new ArrayList<DokCache>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select kode, tekst from " + tab + " order by kode";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         DokCache d = new DokCache();
         dok.add(d);

         d.setKode((Integer) rad.get("kode"));
         d.setTekstEntall((String) rad.get("tekst"));
      }
      return dok;
   }

   /**
    * hente utvalgnr for gitt idnum.
    *
    * @param idnum
    * @throws SQLException
    */
   public List<Utvalg> getUtvalg_enhet(int idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_utvalg_enhet where idnum = ? and idnum is not null  ";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<Utvalg> utvalg = new ArrayList<Utvalg>();
      SortedMap[] rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Utvalg a = new Utvalg();
         a.setUnr((Integer) rad.get("unr"));
         //a.setIdnum((Integer) rad.get("idnum"));
         if( rad.get("utvalg_tom2003")!=null){
         a.setUtvalgtom2003((boolean) rad.get("utvalg_tom2003"));}
         if( rad.get("utvalg_fom2004")!=null){
         a.setUtvalgfom2004((boolean) rad.get("utvalg_fom2004"));}
         utvalg.add(a);
      }

      return utvalg;
   }


   /**
    * Registrerer nytt utvalgnr.
    *
    * @param idnum
    * @param utvalgnr
    * @throws SQLException
    */
   public void registrerNyttUtvalg(Integer utvalgnr, Integer idnum, boolean utvalgaar2003, boolean utvalgaar2004) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_utvalg_enhet (unr, idnum, utvalg_tom2003, utvalg_fom2004) values (?, ?, ?, ?)";
      List values = new ArrayList();

      values.add(utvalgnr);
      values.add(idnum);
      values.add(utvalgaar2003);
      values.add(utvalgaar2004);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Sletter en gitt utvalgnr for gitt idnum.
    *
    * @param idnum
    * @param utvalgnr
    * @throws SQLException
    */
   public void slettOrgnr(Integer utvalgnr, Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_utvalg_enhet where unr = ? and idnum = ?";
      List values = new ArrayList();

      values.add(utvalgnr);
      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

}
