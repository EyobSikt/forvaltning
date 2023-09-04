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

<form action="<p:url value="/regjering/statsraadsarkivet/eldestyngstestatsraader/" />"  method="post">
  <select name="n_statsraader" onChange="form.submit()">
<c:if test="${no}">
	  <OPTION VALUE="5" selected>Velg antall statsråder
		  </c:if>
<c:if test="${en}">
	  <OPTION VALUE="5" selected>Select number of Ministers
	</c:if>
		 <OPTION VALUE="5">5
		 <OPTION VALUE="10">10
		 <OPTION VALUE="15">15
		 <OPTION VALUE="20">20
  </select>
</form>    

</div>

<div id="main" class="superwide">

	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
			> Eldste og yngste statsråder
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			> Oldest and youngest ministers
		</c:if>
	</div>

	<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/eldestyngstestatsraader/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/eldestyngstestatsraader/" />">View this page in Norwegian.</a></c:if>

<c:if test="${no}"><h1>Eldste og yngste statsråder (1814-d.d.)</h1></c:if>
<c:if test="${en}"><h1>Oldest and youngest ministers (1814-present)</h1></c:if>

<c:choose><c:when test="${param.n_statsraader ==null}"><c:set var="NStatsraader" value="5"></c:set></c:when><c:otherwise><c:set var="NStatsraader" value="${param.n_statsraader}"></c:set></c:otherwise></c:choose>

<table class="zebra, text">
	<c:if test="${no}">
		<caption><cfoutput>${NStatsraader}</cfoutput> eldste statsråder 1814-1884</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>
			<th width="100">Ministerium</th>
			<th width="80">Avskjeds- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption><cfoutput>${NStatsraader}</cfoutput> oldest Ministers 1814-1884</caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>
			<th>Government</th>
			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>
		
        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

		<c:forEach items="${eldstestatsraader_1814}" var="eldstestatsraader_1814" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${eldstestatsraader_1814.personId != LikPerson || eldstestatsraader_1814.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}">
            <td>${teljar}</td>
            <td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_1814.personId}&p_info=personalia"/>">${eldstestatsraader_1814.fornavn} ${eldstestatsraader_1814.etternavn}</a></td>
            <td>${eldstestatsraader_1814.doedsaar} år og ${eldstestatsraader_1814.antdag} dager</td>
            <td>${eldstestatsraader_1814.regjeringsnavn_NO}</td>
            <td>${eldstestatsraader_1814.slutt}</td>
        </c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_1814.personId}&p_info=personalia"/>">${eldstestatsraader_1814.fornavn} ${eldstestatsraader_1814.etternavn}</a></td>
			<td>${eldstestatsraader_1814.doedsaar} years and ${eldstestatsraader_1814.antdag} days</td><td>${eldstestatsraader_1814.regjeringsnavn_ENG}</td><td>${eldstestatsraader_1814.slutt}</td>
		</c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${eldstestatsraader_1814.personId}"></c:set>
        <c:set var="LikDager" value="${eldstestatsraader_1814.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>

<p></p>


<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} yngste statsråder 1814-1884</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>
			<th width="100">Ministerium</th>
			<th width="80">Tiltredelses- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} youngest Ministers 1814-1884</caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>
			<th>Government</th>
			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${yngstestatsraader_1814}" var="yngstestatsraader_1814" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${yngstestatsraader_1814.personId != LikPerson || yngstestatsraader_1814.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_1814.personId}&p_info=personalia"/>">${yngstestatsraader_1814.fornavn} ${yngstestatsraader_1814.etternavn}</a></td><td>${yngstestatsraader_1814.doedsaar} år og ${yngstestatsraader_1814.antdag} dager</td><td>${yngstestatsraader_1814.regjeringsnavn_NO}</td><td>${yngstestatsraader_1814.start}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_1814.personId}&p_info=personalia"/>">${yngstestatsraader_1814.fornavn} ${yngstestatsraader_1814.etternavn}</a></td><td>${yngstestatsraader_1814.doedsaar} years and ${yngstestatsraader_1814.antdag} days</td><td>${yngstestatsraader_1814.regjeringsnavn_ENG}</td><td>${yngstestatsraader_1814.start}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${yngstestatsraader_1814.personId}"></c:set>
        <c:set var="LikDager" value="${yngstestatsraader_1814.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>


