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

<h2>${fn:escapeXml(enhet.virksomhet)}</h2>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>

<div class="event"><span class="eventl">"KRD-ID":</span> <span class="eventv">${enhet.krdid}</span></div>

<div class="event"><span class="eventl">NSD IDNUM:</span>
<span class="eventv">
    <c:if test="${enhet.idnum != null}">
        <a href="<p:url value="/data/enhet/${enhet.idnum}" />">${enhet.idnum}</a>
    </c:if>
</span></div>

<div class="event"><span class="eventl">Virksomhet:</span> <span class="eventv">${fn:escapeXml(enhet.virksomhet)}</span></div>
<div class="event"><span class="eventl">Antall stillinger ved etablering:</span> <span class="eventv">${enhet.stillinger}</span></div>
<div class="event"><span class="eventl">Kompetansenivå:</span> <span class="eventv">${fn:escapeXml(enhet.kompetansenivaa)}</span></div>
<div class="event"><span class="eventl">År:</span> <span class="eventv">${enhet.aar}</span></div>
<div class="event"><span class="eventl">Kommune:</span> <span class="eventv">${enhet.kommune.kode} ${fn:escapeXml(enhet.kommune.tekstEntall)}</span></div>
<div class="event"><span class="eventl">Selvstendig/Underordnet:</span> <span class="eventv">${enhet.selvstendig ? "Selvstendig" : "Underordnet"}</span></div>
<div class="event"><span class="eventl">Etablering:</span> <span class="eventv">${fn:escapeXml(enhet.etablering)}</span></div>
<div class="event"><span class="eventl">Type:</span> <span class="eventv">${fn:escapeXml(enhet.type)}</span></div>

<div class="event">
<div class="eventl">Kommentar:</div>
<div class="eventv">
    <p>${fn:replace(fn:escapeXml(enhet.kommentar), newLineChar, "<br />")}</p>
</div>
</div>


<table class="zebra">
<caption>Antall ansatte per år</caption>

<thead>
<tr><th>År</th><th>Antall ansatte</th></tr>
</thead>

<tbody>
<c:forEach items="${ansatte}" var="a">
<tr><td>${a[0]}</td><td>${a[1]}</td></tr>
</c:forEach>
</tbody>

</table>

</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
