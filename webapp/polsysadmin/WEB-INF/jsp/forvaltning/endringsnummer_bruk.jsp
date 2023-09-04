<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Liste over endringskoder.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Endringsnummer (${param.enummer}) - Forvaltning</h1>

<c:if test="${!empty tab}">
<p:tabell value="${tab}" />
</c:if>

<c:if test="${empty tab}">
<p><em>Dette endringsnummeret er ikke i bruk.</em></p>
</c:if>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
