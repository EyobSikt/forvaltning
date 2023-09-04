package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.forvaltning.EndringLogikk;

/**
 *
 * @author hvb
 */
public class AnsatteEnhetKoblingAction {

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

      List<String[]> koblinger = null;

      try {
         conn = DatabaseConnectionFactory.getConnection();
         koblinger = this.getKobling(conn);
      } catch (Exception e) {
         throw new ServletException(e);
      } finally {
         try {
            conn.close();
         } catch (Exception ignored) {
            conn = null;
         }
      }

      request.setAttribute("koblinger", koblinger);

      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/ansatte_enhet_kobling.jsp");
      rd.forward(request, response);
   }

   private List<String[]> getKobling(Connection conn) throws SQLException {
      // returneres fra denne metoden.
      List<String[]> koblinger = new ArrayList<String[]>();
      Result result = null;
      SqlCommandBean sqlCB = new SqlCommandBean();
      SortedMap[] rader = null;
      String sqlSelect = "select distinct ea.ansatte_etatskode, a.u_etatnavn, ea.idnum, e.langt_navn "
              + "from t_forvaltning_enhet_ansatte_kobling as ea "
              + "inner join t_forvaltning_endring as e on ea.idnum = e.idnum "
              + "inner join t_forvaltning_ansatte_etat as a on ea.ansatte_etatskode = a.u_etatskode "
              + "where e.langt_navn is not null "
              + "group by ea.idnum, ea.ansatte_etatskode, e.langt_navn, a.u_etatnavn "
              + "order by ea.ansatte_etatskode, ea.idnum";

      sqlCB.setConnection(conn);
      sqlCB.setSqlValue(sqlSelect);
      result = sqlCB.executeQuery();
      rader = result.getRows();

      if (rader == null || rader.length == 0) {
         return null;
      }

      for (SortedMap rad : rader) {
         String[] s = new String[4];
         s[0] = (String) rad.get("ansatte_etatskode");
         s[1] = (String) rad.get("u_etatnavn");
         s[2] = "" + rad.get("idnum");
         s[3] = (String) rad.get("langt_navn");
         koblinger.add(s);
      }

      return koblinger;
   }
}
