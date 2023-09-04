<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over antall ansatte landsoversikt.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Statsansatte per fylke - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Employees fylker - State Administration Database" />
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
> Fylkesoversikt
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> County overview
</c:if>
</div>

<h1>Fylkesoversikt - statsansatte</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>


<form action="" method="get">
<ul>
<li>Denne siden viser statistikk over statsansatte på fylkesnivå.</li>
<li>Tips: Du kan sortere en tabell på gitt kolonne (både stigende og synkende) ved å klikke på kolonneoverskriften.</li>
<li>Klikk på et fylke for å se detaljert statistikk.</li>

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
<caption>Statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per fylke og år, ${valgtAar}</caption>
<thead>
<tr>
<th>F.nr.</th>
<th>Fylke</th>
<th>Antall ansatte</th><th>Andel %</th><th>% Stat</th><th>Antall årsverk</th><th>Andel %</th>
</tr>
</thead>
<tfoot>
<tr>
<td></td>
<th>TOTALT</th>

<td>${total[valgtAar].ansatte}</td>
<td><fmt:formatNumber value="${100.0}" pattern="0.0" /></td>

<c:set var="s" value="" />
<c:if test="${sysselsatteLand != null && sysselsatteLand != 0}">
<fmt:formatNumber value="${(total[valgtAar].ansatte / sysselsatteLand) * 100}" pattern="0.0" var="s" />
</c:if>
<td>${s}</td>

<td><fmt:formatNumber value="${total[valgtAar].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${100.0}" pattern="0.0" /></td>

</tr>
</tfoot>

<tbody>
<c:forEach items="${ansatte}" var="a">
<tr>
<td>${a.fylkenummer}</td>
<td class="tdtext"><a href="<p:url value="/data/ansatte/fylke/${a.fylkenummer}" />">${fn:escapeXml(fylker[a.fylkenummer].fylke)}</a></td>
<td>${a.ansatte}</td>
<td><fmt:formatNumber value="${(a.ansatte / total[valgtAar].ansatte)*100}" pattern="0.0" /></td>

<c:set var="s" value="" />
<c:if test="${sysselsatte[a.fylkenummer] != null && sysselsatte[a.fylkenummer] != 0}">
<fmt:formatNumber value="${(a.ansatte / sysselsatte[a.fylkenummer]) * 100}" pattern="0.0" var="s" />
</c:if>
<td>${s}</td>

<td><fmt:formatNumber value="${a.aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(a.aarsverk / total[valgtAar].aarsverk)*100}" pattern="0.0" /></td>

</tr>
</c:forEach>
</tbody>

</table>
<p class="tablenote">
Kolonnene "Andel %" viser fylkets andel av landets statsansatte/statsårsverk.
Kolonnen "% Stat" viser hvor stor andel fylkets statsansatte er av fylkets totale antall sysselsatte.
</p>




</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
