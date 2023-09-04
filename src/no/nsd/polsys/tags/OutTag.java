package no.nsd.polsys.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Skriver ut enten norsk eller engelsk tekst.
 * @author HVB
 */
public class OutTag extends SimpleTagSupport {

    private String no = null;
    private String en = null;

    public void setNo(String no) {
        this.no = no;
    }
    public void setEn(String en) {
        this.en = en;
    }

    /**
     * If the URL (value) of this UrlTag starts with slash (/) then the
     * URL is normalized against the context root.
     * @throws java.io.IOException
     */
    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) this.getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        
        if (request.getAttribute("en") != null) {
            this.getJspContext().getOut().print(this.en != null ? this.en : "");
        } else {
            this.getJspContext().getOut().print(this.no != null ? this.no : "");
        }
    }
}

