package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.LitteraturLogikk;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Litteratur;

/**
 *
 * @author hvb
 */
public class LagreLitteraturAction {

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
      // modell
      Litteratur litteratur = new Litteratur();
      // logikk
      LitteraturLogikk logikk = new LitteraturLogikk();
      // kontroll
      Connection conn = null;
      String paramPubId = request.getParameter("pub_id");

      try {
         if (paramPubId != null) {
            litteratur.setPubId(new Integer(paramPubId));
         }

         DokCache type = new DokCache();
         litteratur.setType(type);
         type.setKode(Util.strengTilInteger(request.getParameter("type_kode")));

         Litteratur referanse = new Litteratur();
         litteratur.setReferanse(referanse);
         referanse.setPubId(Util.strengTilInteger(request.getParameter("ref_pub_id")));

         litteratur.setTittel(Util.tomStrengTilNull(request.getParameter("tittel")));
         litteratur.setUtgiver(Util.tomStrengTilNull(request.getParameter("utgiver")));
         litteratur.setAar(Util.tomStrengTilNull(request.getParameter("aarstall")));
         litteratur.setForfatter(Util.tomStrengTilNull(request.getParameter("forfatter")));
         litteratur.setLenkePublikasjon(Util.tomStrengTilNull(request.getParameter("lenke_publikasjon")));
         litteratur.setLenkeOmtale(Util.tomStrengTilNull(request.getParameter("lenke_omtale")));
         litteratur.setKommentarEkstern(Util.tomStrengTilNull(request.getParameter("kommentar_ekstern")));
         litteratur.setKommentarIntern(Util.tomStrengTilNull(request.getParameter("kommentar_intern")));

         litteratur.setAntallSider(Util.strengTilInteger(request.getParameter("antall_sider")));

         litteratur.setIsbn(Util.tomStrengTilNull(request.getParameter("isbn")));
         litteratur.setIssn(Util.tomStrengTilNull(request.getParameter("issn")));
         litteratur.setSpraak(Util.tomStrengTilNull(request.getParameter("spraak")));
         litteratur.setLand(Util.tomStrengTilNull(request.getParameter("land")));
         litteratur.setSammendrag(Util.tomStrengTilNull(request.getParameter("sammendrag")));
         litteratur.setTekstfeltUtl(Util.tomStrengTilNull(request.getParameter("tekstfelt_utl")));


      } catch (Exception e) {
         request.setAttribute("e", e.toString());
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
         return;
      }


      // Settes til true hvis det oppstår en databasefeil.
      boolean feil = false;

      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);

         conn.setAutoCommit(false);

         // Oppretter ny litteratur, hvis nødvendig.
         if (litteratur.getPubId() == null) {
            litteratur.setPubId(logikk.registrerNyLitteratur());
         }
         logikk.oppdaterLitteratur(litteratur);

         conn.commit();

      } catch (Exception e) {
         try {
            conn.rollback();
         } catch (SQLException ignored) {
         }
         request.setAttribute("e", e.getMessage());
         feil = true;
      } finally {
         try {
            conn.close();
         } catch (Exception ignored) {
            conn = null;
         }
      }

      if (feil) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
      } else {
         String url = request.getContextPath() + "/forvaltning/litteratur.p?pub_id=" + litteratur.getPubId() + "&lagret";
         url = response.encodeRedirectURL(url);
         response.sendRedirect(url);
      }
   }
}
