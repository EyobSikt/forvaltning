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
<c:if test="${no}">
	<h3>Generelt</h3>
	<p>Statsekretærembetet, i betydningen 'politisk utnevnt embetsmann', ble innført i 1947. I oversikten på sidene under har vi imidlertid også inkludert de partipolitiske statsministersekretærene utnevnt etter 1945. Merk for øvrig at det i perioder helt siden 1814 har eksistert embeter som "statsrådssekretær", "statssekretær" og "statsministersekretær", uten at disse har fungert i partipolitisk forstand.</p>
 </c:if>
<c:if test="${en}">
	<h3>Note</h3>
	<p>The State Secretary Office was formally established in 1947 (i.e. as a <em>politically</em> appointed office). Included here are also, however, the politically appointed Secretaries to the Prime Minister, an office established in 1945.</p>
 </c:if>

	<c:if test="${no}"><h3>LASTE NED DATASETT</h3>Klikk <a href="<p:url value="/regjering/lastnedstatssekretar/"/>">her</a> å laste ned data om norske statssekretær.</c:if>
	<c:if test="${en}"><h3>DOWNLOAD DATASETS</h3> Click <a href="<p:url value="/regjering/lastnedstatssekretar/"/>">here</a> to download data about norwegian state secretaries.</c:if>

</div>

<div id="main" class="superwide">
	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Statssekretærarkivet</a>
			> Regjeringer statssekretærene
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Archive of State Secretaries</a>
			> State Secretaries
		</c:if>
	</div>

	<c:if test="${no}"><a href="<m:url value="/en/regjering/statssekretaerarkivet/statssekretarregjeringsvis/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvis/" />">View this page in Norwegian.</a></c:if>


<c:if test="${no}"><h1>Statssekretærer (1945-d.d.)</h1></c:if>
<c:if test="${en}"><h1>State Secretaries (1945-present)</h1></c:if>

	<c:if test="${no}">
	<p>Klikk på ønsket regjering for å se oversikten over tilhørende statssekretærer.</p>
	</c:if>

<c:if test="${en}">
	<p>Click on the Goverment's label to see the corresponding State Secretaries.</p>
	</c:if> 


<p></p>

	<table class="zebra, text">
		<c:if test="${no}">
				<caption>Statssekretærer (1945-d.d.)</caption>
		<thead>
			<tr>
				<th>Startår</th>
				<th>Regjering</th>
			</tr>
		</thead>
		</c:if>
		<c:if test="${en}">
			<caption>State Secretaries (1945-present)</caption>
		<thead>
			<tr>
				<th>Start</th>
				<th>Government</th>
			</tr>
		</thead>
		</c:if>
	<tbody>
		<c:forEach items="${statssekretarregjeringsvis}" var="statssekretarregjeringsvis" >
				<tr>
					<td>${statssekretarregjeringsvis.aar}</td>
					 <td>
					<a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${statssekretarregjeringsvis.start}&regslutt=${statssekretarregjeringsvis.slutt}&partierkode=${statssekretarregjeringsvis.partikode}"/>">${statssekretarregjeringsvis.ministerium}</a>
                      </td>
				</tr>
	    </c:forEach>
	</tbody>
	</table>
	<p></p>

	

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>