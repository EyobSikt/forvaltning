<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over en bestemt litteratur/publikasjon.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Utvalgssøk - Utvalg - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Search committees - Advisory Committees - State Administration Database" />
    <c:param name="beskrivelseNo" value="Søk etter utvalg." />
    <c:param name="beskrivelseEn" value="Search advisory committees." />
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
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/utvalgsarkivet.html">Utvalg</a>
> Søk
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/advisorycommittees.html">Advisory Committees</a>
> Search
</c:if>
</div>

<h1>${en ? "Search advisory committees" : "Utvalgssøk"}</h1>

<c:if test="${no}">
<p>Søk på navn til utvalg.</p>
</c:if>
<c:if test="${en}">
<p>Search committees by name.</p>
<p class="nottranslated">Please note: The Advisory Committees pages contains data in Norwegian that is not translated to English.</p>
</c:if>

<form action="" method="get">
<input type="text" value="${param.s}" maxlength="100" size="75" name="s" />
<input class="button" type="submit" value="${en ? "Search" : "Søk"}" />
</form>

<c:if test="${param.s != null}">
<h2>${en ? "Results" : "Søkeresultat"}</h2>
</c:if>

<c:if test="${param.s != null && empty utvalg}">
<p><em>${en ? "No hits." : "Ingen treff."}</em></p>
</c:if>


<c:if test="${!(empty utvalg)}">
<table id="excelTable"  class="text zebra sortable">
<caption>${fn:length(utvalg)} ${en ? "committees" : "utvalg"}</caption>
    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float: right"  src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
<thead>
<tr>
<th>${en ? "From" : "Fra"}</th>
<th>${en ? "To" : "Til"}</th>
<th>${en ? "Function" : "Funksjon"}</th>
<th>${en ? "Name" : "Navn"}</th>
<th>${en ? "until 2003" : "t.o.m 2003"}</th>
<th>${en ? "from 2004" : "f.o.m 2004"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${utvalg}" var="u">
<tr>
<td>${u.opprettelsesaar}</td>
<td>${u.opphoersaar}</td>
<td>${fn:escapeXml(u.hovedfunksjon)}</td>
<td><a href="<p:url value="/data/utvalg/${u.unr}" />">${fn:escapeXml(u.navn)}</a></td>
<td><c:if test="${!(empty u.utvalg2003)}">✔</c:if></td>
<td><c:if test="${!(empty u.utvalg2004)}">&#10004;</c:if></td>
</tr>
</c:forEach>
</tbody>

</table>
</c:if>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
