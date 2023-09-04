<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall forvaltningsenheter.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Antall forvaltningsenheter - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Number of public administration units - State Administration Database" />
    <c:param name="beskrivelseNo" value="Antall forvaltningsenheter fordelt på tilknytningsform." />
    <c:param name="beskrivelseEn" value="Number of public administration units distributed on affiliation." />
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
> Forvaltningsenheter
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Public administration units
</c:if>
</div>

<h1>${en ? "Number of public administration units distributed on affiliation" : "Antall forvaltningsenheter fordelt på tilknytningsform"}</h1>

<c:if test="${no}">
<h3>Om tellemåten:</h3>
<p>
I Forvaltningsdatabasen er forvaltningsorgan med like oppgaver gruppert sammen.
F.eks. er de ca 90 enkelt-ambassadene som rapporterer til Utenriksdepartementet
gruppert som en enhet. Organer med like oppgaver innenfor etater er gruppert på
samme måte (f.eks. Fylkesarbeidskontor). I tellingene her er slike grupper talt som en enhet.
</p>
<p>
Med i tallgrunnlaget er altså alle <strong>nasjonale organ</strong>, <strong>sentralorgan som inngår i
etater</strong> og <strong>grupper av organ under departement</strong>. Lokalnivå under etater
(grupper og enkeltenheter) samt enkeltenheter som inngår i grupper er ikke talt med her.
</p>
</c:if>

<c:if test="${en}">
<h3>Method of counting:</h3>
<p>
In the State Administration Database bodies with same tasks are grouped together.
For instance are the 90 single-embassies subordinated Ministry of Foreign Affairs grouped as one unit.
Bodies with same tasks in integrated civil service organization (local level) are
treated the same way (for instance County Employment Office). In the counting here - groups
are counted as one unit. Groups within integrated civil service organization (local level), are not counted here.
</p>
<p>
The counting therefore include all national bodies, central units within Government
Services and groups of bodies. Local level units and bodies part of group subordinated ministry are not  counted here.
</p>
</c:if>


<%-- ================================ feilmeldinger ====================== --%>
<c:if test="${feilValg}">
    <c:if test="${no}">
        <p><strong>Vennligst velg minst en tilknytningsform.</strong></p>
    </c:if>
    <c:if test="${en}">
        <p><strong>Please choose at least one.</strong></p>
    </c:if>
</c:if>

<c:if test="${feilTid}">
    <c:if test="${no}">
    <p><strong>Vennligst oppgi start- og sluttår. Startår må være lik eller mindre enn sluttår.</strong></p>
    </c:if>
    <c:if test="${en}">
    <p><strong>Please choose start- and end-year. start-year must be less than or equal to end-year.</strong></p>
    </c:if>
</c:if>
<%-- ===================================================================== --%>



<%-- =================================== data ============================ --%>

<iframe id="txtArea1" style="display:none"></iframe>

<c:if test="${!(empty depenhet)}">
<table id="excelTable" class="zebra">
<caption>${en ? "Number of public administration units distributed on affiliation" : "Antall forvaltningsenheter fordelt på tilknytningsform"}
    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>

<thead>
<tr>
<th>${en ? "Date" : "Dato"}</th>
<c:set var="tparam" value="" />
<c:forEach items="${tilknytningsformer}" var="tilknytningsform">
<c:set var="tparam" value="${tparam}&t=${tilknytningsform}" />
<th>${fn:escapeXml(tilknytningsformerNavn[tilknytningsform].tekstFlertall)}</th>
</c:forEach>
<th>Sum</th>
</tr>
</thead>

<tbody>
<c:forEach items="${depenhet}" var="de">
<tr>
<td><fmt:formatDate value="${de.dato}" pattern="dd.MM.yyyy" /></td>
<fmt:formatDate value="${de.dato}" pattern="yyyy" var="y" />
<fmt:formatDate value="${de.dato}" pattern="M" var="m" />
<fmt:formatDate value="${de.dato}" pattern="d" var="d" />
<c:set var="t" value="0" />
<c:forEach items="${de.antall}" var="a">
<td><a href="<p:url value="/data/forvaltningsenhetsliste?y=${y}&m=${m}&d=${d}&t=${tilknytningsformer[t]}" />">${a}</a></td>
<c:set var="t" value="${t + 1}" />
</c:forEach>
<td><a href="<p:url value="/data/forvaltningsenhetsliste?y=${y}&m=${m}&d=${d}${tparam}" />">${de.sum}</a></td>
</c:forEach>
</tr>
</tbody>

</table>

<c:if test="${fn:length(chartUrl) <= 2000}">
<h3>${en ? "Chart" : "Graf"}</h3>
<img class="chart_img" src="${chartUrl}" alt="graf" />
</c:if>

</c:if>
<%-- ===================================================================== --%>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
