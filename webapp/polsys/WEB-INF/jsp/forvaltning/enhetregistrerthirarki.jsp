<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />


<fmt:formatDate value="${brukerdato.date}" pattern="d.M.yyyy" var="dtext" />
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(navn)} - Hierarki per ${dtext} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(navn)} - Hierarchy per ${dtext} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Oversikt over ${fn:escapeXml(navn)} sin plass i forvaltningshierarkiet per ${dtext}." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(navn)} in hierarchy per ${dtext}." />
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
            > Enhet #${idnum}
        </c:if>
        <c:if test="${en}">
            You are here:
            <a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
            > <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
            > Unit #${idnum}
        </c:if>
    </div>
    <c:forEach items="${orginfo}" var="orginfo">
    <h1>${fn:escapeXml(orginfo.navn)} (Orgnr. ${orginfo.idnum})</h1>
    </c:forEach>
    <%-- ======================================== Enhet-linker ==============  --%>
    <c:import url="/WEB-INF/jspf/enhet_navigering.jsp">
        <c:param name="valgt" value="enhetsregisterethierarki" />
    </c:import>



    <%-- ====================================================================  --%>

<div class="enhetcontent">

        <form action="" method="get">
            <p>

                ${en ? "Choose year:" : " Velg år:"}
                <select name="aar">
                    <c:forEach items="${alleAar}" var="i">
                        <option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
                    </c:forEach>

                </select>
                <input type="submit" value="OK" />
            </p>
        </form>


<%-- ======================================== Enhet-nøkkelopplysninger ==============  --%>
    <c:if test="${staten != null && fn:length(staten.get(0)) > 0}">
    <div class="enhetcontent">
        <p>${en ? "This unit ((shown in bold)) place in the CCR hierarchy." : "Denne siden viser enheten (i fet skrifttype) sin plass i enhetsregisteret hierarki per "} <strong>år = ${valgtAar}</strong></p>
        <%-- ======================================== Siste nøkkelopplysninger ==  --%>
        <%-- ========= følgende viser  (i fet skrifttype) sin plass i forvaltningshierarkiet ==========  --%>
        <div class="event">
            <c:forEach items="${staten}" var="staten">
                <p:enhetsregisterethierarki rot="${staten.get(0)}" idnum="${idnum}" />
            </c:forEach>
        </div>
    </div>
    </c:if>
    <c:if test="${staten == null || fn:length(staten.get(0)) <= 0}">
        <p>${en ? "This unit does not find in the CCR hierarchy." : "Denne  enheten finnes ikke i enhetsregisteret hierarki."}</p>
    </c:if>
</div>


<%--
<div id="sidebar">
    <c:if test="${no}">
    <h3>Organisasjons Hierarki</h3>
        <p>
            På <a href="<p:url value="/forvaltning/enhetsregisteret"/>">hierarki</a>
            kan du se oversikt over organisasjonshierarki.
      </p>
    <h3>Søk</h3>
    <p>
        På <a href="<p:url value="/forvaltning/organisasjon/sok"/>">Søk</a>
        side kan du søke på organisasjonsnavn eller kommune hvor enheten har sin forretningsadresse. Du kan velge variabler som skal vises i resultattabell.
    </p>
    <h3>Statistikk side</h3>
    <p>
        På <a href="<p:url value="/forvaltning/organisasjon/statistikk"/>">statistikk</a>
        kan du se oversikt over organisasjoner based på ogranisasjonsform, næringskode, sektorkode.
    </p>

        </c:if>
        <c:if test="${en}">
        </c:if>

</div>
--%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
