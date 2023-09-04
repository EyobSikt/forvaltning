<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over eksisterende enheter på gitt tidspunkt.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Enheter i ${aar} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Units in ${aar} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Liste over eksisterende enheter i ${aar}." />
    <c:param name="beskrivelseEn" value="List of units in ${aar}." />
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
> Enhetliste per år
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> List of units by year
</c:if>
</div>

<h1>Liste over relevante eksisterende enheter for Evalueringsportalen for gitt år</h1>

<p>Listen viser alle relevante enheter som har eksistert i løpet av gitt år.</p>

<h4>Relevante enheter er:</h4>
<ul>
<li>Departementene selv, men ingen interne enheter i departementene.</li>
<li>Alle tilknytningsformer, minus selskaper og stitelser, men inkludert 42 Særlovselskap.</li>
<li>Alle enheter av type 0 (Nasjonal organisasjon) og 11 (Hovedkontor).</li>
<li>For type 11 (Hovedkontor), brukes etatnavnet.</li>
<li>For type 20 (Grupper) og 21 (Enheter som inngår i grupper), kun enheter som ligger hierarkisk ett eller to nivåer under departement.</li>
</ul>


<h3>Oppgi årstall</h3>
<form action="" method="get">
År (åååå): <input type="text" value="${aar}" maxlength="4" size="6" name="y" />
<input type="submit" value="OK" />
</form>

<c:if test="${empty enheter}">
<p><em>Ingen data på gitt år.</em></p>
</c:if>


<c:if test="${!(empty enheter)}">
<table class="text sortable zebra">
<caption>Eksisterende enheter i år ${aar}, antall: ${fn:length(enheter)}</caption>

<thead>
<tr>
<th>ID</th>
<th>Navn</th>
<th>Kort navn</th>
<th>Kode</th>
<th>Tilknytningsform</th>
<th>Type</th>
<th>dep.id</th>
<th>Departement</th>
</tr>
</thead>


<tbody>
<c:forEach items="${enheter}" var="e">
<tr>
<td>${e.idnum}</td>
<td><a href="<p:url value="/data/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></td>
<td>${fn:escapeXml(e.kortNavn)}</td>
<td>${e.tilknytningsform}</td>
<td>${fn:escapeXml(tilknytninger[e.tilknytningsform].tekstEntall)}</td>
<td>${e.grunnenhet}</td>
<td>${e.overordnetDepartement.idnum}</td>
<td>${fn:escapeXml(e.overordnetDepartement.langtNavn)}</td>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
