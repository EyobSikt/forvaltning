package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.KommuneCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.OppgaveLogikk;
import no.nsd.polsys.logikk.forvaltning.OrgnrLogikk;
import no.nsd.polsys.logikk.forvaltning.StatresLogikk;
import no.nsd.polsys.logikk.forvaltning.TabellLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.AnsatteLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.Kommune;
import no.nsd.polsys.modell.forvaltning.Ansatte;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.OppgaveEnhet;
import no.nsd.polsys.modell.forvaltning.Statres;

/**
 *
 * @author hvb
 */
public class TabellAction {

   /**
    * Processes requests for both HTTP
    * <code>GET</code> and
    * <code>POST</code> methods.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Connection conn = null;
      TabellLogikk logikk = new TabellLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      OppgaveLogikk oppgaveLogikk = new OppgaveLogikk();
      StatresLogikk statresLogikk = new StatresLogikk();
      AnsatteLogikk ansatteLogikk = new AnsatteLogikk();
      OrgnrLogikk orgnrLogikk = new OrgnrLogikk();

      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      // dato bruker har oppgitt.
      Dato brukerdato = new Dato();
      // de faktiske http-parametrene.
      String paramAar = request.getParameter("y");
      String paramMaaned = request.getParameter("m");
      String paramDag = request.getParameter("d");

      // de relevante enhetene med data.
      List<Enhet> enheter = null;
      // mapping: idnum --> oppgave.
      Map<Integer, OppgaveEnhet> oppgaver = null;
      // mapping: idnum --> statres.
      Map<Integer, Statres> statres = null;
      // mapping: varkey --> map: idnum --> ansatte.
      Map<String, Map<Integer, Ansatte>> ansatte = null;
      // mapping: idnum --> orgnr.
      Map<Integer, Integer> orgnr = null;
      // mapping: knr --> kommune.
      Map<Integer, Kommune> kommuner = null;

      Set<Integer> tilknytningsformer = this.getFilterSet(request.getParameterValues("t"));
      Set<Integer> grunnenheter = this.getFilterSet(request.getParameterValues("g"));
      Set<Integer> nivaaer = this.getFilterSet(request.getParameterValues("n"));

      Map<String, Boolean> variabler = this.getVariabelMap(request.getParameterValues("v"));
      Map<String, Boolean> sstVariabler = this.getVariabelMap(request.getParameterValues("sst"));

      boolean grupperUtenforDep = this.containsValue("20a", request.getParameterValues("g"));
      boolean grupperNasjonale = this.containsValue("20b", request.getParameterValues("g"));
      boolean grupperRegionale = this.containsValue("20c", request.getParameterValues("g"));

      String[] sstvar = {"ans", "aar", "ald"};
      String[] kjonn = {"1", "2", "a"};
      String[] lonnkat = {"2", "3", "4", "a"};
      String[] stilling = {"h", "d", "t", "a"};

      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);
         parameterLogikk.setConn(conn);
         oppgaveLogikk.setConn(conn);
         statresLogikk.setConn(conn);
         ansatteLogikk.setConn(conn);
         orgnrLogikk.setConn(conn);

         sistOppdatertDato = parameterLogikk.getOppdatertDato();
         brukerdato = Dato.getDato(paramAar, paramMaaned, paramDag, sistOppdatertDato);

         // så lenge tidspunkt ikke er etter oppdatert og dato er gyldig --> OK.
         if (brukerdato.isGyldig() && !brukerdato.isEtter(sistOppdatertDato)) {
            logikk.setTilknytningsformer(tilknytningsformer);
            logikk.setGrunnenheter(grunnenheter);
            logikk.setNivaaer(nivaaer);

            logikk.setGrupperUtenfor(grupperUtenforDep);
            logikk.setGrupperNasjonale(grupperNasjonale);
            logikk.setGrupperRegionale(grupperRegionale);

            logikk.setDate(brukerdato.getDate());

            enheter = logikk.getEnheter();

            oppgaver = oppgaveLogikk.getOppgaver(brukerdato.getAar());
            statres = statresLogikk.getStatres(brukerdato.getAar());
            
            ansatte = ansatteLogikk.getEnhetAnsatte(brukerdato.getAar());
            
            
            
            orgnr = orgnrLogikk.getAlleOrgnr();
            
            List<Kommune> kommuneliste = KommuneCacheFactory.getKommuner(conn);
            kommuner = new HashMap<Integer, Kommune>();
            for (Kommune k : kommuneliste) {
               if ((k.getFomAar() == null || k.getTomAar() == null) || (brukerdato.getAar() >= k.getFomAar() && brukerdato.getAar() <= k.getTomAar())) {
                  kommuner.put(k.getKode(), k);
               }
            }
         }

      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception ignored) {
            conn = null;
         }
      }

      
      request.setAttribute("enheter", enheter);
      request.setAttribute("oppgaver", oppgaver);
      request.setAttribute("statres", statres);
      request.setAttribute("ansatte", ansatte);
      request.setAttribute("orgnr", orgnr);
      request.setAttribute("kommuner", kommuner);
      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("brukerdato", brukerdato);
      request.setAttribute("variabler", variabler);
      request.setAttribute("sstVariabler", sstVariabler);
      request.setAttribute("sstvar", sstvar);
      request.setAttribute("kjonn", kjonn);
      request.setAttribute("lonnkat", lonnkat);
      request.setAttribute("stilling", stilling);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/tabell.jsp");
      rd.forward(request, response);
   }

   private Set<Integer> getFilterSet(String[] tab) {
      Set<Integer> set = new HashSet<Integer>();

      if (tab == null || tab.length == 0) {
         return set;
      }
      for (String s : tab) {
         try {
            set.add(new Integer(s));
         } catch (Exception ignored) {
         }
      }
      return set;
   }

   private boolean containsValue(String val, String[] tab) {
      if (tab == null || tab.length == 0) {
         return false;
      }
      for (String s : tab) {
         if (val.equals(s)) {
            return true;
         }
      }
      return false;
   }

   private Map<String, Boolean> getVariabelMap(String[] tab) {
      Map<String, Boolean> v = new HashMap<String, Boolean>();

      if (tab == null || tab.length == 0) {
         return v;
      }
      for (String s : tab) {
         v.put(s, Boolean.TRUE);
      }

      return v;
   }
   
   
}
