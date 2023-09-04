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
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Forvaltningsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>

<script language="javascript">
var showLink_sikringkonfidensialitet = "[LES HELE]";
var hideLink_sikringkonfidensialitet = "[SKJUL]";
//This method handles the logic to show/hide ingredient section
function display_sikringkonfidensialitet(divId,count){
	if(document.getElementById(divId+count).style.display=="none"){
		document.getElementById(divId+count).style.display = "inline";
		//document.getElementById(divId+count).class = "inactive";

		document.getElementById("ingredientsLink"+count).innerHTML=""+hideLink_sikringkonfidensialitet;
	}else{
		document.getElementById(divId+count).style.display = "none";
		//document.getElementById(divId+count).class = "active";

		document.getElementById("ingredientsLink"+count).innerHTML=""+showLink_sikringkonfidensialitet;
	}
}
 </script>

<style type="text/css">
.lesmer{
 float:right;
 font-size:9px;
}
</style>

<div id="sidebar">

</div>


<div id="main" class="superwide">
<div>
<c:if test="${no}">
Du er her:
	<a href="<p:url value="/"/>">PolSys</a>
> <a href="<p:url value="/storting/" />"> Storting </a>
> Kulturpolitiske
</c:if>
	<c:if test="${en}">
		You are here:
		<a href="<p:url value="/"/>">PolSys</a>
		> <a href="<p:url value="/storting/" />"> Parliament </a>
		> Culture quotes - search
	</c:if>
</div>

	<div>
		<c:if test="${no}"><a href="<m:url value="/en/storting/kulturpolitikk/" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/storting/kulturpolitikk/" />">View this page in Norwegian.</a></c:if>
	</div>
	<c:if test="${no}"> <h1>Fritekstsøk på kulturpolitiske sitat</h1></c:if>
	<c:if test="${en}"><h1>Culture quotes - search (free text) </h1>
		<h3>This page is not translated to English</h3>
		<p>----------------------------------------------</p>
	</c:if>

 <table class="text">
<tbody>
  <tr>
    <td>

		<form   ACTION= "<p:url value="/storting/kulturpolitikk/"/>"  METHOD=GET>
		<p>
			Skriv inn ett eller flere s&oslash;keord, kun ett s&oslash;keord i hvert
			felt:
		</p>

		<h4>Søkeord:</h4>

		<p>
          <input type="text" name="sitsok1" size="15">
          og
          <input type="text" name="sitsok2" size="15">
		  og
		  <input type="text" name="sitsok3" size="15">
		  <input type="Submit" name= "sok"value="Søk">
        </p>

	<table class="text">
	<tbody>
  <tr>
            <td>
				<h4>Velg parti:</h4>
				<p>
			  <select name="parti" size=6 multiple>
				<option value="-1" selected>Alle partiene</option>
				<c:forEach items="${partinavn}" var="partinavn" >
				  <option value=${partinavn.partikode}>${partinavn.partinavn}</option>
				  </c:forEach>
				</select>
				</p>
			</td>
            <td>
				<h4>Velg tema:</h4>
				<p>
				<select name="tema" size=6 multiple>
				<option value="1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16" selected>Alle temaene</option>
				<option value="1,2,3,4,5">Kultur som kollektivt gode</option>
				<option value="2">- Gjøre kulturgoder tilgjengelige</option>
				<option value="3">- Bevare kulturarven</option>
				<option value="4">- Nasjonal kultur og nasjonal identitet</option>
				<option value="5">- Folkeopplysning og allmenndanning</option>
				<option value="6">Kunstnerisk kvalitet</option>
				<option value="7">Sosiale forhold</option>
				<option value="8">Om forskjellige kulturområder</option>
				<option value="9">Kultur og andre samfunnssektorer</option>
				<option value="10">Kulturpolitiske synspunkter</option>
				<option value="11">Distriktspolitiske hensyn</option>
				<option value="12">Norsk bygdekino</option>
				<option value="13">Riksgalleriet</option>
				<option value="14">Rikskonsertene</option>
				<option value="15">Riksteateret</option>
				</select>
			</p>
			</td>
		</tr>

     </tbody>
</table>

