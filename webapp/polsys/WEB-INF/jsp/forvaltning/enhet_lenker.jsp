<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over eksterne lenker til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Lenker - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Links - State Administration Database" />
    <c:param name="beskrivelseNo" value="Lenker relatert til ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Links related to ${fn:escapeXml(enhet.langtNavn)}" />
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
> Enhet #${enhet.idnum}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Unit #${enhet.idnum}
</c:if>
</div>

<h1>${fn:escapeXml(enhet.langtNavn)}</h1>

<%-- ======================================== Enhet-linker ==============  --%>
<c:import url="/WEB-INF/jspf/enhet_navigering.jsp">
    <c:param name="valgt" value="lenker" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}"><p>Siden viser lenker til eksterne ressurser som er relatert til denne enheten.</p></c:if>
<c:if test="${en}"><p>This page shows links related to this unit.</p></c:if>

<c:if test="${empty orgnr && !visEvallink && selskapsdbId == null && empty arkivenheter && empty dbhInstkode}">
<p><em>${en ? "No links for this unit." : "Ingen lenker for denne enheten."}</em></p>
</c:if>

<c:if test="${!(empty orgnr) || visEvallink }">

<h3>${en ? "External links" : "Lenker til eksterne ressurser"}</h3>

<ul>
<c:if test="${!(empty orgnr)}">
<li><a href="http://w2.brreg.no/enhet/sok/detalj.jsp?orgnr=${orgnr[0]}" target="_blank">${en ? "This unit in the Brønnøysund Register" : "Denne enheten i Enhetsregisteret fra Brønnøysundsregistrene"}</a></li>
</c:if>

<c:if test="${visEvallink}">
<li><a href="http://evalueringsportalen.no/etat/${enhet.idnum}" target="_blank">${en ? "This unit in evalueringsportalen.no" : "Denne enheten i Evalueringsportalen"}</a></li>
</c:if>
</ul>
</c:if>

<c:if test="${ selskapsdbId != null || !(empty dbhInstkode)}">

<h3>${en ? "Intern links" : "Lenker til interne ressurser"}</h3>
<ul>
<c:if test="${!(empty dbhInstkode)}">
<c:forEach items="${dbhInstkode}" var="a">
     <li><a href="http://dbh.nsd.uib.no/nokkeltall/nokkeltall_htmlrapport.action?undermeny=nokkeltall_inst&ValgtinstDetail=${a.dbh_instkode}&valgtArstall=${a.aar}" target="_blank"> Denne enheten i NSD sin DBH database</a></li>
</c:forEach>
</c:if>
<c:if test="${selskapsdbId != null}">
 <li><a href="http://dimp.nsd.uib.no/selskap/eierinstitusjon.do?id=${selskapsdbId}" target="_blank">${en ? "This unit in NSDs Company database" : "Denne enheten i NSD sin Selskapsdatabase"}</a></li>
</c:if>
</ul>

    </c:if>

<c:if test="${!(empty arkivenheter)}">
<h4>Link til arkivportalen.no (forvaltningsområde)</h4>

<c:forEach items="${arkivenheter}" var="a">
<p><a href="${fn:escapeXml(a.url)}" target="_blank">${fn:escapeXml(a.navn)}</a> ${fn:escapeXml(a.tidsrom)} (${fn:escapeXml(a.forvaltningsomrade)})</p>
</c:forEach>
</c:if>



</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
