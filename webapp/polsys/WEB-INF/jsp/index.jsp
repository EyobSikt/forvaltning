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
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>
</c:if>
<c:if test="${en}">
<c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp">
</c:import>
</c:if>
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
<c:if test="${no}"><p><a href="<m:url value="/en/" />">View this page in English.</a></p></c:if>
<c:if test="${en}"><p><a href="<m:url value="/" />">View this page in Norwegian.</a></p></c:if>

<c:if test="${no}">
    <h2>Forvaltningsdatabasen</h2>
    <p>Forvaltningsdatabasen inneholder data om den norske statsforvaltningen.</p>
    <p>
        Forvaltningsdatabasen består i hovedsak av følgende: Kartlegging av departement og ytre etater etter 1947. Informasjonen er tilrettelagt slik at man kan få ut oversikt over den hierarkiske organiseringen og hvordan denne har endret seg over tid. Forvaltningslitteratur omhandler litteratur som er relevant for de ulike virksomhetene i Forvaltningsdatabasen, også årsmeldinger. Oversikten over statsansatte gjør det mulig å følge utviklingen i tallet på statlige arbeidstakere. Under Utvalgsarkivet er det tilrettelagt informasjon om utvalg, styrer og råd, samt om medlemmene i disse, for perioden 1980-1997.
    </p>
    <ul>
            <%-- <li><a href="<p:url value="/html/forvaltningsdatabasen.html" />">Enhetene i forvaltningen</a></li>--%>
        <li><a href="/forvaltning/forvaltningsdatabasen.html">Enhetene i forvaltningen</a></li>
        <li><a href="<p:url value="/forvaltning/forvaltningslitteratur.html" />">Forvaltningslitteratur</a></li>
        <li><a href="<p:url value="/forvaltning/statsansatte.html" />">Oversikt over statsansatte</a> 1980 - 2014</li>
        <li><a href="<p:url value="/forvaltning/utvalgsarkivet.html" />">Utvalgsarkivet</a></li>
    </ul>
    <h3>Andre data tilknyttet Forvaltningsdatabasen</h3>
    <p>Her finnes data fra en spørreundersøkelse gjennomført i 2004 om Statlig autonomi og styring i staten. Videre en spørreundersøkelse om Forvaltningens EU-tilpasning (1998 og 2003) og Sentraladministrasjonsundersøkelsen 2006; en spørreundersøkelse om tidsbruk, arbeidsrutiner og beslutningsprosesser i departement og  direktorat. Alle disse undersøkelsene er knyttet til Forvaltningsdatabasen.</p>
    <!-- <p>Embetsmenn inneholder informasjon om norske embetsmenn.</p>-->
    <ul>
        <li><a href="/forvaltning/sentraladmsurvey.html">Sentraladministrasjonsundersøkelsene</a>&nbsp;<br />
            - Spørreundersøkelser gjennomført med 10 års mellomrom om tidsbruk, arbeidsrutiner og beslutningsprosesser i sentralforvaltningen.</li>
        <li><a href="/forvaltning/statligautonomi.html">Spørreundersøkelse om statlig autonomi og styring i staten</a></li>
        <li><a href="/forvaltning/europatilpasning.html">Spørreundersøkelse om forvaltningens EU-tilpasning</a> <br />
            - Sentraladministrasjonen og avdelingene sine arbeidsoppgaver relatert til EU/EØS.<br />
            &nbsp;</li>
        <li><a href="/forvaltning/avtalearkivet.html">Avtalearkivet - evaluering av NAV reformen</a>. Avtalearkivets datasett gir en kvantifisert oversikt over innholdet i inngåtte avtaler mellom Arbeids- og velferdsetaten (representert på fylkesnivå) og den enkelte kommune.</li>
        <!--    <li><a href="/polsys/forvaltning/embetsmannsarkivet.html">Embetsmannsarkivet</a> - Embetsmenns karriere i sentraladministrasjonen.</li> --> </ul>
    <p></p>
