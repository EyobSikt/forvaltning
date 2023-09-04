<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): Eyob
 - Copyright NSD
 - Description: Viser oversikt over tildelingsbrev til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Tildelingsbrev - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Allocation Reports - State Administration Database" />
    <c:param name="beskrivelseNo" value="Tildelingsbrev til ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Allocation Reports, ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="tildelingsbrev" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${empty overordnet}">
<c:if test="${no}"><p>Siden viser tildelingsbrev til denne enheten.</p></c:if>
<c:if test="${en}"><p>This page shows allocation reports for this unit.</p></c:if>
</c:if>

<c:if test="${!(empty overordnet)}">
<p>${en ? "This page shows allocation reports for" : "Siden viser tildelingsbrev for"} ${fn:escapeXml(overordnet.langtNavn)}.</p>
</c:if>

<c:if test="${empty tildelingsbrev}">
<p><em>${en ? "No allocation reports where found for this unit." : "Ingen tildelingsbrev er registrert for denne enheten."}</em></p>
</c:if>


<c:if test="${!(empty tildelingsbrev)}">
<h3>${en ? "Allocation reports" : "Tildelingsbrev"}</h3>

<ul>
<c:forEach items="${tildelingsbrev}" var="a">
<li>
       <%--
       <c:if test="${a.tildelingsbrev != null}"><a href="https://forvaltningsdatabasen.sikt.no/filer/tildelingsbrev/${fn:escapeXml(a.tildelingsbrev)}">${a.aar} ${en ? "Allocation report in Norwegian" : "Tildelingsbrev på norsk"}</a></c:if>
       <c:if test="${a.engelskTildelingsbrev != null}"> - <a href="https://forvaltningsdatabasen.sikt.no/filer/tildelingsbrev/${fn:escapeXml(a.engelskTildelingsbrev)}">${a.aar} ${en ? "Allocation report in English" : "Tildelingsbrev på engelsk"}</a></c:if>
       --%>
           <c:if test="${a.tildelingsbrev != null}">
               <a href="<c:url value="/filer/tildelingsbrev/${fn:escapeXml(a.tildelingsbrev)}" />">${a.aar} ${en ? "Allocation report in Norwegian" : "Tildelingsbrev på norsk"}</a>
           </c:if>
           <c:if test="${a.engelskTildelingsbrev != null}"> -
               <a href="<c:url value="/filer/tildelingsbrev/${fn:escapeXml(a.engelskTildelingsbrev)}" />">${a.aar} ${en ? "Allocation report in English" : "Tildelingsbrev på engelsk"}</a>
           </c:if>

</li>
</c:forEach>
</ul>
</c:if>

</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
