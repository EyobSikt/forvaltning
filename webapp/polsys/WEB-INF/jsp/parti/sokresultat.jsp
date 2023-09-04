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

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>

<%-- --------------------------------------------- inkluderer toppinnhold. --%>

<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="navigation" value="/polsys/parti/" />
    <c:param name="tittelNo" value="Søk i norske partidokumenter - Parti" />
     <c:param name="beskrivelseNo" value="Søk i norske partiprogrammer." />
</c:import>


<div id="main" >
<c:choose><c:when test="${param.rows !=null}"><c:set var="rows" value="&rows=${param.rows}"></c:set></c:when><c:otherwise><c:set var="rows" value="&rows=10"></c:set></c:otherwise></c:choose>
<c:if test="${param.sortfacet !=null}"> <c:set var="sortfacet" value="&sortfacet=${param.sortfacet}"></c:set> </c:if>
<c:if test="${param.sortresult !=null}"> <c:set var="sortresult" value="&sortresult=${param.sortresult}"></c:set> </c:if>

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
    <a href="https://nsd.no/polsys/">PolSys</a>
    > <a href="https://nsd.no/polsys/parti/">Parti</a>
    > <c:choose><c:when test="${param.q!=null}">> <a href="<p:url value="/parti/partidokumentarkivet/"/>">Partidokumentarkivet</a> > Sokresultat</c:when><c:otherwise>Partidokumentarkivet</c:otherwise></c:choose>
</c:if>
</div>

<h2>Partidokumentarkivet  </h2>

<span class="overridep">Partidokumentarkivet er NSDs samling av norske partidokumenter fra 1884 og fram til i dag. Her finner du partidokumenter av ulikt slag fra de fleste politiske partier som har eksistert i Norge opp gjennom tiden.</span>

<div  class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
<div class="dhtmlgoodies_lessmer">
<div >
    <h4>Om de ulike dokumentene</h4>
    <p>
Arkivet inneholder valgprogram for partier som har vært representert på Stortinget, samt i varierende grad også for mindre partier som har stilt liste til et stortingsvalg. Det inneholder også en hel del lokale valgprogram fra fylkes- og kommunevalg , samt enkelte valgprogram fra sametingsvalg. I tillegg finner man i arkivet prinsipprogram for en rekke partier som har stilt til stortingsvalg.    </p>
   <p>
       I arkivet er det tilrettelagt en mengde pressemeldinger som har blitt sendt ut fra sentralt hold i ulike partier. De eldste pressemeldingene er fra Arbeiderpartiet i 1990, de nyeste er fra 2006. Dekningsgraden for pressemeldinger for de ulike partiene varierer noe, avhengig av hvor aktive partiene selv har vært med hensyn til å inkludere NSD som mottaker av pressemeldingene og av i hvor stor grad partiene benytter seg av pressemeldinger som kommunikasjonskanal.   </p>
 <p>
Ellers finner man flere andre typer dokumenter i arkivet, som vedtekter, taler og stiftelseserklæringer. Her ligger for eksempel Sosialistisk Folkepartis stiftelseserklæring av april 1960 og Kråkerøytalen til Einar Gerhardsen fra februar 1948. </p>
 <h4>Tilrettelegging av dokumentene</h4>
 <p>
Tilretteleggingen av partidokumentene er innholdsorientert. De skal være enkle å finne fram i, samtidig som de formidler all relevant informasjon til brukerne. Dette innebærer at dokumentene ikke alltid fremstår nøyaktig slik de er levert fra partiene, men er tilpasset rask tilgang til teksten. De aller fleste dokumentene framstår likevel som tilnærmet identiske med originaldokumentene. Valgprogram fra sametingsvalg er tilrettelagt på både samisk og norsk språk. </p>
<p>
Dokumentene foreligger i ulike formater, avhengig formatet de opprinnelig ble lagret i da de ble tilrettelagt elektronisk for første gang. For å være mest mulig tro mot det opprinnelige materialet, varierer derfor formateringen av dokumentene i arkivet mellom PDF, html og rtf. Det framgår av hvert enkelt dokument hvilken formatering som er benyttet.    
</p>
 <p>
Pressemeldinger fra partier håndteres vanligvis slik at dato og reell avsender, som ikke alltid er oppgitt i selve pressemeldingen, legges til i dokumentet (særlig gjelder dette elektroniske pressemeldinger mottatt per e-post). Samtidig fjerner vi privat kontaktinformasjon som telefonnummer/e-postadresse, som ofte oppgis avslutningsvis som en del av pressemeldingene. </p>
 <h4>Kilder</h4>
