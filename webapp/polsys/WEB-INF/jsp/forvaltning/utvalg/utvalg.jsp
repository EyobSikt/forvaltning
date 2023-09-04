<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over et bestemt utvalg.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(utvalg.navn)} - Utvalg - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Committee - Advisory Committees - State Administration Database" />
    <c:param name="beskrivelseNo" value="Info om utvalg: ${fn:escapeXml(utvalg.navn)}." />
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
> Utvalg #${utvalg.unr}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/advisorycommittees.html">Advisory Committees</a>
> Committee #${utvalg.unr}
</c:if>
</div>

<h1>${fn:escapeXml(utvalg.navn)}</h1>

<c:if test="${en}">
<div class="section">
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
</div>
</c:if>

<c:if test="${no}">
<p class="disclaimer">
Denne siden viser opplysninger om utvalget "${fn:escapeXml(utvalg.navn)}" fra Utvalgsarkivet i Forvaltningsdatabasen.
Utvalgsarkivet er en datasamling med informasjon om statlige utvalg, styrer og råd for <strong>perioden 1980-1997, 2002-2003 og f.o.m 2004</strong>.
Til og med 2003 inneholder arkivet informasjon fra Stortingsmelding nr 7. Fra og med 2004 inneholder arkivet informasjon fra Departementenes sikkerhets- og serviceorganisasjon (DSS).
NSD er ikke ansvarlig for eventuelle feil i kilder. 
<strong>Les mer <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/dokumentasjon/utvalgsarkivet.html">her</a></strong>.
</p>
</c:if>


<%-- ================================== Faste opplysninger ==============  --%>
<h3>Faste utvalgsopplysninger</h3>

<c:if test="${utvalg.navn != null}">
<div class="event"><span class="eventl">${en ? "Name:" : "Navn:"}</span> <span class="eventv">${fn:escapeXml(utvalg.navn)}</span></div>
</c:if>

<c:if test="${utvalg.unr != null}">
<div class="event"><span class="eventl">${en ? "Committe number:" : "Utvalgsnummer:"}</span> <span class="eventv">${utvalg.unr}</span></div>
</c:if>

<c:if test="${utvalg.opprettelsesmaate != null}">
<div class="event"><span class="eventl">${en ? "Opprettelsesmåte:" : "Opprettelsesmåte:"}</span> <span class="eventv">${fn:escapeXml(utvalg.opprettelsesmaate)}</span></div>
</c:if>

<c:if test="${utvalg.opprettelsesaar != null}">
<div class="event"><span class="eventl">${en ? "Opprettelsesår:" : "Opprettelsesår:"}</span> <span class="eventv">${utvalg.opprettelsesaar}</span></div>
</c:if>

<c:if test="${utvalg.odep.idnum != null}">
<div class="event"><span class="eventl">${en ? "Dep:" : "Tilhørende departement:"}</span> <span class="eventv"><a href="<p:url value="/data/enhet/${utvalg.odep.idnum}" />">${fn:escapeXml(utvalg.odep.langtNavn)}</a></span></div>
</c:if>

<c:if test="${utvalg.opphoersaar != null}">
<div class="event"><span class="eventl">${en ? "Opphørsår:" : "Opphørsår:"}</span> <span class="eventv">${utvalg.opphoersaar}</span></div>
</c:if>

<c:if test="${utvalg.hjemmel != null}">
<div class="event"><div class="eventl">${en ? "en" : "Hjemmel"}:</div> <div class="eventv">${fn:escapeXml(utvalg.hjemmel)}</div></div>
</c:if>

<c:if test="${utvalg.mandat != null}">
<div class="event"><div class="eventl">${en ? "en" : "Mandat"}:</div> <div class="eventv">
 <c:if test="${!(empty utvalg.utvalg2003)}">${fn:escapeXml(utvalg.mandat)}</c:if>
 <c:if test="${!(empty utvalg.utvalg2004)}"><a href=${fn:escapeXml(utvalg.mandat)} target="_blank">${fn:escapeXml(utvalg.mandat)}</a></c:if>
</div></div>
 </c:if>

