<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Oppdatere/ny endring/enhet polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<c:if test="${param.id == null && param.idnum == null}"><h1>Ny endring/enhet</h1></c:if>
<c:if test="${param.id != null}"><h1>Oppdater endring</h1></c:if>
<c:if test="${param.idnum != null}"><h1>Ny endring for eksisterende enhet</h1></c:if>


<c:if test="${endring.idnum != null}">
    <br />
    <p>--------------------> <a href="<c:url value="/forvaltning/endringliste.p?idnum=${endring.idnum}" />">Gå tilbake til endringslisten for denne idnum (uten å lagre ev. endringer)</a></p>
</c:if>


<c:if test="${param.slett != null}">
<h2 style="color:red">Bekreft slett</h2>
<form action="<c:url value="/forvaltning/slettendring.p" />" method="post">
<input type="hidden" name="id" value="${param.id}" />
<input type="hidden" name="idnum" value="${endring.idnum}" />
<input type="hidden" name="bekreft" value="ja" />
<p>Eventuelle relasjoner til denne recorden vil også bli slettet.</p>
<p>Hvis dette er eneste record til enheten, blir enheten også slettet fra databasen.</p>
<p><input type="submit" value="OK - Slett record" /></p>
</form>
</c:if>


<form action="<c:url value="/forvaltning/lagreendring.p" />" method="post">

<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>

<h3>id:</h3>
<c:if test="${param.id != null}">
<input type="hidden" name="id" value="${param.id}" />
<p>${param.id}</p>
</c:if>
<c:if test="${param.id == null}">
<p>ny - genereres</p>
</c:if>

<c:if test="${endring.idnum != null}">
<h3>idnum:</h3> <input type="text" name="idnum" value="${endring.idnum}" size="10" />
</c:if>
<c:if test="${endring.idnum == null}">
<h3>idnum:</h3> <p>ny - genereres</p>
</c:if>

<h3>endringskode:</h3>
<select name="endringskode">
<option value=""></option>
<c:forEach items="${endringskoder}" var="endringskode">
<option value="${endringskode.kode}" ${endringskode.kode eq endring.endringskode ? 'selected="selected"' : ''}>${fn:escapeXml(endringskode)}</option>
</c:forEach>
</select>

<h3>tidspunkt:</h3>
<fmt:formatDate value="${endring.tidspunkt}" pattern="d" var="dag" />
<fmt:formatDate value="${endring.tidspunkt}" pattern="M" var="maaned" />
<fmt:formatDate value="${endring.tidspunkt}" pattern="yyyy" var="aar" />
Dag: <input type="text" name="dag" value="${dag}" size="3" />
Måned: <input type="text" name="maaned" value="${maaned}" size="3" />
År: <input type="text" name="aar" value="${aar}" size="6" />

<h3>bekr_dato:</h3>
<input type="radio" name="bekr_dato" value="true" ${endring.bekreftetDato ? 'checked="checked"' : ''} /> Ja
<input type="radio" name="bekr_dato" value="false" ${!endring.bekreftetDato ? 'checked="checked"' : ''} /> Nei

<h3>endringsnummer:</h3> <input type="text" name="endringsnummer" value="${endring.endringsnummer}" size="10" />

<h3>saksnummer stortinget:</h3> <input type="text" name="stortingetsaksnummer" value="${fn:escapeXml(stortingetsaksnummer)}" size="45" /> (skill saksnummer idnum med mellomrom.)

<h3>tilknytningsform:</h3>
<select name="tilknytningsform">
<option value=""></option>
<c:forEach items="${tilknytningsformer}" var="tilknytningsform">
<option value="${tilknytningsform.kode}" ${tilknytningsform.kode eq endring.tilknytningsform ? 'selected="selected"' : ''}>${fn:escapeXml(tilknytningsform)}</option>
</c:forEach>
</select>

