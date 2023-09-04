<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Viser oversikt over departement.
--%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Interne departementsenheter - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Bodies within ministries - State Administration Database" />
    <c:param name="beskrivelseNo" value="Finn antall interne departementsenheter fordelt på administrative nivåer." />
    <c:param name="beskrivelseEn" value="Number of public administration bodies distributed on administrative levels." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<script type="text/javascript">
    function checkBoxValidation()
    {
        var name=document.getElementsByName('n');
        var fra=document.getElementsByName('fra')[0].value;
        var til=document.getElementsByName('til')[0].value;
        var d=document.getElementsByName('d')[0].value;
        var m=document.getElementsByName('m')[0].value;
        var str="";
        for(i=0;i<(name.length);i++){
            if(name[i].checked){
                str+=name[i].value+",";
            }
        }
        if(str.length>0){str=str.substring(0,str.length-1)};// remove the last comma
        var url="antalldepartementsenhet?n="+str+'&fra='+fra+'&til='+til+'&d='+d+'&m='+m;
        document.getElementById('form1').action=url;
        document.getElementById('form1').submit()
    }

</script>

<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> Departementsenheter
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> Public administration units
</c:if>
</div>

<h1>${en ? "Number of public administration bodies distributed on administrative levels" : "Antall interne departementsenheter fordelt på administrative nivåer"}</h1>

<c:if test="${no}">
<h3>Om tellemåten:</h3>
<p>
Alle interne enheter på alle nivå som er listet opp i Statskalenderen og på departementene sine hjemmesider er registrert, og vil vises i oversikten her.
</p>
</c:if>

<c:if test="${en}">
<h3>Method of counting:</h3>
<p>
All internal units under ministries are registered and can be listed here.
</p>
</c:if>

<h3>${en ? "Internal level:" : "Internt nivå"}</h3>

<%--old before rewrite rule for the new website --%>
<%--<form action="<p:url value="/forvaltning/antalldepartementsenhet" />" method="get">--%>
<form id="form1"  onsubmit="checkBoxValidation()" method="post">

<ul class="plain">
<c:forEach items="${nivaaer}" var="nivaa">
<li><input type="checkbox" name="n" value="${nivaa.kode}" id="n${nivaa.kode}" /> <label for="n${nivaa.kode}">${fn:escapeXml(nivaa.tekstFlertall)}</label></li>
</c:forEach>
</ul>

<h3>${en ? "Start- and end year:" : "Start- og sluttår:"}</h3>

<select size="1" name="fra">
<c:forEach begin="1947" end="${sistOppdatertDato.aar}" step="1" var="i">
<option value="${i}" ${i eq 1947 ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

<select size="1" name="til">
<c:forEach begin="1947" end="${sistOppdatertDato.aar}" step="1" var="i">
<option value="${i}" ${i eq sistOppdatertDato.aar ? 'selected="selected"' : ''} >${i}</option>
</c:forEach>
</select>

<input type="submit" value="OK" />

<h3>${en ? "Day and month:" : "Dag og måned:"}</h3>
<select size="1" name="d">
<c:forEach begin="1" end="31" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>

<select size="1" name="m">
<c:forEach begin="1" end="12" step="1" var="i">
<option value="${i}">${i}</option>
</c:forEach>
</select>
</form>


<c:if test="${no}">
<p>Data er oppdatert per ${sistOppdatertDato}. Dersom en vil ha et annet opptellingstidspunkt (dag eller måned) på året kan dette endrest her.</p>
</c:if>

<c:if test="${en}">
<p>Data is updated per ${sistOppdatertDato}. If you want another counting-date (day or month) in the year that differs from this date – change it here.</p>
</c:if>

</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
