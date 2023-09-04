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
    <c:param name="tittelNo" value="Søk i norske partidokumentarkivet" />
     <c:param name="beskrivelseNo" value="Søk i norske partiprogrammer." />
</c:import>

<div id="main"  >


<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="<p:url value="/"/>">PolSys</a>
> Om parti
</c:if>
</div>


<h1>Parti</h1>

<p> I partidatabasen ligger alle NSDs data om norske politiske partier. Her finner du politisk historie for en rekke partier, valglister fra stortings-, fylkes- og sametingsvalg, samt en rikholdig samling partidokumenter av ulike slag.</p>


<div style="width: 700px;">
<div class="menu-list-boks">
<div  class="boks-innhold">
<div  class="seksjon1">
<h3> <a href="<p:url value="/parti/partidokumentarkivet/" />"> Partidokumentarkivet </a> </h3>
<p></p>
<p>
Partidokumentarkivet er NSDs samling av norske partidokumenter, og inneholder ulike typer dokumenter datert helt tilbake til 1884. Med sine gode søke- og filtreringsfunksjoner utgjør arkivet et nyttig verktøy for å studere norsk politisk historie, og kan brukes til å se på hva som har blitt satt på dagsorden, og hva partiene har vært opptatt av. Arkivet inneholder valgprogram, prinsipprogram, vedtekter, pressemeldinger og andre dokumenter som stiftelseserklæringer og taler.
</p>
<p class="mer-link">
<a  href="<p:url value="/parti/partidokumentarkivet/" />"> Søk i partienes partidokumenter  </a>
</p>
</div>

<div  class="seksjon2">
<h3> <a href="<p:url value="/parti/partihistorie/" />">Partihistorie  </a></h3>
<p></p>
<p>
Partihistoriesidene gir oversikt over politisk-historiske hendelser for de største og viktigste partiene i norsk politikk. Arkivet inneholder også en kartlegging av sentrale partiorganisatoriske hendelser, som f.eks. utskilling av deler av et parti til et nytt parti eller sammenslåing av parti. 
</p>
<p class="mer-link">
<a  href="<p:url value="/parti/partihistorie/" />"> Bla i partienes historie </a>
</p>
</div>

<div  class="seksjon3">

<h3 >  <a href="<p:url value="/parti/stortingfylkestingsvalg/" />"> Valglistearkivet  </a></h3>
<p></p>
<p>
I valglistearkivet finner man alle valglister NSD har samlet inn for stortings- (fra 2001), fylkestings- (fra 2003) og sametingsvalg (fra 1989). Arkivet er tilrettelagt slik at man kan bla i listene ut fra valgår, parti og fylke.
</p>
<p class="mer-link">
<a   href="<p:url value="/parti/stortingfylkestingsvalg/" />"> Bla i partienes valglister </a>
</p>

</div>
</div>
</div>

</div>

<%-- main slutter --%>

 </div>



<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>
