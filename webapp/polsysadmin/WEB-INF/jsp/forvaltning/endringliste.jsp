<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>


<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Enhet #${param.idnum}</h1>


<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>


<p:tabell value="${tab}" />

<h3><a href="<c:url value="/forvaltning/endring.p?idnum=${param.idnum}" />">[opprett ny endring]</a></h3>

<br />
<br />

<%-- ======================================= KOMMUNE ====================  --%>
<h3>Kommune</h3>

<form action="<c:url value="/forvaltning/lagrekommuneenhet.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<p>
<span>kommunenr: <input type="text" size="5" name="kommunenr" value="${kommune.kode}" /></span>
 <span>kommunenavn: <input type="text" size="20" name="kommunenavn" value="${kommune.tekstEntall}" /></span>
 <span>Fraår: <input type="text" size="5" name="fomAar" value="${kommune.fomAar}" /></span>
 <span>Tilår: <input type="text" size="5" name="tomAar" value="${kommune.tomAar}" /></span>
    <%--(${kommune.kode} ${kommune.tekstEntall})--%>
   <input type="submit" value="Oppdater kommune" />
    </p>
</form>
    <%-- ====================================================================  --%>



<%-- ========================== ANSATTE-KODE-KOBLING ====================  --%>
<table class="check zebra">
<caption>SST Etatskoder</caption>
<thead>
<tr><th></th><th>Etatkode</th><th>Fra år</th><th>Til år</th><th>Intern kommentar/dokumentasjon</th></tr>
</thead>

<tbody>

<c:forEach items="${ansatteKoder}" var="a">
<tr>
<form action="<c:url value="/forvaltning/slettansattekode.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<input type="hidden" name="id" value="${a.id}" />
<input type="hidden" name="etatkode" value="${fn:escapeXml(a.etatkode)}" />
<td>
    <input type="submit" name="action" value="endre" />
    <input type="submit" name="action" value="slett" />
</td>
<td><input type="text" size="11" name="ny_etatkode" value="${fn:escapeXml(a.etatkode)}" /></td>
<td><input type="text" size="6" name="fra_aar" value="${a.fraAar}" /></td>
<td><input type="text" size="6" name="til_aar" value="${a.tilAar}" /></td>
<td><input type="text" size="50" name="dok" value="${fn:escapeXml(a.dok)}" /></td>
</form>
</tr>
</c:forEach>

<tr>
<form action="<c:url value="/forvaltning/lagreansattekode.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="11" name="etatkode" value="" /></td>
<td><input type="text" size="6" name="fra_aar" value="" /></td>
<td><input type="text" size="6" name="til_aar" value="" /></td>
<td><input type="text" size="50" name="dok" value="" /></td>
</form>
</tr>

</tbody>

</table>
    <%-- ====================================================================  --%>
    <br />
    <br />
    <%-- =============================== Ogranisasjonsprinsipp ====================  --%>

    <table class="data check zebra">
        <caption>Organisasjonsprinsipp
        </caption>
        <thead>
        <tr><th></th><th>OrgprinsippId</th><th>Norsk orgprinsipp</th><th>Engelsk orgprinsipp</th><th>Fra tidspunkt</th><th>Til tidspunkt</th><th>Kommentar</th> </tr>
        </thead>
        <tbody>
        <c:forEach items="${orgprinsipp}" var="a">
            <tr>
                <form action="<c:url value="/forvaltning/endreorgprinsipp.p" />" method="post">
                    <input type="hidden" name="id" value="${a.id}" />
                    <input type="hidden" name="idnum" value="${param.idnum}" />
                    <td>
                        <input type="submit" name="action" value="slett" />
                        <input type="submit" name="action" value="endre" />
                    </td>
                    <td><input type="text" size="10" name="orgprinsipp_id" value="${a.orgprinsippId}" /></td>
                    <td><input type="text" size="20" name="orgprinsipp" value="${fn:escapeXml(a.norskOrgPrinsipp)}" /></td>
                    <td><input type="text" size="20" name="engelskorgprinsipp" value="${fn:escapeXml(a.engelskOrgPrinsipp)}" /></td>
                    <fmt:formatDate value="${a.fraTidspunkt}" pattern="d" var="fradag" />
                    <fmt:formatDate value="${a.fraTidspunkt}" pattern="M" var="framaaned" />
                    <fmt:formatDate value="${a.fraTidspunkt}" pattern="yyyy" var="fraaar" />
                    <td>
                        Dag: <input type="text" name="fradag" value="${fradag}" size="3" />
                        Måned: <input type="text" name="framaaned" value="${framaaned}" size="3" />
                        År: <input type="text" name="fraaar" value="${fraaar}" size="6" />
                            <%-- <input type="text" size="10" name="fratidspunkt" value="${fradag}-${framaaned}-${fraaar}" />--%>
                    </td>
                    <fmt:formatDate value="${a.tilTidspunkt}" pattern="d" var="tildag" />
                    <fmt:formatDate value="${a.tilTidspunkt}" pattern="M" var="tilmaaned" />
                    <fmt:formatDate value="${a.tilTidspunkt}" pattern="yyyy" var="tilaar" />
                    <td>
                        Dag: <input type="text" name="tildag" value="${tildag}" size="3" />
                        Måned: <input type="text" name="tilmaaned" value="${tilmaaned}" size="3" />
                        År: <input type="text" name="tilaar" value="${fraaar}" size="6" />
                        <%--<input type="text" size="10" name="tiltidspunkt" value="${tildag}-${tilmaaned}-${tilaar}" />--%>
                    </td>
                    <td><input type="text" size="50" name="kommentar" value="${fn:escapeXml(a.kommentar)}" /></td>
                </form>
            </tr>
        </c:forEach>
        </tbody>

    </table>
