<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over departement.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Organisasjonsendring, ytre - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Organizational change, bodies outside ministries - State Administration Database" />
    <c:param name="beskrivelseNo" value="Organisasjonsendring fordelt på departement og tilknytingsform." />
    <c:param name="beskrivelseEn" value="Organizational change distributed on ministries and affiliations." />
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

<h1>${en ? "Organizational change distributed on ministries and affiliations" : "Organisasjonsendring fordelt på departement og tilknytingsform"}</h1>

<form action="<p:url value="/data/ytreendringsliste" />" method="get">

<h2>${en ? "(1) Choose ministries" : "(1) Velg departement"}</h2>

<select name="dep">
<option value="">${en ? "All ministries" : "Alle departement"}</option>
<c:forEach items="${departementer}" var="departement">
<option value="${departement.idnum}">${fn:escapeXml(departement)}</option>
</c:forEach>
</select>

<div class="column_wide">
<h2>${en ? "(2) Choose affiliations" : "(2) Velg tilknytningsformer"}</h2>

<ul class="plain">
<c:forEach items="${tilknytningsformer}" var="tilknytningsform">
<c:if test="${tilknytningsform.kode >= 20}">
<li><input type="checkbox" name="t" value="${tilknytningsform.kode}" id="t${tilknytningsform.kode}" checked="checked"> <label for="t${tilknytningsform.kode}">${fn:escapeXml(tilknytningsform.tekstFlertall)}</label></li>
</c:if>
</c:forEach>
</ul>
</div>

<div class="column_wide">
<h2>${en ? "and category" : "og hovedtype"}</h2>

<ul class="plain">
<c:forEach items="${grunnenheter}" var="grunnenhet">
<c:set var="avkrysset" value="${(grunnenhet.kode == 0 || grunnenhet.kode == 11 || grunnenhet.kode == 21) ? 'true' : 'false'}" />
<li><input type="checkbox" name="g" value="${grunnenhet.kode}" id="g${grunnenhet.kode}" ${avkrysset ? 'checked="checked"' : ''}> <label for="g${grunnenhet.kode}">${fn:escapeXml(grunnenhet.tekstFlertall)}</label></li>
</c:forEach>
</ul>
</div>

<h2 class="break">${en ? "(3) Choose organizational change" : "(3) Velg organisatoriske endringer"}</h2>

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


<div class="footnote">
<c:if test="${no}">
<h3>Merk:</h3>
<h4>Valg av hovedtype</h4>

<p>
Begrepet <em>Grupper</em> brukes som samlebetegnelse for organisasjoner med mer eller mindre likestilte oppgaver.
</p>
<p>
<em>Enheter som inngår i grupper</em> er de substansielle enhetene som inngår i gruppen.
</p>
<p>
Merk at alle grupper er registrert i databasen, men at enkeltenheter som
inngår i grupper under departement og under etat er kartlagt på to ulike måtar:
</p>

<ul>
<li>Detaljert:
Hver enkelt enhet vises i oversikten under sine respektive grupper/etater.
Endringshistorien for hver enkelt enhet er registrert for alle år.</li>
<li>Aggregert:
Her vises gruppene som aggregerte tall under sine respektive grupper/etater.
Detaljert endringshistorie er ikke kartlagt. Grupper registrert på denne måten
er særlig store og omfattende grupper som f.eks. likningskontor, postkontor o.l., samt
grupper med vanskelig tilgjengelig endringshistorie og dårligere kilder.
<strong>I uttaket på neste side vil ingen aggregerte grupper vises</strong>.</li>
</ul>

<table class="zebra text">
<caption>Eksempel</caption>
<thead>
<tr><th>Valg</th><th>Eksempel</th></tr>
</thead>
<tbody>
<tr><td>Gruppe under departement</td><td>Statlige høgskoler</td></tr>
<tr><td>Enhet som inngår i gruppe under departement</td><td>De enkelte høgskolene</td></tr>
<tr><td>Gruppe under etat</td><td>Fylkesarbeidskontora</td></tr>
<tr><td>Enheter som inngår i etat</td><td>De enkelte fylkesarbeidskontor</td></tr>
</tbody>
</table>

<h4>Lokale avdelinger/satellitter</h4>
<p>
I tillegg er lokale avdelinger/satellitter registrert i egen tabell.
Dette er enheter som hører inn under en hovedenhet, men som er
organisert som en avdeling lokalisert et annet sted. Endringshistorie er
ikke kartlagt, men informasjon om lokalisering og overordnet er registrert.
Eksempler på slike enheter kan være Avdeling Mo i Rana ved
Høgskolen i Bodø. Et annet eksempel er Medisinsk fødselsregister i
Bergen som ligger under Nasjonalt folkehelesinstitutt i Oslo.
</p>
</c:if>


<c:if test="${en}">
<h3>Remark:</h3>
<p>
Please notice that all groups are registered in the database but that the
specific organizations that are included in a group are mapped in two different ways:
</p>

<ul>
<li>Detailed:
Every single unit is shown in the list in their respective groups / integrated civil
service organization. Event history (change) is registered for every unit for all years.</li>
<li>Aggregated:
Groups are shown as aggregated numbers in the list below in their respective group / integrated
civil service organization. Detailed event history is not registered.
Groups registered in this way are usually large, complex groups like tax offices,
post offices etc. Also groups where event history is difficult accessible / sources are poor,
have been registered in this way.</li>
</ul>
</c:if>
</div>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
