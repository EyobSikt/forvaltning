<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Litteratur - tilknytningsformer.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Tilknytningsformer - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Affiliations - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Finn forvaltningslitteratur etter tilknytningsform." />
    <c:param name="beskrivelseEn" value="Literature by affiliations" />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp">
    </c:import>
</c:if>
<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no/">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningslitteratur.html">Litteratur</a>
> Tilknytningsformer
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">NSAD</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html">Literature</a>
> By affiliations
</c:if>
</div>

<h1>${en ? "Literature by affiliations" : "Litteratur per tilknytningsformer"}</h1>

<c:if test="${no}"><p>Litteratur kategorisert på tilknytningsformer.</p></c:if>
<c:if test="${en}"><p>Literature categorized by affiliations.</p></c:if>

<table class="zebra sortable">
<caption>${en ? "Number of publications by affiliations" : "Antall publikasjoner per tilknytningsform"}</caption>
<thead>
<tr>
<th>${en ? "Affiliation" : "Tilknytningsform"}</th>
<th>${en ? "Number of publications" : "Antall publikasjoner"}</th>
</tr>
</thead>

<tbody>
<c:forEach items="${LittTilknytninger}" var="kat">
<tr>
<th>${fn:escapeXml(tilknytninger[kat.kode].tekstEntall)}</th>
<td><a href="<p:url value="/data/litteratur/tilknytningsform/${kat.kode}" />">${kat.antall}</a></td>

</tr>
</c:forEach>
</tbody>
</table>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
