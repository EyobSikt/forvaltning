<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 15.nov.2010
  Time: 15:31:32
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
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Fraksjonsmerknadsarkivet" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Fraksjonsmerknadsarkivet" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Stortingsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the Parliament Database at NSD." />
</c:import>


 <script type="text/javascript">
   var sesjonarray = new Array();
  var sesjon_id = 0;
  var periodearray = new Array();
  var periode_id = 0;
	<c:forEach items="${frekvensersesjon_asc}" var="frekvensersesjon" >
		sesjonarray[sesjon_id] = new Array("${frekvensersesjon.sesjonId}", "${frekvensersesjon.sesjon}");
		sesjon_id++;
	</c:forEach>
   <c:forEach items="${frekvenserperiode_asc}" var="frekvenserperiode" >
		periodearray[periode_id] = new Array( "${frekvenserperiode.periode}", "${frekvenserperiode.periodetekst}");
	    periode_id++;
	</c:forEach>

 function appNewLevel3(level3Id, level3Name) {
	var createsesjon = document.createElement('option');
    var createperiode = document.createElement('option');
	createsesjon.text = level3Name;
	createsesjon.value = level3Id;
    createperiode.text = level3Name;
	createperiode.value = level3Id;
	var sesjon = document.getElementById('sesjon');
    var periode = document.getElementById('periode');
	try {
		sesjon.add(createsesjon, null); // standards compliant; doesn't work in IE
        periode.add(createperiode, null);
	} catch(ex) {
 		sesjon.add(createsesjon); // IE only
        periode.add(createperiode);
	}
 }
 function removeAllLevel3() {
	var sesjon = document.getElementById('sesjon');
    var periode = document.getElementById('periode');
	if (sesjon) sesjon.length = 0;
    if (periode) periode.length = 0;
 }
 function changesesjonperiode() {
	document.getElementById("sesjon").disabled=false;
    document.getElementById("periode").disabled=false;
	removeAllLevel3();

	for (var i=0; i< sesjonarray.length; i++) {
		if (document.form.tidsenhet[0].checked == true) {
			appNewLevel3(sesjonarray[i][0], sesjonarray[i][1]);
		}
    }
      for (var i=0; i< periodearray.length; i++) {
       if (document.form.tidsenhet[1].checked == true) {
			appNewLevel3(periodearray[i][0], periodearray[i][1]);
		}
	 }
	if (document.getElementById("sesjon").length == 1) document.getElementById("sesjon").disabled=true;
    if (document.getElementById("periode").length == 1) document.getElementById("periode").disabled=true;
 }
  </script>


<div id="sidebar">

</div>


<div id="main" class="superwide">
<div>
<c:if test="${no}">
Du er her:
    <a href="<p:url value="/"/>">PolSys</a>
> <a href="<p:url value="/storting/" />"> Storting </a>
> Frekvenser
</c:if>
 <c:if test="${en}">
    You are here:
    <a href="<p:url value="/"/>">PolSys</a>
    > <a href="<p:url value="/storting/" />"> Parliament </a>
    > Frequencies
 </c:if>
</div>
    <div>
        <c:if test="${no}"><a href="<m:url value="/en/storting/frekvenser/" />">View this page in English.</a></c:if>
        <c:if test="${en}"><a href="<m:url value="/storting/frekvenser/" />">View this page in Norwegian.</a></c:if>
    </div>
<c:if test="${no}"><h1>Frekvenser - velg innstillingstype og tidsrom</h1></c:if>
    <c:if test="${en}"><p>
        <h2>Frequencies- choose type of recommendation and time period</h2>
        <h3>This page is not translated to English </h3>
        If the information on this page seems important, please contact <a style="line-height: 1.22em; color: rgb(48, 80, 128); text-decoration: none;" href="mailto:polsys@nsd.uib.no">PolSys</a>
        </p>
                <p>----------------------------------------------</p>
    </c:if>

<%
String tidsenhet = request.getParameter("tidsenhet") == null ? "-1" : request.getParameter("tidsenhet");
String FraSesjon = request.getParameter("FraSesjon") == null ? "-1" : request.getParameter("FraSesjon");
String TilSesjon = request.getParameter("TilSesjon") == null ? "-1" : request.getParameter("TilSesjon");
String Type = request.getParameter("Type") == null ? "-1" : request.getParameter("Type");
String aar = request.getParameter("aar") == null ? "-1" : request.getParameter("aar");
String said = request.getParameter("said") == null ? "-1" : request.getParameter("said");
String seid = request.getParameter("seid") == null ? "-1" : request.getParameter("seid");
String registrering = request.getParameter("registrering") == null ? "-1" : request.getParameter("registrering");
%>

<c:set var="isSelected" value="false" scope="page"/>
<c:if test="${param.tidsenhet==null}" >
    <c:set var="isSelected" value="true" scope="page"/>