<br />
    <table class="data check zebra">

        <thead>
        <tr><th></th><th>Orgprinsipp</th><th>Fra tidspunkt</th><th>Til tidspunkt</th><th>Kommentar</th> </tr>
        </thead>
        <tbody>

        <tr>
            <form action="<c:url value="/forvaltning/nyorgprinsipp.p" />" method="post">
                <input type="hidden" name="idnum" value="${param.idnum}" />
                <td><input type="submit" value="reg ny" /></td>
                <td>
                    <select name="orgprinsipp_id"  style="width: 150px">
                     <option value="" selected disabled hidden>Velg orgprinsipp her</option>
                  <c:forEach items="${allorgprinsipp}" var="loop" >
                  <option value="${loop.orgprinsippId}">
                            ${loop.norskOrgPrinsipp}
                    </option>
                </c:forEach>
                    </select>
                </td>
                <td>Dag: <input type="text" name="fradag" value="" size="3" />
                    Måned: <input type="text" name="framaaned" value="" size="3" />
                    År: <input type="text" name="fraaar" value="" size="6" /></td>
                <td>Dag: <input type="text" name="tildag" value="" size="3" />
                    Måned: <input type="text" name="tilmaaned" value="" size="3" />
                    År: <input type="text" name="tilaar" value="" size="6" /></td>
                <td><input type="text" size="50" name="kommentar" value="" /></td>
            </form>
        </tr>

        </tbody>

    </table>
    <%-- ====================================================================  --%>
    <br />
    <br />


<%-- =============================== ID til norge.no ====================  --%>
<h3>ID til norge.no</h3>

<form action="<c:url value="/forvaltning/lagrenorgeno.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<p>
<input type="text" size="10" name="norgeno_id" value="${norgenoId}" />
(${norgenoId})
<input type="submit" value="Oppdater norge.no-ID" />
</p>
</form>
<%-- ====================================================================  --%>

<br />
<br />


<%-- ============================= SELSKAPSDATABASEN ====================  --%>
<h3>ID i NSD sin Selskapsdatabase</h3>

<form action="<c:url value="/forvaltning/lagreselskap.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<p>
<input type="text" size="10" name="selskapsdb_id" value="${selskapsdbId}" />
(${selskapsdbId})
<input type="submit" value="Oppdater selskapsdb_id" />
</p>
</form>
<%-- ====================================================================  --%>


