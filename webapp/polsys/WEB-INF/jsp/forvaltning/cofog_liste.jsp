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
    <c:param name="tittelNo" value="COFOG - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="COFOG - State Administration Database" />
    <c:param name="beskrivelseNo" value="Enheter kategorisert på COFOG i forvaltningsdatabasen." />
    <c:param name="beskrivelseEn" value="Units by COFOG in the State Administration Database." />
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
> COFOG
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> COFOG
</c:if>
</div>

<h1>${en ? "Number of units by COFOG" : "Antall enheter per COFOG"}</h1>

<c:if test="${no}"><p>Antall enheter kategorisert på COFOG.</p></c:if>
<c:if test="${en}"><p>Number of units categorized by COFOG.</p></c:if>

<%-- ====================================== Dato ========================= --%>
<h3>${en ? "Date:" : "Tidspunkt:"}</h3>
<form action="" method="get">
<p>
${en ? "Year:" : "År:"} <input type="text" value="${brukerdato.aar}" maxlength="4" size="6" name="y" />

${en ? "Month:" : "Måned:"}
<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}" ${brukerdato.maaned eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

${en ? "Day:" : "Dag:"}
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}" ${brukerdato.dag eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

<input type="submit" value="OK" />
</p>
</form>
<%-- ===================================================================== --%>


<c:if test="${empty enheter}">
<p><em>${en ? "No units on selected date" : "Ingen enheter på valgt dato"}.</em></p>
</c:if>


<%-- ===================================== Tabell ======================== --%>
    <iframe id="txtArea1" style="display:none"></iframe>
<c:if test="${!empty enheter}">
<table id="excelTable" class="zebra sortable">
<caption>${en ? "Number of units by COFOG" : "Antall eheter per COFOG"} per ${brukerdato}
    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="http://www.nsd.uib.no/common/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>
<thead>
<tr>
<th>${en ? "COFOG" : "COFOG"}</th>
<th>${en ? "Number of units" : "Antall enheter"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${enheter}" var="m">
<tr>
<th>${fn:escapeXml(cofog[m.key])}</th>
<td><a href="<p:url value="/data/cofog/${fn:escapeXml(m.key)}?y=${brukerdato.aar}&m=${brukerdato.maaned}&d=${brukerdato.dag}" />">${fn:length(m.value)}</a></td>

</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<%-- ===================================================================== --%>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
