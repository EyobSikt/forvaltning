<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): et
 - Copyright NSD
 - Description: Viser oversikt over en bestemt litteratur/publikasjon.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Liste - Utvalg - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="List - Advisory Committees - State Administration Database" />
    <c:param name="beskrivelseNo" value="Liste over offentlige styrer, utvalg og råd." />
    <c:param name="beskrivelseEn" value="List of advisory committees." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<style type="text/css">

</style>



<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/utvalgsarkivet.html">Utvalg</a>
> Finn utvalg
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/advisorycommittees.html">Advisory Committees</a>
> Find committee
</c:if>
</div>

<h1>${en ? "List of advisory committees" : "Finn utvalg"}</h1>

    <p></p>

    <strong>Søketips:</strong>
    <span class="overridep">Som standard en liste over alle utvalgene er vist. Avgrens listen ved å velge fra valgfeltene og kryssboksene. </span>


    <div class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
    <div class="dhtmlgoodies_lessmer">
        <div >
            <h4>Om Utvalgsarkivet</h4>
            <ul>
                <li>
                  <strong>Perioden 1980-1997 og 2002-2003:</strong>  Arkivet inneholder informasjon om utvalgene og om medlemmene i disse. Grunnlaget for arkivet er de årlige oversiktene over statlige utvalg, styrer og råd (hvert 4. år som Stortingsmelding nr 7).
                    <p>
                    <strong>Datakvalitet:</strong>
                    For å bli registrert i Stortingsmelding nr. 7 eller i oversiktene er en avhengig av at de enkelte departementene rapporterer inn nye utvalg til Arbeids- og Administrasjonsdepartementet. Det viser seg at ikke alle blir innrapporterte, og at derfor materialet vil være noe mangelfullt. Informasjonen om organisasjonstilknytingen til medlemmwne kan òg ofte være mangelfull da denne er basert på selvrapportering.
                    </p>
                </li>
                <li>

                    <strong>Prioden 2004-d.d:</strong> Denne arkivet inneholder oversikt over hvilke styrer, råd, nemnder og utvalg som finnes i norsk forvaltning, faste og midlertidige. Den gir også en oversikt over hvem som sitter i disse. Arkivet inneholder de organene som er aktive pr. dags dato fra Departementenes sikkerhets- og serviceorganisasjon (DSS) (hvert år fra Data.regjeringen.no/sru).
                    <p>
                        <strong>Datakvalitet:</strong>
                    Det er det enkelte departement som legger inn og ajourfører de nødvendige informasjonene om de organene de har ansvar for. Selv om data materialet kan mangle noe informasjon viser det seg at alle blir innrapporterte, og derfor er dette arkivet i bedre kvalitet enn de eldre peridoer (<2004).
                    </p>
                    NSD er ikke ansvarlig for eventuelle feil i kilder. <strong>Les mer <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/dokumentasjon/utvalgsarkivet.html" target="_blank">her</a></strong>.
                </li>
            </ul>
            <h4>Hvordan søker jeg?</h4>
            <ul>
                <li>
                    Velge fra valgfeltene og kryssboksene. Trykk på søkeknappen.
                </li>
            </ul>
            <h4>Hvordan avgrenser jeg søket?</h4>
            <ul>
                <li>
                    Du kan bruke valgfeltene til å avgrense søket ditt. Bruk avkrysningsboksene i hvert felt til å velge bestemte Hovedfunksjon. Det er mulig å velge flere Hovedfunksjoner samtidig. Dersom ingen filtrering er valgt, en liste over alle utvalgene er vist.
                </li>
            </ul>
            <h4>Hvorfor er det to hovedfunksjoner?</h4>
            <ul>
                <li>
               <strong>Hovedfunksjon f.o.m 2004</strong>  Fra og med 2004 funksjoner som utvalget ivaretar er fra Departementenes sikkerhets- og serviceorganisasjon (DSS).
                </li>
                <li><strong>Hovedfunksjon t.o.m 2003</strong>  Til og med 2003 funksjoner som utvalget ivaretar er fra Stortingsmelding nr 7.
                </li>
            </ul>

        </div>
    </div>

    <script type="text/javascript">
        initShowHideDivs();
    </script>

  <p></p>



<form id="sokeboks" class="dokumentTop"   action="<p:url value="/data/utvalg/liste" />" accept-charset="UTF-8"  method="GET">

<p>
${en ? "Ministries" : "Departement"}:
<select name="dep">
<option value="">${en ? "All ministries" : "Alle departement"}</option>
<c:forEach items="${departementer}" var="d">
<option value="${d.idnum}" ${d.idnum eq param.dep ? 'selected="selected"' : ''}>${fn:escapeXml(d)}(${d.fraaar}-${d.tilaar})</option>
</c:forEach>
</select>
</p>

<p>
${en ? "Year" : "Opprettelsesår"}:
<select name="aar">
<option value="">${en ? "All" : "Alle år"}</option>
<c:forEach begin="1980" end="2018" step="1" var="i">
<option value="${i}" ${i eq param.aar ? 'selected="selected"' : ''}>${i}</option>
</c:forEach>
</select>

