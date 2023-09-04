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

    <c:if test="${no}">
    <h3>Departementale unntak</h3>
    <p>Blant de nedlagte departementene fremkommer det noen ”departementer” som strengt tatt ikke var et ’departement’, men som likevel er definert som sådan ettersom hver statsråd blir definert som leder av en gitt enhet.  Disse særtilfellene er:</p>

    <ul>
        <li>Statsrådsavdelingen i Christiania/Kristiania</li>
        <li>Statsministerembetet</li>
        <li>Statsråd uten portefølje</li>
    </ul>
    <p>Statsrådsavdelingen i Christiania/Kristiania og Statsministerembetet er konstruksjoner som her tjener som datidens ”Statsministerens kontor” for å ha en knagg å henge lederne av statsrådene på (visekonger, stattholdere, statsministre og førstestatsråder). For øvrig sorterer alle statsråder som ikke har ledet et eget departement under ”Statsråd uten portefølje”.</p>
     </c:if>
     <c:if test="${en}">
    <h3>Ministerial exceptions</h3>
    <p>Among the abandoned ministries some “ministries" that strictly speaking were not ministries appear. They are termed as such since the ministers here, by definition, are leaders of a certain unit. The special cases are:</p>

    <ul>
        <li>Council of State Division in Christiania/Kristiania</li>
        <li>Prime Minister's Office</li>
        <li>Minister without ministry</li>
    </ul>
    <p>The 'Council of State Division in Christiania/Kristiania' and 'Prime Minister's Office' are constructions that we utilize as a sort of contemporary "Office of the Prime Minister". The viceroys, governors, first ministers and prime ministers before 1939 are included in this construct. All the ministers that did not act as head of a certain ministry are sorted under 'Minister without ministry'.</p>
   </c:if>




</div>

<div id="main" class="superwide">

<div class="breadcrumbs">
	<c:if test="${no}">
		Du er her:
		<a href="<p:url value="/"/>">PolSys</a>
		><a href="<p:url value="/regjering/" />">Regjering</a>
		><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
		> Departementsoversikt
	</c:if>
	<c:if test="${en}">
		You are here:
		<a href="<p:url value="/"/>">PolSys</a>
		><a href="<p:url value="/regjering/" />">Government</a>
		><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
		> Overview of the Norwegian Ministries
	</c:if>
</div>

	<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringsstatsraader/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringsstatsraader/" />">View this page in Norwegian.</a></c:if>

 <c:if test="${no}">
	 <h1>Departementsoversikt</h1>
		Klikk på departementsnavnet for en kronologisk oversikt over tilhørende statsråder.
 </c:if>
  <c:if test="${en}">
	  <h1>  Overview of the Norwegian Ministries (1814-present)</h1>
		Click on the name of the ministry for corresponding ministers.
 </c:if>


 <jsp:useBean id="now" class="java.util.Date" />
    <c:set var="currentYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
    <c:set var="currentMonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
    <c:set var="currentDay"><fmt:formatDate value="${now}" pattern="d" /></c:set>
<table class="zebra, text">
		<c:if test="${no}">
		<caption>Dagens departement</caption>
			<thead>
				<tr>
					<th>Departement</th>
					<th>Eksistert i perioden</th>
				</tr>
			</thead>
		</c:if>
		<c:if test="${en}">
		<caption>Present ministries</caption>
			<thead>
				<tr>
					<th>Ministry</th>
					<th>Existed since</th>
				</tr>
			</thead>
		</c:if>
<tbody>
	<c:forEach items="${dagensdepartment}" var="dagensdepartment" >
		<tr>
			<td width="370">
				<a href="<p:url value="/regjering/statsraadsarkivet/regjeringsstatsraaderoversikt/?dep_kode=${dagensdepartment.dep_kode}"/>">${dagensdepartment.eintaltekst_no}</a>
			</td>
			<td width="140">
				${dagensdepartment.fra_dato}-
				<c:choose><c:when test="${dagensdepartment.til_dato == '9.9.9999'}"> <c:out value="${currentDay}"></c:out>.<c:out value="${currentMonth}"></c:out>.<c:out value="${currentYear}"></c:out>
                </c:when><c:otherwise>${dagensdepartment.til_dato}
            </c:otherwise></c:choose>
			</td>
		</tr>
	</c:forEach>
</tbody>
</table>
    <p></p>

<table class="zebra, text">
	<c:if test="${no}">
		<caption>Nedlagte departement og/eller departement som har endret navn
		</caption>
	<thead>
		<tr>
			<th>Departement</th>
			<th>Eksisterte i perioden</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
	<caption>Abandoned ministries and/or ministries with their former names
	</caption>
	<thead>
		<tr>
			<th>Ministry</th>
			<th>Existed in the period</th>
		</tr>
	</thead>
	</c:if>
    <tbody>
	<c:forEach items="${nedlagtedepartment}" var="nedlagtedepartment" >
	<tr>
		<td width="370">
			<a href="<p:url value="/regjering/statsraadsarkivet/regjeringsstatsraaderoversikt/?dep_kode=${nedlagtedepartment.dep_kode}"/>"> ${nedlagtedepartment.eintaltekst_no}</a>
		</td>
		<td width="140">
			${nedlagtedepartment.fra_dato}-${nedlagtedepartment.til_dato}
		</td>
	</tr>
	</c:forEach>
</tbody>
</table>

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>