<%-- ============================= DBH DATABASEN ====================  --%>

    <table class="data check zebra">
        <caption>ID i NSD sin DBH databasen</caption>
        <thead>
        <tr><th></th><th>Valgtårstall</th><th>Valgtinstkode</th><th>Kommentar</th></tr>
        </thead>

        <tbody>
        <c:forEach items="${dbhdbLink}" var="a">
            <tr>
                <form action="<c:url value="/forvaltning/endredbhinstkode.p" />" method="post">
                    <input type="hidden" name="id" value="${a.id}" />
                    <input type="hidden" name="idnum" value="${param.idnum}" />
                    <td><input type="submit" name="action" value="slett" /><input type="submit" name="action" value="endre" /></td>
                    <td><input type="text" size="4" name="aar" value="${a.aar}" /></td>
                    <td><input type="text" size="20" name="dbhinstkode" value="${fn:escapeXml(a.dbh_instkode)}" /></td>
                    <td><input type="text"  name="kommentar" value="${a.kommentar}" /></td>

                </form>
            </tr>
        </c:forEach>

        <tr>
            <form action="<c:url value="/forvaltning/nydbhinstkode.p" />" method="post">
                <input type="hidden" name="idnum" value="${param.idnum}" />
                <td><input type="submit" value="reg ny" /></td>
                <td><input type="text" size="4" name="aar" value="" /></td>
                <td><input type="text" size="20" name="dbhinstkode" value="" /></td>
                <td><input type="text"  name="kommentar" value="" /></td>

            </form>
        </tr>

        </tbody>

    </table>

<%-- ====================================================================  --%>



<%-- =================================== SATELLITTER ====================  --%>
<table class="check zebra">
<caption>Avdelinger/Satellitter</caption>
<thead>
<tr>
<th></th>
<th>id</th>
<th>Fra (D.M.ÅÅÅÅ)</th>
<th>Til (D.M.ÅÅÅÅ)</th>
<th>navn</th>
<th>eng_navn</th>
<th>kommunenr</th>
<th>dokumentasjon</th>
<th>eng_dokumentasjon</th>
<th>SST-Etatskoder</th>
</tr>
</thead>

<tbody>
<c:forEach items="${satellitter}" var="s">
<tr>
<form action="<c:url value="/forvaltning/lagresatellitt.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<input type="hidden" name="id" value="${s.id}" />
<td><input type="submit" name="action" value="slett" /><input type="submit" name="action" value="endre" /></td>
<td>${s.id}</td>
<td><input type="text" size="10" name="fra_tidspunkt" value="<fmt:formatDate value="${s.fraTidspunkt}" pattern="d.M.yyyy" />" /></td>
<td><input type="text" size="10" name="til_tidspunkt" value="<fmt:formatDate value="${s.tilTidspunkt}" pattern="d.M.yyyy" />" /></td>
<td><input type="text" size="50" name="navn" value="${fn:escapeXml(s.navn)}" /></td>
<td><input type="text" size="30" name="eng_navn" value="${fn:escapeXml(s.engelskNavn)}" /></td>
<td><input type="text" size="5" name="kommunenr" value="${s.kommune.kode}" /></td>
<td><textarea name="dokumentasjon" wrap="soft" cols="50" rows="5">${fn:escapeXml(s.dokumentasjon)}</textarea></td>
<td><textarea name="eng_dokumentasjon" wrap="soft" cols="50" rows="5">${fn:escapeXml(s.engelskDokumentasjon)}</textarea></td>
<td><input type="text" size="50" name="ansattekoder" value="${fn:escapeXml(s.ansatteKoder)}" /></td>
</form>
</tr>
</c:forEach>

<tr>
<form action="<c:url value="/forvaltning/lagresatellitt.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" value="reg ny" /></td>
<td></td>
<td><input type="text" size="10" name="fra_tidspunkt" value="" /></td>
<td><input type="text" size="10" name="til_tidspunkt" value="" /></td>
<td><input type="text" size="50" name="navn" value="" /></td>
<td><input type="text" size="30" name="eng_navn" value="" /></td>
<td><input type="text" size="5" name="kommunenr" value="" /></td>
<td><textarea name="dokumentasjon" wrap="soft" cols="50" rows="5"></textarea></td>
<td><textarea name="eng_dokumentasjon" wrap="soft" cols="50" rows="5"></textarea></td>
<td><input type="text" size="50" name="ansattekoder" value="" /></td>
</form>
</tr>

</tbody>

</table>
<%-- ====================================================================  --%>




