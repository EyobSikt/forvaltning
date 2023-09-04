<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over departement.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Departementer per ${brukerdato} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Ministries per ${brukerdato} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Oversikt over norske departementer per ${brukerdato}." />
    <c:param name="beskrivelseEn" value="List of Norwegian ministries per ${brukerdato}." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<div id="main">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> Departementer
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Ministries
</c:if>
</div>

<h1>${en ? "List of ministries at a given time" : "Oversikt over departementene på bestemt tidspunkt"}</h1>

<c:if test="${empty departementer}">
<p><em>${en ? "No data at given date." : "Ingen data på gitt tidspunkt."}</em></p>
</c:if>
<c:if test="${!brukerdato.gyldig}">
<p><em>${en ? "Invalid date." : "Dato er ugyldig."}</em></p>
</c:if>

<c:if test="${!(empty departementer)}">
<h2>${en ? "Ministries per" : "Departementer per"} ${brukerdato}</h2>
<ul class="data">
<c:forEach items="${departementer}" var="dep">
<c:if test="${brukerdato.aar >= 1947}">
<li><a href="<p:url value="/data/enhet/${dep.idnum}/hierarki?d=${brukerdato.dag}&m=${brukerdato.maaned}&y=${brukerdato.aar}" />">${fn:escapeXml(dep.langtNavn)}</a></li>
</c:if>
<c:if test="${brukerdato.aar < 1947}">
<li>${fn:escapeXml(dep.langtNavn)}</li>
</c:if>
</c:forEach>
</ul>
</c:if>


<h3>${en ? "Select date:" : "Velg tidspunkt:"}</h3>
<form action="" method="get">
<p>
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


</div>


<div id="sidebar">

<c:if test="${no}">
<h3>Merk:</h3>
<p>
Data er oppdatert per ${sistOppdatertDato}.
</p>
<p>
Ved å endre tidspunktet vil en få oversikt over departementene på forskjellige
tidspunkt. Under departementslenken vil en for perioden 01.01.1947 til ${sistOppdatertDato}
(databasens oppdateringstidspunkt) få hierarkisk oversikt over interne og ytre enheter under departementet.
</p>
<p>
For perioden 02.03.1814 til 01.01.1947 vil en få ut departementsoversikt, med da
utan lenking videre til hierarkiske oversikt.
</p>
</c:if>

<c:if test="${en}">
<h3>Remark:</h3>
<p>
Data is updated per ${sistOppdatertDato}.
</p>
<p>
Under the link to the left you will for the period 01.01.1947 to ${sistOppdatertDato} for
selected ministry find list of bodies within and outside ministries.
</p>
<p>
For the period 2.03.1814 til 01.01.1947 only unlinked lists are given.
</p>
</c:if>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
