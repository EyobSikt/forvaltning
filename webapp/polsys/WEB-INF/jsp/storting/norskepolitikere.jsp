<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 17.nov.2010
  Time: 13:31:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- --------------------------------------------- inkluderer toppinnhold. --%>
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Storting" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Parliament Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Stortinget hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the Parliament Database at NSD." />
</c:import>

<style type="text/css">

</style>



<div id="main" class="superwide">
<div>
    <c:if test="${no}">
Du er her:
<a href="<p:url value="/"/>">PolSys</a>
> norskepolitikere
    </c:if>
    <c:if test="${en}">
        You are here:
        <a href="<p:url value="/"/>">PolSys</a>
        > <a href="<p:url value="/storting/"/>">Parliament</a>
        > Norwegian politicians
    </c:if>
</div>
    <div>
        <c:if test="${no}"><a href="<m:url value="/en/person/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}&pager.offset=${param.pager.offset}" />">View this page in English.</a></c:if>
        <c:if test="${en}"><a href="<m:url value="/person/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}&pager.offset=${param.pager.offset}" />">View this page in Norwegian.</a></c:if>
    </div>

<%--<p>&nbsp;</p> --%>

<c:if test="${param.p == null || param.p=='sok'}">

    <c:if test="${no}"><p> <h2>Finn norskepolitikere og last ned biografier</h2></p></c:if>
     <c:if test="${en}"><p>
         <h2>Find MP</h2>
    </c:if>

<c:if test="${no}">
<h3>Søketips:</h3>
 <span class="overridep">
Du kan søke i politierearkivet (1814-d.d.)  ved å skrive et ord i søke-boksen eller ved å velge fra høyre menyen. Du kan avgrense søket ved å bruke menyen.
Etter at du får resultat har du mulighet å laste ned ulike biografier av treff i excel eller csv format.  
</span>

      <div  class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
    <div class="dhtmlgoodies_lessmer">
        <div class="overridep">   Følgende variabler er inkludert i de nedlastede Politikere dataene:
        <ul>
            <li>Fornavn</li>
            <li>Etternavn</li>
            <li>Født år</li>
            <li>Doedsår</li>
            <li>Parti</li>
            <li>Statsråd</li>
            <li>Statssekretær</li>
            <li>Stortingsperiode</li>
        </ul>
        </div>
        </c:if>

 <c:if test="${en}">
 <h3>Search tips:</h3>
 <span class="overridep">
You can search the MP archive (1814-d.d.) by typing a word in the search box or by selecting from the right menu. You can refine your search by using the menu.
After you get results you have the option to download biography data in excel or csv format.
</span>

        <div  class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
        <div class="dhtmlgoodies_lessmer">
            <div class="overridep">  The following variables are included in the downloaded MP data:
                <ul>
                    <li>First name</li>
                    <li>Last Name</li>
                    <li>Birth year</li>
                    <li>Death year</li>
                    <li>Party</li>
                    <li>Minster</li>
                    <li>State Secretary</li>
                    <li>Parliamentary Period</li>
                </ul>
            </div>
  </c:if>
        <%--
        <div >
           <h2>Dataset  variable beskrivelse</h2>
            <p>
                Følgende variabler er automatisk inkludert i de nedlastede dataene som standard:
            <ul>
                <li>Fornavn</li>
                <li>Etternavn</li>
            </ul>
            </p>
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
                <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
                    <caption>Utdanning og yrke</caption>
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

            <!-- yrke begnner her ------>
            <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
                <caption>Kommunalpolitisk aktivitet</caption>
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
                </tbody>
            </table>
            <!-- Fylkespolitisk aktivitet begnner her ------>
            <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
                <caption>Fylkespolitisk aktivitet</caption>
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
                </tbody>
            </table>
            <!-- yrke begnner her ------>
            <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
                <caption>Partiverv</caption>
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
                </tbody>
            </table>
           <!-- yrke begnner her ------>
           <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
               <caption>Offentlige verv</caption>
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
               </tbody>
           </table>
           <!-- yrke begnner her ------>
           <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
               <caption>Adminverv</caption>
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
               </tbody>
           </table>
           <!-- yrke begnner her ------>
           <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
               <caption>Orgverv</caption>
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
               </tbody>
           </table>

        </div>
    --%>
    </div>
    <script type="text/javascript">
        initShowHideDivs();
    </script>
    <p></p>

