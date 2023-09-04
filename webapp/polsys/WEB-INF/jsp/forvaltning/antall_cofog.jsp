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
    <c:param name="tittelNo" value="Antall cofog - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Antall cofog - State Administration Database" />
    <c:param name="beskrivelseNo" value="Antall cofog." />
    <c:param name="beskrivelseEn" value="Antall cofog." />
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
> Antall COFOG
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Antall COFOG
</c:if>
</div>

<h1>Antall statlige selskap klassifisert på COFOG per år</h1>


<table class="zebra">
<caption>Antall enheter per COFOG. Antall er per 1.1. årene 1947-2012</caption>

<thead>
<tr>
<th>År</th>
<th>01</th>
<th>02</th>
<th>03</th>
<th>04</th>
<th>05</th>
<th>06</th>
<th>07</th>
<th>08</th>
<th>09</th>
<th>10</th>
<th>andre (1,2,3,5)</th>
<th>øk. (4)</th>
<th>velferd (6,7,8,9,10)</th>
</tr>
</thead>

<tbody>
<c:forEach begin="1947" end="2011" step="1" var="i">
<tr>
<th>${i}</th>
<td>${data[i][1]}</td>
<td>${data[i][2]}</td>
<td>${data[i][3]}</td>
<td>${data[i][4]}</td>
<td>${data[i][5]}</td>
<td>${data[i][6]}</td>
<td>${data[i][7]}</td>
<td>${data[i][8]}</td>
<td>${data[i][9]}</td>
<td>${data[i][10]}</td>
<td>${data[i][11]}</td>
<td>${data[i][4]}</td>
<td>${data[i][12]}</td>
</tr>
</c:forEach>

</tbody>

</table>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
