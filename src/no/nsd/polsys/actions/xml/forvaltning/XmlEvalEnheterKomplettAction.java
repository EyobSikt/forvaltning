package no.nsd.polsys.actions.xml.forvaltning;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.CofogCacheFactory;
import no.nsd.polsys.factories.forvaltning.GrunnenhetCacheFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.EvalLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Cofog;
import no.nsd.polsys.modell.forvaltning.DokCache;
import no.nsd.polsys.modell.forvaltning.Enhet;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author hvb
 */
public class XmlEvalEnheterKomplettAction {

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
      EvalLogikk logikk = new EvalLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();

      Map<Integer, DokCache> tilknytninger = null;
      Map<Integer, DokCache> grunnenheter = null;
      Map<String, Cofog> cofoger = null;
      // de aktuelle enheter.
      Map<Integer, List<Enhet>> enheterMedEndring = null;
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      Date fraDato = null;

      boolean engelsk = false;

      PrintWriter out = null;

      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         logikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (engelsk) {
            logikk.brukEngelsk();
         }

         sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();
         Calendar cal = new GregorianCalendar();
         cal.setTime(sistOppdatertDato.getDate());

         cal = new GregorianCalendar();
         cal.setLenient(false);
         cal.clear();
         cal.set(1994, 0, 1); // 1.1.1994
         fraDato = cal.getTime();

         enheterMedEndring = logikk.getEndringer(fraDato);

         if (engelsk) {
            tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
            cofoger = CofogCacheFactory.getDokdataEngelsk(conn);
         } else {
            tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdata(conn);
            cofoger = CofogCacheFactory.getDokdata(conn);
         }

         Set<Integer> keys = enheterMedEndring.keySet();


         response.setContentType("text/xml;charset=UTF-8");

         out = response.getWriter();

         Element eRoot = new Element("root");
         Document xmlDoc = new Document(eRoot);

         for (Integer idnum : keys) {
            List<Enhet> endringer = enheterMedEndring.get(idnum);

            Element eEnhet = new Element("enhet");
            eEnhet.setAttribute("idnum", idnum.toString());


            for (Enhet e : endringer) {

               Element eEndring = new Element("endring");
               eEndring.setAttribute("kode", e.getEndringskode().toString());

               Element eDato = new Element("dato");
               Element eYear = new Element("y");
               Element eMonth = new Element("m");
               Element eDay = new Element("d");

               cal = new GregorianCalendar();
               cal.setLenient(false);
               cal.clear();
               cal.setTime(e.getTidspunkt());

               eYear.addContent("" + cal.get(Calendar.YEAR));
               eMonth.addContent("" + (cal.get(Calendar.MONTH) + 1));
               eDay.addContent("" + cal.get(Calendar.DAY_OF_MONTH));

               eDato.addContent(eYear);
               eDato.addContent(eMonth);
               eDato.addContent(eDay);
               eEndring.addContent(eDato);


               if (e.getLangtNavn() != null) {
                  Element eLangtNavn = new Element("langt_navn");
                  eLangtNavn.addContent(e.getLangtNavn());
                  eEndring.addContent(eLangtNavn);
               }

               if (e.getKortNavn() != null) {
                  Element eKortNavn = new Element("kort_navn");
                  eKortNavn.addContent(e.getKortNavn());
                  eEndring.addContent(eKortNavn);
               }

               if (e.getTilknytningsform() != null) {
                  Element eTilknytningsform = new Element("tilknytningsform");
                  eTilknytningsform.setAttribute("kode", (e.getTilknytningsform() != null ? e.getTilknytningsform().toString() : ""));
                  DokCache tilknyt = tilknytninger.get(e.getTilknytningsform());
                  eTilknytningsform.addContent((tilknyt != null ? tilknyt.getTekstEntall() : null));
                  eEndring.addContent(eTilknytningsform);
               }

               if (e.getGrunnenhet() != null) {
                  Element eGrunnenhet = new Element("grunnenhet");
                  eGrunnenhet.setAttribute("kode", (e.getGrunnenhet() != null ? e.getGrunnenhet().toString() : null));
                  DokCache grunnenhet = grunnenheter.get(e.getGrunnenhet());
                  eGrunnenhet.addContent((grunnenhet != null ? grunnenhet.getTekstEntall() : null));
                  eEndring.addContent(eGrunnenhet);
               }

               if (e.getCofog() != null) {
                  Element eCofog = new Element("cofog");
                  eCofog.setAttribute("kode", e.getCofog());
                  Cofog cofog = cofoger.get(e.getCofog());
                  eCofog.addContent((cofog != null ? cofog.getTekst() : null));
                  eEndring.addContent(eCofog);
               }


               Enhet overordnetDep = e.getOverordnetDepartement();
               if (overordnetDep != null && overordnetDep.getIdnum() != null) {
                  Element eOverordnetDep = new Element("overordnet_dep");
                  eOverordnetDep.setAttribute("idnum", overordnetDep.getIdnum().toString());

                  Element eLangtNavnDep = new Element("langt_navn");
                  eLangtNavnDep.addContent(overordnetDep.getLangtNavn());
                  eOverordnetDep.addContent(eLangtNavnDep);

                  Element eKortNavnDep = new Element("kort_navn");
                  eKortNavnDep.addContent(overordnetDep.getKortNavn());
                  eOverordnetDep.addContent(eKortNavnDep);

                  eEndring.addContent(eOverordnetDep);
               }

               if (e.getRelasjoner() != null && !e.getRelasjoner().isEmpty()) {
                  List<Enhet> rel = e.getRelasjoner();
                  Element eRelasjoner = new Element("relasjoner");
                  for (Enhet re : rel) {
                     Element eRelIdnum = new Element("idnum");
                     eRelIdnum.addContent(re.getIdnum().toString());
                     eRelasjoner.addContent(eRelIdnum);
                  }
                  eEndring.addContent(eRelasjoner);
               }

               if (e.getRelasjonerAndre() != null && !e.getRelasjonerAndre().isEmpty()) {
                  List<Enhet> rel = e.getRelasjonerAndre();
                  Element eRelasjonerAndre = new Element("relasjoner_andre");
                  for (Enhet re : rel) {
                     Element eRelNavn = new Element("langt_navn");
                     eRelNavn.addContent(re.getLangtNavn());
                     eRelasjonerAndre.addContent(eRelNavn);
                  }
                  eEndring.addContent(eRelasjonerAndre);
               }


               eEnhet.addContent(eEndring);
            }

            eRoot.addContent(eEnhet);

         }

         Format format = Format.getPrettyFormat();
         format.setTextMode(Format.TextMode.PRESERVE); // dette er også default.
         format.setEncoding("utf-8");
         XMLOutputter outputter = new XMLOutputter(format);
         outputter.output(xmlDoc, out);

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
         try {
            if (out != null) {
               out.close();
            }
         } catch (Exception ignored) {
            out = null;
         }
      }

   }
}
