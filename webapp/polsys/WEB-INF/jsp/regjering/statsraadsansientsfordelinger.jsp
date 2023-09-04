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


<form action="<p:url value="/regjering/statsraadsarkivet/statsraadsansientsfordelinger/" />"  method="post">
	<select name="n_statsraader" onChange="form.submit()">
		<c:if test="${no}">
		<OPTION VALUE="5" selected>Velg antall statsråder
			</c:if>
			<c:if test="${en}">
		<OPTION VALUE="5" selected>Select number of Ministers
			</c:if>
		<OPTION VALUE="5">5
		<OPTION VALUE="10">10
		<OPTION VALUE="25">25
		<OPTION VALUE="50">50
		<OPTION VALUE="100">100
	</select>
</form>

<c:if test="${no}">
	<h3>Merk</h3>
	<p>Beregningene av statsrådenes samlede tjenestetid er foretatt ved å gange antall statsrådsposter med antall tjenestedager i disse statsrådspostene. I ministeriene
	på 1800-tallet var det imidlertid svært vanlig at statsrådene bestyrte flere enn ett departement på samme tid, hvilket man bør ha i mente mår man ser på de særdeles høye ansiennitetene
	enkelte av statsrådene har i denne perioden.
	I perioden 1884-1939 bestyrte statsministrene (med unntak av Michelsen (juni-oktober 1905)) et vanlig departement i tillegg til statsministerembetet, noe som gjør at også disse generelt
	får 'ekstra' høy ansiennitet (se f.eks. Gunnar Knudsen).</p>
	<p></p>
	<p>Merk også at den tjenesteperioden/ansienniteten som dagens sittende statsråder har opparbeidet seg i nåværende regjering, ikke er inkludert i oversiktene fra 1945 og frem til i dag.</p>
</c:if>
<c:if test="${en}">
	<h3>Note</h3>
	<p>The ministers' total amount of seniority is calculated by multiplying the number of appointments with the corresponding days in office. However, during the period with
	non-parliamentarian governments the ministers often held multiple offices. This partly explains why some of the ministers have extraordinary seniority in this period of time. In the years between 1884-1939 the prime ministers (except Michelsen between June and October 1905) also were head of an
	‘ordinary’ ministry, which, of course, adds (disproportionally) to their total seniority.</p>
	<p></p>
	<p>Also note that the time the present ministers have accumulated in the present government is not included in the tables showing the ministers' (continuing) time in office from
	1945 to present.</p>
</c:if>    

</div>

<div id="main" class="superwide">

	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
			> Statsrådenes ansiennitet
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			> The ministers' seniority rankings
		</c:if>
	</div>
	<div>
	<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/statsraadsansientsfordelinger/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/statsraadsansientsfordelinger/" />">View this page in Norwegian.</a></c:if>
	</div>

<c:if test="${no}"><h1>Statsrådene med lengst tjenesteperiode og størst ansiennitet (1814-d.d.)</h1></c:if>
<c:if test="${en}"><h1>Seniority rankings for Norwegian ministers (1814-present)</h1></c:if>

<c:choose><c:when test="${param.n_statsraader ==null}"><c:set var="NStatsraader" value="5"></c:set></c:when><c:otherwise><c:set var="NStatsraader" value="${param.n_statsraader}"></c:set></c:otherwise></c:choose>

<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med lengst sammenhengende tjenesteperiode 1814-1884</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Tjenestetid</th>
			<th width="70">Fra</th>
			<th width="70">Til</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the longest continuing time in office 1814-1884</caption>
	<thead>
		<tr>
			<th width="20">No.</th>
			<th width="200">Name</th>
			<th width="140">Time in office</th>
			<th width="65">From</th>
			<th width="65">Until</th>
		</tr>
	</thead>
	</c:if>