<h3>nivaa:</h3>
<select name="nivaa">
<option value=""></option>
<c:forEach items="${nivaaer}" var="nivaa">
<option value="${nivaa.kode}" ${nivaa.kode eq endring.nivaa ? 'selected="selected"' : ''}>${fn:escapeXml(nivaa)}</option>
</c:forEach>
</select>

<h3>cofog:</h3>
<select name="cofog">
<option value=""></option>
<c:forEach items="${cofog}" var="c">
<option value="${c.kode}" ${c.kode eq endring.cofog ? 'selected="selected"' : ''}>${fn:escapeXml(c)}</option>
</c:forEach>
</select>

<h3>kort_navn:</h3> <input type="text" name="kort_navn" value="${fn:escapeXml(endring.kortNavn)}" size="135" />

<h3>langt_navn:</h3> <input type="text" name="langt_navn" value="${fn:escapeXml(endring.langtNavn)}" size="135" />

<h3>eng_langt_navn:</h3> <input type="text" name="eng_langt_navn" value="${fn:escapeXml(endring.engelskLangtNavn)}" size="135" />

<h3>overordnet_idnum:</h3> <input type="text" name="overordnet_idnum" value="${endring.overordnetIdnum}" size="10" />

<h3>grunnenhet:</h3>
<select name="grunnenhet">
<option value=""></option>
<c:forEach items="${grunnenheter}" var="grunnenhet">
<option value="${grunnenhet.kode}" ${grunnenhet.kode eq endring.grunnenhet ? 'selected="selected"' : ''}>${fn:escapeXml(grunnenhet)}</option>
</c:forEach>
</select>

<h3>kommunenr:</h3> <input type="text" name="kommunenr" value="${endring.kommunenummer}" size="10" />

<h3>flyttbar:</h3> <input type="text" name="flyttbar" value="${endring.flyttbar}" size="10" />

<h3>dok:</h3>
<textarea name="dok" wrap="soft" cols="135" rows="10">${fn:escapeXml(endring.dok)}</textarea>

<h3>eng_dok:</h3>
<textarea name="eng_dok" wrap="soft" cols="135" rows="10">${fn:escapeXml(endring.engelskDok)}</textarea>

<h3>teknisk_kommentar:</h3>
<textarea name="teknisk_kommentar" wrap="soft" cols="135" rows="5">${fn:escapeXml(endring.tekniskKommentar)}</textarea>


<h3>Relasjon</h3>
<p>Registrer enheter (idnum) denne endringen har relasjon til. 
Relasjonsvariabelen skal kun brukes for følgende endringskoder, og har gitt mening:
</p>
<ul class="plain">
<c:forEach items="${endringskoder}" var="endringskode">
<c:if test="${endringskode.relasjontekst != null}">
<li>${endringskode.kode} - ${fn:escapeXml(endringskode.relasjontekst)}</li>
</c:if>
</c:forEach>
</ul>

<p>Relasjoner: <input type="text" name="relasjoner" value="${fn:escapeXml(relasjoner)}" size="75" /> (skill flere idnum med mellomrom.)</p>

<p>Under kan vi registrere id på relaterte enheter som vi ikke har
    (ordentlig) idnum til (som registreres over).</p>
<p>Andre relasjoner: <input type="text" name="relasjoner_andre" value="${fn:escapeXml(relasjonerAndre)}" size="75" /> (skill flere id med mellomrom.)</p>


<h2>Oppdater database</h2>
<p><input type="submit" value="OK - oppdater database" /></p>

</form>

<c:if test="${param.id != null && endring.idnum != null}">
<form action="<c:url value="/forvaltning/slettendring.p" />" method="post">
<input type="hidden" name="id" value="${param.id}" />
<input type="hidden" name="idnum" value="${endring.idnum}" />
<p>
Klikk på sletteknappen til høyre hvis du vil slette denne posten. ---------------------------------------------->
<input type="submit" value="Slett record" />
</p>
<p>Eventuelle relasjoner til denne recorden vil også bli slettet.</p>
<p>Hvis dette er eneste record til enheten, blir enheten også slettet fra databasen.</p>
</form>
</c:if>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
