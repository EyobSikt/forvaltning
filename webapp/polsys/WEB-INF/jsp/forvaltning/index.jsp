<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Index-side for polsys.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Data - Forvaltning" />
    <c:param name="tittelEn" value="Data - Civil Service" />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<div id="main">

<c:if test="${no}"><h1>Datasider - Index</h1></c:if>
<c:if test="${en}"><h1>Data pages - Index</h1></c:if>

<c:if test="${no}"><p><strong>Denne siden er hovedsakelig for internt bruk. Forsiden til Forvaltningsdatabasen kan besøkes <a href="https://forvaltningsdatabasen.sikt.no">her</a>.</strong></p></c:if>
<c:if test="${en}"><p><strong>This page is primarily for internal use. Please visit The Norwegian State Administration Database <a href="https://forvaltningsdatabasen.sikt.no/en/">here</a>.</strong></p></c:if>

<c:if test="${no}"><p><a href="<m:url value="/en/" />">View this page in English.</a></p></c:if>
<c:if test="${en}"><p><a href="<m:url value="/" />">View this page in Norwegian.</a></p></c:if>


<%-- ========================== Norsk ===================================  --%>
<c:if test="${no}">

    <h3>Bla organisasjoner based på Brønnøysundregistrene hierarki</h3>
    <ul>
        <li><a href="<p:url value="/data/enhetsregisteret" />">Bla organisasjoner</a></li>
        <li><a href="<p:url value="/data/organisasjon/sok" />">Søk på organisasjon og registreringsår</a></li>
    </ul>


<h3>Søk etter enheter eller bla via departement</h3>
<ul>
<li><a href="<p:url value="/data/sok" />">Søk på institusjonsnavn</a></li>
<li><a href="<p:url value="/data/departement" />">Interne enheter i og ytre enheter under departement</a></li>
</ul>
<h3>Statistikk om enheter</h3>
<ul>
<li><a href="<p:url value="/data/departementsenhet" />">Antall interne departementsenheter fordelt på departementer</a></li>
<li><a href="<p:url value="/data/internendring" />">Organisasjonsendringer - interne departementsenheter</a></li>
<li><a href="<p:url value="/data/forvaltningsenhet" />">Antall ytre enheter under departement</a></li>
<li><a href="<p:url value="/data/ytreendring" />">Organisasjonsendringer - ytre enheter under departementer</a></li>
<li><a href="<p:url value="/data/lokalisering" />">Nye enheter lokalisert utenfor Oslo</a></li>
<li><a href="<p:url value="/data/cofog" />">Antall enheter per COFOG</a></li>
<li><a href="<p:url value="/data/fylke" />">Antall enheter per fylke</a></li>
<li><a href="<p:url value="/data/oppgave" />">Antall enheter per oppgave</a></li>
<li><a href="<p:url value="/data/lagtabell" />">Lag tabell med data fra databasen</a></li>
</ul>

<h3>Litteratur</h3>
<ul>
<li><a href="<p:url value="/data/litteratur/sok" />">Litteratursøk</a></li>
<li><a href="<p:url value="/data/litteratur/enhet" />">Søk etter litteratur til enheter</a></li>
<li><a href="<p:url value="/data/litteratur/tilknytningsform" />">Finn litteratur på tilknytningsform</a></li>
<li><a href="<p:url value="/data/litteratur/kategori" />">Kategorisering av litteratur</a></li>
</ul>

<h3>Statsansatte</h3>
<ul>
<li><a href="<p:url value="/data/ansatte/land" />">Land</a></li>
<li><a href="<p:url value="/data/ansatte/fylke" />">Fylker</a></li>
<li><a href="<p:url value="/data/ansatte/kommune" />">Kommuner</a></li>
<li><a href="<p:url value="/data/ansatte/dep" />">Departementsområder</a></li>
<li><a href="<p:url value="/data/ansatte/etat" />">Etater</a></li>
<li><a href="<p:url value="/data/ansatte/landetat" />">Etater, aggregert</a></li>
<li><a href="<p:url value="/data/ansatte/lokalisering" />">Oslo vs. resten av landet</a></li>
</ul>

<h3>Utvalg</h3>
<ul>
<li><a href="<p:url value="/data/utvalg/sok" />">Søk etter utvalg på navn</a></li>
<li><a href="<p:url value="/data/utvalg/sokperson" />">Søk etter medlemmer i utvalg</a></li>
<li><a href="<p:url value="/data/utvalg/liste" />">Finn utvalg</a></li>
</ul>

<h3>Diverse</h3>
<ul>
<li><a href="<p:url value="/data/difi" />">difi</a></li>
<li><a href="<p:url value="/data/krd" />">krd</a></li>
<li><a href="<p:url value="/data/antallenheterendring" />">Antall forvaltningsorgan endring</a></li>
<li><a href="<p:url value="/data/antallselskapendring" />">Antall selskap endring</a></li>
<li><a href="<p:url value="/data/antallcofog" />">Antall enheter cofog</a></li>
<li><a href="<p:url value="/data/antallgrunnenhet" />">Antall enheter type</a></li>
</ul>

<h3>Eval: enheter gitt år</h3>
<ul>
<li><a href="<p:url value="/data/eval/enheter" />">Enheter gitt år.</a></li>
</ul>

<h3>Eval: Webtjeneste - xml</h3>
<ul>
<li><a href="<p:url value="/xml/data/eval/enheteraar" />">Enheter - år</a></li>
<li><a href="<p:url value="/xml/data/eval/enheterkomplett" />">Enheter - komplett</a></li>
</ul>
</c:if>
<%-- ====================================================================  --%>


<%-- ======================== Engelsk ===================================  --%>
<c:if test="${en}">
<h3>Find units by searching or browse units by ministries</h3>
<ul>
<li><a href="<p:url value="/data/sok" />">Search by name</a></li>
<li><a href="<p:url value="/data/departement" />">Bodies within and outside ministries</a></li>
</ul>
<h3>Unit statistics</h3>
<ul>
<li><a href="<p:url value="/data/departementsenhet" />">Number of bodies within ministries</a></li>
<li><a href="<p:url value="/data/internendring" />">Organizational change - bodies within ministries</a></li>
<li><a href="<p:url value="/data/forvaltningsenhet" />">Number of bodies outside ministries</a></li>
<li><a href="<p:url value="/data/ytreendring" />">Organizational change - bodies outside ministries</a></li>
<li><a href="<p:url value="/data/lokalisering" />">New units located outside Oslo</a></li>
<li><a href="<p:url value="/data/cofog" />">Number of units by COFOG</a></li>
<li><a href="<p:url value="/data/fylke" />">Number of units by county</a></li>
</ul>

<h3>Litteratur</h3>
<ul>
<li><a href="<p:url value="/data/litteratur/sok" />">Search literature by metadata</a></li>
<li><a href="<p:url value="/data/litteratur/enhet" />">Search literature by unit name</a></li>
<li><a href="<p:url value="/data/litteratur/tilknytningsform" />">Literature by affiliations</a></li>
<li><a href="<p:url value="/data/litteratur/kategori" />">Literature by categories</a></li>
</ul>

<h3>Employees</h3>
<ul>
<li><a href="<p:url value="/data/ansatte/land" />">National</a></li>
<li><a href="<p:url value="/data/ansatte/fylke" />">Fylker</a></li>
<li><a href="<p:url value="/data/ansatte/kommune" />">Kommuner</a></li>
</ul>
</c:if>
<%-- ====================================================================  --%>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
