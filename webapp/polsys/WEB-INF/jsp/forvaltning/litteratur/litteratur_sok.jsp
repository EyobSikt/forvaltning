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
    <c:param name="tittelNo" value="Litteratursøk - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Search literature - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Søk etter forvaltningslitteratur." />
    <c:param name="beskrivelseEn" value="Search literature" />
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
> Søk
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html">Literature</a>
> Search
</c:if>
</div>

<h1>${en ? "Search literature" : "Litteratursøk"}</h1>

<c:if test="${no}">
<p>Søk i publikasjonstittel, utgiver, forfatter, eller eventuelt kommentar eller samandrag. Merk at du kan kun søke i en kategori om gangen.</p>
</c:if>
<c:if test="${en}">
<p>Search publications by title or publisher or authors or summary.</p>
<p class="nottranslated">Please note: The literature pages contains data in Norwegian that is not translated to English.</p>
</c:if>

<form action="" method="get">
<input type="text" value="${param.s}" maxlength="100" size="75" name="s" />
<input class="button" type="submit" value="${en ? "Search" : "Søk"}" />
</form>

<c:if test="${param.s != null}">
<h2>${en ? "Results" : "Søkeresultat"}</h2>
</c:if>

<c:if test="${param.s != null && empty litteratur}">
<p><em>${en ? "No hits." : "Ingen treff."}</em></p>
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