<%-- ========================================= ORGNR ====================  --%>
<table class="check zebra">
<caption>Organisasjonsnummer</caption>
<thead>
<tr><th></th><th>Organisasjonsnummer</th></tr>
</thead>
<tbody>
<tr>
<form action="<c:url value="/forvaltning/nyttorgnr.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="10" name="orgnr" value="" /></td>
</form>

</tr>
<c:forEach items="${orgnr}" var="o">
<tr>
<form action="<c:url value="/forvaltning/slettorgnr.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<input type="hidden" name="orgnr" value="${o}" />
<td><input type="submit" value="slett" /></td>
<td>${o}</td>
</form>
</tr>
</c:forEach>
</tbody>
</table>
<%-- ====================================================================  --%>

    <%-- ========================================= virksomhetsnummer ====================  --%>
    <table class="check zebra">
        <caption>Virksomhetsnummer</caption>
        <thead>
        <tr><th></th><th>Virksomhetsnummer</th> </tr>
        </thead>
        <tbody>
        <tr>
            <form action="<c:url value="/forvaltning/nyttvirksomhetsnr.p" />" method="post">
                <input type="hidden" name="idnum" value="${param.idnum}" />
                <td><input type="submit" value="reg ny" /></td>
                <td><input type="text" size="10" name="virksomhetsnr" value="" /></td>
            </form>
        </tr>
        <c:forEach items="${virksomhetsnummer}" var="o">
            <tr>

                <form action="<c:url value="/forvaltning/slettvirksomhetsnr.p" />" method="post">
                    <input type="hidden" name="idnum" value="${param.idnum}" />
                    <input type="hidden" name="virksomhetsnr" value="${o}" />
                    <td><input type="submit" value="slett" /></td>
                    <td>${o}</td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%-- ====================================================================  --%>



<%-- ========================================= NAVN =====================  --%>
<table class="check zebra">
<caption>Andre navn på denne enheten</caption>
<thead>
<tr><th></th><th>Navn</th></tr>
</thead>

<tbody>

<tr>
<form action="<c:url value="/forvaltning/nyttenhetnavn.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="80" name="navn" value="" /></td>
</form>
</tr>

<c:forEach items="${navn}" var="n">
<tr>
<form action="<c:url value="/forvaltning/slettenhetnavn.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<input type="hidden" name="id" value="${n[0]}" />
<td><input type="submit" value="slett" /></td>
<td>${n[1]}</td>
</form>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ========================================= ÅRSMELDINGER =============  --%>
<table class="data check zebra">
<caption>Årsmeldinger</caption>
<thead>
<tr><th></th><th>År</th><th>Norsk</th><th>Engelsk</th><th>Siste url</th></tr>
</thead>

<tbody>
<c:forEach items="${aarsmeldinger}" var="a">
<tr>
<form action="<c:url value="/forvaltning/endreaarsmelding.p" />" method="post">
<input type="hidden" name="id" value="${a.id}" />
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" name="action" value="slett" /><input type="submit" name="action" value="endre" /></td>
<td><input type="text" size="4" name="aar" value="${a.aar}" /></td>
<td><input type="text" size="20" name="norsk" value="${fn:escapeXml(a.aarsmelding)}" /></td>
<td><input type="text" size="20" name="engelsk" value="${fn:escapeXml(a.engelskAarsmelding)}" /></td>
<td><input type="text" size="50" name="url" value="${fn:escapeXml(a.sisteUrl)}" /></td>
</form>
</tr>
</c:forEach>


<tr>
<form action="<c:url value="/forvaltning/nyaarsmelding.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="4" name="aar" value="" /></td>
<td><input type="text" size="20" name="norsk" value="" /></td>
<td><input type="text" size="20" name="engelsk" value="" /></td>
<td><input type="text" size="50" name="url" value="" /></td>
</form>
</tr>

</tbody>

