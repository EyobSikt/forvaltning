<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Sider der en kan lage tabell med data.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Lag tabell - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Data - State Administration Database" />
    <c:param name="beskrivelseNo" value="Lag tabell med data fra Forvaltningsdatabasen." />
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
> Lag tabell
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Data
</c:if>
</div>

<h1>${en ? "Extract data on units from the database" : "Lag tabell med data om enheter fra Forvaltningsdatabasen"}</h1>

<form action="<p:url value="/data/tabell" />" method="get">

<%-- =============================== Filter ============================== --%>
<h2 class="lineunder">${en ? "Filter - units" : "Filter - enheter"}</h2>


<div class="column_wide">
<h3>${en ? "Choose affiliations" : "Velg tilknytningsformer"}</h3>
<c:if test="${param.valg2 != null}">
    <c:if test="${no}"><p><strong>Vennligst velg minst en tilknytningsform.</strong></p></c:if>
    <c:if test="${en}"><p><strong>Please choose at least one.</strong></p></c:if>
</c:if>

<ul class="plain">
<c:forEach items="${tilknytningsformer}" var="t">
<c:if test="${t.kode >= -10000}">
<li><input type="checkbox" name="t" value="${t.kode}" id="t${t.kode}" /> <label for="t${t.kode}">${t.kode} ${fn:escapeXml(t.tekstFlertall)}</label></li>
</c:if>
</c:forEach>
</ul>
</div>

<div class="column_wide">
<h3>${en ? "Choose category" : "Velg type (ytre enheter)"}</h3>

<ul class="plain">
<li><input type="checkbox" name="g" value="0" id="g0" /> <label for="g0">0 ${en ? "National single organizations" : "Nasjonale organisasjoner"}</label></li>
<li><input type="checkbox" name="g" value="10" id="g10" /> <label for="g10">10 ${en ? "Integrated civil service organizations" : "Etater"}</label></li>
<li><input type="checkbox" name="g" value="11" id="g11" /> <label for="g11">11 ${en ? "Central units in integrated civil service organization" : "Hovedkontor"}</label></li>
<%--<li><input type="checkbox" name="g" value="20a" id="g20a" /> <label for="g20a">20 ${en ? "National groups outside ministries" : "Grupper utenfor departement"}</label></li>--%>
<li><input type="checkbox" name="g" value="20b" id="g20b" /> <label for="g20b">20 ${en ? "National groups under ministries" : "Nasjonale grupper under departement"}</label></li>
<li><input type="checkbox" name="g" value="20c" id="g20c" /> <label for="g20c">20 ${en ? "Regional groups" : "Regionale grupper"}</label></li>
<li><input type="checkbox" name="g" value="21" id="g21" /> <label for="g21">21 ${en ? "Bodies part of group subordinated ministry" : "Enheter som inngår i etat eller gruppe"}</label></li>
</ul>


<h3>${en ? "Choose hierarchical level (bodies within ministries)" : "Velg administrativt nivå (departementsenheter)"}</h3>

<ul class="plain">
<c:forEach items="${nivaa}" var="n">
<li><input type="checkbox" name="n" value="${n.kode}" id="n${n.kode}"> <label for="n${n.kode}">${n.kode} ${fn:escapeXml(n.tekstFlertall)}</label></li>
</c:forEach>
</ul>
</div>
<%-- ===================================================================== --%>



<%-- =============================== Variabler =========================== --%>
<h2 class="break lineunder">${en ? "Variables" : "Variabler"}</h2>

<h3>${en ? "Unit change-variables" : "Løpende oppdaterte variabler (variablene blir regnet ut for oppgitt dato; dag, måned, år)"}</h3>
<ul class="plain">
<li><input type="checkbox" name="v" value="1" id="v1" /> <label for="v1">${en ? "long_name - official name of unit" : "langt_navn - offisielt navn til enheten"}</label></li>
<li><input type="checkbox" name="v" value="2" id="v2" /> <label for="v2">${en ? "short_name - short name of unit" : "kort_navn - kort til enheten"}</label></li>
<li><input type="checkbox" name="v" value="3" id="v3" /> <label for="v3">${en ? "affiliation" : "tilknytningsform"}</label></li>
<li><input type="checkbox" name="v" value="4" id="v4" /> <label for="v4">${en ? "type - main affiliation category" : "grunnenhet"}</label></li>
<li><input type="checkbox" name="v" value="5" id="v5" /> <label for="v5">${en ? "level - hierarchical level - internal " : "nivaa"}</label></li>
<li><input type="checkbox" name="v" value="6" id="v6" /> <label for="v6">${en ? "cofog" : "cofog"}</label></li>
<li><input type="checkbox" name="v" value="7" id="v7" /> <label for="v7">${en ? "sup_ministry_idnum - superior ministry" : "overordnet_dep_idnum - overordnet departement, id nummer"}</label></li>
<li><input type="checkbox" name="v" value="8" id="v8" /> <label for="v8">${en ? "sup_ministry_long - superior ministry long name" : "overordnet_dep_langt - overordnet departement langt navn, langt navn"}</label></li>
<li><input type="checkbox" name="v" value="9" id="v9" /> <label for="v9">${en ? "sup_ministry_short - superior ministry short name (in norwegian)" : "overordnet_dep_kort - overordnet departement, kort navn"}</label></li>

