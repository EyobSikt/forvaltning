<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<% pageContext.setAttribute("newLineChar", "\n"); %>
<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser endringsoversikt til en enhet.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(navn)} - Endringshistorie - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(navn)} - Event history - State Administration Database" />
    <c:param name="beskrivelseNo" value="Endringshistorien til enhet: ${fn:escapeXml(navn)}." />
    <c:param name="beskrivelseEn" value="Event history: for unit: ${fn:escapeXml(navn)}." />
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

<h1>${fn:escapeXml(navn)}</h1>

<%-- ======================================== Enhet-linker ==============  --%>
<c:import url="/WEB-INF/jspf/enhet_navigering.jsp">
    <c:param name="valgt" value="endringshistorie" />
</c:import>
<%-- ====================================================================  --%>

<div class="enhetcontent">

<c:if test="${no}"><p>Denne siden viser endringshistorien til denne enheten.</p></c:if>
<c:if test="${en}"><p>This page shows the event history for this unit.</p></c:if>

<c:set var="vis291" value="false" />

<c:forEach items="${endringer}" var="endring">

<div class="e${endring.endringskode}">

<c:if test="${endring.endringskode eq 291}"><c:set var="vis291" value="true" /></c:if>


<c:if test="${endring.idnum ne -1}">
<h3 class="eventh">
<fmt:formatDate value="${endring.tidspunkt}" pattern="dd.MM.yyyy" /><c:if test="${no}">${(endring.bekreftetDato ? '' : '<abbr title="Tidspunkt ikke bekreftet">*</abbr>')}</c:if><c:if test="${en}">${(endring.bekreftetDato ? '' : '<abbr title="Date not confirmed">*</abbr>')}</c:if>
${fn:escapeXml(endringskoder[endring.endringskode].tekstEntall)}
</h3>
</c:if>

<c:if test="${endringskoder[endring.endringskode].kortDokumentasjon != null}">
<div class="event">
<span class="disclaimer">(${fn:escapeXml(endringskoder[endring.endringskode].kortDokumentasjon)})</span>
</div>
</c:if>

<c:if test="${endring.langtNavn != null}">
<div class="event"><span class="eventl">${en ? "Name:" : "Navn:"}</span>
<span class="eventv">${fn:escapeXml(endring.langtNavn)}</span></div>
</c:if>

<c:if test="${endring.kortNavn != null}">
<div class="event"><span class="eventl">${en ? "Short name:" : "Kort navn:"}</span>
<span class="eventv">${fn:escapeXml(endring.kortNavn)}</span></div>
</c:if>

<c:if test="${endring.tilknytningsform != null}">
<div class="event"><span class="eventl">${en ? "Affiliations:" : "Tilknytningsform:"}</span>
<span class="eventv">${fn:escapeXml(tilknytninger[endring.tilknytningsform].tekstEntall)}</span></div>
</c:if>

<c:if test="${endring.nivaa != null}">
<div class="event"><span class="eventl">${en ? "Hierarchical level:" : "Administrativt nivå:"}</span>
<span class="eventv">${fn:escapeXml(nivaaer[endring.nivaa].tekstEntall)}</span></div>
</c:if>

<c:if test="${endring.cofog != null}">
<div class="event"><span class="eventl">${en ? "COFOG" : "COFOG:"}</span>
<span class="eventv">${fn:escapeXml(cofog[endring.cofog])}</span>
</div>
</c:if>

<c:if test="${endring.overordnetReell != null}">
<div class="event">
<span class="eventl">${en ? "Superior body:" : "Overordnet:"}</span>
<span class="eventv"><a href="<p:url value="/data/enhet/${endring.overordnetReell.idnum}" />">${fn:escapeXml(endring.overordnetReell.langtNavn)}</a></span>
</div>
</c:if>

<c:if test="${endring.overordnetEnhet != null && endring.overordnetEnhet.idnum ne endring.overordnetReell.idnum}">
<div class="event">
<span class="eventl">
<c:if test="${no}">${endring.grunnenhet eq 11 ? "Tilhører etat:" : "Tilhører gruppe :"}</c:if>
<c:if test="${en}">Belongs to group</c:if>
</span>
<span class="eventv"><a href="<p:url value="/data/enhet/${endring.overordnetEnhet.idnum}" />">${fn:escapeXml(endring.overordnetEnhet.langtNavn)}</a></span>
</div>
</c:if>

<c:if test="${endring.grunnenhet != null}">
<div class="event"><span class="eventl">${en ? "Main affiliation category:" : "Type enhet:"}</span>
<span class="eventv">${fn:escapeXml(grunnenheter[endring.grunnenhet].tekstEntall)}</span></div>
</c:if>

