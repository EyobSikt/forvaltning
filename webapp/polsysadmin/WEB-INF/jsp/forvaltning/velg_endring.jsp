<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
 - Copyright SIKT
 - Description: Index-side for polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Endring/Enhet</h1>

<c:if test="${param.endringslettet != null}">
<h2 style="color:green;">-- Record slettet fra databasen --</h2>
</c:if>

<p>Opprett en ny enhet eller, oppgi en bestemt id til en endring eller idnum til en enhet.</p>

<%--<form action="<c:url value="/forvaltning/endring.p" />" method="get">--%>
<form action="<c:url value="/forvaltning/endring.p" />" method="post">
<p><input type="submit" value="Opprett ny enhet" /></p>
</form>

<form action="<c:url value="/forvaltning/endring.p" />" method="get">
<p>ID: <input type="text" name="id" value="" /> <input type="submit" value="OK" /></p>
</form>

<form action="<c:url value="/forvaltning/endringliste.p" />" method="get">
<p>IDNUM: <input type="text" name="idnum" value="" /> <input type="submit" value="OK" /></p>
</form>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
