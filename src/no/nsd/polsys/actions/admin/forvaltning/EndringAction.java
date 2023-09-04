package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.CofogCacheFactory;
import no.nsd.polsys.logikk.forvaltning.*;
import no.nsd.polsys.modell.forvaltning.Cofog;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EndringAction {

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
      Integer id = null;
      Integer idnum = null;
      Enhet endring = null;
      String relasjoner = null;
      String stortingetsaksnummer = null;
      String relasjonerAndre = null;
      List<DokCache> endringskoder = null;
      List<DokCache> grunnenheter = null;
      List<DokCache> nivaaer = null;
      List<DokCache> tilknytningsformer = null;
      List<Cofog> cofog = new ArrayList<Cofog>();
      // logikk
      EndringLogikk logikk = new EndringLogikk();
      RelasjonLogikk relasjonLogikk = new RelasjonLogikk();
      StortingetSaksnummerLogikk stortingetsaksnummerLogikk = new StortingetSaksnummerLogikk();
      EndringskodeLogikk endringskodeLogikk = new EndringskodeLogikk();
      GrunnenhetLogikk grunnenhetLogikk = new GrunnenhetLogikk();
      NivaaLogikk nivaaLogikk = new NivaaLogikk();
      TilknytningsformLogikk tilknytningsformLogikk = new TilknytningsformLogikk();
      // kontroll
      Connection conn = null;
      String paramId = request.getParameter("id");
      String paramIdnum = request.getParameter("idnum");

      if (paramId != null) {
         try {
            id = new Integer(paramId);
         } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }
      }

      if (paramIdnum != null) {
         try {
            idnum = new Integer(paramIdnum);
         } catch (Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
            rd.forward(request, response);
            return;
         }
      }

      // skal ikke oppgi både id og idnum.
      if (id != null && idnum != null) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
         rd.forward(request, response);
         return;
      }

      if (idnum != null) {
         endring = new Enhet();
         endring.setIdnum(idnum);
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);
         relasjonLogikk.setConn(conn);
         stortingetsaksnummerLogikk.setConn(conn);
         endringskodeLogikk.setConn(conn);
         grunnenhetLogikk.setConn(conn);
         nivaaLogikk.setConn(conn);
         tilknytningsformLogikk.setConn(conn);

         if (id != null) {
            endring = logikk.getEndring(id);
            if (endring == null) {
               RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");
               rd.forward(request, response);
               return;
            }
            List<Integer> rel = relasjonLogikk.getRelasjoner(id);
            if (rel != null && rel.size() != 0) {
               relasjoner = rel.get(0).toString();
               for (int i = 1; i < rel.size(); i++) {
                  relasjoner += " " + rel.get(i);
               }
            }
            // Andre relasjoner
            rel = relasjonLogikk.getRelasjonerAndre(id);
            if (rel != null && rel.size() != 0) {
               relasjonerAndre = rel.get(0).toString();
               for (int i = 1; i < rel.size(); i++) {
                  relasjonerAndre += " " + rel.get(i);
               }
            }
            //stortinget saksnummer
            List<Integer> saksnummer = stortingetsaksnummerLogikk.getStortingetsaksnummer(id);
            if (saksnummer != null && saksnummer.size() != 0) {
               stortingetsaksnummer = saksnummer.get(0).toString();
               for (int i = 1; i < saksnummer.size(); i++) {
                  stortingetsaksnummer += " " + saksnummer.get(i);
               }
            }

         }

         endringskoder = endringskodeLogikk.getEndringskoder();
         grunnenheter = grunnenhetLogikk.getGrunnenheter();
         nivaaer = nivaaLogikk.getNivaaer();
         tilknytningsformer = tilknytningsformLogikk.getTilknytningsformer();

         Map<String, Cofog> cMapping = CofogCacheFactory.getDokdata(conn);
         cofog.addAll(cMapping.values());
         Collections.sort(cofog);

      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         try {
            conn.close();
         } catch (Exception ignored) {
            conn = null;
         }
      }

      request.setAttribute("endring", endring);
      request.setAttribute("relasjoner", relasjoner);
      request.setAttribute("relasjonerAndre", relasjonerAndre);
      request.setAttribute("stortingetsaksnummer", stortingetsaksnummer);
      request.setAttribute("endringskoder", endringskoder);
      request.setAttribute("grunnenheter", grunnenheter);
      request.setAttribute("nivaaer", nivaaer);
      request.setAttribute("tilknytningsformer", tilknytningsformer);
      request.setAttribute("cofog", cofog);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/endring.jsp");
      rd.forward(request, response);
   }
}
