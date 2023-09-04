<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall ansatte gitt etat.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Etatkode ${fn:escapeXml(ansatte.etatkode)} - Statsansatte - Forvaltningsdatabasen" />
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
> Etatkode ${fn:escapeXml(ansatte.etatkode)}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> Agency code ${fn:escapeXml(ansatte.etatkode)}
</c:if>
</div>

<h1>Etatkode ${fn:escapeXml(ansatte.etatkode)}</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>


<ul>
<li>Denne siden viser statistikk over statsansatte for etatkode ${fn:escapeXml(ansatte.etatkode)}.</li>
<li><strong>Merk:</strong> Kilden til data om statsansatte er SST. Denne siden viser antall ansatte for gitt etatkode.
Merk at endring i ansatte over tid kan skyldes at etatkoden inkluderer ulike etater for ulike år.
Les mer <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/dokumentasjon/statsansatte.html">her</a>.</li>
</ul>

<form action="" method="get">
<p>
${en ? "Salary category:" : "Lønnskategori:"}
<select size="1" name="lk">
<option value="2" ${param.lk eq '2' ? 'selected="selected"' : ''} >${en ? "2. Ordinary" : "2. Ordinært regulativlønte"}</option>
<option value="3" ${param.lk eq '3' ? 'selected="selected"' : ''} >${en ? "3. Contract" : "3. Overenskomst, timelønte, permisjon, kontrakter + honorar f.o.m 2016"}</option>
<option value="4" ${param.lk eq '4' ? 'selected="selected"' : ''} >${en ? "4. Leaders from 2004" : "4. Ledere på kontrakter f.o.m. 2004 t.o.m 2014"}</option>
<option value="a" ${param.lk eq 'a' ? 'selected="selected"' : ''} >${en ? "All categories" : "Alle kategorier"}</option>
</select>

<input type="submit" value="OK" />
</p>
</form>



<%-- ==================== ETAT NAVN =====================================  --%>
<table class="text zebra">
<caption>Navn på etat for etatkode ${fn:escapeXml(ansatte.etatkode)} for ulike år</caption>

<thead>
<tr><th>År</th><th>Navn</th></tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr><td>${t.key}</td><td>${fn:escapeXml(ansatte.navn[t.key].etat)}</td></tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<c:if test="${fn:length(total) >= 2}">
<h2>Grafer</h2>

<p:graf tittel="Antall ansatte (${p:sgln(param.lk)}) i etatkode ${fn:escapeXml(ansatte.etatkode)}"
        aarsverk="${false}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />

<p:graf tittel="Antall årsverk (${p:sgln(param.lk)}) i etatkode ${fn:escapeXml(ansatte.etatkode)}"
        aarsverk="${true}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />

<h2>Tabeller</h2>
</c:if>



<%-- ==================== ANTALL ANSATTE ================================  --%>
<table class="zebra sortable">
<caption>Antall ansatte (${p:sgln(param.lk)}) i etatkode ${fn:escapeXml(ansatte.etatkode)}</caption>

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
<caption>Antall årsverk (${p:sgln(param.lk)}) i etatkode ${fn:escapeXml(ansatte.etatkode)}</caption>

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
<caption>Etatkode ${fn:escapeXml(ansatte.etatkode)} sin andel av landets statsansatte (${p:sgln(param.lk)})</caption>

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



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