<p>&nbsp;</p>
<%--
    <form  ACTION="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url)} "/>" METHOD="GET">
        <p>
        <div >Søk i etternavn</div>
        <input class="innputt" type="text" name="navn" size="50" maxlength="100" value="">
        <input type="hidden" name="bs" value = "">
        <input type="hidden" name="st" value = "">
        <input type="hidden" name="sr" value = "">
        <input type="hidden" name="ss" value = "">
        <input type="hidden" name="aar" value = "">
        <input type="hidden" name="start" value = "0">
        <input class="subbmitt" type="submit" value="Søk">
        </p>
    </form>
--%>


<c:choose><c:when test="${param.rows !=null}"><c:set var="rows" value="&rows=${param.rows}"></c:set></c:when><c:otherwise><c:set var="rows" value="&rows=50"></c:set></c:otherwise></c:choose>
<c:if test="${param.sortfacet !=null}"> <c:set var="sortfacet" value="&sortfacet=${param.sortfacet}"></c:set> </c:if>
<c:if test="${param.sortresult !=null}"> <c:set var="sortresult" value="&sortresult=${param.sortresult}"></c:set> </c:if>



<form id="sokeboks" class="dokumentTop"  ACTION="<p:url value="/person/norskepolitikere/?navn=${param.navn} "/>" METHOD="GET">

 <span class="clearable">
    <input class="data_field" type="text"  value="${fn:escapeXml(param.navn)}" name="navn" id="querystring" />
    <span title="slett tekst" class="icon_clear" onclick="window.location='?navn=${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url)}'">X</span>
