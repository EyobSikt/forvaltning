<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Søk etter enheter.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Søk etter enhet - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Search units - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Søk etter forvaltningslitteratur til enheter." />
    <c:param name="beskrivelseEn" value="Search literature by unit name." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp">
    </c:import>
</c:if>
<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningslitteratur.html">Litteratur</a>
> Søk på enhetsnavn
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html">Literature</a>
> Search by unit name
</c:if>
</div>

<h1>${en ? "Search literature by unit name" : "Søk på enhetsnavn"}</h1>

<c:if test="${no}"><p>Søk etter litteratur og årsmeldinger tilknyttet enheter.</p></c:if>
<c:if test="${en}"><p>Search for publications and annual reports by unit navn.</p></c:if>

<form action="" method="get">
<input type="text" value="${param.s}" maxlength="100" size="75" name="s" />
<input class="button" type="submit" value="${en ? "Search" : "Søk"}" />
</form>

<c:if test="${param.s != null}">
<h2>${en ? "Results" : "Søkeresultat"}</h2>
</c:if>

<c:if test="${param.s != null && empty enheter}">
<p><em>${en ? "No hits." : "Ingen treff."}</em></p>
</c:if>

<c:if test="${!(empty enheter)}">

<table class="check zebra">
<caption>${fn:length(enheter)} ${fn:length(enheter) > 1 ? "enheter" : "enhet"}</caption>
<thead>
<tr>
<th>${en ? "Unit" : "Enhet"}</th>
<th>${en ? "Publications" : "Publikasjoner"}</th>
<th>${en ? "Annual reports" : "Årsmeldinger"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${enheter}" var="enhet">

<c:set var="i" value="${fn:indexOf(fn:toLowerCase(enhet.langtNavn), fn:toLowerCase(param.s))}" />
<tr>
<th>
<a href="<p:url value="/data/enhet/${enhet.idnum}" />">
${fn:escapeXml(fn:substring(enhet.langtNavn, 0, i))}<c:if test="${i > -1}"><strong>${fn:escapeXml(fn:substring(enhet.langtNavn, i, i + fn:length(param.s)))}</strong>${fn:escapeXml(fn:substring(enhet.langtNavn, i + fn:length(param.s), -1))}</c:if>
</a>

<c:if test="${!(empty enhet.tidligereNavn)}">
<ul class="plain">
<c:forEach items="${enhet.tidligereNavn}" var="navn">
<c:set var="i" value="${fn:indexOf(fn:toLowerCase(navn), fn:toLowerCase(param.s))}" />
<li>
${fn:escapeXml(fn:substring(navn, 0, i))}<c:if test="${i > -1}"><strong>${fn:escapeXml(fn:substring(navn, i, i + fn:length(param.s)))}</strong>${fn:escapeXml(fn:substring(navn, i + fn:length(param.s), -1))}</c:if>
</li>
</c:forEach>
</ul>
</c:if>
</th>

<td>
<%-- Littertur --%>
<c:if test="${litteratur[enhet.idnum] != null}">
<a href="<p:url value="/data/enhet/${enhet.idnum}/litteratur" />">${litteratur[enhet.idnum]} publ.</a>
</c:if>
</td>

<td>
<%-- Årsmeldinger --%>
<c:set var="aa" value="${aarsmeldinger[enhet.idnum]}" />
<c:if test="${!(empty aa)}">
<a href="<p:url value="/data/enhet/${enhet.idnum}/aarsmelding" />">${fn:length(aa)} ${en ? "years" : "årganger"}</a>
</c:if>
</td>

</tr>
</c:forEach>
</tbody>
</table>

</c:if>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
