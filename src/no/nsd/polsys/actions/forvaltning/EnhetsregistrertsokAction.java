package no.nsd.polsys.actions.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetsregisteretLogikk;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Enhetsregisteret;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eyob
 */
public class EnhetsregistrertsokAction {

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
      EnhetsregisteretLogikk enhetsregisteretLogikk = new EnhetsregisteretLogikk();
      List<Enhet> departmentlist = null;
      List<Enhet> registrertaar = null;

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetsregisteretLogikk.setConn(conn);

         registrertaar = enhetsregisteretLogikk.getRegistrertaar();
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

      //List<Enhetsregisteret> finalresultat = new ArrayList<Enhetsregisteret>();
      Enhetsregisteret parti = new Enhetsregisteret();
      String sokfelt = "";
      String encoding = request.getCharacterEncoding();
      StringBuffer cleanString = new StringBuffer();
      String[] fq = new String[0];

      /*hente URL parameter*/
      if (request.getParameter("q") != null) {
         sokfelt = request.getParameter("q").toString().trim();
         //sokfelt = parti.escapeQueryChars(request.getParameter("q").toString().trim());
         //sokfelt = sokfelt.replaceAll("(\"(?=\\S)[^\"]*(?<=)\")|\"", "$1");
      }
      if (request.getParameterValues("fq") != null) fq = request.getParameterValues("fq");
      if (fq != null)
         for (String item : fq)
            if (item != null && !item.equals("null") && !item.equals(""))
               cleanString.append("&fq=").append(item);

      request.setAttribute("url", cleanString.toString());


      /*   url fq values til arraylist */
      ArrayList<String> list_orgnummer = new ArrayList<String>();
      List<Integer> tilknytningsformer = new ArrayList<Integer>();
      ArrayList<String> list_variable = new ArrayList<String>();
      String beforecolon;
      String aftercolon;
      StringBuffer sb_orgnummer = new StringBuffer();
      StringBuffer sb_variable = new StringBuffer();
      /*   url fq values til arraylist */
      for (int w = 0; w < fq.length; w++) {
         if (fq[w] == null) {
         } else {
            beforecolon = parti.substringBefore(fq[w], ":");
            aftercolon = parti.substringAfter(fq[w], ":");
            if (beforecolon.equals("orgnummer") && !aftercolon.equals(null) && !aftercolon.equals("")) {
               try {
                  list_orgnummer.add(String.valueOf(Integer.parseInt(aftercolon)));
                  tilknytningsformer.add(new Integer(Integer.parseInt(aftercolon)));
               } catch (Exception e) {
               }
            }
            if (beforecolon.equals("variable") && !aftercolon.equals(null) && !aftercolon.equals("")) {
               try {
                  list_variable.add(String.valueOf(Integer.parseInt(aftercolon)));
               } catch (Exception e) {
               }
            }
         }
      }
         /*arryalist til &fq= type */
      if (list_orgnummer.size() != 0) {
         sb_orgnummer.append("(");
         for (int a = 0; a < list_orgnummer.size(); a++) {
            sb_orgnummer.append(list_orgnummer.get(a));
            sb_orgnummer.append(" ");
         }
         sb_orgnummer.append(")");
      } else {
         sb_orgnummer.append("*:*");
      }
      String[] list_variable2 = new String[8];
      if (list_variable.size() != 0) {
         sb_variable.append("{(");
         for (int d = 0; d < list_variable.size(); d++) {
            sb_variable.append(list_variable.get(d));
            sb_variable.append(" ");
            list_variable2[d] = list_variable.get(d);
         }
         sb_variable.append(")");
      }

      Map<String, Boolean> variabler = this.getVariabelMap(list_variable2);

      ///////////////////////////////////////////////////////////////////////////////////
      List<Enhetsregisteret> finalresultat = new ArrayList<Enhetsregisteret>();
      if(sokfelt==null || sokfelt.equals("") || sokfelt.equals("*")){sokfelt = "";}