</span>

   <input name="start" type="hidden" value="0">

   <c:choose><c:when test="${param.rows !=null}"><input type="hidden" value="${param.rows}" name="rows"></c:when><c:otherwise><input type="hidden" value="50" name="rows"></c:otherwise></c:choose>
    <c:set var="facetlist" value="${paramValues.bs}"/>
    <c:forEach var="y" begin="0" end="${fn:length(facetlist)}"  >
        <c:if test="${facetlist[y] !=null && facetlist[y]!='' }">
            <input name="bs" type="hidden" value="${fn:escapeXml(facetlist[y])}">
        </c:if>
    </c:forEach>
    <c:set var="periodelist" value="${paramValues.periode}"/>
    <c:forEach var="y" begin="0" end="${fn:length(periodelist)}"  >
        <c:if test="${periodelist[y] !=null && periodelist[y]!='' }">
            <input name="periode" type="hidden" value="${fn:escapeXml(periodelist[y])}">
        </c:if>
    </c:forEach>
    <c:set var="aarlist" value="${paramValues.aar}"/>
    <c:forEach var="y" begin="0" end="${fn:length(aarlist)}"  >
        <c:if test="${aarlist[y] !=null && aarlist[y]!='' }">
            <input name="aar" type="hidden" value="${fn:escapeXml(aarlist[y])}">
        </c:if>
    </c:forEach>
    <input type="hidden" name="st" value = "${param.st}">
    <input type="hidden" name="sr" value = "${param.sr}">
    <input type="hidden" name="ss" value = "${param.ss}">
    <input type="hidden" name="start" value = "0">
    <input  type="submit" value="Søk">
    <c:if test="${no}">  <p class="hjelpetekst">Søk i etternavn</p> </c:if>
    <c:if test="${en}">  <p class="hjelpetekst">Search by last name </p> </c:if>
 </form>

    <p></p>
    <div class="resultat_side_sort" >


        <c:set var="sel1" value=""> </c:set>
        <c:set var="sel2" value=""> </c:set>
        <c:set var="sel2" value=""> </c:set>
        <c:if test="${param.rows ==  50}">
            <c:set var="sel1" value="selected"> </c:set>
        </c:if>
        <c:if test="${param.rows ==  100}">
            <c:set var="sel2" value="selected"> </c:set>

        </c:if>
        <c:if test="${param.rows ==  200}">
            <c:set var="sel3" value="selected"> </c:set>
        </c:if>
        <c:choose>
            <c:when test="${param.sortresult=='' || param.sortresult==null || param.sortresult=='etternavn' }"><c:set var="sel4" value="selected"> </c:set></c:when>
            <c:when test="${param.sortresult=='fornavn' }"><c:set var="sel5" value="selected"> </c:set></c:when>
            <c:when test="${ param.sortresult=='aarstallsynkende' }"><c:set var="sel6" value="selected"> </c:set></c:when>
            <c:when test="${param.sortresult=='aarstallstigende' }"><c:set var="sel7" value="selected"> </c:set></c:when>
        </c:choose>

    <c:if test="${no}"><span >Resultater per side</span></c:if><c:if test="${en}"><span >Results per side</span></c:if>
        <select
                onchange="location = '?navn=' + document.getElementById('querystring').value + '&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${sortfacet}${sortresult}&rows=' + this.options[this.selectedIndex].value;">

            <option name = "sideresultat" value='50'  <c:out value="${sel1}"/> >50</option>
            <option name = "sideresultat" value='100' <c:out value="${sel2}"/> >100</option>
            <option name = "sideresultat" value='200'  <c:out value="${sel3}"/> >200</option>
        </select>

    <c:if test="${no}"><span >Sort resultat i </span></c:if><c:if test="${en}"><span >Sort the results by </span></c:if>
        <select
                onchange="location = '?navn=' + document.getElementById('querystring').value + '${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${sortfacet}&sortresult='  + this.options[this.selectedIndex].value;">
            <option name = "sortresultat" value='etternavn'  <c:out value="${sel4}"/> ><c:if test="${no}">etternavn</c:if><c:if test="${en}">last name</c:if></option>
            <option name = "sortresultat" value='fornavn' <c:out value="${sel5}"/> ><c:if test="${no}">fornavn</c:if><c:if test="${en}"> first name</c:if></option>
            <option name = "sortresultat" value='aarstallsynkende' <c:out value="${sel6}"/> ><c:if test="${no}">født år-synkende</c:if><c:if test="${en}">birth year-descending</c:if> </option>
            <option name = "sortresultat" value='aarstallstigende' <c:out value="${sel7}"/> ><c:if test="${no}">født år-stigende</c:if><c:if test="${en}">birth year-ascending</c:if> </option>
        </select>
    </div>

    <p>&nbsp;</p>


    <%--
    <form action="<p:url value="/storting/newlastned/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}" />"  method="post">
                        <select id="format" name="dataset" style="height: 18px;" onchange="excelcsvikoner(this.value)">
                            <option selected="selected" value="">-- Velg biografi --</option>
                            <option value="1">Stortingsaktivitet</option>
                            <option value="2">Utdanning og yrke</option>
                            <option value="3">Kommunalpolitisk aktivitet</option>
                            <option value="4">Fylkespolitisk aktivitet</option>
                            <option value="5">Partiverv</option>
                            <option value="6">Offentlige verv</option>
                            <option value="7">Adminverv</option>
                            <option value="8">Orgverv</option>
                        </select>
        --%>

    <%--
             <input type="hidden" name="navn" value="<%= request.getParameter("navn") %>">
             <input type="hidden" name="st" value="<%= request.getParameter("st") %>">
             <input type="hidden" name="sr" value="<%= request.getParameter("sr") %>">
             <input type="hidden" name="ss" value="<%= request.getParameter("ss") %>">
             <input type="hidden" name="bs" value="${fn:escapeXml(requestScope.url)}">
             <input type="hidden" name="periode" value="${fn:escapeXml(requestScope.url_periode)}">
             <input type="hidden" name="aar" value="${fn:escapeXml(requestScope.url_aar)}">


             <input type="hidden" name="bs" value="<%= request.getParameter("bs") %>">
             <input type="hidden" name="periode" value="<%= request.getParameter("periode") %>">
             <input type="hidden" name="aar" value="<%= request.getParameter("aar") %>">
             --%>

        <%--
      <input id="submit"  class="disabled" type="submit" value=" " disabled="">
      --%>
        <%--
         laste ned datasett i :

       <button id="excelsubmit" name="filtype" value="EXCEL" title="Lastned dataset i excel format" type="submit" disabled="true" >
       <img  alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/excel.png">
       </button>

        <button id="csvsubmit" name="filtype" value="CSV" title="Lastned dataset i CSV format" type="submit" disabled="true">
       <img alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/csv-icon.gif">
       </button>

       </form>
     --%>




       <div class="hitlist">

       <c:set var="style" value="simple"></c:set>
       <c:set var="position" value="top"></c:set>
           <c:choose><c:when test="${param.rows != null && param.rows !=''}"><c:set var="maxPageItems" value="${param.rows}"></c:set></c:when> <c:otherwise> <c:set var="maxPageItems" value="50"></c:set></c:otherwise></c:choose>
       <c:set var="maxIndexPages" value="10"></c:set>

        <pg:pager items="${fn:length(allenorskepolitikere)}" index="center"
           maxPageItems="${maxPageItems}" maxIndexPages="${maxIndexPages}"
           isOffset="true" export="offset, currentPageNumber=pageNumber"
           scope="request">
           <%-- keep track of preference --%>
	<pg:param name="style" />
	<pg:param name="position" />
	<pg:param name="index" />
	<pg:param name="maxPageItems" />
	<pg:param name="maxIndexPages" />

 <c:set var="endLoop" value="${offset + maxPageItems}"/>
   <c:if test="${fn:length(allenorskepolitikere) < endLoop}">
      <c:set var="endLoop" value="${fn:length(allenorskepolitikere)}"/>
    </c:if>