<li><input type="checkbox" name="v" value="10" id="v10" /> <label for="v10">${en ? "change_102 - founding by secession" : "endring_102 - ny via utskilling"}</label></li>
<li><input type="checkbox" name="v" value="11" id="v11" /> <label for="v11">${en ? "change_104 - founding by splitting" : "endring_104 - ny via splittelse"}</label></li>
<li><input type="checkbox" name="v" value="12" id="v12" /> <label for="v12">${en ? "change_106 - founding by merger" : "endring_106 - ny via sammenslåing"}</label></li>
<li><input type="checkbox" name="v" value="13" id="v13" /> <label for="v13">${en ? "change_111 - founding by complex reorganization" : "endring_111 - ny via omorganisering"}</label></li>

    <li><input type="checkbox" name="v" value="37" id="v37" /> <label for="v37">${en ? "change_101 - regular founding" : "endring_101 - nyopprettelse"}</label></li>
    <li><input type="checkbox" name="v" value="38" id="v38" /> <label for="v38">${en ? "change_112 - entered; new relevant entity" : "endring_112 - nyinnskrevet"}</label></li>

<li><input type="checkbox" name="v" value="14" id="v14" /> <label for="v14">${en ? "change_202 - maintenance by secession" : "endring_202 - består via utskilling"}</label></li>
<li><input type="checkbox" name="v" value="15" id="v15" /> <label for="v15">${en ? "change_203 - maintenance by absorption" : "endring_203 - består via innlemming"}</label></li>
<li><input type="checkbox" name="v" value="16" id="v16" /> <label for="v16">${en ? "change_211 - maintenance by reorganization" : "endring_211 - består via omorganisering"}</label></li>

    <li><input type="checkbox" name="v" value="39" id="v39" /> <label for="v39">${en ? "change_207 - change of name" : "endring_207 - navneendring"}</label></li>
    <li><input type="checkbox" name="v" value="42" id="v42" /> <label for="v42">${en ? "change_209 - change of location" : "endring_209 - fysisk omlokalisering"}</label></li>
    <li><input type="checkbox" name="v" value="43" id="v43" /> <label for="v43">${en ? "change_213 - new line of reporting" : "endring_213 - nyrapportering"}</label></li>
    <li><input type="checkbox" name="v" value="44" id="v44" /> <label for="v44">${en ? "change_221 - new superior organization (horizontal movement)" : "endring_221 - horisontal flytting"}</label></li>
    <li><input type="checkbox" name="v" value="45" id="v45" /> <label for="v45">${en ? "change_222 - new form of affiliation/administrative level" : "endring_222 - vertikal flytting (omdannelse)"}</label></li>
    <li><input type="checkbox" name="v" value="46" id="v46" /> <label for="v46">${en ? "change_223 - new superior organization and level" : "endring_223 - horisontal – og vertikal flytting"}</label></li>
    <li><input type="checkbox" name="v" value="47" id="v47" /> <label for="v47">${en ? "change_291 - no change to unit, but change of superior" : "endring_291 - endring av overordnet"}</label></li>
    <li><input type="checkbox" name="v" value="48" id="v48" /> <label for="v48">${en ? "change_292 - unit moving into, or out of, integrated organizations" : "endring_292 - flytting inn i eller ut av etat/gruppe"}</label></li>
    <li><input type="checkbox" name="v" value="49" id="v49" /> <label for="v49">${en ? "change_303 - ending by absorption" : "endring_303 - nedlagt via innlemming"}</label></li>
    <li><input type="checkbox" name="v" value="50" id="v50" /> <label for="v50">${en ? "change_304 - ending by splitting" : "endring_304 - nedlagt via splittelse"}</label></li>
    <li><input type="checkbox" name="v" value="51" id="v51" /> <label for="v51">${en ? "change_306 - ending by merger" : "endring_306 - nedlagt via sammenslåing"}</label></li>
    <li><input type="checkbox" name="v" value="52" id="v52" /> <label for="v52">${en ? "change_310 - pure disbanding" : "endring_310 - nedlagt"}</label></li>
    <li><input type="checkbox" name="v" value="53" id="v53" /> <label for="v53">${en ? "change_311 - ending by complex reorganization" : "endring_311 - nedlagt via omorganisering"}</label></li>
    <li><input type="checkbox" name="v" value="54" id="v54" /> <label for="v54">${en ? "change_312 - discharged; no longer relevant entity" : "endring_312 - nedlagt med utskriving"}</label></li>

    <li><input type="checkbox" name="v" value="17" id="v17" /> <label for="v17">${en ? "change_agg - 1 if one of variables above is 1, otherwise 0" : "endring_agg - om enheten har gjennomgått minst en av endringene over"}</label></li>

