package no.nsd.polsys.filters.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.OppgaveLogikk;
import no.nsd.polsys.logikk.forvaltning.StatresLogikk;
import no.nsd.polsys.logikk.forvaltning.TallgruppeLogikk;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.OppgaveEnhet;
import no.nsd.polsys.modell.forvaltning.Statres;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hvb
 */
public class EnhetFilter implements Filter {

   private FilterConfig config = null;

   public void init(FilterConfig config) throws ServletException {
      this.config = config;
   }

   public void destroy() {
      this.config = null;
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
           throws IOException, ServletException {

      HttpServletRequest httpReq = (HttpServletRequest) request;

      Connection conn = null;

      EnhetLogikk enhetLogikk = new EnhetLogikk();
      TallgruppeLogikk tallgruppeLogikk = new TallgruppeLogikk();
      OppgaveLogikk oppgaveLogikk = new OppgaveLogikk();
      StatresLogikk statresLogikk = new StatresLogikk();

      Integer idnum = null;
      Enhet enhet = null;

      idnum = Util.getId(httpReq.getRequestURI());
      if (idnum == null) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
         rd.forward(request, response);
         return;
      }

      boolean engelsk = false;
      String uri = httpReq.getRequestURI();
      if (uri.contains("/en/")) {
         engelsk = true;
      } else {
         engelsk = false;
      }


      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         tallgruppeLogikk.setConn(conn);
         oppgaveLogikk.setConn(conn);
         statresLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }

         enhet = enhetLogikk.getEnhet(idnum);
         if (enhet == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }

         boolean visAnsatteLink = true;
         if (enhet.getTilknytningsform() != null && enhet.getTilknytningsform().equals(10)
                 && enhet.getNivaa() != null && !enhet.getNivaa().equals(0)) {
            visAnsatteLink = false;
         }

         boolean visOppgaveLink = false;
         List<OppgaveEnhet> oppgaver = oppgaveLogikk.getOppgaverTilEnhet(idnum);
         if (oppgaver != null && !oppgaver.isEmpty() && !engelsk) {
            visOppgaveLink = true;
            request.setAttribute("oppgaver", oppgaver);
            request.setAttribute("sisteoppgave", oppgaver.get(oppgaver.size() - 1));
         }

         boolean visStatresLink = false;
         List<Statres> statres = statresLogikk.getStatresTilEnhet(idnum);
         if (statres != null && !statres.isEmpty()) {
            visStatresLink = true;
            request.setAttribute("statres", statres);
         }

         request.setAttribute("idnum", idnum);
         request.setAttribute("enhet", enhet);

         request.setAttribute("visTallgruppeLink", tallgruppeLogikk.harTallgrupper(idnum));
         request.setAttribute("visSatellittLink", tallgruppeLogikk.harSatellitt(idnum));
         request.setAttribute("visUtvalgLink", !engelsk); // viser alltid utvalgfanen for norske side.
         request.setAttribute("visAnsatteLink", visAnsatteLink);
         request.setAttribute("visOppgaveLink", visOppgaveLink);
         request.setAttribute("visStatresLink", visStatresLink);

      } catch (Exception e) {
         throw new ServletException();
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException ignored) {
         }
      }


      /*
       * Process the hello of the filter chain, if any, and ultimately
       * the requested servlet or JSP page.
       */
      chain.doFilter(request, response);
   }
}
