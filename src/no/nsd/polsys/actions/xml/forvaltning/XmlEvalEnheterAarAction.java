package no.nsd.polsys.actions.xml.forvaltning;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.factories.forvaltning.GrunnenhetCacheFactory;
import no.nsd.polsys.factories.forvaltning.TilknytningsformCacheFactory;
import no.nsd.polsys.logikk.ParameterLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetDivLogikk;
import no.nsd.polsys.modell.Dato;
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
public class XmlEvalEnheterAarAction {

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
      EnhetDivLogikk enhetLogikk = new EnhetDivLogikk();
      ParameterLogikk parameterLogikk = new ParameterLogikk();

      Map<Integer, DokCache> tilknytninger = null;
      Map<Integer, DokCache> grunnenheter = null;
      // de aktuelle enheter.
      List<Enhet> enheter = null;
      // år bruker har oppgitt.
      Integer aar = null;
      // dato databasen er sist oppdatert.
      Dato sistOppdatertDato = null;
      Date fraDato = null;
      Date tilDato = null;

      boolean engelsk = false;

      PrintWriter out = null;

      if (request.getAttribute("en") != null) {
         engelsk = true;
      }

      try {
         aar = new Integer(request.getParameter("y"));
      } catch (Exception e) {
      }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         parameterLogikk.setConn(conn);
         if (engelsk) {
            enhetLogikk.brukEngelsk();
         }

         sistOppdatertDato = parameterLogikk.getOppdatertYtreEnhet();
         Calendar cal = new GregorianCalendar();
         cal.setTime(sistOppdatertDato.getDate());
         if (aar == null) {
            aar = cal.get(Calendar.YEAR);
         }

         cal = new GregorianCalendar();
         cal.setLenient(false);
         cal.clear();
         cal.set(aar, 0, 1); // 1.1.aar
         fraDato = cal.getTime();

         cal = new GregorianCalendar();
         cal.setLenient(false);
         cal.clear();
         cal.set(aar, 11, 31); // 31.12.aar
         tilDato = cal.getTime();


         enheter = enhetLogikk.getEvalEnheterGittAar(fraDato, tilDato);

         if (engelsk) {
            tilknytninger = TilknytningsformCacheFactory.getDokdataEngelsk(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdataEngelsk(conn);
         } else {
            tilknytninger = TilknytningsformCacheFactory.getDokdata(conn);
            grunnenheter = GrunnenhetCacheFactory.getDokdata(conn);
         }


         response.setContentType("text/xml;charset=UTF-8");

         out = response.getWriter();

         Element eRoot = new Element("root");
         Document xmlDoc = new Document(eRoot);

         for (Enhet e : enheter) {
            Element eEnhet = new Element("enhet");
            eEnhet.setAttribute("idnum", e.getIdnum().toString());

            Element eLangtNavn = new Element("langt_navn");
            eLangtNavn.addContent(e.getLangtNavn());
            eEnhet.addContent(eLangtNavn);

            Element eKortNavn = new Element("kort_navn");
            eKortNavn.addContent(e.getKortNavn());
            eEnhet.addContent(eKortNavn);

            if (e.getTilknytningsform() != null) {
               Element eTilknytningsform = new Element("tilknytningsform");
               eTilknytningsform.setAttribute("kode", (e.getTilknytningsform() != null ? e.getTilknytningsform().toString() : null));
               DokCache tilknyt = tilknytninger.get(e.getTilknytningsform());
               eTilknytningsform.addContent((tilknyt != null ? tilknyt.getTekstEntall() : null));
               eEnhet.addContent(eTilknytningsform);
            }

            if (e.getGrunnenhet() != null) {
               Element eGrunnenhet = new Element("grunnenhet");
               eGrunnenhet.setAttribute("kode", (e.getGrunnenhet() != null ? e.getGrunnenhet().toString() : null));
               DokCache grunnenhet = grunnenheter.get(e.getGrunnenhet());
               eGrunnenhet.addContent((grunnenhet != null ? grunnenhet.getTekstEntall() : null));
               eEnhet.addContent(eGrunnenhet);
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

               eEnhet.addContent(eOverordnetDep);
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