<p>
Kildene for eldre materiell er først og fremst trykte utgaver av dokumentene. Blant annet er samlepublikasjoner med ulike valgprogram brukt som kilde. Fra begynnelsen av 1990-tallet fikk NSD oversendt partiprogrammer elektronisk og siden midten/slutten av 1990-tallet er partidokumentene stort sett lastet direkte ned fra Internett. Unntaket er pressemeldinger, som i hovedsak mottas fra partiene per e-post.
</p>
</div>
</div>

<p></p>

<strong>Søketips:</strong>
<span class="overridep">Bruk søkefeltet til å søke på ord eller tema du er interessert i. Avgrens søket ved å bruke filtrering i menyen til høyre.</span> 


<div class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
<div class="dhtmlgoodies_lessmer">
<div >
    <h4>Hvordan søker jeg?</h4>
    <ul>
        <li>
            Skriv ett eller flere søkeord i søkefeltet. Trykk på søkeknappen.
        </li>
        <li>
            Bruk mellomrom mellom ordene hvis du skriver flere søkeord i søkefeltet. Skriver du flere ord, får du treff som inneholder <em>ett eller flere av</em> ordene.
            <div><strong>Eksempel:</strong></div>
            Dersom du søker på <em>bærekraftig utvikling</em> vil søket inkludere alle dokumenter som inneholder ordene <strong>bærekraftig</strong>, <strong>utvikling</strong> eller <strong>bærekraftig utvikling</strong>.
        </li>
        <li>
            Bruk anførselstegn " " for å søke på kombinasjon av ord. Da vil du kun finne dokumenter som inneholder den eksakte kombinasjonen av ord.
            <div><strong>Eksempel:</strong></div>
            Dersom du søker på <em>"bærekraftig utvikling"</em> vil søket inkludere alle dokumenter som inneholder uttrykket <strong>bærekraftig utvikling</strong>.
        </li>
        <li>
            Bruk stjerner * * for å søke på sammensatte ord der den ene delen av ordet er et bestemt ord eller begrep du er interessert i.
            <div><strong>Eksempel:</strong></div>
            Dersom du søker på ordet <em>*helse*</em> vil søket gi treff på både kvinne<strong>helse</strong> og <strong>helse</strong>personell.
        </li>
    </ul>
    <h4>Hvordan avgrenser jeg søket?</h4>
    <ul>
        <li>
            Du kan bruke filtreringen i høyre kolonne til å avgrense søket ditt. Bruk avkrysningsboksene i hvert felt til å velge bestemte partier, dokumenttyper og årstall. Det er mulig å velge flere partier, dokumenttyper og årstall samtidig. Dersom ingen filtrering er valgt, søker man i alle dokumenter i hele arkivet.
        </li>
    </ul>
    <h4>Eksempler på hva arkivet kan gi av informasjon</h4>
    <ul>
        <li>
            <a href='<p:url value='/parti/partidokumentarkivet/?q=naturvern${rows}&fq=doktype:2&sortresult=aarstallstigende${sortfacet}' />'>
                Ordet <i>naturvern</i> ble for første gang brukt i Venstres valgprogram i 1957.
            </a>
        </li>
        <li>
            <a href='<p:url value='/parti/partidokumentarkivet/?q="bærekraftig utvikling"${rows}&fq=&fq=doktype:2' />'>
                Blant partiene på Stortinget er det bare Fremskrittspartiet som i perioden ikke har brukt begrepet <i>bærekraftig utvikling</i> i sine valgprogrammer til stortingsvalg.
            </a>
        </li>
        <li>
            <a href='<p:url value='/parti/partidokumentarkivet/?q=velferdsstat${rows}&sortresult=aarstallstigende${sortfacet}' />'>
                Det var Sosialistisk Folkeparti som først brukte ordet <i>velferdsstat</i>. Ordet ble brukt i partiets prinsipprogram fra 1962.
            </a>
        </li>
        <li>
            <a href='<p:url value='/parti/partidokumentarkivet/?q=globalisering${rows}&fq=&fq=doktype:2&sortresult=aarstallsynkende${sortfacet}' />'>
                Ordet <i>globalisering</i> ble ikke brukt i noe valgprogram før 1999.
            </a>
        </li>
    </ul>
</div>
</div>

<script type="text/javascript">
initShowHideDivs();
</script>

<p></p>


