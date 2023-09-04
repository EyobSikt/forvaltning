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
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Storting Database" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Parliament Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Storting hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the Parliament Database at NSD." />
</c:import>

<div id="sidebar">
<!--
<h3>LASTNED DATASET</h3>
<form action="<p:url value="/storting/lastnedfil/" />"  method="post">

<table class="text">
<caption>Velg variabler</caption>
 <thead>
 <tr>
      <th >År</th>
      <th >Parti</th>
 </tr>
 </thead>
<tbody>
<tr>
<td width="100px" rowspan="3" valign="top">
                 <c:forEach items="${valgaar}" var="aar" >
                  <input type="checkbox" name="aar" value="choc"> ${aar.valgAar}</input>
                 </c:forEach>
   </td>
<td width="50px"  valign="top" title="Klikk her for prosjektdetaljer">
                 <c:forEach items="${parti}" var="partilist" >
                  <input type="checkbox" name="partinavn" value="choc"> ${partilist.partiKortnavn}</input><br>
                 </c:forEach>
   </td>

</tr>

</tbody>    
</table>
<br>
<div>
<p>
<select id="format" name="format">
<option selected="selected" value="">-- Select Data Format --</option>
<!--<option value="SPSS">SPSS</option>
<option value="SPSSPORT">SPSS Portable</option>
<option value="STATA">Stata v.8</option>
<option value="STATA7">Stata v.7</option>
<option value="STATA6">Stata v.6</option>
<option value="NSD96">NSDstat</option>
<option value="FIXED">Textfile</option>
<option value="FREET">Delimited</option>
<option value="SAS">SAS</option>
-->
    <!--
<option value="EXCEL">EXCEL fil</option>
<option value="CSV">CSV fil</option>
</select>
</p>
<p>
<input type="submit" value="Lastned dataset"/>
</p>
</div>

!-->



</div>


<div id="main" class="wide">
<div>
<c:if test="${no}">
Du er her:
<a href="/polsys/">PolSys</a>
> <a href="<p:url value="/storting/" />"> Storting </a>
> Lastned dataset
</c:if>
</div>

   <c:if test="${no}"> <h1>Lastned dataset</h1></c:if>
    <c:if test="${en}"><h1>Download dataset </h1>
    <h3>This page is not translated to English</h3>
     <p>----------------------------------------------</p>
    </c:if>

    <p>	Velg variabler. Klikk deretter filtype og på lastned knappen. </p>

<form action="<p:url value="/storting/lastned/" />"  method="post">

    <TABLE class="text">
<tbody>

<TR>
		<TD>
			<h4>Valg År:</h4>
        f.o.d
		<select name="fraaar">
		<option value=-1 selected>Alle</option>
		<c:forEach items="${valgaar}" var="valgaar" >
        <option value=${valgaar.periodeKode}>${valgaar.valgAar}(${valgaar.fleirtaltekst})</option>
        </c:forEach>
		</select>
	  	t.o.d
		<select name="tilaar">
		<option value=-1 selected>Alle</option>
		<c:forEach items="${valgaar}" var="valgaar" >
        <option value=${valgaar.periodeKode}>${valgaar.valgAar}(${valgaar.fleirtaltekst})</option>
        </c:forEach>
		</select>
		</TD>
        <!--
		<TD>
			<h4>Periode:</h4>
					<input type="checkbox" name="Aar" value="1814" checked>
					<font size=1>1814-1903</font></input>
					<input type="checkbox" name="Aar" value="1905" checked>
					<font size="1">1903-1945</font></input>
					<input type="checkbox" name="Aar" value="1945" checked>
					<font size="1">1945-d.d.</font></input>
		</TD>
       -->
       <td>
				<h4>Velg dataset:</h4>
				<select name="dataset" >
				<!--<option value="1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16" selected>Alle datasetene</option> -->
				<option value="-1"></option>
                <option value="1">Stortingsrepresentanter 1814-dd</option>
                <option value="2">Bakgrunnsopplysninger 1814-dd</option>
                <option value="3">Utdyrk historie 1814-dd </option>
                <option value="4">Kommunal og fylkespolitisk 1814-dd</option>
                <option value="5">Partiverv aktivitet 1814-dd </option>
                <option value="6">Stortingsrelaterte verv 1814-dd </option>
                <option value="7">Offverv 1814-dd </option>
                <option value="8">Orgverv 1814-dd </option>
                <option value="9">Admverv 1814-dd </option>    
                <!--
                <option value="1,2,3,4,5">Periode: 1814-1905</option>
				<option value="2222">- bakgrunnsopplysninger 1814-1905</option>
				<option value="3333">- stortingsaktivitet  1814-1905</option>
				<option value="4444">Periode: 1905-1940</option>
				<option value="5555">- bakgrunnsopplysninger  1905-1940</option>
				<option value="6777">- utdyrk historie 1905-1940</option>
				<option value="7777">Periode: 1945-d.d.</option>
				<option value="8888">- bakgrunnsopplysninger 1945-d.d.</option>
				<option value="9999">- utdyrk historie 1945-d.d.</option>
                !-->
			</select>
		</td>
  <td>
</td>
</tr>
<tr>
	<td>
        <h4>Velg parti:</h4>
			  <select name="partikode" size=6 multiple>
				<option value="-1" selected>Alle partiene</option>
				 <c:forEach items="${parti}" var="partilist" >
                     <option value=${partilist.partiKode}>${partilist.partiNavn} (${partilist.partiKortnavn})</option>
                 </c:forEach>
				</select>
		</td>
	<td>
<h4>Velg fylke:</h4>
		<select name="fylkekode" size=6 multiple>
		<option value="-1" selected>Alle fylkene</option>
		<c:forEach items="${fylke}" var="fylkelist" >
        <option value=${fylkelist.fylkeid}>${fylkelist.fylkenavn}</option>
        </c:forEach>
    	</select>
		</td>
 <td style="vertical-align:40px;">
 <select  name="filtype">
<option selected="selected" value="">-- Velg Data Format --</option>
<option  value="EXCEL">EXCEL fil</option>
<option  value="CSV">CSV fil</option>
</select>
<input type="submit" value="Lastned dataset"/>  
</td>
</tr>
</tbody>
</TABLE>
</form> 

<!--
 <TABLE class="text">
<tbody>
	<TR>
		<TD>
			<h4>Parti:</h4>
       <c:forEach items="${parti}" var="partilist" >
                  <input type="checkbox" name="partinavn" value="choc"> ${partilist.partiNavn} (${partilist.partiKortnavn})</input><br>  
                 </c:forEach>
		</TD>
		<TD>
			<h4>Fylke:</h4>
					<c:forEach items="${fylke}" var="fylkelist" >
                  <input type="checkbox" name="fylkenavn" value="choc"> ${fylkelist.fylkenavn}</input><br>
                 </c:forEach>
		</TD>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<TD>
			<input type="Submit" name="btnUtfoer" value="Utfør">
		</TD>
	</TR>
</tbody>
</TABLE>
!-->






</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>