<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over lovdata til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Lovdata - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Laws - State Administration Database" />
    <c:param name="beskrivelseNo" value="Lover relatert til ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Laws related to ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="lovdata" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}"><p>Siden viser lover som er relatert til denne enheten.</p></c:if>
<c:if test="${en}"><p>This page shows laws related to this unit.</p></c:if>

<c:if test="${empty lovdata}">
<p><em>${en ? "No laws where found for this unit." : "Ingen lover er registrert for denne enheten."}</em></p>
</c:if>

<c:if test="${!(empty lovdata)}">
<h3>${en ? "Laws" : "Lover"}</h3>
<ul>
<c:forEach items="${lovdata}" var="l">
<li>
<c:if test="${l.url != null}"><a href="${fn:escapeXml(l.url)}" target="_blank">${fn:escapeXml(l.navn)}</a></c:if>
<c:if test="${l.url == null}">${fn:escapeXml(l.navn)}</c:if>
</li>
</c:forEach>
</ul>
</c:if>

</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
