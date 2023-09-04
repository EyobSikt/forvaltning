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
    <c:param name="tittelNo" value="Stortings- og fylkestingsvalg" />
    <c:param name="tittelEn" value="Stortings- og fylkestingsvalg" />
</c:import>


<div id="main" class="wide">
<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
    <a href="https://nsd.no/polsys/">PolSys</a>
    > <a href="https://nsd.no/polsys/parti/">Parti</a>
> Stortings- og fylkestingsvalg valglister
</c:if>
</div>    

<h1>Valglistearkivet</h1>    

<span class="overridep">
NSDs valglistearkiv inneholder valglister for stortings- (fra 2001 til d.d.), fylkestings- (fra 2003 til d.d.) og sametingsvalg (fra 1989 til d.d.). Arkivet gir en utmerket oversikt over alle kandidater til stortings- og fylkestingsvalg for ulike partier i alle landets fylker. Valglistearkivet oppdateres fortløpende ved hvert valg.
</span>

<div  class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
<div class="dhtmlgoodies_lessmer">
<div >
    <h4>Om valglistearkivet</h4>
    <p>
Valglistearkivet ble opprettet som et ledd i NSDs <a href="http://www.samfunnsveven.no/" target="_blank">skolevalgsprosjekt</a>. Ved å samle inn de ulike partienes valglister, kan man se hvilke kandidater som ut fra skolevalgsresultatene vil komme til å få plass på Stortinget/Sametinget/fylkestinget.
    </p>
 <p>
Pressemeldinger fra partier håndteres vanligvis slik at dato og reell avsender, som ikke alltid er oppgitt i selve pressemeldingen, legges til i dokumentet (særlig gjelder dette elektroniske pressemeldinger mottatt per e-post). Samtidig fjerner vi privat kontaktinformasjon som telefonnummer/e-postadresse, som ofte oppgis avslutningsvis som en del av pressemeldingene. </p>
 <h4>Kilder</h4>
<p>
I forbindelse med stortings- og fylkestingsvalg har NSD vanligvis mottatt valglistene fra de lokale valgstyrene, mens valglistene for sametingsvalgene er samlet inn og oversendt NSD i forbindelse med delprosjektet Historiske valgdata - en del av prosjektet <a href="http://www.samiskhs.no/article.php?id=766&amp;p=" target="_blank">Samisk valgforskningsprogram 2009-2011</a>, henter NSD valglistene direkte fra valg.no og sametinget.no, og tilrettelegger dem i valglistearkivet.
</p>
</div>
</div>    
<script type="text/javascript">
initShowHideDivs();
</script>
<p></p>

<div class="valgmenu">
 <ul>
   <li class="valgt">
    <h2> Stortings- og fylkestingsvalg (2001-2019)  </h2>
  </li>
  <li>
 <a  href="<p:url value="/parti/sametingsvalg/" />">  Sametingsvalg (1989-2017) </a>
 </li>
</ul>
</div>

<p></p>

 <c:choose>
     <c:when test="${param.aar==2019 || param.aar==null}"><c:set var="sel2019" value="selected"> </c:set> <c:set var="tabellcaption" value="Fylkestingsvalg - 2019"> </c:set></c:when>
     <c:when test="${param.aar==2017 || param.aar==null}"><c:set var="sel2017" value="selected"> </c:set> <c:set var="tabellcaption" value="Stortingsvalg - 2017"> </c:set></c:when>
     <c:when test="${param.aar==2015 }"><c:set var="sel2015" value="selected"> </c:set> <c:set var="tabellcaption" value="Fylkestingsvalg - 2015"> </c:set></c:when>
     <c:when test="${param.aar==2013 }"><c:set var="sel2013" value="selected"> </c:set> <c:set var="tabellcaption" value="Stortingsvalg - 2013"> </c:set></c:when>
     <c:when test="${param.aar==2011 }"><c:set var="sel2011" value="selected"> </c:set> <c:set var="tabellcaption" value="Fylkestingsvalg - 2011"> </c:set></c:when>
     <c:when test="${param.aar==2009 }"><c:set var="sel2009" value="selected"> </c:set> <c:set var="tabellcaption" value="Stortingsvalg - 2009"> </c:set></c:when>
  <c:when test="${param.aar==2007 }"><c:set var="sel2007" value="selected"> </c:set> <c:set var="tabellcaption" value="Fylkestingsvalg - 2007"> </c:set></c:when>
  <c:when test="${param.aar==2005 }"><c:set var="sel2005" value="selected"> </c:set> <c:set var="tabellcaption" value="Stortingsvalg - 2005"> </c:set></c:when>
   <c:when test="${param.aar==2003 }"><c:set var="sel2003" value="selected"> </c:set> <c:set var="tabellcaption" value="Fylkestingsvalg - 2003"> </c:set></c:when>
  <c:when test="${param.aar==2001 }"><c:set var="sel2001" value="selected"> </c:set><c:set var="tabellcaption" value="Stortingsvalg - 2001"> </c:set></c:when>