<form id="sokeboks" class="dokumentTop" accept-charset="UTF-8" action="<p:url value="/parti/partidokumentarkivet/" />" method="GET">
<%--
 <span class="deleteicon">
    <input   type="text"  value="${fn:escapeXml(param.q)}" name="q" id="querystring">
 <span title="slett tekst" onclick="window.location='?q=${rows}${fn:escapeXml(requestScope.url)}${sortresult}${sortfacet}'"></span>
   </span>
--%>

 <span class="clearable">
    <input class="data_field" type="text"  value="${fn:escapeXml(param.q)}" name="q" id="querystring" />
    <span title="slett tekst" class="icon_clear" onclick="window.location='?q=${rows}${fn:escapeXml(requestScope.url)}${sortresult}${sortfacet}'">X</span>
</span>  


 <input name="start" type="hidden" value="0">
<c:choose><c:when test="${param.rows !=null}"><input type="hidden" value="${param.rows}" name="rows"></c:when><c:otherwise><input type="hidden" value="10" name="rows"></c:otherwise></c:choose>
<c:set var="facetlist" value="${paramValues.fq}"/>
                <c:forEach var="y" begin="0" end="${fn:length(facetlist)}"  >
                     <c:if test="${facetlist[y] !=null && facetlist[y]!='' }">
         <input name="fq" type="hidden" value="${fn:escapeXml(facetlist[y])}">
         </c:if>
      </c:forEach>
    <c:if test="${param.sortresult !=null}"><input type="hidden" value="${param.sortresult}" name="sortresult"></c:if>
<c:if test="${param.sortfacet !=null}"><input type="hidden" value="${param.sortfacet}" name="sortfacet"></c:if>
    <input  type="submit" value="Søk">
<p class="hjelpetekst">Søk f.eks. på kontantstøtte, økonomi, velferdsstat, globalisering osv.</p>
</form>

<p></p>

<div class="resultat_side_sort" >


<c:set var="sel1" value=""> </c:set>
     <c:set var="sel2" value=""> </c:set>
     <c:set var="sel2" value=""> </c:set>
<c:if test="${param.rows ==  10}">
    <c:set var="sel1" value="selected"> </c:set>
</c:if>
    <c:if test="${param.rows ==  50}">
   <c:set var="sel2" value="selected"> </c:set>

    </c:if>
    <c:if test="${param.rows ==  100}">
    <c:set var="sel3" value="selected"> </c:set>
  </c:if>
<c:choose>
 <c:when test="${param.sortresult=='partinavn' }"><c:set var="sel4" value="selected"> </c:set></c:when>
  <c:when test="${param.sortresult=='doktypenavn' }"><c:set var="sel5" value="selected"> </c:set></c:when>
  <c:when test="${param.sortresult=='' || param.sortresult==null || param.sortresult=='aarstallsynkende' }"><c:set var="sel6" value="selected"> </c:set></c:when>
   <c:when test="${param.sortresult=='aarstallstigende' }"><c:set var="sel7" value="selected"> </c:set></c:when>
</c:choose>

<span >Resultater per side</span>
<select
       onchange="location = '?q=' + document.getElementById('querystring').value + '${fn:escapeXml(requestScope.url)}${sortresult}${sortfacet}&rows=' + this.options[this.selectedIndex].value;">

<option name = "sideresultat" value='10'  <c:out value="${sel1}"/> >10</option>
<option name = "sideresultat" value='50' <c:out value="${sel2}"/> >50</option>
<option name = "sideresultat" value='100'  <c:out value="${sel3}"/> >100</option>
</select>
      
<span >Sort resultat</span>
    <select
       onchange="location = '?q=' + document.getElementById('querystring').value + '${rows}&fq=${fn:escapeXml(requestScope.url)}${sortfacet}&sortresult='  + this.options[this.selectedIndex].value;">
    <option name = "sortresultat" value='partinavn'  <c:out value="${sel4}"/> >parti</option>
    <option name = "sortresultat" value='doktypenavn' <c:out value="${sel5}"/> >dokumenttype</option>
    <option name = "sortresultat" value='aarstallsynkende' <c:out value="${sel6}"/> >år-synkende </option>
    <option name = "sortresultat" value='aarstallstigende' <c:out value="${sel7}"/> >år-stigende  </option>
</select>
</div>                            
                              
<div>

  <c:set var="style" value="simple"></c:set>
  <c:set var="position" value="top"></c:set>
 <c:choose><c:when test="${param.rows != null && param.rows !=''}"><c:set var="maxPageItems" value="${param.rows}"></c:set></c:when> <c:otherwise> <c:set var="maxPageItems" value="10"></c:set></c:otherwise></c:choose>
 <c:set var="maxIndexPages" value="5"></c:set>

