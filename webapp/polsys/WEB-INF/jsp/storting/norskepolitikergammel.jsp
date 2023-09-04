<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 17.nov.2010
  Time: 13:31:08
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

<%-- --------------------------------------------- inkluderer toppinnhold. --%>
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Forvaltningsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>

<div id="sidebar">
  
<table class="zebra, text">
		<caption>Personindeks</caption>
        <tbody>
		<tr>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=a">A</a></td>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=b">B</a></td>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=c">C</a></td>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=d">D</a></td>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=e">E</a></td>
		  </tr>
          <tr>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=f">F</a></td>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=g">G</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=h">H</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=i">I</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=j">J</a></td>
		    </tr>
          <tr>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=k">K</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=l">L</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=m">M</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=n">N</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=o">O</a></td>
             </tr>
          <tr>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=p">P</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=q">Q</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=r">R</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=s">S</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=t">T</a></td>
              </tr>
          <tr>
		    <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=u">U</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=v">V</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=w">W</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=x">X</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=y">Y</a></td>
		      </tr>
          <tr>
			<td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=z">Z</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=æ">Æ</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=ø">Ø</a></td>
            <td width="20"><a href="/polsys/storting/norskepolitikere_g/?bokstav=å">Å</a></td>
		</tr>
</tbody>
</table>


</div>

<div id="main" class="superwide">
    <c:if test="${no}"> <h1>Finn representant 1814-d.d.</h1></c:if>
     <c:if test="${en}"><h1>This page is not translated to English </h1>
    <h3>This page is not translated to English</h3>
     <p>----------------------------------------------</p>
    </c:if>


<!-- I øverste tabell legger en inn uttaket....-->

<FORM ACTION="/polsys/storting/norskepolitikere_g/" METHOD=GET>
<TABLE class="text">
<tbody>
	<TR>
		<TD>
			<h4>Etternavn:</h4>
			<input type=text name="navn" value = ""size=22>
		</TD>
		<TD>
			<h4>Periode:</h4>
					<input type="checkbox" name="Aar" value="1814" checked>
					<font size=1>1814-1903</font>
					<input type="checkbox" name="Aar" value="1905" checked>
					<font size="1">1903-1945</font>
					<input type="checkbox" name="Aar" value="1945" checked>
					<font size="1">1945-d.d.</font>
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
</FORM>

<!-- Her er det slutt på uttaket...-->

  <br><br>
   <c:if test="${param.navn !=null}">
        <p>Du søkte på: <c:if test="${param.navn !=null && param.navn!='' }"> "${param.navn}" </c:if> </p>

  </c:if>


 <c:if test="${fn:length(norskepolitikere_1814) > 0}">
 <h3>1814-1903:</h3>
  <c:forEach items="${norskepolitikere_1814}" var="norskepolitikere_1814" >
      <UL>
         <li><A HREF="/polsys/politikerbiografi/?person_id=${norskepolitikere_1814.personId}">
        ${norskepolitikere_1814.etterNavn}, ${norskepolitikere_1814.forNavn} (${norskepolitikere_1814.faar})</A>
         </li>
      </ul>
   </c:forEach>
</c:if>


<!-- 1905 - 1945-->

 <c:if test="${fn:length(norskepolitikere_1905) > 0}">
 <h3>1903-1945:</h3>
  <c:forEach items="${norskepolitikere_1905}" var="norskepolitikere_1905" >
      <UL>
         <li>
        ${norskepolitikere_1905.etterNavn}, ${norskepolitikere_1905.forNavn} (${norskepolitikere_1905.faar})
         </li>
      </ul>
   </c:forEach>
</c:if>


<!-- 1945 --->

 <c:if test="${fn:length(norskepolitikere_1945) > 0}">
 <h3>1945-d.d.:</h3>
  <c:forEach items="${norskepolitikere_1945}" var="norskepolitikere_1945" >
      <UL>
         <li>
        ${norskepolitikere_1945.etterNavn}, ${norskepolitikere_1945.forNavn} (${norskepolitikere_1945.faar})
         </li>
      </ul>
   </c:forEach>
</c:if>



 <br><br>
    <c:choose>
         <c:when test="${param.bokstav !=null}">
 <table class="zebra, check">

           <thead>
               <tr>
               </tr>
           </thead>
   <tbody>
        <c:forEach items="${norskepolitikere}" var="norskepolitikere" >
        <c:if test="${norskepolitikere.personId !='' || norskepolitikere.ssPersonId !='' || norskepolitikere.stPersonId !='' }">
       <tr>
           <th>
               <c:choose>
               <c:when test="${norskepolitikere.initialer == null}">
                <a href="/polsys/politikerbiografi/?person_id=${norskepolitikere.personId}" style="color:#3B5B8B "> ${norskepolitikere.etterNavn},${norskepolitikere.forNavn}</a>(${norskepolitikere.faar})
                </c:when>
                <c:otherwise>
                <a href="##" onClick="window.open('http://www.stortinget.no/no/Representanter-og-komiteer/Representantene/Representantfordeling/Representant/?perid=${norskepolitikere.initialer}', '1945', 'resizable=yes,scrollbars=yes,width=800,height=700,');return false;" style="color:#3B5B8B ">${norskepolitikere.etterNavn},${norskepolitikere.forNavn}</a>(${norskepolitikere.faar})
                </c:otherwise>
               </c:choose>

           <!-- <a href="/polsys/storting/norskepolitikere/">parti</a>, <a href="/polsys/storting/norskepolitikere/">stortingsrepresentant</a>, <a href="/polsys/storting/norskepolitikere/">statsråd</a>, <a href="/polsys/storting/norskepolitikere/">statssekretær</a>
               <br><br>
             -->

           </th>

       </tr>
            </c:if>
       </c:forEach>
   </tbody>
   </table>
 </c:when>
</c:choose>


 <!--

     <c:choose>
          <c:when test="${param.bokstav !=null}">
  <table class="zebra, check">
			<caption>Personindeks</caption>
			<thead>
				<tr>
					<th>Etternavn</th>
					<th>Fornavn</th>
					<th>Fødselsår</th>
					<th width="75">Statsråd</th>
					<th width="75">Stats- sekretær</th>
					<th width="75">Stortings- representant</th>
				</tr>
			</thead>
	<tbody>
         <c:forEach items="${norskepolitikere}" var="norskepolitikere" >
		 <c:if test="${norskepolitikere.personId !='' || norskepolitikere.ssPersonId !='' || norskepolitikere.stPersonId !='' }">
		<tr>
			<th>
			<a href="##" onClick="window.open('http://www.stortinget.no/no/Representanter-og-komiteer/Representantene/Representantfordeling/Representant/?perid=#initialer#', '3', 'resizable=yes,scrollbars=yes,width=800,height=700,');return false;">${norskepolitikere.etterNavn}</A>
			</th>
			<th>${norskepolitikere.forNavn}</th>
			<td>${norskepolitikere.faar}</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
             </c:if>
		</c:forEach>
</tbody>
</table>
  </c:when>
 </c:choose>

  -->

 </div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp"/>
<%-- --------------------------------------------------------------------- --%>