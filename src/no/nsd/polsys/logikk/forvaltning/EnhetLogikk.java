package no.nsd.polsys.logikk.forvaltning;

import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.factories.forvaltning.EndringCacheFactory;
import no.nsd.polsys.factories.forvaltning.RelasjonAndreCacheFactory;
import no.nsd.polsys.factories.forvaltning.RelasjonCacheFactory;
import no.nsd.polsys.factories.forvaltning.StortingetsaksnummerCacheFactory;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.*;

import javax.servlet.jsp.jstl.sql.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author hvb
 */
public class EnhetLogikk {

   // ============================================================== Variabler
   /**
    * Forbindelse til databasen.
    */
   private Connection conn;
   /**
    * Settes til true hvis en vil ha engelsk.
    */
   private boolean engelsk = false;
   /**
    * mapping: idnum --> enhet
    */
   Map<Integer, Enhet> enheter;

   // ============================================================ Konstruktør
   /**
    * Tom konstruktør.
    */
   public EnhetLogikk() {
   }

   // ================================================================ Metoder
   public void setConn(Connection conn) {
      this.conn = conn;
   }

   public void brukEngelsk() {
      this.engelsk = true;
   }

   /**
    * True hvis det eksisterer en enhet med gitt idnum. Nedlagt er irrelevant i
    * denne sammenhengen.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public boolean eksistererEnhet(Integer idnum) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_enhet where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return false;
      }

      return true;
   }

   /**
    * Returnerer siste navnet til en enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public String getSisteNavnTilEnhet(Integer idnum) throws SQLException {
      String navn = null;
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_endring where idnum = ? and langt_navn is not null order by tidspunkt desc";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      SortedMap rad = rader[0];
      if (this.engelsk) {
         navn = (String) rad.get("eng_langt_navn");
      } else {
         navn = (String) rad.get("langt_navn");
      }

      return navn;
   }

   /**
    * Returnerer siste registrerte info om en gitt enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public Enhet getEnhet(Integer idnum) throws SQLException {
      Enhet enhet = new Enhet();
      List<Enhet> endringer = this.getEndringer(idnum, false);

      List<EndringCache> alleEndringer = EndringCacheFactory.getEndringer(this.conn);

      if (endringer == null || endringer.isEmpty()) {
         return null;
      }

      Integer tilknytningsformVedOpprettelse = null;

      enhet.setIdnum(idnum);

      for (Enhet e : endringer) {
         // Nyopprettelse.
         if (e.getEndringskode() != null && (e.getEndringskode() / 100 == 1)) {
            enhet.setTidspunkt(e.getTidspunkt());
            enhet.setBekreftetDato(e.getBekreftetDato());
            enhet.setEndringskode(e.getEndringskode());
            enhet.setRelasjoner(e.getRelasjoner());
            enhet.setRelasjonerAndre(e.getRelasjonerAndre());
            enhet.setStortingetsaksnummer(e.getStortingetsaksnummer());
            enhet.setLangtNavnOpprettet(e.getLangtNavn());
            tilknytningsformVedOpprettelse = e.getTilknytningsform();
         }
         // Nedleggelse.
         if (e.getEndringskode() != null && (e.getEndringskode() / 100 == 3)) {
            enhet.setTidspunktNedlagt(e.getTidspunkt());
            enhet.setBekreftetDatoNedlagt(e.getBekreftetDato());
            enhet.setEndringskodeNedlagt(e.getEndringskode());
            enhet.setNedlagt(true);
            enhet.setRelasjonerNedlagt(e.getRelasjoner());
            enhet.setRelasjonerAndreNedlagt(e.getRelasjonerAndre());
         }

         if (e.getLangtNavn() != null) {
            enhet.setLangtNavn(e.getLangtNavn());
         }
         if (e.getKortNavn() != null) {
            enhet.setKortNavn(e.getKortNavn());
         }
         if (e.getEngelskLangtNavn() != null) {
            enhet.setEngelskLangtNavn(e.getEngelskLangtNavn());
         }
         if (e.getTilknytningsform() != null) {
            enhet.setTilknytningsform(e.getTilknytningsform());
            if (!e.getTilknytningsform().equals(tilknytningsformVedOpprettelse)) {
               enhet.setTidspunktTilknytningsform(e.getTidspunkt());
            } else {
               enhet.setTidspunktTilknytningsform(null);
            }
         }
         if (e.getCofog() != null) {
            enhet.setCofog(e.getCofog());
         }
         if (e.getNivaa() != null) {
            enhet.setNivaa(e.getNivaa());
         }
         if (e.getOverordnetIdnum() != null) {
            enhet.setOverordnetIdnum(e.getOverordnetIdnum());
         }
         if (e.getGrunnenhet() != null) {
            enhet.setGrunnenhet(e.getGrunnenhet());
         }
         if (e.getKommune() != null) {
            enhet.setKommune(e.getKommune());
         }
      }

      if (enhet.getOverordnetIdnum() != null) {
         Enhet o = new Enhet();
         o.setIdnum(enhet.getOverordnetIdnum());
         o.setTidspunkt(enhet.getTidspunktNedlagt());
         this.getEnhetNavn(o, alleEndringer);
         enhet.setOverordnetEnhet(o);
      }

      this.setEnheter(enhet.getTidspunktNedlagt());
      this.setOverordnetDepartement(enhet);
      this.setOverordnetReell(enhet);

      return enhet;
   }

   /**
    * Returnerer alle endringer til en enhet.
    *
    * @param dag
    * @return
    * @throws SQLException
    */
   public List<Enhet> getEndringer(Integer idnum, boolean arv) throws SQLException {

      // endringene til gitt enhet, som skal returneres fra metoden.
      List<Enhet> endringerEnhet = new ArrayList<Enhet>();

      // mapping idnum --> enhet.
      this.enheter = new HashMap<Integer, Enhet>(4000, 0.95f);
      EnhetUtilLogikk util = new EnhetUtilLogikk();
      util.setEnheter(this.enheter);

      // alle endringer i databasen.
      List<EndringCache> alleEndringer = EndringCacheFactory.getEndringer(this.conn);

      List<Kommune> alleKommuner = KommuneCacheFactory.getKommuner(this.conn);
      Map<Integer, List<Integer>> alleRelasjoner = RelasjonCacheFactory.getRelasjoner(this.conn);
      Map<Integer, List<Enhet>> alleRelasjonerAndre = RelasjonAndreCacheFactory.getRelasjoner(this.conn);
      Map<Integer, List<Integer>> alleStortingetsaksnummer = StortingetsaksnummerCacheFactory.getSortingetsaksnummer(this.conn);

      // alle endringer for en dag.
      List<Enhet> endringerDag = new ArrayList<Enhet>();

      Date dag = alleEndringer.get(0).getTidspunkt();

      Enhet forrigeEndring = null;

      // for-løkken finner aktuelle enheter.
      for (EndringCache endringCache : alleEndringer) {
         Enhet enhet = new Enhet(endringCache);
         if (this.engelsk) {
            enhet.setLangtNavn(endringCache.getEngelskLangtNavn());
         }
         // sjekker om vi har gått frem en dag.
         if (enhet.getTidspunkt() != null && dag != null && enhet.getTidspunkt().after(dag)) {

            if (forrigeEndring != null) {
               Enhet o = this.enheter.get(forrigeEndring.getOverordnetIdnum());
               if (o != null) {
                  forrigeEndring.setOverordnetEnhet(new Enhet(o));
               }
               this.setOverordnetReell(forrigeEndring);
               forrigeEndring = null;
            }

            Enhet gittEnhet = this.enheter.get(idnum);
            // hvis enheten er opprettet.
            if (gittEnhet != null) {
               // hvis enheten er nedlagt --> ferdig.
               if (gittEnhet.isNedlagt()) {
                  break;
               }

               if (arv) {
                  util.arvEndringer(gittEnhet, endringerDag, endringerEnhet);
               }

            }

            // vi tømmer endringene for forrige dag.
            endringerDag.clear();
            dag = enhet.getTidspunkt();
         }


         endringerDag.add(enhet);



         Enhet eksisterende = this.enheter.get(enhet.getIdnum());
         if (eksisterende == null) {
            eksisterende = new Enhet();
            eksisterende.setIdnum(enhet.getIdnum());
            eksisterende.setTidspunkt(enhet.getTidspunkt());
            eksisterende.setEndringskode(enhet.getEndringskode());
            eksisterende.setBekreftetDato(enhet.getBekreftetDato());
            this.enheter.put(enhet.getIdnum(), eksisterende);
         }
         if (enhet.getEndringskode() != null && (enhet.getEndringskode().intValue() / 100 == 3)) {
            eksisterende.setNedlagt(true);
            eksisterende.setTidspunktNedlagt(enhet.getTidspunkt());
         }
         if (enhet.getTilknytningsform() != null) {
            eksisterende.setTilknytningsform(enhet.getTilknytningsform());
         }
         if (enhet.getNivaa() != null) {
            eksisterende.setNivaa(enhet.getNivaa());
         }
         if (enhet.getOverordnetIdnum() != null) {
            eksisterende.setOverordnetIdnum(enhet.getOverordnetIdnum());
         }
         if (enhet.getGrunnenhet() != null) {
            eksisterende.setGrunnenhet(enhet.getGrunnenhet());
         }
         if (enhet.getLangtNavn() != null) {
            eksisterende.setLangtNavn(enhet.getLangtNavn());
         }
         if (enhet.getKortNavn() != null) {
            eksisterende.setKortNavn(enhet.getKortNavn());
         }
         if (enhet.getCofog() != null) {
            eksisterende.setCofog(enhet.getCofog());
         }
         if (enhet.getKommunenummer() != null) {
            eksisterende.setKommunenummer(enhet.getKommunenummer());
            eksisterende.setFylkenummer(enhet.getKommunenummer() / 100);
         }
         if (enhet.getIdnum().equals(idnum)) {

            if (enhet.getOverordnetIdnum() != null) {
               forrigeEndring = enhet;
            }
            if (enhet.getKommunenummer() != null) {
               Kommune k = new Kommune();
               k.setKode(enhet.getKommunenummer());
               Kommune komplett = this.getKommune(alleKommuner, enhet.getKommunenummer(), enhet.getTidspunkt());
               if (komplett != null) {
                  enhet.setKommune(komplett);
               } else {
                  enhet.setKommune(k);
               }
            }

            List<Integer> relasjoner = alleRelasjoner.get(enhet.getEndringsid());
            if (relasjoner != null) {
               List<Enhet> enhetRel = new ArrayList<Enhet>();
               enhet.setRelasjoner(enhetRel);
               for (Integer i : relasjoner) {
                  Enhet e = new Enhet();
                  e.setIdnum(i);
                  e.setTidspunkt(enhet.getTidspunkt());
                  this.getEnhetNavn(e, alleEndringer);
                  enhetRel.add(e);
               }
            }
            /*stortinget saksnummer */
            List<Integer> stortingetsaksnummer = alleStortingetsaksnummer.get(enhet.getEndringsid());
            if (stortingetsaksnummer != null) {
               List<Enhet> saksnummer = new ArrayList<Enhet>();
               enhet.setStortingetsaksnummer(saksnummer);
               for (Integer i : stortingetsaksnummer) {
                  Enhet e = new Enhet();
                  e.setIdnum(i);
                  e.setTidspunkt(enhet.getTidspunkt());
                  this.getEnhetNavn(e, alleEndringer);
                  saksnummer.add(e);
               }
            }

            enhet.setRelasjonerAndre(alleRelasjonerAndre.get(enhet.getEndringsid()));

            endringerEnhet.add(enhet);
         }


      }

      this.setDok(endringerEnhet);

      return endringerEnhet;
   }

