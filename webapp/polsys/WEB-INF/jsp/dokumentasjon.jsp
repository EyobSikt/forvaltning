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

<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>

<div id="main">

<%--<c:if test="${no}"><h1>Data - PolSys</h1></c:if>
<c:if test="${en}"><h1>Data - PolSys</h1></c:if>

<c:if test="${no}"><p>
PolSys dataarkiv.
</p></c:if>
<c:if test="${en}"><p>
PolSys data archives.
</p></c:if>

<c:if test="${no}"><a href="<m:url value="/en/" />">View this page in English.</a></c:if>
<c:if test="${en}"><a href="<m:url value="/" />">View this page in Norwegian.</a></c:if>

<c:if test="${no}">
<h2><a href="<p:url value="/forvaltning/" />">Forvaltning</a></h2>
</c:if>
<c:if test="${en}">
<h2><a href="<p:url value="/forvaltning/" />">Civil Service</a></h2>
</c:if>--%>

<%--<c:if test="${no}"><p><a href="<m:url value="/en/dokumentasjon/" />">View this page in English.</a></p></c:if>
<c:if test="${en}"><p><a href="<m:url value="/dokumentasjon/" />">View this page in Norwegian.</a></p></c:if>--%>
<c:if test="${no}">
    <div class="breadcrumbs">Du er her: <a href="/">Forvaltningsdatabasen</a> &gt; Dokumentasjon</div>
    <h1>Dokumentasjon av Forvaltningsdatabasen</h1>
    <p>Under finner du linker til dokumentasjon av Forvaltningsdatabasen.</p>
    <ul>
        <li><a href="/forvaltning/dokumentasjon/om.html">Om Forvaltningsdatabasen</a></li>
        <li><a href="/forvaltning/dokumentasjon/enheter.html">Enheter, tilknytningsformer og type enheter</a></li>
        <li><a href="/forvaltning/dokumentasjon/nivaa.html">Administrativt nivå</a></li>
        <li><a href="/forvaltning/dokumentasjon/endringskoder.html">Endringskoder</a></li>
        <li><a href="/forvaltning/dokumentasjon/forvaltningslitteratur.html">Om forvaltningslitteratur</a></li>
        <li><a href="/forvaltning/dokumentasjon/statsansatte.html">Data om statsansatte</a></li>
        <li><a href="/forvaltning/dokumentasjon/utvalgsarkivet.html">Om Utvalgsarkivet</a></li>
    </ul>
    <h3>Forskningspaper</h3>
    <p>Vitenskapelige artikler som omhandler eller er relatert til Forvaltningsdatabasen.</p>
    <ul>
        <li><a href="/polsys/StatiskeDokument/Mapping_organizational_change.pdf">Paper om kartlegging av statlig endring</a> -  Paul G. Roness og Vidar W. Rolland har skrevet et paper som  diskuterer klassifikasjoner og utfordringer knyttet opp til kartlegging  av statlig endring over tid. Paperet har blitt presentert på en  internasjonal konferanser i Dublin i mars 2009.</li>
        <li><a href="/polsys/StatiskeDokument/Mapping_organizational_units.pdf">Paper  om kartlegging av statlige enheter</a> -  Paul G. Roness og Vidar W. Rolland har skrevet et paper som  diskuterer klassifikasjoner og utfordringer knyttet opp til kartlegging  av endring av statlige enheter over tid. Paperet har blitt presentert på  en internasjonal konferanser i Brussel i april 2009.</li>
    </ul>
    <p></p>
    </div>
</c:if>
<c:if test="${en}">



</c:if>
</div>

<%--<c:import url="/WEB-INF/jspf/bunn.jsp" />--%>
<%--
<div id="signature">
    <p class="copyright">Copyright &copy; NSD - Norwegian Centre for Research Data &bull; <a href="/personvern.html">Privacy Policy</a> &bull; <a class="img_link" href="https://www.twitter.com/nsddata" target="_blank"><img src="/img/twitter_16.png" alt="" /></a></p>
</div>--%>

<div id="signature">
    <p class="copyright">
        Copyright © Sikt – Kunnskapssektorens tjenesteleverandør
        <a href="https://sikt.no/kontakt-oss" target="_blank">
            • Kontakt SIKT
        </a>
        <a href="https://sikt.no/personvernerklaering" target="_blank">
            • Personvernerklæring
        </a>
        <span> Versjon 1.1</span>
        <a href="https://uustatus.no/nb/erklaringer/publisert/e2ebb5d0-077e-4a9d-babd-8698357d4dba" target="_blank">
            • Tilgjengelighetserklæring
        </a>
    </p>
</div>