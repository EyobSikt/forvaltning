<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Liste over publikasjoner.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Litteraturliste - Forvaltning</h1>

<p>Tabellen viser alle publikasjoner i databasen.</p>

<h3><a href="<c:url value="/forvaltning/litteratur.p" />">opprett ny litteratur/publikasjon</a>.</h3>

<table class="text zebra sortable">
<caption>t_forvaltning_litteratur</caption>
<thead>
<tr><th></th> <th>pub_id</th> <th>aarstall</th> <th>tittel</th></tr>
</thead>
<tbody>
<c:forEach items="${litteratur}" var="l">
<tr>
<td><a href="<c:url value="/forvaltning/litteratur.p?pub_id=${l.pubId}" />">endre</a></td>
<td>${l.pubId}</td>
<td>${l.aar}</td>
<td>${fn:escapeXml(l.tittel)}</td>
</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