   /**
    * Legger til dokumentasjon fra basen til gitt liste med endringer.
    *
    * @param endringer
    * @throws SQLException
    */
   private void setDok(List<Enhet> endringer) throws SQLException {

      if (endringer == null || endringer.isEmpty()) {
         return;
      }

      // mapping: id --> kommentar.
      Map<Integer, Enhet> kommentarer = new HashMap<Integer, Enhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;

      String idlist = "";
      for (int i = 0; i < endringer.size(); i++) {
         idlist += endringer.get(i).getEndringsid();
         if (i < endringer.size() - 1) {
            idlist += ", ";
         }
      }

      // SQL-spørring.
      String sqlSelect = "select id, dok, eng_dok from t_forvaltning_endring where id in (" + idlist + ")";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return;
      }

      for (SortedMap rad : rader) {
         Enhet enhet = new Enhet();
         Integer id = (Integer) rad.get("id");
         enhet.setDok((String) rad.get("dok"));
         enhet.setEngelskDok((String) rad.get("eng_dok"));

         kommentarer.put(id, enhet);
      }

      for (Enhet e : endringer) {
         Enhet k = kommentarer.get(e.getEndringsid());
         if (k != null) {
            e.setDok(k.getDok());
            e.setEngelskDok(k.getEngelskDok());
         }
      }

   }

   // Setter det siste navnet til en enhet.
   public void getEnhetNavn(Enhet enhet, List<EndringCache> alleEndringer) {
      for (EndringCache e : alleEndringer) {
         if (enhet.getLangtNavn() != null && enhet.getTidspunkt() != null && e.getTidspunkt().after(enhet.getTidspunkt())) {
            break;
         }
         if (enhet.getIdnum().equals(e.getIdnum()) && e.getLangtNavn() != null) {
            if (this.engelsk) {
               enhet.setLangtNavn(e.getEngelskLangtNavn());
            } else {
               enhet.setLangtNavn(e.getLangtNavn());
            }
         }
      }
   }

   /**
    * Returnerer organisasjonsnummeret til en enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public List<Integer> getOrgnrTilEnhet(Integer idnum) throws SQLException {
      // returneres fra denne metoden.
      List<Integer> orgnr = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_orgnr where idnum = ? order by id desc";
      List values = new ArrayList();

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
         orgnr.add((Integer) rad.get("orgnr"));
      }
      return orgnr;
   }
   /**
    * Returnerer virksomhetsnummer til en enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public List<Integer> getVirksomhetsnummerTilEnhet(Integer idnum) throws SQLException {
      // returneres fra denne metoden.
      List<Integer> virksomhetsnummer = new ArrayList<Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_orgnr where idnum = ? and virksomhetsnummer is not null order by id desc";
      List values = new ArrayList();

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
         virksomhetsnummer.add((Integer) rad.get("virksomhetsnummer"));
      }
      return virksomhetsnummer;
   }
   /**
    * Returnerer andre navn (kallenavn) til en enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public Object[][] getAndreNavnTilEnhet(Integer idnum) throws SQLException {
      // i = id, j = navn.
      Object[][] navn = null;
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_enhet_navn where idnum = ? order by id desc";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      navn = new Object[rader.length][2];

      for (int i = 0; i < rader.length; i++) {
         navn[i][0] = rader[i].get("id");
         navn[i][1] = rader[i].get("navn");
      }
      return navn;
   }

   /**
    * Returnerer alle ansattekoblinger til gitt enhet.
    *
    * @param idnum
    * @return
    * @throws SQLException
    */
   public List<Ansatte> getAnsatteKoblingTilEnhet(Integer idnum) throws SQLException {
      // returneres fra denne metoden.
      List<Ansatte> liste = new ArrayList<Ansatte>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select * from t_forvaltning_enhet_ansatte_kobling where idnum = ? order by ansatte_etatskode";
      List values = new ArrayList();

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
         Ansatte a = new Ansatte();
         liste.add(a);

         a.setId((Integer) rad.get("id"));
         a.setEtatkode((String) rad.get("ansatte_etatskode"));
         a.setFraAar((Integer) rad.get("fra_aar"));
         a.setTilAar((Integer) rad.get("til_aar"));
         a.setDok((String) rad.get("dok"));
      }

      return liste;
   }

   /**
    * Returnerer alle idnum-koblinger til gitt sst-etat og �r.
    *
    * @param etatkode - sst-etatkode.
    * @param aar
    * @return
    * @throws SQLException
    */
   public List<Enhet> getEnheterTilEtatAnsattKobling(String etatkode, Integer aar) throws SQLException {
      // returneres fra denne metoden.
      List<Enhet> liste = new ArrayList<Enhet>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct idnum from t_forvaltning_enhet_ansatte_kobling "
              + "where ? like ansatte_etatskode "
              + "and (fra_aar is null or fra_aar <= ?) and (til_aar is null or til_aar >= ?)";
      List values = new ArrayList();

      values.add(etatkode);
      values.add(aar);
      values.add(aar);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      List<EndringCache> alleEndringer = EndringCacheFactory.getEndringer(this.conn);

      for (SortedMap rad : rader) {
         Enhet e = new Enhet();
         liste.add(e);
         e.setIdnum((Integer) rad.get("idnum"));

         Calendar cal = new GregorianCalendar();
         cal.setLenient(false);
         cal.clear();
         cal.set(aar, 0, 1); // 1.1.aar

         e.setTidspunkt(cal.getTime());

         this.getEnhetNavn(e, alleEndringer);
      }

      return liste;
   }

   // ansatte for gitt idnum og år.
   public Ansatte getAnsatteTilEnhet(Integer idnum, Integer aar) throws SQLException {
      SortedMap<Integer, Ansatte> total = new TreeMap<Integer, Ansatte>();
      List<Ansatte> liste = this.getAnsatteKoblingTilEnhet(idnum);
      if (liste == null) {
         return null;
      }

      for (Ansatte a : liste) {
         if (a.getFraAar() != null && a.getFraAar() > aar) {
            continue;
         }
         if (a.getTilAar() != null && a.getTilAar() < aar) {
            continue;
         }
         this.getAnsatteTilEnhet(a.getEtatkode(), a.getFraAar(), a.getTilAar(), total);
      }

      return total.get(aar);
   }

   // årsverk er feil impl.
   private SortedMap<Integer, Ansatte> getAnsatteTilEnhet(String etatkode,
           Integer fraAar, Integer tilAar, SortedMap<Integer, Ansatte> map) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select u_ver_1, sum(antall) as ansatte, "
              + "sum(u_delpros*antall)/100.0 as aarsverk "
              + "from t_forvaltning_ansatte_kumulativ "
              + "where u_stat_grl in (2,3,4) "
              + "and u_etatskode like ? "
              + (fraAar != null ? "and u_ver_1 >= " + fraAar : "")
              + (tilAar != null ? "and u_ver_1 <= " + tilAar : "")
              + "group by u_ver_1 "
              + "order by u_ver_1";
      List values = new ArrayList();
      values.add(etatkode);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         Integer aar = (Integer) rad.get("u_ver_1");
         Ansatte a = map.get(aar);
         if (a == null) {
            a = new Ansatte();
            map.put(aar, a);
         }

         a.setAar(aar);

         Number ansatte = (Number) rad.get("ansatte");
         int ans = (ansatte != null ? ansatte.intValue() : 0);
         a.setAnsatte((a.getAnsatte() != null ? a.getAnsatte().intValue() + ans : ans));

         Number aarsverk = (Number) rad.get("aarsverk");
         float verk = (aarsverk != null ? aarsverk.floatValue() : 0);
         a.setAarsverk((a.getAarsverk() != null ? a.getAarsverk().floatValue() + verk : verk));
      }

      return map;
   }

   public Kommune getKommune(List<Kommune> alleKommuner, Integer kommunenr, Date date) {
      int aar = -1;
      if (date != null) {
         Calendar cal = new GregorianCalendar();
         cal.setTime(date);
         aar = cal.get(Calendar.YEAR);
      }
      for (Kommune k : alleKommuner) {
         if (aar != -1) {
            if (k.getFomAar() != null && k.getFomAar() > aar) {
               continue;
            }
            if (k.getTomAar() != null && k.getTomAar() < aar) {
               continue;
            }
         }
         if (kommunenr.equals(k.getKode())) {
            return k;
         }
      }
      return null;
   }

   /**
    * Setter overordnet departement til gitt enhet på gitt tid, eller noop hvis
    * ingen overordnet departement finnes.
    *
    * @param e
    * @throws SQLException
    */
   void setOverordnetDepartement(Enhet e) throws SQLException {

      if (e == null) {
         return;
      }
      // Sjekker om enhet er selv et departement.
      if (e.getTilknytningsform() != null && e.getTilknytningsform().equals(10)
              && e.getNivaa() != null && e.getNivaa().equals(0)) {
         return; // e er et departement, og vi trenger ikke finne overordnet dep.
      }
      // Sjekker om enhet er over departement.
      if (e.getIdnum() != null && e.getIdnum().intValue() < 0) {
         return;
      }
      if (e.getOverordnetIdnum() == null) {
         return;
      }

      // finner overordnet dep.
      Enhet overordnet = this.enheter.get(e.getOverordnetIdnum());
      while (overordnet != null) {
         if (overordnet.getTilknytningsform() != null
                 && overordnet.getTilknytningsform().equals(10)
                 && overordnet.getNivaa() != null
                 && overordnet.getNivaa().equals(0)) {
            e.setOverordnetDepartement(overordnet);
            return;
         }
         overordnet = this.enheter.get(overordnet.getOverordnetIdnum());
      }

   }

   /**
    * Setter overordnet reell enhet til gitt enhet hvis den finnes.
    *
    * @param e
    * @throws SQLException
    */
   void setOverordnetReell(Enhet e) throws SQLException {
      if (e == null) {
         return;
      }
      if (e.getOverordnetIdnum() == null) {
         return;
      }

      // finner overordnet reell enhet.
      Enhet overordnet = this.enheter.get(e.getOverordnetIdnum());
      while (overordnet != null) {
         int grunn = (overordnet.getGrunnenhet() != null ? overordnet.getGrunnenhet() : 0);
         if (grunn == 0 || grunn == 11 || grunn == 21) {
            e.setOverordnetReell(new Enhet(overordnet));
            return;
         }
         overordnet = this.enheter.get(overordnet.getOverordnetIdnum());
      }
   }

   /**
    * Beregner og setter alle enheter slik de er per gitt dato.
    *
    * @param dato gitt dato, eller null for per nå.
    * @throws SQLException
    */
   void setEnheter(Date dato) throws SQLException {
      // mapping idnum --> enhet.
      this.enheter = new HashMap<Integer, Enhet>(4000, 0.95f);
      // alle endringer i databasen.
      List<EndringCache> endringer = EndringCacheFactory.getEndringer(this.conn);

      // for-løkken finner aktuelle enheter.
      for (EndringCache endringCache : endringer) {
         Enhet enhet = new Enhet(endringCache);
         if (this.engelsk) {
            enhet.setLangtNavn(enhet.getEngelskLangtNavn());
         }

         if (enhet.getTidspunkt() != null && dato != null && enhet.getTidspunkt().after(dato)) {
            break;
         }

         Enhet eksisterende = this.enheter.get(enhet.getIdnum());
         if (eksisterende == null) {
            this.enheter.put(enhet.getIdnum(), enhet);
            eksisterende = enhet;
         }
         if (enhet.getEndringskode() != null && (enhet.getEndringskode().intValue() / 100 == 3)) {
            eksisterende.setNedlagt(true);
            eksisterende.setTidspunktNedlagt(enhet.getTidspunkt());
         }
         if (enhet.getTilknytningsform() != null) {
            eksisterende.setTilknytningsform(enhet.getTilknytningsform());
         }
         if (enhet.getNivaa() != null) {
            eksisterende.setNivaa(enhet.getNivaa());
         }
         if (enhet.getOverordnetIdnum() != null) {
            eksisterende.setOverordnetIdnum(enhet.getOverordnetIdnum());
         }
         if (enhet.getGrunnenhet() != null) {
            eksisterende.setGrunnenhet(enhet.getGrunnenhet());
         }
         if (enhet.getLangtNavn() != null) {
            eksisterende.setLangtNavn(enhet.getLangtNavn());
         }
         if (enhet.getKortNavn() != null) {
            eksisterende.setKortNavn(enhet.getKortNavn());
         }
         if (enhet.getCofog() != null) {
            eksisterende.setCofog(enhet.getCofog());
         }
         if (enhet.getKommunenummer() != null) {
            eksisterende.setKommunenummer(enhet.getKommunenummer());
            eksisterende.setFylkenummer(enhet.getKommunenummer() / 100);
         }
      }
   }

   /**
    * Returnerer den siste kommunen til enhetene.
    *
    * @return mapping: idnum --> kommunenr.
    * @throws SQLException
    */
   Map<Integer, Integer> getKommuneTilEnheter() throws SQLException {
      Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select idnum, kommunenr from t_forvaltning_enhet where kommunenr is not null";

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return mapping;
      }

      for (SortedMap rad : rader) {
         Integer idnum = (Integer) rad.get("idnum");
         Integer kommunenr = (Integer) rad.get("kommunenr");
         mapping.put(idnum, kommunenr);
      }

      return mapping;
   }

   /**
    * Returnerer den siste kommunen til enheten.
    *
    * @param idnum - gitt enhet
    * @return kommunenr, eller null.
    * @throws SQLException
    */
   public Integer getKommuneTilEnhet(Integer idnum) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select kommunenr from t_forvaltning_enhet where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      return (Integer) rader[0].get("kommunenr");
   }

   /**
    * Returnerer selskapsid til enheten.
    *
    * @param idnum - gitt enhet
    * @return selskapsid, eller null.
    * @throws SQLException
    */
   public Integer getSelskapsidTilEnhet(Integer idnum) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select selskapsdb_id from t_forvaltning_enhet where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      return (Integer) rader[0].get("selskapsdb_id");
   }

   /**
    * Returnerer norge.no-id til enheten.
    *
    * @param idnum - gitt enhet
    * @return norge.no-id , eller null.
    * @throws SQLException
    */
   public Integer getNorgeNoIdTilEnhet(Integer idnum) throws SQLException {
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select norgeno_id from t_forvaltning_norgeno_kobling where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      return (Integer) rader[0].get("norgeno_id");
   }

   // ========================================================================
   // ========================== Action-metoder ==============================
   // ========================================================================
   /**
    * Oppdaterer selskapsdb-id til enheten.
    *
    * @param idnum - gitt enhet.
    * @param selskapsdbId - selskapsdb-id.
    * @throws SQLException
    */
   public void oppdaterSelskapsidTilEnhet(Integer idnum, Integer selskapsdbId) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_enhet set selskapsdb_id = ? where idnum = ?";
      List values = new ArrayList();

      values.add(selskapsdbId);
      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer norgeno-id til enheten.
    *
    * @param idnum - gitt enhet.
    * @param norgenoId - norge.no-id.
    * @throws SQLException
    */
   public void registrerNorgeNoIdTilEnhet(Integer idnum, Integer norgenoId) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_norgeno_kobling(norgeno_id, idnum) values (?, ?)";
      List values = new ArrayList();

      values.add(norgenoId);
      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Sletter norgeno-id til enheten.
    *
    * @param idnum - gitt enhet.
    * @throws SQLException
    */
   public void slettNorgeNoIdTilEnhet(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_norgeno_kobling where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer en ny enhet i databasen og returnerer den nye idnum.
    *
    * @throws java.sql.SQLException
    */
   public Integer registrerNyEnhet() throws SQLException {
      Statement st = null;
      ResultSet rs = null;
      String sql = "insert into t_forvaltning_enhet default values";

      try {
         st = this.conn.createStatement();
         st.execute(sql, Statement.RETURN_GENERATED_KEYS);
         rs = st.getGeneratedKeys();
         if (rs != null && rs.next()) {
            Number n = (Number) rs.getObject(1);
            return (n != null ? n.intValue() : null);
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
            }
         }
         if (st != null) {
            try {
               st.close();
            } catch (SQLException e) {
            }
         }
      }
      return null;
   }

   /**
    * Sletter en gitt enhet i basen.
    *
    * @param idnum - gitt enhet.
    * @throws SQLException
    */
   public void slettEnhet(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_enhet where idnum = ?";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Oppdaterer siste (nåværende) kommune til enheten.
    *
    * @param idnum - gitt enhet.
    * @param kommunenr - gitt kommune.
    * @throws SQLException
    */
   public void oppdaterKommuneTilEnhet(Integer idnum, Integer kommunenr, String kommunenavn,  Integer fomAar, Integer tomAar) throws SQLException {


      Integer eksistendekommunenr = null;
      Result resultkode = null;
      SqlCommandBean sqlCB_kode = new SqlCommandBean();
      SortedMap[] raderkode = null;
      String sqlSelect_kode = "select * from t_forvaltning_enhet where idnum = ? ";
      List values_kode = new ArrayList();
      values_kode.add(idnum);
      sqlCB_kode.setConnection(this.conn);
      sqlCB_kode.setSqlValue(sqlSelect_kode);
      sqlCB_kode.setValues(values_kode);
      resultkode = sqlCB_kode.executeQuery();
      raderkode = resultkode.getRows();
      for (SortedMap rad : raderkode) {
         eksistendekommunenr = (Integer) rad.get("kommunenr");
      }


      Integer kode = null;
      Integer fom_aar = null;
      Integer tom_aar = null;
      Result result = null;
      SqlCommandBean sqlCB_select = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect_select = "select * from t_felles_kommune where kode = ? order by kode, tom_aar desc";
      List values_select = new ArrayList();
      values_select.add(kommunenr);

      sqlCB_select.setConnection(this.conn);
      sqlCB_select.setSqlValue(sqlSelect_select);
      sqlCB_select.setValues(values_select);
      result = sqlCB_select.executeQuery();
      rader = result.getRows();

      for (SortedMap rad : rader) {
         kode = (Integer) rad.get("kode");
         fom_aar = (Integer) rad.get("fom_aar");
         tom_aar = (Integer) rad.get("tom_aar");
      }

      //oppdatere kommunr for idnum
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "update t_forvaltning_enhet set kommunenr = ? where idnum = ? ";
      List values = new ArrayList();
      values.add(kommunenr);
      values.add(idnum);
      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();

      if( kommunenr == kode || kommunenr.equals(kode) ){
      SqlCommandBean sqlCB_felles_kommune = new SqlCommandBean();
      String sqlSelect_felles_kommune = "update t_felles_kommune set tekst_entall = ?, fom_aar=?, tom_aar=?   where kode = ?  ";
      List values_felles_kommune = new ArrayList();

      values_felles_kommune.add(kommunenavn);
      values_felles_kommune.add(fomAar);
      values_felles_kommune.add(tomAar);
      values_felles_kommune.add(kommunenr);

      sqlCB_felles_kommune.setConnection(this.conn);
      sqlCB_felles_kommune.setSqlValue(sqlSelect_felles_kommune);
      sqlCB_felles_kommune.setValues(values_felles_kommune);
      sqlCB_felles_kommune.executeUpdate();
   }
   else {
      SqlCommandBean sqlCB_felles_kommune = new SqlCommandBean();
      String sqlSelect_felles_kommune = "update t_felles_kommune set tom_aar = ?  where kode = ? and tom_aar = '9999' ";
      List values_felles_kommune = new ArrayList();

      values_felles_kommune.add(fomAar - 1);
      values_felles_kommune.add(eksistendekommunenr);

      sqlCB_felles_kommune.setConnection(this.conn);
      sqlCB_felles_kommune.setSqlValue(sqlSelect_felles_kommune);
      sqlCB_felles_kommune.setValues(values_felles_kommune);
      sqlCB_felles_kommune.executeUpdate();


      SqlCommandBean sqlCB_insert__felles_kommune = new SqlCommandBean();
      String sqlSelect_insert_felles_kommune = "insert into t_felles_kommune (kode, tekst_entall, fom_aar, tom_aar) values (?, ?, ?, ?)";
      List values_insert__felles_kommune = new ArrayList();

      values_insert__felles_kommune.add(kommunenr);
      values_insert__felles_kommune.add(kommunenavn);
      values_insert__felles_kommune.add(fomAar);
      values_insert__felles_kommune.add(tomAar);

      sqlCB_insert__felles_kommune.setConnection(this.conn);
      sqlCB_insert__felles_kommune.setSqlValue(sqlSelect_insert_felles_kommune);
      sqlCB_insert__felles_kommune.setValues(values_insert__felles_kommune);
      sqlCB_insert__felles_kommune.executeUpdate();
   }
   }

   /**
    * Sletter et gitt enhetnavn (kallenavn).
    *
    * @param id
    * @throws SQLException
    */
   public void slettEnhetnavn(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_enhet_navn where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   /**
    * Registrerer nytt enhetnavn (kallenavn).
    *
    * @param idnum
    * @param navn
    * @throws SQLException
    */
   public void registrerNyttEnhetnavn(Integer idnum, String navn) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_enhet_navn (idnum, navn) values (?, ?)";
      List values = new ArrayList();

      values.add(idnum);
      values.add(navn);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void slettAnsatteKoblingTilEnhet(Integer id) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "delete from t_forvaltning_enhet_ansatte_kobling where id = ?";
      List values = new ArrayList();

      values.add(id);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public void registrerNyAnsatteKoblingTilEnhet(Integer idnum, String kode, Integer fraAar, Integer tilAar, String dok) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "insert into t_forvaltning_enhet_ansatte_kobling (idnum, ansatte_etatskode, fra_aar, til_aar, dok) values (?, ?, ?, ?, ?)";
      List values = new ArrayList();

      values.add(idnum);
      values.add(kode);
      values.add(fraAar);
      values.add(tilAar);
      values.add(dok);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      sqlCB.executeUpdate();
   }

   public List<OrgPrinsipp> getOrgPrinsippTilEnhet(Integer idnum) throws SQLException {
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select * from t_forvaltning_organisasjonsprinsipp where idnum = ? order by fratidspunkt desc limit 1 ";
      List values = new ArrayList();

      values.add(idnum);

      sqlCB.setConnection(this.conn);
      sqlCB.setSqlValue(sqlSelect);
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();

      List<OrgPrinsipp> orgprinsipp = new ArrayList<OrgPrinsipp>();
      SortedMap[] rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         OrgPrinsipp a = new OrgPrinsipp();
         a.setId((Integer) rad.get("id"));
         a.setIdnum((Integer) rad.get("idnum"));
         a.setOrgprinsippId((Integer) rad.get("orgprinsipp_id"));

         Result result2 = null;
         SqlCommandBean sqlCB_select = new SqlCommandBean();
         SortedMap[] rader2 = null;
         String sqlSelect_select = "select * from t_forvaltning_organisasjonsprinsipp_id where orgprinsipp_id = ? ";
         List values_select = new ArrayList();
         values_select.add((Integer) rad.get("orgprinsipp_id"));
         sqlCB_select.setConnection(this.conn);
         sqlCB_select.setSqlValue(sqlSelect_select);
         sqlCB_select.setValues(values_select);
         result2 = sqlCB_select.executeQuery();
         rader2 = result2.getRows();

         for (SortedMap rad2 : rader2) {
            a.setNorskOrgPrinsipp((String) rad2.get("orgprinsipp"));
            a.setEngelskOrgPrinsipp((String) rad2.get("eng_orgprinsipp"));
         }

         /*a.setNorskOrgPrinsipp((String) rad.get("orgprinsipp"));
         a.setEngelskOrgPrinsipp((String) rad.get("eng_orgprinsipp"));*/


         a.setFraTidspunkt((Date) rad.get("fratidspunkt"));
         a.setTilTidspunkt((Date) rad.get("tiltidspunkt"));
         a.setKommentar((String) rad.get("kommentar"));
         orgprinsipp.add(a);
      }

      return orgprinsipp;
   }

}
