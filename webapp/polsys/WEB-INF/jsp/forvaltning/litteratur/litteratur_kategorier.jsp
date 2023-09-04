<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Litteratur - kategorier.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Kategorier - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Categories - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Finn forvaltningslitteratur etter kategori." />
    <c:param name="beskrivelseEn" value="Literature by categories" />
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
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningslitteratur.html">Litteratur</a>
> Kategorier
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html">Literature</a>
> Categories
</c:if>
</div>

<h1>${en ? "Literature by categories" : "Litteratur per kategorier"}</h1>

<c:if test="${no}"><p>De enkelte publikasjonene er typologisert etter følgende kategorier:</p></c:if>
<c:if test="${en}">
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
<p>Publications by categories:</p>
</c:if>

<ul>
<c:forEach items="${kategorier}" var="kat">
<li><a href="<p:url value="/data/litteratur/kategori/${kat.kode}" />">${fn:escapeXml(kat.tekstEntall)}</a></li>
</c:forEach>
</ul>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