<tbody>
		
        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

		<c:forEach items="${lengststatsraader_1814}" var="lengststatsraader_1814" >
		<c:if test="${teljar <= NStatsraader}">

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${lengststatsraader_1814.personId}&p_info=personalia"/>">${lengststatsraader_1814.fornavn} ${lengststatsraader_1814.etternavn}</a></td><td>${lengststatsraader_1814.doedsaar} år og ${lengststatsraader_1814.antdag} dager</td><td>${lengststatsraader_1814.start}</td><td>${lengststatsraader_1814.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${lengststatsraader_1814.personId}&p_info=personalia"/>">${lengststatsraader_1814.fornavn} ${lengststatsraader_1814.etternavn}</a></td><td>${lengststatsraader_1814.doedsaar} years and ${lengststatsraader_1814.antdag} days</td><td>${lengststatsraader_1814.start}</td><td>${lengststatsraader_1814.slutt}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
		
	 </c:forEach>
</tbody>
</table>

<p></p>


<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med kortest sammenhengende tjenesteperiode 1814-1884</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Tjenestetid</th>
			<th width="70">Fra</th>
			<th width="70">Til</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the shortest continuing time in office 1814-1884</caption>
	<thead>
		<tr>
			<th width="20">No.</th>
			<th width="200">Name</th>
			<th width="140">Time in office</th>
			<th width="65">From</th>
			<th width="65">Until</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

		<c:forEach items="${korteststatsraader_1814}" var="korteststatsraader_1814" >
		<c:if test="${teljar <= NStatsraader}">

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${korteststatsraader_1814.personId}&p_info=personalia"/>">${korteststatsraader_1814.fornavn} ${korteststatsraader_1814.etternavn}</a></td><td>${korteststatsraader_1814.doedsaar} år og ${korteststatsraader_1814.antdag} dager</td><td>${korteststatsraader_1814.start}</td><td>${korteststatsraader_1814.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="index.cfm?urlname=#lcase(urlname)#&lan=#lan#&MenuItem=#MenuItem#&ChildItem=#ChildItem#&State=#State#&UttakNr=32&person=#person#">${korteststatsraader_1814.fornavn} ${korteststatsraader_1814.etternavn}</a></td><td>${korteststatsraader_1814.doedsaar} years and ${korteststatsraader_1814.antdag} days</td><td>${korteststatsraader_1814.start}</td><td>${korteststatsraader_1814.slutt}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>

	 </c:forEach>
</tbody>
</table>


<!-- lengst statsråder 1884-1945 -->
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med lengst sammenhengende tjenesteperiode 1884-1945</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Tjenestetid</th>
			<th width="70">Fra</th>
			<th width="70">Til</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the longest continuing time in office 1884-1945</caption>
	<thead>
		<tr>
			<th width="20">No.</th>
			<th width="200">Name</th>
			<th width="140">Time in office</th>
			<th width="65">From</th>
			<th width="65">Until</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

		<c:forEach items="${lengststatsraader_1884}" var="lengststatsraader_1884" >
		<c:if test="${teljar <= NStatsraader}">

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${lengststatsraader_1884.personId}&p_info=personalia"/>">${lengststatsraader_1884.fornavn} ${lengststatsraader_1884.etternavn}</a></td><td>${lengststatsraader_1884.doedsaar} år og ${lengststatsraader_1884.antdag} dager</td><td>${lengststatsraader_1884.start}</td><td>${lengststatsraader_1884.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${lengststatsraader_1884.personId}&p_info=personalia"/>">${lengststatsraader_1884.fornavn} ${lengststatsraader_1884.etternavn}</a></td><td>${lengststatsraader_1884.doedsaar} years and ${lengststatsraader_1884.antdag} days</td><td>${lengststatsraader_1884.start}</td><td>${lengststatsraader_1884.slutt}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>

	 </c:forEach>
</tbody>
</table>



<p></p>

<!-- kortest statsråder 1884-1945 -->
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med kortest sammenhengende tjenesteperiode 1884-1945</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Tjenestetid</th>
			<th width="70">Fra</th>
			<th width="70">Til</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the shortest continuing time in office 1884-1945</caption>
	<thead>
		<tr>
			<th width="20">No.</th>
			<th width="200">Name</th>
			<th width="140">Time in office</th>
			<th width="65">From</th>
			<th width="65">Until</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

		<c:forEach items="${korteststatsraader_1884}" var="korteststatsraader_1884" >
		<c:if test="${teljar <= NStatsraader}">

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${korteststatsraader_1884.personId}&p_info=personalia"/>">${korteststatsraader_1884.fornavn} ${korteststatsraader_1884.etternavn}</a></td><td>${korteststatsraader_1884.doedsaar} år og ${korteststatsraader_1884.antdag} dager</td><td>${korteststatsraader_1884.start}</td><td>${korteststatsraader_1884.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${korteststatsraader_1884.personId}&p_info=personalia"/>">${korteststatsraader_1884.fornavn} ${korteststatsraader_1884.etternavn}</a></td><td>${korteststatsraader_1884.doedsaar} years and ${korteststatsraader_1884.antdag} days</td><td>${korteststatsraader_1884.start}</td><td>${korteststatsraader_1884.slutt}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>

	 </c:forEach>
</tbody>
</table>


<!-- lengst statsråder 1945-d.d -->
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med lengst sammenhengende tjenesteperiode 1945-d.d.</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Tjenestetid</th>
			<th width="70">Fra</th>
			<th width="70">Til</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the longest continuing time in office 1945-present</caption>
	<thead>
		<tr>
			<th width="20">No.</th>
			<th width="200">Name</th>
			<th width="140">Time in office</th>
			<th width="65">From</th>
			<th width="65">Until</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

		<c:forEach items="${lengststatsraader_1945}" var="lengstststatsraader_1945" >
		<c:if test="${teljar <= NStatsraader}">

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${lengstststatsraader_1945.personId}&p_info=personalia"/>">${lengstststatsraader_1945.fornavn} ${lengstststatsraader_1945.etternavn}</a></td><td>${lengstststatsraader_1945.doedsaar} år og ${lengstststatsraader_1945.antdag} dager</td><td>${lengstststatsraader_1945.start}</td><td>${lengstststatsraader_1945.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${lengstststatsraader_1945.personId}&p_info=personalia"/>">${lengstststatsraader_1945.fornavn} ${lengstststatsraader_1945.etternavn}</a></td><td>${lengstststatsraader_1945.doedsaar} years and ${lengstststatsraader_1945.antdag} days </td><td>${lengstststatsraader_1945.start}</td><td>${lengstststatsraader_1945.slutt}</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>

	 </c:forEach>
</tbody>
</table>

<p></p>
<!-- kortest statsråder 1945-d.d -->
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med kortest sammenhengende tjenesteperiode 1945-d.d.</caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Tjenestetid</th>
			<th width="70">Fra</th>
			<th width="70">Til</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the shortest continuing time in office 1945-present</caption>
	<thead>
		<tr>
			<th width="20">No.</th>
			<th width="200">Name</th>
			<th width="140">Time in office</th>
			<th width="65">From</th>
			<th width="65">Until</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

		<c:forEach items="${korteststatsraader_1945}" var="korteststatsraader_1945" >
		<c:if test="${teljar <= NStatsraader}">

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${korteststatsraader_1945.personId}&p_info=personalia"/>">${korteststatsraader_1945.fornavn} ${korteststatsraader_1945.etternavn}</a></td><td>${korteststatsraader_1945.doedsaar} år og ${korteststatsraader_1945.antdag} dager</td><td>${korteststatsraader_1945.start}</td><td>${korteststatsraader_1945.slutt}</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${korteststatsraader_1945.personId}&p_info=personalia"/>">${korteststatsraader_1945.fornavn} ${korteststatsraader_1945.etternavn}</a></td><td>${korteststatsraader_1945.doedsaar} years and ${korteststatsraader_1945.antdag} days</td><td>${korteststatsraader_1945.start}</td><td>${korteststatsraader_1945.slutt}</td></c:if>

	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>

	 </c:forEach>
</tbody>
</table>



<p></p>


<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med størst ansiennitet 1814-1884 </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the most seniority 1814-1884 </caption>
	<thead>
		<tr>
		<th width="20">No.</th>
	    <th width="200">Name</th>
		<th width="140">Seniority</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${sumstorststatsraader_1814}" var="sumstorststatsraader_1814" >
		<c:if test="${teljar <= NStatsraader}">

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstorststatsraader_1814.personId}&p_info=personalia"/>">${sumstorststatsraader_1814.fornavn} ${sumstorststatsraader_1814.etternavn}</a></td><td>${sumstorststatsraader_1814.doedsaar} år og ${sumstorststatsraader_1814.antdag} dager</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstorststatsraader_1814.personId}&p_info=personalia"/>">${sumstorststatsraader_1814.fornavn} ${sumstorststatsraader_1814.etternavn}</a></td><td>${sumstorststatsraader_1814.doedsaar} years and ${sumstorststatsraader_1814.antdag} days</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>

	 </c:forEach>
</tbody>
</table>

<p></p>
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med minst ansiennitet 1814-1884 </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the least seniority 1814-1884 </caption>
	<thead>
		<tr>
		<th width="20">No.</th>
	    <th width="200">Name</th>
		<th width="140">Seniority</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${summinststatsraader_1814}" var="summinststatsraader_1814" >
		<c:if test="${teljar <= NStatsraader}">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${summinststatsraader_1814.personId}&p_info=personalia"/>">${summinststatsraader_1814.fornavn} ${summinststatsraader_1814.etternavn}</a></td><td>${summinststatsraader_1814.doedsaar} år og ${summinststatsraader_1814.antdag} dager</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${summinststatsraader_1814.personId}&p_info=personalia"/>">${summinststatsraader_1814.fornavn} ${summinststatsraader_1814.etternavn}</a></td><td>${summinststatsraader_1814.doedsaar} years and ${summinststatsraader_1814.antdag} days</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
	 </c:forEach>
</tbody>
</table>

<p></p>
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med størst ansiennitet 1884-1945 </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the most seniority 1884-1945 </caption>
	<thead>
		<tr>
		<th width="20">No.</th>
	    <th width="200">Name</th>
		<th width="140">Seniority</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${sumstorststatsraader_1814}" var="sumstorststatsraader_1814" >
		<c:if test="${teljar <= NStatsraader}">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstorststatsraader_1814.personId}&p_info=personalia"/>">${sumstorststatsraader_1814.fornavn} ${sumstorststatsraader_1814.etternavn}</a></td><td>${sumstorststatsraader_1814.doedsaar} år og ${sumstorststatsraader_1814.antdag} dager</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstorststatsraader_1814.personId}&p_info=personalia"/>">${sumstorststatsraader_1814.fornavn} ${sumstorststatsraader_1814.etternavn}</a></td><td>${sumstorststatsraader_1814.doedsaar} years and ${sumstorststatsraader_1814.antdag} days</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
	 </c:forEach>
</tbody>
</table>

<p></p>
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med minst ansiennitet 1884-1945 </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the least seniority 1884-1945 </caption>
	<thead>
		<tr>
		<th width="20">No.</th>
	    <th width="200">Name</th>
		<th width="140">Seniority</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${summinststatsraader_1884}" var="summinststatsraader_1884" >
		<c:if test="${teljar <= NStatsraader}">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${summinststatsraader_1884.personId}&p_info=personalia"/>">${summinststatsraader_1884.fornavn} ${summinststatsraader_1884.etternavn}</a></td><td>${summinststatsraader_1884.doedsaar} år og ${summinststatsraader_1884.antdag} dager</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${summinststatsraader_1884.personId}&p_info=personalia"/>">${summinststatsraader_1884.fornavn} ${summinststatsraader_1884.etternavn}</a></td><td>${summinststatsraader_1884.doedsaar} years and ${summinststatsraader_1884.antdag} days</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
	 </c:forEach>
</tbody>
</table>


<p></p>
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med størst ansiennitet 1945-d.d. </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the most seniority 1945-present </caption>
	<thead>
		<tr>
		<th width="20">No.</th>
	    <th width="200">Name</th>
		<th width="140">Seniority</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${sumstorststatsraader_1945}" var="sumstorststatsraader_1945" >
		<c:if test="${teljar <= NStatsraader}">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstorststatsraader_1945.personId}&p_info=personalia"/>">${sumstorststatsraader_1945.fornavn} ${sumstorststatsraader_1945.etternavn}</a></td><td>${sumstorststatsraader_1945.doedsaar} år og ${sumstorststatsraader_1945.antdag} dager</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstorststatsraader_1945.personId}&p_info=personalia"/>">${sumstorststatsraader_1945.fornavn} ${sumstorststatsraader_1945.etternavn}</a></td><td>${sumstorststatsraader_1945.doedsaar} years and ${sumstorststatsraader_1945.antdag} days</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
	 </c:forEach>
