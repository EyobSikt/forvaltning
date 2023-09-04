<% response.setStatus(javax.servlet.http.HttpServletResponse.SC_NOT_FOUND); %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Side som vises ved HTTP 404-feil.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittel" value="Data om det politiske system" />
</c:import>

<div id="main">

<h1>Fant ikke siden</h1>
<p>Fant ikke siden du etterspurte.</p>

<h2>Page not found</h2>
<p>The requested page was not found.</p>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
