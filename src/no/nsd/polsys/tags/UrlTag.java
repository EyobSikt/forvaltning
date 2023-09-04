package no.nsd.polsys.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Setter på context og evt. /en/ i url.
 *
 * @author HVB
 */
public class UrlTag extends SimpleTagSupport {

   private String value = null;

   public void setValue(String value) {
      this.value = value;
   }

   /**
    * If the URL (value) of this UrlTag starts with slash (/) then the URL is
    * normalized against the context root.
    *
    * @throws java.io.IOException
    */
   @Override
   public void doTag() throws IOException {
      if (this.value == null) {
         return;
      }
      PageContext pageContext = (PageContext) this.getJspContext();
      HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
      String engelsk = "";
      if (request.getAttribute("en") != null) {
         engelsk = "/en";
      }

      if (this.value.startsWith("/")) {
         // normalize relative URLs against the context root
         this.getJspContext().getOut().print(request.getContextPath() + engelsk + this.value);
      } else {
         this.getJspContext().getOut().print(this.value);
      }

   }
}
