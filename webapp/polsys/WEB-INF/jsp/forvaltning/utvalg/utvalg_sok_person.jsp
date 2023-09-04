<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over en bestemt litteratur/publikasjon.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Medlemssøk - Utvalg - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Search committees members - Advisory Committees - State Administration Database" />
    <c:param name="beskrivelseNo" value="Søk etter medlemmer i offentlige styrer, utvalg og råd." />
    <c:param name="beskrivelseEn" value="Search advisory committees members." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<style type="text/css">

    .overridep{
        line-height: 1.5em;
        margin: 0 0 10px;
    }

    .dhtmlgoodies_vismer{    /* Styling  vis mer */
        font-size:0.9em;
        color:#305080;
        width:60px;
        margin-bottom:2px;
        margin-top:2px;
        padding-left:2px;
        height:20px;
        text-decoration: underline;
        /* End layout CSS */
        overflow:hidden;
        cursor:pointer;
    }

    .dhtmlgoodies_lessmer{    /* Parent box of slide down content */
        /* Start layout CSS */
        border:1px solid #A5A1A0;
        width:550px;
        /* End layout CSS */
        visibility:hidden;
        height:0px;
        overflow:hidden;
        position:relative;
    }
    .dhtmlgoodies_content{    /* Content that is slided down */
        padding:10px;
        font-size:10px;
        position:relative;
    }

    #wrapper {
        width: 1005px;
        margin: 0 auto;
    }

    #leftcolumn, #rightcolumn {
        /*border: 1px solid white;
        border: 1px solid;*/
        float: left;
        min-height: 400px;
        list-style: none outside none;
        /*color: white;*/
    }
    #leftcolumn {
        width: 250px;
        /*background-color: #777;*/
    }
    #rightcolumn {
        width: 500px;
        /*background-color: #777;*/
    }
    .fasetter ul {
        list-style: none outside none;

    }
    #submitbutton {
        margin: 10px 500px 0 0;
    }

</style>


<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/utvalgsarkivet.html">Utvalg</a>
> Søk etter medlem
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/advisorycommittees.html">Advisory Committees</a>
> Search members
</c:if>
</div>

<h1>${en ? "Search advisory committees members" : "Medlemssøk"}</h1>

<c:if test="${no}">
<p>Søk på navn til utvalgsmedlemmer.</p>
</c:if>
<c:if test="${en}">
<p>Search committees by name.</p>
<p class="nottranslated">Please note: The Advisory Committees pages contains data in Norwegian that is not translated to English.</p>
</c:if>

<form action="" method="get">
<input type="text" value="${param.s}" maxlength="100" size="75" name="s" />
<input class="button" type="submit" value="${en ? "Search" : "Søk"}" />
</form>

<c:if test="${param.s != null}">
<h2>${en ? "Results" : "Søkeresultat"}</h2>
</c:if>

<c:if test="${param.s != null && empty personer}">
<p><em>${en ? "No hits." : "Ingen treff."}</em></p>
</c:if>

<c:if test="${!(empty personer)}">
<c:if test="${no}"><p>Søket ditt ga ${fn:length(personer)} ${fn:length(personer) > 1 ? "resultater" : "resultat"}.</p></c:if>
<c:if test="${en}"><p>${fn:length(personer)} ${fn:length(personer) > 1 ? "hits" : "hit"}.</p></c:if>

    <p></p>

    <strong>tips:</strong>
    <span class="overridep">Som standard en liste over alle utvalgsmedlemmer som inneholder innspill i søkeboksen er vist. </span>


    <div class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
    <div class="dhtmlgoodies_lessmer">
        <div >
            <h4>Om Utvalgsarkivet</h4>
            <ul>
                <li>
                    <strong>Perioden 1980-1997 og 2002-2003</strong>  Arkivet inneholder informasjon om utvalgene og om medlemmene i disse. Grunnlaget for arkivet er de årlige oversiktene over statlige utvalg, styrer og råd (hvert 4. år som Stortingsmelding nr 7).
                </li>
                <li>
                    <strong>Prioden 2004-d.d</strong> Denne arkivet inneholder oversikt over hvilke styrer, råd, nemnder og utvalg som finnes i norsk forvaltning, faste og midlertidige. Den gir også en oversikt over hvem som sitter i disse. Arkivet inneholder de organene som er aktive pr. dags dato (hvert år fra Data.regjeringen.no/sru).
                </li>
                <p> NSD er ikke ansvarlig for eventuelle feil i kilder. <strong>Les mer <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/dokumentasjon/utvalgsarkivet.html" target="_blank">her</a></strong>.</p>
            </ul>
            <h4>Hvorfor er det det samme navnet gjentas flere ganger?</h4>
            <ul>
                <li>
                    <strong>Utvalgsmedlemmer f.o.m 2004</strong>  Fra og med 2004 medlemmer som utvalget ivaretar er fra Departementenes sikkerhets- og serviceorganisasjon (DSS). Det viser seg at alle blir innrapporterte, og at derfor materialet vil være bedre kvalitet.
                </li>
                <li><strong>Utvalgsmedlemmer t.o.m 2003</strong>  For å bli registrert i Stortingsmelding nr. 7 eller i oversiktene er en avhengig av at de enkelte departementene rapporterer inn nye utvalg til Arbeids- og Administrasjonsdepartementet. Det viser seg at ikke alle blir innrapporterte, og at derfor materialet vil være noe mangelfullt. Informasjonen om organisasjonstilknytingen til medlemmene kan òg ofte være mangelfull da denne er basert på selvrapportering.
                </li>
            </ul>

        </div>
    </div>

    <script type="text/javascript">
        initShowHideDivs();
    </script>

    <p></p>

<ol>
<c:forEach items="${personer}" var="person">
<c:set var="i" value="${fn:indexOf(fn:toLowerCase(person.etterOgFornavn), fn:toLowerCase(param.s))}" />
<li>
    <c:if test="${!(empty person.utvalg2003)}"> <c:set var="aar2003" value="t.o.m 2003"></c:set> </c:if>
${fn:escapeXml(fn:substring(person.etterOgFornavn, 0, i))}<c:if test="${i > -1}"><strong>${fn:escapeXml(fn:substring(person.etterOgFornavn, i, i + fn:length(param.s)))}</strong>${fn:escapeXml(fn:substring(person.etterOgFornavn, i + fn:length(param.s), -1))}</c:if>
 <%--<a href="<p:url value="/forvaltning/utvalg/person/${person.pnr}" />"></a>--%>
   <table class="zebra sortable">
       <thead>
       <tr>
           <th>${en ? "Committee" : "Utvalg"} <c:if test="${!(empty person.utvalg2003)}"> (t.o.m 2003) </c:if> <c:if test="${!(empty person.utvalg2004)}"> (f.o.m 2004) </c:if> </th>
           <th>${en ? "Function" : "Funksjon"}</th>
           <th>${en ? "Birth year" : "Fødselsår"}</th>
       </tr>
       </thead>
        <tbody>
            <tr>
                <td class="tdtext"><a href="<p:url value="/data/utvalg/person/${person.pnr}" />">${fn:escapeXml(person.utvalgnavn)}</a></td>
                <td class="tdtext">${fn:escapeXml(person.hovedfunksjon)}</td>
                <td class="tdtext">${fn:escapeXml(person.foedselsaar)}</td>
            </tr>
        </tbody>
   </table>


</li>
</c:forEach>
</ol>

</c:if>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
