<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<% pageContext.setAttribute("newLineChar", "\n"); %>
<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over underliggende avdelinger/satellitter til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Avdelinger/satellitter under ${fn:escapeXml(enhet.langtNavn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Local units under ${fn:escapeXml(enhet.langtNavn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Local units under ${fn:escapeXml(enhet.langtNavn)}." />
    <c:param name="beskrivelseEn" value="Avdelinger/satellitter under ${fn:escapeXml(enhet.langtNavn)}." />
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
> Enhet #${enhet.idnum}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Unit #${enhet.idnum}
</c:if>
</div>

<h1>${fn:escapeXml(enhet.langtNavn)}</h1>

<%-- ======================================== Enhet-linker ==============  --%>
<c:import url="/WEB-INF/jspf/enhet_navigering.jsp">
    <c:param name="valgt" value="avdeling" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}"><p>Denne siden viser oversikt over lokale avdelinger/satellitter.</p></c:if>
<c:if test="${en}"><p>This page shows local units.</p></c:if>

<c:if test="${param.visalle == null && antallNedlagt != 0}">
<p><a href="?visalle">${en ? "Show previous units" : "Vis tidligere avdelinger"}</a></p>
</c:if>

<c:if test="${empty tallgrupper}">
<p><em>${en ? "No units" : "Ingen enheter."}</em></p>
</c:if>

<c:if test="${antallNaa != 0 || (param.visalle != null && antallNedlagt != 0)}">
<table class="text zebra satellitt">
<thead>
<tr>
<th>${en ? "From" : "Fra"}</th>
<th>${en ? "To" : "Til"}</th>
<th>${en ? "Name" : "Navn"}</th>
<th>${en ? "Located" : "Kommune"}</th>
<th>${en ? "Comment" : "Kommentar"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${tallgrupper}" var="t">
<c:if test="${t.tilTidspunkt == null || param.visalle != null}">
<tr>
<td><fmt:formatDate value="${t.fraTidspunkt}" pattern="d.M.yyyy" /></td>
<td><fmt:formatDate value="${t.tilTidspunkt}" pattern="d.M.yyyy" /></td>
<td>
<c:if test="${no}">${fn:escapeXml(t.navn)}</c:if>
<c:if test="${en}"><c:out value="${t.engelskNavn}" default="${t.navn}" escapeXml="true" /></c:if>
</td>
<td>${t.kommune.kode} ${fn:escapeXml(t.kommune.tekstEntall)}</td>
<td>${fn:replace(fn:escapeXml(t.dokumentasjon), newLineChar, "<br />")}</td>
</tr>
</c:if>
</c:forEach>
</tbody>

</table>
</c:if>

</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