</c:if>



 <form name="form" class="form" ACTION= "<p:url value="/storting/frekvenser/"/>"  METHOD="GET">
   <TABLE class="text">
   <tbody>
	 <TR>
		<TD>
			<h3>Tidsrom:</h3>
		</td>
		<TD>
			<h3>Innstillingstype:</h3>
		</td>
		<td></td>
	</tr>
	<TR>
		<td>
       <%--
       <h4>Sesjonsvis</h4> <input type="radio" name="tidsenhet" value="sesjon" <c:out value="${sel2011}"/> onClick="changesesjonperiode()" <%= tidsenhet.equals("sesjon") ? "checked=\"checked\"" : "" %>>
       <h4>Periodevis</h4> <input type="radio" name="tidsenhet" value="periode" <c:out value="${sel2009}"/> onClick="changesesjonperiode()" checked = true <%= tidsenhet.equals("periode") ? "checked=\"checked\"" : "" %>>
        --%>
        <h4>Sesjonsvis </h4> <input type="radio" name="tidsenhet" value="sesjon" onClick="changesesjonperiode()" <%= tidsenhet.equals("sesjon") ? "checked=\"checked \"" : "" %>>
        <h4>Periodevis </h4> <input type="radio" name="tidsenhet" value="periode" onClick="changesesjonperiode()" <%= tidsenhet.equals("periode") ? "checked=\"checked \"" : "" %> <c:if test="${isSelected}">checked=true</c:if>>

     <c:choose><c:when test="${param.tidsenhet=='sesjon'}">
         <select name="FraSesjon" id="sesjon">
             <c:forEach items="${frekvensersesjon_asc}" var="frekvenserperiode" >
                 <option value="${frekvenserperiode.sesjonId}" <c:if test="${frekvenserperiode.sesjonId==param.FraSesjon}"> selected="selected" </c:if>>${frekvenserperiode.sesjon}</option>
             </c:forEach>
         </select>
         til
         <select name="TilSesjon" id="periode">
             <c:forEach items="${frekvensersesjon_desc}" var="frekvenserperiode" >
                 <option value="${frekvenserperiode.sesjonId}" <c:if test="${frekvenserperiode.sesjonId==param.TilSesjon}"> selected="selected" </c:if>>${frekvenserperiode.sesjon}</option>
             </c:forEach>
         </select>
     </c:when>
      <c:otherwise>
          <select name="FraSesjon" id="sesjon">
              <c:forEach items="${frekvenserperiode_asc}" var="frekvenserperiode" >
                  <option value="${frekvenserperiode.periode}" <c:if test="${frekvenserperiode.periode==param.FraSesjon}"> selected="selected" </c:if>>${frekvenserperiode.periodetekst} </option>
              </c:forEach>
          </select>
          til
          <select name="TilSesjon" id="periode">
              <c:forEach items="${frekvenserperiode_desc}" var="frekvenserperiode" >
                  <option value="${frekvenserperiode.periode}" <c:if test="${frekvenserperiode.periode==param.TilSesjon}"> selected="selected" </c:if>>${frekvenserperiode.periodetekst}</option>
              </c:forEach>
          </select>
      </c:otherwise></c:choose>


 </TD>
	<TD>
        <c:set var="types" value="${paramValues.Type}"/>
        <c:set var="zero_isSelected" value="false" scope="page"/>
        <c:set var="en_isSelected" value="false" scope="page"/>
        <c:set var="to_isSelected" value="false" scope="page"/>
        <c:set var="tre_isSelected" value="false" scope="page"/>
        <c:forEach var="k" begin="0" end="${fn:length(types)}" >
            <c:if test="${types[k] ==0 || fn:length(types)==0}">
                <c:set var="zero_isSelected" value="true" scope="page"/>
            </c:if>
            <c:if test="${types[k] ==1 }">
                <c:set var="en_isSelected" value="true" scope="page"/>
            </c:if>
            <c:if test="${types[k] ==2 }">
                <c:set var="to_isSelected" value="true" scope="page"/>
            </c:if>
            <c:if test="${types[k] ==3 }">
                <c:set var="tre_isSelected" value="true" scope="page"/>
            </c:if>
        </c:forEach>
     <input type="checkbox" name="Type" value="0"  <c:if test="${zero_isSelected}">checked=true</c:if>>
			Alle innstillinger<br>
			<p></p>
			<input type="checkbox" name="Type" value="1"  <c:if test="${en_isSelected}">checked=true</c:if>>
			Stortingsinnstillinger<br>
			<input type="checkbox" name="Type" value="2"  <c:if test="${to_isSelected}">checked=true</c:if>>
			Odelstingsinnstillinger<br>
			<input type="checkbox" name="Type" value="3"  <c:if test="${tre_isSelected}">checked=true</c:if>>
			Budsjettinnstillinger<br>
  </TD>
 <td></td>
 </tr>
 <tr>
 <td></td>
	<td></td>
	<TD>
		<input type="Submit" name="btnUtfoer" value="Utfør" >
	</TD>
