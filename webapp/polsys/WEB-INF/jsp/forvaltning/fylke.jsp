<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Enheter per gitt fylke.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fylker[fylkenummer].tekstEntall} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fylker[fylkenummer].tekstEntall} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Enheter på gitt fylke i forvaltningsdatabasen." />
    <c:param name="beskrivelseEn" value="Units by county in the State Administration Database." />
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
> <a href="<p:url value="/forvaltning/fylke" />">Fylker</a>
> ${fylker[fylkenummer].tekstEntall}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> <a href="<p:url value="/forvaltning/fylke" />">Counties</a>
> ${fylker[fylkenummer].tekstEntall}
</c:if>
</div>

<h1>${en ? "Units in" : "Enheter i"} ${fylker[fylkenummer].tekstEntall} ${en ? "county" : "fylke"}</h1>

<c:if test="${no}"><p>Enheter lokalisert i fylket ${fylker[fylkenummer].tekstEntall}.</p></c:if>
<c:if test="${en}"><p>Units located in county ${fylker[fylkenummer].tekstEntall}.</p></c:if>


<%-- ====================================== Sektor og dato =============== --%>
<h3>${en ? "Affiliation and date:" : "Type enheter og tidspunkt:"}</h3>
<form action="" method="get">
<p>
${en ? "Affiliations:" : "Type enheter"}
<select size="1" name="s">
<option value="1" ${param.s eq 1 ? 'selected="selected"' : ''} >${en ? "Public administration bodies" : "Forvaltningsorgan"}</option>
<option value="2" ${param.s eq 2 ? 'selected="selected"' : ''} >${en ? "Sate companies and foundations" : "Foretak, selskap, stiftelser"}</option>
<option value="3" ${param.s eq 3 ? 'selected="selected"' : ''} >${en ? "All units" : "Alle enheter"}</option>
</select>

${en ? "Year:" : "År:"} <input type="text" value="${brukerdato.aar}" maxlength="4" size="6" name="y" />

${en ? "Month:" : "Måned:"}
<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}" ${brukerdato.maaned eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

${en ? "Day:" : "Dag:"}
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}" ${brukerdato.dag eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

<input type="submit" value="OK" />
</p>
</form>
<%-- ===================================================================== --%>


<c:if test="${empty enheter[fylkenummer] && empty satellitter[fylkenummer]}">
<p><em>${en ? "No units on selected date" : "Ingen enheter på valgt dato"}.</em></p>
</c:if>

<%-- ===================================== Tabell Enheter ================ --%>
<c:if test="${!empty enheter[fylkenummer]}">
<table class="text zebra sortable">
<caption>
    <c:if test="${empty param.s || param.s eq '1'}">${en ? "Public administration bodies" : "Forvaltningsorgan"}</c:if>
    <c:if test="${param.s eq '2'}">${en ? "Sate companies and foundations" : "Foretak, selskap, stiftelser"}</c:if>
    <c:if test="${param.s eq '3'}">${en ? "Units" : "Enheter"}</c:if>
    per ${brukerdato}
</caption>
<thead>
<tr>
<th>${en ? "Code" : "K.nr."}</th>
<th>${en ? "Municipality" : "Kommune"}</th>
<th>${en ? "Ministry" : "Departement"}</th>
<th>${en ? "Affiliation" : "Tilkn."}</th>
<th>${en ? "Category" : "Type"}</th>
<th>${en ? "Units" : "Enhet"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${enheter[fylkenummer]}" var="e">
<tr>
<td>${e.kommune.kode}</td>
<td>${fn:escapeXml(e.kommune.tekstEntall)}</td>
<td>${fn:escapeXml(e.overordnetDepartement.langtNavn)}</td>
<td title="${fn:escapeXml(tilknytninger[e.tilknytningsform].tekstEntall)}">${e.tilknytningsform}</td>
<td title="${fn:escapeXml(grunnenheter[e.grunnenhet].tekstEntall)}">${e.grunnenhet}</td>
<td><a href="<p:url value="/data/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></td>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<%-- ===================================================================== --%>


<%-- ===================================== Tabell Satellitter ============ --%>
<c:if test="${!empty satellitter[fylkenummer]}">
<table class="text zebra sortable">
<caption>${en ? "Local units" : "Lokale avdelinger"} per ${brukerdato}</caption>
<thead>
<tr>
<th>${en ? "Code" : "K.nr."}</th>
<th>${en ? "Municipality" : "Kommune"}</th>
<th>${en ? "Ministry" : "Departement"}</th>
<th>${en ? "Affiliation" : "Tilkn."}</th>
<th>${en ? "Category" : "Type"}</th>
<th>${en ? "Superior units" : "Overordnet enhet"}</th>
<th>${en ? "Local unit" : "Lokal avdeling"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${satellitter[fylkenummer]}" var="s">
<tr>
<td>${s.kommune.kode}</td>
<td>${fn:escapeXml(s.kommune.tekstEntall)}</td>
<td>${fn:escapeXml(s.enhet.overordnetDepartement.langtNavn)}</td>
<td title="${fn:escapeXml(tilknytninger[s.enhet.tilknytningsform].tekstEntall)}">${s.enhet.tilknytningsform}</td>
<td title="${fn:escapeXml(grunnenheter[s.enhet.grunnenhet].tekstEntall)}">${s.enhet.grunnenhet}</td>
<td><a href="<p:url value="/data/enhet/${s.enhet.idnum}" />">${fn:escapeXml(s.enhet.langtNavn)}</a></td>
<td>${fn:escapeXml(s.navn)}</td>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<%-- ===================================================================== --%>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
