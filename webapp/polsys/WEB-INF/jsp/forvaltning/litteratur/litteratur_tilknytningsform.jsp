<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over en bestemt litteratur/publikasjon.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(tilknytninger[tilknytningsform].tekstEntall)} - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(tilknytninger[tilknytningsform].tekstEntall)} - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Publikasjoner som omhandler tilknytningsformen ${fn:escapeXml(tilknytninger[tilknytningsform].tekstEntall)}." />
    <c:param name="beskrivelseEn" value="Publications on affiliation ${fn:escapeXml(tilknytninger[tilknytningsform].tekstEntall)}" />
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
> Tilknytningsform
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html">Literature</a>
> By affiliation
</c:if>
</div>

<h1>${en ? "Publications on" : "Publikasjoner om"} ${fn:escapeXml(tilknytninger[tilknytningsform].tekstEntall)}</h1>

<c:if test="${no}"><p>Publikasjoner som omhandler tilknytningsformen: ${fn:escapeXml(tilknytninger[tilknytningsform].tekstEntall)}.</p></c:if>
<c:if test="${en}">
<p>Publications on affiliation: ${fn:escapeXml(tilknytninger[tilknytningsform].tekstEntall)}.</p>
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
</c:if>

<c:if test="${!(empty litteratur)}">
<table class="text zebra sortable">
<caption>${fn:length(litteratur)} ${en ? "publications" : "publikasjoner"}</caption>

<thead>
<tr>
<th>Type</th>
<th>${en ? "Year" : "År"}</th>
<th>${en ? "Title" : "Publikasjonstittel"}</th>
<th>${en ? "Author" : "Forfattar"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${litteratur}" var="l">
<tr>
<td>${fn:escapeXml(l.type.tekstEntall)}</td>
<td>${fn:escapeXml(l.aar)}</td>
<td><a href="<p:url value="/data/litteratur/${l.pubId}" />">${fn:escapeXml(l.tittel)}</a></td>
<td>${fn:escapeXml(l.forfatter)}</td>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
