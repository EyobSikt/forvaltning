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
    <c:param name="tittelNo" value="(${fn:escapeXml(ansatte.etatkode)}) ${fn:escapeXml(ansatte.etat)}, år ${valgtAar} - Statsansatte - Forvaltningsdatabasen" />
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
> Etat ${fn:escapeXml(ansatte.etatkode)}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> Agency
</c:if>
</div>

<h1>(${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))}    </h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>


<form action="" method="get">
<ul>
<li>Denne siden viser statistikk over statsansatte for etat (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))}.</li>
<c:if test="${!depom}">
<li>Departementsområdet til denne etaten er <a href="<p:url value="/data/ansatte/etat/${ansatte.depkode}" />">(${ansatte.depkode}) ${fn:escapeXml(ansatte.dep)}</a>.</li>
</c:if>

<li>Tips: Du kan sortere en tabell på gitt kolonne (både stigende og synkende) ved å klikke på kolonneoverskriften.</li>
<li>
Valgt år er ${valgtAar}. Velg år:

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

<input type="submit" value="OK" />
</li>
</ul>
</form>


<c:import url="/WEB-INF/jspf/forvaltning/sst_disclaimer.jsp" />
<c:import url="/WEB-INF/jspf/forvaltning/statsansatte_disclaimer.jsp" />

<c:if test="${param.lk ==null || param.lk =='0' || param.lk=='2'}">  <c:set var="tabellcaption" value="ordinært regulativlønte"> </c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='3'}">  <c:set var="tabellcaption" value="lønnskategori 3"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='4'}">  <c:set var="tabellcaption" value="lønnskategori 4"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk =='a'}">  <c:set var="tabellcaption" value="alle lønnskategorier"></c:set></c:if>

<%-- ==================== KOBLING ENHETER ===============================  --%>
<c:if test="${!empty koblingEnheter}">
<h3>Endring i ansatte over tid</h3>
<p>Ansattdata fra SST er knyttet opp til enhetene i Forvaltningsdatabasen.
Under er aktuelle linker til enheter for denne SST-etaten.</p>
<ul>
<c:forEach items="${koblingEnheter}" var="enhet">
<li><a href="<p:url value="/data/enhet/${enhet.idnum}/ansatte" />">${fn:escapeXml(enhet.langtNavn)}</a></li>
</c:forEach>
</ul>
</c:if>
<%-- ====================================================================  --%>



<%-- ==================== ANTALL ANSATTE ================================  --%>
<table class="zebra">
<caption>Antall ansatte og årsverk (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) i (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))} i ${valgtAar}</caption>

<thead>
<tr>
<th></th>
<th colspan="2">Totalt</th>
<th colspan="2">Heltid</th>
<th colspan="2">Deltid</th>
</tr>

<tr>
<th></th>
<th>Ansatte</th><th>Årsverk</th>
<th>Ansatte</th><th>Årsverk</th>
<th>Ansatte</th><th>Årsverk</th>
</tr>
</thead>

