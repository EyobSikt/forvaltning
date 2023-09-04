<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over andel ansatte i Oslo vs. resten av landet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Lokalisering - Statsansatte - Forvaltningsdatabasen" />
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
> Lokalisering
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/stateemployees.html">Employees</a>
> Localisation
</c:if>
</div>

<h1>Lokalisering av statsansatte</h1>

<p>Denne siden viser andelen statsansatte basert på lokalisering i fire kategorier:
    Oslo, andre fylker i Norge utenom Oslo, utlandet og uspesifisert lokalisering (Forsvaret).
    Prosentandelene er i forhold til alle statsansatte/alle årsverk i staten.</p>


<form action="" method="get">
<p>
${en ? "Show:" : "Vis:"}
<select size="1" name="vis">
<option value="1" ${param.vis eq '1' ? 'selected="selected"' : ''} >${en ? "Number of employees" : "Antall ansatte"}</option>
<option value="2" ${param.vis eq '2' ? 'selected="selected"' : ''} >${en ? "Work year equivalent" : "Antall årsverk"}</option>
</select>

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


<c:if test="${en}">
<p><strong>This page is only available in Norwegian.</strong></p>
</c:if>

<c:import url="/WEB-INF/jspf/forvaltning/sst_disclaimer.jsp" />
<c:import url="/WEB-INF/jspf/forvaltning/statsansatte_disclaimer.jsp" />

<c:if test="${param.lk ==null || param.lk =='0' || param.lk=='2'}">  <c:set var="tabellcaption" value="ordinært regulativlønte"> </c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='3'}">  <c:set var="tabellcaption" value="lønnskategori 3"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk=='4'}">  <c:set var="tabellcaption" value="lønnskategori 4"></c:set></c:if>
<c:if test="${param.lk !=null && param.lk =='a'}">  <c:set var="tabellcaption" value="alle lønnskategorier"></c:set></c:if>

<c:if test="${param.vis == null || param.vis eq '1'}">
<p:agraf tittel="Lokalisering av statsansatte (${p:sgln(param.lk)})"
        aarsverk="${false}"
        total="${totalLand}"
        data1="${oslo}"
        data2="${resten}"
        data3="${utlandet}"
        data4="${uspesifisert}"
        label1="Oslo"
        label2="Andre spesifiserte fylker i Norge"
        label3="Utlandet"
        label4="Uspesifisert lokalisering (Forsvaret)"
        />
</c:if>

<c:if test="${param.vis eq '2'}">
<p:agraf tittel="Lokalisering av årsverk i staten (${p:sgln(param.lk)})"
        aarsverk="${true}"
        total="${totalLand}"
        data1="${oslo}"
        data2="${resten}"
        data3="${utlandet}"
        data4="${uspesifisert}"
        label1="Oslo"
        label2="Andre spesifiserte fylker i Norge"
        label3="Utlandet"
        label4="Uspesifisert lokalisering (Forsvaret)"
        />
</c:if>


<%-- ========================== Antall ==================================  --%>
<c:if test="${param.vis == null || param.vis eq '1'}">
<table class="zebra sortable">
<caption>Lokalisering av statsansatte (<%--${p:sgln(param.lk)}--%> ${tabellcaption})</caption>

<thead>
<tr>
<th></th>
<th>Total</th>
<th colspan="2">Oslo</th>
<th colspan="2">Andre spesifiserte fylker i Norge</th>
<th colspan="2">Utlandet</th>
<th colspan="2">Uspesifisert lokalisering (Forsvaret)</th>
</tr>

<tr>
<th>År</th>
<th>Antall</th>
<th>Antall</th><th>Andel %</th>
<th>Antall</th><th>Andel %</th>
<th>Antall</th><th>Andel %</th>
<th>Antall</th><th>Andel %</th>
</tr>
</thead>

<tbody>
<c:forEach items="${totalLand}" var="t">
<tr>

<td>${t.key}</td>

<td>${t.value.ansatte}</td>

<td>${oslo[t.key].ansatte}</td>
<td><fmt:formatNumber value="${(oslo[t.key].ansatte / t.value.ansatte)*100}" pattern="0.0" /></td>

<td>${resten[t.key].ansatte}</td>
<td><fmt:formatNumber value="${(resten[t.key].ansatte / t.value.ansatte)*100}" pattern="0.0" /></td>

<td>${utlandet[t.key].ansatte}</td>
<td><fmt:formatNumber value="${(utlandet[t.key].ansatte / t.value.ansatte)*100}" pattern="0.0" /></td>

<td>${uspesifisert[t.key].ansatte}</td>
<td><fmt:formatNumber value="${(uspesifisert[t.key].ansatte / t.value.ansatte)*100}" pattern="0.0" /></td>

</tr>
</c:forEach>
</tbody>

</table>
<p class="tablenote">
Prosentandelene er i forhold til alle statsansatte (${p:sgln(param.lk)}) i staten.
</p>
</c:if>
<%-- ====================================================================  --%>


<%-- ========================== Årsverk =================================  --%>
<c:if test="${param.vis eq '2'}">
<table class="zebra sortable">
<caption>Lokalisering av årsverk i staten (${p:sgln(param.lk)})</caption>

<thead>
<tr>
<th></th>
<th>Total</th>
<th colspan="2">Oslo</th>
<th colspan="2">Andre spesifiserte fylker i Norge</th>
<th colspan="2">Utlandet</th>
<th colspan="2">Uspesifisert lokalisering (Forsvaret)</th>
</tr>

<tr>
<th>År</th>
<th>Antall</th>
<th>Antall</th><th>Andel %</th>
<th>Antall</th><th>Andel %</th>
<th>Antall</th><th>Andel %</th>
<th>Antall</th><th>Andel %</th>
</tr>
</thead>

<tbody>
<c:forEach items="${totalLand}" var="t">
<tr>

<td>${t.key}</td>

<td><fmt:formatNumber value="${t.value.aarsverk}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${oslo[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(oslo[t.key].aarsverk / t.value.aarsverk)*100}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${resten[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(resten[t.key].aarsverk / t.value.aarsverk)*100}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${utlandet[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(utlandet[t.key].aarsverk / t.value.aarsverk)*100}" pattern="0.0" /></td>

<td><fmt:formatNumber value="${uspesifisert[t.key].aarsverk}" pattern="0.0" /></td>
<td><fmt:formatNumber value="${(uspesifisert[t.key].aarsverk / t.value.aarsverk)*100}" pattern="0.0" /></td>

</tr>
</c:forEach>
</tbody>

</table>
<p class="tablenote">
Prosentandelene er i forhold til alle årsverk i staten (${p:sgln(param.lk)}).
</p>
</c:if>
<%-- ====================================================================  --%>



</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
