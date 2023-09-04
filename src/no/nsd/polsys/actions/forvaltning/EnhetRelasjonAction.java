package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.EndringskodeCacheFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.EnhetRelasjon;

public class EnhetRelasjonAction {


   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Connection conn = null;

      EnhetDivLogikk enhetDivLogikk = new EnhetDivLogikk();
      
      Map<Integer, Enhet> mappingIdnumToUnit;
      List<EnhetRelasjon> alleRelasjoner;
      List<EnhetRelasjon> relevanteRelasjoner;
      Map<Integer, DokCache> endringskoder = null;

      Enhet enhet = (Enhet) request.getAttribute("enhet");

      String paramAlle = request.getParameter("alle");
      
      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetDivLogikk.setConn(conn);
         
         if (engelsk) {
            enhetDivLogikk.brukEngelsk();
         }
         
         if (paramAlle != null) {
            mappingIdnumToUnit = enhetDivLogikk.getMappingIdnumToUnit();
            alleRelasjoner = this.getAlleRelasjoner(conn, mappingIdnumToUnit);
            relevanteRelasjoner = this.getRelevanteRelasjoner(alleRelasjoner, enhet);
         } else {
            mappingIdnumToUnit = enhetDivLogikk.getMappingIdnumToUnit();
            relevanteRelasjoner = this.getDirekteRelasjoner(conn, mappingIdnumToUnit, enhet);
         }

         if (relevanteRelasjoner != null) {
            Collections.sort(relevanteRelasjoner);
         }

         if (engelsk) {
            endringskoder = EndringskodeCacheFactory.getDokdataEngelsk(conn);
         } else {
            endringskoder = EndringskodeCacheFactory.getDokdata(conn);
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

      request.setAttribute("relevanteRelasjoner", relevanteRelasjoner);

      request.setAttribute("endringskoder", endringskoder);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhet_relasjon.jsp");
      rd.forward(request, response);
   }
   
   public List<EnhetRelasjon> getDirekteRelasjoner(Connection conn, Map<Integer, Enhet> map, Enhet enhet) throws SQLException {
      List<EnhetRelasjon> liste = new ArrayList<EnhetRelasjon>();
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select e.id, e.tidspunkt, e.idnum as unita, e.endringskode, r.idnum as unitb "
              + "from t_forvaltning_relasjon as r inner join t_forvaltning_endring as e on r.endringsid = e.id "
              + "where e.idnum = ? or r.idnum = ?";

      sqlCB.setConnection(conn);
      sqlCB.setSqlValue(sqlSelect);

      List<Integer> values = new ArrayList<Integer>();
      values.add(enhet.getIdnum());
      values.add(enhet.getIdnum());
     
      sqlCB.setValues(values);
      Result result = sqlCB.executeQuery();
      SortedMap[] rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         EnhetRelasjon er = new EnhetRelasjon();
         liste.add(er);
         
         Integer a = (Integer) rad.get("unita");
         Integer b = (Integer) rad.get("unitb");
         
         er.setEnhetA(map.get(a));
         er.setEnhetB(map.get(b));
         
         er.setId((Integer) rad.get("id"));
         er.setTidspunkt((Date) rad.get("tidspunkt"));
         er.setEndringskode((Integer) rad.get("endringskode"));
      }

      return liste;
   }
   
   public List<EnhetRelasjon> getAlleRelasjoner(Connection conn, Map<Integer, Enhet> map) throws SQLException {
      List<EnhetRelasjon> liste = new ArrayList<EnhetRelasjon>();
      SqlCommandBean sqlCB = new SqlCommandBean();
      String sqlSelect = "select e.id, e.tidspunkt, e.idnum as unita, e.endringskode, r.idnum as unitb "
              + "from t_forvaltning_relasjon as r inner join t_forvaltning_endring as e on r.endringsid = e.id";

      sqlCB.setConnection(conn);
      sqlCB.setSqlValue(sqlSelect);
      Result result = sqlCB.executeQuery();
      SortedMap[] rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         EnhetRelasjon er = new EnhetRelasjon();
         liste.add(er);
         
         Integer a = (Integer) rad.get("unita");
         Integer b = (Integer) rad.get("unitb");
         
         er.setEnhetA(map.get(a));
         er.setEnhetB(map.get(b));
         
         er.setId((Integer) rad.get("id"));
         er.setTidspunkt((Date) rad.get("tidspunkt"));
         er.setEndringskode((Integer) rad.get("endringskode"));
      }

      return liste;
   }
      
   
   public List<EnhetRelasjon> getRelevanteRelasjoner(List<EnhetRelasjon> alleRelasjoner, Enhet enhet) {
      List<EnhetRelasjon> relevanteRelasjoner = new ArrayList<EnhetRelasjon>();
      
      Set<Enhet> enheter = new HashSet<Enhet>();
      enheter.add(enhet);
      
      int antall = 0;
      do {
         antall = relevanteRelasjoner.size();
         
         for (EnhetRelasjon er : alleRelasjoner) {
            if (er.isValgt()) {
               continue;
            }
            
            if (enheter.contains(er.getEnhetA()) || enheter.contains(er.getEnhetB())) {
               enheter.add(er.getEnhetA());
               enheter.add(er.getEnhetB());
               relevanteRelasjoner.add(er);
               er.setValgt(true);
            }
         }
      } while (relevanteRelasjoner.size() > antall);
      
      
      return relevanteRelasjoner;
   }
   
   
   
   
   
}