<!-- eldste statsråder 1884-1945 -->
 <table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} eldste statsråder 1884-1945</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>
			<th width="100">Regjering</th>
			<th width="80">Avskjeds- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} oldest Ministers 1884-1945</caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>
			<th>Government</th>
			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${eldstestatsraader_1884}" var="eldstestatsraader_1884" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${eldstestatsraader_1884.personId != LikPerson || eldstestatsraader_1884.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_1884.personId}&p_info=personalia"/>">${eldstestatsraader_1884.fornavn} ${eldstestatsraader_1884.etternavn}</a></td><td>${eldstestatsraader_1884.doedsaar} år og ${eldstestatsraader_1884.antdag} dager</td><td>${eldstestatsraader_1884.regjeringsnavn_NO}</td><td>${eldstestatsraader_1884.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_1884.personId}&p_info=personalia"/>">${eldstestatsraader_1884.fornavn} ${eldstestatsraader_1884.etternavn}</a></td><td>${eldstestatsraader_1884.doedsaar} years and ${eldstestatsraader_1884.antdag} days</td><td>${eldstestatsraader_1884.regjeringsnavn_ENG}</td><td>${eldstestatsraader_1884.slutt}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${eldstestatsraader_1884.personId}"></c:set>
        <c:set var="LikDager" value="${eldstestatsraader_1884.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>

     

<p></p>

<!-- yngste statsråder 1884-1945 -->
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} yngste statsråder 1884-1945</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>
			<th width="100">Regjering</th>
			<th width="80">Avskjeds- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} youngest Ministers 1884-1945</caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>
			<th>Government</th>
			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${yngstestatsraader_1884}" var="yngstestatsraader_1884" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${yngstestatsraader_1884.personId != LikPerson || yngstestatsraader_1884.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_1884.personId}&p_info=personalia"/>">${yngstestatsraader_1884.fornavn} ${yngstestatsraader_1884.etternavn}</a></td><td>${yngstestatsraader_1884.doedsaar} år og ${yngstestatsraader_1884.antdag} dager</td><td>${yngstestatsraader_1884.regjeringsnavn_NO}</td><td>${yngstestatsraader_1884.start}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_1884.personId}&p_info=personalia"/>">${yngstestatsraader_1884.fornavn} ${yngstestatsraader_1884.etternavn}</a></td><td>${yngstestatsraader_1884.doedsaar} years and ${yngstestatsraader_1884.antdag} days</td><td>${yngstestatsraader_1884.regjeringsnavn_ENG}</td><td>${yngstestatsraader_1884.start}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${yngstestatsraader_1884.personId}"></c:set>
        <c:set var="LikDager" value="${yngstestatsraader_1884.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>


<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} eldste statsråder 1945-d.d.</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>
			<th width="100">Regjering</th>
			<th width="80">Avskjeds- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} oldest Ministers 1945-present</caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>
			<th>Government</th>
			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${eldstestatsraader_1945}" var="eldstestatsraader_1945" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${eldstestatsraader_1945.personId != LikPerson || eldstestatsraader_1945.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_1945.personId}&p_info=personalia"/>">${eldstestatsraader_1945.fornavn} ${eldstestatsraader_1945.etternavn}</a></td><td>${eldstestatsraader_1945.doedsaar} år og ${eldstestatsraader_1945.antdag} dager</td><td>${eldstestatsraader_1945.regjeringsnavn_NO}</td><td>${eldstestatsraader_1945.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_1945.personId}&p_info=personalia"/>">${eldstestatsraader_1945.fornavn} ${eldstestatsraader_1945.etternavn}</a></td><td>${eldstestatsraader_1945.doedsaar} years and ${eldstestatsraader_1945.antdag} days</td><td>${eldstestatsraader_1945.regjeringsnavn_ENG}</td><td>${eldstestatsraader_1945.slutt}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${eldstestatsraader_1945.personId}"></c:set>
        <c:set var="LikDager" value="${eldstestatsraader_1945.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>

