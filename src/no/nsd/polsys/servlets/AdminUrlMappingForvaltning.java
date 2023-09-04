
package no.nsd.polsys.servlets;


import no.nsd.polsys.actions.admin.forvaltning.*;
import no.nsd.polsys.logikk.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author hvb
 */
public class AdminUrlMappingForvaltning {


    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = Util.getUriWithoutJsessionId(request.getRequestURI());
        String context = request.getContextPath();

        // URL-mapping
        if (uri.matches(context + "/forvaltning/index\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/index.jsp");
            rd.forward(request, response);
        }
        else if (uri.matches(context + "/forvaltning/dokliste\\.p")) new DoklisteAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/velgendring\\.p")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/velg_endring.jsp");
            rd.forward(request, response);
        }
        else if (uri.matches(context + "/forvaltning/endringliste\\.p")) new EndringlisteAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endring\\.p")) new EndringAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagreendring\\.p")) new LagreEndringAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endringsnummer\\.p")) new EndringsnummerAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endringsnummerbruk\\.p")) new EndringsnummerBrukAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyttendringsnummer\\.p")) new NyttEndringsnummerAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettendring\\.p")) new SlettEndringAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyttorgnr\\.p")) new NyttOrgnrAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyttvirksomhetsnr\\.p")) new NyttVirksomhetsnrAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettorgnr\\.p")) new SlettOrgnrAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettvirksomhetsnr\\.p")) new SlettVirksomhetsnrAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nydbhinstkode\\.p")) new NyDbhinstkodeAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyaarsmelding\\.p")) new NyAarsmeldingAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyinstruks\\.p")) new NyInstruksAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyorgprinsipp\\.p")) new NyOrgPrinsippAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nytildelingsbrev\\.p")) new NyTildelingsbrevAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyarkivportalen\\.p")) new NyArkivportalenAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyttutvalg\\.p")) new NyttUtvalgAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettutvalg\\.p")) new SlettUtvalgAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endreaarsmelding\\.p")) new EndreAarsmeldingAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endreinstruks\\.p")) new EndreInstruksAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endreorgprinsipp\\.p")) new EndreOrgPrinsippAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endredbhinstkode\\.p")) new EndreDbhinstkodeAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endretildelingsbrev\\.p")) new EndreTildelingsbrevAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endrearkivportalen\\.p")) new EndreArkivportalenAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/endringsnummer\\.p")) new EndringsnummerAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lovdata\\.p")) new LovdataAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nylovdata\\.p")) new NyLovdataAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettlovdata\\.p")) new SlettLovdataAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nylovdataenhet\\.p")) new NyLovdataEnhetAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettlovdataenhet\\.p")) new SlettLovdataEnhetAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagrekommuneenhet\\.p")) new LagreKommuneEnhetAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagreselskap\\.p")) new LagreSelskapAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagrenorgeno\\.p")) new LagreNorgeNoAction().process(request, response);
        //else if (uri.matches(context + "/forvaltning/lagreorgprinsipp\\.p")) new LagreOrgPrinsippAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nyttenhetnavn\\.p")) new NyttEnhetnavnAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettenhetnavn\\.p")) new SlettEnhetnavnAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/relasjonenhet\\.p")) new RelasjonEnhetAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagrerelasjonenhet\\.p")) new LagreRelasjonEnhetAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/litteraturliste\\.p")) new LitteraturlisteAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/litteratur\\.p")) new LitteraturAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagrelitteratur\\.p")) new LagreLitteraturAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nylitteraturenhet\\.p")) new NyLitteraturEnhetAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettlitteraturenhet\\.p")) new SlettLitteraturEnhetAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/nylitteraturtilknytningsform\\.p")) new NyLitteraturTilknytningsformAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettlitteraturtilknytningsform\\.p")) new SlettLitteraturTilknytningsformAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagresatellitt\\.p")) new LagreSatellittAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/lagreansattekode\\.p")) new LagreAnsatteKoderAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/slettansattekode\\.p")) new SlettAnsatteKodeAction().process(request, response);

        else if (uri.matches(context + "/forvaltning/eventtable\\.p")) new EventTableAction().process(request, response);

        else if (uri.matches(context + "/forvaltning/ansatteenhetkobling\\.p")) new AnsatteEnhetKoblingAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/cofogalleliste\\.p")) new CofogAlleListeAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/uploadfile\\.p")) new UploadFileAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/uploadtildelingsbrev\\.p")) new UploadTildelingsbrevAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/uploadaarsmeldinger\\.p")) new UploadAarsmeldingerAction().process(request, response);
        else if (uri.matches(context + "/forvaltning/uploadinstrukser\\.p")) new UploadInstrukserAction().process(request, response);

        // 404
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
        }

    }

}
