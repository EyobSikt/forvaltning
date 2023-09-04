<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser søkeside for interne ogranisasjonsendringer.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Organisasjonsendring, intern - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Organizational change, bodies within ministries - State Administration Database" />
    <c:param name="beskrivelseNo" value="Organisasjonsendring fordelt på departement og administrativt nivå." />
    <c:param name="beskrivelseEn" value="Organizational change distributed on ministries and administrative levels." />
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
> Organisasjonsendringer
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Organizational change
</c:if>
</div>

<h1>${en ? "Organizational change distributed on ministries and administrative levels" : "Organisasjonsendring fordelt på departement og administrativt nivå"}</h1>

<form action="<p:url value="/data/internendringsliste" />" method="get">

<h2>${en ? "(1) Choose ministries" : "(1) Velg departement"}</h2>

<select name="dep">
<option value="">${en ? "All ministries" : "Alle departement"}</option>
<c:forEach items="${departementer}" var="departement">
<option value="${departement.idnum}">${fn:escapeXml(departement)}</option>
</c:forEach>
</select>

<h2>${en ? "(2) Choose adminitrative levels" : "(2) Velg administrative nivåer"}</h2>

<ul class="plain">
<c:forEach items="${nivaaer}" var="nivaa">
<li><input type="checkbox" name="n" value="${nivaa.kode}" id="n${nivaa.kode}" checked="checked"> <label for="n${nivaa.kode}">${fn:escapeXml(nivaa.tekstFlertall)}</label></li>
</c:forEach>
</ul>


<h2>${en ? "(3) Choose organizational changes" : "(3) Velg organisatoriske endringer"}</h2>

<div class="column_thin">
<h3>${en ? '"New-" changes' : '"Nye-" endringer'}</h3>
<ul class="plain">
<c:forEach items="${endringskoder}" var="endring">
<c:if test="${endring.kode / 100 < 2}">
<li><input type="checkbox" name="e" value="${endring.kode}" id="e${endring.kode}" ${endring.kode == 101 ? 'checked="checked"' : ''}>
<label for="e${endring.kode}">${fn:escapeXml(endring.tekstFlertall)}</label></li>
</c:if>
</c:forEach>
</ul>
</div>

<div class="column">
<h3>${en ? '"Maintenance-" changes' : '"Bestående-" endringer'}</h3>
<ul class="plain">
<c:forEach items="${endringskoder}" var="endring">
<c:if test="${endring.kode / 100 >= 2 && endring.kode / 100 < 3}">
<li><input type="checkbox" name="e" value="${endring.kode}" id="e${endring.kode}"> <label for="e${endring.kode}">${fn:escapeXml(endring.tekstFlertall)}</label></li>
</c:if>
</c:forEach>
</ul>
</div>

<div class="column_thin">
<h3>${en ? '"Terminate-" changes' : '"Nedlagte-" endringer'}</h3>
<ul class="plain">
<c:forEach items="${endringskoder}" var="endring">
<c:if test="${endring.kode / 100 >= 3}">
<li><input type="checkbox" name="e" value="${endring.kode}" id="e${endring.kode}"> <label for="e${endring.kode}">${fn:escapeXml(endring.tekstFlertall)}</label></li>
</c:if>
</c:forEach>
</ul>
</div>


<h2 class="break">${en ? "(4) Choose time period and submit search" : "(4) Velg tidsperiode og utfør søket"}</h2>

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

<input type="submit" value="${en ? "Submit" : "Utfør søket"}" />

</form>


<c:if test="${no}">
<h3>Om tellemåten:</h3>
<p>
Alle interne enheter på alle nivåer listet opp i Statskalenderen og på departementene sine hjemmesider er registrert og vil vises i oversiktene her.
</p>
</c:if>

<c:if test="${en}">
<h3>Method of counting:</h3>
<p>
All internal units under ministries are registered and can be listed here.
</p>
</c:if>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
