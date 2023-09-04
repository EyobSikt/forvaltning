<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Enheter per COFOF.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="COFOG: ${cofog[kode]} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="COFOG: ${cofog[kode]} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Enheter kategorisert på COFOG i forvaltningsdatabasen." />
    <c:param name="beskrivelseEn" value="Units by COFOG in the State Administration Database." />
</c:import>
</c:if>
<c:if test="${en}">
<c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> <a href="<p:url value="/data/cofog" />">COFOG</a>
> ${cofog[kode]}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> <a href="<p:url value="/data/cofog" />">COFOG</a>
> ${cofog[kode]}
</c:if>
</div>

<h1>${en ? "Units by COFOG" : "Enheter på COFOG"}: ${cofog[kode]}</h1>

<c:if test="${no}"><p>Enheter kategorisert på COFOG: ${cofog[kode]}.</p></c:if>
<c:if test="${en}"><p>Units categorized by COFOG: ${cofog[kode]}.</p></c:if>

<%-- ====================================== Dato ========================= --%>
<h3>${en ? "Date:" : "Tidspunkt:"}</h3>
<form action="" method="get">
<p>
${en ? "Year:" : "År:"} <input type="text" value="${brukerdato.aar}" maxlength="4" size="6" name="y" />

${en ? "Month:" : "Måned:"}
<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}" ${brukerdato.maaned eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

${en ? "Day:" : "Dag:"}
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}" ${brukerdato.dag eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

<input type="submit" value="OK" />
</p>
</form>
<%-- ===================================================================== --%>


<c:if test="${empty enheter}">
<p><em>${en ? "No units on selected date" : "Ingen enheter på valgt dato"}.</em></p>
</c:if>


<c:if test="${!empty enheter}">
<h3>${en ? "Units" : "Enheter"} per ${brukerdato}</h3>
<ul>
<c:forEach items="${enheter[kode]}" var="e">
<li><a href="<p:url value="/data/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></li>
</c:forEach>
</ul>
</c:if>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
