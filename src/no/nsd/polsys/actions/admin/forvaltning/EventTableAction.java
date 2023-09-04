package no.nsd.polsys.actions.admin.forvaltning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import no.nsd.common.beans.sql.SqlCommandBean;
import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.modell.forvaltning.Enhet;

/**
 *
 * @author hvb
 */
public class EventTableAction {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;

        Map data = null;

        try {
            conn = DatabaseConnectionFactory.getConnection();
            
            data = this.getData(conn);
            
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }

        request.setAttribute("data", data);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/event_table.jsp");
        rd.forward(request, response);
    }

    
    private SortedMap<Integer, List<Enhet>> getData(Connection conn) throws SQLException {
       SortedMap<Integer, List<Enhet>> map = new TreeMap<Integer, List<Enhet>>();
       SqlCommandBean sqlCB = new SqlCommandBean();
       String sqlSelect = "select date_part('year', tidspunkt) as y, idnum, count(idnum) as ant, min(id) as id "
               + "from t_forvaltning_endring "
               + "group by date_part('year', tidspunkt), idnum "
               + "order by date_part('year', tidspunkt)";

       sqlCB.setConnection(conn);
       sqlCB.setSqlValue(sqlSelect);
       Result result = sqlCB.executeQuery();
       SortedMap[] rader = result.getRows();

       if (rader == null || rader.length == 0) {
          return null;
       }

       for (SortedMap rad : rader) {
          Enhet e = new Enhet();

          e.setIdnum((Integer) rad.get("idnum"));
          e.setEndringsid(((Number) rad.get("id")).intValue());
          e.setNivaa(((Number) rad.get("ant")).intValue());

          Integer year = ((Number) rad.get("y")).intValue();

          List<Enhet> list = map.get(year);
          if (list == null) {
             list = new ArrayList<Enhet>();
             map.put(year, list);
          }
          list.add(e);
       }

       return map;


       
    }
    

}
