package no.nsd.polsys.servlets;

import no.nsd.polsys.actions.forvaltning.*;
import no.nsd.polsys.actions.forvaltning.ansatte.*;
import no.nsd.polsys.actions.forvaltning.litteratur.*;
import no.nsd.polsys.actions.forvaltning.utvalg.*;
import no.nsd.polsys.actions.xml.forvaltning.XmlEvalEnheterAarAction;
import no.nsd.polsys.actions.xml.forvaltning.XmlEvalEnheterKomplettAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Klassen inneholder mapping fra url til action-klasser for url'er som 
 * begynner med /forvaltning/.
 * @author hvbdepartement
 */
public class UrlMappingForvaltning {

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
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String uri = request.getRequestURI();
      String context = request.getContextPath();

      // URL-mapping

      if (uri.matches(context + "(/en)?/data/")) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/index.jsp");
         rd.forward(request, response);
      }// Enheter fra brønnysundregistrene
      else if (uri.matches(context + "(/en)?/data/enhetsregisteret")) {
         new EnhetsregisteretAction().process(request, response);
      }else if (uri.matches(context + "(/en)?/data/organisasjon/-?\\d+")) {
         new EnhetsregisteretopplysAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/organisasjon/statistikk")) {
         new EnhetsregistrertstatistikkAction().process(request, response);
      }else if (uri.matches(context + "(/en)?/data/organisasjon/sok")) {
         new EnhetsregistrertsokAction().process(request, response);
      } // Enheter
      else if (uri.matches(context + "(/en)?/data/departement")) {
         new DepartementAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/sok")) {
         new SokAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+")) {
         new EnhetAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/departementsenhet")) {
         new DepartementsenhetAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/antalldepartementsenhet")) {
         new AntallDepartementsenhetAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/departementsenhetsliste")) {
         new DepartementsenhetslisteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/forvaltningsenhet")) {
         new ForvaltningsenhetAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/antallforvaltningsenhet")) {
         new AntallForvaltningsenhetAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/forvaltningsenhetsliste")) {
         new ForvaltningsenhetslisteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/internendring")) {
         new InternEndringAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/internendringsliste")) {
         new InternEndringslisteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ytreendring")) {
         new YtreEndringAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ytreendringsliste")) {
         new YtreEndringslisteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/endringshistorie")) {
         new EnhetEndringshistorieAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/litteratur")) {
         new EnhetLitteraturAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/aarsmelding")) {
         new EnhetAarsmeldingAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/instruks")) {
            new EnhetInstrukAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/organisjonsprinsipp")) {
         new EnhetOrganisjonsprinsippAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/tildelingsbrev")) {
         new EnhetTildelingsbrevAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/lovdata")) {
         new EnhetLovdataAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/hierarki")) {
         new EnhetHierarkiAction().process(request, response);

      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/enhetsregisterethierarki")) {
         new EnhetsregisteretHerarkiAction().process(request, response);

      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/relasjon")) {
         new EnhetRelasjonAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/avdeling")) {
         new EnhetSatellittAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/desentralisert")) {
         new EnhetDesentralisertAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/utvalg")) {
         new EnhetUtvalgAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/ansatte")) {
         new EnhetAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/oppgave")) {
         new EnhetOppgaveAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/statres")) {
         new EnhetStatresAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/lenker")) {
         new EnhetLenkerAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/enhet/-?\\d+/desentralisert/\\d+")) {
         new EnhetDesentralisertLokaliseringAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/cofog")) {
         new CofogListeAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/cofog/.+")) {
         new CofogAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/fylke")) {
         new FylkelisteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/fylke/\\d+")) {
         new FylkeAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/oppgave")) {
         new OppgavelisteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/oppgave/\\d+")) {
         new OppgaveAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/lagtabell")) {
         new TabellvalgAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/tabell")) {
         new TabellAction().process(request, response);
      } 
      
      // Litteratur
      else if (uri.matches(context + "(/en)?/data/litteratur/\\d+")) {
         new LitteraturAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/litteratur/sok")) {
         new LitteraturSokAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/litteratur/enhet")) {
         new LitteraturEnhetSokAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/litteratur/tilknytningsform")) {
         new LitteraturTilknytningsformerAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/litteratur/tilknytningsform/-?\\d+")) {
         new LitteraturTilknytningsformAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/litteratur/kategori")) {
         new LitteraturKategorierAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/litteratur/kategori/\\d+")) {
         new LitteraturKategoriAction().process(request, response);
      } 
      
      // Statsansatte
      else if (uri.matches(context + "(/en)?/data/ansatte/land")) {
         new LandAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/fylke")) {
         new FylkerAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/kommune")) {
         new KommunerAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/dep")) {
         new DepartementerAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/etat")) {
         new EtaterAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/fylke/\\d+")) {
         new FylkeAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/kommune/\\d+")) {
         new KommuneAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/etat/\\d+(:\\d+)*")) {
         new EtatAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/etatkode/\\d+(:\\d+)*")) {
         new EtatAlleAarAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/depetat/\\d+")) {
         new DepartementEtatAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/landetat")) {
         new LandEtatAnsatteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/ansatte/lokalisering")) {
         new LokaliseringAnsatteAction().process(request, response);
      } 
      
      // Utvalg
      else if (uri.matches(context + "(/en)?/data/utvalg/sok")) {
         new UtvalgSokAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/utvalg/liste")) {
         new UtvalglisteAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/utvalg/sokperson")) {
         new UtvalgSokPersonAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/utvalg/\\d+")) {
         new UtvalgAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/utvalg/person/\\d+")) {
         new UtvalgpersonAction().process(request, response);
      } 
      
      // Lokalisering utenfor Oslo
      else if (uri.matches(context + "(/en)?/data/lokalisering")) {
         new LokaliseringAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/lokaliseringsliste")) {
         new LokaliseringslisteAction().process(request, response);
      } 
      
      // Data for Evalueringsportalen DFØ
      else if (uri.matches(context + "(/en)?/data/eval/enheter")) {
         new EvalEnhetlisteAction().process(request, response);
      } 
      
      // XML
      else if (uri.matches(context + "/xml/data/eval/enheteraar")) {
         new XmlEvalEnheterAarAction().process(request, response);
      } else if (uri.matches(context + "/xml/data/eval/enheterkomplett")) {
         new XmlEvalEnheterKomplettAction().process(request, response);
      } 
      
      // Enheter for DIFI
      else if (uri.matches(context + "(/en)?/data/difi")) {
         new DifiAction().process(request, response);
      } 
      
      // ad hoc krd - utfytting
      else if (uri.matches(context + "(/en)?/data/krd")) {
         new KrdListeAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/krd/\\d+")) {
         new KrdEnhetAction().process(request, response);
      } 
      
      // uttak for forsker
      else if (uri.matches(context + "(/en)?/data/antallenheterendring")) {
         new AntallEnheterEndringAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/antallselskapendring")) {
         new AntallSelskapEndringAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/antallcofog")) {
         new AntallCofogAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/antallgrunnenhet")) {
         new AntallGrunnenhetAction().process(request, response);
      } else if (uri.matches(context + "(/en)?/data/tempenheter")) {
         new TempEnheterAction().process(request, response);
      } 
      
      // 404
      else {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
         rd.forward(request, response);
      }

   }
}
