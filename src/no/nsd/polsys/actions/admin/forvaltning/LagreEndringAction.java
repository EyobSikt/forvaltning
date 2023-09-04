package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.logikk.forvaltning.EndringLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetLogikk;
import no.nsd.polsys.logikk.forvaltning.RelasjonLogikk;
import no.nsd.polsys.logikk.forvaltning.StortingetSaksnummerLogikk;
import no.nsd.polsys.modell.Dato;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class LagreEndringAction {

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
      // modell
      Integer id = null;
      Enhet endring = new Enhet();
      // logikk
      EnhetLogikk enhetLogikk = new EnhetLogikk();
      EndringLogikk endringLogikk = new EndringLogikk();
      RelasjonLogikk relasjonLogikk = new RelasjonLogikk();
      StortingetSaksnummerLogikk stortingetsaksnummerLogikk = new StortingetSaksnummerLogikk();
      // kontroll
      Connection conn = null;
      String paramId = request.getParameter("id");
      String paramIdnum = request.getParameter("idnum");

      String relasjoner = request.getParameter("relasjoner");
      List<Integer> nyeRelasjoner = new ArrayList<Integer>();
      // Andre relasjoner
      String relasjonerAndre = request.getParameter("relasjoner_andre");
      List<Integer> nyeRelasjonerAndre = new ArrayList<Integer>();
      //stortinget saksnummer
      String stortingetsaksnummer = request.getParameter("stortingetsaksnummer");
      List<Integer> nyeStortingetSaksnummer = new ArrayList<Integer>();

      try {
         if (paramId != null) {
            id = new Integer(paramId);
         }

         if (paramIdnum != null) {
            // Obligatorisk
            endring.setIdnum(new Integer(paramIdnum));
         }

         // Obligatorisk
         endring.setEndringskode(new Integer(request.getParameter("endringskode")));

         // Obligatorisk
         int dag = Integer.parseInt(request.getParameter("dag"));
         int maaned = Integer.parseInt(request.getParameter("maaned"));
         int aar = Integer.parseInt(request.getParameter("aar"));
         Dato dato = new Dato(aar, maaned, dag);
         endring.setTidspunkt(dato.getDate());

         // Obligatorisk
         endring.setBekreftetDato(Boolean.parseBoolean(request.getParameter("bekr_dato")));

         endring.setEndringsnummer(Util.strengTilInteger(request.getParameter("endringsnummer")));
         //endring.setSaksnummer(Util.strengTilInteger(request.getParameter("saksnummer")));
         endring.setTilknytningsform(Util.strengTilInteger(request.getParameter("tilknytningsform")));
         endring.setNivaa(Util.strengTilInteger(request.getParameter("nivaa")));
         endring.setCofog(Util.tomStrengTilNull(request.getParameter("cofog")));
         endring.setKortNavn(Util.tomStrengTilNull(request.getParameter("kort_navn")));
         endring.setLangtNavn(Util.tomStrengTilNull(request.getParameter("langt_navn")));
         endring.setEngelskLangtNavn(Util.tomStrengTilNull(request.getParameter("eng_langt_navn")));
         endring.setOverordnetIdnum(Util.strengTilInteger(request.getParameter("overordnet_idnum")));
         endring.setGrunnenhet(Util.strengTilInteger(request.getParameter("grunnenhet")));
         endring.setKommunenummer(Util.strengTilInteger(request.getParameter("kommunenr")));
         endring.setFlyttbar(Util.strengTilInteger(request.getParameter("flyttbar")));
         endring.setDok(Util.tomStrengTilNull(request.getParameter("dok")));
         endring.setEngelskDok(Util.tomStrengTilNull(request.getParameter("eng_dok")));
         endring.setTekniskKommentar(Util.tomStrengTilNull(request.getParameter("teknisk_kommentar")));
         String[] relsplit = relasjoner.split(" ");
         for (String s : relsplit) {
            if (s != null && s.length() != 0) {
               nyeRelasjoner.add(new Integer(s));
            }
         }
         // Andre relasjoner
         relsplit = relasjonerAndre.split(" ");
         for (String s : relsplit) {
            if (s != null && s.length() != 0) {
               nyeRelasjonerAndre.add(new Integer(s));
            }
         }
         // stortinget saksnummer
         String[] saksnummersplit = stortingetsaksnummer.split(" ");
         for (String s : saksnummersplit) {
            if (s != null && s.length() != 0) {
               nyeStortingetSaksnummer.add(new Integer(s));
            }
         }

      } catch (Exception e) {
         request.setAttribute("e", e.toString());
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
         return;
      }


      // Settes til true hvis det oppstår en databasefeil.
      boolean feil = false;

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetLogikk.setConn(conn);
         endringLogikk.setConn(conn);
         relasjonLogikk.setConn(conn);
         stortingetsaksnummerLogikk.setConn(conn);

         conn.setAutoCommit(false);

         // Oppretter ny enhet, hvis nødvendig.
         if (endring.getIdnum() == null) {
            endring.setIdnum(enhetLogikk.registrerNyEnhet());
         }
         // Lagrer endringen.
         if (id == null) {
            id = endringLogikk.registrerNyEndring(endring);
         } else {
            endringLogikk.oppdaterEndring(endring, id);
         }
         // Registrerer relasjoner.
         relasjonLogikk.slettRelasjoner(id);
         if (!nyeRelasjoner.isEmpty()) {
            relasjonLogikk.registrerNyeRelasjoner(id, nyeRelasjoner);
         }
         // Registrerer andre vrelasjoner.
         relasjonLogikk.slettRelasjonerAndre(id);
         if (!nyeRelasjonerAndre.isEmpty()) {
            relasjonLogikk.registrerNyeRelasjonerAndre(id, nyeRelasjonerAndre);
         }
         // registrere stortinget saksnummer
         stortingetsaksnummerLogikk.slettStortingetSaksnummer(id);
         if (!nyeStortingetSaksnummer.isEmpty()) {
            stortingetsaksnummerLogikk.registrerNyeStortingetSaksnummer(id, endring.getIdnum(), nyeStortingetSaksnummer);
         }

         conn.commit();

      } catch (Exception e) {
         try {
            conn.rollback();
         } catch (SQLException ignored) {
         }
         request.setAttribute("e", e.getMessage());
         feil = true;
      } finally {
         try {
            conn.close();
         } catch (Exception ignored) {
            conn = null;
         }
      }

      if (feil) {
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/feilmelding.jsp");
         rd.forward(request, response);
      } else {
         String url = request.getContextPath() + "/forvaltning/endringliste.p?idnum=" + endring.getIdnum() + "&lagret";
         url = response.encodeRedirectURL(url);
         response.sendRedirect(url);
      }
   }
}
