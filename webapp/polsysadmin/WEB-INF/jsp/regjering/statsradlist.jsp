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

    <div class="breadcrumbs">
        Du er her: <a href="/polsysadmin">Polsys</a>
        >
        <a href="<p:url value="/regjering/index.p"/>">regjering</a>
        > Statsråder
    </div>

<h1>Regjering statsråder - administrasjonsside</h1>

<p>Tabellen viser alle statsråder registrert i databasen.</p>

<%--
<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>
--%>


<table class="zebra text">
<caption>PS: Norske_politikere og Norske_statsraader_ny tabeller blir oppdatert</caption>
<thead>
<tr><th></th><!--<th>personkode</th>--><th>Statsråd etternavn</th><th>Statsråd fornavn</th><th>Født dd-mm-yyyy</th><th>Død dd-mm-yyyy</th><th>Parti</th><th>Kjønn</th><th>Periodestart</th></tr>
</thead>
<tbody>

<tr>
<form action="<c:url value="/regjering/registerstatsrad.p" />" method="post">

<td><input type="submit" value="reg ny" /></td>
<td><input type="text" size="18" name="etternavn" value=""  />
    <c:if test="${param.lagret != null}">
        <h4 style="color:green;">-- Ny statsråd registrert OK --</h4>
    </c:if>
</td>
<td><input type="text"  size="18" name="fornavn" value=""  />
</td>
<td><input type="text" size="21" name="foedt" value="" /></td>
<td><input type="text" size="21" name="doedt" value="" /></td>
<td>
    <select name="partikode">
        <option value="-1"></option>
        <c:forEach items="${partnavnilist}" var="e">
            <option value="${e.partikode}"> ${fn:escapeXml(e.partinavn)} </option>
        </c:forEach>
    </select></td>
  <td> <select name="kjoenn">
      <option value="-1"></option>
      <option value="1">Mann</option>
      <option value="2">Kvinne</option>
  </select></td>
    <td> <select name="periodestart">
        <option value="-1"></option>
        <option value="2013-2017">2013-2017</option>
        <option value="2017-2021">2017-2021</option>
    </select>
</form>
</tr>

<c:forEach items="${statsradlist}" var="e">
<tr>

<input type="hidden" name="personkode" value="${e.personkode}" />
<form action="<c:url value="/regjering/slettestatsrad.p" />" method="get" name="frmIndex">
    <input type="hidden" name="person_id" value="${e.personkode}" />
    <td><input type="submit" value="slett" /></td>
</form>
<%--<td></td> --%>
<%--<td>${e.personkode}</td>--%>
<td><a href="<p:url value="/regjering/statsradinfo.p?person_id=${e.personkode}" />">  ${fn:escapeXml(e.etternavn)}  </a></td>
<td>${fn:escapeXml(e.fornavn)}</td>
<td>
    <form class="" action="<p:url value="/regjering/lagrestatsradfoedt.p"/>" method="get" name="frmIndex" >
        <input type="hidden" name="person_id" value="${e.personkode}" />
        <input type="text" size="10" name="foedt" value="${fn:escapeXml(e.foedtaar)}" />
        <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
    </form>
</td>
<td>
    <form class="" action="<p:url value="/regjering/lagrestatsraddoedt.p"/>" method="get" name="frmIndex" >
        <input type="hidden" name="person_id" value="${e.personkode}" />
        <input type="text" size="10" name="doedt" value="${fn:escapeXml(e.doedsaar)}" />
        <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
    </form>
</td>
<td>${fn:escapeXml(e.parti)}</td>
</tr>
</c:forEach>
</tbody>
</table>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