</c:if>
<c:if test="${en}">
    <h2>The Norwegian State Administration Database</h2>
    <p>The Norwegian State Administration Database (NSAD) gives a detailed overview of the Norwegian state administration after the Second World War. It also contains information about literature on the state administration and data on civil service employees.</p>
    <ul>
        <li><a href="<p:url value="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html" />">State units</a> - Organisational change for ministries, other administrative units, state-owned companies and foundations.</li>
        <li><a href="<p:url value="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html" />">Literature on the state administration.</a> Also annual reports.</li>
        <li><a href="<p:url value="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html" />">Civil Service Employees</a> - Data on Civil Service Employees</li>
    </ul>

    <h3>Other data</h3>
    <p>In addition, The NSAD holds data from a Survey of Governmental Autonomy conducted i 2004, data from a Survey of Governmental Adaptations to EU (1998 and 2003) and data from the Survey of State Administration 2006.</p>
    <p>Advisory Committees contain information on the committee as such and about their members. Civil Servants give information about carrer of civil servants.</p>
    <ul>
        <li><a href="https://forvaltningsdatabasen.sikt.no/civilservice/autonomysurvey.html">Survey of Governmental Autonomy</a> - Questionnaire on governmental autonomy and steering.</li>
        <li><a href="https://forvaltningsdatabasen.sikt.no/civilservice/euadaptation.html">Survey of Governmental Adaptations to EU</a> - Questionnaire on governmental EU adaption.</li>
        <li><a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationsurvey06.html">Survey  of State Administration 2006</a></li>
        <li><a href="https://forvaltningsdatabasen.sikt.no/civilservice/advisorycommittees.html">Archive of Advisory Committees</a> - Occupation and affiliation to non-profit organisations etc for members of Advisory Committees.</li>
        <li><a href="https://forvaltningsdatabasen.sikt.no/civilservice/civilservants.html">Archive of Civil Servants</a> - Carrier for civil servants employed in the central administration.</li>
    </ul>
    <p></p>
    </div>


    <div id="sidebar">
        <h3><a href="/polsys/StatiskeDokument/Mapping_organizational_change.pdf">Mapping organizational change in the state</a></h3>
        <p>Paul G. Roness and Vidar W. Rolland have written a paper which discusses the challenges and classifications you are faced with when mapping change in the state. The paper was presented at an international conferences i Dublin in March 2009. <a href="/polsys/StatiskeDokument/Mapping_organizational_change.pdf">MORE (pdf) </a></p>
        <h3><a href="/polsys/StatiskeDokument/Mapping_organizational_units.pdf">Mapping organizational units in the state</a></h3>
        <p>Paul G. Roness and Vidar W. Rolland have written a paper which discusses the challenges and classifications you are faced with when mapping&nbsp;units in the state. The paper was presented at an international conferences i Dublin in March 2009 and has been published in the <a title="International Journal of Public Administration" target="_blank" href="http://www.informaworld.com/smpp/title~content=t713597261"><b>International Journal of Public Administration</b></a>. <a href="/polsys/StatiskeDokument/Mapping_organizational_units.pdf">MORE (pdf) </a></p>
        <h3><a href="/polsys/index.cfm?urlname=civilservice&amp;lan=eng&amp;aktuelt=11&amp;institusjonsnr=4&amp;arkivnr=6&amp;MenuItem=N1_4&amp;ChildItem=&amp;State=collapse">Mapping the Irish State</a></h3>
        <p>At University College Dublin, a project has been started up building a searchable time-series database of national-level state institutions. The project is inspired by the Norwegian State Administration Database <a href="/polsys/index.cfm?urlname=civilservice&amp;lan=eng&amp;aktuelt=11&amp;institusjonsnr=4&amp;arkivnr=6&amp;MenuItem=N1_4&amp;ChildItem=&amp;State=collapse">MORE</a></p>
    </div>


</c:if>
</div>

<%--<c:import url="/WEB-INF/jspf/bunn.jsp" />--%>
<%--
<div id="signature">
    <p class="copyright">Copyright &copy; NSD - Norwegian Centre for Research Data &bull; <a href="https://sikt.no/personvernerklaering">Privacy Policy</a> &bull; <a class="img_link" href="https://www.twitter.com/nsddata" target="_blank"><img src="/common/img/twitter_16.png" alt="" /></a></p>
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