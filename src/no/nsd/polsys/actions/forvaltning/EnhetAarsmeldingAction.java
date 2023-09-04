package no.nsd.polsys.actions.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.AarsmeldingLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetUtilLogikk;
import no.nsd.polsys.modell.forvaltning.Aarsmelding;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 * Action-klasse som viser årsmeldingssiden i fvdb.
 * @author hvb
 */
public class EnhetAarsmeldingAction {

   private HttpServletRequest request;
   private final AarsmeldingLogikk aarsmeldingLogikk = new AarsmeldingLogikk();
   private List<Aarsmelding> aarsmeldinger;
   private Enhet enhet;
   private boolean engelsk;
   
   /**
    * Prosesserer forespørselen og henter relevant data og videresender til jsp-side.
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.request = request;
      enhet = (Enhet) request.getAttribute("enhet");

      if (request.getAttribute("en") != null) {
         engelsk = true;
      }
      
      this.getData();
      
      request.setAttribute("aarsmeldinger", aarsmeldinger);
      HttpUtil.forward("/WEB-INF/jsp/forvaltning/enhet_aarsmelding.jsp", request, response);
   }
   
   
   private void getData() throws ServletException {
      Connection conn = null;
      
      try {
         conn = DatabaseConnectionFactory.getConnection();
         aarsmeldingLogikk.setConn(conn);
         aarsmeldinger = aarsmeldingLogikk.getAarsmeldingerTilRegEnhet(enhet.getIdnum());
         
         if (aarsmeldinger == null || aarsmeldinger.isEmpty()) {
            getAarsmeldingerTilOverordnet(conn);
         }
         
      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         SqlUtil.close(conn);
      }
   }

   
   private void getAarsmeldingerTilOverordnet(Connection conn) throws SQLException {
      EnhetUtilLogikk util = new EnhetUtilLogikk();
      util.setConn(conn);
      if (this.engelsk) {
         util.brukEngelsk();
      }
      util.beregnEnheter(null);

      Enhet overordnet = util.getOverordnetEtat(enhet);
      
      if (overordnet != null) {
         aarsmeldinger = aarsmeldingLogikk.getAarsmeldingerTilRegEnhet(overordnet.getIdnum());
         request.setAttribute("overordnet", overordnet);
      }
      
   }
   
   
}
