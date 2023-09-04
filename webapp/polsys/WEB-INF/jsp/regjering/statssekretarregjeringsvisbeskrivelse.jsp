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
     <c:choose>

    <c:when test="${fn:substring(param.regslutt, 0, 4) == '9999' &&  fn:substring(param.regslutt, 4, 5) == '9' && fn:substring(param.regslutt, 5, 6) == '9'}">
    <c:set var="RRRegslutt" value="${now}"></c:set>
    </c:when>
    <c:otherwise> <c:set var="RRRegslutt" value="${param.regslutt}"></c:set></c:otherwise>
	 </c:choose>

	<c:forEach items="${statssekretarregjeringsvis}" var="statssekretarregjeringsvis" >
	<!-- de generelle kommentarene under finnes også i 'norske_regjeringer.eksternkommentar' --->

	<c:if test="${no}">
		<h3>LASTE NED DATASETT</h3>
	Klikk <a href="<p:url value="/regjering/lastnedstatssekretar/"/>">her</a> å laste ned data om norske statssekretær.
		<h3>Statsråder</h3> <a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${statssekretarregjeringsvis.startdato}&regslutt=${statssekretarregjeringsvis.sluttdato}&stortingperiodekode=${statssekretarregjeringsvis.stortingperiodekode}&partierkode=${statssekretarregjeringsvis.partikode}"/>"> Klikk her</a> for å se statsrådene i regjeringen.
		<p></p>
	</c:if>
		<p></p>
	<c:if test="${en}">
		<h3>DOWNLOAD DATASETS</h3>
		Click <a href="<p:url value="/regjering/lastnedstatssekretar/"/>">here</a> to download data about norwegian state secretaries.
		<h3>Ministers</h3> <a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${statssekretarregjeringsvis.startdato}&regslutt=${statssekretarregjeringsvis.sluttdato}&stortingperiodekode=${statssekretarregjeringsvis.stortingperiodekode}&partierkode=${statssekretarregjeringsvis.partikode}"/>"> Click here</a> for an overview of this Government's ministers.
		<p></p>
	</c:if>
		<p></p>
	</c:forEach>
</div>

<div id="main" class="superwide">
	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Statssekretærarkivet</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvis/" />">Regjeringer statssekretærene</a>
			> Statssekretærene beskrivelse
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Archive of State Secretaries</a>
			><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvis/" />">State Secretaries</a>
			> State Secretaries description
		</c:if>
	</div>

	<c:if test="${no}"><a href="<m:url value="/en/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${param.regstart}&regslutt=${param.regslutt}&partierkode=${param.partierkode}" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${param.regstart}&regslutt=${param.regslutt}&partierkode=${param.partierkode}" />">View this page in Norwegian.</a></c:if>

<table class="text">
		<caption>
			<c:forEach items="${statssekretarregjeringsvis}" var="statssekretarregjeringsvis" >

			 <h2><c:if test="${no}">Statssekretærene i</c:if>  <c:if test="${en}">The State Secretaries in</c:if>
			 ${statssekretarregjeringsvis.ministerium} (${statssekretarregjeringsvis.start}-${statssekretarregjeringsvis.slutt})</h2><br>
                <c:if test="${statssekretarregjeringsvis.regjering_reg_kode==2}">
                  ${statssekretarregjeringsvis.partinavn}
                </c:if>
			</c:forEach>
		</caption>
    <thead>
		<c:if test="${no}">
        <tr>
           <th>Statssekretærer</th>
			<th>Virkeperiode</th>
        </tr>
		</c:if>
		<c:if test="${en}">
        <tr>
           <th>State Secretaries</th>
			<th>In office</th>
        </tr>
		</c:if>
    </thead>
	<tbody>
	<c:forEach items="${statssekretarregjeringsvisbeskrivelse}" var="statssekretarregjeringsvisbeskrivelse" >
					<c:if test="${statssekretarregjeringsvisbeskrivelse.statsraadnavn!=null}">
						<tr>
						<td> <h3>${statssekretarregjeringsvisbeskrivelse.statsraadnavn}</h3>
                        </td>
                    </tr>
					</c:if>
					<tr>
						<td>
							${statssekretarregjeringsvisbeskrivelse.stilling}
					        <a href="<p:url value="/person/politikerbiografi/?person_id=${statssekretarregjeringsvisbeskrivelse.personId}&p_info=personalia"/>">${statssekretarregjeringsvisbeskrivelse.fornavn} ${statssekretarregjeringsvisbeskrivelse.etternavn}</a>
							(f. ${statssekretarregjeringsvisbeskrivelse.foedt})
                            <c:if test="${statssekretarregjeringsvisbeskrivelse.antall_parti_i_reg > 1 && statssekretarregjeringsvisbeskrivelse.regjering_reg_kode!=10 }">
                               ${statssekretarregjeringsvisbeskrivelse.partikortnavn}
                             </c:if>
                               <br> <c:if test="${statssekretarregjeringsvisbeskrivelse.eksternkommentar !=null}"><em>${statssekretarregjeringsvisbeskrivelse.eksternkommentar}</em> <p></p></c:if>
						</td>
						<td valign="top">${statssekretarregjeringsvisbeskrivelse.start}-${statssekretarregjeringsvisbeskrivelse.slutt}</td>
					</tr>
			</c:forEach>
	</tbody>
</table>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>