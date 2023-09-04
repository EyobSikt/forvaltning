<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over underliggende desentraliserte enheter til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Desentraliserte enheter under ${fn:escapeXml(enhet.langtNavn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Decentralized units under ${fn:escapeXml(enhet.langtNavn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Desentraliserte statlige enheter og desentraliserte etatsenheter under ${fn:escapeXml(enhet.langtNavn)}." />
    <c:param name="beskrivelseEn" value="Decentralized state units and decentralized subordinate units under ${fn:escapeXml(enhet.langtNavn)}." />
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
    <c:param name="valgt" value="desentralisert" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}"><p>Denne siden viser oversikt over desentraliserte statlige enheter og desentraliserte etatsenheter.</p></c:if>
<c:if test="${en}"><p>This page shows decentralized state units and decentralized subordinate units.</p></c:if>

<c:if test="${empty tallgrupper}">
<p>
<em>${en ? "No units" : "Ingen enheter."}</em>
</p>
</c:if>


<c:if test="${!empty tallgrupper}">

<c:set var="lpnr" value="-1" />
<c:set var="lukkTabell" value="false" />

<c:forEach items="${tallgrupper}" var="t">

<c:if test="${t.lpnr != lpnr}">

<c:if test="${lukkTabell}"></tbody></table></c:if>

<h3><a name="${t.lpnr}" class="img_link">${en ? "Decentralized units" : "Desentraliserte enheter og antall"}</a></h3>

<table class="zebra">
<thead>
<tr><th>${en ? "Year" : "År"}</th><th>${en ? "Count" : "Antall"}</th><th>${en ? "Name" : "Navn"}</th><th>${en ? "Decentralized" : "Desentraliserte"}</th></tr>
</thead>
<tbody>
<c:set var="lukkTabell" value="true" />
<c:set var="lpnr" value="${t.lpnr}" />
</c:if>

<tr>
<fmt:formatDate var="fra" value="${t.fraTidspunkt}" pattern="d.M.yyyy" /><fmt:formatDate var="til" value="${t.tilTidspunkt}" pattern="d.M.yyyy" />
<td title="${fra} - ${til}">${t.aar}</td>
<td>
<c:if test="${t.enheterKartlagt != 2}"><a href="<p:url value="/forvaltning/enhet/${enhet.idnum}/desentralisert/${t.id}" />">${t.antallEnheter}</a></c:if>
<c:if test="${t.enheterKartlagt == 2}">${t.antallEnheter} (est.)</c:if>
</td>
<td>${fn:escapeXml(t.navn)}</td>
<td>
<c:if test="${no}">${t.tallgruppekode == 21 ? "statlige enheter" : "etatsenheter"}</c:if>
<c:if test="${en}">${t.tallgruppekode == 21 ? "state units" : "subordinate units"}</c:if>
</td>
</tr>

</c:forEach>

<c:if test="${lukkTabell}">
</tbody>
</table>
</c:if>

<%--
<c:if test="${fn:length(chartUrl) <= 2000}">
<h3>${en ? "Chart" : "Graf"}</h3>
<img class="chart_img" src="${chartUrl}" alt="graf" />
</c:if>
--%>

</c:if>


<div class="footnote">
<c:if test="${no}">
<h3>Antall</h3>
<p>
Kartleggingen er gjennomført i et utvalg år i tidsperioden.
I de tilfeller hvor det har vært endring mellom to kartlagte år –
og det følgelig ikke vites i hvilket år endringen skjedde – er antallet estimert
(gjennomsnittet av ytterverdiene, avrundet oppover). Estimerte antall er merket med (est.).
</p>
<h3>Merk</h3>
<p>'Desentraliserte stalige enheter' vil si enheter som rapporterer direkte til et departement, uten at det eksisterer et mellomliggende direktorat eller tilsvarende.</p>
<p>'Desentraliserte etatsenheter' vil si enheter som også har et mellomnivå mellom seg og departementet, det være seg et direktorat eller tilsvarende.</p>
</c:if>

<c:if test="${en}">
<h3>Count</h3>
<p>
In cases where the local units are estimated, this is indicated '(est.)'.
</p>
<h3>Note</h3>
<p>'Decentralized state units' report directly to the ministry. In other words there are no intermediate directorate etc.</p>
<p>'Decentralized subordinate units' have an intermediate level between themselves and the corresponding ministry. I.e. the units have to report through a directorate etc.</p>
</c:if>
</div>


</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
