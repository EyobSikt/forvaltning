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

<div id="main" class="superwide">
	<div id="main" class="wide">
		<div class="breadcrumbs">
			<c:if test="${no}">
				Du er her:
				<a href="<p:url value="/"/>">PolSys</a>
				><a href="<p:url value="/regjering/" />">Regjering</a>
				><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Statssekretærarkivet</a>
				> Departementsoversikt
			</c:if>
			<c:if test="${en}">
				You are here:
				<a href="<p:url value="/"/>">PolSys</a>
				><a href="<p:url value="/regjering/" />">Government</a>
				><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Archive of State Secretaries</a>
				> Overview of the Norwegian Ministries
			</c:if>
		</div>
		<div>
		<c:if test="${no}"><a href="<m:url value="/en/regjering/statssekretaerarkivet/statssekretardepartementsvis/" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvis/" />">View this page in Norwegian.</a></c:if>
		</div>
<c:if test="${no}"><h1>Departementsoversikt</h1></c:if>
<c:if test="${en}"><h1>Overview of the Norwegian Ministries (1945-present)</h1></c:if>

 <c:if test="${no}">
		Klikk på departementsnavnet for en kronologisk oversikt over tilhørende statssekretar.
 </c:if>
  <c:if test="${en}">
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
				<a href="<p:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvisoversikt/?dep_kode=${dagensdepartment.dep_kode}"/>">${dagensdepartment.eintaltekst_no}</a>
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
			<a href="<p:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvisoversikt/?dep_kode=${nedlagtedepartment.dep_kode}"/>"> ${nedlagtedepartment.eintaltekst_no}</a>
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