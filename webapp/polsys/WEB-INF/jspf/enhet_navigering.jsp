<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: JSP som inneholder enhet-navigasjon.
--%>

<c:if test="${no}">
<p class="disclaimer">
Denne siden og de ulike fanene under viser informasjon om denne enheten og data 
tilknyttet denne enheten fra Forvaltningsdatabasen.
Forvaltningsdatabasen er en grundig og detaljert kartlegging av organisering og 
endring av den norske statsforvaltningen fra 1947 - d.d.
Fanen "Lenker" inneholder eventuelle lenker til eksterne ressurser. 
</p>
</c:if>

<ul class="enhetnav">
<li${param.valgt eq "enhet" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}" />">${en ? "Unit" : "Enhet"}</a></li>
<li${param.valgt eq "endringshistorie" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/endringshistorie" />">${en ? "Event history" : "Endringshistorie"}</a></li>
<li${param.valgt eq "hierarki" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/hierarki" />">${en ? "Management Hierarchy" : "Forvaltningshierarki"}</a></li>

<li${param.valgt eq "enhetsregisterethierarki" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/enhetsregisterethierarki" />">${en ? "CCR Hierarchy" : "Enhetsregisteret hierarki"}</a></li>
<li${param.valgt eq "relasjon" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/relasjon" />">${en ? "Relations" : "Relasjoner"}</a></li>

<c:if test="${visTallgruppeLink}"><li${param.valgt eq "desentralisert" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/desentralisert" />">${en ? "Decentralized" : "Desentralisert"}</a></li></c:if>
<c:if test="${visSatellittLink}"><li${param.valgt eq "avdeling" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/avdeling" />">${en ? "Local units" : "Avdelinger"}</a></li></c:if>

<li${param.valgt eq "litteratur" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/litteratur" />">${en ? "Literature" : "Litteratur"}</a></li>
<li${param.valgt eq "aarsmelding" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/aarsmelding" />">${en ? "Annual reports" : "Årsmeldinger"}</a></li>
<li${param.valgt eq "tildelingsbrev" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/tildelingsbrev" />">${en ? "Allocation reports" : "Tildelingsbrev"}</a></li>
<li${param.valgt eq "lovdata" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/lovdata" />">${en ? "Laws" : "Lovdata"}</a></li>

<c:if test="${visUtvalgLink}">
    <li${param.valgt eq "utvalg" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/utvalg" />">${en ? "Committees" : "Utvalg"}</a></li>
</c:if>
<c:if test="${visAnsatteLink}">
    <li${param.valgt eq "ansatte" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/ansatte" />">${en ? "Employees" : "Ansatte"}</a></li>
</c:if>
<c:if test="${visOppgaveLink}">
    <li${param.valgt eq "oppgave" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/oppgave" />">${en ? "Tasks" : "Oppgaver"}</a></li>
</c:if>
<c:if test="${visStatresLink}">
    <li${param.valgt eq "statres" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/statres" />">${en ? "StatRes" : "StatRes"}</a></li>
</c:if>
    
<li${param.valgt eq "lenker" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/lenker" />">${en ? "Links" : "Lenker"}</a></li>
<li${param.valgt eq "instruks" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/instruks" />">${en ? "Annual reports" : "Instrukser"}</a></li>
<li${param.valgt eq "organisjonsprinsipp" ? ' class="valgt"' : ''}><a href="<p:url value="/data/enhet/${enhet.idnum}/organisjonsprinsipp" />">${en ? "Organizational principle" : "Organisjonsprinsipp"}</a></li>



</ul>
