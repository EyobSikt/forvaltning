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
			><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsstatsraader/" />">Departementsoversikt</a>
			> Statsråder fordelt på departement
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsstatsraader/" />">Overview of the Norwegian Ministries </a>
			> Ministers by Ministries
		</c:if>
	</div>

	<div>
		<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringsstatsraaderoversikt/?dep_kode=${param.dep_kode}" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringsstatsraaderoversikt/?dep_kode=${param.dep_kode}" />">View this page in Norwegian.</a></c:if>
	</div>

<c:if test="${no}"><h1>Statsråder fordelt på departement</h1></c:if>
<c:if test="${en}"><h1>Ministers by Ministries</h1></c:if>
 <jsp:useBean id="now" class="java.util.Date" />
 <c:set var="currentYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
 <c:set var="currentMonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
 <c:set var="currentDay"><fmt:formatDate value="${now}" pattern="d" /></c:set>

<!--
<cfoutput query="Norske_departementer">
	<cfif #datepart("d",til_tidspunkt)# eq '09' and #datepart("m",til_tidspunkt)# eq '09' and #datepart("yyyy",til_tidspunkt)# eq '9999'>
			<cfset Nedlagt=now()>
			<cfelse>
			<cfset Nedlagt=#til_tidspunkt#>
	</cfif>


	<p>
        <cfif #lan# eq "">Om</cfif><cfif #lan# eq "eng">About the</cfif> <a href="index.cfm?urlname=#lcase(urlname)#&lan=#lan#&MenuItem=N1_4&ChildItem=#ChildItem#&State=#State#&UttakNr=53&idnum=#idnum#">#eintaltekst# (#kode_dep#)</a> (#DateFormat(fra_tidspunkt, "dd.mm.yyyy")#-#DateFormat(Nedlagt, "dd.mm.yyyy")#)

    </p>
</cfoutput>
-->

	<c:forEach items="${nedlagtedepartment}" var="nedlagtedepartment" >
		<p><c:if test="${no}">Om</c:if><c:if test="${en}">About the</c:if>
			<a href="<p:url value="/forvaltning/enhet/${nedlagtedepartment.dep_kode}/endringshistorie"  />">${nedlagtedepartment.eintaltekst_no} (${nedlagtedepartment.dep_kode})</a>
			(${nedlagtedepartment.fra_dato}- ${nedlagtedepartment.til_dato})
		</p>
	</c:forEach>



<table class="text">
		<c:if test="${no}">
		<caption>Kronologisk oversikt</caption>
			<thead>
				<tr>
                   <c:choose><c:when test="${param.d_kode==100}"><th>Statsministre</th></c:when><c:otherwise><th>Statsråder</th></c:otherwise></c:choose>
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
					<a href="<p:url value="/person/politikerbiografi/?person_id=${departmentoversikt.person_id}&p_info=personalia"/>">${departmentoversikt.fornavn} ${departmentoversikt.navn}</a>
				</c:otherwise></c:choose>

          <c:choose><c:when test="${departmentoversikt.eintaltekst_forkorting_no=='Sp' && departmentoversikt.fra_dato < '1.1.1959'}">(Bp)</c:when><c:otherwise>(${departmentoversikt.eintaltekst_forkorting_no})</c:otherwise></c:choose> 
		 </td>
	   <td>${departmentoversikt.fra_dato}-<c:choose><c:when test="${departmentoversikt.til_dato == '9.9.9999'}"> <c:out value="${currentDay}"></c:out>.<c:out value="${currentMonth}"></c:out>.<c:out value="${currentYear}"></c:out>
                </c:when><c:otherwise>${departmentoversikt.til_dato}
            </c:otherwise></c:choose><br></td>
    </c:if>

      <c:if test="${en}">
	<td><c:if test="${departmentoversikt.stilling_avvik_no!='utenriksminister'}">${departmentoversikt.stilling_avvik_eng}</c:if>

				<a href="<p:url value="/person/politikerbiografi/?person_id=${departmentoversikt.person_id}&p_info=personalia"/>">${departmentoversikt.fornavn} ${departmentoversikt.navn}</a>

		 <c:choose><c:when test="${departmentoversikt.eintaltekst_forkorting_no=='Sp' && departmentoversikt.fra_dato < '1.1.1959'}">(Bp)</c:when><c:otherwise>(${departmentoversikt.eintaltekst_forkorting_eng})</c:otherwise></c:choose> 
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