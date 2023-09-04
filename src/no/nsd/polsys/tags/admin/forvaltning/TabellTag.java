package no.nsd.polsys.tags.admin.forvaltning;

import java.io.IOException;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author hvb
 */
public class TabellTag extends SimpleTagSupport {

    // -------------------------------------------------------------- Variabler

    List value;


    // --------------------------------------------------------- setter metoder

    public void setValue(List value) {
        this.value = value;
    }


    /**
     * Skriver valgresultatet.
     *
     * @throws java.io.IOException
     */
    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) this.getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        JspWriter out = this.getJspContext().getOut();
        String context = request.getContextPath();
        String tabellnavn = (String) request.getAttribute("tabellnavn");

        out.println("<table class=\"data text zebra\">");
        out.println("<caption>" + tabellnavn + "<caption>");

        out.println("<thead>");
        out.print("<tr>");
        out.print("<th></th>");
        List kol = (List) this.value.get(0);
        for (Object o : kol) {
            out.print("<th>");
            out.print(this.getString(o));
            out.print("</th>");
        }
        out.println("</tr>");
        out.println("</thead>");

        out.println("<tbody>");
        for (int i = 1; i < this.value.size(); i++) {
            List rad = (List) this.value.get(i);
            out.print("<tr>");
            
            out.print("<th>");
            if (tabellnavn.equals("t_forvaltning_endring")) {
                String id = this.getString(rad.get(0));
                String url = context;
                url += "/forvaltning/endring.p";
                url += "?id=" + id;
                url = response.encodeURL(url);
                out.print("<a href=\"" + url + "\">endre</a>");
            }
            out.print("</th>");

            for (Object o : rad) {
                out.print("<td>");
                out.print(this.getString(o));
                out.print("</td>");
            }
            out.println("</tr>");
        }
        out.println("</tbody>");

        out.println("</table>");
    }


    private String getString(Object o) {
        String s = (o != null ? o.toString() : null);
        if (s == null) return null;
        if (s.length() > 60) {
            s = s.substring(0, 60);
        }
        s = s.replaceAll("&", "&#38;");
        s = s.replaceAll("\"", "&#34;");
        s = s.replaceAll("'", "&#39;");
        s = s.replaceAll("<", "&#60;");
        s = s.replaceAll(">", "&#62;");
        return s;
    }

}

