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
    <c:param name="tittelNo" value="Landsoversikt - Statsansatte - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Employees - State Administration Database" />
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
> Landsoversikt
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> Country overview
</c:if>
</div>

<h1>Landsoversikt - statsansatte</h1>

<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>

<ul>
<li>Denne siden viser statistikk over statsansatte på landsnivå.</li>
<li>Tips: Du kan sortere en tabell på gitt kolonne (både stigende og synkende) ved å klikke på kolonneoverskriften.</li>
</ul>


<form action="" method="get">
<p>
${en ? "Salary category:" : "Lønnskategori:"}
<select size="1" name="lk">
<option value="2" ${param.lk eq '2' ? 'selected="selected"' : ''} >${en ? "2. Ordinary" : "2. Ordinært regulativlønte"}</option>
<option value="3" ${param.lk eq '3' ? 'selected="selected"' : ''} >${en ? "3. Contract" : "3. Overenskomst, timelønte, permisjon, kontrakter + honorar f.o.m 2016"}</option>
<option value="4" ${param.lk eq '4' ? 'selected="selected"' : ''} >${en ? "4. Leaders from 2004" : "4. Ledere på kontrakter f.o.m. 2004 t.o.m 2014"}</option>
<option value="a" ${param.lk eq 'a' ? 'selected="selected"' : ''} >${en ? "All categories" : "Alle kategorier"}</option>
</select>

<input type="submit" value="OK" />
</p>
</form>

<c:import url="/WEB-INF/jspf/forvaltning/sst_disclaimer.jsp" />
<c:import url="/WEB-INF/jspf/forvaltning/statsansatte_disclaimer.jsp" />

<c:if test="${param.lk ==null || param.lk =='0' || param.lk=='2'}">  <c:set var="tabellcaption" value="ordinært regulativlønte"> </c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='3'}">  <c:set var="tabellcaption" value="lønnskategori 3"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='4'}">  <c:set var="tabellcaption" value="lønnskategori 4"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk =='a'}">  <c:set var="tabellcaption" value="alle lønnskategorier"></c:set></c:if>


<c:if test="${fn:length(total) >= 2}">
<h2>Grafer</h2>

<p:graf tittel="Antall statsansatte (${p:sgln(param.lk)}) per år, landsoversikt"
        aarsverk="${false}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />

<p:graf tittel="Antall årsverk i staten (${p:sgln(param.lk)}) per år, landsoversikt"
        aarsverk="${true}"
        total="${total}"
        menn="${mennTotal}"
        kvinner="${kvinnerTotal}"
        />


<h2>Tabeller</h2>
</c:if>


<%-- ============================= ANTALL ANSATTE =======================  --%>
<table class="zebra sortable">
<caption>Antall statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per år, landsoversikt</caption>

<thead>
<tr>
<th></th>
<th colspan="3">Totalt</th>
<th colspan="3">Heltid</th>
<th colspan="3">Deltid</th>
</tr>

<tr>
<th>År</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>

<td>${mennTotal[t.key].ansatte}</td>
<td>${kvinnerTotal[t.key].ansatte}</td>
<td>${t.value.ansatte}</td>

<td>${mennHeltid[t.key].ansatte}</td>
<td>${kvinnerHeltid[t.key].ansatte}</td>
<td>${heltid[t.key].ansatte}</td>

<td>${mennDeltid[t.key].ansatte}</td>
<td>${kvinnerDeltid[t.key].ansatte}</td>
<td>${deltid[t.key].ansatte}</td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ============================= ANTALL ÅRSVERK =======================  --%>
<table class="zebra sortable">
<caption>Antall årsverk i staten (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per år, landsoversikt</caption>

<thead>
<tr>
<th></th>
<th colspan="3">Totalt</th>
<th colspan="3">Heltid</th>
<th colspan="3">Deltid</th>
</tr>

<tr>
<th>År</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>

<td><fmt:formatNumber value="${mennTotal[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${kvinnerTotal[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${t.value.aarsverk}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${mennHeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${kvinnerHeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${heltid[t.key].aarsverk}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${mennDeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${kvinnerDeltid[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${deltid[t.key].aarsverk}" pattern="0.0" /></td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ============================= ANDEL SYSSELSATTE ====================  --%>
<table class="zebra sortable">
<caption>Andel statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) av landets totale sysselsatte</caption>

<thead>

<tr>
<th>År</th>
<th>Statsansatte</th>
<th>Totalt sysselsatte</th>
<th>Andel %</th>
<th>Årsverk i staten</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>
<td>${t.value.ansatte}</td>
<td>${sysselsatte[t.key]}</td>

<c:set var="s" value="" />
<c:if test="${sysselsatte[t.key] != null && sysselsatte[t.key] != 0}">
<fmt:formatNumber value="${(t.value.ansatte / sysselsatte[t.key]) * 100}" pattern="0.0" var="s" />
</c:if>
<td>${s}</td>

<td><fmt:formatNumber value="${t.value.aarsverk}" pattern="0.0" /></td>

</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>



<%-- ============================= ALDER ================================  --%>
<table class="zebra sortable">
<caption>Gjennomsnittsalder statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption}) per år, landsoversikt</caption>

<thead>
<tr>
<th></th>
<th colspan="3">Totalt</th>
<th colspan="3">Heltid</th>
<th colspan="3">Deltid</th>
</tr>

<tr>
<th>År</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
<th>Menn</th><th>Kvinner</th><th>Totalt</th>
</tr>
</thead>

<tbody>
<c:forEach items="${total}" var="t">
<tr>
<td>${t.key}</td>

<td>${mennTotal[t.key].alder}</td>
<td>${kvinnerTotal[t.key].alder}</td>
<td>${t.value.alder}</td>

<td>${mennHeltid[t.key].alder}</td>
<td>${kvinnerHeltid[t.key].alder}</td>
<td>${heltid[t.key].alder}</td>

<td>${mennDeltid[t.key].alder}</td>
<td>${kvinnerDeltid[t.key].alder}</td>
<td>${deltid[t.key].alder}</td>
</tr>
</c:forEach>
</tbody>

</table>
<%-- ====================================================================  --%>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
