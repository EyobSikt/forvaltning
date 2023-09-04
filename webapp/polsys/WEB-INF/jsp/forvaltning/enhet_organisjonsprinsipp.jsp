<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Organisjonsprinsipp - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Organizational principle - State Administration Database" />
    <c:param name="beskrivelseNo" value="Organisjonsprinsipper til ${fn:escapeXml(enhet.langtNavn)}" />
    <c:param name="beskrivelseEn" value="Instructions, ${fn:escapeXml(enhet.langtNavn)}" />
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
    <c:param name="valgt" value="organisjonsprinsipp" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${empty overordnet}">
<c:if test="${no}"><p>Siden viser organisjonsprinsipp til denne enheten.</p></c:if>
<c:if test="${en}"><p>This page shows organisjonsprinsipp for this unit.</p></c:if>
</c:if>

<c:if test="${!(empty overordnet)}">
<p>${en ? "This page shows annual reports for" : "Siden viser organisjonsprinsipper for"} ${fn:escapeXml(overordnet.langtNavn)}.</p>
</c:if>

<c:if test="${empty organisjonsprinsipp}">
<p><em>${en ? "No annual reports where found for this unit." : "Ingen organisjonsprinsipper er registrert for denne enheten."}</em></p>
</c:if>


<c:if test="${!(empty organisjonsprinsipp)}">
    <table class="zebra sortable">
        <caption>${en ? "Organizational principle" : "Organisjonsprinsipp"}</caption>
        <thead>
        <tr>
            <th>${en ? "From" : "Fra"}</th>
            <th>${en ? "To" : "Til"}</th>
            <th>${en ? "Organizational principle" : "Organisjonsprinsipp"}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${organisjonsprinsipp}" var="a">

            <fmt:formatDate value="${a.fraTidspunkt}" pattern="d" var="fradag" />
            <fmt:formatDate value="${a.fraTidspunkt}" pattern="M" var="framaaned" />
            <fmt:formatDate value="${a.fraTidspunkt}" pattern="yyyy" var="fraaar" />
            <fmt:formatDate value="${a.tilTidspunkt}" pattern="d" var="tildag" />
            <fmt:formatDate value="${a.tilTidspunkt}" pattern="M" var="tilmaaned" />
            <fmt:formatDate value="${a.tilTidspunkt}" pattern="yyyy" var="tilaar" />

           <tr>
                <td class="tdtext"> <c:if test="${fradag != null}">
                    ${fradag}/${framaaned}/${fraaar}
                </c:if>
                </td>
               <td class="tdtext"><c:if test="${tildag != null}">
                    ${tildag}/${tilmaaned}/${tilaar}
                </c:if>
                </td>
                <td class="tdtext">
                    <c:if test="${no && a.norskOrgPrinsipp != null}">
                        ${fn:escapeXml(a.norskOrgPrinsipp)}
                    </c:if>
                    <c:if test="${en && a.engelskOrgPrinsipp != null}">
                        ${fn:escapeXml(a.engelskOrgPrinsipp)}
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</div>
</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
