<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Index-side for polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

<h1>Forvaltning</h1>

<p>Forvaltning-administrasjonssider.</p>

<ul>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_endringskode" />">Endringskoder</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_grunnenhet" />">Grunnenhet</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_nivaa" />">Nivaa</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_tilknytningsform" />">Tilknytningsform</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_cofog" />">COFOG</a></li>
<li><a href="<c:url value="/forvaltning/endringsnummer.p" />">Endringsnummer</a></li>
<li><a href="<c:url value="/forvaltning/relasjonenhet.p" />">Relasjonenheter</a></li>
<li><a href="<c:url value="/forvaltning/lovdata.p" />">Lovdata</a></li>
<li><a href="<c:url value="/forvaltning/litteraturliste.p" />">Litteratur</a></li>
<li><strong><a href="<c:url value="/forvaltning/velgendring.p" />">Endring/Enhet ("statsforvaltninga")</a></strong></li>
<li><a href="<c:url value="/forvaltning/eventtable.p" />">Event table</a></li>
</ul>


<p>Sjekkliste.</p>
<ul>
<li><a href="<c:url value="/forvaltning/ansatteenhetkobling.p" />">SST-FVDB-enhet-kobling</a></li>
<li><a href="<c:url value="/forvaltning/cofogalleliste.p" />">Enheter med cofog</a></li>
</ul>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