<table class="zebra text" width="500" cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
<caption>
  <form action="<p:url value="/person/lastnedpolitikeredata/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}" />"  method="post">
    <span style="float: left"><c:if test="${no}">Antall treff :</c:if> <c:if test="${en}"> Number of hits :</c:if>${fn:length(allenorskepolitikere)}</span>
      <span style="float: right"> <c:if test="${no}">  Laste ned datasett i :</c:if><c:if test="${en}"> Download data set in :</c:if>
        <button name="filtype" value="EXCEL" title="Lastned dataset i excel format" type="submit" >
            <img  alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/excel.png">
        </button>
        <button  name="filtype" value="CSV" title="Lastned dataset i CSV format" type="submit" >
            <img alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/csv-icon.gif">
        </button>
      </span>
  </form>

</caption>
<thead>
<tr>
<th width="75" valign="top">
 <c:if test="${no}"><strong>Etternavn</strong></c:if> <c:if test="${en}"><strong>Last name</strong></c:if>
</th>
<th width="75" valign="top">
 <c:if test="${no}"><strong>Fornavn</strong></c:if><c:if test="${en}"><strong>First name</strong></c:if></th>
<th width="65" valign="top">
<c:if test="${no}"><strong>Født år</strong></c:if><c:if test="${en}"><strong> Birth year</strong></c:if>
</th>
</tr>
</thead>
<tbody>

<c:forEach items="${allenorskepolitikere}" var="allenorskepolitikere" varStatus="searchIndex" begin="${offset}" end="${endLoop}">
      <pg:item>
 <tr>
 <th class="label">
<A HREF="<p:url value="/person/politikerbiografi/?person_id=${allenorskepolitikere.personId}&p_info=personalia&navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}"/>">
${allenorskepolitikere.etterNavn}</A>
</th>
<td>
 ${allenorskepolitikere.forNavn}
</td>
<td>
${allenorskepolitikere.faar}
</td>
</tr>
</pg:item>
    </c:forEach>
</tbody>
</table>

 <p></p>
   <center>
	<pg:index>
		<jsp:include page="/WEB-INF/jsp/storting/felerenorskepolitikere.jsp" flush="true" />
	</pg:index>
     </center>
</pg:pager>

</div>

<p></p>

</c:if>

 </div>
</div>

<div id="sidebar">
<c:if test="${no}"> <h2 class="NavigatorboxHeader">Filtrering av søk</h2></c:if>
<c:if test="${en}"> <h2 class="NavigatorboxHeader">Filtering of search</h2></c:if>
<%--  Personindeks  --%>
<div class="fasetter">
<div class="fasettertittel" >
<c:if test="${no}"> Personindeks</c:if>
<c:if test="${en}"> Name index</c:if>
 <span class="markfjerne">
  <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}' />"><c:if test="${no}">fjern alle</a></c:if><c:if test="${en}">remove all</a></c:if>
  <span class="sortspace"></span>  <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.bokstaveurl)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}' />"><c:if test="${no}">merk alle</a></c:if><c:if test="${en}">mark all</a></c:if>
 </span>
</div>
<ul>
<table class="zebra, text">

<c:forEach items="${biografi_bokstave}" var="solr_doktype2" begin="0" end="${fn:length(biografi_bokstave)}" step="5" varStatus="loop" >

