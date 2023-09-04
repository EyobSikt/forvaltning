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

<p>Tabellen viser alle partidokumenter registrert i databasen.</p>

<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>

<table class="check zebra">
<caption>t_parti_dokument_info </caption>
<thead>
<tr><th></th><th>ID</th><th>Tittel</th><th>Doktype</th><th>Parti</th><th>År</th></tr>
</thead>
<tbody>
<tr>
<form action="<c:url value="/parti/lagrepartidok.p" />" method="post">
<td><input type="submit" value="reg ny" /></td>
<td><input type="text" name="doknr" value="" size="11" /></td>
<td><input type="text"  name="tittel" value="" size="50" /></td>

<td>
<select name="doktype" size=1 >
<option value=""></option>
   <option value="1">prinsipprogram</option>
   <option value="3">pressemeldinger</option>
    <option value="2">valgprogram</option>
    <option value="4">vedtekter</option>
    <option value="5">andre</option>
 </select>
 </td>
<td>
<select name="partinavn"  size=1 >
            <option value=""></option>
    <c:forEach items="${partilist}" var="p">
            <option value="${p.idnum}">${p.partinavn}</option>
</c:forEach>
 </select>
</td>
<td><input type="text" size="10" name="aar" value="" /></td>
</form>
</tr>

<c:forEach items="${enheter}" var="e">
<tr>
<form action="<c:url value="/parti/lagrepartidok.p" />" method="post">
<input type="hidden" name="id" value="${e.idnum}" />
<td><input type="submit" value="endre" /></td>
<td>${e.idnum}</td>
<td><input type="text" size="50" name="tittel" value="${fn:escapeXml(e.tittel)}" /></td>
<td>
<select name="doktype" size=1 >
<option value="${fn:escapeXml(e.doktypeid)}">${fn:escapeXml(e.doktype)}</option>
     <option value="">******endre doktype*****</option>
   <option value="1">prinsipprogram</option>
   <option value="3">pressemeldinger</option>
    <option value="2">valgprogram</option>
   <option value="4">vedtekter</option>
    <option value="5">andre</option>
 </select>
</td>
<td>
<select name="partinavn"  size=1 >
            <option value="${fn:escapeXml(e.partikode)}">${fn:escapeXml(e.partinavn)}</option>
            <option value="">******endre parti*****</option>
            <c:forEach items="${partilist}" var="p">
            <option value="${p.idnum}">${p.partinavn}</option>
</c:forEach>
 </select>
</td>
<td><input type="text" size="10" name="aar" value="${e.aar}" /></td>
</form>

</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
