package no.nsd.polsys.tags.forvaltning.ansatte;

import java.io.IOException;
import java.util.Iterator;
import java.util.SortedMap;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.modell.forvaltning.Ansatte;

/**
 *
 * @author hvb
 */
public class GrafAndelAnsatteTag extends SimpleTagSupport {

   // ============================================================== Variabler
   private String tittel;
   private SortedMap<Integer, Ansatte> total;
   boolean aarsverk;
   private SortedMap<Integer, Ansatte> data1;
   private SortedMap<Integer, Ansatte> data2;
   private SortedMap<Integer, Ansatte> data3;
   private SortedMap<Integer, Ansatte> data4;
   private String label1;
   private String label2;
   private String label3;
   private String label4;


   // ================================================================ Metoder
   public void setTittel(String tittel) {
      this.tittel = tittel;
   }

   public void setAarsverk(boolean aarsverk) {
      this.aarsverk = aarsverk;
   }

   public void setData1(SortedMap<Integer, Ansatte> data1) {
      this.data1 = data1;
   }

   public void setData2(SortedMap<Integer, Ansatte> data2) {
      this.data2 = data2;
   }

   public void setData3(SortedMap<Integer, Ansatte> data3) {
      this.data3 = data3;
   }

   public void setData4(SortedMap<Integer, Ansatte> data4) {
      this.data4 = data4;
   }

   public void setLabel4(String label4) {
      this.label4 = label4;
   }

   public void setLabel1(String label1) {
      this.label1 = label1;
   }

   public void setLabel2(String label2) {
      this.label2 = label2;
   }

   public void setLabel3(String label3) {
      this.label3 = label3;
   }

   public void setTotal(SortedMap<Integer, Ansatte> total) {
      this.total = total;
   }

   @Override
   public void doTag() throws IOException {

      JspWriter out = this.getJspContext().getOut();

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
      String tempData1 = "";
      String tempData2 = "";
      String tempData3 = "";
      String tempData4 = "";
      Iterator<Integer> iter = this.data1.keySet().iterator();
      while (iter.hasNext()) {
         Integer aar = iter.next();
         if (teller > 0) {
            tempData1 += ",";
            tempData2 += ",";
            tempData3 += ",";
            tempData4 += ",";
         }
         float tall1 = this.getData(aar, this.data1);
         float tall2 = this.getData(aar, this.data2);
         float tall3 = this.getData(aar, this.data3);
         float tall4 = this.getData(aar, this.data4);

         tempData1 += this.formatNumber(tall1);
         tempData2 += this.formatNumber(tall2);
         tempData3 += this.formatNumber(tall3);
         tempData4 += this.formatNumber(tall4);

         if ((int) tall1 > maxdata) {
            maxdata = (int) tall1;
         }
         if ((int) tall2 > maxdata) {
            maxdata = (int) tall2;
         }
         if ((int) tall3 > maxdata) {
            maxdata = (int) tall3;
         }
         if ((int) tall4 > maxdata) {
            maxdata = (int) tall4;
         }

         if (teller == 0) {
            fraAar = aar;
         }
         if (teller == this.data1.size() - 1) {
            tilAar = aar;
         }
         teller++;
      }
      chd += tempData1 + "|" + tempData2 + "|" + tempData3 + "|" + tempData4;

      if (maxdata < 10) {
         maxdata = 10;
      }

      int antallAar = tilAar - fraAar;
      stepX = (antallAar / 10) + 1;


      int maxY = (int) (maxdata * 1.1f);

      if (maxY <= 20) {
         stepY = 1;
      } else if (maxY <= 40) {
         stepY = 2;
      } else if (maxY <= 100) {
         stepY = 5;
      } else {
         stepY = 10;
      }

      // Grid Lines
      float gridY = 100f / ((float) maxY / (float) stepY);

      chg = -1 + "," + gridY;


      String[] label = {this.label1, this.label2, this.label3, this.label4};
      String[] farger = {"0000FF", "FF0000", "000000", "888888"};

      // finner series label;
      for (int i = 0; i < label.length; i++) {
         String farge = farger[i];
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
      chxl += "3:|år|4:|%";

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
      Ansatte tot = this.total.get(aar);
      if (a == null) {
         return 0;
      }
      if (this.aarsverk) {
         float i = (a.getAarsverk() != null ? a.getAarsverk() : 0);
         data = (i / tot.getAarsverk()) * 100f;
      } else {
         float i = (a.getAnsatte() != null ? a.getAnsatte() : 0);
         data = (i / (float) tot.getAnsatte()) * 100f;
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
