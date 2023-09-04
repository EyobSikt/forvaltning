<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Liste over endringsnummer.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Andre relasjonsenheter - Forvaltning</h1>

<p>Tabellen viser alle andre relasjonsenheter i databasen.</p>

<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>

<table class="data check zebra">
<caption>t_forvaltning_relasjon_andre_enhet</caption>
<thead>
<tr><th></th><th>ID</th><th>Navn</th><th>Engelsk navn</th></tr>
</thead>
<tbody>

<tr>
<form action="<c:url value="/forvaltning/lagrerelasjonenhet.p" />" method="post">
<td><input type="submit" value="ny" /></td>
<td>(blir generert)</td>
<td><input type="text" size="50" name="navn" value="" /></td>
<td><input type="text" size="50" name="eng_navn" value="" /></td>
</form>
</tr>

<c:forEach items="${enheter}" var="e">
<tr>
<form action="<c:url value="/forvaltning/lagrerelasjonenhet.p" />" method="post">
<input type="hidden" name="id" value="${e.idnum}" />
<td><input type="submit" name="action" value="slett" /><input type="submit" name="action" value="endre" /></td>
<td>${e.idnum}</td>
<td><input type="text" size="50" name="navn" value="${fn:escapeXml(e.langtNavn)}" /></td>
<td><input type="text" size="50" name="eng_navn" value="${fn:escapeXml(e.engelskLangtNavn)}" /></td>
</form>
</tr>
</c:forEach>

</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
