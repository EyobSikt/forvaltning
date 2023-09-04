<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
 - Author(s): ET
 - Copyright NSD
 - Description: Liste over endringsnummer.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />



<div id="main" class="wide">

    <div class="breadcrumbs">
        Du er her: <a href="/polsysadmin">Polsys</a>
        >
        <a href="<p:url value="/regjering/index.p"/>">regjering</a>
        > Statsråder
    </div>

<h1>Regjering statsråder embete - administrasjonsside</h1>

<p>Tabellen viser alle embete/minister registrert i databasen.</p>



<table class="zebra text">
<caption>PS: Norske_statsraader_embete tabell blir oppdatert</caption>
<thead>
<tr><th></th><th>Embete</th></tr>
</thead>
<tbody>

<tr>
<form action="<c:url value="/regjering/registerembete.p" />" method="post">

<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="60" name="embete" value=""  />
    <c:if test="${param.lagret != null}">
        <h4 style="color:green;">-- Ny embete registrert OK --</h4>
    </c:if>
</td>

</form>
</tr>

<c:forEach items="${embetelist}" var="e">
<tr>
    <td></td>
<td> ${fn:escapeXml(e.embete)}  </a></td>

</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
