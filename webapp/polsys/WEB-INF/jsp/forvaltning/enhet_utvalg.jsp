<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over utvalg til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - utvalg - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - committees - State Administration Database" />
    <c:param name="beskrivelseNo" value="Utvalg relatert til ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Committees related to ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="utvalg" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}"><p>Siden viser utvalg <strong>i perioden 1980-1997 og 2002-2013</strong> som er relatert til denne enheten.</p></c:if>
<c:if test="${en}"><p>This page shows committees <strong>(only years 1980-1997 and 2002-2013)</strong> related to this unit.</p></c:if>

<c:if test="${empty utvalg}">
<p><em>${en ? "No committees where found for this unit." : "Ingen utvalg er registrert for denne enheten."}</em></p>
</c:if>

<c:if test="${!(empty utvalg)}">
<table class="text zebra sortable">
<caption>${fn:length(utvalg)} ${en ? "committees" : "utvalg"}</caption>

<thead>
<tr>
<th>${en ? "From" : "Fra"}</th>
<th>${en ? "To" : "Til"}</th>
<th>${en ? "Function" : "Funksjon"}</th>
<th>${en ? "Name" : "Navn"}</th>
<th>${en ? "until 2003" : "t.o.m 2003"}</th>
<th>${en ? "from 2004" : "f.o.m 2004"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${utvalg}" var="u">
<tr>
<td>${u.opprettelsesaar}</td>
<td>${u.opphoersaar}</td>
<td>${fn:escapeXml(u.hovedfunksjon)}</td>
<td><a href="<p:url value="/data/utvalg/${u.unr}" />">${fn:escapeXml(u.navn)}</a></td>
<td><c:if test="${!(empty u.utvalg2003)}">✔</c:if></td>
<td><c:if test="${!(empty u.utvalg2004)}">&#10004;</c:if></td>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>

</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
