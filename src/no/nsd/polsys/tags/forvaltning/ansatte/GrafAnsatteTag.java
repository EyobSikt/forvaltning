package no.nsd.polsys.tags.forvaltning.ansatte;

import java.io.IOException;
import java.util.Iterator;
import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.modell.forvaltning.Ansatte;

/**
 *
 * @author hvb
 */
public class GrafAnsatteTag extends SimpleTagSupport {

   // ============================================================== Variabler
   String tittel;
   boolean aarsverk;
   SortedMap<Integer, Ansatte> total;
   SortedMap<Integer, Ansatte> menn;
   SortedMap<Integer, Ansatte> kvinner;


   // ================================================================ Metoder
   public void setTittel(String tittel) {
      this.tittel = tittel;
   }

   public void setAarsverk(boolean aarsverk) {
      this.aarsverk = aarsverk;
   }

   public void setKvinner(SortedMap<Integer, Ansatte> kvinner) {
      this.kvinner = kvinner;
   }

   public void setMenn(SortedMap<Integer, Ansatte> menn) {
      this.menn = menn;
   }

   public void setTotal(SortedMap<Integer, Ansatte> total) {
      this.total = total;
   }

   @Override
   public void doTag() throws IOException {

      JspWriter out = this.getJspContext().getOut();

      PageContext pageContext = (PageContext) this.getJspContext();
      HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

      boolean engelsk = false;
      if (request.getAttribute("en") != null) {
         engelsk = true;
      }


      // Chart type.
      String cht = "lc";
      // data eks.: t:50,30,40|70,90,60
      String chd = "t:";
      // størrelsen bxh.
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

      // Grid Lines
      String chg = "";

      // title.
      String chtt = this.tittel;

      // title style. farge,size.
      String chts = "305080,14";

      int maxdata = 0;
      int stepX = 1;
      int stepY = 10;
      int fraAar = 0;
      int tilAar = 0;

      // finner data og maxdata
      int teller = 0;
      String tempTotal = "";
      String tempMenn = "";
      String tempKvinner = "";
      Iterator<Integer> iter = this.total.keySet().iterator();
      while (iter.hasNext()) {
         Integer aar = iter.next();
         if (teller > 0) {
            tempTotal += ",";
            tempMenn += ",";
            tempKvinner += ",";
         }
         float dataTotal = this.getData(aar, this.total);
         float dataMenn = this.getData(aar, this.menn);
         float dataKvinner = this.getData(aar, this.kvinner);

         tempTotal += this.formatNumber(dataTotal);
         tempMenn += this.formatNumber(dataMenn);
         tempKvinner += this.formatNumber(dataKvinner);

         if ((int) dataTotal > maxdata) {
            maxdata = (int) dataTotal;
         }
         if (teller == 0) {
            fraAar = aar;
         }
         if (teller == this.total.size() - 1) {
            tilAar = aar;
         }
         teller++;
      }
      chd += tempTotal + "|" + tempMenn + "|" + tempKvinner;

      if (maxdata < 10) {
         maxdata = 10;
      }

      int antallAar = tilAar - fraAar;
      stepX = (antallAar / 10) + 1;

      // regner ut aksesteg
      double exp = Math.floor(Math.log10((double) maxdata));
      double prod = Math.pow(10d, exp);
      int fakt = (int) Math.ceil((double) maxdata / prod);
      int t = fakt * (int) prod;
      stepY = t / 10;
      int f2 = (int) Math.ceil((double) maxdata / (double) stepY);
      int t2 = stepY * f2;
      if (f2 < 10 && stepY > 1) {
         stepY = (int) Math.ceil((float) stepY / 2f);
      }
      int maxY = t2;
      if (t2 == maxdata) {
         maxY += stepY;
      }


      // Grid Lines
      float gridY = 100f / (float) (maxY / stepY);

      chg = -1 + "," + gridY;


      String[] label = {"Total", "Menn", "Kvinner"};
      if (engelsk) {
         label[0] = "Total";
         label[1] = "Men";
         label[2] = "Women";
      }

      // finner series label;
      for (int i = 0; i < label.length; i++) {
         String farge = Util.getFarge(i);
         if (i > 0) {
            chdl += "|"; // label
            chco += ","; // farge
            chm += "|"; // punktsymbol
            chls += "|"; // linjetykkelse
         }
         chdl += label[i];
         chco += farge;
         chm += "s," + farge + "," + i + ",-1,5";
         chls += "1.5";
      }

      chxp += "3,50|4,50";
      if (engelsk) {
         chxl += "3:|Year|4:|N";
      } else {
         chxl += "3:|år|4:|N";
      }

      chxr += "0," + fraAar + "," + tilAar + "," + stepX + "|"; // x-akse
      chxr += "1,0," + maxY + "," + stepY; // y-akse.
      chxr += "|2,0," + maxY + "," + stepY; // r-akse.
      chds = "0," + maxY;

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
              + "chg=" + chg + "&"
              + "chts=" + chts;

      chartUrl = Util.encodeString(chartUrl);

      out.println("<img alt=\"graf\" class=\"chart_img_2\" src=\"" + chartUrl + "\" />");

   }

   private float getData(Integer aar, SortedMap<Integer, Ansatte> map) {
      float data = 0;
      if (map == null) {
         return 0;
      }
      Ansatte a = map.get(aar);
      if (a == null) {
         return 0;
      }
      if (this.aarsverk) {
         if (a.getAarsverk() != null) {
            data = a.getAarsverk();
         }
      } else {
         data = (a.getAnsatte() != null ? a.getAnsatte() : 0);
      }
      return data;
   }

   private String formatNumber(Float f) {
      if (f == null) {
         return "-1";
      }
      int i = Math.round(f * 10);
      int des = i % 10;
      int hel = i / 10;
      return hel + "." + des;
   }
}
