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


<div id="main" class="superwide">
	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/regjeringer/" />">Regjeringer </a>
			> Regjering årsak avgang
		</c:if>
	</div>


	<c:forEach items="${regjeringsarsakavgang}" var="regjeringsarsakavgang" >
		<h2> ${regjeringsarsakavgang.fleirtaltekst} gjekk av ${regjeringsarsakavgang.sluttdato} </h2>
	</c:forEach>

	<c:forEach items="${regjeringsarsakavgangbeskrivelse}" var="regjeringsarsakavgangbeskrivelse" >
		<c:choose><c:when test = "${param.regjeringskode == 9 || param.regjeringskode == 12}"><h3>${regjeringsarsakavgangbeskrivelse.sluttdato}<br></h3>${regjeringsarsakavgangbeskrivelse.fleirtaltekst}</c:when>
     	<c:otherwise><div> ${regjeringsarsakavgangbeskrivelse.fleirtaltekst} </div></c:otherwise></c:choose>
	    </c:forEach>


</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>