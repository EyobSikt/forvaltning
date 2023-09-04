
package no.nsd.polsys.actions.forvaltning.litteratur;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.LitteraturLogikk;
import no.nsd.polsys.modell.forvaltning.Litteratur;
import no.nsd.polsys.modell.forvaltning.Litteraturkategori;

/**
 *
 * @author hvb
 */
public class LitteraturKategoriAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        LitteraturLogikk logikk = new LitteraturLogikk();
        List<Litteraturkategori> koder = null;
        List<Litteratur> litteratur = null;
        Integer kategori = null;
        String kategorinavn = null;
        Integer kode = null;
        String kodenavn = null;

        boolean engelsk = false;
        if (request.getAttribute("en") != null) {
            engelsk = true;
        }

        String uri = request.getRequestURI();
        try {
            int i = uri.lastIndexOf("/");
            String s = uri.substring(i + 1);
            kategori = new Integer(s);
        } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            kode = new Integer(request.getParameter("k"));
        } catch (Exception e) {
        }


        try {
            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            if (kode == null) {
                koder = logikk.getKategoriverdier(kategori);
            } else {
                kodenavn = logikk.getKode(kategori, kode);
                litteratur = logikk.getLitteraturTilKode(kategori, kode);
            }

            kategorinavn = logikk.getKategori(kategori);
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

        request.setAttribute("koder", koder);
        request.setAttribute("kategori", kategori);
        request.setAttribute("kategorinavn", kategorinavn);
        request.setAttribute("kodenavn", kodenavn);
        request.setAttribute("litteratur", litteratur);

        if (kode == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur/litteratur_kategori.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/litteratur/litteratur_kategori_kode.jsp");
            rd.forward(request, response);
        }

    }


}
