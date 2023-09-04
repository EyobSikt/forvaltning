package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.ArkivLogikk;
import no.nsd.polsys.logikk.forvaltning.DbhdbLinkLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetUtilLogikk;
import no.nsd.polsys.modell.forvaltning.ArkivEnhet;
import no.nsd.polsys.modell.forvaltning.DbhdbLink;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EnhetLenkerAction {

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
      EnhetLogikk enhetLogikk = new EnhetLogikk();
      EnhetUtilLogikk util = new EnhetUtilLogikk();
      ArkivLogikk arkivLogikk = new ArkivLogikk();
      DbhdbLinkLogikk dbhdbLinkLogikk = new DbhdbLinkLogikk();
      
      List<Integer> orgnr = null;
      // Om vi skal linke til evalueringsportalen.
      boolean visEvallink = false;
      Integer selskapsdbId = null;
      List<ArkivEnhet> arkivenheter = null;
      List<DbhdbLink> dbhInstkode = null;

      Integer idnum = (Integer) request.getAttribute("idnum");
      Enhet enhet = (Enhet) request.getAttribute("enhet");

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         util.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }
         arkivLogikk.setConn(conn);
         dbhdbLinkLogikk.setConn(conn);

         orgnr = enhetLogikk.getOrgnrTilEnhet(idnum);
         selskapsdbId = enhetLogikk.getSelskapsidTilEnhet(idnum);
         arkivenheter = arkivLogikk.getArkivEnheter(idnum);
         dbhInstkode = dbhdbLinkLogikk.getDbhdbLinkTilEnhet(idnum);

         util.beregnEnheter(null);
         visEvallink = util.erEvalEnhet(enhet);

      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         SqlUtil.close(conn);
      }

      request.setAttribute("orgnr", orgnr);
      request.setAttribute("visEvallink", visEvallink);
      request.setAttribute("selskapsdbId", selskapsdbId);
      request.setAttribute("arkivenheter", arkivenheter);
      request.setAttribute("dbhInstkode", dbhInstkode);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_lenker.jsp");
      rd.forward(request, response);
   }
}
