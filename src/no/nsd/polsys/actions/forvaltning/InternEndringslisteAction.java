package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.EndringskodeCacheFactory;
import no.nsd.polsys.factories.forvaltning.NivaaCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class InternEndringslisteAction {

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
      DepartementLogikk departementLogikk = new DepartementLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;

      // liste av endringer av nivåer av enheter.
      List<List<List<Enhet>>> endringsliste = null;

      int fraAar = 1947;
      int tilAar = 2010;

      Integer depIdnum = null;
      Enhet departement = null;
      Date fraDato = null;
      Date tilDato = null;
      List<Integer> nivaaer = new ArrayList<Integer>();
      List<Integer> endringer = new ArrayList<Integer>();
      Map<Integer, DokCache> nivaaNavn = null;
      Map<Integer, DokCache> endringskoderNavn = null;

      List<Integer> antallEndringer = new ArrayList<Integer>();
      int antallTotal = 0;

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }


      // departement
      try {
         String dep = request.getParameter("dep");
         if (dep != null && dep.length() != 0) {
            depIdnum = Integer.parseInt(dep);
         }
      } catch (Exception e) {
         request.setAttribute("feilValg1", true);
         this.forward(request, response);
         return;
      }

      // tidsperiode
      try {
         fraAar = Integer.parseInt(request.getParameter("fra"));
         tilAar = Integer.parseInt(request.getParameter("til"));
         if (fraAar > tilAar) {
            throw new Exception();
         }
      } catch (Exception e) {
         request.setAttribute("feilTid", true);
         this.forward(request, response);
         return;
      }

      // nivåer
      try {
         String[] n = request.getParameterValues("n");
         if (n == null || n.length == 0) {
            throw new Exception();
         }
         for (String s : n) {
            nivaaer.add(new Integer(s));
         }
      } catch (Exception e) {
         request.setAttribute("feilValg2", true);
         this.forward(request, response);
         return;
      }

      // endringer
      try {
         String[] e = request.getParameterValues("e");
         if (e == null || e.length == 0) {
            throw new Exception();
         }
         for (String s : e) {
            endringer.add(new Integer(s));
         }
      } catch (Exception e) {
         request.setAttribute("feilValg3", true);
         this.forward(request, response);
         return;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         departementLogikk.setConn(conn);
         parameterLogikk.setConn(conn);

         if (engelsk) {
            departementLogikk.brukEngelsk();
         }

         sistOppdatertDato = parameterLogikk.getOppdatertInternEnhet();
         Calendar cal = new GregorianCalendar();

         // fra-år
         cal.clear();
         cal.set(fraAar, 0, 1); // 1.1.fraAar
         if (cal.getTime().after(sistOppdatertDato.getDate())) {
            fraDato = sistOppdatertDato.getDate();
         } else {
            fraDato = cal.getTime();
         }
         // til-år
         cal.clear();
         cal.set(tilAar, 11, 31); // 31.12.tilAar
         if (cal.getTime().after(sistOppdatertDato.getDate())) {
            tilDato = sistOppdatertDato.getDate();
         } else {
            tilDato = cal.getTime();
         }

         endringsliste = departementLogikk.getEndringer(depIdnum, fraDato, tilDato, endringer, nivaaer, null, true);

         if (depIdnum != null) {
            departement = departementLogikk.getHistoriskDepartement(depIdnum, fraDato, tilDato);
         }

         // beregner antall
         for (List<List<Enhet>> n : endringsliste) {
            int antall = 0;
            for (List<Enhet> a : n) {
               antall += a.size();
               antallTotal += a.size();
            }
            antallEndringer.add(antall);
         }

         if (engelsk) {
            nivaaNavn = NivaaCacheFactory.getDokdataEngelsk(conn);
            endringskoderNavn = EndringskodeCacheFactory.getDokdataEngelsk(conn);
         } else {
            nivaaNavn = NivaaCacheFactory.getDokdata(conn);
            endringskoderNavn = EndringskodeCacheFactory.getDokdata(conn);
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

      request.setAttribute("nivaaNavn", nivaaNavn);
      request.setAttribute("endringskoderNavn", endringskoderNavn);
      request.setAttribute("nivaaer", nivaaer);
      request.setAttribute("endringer", endringer);
      request.setAttribute("endringsliste", endringsliste);
      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("fraDato", fraDato);
      request.setAttribute("tilDato", tilDato);
      request.setAttribute("antallEndringer", antallEndringer);
      request.setAttribute("antallTotal", antallTotal);
      request.setAttribute("departement", departement);

      this.forward(request, response);
   }

   private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/intern_endringsliste.jsp");
      rd.forward(request, response);
   }
}
