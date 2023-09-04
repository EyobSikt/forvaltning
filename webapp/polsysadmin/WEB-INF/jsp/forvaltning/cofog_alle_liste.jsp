<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Liste over enheter med cofog.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Enheter med cofog - Forvaltning</h1>

<p>Tabellen alle enheter med cofog.</p>


<table class="text sortable zebra">
<caption>enheter med cofog</caption>
<thead>
<tr><th>cofog</th><th>enhet</th><th>idnum</th></tr>
</thead>
<tbody>
<c:forEach items="${enheter}" var="e">
<c:if test="${e.cofog != null}">
<tr>
<td>${fn:escapeXml(e.cofog)}</td>
<td>${fn:escapeXml(e.langtNavn)}</td>
<td>${e.idnum}</td>
</tr>
</c:if>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