</table>
<%-- ====================================================================  --%>


    <%-- ========================================= Tildelingsbrev =============  --%>
    <table class="data check zebra">
        <caption>Tildelingsbrev</caption>
        <thead>
        <tr><th></th><th>År</th><th>Norsk</th><th>Engelsk</th><th>Siste url</th></tr>
        </thead>

        <tbody>
        <c:forEach items="${tildelingsbrev}" var="a">
            <tr>
                <form action="<c:url value="/forvaltning/endretildelingsbrev.p" />" method="post">
                    <input type="hidden" name="id" value="${a.id}" />
                    <input type="hidden" name="idnum" value="${param.idnum}" />
                    <td><input type="submit" name="action" value="slett" /><input type="submit" name="action" value="endre" /></td>
                    <td><input type="text" size="4" name="aar" value="${a.aar}" /></td>
                    <td><input type="text" size="20" name="norsk" value="${fn:escapeXml(a.tildelingsbrev)}" /></td>
                    <td><input type="text" size="20" name="engelsk" value="${fn:escapeXml(a.engelskTildelingsbrev)}" /></td>
                    <td><input type="text" size="50" name="url" value="${fn:escapeXml(a.sisteUrl)}" /></td>
                </form>
            </tr>
        </c:forEach>


        <tr>
            <form action="<c:url value="/forvaltning/nytildelingsbrev.p" />" method="post">
                <input type="hidden" name="idnum" value="${param.idnum}" />
                <td><input type="submit" value="reg ny" /></td>
                <td><input type="text" size="4" name="aar" value="" /></td>
                <td><input type="text" size="20" name="norsk" value="" /></td>
                <td><input type="text" size="20" name="engelsk" value="" /></td>
                <td><input type="text" size="50" name="url" value="" /></td>
            </form>
        </tr>

        </tbody>

    </table>
    <%-- ====================================================================  --%>


    <%-- ========================================= Instrukser =============  --%>

    <table class="data check zebra">
        <caption>Instrukser</caption>
        <thead>
        <tr><th></th><th>År</th><th>Norsk</th><th>Engelsk</th><th>Siste url</th></tr>
        </thead>

        <tbody>
        <c:forEach items="${instrukser}" var="a">
            <tr>
                <form action="<c:url value="/forvaltning/endreinstruks.p" />" method="post">
                    <input type="hidden" name="id" value="${a.id}" />
                    <input type="hidden" name="idnum" value="${param.idnum}" />
                    <td><input type="submit" name="action" value="slett" /><input type="submit" name="action" value="endre" /></td>
                    <td><input type="text" size="4" name="aar" value="${a.aar}" /></td>
                    <td><input type="text" size="20" name="norsk" value="${fn:escapeXml(a.instruks)}" /></td>
                    <td><input type="text" size="20" name="engelsk" value="${fn:escapeXml(a.engelskInstruks)}" /></td>
                    <td><input type="text" size="50" name="url" value="${fn:escapeXml(a.sisteUrl)}" /></td>
                </form>
            </tr>
        </c:forEach>


        <tr>
            <form action="<c:url value="/forvaltning/nyinstruks.p" />" method="post">
                <input type="hidden" name="idnum" value="${param.idnum}" />
                <td><input type="submit" value="reg ny" /></td>
                <td><input type="text" size="4" name="aar" value="" /></td>
                <td><input type="text" size="20" name="norsk" value="" /></td>
                <td><input type="text" size="20" name="engelsk" value="" /></td>
                <td><input type="text" size="50" name="url" value="" /></td>
            </form>
        </tr>

        </tbody>

    </table>

    <%-- ====================================================================  --%>




<%-- ========================================= LOVDATA ==================  --%>
<table class="check zebra">
<caption>Lovdata</caption>
<thead>
<tr><th></th><th>Lovnummer</th><th>Lovnavn</th></tr>
</thead>

<tbody>

<c:forEach items="${lovdata}" var="l">
<tr>
<form action="<c:url value="/forvaltning/slettlovdataenhet.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<input type="hidden" name="lovnummer" value="${l.nummer}" />
<td><input type="submit" value="slett" /></td>
<td>${l.nummer}</td>
<td class="tdtext">${fn:escapeXml(l.navn)}</td>
</form>
</tr>
</c:forEach>

<tr>
<form action="<c:url value="/forvaltning/nylovdataenhet.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="11" name="lovnummer" value="" /></td>
<td></td>
</form>
</tr>

</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ====================================== LITTERATUR ==================  --%>
<table class="check zebra">
<caption>Litteratur</caption>
<thead>
<tr><th></th><th>Pub_id</th><th>Fra år</th><th>Til år</th><th>Tittel</th></tr>
</thead>

<tbody>

