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
> Oppgaveportefølje
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Tasks
</c:if>
</div>

<h1>${en ? "Number of units by task" : "Antall enheter klassifisert på oppgave"}</h1>

<c:if test="${no}"><p>Antall enheter kategorisert på oppgaveportefølje.</p></c:if>
<c:if test="${en}"><p>Number of units categorized by COFOG.</p></c:if>

<%-- ====================================== Dato ========================= --%>
<form action="" method="get">
<p>
${en ? "Year:" : "År:"}
<select size="1" name="y">
<c:forEach items="${alleAar}" var="i">
<option value="${i}" ${valgtAar eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

${en ? "Show:" : "Vis antall der oppgaven er:"}
<select size="1" name="vis">
<option value="1" ${param.vis eq 1 ? 'selected="selected"' : ''} >enten hovedoppgave eller bioppgave</option>
<option value="2" ${param.vis eq 2 ? 'selected="selected"' : ''} >kun hovedoppgave</option>
<option value="3" ${param.vis eq 3 ? 'selected="selected"' : ''} >kun bioppgave</option>
</select>

<input type="submit" value="OK" />
</p>
</form>
<%-- ===================================================================== --%>


<c:if test="${empty oppgaveEnheter}">
<p><em>${en ? "No units on selected year" : "Ingen enheter på valgt år"}.</em></p>
</c:if>


<%-- ===================================== Tabell ======================== --%>
    <iframe id="txtArea1" style="display:none"></iframe>
<c:if test="${!empty oppgaveEnheter}">
<table id="excelTable" class="zebra sortable">
<caption>
${en ? "Number of units by task" : "Antall eheter per oppgave"} i ${valgtAar},
der gitt oppgave er enheten sin 
<c:if test="${empty param.vis || param.vis eq 1}">hovedoppgave eller bioppgave</c:if>
<c:if test="${param.vis eq 2}">hovedoppgave</c:if>
<c:if test="${param.vis eq 3}">bioppgave</c:if>
    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>
<thead>
<tr>
<th>${en ? "Task" : "Oppgave"}</th>
<th>${en ? "Number of units" : "Antall enheter"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${oppgaveEnheter}" var="m">
<tr>
<th>${m.key}. ${fn:escapeXml(oppgaver[m.key])}</th>
<td><a href="<p:url value="/data/oppgave/${m.key}?y=${valgtAar}&vis=${param.vis}" />">${fn:length(m.value)}</a></td>

</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<%-- ===================================================================== --%>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
