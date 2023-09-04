
package no.nsd.polsys.actions.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetsregisteretLogikk;
import no.nsd.polsys.modell.forvaltning.Enhet;

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
public class EnhetsregistrertstatistikkAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        EnhetsregisteretLogikk enhetsregisteretLogikk = new EnhetsregisteretLogikk();
        List<Enhet> organisasjonsform = null;
        List<Enhet> naeringskode = null;
        List<Enhet> sektorkode = null;
        List<Enhet> forretningskommune = null;
        List<Enhet> alle_arbeideyrke = null;

        String orggruppe = null;
        String statgruppe = null;

        String orgform = null;
        String naerkode = null;
        String sekkode = null;
        String forretningskommunekode = null;
        Integer yrkekode = null;

        List<Enhet> organisasjonsform2 = null;
        List<Enhet> naeringskode2 = null;
        List<Enhet> sektorkode2 = new ArrayList<>();
        List<Enhet> forretningskommunekode2 = new ArrayList<>();
        List<Enhet> arbeidsyrke2 = new ArrayList<>();
        List<Integer> alleAar = new ArrayList<Integer>();
        Integer valgtAar = null;

        try {
            valgtAar = new Integer(request.getParameter("aar"));
        } catch (Exception ignored) {
        }

        try {
            orggruppe = new String(request.getParameter("orggruppe"));
            statgruppe = new String(request.getParameter("statgruppe"));
        } catch (Exception ignored) {
            orggruppe = null;
        }
        try {
            conn = DatabaseConnectionFactory.getConnection();
            enhetsregisteretLogikk.setConn(conn);
            alleAar = enhetsregisteretLogikk.getAlleAar();
            if (valgtAar == null) {
                valgtAar = alleAar.get(0);
            }
            if(orggruppe!=null) {
                if (orggruppe.equals("orgform")) {
                    organisasjonsform = enhetsregisteretLogikk.getOrganisasjonsform(valgtAar);
                }
                if (orggruppe.equals("naerkode")) {
                    naeringskode = enhetsregisteretLogikk.getNaeringskode(valgtAar);
                }
                if (orggruppe.equals("sekkode")) {
                    sektorkode = enhetsregisteretLogikk.getSektorkode(valgtAar);
                }
                if (orggruppe.equals("forretningskommune")) {
                    forretningskommune = enhetsregisteretLogikk.getForretningskommuneskode(valgtAar);
                }
            }
            else{organisasjonsform = enhetsregisteretLogikk.getOrganisasjonsform(valgtAar);}

                alle_arbeideyrke = enhetsregisteretLogikk.getAlleEnhet_arb_yrke(valgtAar);


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
        request.setAttribute("organisasjonsform", organisasjonsform);
        request.setAttribute("naeringskode", naeringskode);
        request.setAttribute("sektorkode", sektorkode);
        request.setAttribute("forretningskommune", forretningskommune);
        request.setAttribute("alle_arbeideyrke", alle_arbeideyrke);

/*============================tabell resultat=========================================*/


       //String orgformvalue=null;
        try {orgform = new String(request.getParameter("orgform"));
          /*  byte[] ptext = orgform.getBytes("ISO-8859-1");
            orgformvalue = new String(ptext, "UTF-8");*/
        }catch (Exception ignored) {orgform = null;}

        try {naerkode = new String(request.getParameter("naeringskode"));} catch (Exception ignored) {naerkode = null;}
        try {sekkode = new String(request.getParameter("sektorkode"));} catch (Exception ignored) {sekkode = null;}
        try {forretningskommunekode = new String(request.getParameter("forretningskommunekode"));} catch (Exception ignored) {forretningskommunekode = null;}
        try {yrkekode = new Integer(request.getParameter("arbeidsyrke"));} catch (Exception ignored) {yrkekode = null;}
        try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetsregisteretLogikk.setConn(conn);
         alleAar = enhetsregisteretLogikk.getAlleAar();
            if (valgtAar == null) {
                valgtAar = alleAar.get(0);
            }

            organisasjonsform2 = enhetsregisteretLogikk.getOrganisasjonsformgruppe(orgform, valgtAar);
            sektorkode2 = enhetsregisteretLogikk.getSektorkodegruppe(sekkode, valgtAar);
            naeringskode2 = enhetsregisteretLogikk.getNaeringskodegruppe(naerkode, valgtAar);
            forretningskommunekode2 = enhetsregisteretLogikk.getForretningskommunekodegruppe(forretningskommunekode, valgtAar);
            arbeidsyrke2 = enhetsregisteretLogikk.getOrgArbeidsyrkekodegruppe(yrkekode, valgtAar);
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
        request.setAttribute("organisasjonsform2", organisasjonsform2);
        request.setAttribute("naeringskode2", naeringskode2);
        request.setAttribute("sektorkode2", sektorkode2);
        request.setAttribute("forretningskommunekode2", forretningskommunekode2);
        request.setAttribute("arbeidsyrke2", arbeidsyrke2);
        request.setAttribute("alleAar", alleAar);
        request.setAttribute("valgtAar", valgtAar);




        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhetregistrertstatistikk.jsp");
        rd.forward(request, response);
    }
}
