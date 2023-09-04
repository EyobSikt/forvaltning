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


<h1>Parti-administrasjonssider.</h1>
<%--
<ul>
 <li><a href="<c:url value="/parti/dokliste.p?tabellnavn=t_forvaltning_endringskode" />">Parti - fylke_lister</a></li>
<li><a href="<c:url value="/parti/dokliste.p?tabellnavn=t_forvaltning_grunnenhet" />">Parti - partilister</a></li>
</ul>
--%>

<ul>
<li><strong><a href="<c:url value="/parti/partilist.p" />">Legge inn/endre parti</a></strong></li>
<li><strong><a href="<c:url value="/parti/partidok.p" />">Legge inn/endre partidokument</a></strong></li>
</ul>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
