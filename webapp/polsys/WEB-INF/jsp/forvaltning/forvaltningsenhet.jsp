<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over forvaltningsenheter.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Forvaltningsenheter - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Public administration units - State Administration Database" />
    <c:param name="beskrivelseNo" value="Finn antall forvaltningsenheter fordelt på tilknytningsformer." />
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

<h1>${en ? "Number of public administration units distributed on affiliation" : "Antall forvaltningsenheter fordelt på tilknytningsformer"}</h1>

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
For instance are the 90 single-embassies subordinated Ministry of Foreign Affairs
grouped as one unit. Bodies with same tasks in integrated civil service organization
(local level) are treated the same way (for instance County Employment Office).
In the counting here - groups are counted as one unit. Groups within integrated
civil service organization (local level), are not counted here.
</p>
<p>
The counting therefore include all national bodies, central units within Government
Services and groups of bodies. Local level units and bodies part of group subordinated ministry are not  counted here.
</p>
</c:if>

<h3>${en ? "Form of affiliations:" : "Tilknytningsformer:"}</h3>

<form action="<p:url value="/data/antallforvaltningsenhet" />" method="get">

<ul class="plain">
<c:forEach items="${tilknytningsformer}" var="t">
<c:if test="${t.kode >= 20}">
<li><input type="checkbox" name="t" value="${t.kode}" id="t${t.kode}" /> <label for="t${t.kode}">${fn:escapeXml(t.tekstFlertall)}</label></li>
</c:if>
</c:forEach>
</ul>

<h3>${en ? "Start- and end year:" : "Start- og sluttår:"}</h3>

<p>
<select size="1" name="fra">
<c:forEach begin="1947" end="${sistOppdatertDato.aar}" step="1" var="i">
<option value="${i}" ${i eq 1947 ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

<select size="1" name="til">
<c:forEach begin="1947" end="${sistOppdatertDato.aar}" step="1" var="i">
<option value="${i}" ${i eq sistOppdatertDato.aar ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

<input type="submit" value="OK" />
</p>

<h3>${en ? "Day and month:" : "Dag og måned:"}</h3>
<p>
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>

<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>
</p>

</form>

<c:if test="${no}">
<p>Data er oppdatert per ${sistOppdatertDato}. Hvis du vil ha et annet opptellingstidspunkt (dag eller måned) på året, kan dette endres her.</p>
</c:if>

<c:if test="${en}">
<p>Data is updated per ${sistOppdatertDato}. If you want another counting-date (day or month) in the year that differs from this date – change it here.</p>
</c:if>

</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
