<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Hovedsiden til en enhet.
--%>


<%-- ============================================== Topp ================  --%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Forvaltningsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<%-- ====================================================================  --%>


<div id="main" class="wide">


<%-- ============================================ Breadcrumbs ===========  --%>
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
<%-- ====================================================================  --%>


<h1>${fn:escapeXml(enhet.langtNavn)}</h1>


<%-- ======================================== Enhet-linker ==============  --%>
<c:import url="/WEB-INF/jspf/enhet_navigering.jsp">
    <c:param name="valgt" value="enhet" />
</c:import>
<%-- ====================================================================  --%>


<div class="enhetcontent">

<p>${en ? "Unit info." : "Denne siden viser info om denne enheten."}</p>


<c:if test="${enhet.idnum ne -1}">
<%-- ========================================= Opprettelse ==============  --%>
<h3 class="eventh">${fn:escapeXml(endringskoder[enhet.endringskode].tekstEntall)}</h3>

<div class="event">
<span class="eventl">${en ? "Date:" : "Dato:"}</span>
<span class="eventv">
<fmt:formatDate value="${enhet.tidspunkt}" pattern="dd.MM.yyyy" />
<c:if test="${!enhet.bekreftetDato}">(${en ? "date not confirmed" : "tidspunkt ikke bekreftet"})</c:if>
</span>
</div>

<c:if test="${enhet.langtNavnOpprettet != null && !(enhet.langtNavnOpprettet eq enhet.langtNavn)}">
<div class="event">
<span class="eventl">${en ? "Name at founding:" : "Navn ved opprettelse:"}</span>
<span class="eventv">${fn:escapeXml(enhet.langtNavnOpprettet)}</span>
</div>
</c:if>

<c:if test="${!(empty enhet.relasjoner) || !(empty enhet.relasjonerAndre)}">
<div class="event">

<div class="eventl"><c:out value="${endringskoder[enhet.endringskode].relasjontekst}" default="Relasjoner:" escapeXml="true" /></div>

<div class="eventv">
<ul class="data">
<c:forEach items="${enhet.relasjoner}" var="relasjon">
<li><a href="<p:url value="/data/enhet/${relasjon.idnum}" />">${fn:escapeXml(relasjon.langtNavn)}</a></li>
</c:forEach>

<%-- Andre relasjonsenheter --%>
<c:forEach items="${enhet.relasjonerAndre}" var="relasjon">
<li>${en ? fn:escapeXml(relasjon.engelskLangtNavn) : fn:escapeXml(relasjon.langtNavn)}</li>
</c:forEach>
</ul>
</div>

</div>
</c:if>
<%-- ====================================================================  --%>
</c:if>


<%-- ========================================= Ev. nedleggelse ==========  --%>
<c:if test="${enhet.nedlagt}">
<h3 class="eventh">${fn:escapeXml(endringskoder[enhet.endringskodeNedlagt].tekstEntall)}</h3>

<div class="event">
<span class="eventl">${en ? "Date:" : "Dato:"}</span>
<span class="eventv">
<fmt:formatDate value="${enhet.tidspunktNedlagt}" pattern="dd.MM.yyyy" />
<c:if test="${!enhet.bekreftetDatoNedlagt}">(${en ? "date not confirmed" : "tidspunkt ikke bekreftet"})</c:if>
</span>
</div>

<c:if test="${!(empty enhet.relasjonerNedlagt) || !(empty enhet.relasjonerAndreNedlagt)}">
<div class="event">

<div class="eventl"><c:out value="${endringskoder[enhet.endringskodeNedlagt].relasjontekst}" default="Relasjoner:" escapeXml="true" /></div>

<div class="eventv">
<ul class="data">
<c:forEach items="${enhet.relasjonerNedlagt}" var="relasjon">
<li><a href="<p:url value="/data/enhet/${relasjon.idnum}" />">${fn:escapeXml(relasjon.langtNavn)}</a></li>
</c:forEach>

<%-- Andre relasjonsenheter --%>
<c:forEach items="${enhet.relasjonerAndreNedlagt}" var="relasjon">
<li>${en ? fn:escapeXml(relasjon.engelskLangtNavn) : fn:escapeXml(relasjon.langtNavn)}</li>
</c:forEach>
</ul>
</div>

