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
    <c:param name="tittelNo" value="${fn:escapeXml(kodenavn)} - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Literature by subcategory - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Publikasjoner som omhandler ${fn:escapeXml(kodenavn)}." />
    <c:param name="beskrivelseEn" value="Literature by subcategory" />
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
> Kategori
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html">Literature</a>
> Category
</c:if>
</div>

<h1>${en ? "Publications on" : "Publikasjoner om"} "${fn:escapeXml(kategorinavn)}": "${fn:escapeXml(kodenavn)}"</h1>

<c:if test="${no}"><p>Publikasjoner kategorisert på: "${fn:escapeXml(kategorinavn)}" og "${fn:escapeXml(kodenavn)}".</p></c:if>
<c:if test="${en}">
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
<p>Publications categorized by: "${fn:escapeXml(kategorinavn)}" and "${fn:escapeXml(kodenavn)}".</p>
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
