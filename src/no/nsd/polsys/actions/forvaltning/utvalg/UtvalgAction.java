
package no.nsd.polsys.actions.forvaltning.utvalg;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.logikk.forvaltning.UtvalgLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Utvalg;
import no.nsd.polsys.modell.forvaltning.Utvalgmedlemskap;

/**
 *
 * @author hvb
 */
public class UtvalgAction {

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
        DepartementLogikk departementLogikk = new DepartementLogikk();
        ParameterLogikk parameterLogikk = new ParameterLogikk();
        Integer unr = null;
        Utvalg utvalg = null;
        List<Utvalg> tid = null;
        List<Utvalgmedlemskap> medlem = null;
        // dato databasen er sist oppdatert.
        Dato dato = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        String uri = request.getRequestURI();
        try {
            int i = uri.lastIndexOf("/");
            String s = uri.substring(i + 1);
            unr = new Integer(s);
        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            departementLogikk.setConn(conn);
            parameterLogikk.setConn(conn);

            utvalg = logikk.getUtvalg(unr);
            if (utvalg == null) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
                rd.forward(request, response);
                return;
            }

            tid = logikk.getUtvalgTid(unr);
            medlem = logikk.getUtvalgMedlem(unr);

            if (utvalg.getOpprettelsesaar() != null) {
                int aar = utvalg.getOpprettelsesaar();
                if (aar < 1753) {
                    aar = 1753;
                }
                dato = new Dato(aar, 1, 1);
            } else {
                dato = parameterLogikk.getOppdatertInternEnhet();
            }

            if (utvalg.getOdep() != null && utvalg.getOdep().getIdnum() != null) {
                Enhet departement = departementLogikk.getDepartement(utvalg.getOdep().getIdnum(), dato.getDate());
                utvalg.setOdep(departement);
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

        request.setAttribute("utvalg", utvalg);
        request.setAttribute("tid", tid);
        request.setAttribute("medlem", medlem);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/utvalg/utvalg.jsp");
        rd.forward(request, response);
    }


}
