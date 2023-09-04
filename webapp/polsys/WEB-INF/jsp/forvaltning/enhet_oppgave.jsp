<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over oppgaver til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Oppgave - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Tasks - State Administration Database" />
    <c:param name="beskrivelseNo" value="Oppgaveportefølje til ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Tasks registered for ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="oppgave" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}">
<p>Denne siden viser enheten sin oppgaveportefølje for de år der dette er registrert.</p>
</c:if>
<c:if test="${en}">
<p>This page shows tasks registered for this unit.</p>
</c:if>

<c:if test="${empty oppgaver}">
<p><em>${en ? "No tasks for this unit." : "Ingen oppgaver er registrert for denne enheten."}</em></p>
</c:if>

<c:if test="${!(empty oppgaver)}">
<table class="text zebra sortable">
<caption>${en ? "Tasks by year" : "Oppgaver per år"}</caption>
<thead>
<tr>
<th>${en ? "Year" : "År"}</th>
<th>${en ? "Main task" : "Hovedoppgave"}</th>
<th>${en ? "Side task" : "Bioppgave"}</th>
<th>${en ? "Side task" : "Bioppgave"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${oppgaver}" var="oe">
<tr>
<td>${oe.aar}</td>
<td>${fn:escapeXml(oppgavenavn[oe.hovedoppgave])}</td>
<td>${fn:escapeXml(oppgavenavn[oe.bioppgave1])}</td>
<td>${fn:escapeXml(oppgavenavn[oe.bioppgave2])}</td>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>

</div>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
