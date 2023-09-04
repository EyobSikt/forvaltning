<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 03.nov.2010
  Time: 08:18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- --------------------------------------------- inkluderer toppinnhold. --%>

<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>



<style type="text/css">
.updated {
    margin-left: 490px;
    padding-bottom: 6px;
    width: 100%;
}

.openorig {
    background-color: #EEEEEE;
    border: 1px solid #BBBBBB;
    color: #888888;
    font-size: 7pt;
    margin-top: 5px;
    padding: 2px;
    text-align: center;
    text-decoration: none;
    text-transform: uppercase;
    width: 110px;
}    

</style>



<div id="sidebar">


</div>

<div id="main" class="wide">


<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="<p:url value="/"/>">PolSys</a>
> <a href="<p:url value="/storting/"/>">Storting</a>
> Varible beskrivelse
</c:if>
</div>



<div class="sokhjelp">
 <ul class="enhetnav">
<c:if test="${param.p=='sok'}"><c:set var="s_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p=='hjelp'}"><c:set var="h_class" value="valgt"></c:set> </c:if>
<li  class="${s_class}"><a href="<p:url value="/storting/norskepolitikere/?p=sok" />">${en ? "parliament " : "Søk og last ned"}</a></li>
<li  class="${h_class}"><a href="<p:url value="/storting/omdataset/?p=hjelp" />">${en ? "education and work" : "Variable beskrivelse"}</a></li>
</ul>
</div>

<p>&nbsp;</p>    
<h2>Dataset  variable beskrivelse</h2>
 <p>
Følgende variabler er automatisk inkludert i de nedlastede dataene som standard:
<ul>
<li>Fornavn</li>
<li>Etternavn</li>
</ul>
 </p>

 <c:if test="${param.p=='hjelp'}">

 <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
<caption>Stortingsaktivitet</caption>
<thead>
<tr>
<th  valign="top">
<strong>Varible</strong>

<th  valign="top">
<strong>Beskrivelse</strong>
</th>
</tr>
</thead>
<tbody>
 <tr>
 <th class="label">
Stortingsperiode
</th>
<td>
<p>Denne variabelen viser hvilken periode personen var valgt for.</p>    
</td>
</tr>
 <tr>
 <th class="label">
Fødselsdato
</th>
<td>
</td>
</tr>
 <tr>
 <th class="label">
Dødsdato
</th>
<td>
</td>
</tr>
 <tr>
 <th class="label">
Stortingsperiode
</th>
<td>
</td>
</tr>
 <tr>
 <th class="label">
Representantnummer (Repnr)
</th>
<td>
<p>Representanene som blir valg inn på Stortinget representerer en valgkrets. Denne variabelen viser rekkefølgen innenfor valgkretsen representanten ble valgt inn for. Representant nummer 1 i dette feltet har altså flest valgstemmer bak seg osv. </p>
</td>
</tr>
 <tr>
 <th class="label">
Suppleantnummer (Supnr)
</th>
<td>
<p>Representanene som blir valg inn på Stortinget representerer en valgkrets. Denne variabelen viser rekkefølgen innenfor valgkretsen vararepresentanten ble valgt inn for. Vararepresentant nummer 1 i dette feltet er altså den vara fra valgkretsen som har flest valgstemmer bak seg osv. </p>
</td>
</tr>
 
 <tr>
 <th class="label">
Valgkretskode
</th>
<td>
<p>Dette er et nytt kodesett som viser hvilken valgkrets personen tilhørte.</p>    
</td>
</tr>
 <tr>
 <th class="label">
Partitilhørighet (Parti)
</th>
<td>
<p>Partitilknytning til politikeren. Ved partiskifte innen stortingsperioden, er det som oftes partitilknytning ved valget som er registrert.</p>
</td>
</tr>
 <tr>
 <th class="label">
Stilling
</th>
<td>
 <p>Denne tekstvariabelen viser hvilken stilling politikeren hadde ved tidspunktet for valget og er i hovedsak hentet fra Statistisk sentralbyrå publikasjoner fra Stortingsvalgene.</p>
</td>
</tr>
 </tbody>
</table>


<!-- yrke begnner her ------>

<h2>Dataset - Utdanning og yrke </h2>
     <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
    <caption>Stortingsaktivitet</caption>
    <thead>
    <tr>
    <th  valign="top">
    <strong>Varible</strong>

    <th  valign="top">
    <strong>Beskrivelse</strong>
    </th>
    </tr>
    </thead>
    <tbody>
     <tr>
     <th class="label">
    Fra
    </th>
    <td>
    <p>Året aktiviteten startet</p>
    </td>
    </tr>
     <tr>
     <th class="label">
    Til
    </th>
    <td>
    <p>Året aktiviteten opphørte</p>    
    </td>
    </tr>
     <tr>
     <th class="label">
    Aktivitet
    </th>
    <td>
        <p>Feltet inneholder opplysninger om representantenes utdanning, yrke medaljer/utmerkelser m.m. </p>
    </td>
    </tr>
    
     </tbody>
    </table>







<h2>Dataset - Kommunalpolitisk aktivitet </h2>

<h2>Dataset - Fylkespolitisk aktivitet  </h2>

<h2>Dataset - Partiverv </h2>

<h2>Dataset - Offentlige verv </h2>

<h2>Dataset - Adminverv </h2>

<h2>Dataset - Orgverv  </h2>
 </c:if>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>