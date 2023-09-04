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


<div id="sidebar">
  <h2 class="NavigatorboxHeader">Avgrens søk</h2>

<table class="zebra, text">
		<caption>Personindeks</caption>
        <tbody>
		<tr>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=a&aar=${param.aar}"/>">A</a></td>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=b&aar=${param.aar}"/>">B</a></td>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=c&aar=${param.aar}"/>">C</a></td>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=d&aar=${param.aar}"/>">D</a></td>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=e&aar=${param.aar}"/>">E</a></td>
		  </tr>
          <tr>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=f&aar=${param.aar}"/>">F</a></td>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=g&aar=${param.aar}"/>">G</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=h&aar=${param.aar}"/>">H</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=i&aar=${param.aar}"/>">I</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=j&aar=${param.aar}"/>">J</a></td>
		    </tr>
          <tr>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=k&aar=${param.aar}"/>">K</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=l&aar=${param.aar}"/>">L</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=m&aar=${param.aar}"/>">M</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=n&aar=${param.aar}"/>">N</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=o&aar=${param.aar}"/>">O</a></td>
             </tr>
          <tr>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=p&aar=${param.aar}"/>">P</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=q&aar=${param.aar}"/>">Q</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=r&aar=${param.aar}"/>">R</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=s&aar=${param.aar}"/>">S</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=t&aar=${param.aar}"/>">T</a></td>
              </tr>
          <tr>
		    <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=u&aar=${param.aar}"/>">U</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=v&aar=${param.aar}"/>">V</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=w&aar=${param.aar}"/>">W</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=x&aar=${param.aar}"/>">X</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=y&aar=${param.aar}"/>">Y</a></td>
		      </tr>
          <tr>
			<td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=z&aar=${param.aar}"/>">Z</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=æ&aar=${param.aar}"/>">Æ</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=ø&aar=${param.aar}"/>">Ø</a></td>
            <td width="20"><a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=å&aar=${param.aar}"/>">Å</a></td>
		</tr>
</tbody>
</table>
<h3>Politiker i:</h3>
<a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=storting&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>">stortingsrepresentat</a><br>
<a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=statsraad&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>">statsråd</a><br>
<a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=statssekretar&periode=${param.periode}&aar=${param.aar}"/>">statssekretær</a><br>

<h3>Periode:</h3>
 <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=1945&aar=${param.aar}"/>">1945-d.d. </a><br>
 <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=1903&aar=${param.aar}"/>">1903-1945 </a><br>
 <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=1814&aar=${param.aar}"/>">1814-1903 </a>

<h3>Se kun tidsrom:</h3>
<table  class="text">
<tbody>
<tr>
 <td  rowspan="3" valign="top" width="62px">
    <c:forEach items="${valgaar}" var="aar" >
      <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${aar.periodeAar}"/>"> ${aar.valgAar}</a>
    </c:forEach>
  </td>
</tr>
</tbody>
</table>

</div>

<div id="main" class="superwide">
<div>
    <c:if test="${no}">
Du er her:
<a href="<p:url value="/"/>">PolSys</a>
> <a href="<p:url value="/storting/"/>">Storting</a>
> norskepolitikere

    </c:if>
</div>

<p></p>

<div class="sokhjelp">
 <ul class="enhetnav">
<c:if test="${param.p=='sok'}"><c:set var="s_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p=='hjelp'}"><c:set var="h_class" value="valgt"></c:set> </c:if>
<li  class="${s_class}"><a href="<p:url value="/storting/norskepolitikere/?p=sok" />">${en ? "søk og last ned " : "Søk og last ned"}</a></li>
<li  class="${h_class}"><a href="<p:url value="/storting/omdataset/?p=hjelp" />">${en ? "variabel beskrivelse" : "Variable beskrivelse"}</a></li>
</ul>
</div>

<p>&nbsp;</p>

<c:if test="${param.p == null || param.p=='sok'}">

    <c:if test="${no}"><p> <h2>Finn norskepolitikere og last ned biografier</h2></p></c:if>
     <c:if test="${en}"><p><h2>This page is not translated to English </h2></p>
     <p>----------------------------------------------</p>
    </c:if>


<h3>Søketips:</h3>
<p>
Du kan søke i politierearkivet (1814-d.d.)  ved å skrive et ord i søke-boksen eller ved å velge fra høyre menyen. Du kan avgrense søket ved å bruke menyen.
Etter at du får resultat har du mulighet å laste ned ulike biografier av treff i excel eller csv format.  
</p>

    <span class="overridep">
