<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over en bestemt kategori.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(kategorinavn)} - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Literature by category - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Publikasjoner som omhandler kategori ${fn:escapeXml(kategorinavn)}." />
    <c:param name="beskrivelseEn" value="Literature by category" />
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

<h1>${en ? "Publications on" : "Publikasjoner om"} "${fn:escapeXml(kategorinavn)}"</h1>

<c:if test="${no}"><p>Publikasjoner som omhandler kategori: ${fn:escapeXml(kategorinavn)}.</p></c:if>
<c:if test="${en}">
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
<p>Publications on category: ${fn:escapeXml(kategorinavn)}.</p>
</c:if>

<table class="zebra sortable">
<caption>${en ? "Number of publications per code" : "Antall publikasjoner per kode"}</caption>
<thead>
<tr>
<th>${en ? "Code" : "Kode"}</th>
<th>${en ? "Name" : "Navn"}</th>
<th>${en ? "Count" : "Antall"}</th>
<th>${en ? "Comment" : "Kommentar"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${koder}" var="kode">
<tr>
<td>${kode.kode}</td>
<td class="tdtext">${fn:escapeXml(kode.kategori)}</td>
<td><a href="<p:url value="/data/litteratur/kategori/${kategori}?k=${kode.kode}" />">${kode.antall}</a></td>
<td class="tdtext">${fn:escapeXml(kode.dokumentasjon)}</td>
</tr>
</c:forEach>
</tbody>

</table>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