<li><input type="checkbox" name="v" value="18" id="v18" /> <label for="v18">${en ? "founded_year_unit - year this unit was founded" : "opprettet_aar_enhet - året denne enheten ble opprettet i sin nåværende form"}</label></li>
<li><input type="checkbox" name="v" value="19" id="v19" /> <label for="v19">${en ? "founded_year_pre - year this unit was founded included any predecessors" : "opprettet_aar_forl - året denne enheten ble etablert inkludert forløpere"}</label></li>
<li><input type="checkbox" name="v" value="20" id="v20" /> <label for="v20">${en ? "sub_count - " : "antall_under - antall underliggende enheter, substansielle, inkl. tilknyttede selskap/stiftelser, eksl. avdelinger/satellitter"}</label></li>
<li><input type="checkbox" name="v" value="21" id="v21" /> <label for="v21">${en ? "sub_count_group - " : "antall_under_gruppe - antall underliggende enheter, total inkl. tilknyttede selskap/stiftelser og grupper, men eksl. avdelinger/satellitter"}</label></li>
<li><input type="checkbox" name="v" value="22" id="v22" /> <label for="v22">${en ? "sub_count_sat - " : "antall_sat - antall underliggende avdelinger/satellitter"}</label></li>
<li><input type="checkbox" name="v" value="23" id="v23" /> <label for="v23">${en ? "english_name - english name of unit" : "engelsk_navn - engelsk navn til enheten"}</label></li>
<li><input type="checkbox" name="v" value="24" id="v24" /> <label for="v24">${en ? "location - municipality number" : "kommunenr - kommunenummer enheten er lokalisert"}</label></li>
<li><input type="checkbox" name="v" value="40" id="v40" /> <label for="v40">${en ? "location - municipality name" : "kommune - navn på kommunen enheten er lokalisert"}</label></li>
<li><input type="checkbox" name="v" value="41" id="v41" /> <label for="v41">${en ? "Organization number" : "Organisasjonsnummer"}</label></li>
</ul>

<h3>${en ? "Unit year-variables" : "Årspesifikke variabler (variablene gjelder for oppgitt år, men ikke nødvendigvis oppgitt dag, måned)"}</h3>
<ul class="plain">
<li><input type="checkbox" name="v" value="25" id="v25" /> <label for="v25">${en ? "main_task - main task of unit" : "hovedoppgave - hovedoppgaven til enheten"}</label></li>
<li><input type="checkbox" name="v" value="26" id="v26" /> <label for="v26">${en ? "sec_task_1 - seconardy task (1) of unit" : "bioppgave_1 - bioppgave (1) til enheten"}</label></li>
<li><input type="checkbox" name="v" value="27" id="v27" /> <label for="v27">${en ? "sec_task_2 - secondary task (2) of unit" : "bioppgave_2 - bioppgave (2) til enheten"}</label></li>

<li><input type="checkbox" name="v" value="28" id="v28" /> <label for="v28">${en ? "statres_name - StatRes: name)" : "statres_navn - navn på etat/område bruk i StatRes"}</label></li>
<li><input type="checkbox" name="v" value="29" id="v29" /> <label for="v29">${en ? "production - StatRes: Own production (million Nkr)" : "egenprod - StatRes: Egenproduksjon (mill. kr)"}</label></li>
<li><input type="checkbox" name="v" value="30" id="v30" /> <label for="v30">${en ? "salaries - StatRes: Salaries as a percentage of own production" : "lonnskost - StatRes: Lønnskostnader i prosent av egenproduksjon"}</label></li>
<li><input type="checkbox" name="v" value="31" id="v31" /> <label for="v31">${en ? "purchases - StatRes: Purchases of goods and services as a percentage of own production" : "kjop - StatRes: Kjøp av varer og tjenester i prosent av egenproduksjon"}</label></li>
<li><input type="checkbox" name="v" value="32" id="v32" /> <label for="v32">${en ? "investments - StatRes: Investments (million Nkr)" : "investeringer - StatRes: Investeringer (mill. kr)"}</label></li>
<li><input type="checkbox" name="v" value="33" id="v33" /> <label for="v33">${en ? "appropriations - StatRes: Appropriations (million Nkr)" : "overforinger - StatRes: Overføringer (mill. kr)"}</label></li>
<li><input type="checkbox" name="v" value="34" id="v34" /> <label for="v34">${en ? "expenditure - StatRes: Total expenditure (million Nkr)" : "utgifter - StatRes: Totale utgifter (mill. kr)"}</label></li>
<li><input type="checkbox" name="v" value="35" id="v35" /> <label for="v35">${en ? "premium_spf - StatRes: Estimated premium to the State Pension Fund (million Nkr)" : "premie_sp - StatRes: Beregnet premie til Statens Pensjonskasse (mill. kr)"}</label></li>
<li><input type="checkbox" name="v" value="36" id="v36" /> <label for="v36">${en ? "fte_statres - StatRes: FTEs (exclusive long-term leave)" : "aarsverk_statres - StatRes: Avtalte årsverk eksklusive lange fravær (årsverk)"}</label></li>
</ul>

