<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Søk etter enheter.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Søk på institusjonsnamn - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Search by name - State Administration Database" />
    <c:param name="beskrivelseNo" value="Søk på navn til enhetene i Forvaltningsdatabasen." />
    <c:param name="beskrivelseEn" value="Search the State Administration Database." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no/">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> Søk
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">NSAD</a>
> <a href="https://forvaltningsdatabasen.sikt.no/en/civilservice/administrationdatabase.html">Units</a>
> Search
</c:if>
</div>

<h1>${en ? "Search by name" : "Søk på navn til enhet"}</h1>

<c:if test="${no}"><p>Merk: Data er oppdatert per ${sistOppdatertDato}.</p></c:if>
<c:if test="${en}"><p>Please note: Data is updated per ${sistOppdatertDato}.</p></c:if>

<form action="" method="get">

<p>
<input type="text" value="${param.s}" maxlength="100" size="75" name="s" />
<input class="button" type="submit" value="${en ? "Search" : "Søk"}" />
</p>

</form>

<c:if test="${param.s != null}">
<h3>${en ? "Results" : "Søkeresultat"}</h3>
</c:if>

<c:if test="${param.s != null && empty enheter}">
<p><em>${en ? "No hits." : "Ingen treff."}</em></p>
</c:if>

<c:if test="${!(empty enheter)}">
<c:if test="${no}"><p>Søket ditt ga ${fn:length(enheter)} treff.</p></c:if>
<c:if test="${en}"><p>${fn:length(enheter)} ${fn:length(enheter) > 1 ? "hits" : "hit"}.</p></c:if>

<ol>
<c:forEach items="${enheter}" var="enhet">

<c:set var="i" value="${fn:indexOf(fn:toLowerCase(enhet.langtNavn), fn:toLowerCase(param.s))}" />
<li>
<a href="<p:url value="/data/enhet/${enhet.idnum}" />">
${fn:escapeXml(fn:substring(enhet.langtNavn, 0, i))}<c:if test="${i > -1}"><strong>${fn:escapeXml(fn:substring(enhet.langtNavn, i, i + fn:length(param.s)))}</strong>${fn:escapeXml(fn:substring(enhet.langtNavn, i + fn:length(param.s), -1))}</c:if>
</a>

<c:if test="${!(empty enhet.tidligereNavn)}">
<ul class="plain">
<c:forEach items="${enhet.tidligereNavn}" var="navn">
<c:set var="i" value="${fn:indexOf(fn:toLowerCase(navn), fn:toLowerCase(param.s))}" />
<li>
${fn:escapeXml(fn:substring(navn, 0, i))}<c:if test="${i > -1}"><strong>${fn:escapeXml(fn:substring(navn, i, i + fn:length(param.s)))}</strong>${fn:escapeXml(fn:substring(navn, i + fn:length(param.s), -1))}</c:if>
</li>
</c:forEach>
</ul>
</c:if>

</li>
</c:forEach>
</ol>

</c:if>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
