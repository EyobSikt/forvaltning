<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over årsmeldingene til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Instrukser - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Instructions - State Administration Database" />
    <c:param name="beskrivelseNo" value="Instrukser til ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Instructions, ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="instruks" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${empty overordnet}">
<c:if test="${no}"><p>Siden viser instrukser til denne enheten.</p></c:if>
<c:if test="${en}"><p>This page shows instructions for this unit.</p></c:if>
</c:if>

<c:if test="${!(empty overordnet)}">
<p>${en ? "This page shows annual reports for" : "Siden viser instrukser for"} ${fn:escapeXml(overordnet.langtNavn)}.</p>
</c:if>

<c:if test="${empty instrukser}">
<p><em>${en ? "No annual reports where found for this unit." : "Ingen instrukser er registrert for denne enheten."}</em></p>
</c:if>


<c:if test="${!(empty instrukser)}">
<h3>${en ? "Instructions" : "Instrukser"}</h3>

<ul>
<c:forEach items="${instrukser}" var="a">
<li>
<%--
<c:if test="${a.aarsmelding != null}"><a href="https://forvaltningsdatabasen.sikt.no/filer/aarsmeldinger/${fn:escapeXml(a.aarsmelding)}">${a.aar} ${en ? "Annual report in Norwegian" : "Årsmelding på norsk"}</a></c:if>
<c:if test="${a.engelskAarsmelding != null}"> - <a href="https://forvaltningsdatabasen.sikt.no/filer/aarsmeldinger/${fn:escapeXml(a.engelskAarsmelding)}">${a.aar} ${en ? "Annual report in English" : "Årsmelding på engelsk"}</a></c:if>
--%>
<c:if test="${a.instruks != null}">
    <a href="<c:url value="/filer/instrukser/${fn:escapeXml(a.instruks)}" />">${a.aar} ${en ? "Instruks in Norwegian" : "Instruks på norsk"}</a>
</c:if>
<c:if test="${a.engelskInstruks != null}"> -
 <a href="<c:url value="/filer/instrukser/${fn:escapeXml(a.engelskInstruks)}" />">${a.aar} ${en ? "Instructions in English" : "Instruks på engelsk"}</a>
</c:if>
</li>
</c:forEach>
</ul>
</c:if>

</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
