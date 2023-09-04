
package no.nsd.polsys.actions.forvaltning.utvalg;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.UtvalgLogikk;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Enhetsregisteret;
import no.nsd.polsys.modell.forvaltning.Utvalg;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvb
 */
public class UtvalglisteAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;

        UtvalgLogikk logikk = new UtvalgLogikk();

        List<Utvalg> utvalg = null;
        List<DokCache> geografi = null;
        List<DokCache> hovedfunksjon = null;
        List<DokCache> hovedfunksjon2018 = null;
        List<DokCache> type = null;
        List<Enhet> departementer = null;

        Integer pDep = null;
        Integer pAar = null;
        Integer pType = null;
        Integer pFunk = null;
        Integer pGeo = null;

        boolean finn = true;

        try {
            if (request.getParameter("dep") == null
                    && request.getParameter("aar") == null
                    && request.getParameter("type") == null
                    //&& request.getParameter("funk") == null
                    && request.getParameter("geo") == null) {
                finn = false;
            }

            pDep = Util.strengTilInteger(request.getParameter("dep"));
            pAar = Util.strengTilInteger(request.getParameter("aar"));
            pType = Util.strengTilInteger(request.getParameter("type"));
            //pFunk = Util.strengTilInteger(request.getParameter("funk"));
            pGeo = Util.strengTilInteger(request.getParameter("geo"));
        } catch (Exception ignored) {
        }
//////////////////////////////////////////////////////////////////////
        StringBuffer cleanString = new StringBuffer();
        String[] fq = new String[0];
        Enhetsregisteret parti = new Enhetsregisteret();

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        if (request.getParameterValues("fq") != null) fq = request.getParameterValues("fq");
        if (fq != null)
            for (String item : fq)
                if (item != null && !item.equals("null") && !item.equals(""))
                    cleanString.append("&fq=").append(item);

        request.setAttribute("url", cleanString.toString());


         /*   url fq values til arraylist */
        ArrayList<String> list_orgnummer = new ArrayList<String>();
        List<Integer> hovedfunksjon_2003 = new ArrayList<Integer>();
        List<Integer> hovedfunksjon_2018 = new ArrayList<Integer>();
        ArrayList<String> list_variable = new ArrayList<String>();
        String beforecolon;
        String aftercolon;
        StringBuffer sb_orgnummer = new StringBuffer();
        StringBuffer sb_variable = new StringBuffer();
      /*   url fq values til arraylist */
        for (int w = 0; w < fq.length; w++) {
            if (fq[w] == null) {
            } else {
                beforecolon = parti.substringBefore(fq[w], "-");
                aftercolon = parti.substringAfter(fq[w], "-");
                if (beforecolon.equals("hf2013") && !aftercolon.equals(null) && !aftercolon.equals("")) {
                    try {
                        list_orgnummer.add(String.valueOf(Integer.parseInt(aftercolon)));
                        hovedfunksjon_2003.add(new Integer(Integer.parseInt(aftercolon)));
                    } catch (Exception e) {
                    }
                }
                if (beforecolon.equals("hf2018") && !aftercolon.equals(null) && !aftercolon.equals("")) {
                    try {
                        list_variable.add(String.valueOf(Integer.parseInt(aftercolon)));
                        hovedfunksjon_2018.add(new Integer(Integer.parseInt(aftercolon)));
                    } catch (Exception e) {
                    }
                }
            }
        }

////////////////////////////////////////////////////////////////////

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);

            geografi = logikk.getGeografi();
            hovedfunksjon = logikk.getHovedfunksjon();
            hovedfunksjon2018 = logikk.getHovedfunksjon2018();
            type = logikk.getUtvalgstype();

            departementer = logikk.getDepartement();

           // if (finn) {
                //utvalg = logikk.getUtvalg(pDep, pAar, pType, pFunk, pGeo);
                utvalg = logikk.getUtvalg(pDep, pAar, pType, pGeo, hovedfunksjon_2003, hovedfunksjon_2018 );
            //}

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

        request.setAttribute("finn", finn);
        request.setAttribute("utvalg", utvalg);
        request.setAttribute("geografi", geografi);
        request.setAttribute("hovedfunksjon", hovedfunksjon);
        request.setAttribute("hovedfunksjon2018", hovedfunksjon2018);
        request.setAttribute("type", type);
        request.setAttribute("departementer", departementer);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/utvalg/utvalg_liste.jsp");
        rd.forward(request, response);
    }


}