<c:if test="${loop.count < 6}">
    <tr>
        <td>
            <li>
                    <c:set var="names" value="${paramValues.bs}"/>
                    <c:set var="doknavn2" value="${biografi_bokstave[loop.index].bokstave}"/>
                    <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                <c:if test="${names[k] ==doknavn2 }">
                    <c:set var="isSelected" value="true" scope="page"/>
                </c:if>
                </c:forEach>
                <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn2}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index].bokstave}
            </li>
        </td>
        <td>
            <li>
                <c:set var="doknavn3" value="${biografi_bokstave[loop.index+1].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn3 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index+1].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn3}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index+1].bokstave}
            </li>
        </td>
        <td>
            <li>
                <c:set var="doknavn4" value="${biografi_bokstave[loop.index+2].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn4 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index+2].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn4}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index+2].bokstave}
            </li>
        </td>
        <td>
            <li>
                <c:set var="doknavn5" value="${biografi_bokstave[loop.index+3].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn5 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index+3].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn5}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index+3].bokstave}
            </li>
        </td>
        <td>
            <li>
                <c:set var="doknavn6" value="${biografi_bokstave[loop.index+4].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn6 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index+4].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn6}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index+4].bokstave}
            </li>
        </td>
    </tr>
</c:if>

<c:if test="${loop.count == 6}">
    <tr>
        <td>
            <li>
                <c:set var="names" value="${paramValues.bs}"/>
                <c:set var="doknavn7" value="${biografi_bokstave[loop.index].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn7 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn7}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index].bokstave}
            </li>
        </td>
        <td>
            <li>
                <c:set var="names" value="${paramValues.bs}"/>
                <c:set var="doknavn8" value="${biografi_bokstave[loop.index+1].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn8 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index+1].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                           <c:set var="names" value="${paramValues.bs}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                       <c:if test="${names[k] !=doknavn8}">
                           <c:set var="fqlist" value="&bs=" />
                           <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                           <c:set var="myVar" value="" />
                       </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index+1].bokstave}
            </li>
        </td>
        <td>
            <li>
                <c:set var="names" value="${paramValues.bs}"/>
                <c:set var="doknavn9" value="${biografi_bokstave[loop.index+2].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn9 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index+2].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn9}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index+2].bokstave}
            </li>
        </td>
        <td>
            <li>
                <c:set var="names" value="${paramValues.bs}"/>
                <c:set var="doknavn10" value="${biografi_bokstave[loop.index+3].bokstave}"/>
                <c:set var="isSelected" value="false" scope="page"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==doknavn10 }">
                        <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>

                <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}&bs=${fn:escapeXml(biografi_bokstave[loop.index+3].bokstave)}${sortfacet}${sortresult}';
                               else if (!this.checked)
                               <c:set var="names" value="${paramValues.bs}"/>
                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                               <c:if test="${names[k] !=doknavn10}">
                               <c:set var="fqlist" value="&bs=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                               </c:if>
                               </c:forEach>
                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                               </c:forEach>
                               "/>
                    ${biografi_bokstave[loop.index+3].bokstave}
            </li>
        </td>
    </tr>
</c:if>

</c:forEach>
</table>
</ul>
</div>

<%--  POLITIKERE I   --%>
<div class="fasetter">
    <div class="fasettertittel" >
