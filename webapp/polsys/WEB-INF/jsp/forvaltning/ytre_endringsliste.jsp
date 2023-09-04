<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser liste over ytre endringer.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Ytre organisasjonsendringer - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Organizational change outside ministries - State Administration Database" />
    <c:param name="beskrivelseNo" value="Organisasjonsendringer fordelt på departement og tilknytingsform." />
    <c:param name="beskrivelseEn" value="Organizational change distributed on ministries and affiliations." />
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
> Organisasjonsendringer
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Organizational change
</c:if>
</div>

<h1>${en ? "Organizational change distributed on ministries and affiliations" : "Organisasjonsendringer fordelt på departement og tilknytingsform"}</h1>


<%-- ================================ feilmeldinger ====================== --%>
<c:if test="${feilValg1}">
    <c:if test="${no}"><p><strong>Vennligst velg et departement.</strong></p></c:if>
    <c:if test="${en}"><p><strong>Please choose a ministry.</strong></p></c:if>
</c:if>

<c:if test="${feilValg2}">
    <c:if test="${no}"><p><strong>Vennligst velg minst en tilknytningsform.</strong></p></c:if>
    <c:if test="${en}"><p><strong>Please choose at least one.</strong></p></c:if>
</c:if>

<c:if test="${feilValg3}">
    <c:if test="${no}"><p><strong>Vennligst velg minst en endring.</strong></p></c:if>
    <c:if test="${en}"><p><strong>Please choose at least one.</strong></p></c:if>
</c:if>

<c:if test="${feilTid}">
    <c:if test="${no}">
    <p><strong>Vennligst oppgi start- og sluttår. Startår må være lik eller mindre enn sluttår.</strong></p>
    </c:if>
    <c:if test="${en}">
    <p><strong>Please choose start- and end-year. start-year must be less than or equal to end-year.</strong></p>
    </c:if>
</c:if>
<%-- ===================================================================== --%>



<%-- =================================== data ============================ --%>
<c:if test="${!(empty endringer)}">

<h3>${en ? "Ministries:" : "Departement:"}</h3>
<c:if test="${departement == null}">${en ? "All ministries" : "Alle departementer"}</c:if>
<c:if test="${departement != null}">
<ul class="plain">
<li><a href="<p:url value="/data/enhet/${departement.idnum}" />">${fn:escapeXml(departement.langtNavn)}</a></li>
<c:forEach items="${departement.tidligereNavnListe}" var="navn">
<li>${fn:escapeXml(navn)}</li>
</c:forEach>
</ul>
</c:if>

<h3>${en ? "Period:" : "Tidsperiode:"} <fmt:formatDate value="${fraDato}" pattern="d.M.yyyy" /> - <fmt:formatDate value="${tilDato}" pattern="d.M.yyyy" /></h3>

<h3>${en ? "Total number of events:" : "Antall endringer:"} ${antallTotal}</h3>

<ul class="plain">
<c:forEach begin="0" end="${fn:length(endringer) - 1}" step="1" var="i">
<c:if test="${antallEndringer[i] > 0}">
<li>${antallEndringer[i]} ${fn:escapeXml(endringskoderNavn[endringer[i]].tekstFlertall)}
<ul class="plain">
<c:forEach begin="0" end="${fn:length(tilknytningsformer) - 1}" step="1" var="j">
<c:if test="${!(empty endringsliste[i][j])}">
<li>${fn:length(endringsliste[i][j])} ${fn:escapeXml(tilknytningsformNavn[tilknytningsformer[j]].tekstFlertall)}</li>
</c:if>
</c:forEach>
</ul></li>
</c:if>
</c:forEach>
</ul>


<c:forEach begin="0" end="${fn:length(endringer) - 1}" step="1" var="i">
<c:if test="${antallEndringer[i] > 0}">
<h2>${fn:escapeXml(endringskoderNavn[endringer[i]].tekstFlertall)}</h2>

<c:forEach begin="0" end="${fn:length(tilknytningsformer) - 1}" step="1" var="j">
<c:if test="${!(empty endringsliste[i][j])}">
<h3>${fn:escapeXml(tilknytningsformNavn[tilknytningsformer[j]].tekstFlertall)}</h3>
<ul class="plain">
<c:forEach items="${endringsliste[i][j]}" var="e">
<li><fmt:formatDate value="${e.tidspunkt}" pattern="dd.MM.yyyy" />: <a href="<p:url value="/data/enhet/${e.idnum}" />">${fn:escapeXml(e.langtNavn)}</a></li>
</c:forEach>
</ul>
</c:if>
</c:forEach>

</c:if>
</c:forEach>


</c:if>
<%-- ===================================================================== --%>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
