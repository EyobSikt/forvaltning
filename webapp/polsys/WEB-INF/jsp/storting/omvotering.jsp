<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 03.nov.2010
  Time: 08:18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- --------------------------------------------- inkluderer toppinnhold. --%>

<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>



<style type="text/css">
.updated {
    margin-left: 490px;
    padding-bottom: 6px;
    width: 100%;
}

.openorig {
    background-color: #EEEEEE;
    border: 1px solid #BBBBBB;
    color: #888888;
    font-size: 7pt;
    margin-top: 5px;
    padding: 2px;
    text-align: center;
    text-decoration: none;
    text-transform: uppercase;
    width: 110px;
}    

</style>

<div id="sidebar">
<c:if test="${no}">
    <h3>Døme</h3>
    <ul>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?headers=Sesjon&Sesjonslice=154&stubs=part1&stubs=part2&measure=common&virtualslice=parti_value&layers=virtual&study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-S08&mode=cube&v=2&virtualsubset=parti_value&part1subset=7&submode=timeline&measuretype=4&part2subset=1+-+7&part1slice=7&cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-S08_C1&Sesjonsubset=124+-+154&part2slice=1&top=yes">Frp mot sentrum?</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part1&amp;stubs=part2&amp;measuretype=4&amp;submode=timeline&amp;v=2&amp;part2slice=2&amp;part2subset=2+-+7&amp;part1slice=1&amp;layers=virtual&amp;measure=common&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-S08&amp;virtualslice=parti_value&amp;part1subset=1&amp;headers=Sesjon&amp;mode=cube&amp;Sesjonslice=153&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-S08_C1&amp;virtualsubset=parti_value&amp;Sesjonsubset=124+-+153&amp;top=yes" target="_blank">SV – fra opposisjon til regjering</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&amp;stubs=Emne&amp;measuretype=4&amp;submode=timeline&amp;Emneslice=21000&amp;Emnesubset=21000+-+22000&amp;v=2&amp;part2slice=3&amp;part2subset=3&amp;part1slice=2&amp;layers=virtual&amp;layers=part1&amp;measure=common&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-E08&amp;virtualslice=parti_value&amp;part1subset=2&amp;headers=Sesjon&amp;mode=cube&amp;Sesjonslice=153&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-E08_C1&amp;virtualsubset=parti_value&amp;Sesjonsubset=124+-+153&amp;top=yes" target="_blank">DNA og SP i utenriks- og utdanningssaker</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&amp;stubs=Emne&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-E08&amp;virtualslice=parti_value&amp;part1subset=5&amp;measuretype=4&amp;headers=Sesjon&amp;mode=cube&amp;submode=timeline&amp;Sesjonslice=153&amp;Emneslice=4000&amp;Emnesubset=4000&amp;v=2&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-E08_C1&amp;part2slice=1&amp;part2subset=1+-+4%2C6+-+7&amp;virtualsubset=parti_value&amp;part1slice=5&amp;Sesjonsubset=124+-+153&amp;layers=virtual&amp;layers=part1&amp;measure=common&amp;top=yes" target="_blank">Høyres samarbeidsmønster med alle partier i finanssaker</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&amp;stubs=Komite&amp;measuretype=4&amp;Komitesubset=160%2C200&amp;submode=timeline&amp;v=2&amp;part2slice=2&amp;part2subset=2%2C5&amp;Komiteslice=160&amp;part1slice=4&amp;layers=virtual&amp;layers=part1&amp;measure=common&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-SK08&amp;virtualslice=parti_value&amp;part1subset=4&amp;headers=Sesjon&amp;mode=cube&amp;Sesjonslice=153&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-SK08_C1&amp;virtualsubset=parti_value&amp;Sesjonsubset=124+-+153&amp;top=yes" target="_blank">KrFs samarbeidsmønster med Høyre og DNA i saker fra Kirke- og undervisningskomiteen (1979–93) og Kirke-, utdannings- og forskningskomiteen (1993–)</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&amp;stubs=Komite&amp;measuretype=4&amp;Komitesubset=220&amp;submode=timeline&amp;v=2&amp;part2slice=1&amp;part2subset=1+-+5%2C7&amp;Komiteslice=220&amp;part1slice=6&amp;layers=virtual&amp;layers=part1&amp;measure=common&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-SK08&amp;virtualslice=parti_value&amp;part1subset=6&amp;headers=Sesjon&amp;mode=cube&amp;Sesjonslice=153&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-SK08_C1&amp;virtualsubset=parti_value&amp;Sesjonsubset=138+-+153&amp;top=yes" target="_blank">Venstres samarbeidsmønster med alle partier i saker fra Næringskomiteen i perioden 1993-09</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&amp;stubs=Emne&amp;measuretype=4&amp;submode=timeline&amp;Emneslice=16000&amp;Emnesubset=16000&amp;v=2&amp;part2slice=1&amp;part2subset=1+-+6&amp;part1slice=7&amp;layers=virtual&amp;layers=part1&amp;measure=common&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-E08&amp;virtualslice=parti_value&amp;part1subset=7&amp;headers=Sesjon&amp;mode=cube&amp;Sesjonslice=153&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-E08_C1&amp;virtualsubset=parti_value&amp;Sesjonsubset=126+-+153&amp;top=yes" target="_blank">FrPs samarbeidsmønster med alle partier i rettsvesensaker</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&amp;stubs=Emne&amp;measuretype=4&amp;submode=timeline&amp;Emneslice=14000&amp;Emnesubset=14000&amp;v=2&amp;part2slice=1&amp;part2subset=1+-+2%2C4+-+7&amp;part1slice=3&amp;layers=virtual&amp;layers=part1&amp;measure=common&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-E08&amp;virtualslice=parti_value&amp;part1subset=3&amp;headers=Sesjon&amp;mode=cube&amp;Sesjonslice=153&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-E08_C1&amp;virtualsubset=parti_value&amp;Sesjonsubset=142+-+153&amp;top=yes" target="_blank">SPs samarbeidsmønster med alle partier i lokalforvaltningssaker i perioden 1997-09</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&amp;stubs=Komite&amp;measuretype=4&amp;Komitesubset=180&amp;submode=timeline&amp;v=2&amp;part2slice=2&amp;part2subset=2+-+7&amp;Komiteslice=180&amp;part1slice=1&amp;layers=virtual&amp;layers=part1&amp;measure=common&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-SK08&amp;virtualslice=parti_value&amp;part1subset=1&amp;headers=Sesjon&amp;mode=cube&amp;Sesjonslice=153&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-SK08_C1&amp;virtualsubset=parti_value&amp;Sesjonsubset=138+-+153&amp;top=yes" target="_blank">SVs samarbeidsmønster med alle partier i saker fra Energi- og miljøkomiteen i perioden 1993-09</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&stubs=Komite&study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-STK08&virtualslice=parti_value&part1subset=8&measuretype=4&headers=Sesjon&mode=cube&Komitesubset=110&submode=timeline&Sesjonslice=141&v=2&cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-STK08_C1&part2slice=2&part2subset=1+-+7&Komiteslice=110&part1slice=8&Sesjonsubset=138+-+141&layers=virtual&layers=part1&top=yes" target="_blank">RV – mer enig med SP enn DNA i saker fra Finanskomiteen i perioden 1993-97</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part1&stubs=part2&study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-AE08&virtualslice=parti_value&part1subset=9%2C11&measuretype=4&headers=Sesjon&mode=cube&submode=timeline&Sesjonslice=149&v=2&cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-AE08_C1&part2slice=1&part2subset=1+-+7&virtualsubset=parti_value&part1slice=9&Sesjonsubset=142+-+149&layers=virtual&measure=common&top=yes" target="_blank">Kystpartiets (inkl. TVF) samarbeidmønster på Stortinget 1997-05</a></li>
        <li><a href="http://polsysdata.nsd.uib.no/webview/index.jsp?stubs=part2&study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-AE08&virtualslice=parti_value&part1subset=12&measuretype=4&headers=Sesjon&mode=cube&submode=timeline&Sesjonslice=141&v=2&charttype=null&cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-AE08_C1&part2slice=1&part2subset=1+-+7&virtualsubset=parti_value&part1slice=12&Sesjonsubset=138+-+141&layers=virtual&layers=part1&measure=common&top=yes" target="_blank">FrP-utbryternes (UAVH.) samarbeidmønster på Stortinget 1993-97</a></li>
    </ul>
