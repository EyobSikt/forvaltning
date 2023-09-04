<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over eksisterende enheter på gitt tidspunkt.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Enheter - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Units - State Administration Database" />
    <c:param name="beskrivelseNo" value="Liste over enheter." />
    <c:param name="beskrivelseEn" value="List of units." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> Enhetliste
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> List of units
</c:if>
</div>

<h1>Liste over enheter per <fmt:formatDate value="${dato}" pattern="d.M.yyyy" /></h1>

<p>Listen viser alle relevante enheter per <fmt:formatDate value="${dato}" pattern="d.M.yyyy" /></p>

<h3>Oppgi årstall</h3>
<form action="" method="get">
År (åååå): <input type="text" value="${aar}" maxlength="4" size="6" name="y" />
<input type="submit" value="OK" />
</form>

<c:if test="${empty enheter}">
<p><em>Ingen data på gitt år.</em></p>
</c:if>


<c:if test="${!(empty enheter)}">
<table class="text sortable zebra">
<caption>Eksisterende enheter per <fmt:formatDate value="${dato}" pattern="d.M.yyyy" />, antall: ${fn:length(enheter)}</caption>

<thead>
<tr>
<th>dep.id</th>
<th>Departement</th>
<th>enhet.id</th>
<th>Navn</th>
<th>Kort navn</th>
<th>Fylke</th>
<th>Type</th>
<th>Ansatte</th>
<%--<th>Årsverk</th>--%>
</tr>
</thead>

<tfoot>
<tr>
<td></td>
<td></td>
<td></td>
<td>Totalt</td>
<td>Totalt</td>
<td></td>
<td></td>
<td>${totaltAnsatte}</td>

<%--<td><fmt:formatNumber value="${totaltAarsverk}" pattern="0.0" /></td>--%>

</tr>
</tfoot>

<tbody>
<c:forEach items="${enheter}" var="e">
<tr>
<td>${e.overordnetEnhet.idnum}</td>
<td>${fn:escapeXml(e.overordnetEnhet.langtNavn)}</td>
<td>${e.idnum}</td>
<td><a href="<p:url value="/data/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></td>
<td>${fn:escapeXml(e.kortNavn)}</td>
<td>${e.fylkenummer}</td>
<td>${fn:escapeXml(grunnenheter[e.grunnenhet].tekstEntall)}</td>
<td>${ansatte[e.idnum].ansatte}</td>

<%--<td><fmt:formatNumber value="${ansatte[e.idnum].aarsverk}" pattern="0.0" /></td>--%>

</tr>
</c:forEach>
</tbody>

</table>
</c:if>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