Du kan last ned biografi av treff i Excel eller csv format ved å velge fra rulleboksen.
</span>

    <div  class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
    <div class="dhtmlgoodies_lessmer">
        <div >
            <h4>Om partihistoriesidene</h4>
            <p>
                På disse sidene har, etter beste evne, de viktigste historiske hendelsene i norsk partihistorie siden 1814 blitt nedtegnet. Det sentrale utvalgskriteriet har vært hvorvidt hendelsene har omfattet og påvirket politiske institusjoner. Nedtegningene er tidvis i stikkordsform, og har som sin primære funksjon å minne om hendelsen, og ikke å være en komplett historisk redegjørelse. Under organisatoriske hendelser har fokus vært på å inkludere de hendelsene som har trukket opp grensene for et partis virksomhet, som oppsplitting og sammenslåing av partier, eller nedleggelse av et parti.
            </p>
            <h4>Tilrettelegging av innholdet</h4>
            <p>
                NSDs partihistoriske arkiv er som nevnt på forsiden ikke et komplett arkiv over alle partier som har eksistert i norsk politikk gjennom historien. Det gir imidlertid et godt bilde av partihistorien til de fremste partiene i norsk politikk. For de partiene der organisatoriske endringer har blitt dokumentert, har intensjonen vært å få gjort dette så fullstendig som mulig. Hva som defineres som en viktig historisk hendelse vil kunne variere noe fra person til person, og vil i tillegg være avhengig av ståsted, bakgrunn og tid. Utvalget som er gjort på disse sidene har primært til hensikt å komplementere de øvrige partidata NSD har i sine arkiver.
            </p>
            <p>
                Historiske prosesser er ikke inkludert på disse sidene. For eksempel er unionsoppløsningen i 1905 med som en arkivpost, mens prosessen og årsakene som førte til oppløsningen i liten grad er inkludert. Tilsvarende er andre prosessliknende og vanskelig identifiserbare hendelser ikke tatt med. Et slikt eksempel kan være den økonomiske stagnasjonen noen år etter første verdenskrig, som etter hvert fikk implikasjoner for norsk partipolitikk og organiseringen av norske partier.
            </p>
            <h4>Kilder</h4>
            <p>
                I registreringsarbeidet har det blitt benyttet en del politisk litteratur, faktabøker og internettkilder. De fleste av hendelsene innenfor partihistorien må likevel kunne sies å være allemannseie. ‘Lov om arbeidervern’ av 1892 kan f.eks. ikke knyttes opp mot en bestemt kilde.
            </p>
        </div>
    </div>
    <script type="text/javascript">
        initShowHideDivs();
    </script>
    <p></p>



<%--
<form  ACTION="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}&start=${param.start}"/>" METHOD="GET">
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


   <c:if test="${param.navn !=null}">
        <div class="filter">
           <div class="filter_item">
   
            <c:if test="${param.navn !=null && param.navn!='' }"> "${param.navn}"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
           &nbsp;&nbsp;
            <c:if test="${param.bs !=null && param.bs!='' }"> "${param.bs}"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            &nbsp;&nbsp;

            <c:if test="${param.st !=null && param.st!='' }">  "stortingsrepresentat"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            <c:if test="${param.sr !=null && param.sr!='' }">  "statsråd"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            <c:if test="${param.ss !=null && param.ss!='' }">  "statssekretær"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            &nbsp;&nbsp;

            <c:if test="${param.periode !=null && param.periode!='' }">
                <c:if test="${param.periode=='1814'}">"1814-1903"</c:if>
                <c:if test="${param.periode=='1903'}">"1903-1945"</c:if>
                <c:if test="${param.periode=='1945'}">"1945-d.d."</c:if>
                <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            &nbsp;&nbsp;
            <c:if test="${param.aar !=null && param.aar!='' }">
            <c:forEach items="${valgaar}" var="aar" ><c:if test="${aar.periodeAar == param.aar}"> "${aar.valgAar}"</c:if> </c:forEach>
                <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar="/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            </div>
       </div>
  </c:if>

<p></p>

<p>
<%--
<c:if test="${param.navn !=null}">
<c:if test="${fn:length(allenorskepolitikere) > 0}">

Antall treff: ${fn:length(allenorskepolitikere)}

</c:if>
</c:if>
--%>
</p>