<c:if test="${no}">  Politiker i </c:if><c:if test="${en}">  Politician in </c:if>
    <span class="markfjerne">
         <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${fn:escapeXml(requestScope.url_periode)}${sortfacet}${sortresult}' />"><c:if test="${no}">fjern alle</a></c:if><c:if test="${en}">remove all</a></c:if>
        <span class="sortspace"></span>  <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=storting&sr=statsraad&ss=statssekretar${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}' />"><c:if test="${no}">merk alle</a></c:if><c:if test="${en}">mark all</a></c:if>
       </span>
    </div>
    <ul>
        <c:set var="isSelected_st" value="false" scope="page"/>
        <c:if test="${param.st =='storting' }">
            <c:set var="isSelected_st" value="true" scope="page"/>
        </c:if>
        <c:set var="isSelected_sr" value="false" scope="page"/>
        <c:if test="${param.sr =='statsraad' }">
            <c:set var="isSelected_sr" value="true" scope="page"/>
        </c:if>
        <c:set var="isSelected_ss" value="false" scope="page"/>
        <c:if test="${param.ss =='statssekretar' }">
            <c:set var="isSelected_ss" value="true" scope="page"/>
        </c:if>
        <li>
            <input type="checkbox" name="st"  <c:if test="${isSelected_st}">checked=true</c:if>
                   onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=storting&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           else if (!this.checked)
                           window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           "/><c:if test="${no}">stortingsrepresentant</c:if><c:if test="${en}">parliamentary representative</c:if>
        </li>
        <c:set var="isSelected" value="false" scope="page"/>
        <c:if test="${param.st =='storting' }">
            <c:set var="isSelected" value="true" scope="page"/>
        </c:if>
        <li>
            <input type="checkbox" name="sr"  <c:if test="${isSelected_sr}">checked=true</c:if>
                   onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=statsraad&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           else if (!this.checked)
                           window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           "/><c:if test="${no}">statsråd</c:if><c:if test="${en}">minister</c:if>
        </li>
        <c:set var="isSelected" value="false" scope="page"/>
        <c:if test="${param.st =='storting' }">
            <c:set var="isSelected" value="true" scope="page"/>
        </c:if>
        <li>
            <input type="checkbox" name="ss"  <c:if test="${isSelected_ss}">checked=true</c:if>
                   onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=statssekretar${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           else if (!this.checked)
                           window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           "/><c:if test="${no}">statssekretær</c:if><c:if test="${en}">state Secretary</c:if>
        </li>
    </ul>
</div>

<%--  STORTINGSPERIODE  --%>
<div class="fasetter">
    <div class="fasettertittel" >
<c:if test="${no}">Stortingsperiode </c:if><c:if test="${en}">Parliamentary Period </c:if>
       <span class="markfjerne">
         <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}' />"><c:if test="${no}">fjern alle</a></c:if><c:if test="${en}">remove all</a></c:if>
        <span class="sortspace"></span>  <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.periodeurl)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}' />"><c:if test="${no}">merk alle</a></c:if><c:if test="${en}">mark all</a></c:if>
       </span>
    </div>
    <ul>
        <c:set var="names" value="${paramValues.periode}"/>
        <c:set var="periode_1945" value="1945"/>
        <c:set var="isSelected" value="false" scope="page"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
            <c:if test="${names[k] ==periode_1945 }">
                <c:set var="isSelected" value="true" scope="page"/>
            </c:if>
        </c:forEach>
        <li>
            <input type="checkbox" name="periode"  <c:if test="${isSelected}">checked=true</c:if>
                   onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}&periode=1945${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           else if (!this.checked)

                           <c:set var="names" value="${paramValues.periode}"/>
                           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                           <c:if test="${names[k] !=periode_1945}">
                           <c:set var="fqlist" value="&periode=" />
                           <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                           </c:if>
                           </c:forEach>
                           window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                           <c:set var="myVar" value="" />
                           </c:forEach>
                           "/> 1945-d.d.
        </li>
        <c:set var="names" value="${paramValues.periode}"/>
        <c:set var="periode_1903" value="1903"/>
        <c:set var="isSelected" value="false" scope="page"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
            <c:if test="${names[k] ==periode_1903}">
                <c:set var="isSelected" value="true" scope="page"/>
            </c:if>
        </c:forEach>
        <li>
            <input type="checkbox" name="periode"  <c:if test="${isSelected}">checked=true</c:if>
                   onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}&periode=1903${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           else if (!this.checked)

                           <c:set var="names" value="${paramValues.periode}"/>
                           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                           <c:if test="${names[k] !=periode_1903}">
                           <c:set var="fqlist" value="&periode=" />
                           <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                           </c:if>
                           </c:forEach>
                           window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                           <c:set var="myVar" value="" />
                           </c:forEach>
                           "/> 1903-1945
        </li>
        <c:set var="names" value="${paramValues.periode}"/>
        <c:set var="periode_1814" value="1814"/>
        <c:set var="isSelected" value="false" scope="page"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
            <c:if test="${names[k] ==periode_1814 }">
                <c:set var="isSelected" value="true" scope="page"/>
            </c:if>
        </c:forEach>
        <li>
            <input type="checkbox" name="periode"  <c:if test="${isSelected}">checked=true</c:if>
                   onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}&periode=1814${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           else if (!this.checked)

                           <c:set var="names" value="${paramValues.periode}"/>
                           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                           <c:if test="${names[k] !=periode_1814}">
                           <c:set var="fqlist" value="&periode=" />
                           <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                           </c:if>
                           </c:forEach>
                           window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                           <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                           <c:set var="myVar" value="" />
                           </c:forEach>
                           "/> 1814-1903
        </li>

    </ul>
</div>


<%--
<select name="partikode" size=6 multiple>
    <option value="-1" selected>Alle partiene</option>
    <c:forEach items="${parti}" var="partilist" >
        <option value=${partilist.partikode}>${partilist.partinavn} (${partilist.partiKortnavn})</option>
    </c:forEach>
</select>
--%>
<%--  parti --%>

<div class="fasetter">
    <c:set var="aarstallnames" value="${paramValues.fq}"/>
    <div  class="fasettertittel">
<c:if test="${no}">Parti</c:if><c:if test="${en}">Party</c:if>
       <span class="markfjerne">
         <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_aar)}${sortfacet}${sortresult}' />"><c:if test="${no}">fjern alle</a></c:if><c:if test="${en}">remove all</a></c:if>
        <span class="sortspace"></span>  <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.partiurl)}${fn:escapeXml(requestScope.url_aar)}${sortfacet}${sortresult}' />"><c:if test="${no}">merk alle</a></c:if><c:if test="${en}">mark all</a></c:if>
       </span>
    </div>
    <ul>
        <c:forEach items="${parti}" var="partilist" >

                <c:set var="top6" value="${top6+1}"></c:set>
                <c:if test="${top6 < 7}">
                    <li>
                        <c:set var="names" value="${paramValues.parti}"/>
                        <c:set var="aarstallnavn" value="${partilist.partikode}"/>
                        <c:set var="aarstallnavn2" value="${aarstallnavn}"/>
                        <c:set var="isSelected" value="false" scope="page"/>
                        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                            <c:if test="${names[k] ==aarstallnavn2 }">
                                <c:set var="isSelected" value="true" scope="page"/>
                            </c:if>
                        </c:forEach>
                        <input type="checkbox" name="parti"  <c:if test="${isSelected}">checked=true</c:if>
                               onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_parti)}&parti=${partilist.partikode}${fn:escapeXml(requestScope.url_aar)}${sortfacet}${sortresult}';
                                       else if (!this.checked)
                                       <c:set var="names" value="${paramValues.parti}"/>
                                       <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                       <c:if test="${names[k] !=aarstallnavn2}">
                                       <c:set var="fqlist" value="&parti=" />
                                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                       </c:if>
                                       </c:forEach>
                                       window.location = '??navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${sortfacet}${sortresult}';
                                       <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                       <c:set var="myVar" value="" />
                                       </c:forEach>
                                       "/>  ${partilist.partinavn} (${partilist.partiKortnavn})
                    </li>
                </c:if>

        </c:forEach>
    </ul>


    <p class="lesmer"> <a id="hideid2"  href="javascript:onclick=showHide('hideid2');">Vis mer</a></p>
    <div id="hideid2area" class="hidearea">
        <ul>
            <c:forEach items="${parti}" var="partilist" >

                    <c:set var="uten_top6" value="${uten_top6+1}"></c:set>
                    <c:if test="${uten_top6 >= 7}">
                        <li>
                            <c:set var="names" value="${paramValues.parti}"/>
                            <c:set var="aarstallnavn" value="${partilist.partikode}"/>
                            <c:set var="aarstallnavn2" value="${aarstallnavn}"/>
                            <c:set var="isSelected" value="false" scope="page"/>
                            <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                <c:if test="${names[k] ==aarstallnavn2 }">
                                    <c:set var="isSelected" value="true" scope="page"/>
                                </c:if>
                            </c:forEach>
                            <input type="checkbox" name="parti"  <c:if test="${isSelected}">checked=true</c:if>
                                   onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_parti)}&parti=${partilist.partikode}${fn:escapeXml(requestScope.url_aar)}${sortfacet}${sortresult}';
                                           else if (!this.checked)
                                           <c:set var="names" value="${paramValues.parti}"/>
                                           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                           <c:if test="${names[k] !=aarstallnavn2}">
                                           <c:set var="fqlist" value="&parti=" />
                                           <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                           </c:if>
                                           </c:forEach>
                                           window.location = '??navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url_aar)}${sortfacet}${sortresult}';
                                           <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                           <c:set var="myVar" value="" />
                                           </c:forEach>
                                           "/>  ${partilist.partinavn} (${partilist.partiKortnavn})
                        </li>
                    </c:if>

            </c:forEach>
        </ul>

    </div>
