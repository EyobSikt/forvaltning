<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall ansatte - etater.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Statsansatte per etat - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Employees per unit - State Administration Database" />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/statsansatte.html">Statsansatte</a>
> Etater
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> Agencies
</c:if>
</div>

<h1>Etatsoversikt - statsansatte</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>


<form action="" method="get">
<ul>
<li>Denne siden viser statistikk over statsansatte under ulike etater.</li>
<li>Tips: Du kan sortere en tabell på gitt kolonne (både stigende og synkende) ved å klikke på kolonneoverskriften.</li>
<li>Klikk på en etatkode for å se detaljert statistikk.</li>

<li>
Velg år:

<select name="aar">
<c:forEach items="${alleAar}" var="i">
<option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
</c:forEach>
</select>

${en ? "Salary category:" : "Lønnskategori:"}
<select size="1" name="lk">
<option value="2" ${param.lk eq '2' ? 'selected="selected"' : ''} >${en ? "2. Ordinary" : "2. Ordinært regulativlønte"}</option>
<option value="3" ${param.lk eq '3' ? 'selected="selected"' : ''} >${en ? "3. Contract" : "3. Overenskomst, timelønte, permisjon, kontrakter + honorar f.o.m 2016"}</option>
<option value="4" ${param.lk eq '4' ? 'selected="selected"' : ''} >${en ? "4. Leaders from 2004" : "4. Ledere på kontrakter f.o.m. 2004 t.o.m 2014"}</option>
<option value="a" ${param.lk eq 'a' ? 'selected="selected"' : ''} >${en ? "All categories" : "Alle kategorier"}</option>
</select>

<input type="submit" value="OK" />
</li>
</ul>
</form>

<c:import url="/WEB-INF/jspf/forvaltning/sst_disclaimer.jsp" />
<c:import url="/WEB-INF/jspf/forvaltning/statsansatte_disclaimer.jsp" />

<c:if test="${param.lk ==null || param.lk =='0' || param.lk=='2'}">  <c:set var="tabellcaption" value="ordinært regulativlønte"> </c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='3'}">  <c:set var="tabellcaption" value="lønnskategori 3"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='4'}">  <c:set var="tabellcaption" value="lønnskategori 4"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk =='a'}">  <c:set var="tabellcaption" value="alle lønnskategorier"></c:set></c:if>

    <table class="zebra sortable">
<caption>Statsansatte <%--(${p:sgln(param.lk)})--%>
  ${tabellcaption} per etat, år ${valgtAar}
</caption>

<thead>

<tr>
<th></th>
<th></th>
<th></th>
<th colspan="2">Ansatte</th>
<th colspan="2">Årsverk</th>
</tr>

<tr>
<th>Kode</th>
<th>Etat</th>
<th>Departementsområde</th>
<th>Antall</th>
<th>Andel %</th>
<th>Antall</th>
<th>Andel %</th>
</tr>
</thead>

<tbody>
<c:forEach items="${etater}" var="a">
<c:if test="${!empty a.etatkode and a.etatkode != null and a.etatkode!='-1' }">
<tr>
<td class="tdtext"><a href="<p:url value="/data/ansatte/etat/${fn:escapeXml(a.etatkode)}?aar=${valgtAar}" />">${fn:escapeXml(a.etatkode)}</a></td>
<td class="tdtext">${fn:escapeXml(a.etat)}</td>
<td class="tdtext">${fn:escapeXml(a.dep)}</td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${(a.ansatte / total[valgtAar].ansatte)*100}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(a.aarsverk / total[valgtAar].aarsverk)*100}" pattern="0.0" /></td>
</tr>
</c:if>
</c:forEach>
</tbody>

</table>

<p class="tablenote">
Kolonnene "Andel %" viser etatens andel av henholdsvis landets
statsansatte og andel av landets statsårsverk.
</p>




</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
