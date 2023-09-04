<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Antall enheter endring.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Antall type - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Antall type - State Administration Database" />
    <c:param name="beskrivelseNo" value="Antall type." />
    <c:param name="beskrivelseEn" value="Antall type." />
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
> Antall type enhet
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Antall type enhet
</c:if>
</div>

<h1>Antall enheter klassifisert på type</h1>


<table class="zebra">
<caption>Antall enheter per type enhet. Antall er per 1.1. årene 1947-2011</caption>

<thead>
<tr>
<th>År</th>
<th>Nasjonal organisasjon</th>
<th>Etat</th>
<th>Gruppe under departement</th>
</tr>
</thead>

<tbody>
<c:forEach begin="1947" end="2011" step="1" var="i">
<tr>
<th>${i}</th>
<td>${data[i][0]}</td>
<td>${data[i][10]}</td>
<td>${data[i][20]}</td>
</tr>
</c:forEach>

</tbody>

</table>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