</div>



<%--  SE KUN TIDSROM  --%>
<div class="fasetter">
    <div class="fasettertittel" >
<c:if test="${no}">Se kun tidsrom</c:if><c:if test="${en}">See only year</c:if>
    <span class="markfjerne">
  <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}' />"><c:if test="${no}">fjern alle</a></c:if><c:if test="${en}">remove all</a></c:if>
  <span class="sortspace"></span>  <a  href="<p:url value='/person/norskepolitikere/?navn=${param.navn}${rows}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.aarurl)}${fn:escapeXml(requestScope.url_aar)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}' />"><c:if test="${no}">merk alle</a></c:if><c:if test="${en}">mark all</a></c:if>
 </span>
    </div>
    <ul>
        <table class="zebra, text">
            <c:forEach items="${valgaar}" var="aarValg" begin="0" end="${fn:length(valgaar)}" step="3" varStatus="loop" >
                <c:if test="${loop.count < 21}">
                    <tr>
                        <td>
                            <li>
                                    <c:set var="names" value="${paramValues.aar}"/>
                                    <c:set var="aar1" value="${valgaar[loop.index].periodeAar}"/>
                                    <c:set var="isSelected" value="false" scope="page"/>
                                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                <c:if test="${names[k] ==aar1 }">
                                    <c:set var="isSelected" value="true" scope="page"/>
                                </c:if>
                                </c:forEach>
                                <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index].periodeAar)}${sortfacet}${sortresult}';
                                               else if (!this.checked)
                                               <c:set var="names" value="${paramValues.aar}"/>

                                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                               <c:if test="${names[k] !=aar1}">
                                               <c:set var="fqlist" value="&aar=" />
                                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                               </c:if>
                                               </c:forEach>
                                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                               <c:set var="myVar" value="" />
                                               </c:forEach>
                                               "/>
                                    ${valgaar[loop.index].valgAar} </td>
                        </li>

                        <td>
                            <li>
                                    <c:set var="aar2" value="${valgaar[loop.index+1].periodeAar}"/>
                                    <c:set var="isSelected" value="false" scope="page"/>
                                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                <c:if test="${names[k] ==aar2 }">
                                    <c:set var="isSelected" value="true" scope="page"/>
                                </c:if>
                                </c:forEach>

                                <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index+1].periodeAar)}${sortfacet}${sortresult}';
                                               else if (!this.checked)
                                               <c:set var="names" value="${paramValues.aar}"/>
                                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                               <c:if test="${names[k] !=aar2}">
                                               <c:set var="fqlist" value="&aar=" />
                                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                               </c:if>
                                               </c:forEach>
                                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                               <c:set var="myVar" value="" />
                                               </c:forEach>
                                               "/>
                                    ${valgaar[loop.index+1].valgAar} </td>
                        </li>
                        </td>
                        <td>
                            <li>
                                    <c:set var="aar3" value="${valgaar[loop.index+2].periodeAar}"/>
                                    <c:set var="isSelected" value="false" scope="page"/>
                                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                <c:if test="${names[k] ==aar3 }">
                                    <c:set var="isSelected" value="true" scope="page"/>
                                </c:if>
                                </c:forEach>

                                <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index+2].periodeAar)}${sortfacet}${sortresult}';
                                               else if (!this.checked)
                                               <c:set var="names" value="${paramValues.aar}"/>
                                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                               <c:if test="${names[k] !=aar3}">
                                               <c:set var="fqlist" value="&aar=" />
                                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                               </c:if>
                                               </c:forEach>
                                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                               <c:set var="myVar" value="" />
                                               </c:forEach>
                                               "/>
                                    ${valgaar[loop.index+2].valgAar} </td>
                        </li>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${loop.count == 21}">
                    <tr>
                        <td>
                            <li>
                                    <c:set var="names" value="${paramValues.aar}"/>
                                    <c:set var="aar4" value="${valgaar[loop.index].periodeAar}"/>
                                    <c:set var="isSelected" value="false" scope="page"/>
                                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                <c:if test="${names[k] ==aar4 && names[k]!='' }">
                                    <c:set var="isSelected" value="true" scope="page"/>
                                </c:if>
                                </c:forEach>
                                <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                                       onclick="if (this.checked) window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index].periodeAar)}${sortfacet}${sortresult}';
                                               else if (!this.checked)
                                               <c:set var="names" value="${paramValues.aar}"/>
                                               <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                               <c:if test="${names[k] !=aar4}">
                                               <c:set var="fqlist" value="&aar=" />
                                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                               </c:if>
                                               </c:forEach>
                                               window.location = '?navn=${param.navn}${rows}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_parti)}${sortfacet}${sortresult}';
                                               <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                               <c:set var="myVar" value="" />
                                               </c:forEach>
                                               "/>
                                    ${valgaar[loop.index].valgAar} </td>
                        </li>
                        </td>
                    </tr>
                </c:if>
           </c:forEach>
        </table>
    </ul>
</div>

</div>




<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp"/>
<%-- --------------------------------------------------------------------- --%>