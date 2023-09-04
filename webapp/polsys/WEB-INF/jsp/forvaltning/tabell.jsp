<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser tabell med valgt data.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Tabell - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Data - State Administration Database" />
    <c:param name="beskrivelseNo" value="Tabell med data fra Forvaltningsdatabasen." />
    <c:param name="beskrivelseEn" value="Data from The Norwegian State Administration Database." />
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
> Tabell
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Data
</c:if>
</div>

<h1>${en ? "Data on units from the database" : "Tabell med data om enheter fra Forvaltningsdatabasen"}</h1>

    <iframe id="txtArea1" style="display:none"></iframe>
<table id="excelTable" class="zebra sortable">
<caption>${en ? "Data per" : "Data per"} ${brukerdato}, ${en ? "number of units: " : "antall enheter: "} ${fn:length(enheter)}
   <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>
<thead>
<tr>
<th>idnum</th>
<c:if test="${variabler['1']}"><th>${en ? "long_name" : "langt_navn"}</th></c:if>
<c:if test="${variabler['2']}"><th>${en ? "short_name" : "kort_navn"}</th></c:if>
<c:if test="${variabler['3']}"><th>${en ? "affiliation" : "tilknytningsform"}</th></c:if>
<c:if test="${variabler['4']}"><th>${en ? "type" : "grunnenhet"}</th></c:if>
<c:if test="${variabler['5']}"><th>${en ? "level" : "nivaa"}</th></c:if>
<c:if test="${variabler['6']}"><th>${en ? "cofog" : "cofog"}</th></c:if>
<c:if test="${variabler['7']}"><th>${en ? "sup_ministry_idnum" : "overordnet_dep_idnum"}</th></c:if>
<c:if test="${variabler['8']}"><th>${en ? "sup_ministry_long" : "overordnet_dep_langt"}</th></c:if>
<c:if test="${variabler['9']}"><th>${en ? "sup_ministry_short" : "overordnet_dep_kort"}</th></c:if>
<c:if test="${variabler['10']}"><th>${en ? "change_102" : "endring_102"}</th></c:if>
<c:if test="${variabler['11']}"><th>${en ? "change_104" : "endring_104"}</th></c:if>
<c:if test="${variabler['12']}"><th>${en ? "change_106" : "endring_106"}</th></c:if>
<c:if test="${variabler['13']}"><th>${en ? "change_111" : "endring_111"}</th></c:if>

    <c:if test="${variabler['37']}"><th>${en ? "change_101" : "endring_101"}</th></c:if>
    <c:if test="${variabler['38']}"><th>${en ? "change_112" : "endring_112"}</th></c:if>

<c:if test="${variabler['14']}"><th>${en ? "change_202" : "endring_202"}</th></c:if>
<c:if test="${variabler['15']}"><th>${en ? "change_203" : "endring_203"}</th></c:if>
<c:if test="${variabler['16']}"><th>${en ? "change_211" : "endring_211"}</th></c:if>


    <c:if test="${variabler['39']}"><th>${en ? "change_207" : "endring_207"}</th></c:if>
    <c:if test="${variabler['42']}"><th>${en ? "change_209" : "endring_209"}</th></c:if>
    <c:if test="${variabler['43']}"><th>${en ? "change_213" : "endring_213"}</th></c:if>
    <c:if test="${variabler['44']}"><th>${en ? "change_221" : "endring_221"}</th></c:if>
    <c:if test="${variabler['45']}"><th>${en ? "change_222" : "endring_222"}</th></c:if>
    <c:if test="${variabler['46']}"><th>${en ? "change_223" : "endring_223"}</th></c:if>
    <c:if test="${variabler['47']}"><th>${en ? "change_291" : "endring_291"}</th></c:if>
    <c:if test="${variabler['48']}"><th>${en ? "change_292" : "endring_292"}</th></c:if>
    <c:if test="${variabler['49']}"><th>${en ? "change_303" : "endring_303"}</th></c:if>
    <c:if test="${variabler['50']}"><th>${en ? "change_304" : "endring_304"}</th></c:if>
    <c:if test="${variabler['51']}"><th>${en ? "change_306" : "endring_306"}</th></c:if>
    <c:if test="${variabler['52']}"><th>${en ? "change_310" : "endring_310"}</th></c:if>
    <c:if test="${variabler['53']}"><th>${en ? "change_311" : "endring_311"}</th></c:if>
    <c:if test="${variabler['54']}"><th>${en ? "change_312" : "endring_312"}</th></c:if>

