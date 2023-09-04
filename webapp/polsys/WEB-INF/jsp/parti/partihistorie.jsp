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
      <c:param name="navigation" value="/polsys/parti/" />
    <c:param name="tittelNo" value="Partihistorie" />
    <c:param name="beskrivelseNo" value="Parti bakgrunnsinformasjon." />
</c:import>
 
<div id="main" class="superwide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
    <a href="https://nsd.no/polsys/">PolSys</a>
    > <a href="https://nsd.no/polsys/parti/">Parti</a>
<c:if test="${fn:escapeXml(requestScope.partikode) !=''}">
> <a href="<p:url value="/parti/partihistorie/" />">Partihistorie</a>
 </c:if>
<c:if test="${fn:escapeXml(requestScope.partikode) ==''}">
> Partihistorie
 </c:if>
<c:if test="${fn:escapeXml(requestScope.partikode) !=''}">
> <c:forEach items="${partinavn}" var="partinavn">
     ${partinavn.partinavn}
  </c:forEach>
</c:if>
    </c:if>
</div>


<c:if test="${fn:escapeXml(requestScope.partikode) ==''}">

<h1>Partihistorie </h1>

<span class="overridep">
Velg et parti fra menyen under for å lese om partiets historie og for å se oversikten over partiorganisatoriske hendelser. Sidene gir et oversiktsbilde av norsk partihistorie med hensyn til organisatorisk utvikling, ideologisk grunnlag, valg- og regjeringsdeltakelse mm.. Informasjonen som finnes under hvert parti gir mye interessant lesning, og kan brukes til f.eks. å identifisere sentrale historiske skillelinjer mellom partier i Norge.
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
    NSDs partihistoriske arkiv er som nevnt på forsiden ikke et komplett arkiv over alle partier som har eksistert i norsk politikk gjennom historien. Det gir imidlertid et nyttig oversiktsbilde av partihistorien til de fremste partiene i norsk politikk. For de partiene der også organisatoriske endringer har blitt dokumentert, har intensjonen vært å få gjort dette så fullstendig som mulig. Hva som oppfattes som en viktig historisk hendelse vil kunne variere noe fra person til person, og vil i tillegg være avhengig av ståsted, bakgrunn og tid. Utvalget som er gjort på disse sidene har primært til hensikt å komplementere de øvrige partidata NSD har i sine arkiver.
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


 <ul>   
    <c:forEach items="${partihistorie}" var="partihistorie">
    <li><a href="<p:url value="/parti/partihistorie/${partihistorie.partikode}" />" >${partihistorie.partinavn}</a></li>
    </c:forEach>
</ul>
</c:if>

<c:if test="${fn:escapeXml(requestScope.partikode) !=''}">
  <c:forEach items="${partinavn}" var="partinavn">
     <h2>${partinavn.partinavn}</h2>
  </c:forEach>

<c:if test="${fn:length(partibakgrunn) ==0 && fn:length(endringshistorie)==0}">
    <p><br>Søkjet ga ingen treff på partibakrunn og endringshistorie. </p>
</c:if>
 <c:forEach items="${partibakgrunn}" var="partibakgrunn">
 <table class="text">
    <caption>Partibakgrunn</caption>
    <thead>
     <tr>
    <th>Stifta</th><c:if test="${partibakgrunn.nedleggjingsaar!=null}"><th>Nedlagd</th></c:if><th>Valdeltaking</th><th>Stortingsrepresentasjon</th>
     </tr>
    </thead>
    <tbody>
    <tr>
       <td>${partibakgrunn.skipingstidspunkt}</td>
     	<c:if test="${partibakgrunn.nedleggjingsaar!=null}"><td>${partibakgrunn.nedleggjingsaar}</td></c:if>
          <td>${partibakgrunn.valdeltaking}</td>
       <c:choose><c:when test="${partibakgrunn.stortingsparti==0}"><td>Aldri</td></c:when><c:otherwise><td>Minst ein periode</td></c:otherwise></c:choose>
   </tr>
  </tbody>
</table>
<c:if test="${partibakgrunn.partihistorikk !=null}">
 <p>
 <h3>Historie</h3>
${partibakgrunn.partihistorikk}
 </p>
</c:if>
 </c:forEach>
<table class="zebra, text">
 <c:if test="${fn:length(endringshistorie) >= 1}">
    <caption>Sentrale organisatoriske hendingar</caption>
    <thead>
    <tr>
    <th>År</th><th>Hending</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${endringshistorie}" var="endringshistorie">
    <tr>
       <td>${endringshistorie.aarstal}</td>
       <td>${endringshistorie.dokumentasjon}</td>
     </tr>
        </c:forEach>
    </tbody>
   </c:if>
</table>
    
</c:if>


</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>