      List<Integer> alleAar = new ArrayList<Integer>();
      Integer valgtAar = null;
      try {
         valgtAar = new Integer(request.getParameter("aar"));
      } catch (Exception ignored) {
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetsregisteretLogikk.setConn(conn);
         // ytre enheter, alts� ikke intern --> false.

         alleAar = enhetsregisteretLogikk.getAlleAar();

         if (valgtAar == null) {
            valgtAar = alleAar.get(0);
         }
         departmentlist = enhetsregisteretLogikk.getDepartmentlist(valgtAar);
         finalresultat = enhetsregisteretLogikk.getAntallDepartementsenhet(sokfelt,  tilknytningsformer, valgtAar);

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




      ///////////////////////////////////////////////////////////////////////////////////



/*

      if(sokfelt==null || sokfelt.equals("") || sokfelt.equals("*")){sokfelt = "*:*";}
      try {
         // build a lucene index
         conn = DatabaseConnectionFactory.getConnection();
         Indexer indexer = new Indexer();
         indexer.rebuildIndexes(conn);


         // perform search on "Notre Dame museum"
         // and retrieve the top 100 result
         SearchEngine se = new SearchEngine();
         //if you want to bring from input use the following  int maxHits = Integer.parseInt(args[2]);

         int pageIndex = 0;
         int pageSize = 10;
         if (request.getParameter("start") != null && request.getParameter("start") != ""){try {pageIndex = Integer.parseInt(request.getParameter("start"));}catch (Exception e) { RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp"); rd.forward(request, response);return;}} else {pageIndex = 0;}
         if (request.getParameter("rows") != null && request.getParameter("rows") != ""){try {pageSize = Integer.parseInt(request.getParameter("rows"));}catch (Exception e) {RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp"); rd.forward(request, response);return;}} else {pageSize = 10;}
         if(pageSize <=0 || pageIndex < 0){ RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");rd.forward(request, response);return;}

         int startIndex = pageIndex;
         int endIndex = pageIndex + pageSize;

         TopDocs topDocs = se.performSearch(sokfelt, sb_orgnummer, indexer.totalNummerHits);
         int antalltreff = (int) topDocs.totalHits;
         request.setAttribute("antalltreff", antalltreff);

         ScoreDoc[] hits = topDocs.scoreDocs;

            for (int i = 0; i < antalltreff ; i++) {
                Enhetsregisteret sokresultat = new Enhetsregisteret();
                Document doc = se.getDocument(hits[i].doc);

               if (!doc.get("level_1").equals("null")) { sokresultat.setLevel_1(doc.get("level_1"));}
               if (!doc.get("level_2").equals("null")) { sokresultat.setLevel_2(doc.get("level_2"));}
               if (!doc.get("level_3").equals("null")) { sokresultat.setLevel_3(doc.get("level_3"));}
               if (!doc.get("level_4").equals("null")) { sokresultat.setLevel_4(doc.get("level_4"));}

                sokresultat.setIdnum(Integer.valueOf(doc.get("topLevel")));
                sokresultat.setNavn(doc.get("navn"));

                sokresultat.setOverordnetEnhet(Integer.valueOf(doc.get("overordnetEnhet")));
                sokresultat.setYear(Integer.valueOf(doc.get("aar")));

                if (!doc.get("sst_antall_menn").equals("null")) {
                    sokresultat.setAntallAnsatte_menn(Integer.valueOf(doc.get("sst_antall_menn")));
                }
                if (!doc.get("sst_antall_kvinner").equals("null")) {
                    sokresultat.setAntallAnsatte_kvinner(Integer.valueOf(doc.get("sst_antall_kvinner")));
                }

               if (!doc.get("organisasjonsform").equals("null")) {sokresultat.setOrganisasjonsform(doc.get("organisasjonsform"));}
               if (!doc.get("sektorkode").equals("null")) { sokresultat.setSektorkode(doc.get("sektorkode") + "(" + doc.get("sektorkode_besk") + ")");}
               if (!doc.get("naringskode").equals("null")) { sokresultat.setNaringskode(doc.get("naringskode") + "(" + doc.get("naringskode_besk") + ")");}

               if (!doc.get("nsd_idnum").equals("null")) {
                    sokresultat.setNsd_idnum(Integer.valueOf(doc.get("nsd_idnum")));
           }

            finalresultat.add(sokresultat);
         }
      } catch (Exception e) {
         System.out.println(e);
      }
*/
      request.setAttribute("departmentlist", departmentlist);
      request.setAttribute("registrertaar", registrertaar);


      //request.setAttribute("finalresultat", finalresultat);
      request.setAttribute("variabler", variabler);

      request.setAttribute("finalresultat", finalresultat);
      request.setAttribute("antalltreff", finalresultat.size());

      request.setAttribute("alleAar", alleAar);
      request.setAttribute("valgtAar", valgtAar);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhetregistrertsok.jsp");
      rd.forward(request, response);
   }

   private Map<String, Boolean> getVariabelMap(String[] tab) {
      Map<String, Boolean> v = new HashMap<String, Boolean>();

      if (tab == null || tab.length == 0) {
         return v;
      }
      for (String s : tab) {
         v.put(s, Boolean.TRUE);
      }

      return v;
   }
}
