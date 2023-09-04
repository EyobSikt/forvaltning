<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Oppdatere/ny litteratur/publikasjon polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<c:if test="${param.pub_id == null}"><h1>Ny litteraur/publikasjon</h1></c:if>
<c:if test="${param.pub_id != null}"><h1>Oppdater litteratur/publikasjon</h1></c:if>


<form action="<c:url value="/forvaltning/lagrelitteratur.p" />" method="post">

<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>

<h3>pub_id:</h3>
<c:if test="${param.pub_id != null}">
<input type="hidden" name="pub_id" value="${param.pub_id}" />
<p>${param.pub_id}</p>
</c:if>
<c:if test="${param.pub_id == null}">
<p>ny - genereres</p>
</c:if>

<h3>type_kode:</h3>
<select name="type_kode">
<option value=""></option>
<c:forEach items="${typer}" var="type">
<option value="${type.kode}" ${type eq litteratur.type ? 'selected="selected"' : ''}>${fn:escapeXml(type)}</option>
</c:forEach>
</select>


<h3>tittel:</h3> <input type="text" name="tittel" value="${fn:escapeXml(litteratur.tittel)}" size="135" />
<h3>utgiver:</h3> <input type="text" name="utgiver" value="${fn:escapeXml(litteratur.utgiver)}" size="135" />
<h3>aarstall:</h3> <input type="text" name="aarstall" value="${fn:escapeXml(litteratur.aar)}" size="10" />
<h3>forfatter:</h3> <input type="text" name="forfatter" value="${fn:escapeXml(litteratur.forfatter)}" size="135" />
<h3>lenke_publikasjon:</h3> <input type="text" name="lenke_publikasjon" value="${fn:escapeXml(litteratur.lenkePublikasjon)}" size="135" />
<h3>lenke_omtale:</h3> <input type="text" name="lenke_omtale" value="${fn:escapeXml(litteratur.lenkeOmtale)}" size="135" />

<h3>kommentar_ekstern:</h3>
<textarea name="kommentar_ekstern" wrap="soft" cols="135" rows="5">${fn:escapeXml(litteratur.kommentarEkstern)}</textarea>

<h3>kommentar_intern:</h3>
<textarea name="kommentar_intern" wrap="soft" cols="135" rows="5">${fn:escapeXml(litteratur.kommentarIntern)}</textarea>

<h3>ref_pub_id:</h3>
<p>
I enkelte tilfeller vil berre et kapittel i ei bok eller en rapport handle
om statsforvaltningen (og derfor være aktuelt å registrere). Dette feltet blir
då brukt for å vise til bok eller rapport kapitlet er publisert i.
</p>
<input type="text" name="ref_pub_id" value="${fn:escapeXml(litteratur.referanse.pubId)}" size="10" />

<h3>antall_sider:</h3> <input type="text" name="antall_sider" value="${fn:escapeXml(litteratur.antallSider)}" size="5" />
<h3>isbn:</h3> <input type="text" name="isbn" value="${fn:escapeXml(litteratur.isbn)}" size="20" />
<h3>issn:</h3> <input type="text" name="issn" value="${fn:escapeXml(litteratur.issn)}" size="20" />
<h3>spraak:</h3> <input type="text" name="spraak" value="${fn:escapeXml(litteratur.spraak)}" size="50" />
<h3>land:</h3> <input type="text" name="land" value="${fn:escapeXml(litteratur.land)}" size="50" />

<h3>sammendrag:</h3>
<textarea name="sammendrag" wrap="soft" cols="135" rows="5">${fn:escapeXml(litteratur.sammendrag)}</textarea>

<h3>tekstfelt_utl:</h3>
<p>
For publikasjoner som omfatter vertikale eller horisontale aspekt eller
omfatter en spesifikk enhet i andre lands forvaltninger, blir dette feltet
brukt for å dokumentere dette i prosaform.
</p>
<textarea name="tekstfelt_utl" wrap="soft" cols="135" rows="5">${fn:escapeXml(litteratur.tekstfeltUtl)}</textarea>


<h2>Oppdater database</h2>
<p><input type="submit" value="OK - oppdater database" /></p>

</form>


<%-- ============================== Tilknytningsformer ==================  --%>
<table class="check zebra">
<caption>Tilknytningsformer</caption>
<thead>
<tr><th>Kode</th><th>Fra år</th><th>Til år</th><th></th><th>Tilknytningsform</th></tr>
</thead>

<tbody>

<c:forEach items="${tilknytningsformer}" var="l">
<tr>
<form action="<c:url value="/forvaltning/slettlitteraturtilknytningsform.p" />" method="post">
<input type="hidden" name="tilknytningsform" value="${l.tilknytningsform.kode}" />
<input type="hidden" name="pub_id" value="${param.pub_id}" />
<td>${l.tilknytningsform.kode}</td>
<td>${l.fraAar}</td>
<td>${l.tilAar}</td>
<td><input type="submit" value="slett" /></td>
<td class="tdtext">${fn:escapeXml(l.tilknytningsform.tekstEntall)}</td>
</form>
</tr>
</c:forEach>

<tr>
<form action="<c:url value="/forvaltning/nylitteraturtilknytningsform.p" />" method="post">
<input type="hidden" name="pub_id" value="${param.pub_id}" />
<td><input type="text" size="11" name="tilknytningsform" value="" /></td>
<td><input type="text" size="11" name="fra_aar" value="" /></td>
<td><input type="text" size="11" name="til_aar" value="" /></td>
<td><input type="submit" value="reg ny" /></td>
<td></td>
</form>
</tr>

</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ====================================== Enheter =====================  --%>
<table class="check zebra">
<caption>Enheter</caption>
<thead>
<tr><th>Idnum</th><th>Fra år</th><th>Til år</th><th></th><th>Navn</th></tr>
</thead>

<tbody>

<c:forEach items="${enheter}" var="l">
<tr>
<form action="<c:url value="/forvaltning/slettlitteraturenhet.p" />" method="post">
<input type="hidden" name="idnum" value="${l.enhet.idnum}" />
<input type="hidden" name="pub_id" value="${param.pub_id}" />
<input type="hidden" name="tillitt" value="yes" />
<td>${l.enhet.idnum}</td>
<td>${l.fraAar}</td>
<td>${l.tilAar}</td>
<td><input type="submit" value="slett" /></td>
<td class="tdtext">${fn:escapeXml(l.enhet.langtNavn)}</td>
</form>
</tr>
</c:forEach>

<tr>
<form action="<c:url value="/forvaltning/nylitteraturenhet.p" />" method="post">
<input type="hidden" name="pub_id" value="${param.pub_id}" />
<input type="hidden" name="tillitt" value="yes" />
<td><input type="text" size="11" name="idnum" value="" /></td>
<td><input type="text" size="11" name="fra_aar" value="" /></td>
<td><input type="text" size="11" name="til_aar" value="" /></td>
<td><input type="submit" value="reg ny" /></td>
<td></td>
</form>
</tr>

</tbody>

</table>
<%-- ====================================================================  --%>





</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