<p></p>
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} yngste statsråder 1945-d.d.</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>
			<th width="100">Regjering</th>
			<th width="80">Avskjeds- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} youngest Ministers 1945-present</caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>
			<th>Government</th>
			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${yngstestatsraader_1945}" var="yngstestatsraader_1945" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${yngstestatsraader_1945.personId != LikPerson || yngstestatsraader_1945.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_1945.personId}&p_info=personalia"/>">${yngstestatsraader_1945.fornavn} ${yngstestatsraader_1945.etternavn}</a></td><td>${yngstestatsraader_1945.doedsaar} år og ${yngstestatsraader_1945.antdag} dager</td><td>${yngstestatsraader_1945.regjeringsnavn_NO}</td><td>${yngstestatsraader_1945.start}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_1945.personId}&p_info=personalia"/>">${yngstestatsraader_1945.fornavn} ${yngstestatsraader_1945.etternavn}</a></td><td>${yngstestatsraader_1945.doedsaar} years and ${yngstestatsraader_1945.antdag} days</td><td>${yngstestatsraader_1945.regjeringsnavn_ENG}</td><td>${yngstestatsraader_1945.start}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${yngstestatsraader_1945.personId}"></c:set>
        <c:set var="LikDager" value="${yngstestatsraader_1945.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>



<p></p>


<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} eldste statsråder i den sittende regjering </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>
		
			<th width="80">Avskjeds- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} oldest Ministers in the present Government </caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>

			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${eldstestatsraader_dagens}" var="eldstestatsraader_dagens" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${eldstestatsraader_dagens.personId != LikPerson || eldstestatsraader_dagens.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_dagens.personId}&p_info=personalia"/>">${eldstestatsraader_dagens.fornavn} ${eldstestatsraader_dagens.etternavn}</a></td><td>${eldstestatsraader_dagens.doedsaar} år og ${eldstestatsraader_dagens.antdag} dager</td><td>${eldstestatsraader_dagens.start}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${eldstestatsraader_dagens.personId}&p_info=personalia"/>">${eldstestatsraader_dagens.fornavn} ${eldstestatsraader_dagens.etternavn}</a></td><td>${eldstestatsraader_dagens.doedsaar} years and ${eldstestatsraader_dagens.antdag} days</td><td>${eldstestatsraader_dagens.start}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${eldstestatsraader_dagens.personId}"></c:set>
        <c:set var="LikDager" value="${eldstestatsraader_dagens.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>



<p></p>

<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} yngste statsråder i den sittende regjering </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="180">Navn</th>
			<th width="120">Alder</th>

			<th width="80">Avskjeds- dato</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} youngest Ministers in the present Government </caption>
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Age</th>

			<th>End date</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${yngstestatsraader_dagens}" var="yngstestatsraader_dagens" >
		<c:if test="${teljar <= NStatsraader}">
        <c:if test="${yngstestatsraader_dagens.personId != LikPerson || yngstestatsraader_dagens.aldersdager_ved_slutt_eksakt != LikDager }">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_dagens.personId}&p_info=personalia"/>">${yngstestatsraader_dagens.fornavn} ${yngstestatsraader_dagens.etternavn}</a></td><td>${yngstestatsraader_dagens.doedsaar} år og ${yngstestatsraader_dagens.antdag} dager</td><td>${yngstestatsraader_dagens.start}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${yngstestatsraader_dagens.personId}&p_info=personalia"/>">${yngstestatsraader_dagens.fornavn} ${yngstestatsraader_dagens.etternavn}</a></td><td>${yngstestatsraader_dagens.doedsaar} years and ${yngstestatsraader_dagens.antdag} days</td><td>${yngstestatsraader_dagens.start}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		</c:if>
        <c:set var="LikPerson" value="${yngstestatsraader_dagens.personId}"></c:set>
        <c:set var="LikDager" value="${yngstestatsraader_dagens.aldersdager_ved_slutt_eksakt}"></c:set>
	 </c:forEach>
</tbody>
</table>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>