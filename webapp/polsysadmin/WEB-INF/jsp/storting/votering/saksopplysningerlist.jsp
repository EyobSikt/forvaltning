<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<c:import url="/WEB-INF/jspf/topp_votering.jsp" />



<div id="main" class="wide" >

    <div class="breadcrumbs">
        Du er her:
        <a href="http://nsd241/polsysadmin/">PolSys-Admin</a>
        > <a href="http://nsd241/polsysadmin/storting/index.p">Storting</a>
        > <a href="http://nsd241/polsysadmin/storting/votering/lessaksopplysningerlist.p">Les votering saker</a>
        > Votering saker
    </div>


<h1>Votering data - administrasjonsside</h1>

<p>Tabellen viser alle SAKER  registrert i databasen for <b><%= request.getParameter("sesjon") %></b> sesjon.</p>

<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>

<table class="check zebra">
<caption>t_storting_votering_import_saksopplysninger (Antall treff: ${fn:length(sakerlist)} ) </caption>
<thead>
<tr><th></th><th>ID</th><th>SAK</th><th>VOTNR</th><th>TYPSAK</th><th>typesak_text</th><th>VOTTYP</th><th>vottype_text</th><th>SAKSREFERANSE</th><th>SAKSREGISTER</th></tr>
</thead>
<tbody>


<c:forEach items="${sakerlist}" var="e">
<tr>
<form action="<c:url value="/storting/votering/lagresaksopplysninger.p" />" method="post" >
<input type="hidden" name="id" value="${e.id}" />
    <input type="hidden" name="sesjon" value="${e.sesjon}" />
<td><input type="submit" value="endre" /></td>
<td>${e.id}</td>
    <td>${e.SAK}</td>
    <td>${e.VOTNR}</td>
<td><input type="text" size="7" name="typesak" value="${fn:escapeXml(e.TYPSAK)}" /></td>
    <td>${e.typesak_text}</td>
<td><input type="text" size="7" name="vottype" value="${fn:escapeXml(e.VOTTYP)}" /></td>
    <td>${e.vottype_text}</td>
    <td></td>
    <td>${e.SAKSREGISTER}</td>

</form>
</tr>
</c:forEach>
</tbody>
</table>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
