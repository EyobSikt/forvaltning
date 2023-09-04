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
			> Regjeringer parlamentarisk grunnlag
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			> Parliamentary basis
		</c:if>
	</div>

	<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringsparlamentariskgrunnlag/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringsparlamentariskgrunnlag/" />">View this page in Norwegian.</a></c:if>

<c:if test="${no}"><h1>Parlamentarisk grunnlag i norske regjeringer (1884-d.d.)</h1></c:if>
<c:if test="${en}"><h1>Parliamentary Bases (1884-present)</h1></c:if>

<table class="text">
		<c:if test="${no}">
			<caption>Parlamentarisk grunnlag</caption>
			<thead>
				<tr>
					<th>Startår</th>
					<th>Regjering</th>
					<th>Grunnlag</th>
				</tr>
			</thead>
		</c:if>
		<c:if test="${en}">
			<caption>Parliamentary Basis</caption>
			<thead>
				<tr>
					<th>Start</th>
					<th>Government</th>
					<th>Basis</th>
				</tr>
			</thead>
		</c:if>
<tbody>


    <c:forEach items="${regjeringsparlamentariskgrunnlag}" var="regjeringsparlamentariskgrunnlag" varStatus="recipeCounter">
	<tr>

		<td>${regjeringsparlamentariskgrunnlag.startaar} </td>
		<td>
			<cfset StartUt=#datepart("yyyy",Startarray[i])# & "-" & #datepart("m",Startarray[i])# & "-" & #datepart("d" ,Startarray[i])# > <!--- triks for å omgå problemene som oppstår som følge av at sql-servereren, den hunden, legger til klokkeslett i datovariabler (her: 'start' og 'slutt') jf. også CONVERT(DATETIME, '#Regstart#', 102) i I28--->
			<cfset SluttUt=#datepart("yyyy",Sluttarray[i])# & "-" & #datepart("m",Sluttarray[i])# & "-" & #datepart("d" ,Sluttarray[i])# >
			<a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${regjeringsparlamentariskgrunnlag.start}&regslutt=${regjeringsparlamentariskgrunnlag.slutt}&stortingperiodekode=${regjeringsparlamentariskgrunnlag.stortingperiodekode}&partierkode=${regjeringsparlamentariskgrunnlag.partikode}"/>">
                <c:if test="${no}">${regjeringsparlamentariskgrunnlag.regjeringsnavn_NO}</c:if>
              <c:if test="${en}">${regjeringsparlamentariskgrunnlag.regjeringsnavn_ENG}</c:if></a>
		</td>
        
	<td>
     <table class="text">
		 <c:if test="${recipeCounter.count ==1 }">
			<c:if test="${no}">
			<thead>
				<tr>
					<th width="50">Valg- periode</th>
					<th>Prosentandel stortingsseter</th>
				</tr>
			</thead>
			</c:if>
			<c:if test="${en}">
			<thead>
				<tr>
					<th>Period</th>
					<th>Percentage of seats</th>
				</tr>
			</thead>
			</c:if>
		 </c:if>
		<tbody>

		<c:forEach items="${regjeringsparlamentariskgrunnlag.fleirtaltekstArray}"  varStatus="loop">
		<tr>
			<td width="50">
					${regjeringsparlamentariskgrunnlag.fleirtaltekstArray[loop.index]}
			</td>
 			<td>
				<c:set var="tall"  value="${regjeringsparlamentariskgrunnlag.talposisjonArray[loop.index]}" />
				<c:set var = "resttal2" value="${100-tall}"/>
				<c:set var = "resttal" ><fmt:formatNumber type="number" maxFractionDigits="1"  value="${resttal2}" /></c:set>
				<c:set var="regjeringsfarge" value="${tall*2}" ></c:set>
				<c:set var="opposisjonsfarge" value="${resttal2*2}" ></c:set>
			<div style="width: ${regjeringsfarge}px;" class="histogram" title="<c:if test="${no}">Regjeringsparti:${tall}</c:if><c:if test="${en}">Governmental parties:${tall}</c:if>" alt="HVA ER DETTE?">${tall}</div>
			<div style="width: ${opposisjonsfarge}px;" class="histogram color2" title="<c:if test="${no}">Resterende parti: ${resttal}</c:if><c:if test="${en}">Other parties:${resttal}</c:if>" alt="HVA ER DETTE?"> ${resttal}</div>
		</td>
          </tr>
		</c:forEach>
		</tbody>
		</table>
	</td>
	</tr>

    </c:forEach>
</tbody>
</table>


<c:if test="${no}">
	<p class="tabellnote" xmlns="">
	Merk: Se særskilte kommentarer om regjeringen Sverdrup og regjeringen Nygaardsvold på deres resepktive sider.
</c:if>
<c:if test="${en}">
	<p class="tabellnote" xmlns="">
	Note: Cf. special comments on the Sverdrup Governmnet, and the Nygaardsvold Government on their respective pages.
</c:if>


</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>