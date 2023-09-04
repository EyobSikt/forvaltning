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

<h1>Event table</h1>

<p>The table shows the ID of all units with events per year. Units with more than one event per year are showed in bold.</p>

<table class="text zebra">
<thead>
<tr><th>Year</th><th>IDNR</th></tr>
</thead>
<tbody>
<c:forEach items="${data}" var="m">
<tr>
<td>${m.key}</td>
<td>
<c:forEach items="${m.value}" var="e">
<c:if test="${e.nivaa == 1}"><a href="<c:url value="/forvaltning/endring.p?id=${e.endringsid}" />">${e.idnum}</a></c:if>
<c:if test="${e.nivaa > 1}"><strong><a href="<c:url value="/forvaltning/endringliste.p?idnum=${e.idnum}" />">${e.idnum}</a></strong></c:if>
</c:forEach>
</td>
</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
