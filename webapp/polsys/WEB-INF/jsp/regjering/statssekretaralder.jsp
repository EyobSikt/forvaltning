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

<style type="text/css">
.histogram {
    background-color: #007AAD;
    color: white;
    float: left;
    font-size: 8px;
    height: 14px;
    margin: 0;
    padding: 0;
    text-align: center;
}

</style>


<div id="sidebar">
	<c:if test="${no}">
		<h3>Merk</h3>
 		<p>For å ta høyde for at statssekretærer (nødvendigvis) ofte tiltrer en viss tid etter statsrådene, har vi her inkludert alle statssekretærer som tiltrådte inntil én måned etter at regjeringen ble utnevnt.</p>
</c:if>
	<c:if test="${en}">
		<h3>Note</h3>
		<p>State Secretaries who assumed office within one month after the Government came to power are also inluded, since the Secretaries often (by necessity) assumes office some time after the ministers.</p>
	</c:if>
</div>



<div id="main" class="superwide">

	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Statssekretærarkivet</a>
			> Statssekretærenes gjennomsnittlige alder
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Archive of State Secretaries</a>
			> State secretaries gender distribution
		</c:if>
	</div>
	<div><c:if test="${no}"><a href="<m:url value="/en/regjering/statssekretaerarkivet/statssekretarsalder/" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/regjering/statssekretaerarkivet/statssekretarsalder/" />">View this page in Norwegian.</a></c:if>
	</div>

<c:if test="${no}">	<h1>Statssekretærenes gjennomsnittlige alder da regjeringene tiltrådte (1945-d.d.)</h1></c:if>
	<c:if test="${en}"><h1>The state secretaries' average age when the Governments came to power (1945-present)</h1></c:if>
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
	<cfset Alder=numberformat(#tt#,"0.0")>
	<cfset Alder_stolpe=(#alder#*3)>
	<tr>
		<td>${statsraadsaldersfordeling.startaar}</td>
		<td>
			<cfset StartUt=#datepart("yyyy",Start)# & "-" & #datepart("m",Start)# & "-" & #datepart("d" ,Start)# > <!--- triks for å omgå problemene som oppstår som følge av at sql-servereren, den hunden, legger til klokkeslett i datovariabler (her: 'start' og 'slutt') jf. også CONVERT(DATETIME, '#Regstart#', 102) i I28--->
			<cfset SluttUt=#datepart("yyyy",Slutt)# & "-" & #datepart("m",Slutt)# & "-" & #datepart("d" ,Slutt)# >
			<c:if test="${statsraadsaldersfordeling.min_reg ==1}">
            <a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${statsraadsaldersfordeling.start}&regslutt=${statsraadsaldersfordeling.slutt}"/>">
              <c:if test="${no}">${statsraadsaldersfordeling.regjeringsnavn_NO}</c:if>
              <c:if test="${en}">${statsraadsaldersfordeling.regjeringsnavn_ENG}</c:if></a>
			</c:if>
            <c:if test="${statsraadsaldersfordeling.min_reg ==2}">
            <a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${statsraadsaldersfordeling.start}&regslutt=${statsraadsaldersfordeling.slutt}&stortingperiodekode=${statsraadsaldersfordeling.stortingperiodekode}&partierkode=${statsraadsaldersfordeling.partikode}"/>">
              <c:if test="${no}">${statsraadsaldersfordeling.regjeringsnavn_NO}</c:if>
              <c:if test="${en}">${statsraadsaldersfordeling.regjeringsnavn_ENG}</c:if></a>
   	        </c:if>
        </td>
		<td>

		
	    <div style="width: ${statsraadsaldersfordeling.antaldertotal}px;" class="histogram" title="N=#antall#" alt="HVA ER DETTE?">  <fmt:formatNumber type="number" maxFractionDigits="1" value="${statsraadsaldersfordeling.antalder}"/> <c:if test="${no}">år</c:if><c:if test="${en}">years</c:if> </div>
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