<pg:pager items="${requestScope.antalltreff}" index="center"
	maxPageItems="${maxPageItems}" maxIndexPages="${maxIndexPages}"
	isOffset="true" export="offset, currentPageNumber=pageNumber"
	scope="request">
	<pg:param name="style" />
	<pg:param name="position" />
	<pg:param name="index" />
	<pg:param name="maxPageItems" />
	<pg:param name="maxIndexPages" />

   <c:set var="endLoop" value="${offset + maxPageItems}"/>
   <c:if test="${fn:length(solr) < endLoop}">
      <c:set var="endLoop" value="${fn:length(solr)}"/>
    </c:if>

<ul class="sokeList">
<p class="result">Antall treff: ${fn:escapeXml(requestScope.antalltreff)}</p>
   
 <c:forEach items="${solr}" var="solr2" >
        <li>
   <c:choose><c:when test="${fn:substringAfter(solr2.filenameformat, '.')=='pdf'}"><c:set var="fileformat" value=".pdf"></c:set><c:set var="filtittel" value="PDF format"></c:set></c:when><c:when test="${fn:substringAfter(solr2.filenameformat, '.')=='rtf'}"><c:set var="fileformat" value=".rtf"></c:set><c:set var="filtittel" value="RTF format"></c:set></c:when><c:otherwise><c:set var="fileformat" value=".html"></c:set><c:set var="filtittel" value="HTML format"></c:set></c:otherwise></c:choose>
            <a  title="${filtittel}" href="https://nsd.no/polsys/data/filer/parti/${solr2.filename}${fileformat}" <c:if test="${fn:substringAfter(solr2.filenameformat, '.')!='rtf'}"> onClick="return popup(this, 'notes')" </c:if>>
            <div class="search_content">
 <c:choose><c:when test="${fn:substringAfter(solr2.filenameformat, '.')=='pdf'}"><img src="https://nsd.no/common/polsys/img/pdf.gif" ></c:when><c:when test="${fn:substringAfter(solr2.filenameformat, '.')=='rtf'}"><img src="https://nsd.no/common/polsys/img/icon-rtf.gif" ></c:when><c:otherwise></c:otherwise></c:choose>
<strong>${solr2.tittel}</strong>
<span class="sok_fil_info">
[    Parti:${solr2.partinavn},
    Doktype:${solr2.doktypenavn},
    År:${solr2.aarstall}]
       </span>
<span class="soke_tekst">
${solr2.tekst}...
</span>
</div>
</a>
<c:if test="${solr2.samiskdokref != null}">
<div class="samisk_versjon">
<a  title="word format" href="https://nsd.no/polsys/data/filer/parti/sa/${solr2.samiskdokref}" > Samisk versjon <img src="https://nsd.no/common/polsys/img/word.png" > </a>
</div>
</c:if>
</li>
</c:forEach>
</ul>

<p></p>

 <c:choose>
    <c:when test="${fn:length(solr) > 0}">
        <center>
	        <jsp:include page="/WEB-INF/jsp/parti/alltheweb.jsp" flush="true" />
        </center>
    </c:when>
  <c:otherwise>

          <div class="nullresultattekst">Søket gav ingen resultat, prøv på nytt med andre søkeord og/eller ved å velge fra menyen til høyre.  </div>
        
   </c:otherwise>
   </c:choose>    

</pg:pager>

</div>

  <%---------------------- main slutter ---------------------------%>
</div>

<div id="sidebar">

<h3>Filtrering av søk</h3>

<div class="facet_top_menu">
Vis kategorier med null treff
    <input id="divcheck" type="checkbox" name="show_zeros" value="zeros" class="visnullresulatat_input"
             onclick='javascript:fncheckedzerofacet();' >
</div>
<div class="facet_top_menu" st>
Sortering:
&nbsp;<c:choose><c:when test="${ param.sortfacet== null || param.sortfacet== ''  || param.sortfacet=='index'}"><strong>Alfabetisk</strong></c:when><c:otherwise><span id="myButton" onclick="window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}${sortresult}&sortfacet=index'"><a title="sortere menyen alphabetisk" href="#"> Alfabetisk</a></span></c:otherwise></c:choose>
&nbsp;<c:choose><c:when test="${ param.sortfacet=='count'}"><strong>Antall</strong></c:when><c:otherwise><span id="myButton" onclick="window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}${sortresult}&sortfacet=count'"><a title="sortere menyen etter antall treff" href="#">Antall</a></span></c:otherwise></c:choose>
</div>