</TR>
</tbody>
</TABLE>     
 </form>

<c:set var="type" value="${paramValues.Type}"/>

<c:if test="${param.btnUtfoer !=null}">
<c:if test="${param.Type =='0'}">
<c:set var="Tidsenhet" value="alle innstillingstyper"></c:set>
</c:if>
<c:if test="${param.Type =='1'}">
<c:set var="Tidsenhet" value="stortingsinnstillinger"></c:set>
</c:if>
<c:if test="${param.Type =='2'}">
<c:set var="Tidsenhet" value="odelstingsinnstillinger"></c:set>
</c:if>
<c:if test="${param.Type =='3'}">
<c:set var="Tidsenhet" value="budsjettinnstillinger"></c:set>
</c:if>

    <c:forEach var="k" begin="0" end="${fn:length(names)}" >
        <c:if test="${type[k] =='1' && type[k+1] =='2'}">
            <c:set var="Tidsenhet" value="stortings- og odelstingsinnstillinger"></c:set>
        </c:if>
    </c:forEach>
    <c:forEach var="k" begin="0" end="${fn:length(names)}" >
        <c:if test="${type[k] =='1' && type[k+1] =='3'}">
            <c:set var="Tidsenhet" value="stortings- og budsjettinnstillinger"></c:set>
        </c:if>
    </c:forEach>
<c:forEach var="k" begin="0" end="${fn:length(names)}" >
<c:if test="${type[k] =='2' && type[k+1] =='3'}">
<c:set var="Tidsenhet" value="Odelstings- og budsjettinnstillinger"></c:set>
</c:if>
</c:forEach>

    <table class="zebra">
	<caption>Frekvenser for ${Tidsenhet}</caption>
	<thead>
		<tr>
		<th>${param.tidsenhet}</th>
		<th>Antall innstillinger</th>
		<th>Innstillinger m/merkn. (N)</th>
		<th>Innstillinger m/merkn. (%)</th>
		<th>Antall fraksjons- merknader</th>
		<th>Fraksjons- merknader pr. innstilling</th>
		<th>Fraksjonsmerknader pr. innstilling m/merkn.</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${sokfrekvenserperiode}" var="sokfrekvenserperiode" >
		<tr>
			<td>${sokfrekvenserperiode.periodetekst}</td>
            <td>${sokfrekvenserperiode.periodesum_innst}</td>
             <td>${sokfrekvenserperiode.periodesum_innst_m_merkn}</td>
            <td>${sokfrekvenserperiode.prs_innst_m_merkn}</td>
            <td>${sokfrekvenserperiode.periodesum_totalt_merknader}</td>
             <td>${sokfrekvenserperiode.prs_merknader_pr_instilling}</td>
            <td>${sokfrekvenserperiode.prs_merknader_pr_instilling_merkn}</td>
		</tr>
		 </c:forEach>
	</tbody>
</table>

<c:forEach items="${sokfrekvenserperiode}" var="sokfrekvenserperiode" >
<c:set value="${sokfrekvenserperiode.instillinglengde}" var="typelengde"></c:set>
<c:set value="${sokfrekvenserperiode.instilling}" var="instillingnavn"></c:set>
</c:forEach>

  
<c:if test="${param.Type !='0' && typelengde !='3' && typelengde !='4' }">
 <table class="zebra">

<caption>...${instillingnavn} relativt til alle innstillingstyper (total)</caption>
<thead>
	<tr>
		<th>${param.tidsenhet}</th>
		<th>% Innst. av total</th>
		<th>% Innst. m/merkn. av total</th>
		<th>% Merkn. av total</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${sokfrekvenserperiode}" var="sokfrekvenserperiode" >
	<tr>
		<td>${sokfrekvenserperiode.periodetekst}</td>
		<td>${sokfrekvenserperiode.prs_innst_s_m_merkn}</td>
		<td>${sokfrekvenserperiode.prs_merknader_s_pr_instilling}</td>
		<td>${sokfrekvenserperiode.prs_merknader_s_pr_instilling_merkn}</td>
	</tr>
</c:forEach>
</tbody>
</table>

</c:if>    

</c:if>    

<h3>Forklaringer til tabellen</h3>
<ul>
	<li>Innstillinger m/merkn. (N) = antall innstillinger med fraksjonsmerknader</li>
	<li>Innstillinger m/merkn. (%) = prosent innstillinger med fraksjonsmerknader</li>
	<li>Fraksjonsmerknader pr. innstilling m/merkn. = antall fraksjonsmerknader pr. innstilling med fraksjonsmerknader</li>
</ul>



    
</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>