<c:if test="${utvalg.tidsfrist != null}">
<div class="event"><span class="eventl">${en ? "Tidsfrist:" : "Tidsfrist:"}</span> <span class="eventv">${utvalg.tidsfrist}</span></div>
</c:if>

<c:if test="${utvalg.utvalgstype != null}">
<div class="event"><span class="eventl">${en ? "Type:" : "Utvalgstype:"}</span> <span class="eventv">${fn:escapeXml(utvalg.utvalgstype)}</span></div>
</c:if>

<c:if test="${utvalg.hovedfunksjon != null}">
<div class="event"><span class="eventl">${en ? "Function:" : "Hovedfunksjon:"}</span> <span class="eventv">${fn:escapeXml(utvalg.hovedfunksjon)}</span></div>
</c:if>

<c:if test="${utvalg.geografi != null}">
<div class="event"><span class="eventl">${en ? "Geografisk virkeområde:" : "Geografisk virkeområde:"}</span> <span class="eventv">${fn:escapeXml(utvalg.geografi)}</span></div>
</c:if>

<c:if test="${!(empty utvalg.utvalg2003)}">
<div class="event"><span class="eventl">${en ? "committees periode" : "Utvalg periode:"}</span> <span class="eventv">t.o.m 2003</span></div>
</c:if>

<c:if test="${!(empty utvalg.utvalg2004)}">
<div class="event"><span class="eventl">${en ? "committees periode" : "Utvalg periode:"}</span> <span class="eventv">f.o.m 2004</span></div>
</c:if>
<%-- ====================================================================  --%>



<%-- ================================================= Tid ==============  --%>
<c:if test="${!(empty tid)}">
<table class="zebra sortable">
<caption>${en ? "Info" : "Tidsavhengige utvalgsopplysninger"}</caption>

<thead>
<tr>
<th>${en ? "Year" : "År"}</th>
<th>${en ? "Ministry" : "Departement"}</th>
<th>${en ? "Number of members" : "Ant. medl."}</th>
<th>${en ? "Number og substitute members" : "Ant. vara."}</th>
<th>${en ? "Number og meetings" : "Ant. møter"}</th>
<th>${en ? "Expense" : "Utgifter"}</th>
<th>${en ? "Utgiftsdekning" : "Utgiftsdekning"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${tid}" var="t">
<tr>
<td>${t.aar}</td>
<td class="tdtext"><a href="<p:url value="/data/enhet/${t.odep.idnum}" />">${fn:escapeXml(t.odep.langtNavn)}</a></td>
<td>${t.antallMedlemmer}</td>
<td>${t.antallVaramedlemmer}</td>
<td>${t.antallMoeter}</td>
<td>${t.utgifter}</td>
<td class="tdtext">${fn:escapeXml(t.utgiftsdekning)}</td>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>
<%-- ====================================================================  --%>


<%-- ============================================== Medlem ==============  --%>
<c:if test="${!(empty medlem)}">
<table class="zebra sortable">
<caption>${en ? "Member Info" : "Medlemsopplysninger"}</caption>

<thead>
<tr>
<th>${en ? "Year" : "År"}</th>
<th>${en ? "Verv" : "Verv"}</th>
<th>${en ? "Name" : "Navn"}</th>
<th>${en ? "Sex" : "Kjønn"}</th>
<th>${en ? "Municipality" : "Kommune"}</th>
<th>${en ? "Job" : "Stilling"}</th>
<th>${en ? "Oppnevningsmåte" : "Oppnevningsmåte"}</th>
<th>${en ? "From date" : "Fra dato"}</th>
<th>${en ? "To date" : "Til dato"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${medlem}" var="m">
<tr>
<td>${m.aar}</td>
<td class="tdtext">${fn:escapeXml(m.verv)}</td>
<td class="tdtext"><a href="<p:url value="/data/utvalg/person/${m.person.pnr}" />">${fn:escapeXml(m.person.navn)}</a></td>
<td>${m.person.kjoenn}</td>
<td>${m.kommune.kode}</td>
<td class="tdtext">${fn:escapeXml(m.stilling)}</td>
<td class="tdtext">${fn:escapeXml(m.oppnevningsmaate)}</td>
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
