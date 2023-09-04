
package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.AntallEnheterEndringLogikk;
import no.nsd.polsys.modell.forvaltning.AntallEnheterEndring;

/**
 *
 * @author hvb
 */
public class AntallEnheterEndringAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        AntallEnheterEndringLogikk logikk = new AntallEnheterEndringLogikk();
        List<AntallEnheterEndring> data = null;

        List<AntallEnheterEndring> data1a = null;
        List<AntallEnheterEndring> data1b = null;
        List<AntallEnheterEndring> data1c = null;

        List<AntallEnheterEndring> data2a = null;
        List<AntallEnheterEndring> data2b = null;
        List<AntallEnheterEndring> data2c = null;

        List<AntallEnheterEndring> data3a = null;
        List<AntallEnheterEndring> data3b = null;
        List<AntallEnheterEndring> data3c = null;

        List<AntallEnheterEndring> data301 = null;
        List<AntallEnheterEndring> data302 = null;
        List<AntallEnheterEndring> data303 = null;
        List<AntallEnheterEndring> data304 = null;
        List<AntallEnheterEndring> data305 = null;
        List<AntallEnheterEndring> data306 = null;
        List<AntallEnheterEndring> data307 = null;
        List<AntallEnheterEndring> data308 = null;
        List<AntallEnheterEndring> data309 = null;
        List<AntallEnheterEndring> data310 = null;

        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);


            logikk.beregnRelevanteGrupper();
            
            data = logikk.getEnheter(0); // total.

            data1a = logikk.getEnheter(1, 20); // dir.
            data1b = logikk.getEnheter(1, 33); // andre ord.
            data1c = logikk.getEnheter(1, 31); // særskilte fullm.
            
            data2a = logikk.getEnheter(2, 0); // nasj. org.
            data2b = logikk.getEnheter(2, 11); // etat.
            data2c = logikk.getEnheter(2, 20); // gruppe.

            data3a = logikk.getEnheter(3, 1,2,3,5); // andre
            data3b = logikk.getEnheter(3, 4); // økonomi.
            data3c = logikk.getEnheter(3, 6,7,8,9,10); // velferd.

            data301 = logikk.getEnheter(3, 1);
            data302 = logikk.getEnheter(3, 2);
            data303 = logikk.getEnheter(3, 3);
            data304 = logikk.getEnheter(3, 4);
            data305 = logikk.getEnheter(3, 5);
            data306 = logikk.getEnheter(3, 6);
            data307 = logikk.getEnheter(3, 7);
            data308 = logikk.getEnheter(3, 8);
            data309 = logikk.getEnheter(3, 9);
            data310 = logikk.getEnheter(3, 10);

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

        request.setAttribute("data", data);

        request.setAttribute("data1a", data1a);
        request.setAttribute("data1b", data1b);
        request.setAttribute("data1c", data1c);

        request.setAttribute("data2a", data2a);
        request.setAttribute("data2b", data2b);
        request.setAttribute("data2c", data2c);

        request.setAttribute("data3a", data3a);
        request.setAttribute("data3b", data3b);
        request.setAttribute("data3c", data3c);

        request.setAttribute("data301", data301);
        request.setAttribute("data302", data302);
        request.setAttribute("data303", data303);
        request.setAttribute("data304", data304);
        request.setAttribute("data305", data305);
        request.setAttribute("data306", data306);
        request.setAttribute("data307", data307);
        request.setAttribute("data308", data308);
        request.setAttribute("data309", data309);
        request.setAttribute("data310", data310);
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/antall_enheter_endring.jsp");
        rd.forward(request, response);
    }


}