</c:if>

</div>

<div id="main" class="superwide">
    <div class="breadcrumbs">
        <c:if test="${no}">
            Du er her:
            <a href="<p:url value="/"/>">PolSys</a>
            > <a href="<p:url value="/storting/" />"> Storting </a>
            > Bakgrunn
        </c:if>
        <c:if test="${en}">
            You are here:
            <a href="<p:url value="/"/>">PolSys</a>
            > <a href="<p:url value="/storting/"/>">Parliament</a>
            > Background
        </c:if>
    </div>
    <div>
        <c:if test="${no}"><a href="<m:url value="/en/storting/votering/bakgrunn/" />">View this page in English.</a></c:if>
        <c:if test="${en}"><a href="<m:url value="/storting/votering/bakgrunn/" />">View this page in Norwegian.</a></c:if>
    </div>

    <c:if test="${no}"><h1>NSDs Uenighetsindeks 1979-2015</h1></c:if>
    <c:if test="${en}"><h1>NSD's Disagreement Index 1979-2015</h1>
        <h3>This page is not translated to English</h3>
        <p>----------------------------------------------</p>
    </c:if>


<c:if test="${no}">
    <h3>Bakgrunn</h3>

    <p>Stortingsrepresentantenes atferd ved votering kan fortelle noe om partisystemets form og tydeliggjøre eventuelle endrede konstellasjoner. En innfallsvinkel er å konstruere uenighetsindekser, dvs. indekser som søker å tallfeste graden av uenighet  mellom to og to partier. For å få til dette har vi tatt utgangspunkt i representantenes voteringer i Storting og Odelsting. Vi fordeler
        representantenes for-stemmer på de ulike partiene og beregner så partienes for-stemmer i prosent av det totale stemmeantallet. Ved å sammenligne
        for-stemmene for to partier kan en si noe om avstanden mellom partiene. Har alle
        representantene for begge partier stemt for i en votering, vil andelen for-stemmer være 100 % for begge partier og det vil i så måte ikke være noe
        avstand mellom partiene. Samme resultat – ingen uenighet – vil vi få dersom alle i de to partiene stemte mot. Avstanden mellom to
        partier når følgelig sitt maksimum når alle representantene i det ene partiet stemmer for et forslag, mens alle representantene i den
        andre partiet stemmer mot forslaget. Matematisk beregner vi avstanden ved å ta differansen (absoluttverdien) mellom andelen for-stemmer mellom de par av partier som studeres. Indeksen uttrykker dermed den gjennomsnittlige  avstanden mellom andelen for-stemmer i parti A og B, og varierer mellom 0 (ingen uenighet) og 100 (maksimal  uenighet).</p>

    <p>Uenighetsindeksen strekker seg tilbake til høstsesjonen i 1979 og er oppdatert til og med vårsesjonen i 2013. Materialet kan deles inn og analyseres med bakgrunn i blant annet sesjon og emne. </p>

    <p>Se <a href="http://www.nsd.uib.no/polsys/index.cfm?urlname=polsys&lan=&institusjonsnr=1&arkivnr=14&MenuItem=N1_1&ChildItem=&State=collapse" target="_blank">Voteringsarkivet</a> for generell informasjon om rådataene bak Uenighetsindeksen. </p>

    <h3>Bruk</h3>

    <p>Statistikkverktøyet i <a href="http://www.nesstar.com/" target="_blank">Nesstar</a> gjør det mulig å lage egne tabeller, diagrammer og figurer. Slik går du frem i <a href="http://polsysdata.nsd.uib.no/webview/index.jsp?headers=Sesjon&stubs=part1&stubs=part2&measure=common&virtualslice=parti_value&layers=virtual&study=http%3A%2F%2F129.177.90.185%3A80%2Fobj%2FfStudy%2FUI-S08&mode=documentation&virtualsubset=parti_value&v=2&part1subset=14+-+21%2C31+-+81%2C205&measuretype=4&submode=ddi&submode=abstract&part2subset=14+-+21%2C31+-+81%2C205&cube=http%3A%2F%2F129.177.90.185%3A80%2Fobj%2FfCube%2FUI-S08_C1&Sesjonsubset=124+-+159&top=yes">Uenighetsindeksen</a>:</p>

    <ol>
        <li>Velg den kategorien du er interessert i å studere. Du har her to valg: <a href="http://polsysdata.nsd.uib.no/webview/index.jsp?headers=Sesjon&stubs=part1&stubs=part2&measure=common&virtualslice=parti_value&layers=virtual&study=http%3A%2F%2F129.177.90.185%3A80%2Fobj%2FfStudy%2FUI-S08&mode=cube&v=2&virtualsubset=parti_value&part1subset=14+-+21%2C31+-+81%2C205&measuretype=4&part2subset=14+-+21%2C31+-+81%2C205&Sesjonsubset=124+-+159&cube=http%3A%2F%2F129.177.90.185%3A80%2Fobj%2FfCube%2FUI-S08_C1&top=yes">'Alle saker'</a> og <a href="http://polsysdata.nsd.uib.no/webview/index.jsp?headers=Sesjon&stubs=part1&stubs=part2&measure=common&Emnesubset=1&virtualslice=parti_value&layers=Emne&layers=virtual&study=http%3A%2F%2F129.177.90.185%3A80%2Fobj%2FfStudy%2FUI-E08&Emneslice=1&mode=cube&v=2&virtualsubset=parti_value&part1subset=14+-+21%2C31+-+81%2C205&measuretype=4&part2subset=14+-+21%2C31+-+81%2C205&Sesjonsubset=124+-+159&cube=http%3A%2F%2F129.177.90.185%3A80%2Fobj%2FfCube%2FUI-E08_C1&top=yes">'Emne'</a>. </li>
        <li>Ved hjelp av menyvalget kan du velge partier, emner og tidsperiode.</li>
        <li>Få frem diagrammer og figurer ved å trykke på ikonene øverst til høyre i menyen. </li>
    </ol>
    <h3>Vilkår</h3>

    <p>(En del av) de data som er benyttet i denne publikasjonen er hentet fra  NSD - Norsk senter for forskningsdata Voteringsarkiv.  Materialet er stilt til disposisjon av Stortingsarkivet og tilrettelagt  av NSD. Verken Stortingsarkivet eller NSD er ansvarlige for analyser eller tolkninger som blir gjort på bakgrunn av Uenighetsindeksen. </p>
    <p></p>

