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
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Statssekretærarkivet</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvis/" />">Departementsoversikt</a>
			> Statssekretærer fordelt på departement
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Archive of State Secretaries</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvis/" />">Norwegian Ministries </a>
			> State Secretaries by Ministries
		</c:if>
	</div>
	<div>
		<c:if test="${no}"><a href="<m:url value="/en/regjering/statssekretaerarkivet/statssekretardepartementsvisoversikt/?dep_kode=${param.dep_kode}" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvisoversikt/?dep_kode=${param.dep_kode}" />">View this page in Norwegian.</a></c:if>
	</div>
<c:if test="${no}"><h1>Statssekretærer fordelt på departement</h1></c:if>
<c:if test="${en}"><h1>State Secretaries by Ministries</h1></c:if>

 <jsp:useBean id="now" class="java.util.Date" />
 <c:set var="currentYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
 <c:set var="currentMonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
 <c:set var="currentDay"><fmt:formatDate value="${now}" pattern="d" /></c:set>


<c:forEach items="${nedlagtedepartment}" var="nedlagtedepartment" >
	<p><c:if test="${no}">Om
		<a href="<p:url value="/forvaltning/enhet/${nedlagtedepartment.dep_kode}/endringshistorie"  />">${nedlagtedepartment.eintaltekst_no} (${nedlagtedepartment.dep_kode})</a>
	</c:if><c:if test="${en}">About the
		<a href="<p:url value="/forvaltning/enhet/${nedlagtedepartment.dep_kode}/endringshistorie"  />">${nedlagtedepartment.eintaltekst_eng} (${nedlagtedepartment.dep_kode})</a>
	</c:if>

	(${nedlagtedepartment.fra_dato}- ${nedlagtedepartment.til_dato})
	</p>
</c:forEach>


<table class="text">
		<c:if test="${no}">
		<caption>Kronologisk oversikt</caption>
			<thead>
				<tr>
                   <c:choose><c:when test="${param.d_kode==100}"><th>Statsministre</th></c:when><c:otherwise><th>Statssekretærer</th></c:otherwise></c:choose>
					<th>Virkeperiode</th>
				</tr>
			</thead>
		 </c:if>
		<c:if test="${en}">
		<caption>Chronological overview</caption>
			<thead>
				<tr>
					<th>Ministers</th>
					<th>In office</th>
				</tr>
			</thead>
		</c:if>
<tbody>

<c:forEach items="${departmentoversikt}" var="departmentoversikt" >

<tr>
     <c:if test="${no}">
	<td> <c:if test="${departmentoversikt.stilling_avvik_no!='utenriksminister'}">${departmentoversikt.stilling_avvik_no}</c:if>
			    <c:choose><c:when test="${departmentoversikt.initialer== null || departmentoversikt.initialer== '' }">
					<a href="<p:url value="/person/politikerbiografi/?person_id=${departmentoversikt.person_id}&p_info=personalia"/>">${departmentoversikt.fornavn} ${departmentoversikt.navn}</a>
				</c:when><c:otherwise>
					<a href="<p:url value="/person/politikerbiografi/?person_id=${departmentoversikt.person_id}&p_info=personalia"/>">${departmentoversikt.fornavn} ${departmentoversikt.navn} </a>
				</c:otherwise></c:choose>

          <c:choose><c:when test="${departmentoversikt.eintaltekst_forkorting_no=='Sp' && departmentoversikt.fra_dato < '1.1.1959'}">(Bp)</c:when><c:otherwise>(${departmentoversikt.eintaltekst_forkorting_no})</c:otherwise></c:choose>
		<br> <em>${departmentoversikt.eksternkommentar}</em><c:if test="${departmentoversikt.eksternkommentar !=null}"><p></p></c:if>
	</td>
	   <td>${departmentoversikt.fra_dato}-<c:choose><c:when test="${departmentoversikt.til_dato == '9.9.9999'}"> <c:out value="${currentDay}"></c:out>.<c:out value="${currentMonth}"></c:out>.<c:out value="${currentYear}"></c:out>
                </c:when><c:otherwise>${departmentoversikt.til_dato}
            </c:otherwise></c:choose><br></td>
    </c:if>

      <c:if test="${en}">
	<td><c:if test="${departmentoversikt.stilling_avvik_no!='utenriksminister'}">${departmentoversikt.stilling_avvik_eng}</c:if>
		<a href="<p:url value="/person/politikerbiografi/?person_id=${departmentoversikt.person_id}&p_info=personalia"/>">${departmentoversikt.fornavn} ${departmentoversikt.navn}</a>
		 <c:choose><c:when test="${departmentoversikt.eintaltekst_forkorting_no=='Sp' && departmentoversikt.fra_dato < '1.1.1959'}">(Bp)</c:when><c:otherwise>(${departmentoversikt.eintaltekst_forkorting_eng})</c:otherwise></c:choose>
		<br><em>${departmentoversikt.eksternkommentar}</em><c:if test="${departmentoversikt.eksternkommentar!=null}"><p></p></c:if>
	</td>
		  <td>${departmentoversikt.fra_dato}-<c:choose><c:when test="${departmentoversikt.til_dato == '9.9.9999'}"> <c:out value="${currentDay}"></c:out>.<c:out value="${currentMonth}"></c:out>.<c:out value="${currentYear}"></c:out>
                </c:when><c:otherwise>${departmentoversikt.til_dato}
            </c:otherwise></c:choose><br></td>
    </c:if>


</tr>
</c:forEach>
</tbody>
</table>

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>