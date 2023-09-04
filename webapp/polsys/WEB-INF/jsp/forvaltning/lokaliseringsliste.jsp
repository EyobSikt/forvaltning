<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser liste over ytre endringer.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Lokalisering utenfor Oslo - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Units outside Oslo - State Administration Database" />
    <c:param name="beskrivelseNo" value="Enheter lokalisert utenfor Oslo." />
    <c:param name="beskrivelseEn" value="Units located outside Oslo." />
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
> Lokalisering utenfor Oslo
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Units located outside Oslo
</c:if>
</div>

<h1>${en ? "New units located outside Oslo" : "Nye enheter lokalisert utenfor Oslo"}</h1>


<%-- ================================ feilmeldinger ====================== --%>
<c:if test="${feilValg1}">
    <c:if test="${no}"><p><strong>Vennligst velg et departement.</strong></p></c:if>
    <c:if test="${en}"><p><strong>Please choose a ministry.</strong></p></c:if>
</c:if>

<c:if test="${feilValg2}">
    <c:if test="${no}"><p><strong>Vennligst velg minst en tilknytningsform.</strong></p></c:if>
    <c:if test="${en}"><p><strong>Please choose at least one.</strong></p></c:if>
</c:if>

<c:if test="${feilValg3}">
    <c:if test="${no}"><p><strong>Vennligst velg minst en endring.</strong></p></c:if>
    <c:if test="${en}"><p><strong>Please choose at least one.</strong></p></c:if>
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
<c:if test="${!(empty enhetliste)}">

<h3>${en ? "Ministries:" : "Departement:"}</h3>
<c:if test="${departement == null}">${en ? "All ministries" : "Alle departementer"}</c:if>
<c:if test="${departement != null}">
<ul class="plain">
<li><a href="<p:url value="/data/enhet/${departement.idnum}" />">${fn:escapeXml(departement.langtNavn)}</a></li>
<c:forEach items="${departement.tidligereNavnListe}" var="navn">
<li>${fn:escapeXml(navn)}</li>
</c:forEach>
</ul>
</c:if>

<h3>${en ? "Period:" : "Tidsperiode:"} <fmt:formatDate value="${fraDato}" pattern="d.M.yyyy" /> - <fmt:formatDate value="${tilDato}" pattern="d.M.yyyy" /></h3>

<h3>${en ? "Total number of units" : "Antall enheter:"} ${fn:length(enhetliste)}</h3>

    <iframe id="txtArea1" style="display:none"></iframe>
<table id="excelTable" class="text zebra sortable">
<caption>${en ? "Units" : "Enheter"}
    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/polsys/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>
<thead>
<tr>
<th>${en ? "Date" : "Dato"}</th>
<th>${en ? "Unit" : "Enhet"}</th>
<th>${en ? "Located, code" : "Kommunenr"}</th>
<th>${en ? "Located, name" : "Kommune"}</th>
</tr>
</thead>
<tbody>
<c:forEach items="${enhetliste}" var="e">
<tr>
<td><fmt:formatDate value="${e.tidspunkt}" pattern="yyyy.MM.dd" /></td>
<td><a href="<p:url value="/data/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></td>
<td>${e.kommunenummer}</td>
<td>${fn:escapeXml(e.kommune.tekstEntall)}</td>
</tr>
</c:forEach>
</tbody>
</table>


</c:if>
<%-- ===================================================================== --%>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