</c:if>

<c:if test="${en}">
    <h3>Background</h3>

    <p>Based on the Norwegian MPs voting records, NSD has constructed an index that expresses political differences and changing coalitions in the parliament (Storting). The index takes into account each representative’s voting behaviour. In each voting, the representatives are voting “Yes” or “No”. If every representative from Party A vote “Yes”, and every representative from Party B vote “No”, there is a 100 percent disagreement between the two parties. Likewise, if every representative from both parties votes “Yes”, there is a 0 percent disagreement between the two parties. Based on the above, it is possible to measure the political distance between the parties in the Storting. The index expresses the mean distance between the number of “Yes”-votes for Party A and B, and varies between 0 (no disagreement) and 100 (maximum disagreement). </p>

    <p>The disagreement index includes data going back to 1979. See <a href="http://www.nsd.uib.no/polsys/index.cfm?urlname=parliament&amp;lan=eng&amp;institusjonsnr=1&amp;arkivnr=14&amp;MenuItem=N1_1&amp;ChildItem=&amp;State=collapse" target="_blank">Archive of Voting Records</a> for more information about the data.</p>

    <h3>Usage</h3>
    <p>The statistical tool Nesstar enables you to customize tables, diagrams and figures. Follow this step-wise procedure:</p>

    <ol>

        <li>Choose the category you are interested in studying. You have three options: ‘Alle saker’, ‘Emne’, and ‘Stortingskomité’.</li>
        <li>The menu enables you to choose between different ‘Partier’, ‘Emner/stortingskomiteer’ og ‘Tidsperiode’.</li>
        <li>Diagrams and figures are available by clicking on the icons in the top-right corner.</li>
    </ol>


    <h3>Terms of use</h3>
    <p>(Some of) the data applied in the analyses in this publication are based on Norwegian Social Science Data Services' (NSD's) Archive of Voting Records. The data are provided by the Department of History, University of Oslo, and prepared and made available by NSD. Neither the Department of History, University of Oslo, nor NSD are responsible for the analyses/interpretation of data presented here.</p>

    <h3>Contact</h3>
    <p>If you have any enquiries or questions, or detect any errors, please send an email to polsys@nsd.uib.no. </p>

</c:if>

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>