<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

    <div class="breadcrumbs">
        Du er her:
        <a href="http://nsd241/polsysadmin/">PolSys-Admin</a>
        > Storting
    </div>

<h1>Storting-administrasjonssider.</h1>
<%--
<ul>
 <li><a href="<c:url value="/parti/dokliste.p?tabellnavn=t_forvaltning_endringskode" />">Parti - fylke_lister</a></li>
<li><a href="<c:url value="/parti/dokliste.p?tabellnavn=t_forvaltning_grunnenhet" />">Parti - partilister</a></li>
</ul>
--%>

<h2>Velg fra listen</h2>
<ul>
<!--
<li><strong><a href="<c:url value="/storting/votering/votering.p" />"> Les votering data fra stortinget side (blir gjort) </a></strong></li>
-->
    <li><strong><a href="<c:url value="/storting/votering/lessaker.p" />"> Importere votering data</a></strong></li>
<li><strong><a href="<c:url value="/storting/votering/lessaksopplysningerlist.p" />"> Endre votering data</a></strong></li>
</ul>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