<c:forEach items="${litteratur}" var="l">
<tr>
<form action="<c:url value="/forvaltning/slettlitteraturenhet.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<input type="hidden" name="pub_id" value="${l.pubId}" />
<td><input type="submit" value="slett" /></td>
<td>${l.pubId}</td>
<td>${l.fraAar}</td>
<td>${l.tilAar}</td>
<td class="tdtext">${fn:escapeXml(l.tittel)}</td>
</form>
</tr>
</c:forEach>

<tr>
<form action="<c:url value="/forvaltning/nylitteraturenhet.p" />" method="post">
<input type="hidden" name="idnum" value="${param.idnum}" />
<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="11" name="pub_id" value="" /></td>
<td><input type="text" size="11" name="fra_aar" value="" /></td>
<td><input type="text" size="11" name="til_aar" value="" /></td>
<td></td>
</form>
</tr>

</tbody>

</table>
<%-- ====================================================================  --%>


    <%-- ========================================= Tildelingsbrev =============  --%>
    <table class="data check zebra">
        <caption>Lenker til Arkivportalen</caption>
        <thead>
        <tr><th></th><th>Navn</th><th>Tidsrom</th><th>Forvaltningsområde</th><th>Url</th></tr>
        </thead>

        <tbody>
        <c:forEach items="${arkivportalen}" var="a">
            <tr>
                <form action="<c:url value="/forvaltning/endrearkivportalen.p" />" method="post">
                    <input type="hidden" name="id" value="${a.id}" />
                    <input type="hidden" name="idnum" value="${param.idnum}" />
                    <td><input type="submit" name="action" value="slett" /><input type="submit" name="action" value="endre" /></td>
                    <td><input type="text" size="20" name="navn" value="${a.navn}" /></td>
                    <td><input type="text" size="20" name="tidsrom" value="${fn:escapeXml(a.tidsrom)}" /></td>
                    <td><input type="text" size="20" name="forvaltningsomrade" value="${fn:escapeXml(a.forvaltningsomrade)}" /></td>
                    <td><input type="text" size="50" name="url" value="${fn:escapeXml(a.url)}" /></td>
                </form>
            </tr>
        </c:forEach>


        <tr>
            <form action="<c:url value="/forvaltning/nyarkivportalen.p" />" method="post">
                <input type="hidden" name="idnum" value="${param.idnum}" />
                <td><input type="submit" value="reg ny" /></td>
                <td><input type="text" size="20" name="navn" value="" /></td>
                <td><input type="text" size="20" name="tidsrom" value="" /></td>
                <td><input type="text" size="20" name="forvaltningsomrade" value="" /></td>
                <td><input type="text" size="50" name="url" value="" /></td>
            </form>
        </tr>

        </tbody>

    </table>
    <%-- ====================================================================  --%>

    <%-- ========================================= ORGNR ====================  --%>
    <table class="check zebra">
        <caption>Utvalg</caption>
        <thead>
        <tr><th></th><th>Utvalg (skirv <i>true</i> under f.o.m 2004)</th><th>t.o.m 2003</th><th>f.o.m 2004</th></tr>
        </thead>
        <tbody>
        <tr>
            <form action="<c:url value="/forvaltning/nyttutvalg.p" />" method="post">
                <input type="hidden" name="idnum" value="${param.idnum}" />
                <td><input type="submit" value="reg ny" /></td>
                <td><input type="text" size="10" name="utvalg" value="" /></td>
                <td><input type="text" size="10" name="utvalgaar2003" value="" /></td>
                <td><input type="text" size="10" name="utvalgaar2004" value="" /></td>
            </form>

        </tr>
        <c:forEach items="${utvalg}" var="o">
            <tr>
                <form action="<c:url value="/forvaltning/slettutvalg.p" />" method="post">
                    <input type="hidden" name="idnum" value="${param.idnum}" />
                    <input type="hidden" name="utvalg" value="${o.unr}" />
                    <input type="hidden" name="utvalgaar2003" value="${o.utvalgtom2003}" />
                    <input type="hidden" name="utvalgaar2004" value="${o.utvalgfom2004}" />
                    <td><input type="submit" value="slett" /></td>
                    <td>${o.unr}</td>
                    <td>${o.utvalgtom2003}</td>
                    <td>${o.utvalgfom2004}</td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%-- ====================================================================  --%>





</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