<h4>Tidsperiode:</h4>
 		<p>
        Velg fra og med
		<select name="frasesjon">
		<option value=0 selected>Alle</option>
		<c:forEach var="i" begin="1863" end="1969">
		<option value=${i}>${i}</option>
		</c:forEach>
		</select>
	  	til og med
		<select name="tilsesjon">
		<option value=9999 selected>Alle</option>
		<c:forEach var="i" begin="1863" end="1969">
		<option value=${i}>${i}</option>
		</c:forEach>
		</select>
		</p>

		<h4>Sorter treffene etter:</h4>
        <table class="text">
		<tbody>
          <tr>
            <td width="100">
              <input type="radio" name="sortering" value=dbo.KultSitat.Dato checked>
              Dato </td>
            <td width="100">
              <input type="radio" name="sortering" value=dbo.Norske_politikere.navn>
              Etternavn</td>
            <td width="100">
              <input type="radio" name="sortering" value=dbo.DOK_partinavn.Eintaltekst>
              Parti</td>
		  </tr>
		</tbody>
		</table>
      </form>
	</td>
  </tr>
</tbody>
</table>

<p></p>

<c:if test="${param.tema=='1,2,3,4,5'}">
<c:set var="tema" value="Kultur som kollektivt gode"></c:set>
</c:if>
<c:if test="${param.tema=='2'}">
<c:set var="tema" value="Gjøre kulturgoder tilgjengelige"></c:set>
</c:if>
<c:if test="${param.tema=='3'}">
<c:set var="tema" value="Bevare kulturarven"></c:set>
</c:if>
<c:if test="${param.tema=='4'}">
<c:set var="tema" value="Nasjonal kultur og nasjonal identitet"></c:set>
</c:if>
<c:if test="${param.tema=='5'}">
<c:set var="tema" value="Folkeopplysning og allmenndanning"></c:set>
</c:if>
<c:if test="${param.tema=='6'}">
<c:set var="tema" value="Kunstnerisk kvalitet"></c:set>
</c:if>
<c:if test="${param.tema=='7'}">
<c:set var="tema" value="Sosiale forhold"></c:set>
</c:if>
<c:if test="${param.tema=='8'}">
<c:set var="tema" value="Om forskjellige kulturområder"></c:set>
</c:if>



 <c:if test="${fn:length(sokkulturpolitikk) > 0}">
Søket gav ${fn:length(sokkulturpolitikk)}  treff

<table class="text">
<tbody>

	<tr>
		<td>
			<strong>Tidsrom:</strong>

			${param.frasesjon}	- ${param.tilsesjon}

		</td>
	</tr>
	<tr>
		<td>
			<strong>Tema valgt:</strong>
			<c:choose>
            <c:when test="${tema!=null}">${tema}</c:when>
            <c:otherwise>Alle temaene</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>
			<strong>Parti valgt:</strong>
		     <c:forEach items="${partinavn}" var="partinavn" >
               <c:if test="${partinavn.partikode == param.parti}"></c:if>
				  ${partinavn.partinavn}
				  </c:forEach>
		</td>
	</tr>
</tbody>
</table>    

<p></p>

<c:if test="${param.sok !=null}">
 <c:set var="id" value="2"></c:set>
<ul>
	<c:forEach items="${sokkulturpolitikk}" var="sokkulturpolitikk" >
	<li>
		${sokkulturpolitikk.doktittel} [${sokkulturpolitikk.dato}]	<br>
		<a href="<p:url value="/person/politikerbiografi/?person_id=${sokkulturpolitikk.personId}&p_info=personalia"/>">	${sokkulturpolitikk.etternavn}, ${sokkulturpolitikk.fornavn} </a>
			(${sokkulturpolitikk.partinavn})
       <br>
       <c:set var="sikringkonfidensialitetmellomrom" value="${fn:indexOf(fn:substring(sokkulturpolitikk.sitathtml, 170, fn:length(sokkulturpolitikk.sitathtml)), ' ')}">  </c:set>
      ${fn:substring(sokkulturpolitikk.sitathtml, 0, 170+sikringkonfidensialitetmellomrom)}
        <c:if test="${fn:length(sokkulturpolitikk.sitathtml) > 170+sikringkonfidensialitetmellomrom }">

      <c:set var="id" value="${id+1}"></c:set>   
       <p style="display: none; " id="script${id+1}" >
            ${fn:substring(sokkulturpolitikk.sitathtml, 170+sikringkonfidensialitetmellomrom, fn:length(sokkulturpolitikk.sitathtml))}
            <a href='javascript:display_sikringkonfidensialitet("script",${id+1});' class="lesmer"  id="ingredientsLink${id+1}">[LES HELE]</a>
       </p>
    </c:if>
	</li>
 </c:forEach>
</ul>	       


  </c:if>

 </c:if>


</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>