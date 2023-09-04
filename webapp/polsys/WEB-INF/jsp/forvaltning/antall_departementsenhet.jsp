<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall departementsenhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Antall interne departementsenheter - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Number of bodies within ministries - State Administration Database" />
    <c:param name="beskrivelseNo" value="Antall interne departementsenheter fordelt på administrative nivåer." />
    <c:param name="beskrivelseEn" value="Number of bodies within ministries distributed on administrative levels." />
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
> Departementsenheter
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Bodies within ministries
</c:if>
</div>

<h1>${en ? "Number of bodies within ministries distributed on administrative levels" : "Antall interne departementsenheter fordelt på administrative nivåer"}</h1>

<c:if test="${no}">
<h3>Om teljemåten:</h3>
<p>
Alle interne enheter på alle nivå som er listet opp i Statskalenderen og på departementene sine hjemmesider er registrert, og vil vises i oversikten her.
</p>
</c:if>

<c:if test="${en}">
<h3>Method of counting:</h3>
<p>
All internal units under ministries are registered and selected levels are listed here.
</p>
</c:if>

<%-- ================================ feilmeldinger ====================== --%>
<c:if test="${feilValg}">
    <c:if test="${no}">
        <p><strong>Vennligst velg minst ett nivå.</strong></p>
    </c:if>
    <c:if test="${en}">
        <p><strong>Please choose at least one.</strong></p>
    </c:if>
</c:if>

<c:if test="${feilTid}">
    <c:if test="${no}">
    <p><strong>Vennligst oppgi start- og sluttår. Startår må være lik eller mindre enn sluttår.</strong></p>
    </c:if>
    <c:if test="${en}">
    <p><strong>Please choose start- and end-year. start-year must be less than or equal to end-year.</strong></p>
    </c:if>
</c:if>
<%-- ===================================================================== --%>



<%-- =================================== data ============================ --%>
    <iframe id="txtArea1" style="display:none"></iframe>
    <c:if test="${!(empty depenhet)}">

<table id="excelTable" class="zebra">
<caption>${en ? "Number of bodies within ministries distributed on administrative levels" : "Antall interne departementsenheter fordelt på administrative nivåer"}
    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>
<thead>
<tr>
<th>${en ? "Date" : "Dato"}</th>
<c:set var="nparam" value="" />
<c:forEach items="${nivaaer}" var="nivaa">
<c:set var="nparam" value="${nparam}&n=${nivaa}" />
<th>${fn:escapeXml(nivaaNavn[nivaa].tekstFlertall)}</th>
</c:forEach>
<th>Sum</th>
</tr>
</thead>

<tbody>
<c:forEach items="${depenhet}" var="de">
<tr>
<td><fmt:formatDate value="${de.dato}" pattern="dd.MM.yyyy" /></td>
<fmt:formatDate value="${de.dato}" pattern="yyyy" var="y" />
<fmt:formatDate value="${de.dato}" pattern="M" var="m" />
<fmt:formatDate value="${de.dato}" pattern="d" var="d" />
<c:set var="n" value="0" />
<c:forEach items="${de.antall}" var="a">
<td><a href="<p:url value="/data/departementsenhetsliste?y=${y}&m=${m}&d=${d}&n=${nivaaer[n]}" />">${a}</a></td>
<c:set var="n" value="${n + 1}" />
</c:forEach>
<td><a href="<p:url value="/data/departementsenhetsliste?y=${y}&m=${m}&d=${d}${nparam}" />">${de.sum}</a></td>
</c:forEach>
</tr>
</tbody>

</table>

<c:if test="${fn:length(chartUrl) <= 2000}">
<h3>${en ? "Chart" : "Graf"}</h3>
<img class="chart_img" src="${chartUrl}" alt="graf" />
</c:if>

</c:if>
<%-- ===================================================================== --%>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