</div>
</c:if>

</c:if>
<%-- ====================================================================  --%>


<%-- ======================================== Siste nøkkelopplysninger ==  --%>
<h3 class="eventh">${en ? "Latest key info" : "Siste nøkkelopplysninger"}</h3>

<c:if test="${enhet.idnum != null}">
<div class="event">
<span class="eventl">${en ? "NSD id number:" : "NSD id-nummer:"}</span>
<span class="eventv">${enhet.idnum}</span>
</div>
</c:if>

<c:if test="${enhet.langtNavn != null}">
<div class="event">
<span class="eventl">${en ? "Name:" : "Navn:"}</span>
<span class="eventv">${fn:escapeXml(enhet.langtNavn)}</span>
</div>
</c:if>

<c:if test="${enhet.kortNavn != null}">
<div class="event">
<span class="eventl">${en ? "Short name:" : "Kort navn:"}</span>
<span class="eventv">${fn:escapeXml(enhet.kortNavn)}</span>
</div>
</c:if>

<c:if test="${no && enhet.tilknytningsform != null && !(enhet.tilknytningsform eq 10) && enhet.engelskLangtNavn != null}">
<div class="event">
<span class="eventl">Engelsk navn:</span>
<span class="eventv">${fn:escapeXml(enhet.engelskLangtNavn)}</span>
</div>
</c:if>

<c:if test="${no && navn != null}">
<div class="event">
<span class="eventl">Andre navn på enheten:</span>
<span class="eventv">${fn:escapeXml(navn)}</span>
</div>
</c:if>

<c:if test="${enhet.tilknytningsform != null}">
<div class="event">
<span class="eventl">${en ? "Affiliations:" : "Tilknytningsform:"}</span>
<span class="eventv">${fn:escapeXml(tilknytninger[enhet.tilknytningsform].tekstEntall)}
    <c:if test="${enhet.tidspunktTilknytningsform != null}">(${en ? "from" : "fra"} <fmt:formatDate value="${enhet.tidspunktTilknytningsform}" pattern="d.M.yyyy" />)</c:if></span>
</div>
</c:if>

<c:if test="${enhet.nivaa != null}">
<div class="event">
<span class="eventl">${en ? "Hierarchical level:" : "Administrativt nivå:"}</span>
<span class="eventv">${fn:escapeXml(nivaaer[enhet.nivaa].tekstEntall)}</span>
</div>
</c:if>

<c:if test="${enhet.cofog != null}">
<div class="event">
<span class="eventl">${en ? "COFOG" : "COFOG:"}</span>
<span class="eventv">${fn:escapeXml(cofog[enhet.cofog])}</span>
</div>
</c:if>


<c:if test="${no}">
<%-- oppgave  --%>
<c:if test="${sisteoppgave != null}">

<%-- hovedoppgave  --%>
<div class="event">
<span class="eventl">${en ? "Last registered main task" : "Siste registrerte hovedoppgave"} (${sisteoppgave.aar}):</span>
<span class="eventv">${fn:escapeXml(oppgavenavn[sisteoppgave.hovedoppgave])}</span>
</div>

<%-- bioppgave  --%>
<c:if test="${sisteoppgave.bioppgave1 != null}">
<div class="event">
<span class="eventl">${en ? "Last registered side task" : "Siste registrerte bioppgave"} (${sisteoppgave.aar}):</span>
<span class="eventv">
${fn:escapeXml(oppgavenavn[sisteoppgave.bioppgave1])}<c:if test="${sisteoppgave.bioppgave2 != null}"> / ${fn:escapeXml(oppgavenavn[sisteoppgave.bioppgave2])}</c:if>
</span>
</div>
</c:if>

</c:if>
</c:if>

<c:if test="${enhet.overordnetDepartement != null && !(enhet.overordnetDepartement eq enhet.overordnetReell)}">
<div class="event">
<span class="eventl">${en ? "Superior ministry:" : "Overordnet departement:"}</span>
<span class="eventv"><a href="<p:url value="/data/enhet/${enhet.overordnetDepartement.idnum}" />">${fn:escapeXml(enhet.overordnetDepartement.langtNavn)}</a></span>
</div>
</c:if>


