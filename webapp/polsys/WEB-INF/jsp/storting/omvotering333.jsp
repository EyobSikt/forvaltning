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

<div id="main" class="superwide">


<c:if test="${no}"><h1>Polsys - Voteringsarkivet </h1></c:if>

<!--parti -->

<c:if test="${no}">
<h3>Tidsperiode: 1979-2010</h3>
 <ul>
<li><a target="_blank" href="http://polsysdata.nsd.uib.no/webview/index.jsp?v=2&study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-S08&mode=cube&cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-S08_C1&top=yes">Uenighetsindeksen 1979–2009 (Alle saker) </a></li>

<p>
<h2>FrP mot sentrum?</h2>

Ein utvida analyse som måler alle saker sett under eitt, syner at FrP har vorte meir einig med alle partia på Stortinget. Denne trenden har blitt noko sterkare etter at Stoltenberg si raudgrøne regjering vart gjenvald i 2009. Figuren syner at det er ein klar tredeling når det gjeld partia sin avstand til FrP. Avstanden til dei raudgrøne partia er størst, deretter fylgjer KrF og Venstre og til sist Høgre. Det er berre mellom Høgre og FrP at me finn at ueinigheita er under 50 prosent, samstundes har ueinigheita mellom FrP og sentrumspartia vorte mindre dei siste åra.

</p>
<div class="updated">     
<a title="See the original element in Nesstar WebView" target="_blank" href="http://polsysdata.nsd.uib.no/webview/index.jsp?headers=Sesjon&stubs=part1&stubs=part2&measure=common&layers=virtual&study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-S08&mode=cube&virtualsubset=parti_value&part1subset=7&part2subset=1+-+7&part1slice=7&part2slice=1&Sesjonslice=154&virtualslice=parti_value&v=2&submode=timeline&measuretype=4&cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-S08_C1&Sesjonsubset=124+-+154&top=yes" class="openorig">Open original</a>
</div>

<div id="diagram">
<img usemap="#A381C782A0682E3BA7E5718236CCBF293939" src="/test/Bilder/3109536528A0CC448DC1245C2770AE49193645759489823215123541.png">
</div>



<li><a target="_blank" href="http://polsysdata.nsd.uib.no/webview/index.jsp?v=2&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-E08&amp;mode=cube&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-E08_C1&amp;top=yes">Uenighetsindeksen 1979–2009 (Emne) </a></li>
<li><a target="_blank" href="http://polsysdata.nsd.uib.no/webview/index.jsp?v=2&amp;study=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfStudy%2FUI-SK08&amp;mode=cube&amp;cube=http%3A%2F%2F129.177.90.165%3A80%2Fobj%2FfCube%2FUI-SK08_C1&amp;top=yes">Uenighetsindeksen 1979–2009 (Stortingskomité) </a></li>
</ul>


</c:if>    

</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>