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


<CFSET MennDager = ArrayNew(1)>
<CFSET KvinneDager = ArrayNew(1)>

<c:set var="MennDager" value=""></c:set>
    
<cfloop index = "i" from = "10" to = "56">
	<cfset MennDager[i]="0">
  	<cfset KvinneDager[i]="0">
</cfloop>


<CFLOOP QUERY="Norske_regjeringer_kjoennsfordeling">
		<cfif #kjoenn# eq "1"><CFSET MennDager[#Kode#] = #antdag#></cfif>
		<cfif #kjoenn# eq "2"><CFSET KvinneDager[#Kode#] = #antdag#></cfif>
</CFLOOP>

<!--- Leser inn i array statsrådsbetegnelse og år --->

<CFSET StBet= ArrayNew(1)>
<CFSET StAar = ArrayNew(1)>
<CFSET Blo = ArrayNew(1)>
<CFSET Startreg = ArrayNew(1)>
<CFSET Sluttreg = ArrayNew(1)>

<CFLOOP QUERY="Statsraadsbetegnelse">
		<CFSET StBet[#Kode#] = #Statsraadsbetegnelse#>
		<CFSET StAar[#Kode#] = #startaar#>
		<CFSET Blo[#Kode#] = #Blokk#>
		<CFSET Startreg[#Kode#] = #Start#>
		<CFSET Sluttreg[#Kode#] = #Slutt#>
		<CFSET StPeriode[#Kode#] = #StortingPeriodeKode#>
		<CFSET PartierKodeUt[#Kode#] = #Partierkode#>
</CFLOOP>



<div id="sidebar">
<c:if test="${no}">
		<h3>Merk</h3>
		<p>Antall observasjoner er lik antall dager de mannlige og kvinnlige statsrådene hadde i sine respektive embeter i de ulike regjeringene. Oversikten over dagens regjering er følgelig dynamisk.</p>
	</c:if>
	<c:if test="${en}">
		<h3>Note</h3>
		<p>The number of observations equals the number of days each gender had in office. The overview of the present government is thus dynamic.</p>
	</c:if>
</div>



<div id="main" class="superwide">
	<div id="main" class="wide">
		<div class="breadcrumbs">
			<c:if test="${no}">
				Du er her:
				<a href="<p:url value="/"/>">PolSys</a>
				><a href="<p:url value="/regjering/" />">Regjering</a>
				><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Statssekretærarkivet</a>
				> Kjønnsfordeling blant statssekretærene
			</c:if>
			<c:if test="${en}">
				You are here:
				<a href="<p:url value="/"/>">PolSys</a>
				><a href="<p:url value="/regjering/" />">Government</a>
				><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Archive of State Secretaries</a>
				> State secretaries gender distribution
			</c:if>
		</div>
<div><c:if test="${no}"><a href="<m:url value="/en/regjering/statssekretaerarkivet/statssekretarskjonn/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statssekretaerarkivet/statssekretarskjonn/" />">View this page in Norwegian.</a></c:if>
</div>
 <h1>Kjønnsfordeling blant statssekretærene i norske regjeringer (1945-d.d.)</h1>

<table class="text">
	<c:if test="${no}">
		<caption>Fordeling av statssekretærer etter kjønn i norske regjeringer siden 1945</caption>
	<thead>
		<tr>
			<th>Startår</th>
			<th>Regjering</th>
			<th>Kjønnsfordeling</th>
		</tr>
	</thead>
	</c:if>

	<c:if test="${en}">
		<caption>Gender distribution in Norwegian governments since 1945 at state secretarial level</caption>
	<thead>
		<tr>
			<th>Start</th>
			<th>Government</th>
			<th>Gender distribution</th>
		</tr>
	</thead>
	</c:if>
<tbody>
	
       <c:forEach items="${statssekretarbetegnelse}" var="statssekretarbetegnelse" begin="26" end="56">
	<tr>
		<td>
				${statssekretarbetegnelse.startaar}
		</td>
		<c:if test="${no}"><td width="250"></c:if> <c:if test="${en}"><td width="270"></c:if>
				<a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${statssekretarbetegnelse.start}&regslutt=${statssekretarbetegnelse.slutt}&partierkode=${statssekretarbetegnelse.partikode}"/>">
			${statssekretarbetegnelse.regjeringsnavn_NO}
                </a>
			</cfoutput>
		</td>
		<td>
			<c:choose>
				<c:when test="${statssekretarbetegnelse.menn == 0 || statssekretarbetegnelse.kvinner ==0}">
					<c:set var="pmenn" ><fmt:formatNumber type="number" maxFractionDigits="0" value="${((statssekretarbetegnelse.menn/(statssekretarbetegnelse.menn+statssekretarbetegnelse.kvinner))*100)}" /></c:set>

				</c:when>
				<c:otherwise>
					<c:set var="pmenn" ><fmt:formatNumber type="number" maxFractionDigits="1" value="${((statssekretarbetegnelse.menn/(statssekretarbetegnelse.menn+statssekretarbetegnelse.kvinner))*100)}" /></c:set>

				</c:otherwise>
			</c:choose>

			<c:set var="pmenn"  value="${ ((statssekretarbetegnelse.menn/(statssekretarbetegnelse.menn+statssekretarbetegnelse.kvinner))*100) }"/>


			<c:set var = "pkvinner" value="${100-pmenn}"/>
			<c:set var = "pmenn2" value="${pmenn*2}"/>
			<c:set var = "pkvinner2" value="${pkvinner*2}"/>
			<div style="width: ${pmenn2}px;" class="histogram" title="#pmenn# % menn" alt="HVA ER DETTE?"><fmt:formatNumber type="number" maxFractionDigits="1" value="${pmenn}"/></div>
			<div style="width: ${pkvinner2}px;" class="histogram color2" title="#pkvinner# % kvinner" alt="HVA ER DETTE?"><fmt:formatNumber type="number" maxFractionDigits="1" value="${pkvinner}"/></div>

		</td>
		</tr>
	 </c:forEach>
</tbody>
</table>

<table class="text">
	<c:if test="${no}">
		<caption>Dagens regjering</caption>
		<thead>
		<tr>
			<th>Startår</th>
			<th>Regjering</th>
			<th>Kjønnsfordeling</th>
		</tr>
		</thead>
	</c:if>

	<c:if test="${en}">
		<caption>Present Government</caption>
		<thead>
		<tr>
			<th>Start</th>
			<th>Government</th>
			<th>Gender distribution</th>
		</tr>
		</thead>
	</c:if>
<tbody>
<c:forEach items="${dagensregjeringsstatssekretarkjonn}" var="dagensregjeringsstatssekretarkjonn" >
	<tr>
		<td>${dagensregjeringsstatssekretarkjonn.startaar}</td>
		<c:if test="${no}"><td width="250"></c:if> <c:if test="${en}"><td width="270"></c:if>
		<a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${dagensregjeringsstatssekretarkjonn.start}&regslutt=${dagensregjeringsstatssekretarkjonn.slutt}&partierkode=${dagensregjeringsstatssekretarkjonn.partikode}"/>">
			${dagensregjeringsstatssekretarkjonn.regjeringsnavn_NO}
		</a>
	</td>
		<td>
			<c:choose>
				<c:when test="${dagensregjeringsstatssekretarkjonn.avgaatte_statsraader_menn == 0 }">
						<c:set var="nymannevariable" value="0" />
				</c:when>
				<c:otherwise>
					<c:set var="nymannevariable" value="${dagensregjeringsstatssekretarkjonn.avgaatte_statsraader_menn}" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${dagensregjeringsstatssekretarkjonn.avgaatte_statsraader_kvinner == 0 }">
					<c:set var="nykvinnevariable" value="0" />
				</c:when>
				<c:otherwise>
					<c:set var="nykvinnevariable"  value="${dagensregjeringsstatssekretarkjonn.avgaatte_statsraader_kvinner}" />
				</c:otherwise>
			</c:choose>

			<c:set var = "SUMmenn" value="${nymannevariable + dagensregjeringsstatssekretarkjonn.menn}"/>
			<c:set var = "SUMkvinner" value="${nykvinnevariable + dagensregjeringsstatssekretarkjonn.kvinner}"/>
			<c:set var = "SUMtotal" value="${SUMmenn + SUMkvinner}"/>
			<c:set var="pmenn3" ><fmt:formatNumber type="number" maxFractionDigits="1" value="${((SUMmenn/(SUMtotal))*100)}" /></c:set>
			<fmt:parseNumber var="pmenn" type="number" value="${pmenn3}" />
			<c:set var="pkvinner3" ><fmt:formatNumber type="number" maxFractionDigits="1" value="${100-pmenn}" /></c:set>
			<fmt:parseNumber var="pkvinner" type="number" value="${pkvinner3}" />
			<c:set var = "pmenn2" value="${pmenn*2}"/>
			<c:set var = "pkvinner2" value="${pkvinner*2}"/>

			<div style="width: ${pmenn2}px;" class="histogram" title="#pmenn# % menn" alt="HVA ER DETTE?">${pmenn}</div>
			<div style="width: ${pkvinner2}px;" class="histogram color2" title="#pkvinner# % kvinner" alt="HVA ER DETTE?">${pkvinner}</div>
			<!--- TIDLIGERE OPPLEGG <table bgcolor="##99CCCC"  border="1" width="250"><tr height="10"><td bgcolor="##99CCCC" width="#pmenn2#">&ordm;</td>  <td bgcolor="##FF0066">&ordf;</td></tr></table>  merk at summen av bredden på selve tabellen (her:"250") må være lik prosentsummen (derfor: pmenn*2=pmenn2). --->

		</td>
	</tr>
</c:forEach>
</tbody>
</table>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>