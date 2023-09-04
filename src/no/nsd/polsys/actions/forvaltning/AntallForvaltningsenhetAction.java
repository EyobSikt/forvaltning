package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.DepartementLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Departementsenhet;
import no.nsd.polsys.modell.forvaltning.DokCache;

/**
 *
 * @author hvb
 */
public class AntallForvaltningsenhetAction {

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
      DepartementLogikk departementLogikk = new DepartementLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;

      List<Departementsenhet> depenhet = null;

      int fraAar = 1947;
      int tilAar = 2010;
      int maaned = 1;
      int dag = 1;

      List<Date> datoer = new ArrayList<Date>();
      List<Integer> tilknytningsformer = new ArrayList<Integer>();
      Map<Integer, DokCache> tilknytningsformerNavn = null;

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         fraAar = Integer.parseInt(request.getParameter("fra"));
         tilAar = Integer.parseInt(request.getParameter("til"));
         maaned = Integer.parseInt(request.getParameter("m"));
         dag = Integer.parseInt(request.getParameter("d"));
         if (fraAar > tilAar) {
            throw new Exception();
         }
      } catch (Exception e) {
         request.setAttribute("feilTid", true);
         this.forward(request, response);
         return;
      }

      try {
         String[] n = request.getParameterValues("t");
         if (n == null || n.length == 0) {
            throw new Exception();
         }
         for (String s : n) {
            tilknytningsformer.add(new Integer(s));
         }
      } catch (Exception e) {
         request.setAttribute("feilValg", true);
         this.forward(request, response);
         return;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         departementLogikk.setConn(conn);
         parameterLogikk.setConn(conn);

         sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();
         for (int aar = fraAar; aar <= tilAar; aar++) {
            Calendar cal = new GregorianCalendar();
            cal.setLenient(true);
            cal.clear();
            cal.set(aar, maaned - 1, dag);

            if (cal.getTime().after(sistOppdatertDato.getDate())) {
               datoer.add(sistOppdatertDato.getDate());
            } else {
               datoer.add(cal.getTime());
            }
         }

         // ytre enheter, altså ikke intern --> false.
         depenhet = departementLogikk.getAntallDepartementsenhet(datoer, tilknytningsformer, false);

         if (engelsk) {
            tilknytningsformerNavn = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
         } else {
            tilknytningsformerNavn = TilknytningsformCacheFactory.getDokdata(conn);
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

      // --------------------------------- start: Google Chart
      // Chart type.
      String cht = "lc";
      // data eks.: t:50,30,40|70,90,60
      String chd = "t:";
      // størrensen bxh.
      String chs = "640x450";
      // Akser
      String chxt = "x,y,r,x,y";
      // axis range: aksenr, min, max, step. Eks.: 0,2008,2010,1|1,0,110,10
      String chxr = "";
      // y-akse min, max. Eks.: 0,110
      String chds = "";
      // Series label. Eks.: avdeling|kvasiavdeling
      String chdl = "";
      // plassering av series label.
      String chdlp = "t";
      // series farger, eks.: 000000,aaaaaa
      String chco = "";
      // series punktsymbol. Eks.: s,000000,0,-1,5|s,aaaaaa,1,-1,5
      String chm = "";

      // axis label. <axis_index>:|<label_1>|...|<label_n>
      String chxl = "";
      // Axis label position.
      String chxp = "";
      // tykkelse på linjen.
      String chls = "";

      // title.
      String chtt = "Antall forvaltningsenheter fordelt på tilknytningsform";
      if (engelsk) {
         chtt = "Number of bodies within ministries distributed on administrative levels";
      }

      // title style. farge,size.
      String chts = "305080,14";

      int maxdata = 0;
      int stepX = 1;
      int stepY = 10;

      int antallAar = tilAar - fraAar;
      stepX = (antallAar / 20) + 1;

      // finner data og maxdata
      for (int i = 0; i < tilknytningsformer.size(); i++) {
         if (i > 0) {
            chd += "|";
         }
         for (int j = 0; j < depenhet.size(); j++) {
            Departementsenhet de = depenhet.get(j);
            int[] antall = de.getAntall();
            if (j > 0) {
               chd += ",";
            }
            chd += antall[i];
            if (antall[i] > maxdata) {
               maxdata = antall[i];
            }
         }
      }

      maxdata += 10;

      // finner series label;
      for (int i = 0; i < tilknytningsformer.size(); i++) {
         Integer n = tilknytningsformer.get(i);
         DokCache d = tilknytningsformerNavn.get(n);
         String farge = Util.getFarge(i);
         if (i > 0) {
            chdl += "|"; // label
            chco += ","; // farge
            chm += "|"; // punktsymbol
            chls += "|"; // linjetykkelse
         }
         chdl += d.getTekstFlertall();
         chco += farge;
         chm += "s," + farge + "," + i + ",-1,5";
         chls += "1.5";
      }

      chxp += "3,50|4,50";
      chxl += "3:|år|4:|n";

      chxr = "0," + fraAar + "," + tilAar + "," + stepX + "|1,0," + maxdata + "," + stepY;
      chxr += "|2,0," + maxdata + "," + stepY; // r-akse.
      chds = "0," + maxdata;

      String chartUrl = "http://chart.apis.google.com/chart?"
              + "cht=" + cht + "&"
              + "chd=" + chd + "&"
              + "chs=" + chs + "&"
              + "chxt=" + chxt + "&"
              + "chxr=" + chxr + "&"
              + "chds=" + chds + "&"
              + "chdl=" + chdl + "&"
              + "chdlp=" + chdlp + "&"
              + "chco=" + chco + "&"
              + "chm=" + chm + "&"
              + "chtt=" + chtt + "&"
              + "chxl=" + chxl + "&"
              + "chxp=" + chxp + "&"
              + "chls=" + chls + "&"
              + "chts=" + chts;

      chartUrl = Util.encodeString(chartUrl);
      // --------------------------------- slutt: Google Chart


      request.setAttribute("chartUrl", chartUrl);
      request.setAttribute("tilknytningsformerNavn", tilknytningsformerNavn);
      request.setAttribute("tilknytningsformer", tilknytningsformer);
      request.setAttribute("depenhet", depenhet);
      request.setAttribute("sistOppdatertDato", sistOppdatertDato);

      this.forward(request, response);
   }

   private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/antall_forvaltningsenhet.jsp");
      rd.forward(request, response);
   }
}