<div class="facet_top_menu">
<c:if test="${fn:escapeXml(requestScope.minst_en_parti_mark || requestScope.minst_en_doktype_mark || requestScope.minst_en_aarstall_mark)}">
Avgrensning på:
<span class="avgrensiningvalgt"><c:if test="${fn:escapeXml(requestScope.minst_en_parti_mark)}"><i>- parti</i></c:if></span><span class="avgrensiningvalgt"><c:if test="${fn:escapeXml(requestScope.minst_en_doktype_mark)}"><i>- dokumenttype</i></c:if></span><span class="avgrensiningvalgt"><c:if test="${fn:escapeXml(requestScope.minst_en_aarstall_mark)}"><i>- årstall</i></c:if></span>
</span>
</c:if>
</div>
<div class="facet_top_menu">
<c:if test="${fn:escapeXml(requestScope.minst_en_parti_mark || requestScope.minst_en_doktype_mark || requestScope.minst_en_aarstall_mark)}">
<a  href="<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}${rows}${sortresult}${sortfacet}' />">Fjern alle avgrensninger</a>
</c:if>
</div>

<div class="fasetter">
      <c:set var="partinames" value="${paramValues.fq}"/>
           <div class="fasettertittel" >Parti
               <span class="markfjerne">
               <a  href="<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}${rows}${fn:escapeXml(requestScope.markpartiurl)}${sortresult}${sortfacet}' />">fjern alle</a>
              <span class="sortspace"></span> <a  href="<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}${rows}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.partiurl)}${sortresult}${sortfacet}' />">merk alle</a>
             </span>
           </div>
       <ul>
       <c:forEach items="${solr_partinavn}" var="solr_partinavn2" >
          <c:if test="${solr_partinavn2.dokumenttall >0 }">
              <c:set var="parti_top7" value="${parti_top7+1}"></c:set>
              <c:set var="names" value="${paramValues.fq}"/>
             <c:set var="partinavn2" value="partikode:${solr_partinavn2.partikode}"/>
             <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
             <c:if test="${names[k] ==partinavn2 }">
             <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>
        <c:choose>
         <c:when test="${solr_partinavn2.partikode==71 || solr_partinavn2.partikode==21 || solr_partinavn2.partikode==81 || solr_partinavn2.partikode==51 || solr_partinavn2.partikode==41 || solr_partinavn2.partikode==14 || solr_partinavn2.partikode==31}">
          <li>
          <input type="checkbox" name="fq"    <c:if test="${isSelected}">checked=true</c:if>
           onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=partikode:${fn:escapeXml(solr_partinavn2.partikode)}${sortresult}${sortfacet}';
            else if (!this.checked)
            <c:set var="names" value="${paramValues.fq}"/>
            <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=partinavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
        "/> <c:choose><c:when test="${solr_partinavn2.partilinkid!=-1}"><a title="partiets historie" href='javascript:NewWin("<p:url value='/parti/partihistorie/${solr_partinavn2.partilinkid}' />")'> ${solr_partinavn2.partinavn}</a></c:when><c:otherwise>${solr_partinavn2.partinavn}</c:otherwise></c:choose>(${solr_partinavn2.dokumenttall})</span>
      </li>
         </c:when>
         <c:when test="${fn:length(solr_partinavn) < 8}">
          <li>
        <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=partikode:${fn:escapeXml(solr_partinavn2.partikode)}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=partinavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>

        "/><c:choose><c:when test="${solr_partinavn2.partilinkid!=-1}"><a title="partiets historie" href='javascript:NewWin("<p:url value='/parti/partihistorie/${solr_partinavn2.partilinkid}' />")'> ${solr_partinavn2.partinavn}</a></c:when><c:otherwise>${solr_partinavn2.partinavn}</c:otherwise></c:choose>(${solr_partinavn2.dokumenttall})
      </li>
       </c:when>
        </c:choose>
         </c:if>
       </c:forEach>
       </ul>
       <ul class="value_zero" style="display: none; " id="divcheckp1">
       <c:forEach items="${solr_partinavn}" var="solr_partinavn2" >
           <c:if test="${solr_partinavn2.dokumenttall ==0 }">
              <c:set var="partizeros_top7" value="${partizeros_top7+1}"></c:set>
              <c:set var="names" value="${paramValues.fq}"/>
             <c:set var="partinavn2" value="partikode:${solr_partinavn2.partikode}"/>
             <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
             <c:if test="${names[k] ==partinavn2 }">
             <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>
        <c:choose>
         <c:when test="${solr_partinavn2.partikode==71 || solr_partinavn2.partikode==21 || solr_partinavn2.partikode==81 || solr_partinavn2.partikode==51 || solr_partinavn2.partikode==41 || solr_partinavn2.partikode==14 || solr_partinavn2.partikode==31}">
          <li>
          <input type="checkbox" name="fq"    <c:if test="${isSelected}">checked=true</c:if>
           onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=partikode:${fn:escapeXml(solr_partinavn2.partikode)}${sortresult}${sortfacet}';
            else if (!this.checked)
            <c:set var="names" value="${paramValues.fq}"/>
            <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=partinavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
        "/> <c:choose><c:when test="${solr_partinavn2.partilinkid!=-1}"><a title="partiets historie" href='javascript:NewWin("<p:url value='/parti/partihistorie/${solr_partinavn2.partilinkid}' />")'> ${solr_partinavn2.partinavn}</a></c:when><c:otherwise>${solr_partinavn2.partinavn}</c:otherwise></c:choose>(${solr_partinavn2.dokumenttall})</span>
      </li>
         </c:when>
          <c:when test="${fn:length(solr_partinavn) < 8}">
          <li>
        <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=partikode:${fn:escapeXml(solr_partinavn2.partinavn)}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=partinavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
        "/><c:choose><c:when test="${solr_partinavn2.partilinkid!=-1}"><a title="partiets historie" href='javascript:NewWin("<p:url value='/parti/partihistorie/${solr_partinavn2.partilinkid}' />")'> ${solr_partinavn2.partinavn}</a></c:when><c:otherwise>${solr_partinavn2.partinavn}</c:otherwise></c:choose>(${solr_partinavn2.dokumenttall})
      </li>
       </c:when>
        </c:choose>
        </c:if>
       </c:forEach>
       </ul>

            <p class="lesmer">  <a id="hideid1"  href="javascript:onclick=showHide('hideid1');">Vis mer</a></p>
            <div id="hideid1area" class="hidearea">
           <ul>
           <c:forEach items="${solr_partinavn}" var="solr_partinavn2">
         <c:if test="${solr_partinavn2.dokumenttall >0 }">
         <c:set var="parti_uten_top7" value="${parti_uten_top7+1}"></c:set>
        <c:set var="names" value="${paramValues.fq}"/>

         <c:set var="partinavn2" value="partikode:${solr_partinavn2.partikode}"/>
        <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
           <c:if test="${names[k] ==partinavn2 }">
        <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>

      <c:if test="${solr_partinavn2.partikode !=71 && solr_partinavn2.partikode !=21 && solr_partinavn2.partikode !=81 && solr_partinavn2.partikode !=51 && solr_partinavn2.partikode !=41 && solr_partinavn2.partikode !=14 && solr_partinavn2.partikode !=31}">
       <li>
        <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
     onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=partikode:${fn:escapeXml(solr_partinavn2.partikode)}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=partinavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
       "/><c:choose><c:when test="${solr_partinavn2.partilinkid!=-1}"><a title="partiets historie" href='javascript:NewWin("<p:url value='/parti/partihistorie/${solr_partinavn2.partilinkid}' />")'> ${solr_partinavn2.partinavn}</a></c:when><c:otherwise>${solr_partinavn2.partinavn}</c:otherwise></c:choose>(${solr_partinavn2.dokumenttall})
           </li>
           </c:if>
        </c:if>
       </c:forEach>
      </ul>
    <ul class="value_zero"  style="display: none; " id="divcheckp2">
       <c:forEach items="${solr_partinavn}" var="solr_partinavn2">
        <c:if test="${solr_partinavn2.dokumenttall ==0 }">
            <c:set var="partizeros_uten_top7" value="${partizeros_uten_top7+1}"></c:set>
         <c:set var="names" value="${paramValues.fq}"/>
                    <c:set var="partinavn2" value="partikode:${solr_partinavn2.partikode}"/>
           <c:set var="isSelected" value="false" />
                      <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                  <c:if test="${names[k] ==partinavn2 }">
                   <c:set var="isSelected" value="true" />
                    </c:if>
                  </c:forEach>
           <c:if test="${solr_partinavn2.partikode !=71 && solr_partinavn2.partikode !=21 && solr_partinavn2.partikode !=81 && solr_partinavn2.partikode !=51 && solr_partinavn2.partikode !=41 && solr_partinavn2.partikode !=14 && solr_partinavn2.partikode !=31}">
           <li>
            <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=yes</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=partikode:${fn:escapeXml(solr_partinavn2.partikode)}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=partinavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
        "/><c:choose><c:when test="${solr_partinavn2.partilinkid!=-1}"><a title="partiets historie" href='javascript:NewWin("<p:url value='/parti/partihistorie/${solr_partinavn2.partilinkid}' />")'> ${solr_partinavn2.partinavn}</a></c:when><c:otherwise>${solr_partinavn2.partinavn}</c:otherwise></c:choose>(${solr_partinavn2.dokumenttall})
           </li>
           </c:if>
          </c:if>
       </c:forEach>
       </ul>
    </div>
    </div>

    <div class="fasetter">
        <c:set var="doktypenames" value="${paramValues.fq}"/>
      <div class="fasettertittel">Dokumenttype
         <span class="markfjerne">
          <a  href="<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}${rows}${fn:escapeXml(requestScope.markdoktypeurl)}${sortresult}${sortfacet}' />">fjern alle</a>
        <span class="sortspace"></span><a  href="<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}&rows=${param.rows}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.doktypeurl)}${sortresult}${sortfacet}' />">merk alle</a>
         </span>
       </div>   
    <ul>
     <c:forEach items="${solr_doktype}" var="solr_doktype2" >
          <c:if test="${solr_doktype2.doktypetall >0 }">
     <li>
     <c:set var="names" value="${paramValues.fq}"/>
    <%-- <c:set var="doknavn" value="${fn:substring(fn:trim(solr_doktype2.doktypenavn),0,fn:length(fn:trim(solr_doktype2.doktypenavn)))}"/> --%>
     <c:set var="doknavn2" value="doktype:${solr_doktype2.doktypekode}"/>
        <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
           <c:if test="${names[k] ==doknavn2 }">
        <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>
     <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}&fq=doktype:${fn:escapeXml(solr_doktype2.doktypekode)}${fn:escapeXml(requestScope.url)}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=doknavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
   "/> ${solr_doktype2.doktypenavn} (${solr_doktype2.doktypetall})
   </li>
    </c:if>
     </c:forEach>
   </ul>
   <ul class="value_zero"  style="display: none; " id="divcheckd1">
    <c:forEach items="${solr_doktype}" var="solr_doktype2" varStatus="searchIndex">
      <c:if test="${solr_doktype2.doktypetall ==0 }">
    <li>
     <c:set var="names" value="${paramValues.fq}"/>
    <%-- <c:set var="doknavn" value="${fn:substring(fn:trim(solr_doktype2.doktypenavn),0,fn:length(fn:trim(solr_doktype2.doktypenavn)))}"/>   --%>
    <c:set var="doknavn2" value="doktype:${solr_doktype2.doktypekode}"/>
         <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
           <c:if test="${names[k] ==doknavn2 }">
        <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>                                                                                                 
     <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}&fq=doktype:${fn:escapeXml(solr_doktype2.doktypekode)}${fn:escapeXml(requestScope.url)}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=doknavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
   "/>  ${solr_doktype2.doktypenavn} (${solr_doktype2.doktypetall})
    </li>
       </c:if>
    </c:forEach>
    </ul>
    </div>
      <div class="fasetter">
    <c:set var="aarstallnames" value="${paramValues.fq}"/>
      <div  class="fasettertittel">Årstall
       <span class="markfjerne">
         <a  href="<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}${rows}${fn:escapeXml(requestScope.markaarstallurl)}${sortresult}${sortfacet}' />">fjern alle</a>
        <span class="sortspace"></span>  <a  href="<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}${rows}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.aarstallurl)}${sortresult}${sortfacet}' />">merk alle</a>
       </span>
      </div>
       <ul>
          <c:forEach items="${solr_aar}" var="solr_aar2" >
       <c:if test="${solr_aar2.aartall >0 }">
         <c:set var="top6" value="${top6+1}"></c:set>
         <c:if test="${top6 < 7}">
       <li>
          <c:set var="names" value="${paramValues.fq}"/>
     <c:set var="aarstallnavn" value="${fn:substring(fn:trim(solr_aar2.aar),0,fn:length(fn:trim(solr_aar2.aar)))}"/>
     <c:set var="aarstallnavn2" value="aarstall:${aarstallnavn}"/>
       <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
           <c:if test="${names[k] ==aarstallnavn2 }">
        <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>
         <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=aarstall:${solr_aar2.aar}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=aarstallnavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
   "/>  ${solr_aar2.aar_tall}
       </li>
       </c:if>
        </c:if>
        </c:forEach>
      </ul>
    <ul class="value_zero"  style="display: none; " id="divchecka1">
          <c:forEach items="${solr_aar}" var="solr_aar2" >
       <c:if test="${solr_aar2.aartall ==0 }">
         <c:set var="top6" value="${top6+1}"></c:set>
         <c:if test="${top6 < 7}">
       <li>
          <c:set var="names" value="${paramValues.fq}"/>
     <c:set var="aarstallnavn" value="${fn:substring(fn:trim(solr_aar2.aar),0,fn:length(fn:trim(solr_aar2.aar)))}"/>
     <c:set var="aarstallnavn2" value="aarstall:${aarstallnavn}"/>

       <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
           <c:if test="${names[k] ==aarstallnavn2 }">
        <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>
         <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location =  '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=aarstall:${solr_aar2.aar}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=aarstallnavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location =  '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
   "/>  ${solr_aar2.aar_tall}
       </li>
       </c:if>
        </c:if>
        </c:forEach>
      </ul>
      <c:forEach items="${solr_aar}" var="solr_aar2" >
                 <c:if test="${solr_aar2.aartall >0 }">
         <c:set var="vismertekst" value="${vismertekst+1}"></c:set>
         </c:if>
          </c:forEach>
         <c:if test="${vismertekst >= 7}">
          </c:if>
      <p class="lesmer"> <a id="hideid2"  href="javascript:onclick=showHide('hideid2');">Vis mer</a></p>
      <div id="hideid2area" class="hidearea">
        <ul>
        <c:forEach items="${solr_aar}" var="solr_aar2" >
         <c:if test="${solr_aar2.aartall >0 }">
         <c:set var="uten_top6" value="${uten_top6+1}"></c:set>
         <c:if test="${uten_top6 >= 7}">
           <li>
            <c:set var="names" value="${paramValues.fq}"/>
     <c:set var="aarstallnavn" value="${fn:substring(fn:trim(solr_aar2.aar),0,fn:length(fn:trim(solr_aar2.aar)))}"/>
     <c:set var="aarstallnavn2" value="aarstall:${aarstallnavn}"/>
     <c:set var="isSelected" value="false" scope="page"/>
     <c:forEach var="k" begin="0" end="${fn:length(names)}" >
     <c:if test="${names[k] ==aarstallnavn2 }">
     <c:set var="isSelected" value="true" scope="page"/>
     </c:if>
       </c:forEach>
      <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=aarstall:${solr_aar2.aar}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=aarstallnavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
   "/>  ${solr_aar2.aar_tall}
           </li>
             </c:if>
           </c:if>
            </c:forEach>
              </ul>
      <ul class="value_zero"  style="display: none; " id="divchecka2">
                 <c:forEach items="${solr_aar}" var="solr_aar2" >
            <c:if test="${solr_aar2.aartall ==0 }">
            <c:set var="zeros_uten_top6" value="${zeros_uten_top6+1}"></c:set>
         <c:if test="${zeros_uten_top6 >= 7}">
              <li>
             <c:set var="names" value="${paramValues.fq}"/>
             <c:set var="aarstallnavn" value="${fn:substring(fn:trim(solr_aar2.aar),0,fn:length(fn:trim(solr_aar2.aar)))}"/>
            <c:set var="aarstallnavn2" value="aarstall:${aarstallnavn}"/>
              <c:set var="isSelected" value="false" scope="page"/>
           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
           <c:if test="${names[k] ==aarstallnavn2 }">
        <c:set var="isSelected" value="true" scope="page"/>
         </c:if>
       </c:forEach>
     <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=aarstall:${solr_aar2.aar}${sortresult}${sortfacet}';
       else if (!this.checked)
        <c:set var="names" value="${paramValues.fq}"/>
        <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                         <c:if test="${names[k] !=aarstallnavn2}">
                         <c:set var="fqlist" value="&fq=" />
                       <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                       </c:if>
             </c:forEach>
       window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
        <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                     <c:set var="myVar" value="" />
               </c:forEach>
   "/>  ${solr_aar2.aar_tall} 
         </li>
         </c:if>
         </c:if>             
   </c:forEach>
        </ul>
  </div>
  </div>
 </div>


<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>
