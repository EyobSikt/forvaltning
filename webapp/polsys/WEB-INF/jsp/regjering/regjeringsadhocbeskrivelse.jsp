<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 03.nov.2010
  Time: 08:18:53
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
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>

<div id="sidebar">

<c:if test="${no}">
	<c:if test="${param.reg_adhoc == '8'}">
			<h3>Generelt</h3>
			<p>Regjeringen rakk aldri å tre sammen før den ble avsatt av de tyske okkupasjons- myndighetene.</p>
		</c:if>
		<c:if test="${param.reg_adhoc == '9'}">
			<h3>Generelt</h3>
			<p>Administrasjonsrådet ble oppnevnt av Høyesterett i samråd med okkupasjonsmyndighetene, og var partipolitisk nøytralt. Ingolf Elster Christensen fungerte som leder av rådet.</p>
		</c:if>
		<c:if test="${param.reg_adhoc == '12'}">
			<h3>Generelt</h3>
			<p>Da deler av eksilregjeringen i London vendte tilbake og tok sete fra og med 14.05.1945, gikk rådmennene over til å bli de hjemvendte statsrådenes rådgivere.</p>
		</c:if>
		<c:if test="${param.reg_adhoc == '10'}">
			<h3>Generelt</h3>
			<p>Regjeringen kom i stand som følge av at riksrådsforhandlingene brøt sammen. Sandberg og Johannessen var de eneste regjeringsmedlemmene som ikke var NS-medlemmer, mens Fretheim var partiuavhengig frem til mai 1941.
			 F.o.m. 25.09.1941 ble de konstituerte statsrådene titulert 'konstituert minister.' Kollegiet hadde ingen norsk leder.</p>
		</c:if>	
	</c:if>
	<c:if test="${en}">
		<c:if test="${param.reg_adhoc == '8'}">
			<h3>Note</h3>
			<p>The German occupation authorities forced the government to resign before it gathered.</p>
		</c:if>
		<c:if test="${param.reg_adhoc == '9'}">
			<h3>Note</h3>
			<p>The Council was appointed by the Norwegian Supreme Court in accordance with the German occupation authorities. The Council was led by Ingolf Elster Christensen.</p>
		</c:if>
		<c:if test="${param.reg_adhoc == '12'}">
			<h3>Note</h3>
			<p>When the Norwegian London Government returned, these Councillors became the returning ministers' advisors.</p>
		</c:if>
		<c:if test="${param.reg_adhoc == '10'}">
			<h3>Note</h3>
			<p>Sandberg and Johannessen were the two only persons in the Commissariat who were never a member of the National Unity Party. Fretheim was independent until May 1941, though.
			 From 25.09.1941, and onwards, the members of the Commissariat were titled acting Ministers. The Commissariat had no Norwegian leader.</p>
		</c:if>		
	</c:if>        

</div>

<div id="main" class="superwide">
	<div id="main" class="superwide">
		<div class="breadcrumbs">
			<c:if test="${no}">
				Du er her:
				<a href="<p:url value="/"/>">PolSys</a>
				><a href="<p:url value="/regjering/" />">Regjering</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsadhoc/" />">Regjeringsadhoc</a>
				> Regjeringsadhoc beskrivelse
			</c:if>
			<c:if test="${en}">
				You are here:
				<a href="<p:url value="/"/>">PolSys</a>
				><a href="<p:url value="/regjering/" />">Government</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/regjeringsadhoc/" />">Government ad hoc</a>
				> Government ad hoc description
			</c:if>
		</div>
		<div>
			<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringsadhocbeskrivelse/?reg_adhoc=${param.reg_adhoc}" />">View this page in English.</a></c:if>
			<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringsadhocbeskrivelse/?reg_adhoc=${param.reg_adhoc}" />">View this page in Norwegian.</a></c:if>
		</div>
 <c:forEach items="${regjeringsadhocbeskrivelse}" var="regjeringsadhocbeskrivelse" >
   <h2> ${regjeringsadhocbeskrivelse.regjeringsnavn_NO}

     (${regjeringsadhocbeskrivelse.start}-${regjeringsadhocbeskrivelse.slutt})
   </h2>
 </c:forEach>


