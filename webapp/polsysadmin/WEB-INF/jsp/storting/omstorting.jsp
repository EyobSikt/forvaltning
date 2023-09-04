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

<div id="sidebar">


</div>

<div id="main" class="superwide">


<c:if test="${no}"><h1>Polsys - Storting data </h1></c:if>
<c:if test="${en}"><h1>Polsys - Parliament data</h1></c:if>

<c:if test="${no}"><p>
PolSys dataarkiv.
</p></c:if>
<c:if test="${en}"><p>
PolSys data archives.
</p></c:if>

<c:if test="${no}"><a href="<m:url value="/en/storting/" />">View this page in English.</a></c:if>
<c:if test="${en}"><a href="<m:url value="/storting/" />">View this page in Norwegian.</a></c:if>


<c:if test="${no}">
<h2>Stortingsdatabasen 1814-d.d.</h2>
<h3>Søk etter norskepolitiker eller bla via arkivet</h3>
<ul>
<li><a href="<p:url value="/storting/norskepolitikere/" />">Finn norskepolitiker 1814-d.d. (ved søk og indekser) </a></li>
<li><a href="<p:url value="/storting/sammensetning/" />">Det norske Storting 1814-d.d.</a></li>
<!--<li><a href="<p:url value="/storting/norskepolitikere_g/" />">Finn representant 1814-d.d. (ved søk og personindeks)</a></li>  -->

</ul>
</c:if>

<c:if test="${en}">
    <h2> The Norwegian Parliament Database 1814-present</h2>
    <h3>Find norwegian politician by searching  or browse archive of politicians </h3>
    <ul>
    <li><a href="<p:url value="/storting/norskepolitikere/" />">Find norwegian politician 1814-present (search by name and indexes)</a></li>
    <li><a href="<p:url value="/storting/sammensetning/" />">The Norwegian Storting (parliament) 1814-present</a></li>
   <!-- <li><a href="<p:url value="/storting/norskepolitikere_g/" />">Find MP</a></li> -->

    </ul>
</c:if>

<c:if test="${no}">
<h3>Fraksjonsmerknadsarkivet</h3>
 <h4>Tidsperiode: 1945-2005</h4>
<ul>
<li><a href="<p:url value="/storting/merknadsfrekvenser/" />">Innstillings- og merknadsfrekvenser </a></li>
<li><a href="<p:url value="/storting/frekvenser/" />">Frekvenser - velg innstillingstype og tidsrom </a></li>
<li><a href="<p:url value="/storting/partiavstand/" />">Politisk partiavstand  </a></li>
</ul>    
</c:if>

<c:if test="${en}">
<h3>Archive of Remarks in Recommendations</h3>
 <h4>Period: 1945-2005</h4>
<ul>
<li><a href="<p:url value="/storting/merknadsfrekvenser/" />">Remark and recommendation frequencies </a></li>
<li><a href="<p:url value="/storting/frekvenser/" />">Frequencies- choose type of recommendation and time period </a></li>
<li><a href="<p:url value="/storting/partiavstand/" />">Political party-distance  </a></li>
</ul>
</c:if>

<c:if test="${no}">
<h3>Dokument 8-arkivet</h3>
 <h4>Tidsperiode: 1984-1998</h4>
<ul>
<li><a href="<p:url value="/storting/dokumentarkiv/" />">Frekvenser  </a></li>
</ul>
</c:if>

<c:if test="${en}">
<h3>Archive of Private Proposals</h3>
 <h4>Period: 1984-1998</h4>
<ul>
<li><a href="<p:url value="/storting/dokumentarkiv/" />">Frequencies </a></li>
</ul>
</c:if>    

<c:if test="${no}">
<h3>Arkiv med sitat om kulturpolitikk</h3>
 <h4>Data år: 1865, 1924-1969</h4>
<ul>
<li><a href="<p:url value="/storting/kulturpolitikk/" />">Kulturpolitiske sitat - fritekstsøk  </a></li>
<li><a href="<p:url value="/storting/kulturpolitikk/" />">Kulturpolitiske sitat - tematisk søk  </a></li>
<li><a href="<p:url value="/storting/kulturpolitikkfrekvens/" />">Kulturpolitiske sitat - frekvenstabell   </a></li>
</ul>
</c:if>

<c:if test="${en}">
<h3> Archive of Political Culture Quotes</h3>
 <h4>Data years: 1865, 1924-1969</h4>
<ul>
<li><a href="<p:url value="/storting/kulturpolitikk/" />">Culture quotes - search (free text) </a></li>
<li><a href="<p:url value="/storting/kulturpolitikk/" />">Culture quotes - search (thematic) </a></li>
<li><a href="<p:url value="/storting/kulturpolitikkfrekvens/" />">Culture quotes - frequencies  </a></li>
</ul>
</c:if>    

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>