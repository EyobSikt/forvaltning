<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over hierarkiet til en enhet.
--%>

<fmt:formatDate value="${brukerdato.date}" pattern="d.M.yyyy" var="dtext" />
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Hierarki per ${dtext} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Hierarchy per ${dtext} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Oversikt over ${fn:escapeXml(enhet.langtNavn)} sin plass i forvaltningshierarkiet per ${dtext}." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in hierarchy per ${dtext}." />
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
    <c:param name="valgt" value="hierarki" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<c:if test="${no}"><p>Denne siden viser enheten (i fet skrifttype) sin plass i forvaltningshierarkiet per ${brukerdato}.</p></c:if>
<c:if test="${en}"><p>This page shows this unit's (shown in bold) place in the state adminstration hierarchy per ${brukerdato}.</p></c:if>


<form action="" method="get">
<p>
${en ? "Select date:" : "Velg tidspunkt:"}

${en ? "Day:" : "Dag:"}
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}" ${brukerdato.dag eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

${en ? "Month:" : "Måned:"}
<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}" ${brukerdato.maaned eq i ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

${en ? "Year:" : "År:"} <input type="text" value="${brukerdato.aar}" maxlength="4" size="6" name="y" />

<input type="submit" value="OK" />
</p>
</form>

<c:if test="${staten == null}">

<c:if test="${no}">
    <p><em>Ingen data for gitt tidspunkt.</em></p>
    <p><em>Merk:</em></p>
    <p><em>Data er oppdatert per ${sistOppdatertDato}.</em></p>
    <p><em>Enheten ble opprettet ${opprettetDato}.</em></p>
    <c:if test="${nedlagtDato != null}"><p><em>Enheten ble nedlagt ${nedlagtDato}.</em></p></c:if>
</c:if>

<c:if test="${en}">
    <p><em>No data at given date.</em></p>
    <p><em>Please note:</em></p>
    <p><em>Data is updated per ${sistOppdatertDato}.</em></p>
    <p><em>This unit was founded: ${opprettetDato}.</em></p>
    <c:if test="${nedlagtDato != null}"><p><em>Unit termination date: ${nedlagtDato}.</em></p></c:if>
</c:if>
    
</c:if>


<c:if test="${!brukerdato.gyldig}">
    <p><em>${en ? "Invalid date." : "Dato er ugyldig."}</em></p>
</c:if>

    
<c:if test="${staten != null}">
<p:hierarki rot="${staten}" idnum="${enhet.idnum}" />
</c:if>

    
<c:if test="${en}">
<p><em>
* = If a name is denoted with an asterisk (*), this implies a direct translation from Norwegian to English.
The translation is thus not necessarily the official one (if any exists at all).
</em></p>
</c:if>


<div class="footnote">
<c:if test="${no}">
<h3>Merk</h3>
<p>
Denne siden viser alle overordnede og underordnede enheter til gitt enhet (i fet skrifttype).
Departementer og underliggende interne departementsorgan vises med blå farge, 
mens underliggende forvaltningsorgan vises med grønn farge.
Tilknyttede selskaper og stiftelser vises med henholdsvis rød og lilla farge. 
Enheter som representerer etater og grupper vises i lys grå farge.
</p>
<p>
I hierarkiet er noen lokale statlige enheter (for noen år) vist i kursiv skrift sammen med antallet.
I tilfeller der det lokale apparatet er estimert for det gitte året, er dette markert "(est.)".
Merk også at hierarkiet for store etater kan være noe forenklet ved bruk av "gruppe-enheter" 
som vises i lys grå farge.
</p>
</c:if>

<c:if test="${en}">
<h3>Remark</h3>
<p>
This page shows all superior and subordinate bodies to the given unit (shown in bold).
Ministries and bodies within ministries are showed in blue color, 
bodies outside ministries are showed in green color, and companies in red and foundations in purple color.
Units the represent a whole group or a whole integrated civil service organization are shown in light grey color.
</p>
<p>
Some local state units listed here are displayed in italic together with the number of units.
In cases where the local units are estimated, this is indicated '(est.)'.
Also note that the hierarchy of some large integrated civil service organization 
may not include alle local units.
</p>
</c:if>
</div>

</div>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