${en ? "Type" : "Utvalgstype"}:
<select name="type">
<option value="">${en ? "All" : "Alle typer"}</option>
<c:forEach items="${type}" var="t">
<option value="${t.kode}" ${t.kode eq param.type ? 'selected="selected"' : ''}>${fn:escapeXml(t.tekstEntall)}</option>
</c:forEach>
</select>
</p>

    <p>
        ${en ? "Geography" : "Geografisk vireområde"}:
        <select name="geo">
            <option value="">${en ? "All" : "Alle virkeområder"}</option>
            <c:forEach items="${geografi}" var="g">
                <option value="${g.kode}" ${g.kode eq param.geo ? 'selected="selected"' : ''}>${fn:escapeXml(g.tekstEntall)}</option>
            </c:forEach>
        </select>
    </p>


<%--
<p>
${en ? "Function" : "Hovedfunksjon"}:
<select name="funk">
<option value="">${en ? "All" : "Alle hovedfunksjoner"}</option>
<c:forEach items="${hovedfunksjon}" var="h">
<option value="${h.kode}" ${h.kode eq param.funk ? 'selected="selected"' : ''}>${fn:escapeXml(h.tekstEntall)}</option>
</c:forEach>
</select>
</p>

 <p>
     ${en ? "Function" : "Hovedfunksjon20018"}:
        <select name="funk">
            <option value="">${en ? "All" : "Alle hovedfunksjoner"}</option>
            <c:forEach items="${hovedfunksjon2018}" var="h">
                <option value="${h.kode}" ${h.kode eq param.funk ? 'selected="selected"' : ''}>${fn:escapeXml(h.tekstEntall)}</option>
            </c:forEach>
        </select>
 </p>
 --%>

    <div id="wrapper" class="fasetter">
    <div id="leftcolumn" >
        <c:set var="doktypenames" value="${paramValues.fq}"/>
        <div class="fasettertittel"><p><strong>Hovedfunksjon t.o.m 2003</strong></p>
        </div>
        <ul>
            <c:forEach items="${hovedfunksjon}" var="solr_doktype2" >
                <li>
                    <c:set var="names" value="${paramValues.fq}"/>
                    <c:set var="doknavn2" value="hf2013-${solr_doktype2.kode}" />
                    <c:set var="isSelected" value="false" scope="page"/>
                    <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                        <c:if test="${names[k] ==doknavn2 }">
                            <c:set var="isSelected" value="true" scope="page"/>
                        </c:if>
                    </c:forEach>
                    <input type="checkbox" name="fq"  value="${fn:escapeXml(doknavn2)}" escapeXml="false"  <c:if test="${isSelected}">checked=true</c:if>"/> ${solr_doktype2.tekstEntall}
                </li>
            </c:forEach>
        </ul>
    </div>


    <div id="rightcolumn">
        <c:set var="doktypenames" value="${paramValues.fq}"/>
        <div class="fasettertittel"><p><strong>Hovedfunksjon f.o.m 2004</strong></p>
        </div>
        <ul>
            <c:forEach items="${hovedfunksjon2018}" var="solr_doktype2" >
                <li>
                    <c:set var="names" value="${paramValues.fq}"/>
                    <c:set var="doknavn2" value="hf2018-${solr_doktype2.kode}"/>
                    <c:set var="isSelected" value="false" scope="page"/>
                    <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                        <c:if test="${names[k] ==doknavn2 }">
                            <c:set var="isSelected" value="true" scope="page"/>
                        </c:if>
                    </c:forEach>
                    <input type="checkbox" name="fq"  value="${doknavn2}"    <c:if test="${isSelected}">checked=true</c:if>"/> ${solr_doktype2.tekstEntall}
                </li>
            </c:forEach>
        </ul>
    </div>
    </div>
<p></p>
<div>
<p><input type="submit" id="submitbutton" value="Finn utvalg"  /></p>
</div>
</form>


<c:if test="${empty utvalg && finn}">
<p><em>Ingen treff.</em></p>
</c:if>
 <iframe id="txtArea1" style="display:none"></iframe>
<%--<c:if test="${!(empty utvalg)}">--%>
<table id="excelTable" class="text zebra sortable ">
<caption>${fn:length(utvalg)} ${en ? "committees" : "utvalg"}
 <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
</caption>

<thead>
<tr>
<th>${en ? "From" : "Fra"}</th>
<th>${en ? "To" : "Til"}</th>
<th>${en ? "Function" : "Funksjon"}</th>
<th>${en ? "Name" : "Navn"}</th>
<th>${en ? "until 2003" : "t.o.m 2003"}</th>
<th>${en ? "from 2004" : "f.o.m 2004"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${utvalg}" var="u">
<tr class="lineunder">
<td>${u.opprettelsesaar}</td>
<td>${u.opphoersaar}</td>
<td>${fn:escapeXml(u.hovedfunksjon)}</td>
<td><a href="<p:url value="/data/utvalg/${u.unr}" />">${fn:escapeXml(u.navn)}</a></td>
<td><c:if test="${!(empty u.utvalg2003)}">X</c:if></td>
<td><c:if test="${!(empty u.utvalg2004)}">X</c:if></td>
</tr>
</c:forEach>
</tbody>

</table>
<%--</c:if> --%>

</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
