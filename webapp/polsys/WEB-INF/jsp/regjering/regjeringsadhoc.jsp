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
	<p>Som "ad hoc-regjering" regnes her de midlertidige interimsregjeringene som fant sted i Stockholm på 1800-tallet, samt de ulike regjeringene
	som hadde sete i Oslo under 2. verdenskrig.</p>
	</c:if>
	<c:if test="${en}">
		<h3>Note</h3>
	<p>The Interim Governments in Stockholm in the 19th century and the different governments in Oslo during World War II constitute what we here term
	an "ad hoc government".</p>
	</c:if>        

</div>

<div id="main" class="superwide">
	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
			> Regjeringsadhoc
		</c:if>
		<c:if test="${en}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			> Ad hoc governments
		</c:if>
	</div>
<div>
	<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringsadhoc/" />">View this page in English.</a></c:if>
	<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringsadhoc/" />">View this page in Norwegian.</a></c:if>
</div>
 <h1>Norske ad hoc-regjeringer</h1>   

<table class="zebra, text">
		<c:if test="${no}">
			<caption>Norske ad hoc-regjeringer</caption>
		<thead>
			<tr>
				<th>Regjering</th>
				<th>Periode</th>
			</tr>
		</thead>
		 </c:if>
		<c:if test="${en}">
		<caption>Norwegian ad hoc Governments</caption>
		<thead>
			<tr>
				<th>Government</th>
				<th>Period</th>
			</tr>
		</thead>
    </c:if>
	<tbody>
		<c:forEach items="${regjeringeradhoc}" var="regjeringeradhoc" >
		<tr>
             <c:if test="${no}">
			<td>
			<a href="<p:url value="/regjering/statsraadsarkivet/regjeringsadhocbeskrivelse/?reg_adhoc=${regjeringeradhoc.regjering_reg_kode}"/>"> ${regjeringeradhoc.regjeringsnavn_NO}</a>
			</td>
            </c:if>
              <c:if test="${en}">
			<td>
			<a href="<p:url value="/regjering/statsraadsarkivet/regjeringsadhocbeskrivelse/?reg_adhoc=${regjeringeradhoc.regjering_reg_kode}"/>"> ${regjeringeradhoc.regjeringsnavn_ENG}</a>
			</td>
            </c:if>
           <td>${regjeringeradhoc.start}-${regjeringeradhoc.slutt}</td> 
		</tr>
		</c:forEach>
    </tbody>
</table>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>