<h3>${en ? "Employees - SST data" : "Ansatte - SST data (årsspesifikk)"}</h3>
<h4>${en ? "Variables" : "Variabler"}</h4>
<ul class="plain">
<li><input type="checkbox" name="sst" value="ans" id="sst-ans" /> <label for="sst-ans">${en ? "number og employees" : "antall ansatte"}</label></li>
<li><input type="checkbox" name="sst" value="aar" id="sst-aar" /> <label for="sst-aar">${en ? "number of FTEs" : "antall årsverk"}</label></li>
<li><input type="checkbox" name="sst" value="ald" id="sst-ald" /> <label for="sst-ald">${en ? "age" : "gj.snitt alder"}</label></li>
</ul>
<h4>${en ? "Filters" : "Filter"}</h4>
<ul class="plain">
<li><input type="checkbox" name="sst" value="k1" id="sst-k1" /> <label for="sst-k1">${en ? "gender: men" : "kjønn: menn"}</label></li>
<li><input type="checkbox" name="sst" value="k2" id="sst-k2" /> <label for="sst-k2">${en ? "gender: women" : "kjønn: kvinner"}</label></li>
<li><input type="checkbox" name="sst" value="ka" id="sst-ka" /> <label for="sst-ka">${en ? "gender: men and women" : "kjønn: ALLE"}</label></li>
<li><input type="checkbox" name="sst" value="l2" id="sst-l2" /> <label for="sst-l2">${en ? "salary category: ordinary" : "lønnskategori: ordinært regulativlønte"}</label></li>
<li><input type="checkbox" name="sst" value="l3" id="sst-l3" /> <label for="sst-l3">${en ? "salary category: contract" : "lønnskategori: overenskomst, timelønte, permisjon, kontrakter"}</label></li>
<li><input type="checkbox" name="sst" value="l4" id="sst-l4" /> <label for="sst-l4">${en ? "salary category: leaders" : "lønnskategori: ledere"}</label></li>
<li><input type="checkbox" name="sst" value="la" id="sst-la" /> <label for="sst-la">${en ? "salary category: all categories" : "lønnskategori: ALLE"}</label></li>
<li><input type="checkbox" name="sst" value="hdh" id="sst-hdh" /> <label for="sst-hdh">${en ? "part-time/full-time: full-time" : "stillingstype: heltid"}</label></li>
<li><input type="checkbox" name="sst" value="hdd" id="sst-hdd" /> <label for="sst-hdd">${en ? "part-time/full-time: part-time" : "stillingstype: deltid"}</label></li>
<li><input type="checkbox" name="sst" value="hdt" id="sst-hdt" /> <label for="sst-hdt">${en ? "part-time/full-time: hourly" : "stillingstype: timelønt"}</label></li>
<li><input type="checkbox" name="sst" value="hda" id="sst-hda" /> <label for="sst-hda">${en ? "part-time/full-time: all" : "stillingstype: ALLE"}</label></li>
</ul>


<%-- ===================================================================== --%>



<%-- =================================== Dato og søk ===================== --%>
<h2 class="break lineunder">${en ? "Date" : "Dato"}</h2>

<h3>${en ? "Enter a date and click submit" : "Oppgi dato og klikk lag tabell"}</h3>

<p>
${en ? "Day:" : "Dag:"}
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>

${en ? "Month:" : "Måned:"}
<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>

${en ? "Year:" : "År:"} <input type="text" value="${sistOppdatertDato.aar}" maxlength="4" size="6" name="y" />

<input type="submit" value="${en ? "Submit" : "Lag tabell"}" />
</p>
<%-- ===================================================================== --%>

</form>




</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
