<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall ansatte gitt fylke.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(fylke)} - Statsansatte - Forvaltningsdatabasen" />
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
> <a href="<p:url value="/data/ansatte/fylke" />">Fylke</a>
> ${fn:escapeXml(fylke)}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> <a href="<p:url value="/data/ansatte/fylke" />">County</a>
> ${fn:escapeXml(fylke)}
</c:if>
</div>

<h1>${fn:escapeXml(fylke)}</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>


<form action="" method="get">
<ul>
<li>Denne siden viser statistikk over statsansatte i ${fn:escapeXml(fylke)}.</li>
<li>Tips: Du kan sortere en tabell på gitt kolonne (både stigende og synkende) ved å klikke på kolonneoverskriften.</li>

<li>Noen tabeller viser kun statistikk for ett år. Valgt år er ${valgtAar}.</li>

<li>
Velg år:

<select name="aar">
<c:forEach items="${alleAar}" var="i">
<option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
</c:forEach>
</select>

${en ? "Salary category:" : "Lønnskategori:"}
<select size="1" name="lk">
<option value="2" ${param.lk eq '2' ? 'selected="selected"' : ''} >${en ? "2. Ordinary" : "2. Ordinært regulativlønte"}</option>
<option value="3" ${param.lk eq '3' ? 'selected="selected"' : ''} >${en ? "3. Contract" : "3. Overenskomst, timelønte, permisjon, kontrakter + honorar f.o.m 2016 "}</option>
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

<c:if test="${fn:length(total) >= 2}">
<h2>Grafer</h2>

<p:graf tittel="Antall statsansatte (${p:sgln(param.lk)}) i ${fn:escapeXml(fylke)}"
        aarsverk="${false}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />

<p:graf tittel="Antall årsverk i staten (${p:sgln(param.lk)}) i ${fn:escapeXml(fylke)}"
        aarsverk="${true}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />


<h2>Tabeller</h2>
</c:if>

<%-- ==================== ANTALL ANSATTE ================================  --%>
<table class="zebra sortable">
<caption>Antall statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) i ${fn:escapeXml(fylke)}</caption>

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
<caption>Antall årsverk i staten (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) i ${fn:escapeXml(fylke)}</caption>

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
<caption>Oversikt over ansatte, årsverk og andel statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) i ${fn:escapeXml(fylke)} </caption>

<thead>

<tr>
<th>År</th>
<th>Statsansatte</th>
<th>Andel ansatte</th>
<th>% Stat</th>
<th>Antall årsverk</th>
<th>Andel årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>
<td>${t.value.ansatte}</td>
<td><fmt:formatNumber value="${(t.value.ansatte / totalLand[t.key].ansatte)*100}" pattern="0.0" /></td>

<c:set var="s" value="" />
<c:if test="${sysselsatte[t.key] != null && sysselsatte[t.key] != 0}">
<fmt:formatNumber value="${(t.value.ansatte / sysselsatte[t.key]) * 100}" pattern="0.0" var="s" />
</c:if>
<td>${s}</td>

<td><fmt:formatNumber value="${t.value.aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(t.value.aarsverk / totalLand[t.key].aarsverk)*100}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<p class="tablenote">
Kolonnene "Andel ansatte" og "Andel årsverk" viser fylkets andel av landets statsansatte/statsårsverk.
Kolonnen "% Stat" viser hvor stor andel fylkets statsansatte er av fylkets totale antall sysselsatte.
</p>
<%-- ====================================================================  --%>



<%-- ========================= KOMMUNEOVERSIKT ==========================  --%>
<table class="zebra sortable">
<caption>Statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per kommune i ${fn:escapeXml(fylke)}, år ${valgtAar}</caption>

<thead>

<tr>
<th>K.nr.</th>
<th>Kommune</th>
<th>Ansatte</th>
<th>Andel land</th>
<th>Andel fylke</th>
<th>% Stat</th>
<th>Årsverk</th>
<th>Andel land</th>
<th>Andel fylke</th>
</tr>
</thead>

<tbody>
<c:forEach items="${kommuner}" var="a">
<tr>
<td>${a.kommunenummer}</td>
<td class="tdtext"><a href="<p:url value="/data/ansatte/kommune/${a.kommunenummer}" />">${fn:escapeXml(a.kommune)}</a></td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${(a.ansatte / totalLand[valgtAar].ansatte)*100}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(a.ansatte / total[valgtAar].ansatte)*100}" pattern="0.0" /></td>

<c:set var="s" value="" />
<c:if test="${sysselsatteKommune[a.kommunenummer] != null && sysselsatteKommune[a.kommunenummer] != 0}">
<fmt:formatNumber value="${(a.ansatte / sysselsatteKommune[a.kommunenummer]) * 100}" pattern="0.0" var="s" />
</c:if>
<td>${s}</td>

<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(a.aarsverk / totalLand[valgtAar].aarsverk)*100}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(a.aarsverk / total[valgtAar].aarsverk)*100}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<p class="tablenote">
Kolonnen "Andel land" viser kommunens andel av landets statsansatte/statsårsverk.
Kolonnen "Andel fylke" viser kommunens andel av fylkets statsansatte/statsårsverk.
Kolonnen "% Stat" viser hvor stor andel kommunens statsansatte er av kommunens totale antall sysselsatte.
</p>
<%-- ====================================================================  --%>



<%-- ====================== Departement aggregert =======================  --%>
<table class="zebra sortable">
<caption>Statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per departementsområde i ${fn:escapeXml(fylke)}, år ${valgtAar}</caption>

<thead>

<tr>
<th>Departementsområde</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${depAgg}" var="a">
<tr>
<th><a href="<p:url value="/data/ansatte/etat/${fn:escapeXml(a.depkode)}" />">${fn:escapeXml(a.dep)}</a></th>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- =========================== Etat aggregert =======================  --%>
<table class="zebra sortable">
<caption>Statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per etat i ${fn:escapeXml(fylke)}, år ${valgtAar}</caption>

<thead>

<tr>
<th>Etat</th>
<th>Departementsområde</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etatAgg}" var="a">
<tr>
<th>${fn:escapeXml(a.etat)}</th>
<td class="tdtext">${fn:escapeXml(a.dep)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- =========================== Etat per kommune =======================  --%>
<table class="zebra sortable">
<caption>Statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per etat og kommune i ${fn:escapeXml(fylke)}, år ${valgtAar}</caption>

<thead>

<tr>
<th>Etat</th>
<th>K.nr.</th>
<th>Kommune</th>
<th>Ansatte</th>
<th>Årsverk</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etat}" var="a">
<tr>
<th>${fn:escapeXml(a.etat)}</th>
<td>${a.kommunenummer}</td>
<td class="tdtext">${fn:escapeXml(a.kommune)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
