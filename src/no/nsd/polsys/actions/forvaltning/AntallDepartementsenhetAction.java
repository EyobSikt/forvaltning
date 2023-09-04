package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.NivaaCacheFactory;
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
public class AntallDepartementsenhetAction {

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
      List<Integer> nivaaer = new ArrayList<Integer>();
      Map<Integer, DokCache> nivaaNavn = null;

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
        //new after rewrite rule for the new website
         final String[] strLoadID = request.getParameterValues("n");
         String[] n = strLoadID[0].split(",");
        //old before rewrite rule for the new website
         /*String[] n = request.getParameterValues("n");*/
         if (n == null || n.length == 0) {
            throw new Exception();
         }
         for (String s : n) {
            nivaaer.add(new Integer(s));
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

         sistOppdatertDato = parameterLogikk.getOppdatertInternEnhet();
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

         // interne enheter, altså intern --> true.
         depenhet = departementLogikk.getAntallDepartementsenhet(datoer, nivaaer, true);

         if (engelsk) {
            nivaaNavn = NivaaCacheFactory.getDokdataEngelsk(conn);
         } else {
            nivaaNavn = NivaaCacheFactory.getDokdata(conn);
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


      String chartUrl = this.lagGoogleChart(engelsk, fraAar, tilAar, nivaaer, nivaaNavn, depenhet);

      request.setAttribute("chartUrl", chartUrl);
      request.setAttribute("nivaaNavn", nivaaNavn);
      request.setAttribute("nivaaer", nivaaer);
      request.setAttribute("depenhet", depenhet);
      request.setAttribute("sistOppdatertDato", sistOppdatertDato);

      this.forward(request, response);
   }

   private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/antall_departementsenhet.jsp");
      rd.forward(request, response);
   }

   private String lagGoogleChart(boolean engelsk, int fraAar, int tilAar,
           List<Integer> nivaaer, Map<Integer, DokCache> nivaaNavn, List<Departementsenhet> depenhet) {
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
      String chdlp = "t|l";
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
      String chtt = "Antall interne departementsenheter fordelt på administrative nivåer";
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
      for (int i = 0; i < nivaaer.size(); i++) {
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
      for (int i = 0; i < nivaaer.size(); i++) {
         Integer n = nivaaer.get(i);
         DokCache d = nivaaNavn.get(n);
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


      chxr += "0," + fraAar + "," + tilAar + "," + stepX + "|"; // x-akse
      chxr += "1,0," + maxdata + "," + stepY; // y-akse.
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

      return chartUrl;
   }
}
