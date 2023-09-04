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

<h1>Partidoumenter - administrasjonsside</h1>

<p>Tabellen viser alle partier registrert i databasen.</p>

<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>

<table class="check zebra">
<caption>t_felles_parti_navn</caption>
<thead>
<tr><th></th><%--<th>partikode</th>--%><th>partinavn</th><th>partinavn_eng</th><th>forkortering</th><th>Dokumentasjon</th></tr>
</thead>
<tbody>

<tr>
<form action="<c:url value="/parti/lagrepartilist.p" />" method="post">
<td><input type="submit" value="reg ny" /></td>
<%--<td></td>--%>
<td><input type="text" size="50" name="partinavn" value=""  /></td>
<td><input type="text"  size="30" name="partinavneng" value=""  /></td>
<td><input type="text" size="10" name="partinavnforkorting" value="" /></td>
<td><input type="text" size="50" name="dokumentasjon" value="" /></td>
</form>
</tr>

<c:forEach items="${partnavnilist}" var="e">
<tr>
<form action="<c:url value="/parti/lagrepartilist.p" />" method="post">
<input type="hidden" name="partikode" value="${e.partikode}" />
<td><input type="submit" value="endre" /></td>
<%--<td>${e.partikode}</td>--%>
<td><input type="text" size="50" name="partinavn" value="${fn:escapeXml(e.partinavn)}" /></td>
<td><input type="text" size="30" name="partinavneng" value="${fn:escapeXml(e.partinavnENG)}" /></td>
<td><input type="text" size="10" name="partinavnforkorting" value="${fn:escapeXml(e.partinavnforkorting)}" /></td>
<td><input type="text" size="50" name="dokumentasjon" value="${e.dokumentasjon}" /></td>
</form>
</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