<c:set var="forrige" value="">  </c:set>
 <c:forEach items="${statsraadadhocbeskrivelse}" var="statsraadadhocbeskrivelse" >
	<c:if test="${no}">
        <c:if test="${statsraadadhocbeskrivelse.uttakskode=='0'}">
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_NO=='Statsminister' && statsraadadhocbeskrivelse.stilling_avvik_NO!=forrige}"><h3>Statsminister</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_NO}"></c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_NO=='Statsråd'  && statsraadadhocbeskrivelse.stilling_avvik_NO!=forrige }"><h3>Statsråder</h3> <c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_NO}"></c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_NO=='Tilforordnet statsråd' && statsraadadhocbeskrivelse.stilling_avvik_NO!=forrige}"><h3>Tilforordnede statsråder</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_NO}">  </c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_NO=='Tilforordnet statsråd i Christiania' &&  statsraadadhocbeskrivelse.stilling_avvik_NO!=forrige}"><h3>Tilforordnede statsråder i Christiania</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_NO}"></c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_NO=='Tilforordnet statsråd i Kristiania' && statsraadadhocbeskrivelse.stilling_avvik_NO!=forrige}"><h3>Tilforordnede statsråder i Kristiania</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_NO}"></c:set></c:if>

            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_NO=='Tilforordnet statsråd i Christiania' || statsraadadhocbeskrivelse.stilling_avvik_NO=='Tilforordnet statsråd i Kristiania'}">${statsraadadhocbeskrivelse.eintaltekst_NO}:</c:if>${statsraadadhocbeskrivelse.fornavn} ${statsraadadhocbeskrivelse.etternavn} (${statsraadadhocbeskrivelse.foedt}-${statsraadadhocbeskrivelse.doedsaar})<c:if test="${statsraadadhocbeskrivelse.eksternkommentar_NO!=''}">.</c:if><em>${statsraadadhocbeskrivelse.eksternkommentar_NO}</em><br>
            			
			<p></p>
		</c:if>
	</c:if>

	<c:if test="${en}">
          <c:if test="${statsraadadhocbeskrivelse.uttakskode=='0'}">
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_ENG=='Prime Minister' && statsraadadhocbeskrivelse.stilling_avvik_ENG!=forrige}"><h3>Prime Minister</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_ENG}"></c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_ENG=='Councillor of State' && statsraadadhocbeskrivelse.stilling_avvik_ENG!=forrige}"><h3>Councillors of State</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_ENG}"></c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_ENG=='Acting Councillor of State' && statsraadadhocbeskrivelse.stilling_avvik_ENG!=forrige}"><h3>Acting Councillors of State</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_ENG}"></c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_ENG=='Acting Councillor of State in Christiania' && statsraadadhocbeskrivelse.stilling_avvik_ENG!=forrige}"><h3>Acting Councillors of State in Christiania</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_ENG}"></c:set></c:if>
            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_ENG=='Acting Councillor of State in Kristiania' && statsraadadhocbeskrivelse.stilling_avvik_ENG!=forrige}"><h3>Acting Councillors of State in Kristiania</h3><c:set var="forrige" value="${statsraadadhocbeskrivelse.stilling_avvik_ENG}"></c:set></c:if>

            <c:if test="${statsraadadhocbeskrivelse.stilling_avvik_ENG=='Acting Councillor of State in Christiania' || statsraadadhocbeskrivelse.stilling_avvik_ENG=='Acting Councillor of State in Kristiania'}">${statsraadadhocbeskrivelse.eintaltekst_ENG}:</c:if>${statsraadadhocbeskrivelse.fornavn} ${statsraadadhocbeskrivelse.etternavn} (${statsraadadhocbeskrivelse.foedt}-${statsraadadhocbeskrivelse.doedsaar})<c:if test="${statsraadadhocbeskrivelse.eksternkommentar_ENG!=''}">.</c:if><em>${statsraadadhocbeskrivelse.eksternkommentar_ENG}</em><br>

			<p></p>
		</c:if>
		
	</c:if>
