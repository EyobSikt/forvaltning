<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser statistikk til DIFI.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="DIFI-statistikk - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="DIFI-statistikk - State Administration Database" />
    <c:param name="beskrivelseNo" value="Statistikk for DIFI." />
    <c:param name="beskrivelseEn" value="Statistikk for DIFI." />
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
> DIFI
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> DIFI
</c:if>
</div>

<h1>DIFI-statistikk</h1>

<p>Statistikk fra Forvaltningsdatabasen per ${brukerdato}. 
    (I denne statistikken er noen enheter fra basen unntatt.)</p>

<form action="" method="get">
<p>
${en ? "Day:" : "Dag:"}
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>

${en ? "Month:" : "Måned:"}
<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>

${en ? "Year:" : "År:"} <input type="text" value="${brukerdato.aar}" maxlength="4" size="6" name="y" />

<input type="submit" value="${en ? "Submit" : "OK"}" />
</p>
</form>


<table class="zebra">
<caption>Tabell 2.1 Statens omfang (forvaltningsenheter) etter grunntype organisasjon.</caption>
<thead>
<tr><th>Grunntype organisasjon</th><th>Antall enheter</th></tr>
</thead>

<tfoot>
<tr><th>I alt</th><td>${difi.statGrunnenhet["0"]+difi.statGrunnenhet["11"]+difi.statGrunnenhet["21E"]+antallDesent31+difi.statGrunnenhet["21G"]+antallDesent21+antallSatellitt}</td></tr>
</tfoot>

<tbody>
<tr><th>0 Nasjonale enkeltstående organ</th><td>${difi.statGrunnenhet["0"]}</td></tr>
<tr><th>11 Etater - Hovedkontor</th><td>${difi.statGrunnenhet["11"]}</td></tr>
<tr><th>21E Etater - Distriktsenheter/lokale enheter</th><td>${difi.statGrunnenhet["21E"]+antallDesent31}</td></tr>
<tr><th>21G Enkeltenheter i grupper</th><td>${difi.statGrunnenhet["21G"]+antallDesent21}</td></tr>
<tr><th>SAT Andre lokale enheter</th><td>${antallSatellitt}</td></tr>
</tbody>

</table>


<table class="zebra">
<caption>Tabell 2.2.a Antall virksomheter (forvaltningsorganer, ekskl. lokale enheter) etter hovedgruppe organisering.</caption>
<thead>
<tr><th>Forvaltningsorganer</th><th>Antall enheter</th><th>Kommentar</th></tr>
</thead>

<tfoot>
<tr><th>Forvaltningsorganer i alt</th><td>${difi.statTilknytning["20"]+difi.statTilknytning["31"]+difi.statTilknytning["33"]+difi.statTilknytning["32"]+difi.statTilknytning["35"]}</td><td>Ekskl. departementene</td></tr>
</tfoot>

<tbody>
<tr><th>10 Departement</th><td>${difi.statTilknytning["10"]}</td><td>Inkl. Statsministerens kontor</td></tr>
<tr><th>20 Sentraladministrative organ, direktorat</th><td>${difi.statTilknytning["20"]}</td><td></td></tr>
<tr><th>31 Forvaltningsorgan med særskilte fullmakter</th><td>${difi.statTilknytning["31"]}</td><td></td></tr>
<tr><th>33 Andre ordinære forvaltningsorgan</th><td>${difi.statTilknytning["33"]}</td><td></td></tr>
<tr><th>32 Forvaltningsbedrift</th><td>${difi.statTilknytning["32"]}</td><td></td></tr>
<tr><th>35 Finansieringsinstitusjon</th><td>${difi.statTilknytning["35"]}</td><td></td></tr>
</tbody>
</table>


<table class="zebra">
<caption>Tabell 2.2.b Antall virksomheter (selskaper) etter hovedgruppe organisering.</caption>
<thead>
<tr><th>Selskaper</th><th>Antall enheter</th><th>Kommentar</th></tr>
</thead>

<tfoot>
<tr><th>Selskaper i alt</th><td>${difi.statTilknytning["42"]+difi.statTilknytning["43"]+difi.statTilknytning["46"]+difi.statTilknytning["41"]+difi.statTilknytning["45"]}</td><td></td></tr>
</tfoot>

<tbody>
<tr><th>42 Særlovselskap</th><td>${difi.statTilknytning["42"]}</td><td></td></tr>
<tr><th>43 Statsforetak</th><td>${difi.statTilknytning["43"]}</td><td></td></tr>
<tr><th>46 Heleseforetak</th><td>${difi.statTilknytning["46"]}</td><td></td></tr>
<tr><th>41 Statsaksjeselskap, heleide</th><td>${difi.statTilknytning["41"]}</td><td></td></tr>
<tr><th>45 Statsaksjeselskap, deleide</th><td>${difi.statTilknytning["45"]}</td><td>Staten eier minst 50%</td></tr>
</tbody>
</table>



<table class="text sortable zebra">
<caption>Tabell 2.3. Statlige forvaltningsorgan. Antall: ${fn:length(difi.enheter)}</caption>

<thead>
<tr>
<th>Navn</th>
<th>Fylke</th>
<th>Dep</th>
<th>Tilknytningsform</th>
</tr>
</thead>


<tbody>
<c:forEach items="${difi.enheter}" var="e">
<tr>
<td><a href="<p:url value="/forvaltning/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></td>
<td>${fylker[e.fylkenummer].tekstEntall}</td>
<td title="${fn:escapeXml(e.overordnetDepartement.langtNavn)}">${fn:escapeXml(e.overordnetDepartement.kortNavn)}</td>
<td>${fn:escapeXml(tilknytninger[e.tilknytningsform].tekstEntall)}</td>
</tr>
</c:forEach>
</tbody>

</table>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
