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
<h3>LASTNED DATASET</h3>

Klikk <a href="/polsys/storting/lastnedfil/">her</a> å laste ned data fil.



</div>
 

<div id="main" class="superwide">
<div>
    <c:if test="${no}">
Du er her:
<a href="<p:url value="/" />">PolSys</a>        
> <a href="<p:url value="/storting/"/> ">Storting</a>
> Sammensetning

    </c:if>
</div>

   <c:if test="${no}"> <h1>Stortingets sammensetning 1814-d.d.</h1></c:if>
    <c:if test="${en}"><h1>The Norwegian Storting (parliament) 1814-present </h1>
    <h3>This page is not translated to English</h3>
     <p>----------------------------------------------</p>   
    </c:if>

    <p>
	Velg valgår for ønsket valgperiode. Klikk deretter på partinavn for oversikt over partiets utvikling over tid, eller gå videre til navnelister og biografier via totalene i høyre kolonne av tabellen.
    </p>


<table class="text">
<tbody>
<tr>
<td valign="top">
    <table class="text" >
    <caption>Representanter fordelt på periode og parti</caption>
      <thead>
         <tr>
		<th width="65px">Valgår</th>
		     <c:forEach items="${alleSammensetning}" var="Sammensetning" begin="0" end="0">
            <c:choose>
          <c:when test="${Sammensetning.periodeKode >= 1}">
		<th>Stortingets sammensetning ${Sammensetning.valgAar}</th>
       </c:when>
          <c:otherwise>
		<th>Forsamlinger 1814</th>
            </c:otherwise>
            </c:choose>
		</c:forEach>
	</tr>
      </thead>
        <tbody>
          <tr>
              <td width="62px" rowspan="3" valign="top">
                 <c:forEach items="${valgaar}" var="aar" >
                  <a href= "<p:url value="/storting/sammensetning/?periode=${aar.periodeKode}"/> "> ${aar.valgAar}</a>
                 </c:forEach>
              </td>
              <td valign="top">
              <table>

          <c:forEach items="${alleSammensetning}" var="Sammensetning" begin="0" end="0">
           <c:choose>
             <c:when test="${Sammensetning.periodeKode >= 0}">
		       <thead>
                <tr>
			    	<th width="280">Parti </th>
					<th>Antall</th>
			    </tr>
              </thead>
            </c:when>

            </c:choose>
		  </c:forEach>
        <tfoot>
             <c:forEach items="${alleSammensetning}" var="Sammensetning" begin="0" end="0">
                <c:choose>
                  <c:when test="${Sammensetning.periodeKode > 89}">
            <td>
            Antall representanter totalt
         </td>
         <td>
          <c:forEach items="${alleSammensetning}" var="Sammensetning" >
         <c:set var="ageTotal" value="${ageTotal + Sammensetning.antall}" />
           <c:set var="periodekode" value="${Sammensetning.periodeKode}" />
           </c:forEach>
         <a href="<p:url value="/storting/representant/?periode=${periodekode}"/> "> ${ageTotal} </a>
         </td>
             </c:when>
             <c:when test="${Sammensetning.periodeKode < 90 && Sammensetning.periodeKode > 52}">
              <td width="240">
										Antall representanter totalt
									</td>
             <td>
          <c:forEach items="${alleSammensetning}" var="Sammensetning" >
         <c:set var="ageTotal" value="${ageTotal + Sammensetning.antall}" />
              <c:set var="periodekode" value="${Sammensetning.periodeKode}" />
           </c:forEach>
          <a href="<p:url value="/storting/representant/?periode=${periodekode}"/> "> ${ageTotal}  </a>
         </td>  
             </c:when>
              <c:when test="${Sammensetning.periodeKode < 53 && Sammensetning.periodeKode > 0}">
              <td width="240">
				Antall representanter totalt
				</td>
             <td>
          <c:forEach items="${alleSammensetning}" var="Sammensetning" >
         <c:set var="ageTotal" value="${ageTotal + Sammensetning.antall}" />
          <c:set var="periodekode" value="${Sammensetning.periodeKode}" />
           </c:forEach>
         <a href="<p:url value="/storting/representant/?periode=${periodekode}"/> "> ${ageTotal} </a>
         </td>
             </c:when>       
              <c:when test="${Sammensetning.periodeKode < 1}">
                 <td width="115">
				Se begge listene
			</td>
                  <td>
                      112+78
                  </td>
                 </c:when>
                </c:choose>
          </c:forEach>
         </tfoot>
           <tbody>
              <c:forEach items="${alleSammensetning}" var="Sammensetning" >
                <c:choose>
                  <c:when test="${Sammensetning.periodeKode > 89}">
                    <tr>
                       <td>
                         <a href="<p:url value="/storting/sammensetning/?periode=${Sammensetning.periodeKode}&parti=${Sammensetning.partiKode}"/>"> ${Sammensetning.partiNavn}</a>
                       </td>
                       <td>
                         <c:choose>
                           <c:when test="${Sammensetning.partiKode==21}">
                           <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=A"/>"> ${Sammensetning.antall} </a>
                               <input type="hidden" name="parti_kortnavn" value="A">
                           </c:when>
                           <c:when test="${Sammensetning.partiKode==41 && Sammensetning.periodeKode < 106}">
                           <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=B"/>"> ${Sammensetning.antall} </a>
                           </c:when>
                           <c:when test="${Sammensetning.partiKode==14 && Sammensetning.periodeKode < 118}">
                           <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=SF"/>"> ${Sammensetning.antall} </a>
                           </c:when>
                           <c:when test="${Sammensetning.partiKode==61 && Sammensetning.periodeKode == 118}">
                           <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=DNF"/>"> ${Sammensetning.antall} </a>
                           </c:when>
                           <c:when test="${Sammensetning.partiKode==81 && Sammensetning.periodeKode == 118}">
                           <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=ALP"/>"> ${Sammensetning.antall} </a>
                           </c:when>
                           <c:when test="${Sammensetning.partiKode==24 && Sammensetning.periodeKode == 142}">
                           <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=TF"/>"> ${Sammensetning.antall} </a>
                           </c:when> 
                           <c:otherwise>
                            <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=${Sammensetning.partiKortnavn}"/>"> ${Sammensetning.antall} </a>
                               <input type="hidden" name="parti_kortnavn" value="${Sammensetning.partiKortnavn}">
                            </c:otherwise>
                        </c:choose>
                        </td>
                      </tr>
                     </c:when>
                       <c:when test="${Sammensetning.periodeKode < 90 && Sammensetning.periodeKode > 52}">
                        <tr>
                          <td>
                         <a href="<p:url value="/storting/sammensetning/?periode=${Sammensetning.periodeKode}&parti=${Sammensetning.partiKode}"/>"> ${Sammensetning.partiNavn}</a>
                       </td>
                        <td>
                        <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti=${Sammensetning.partiKortnavn}"/>"> ${Sammensetning.antall} </a>
                        </td>
                         </tr>
                         </c:when>
                    <c:when test="${Sammensetning.periodeKode < 53 && Sammensetning.periodeKode > 0}">
                       <tr>
                          <td>
                         <a href="<p:url value="/storting/sammensetning/?periode=${Sammensetning.periodeKode}&parti=${Sammensetning.partiKode}"/>"> ${Sammensetning.partiNavn}</a>
                       </td>
                        <td>
                        ${Sammensetning.antall}
                        </td>
                           </tr>
                         </c:when>
                     <c:when test="${Sammensetning.periodeKode < 1}">
                       <h4>Riksforsamlingen:</h4>
							<tr>
								<td width="115">
									${Sammensetning.partiNavn}
								</td>
								<td width="50">
									112
								</td>
							</tr>
							<tr>
								<td colspan=2>
									<h4>Overordentlig Storting:</h4>
								</td>
							</tr>
							<tr>
								<td width="115">
									${Sammensetning.partiNavn}
								</td>
								<td width="50">
									78
								</td>
							</tr>
                         </c:when>

                     <c:otherwise>
                     </c:otherwise>
                      </c:choose>


                    </c:forEach>
                 </tbody>
              </table>
             </td>
             </tr>

</tbody>
    </table>

      </td>
       <c:choose>
          <c:when test="${param.parti !=null}">
             <td valign="top">
                 <table class="text">
		<caption>
			<c:forEach items="${partitidsserier}" var="partitidsserier" begin="0" end="0">
				Tidsserie for ${partitidsserier.partiNavn}
			 </c:forEach>
		</caption>
	<thead>
		<tr>
			<th>Valgår</th>
			<th>Antall<br> representanter</th>
			<th>Av totalt</th>
		</tr>
	</thead>
	<tbody>
    <c:forEach items="${partitidsserier}" var="partitidsserier" >
			<tr>
				<td> ${partitidsserier.valgAar}</td>
				<td>${partitidsserier.antall}</td>
				<td>${partitidsserier.antallTotalt}</td>
			</tr>
         </c:forEach>
	</tbody>
</table>
</td>
  </c:when>

       </c:choose>

   </tr>
</tbody>
    </table>

</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>