<c:if test="${endring.kommune != null}">
<div class="event"><span class="eventl">${en ? "Located:" : "Lokalisering:"}</span>
<span class="eventv">${endring.kommune.kode} ${fn:escapeXml(endring.kommune.tekstEntall)}</span></div>
</c:if>

<c:if test="${!(empty endring.stortingetsaksnummer) }">
    <div class="event">
        <div class="eventl"><span class="eventl">${en ? "Procedure and documents for this change:" : "Saksgang og dokumenter for denne endringen:"}</span></div>
        <div class="eventv">
            <ul class="data">
        <c:forEach items="${endring.stortingetsaksnummer}" var="stortingetsaksnummer">
            <li> <a href="https://www.stortinget.no/no/Saker-og-publikasjoner/Saker/Sak/?p=${stortingetsaksnummer.idnum}" target="_blank">${en ? "Case number Parliament" : "Saksnummer Stortinget"}</a> sakid=(${stortingetsaksnummer.idnum})</li>
        </c:forEach>
            </ul>
        </div>
    </div>
    </c:if>

<c:if test="${!(empty endring.relasjoner) || !(empty endring.relasjonerAndre)}">
<div class="event">
<div class="eventl"><c:out value="${endringskoder[endring.endringskode].relasjontekst}" default="Relasjoner:" escapeXml="true" /></div>
<div class="eventv">
<ul class="data">
<c:forEach items="${endring.relasjoner}" var="relasjon">
<li><a href="<p:url value="/data/enhet/${relasjon.idnum}" />">${fn:escapeXml(relasjon.langtNavn)}</a></li>
</c:forEach>

<%-- Andre relasjonsenheter --%>
<c:forEach items="${endring.relasjonerAndre}" var="relasjon">
<li>${en ? fn:escapeXml(relasjon.engelskLangtNavn) : fn:escapeXml(relasjon.langtNavn)}</li>
</c:forEach>
</ul>
</div>
</div>
</c:if>

<c:if test="${endring.dok != null && no}">
<div class="event">
<div class="eventv"><p>${fn:replace(fn:escapeXml(endring.dok), newLineChar, "<br />")}</p></div>
</div>
</c:if>

<c:if test="${endring.engelskDok != null && en}">
<div class="event">
<div class="eventv"><p>${fn:replace(fn:escapeXml(endring.engelskDok), newLineChar, "<br />")}</p></div>
</div>
</c:if>

<c:if test="${endring.engelskDok == null && endring.dok != null && en}">
<div class="event">
<div class="eventv"><p><em>Please note that this event has comments in Norwegian that are for the present not translated into English.</em></p></div>
</div>
</c:if>


</div>

</c:forEach>


<div class="footnote">
<c:if test="${no}">
<h3>Merk</h3>
<p><em>* = Dato merket med asterisk (*), betyr at tidspunkt ikke er bekreftet.</em></p>
<%--<c:if test="${vis291}"><p><a href="javascript:onClick=showClass('e291');">Vis alle endringer av overordnet</a>.</p>--%>
<c:if test="${vis291}"><p><a href="javascript:onClick=myShowHide('e291');">Vis alle endringer av overordnet</a>.</p>
  <div id="showhide291" style="display: none">
        <c:forEach items="${endringer}" var="endring">
            <c:if test="${fn:escapeXml(endring.endringskode) == 291}">
                <p class="eventv"><a target=_blank href="<p:url value="/data/enhet/${endring.overordnetEnhet.idnum}" />">${fn:escapeXml(endring.overordnetEnhet)}</a></p>
            </c:if>
        </c:forEach>
   </div>
</c:if>
</c:if>

<c:if test="${en}">
<h3>Remarks</h3>
<p><em>* = If a date is denoted with an asterisk (*), this implies that the date is not confirmed.</em></p>
<p><em>
* = If a name is denoted with an asterisk (*), this implies a direct translation from Norwegian to English.
The translation is thus not necessarily the official one (if any exists at all).
</em></p>
<p><em>
Comment to the change-of-name event: Sometimes the old and new name is the same.
This occur when the translation to English haven’t taken into consideration minor Norwegian name change.
</em></p>
<%--<c:if test="${vis291}"><p><a href="javascript:onClick=showClass('e291');">Show all changes of superior</a>.</p>--%>
<c:if test="${vis291}"><p><a href="javascript:onClick=myShowHide('e291');">Show all changes of superior</a>.</p>
  <div id="showhide291" style="display: none">
        <c:forEach items="${endringer}" var="endring">
            <c:if test="${fn:escapeXml(endring.endringskode) == 291}">
                <p class="eventv"><a target=_blank href="<p:url value="/data/enhet/${endring.overordnetEnhet.idnum}" />">${fn:escapeXml(endring.overordnetEnhet)}</a></p>
            </c:if>
        </c:forEach>
    </div>
</c:if>
</c:if>
</div>

</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