</c:forEach>


<c:if test="${fn:length(statsraadadhocbeskrivelse) >= 1}">

<p></p>
<table class="zebra, text">
	    <c:forEach items="${statsraadadhocbeskrivelse}" var="statsraadadhocbeskrivelse" begin="0" end="0">
        <c:if test="${statsraadadhocbeskrivelse.uttakskode=='1' }">
			<c:if test="${no}">

		<thead>
			<tr>
				<th>Stilling</th>
			    <th>Virkeperiode</th>
			</tr>
		</thead>
		 </c:if>
		<c:if test="${en}">
		<caption>Norwegian ad hoc Governments</caption>
		<thead>
			<tr>
				<th>Position</th>
				<th>In office</th>
			</tr>
		</thead>
    </c:if>
       </c:if>
    </c:forEach>
	<tbody>
		<c:forEach items="${statsraadadhocbeskrivelse}" var="statsraadadhocbeskrivelse" >

          <c:if test="${no}">
              <c:if test="${statsraadadhocbeskrivelse.uttakskode=='1'}">
			    <tr>
			       <td>
				     <h3>${statsraadadhocbeskrivelse.eintaltekst_NO}</h3>
			        </td>
		        </tr>
                 <tr>
			         <td><c:if test="${statsraadadhocbeskrivelse.eintaltekst_NO==null}"><h3>${statsraadadhocbeskrivelse.stilling_avvik_NO}</h3></c:if><c:if test="${statsraadadhocbeskrivelse.eintaltekst_NO!=null}">${statsraadadhocbeskrivelse.stilling_avvik_NO}</c:if> ${statsraadadhocbeskrivelse.fornavn} ${statsraadadhocbeskrivelse.etternavn} (${statsraadadhocbeskrivelse.foedt}-${statsraadadhocbeskrivelse.doedsaar}) <br> <em>${statsraadadhocbeskrivelse.eksternkommentar_NO}</em> <c:if test="${statsraadadhocbeskrivelse.eksternkommentar_NO!=''}"> <p></p> </c:if></td>
		        	 <td>${statsraadadhocbeskrivelse.start}-${statsraadadhocbeskrivelse.slutt}</td>
	    	    </tr>
           </c:if>     
        </c:if>

        <c:if test="${en}">
		    <c:if test="${statsraadadhocbeskrivelse.uttakskode=='1'}">
		        <tr>
		            <td>
		                <h3>${statsraadadhocbeskrivelse.eintaltekst_ENG}</h3>
			        </td>
		        </tr>
                <tr>
			         <td><c:if test="${statsraadadhocbeskrivelse.eintaltekst_ENG==''}"><h3>${statsraadadhocbeskrivelse.eintaltekst_ENG}</h3></c:if><c:if test="${statsraadadhocbeskrivelse.eintaltekst_ENG==''}">${statsraadadhocbeskrivelse.stilling_avvik_ENG}</c:if> ${statsraadadhocbeskrivelse.fornavn} ${statsraadadhocbeskrivelse.etternavn} (${statsraadadhocbeskrivelse.foedt}-${statsraadadhocbeskrivelse.doedsaar}) <br> <em>${statsraadadhocbeskrivelse.eksternkommentar_ENG}</em> <c:if test="${statsraadadhocbeskrivelse.eksternkommentar_ENG!=''}"> <p></p> </c:if></td>
		         	 <td>${statsraadadhocbeskrivelse.start}-${statsraadadhocbeskrivelse.slutt}</td>
	        	 </tr>
            </c:if>
        </c:if>

	</c:forEach>
    </tbody>
</table>
</c:if>



</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>