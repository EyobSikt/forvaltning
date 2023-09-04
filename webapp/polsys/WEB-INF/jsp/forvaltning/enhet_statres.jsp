<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over oppgaver til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - StatRes - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - StatRes - State Administration Database" />
    <c:param name="beskrivelseNo" value="Data fra StatRes for ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Data from StatRes for ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="statres" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}">
<p>Denne siden viser data fra StatRes som er tilknyttet denne enheten. Kilde: StatRes/SSB.</p>
</c:if>
<c:if test="${en}">
<p>This page shows data from StatRes registered for this unit. Source: StatRes/SSB.</p>
</c:if>

<c:if test="${no}">
<p><strong>StatRes var SSBs system for statistikk om statlig ressursbruk og resultater. Denne statistikken er opphørt fra 2015; perioden som dekkes er fra 2006-2014.</strong></p>
</c:if>
<c:if test="${en}">
<p><strong>StatRes was SSB's system for statistics on government use of resources and results. This statistic is discontinued from 2015; period covered is from 2006 to 2014.</strong></p>
</c:if>

<c:if test="${empty statres}">
<p><em>${en ? "No data for this unit." : "Ingen data er registrert for denne enheten."}</em></p>
</c:if>


<c:if test="${!(empty statres)}">
    
    
<table class="zebra">
<caption>${en ? "Data from StatRes" : "Data fra StatRes"}</caption>
<thead>
<tr>
<th>${en ? "Year" : "År"}</th>
<th>${en ? "StatRes name" : "StatRes navn"}</th>
<th>${en ? "Prod." : "Prod."}</th>
<th>${en ? "Salaries" : "Lønn"}</th>
<th>${en ? "Purchases" : "Kjøp"}</th>
<th>${en ? "Invest." : "Invest."}</th>
<th>${en ? "Appropr." : "Overf."}</th>
<th>${en ? "Expend." : "Utgift."}</th>
<th>${en ? "Pension" : "Pensjon"}</th>
<th>${en ? "FTEs" : "Årsverk"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${statres}" var="s">
<tr>
<td>${s.aar}</td>
<td class="tdtext">${fn:escapeXml(s.statresNavn)}</td>
<td>${s.egenproduksjon}</td>
<td>${s.lonnskostEgenprod}</td>
<td>${s.varerTjenester}</td>
<td>${s.investeringer}</td>
<td>${s.overforinger}</td>
<td>${s.totaleUtgifter}</td>
<td>${s.premieSp}</td>
<td>${s.avtalteAarsverk}</td>
</tr>
</c:forEach>
</tbody>
</table>
<ul class="tablenote">
<li>${en ? "Prod. = Own production (million Nkr)" : "Prod. = Egenproduksjon (mill. kr)"}</li>
<li>${en ? "Salaries = Salaries as a percentage of own production" : "Lønn = Lønnskostnader i prosent av egenproduksjon"}</li>
<li>${en ? "Purchases = Purchases of goods and services as a percentage of own production" : "Kjøp = Kjøp av varer og tjenester i prosent av egenproduksjon"}</li>
<li>${en ? "Invest = Investments (million Nkr)" : "Invest. = Investeringer (mill. kr)"}</li>
<li>${en ? "Appropr. = Appropriations (million Nkr)" : "Overf. = Overføringer (mill. kr)"}</li>
<li>${en ? "Expend. = Total expenditure (million Nkr)" : "Utgift. = Totale utgifter (mill. kr)"}</li>
<li>${en ? "Pension = Estimated premium to the State Pension Fund (million Nkr)" : "Pensjon = Beregnet premie til Statens Pensjonskasse (mill. kr)"}</li>
<li>${en ? "FTEs = FTEs (exclusive long-term leave)" : "Årsverk = Avtalte årsverk eksklusive lange fravær (årsverk)"}</li>
</ul>
<p class="tablenote">${en ? "Source: " : "Kilde: "}<a href="http://ssb.no/statres/" target="_blank">StatRes/SSB</a>.</p>


</c:if>


</div>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
