<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Liste over endringsnummer.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Endringsnummer - Forvaltning</h1>

<p>Tabellen viser alle endringsnummer i databasen.</p>

<h3><a href="<c:url value="/forvaltning/nyttendringsnummer.p" />">opprett nytt</a>.</h3>

<table class="zebra">
<caption>t_forvaltning_endringsnummer</caption>
<thead>
<tr><th>endringsnummer</th><th>antall</th></tr>
</thead>
<tbody>
<c:forEach items="${endringsnumre}" var="e">
<tr>
<td>${e[0]}</td>
<td><a href="<c:url value="/forvaltning/endringsnummerbruk.p?enummer=${e[0]}" />">${e[1]}</a></td>
</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
