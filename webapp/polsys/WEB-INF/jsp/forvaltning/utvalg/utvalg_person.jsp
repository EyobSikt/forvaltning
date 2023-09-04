<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over en gitt person i utvalgsarkivet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(person.navn)} - Utvalg - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Committee - Advisory Committees - State Administration Database" />
    <c:param name="beskrivelseNo" value="Info om utvalg: ${fn:escapeXml(person.navn)}." />
    <c:param name="beskrivelseEn" value="Advisory committee." />
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
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/utvalgsarkivet.html">Utvalg</a>
> Person #${person.pnr}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/advisorycommittees.html">Advisory Committees</a>
> Person #${person.pnr}
</c:if>
</div>

<h1>${fn:escapeXml(person.navn)}</h1>

<c:if test="${en}">
<div class="section">
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
</div>
</c:if>

<c:if test="${no}">
<p class="disclaimer">
Denne siden viser opplysninger om ${fn:escapeXml(person.navn)} fra Utvalgsarkivet i Forvaltningsdatabasen.
Utvalgsarkivet er en datasamling med informasjon om statlige utvalg, styrer og råd for <strong>perioden 1980-1997, 2002-2003 og f.o.m 2004</strong>.
Til og med 2003 inneholder arkivet informasjon fra Stortingsmelding nr 7. Fra og med 2004 inneholder arkivet informasjon fra Departementenes sikkerhets- og serviceorganisasjon (DSS).
NSD er ikke ansvarlig for eventuelle feil i kilder.
<strong>Les mer <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/dokumentasjon/utvalgsarkivet.html">her</a></strong>.
</p>
</c:if>


<%-- ================================== Personopplysninger ==============  --%>
<h3>Personopplysninger</h3>

<c:if test="${person.navn != null}">
<div class="event"><span class="eventl">${en ? "Name:" : "Navn:"}</span> <span class="eventv">${fn:escapeXml(person.navn)}</span></div>
</c:if>

<c:if test="${person.pnr != null}">
<div class="event"><span class="eventl">${en ? "NSD Number:" : "NSD id-nummer:"}</span> <span class="eventv">${person.pnr}</span></div>
</c:if>

<c:if test="${person.foedselsaar != null}">
<div class="event"><span class="eventl">${en ? "Birth year" : "Fødselsår:"}</span> <span class="eventv">${person.foedselsaar}</span></div>
</c:if>
<c:if test="${!(empty person.utvalg2003)}">
 <div class="event"><span class="eventl">${en ? "committees periode" : "Utvalg periode:"}</span> <span class="eventv">t.o.m 2003</span></div>
 </c:if>
<c:if test="${!(empty person.utvalg2004)}">
<div class="event"><span class="eventl">${en ? "committees periode" : "Utvalg periode:"}</span> <span class="eventv">f.o.m 2004</span></div>
</c:if>
<%-- ====================================================================  --%>


<%-- ============================================== Medlem ==============  --%>
<c:if test="${!(empty medlem)}">
<table class="zebra sortable">
<caption>${en ? "Member Info" : "Medlemsopplysninger"}</caption>

<thead>
<tr>
<th>${en ? "Year" : "År"}</th>
<th>${en ? "Committee" : "Utvalg"}</th>
<th>${en ? "Function" : "Funksjon"}</th>
<th>${en ? "Verv" : "Verv"}</th>
<th>${en ? "Oppnevningsmåte" : "Oppnevningsmåte"}</th>
<th>${en ? "Municipality" : "Kommune"}</th>
<th>${en ? "Job" : "Stilling"}</th>
<th>${en ? "From date" : "Fra dato"}</th>
<th>${en ? "To date" : "Til dato"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${medlem}" var="m">
<tr>
<td>${m.aar}</td>
<td class="tdtext"><a href="<p:url value="/data/utvalg/${m.utvalg.unr}" />">${fn:escapeXml(m.utvalg.navn)}</a></td>
<td class="tdtext">${fn:escapeXml(m.utvalg.hovedfunksjon)}</td>
<td class="tdtext">${fn:escapeXml(m.verv)}</td>
<td class="tdtext">${fn:escapeXml(m.oppnevningsmaate)}</td>
<td>${m.kommune.kode}</td>
<td class="tdtext">${fn:escapeXml(m.stilling)}</td>
<td>${m.fradato}</td>
<td>${m.tildato}</td>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>
<%-- ====================================================================  --%>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
