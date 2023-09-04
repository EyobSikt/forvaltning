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
	<c:forEach items="${svenskenorskeutenriksministre}" var="svenskenorskeutenriksministre" >
		<c:if test="${svenskenorskeutenriksministre.uttakskode == 1}">
			${svenskenorskeutenriksministre.eksternkommentar_NO}
		</c:if>
		<p></p>
		<c:if test="${svenskenorskeutenriksministre.uttakskode eq 20}">
			${svenskenorskeutenriksministre.eksternkommentar_NO}
		</c:if>
	</c:forEach>
	<p><strong>Frem til 19.03.1876</strong> ble de svensk-norske utenriksministrene titulert som "Statsminister för utrikes ärendena," mens de fra og med 20.03.1876 ble titulert "Minister för utrikes ärendena."
	</p>
	</c:if>
	<c:if test="${en}">
		<h3>Note</h3>
		<c:forEach items="${svenskenorskeutenriksministre}" var="svenskenorskeutenriksministre" >
			<c:if test="${svenskenorskeutenriksministre.uttakskode == 1}">
				${svenskenorskeutenriksministre.eksternkommentar_ENG}
			</c:if>
			<p></p>
			<c:if test="${svenskenorskeutenriksministre.uttakskode eq 20}">
				${svenskenorskeutenriksministre.eksternkommentar_ENG}
			</c:if>
		</c:forEach>
		<p><br><strong>Until 19.03.1876</strong> the Swedish-Norwegian Foreign Ministers were titled "Prime Minister of Foreign Affairs" ("Statsminister för utrikes ärendena").
		</p>
	</c:if>        

</div>

<div id="main" class="superwide">

	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
			> Svensk-norske utenriksministre (1814-1905)
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			> Swedish-Norwegian M.F.A (1814-1905)
		</c:if>
	</div>
	<div>
		<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/svenskenorskeutenriksministre/" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/svenskenorskeutenriksministre/" />">View this page in Norwegian.</a></c:if>
	</div>
<c:if test="${no}"><h1>Svensk-norske utenriksministre (1814-1905)</h1></c:if>
<c:if test="${en}"><h1>Swedish-Norwegian Ministers of Foreign Affairs(1814-1905)</h1></c:if>
<table class="zebra, text">
		<c:if test="${no}">
			<caption>Utenriksministre</caption>
		<thead>
			<tr>
				<th>Ministre</th>
				<th>Virkeperiode</th>
			</tr>
		</thead>
		 </c:if>
		<c:if test="${en}">
		<caption>Ministers of Foreign Affairs</caption>
		<thead>
			<tr>
				<th>Ministers</th>
				<th>In office</th>
			</tr>
		</thead>
    </c:if>
	<tbody>
		<c:forEach items="${svenskenorskeutenriksministre}" var="svenskenorskeutenriksministre" >
		<tr>

			<td>
			 ${svenskenorskeutenriksministre.fornavn} ${svenskenorskeutenriksministre.etternavn} (${svenskenorskeutenriksministre.foedt}- ${svenskenorskeutenriksministre.doedsaar})
				 <c:if test="${svenskenorskeutenriksministre.uttakskode !=1 && svenskenorskeutenriksministre.uttakskode !=20 && svenskenorskeutenriksministre.uttakskode !=null}">
				 <c:if test="${no}"><br> <em> ${svenskenorskeutenriksministre.eksternkommentar_NO} </em> <p></p> </c:if>
				 <c:if test="${en}"><br> <em> ${svenskenorskeutenriksministre.eksternkommentar_ENG} </em> <p></p> </c:if>
				 </c:if>
			</td>

				 <%-- <c:if test="${no}"> </c:if>
                  <c:if test="${en}">
                <td>
                 ${regjeringeradhoc.regjeringsnavn_ENG}
			</td>
            </c:if>
			--%>
           <td>${svenskenorskeutenriksministre.start}-${svenskenorskeutenriksministre.slutt} <p></p></td>
		</tr>
		</c:forEach>
    </tbody>
</table>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>