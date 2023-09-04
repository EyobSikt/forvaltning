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
        Du er her:  <a href="/polsysadmin">Polsys</a>
        >
        <a href="<p:url value="/regjering/index.p"/>">regjering</a>
        > Statssekretar
    </div>

<h1>Regjering Statssekretær - administrasjonsside</h1>

<p>Tabellen viser alle partier registrert i databasen.</p>

<%--
<c:if test="${param.lagret != null}">
<h2 style="color:green;">-- Databasen ble oppdatert OK --</h2>
</c:if>
--%>


<table class="zebra text">
<caption>PS: Norske_politikere og Norske_statssekretaerer_ny tabeller blir oppdatert</caption>
<thead>
<tr><th></th><!--<th>personkode</th>--><th>Statssekretær etternavn</th><th>Statssekretær fornavn</th><th>Født dd-mm-yyyy</th><th>Død dd-mm-yyyy</th><th>Parti</th><th>Kjønn</th><th>Periodestart</th></tr>
</thead>
<tbody>

<tr>
    <form action="<c:url value="/regjering/registerstatssekretar.p" />" method="post">

        <td><input type="submit" value="reg ny" /></td>
        <td><input type="text" size="15" name="etternavn" value=""  />
            <c:if test="${param.lagret != null}">
                <h4 style="color:green;">-- Ny statssekrtær registrert OK --</h4>
            </c:if>
        </td>
        <td><input type="text"  size="15" name="fornavn" value=""  />
        </td>
        <td><input type="text" size="22" name="foedt" value="" /></td>
        <td><input type="text" size="22" name="doedt" value="" /></td>
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
        </select></td>
    </form>

</tr>

<c:forEach items="${statssekretarlist}" var="e">
<tr>
<%--<form action="<c:url value="/parti/lagrepartilist.p" />" method="post">   --%>
<input type="hidden" name="personkode" value="${e.personkode}" />
    <form action="<c:url value="/regjering/slettestatssekretar.p" />" method="get" name="frmIndex">
        <input type="hidden" name="person_id" value="${e.personkode}" />
        <td><input type="submit" value="slett" /></td>
    </form>
    <%--<td></td>--%>
    <!--<td>${e.personkode}</td> -->
    <td><a href="<p:url value="/regjering/statssekretarinfo.p?person_id=${e.personkode}" />">  ${fn:escapeXml(e.etternavn)}  </a></td>
    <td>${fn:escapeXml(e.fornavn)}</td>
    <td>
        <form class="" action="<p:url value="/regjering/lagrestatssekretarfoedt.p"/>" method="get" name="frmIndex" >
            <input type="hidden" name="person_id" value="${e.personkode}" />
            <input type="text" size="10" name="foedt" value="${fn:escapeXml(e.foedtaar)}" />
            <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
        </form>
    </td>
    <td>
        <form class="" action="<p:url value="/regjering/lagrestatssekretardoedt.p"/>" method="get" name="frmIndex" >
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
