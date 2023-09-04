<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Liste over endringsnummer.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Lovdata - Forvaltning</h1>

<p>Tabellen viser alle lovdata i databasen.</p>

<table class="data text zebra">
<caption>t_forvaltning_lovdata</caption>
<thead>
<tr><th>(handling)</th><th>Lovnummer</th><th>URL</th><th>Lovnavn</th></tr>
</thead>
<tbody>
<tr>
<form action="<c:url value="/forvaltning/nylovdata.p" />" method="post">
<td class="tdcheck">
<input type="submit" value="reg ny" />
</td>
<td><input type="text" name="lovnummer" value="" size="11" /></td>
<td><input type="text" name="url" value="" size="50" /></td>
<td><input type="text" name="navn" value="" size="150" /></td>
</form>
</tr>
<c:forEach items="${lovdata}" var="l">
<tr>
<form action="<c:url value="/forvaltning/slettlovdata.p" />" method="post">
<td class="tdcheck">
<input type="hidden" name="lovnummer" value="${l.nummer}" />
<input type="submit" name="action" value="endre" />
<input type="submit" name="action" value="slett" />
</td>
<td>${l.nummer}</td>
<td><input type="text" name="url" value="${fn:escapeXml(l.url)}" size="50" /></td>
<td><input type="text" name="navn" value="${fn:escapeXml(l.navn)}" size="150" /></td>
</form>
</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
