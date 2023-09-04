<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over litteraturer til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Publikasjoner som omhandler ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Literature on ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="litteratur" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}">
<p>Denne siden viser publikasjoner som omhandler denne enheten.</p>
</c:if>
<c:if test="${en}">
<p>This page shows publications about this unit.</p>
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
</c:if>

<c:if test="${empty litteratur}">
<p><em>${en ? "No publications for this unit." : "Ingen publikasjoner for denne enheten."}</em></p>
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



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
