<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 15.nov.2010
  Time: 15:31:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- --------------------------------------------- inkluderer toppinnhold. --%>
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Forvaltningsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>

<div id="sidebar">
    <h3>Om tabellen</h3>
     <p>Tabellen gir en oversikt over utviklingen i antall innstillinger og fraksjonsmerknader fra 1945 og fram til 2005. De siste årene har rundt 2/3 av komiteenes innstillinger inneholdt fraksjonsmerknader. Gjennomsnittlig antall merknader per sesjon steg fra ca. 2000 på 80-tallet til over 6000 på 90-tallet.</p>
</div>


<div id="main" class="superwide">
<div>
<c:if test="${no}">
Du er her:
<a href="/polsys/">PolSys</a>
> <a href="<p:url value="/storting/" />"> Storting </a>
> Merknadsfrekvenser
</c:if>
<c:if test="${en}">
You are here:
<a href="/polsys/">PolSys</a>
> <a href="<p:url value="/storting/" />"> Parliament </a>
> Recommendation frequencies
</c:if>
</div>
<div>
	<c:if test="${no}"><a href="<m:url value="/en/storting/merknadsfrekvenser/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/storting/merknadsfrekvenser/" />">View this page in Norwegian.</a></c:if>
</div>

<c:if test="${no}">	<h1>Innstillings- og merknadsfrekvenser</h1></c:if>
<c:if test="${en}"><h1>Remark and recommendation frequencies</h1>
	<h3>This page is not translated to English</h3>
	<p>----------------------------------------------</p>
</c:if>

<table class="zebra">
	<caption>Frekvenser</caption>
	<thead>
		<tr>
			<th>Sesjon</th>
			<th>Antall innst.</th>
			<th>Antall innst. m/merkn.</th>
			<th>Prosent innst. m/merkn.</th>
			<th>Antall merknader</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${merknadsfrekvenser}" var="merknadsfrekvenser" >

		<tr>
			<td>${merknadsfrekvenser.sesjon}</td>
			<td>${merknadsfrekvenser.innstillinger}</td>
			<td>${merknadsfrekvenser.innst_m_merknader}</td>
			<td>${merknadsfrekvenser.prosent_innst_m_m}</td>
			<td>${merknadsfrekvenser.totalt_merknader}</td>
		</tr>
		 </c:forEach>
	</tbody>
</table>
  

</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>