<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 03.nov.2010
  Time: 08:18:53
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
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>


<div id="sidebar">

</div>

<div id="main" class="wide">

	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
			> Regjeringenes varighet
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			> Number of Days in Office
		</c:if>
	</div>
	<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringsvarighet/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringsvarighet/" />">View this page in Norwegian.</a></c:if>

<c:if test="${no}"><h1>Regjeringenes varighet (1884-d.d.)</h1></c:if>
<c:if test="${en}"><h1>Number of Days in Office </h1></c:if>

	<table class="text">
	<c:if test="${no}">
	<caption>Proporsjonal oversikt over hvor lenge tidligere regjeringer har sittet ved makten, målt etter antall dager</caption>
	<thead>
		<tr>
			<th>Startår</th>
			<th>Regjering</th>
			<th>Ansiennitet</th>
		</tr>
	</thead>
	</c:if>  
	<c:if test="${en}">
	<caption>Former Governments' number of days in power (proportional)</caption>
	<thead>
		<tr>
			<th>Start</th>
			<th>Government</th>
			<th>Days in office</th>
		</tr>
	</thead>
	</c:if>
<tbody>
	<c:forEach items="${regjeringenesvarighet}" var="regjeringenesvarighet" >
	<tr>
		<td>${regjeringenesvarighet.startaar}</td>
		<td width="250">
			 <a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${regjeringenesvarighet.start}&regslutt=${regjeringenesvarighet.slutt}&stortingperiodekode=${regjeringenesvarighet.stortingperiodekode}&partierkode=${regjeringenesvarighet.partikode}"/>">
			 ${regjeringenesvarighet.regjeringsnavn_NO}

             </a>
	   </td>
		<td width="350">
		<div style="width: ${regjeringenesvarighet.antdag_stolpe}px;" class="histogram" title="${regjeringenesvarighet.antdag}" alt="HVA ER DETTE?">${regjeringenesvarighet.antdag}</div>
		<!---<div style="width: 25px;" class="histogram color2">25</div>
		<table bgcolor="##99CCCC"  border="1" width="#Antdag_stolpe#" ><tr height="10"><td></td></tr></table> setter inn tabell i tabellen for å få flere formateringsmuligheter--->
		</td>
	</tr>
	</c:forEach>
</tbody>
</table>

<table class="text">
	<c:if test="${no}">
		<caption>Dagens regjering</caption>
	<thead>
		<tr>
			<th>Startår</th>
			<th>Regjering</th>
			<th>Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
	<caption>Present Government</caption>
	<thead>
		<tr>
			<th>Start</th>
			<th>Government</th>
			<th>Days in office</th>
		</tr>
	</thead>
	</c:if>
<tbody>
	<c:forEach items="${dagensregjeringenesvarighet}" var="dagensregjeringenesvarighet" >

	<tr>
		<td>${dagensregjeringenesvarighet.startaar}</td>
		<td width="250">
			 <a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${dagensregjeringenesvarighet.start}&regslutt=${dagensregjeringenesvarighet.slutt}&stortingperiodekode=${dagensregjeringenesvarighet.stortingperiodekode}&partierkode=${dagensregjeringenesvarighet.partikode}"/>">
                ${dagensregjeringenesvarighet.regjeringsnavn_NO}
		</td>
		<td width="350">
			<div style="width: ${dagensregjeringenesvarighet.antdag_stolpe}px;" class="histogram" title="${dagensregjeringenesvarighet.antdag}" alt="HVA ER DETTE?">${dagensregjeringenesvarighet.antdag}</div>
			<!---<div style="width: 25px;" class="histogram color2">25</div>--->
		</td>
	</tr>
		</c:forEach>
</tbody>
</table>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>