<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall ansatte gitt for gitt dep.område.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(dep)} - Statsansatte - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Employees - State Administration Database" />
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
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/statsansatte.html">Statsansatte</a>
> Departementsområde
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> Ministry overview
</c:if>
</div>

<h1>${fn:escapeXml(dep)}</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>


<form action="" method="get">
<ul>
<li>Denne siden viser statistikk over statsansatte i departementsområdet ${fn:escapeXml(dep)}.</li>
<li>Klikk en gang på en kolonneoverskrift for å sortere tabellen stigende på gitt kolonne. Klikk en gang til for å sortere synkende.</li>

<li>
Noen tabeller viser kun statistikk for ett år. Valgt år er ${valgtAar}.
Velg et annet år her:

<select name="aar">
<c:forEach items="${alleAar}" var="i">
<option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
</c:forEach>
</select>

<input type="submit" value="OK" />
</li>
</ul>
</form>

<c:import url="/WEB-INF/jspf/forvaltning/sst_disclaimer.jsp" />

<c:if test="${fn:length(total) >= 2}">
<h2>Grafer</h2>

<p:graf tittel="Antall statsansatte i departementsområdet|${fn:escapeXml(dep)}"
        aarsverk="${false}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />

<p:graf tittel="Antall årsverk i staten i departementsområdet|${fn:escapeXml(dep)}"
        aarsverk="${true}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />

<h2>Tabeller</h2>
</c:if>

<%-- ==================== ANTALL ANSATTE ================================  --%>
<table class="zebra sortable">
<caption>Antall statsansatte i departementsområdet ${fn:escapeXml(dep)}</caption>

<thead>
<tr>
<th></th>
<th colspan="3">Totalt</th>
<th colspan="3">Heltid</th>
<th colspan="3">Deltid</th>
</tr>

<tr>
<th>År</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>

<td>${mennTotal[t.key].ansatte}</td>
<td>${kvinnerTotal[t.key].ansatte}</td>
<td>${t.value.ansatte}</td>

<td>${mennHeltid[t.key].ansatte}</td>
<td>${kvinnerHeltid[t.key].ansatte}</td>
<td>${heltid[t.key].ansatte}</td>

<td>${mennDeltid[t.key].ansatte}</td>
<td>${kvinnerDeltid[t.key].ansatte}</td>
<td>${deltid[t.key].ansatte}</td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ==================== ANTALL ÅRSVERK ================================  --%>
<table class="zebra sortable">
<caption>Antall årsverk i staten i departementsområdet ${fn:escapeXml(dep)}</caption>

<thead>
<tr>
<th></th>
<th colspan="3">Totalt</th>
<th colspan="3">Heltid</th>
<th colspan="3">Deltid</th>
</tr>

<tr>
<th>År</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>

<td><fmt:formatNumber value="${mennTotal[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${kvinnerTotal[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${t.value.aarsverk}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${mennHeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${kvinnerHeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${heltid[t.key].aarsverk}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${mennDeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${kvinnerDeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${deltid[t.key].aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ========================== ANDEL ===================================  --%>
<table class="zebra sortable">
<caption>Departementsområdet ${fn:escapeXml(dep)} sin andel av landets statsansatte</caption>

<thead>

<tr>
<th>År</th>
<th>Prosent av ansatte</th>
<th>Prosent av årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>
<td><fmt:formatNumber value="${(t.value.ansatte / totalLand[t.key].ansatte)*100}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(t.value.aarsverk / totalLand[t.key].aarsverk)*100}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ======================= Fylkesoversikt =============================  --%>
<table class="zebra sortable">
<caption>Statsansatte i departementsområdet ${fn:escapeXml(dep)} per fylke, år ${valgtAar}</caption>

<thead>
<tr>
<th>Fylke</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${ansatteFylker}" var="a">
<tr>
<th><a href="<p:url value="/data/ansatte/fylke/${a.fylkenummer}" />">${fn:escapeXml(fylker[a.fylkenummer].fylke)}</a></th>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ========================= KOMMUNEOVERSIKT ==========================  --%>
<table class="zebra sortable">
<caption>Statsansatte i departementsområdet ${fn:escapeXml(dep)} per kommune, år ${valgtAar}</caption>

<thead>
<tr>
<th>Fylke</th>
<th>Kommune</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${kommuner}" var="a">
<tr>
<th>${fn:escapeXml(fylker[a.fylkenummer].fylke)}</th>
<td class="tdtext"><a href="<p:url value="/data/ansatte/kommune/${a.kommunenummer}" />">${fn:escapeXml(a.kommune)}</a></td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- =========================== Etat aggregert =======================  --%>
<table class="zebra sortable">
<caption>Statsansatte per etat i departementsområdet ${fn:escapeXml(dep)}, år ${valgtAar}</caption>

<thead>

<tr>
<th>Etat</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etatAgg}" var="a">
<tr>
<th><a href="<p:url value="/data/ansatte/etat/${fn:escapeXml(a.etatkode)}" />">${fn:escapeXml(a.etat)}</a></th>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- =========================== Etat per fylke =========================  --%>
<table class="zebra sortable">
<caption>Statsansatte per etat og fylke i departementsområdet ${fn:escapeXml(dep)}, år ${valgtAar}</caption>

<thead>

<tr>
<th>Fylke</th>
<th>Etat</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etat}" var="a">
<tr>
<th>${fn:escapeXml(fylker[a.fylkenummer].fylke)}</th>
<td class="tdtext">${fn:escapeXml(a.etat)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- =========================== Etat per kommune =======================  --%>
<table class="zebra sortable">
<caption>Statsansatte per etat og kommmune i departementsområdet ${fn:escapeXml(dep)}, år ${valgtAar}</caption>

<thead>

<tr>
<th>Fylke</th>
<th>Kommune</th>
<th>Etat</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etatKommune}" var="a">
<tr>
<th>${fn:escapeXml(fylker[a.fylkenummer].fylke)}</th>
<td class="tdtext">${fn:escapeXml(a.kommune)}</td>
<td class="tdtext">${fn:escapeXml(a.etat)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
