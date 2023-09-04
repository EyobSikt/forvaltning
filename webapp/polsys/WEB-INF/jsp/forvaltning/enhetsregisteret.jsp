<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<link rel="stylesheet" href="<p:url value="/css/style.css"/>" type="text/css"  media="screen"/>
<script src="<p:url value="/js/jquery-1.4.2.min.js"/>" type="text/javascript"></script>
<script src="<p:url value="/js/scripts.js"/>" type="text/javascript"></script>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Departementer per ${brukerdato} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Ministries per ${brukerdato} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Oversikt over norske departementer per ${brukerdato}." />
    <c:param name="beskrivelseEn" value="List of Norwegian ministries per ${brukerdato}." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>

<div id="main">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> Organisasjoner
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Organisations
</c:if>
</div>

<h2>Hierarki over organisasjoner</h2>
    <p><em>Siden viser hierarki over organisasjoner som finnes på Statens sentrale tjenestemannsregister (SST) og Brønnøysundregistrene.</em></p>
    <h3></h3>
<h3></h3>

    <form action="" method="get">

            <p>
                Velg år:

                <select name="aar">
                    <c:forEach items="${alleAar}" var="i">
                        <option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
                    </c:forEach>

                </select>
                ${en ? "Choose department:" : "Department:"}
                <select name="orgnummer">
                    <option value="">Alle departmenter</option>
                    <c:forEach items="${departementer2}" var="i">
                        <option value="${i.idnum}" ${i.idnum eq param.orgnummer ? 'selected="selected"' : ''} >${i.langtNavn}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="OK" />
            </p>

    </form>


<c:forEach items="${staten2}" var="staten">
       <p:enhethierarki rot="${staten.get(0)}" idnum="${enhet.idnum}" />
</c:forEach>

</div>

<div id="sidebar">
<c:if test="${no}">

  <h3>Søk</h3>
    <p>
På <a href="<p:url value="/data/organisasjon/sok"/>">Søk</a>
 side kan du søke på organisasjonsnavn eller kommune hvor enheten har sin forretningsadresse. Du kan velge variabler som skal vises i resultattabell.
</p>
<h3>Statistikk side</h3>
<p>
På <a href="<p:url value="/data/organisasjon/statistikk"/>">statistikk</a>
kan du se oversikt over organisasjoner based på ogranisasjonsform, næringskode, sektorkode.
</p>

</c:if>
<c:if test="${en}">
</c:if>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
