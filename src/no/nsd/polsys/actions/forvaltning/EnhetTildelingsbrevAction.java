package no.nsd.polsys.actions.forvaltning;

import no.nsd.common.util.HttpUtil;
import no.nsd.common.util.SqlUtil;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.TildelingsbrevLogikk;
import no.nsd.polsys.logikk.forvaltning.EnhetUtilLogikk;
import no.nsd.polsys.modell.forvaltning.Tildelingsbrev;
import no.nsd.polsys.modell.forvaltning.Enhet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Action-klasse som viser tildelingsbrevsiden i fvdb.
 * @author eyob
 */
public class EnhetTildelingsbrevAction {

   private HttpServletRequest request;
   private final TildelingsbrevLogikk tildelingsbrevLogikk = new TildelingsbrevLogikk();
   private List<Tildelingsbrev> tildelingsbrev;
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
      
      request.setAttribute("tildelingsbrev", tildelingsbrev);
      HttpUtil.forward("/WEB-INF/jsp/forvaltning/enhet_tildelingsbrev.jsp", request, response);
   }
   
   
   private void getData() throws ServletException {
      Connection conn = null;
      
      try {
         conn = DatabaseConnectionFactory.getConnection();
         tildelingsbrevLogikk.setConn(conn);
         tildelingsbrev = tildelingsbrevLogikk.getTildelingsbrevTilRegEnhet(enhet.getIdnum());
         
         if (tildelingsbrev == null || tildelingsbrev.isEmpty()) {
            getTildelingsbrevTilOverordnet(conn);
         }
         
      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         SqlUtil.close(conn);
      }
   }

   
   private void getTildelingsbrevTilOverordnet(Connection conn) throws SQLException {
      EnhetUtilLogikk util = new EnhetUtilLogikk();
      util.setConn(conn);
      if (this.engelsk) {
         util.brukEngelsk();
      }
      util.beregnEnheter(null);

      Enhet overordnet = util.getOverordnetEtat(enhet);
      
      if (overordnet != null) {
         tildelingsbrev = tildelingsbrevLogikk.getTildelingsbrevTilRegEnhet(overordnet.getIdnum());
         request.setAttribute("overordnet", overordnet);
      }
      
   }
   
   
}