<c:if test="${variabler['17']}"><th>${en ? "change_agg" : "endring_agg"}</th></c:if>
<c:if test="${variabler['18']}"><th>${en ? "founded_year_unit" : "opprettet_aar_enhet"}</th></c:if>
<c:if test="${variabler['19']}"><th>${en ? "founded_year_pre" : "opprettet_aar_forl"}</th></c:if>
<c:if test="${variabler['20']}"><th>${en ? "sub_count" : "antall_under"}</th></c:if>
<c:if test="${variabler['21']}"><th>${en ? "sub_count_group" : "antall_under_gruppe"}</th></c:if>
<c:if test="${variabler['22']}"><th>${en ? "sub_count_sat" : "antall_sat"}</th></c:if>
<c:if test="${variabler['23']}"><th>${en ? "english_name" : "engelsk_navn"}</th></c:if>
<c:if test="${variabler['24']}"><th>${en ? "location" : "kommunenr"}</th></c:if>
<c:if test="${variabler['40']}"><th>${en ? "location name" : "kommune"}</th></c:if>
<c:if test="${variabler['41']}"><th>${en ? "Organization number" : "Org.nr."}</th></c:if>

<c:if test="${variabler['25']}"><th>${en ? "main_task" : "hovedoppgave"}</th></c:if>
<c:if test="${variabler['26']}"><th>${en ? "sec_task_1" : "bioppgave_1"}</th></c:if>
<c:if test="${variabler['27']}"><th>${en ? "sec_task_2" : "bioppgave_2"}</th></c:if>

<c:if test="${variabler['28']}"><th>${en ? "statres_name" : "statres_navn"}</th></c:if>
<c:if test="${variabler['29']}"><th>${en ? "production" : "egenprod"}</th></c:if>
<c:if test="${variabler['30']}"><th>${en ? "salaries" : "lonnskost"}</th></c:if>
<c:if test="${variabler['31']}"><th>${en ? "purchases" : "kjop"}</th></c:if>
<c:if test="${variabler['32']}"><th>${en ? "investments" : "investeringer"}</th></c:if>
<c:if test="${variabler['33']}"><th>${en ? "appropriations" : "overforinger"}</th></c:if>
<c:if test="${variabler['34']}"><th>${en ? "expenditure" : "utgifter"}</th></c:if>
<c:if test="${variabler['35']}"><th>${en ? "premium_spf" : "premie_sp"}</th></c:if>
<c:if test="${variabler['36']}"><th>${en ? "fte_statres" : "aarsverk_statres"}</th></c:if>

<c:forEach items="${sstvar}" var="sstv">
<c:forEach items="${kjonn}" var="k">
<c:forEach items="${lonnkat}" var="l">
<c:forEach items="${stilling}" var="s">
<c:set var="keyK" value="k${k}" />
<c:set var="keyL" value="l${l}" />
<c:set var="keyS" value="hd${s}" />
<c:if test="${sstVariabler[sstv] && sstVariabler[keyK] && sstVariabler[keyL] && sstVariabler[keyS]}">
<th>${sstv}-k${k}-l${l}-hd${s}</th>
</c:if>
</c:forEach>
</c:forEach>
</c:forEach>
</c:forEach>

</tr>
</thead>

<tbody>
<c:forEach items="${enheter}" var="e">
<tr>
<td>${e.idnum}</td>
<c:if test="${variabler['1']}"><td class="tdtext">${fn:escapeXml(e.langtNavn)}</td></c:if>
<c:if test="${variabler['2']}"><td class="tdtext">${fn:escapeXml(e.kortNavn)}</td></c:if>
<c:if test="${variabler['3']}"><td>${e.tilknytningsform}</td></c:if>
<c:if test="${variabler['4']}"><td>${e.grunnenhet}</td></c:if>
<c:if test="${variabler['5']}"><td>${e.nivaa}</td></c:if>
<c:if test="${variabler['6']}"><td>${fn:escapeXml(e.cofog)}</td></c:if>
<c:if test="${variabler['7']}"><td>${e.overordnetDepartement.idnum}</td></c:if>
<c:if test="${variabler['8']}"><td class="tdtext">${fn:escapeXml(e.overordnetDepartement.langtNavn)}</td></c:if>
<c:if test="${variabler['9']}"><td class="tdtext">${fn:escapeXml(e.overordnetDepartement.kortNavn)}</td></c:if>
<c:if test="${variabler['10']}"><td>${e.variabler.endring102}</td></c:if>
<c:if test="${variabler['11']}"><td>${e.variabler.endring104}</td></c:if>
<c:if test="${variabler['12']}"><td>${e.variabler.endring106}</td></c:if>
<c:if test="${variabler['13']}"><td>${e.variabler.endring111}</td></c:if>
<c:if test="${variabler['14']}"><td>${e.variabler.endring202}</td></c:if>
<c:if test="${variabler['15']}"><td>${e.variabler.endring203}</td></c:if>
<c:if test="${variabler['16']}"><td>${e.variabler.endring211}</td></c:if>

    <c:if test="${variabler['37']}"><td>${e.variabler.endring101}</td></c:if>
    <c:if test="${variabler['38']}"><td>${e.variabler.endring112}</td></c:if>
    <c:if test="${variabler['39']}"><td>${e.variabler.endring207}</td></c:if>
    <c:if test="${variabler['42']}"><td>${e.variabler.endring209}</td></c:if>
    <c:if test="${variabler['43']}"><td>${e.variabler.endring213}</td></c:if>
    <c:if test="${variabler['44']}"><td>${e.variabler.endring221}</td></c:if>
    <c:if test="${variabler['45']}"><td>${e.variabler.endring222}</td></c:if>
    <c:if test="${variabler['46']}"><td>${e.variabler.endring223}</td></c:if>
    <c:if test="${variabler['47']}"><td>${e.variabler.endring291}</td></c:if>
    <c:if test="${variabler['48']}"><td>${e.variabler.endring292}</td></c:if>
    <c:if test="${variabler['49']}"><td>${e.variabler.endring303}</td></c:if>
    <c:if test="${variabler['50']}"><td>${e.variabler.endring304}</td></c:if>
    <c:if test="${variabler['51']}"><td>${e.variabler.endring306}</td></c:if>
    <c:if test="${variabler['52']}"><td>${e.variabler.endring310}</td></c:if>
    <c:if test="${variabler['53']}"><td>${e.variabler.endring311}</td></c:if>
    <c:if test="${variabler['54']}"><td>${e.variabler.endring312}</td></c:if>


