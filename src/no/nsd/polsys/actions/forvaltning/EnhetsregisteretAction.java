package no.nsd.polsys.actions.forvaltning;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EnhetsregisteretLogikk;
import no.nsd.polsys.modell.forvaltning.Enhet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvb
 */
public class EnhetsregisteretAction {

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
     List<Enhet> departementer2 = null;
      List<List<Enhet>> staten2 = new ArrayList<>();
       List<Integer> alleAar = new ArrayList<Integer>();

       Integer valgtAar = null;
       try {
           valgtAar = new Integer(request.getParameter("aar"));
       } catch (Exception ignored) {
       }
       Integer orgnummer = null;
       try {
           orgnummer = new Integer(request.getParameter("orgnummer"));
       } catch (Exception ignored) {
       }

      try {
         conn = DatabaseConnectionFactory.getConnection();
         enhetsregisteretLogikk.setConn(conn);
          alleAar = enhetsregisteretLogikk.getAlleAar();

          if (valgtAar == null) {
              valgtAar = alleAar.get(0);
          }

          departementer2 = enhetsregisteretLogikk.getDepartementer2(valgtAar);
          if (orgnummer == null) {

              for (int i = 0; i < departementer2.size(); i++) {
                  staten2.add(enhetsregisteretLogikk.getHierarki3(departementer2.get(i).getIdnum(), valgtAar));
              }
          }
         else{
              staten2.add(enhetsregisteretLogikk.getHierarki3(orgnummer, valgtAar));
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

      request.setAttribute("staten2", staten2);
       request.setAttribute("alleAar", alleAar);
      request.setAttribute("departementer2", departementer2);
       request.setAttribute("valgtAar", valgtAar);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/enhetsregisteret.jsp");
      rd.forward(request, response);
   }
}