<c:if test="${enhet.overordnetReell != null}">
<div class="event">
<span class="eventl">${en ? "Superior body:" : "Overordnet:"}</span>
<span class="eventv"><a href="<p:url value="/data/enhet/${enhet.overordnetReell.idnum}" />">${fn:escapeXml(enhet.overordnetReell.langtNavn)}</a></span>
</div>
</c:if>


<c:if test="${enhet.overordnetEnhet.idnum ne enhet.overordnetReell.idnum}">
<div class="event">
<span class="eventl">
<c:if test="${no}">${enhet.grunnenhet eq 11 ? "Tilhører etat:" : "Tilhører gruppe :"}</c:if>
<c:if test="${en}">Belongs to group</c:if>
</span>
<span class="eventv"><a href="<p:url value="/data/enhet/${enhet.overordnetEnhet.idnum}" />">${fn:escapeXml(enhet.overordnetEnhet.langtNavn)}</a></span>
</div>
</c:if>


<c:if test="${enhet.grunnenhet != null}">
<div class="event">
<span class="eventl">${en ? "Main affiliation category:" : "Type enhet:"}</span>
<span class="eventv">${fn:escapeXml(grunnenheter[enhet.grunnenhet].tekstEntall)}</span>
</div>
</c:if>

<c:if test="${enhet.kommune != null || kommune != null}">
<div class="event">
<span class="eventl">${en ? "Located:" : "Lokalisering:"}</span>
<span class="eventv">
<c:if test="${kommune != null}">${kommune.kode} ${fn:escapeXml(kommune.tekstEntall)}</c:if>    
<c:if test="${kommune == null}">${enhet.kommune.kode} ${fn:escapeXml(enhet.kommune.tekstEntall)}</c:if>
</span>
</div>
</c:if>

<c:if test="${!(empty orgnr)}">
<div class="event">
<span class="eventl">${en ? "Organization number:" : "Organisasjonsnummer:"}</span>
<span class="eventv">${orgnr[0]}</span>
</div>
</c:if>

<c:if test="${fn:length(orgnr) > 1}">
<div class="event">
<div class="eventl">${en ? "Previous organization number:" : "Tidligere organisasjonsnummer:"}</div>
<div class="eventv">
<ul class="plain">
<c:forEach begin="1" end="${fn:length(orgnr) - 1}" step="1" var="i">
<li>${orgnr[i]}</li>
</c:forEach>
</ul>
</div>
</div>
</c:if>
    <c:if test="${!(empty orgprinsipp)}">
        <div class="event">
            <span class="eventl">${en ? "Ogranisjonsprinsipp:" : "Ogranisjonsprinsipp:"}</span>
            <c:forEach items="${orgprinsipp}" var="a">
            <%--    <c:if test="${a.orgprinsippId ==1}">
                 Geografi <c:if test="${!(empty a.fraAar) || !(empty a.tilAar)}"> (${a.fraAar} - ${a.tilAar}) </c:if>
                </c:if>
                <c:if test="${a.orgprinsippId ==2}">
                    Funksjon <c:if test="${!(empty a.fraAar) || !(empty a.tilAar)}"> (${a.fraAar} - ${a.tilAar}) </c:if>
                </c:if>
                <c:if test="${a.orgprinsippId ==3}">
                    Funksjonsbasert m/geografi <c:if test="${!(empty a.fraAar) || !(empty a.tilAar)}"> (${a.fraAar} - ${a.tilAar}) </c:if>
                </c:if>--%>
                ${a.norskOrgPrinsipp}
            </c:forEach>
        </div>
    </c:if>
<%-- ====================================================================  --%>


<c:if test="${en}">
<div class="footnote">
<h3>Remarks</h3>
<p><em>
* = If a name is denoted with an asterisk (*), this implies a direct translation from Norwegian to English.
The translation is thus not necessarily the official one (if any exists at all).
</em></p>
</div>
</c:if>

</div>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
