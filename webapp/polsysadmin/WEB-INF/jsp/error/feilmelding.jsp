<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Index-side for polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Det oppsto en feil</h1>

<p>Det oppsto en feil, databasen er <strong>ikke</strong> oppdatert.
    Trykk tilbake-knappen i nettleseren og rett opp feilen (hvis dette er en brukerfeil).</p>

<h3>Feilmelding:</h3>
<p>${fn:escapeXml(e)}</p>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