<tbody>
<tr>
<th>Menn</th>
<td>${mennTotal[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${mennTotal[valgtAar].aarsverk}" pattern="0.0" /></td>
<td>${mennHeltid[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${mennHeltid[valgtAar].aarsverk}" pattern="0.0" /></td>
<td>${mennDeltid[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${mennDeltid[valgtAar].aarsverk}" pattern="0.0" /></td>
</tr>

<tr>
<th>Kvinner</th>
<td>${kvinnerTotal[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${kvinnerTotal[valgtAar].aarsverk}" pattern="0.0" /></td>
<td>${kvinnerHeltid[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${kvinnerHeltid[valgtAar].aarsverk}" pattern="0.0" /></td>
<td>${kvinnerDeltid[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${kvinnerDeltid[valgtAar].aarsverk}" pattern="0.0" /></td>
</tr>

<tr>
<th>Totalt</th>
<td>${total[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${total[valgtAar].aarsverk}" pattern="0.0" /></td>
<td>${heltid[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${heltid[valgtAar].aarsverk}" pattern="0.0" /></td>
<td>${deltid[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${deltid[valgtAar].aarsverk}" pattern="0.0" /></td>
</tr>
</tbody>

</table>
<%-- ====================================================================  --%>




<%-- ========================== ANDEL ===================================  --%>
<table class="zebra sortable">
<caption>(${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))} sin andel av landets statsansatte (${tabellcaption}<%--${p:sgln(param.lk)}--%>) i ${valgtAar}</caption>

<thead>

<tr>
<th>Prosent av ansatte</th>
<th>Prosent av årsverk</th>
</tr>
</thead>

<tbody>
<tr>
<td><fmt:formatNumber value="${(total[valgtAar].ansatte / totalLand[valgtAar].ansatte)*100}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(total[valgtAar].aarsverk / totalLand[valgtAar].aarsverk)*100}" pattern="0.0" /></td>
</tr>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ======================= Fylkesoversikt =============================  --%>
<table class="zebra sortable">
<caption>Statsansatte (${tabellcaption}<%--${p:sgln(param.lk)}--%>) i (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))} per fylke, år ${valgtAar}</caption>

<thead>
<tr>
<th>F.nr.</th>
<th>Fylke</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${ansatteFylker}" var="a">
<tr>
<td>${a.fylkenummer}</td>
<td class="tdtext"><a href="<p:url value="/data/ansatte/fylke/${a.fylkenummer}" />">${fn:escapeXml(fylker[a.fylkenummer].fylke)}</a></td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ========================= KOMMUNEOVERSIKT ==========================  --%>
<table class="zebra sortable">
<caption>Statsansatte (${tabellcaption}<%--${p:sgln(param.lk)}--%>) i (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))} per kommune, år ${valgtAar}</caption>

<thead>
<tr>
<th>Fylke</th>
<th>K.nr.</th>
<th>Kommune</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${kommuner}" var="a">
<tr>
<td class="tdtext">${fn:escapeXml(fylker[a.fylkenummer].fylke)}</td>
<td>${a.kommunenummer}</td>
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
<caption>Statsansatte (${tabellcaption}<%--${p:sgln(param.lk)}--%>) per etat i (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))}, år ${valgtAar}</caption>

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
<td class="tdtext">${fn:escapeXml(a.etat)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- =========================== Etat per fylke =========================  --%>
<table class="zebra sortable">
<caption>Statsansatte (${tabellcaption}<%--${p:sgln(param.lk)}--%>) per etat og fylke i (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))}, år ${valgtAar}</caption>

<thead>

<tr>
<th>F.nr.</th>
<th>Fylke</th>
<th>Etat</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etatFylke}" var="a">
<tr>
<td>${a.fylkenummer}</td>
<td class="tdtext">${fn:escapeXml(fylker[a.fylkenummer].fylke)}</td>
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
<caption>Statsansatte (${tabellcaption}<%--${p:sgln(param.lk)}--%>) per etat og kommmune i (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))}, år ${valgtAar}</caption>

<thead>

<tr>
<th>Fylke</th>
<th>K.nr.</th>
<th>Kommune</th>
<th>Etat</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etatKommune}" var="a">
<tr>
<td class="tdtext">${fn:escapeXml(fylker[a.fylkenummer].fylke)}</td>
<td>${a.kommunenummer}</td>
<td class="tdtext">${fn:escapeXml(a.kommune)}</td>
<td class="tdtext">${fn:escapeXml(a.etat)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ========================= Arbeidssted ==============================  --%>
<table class="zebra sortable">
<caption>Statsansatte (${tabellcaption}<%--${p:sgln(param.lk)}--%>) i (${fn:escapeXml(ansatte.etatkode)}) <%--${fn:escapeXml(ansatte.etat)}--%> Departementsområdet ${fn:escapeXml(fn:substring(ansatte.etat, 21, 70))} per arbeidssted, år ${valgtAar}</caption>

<thead>
<tr>
<th>Fylke</th>
<th>K.nr.</th>
<th>Kommune</th>
<th>Etat</th>
<th>Etatkode</th>
<th>Arbeidssted</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${sted}" var="a">
<tr>
<td class="tdtext">${fn:escapeXml(fylker[a.fylkenummer].fylke)}</td>
<td>${a.kommunenummer}</td>
<td class="tdtext">${fn:escapeXml(a.kommune)}</td>
<td class="tdtext">${fn:escapeXml(a.etat)}</td>
<td><a href="<p:url value="/data/ansatte/etat/${fn:escapeXml(a.etatkode)}?aar=${valgtAar}" />">${fn:escapeXml(a.etatkode)}</a></td>
<td class="tdtext">${fn:escapeXml(a.arbeidssted)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
