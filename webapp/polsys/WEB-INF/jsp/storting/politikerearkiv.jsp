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

<c:if test="${no}">
<h3>LASTNED DATASET</h3><br>
<div>Klikk <a href="<p:url value="/person/norskepolitikere/"/> "> her </a> å laste ned data om norske poliitiker.</div><br>
<div>Klikk <a href="<p:url value="/regjering/lastnedstatsraad/"/> "> her </a> å laste ned data om norske statsråder.</div><br>
<div>Klikk <a href="<p:url value="/regjering/lastnedstatssekretar/"/> "> her </a> å laste ned data om norske poliitiker.</div>
</c:if>
<c:if test="${en}">
 <h3>DOWNLOAD DATA SETS</h3><br>
<div>Click <a href="<p:url value="/person/norskepolitikere/"/> "> here </a> to download data about norwegian politicians.</div><br>
<div>Click <a href="<p:url value="/regjering/lastnedstatsraad/"/> "> here </a> to download data about norwegian ministers.</div><br>
<div>Click <a href="<p:url value="/regjering/lastnedstatssekretar/"/> "> here </a> to download data about norwegian state secretaries. </div>
</c:if>



</div>
 

<div id="main" class="superwide">
<div>
    <c:if test="${no}">
Du er her:
<a href="<p:url value="/" />">PolSys</a>        
> <a href="<p:url value="/storting/"/> ">Storting</a>
> Sammensetning
    </c:if>
<c:if test="${en}">
    You are here:
    <a href="<p:url value="/" />">PolSys</a>
    > <a href="<p:url value="/storting/"/> ">Parliament</a>
    > Composition
</c:if>
</div>
    <div>
        <c:if test="${no}"><a href="<m:url value="/en/storting/sammensetning/" />">View this page in English.</a></c:if>
        <c:if test="${en}"><a href="<m:url value="/storting/sammensetning/" />">View this page in Norwegian.</a></c:if>
    </div>

   <c:if test="${no}"> <h1>Stortingets sammensetning 1814-d.d.</h1></c:if>
    <c:if test="${en}"><h1>The Norwegian Storting (parliament) 1814-present </h1>
    </c:if>

    <p>
<c:if test="${no}">	Velg valgår for ønsket valgperiode. Klikk deretter på partinavn for oversikt over partiets utvikling over tid, eller gå videre til navnelister og biografier via totalene i høyre kolonne av tabellen.</c:if>
<c:if test="${en}">	Select year for the desired term. Then click on the party name for an overview of the party's development over time, or go to names lists and biographies via the totals in the right-hand column of the table.</c:if>
    </p>


<table class="text">
<tbody>
<tr>
<td valign="top">
    <table class="text" >
    <caption><c:if test="${no}">Representanter fordelt på periode og parti</c:if><c:if test="${en}">Representatives by period and party</c:if></caption>
      <thead>
         <tr>
		<th width="65px"><c:if test="${no}">Valgår</c:if><c:if test="${en}">Election year</c:if></th>
		     <c:forEach items="${alleSammensetning}" var="Sammensetning" begin="0" end="0">
            <c:choose>
          <c:when test="${Sammensetning.periodeKode >= 1}">
		<th><c:if test="${no}">Stortingets sammensetning</c:if><c:if test="${en}">Parliament composition</c:if> ${Sammensetning.valgAar}</th>
       </c:when>
          <c:otherwise>
		<th><c:if test="${no}">Forsamlinger</c:if><c:if test="${en}">Assembly</c:if> 1814</th>
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
			    	<th width="280"> <c:if test="${no}">Parti</c:if><c:if test="${en}">Party</c:if> </th>
					<th><c:if test="${no}">Antall</c:if><c:if test="${en}">Number</c:if></th>
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
                <c:if test="${no}">Antall representanter totalt</c:if><c:if test="${en}">Number of representatives in total</c:if>
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
              <td width="240">	<c:if test="${no}">Antall representanter totalt</c:if><c:if test="${en}">Number of representatives in total</c:if> 	</td>
             <td>
          <c:forEach items="${alleSammensetning}" var="Sammensetning" >
         <c:set var="ageTotal" value="${ageTotal + Sammensetning.antall}" />
              <c:set var="periodekode" value="${Sammensetning.periodeKode}" />
           </c:forEach>
          <a href="<p:url value="/storting/representant/?periode=${periodekode}"/> "> ${ageTotal}  </a>
         </td>  
             </c:when>
              <c:when test="${Sammensetning.periodeKode < 53 && Sammensetning.periodeKode > 0}">
              <td width="240"> <c:if test="${no}">Antall representanter totalt</c:if><c:if test="${en}">Number of representatives in total</c:if> </td>
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
                     <c:if test="${no}"> Se begge listene </c:if><c:if test="${en}"> See both lists </c:if>
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
                           <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=${Sammensetning.partiKode}"/>"> ${Sammensetning.antall} </a>
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
                            <a href="<p:url value="/storting/representant/?periode=${Sammensetning.periodeKode}&parti_kortnavn=${Sammensetning.partiKode}"/>"> ${Sammensetning.antall} </a>
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
                       <h4><c:if test="${no}">Riksforsamlingen:</c:if><c:if test="${en}">The National Assembly:</c:if></h4>
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
									<h4><c:if test="${no}">Overordentlig Storting:</c:if><c:if test="${en}">Extraordinary Parliament:</c:if></h4>
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
                <c:if test="${no}">Tidsserie for</c:if><c:if test="${en}">Time series for</c:if> ${partitidsserier.partiNavn}
			 </c:forEach>
		</caption>
	<thead>
		<tr>
			<th><c:if test="${no}">Valgår</c:if><c:if test="${en}">Election year</c:if></th>
			<th><c:if test="${no}">Antall<br> representanter</c:if><c:if test="${en}">Number<br> representatives</c:if></th>
			<th><c:if test="${no}">Av totalt</c:if><c:if test="${en}">Of total</c:if></th>
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