<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over relasjoner til en enhet.
--%>

<fmt:formatDate value="${brukerdato.date}" pattern="d.M.yyyy" var="dtext" />
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Relasjoner - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Relations - State Administration Database" />
    <c:param name="beskrivelseNo" value="Oversikt over relasjoner til ${fn:escapeXml(enhet.langtNavn)} i forvaltningshierarkiet per." />
    <c:param name="beskrivelseEn" value="Relations for ${fn:escapeXml(enhet.langtNavn)}." />
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
> Enhet #${enhet.idnum}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Unit #${enhet.idnum}
</c:if>
</div>

<h1>${fn:escapeXml(enhet.langtNavn)}</h1>

<%-- ======================================== Enhet-linker ==============  --%>
<c:import url="/WEB-INF/jspf/enhet_navigering.jsp">
    <c:param name="valgt" value="relasjon" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}">
   <p>Denne siden viser alle enheter som er relatert til denne enheten (i fet skrifttype).
      (Navn til enhetene er siste registrerte navn.)</p>
</c:if>

<c:if test="${en}">
   <p>This page shows all units related (both explicit and implicit) to this unit (in bold).
   (Names for units used in the table are the last registered names.).</p>
</c:if>
   
<c:if test="${param.alle == null}">
<c:if test="${no}">
   <p><strong>Vis kun direkte relaterte enheter</strong> |
      <a href="<p:url value="/data/enhet/${enhet.idnum}/relasjon?alle" />">Vis alle (indirekte) relaterte enheter</a></p>
</c:if>
<c:if test="${en}">
   <p><strong>Show only directly related units</strong> |
      <a href="<p:url value="/data/enhet/${enhet.idnum}/relasjon?alle" />">Show all related units</a></p>
</c:if>
</c:if>

<c:if test="${param.alle != null}">
<c:if test="${no}">
   <p><a href="<p:url value="/data/enhet/${enhet.idnum}/relasjon" />">Vis kun direkte relaterte enheter</a> |
      <strong>Vis alle (indirekte) relaterte enheter</strong></p>
</c:if>
<c:if test="${en}">
   <p><a href="<p:url value="/data/enhet/${enhet.idnum}/relasjon" />">Show only directly related units</a> |
      <strong>Show all related units</strong></p>
</c:if>
</c:if>


<c:if test="${!empty relevanteRelasjoner}">
<table class="text zebra">
<thead>
<tr>
   <th>${en ? "Date" : "Dato"}</th>
   <th>${en ? "Unit A" : "Enhet A"}</th>
   <th>${en ? "Event" : "Endring"}</th>
   <th>${en ? "Relation" : "Relasjon"}</th>
   <th>${en ? "Unit B" : "Enhet B"}</th>
</tr>
</thead>

<tbody>
<c:set var="date" value="" />
<c:forEach items="${relevanteRelasjoner}" var="rel">

<c:if test="${date == rel.tidspunkt}"><tr></c:if>
<c:if test="${date != rel.tidspunkt}"><tr style="border-top: 2px solid #777777;"></c:if>
<c:set var="date" value="${rel.tidspunkt}" />

<td><fmt:formatDate value="${rel.tidspunkt}" pattern="dd.MM.yyyy" /></td>


<td>
   ${enhet.idnum eq rel.enhetA.idnum ? '<strong>' : ''}
   <a href="<p:url value="/data/enhet/${rel.enhetA.idnum}" />">${fn:escapeXml(rel.enhetA.langtNavn)}</a>
   ${enhet.idnum eq rel.enhetA.idnum ? '</strong>' : ''}
</td>

<td><abbr title="${fn:escapeXml(endringskoder[rel.endringskode].tekstEntall)}">${rel.endringskode}</abbr></td>
<td>${fn:escapeXml(endringskoder[rel.endringskode].relasjontekst)}</td>

<td>
   ${enhet.idnum eq rel.enhetB.idnum ? '<strong>' : ''}
   <a href="<p:url value="/data/enhet/${rel.enhetB.idnum}" />">${fn:escapeXml(rel.enhetB.langtNavn)}</a>
   ${enhet.idnum eq rel.enhetB.idnum ? '</strong>' : ''}
</td>

</tr>
</c:forEach>
</tbody>
</table>
</c:if>


<c:if test="${empty relevanteRelasjoner}"><p><em>${en ? "No relations for this unit." : "Ingen relaterte enheter."}</em></p></c:if>


</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
