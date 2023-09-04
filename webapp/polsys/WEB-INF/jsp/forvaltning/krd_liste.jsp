<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall departementsenhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="KRD, utflytting - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="KRD, utflytting - State Administration Database" />
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
> KRD
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> KRD
</c:if>
</div>

<h1>KRD utflyttinger</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>

<table class="text zebra sortable">
<caption>Enheter</caption>

<thead>
<tr>
<th>"KRD-ID"</th>
<th>NSD IDNUM</th>
<th>Virksomhet</th>
<th>Antall stillinger ved etablering</th>
<th>Kompetansenivå</th>
<th>År</th>
<th>Kommune</th>
<th>Selvstendig/Underordnet</th>
<th>Etablering</th>
<th>Type</th>
</tr>
</thead>

<tbody>
<c:forEach items="${enheter}" var="e">
<tr>
<td><a href="<p:url value="/data/krd/${e.krdid}" />">${e.krdid}</a></td>
<td>
    <c:if test="${e.idnum != null}">
        <a href="<p:url value="/data/enhet/${e.idnum}" />">${e.idnum}</a>
    </c:if>
</td>
<td>${fn:escapeXml(e.virksomhet)}</td>
<td>${e.stillinger}</td>
<td>${fn:escapeXml(e.kompetansenivaa)}</td>
<td>${e.aar}</td>
<td>${fn:escapeXml(e.kommune.tekstEntall)}</td>
<td>${e.selvstendig ? "Selvstendig" : "Underordnet"}</td>
<td>${fn:escapeXml(e.etablering)}</td>
<td>${fn:escapeXml(e.type)}</td>
</tr>
</c:forEach>
</tbody>

</table>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
