<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Enheter per fylke.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Fylker - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Counties - State Administration Database" />
    <c:param name="beskrivelseNo" value="Enheter per fylke i forvaltningsdatabasen." />
    <c:param name="beskrivelseEn" value="Units by counties in the State Administration Database." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<div id="main">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> Fylker
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Counties
</c:if>
</div>

<h1>${en ? "Number of units by counties" : "Antall enheter per fylke"}</h1>

<c:if test="${no}"><p>Antall enheter per fylke.</p></c:if>
<c:if test="${en}"><p>Number of units by county (Norwegian: fylke).</p></c:if>

<%-- ====================================== Sektor og dato =============== --%>
<h3>${en ? "Affiliation and date:" : "Type enheter og tidspunkt:"}</h3>
<form action="" method="get">
<p>
${en ? "Affiliations:" : "Type enheter"}
<select size="1" name="s">
<option value="1" ${param.s eq '1' ? 'selected="selected"' : ''} >${en ? "Public administration bodies" : "Forvaltningsorgan"}</option>
<option value="2" ${param.s eq '2' ? 'selected="selected"' : ''} >${en ? "Sate companies and foundations" : "Foretak, selskap, stiftelser"}</option>
<option value="3" ${param.s eq '3' ? 'selected="selected"' : ''} >${en ? "All units" : "Alle enheter"}</option>
</select>

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
<p><em>${en ? "No units on selected date and sector" : "Ingen enheter på valgt dato og sektor"}.</em></p>
</c:if>


<%-- ===================================== Tabell ======================== --%>
    <iframe id="txtArea1" style="display:none"></iframe>
<c:if test="${!empty enheter}">
<table id="excelTable" class="zebra sortable">
<caption>
    ${en ? "Number of" : "Antall"}
    <c:if test="${empty param.s || param.s eq '1'}">${en ? "public administration bodies" : "forvaltningsorgan"}</c:if>
    <c:if test="${param.s eq '2'}">${en ? "Sate companies and foundations" : "Foretak, selskap, stiftelser"}</c:if>
    <c:if test="${param.s eq '3'}">${en ? "units" : "enheter"}</c:if>
    ${en ? "by county" : "per fylke"}
    per ${brukerdato}
        <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/polsys/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>
<thead>
<tr>
<th></th>
<th></th>
<th colspan="3">${en ? "Number of units" : "Antall enheter"}</th>
<c:if test="${param.s == null || param.s eq '1'}">
<th></th><th></th>
</c:if>
</tr>
<tr>
<th>${en ? "Code" : "F.nr."}</th>
<th>${en ? "County" : "Fylke"}</th>
<th>${en ? "Number of units" : "Sum"}</th>
<th>${en ? "Units" : "Enheter"}</th>
<th>${en ? "Local units" : "Avdelinger"}</th>
<c:if test="${param.s == null || param.s eq '1'}">
<th>${en ? "Number of employees" : "Antall ansatte"}</th>
<th>${en ? "Full-time equivalent" : "Antall årsverk"}</th>
</c:if>
</tr>
</thead>

<tbody>
<c:forEach items="${enheter}" var="m">
<tr>
<td>${m.key}</td>
<td class="tdtext">${fn:escapeXml(fylker[m.key].tekstEntall)}</td>
<td><a href="<p:url value="/data/fylke/${fn:escapeXml(m.key)}?s=${fn:escapeXml(param.s)}&y=${brukerdato.aar}&m=${brukerdato.maaned}&d=${brukerdato.dag}" />">${fn:length(m.value) + fn:length(satellitter[m.key])}</a></td>
<td>${fn:length(m.value)}</td>
<td>${fn:length(satellitter[m.key])}</td>
<c:if test="${param.s == null || param.s eq '1'}">
<td>${ansatte[m.key].ansatte}</td>
<td><fmt:formatNumber value="${ansatte[m.key].aarsverk}" pattern="0.0" /></td>
</c:if>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<%-- ===================================================================== --%>


</div>


<div id="sidebar">
<c:if test="${no}">
<h3>Merk</h3>
<p>
Merk at oversiktene over lokale enheter i fylkene består av nasjonale enheter uten distriktsapparat samt hovedkontor i større etater.
Av etater som har et omfattende antall kontor etc. på lokalt plan er f.eks. verken lensmannskontor, skatt, prostier m.m. tatt med i oversikten.
Oversiktene gir likevel et godt inntak til informasjon om det regionale statsapparatet.
</p>
<p>
Data om ansatte baserer seg på informasjon fra Statens Sentrale Tjenestemannsregister (SST).
</p>
<p>
Heller ikke for selskap er datterselskap, sykehus i de regionale helseforetak etc.
med i oversiktene. Merk at for selskap og stiftelser foreligger det heller ikke data om ansatte.
</p>
</c:if>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
