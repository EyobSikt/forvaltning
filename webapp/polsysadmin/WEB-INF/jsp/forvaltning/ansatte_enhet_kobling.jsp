<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Liste over ansatte-enhet-koblinger.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Ansatt-enhet-kobling - Forvaltning</h1>

<p>Tabellen viser koblinger mellom sst-etater og fvdb-enheter.</p>

<c:set var="i" value="0" />
<c:set var="s" value="" />

<table class="text sortable">
<caption>koblinger</caption>
<thead>
<tr><th>kode</th><th>sst-etat</th><th>idnum</th><th>fvdb-enhet</th></tr>
</thead>
<tbody>
<c:forEach items="${koblinger}" var="k">
<c:if test="${!(s eq k[0])}">
    <c:set var="s" value="${k[0]}" />
    <c:set var="i" value="${i+1}" />
</c:if>
<tr ${i%2==0 ? 'class="odd"' : ''}>
<td>${fn:escapeXml(k[0])}</td>
<td>${fn:escapeXml(k[1])}</td>
<td>${fn:escapeXml(k[2])}</td>
<td>${fn:escapeXml(k[3])}</td>
</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
