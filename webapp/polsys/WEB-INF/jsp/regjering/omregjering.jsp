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


<c:if test="${no}"><h1>Polsys - Regjering data </h1></c:if>
<c:if test="${en}"><h1>Polsys - Government data</h1></c:if>

<c:if test="${no}"><p>
PolSys dataarkiv.
</p></c:if>
<c:if test="${en}"><p>
PolSys data archives.
</p></c:if>

<c:if test="${no}"><a href="<m:url value="/en/regjering/" />">View this page in English.</a></c:if>
<c:if test="${en}"><a href="<m:url value="/regjering/" />">View this page in Norwegian.</a></c:if>

<c:if test="${no}">
<p>
    <h2>Regjering</h2>

    <p>Regjeringssidene inneholder data om samtlige norske ministerier og regjeringer i fra 1814 og frem til i dag. Sidene inneholder regjeringsoversikter og departementsoversikter med tilhørende statsråder og statssekretærer.
    Sidene inneholder også en del enkel statistikk både på regjeringsnivå og på statsråds- og statssekretærnivå.</p>


    <h3><a href="<p:url value="/person/sok/" />">Søk etter norskepolitikere.</a></h3>
    <p>Søk norske politikere på etternavn</p>

    <h3><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsrådsarkivet</a></h3>
    <p>Statsråders utdanning, yrke, politiske aktivitet m.m.</p>

    <h3><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Statssekretærarkivet</a> </h3>
    <p>Statssekretærers politiske aktivitet m.m.</p>
</p>
</c:if>

<c:if test="${en}">
    <p>
    <h2>Government</h2>

    <p>The government pages consist of data about all Norwegian governments since the dissolution of Denmark-Norway in 1814.
        The pages include government overviews and ministerial overviews with corresponding ministers and state secretaries. The pages also include some basic statistics both on governmental level and on ministerial- and state secretarial level.</p>

    <h3><a href="<p:url value="/person/sok/" />">Search for Norwegian politicians</a></h3>
    <p>Search norwegian politician by last name</p>

    <h3><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a></h3>
    <p>Education, occupation, political activity etc. for Norwegian ministers.</p>

    <h3><a href="<p:url value="/regjering/statssekretaerarkivet/" />">Archive of State Secretaries</a> </h3>
    <p>Political activity etc. for Norwegian state secretaries.</p>
    </p>

</c:if>

<!---
<p>
<c:if test="${no}">
<h2>Statsrådsarkivet</h2>
<ul>
<li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringer/" />">Ministerier og regjeringer </a></li>
<li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsadhoc/" />">Ad hoc-regjeringer </a></li>
<li><a href="<p:url value="/regjering//statsraadsarkivetsvenskenorskeutenriksministre/" />">Svensk-norske utenriksministre (1814-1905)   </a></li>
<li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsstatsraader/" />">Statsråder departementsvis  </a></li>
<li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsvarighet/" />">Regjeringenes varighet   </a></li>
<li><a href="<p:url value="/regjering/statsraadsarkivet/statsraadskjonn/" />">Regjeringenes kjønnsfordeling   </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/statsraadsalder/" />">Statsrådenes gjennomsnittsalder regjeringsvis    </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/eldestyngstestatsraader/" />">Eldste og yngste statsråder   </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/statsraadsansientsfordelinger/" />">Ansiennitetsfordelinger    </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsparlamentariskgrunnlag/" />">Regjeringenes parlamentariske grunnlag     </a></li>
</ul>
</c:if>

<c:if test="${en}">
 <h2>Archive of Ministers</h2>
    <ul>
    <li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringer/" />">Governments </a></li>
    <li><a href="<p:url value="/regjering/statsraadsarkivet/adhocregjering/" />">Ad hoc governments </a></li>
   <li><a href="<p:url value="/regjering/statsraadsarkivet/svenskenorskeutenriksministre/" />">Svensk-norske utenriksministre (1814-1905)   </a></li>
    <li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsstatsraader/" />">Ministers by ministries  </a></li>
   <li><a href="<p:url value="/regjering/statsraadsarkivet/svenskenorskeutenriksministre/" />">Swedish-Norwegian foreign ministers (1814-1905)   </a></li>
<li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsvarighet/" />">Governing length   </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/statsraadskjonn/" />">The governments' gender distribution   </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/statsraadsalder/" />">The ministers' average age by governments     </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/eldestyngstestatsraader/" />">Oldest and youngest ministers  </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/statsraadsansientsfordelinger/" />">Seniority rankings     </a></li>
 <li><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsparlamentariskgrunnlag/" />">Parliamentary bases     </a></li>
    </ul>
</c:if>

<c:if test="${no}">
<h2>Statssekretærarkivet</h2>    
 <ul>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvis/" />">Statssekretærer regjeringsvis </a>  </li>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvis/" />"> Statssekretærer departementsvis  </a></li>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarskjonn/" />"> Regjeringenes kjønnsfordeling   </a></li>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarsalder/" />"> Statssekretærenes gjennomsnittsalder regjeringsvis   </a></li>
 </ul>
</c:if>

<c:if test="${en}">
 <h2>Archive of State Secretaries</h2>
 <ul>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvis/" />">State secretaries by governments</a></li>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretardepartementsvis/" />">State secretaries by ministries</a></li>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarskjonn/" />"> The governments' gender distribution    </a></li>
    <li><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarsalder/" />"> The state secretaries' average age by governments  </a></li>
 </ul>
</c:if>
</p>
--->
</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>