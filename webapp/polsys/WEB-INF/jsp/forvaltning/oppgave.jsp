<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Enheter per COFOF.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Oppgaver - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Tasks - State Administration Database" />
    <c:param name="beskrivelseNo" value="Enheter kategorisert på oppgave i forvaltningsdatabasen." />
    <c:param name="beskrivelseEn" value="Units by task in the State Administration Database." />
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
> <a href="<p:url value="/data/oppgave" />">Oppgaveportefølje</a>
> ${kode}. ${fn:escapeXml(oppgaver[kode])}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> <a href="<p:url value="/data/oppgave" />">Tasks</a>
> ${kode}. ${fn:escapeXml(oppgaver[kode])}
</c:if>
</div>

<h1>${en ? "Units by task:" : "Enheter per oppgave:"} ${fn:escapeXml(oppgaver[kode])}</h1>

<c:if test="${no}"><p>Siden viser enheter kategorisert på oppgaven: "${fn:escapeXml(oppgaver[kode])}."</p></c:if>
<c:if test="${en}"><p>Units categorized by task: ${kode}. ${fn:escapeXml(oppgaver[kode])}</p></c:if>

<%-- ====================================== Dato ========================= --%>
<form action="" method="get">
<p>
${en ? "Year:" : "År:"}
<select size="1" name="y">
<c:forEach items="${alleAar}" var="i">
<option value="${i}" ${valgtAar eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

${en ? "Show:" : "Vis enheter der"} "${fn:escapeXml(oppgaver[kode])}" er enheten sin: 
<select size="1" name="vis">
<option value="1" ${param.vis eq 1 ? 'selected="selected"' : ''} >hovedoppgave eller bioppgave</option>
<option value="2" ${param.vis eq 2 ? 'selected="selected"' : ''} >hovedoppgave</option>
<option value="3" ${param.vis eq 3 ? 'selected="selected"' : ''} >bioppgave</option>
</select>

<input type="submit" value="OK" />
</p>
</form>
<%-- ===================================================================== --%>


<c:if test="${empty oppgaveEnheter[kode]}">
<p><em>${en ? "No matching units" : "Ingen enheter for gitt valg"}.</em></p>
</c:if>


<%-- ===================================== Tabell ======================== --%>
<c:if test="${!empty oppgaveEnheter[kode]}">
<table class="text zebra sortable">
<caption>
${en ? "Units by task" : "Eheter"} i ${valgtAar},
der "${fn:escapeXml(oppgaver[kode])}" er enheten sin 
<c:if test="${empty param.vis || param.vis eq 1}">hovedoppgave eller bioppgave.</c:if>
<c:if test="${param.vis eq 2}">hovedoppgave.</c:if>
<c:if test="${param.vis eq 3}">bioppgave.</c:if>
Antall: ${fn:length(oppgaveEnheter[kode])}
</caption>
<thead>
<tr>
<th>${en ? "Unit" : "Enhet"}</th>
<th>${en ? "Main task" : "Hovedoppgave"}</th>
<th>${en ? "Side task" : "Bioppgave"}</th>
<th>${en ? "Side task" : "Bioppgave"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${oppgaveEnheter[kode]}" var="oe">
<tr>
<td><a href="<p:url value="/data/enhet/${oe.enhet.idnum}" />">${fn:escapeXml(oe.enhet.langtNavn)}</a></td>
<td>${fn:escapeXml(oppgaver[oe.hovedoppgave])}</td>
<td>${fn:escapeXml(oppgaver[oe.bioppgave1])}</td>
<td>${fn:escapeXml(oppgaver[oe.bioppgave2])}</td>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<%-- ===================================================================== --%>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
