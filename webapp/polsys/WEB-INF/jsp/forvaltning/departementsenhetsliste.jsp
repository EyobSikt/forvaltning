<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall departementsenhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Interne departementsenheter per ${brukerdato} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Bodies within ministries per ${brukerdato} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Interne departementsenheter fordelt på administrative nivåer og departement - per ${brukerdato}." />
    <c:param name="beskrivelseEn" value="Public administration units within ministries distributed on ministries - per ${brukerdato}." />
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
> Departementsenheter
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Public administration units
</c:if>
</div>

<h1>${en ? "Public administration units within ministries distributed on ministries - per" : "Interne departementsenheter fordelt på administrative nivåer og departement - per"} ${brukerdato}</h1>
    <iframe id="txtArea1" style="display:none"></iframe>
    <c:if test="${fn:escapeXml(nivaaNavn[nivaaer[i]].tekstFlertall).equals('Avdelinger')}">   <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="http://www.nsd.uib.no/common/polsys/img/excel.png"/>" title="Eksport resultatet til Excel"  />
    </a></c:if>
    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/img/excel.png"/>" title="Eksport resultatet til Excel"  />
    </a>
    <c:forEach begin="0" end="${fn:length(nivaaer) - 1}" step="1" var="i">
 <table id="excelTable" class="text">
<caption>${fn:escapeXml(nivaaNavn[nivaaer[i]].tekstFlertall)}</caption>
<thead>
<tr>
<th></th>
<th></th>
<th>${en ? "Name" : "Navn"}</th>
<th>${en ? "Ministry" : "Departement"}</th>
</tr>
</thead>

<c:set var="depteller" value="0" />
<c:set var="depidnum" value="0" />
<c:set var="enhetteller" value="0" />

<tbody>
<c:set var="j" value="0" />
<c:forEach items="${depenheter[i]}" var="e">
<c:set var="j" value="${j + 1}" />
<c:if test="${!(e.overordnetEnhet.idnum eq depidnum)}">
<c:set var="depteller" value="${depteller + 1}" />
<c:set var="depidnum" value="${e.overordnetEnhet.idnum}" />
<c:set var="enhetteller" value="0" />
</c:if>
<c:set var="enhetteller" value="${enhetteller + 1}" />
<tr${depteller % 2 == 0 ? ' class="odd"' : ''}>
<td>${j}</td>
<td>${enhetteller}</td>
<td><a href="<p:url value="/data/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></td>
<td>${fn:escapeXml(e.overordnetDepartement.langtNavn)}</td>
<%-- <td>${fn:escapeXml(e.overordnetEnhet.langtNavn)}</td>--%>
</tr>
</c:forEach>
<c:if test="${empty depenheter[i]}">
${en ? '<tr><td colspan="3"><em>No units</em></td></tr>' : '<tr><td colspan="3"><em>Ingen enheter</em></td></tr>'}
</c:if>
</tbody>

</table>

</c:forEach>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
