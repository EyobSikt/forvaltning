package no.nsd.polsys.actions.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.*;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.ansatte.AnsatteLogikk;
import no.nsd.polsys.modell.forvaltning.Ansatte;
import no.nsd.polsys.modell.forvaltning.Cofog;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author hvb
 */
public class EnhetAnsatteAction {

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
      AnsatteLogikk logikk = new AnsatteLogikk();

      SortedMap<Integer, Ansatte> total = null;
      SortedMap<Integer, Ansatte> mennTotal = null;
      SortedMap<Integer, Ansatte> kvinnerTotal = null;

      SortedMap<Integer, Ansatte> heltid = null;
      SortedMap<Integer, Ansatte> mennHeltid = null;
      SortedMap<Integer, Ansatte> kvinnerHeltid = null;

      SortedMap<Integer, Ansatte> deltid = null;
      SortedMap<Integer, Ansatte> mennDeltid = null;
      SortedMap<Integer, Ansatte> kvinnerDeltid = null;

      Integer idnum = (Integer) request.getAttribute("idnum");
      List<Enhet> endringer = null;

      Map<Integer, DokCache> endringskoder = null;
      Map<Integer, DokCache> tilknytninger = null;
      Map<Integer, DokCache> nivaaer = null;
      Map<Integer, DokCache> grunnenheter = null;
      Map<String, Cofog> cofog = null;

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      Integer lonnskategori = null;
      if(request.getParameterMap().containsKey("lk"))
      try {
         lonnskategori = new Integer(request.getParameter("lk"));
      } catch (Exception ignored) {
         // ved alle lønnskategorier er lk = a, altså lonnskategori vil forbli null.
         lonnskategori = null;
      }
      else{lonnskategori = 2;}


      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);
         enhetLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }

         logikk.beregnAnsatteTilEnhet(idnum, lonnskategori);

         total = logikk.getTotal();
         mennTotal = logikk.getMennTotal();
         kvinnerTotal = logikk.getKvinnerTotal();

         heltid = logikk.getHeltid();
         mennHeltid = logikk.getMennHeltid();
         kvinnerHeltid = logikk.getKvinnerHeltid();

         deltid = logikk.getDeltid();
         mennDeltid = logikk.getMennDeltid();
         kvinnerDeltid = logikk.getKvinnerDeltid();

         endringer = enhetLogikk.getEndringer(idnum, true);

         if (engelsk) {
            endringskoder = EndringskodeCacheFactory.getDokdataEngelsk(conn);
            tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
            nivaaer = NivaaCacheFactory.getDokdataEngelsk(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
            cofog = CofogCacheFactory.getDokdataEngelsk(conn);
         } else {
            endringskoder = EndringskodeCacheFactory.getDokdata(conn);
            tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
            nivaaer = NivaaCacheFactory.getDokdata(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdata(conn);
            cofog = CofogCacheFactory.getDokdata(conn);
         }

         List relevanteEndringer = new ArrayList<Enhet>();
         if (endringer != null) {
            for (Enhet e : endringer) {

               if (e != null && e.getEndringskode() != null) {
                  if (e.getEndringskode().equals(202)
                          || e.getEndringskode().equals(203)
                          || e.getEndringskode().equals(207)
                          || e.getEndringskode().equals(209)
                          || e.getEndringskode().equals(211)
                          || e.getEndringskode().equals(222)
                          || e.getEndringskode().equals(223)
                          || e.getEndringskode().equals(292)) {
                     relevanteEndringer.add(e);
                  }
               }
            }
         }
         endringer = relevanteEndringer;

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

      request.setAttribute("endringer", endringer);

      request.setAttribute("total", total);
      request.setAttribute("mennTotal", mennTotal);
      request.setAttribute("kvinnerTotal", kvinnerTotal);

      request.setAttribute("heltid", heltid);
      request.setAttribute("mennHeltid", mennHeltid);
      request.setAttribute("kvinnerHeltid", kvinnerHeltid);

      request.setAttribute("deltid", deltid);
      request.setAttribute("mennDeltid", mennDeltid);
      request.setAttribute("kvinnerDeltid", kvinnerDeltid);

      request.setAttribute("endringskoder", endringskoder);
      request.setAttribute("tilknytninger", tilknytninger);
      request.setAttribute("nivaaer", nivaaer);
      request.setAttribute("grunnenheter", grunnenheter);
      request.setAttribute("cofog", cofog);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_ansatte.jsp");
      rd.forward(request, response);
   }
}
