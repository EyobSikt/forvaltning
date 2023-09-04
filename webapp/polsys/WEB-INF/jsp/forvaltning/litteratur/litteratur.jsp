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
 - Description: Viser oversikt over en bestemt litteratur/publikasjon.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Publikasjon ${litteratur.pubId} - Litteratur - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Publication ${litteratur.pubId} - Literature - State Administration Database" />
    <c:param name="beskrivelseNo" value="Tittel: ${fn:escapeXml(litteratur.tittel)}, årstall: ${fn:escapeXml(litteratur.aar)}, utgiver: ${fn:escapeXml(litteratur.utgiver)}, forfatter: ${fn:escapeXml(litteratur.forfatter)}" />
    <c:param name="beskrivelseEn" value="Title: ${fn:escapeXml(litteratur.tittel)}, year: ${fn:escapeXml(litteratur.aar)}, publisher: ${fn:escapeXml(litteratur.utgiver)}, authors: ${fn:escapeXml(litteratur.forfatter)}" />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp">
    </c:import>
</c:if>
<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningslitteratur.html">Litteratur</a>
> Publikasjon #${litteratur.pubId}
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/civilservice/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationliterature.html">Literature</a>
> Publication #${litteratur.pubId}
</c:if>
</div>

<div class="section">
<h3 class="news_super">${fn:escapeXml(litteratur.forfatter)} (${fn:escapeXml(litteratur.aar)}):</h3>
<h1 class="news_head">${fn:escapeXml(litteratur.tittel)}</h1>
<p>${fn:escapeXml(litteratur.utgiver)}</p>
</div>

<c:if test="${en}">
<div class="section">
<p class="nottranslated">Please note: This page may contain data in Norwegian that is not translated to English.</p>
</div>
</c:if>

<div class="section">
<c:if test="${litteratur.type.tekstEntall != null}">
<h4 class="literatureh">${en ? "Type of publication" : "Publikasjonstype"}:</h4>
<p>${fn:escapeXml(litteratur.type.tekstEntall)}</p>
</c:if>

<c:if test="${litteratur.lenkePublikasjon != null}">
<h4 class="literatureh">${en ? "Link to publication" : "Fulltekst"}:</h4>
<p><a href="${fn:escapeXml(litteratur.lenkePublikasjon)}" target="_blank">${fn:escapeXml(litteratur.lenkePublikasjon)}</a></p>
</c:if>

<c:if test="${litteratur.lenkeOmtale != null}">
<h4 class="literatureh">${en ? "Link to review" : "Omtale"}:</h4>
<p><a href="${fn:escapeXml(litteratur.lenkeOmtale)}" target="_blank">${fn:escapeXml(litteratur.lenkeOmtale)}</a></p>
</c:if>

<c:if test="${litteratur.kommentarEkstern != null}">
<h4 class="literatureh">${en ? "Comment" : "Kommentar"}:</h4>
<p>${fn:replace(fn:escapeXml(litteratur.kommentarEkstern), newLineChar, "<br />")}</p>
</c:if>

<c:if test="${litteratur.antallSider != null}">
<h4 class="literatureh">${en ? "Number of pages" : "Antall sider"}:</h4>
<p>${litteratur.antallSider}</p>
</c:if>

<c:if test="${litteratur.isbn != null}">
<h4 class="literatureh">${en ? "ISBN" : "ISBN-nummer"}:</h4>
<p>${fn:escapeXml(litteratur.isbn)}</p>
</c:if>

<c:if test="${litteratur.issn != null}">
<h4 class="literatureh">${en ? "ISSN" : "ISSN-nummer"}:</h4>
<p>${fn:escapeXml(litteratur.issn)}</p>
</c:if>

<c:if test="${litteratur.spraak != null}">
<h4 class="literatureh">${en ? "Language of publication" : "Publiseringsspråk"}:</h4>
<p>${fn:escapeXml(litteratur.spraak)}</p>
</c:if>

<c:if test="${litteratur.land != null}">
<h4 class="literatureh">${en ? "Country of publication" : "Land publikasjonen kommer fra"}:</h4>
<p>${fn:escapeXml(litteratur.land)}</p>
</c:if>

<c:if test="${litteratur.pubId != null}">
<h4 class="literatureh">${en ? "NSD-reference" : "NSD-referanse"}:</h4>
<p>${litteratur.pubId}</p>
</c:if>

<c:if test="${litteratur.sisteRegTid != null}">
<h4 class="literatureh">${en ? "This page was last updated" : "Disse opplysningene er sist endret"}:</h4>
<p><fmt:formatDate value="${litteratur.sisteRegTid}" pattern="d/M yyyy" /></p>
</c:if>
</div>


<div class="section">

<c:if test="${!(empty LittEnheter)}">
<h4 class="literatureh">${en ? "State units related to this publication" : "Spesifikke virksomheter publikasjonen omhandler"}:</h4>
<ul class="plain er">
<c:forEach items="${LittEnheter}" var="l">
<li><a href="<p:url value="/data/enhet/${l.enhet.idnum}" />">${fn:escapeXml(l.enhet.langtNavn)}</a></li>
</c:forEach>
</ul>
</c:if>

<c:if test="${!(empty LittTilknytninger)}">
<h4 class="literatureh">${en ? "Affiliations related to this publication" : "Horisontal dimensjon"}:</h4>
<ul class="plain er">
<c:forEach items="${LittTilknytninger}" var="l">
<li>${fn:escapeXml(l.tilknytningsform.tekstEntall)}</li>
</c:forEach>
</ul>
</c:if>


<c:forEach items="${kategorityper}" var="kattype">

<c:if test="${!(empty kategorisering[kattype.kode])}">
<h4 class="literatureh">${fn:escapeXml(kattype.tekstEntall)}:</h4>
<ul class="plain er">
<c:forEach items="${kategorisering[kattype.kode]}" var="katverdi">
<li>${fn:escapeXml(katverdi)}</li>
</c:forEach>
</ul>
</c:if>

</c:forEach>

</div>


<c:if test="${litteratur.sammendrag != null}">
<h4 class="literatureh">${en ? "Summary" : "Sammendrag"}:</h4>
<p>${fn:replace(fn:escapeXml(litteratur.sammendrag), newLineChar, "<br />")}</p>
</c:if>

<c:if test="${litteratur.tekstfeltUtl != null}">
<h4 class="literatureh">${en ? "Note" : "Virksomheter, vertikal og horisontal dimensjon for utenlandske studier"}:</h4>
<p>${fn:replace(fn:escapeXml(litteratur.tekstfeltUtl), newLineChar, "<br />")}</p>
</c:if>


</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