<%--
<c:if test="${param.navn !=null}">
<c:if test="${fn:length(allenorskepolitikere) > 0}" >
--%>

    <p>Velg og last ned biografi av treff i Excel eller csv format. <a href="<p:url value="/storting/omdataset/?p=hjelp"/>"> Les variable beskrivelse av datafilen.</a><br></p>



 <form action="<p:url value="/storting/newlastned/" />"  method="post">

   <TABLE class="text">
    <tbody>
<TR>
		<TD>
		<input type="radio" name="dataset" value="1" ><a href="<p:url value="/storting/omdataset/?datatype=saktivitet"/>">Stortingsaktivitet</a><br>
        <input type="radio" name="dataset" value="2"><a href="<p:url value="/storting/omdataset/?datatype=utdyrke"/>">Utdanning og yrke</a> <br>
        <input type="radio" name="dataset" value="3"><a href="<p:url value="/storting/omdataset/?datatype=kaktivitet"/>">Kommunalpolitisk aktivitet</a>  <br>
        <input type="radio" name="dataset" value="4"><a href="<p:url value="/storting/omdataset/?datatype=faktivitet"/>">Fylkespolitisk aktivitet</a>
		</TD>

       <td>
        <input type="radio" name="dataset" value="5"><a href="<p:url value="/storting/omdataset/?datatype=pverv"/>">Partiverv</a><br>
        <input type="radio" name="dataset" value="6"><a href="<p:url value="/storting/omdataset/?datatype=fverv"/>">Offentlige verv</a><br>
        <input type="radio" name="dataset" value="7"><a href="<p:url value="/storting/omdataset/?datatype=averv"/>">Adminverv</a><br>
        <input type="radio" name="dataset" value="8"><a href="<p:url value="/storting/omdataset/?datatype=orgverv"/>">Orgverv</a>
		</td>

 <td style="vertical-align:40px;">

<input type="hidden" name="navn" value="<%= request.getParameter("navn") %>">
<input type="hidden" name="st" value="<%= request.getParameter("st") %>">
<input type="hidden" name="sr" value="<%= request.getParameter("sr") %>">
<input type="hidden" name="ss" value="<%= request.getParameter("ss") %>">
<input type="hidden" name="bs" value="<%= request.getParameter("bs") %>">
<input type="hidden" name="periode" value="<%= request.getParameter("periode") %>">      
<input type="hidden" name="aar" value="<%= request.getParameter("aar") %>">


<!--
 <select  name="filtype">
<option selected="selected" value="">-- Velg Data Format --</option>
<option  value="EXCEL">EXCEL fil</option>
<option  value="CSV">CSV fil</option>
</select>

<input type="submit" value="Lastned dataset"/> -->

<button name="filtype" value="EXCEL" title="Lastned dataset i excel format" type="submit">
<img alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/excel.png">
</button>
     
 <button name="filtype" value="CSV" title="Lastned dataset i CSV format" type="submit">
<img alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/csv-icon.gif">
</button>
 </td>
</tr>

</tbody>
</TABLE>
</form>
<%--
</c:if>
</c:if>
--%>
<p>&nbsp;</p>

<div class="hitlist">
<%--
 <c:if test="${param.navn !=null}">
 <c:if test="${fn:length(allenorskepolitikere) > 0}">
 --%>
<c:set var="style" value="simple"></c:set>
<c:set var="position" value="top"></c:set>
<c:set var="maxPageItems" value="50"></c:set>
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
<caption> Antall treff Politikere: ${fn:length(allenorskepolitikere)} </caption>
<thead>
<tr>
<th width="150" valign="top">
<strong>Etternavn, fornavn</strong>

<th width="65" valign="top">
<strong>Født år</strong>
</th>
</tr>
</thead>
<tbody>

<c:forEach items="${allenorskepolitikere}" var="allenorskepolitikere" varStatus="searchIndex" begin="${offset}" end="${endLoop}">
      <pg:item>
 <tr>
 <th class="label">
<A HREF="<p:url value="/person/politikerbiografi/?person_id=${allenorskepolitikere.personId}&p_info=personalia"/>">
        ${allenorskepolitikere.etterNavn}, ${allenorskepolitikere.forNavn}</A>
</th>
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
<%--
</c:if>
</c:if>
--%>
</div>

<p></p>

</c:if>

 </div>


<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp"/>
<%-- --------------------------------------------------------------------- --%>