</c:choose>


<div>Velg valgperiode
    <select
       onchange="location = this.options[this.selectedIndex].value;">
        <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2019"/>'  <c:out value="${sel2019}"/> >Fylkestingsvalget 2019</option>
        <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2017"/>'  <c:out value="${sel2017}"/> >Stortingsvalget 2017</option>
        <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2015"/>'  <c:out value="${sel2015}"/> >Fylkestingsvalget 2015</option>
        <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2013"/>'  <c:out value="${sel2013}"/> >Stortingsvalget 2013</option>
        <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2011"/>'  <c:out value="${sel2011}"/> >Fylkestingsvalget 2011</option>
    <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2009"/>'  <c:out value="${sel2009}"/> >Stortingsvalget 2009</option>
    <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2007"/>' <c:out value="${sel2007}"/> >Fylkestingsvalget 2007</option>
    <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2005"/>' <c:out value="${sel2005}"/> >Stortingsvalget 2005 </option>
    <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2003"/>' <c:out value="${sel2003}"/> >Fylkestingsvalget 2003  </option>
     <option name = "storting-fylkestingsvalg" value='<p:url value="/parti/stortingfylkestingsvalg/?aar=2001"/>' <c:out value="${sel2001}"/> >Stortingsvalget 2001  </option>
</select>
</div>


<table class="text">
<CAPTION>Valglister for ${tabellcaption}</CAPTION>
<thead>
	<tr>
        <th>Valgkrets</th><th>Parti</th><th> <%--Valgliste for--%>
        <c:forEach items="${navnlister}" var="navnlister" begin="0" end="0">
        ${navnlister.partinavn} <%--i ${navnlister.fylkenavn}--%></th>
         </c:forEach>
	</tr>
</thead>
<tbody>
<tr>

	<td valign=top nowrap="nowrap">
		<c:forEach items="${fylker}" var="fylker">
      <span class="valgistepartifylke">
<c:choose><c:when test="${fylker.fylke_kode==fn:escapeXml(requestScope.fkode)}"><strong>${fylker.partinavn}</strong></c:when><c:otherwise><a href="<p:url value="/parti/stortingfylkestingsvalg/?aar=${fylker.aarstal}&fid=${fylker.fylke_kode}"/>">${fylker.partinavn}</a></c:otherwise></c:choose>
	</span>
    </c:forEach>
	</td>
    <td valign=top nowrap="nowrap">
        <c:forEach items="${partier}" var="partier">
        <span class="valgistepartifylke">
       <c:choose><c:when test="${partier.partikode == fn:escapeXml(requestScope.pkode)}"><strong>${partier.partinavn}</strong></c:when><c:otherwise><a href="<p:url value="/parti/stortingfylkestingsvalg/?aar=${partier.aarstal}&fid=${partier.fylke_kode}&pid=${partier.partikode}"/>">${partier.partinavn}</a></c:otherwise></c:choose>
		</span>
        </c:forEach>
    </td>
    <td valign=top>
		 <c:if test="${fn:length(navnlister) >= 1}">
			<table class="zebra, text">
			<thead>
				<tr>
				<th>Nr.</th><th>Navn</th><th>Stilling</th><th>Bosted</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${navnlister}" var="navnlister">
				<tr>
				<td>${navnlister.nummer}</td> <td>${navnlister.navn}</td> <td>${navnlister.stilling}</td> <td>${navnlister.bosted}</td>
				</tr>
				</c:forEach>
			</tbody>
			</table>
		</c:if>

        <c:if test="${fn:length(navnlister) < 1}">
			<p>
				<strong>Ingen treff!</strong>
			</p>
			<p>
				Partiet stilte kun liste i fylkene til venstre. (Eventuelt kan det være at valglisten foreløpig ikke er tilrettelagt av NSD.)
			</p>
		</c:if>
	</td>
</tr>
</tbody>
</table>


<p>&nbsp;</p>    

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>
