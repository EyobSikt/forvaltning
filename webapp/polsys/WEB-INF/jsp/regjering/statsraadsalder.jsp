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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		> Statsrådenes gjennomsnittlige alder
	</c:if>
	<c:if test="${en}">
		You are here:
		<a href="<p:url value="/"/>">PolSys</a>
		><a href="<p:url value="/regjering/" />">Government</a>
		><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
		> The ministers' average age
	</c:if>
</div>

<div>	<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/statsraadsalder/" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/statsraadsalder/" />">View this page in Norwegian.</a></c:if>
</div>

<c:if test="${no}"><h1>Statsrådenes gjennomsnittlige alder da ministeriene og regjeringene tiltrådte (1814-d.d.)</h1></c:if>
<c:if test="${en}"><h1>The ministers' average age when the Governments came to power (1814-present)</c:if></h1>

	<table class="text">
	<c:if test="${no}">
	<caption>Oversikt over gjennomsnittsaldre</caption>
	<thead>
		<tr>
			<th>Startår</th>
			<th>Regjering</th>
			<th>Alder</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
	<caption>Average ages</caption>
	<thead>
		<tr>
			<th>Start</th>
			<th>Government</th>
			<th>Age</th>
		</tr>
	</thead>
	</c:if>
 <tbody>
	 <c:forEach items="${statsraadsaldersfordeling}" var="statsraadsaldersfordeling" >
	<tr>
		<td>${statsraadsaldersfordeling.startaar}</td>
		<td>
			<c:if test="${statsraadsaldersfordeling.min_reg ==4}">
            <a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${statsraadsaldersfordeling.start}&regslutt=${statsraadsaldersfordeling.slutt}"/>">
              <c:if test="${no}">${statsraadsaldersfordeling.regjeringsnavn_NO}</c:if>
              <c:if test="${en}">${statsraadsaldersfordeling.regjeringsnavn_ENG}</c:if></a>
			</c:if>
            <c:if test="${statsraadsaldersfordeling.min_reg ==5}">
            <a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${statsraadsaldersfordeling.start}&regslutt=${statsraadsaldersfordeling.slutt}&stortingperiodekode=${statsraadsaldersfordeling.stortingperiodekode}&partierkode=${statsraadsaldersfordeling.partikode}"/>">
              <c:if test="${no}">${statsraadsaldersfordeling.regjeringsnavn_NO}</c:if>
              <c:if test="${en}">${statsraadsaldersfordeling.regjeringsnavn_ENG}</c:if></a>
   	        </c:if>
        </td>
		<td>

		
	    <div style="width: ${statsraadsaldersfordeling.antaldertotal}px;" class="histogram" title="N=#antall#" alt="HVA ER DETTE?">  <fmt:formatNumber type="number" maxFractionDigits="1" value="${statsraadsaldersfordeling.antalder}"/>
							<!---<table bgcolor="##0000CC"   width="#Alder_stolpe#" ><tr height="10"><td>N=#antall#</td></tr></table>  tidligere funksjon--->
		</td>
	</tr>
	 </c:forEach>
</tbody>
</table>


</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>