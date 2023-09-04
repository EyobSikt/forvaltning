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

<h1>Parametre - Felles</h1>

<p>Tabellen viser alle parametre i databasen.</p>

<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>

<table class="check zebra">
<caption>t_felles_parametre</caption>
<thead>
<tr><th></th><th>parameternavn</th><th>parameterverdi</th><th>dokumentasjon</th></tr>
</thead>
<tbody>
<c:forEach items="${parametre}" var="p">
<tr>
<form action="<c:url value="/felles/lagreparameter.p" />" method="post">
<td><input type="submit" value="endre" /></td>
<td><input type="text" size="30" name="parameternavn" value="${fn:escapeXml(p[0])}" /></td>
<td><input type="text" size="55" name="parameterverdi" value="${fn:escapeXml(p[1])}" /></td>
<td><input type="text" size="100" name="dokumentasjon" value="${fn:escapeXml(p[2])}" /></td>
</form>
</tr>
</c:forEach>
<tr>
<form action="<c:url value="/felles/lagreparameter.p" />" method="post">
<td><input type="submit" value="ny" /></td>
<td><input type="text" size="30" name="parameternavn" value="" /></td>
<td><input type="text" size="55" name="parameterverdi" value="" /></td>
<td><input type="text" size="100" name="dokumentasjon" value="" /></td>
</form>
</tr>

</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
