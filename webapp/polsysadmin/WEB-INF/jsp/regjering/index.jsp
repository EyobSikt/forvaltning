<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Index-side for polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

    <div class="breadcrumbs">
        Du er her:  <a href="/polsysadmin">Polsys</a>
        >  Regjering
    </div>

<h1>Regjering-administrasjonssider.</h1>
<%--
<ul>
 <li><a href="<c:url value="/parti/dokliste.p?tabellnavn=t_forvaltning_endringskode" />">Parti - fylke_lister</a></li>
<li><a href="<c:url value="/parti/dokliste.p?tabellnavn=t_forvaltning_grunnenhet" />">Parti - partilister</a></li>
</ul>
--%>

<ul>
<li><strong><a href="<c:url value="/regjering/statsrad.p" />">Legge inn/endre statsråd</a></strong></li>
<li><strong><a href="<c:url value="/regjering/statssekretar.p" />">Legge inn/endre statssekretær</a></strong></li>
<li><strong><a href="<c:url value="/regjering/embete.p" />">Legge inn ny embete/minister</a></strong></li>
</ul>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