</tbody>
</table>

<p></p>
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} statsråder med minst ansiennitet 1945-d.d. </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption>${NStatsraader} Ministers with the least seniority 1945-present </caption>
	<thead>
		<tr>
		<th width="20">No.</th>
	    <th width="200">Name</th>
		<th width="140">Seniority</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${summinststatsraader_1945}" var="summinststatsraader_1945" >
		<c:if test="${teljar <= NStatsraader}">
	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${summinststatsraader_1945.personId}&p_info=personalia"/>">${summinststatsraader_1945.fornavn} ${summinststatsraader_1945.etternavn}</a></td><td>${summinststatsraader_1945.doedsaar} år og ${summinststatsraader_1945.antdag} dager</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${summinststatsraader_1945.personId}&p_info=personalia"/>">${summinststatsraader_1945.fornavn} ${summinststatsraader_1945.etternavn}</a></td><td>${summinststatsraader_1945.doedsaar} years and ${summinststatsraader_1945.antdag} days</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>
		</c:if>
	 </c:forEach>
</tbody>
</table>

<p></p>
<table class="zebra, text">
	<c:if test="${no}">
		<caption>${NStatsraader} Alfabetisk oversikt over dagens statsråder med oppsamlet ansiennitet pr. dags dato </caption>
	<thead>
		<tr>
			<th width="20">Nr.</th>
			<th width="200">Navn</th>
			<th width="110">Ansiennitet</th>
		</tr>
	</thead>
	</c:if>
	<c:if test="${en}">
		<caption> Present ministers' seniority </caption>
	<thead>
		<tr>
		<th width="20">No.</th>
	    <th width="200">Name</th>
		<th width="140">Seniority</th>
		</tr>
	</thead>
	</c:if>
<tbody>

        <c:set var="LikDager" value=""></c:set>
        <c:set var="LikPerson" value=""></c:set>
        <c:set var="teljar" value="1"></c:set>

	<c:forEach items="${sumstatsraader_dagens}" var="sumstatsraader_dagens" >

	<tr>
		<c:if test="${no}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstatsraader_dagens.personId}&p_info=personalia"/>">${sumstatsraader_dagens.fornavn} ${sumstatsraader_dagens.etternavn}</a></td><td>${sumstatsraader_dagens.doedsaar} år og ${sumstatsraader_dagens.antdag} dager</td></c:if>
		<c:if test="${en}"><td>${teljar}</td><td><a href="<p:url value="/person/politikerbiografi/?person_id=${sumstatsraader_dagens.personId}&p_info=personalia"/>">${sumstatsraader_dagens.fornavn} ${sumstatsraader_dagens.etternavn}</a></td><td>${sumstatsraader_dagens.doedsaar} years and ${sumstatsraader_dagens.antdag} days</td></c:if>
	</tr>
		<c:set var="teljar" value="${teljar+1}"></c:set>

	 </c:forEach>
</tbody>
</table>

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>