package no.nsd.polsys.logikk.forvaltning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.OppgaveEnhet;

/**
 *
 * @author hvb
 */
public class OppgaveLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;


   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public List<Integer> getAlleOppgaveAar() throws SQLException {
      List<Integer> liste = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct aar from t_forvaltning_oppgave_enhet order by aar desc";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer aar = (Integer) rad.get("aar");
         liste.add(aar);
      }

      return liste;
   }

   /**
    * Returnerer mapping: oppgave-kode --> oppgave-tekst.
    *
    * @return
    * @throws SQLException
    */
   public Map<Integer, String> getOppgaver() throws SQLException {
      Map<Integer, String> oppgaver = new HashMap<Integer, String>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select kode, tekst from t_forvaltning_oppgave";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer kode = (Integer) rad.get("kode");
         String tekst = (String) rad.get("tekst");

         oppgaver.put(kode, tekst);
      }

      return oppgaver;
   }

   public List<OppgaveEnhet> getOppgaverTilEnheter(Integer aar) throws SQLException {
      List<OppgaveEnhet> liste = new ArrayList<OppgaveEnhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_oppgave_enhet where aar = ?";
      List<Integer> values = new ArrayList<Integer>();

      values.add(aar);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         OppgaveEnhet o = new OppgaveEnhet();
         Enhet e = new Enhet();
         o.setEnhet(e);

         e.setIdnum((Integer) rad.get("idnum"));

         o.setHovedoppgave((Integer) rad.get("hovedoppgave"));
         o.setBioppgave1((Integer) rad.get("bioppgave1"));
         o.setBioppgave2((Integer) rad.get("bioppgave2"));

         liste.add(o);
      }

      return liste;
   }

   public List<OppgaveEnhet> getOppgaverTilEnhet(Integer idnum) throws SQLException {
      List<OppgaveEnhet> liste = new ArrayList<OppgaveEnhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_oppgave_enhet where idnum = ? order by aar";
      List<Integer> values = new ArrayList<Integer>();

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
         OppgaveEnhet o = new OppgaveEnhet();

         o.setAar((Integer) rad.get("aar"));
         o.setHovedoppgave((Integer) rad.get("hovedoppgave"));
         o.setBioppgave1((Integer) rad.get("bioppgave1"));
         o.setBioppgave2((Integer) rad.get("bioppgave2"));

         liste.add(o);
      }

      return liste;
   }

   /**
    * Returnerer alle oppgaver til alle enheter for gitt år.
    *
    * @param aar
    * @return mapping: idnum --> oppgave.
    * @throws SQLException
    */
   public Map<Integer, OppgaveEnhet> getOppgaver(Integer aar) throws SQLException {
      Map<Integer, OppgaveEnhet> mapping = new HashMap<Integer, OppgaveEnhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_oppgave_enhet where aar = ?";
      List<Integer> values = new ArrayList<Integer>();

      values.add(aar);

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
         OppgaveEnhet o = new OppgaveEnhet();
         o.setHovedoppgave((Integer) rad.get("hovedoppgave"));
         o.setBioppgave1((Integer) rad.get("bioppgave1"));
         o.setBioppgave2((Integer) rad.get("bioppgave2"));

         mapping.put(idnum, o);
      }

      return mapping;
   }
}
