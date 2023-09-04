<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall ansatte.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Landsoversikt, etater - Statsansatte - Forvaltningsdatabasen" />
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
> Landsoversikt - departementområder og etater
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> By ministries and angencies
</c:if>
</div>

<h1>Landsoversikt - etater og departementsområder</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>


<form action="" method="get">
<ul>
<li>Denne siden viser statistikk over statsansatte på etatnivå og departementsområdenivå.</li>
<li>Tips: Du kan sortere en tabell på gitt kolonne (både stigende og synkende) ved å klikke på kolonneoverskriften.</li>
</ul>

<h3>Velg år, lønnskategori og tabell</h3>

<p>
År:
<select name="aar">
<c:forEach items="${alleAar}" var="i">
<option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
</c:forEach>
</select>

${en ? "Salary category:" : "Lønnskategori:"}
<select size="1" name="lk">
<option value="2" ${param.lk eq '2' ? 'selected="selected"' : ''} >${en ? "2. Ordinary" : "2. Ordinært regulativlønte"}</option>
<option value="3" ${param.lk eq '3' ? 'selected="selected"' : ''} >${en ? "3. Contract" : "3. Overenskomst, timelønte, permisjon, kontrakter + honorar f.o.m 2016"}</option>
<option value="4" ${param.lk eq '4' ? 'selected="selected"' : ''} >${en ? "4. Leaders from 2004" : "4. Ledere på kontrakter f.o.m. 2004 t.o.m 2014"}</option>
<option value="a" ${param.lk eq 'a' ? 'selected="selected"' : ''} >${en ? "All categories" : "Alle kategorier"}</option>
</select>

Tabell:
<select name="vis">
<option value="tot">Ansatte og årsverk per etat</option>
<option value="ans" ${param.vis eq 'ans' ? 'selected="selected"' : ''}>Ansatte per kommune og etat</option>
<option value="verk" ${param.vis eq 'verk' ? 'selected="selected"' : ''}>Årsverk per kommune og etat</option>
</select>

<input type="submit" value="OK" />
</p>
</form>

<c:import url="/WEB-INF/jspf/forvaltning/sst_disclaimer.jsp" />
<c:import url="/WEB-INF/jspf/forvaltning/statsansatte_disclaimer.jsp" />


<%-- ====================== TOTAL ANTALL ================================  --%>
<c:if test="${empty param.vis || param.vis eq 'tot'}">
<table class="zebra sortable">
<caption>Antall statsansatte og årsverk i staten (${p:sgln(param.lk)}) per etatkode, år ${valgtAar}</caption>

<thead>

<tr>
<th>Kode</th>
<th>Navn</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<c:if test="${!empty t.etatkode and t.etatkode != null and t.etatkode!='-1' }">
<tr>
<td><a href="<p:url value="/data/ansatte/etat/${fn:escapeXml(t.etatkode)}" />">${fn:escapeXml(t.etatkode)}</a></td>
<td class="tdtext">${fn:escapeXml(t.etat)}</td>
<td>${t.ansatte}</td>
<td><fmt:formatNumber value="${t.aarsverk}" pattern="0.0" /></td>
</tr>
</c:if>
</c:forEach>
</tbody>

</table>
</c:if>
<%-- ====================================================================  --%>


<%-- ========================= KOMMUNEOVERSIKT - ANSATTE ================  --%>
<c:if test="${param.vis eq 'ans'}">
<table class="zebra sortable">
<caption>Antall statsansatte (${p:sgln(param.lk)}) per kommune og etatkode, år ${valgtAar}</caption>

<thead>
<tr>
<th>Fylke</th>
<th>K.nr.</th>
<th>Kommune</th>
<th>Total</th>
<c:forEach items="${ansatte}" var="e"><th>${fn:escapeXml(e.etatkode)}</th></c:forEach>
</tr>
</thead>

<tbody>
<c:forEach items="${kommuner}" var="k">
<tr>
<th>${fn:escapeXml(fylker[k.fylkenummer].fylke)}</th>
<td>${k.kommunenummer}</td>
<td class="tdtext"><a href="<p:url value="/data/ansatte/kommune/${k.kommunenummer}" />">${fn:escapeXml(k.kommune)}</a></td>
<td>${k.ansatte}</td>
<c:forEach items="${ansatte}" var="e"><td>${e.ansatteKommune[k.kommunenummer].ansatte}</td></c:forEach>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>
<%-- ====================================================================  --%>


<%-- ========================= KOMMUNEOVERSIKT - ÅRSVERK ================  --%>
<c:if test="${param.vis eq 'verk'}">
<table class="zebra sortable">
<caption>Antall årsverk i staten (${p:sgln(param.lk)}) per kommune og etatkode, år ${valgtAar}</caption>

<thead>
<tr>
<th>Fylke</th>
<th>K.nr.</th>
<th>Kommune</th>
<th>Total</th>
<c:forEach items="${ansatte}" var="e"><th>${fn:escapeXml(e.etatkode)}</th></c:forEach>
</tr>
</thead>

<tbody>
<c:forEach items="${kommuner}" var="k">
<tr>
<th>${fn:escapeXml(fylker[k.fylkenummer].fylke)}</th>
<td>${k.kommunenummer}</td>
<td class="tdtext"><a href="<p:url value="/data/ansatte/kommune/${k.kommunenummer}" />">${fn:escapeXml(k.kommune)}</a></td>
<td><fmt:formatNumber value="${k.aarsverk}" pattern="0.0" /></td>

<c:forEach items="${ansatte}" var="e">
<td><fmt:formatNumber value="${e.ansatteKommune[k.kommunenummer].aarsverk}" pattern="0.0" /></td>
</c:forEach>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>
<%-- ====================================================================  --%>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
