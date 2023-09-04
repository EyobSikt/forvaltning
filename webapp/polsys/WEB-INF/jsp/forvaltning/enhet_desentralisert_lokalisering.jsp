<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<% pageContext.setAttribute("newLineChar", "\n"); %>
<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over lokaliseringer til en bestemt desentralisert enhet (tallgruppe).
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Lokalisering av ${fn:escapeXml(tallgruppe.navn)}, ${tallgruppe.aar} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Locations of ${fn:escapeXml(tallgruppe.navn)}, ${tallgruppe.aar} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Lokalisering av ${fn:escapeXml(tallgruppe.navn)} under ${fn:escapeXml(enhet.langtNavn)}." />
    <c:param name="beskrivelseEn" value="Locations of ${fn:escapeXml(tallgruppe.navn)} under ${fn:escapeXml(enhet.langtNavn)}." />
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
<a href=https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Unit #${enhet.idnum}
</c:if>
</div>

<h1>${fn:escapeXml(enhet.langtNavn)}</h1>


<%-- ======================================== Enhet-linker ==============  --%>
<c:import url="/WEB-INF/jspf/enhet_navigering.jsp">
    <c:param name="valgt" value="desentralisert" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<h3>${en ? "Locations of" : "Lokalisering av"} ${fn:escapeXml(tallgruppe.navn)} under ${fn:escapeXml(enhet.langtNavn)}</h3>
    

<p>
${en ? "Period:" : "Tidsperiode:"} 
<fmt:formatDate value="${tallgruppe.fraTidspunkt}" pattern="d.M.yyyy" /> - <fmt:formatDate value="${tallgruppe.tilTidspunkt}" pattern="d.M.yyyy" />
</p>

<p>${fn:replace(fn:escapeXml(tallgruppe.dokumentasjon), newLineChar, "<br />")}</p>


<c:if test="${no}">

<c:if test="${tallgruppe.kommentarNote != null}">
<h3>Kommentar</h3>
<p>${fn:replace(fn:escapeXml(tallgruppe.kommentarNote), newLineChar, "<br />")}</p>
</c:if>

<c:if test="${tallgruppe.plussEnhet != null || tallgruppe.minusEnhet != null}">
<h3><c:out value="${tallgruppe.kommentarEndring}" default="Endringer i forhold til foregående år:" escapeXml="true" /></h3>
<h4>Plussenheter</h4>
<p><c:out value="${tallgruppe.plussEnhet}" default="(Ingen)" escapeXml="true" /></p>
<h4>Minusenheter</h4>
<p><c:out value="${tallgruppe.minusEnhet}" default="(Ingen)" escapeXml="true" /></p>
</c:if>

<c:if test="${tallgruppe.kommentarAnnet != null}">
<h3>Annet</h3>
<p>${fn:replace(fn:escapeXml(tallgruppe.kommentarAnnet), newLineChar, "<br />")}</p>
</c:if>

<c:if test="${tallgruppe.kommentarKilde != null}">
<h3>Supplerende kilde</h3>
<p>${fn:replace(fn:escapeXml(tallgruppe.kommentarKilde), newLineChar, "<br />")}</p>
</c:if>


<c:if test="${tallgruppe.forrigeAar != null || tallgruppe.nesteAar != null}">
<p>
Når antall enheter mellom to kartlagte år er identisk, men det likefult har vært
lokaliseringsendringer (dvs. samme antall plussenheter som minusenheter), antas
antallet å ha vært det samme i de mellomliggende år. Dette er tilfelle her.
</p>
<c:if test="${tallgruppe.forrigeAar != null && tallgruppe.nesteAar != null}">
<p>Se derfor <strong>${fn:escapeXml(tallgruppe.forrigeAar)}</strong> og/eller <strong>${fn:escapeXml(tallgruppe.nesteAar)}</strong> for nærmeste bekreftede lokaliseringer.</p>
</c:if>
<c:if test="${tallgruppe.forrigeAar != null && tallgruppe.nesteAar == null}">
<p>Se derfor <strong>${fn:escapeXml(tallgruppe.forrigeAar)}</strong> for nærmeste bekreftede lokaliseringer.</p>
</c:if>
<c:if test="${tallgruppe.forrigeAar == null && tallgruppe.nesteAar != null}">
<p>Se derfor <strong>${fn:escapeXml(tallgruppe.nesteAar)}</strong> for nærmeste bekreftede lokaliseringer.</p>
</c:if>
</c:if>

</c:if>



<c:if test="${en}">

<%--
IKKE OVERSATT ENNÅ
<c:if test="${tallgruppe.kommentarNote != null}">
<h3>Kommentar</h3>
<p>${fn:replace(fn:escapeXml(tallgruppe.kommentarNote), newLineChar, "<br />")}</p>
</c:if>
--%>
<h3>Note</h3>
<p>
There may occur some Norwegian labels and language within the documentation of 
the localisation of the units. Please contact PolSys if this information seems important.
</p>

<c:if test="${tallgruppe.plussEnhet != null || tallgruppe.minusEnhet != null}">
<h3><c:out value="${tallgruppe.kommentarEndring}" default="Changes of locations compared to the previous year:" escapeXml="true" /></h3>
<h4>New unit(s)</h4>
<p><c:out value="${tallgruppe.plussEnhet}" default="(None)" escapeXml="true" /></p>
<h4>Abandoned unit(s)</h4>
<p><c:out value="${tallgruppe.minusEnhet}" default="(None)" escapeXml="true" /></p>
</c:if>

<%--
IKKE OVERSATT ENNÅ
<c:if test="${tallgruppe.kommentarAnnet != null}">
<h3>Annet</h3>
<p>${fn:replace(fn:escapeXml(tallgruppe.kommentarAnnet), newLineChar, "<br />")}</p>
</c:if>

<c:if test="${tallgruppe.kommentarKilde != null}">
<h3>Supplerende kilde</h3>
<p>${fn:replace(fn:escapeXml(tallgruppe.kommentarKilde), newLineChar, "<br />")}</p>
</c:if>
--%>

<c:if test="${tallgruppe.forrigeAar != null || tallgruppe.nesteAar != null}">
<p>
When the number of units between two mapped years are identical,
but there still have been changes of locations (i.e. the number of new units
and the number of abandoned units cancel each other out), the same number of
units are presumed to have existed in the years between these mapped years,
even though the exact locations cannot be confirmed. This is the case here.
</p>
<c:if test="${tallgruppe.forrigeAar != null && tallgruppe.nesteAar != null}">
<p>Thus, see <strong>${fn:escapeXml(tallgruppe.forrigeAar)}</strong> and/or <strong>${fn:escapeXml(tallgruppe.nesteAar)}</strong> for the nearest confirmed locations.</p>
</c:if>
<c:if test="${tallgruppe.forrigeAar != null && tallgruppe.nesteAar == null}">
<p>Thus, see <strong>${fn:escapeXml(tallgruppe.forrigeAar)}</strong> for the nearest confirmed locations.</p>
</c:if>
<c:if test="${tallgruppe.forrigeAar == null && tallgruppe.nesteAar != null}">
<p>Thus, see <strong>${fn:escapeXml(tallgruppe.nesteAar)}</strong> for the nearest confirmed locations.</p>
</c:if>
</c:if>

</c:if>



</div>

</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
