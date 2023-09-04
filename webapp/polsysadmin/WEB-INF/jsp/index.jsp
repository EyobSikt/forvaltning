<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- 
 - Author(s): ET
 - Copyright NSD
 - Description: Index-side for polsys-admin.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Forvaltning-Admin</h1>

<p>Melding: ${fn:escapeXml(param.m)}</p>

<p>Forvaltning-administrasjonssider.</p>

<ul>
<li><a href="<c:url value="/felles/parametre.p" />">Parametre</a></li>
<li><a href="<c:url value="/forvaltning/index.p" />">Forvaltning</a></li>
<li><a href="<c:url value="/forvaltning/uploadfile.p" />">Last opp fil</a></li>
<%--
<li><a href="<c:url value="/storting/index.p" />">Storting</a></li>
<li><a href="<c:url value="/regjering/index.p" />">Regjering</a></li>
<li><a href="<c:url value="/parti/index.p" />">Parti</a></li>
--%>
</ul>
 

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
