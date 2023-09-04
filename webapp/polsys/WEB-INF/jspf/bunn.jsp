<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--
 - Copyright NSD
 - Description: JSP som inneholder bunninnholdet for polsys-admin-sidene.
--%>

<%--<div id="signature">
<p class="copyright">
 Copyright © NSD - Norsk senter for forskningsdata •
 <a href="/nsd/personvern.html">Om personvern</a>
 •
 <a class="img_link" href="http://www.twitter.com/nsddata" target="_blank">
  <img src="/img/twitter_16.png" alt="">
 </a>
</p>
</div>--%>


<div id="signature">
 <p class="copyright">
  Copyright © Sikt – Kunnskapssektorens tjenesteleverandør
  <a href="https://sikt.no/kontakt-oss" target="_blank">
   • Kontakt SIKT
  </a>
  <a href="https://sikt.no/personvernerklaering" target="_blank">
   • Personvernerklæring
  </a>
  <span> Versjon 1.1</span>

<%--  <a href="<p:url value="/forvaltning/tilgjengelighetserklaring.html" />"> • Tilgjengelighetserklæring</a>--%>
<c:if test="${no}">
  <a href="https://uustatus.no/nb/erklaringer/publisert/e2ebb5d0-077e-4a9d-babd-8698357d4dba" target="_blank">
   • Tilgjengelighetserklæring
  </a>
</c:if>
  <c:if test="${en}">
   <a href="https://uustatus.no/nb/erklaringer/publisert/e2ebb5d0-077e-4a9d-babd-8698357d4dba" target="_blank">
    • Accessibility Statement (in Norwegian)
   </a>
  </c:if>
 </p>
</div>

</body>
</html>
