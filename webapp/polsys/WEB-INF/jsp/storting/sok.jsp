<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>

<%-- 
 - Author(s): ET
 - Copyright NSD
 - Description: Søk etter enheter.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Storting" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Parliament Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Stortinget hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the Parliament Database at NSD." />
</c:import>

<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="<p:url value="/"/>">PolSys</a>
> Søk
</c:if>
<c:if test="${en}">
You are here:
<a href="<p:url value="/"/>">PolSys</a>
> Search
</c:if>
</div>
    <div>
        <c:if test="${no}"><a href="<m:url value="/en/person/sok/" />">View this page in English.</a></c:if>
        <c:if test="${en}"><a href="<m:url value="/person/sok/" />">View this page in Norwegian.</a></c:if>
    </div>
<h1>${en ? "Search norwegian politician by last name" : "Søk norske politiker i etternavn"}</h1>

<!--
<form action="" method="get">

<p>
<input type="text" value="${param.s}" maxlength="100" size="75" name="s" />
<input class="button" type="submit" value="${en ? "Search" : "Søk"}" />
</p>
</form>
-->

    <form   ACTION="<p:url value="/person/norskepolitikere/?navn=${param.navn} "/>" METHOD="GET">

 <span class="clearable">
    <input class="data_field" type="text"  value="${fn:escapeXml(param.navn)}" name="navn" id="querystring" />
    <span title="slett tekst" class="icon_clear" onclick="window.location='?navn=${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url)}'">X</span>
</span>

        <input name="start" type="hidden" value="0">

        <c:choose><c:when test="${param.rows !=null}"><input type="hidden" value="${param.rows}" name="rows"></c:when><c:otherwise><input type="hidden" value="50" name="rows"></c:otherwise></c:choose>
        <c:set var="facetlist" value="${paramValues.bs}"/>
        <c:forEach var="y" begin="0" end="${fn:length(facetlist)}"  >
            <c:if test="${facetlist[y] !=null && facetlist[y]!='' }">
                <input name="bs" type="hidden" value="${fn:escapeXml(facetlist[y])}">
            </c:if>
        </c:forEach>
        <c:set var="periodelist" value="${paramValues.periode}"/>
        <c:forEach var="y" begin="0" end="${fn:length(periodelist)}"  >
            <c:if test="${periodelist[y] !=null && periodelist[y]!='' }">
                <input name="periode" type="hidden" value="${fn:escapeXml(periodelist[y])}">
            </c:if>
        </c:forEach>
        <c:set var="aarlist" value="${paramValues.aar}"/>
        <c:forEach var="y" begin="0" end="${fn:length(aarlist)}"  >
            <c:if test="${aarlist[y] !=null && aarlist[y]!='' }">
                <input name="aar" type="hidden" value="${fn:escapeXml(aarlist[y])}">
            </c:if>
        </c:forEach>
        <input type="hidden" name="st" value = "${param.st}">
        <input type="hidden" name="sr" value = "${param.sr}">
        <input type="hidden" name="ss" value = "${param.ss}">
        <input type="hidden" name="start" value = "0">
        <input  type="submit" <c:if test="${no}">value="Søk"</c:if><c:if test="${en}">value="Search"</c:if>>
<c:if test="${no}"> <p class="hjelpetekst">Søk i etternavn</p></c:if>
<c:if test="${en}"> <p class="hjelpetekst">Search by last name</p></c:if>
    </form>



<%--
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
<a href="<p:url value="/forvaltning/enhet/${enhet.idnum}" />">
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
--%>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
