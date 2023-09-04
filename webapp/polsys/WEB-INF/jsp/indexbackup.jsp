<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Index-side for polsys.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>

<div id="main">

<c:if test="${no}"><h1>Data - PolSys</h1></c:if>
<c:if test="${en}"><h1>Data - PolSys</h1></c:if>

<c:if test="${no}"><p>
PolSys dataarkiv.
</p></c:if>
<c:if test="${en}"><p>
PolSys data archives.
</p></c:if>

<c:if test="${no}"><a href="<m:url value="/en/" />">View this page in English.</a></c:if>
<c:if test="${en}"><a href="<m:url value="/" />">View this page in Norwegian.</a></c:if>

<c:if test="${no}">
<h2><a href="<p:url value="/forvaltning/" />">Forvaltning</a></h2>
</c:if>
<c:if test="${en}">
<h2><a href="<p:url value="/forvaltning/" />">Civil Service</a></h2>
</c:if>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
