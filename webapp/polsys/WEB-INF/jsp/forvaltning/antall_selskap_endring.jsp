<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Antall enheter endring.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Antall enheter endring - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Antall enheter endring - State Administration Database" />
    <c:param name="beskrivelseNo" value="Antall enheter endring." />
    <c:param name="beskrivelseEn" value="Antall enheter endring." />
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
> Antall enheter endring
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Organizational changes affecting number og units
</c:if>
</div>

<h1>Organizational changes influencing the number of units (selskap). 1947-2012</h1>

<h2>Total</h2>
<c:set var="tabelldata" value="${data}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 0. Total" />
</c:import>

<br /><br />
<h2>Tilknytningsform</h2>
<c:set var="tabelldata" value="${data1a}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 1a. Heleide statsaksjeselskap" />
</c:import>

<c:set var="tabelldata" value="${data1b}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 1b. Særlovsselskap" />
</c:import>

<c:set var="tabelldata" value="${data1c}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 1c. Statsforetak" />
</c:import>

<c:set var="tabelldata" value="${data1d}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 1d. Statsselskap organisert etter 1965-loven" />
</c:import>

<c:set var="tabelldata" value="${data1e}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 1e. Statsaksjeselskap (deleigd; majoritet)" />
</c:import>

<c:set var="tabelldata" value="${data1f}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 1f. Helseforetak" />
</c:import>


<br /><br />
<h2>COFOG - grupper</h2>
<c:set var="tabelldata" value="${data3a}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 3a. COFOG Andre (1,2,3,5)" />
</c:import>

<c:set var="tabelldata" value="${data3b}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 3b. COFOG Økonomi (4)" />
</c:import>

<c:set var="tabelldata" value="${data3c}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp">
    <c:param name="tabelloverskrift" value="Tabell 3c. COFOG Velferd (6,7,8,9,10)" />
</c:import>

<br /><br />
<h2>COFOG</h2>
<c:set var="tabelldata" value="${data301}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.01. COFOG 01" /></c:import>
<c:set var="tabelldata" value="${data302}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.02. COFOG 02" /></c:import>
<c:set var="tabelldata" value="${data303}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.03. COFOG 03" /></c:import>
<c:set var="tabelldata" value="${data304}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.04. COFOG 04" /></c:import>
<c:set var="tabelldata" value="${data305}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.05. COFOG 05" /></c:import>
<c:set var="tabelldata" value="${data306}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.06. COFOG 06" /></c:import>
<c:set var="tabelldata" value="${data307}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.07. COFOG 07" /></c:import>
<c:set var="tabelldata" value="${data308}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.08. COFOG 08" /></c:import>
<c:set var="tabelldata" value="${data309}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.09. COFOG 09" /></c:import>
<c:set var="tabelldata" value="${data310}" scope="request" />
<c:import url="/WEB-INF/jspf/forvaltning/antall_enheter_endring_tabell.jsp"><c:param name="tabelloverskrift" value="Tabell 3.10. COFOG 10" /></c:import>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
