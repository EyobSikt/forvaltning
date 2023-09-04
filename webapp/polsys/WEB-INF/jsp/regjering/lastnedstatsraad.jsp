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
    <a href="<p:url value="/" />">PolSys</a>
    > <a href="<p:url value="/regjering/" />"> Regjering </a>
> <a href="<p:url value="/regjering/statsraadsarkivet/" />"> Statsrådsarkivet  </a>
> <a href="<p:url value="/regjering/statsraadsarkivet/regjeringer/" />"> Regjeringer   </a>
> Laste ned data om norske statsråder
</c:if>
    <c:if test="${en}">
        You are here:
        <a href="<p:url value="/" />">PolSys</a>
        > <a href="<p:url value="/regjering/" />"> Government </a>
        > <a href="<p:url value="/regjering/statsraadsarkivet/" />"> Archive of Ministers </a>
        > <a href="<p:url value="/regjering/statsraadsarkivet/regjeringer/" />"> Governments    </a>
        > Download data about norwegian ministers.
    </c:if>
</div>
    <div>
<p>        <c:if test="${no}"><a href="<m:url value="/en/regjering/lastnedstatsraad/" />">View this page in English.</a></c:if>
        <c:if test="${en}"><a href="<m:url value="/regjering/lastnedstatsraad/" />">View this page in Norwegian.</a></c:if>
</p>
    </div>
   <c:if test="${no}"> <h1>Laste ned data om norske statsråder</h1>
    <p>	Velg en eller flere regjeringer. Velg deretter på data format å laste ned statsråder datasett for de valgte regjeringer. </p>
   </c:if>
    <c:if test="${en}"><h1>Download data about norwegian ministers</h1>
     <p> Choose one or more governments. Then select the data set format to download norwegian ministers data sets for the selected governments.</p>
    </c:if>

<form action="<p:url value="/regjering/lastnedfilstatsraad/" />"  method="post">

    <TABLE class="text">
<tbody>
<!--
<TR>
		<TD>
			<h4>Valg År:</h4>
        fra:
		<select name="fraaar">
		<option value=-1 selected>Alle</option>
		<c:forEach items="${statsraadsaldersfordeling}" var="statsraadsaldersfordeling" >
        <option value=${statsraadsaldersfordeling.startaar}> ${statsraadsaldersfordeling.startaar}(${statsraadsaldersfordeling.startaar}-${statsraadsaldersfordeling.sluttaar})</option>
        </c:forEach>
		</select>
	  	t.o.m
		<select name="tilaar">
		<option value=-1 selected>Alle</option>
		<c:forEach items="${statsraadsaldersfordeling}" var="statsraadsaldersfordeling" >
        <option value=${statsraadsaldersfordeling.sluttaar}> ${statsraadsaldersfordeling.startaar} (${statsraadsaldersfordeling.startaar}-${statsraadsaldersfordeling.sluttaar})</option>
        </c:forEach>
		</select>
		</TD>
</tr>
 -->

<tr>
	<td>
<c:if test="${no}"><h4>Velg regjering:</h4></c:if><c:if test="${en}"><h4>Choose government:</h4></c:if>
			  <select name="regjeringsperiode" size=12 multiple>
				<option value="-1" selected><c:if test="${no}">Alle regjeringer</c:if><c:if test="${en}">All governments</c:if></option>
				 <c:forEach items="${statsraadsaldersfordeling}" var="statsraadsaldersfordeling" >
                     <c:choose><c:when test="${statsraadsaldersfordeling.sluttaar==9999}"><c:set var="sluttaar" value="${''}" /></c:when><c:otherwise><c:set var="sluttaar" value="${statsraadsaldersfordeling.sluttaar}"/></c:otherwise></c:choose>

                     <option value=${statsraadsaldersfordeling.startaar}-${statsraadsaldersfordeling.sluttaar}>${statsraadsaldersfordeling.regjeringsnavn_NO} (${statsraadsaldersfordeling.startaar}-${sluttaar}) </option>
                 </c:forEach>
				</select>
		</td>

 <td style="vertical-align:40px;">
 <select  name="filtype">
<option selected="selected" value="">-- <c:if test="${no}">Velg</c:if><c:if test="${en}">Choose</c:if> Data Format --</option>
<option  value="EXCEL">EXCEL</option>
<option  value="CSV">CSV</option>
</select>
<input type="submit" <c:if test="${no}">value="Lastned dataset"</c:if><c:if test="${en}">value="Download dataset"</c:if>/>
</td>
</tr>
</tbody>
</TABLE>
</form> 

<h3>Tips:</h3>
<c:if test="${no}"> <p>Du kan velge flere regjeringsperiode. Velg hvilken regjeringsperiode det skal laste ned. Datasett fra den uthevede vil bli lastet ned. </p></c:if>
<c:if test="${en}"> <p>You can choose more than one government periods. Select which government period to download. Data sets from the highlighted will be downloaded.</p></c:if>

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