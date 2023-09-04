package no.nsd.polsys.actions.forvaltning;

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
import no.nsd.polsys.factories.forvaltning.GrunnenhetCacheFactory;
import no.nsd.polsys.factories.forvaltning.NivaaCacheFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.DokCache;


public class TabellvalgAction {

   
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Connection conn = null;
      DepartementLogikk departementLogikk = new DepartementLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      Map<Integer, DokCache> tilknytningsformMapping = null;
      Map<Integer, DokCache> nivaaMapping = null;
      Map<Integer, DokCache> grunnenhetMapping = null;
      List<DokCache> tilknytningsformer = null;
      List<DokCache> nivaa = null;
      List<DokCache> grunnenheter = null;
      boolean engelsk = false;

      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         departementLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (engelsk) {
            departementLogikk.brukEngelsk();
         }
         sistOppdatertDato = parameterLogikk.getOppdatertDato();

         if (engelsk) {
            tilknytningsformMapping = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
            nivaaMapping = NivaaCacheFactory.getDokdataEngelsk(conn);
            grunnenhetMapping = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
         } else {
            tilknytningsformMapping = TilknytningsformCacheFactory.getDokdata(conn);
            nivaaMapping = NivaaCacheFactory.getDokdata(conn);
            grunnenhetMapping = GrunnenhetCacheFactory.getDokdata(conn);
         }

         tilknytningsformer = new ArrayList<DokCache>(tilknytningsformMapping.values());
         Collections.sort(tilknytningsformer);
         nivaa = new ArrayList<DokCache>(nivaaMapping.values());
         Collections.sort(nivaa);
         grunnenheter = new ArrayList<DokCache>(grunnenhetMapping.values());
         Collections.sort(grunnenheter);

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

      request.setAttribute("sistOppdatertDato", sistOppdatertDato);
      request.setAttribute("tilknytningsformer", tilknytningsformer);
      request.setAttribute("nivaa", nivaa);
      request.setAttribute("grunnenheter", grunnenheter);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/lag_tabell.jsp");
      rd.forward(request, response);
   }

}