<c:if test="${variabler['17']}"><td>${e.variabler.endringAgg}</td></c:if>
<c:if test="${variabler['18']}"><td><fmt:formatDate value="${e.tidspunkt}" pattern="yyyy" /></td></c:if>
<c:if test="${variabler['19']}"><td><fmt:formatDate value="${e.tidspunktOpprettetForloper}" pattern="yyyy" /></td></c:if>
<c:if test="${variabler['20']}"><td>${e.variabler.antallUnder}</td></c:if>
<c:if test="${variabler['21']}"><td>${e.variabler.antallUnderGruppe}</td></c:if>
<c:if test="${variabler['22']}"><td>${e.variabler.antallSat}</td></c:if>
<c:if test="${variabler['23']}"><td class="tdtext">${fn:escapeXml(e.engelskLangtNavn)}</td></c:if>
<c:if test="${variabler['24']}"><td>${e.kommunenummer}</td></c:if>

<c:if test="${variabler['40']}"><td>${kommuner[e.kommunenummer]}</td></c:if>
<c:if test="${variabler['41']}"><td>${orgnr[e.idnum]}</td></c:if>

<c:if test="${variabler['25']}"><td>${oppgaver[e.idnum].hovedoppgave}</td></c:if>
<c:if test="${variabler['26']}"><td>${oppgaver[e.idnum].bioppgave1}</td></c:if>
<c:if test="${variabler['27']}"><td>${oppgaver[e.idnum].bioppgave2}</td></c:if>

<c:if test="${variabler['28']}"><td class="tdtext">${fn:escapeXml(statres[e.idnum].statresNavn)}</td></c:if>
<c:if test="${variabler['29']}"><td>${statres[e.idnum].egenproduksjon}</td></c:if>
<c:if test="${variabler['30']}"><td>${statres[e.idnum].lonnskostEgenprod}</td></c:if>
<c:if test="${variabler['31']}"><td>${statres[e.idnum].varerTjenester}</td></c:if>
<c:if test="${variabler['32']}"><td>${statres[e.idnum].investeringer}</td></c:if>
<c:if test="${variabler['33']}"><td>${statres[e.idnum].overforinger}</td></c:if>
<c:if test="${variabler['34']}"><td>${statres[e.idnum].totaleUtgifter}</td></c:if>
<c:if test="${variabler['35']}"><td>${statres[e.idnum].premieSp}</td></c:if>
<c:if test="${variabler['36']}"><td>${statres[e.idnum].avtalteAarsverk}</td></c:if>

<c:forEach items="${sstvar}" var="sstv">
<c:forEach items="${kjonn}" var="k">
<c:forEach items="${lonnkat}" var="l">
<c:forEach items="${stilling}" var="s">
<c:set var="keyK" value="k${k}" />
<c:set var="keyL" value="l${l}" />
<c:set var="keyS" value="hd${s}" />
<c:set var="key" value="${keyK}-${keyL}-${keyS}" />
<c:if test="${sstVariabler[sstv] && sstVariabler[keyK] && sstVariabler[keyL] && sstVariabler[keyS]}">
<c:if test="${sstv eq 'ans'}"><td>${ansatte[key][e.idnum].ansatte}</td></c:if>
<c:if test="${sstv eq 'aar'}"><td><fmt:formatNumber value="${ansatte[key][e.idnum].aarsverk}" pattern="0.0" /></td></c:if>
<c:if test="${sstv eq 'ald'}">
   <c:set var="alder" value="${ansatte[key][e.idnum].alder / ansatte[key][e.idnum].ansatte}" />
   <td><fmt:formatNumber value="${alder}" pattern="0.0" /></td>
</c:if>
</c:if>
</c:forEach>
</c:forEach>
</c:forEach>
</c:forEach>


</tr>
</c:forEach>
</tbody>

</table>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
