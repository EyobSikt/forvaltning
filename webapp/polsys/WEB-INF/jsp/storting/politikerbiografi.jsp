<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 25.nov.2010
  Time: 09:42:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Stortingdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Parliament Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Stortingsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>

<div id="main" class="wide">

    <h1>Biografier 1905-1945</h1>
 <!--Representantens navn -->
  <c:forEach items="${personinfo}" var="personinfo">
      <h1>${personinfo.forNavn} ${personinfo.etterNavn} </h1><br>
       <em>Født:</em>  ${personinfo.fodt}<br>
      <em>Død:</em>  ${personinfo.dod}
   </c:forEach>

    <!--Representantens stortingsperioder -->
    <table class="zebra, text">
      <c:if test="${fn:length(personstortinginfo) > 0}">
      <caption>Stortingsaktivitet</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Rep. nr.</th>
		<th>Valgkrets</th>
		<th>Stilling</th>
		<th>Parti</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${personstortinginfo}" var="personstortinginfo">
 <tr>
     <c:if test="${personstortinginfo.periode < 32}">
      <c:if test="${personstortinginfo.rep_nr >0}">
         <td>${personstortinginfo.fleirtaltekst}</td> <td>${personstortinginfo.rep_nr}. representant</td> <td>${personstortinginfo.valkrinsnavn}</td> <td>${personstortinginfo.stilling}</td><td></td>
     </c:if>
     <c:if test="${personstortinginfo.sup_nr >0}">
         <td>${personstortinginfo.fleirtaltekst}</td> <td>${personstortinginfo.sup_nr}. suppleant</td> <td>${personstortinginfo.valkrinsnavn}</td> <td>${personstortinginfo.stilling}</td><td></td>
     </c:if>
    </c:if>
     <c:if test="${personstortinginfo.periode > 29}">
      <c:if test="${personstortinginfo.rep_nr >0}">
         <td>${personstortinginfo.fleirtaltekst}</td> <td>${personstortinginfo.rep_nr}. representant</td> <td>${personstortinginfo.valkrinsnavn}</td> <td>${personstortinginfo.stilling}</td><td>${personstortinginfo.partiNavn}</td>
     </c:if>
     <c:if test="${personstortinginfo.sup_nr >0}">
         <td>${personstortinginfo.fleirtaltekst}</td> <td>${personstortinginfo.sup_nr}. suppleant</td> <td>${personstortinginfo.valkrinsnavn}</td> <td>${personstortinginfo.stilling}</td><td>${personstortinginfo.partiNavn}</td>
     </c:if>
    </c:if>
 </tr>
 </c:forEach>

</tbody>
	</c:if>
</table>
     
 <!--Representantens medlemskap i presidentskapet -->
    <table class="text">
	<c:if test="${fn:length(personstortingspresident) > 0}">
		<caption>Medlemskap i presidentskapet</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Embete</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${personstortingspresident}" var="personstortingspresident">
	<tr>
        <c:choose>
         <c:when test="${personstortingspresident.tilaar ==-1}">
          <td>${personstortingspresident.fraaar}-</td> <td>${personstortingspresident.stilling} ${personstortingspresident.institusjon}</td>
         </c:when>
          <c:otherwise>
        <td>${personstortingspresident.fraaar}-${personstortingspresident.tilaar}</td> <td>${personstortingspresident.stilling} ${personstortingspresident.institusjon}</td>
          </c:otherwise>
        </c:choose>
		
	</tr>
	</c:forEach>
</tbody>
	</c:if>
</table>

<!--Representantens komitemedlemskap -->

<c:if test="${fn:length(personstortingskomite) > 0}">
		<p></p>
		<h3>Medlemskap i stortingskomiteer:</h3>
</c:if>

<c:set var="forrige" value="">  </c:set>
<table class="text">
<c:if test="${fn:length(personstortingsfagkomite) > 0}">
		<caption>Fagkomiteer</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Komite</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${personstortingsfagkomite}" var="personstortingsfagkomite">
		<tr>
			<td valign="top">
				<c:choose>
                 <c:when test="${personstortingsfagkomite.fleirtaltekst == forrige}">
                  </c:when>
                 <c:otherwise>
                  ${personstortingsfagkomite.fleirtaltekst}
                 </c:otherwise>
				</c:choose>
             <c:set var="forrige" value="${personstortingsfagkomite.fleirtaltekst}">  </c:set>
			</td>
			<td>
			  <c:if test="${personstortingsfagkomite.status < 1}">
               Medlem i ${personstortingsfagkomite.eintaltekst}
			  </c:if>
               <c:if test="${personstortingsfagkomite.status == 1}">
               Formann i ${personstortingsfagkomite.eintaltekst}
			  </c:if>
               <c:if test="${personstortingsfagkomite.status == 2}">
               Sekretær i ${personstortingsfagkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsfagkomite.status == 3}">
               Nestformann i ${personstortingsfagkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsfagkomite.status == 4}">
               Ordfører i ${personstortingsfagkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsfagkomite.status == 5}">
               Varamedlem i ${personstortingsfagkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsfagkomite.status > 0}">
                <br><em>${personstortingsfagkomite.kommentar}</em><p></p>
			  </c:if>
			</td>
		</tr>
	</c:forEach>
</tbody>
</c:if>
</table>

<!-- spesialkomite-->
  <c:set var="forrige" value="">  </c:set>
<table class="text">
<c:if test="${fn:length(personstortingsspesialkomite) > 0}">
		<caption>Spesialkomiteer</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Komite</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${personstortingsspesialkomite}" var="personstortingsspesialkomite">
		<tr>
			<td valign="top">
				<c:choose>
                 <c:when test="${personstortingsspesialkomite == 'forrige'}">
                  </c:when>
                 <c:otherwise>
                  ${personstortingsspesialkomite.fleirtaltekst}
                 </c:otherwise>
				</c:choose>
             <c:set var="forrige" value="${personstortingsspesialkomite.fleirtaltekst}">  </c:set>
			</td>
			<td>
			  <c:if test="${personstortingsspesialkomite.status < 1}">
               Medlem i ${personstortingsspesialkomite.eintaltekst}
			  </c:if>
               <c:if test="${personstortingsspesialkomite.status == 1}">
               Formann i ${personstortingsspesialkomite.eintaltekst}
			  </c:if>
               <c:if test="${personstortingsspesialkomite.status == 2}">
               Sekretær i ${personstortingsspesialkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsspesialkomite.status == 3}">
               Nestformann i ${personstortingsspesialkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsspesialkomite.status == 4}">
               Ordfører i ${personstortingsspesialkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsspesialkomite.status == 5}">
               Varamedlem i ${personstortingsspesialkomite.eintaltekst}
			  </c:if>
              <c:if test="${personstortingsspesialkomite.status > 0}">
                <br><em>${personstortingsspesialkomite.kommentar}</em><p></p>
			  </c:if>
			</td>
		</tr>
	</c:forEach>
</tbody>
</c:if>
</table>


<!--Representantens medlemskap i delegasjoner -->    

 <c:if test="${fn:length(personstortingsdelegasjon) > 0}">
		<p></p>
		<h3>Medlemskap i delegasjoner:</h3>
</c:if>

<c:forEach items="${personstortingsfagkomite}" var="personstortingsfagkomite">
   <c:if test="${personstortingsfagkomite.fraaar < 0 && personstortingsfagkomite.tilaar < 1}">${personstortingsfagkomite.stilling} - ${personstortingsfagkomite.institusjon}.<br> </c:if>
   <c:if test="${personstortingsfagkomite.fraaar > 0 && personstortingsfagkomite.tilaar < 1}">${personstortingsfagkomite.stilling} - ${personstortingsfagkomite.institusjon}, ${personstortingsfagkomite.fraaar} .<br></c:if>
   <c:if test="${personstortingsfagkomite.fraaar > 0 && personstortingsfagkomite.tilaar > 0}">${personstortingsfagkomite.stilling} - ${personstortingsfagkomite.institusjon}, ${personstortingsfagkomite.fraaar}-${personstortingsfagkomite.tilaar}.<br> </c:if>
</c:forEach>


<!--Lederskap i gruppestyrer -->
    
 <table class="text">

     <c:if test="${fn:length(personstortingsfoerer) > 0}">
		<caption>Lederskap i stortingsgrupper</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Embete</th>
		<th>Parti</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${personstortingsfoerer}" var="personstortingsfoerer">
	<tr>
		 <c:if test="${personstortingsfoerer.fraaar > 0 && personstortingsfoerer.tilaar > 0}">
 			<td>${personstortingsfoerer.fraaar}-${personstortingsfoerer.tilaar}</td> <td>Parlamentarisk leder</td> <td>${personstortingsfoerer.institusjon}</td>
		 </c:if>
        <c:if test="${personstortingsfoerer.fraaar > 0 && personstortingsfoerer.tilaar  < 1}">
 			<td>${personstortingsfoerer.fraaar}</td> <td>Parlamentarisk leder</td> <td>${personstortingsfoerer.institusjon}</td>
		 </c:if>
        <c:if test="${personstortingsfoerer.fraaar < 1 && personstortingsfoerer.tilaar < 1}">
 			<td></td> <td>Parlamentarisk leder</td> <td>${personstortingsfoerer.institusjon}</td>
		 </c:if>
	</tr>
	</c:forEach>
</tbody>
	</c:if>
</table>

<!--Representantens regjeringsmedlemskap -->
  
   <c:set var="now" value="<%=new java.util.Date()%>" />
    <table class="zebra, text">
        <c:if test="${fn:length(statsraad) > 0}">
            <caption>Statsrådsaktivitet</caption>
    <thead>
        <tr>
            <th>Embete</th>
            <th>Departement</th>
            <th>Virkeperiode</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${statsraad}" var="statsraad">
    <tr>
       <c:choose>
        <c:when test="${statsraad.tilaar == '09-09-9999'}">
        <c:set var="slutt_dato" value="${now}" />
        </c:when>
       <c:otherwise><c:set var="slutt_dato" value="${statsraad.tilaar}" /></c:otherwise>
       </c:choose>

              <c:choose>
               <c:when test="${statsraad.stilling == '' &&  (statsraad.eintaltekst != 'Statsministerembetet' && statsraad.eintaltekst!= 'Statsministerens kontor' && statsraad.eintaltekst != 'Statsråd uten portefølje' && statsraad.eintaltekst != 'Statsrådsavdelingen i Christiania' && statsraad.eintaltekst!= 'Statsrådsavdelingen i Kristiania')}">
                <td>${statsraad.stilling}</td> <td>${statsraad.eintaltekst}</td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
               <c:when test="${statsraad.stilling == '' &&  (statsraad.eintaltekst == 'Statsministerembetet' )}">
                <td>Statsminister</td> <td></td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
                <c:when test="${statsraad.stilling == '' &&  (statsraad.eintaltekst == 'Statsministerens kontor' )}">
               <td>Statsminister</td> <td>${statsraad.eintaltekst}</td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
               <c:when test="${statsraad.stilling != '' &&  (statsraad.eintaltekst == 'Statsministerens kontor' )}">
                <td>${statsraad.stilling}</td> <td>${statsraad.eintaltekst}</td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
               <c:when test="${statsraad.stilling != '' &&  (statsraad.eintaltekst == 'Statsministerembetet')}">
                <td>${statsraad.stilling}</td> <td></td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
               <c:when test="${statsraad.stilling != '' &&  (  statsraad.eintaltekst =='Statsrådsavdelingen i Christiania' )}">
                <td>${statsraad.stilling}</td> <td></td><td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
               <c:when test="${statsraad.tilaar != '' &&  ( statsraad.eintaltekst == 'Statsrådsavdelingen i Kristiania')}">
                <td>${statsraad.stilling}</td> <td></td><td>${statsraad.fraaar} - ${statsraad.tilaar}</td>
               </c:when>
               <c:when test="${statsraad.stilling == '' &&  (statsraad.eintaltekst != 'Utenriksdepartementet' )}">
                <td>Utenriksminister</td> <td>${statsraad.eintaltekst}</td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
                 <c:when test="${statsraad.stilling == 'Utenriksminister' &&  (statsraad.eintaltekst == 'Utenriksdepartementet' )}">
                <td>${statsraad.stilling}</td> <td>${statsraad.eintaltekst}</td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
                <c:when test="${statsraad.stilling != 'Utenriksminister' &&  (statsraad.eintaltekst == 'Utenriksdepartementet' )}">
                <td>${statsraad.stilling}</td> <td>${statsraad.eintaltekst}</td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
               <c:when test="${statsraad.stilling == '' &&  (statsraad.eintaltekst == 'Statsråd uten portefølje' )}">
                <td>Statsråd uten portefølje</td> <td></td><td>${statsraad.fraaar} - ${statsraad.tilaar}</td>
               </c:when>
               <c:when test="${statsraad.stilling != '' &&  (statsraad.eintaltekst == 'Statsråd uten portefølje' )}">
                <td>${statsraad.stilling} uten portefølje</td><td></td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
               </c:when>
              <c:otherwise>
                <td>Statsråd</td> <td>${statsraad.eintaltekst}</td>   <td>${statsraad.fraaar} - ${slutt_dato}</td>
              </c:otherwise>
              </c:choose>

        </tr>
       </c:forEach>
    </tbody>
       </c:if>
    </table>

    <table class="zebra, text">
       <c:if test="${fn:length(sekretaer) > 0}">
           <c:if test="${no}">
                <caption>Statssekretærsaktivitet</caption>
            <thead>
                <tr>
                    <th>Embete</th>
                    <th>Departement</th>
                    <th>Virkeperiode</th>
                </tr>
            </thead>
           </c:if>
           <c:if test="${en}">
                <caption>State Secretary activity</caption>
            <thead>
                <tr>
                    <th>Position</th>
                    <th>Ministry</th>
                    <th>In office</th>
                </tr>
            </thead>
           </c:if>
        </cfif>
    <tbody>
      <c:forEach items="${sekretaer}" var="sekretaer">
        <tr>
           <c:choose>
        <c:when test="${sekretaer.tilaar == '09-09-9999'}">${now}</c:when>
       <c:otherwise>${sekretaer.tilaar}</c:otherwise>
       </c:choose>
            <c:if test="${no}">
              <c:choose>
               <c:when test="${sekretaer.stilling != ''}"> <td>${sekretaer.stilling}</td></c:when>
              <c:otherwise><td>Statssekretær</td></c:otherwise>
              </c:choose>
              <td>${sekretaer.eintaltekst}</td> <td>${sekretaer.fraaar} - ${sekretaer.tilaar}</td>
             </c:if>
            <c:if test="${en}">
            <c:choose>
               <c:when test="${sekretaer.stilling != ''}"> <td>${sekretaer.stilling}</td></c:when>
              <c:otherwise><td>State Secretary</td></c:otherwise>
              </c:choose>
              <td>${sekretaer.eintaltekst}</td> <td>${sekretaer.fraaar} - ${sekretaer.tilaar}</td>
            </c:if>
        </tr>
      </c:forEach>
    </tbody>
     </c:if>
    </table>

    <!--Her begynner de biografiske opplysningene -->

    <!--Representantens personalia -->
<cfoutput query="personlig">
	 <c:if test="${fn:length(personliginfo) > 0}">
		<p></p>
		<h3>Personalia:</h3>
	</c:if>
</cfoutput>

 <c:forEach items="${personliginfo}" var="personliginfo">
        Født ${personliginfo.fodt}, ${personliginfo.fodtSted}.<br>
        <c:choose>
         <c:when test="${personliginfo.far_forNavn == '' && personliginfo.mor_forNavn ==''}"> </c:when>
        <c:otherwise>
         <c:if test="${personliginfo.kjoen ==1}">
           Sønn
         </c:if>
         <c:if test="${personliginfo.kjoen ==2}">
            Datter
         </c:if>
           av
          <c:if test="${personliginfo.far_f_aar < 1 && personliginfo.mor_forNavn == ''}">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}
          </c:if>
          <c:if test="${personliginfo.far_f_aar < 1 && personliginfo.mor_forNavn != '' && personliginfo.mor_f_aar < 1 } ">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn} og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}
          </c:if>
            <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar < 1 && personliginfo.mor_forNavn != '' && personliginfo.mor_f_aar < 1 } ">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar < 1 && personliginfo.mor_f_aar > 0 && personliginfo.mor_f_aar < 1 } ">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-)
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar < 1 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar > 0 } ">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-${personliginfo.mor_d_aar})
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar > 0 && personliginfo.mor_f_aar < 1 } ">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-${personliginfo.far_d_aar}) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}.
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar > 0 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar  < 1} ">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-${personliginfo.far_d_aar}) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-).
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar > 0 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar  > 0} ">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-${personliginfo.far_d_aar}) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-${personliginfo.mor_d_aar}).
          </c:if>

        </c:otherwise>
        </c:choose>

 </c:forEach>


<!--Representantens utdanning- og yrkeskarriere -->

	 <c:if test="${fn:length(utdyrk) > 0}">
		<p></p>
		<h3>Utdanning og yrke:</h3>
	 </c:if>


<c:forEach items="${utdanningyrke}" var="utdanningyrke">
	<c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger < -1 && utdanningyrke.tilaarInteger < -1 }">
     ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}">${utdanningyrke.institusjon}</c:if> <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
    </c:if>
    <c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger != null && utdanningyrke.tilaarInteger < -1 }">
     ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(${utdanningyrke.fraaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
    </c:if>
     <c:if test="${utdanningyrke.type > 19 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger != null && utdanningyrke.tilaarInteger == -1 }">
     ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(fra ${utdanningyrke.fraaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
    </c:if>
      <c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger != null && utdanningyrke.tilaarInteger != null && utdanningyrke.tilaarInteger != -1 }">
     ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(${utdanningyrke.fraaarInteger}-${utdanningyrke.tilaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
    </c:if>

    <c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19  && utdanningyrke.fraaarInteger == null && utdanningyrke.tilaarInteger != null }">
     ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(til ${utdanningyrke.tilaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
    </c:if>
</c:forEach>

<p></p>

<c:forEach items="${utdyrk}" var="utdyrk">

    <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger < -1 && utdyrk.tilaarInteger < -1 }">
     ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=''}">${utdyrk.institusjon}</c:if> <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
    </c:if>
    <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger < -1 }">
     ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=''}"> - ${utdyrk.institusjon}</c:if>(${utdyrk.fraaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
    </c:if>
     <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger == -1 }">
     ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(fra ${utdyrk.fraaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
    </c:if>
      <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger != null && utdyrk.tilaarInteger != -1 }">
     ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(${utdyrk.fraaarInteger}-${utdyrk.tilaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
    </c:if>
    <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger == null && utdyrk.tilaarInteger != null }">
     ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(til ${utdyrk.tilaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
    </c:if>
</c:forEach>


<!--Representantens kommunalpolitiske aktivitet -->
<table class="text">
	 <c:if test="${fn:length(kompol) > 0}">
		<caption>Kommunalpolitisk aktivitet</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Embete</th>
		<th>Valgkrets</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${kompol}" var="kompol">
	<tr>
		<c:if test="${kompol.periode == 1}">
         <td>Ukjent periode</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
		<c:if test="${kompol.periode == 2}">
         <td>${kompol.expr3}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
		<c:if test="${kompol.periode == 3}">
         <td>Fra ${kompol.periodekode}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
		<c:if test="${kompol.periode == 4}">
         <td>${kompol.periodekode}-${kompol.tilaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
		<c:if test="${kompol.periode == 5}">
         <td>Fra ${kompol.fraaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
        <c:if test="${kompol.periode == 6}">
         <td>${kompol.fraaarInteger} - ${kompol.tilaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
		<c:if test="${kompol.periode == 7}">
         <td>Fra ${kompol.fraaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
		 <c:if test="${kompol.periode == 8}">
         <td>${kompol.fraaarInteger} - ${kompol.tilaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
        <c:if test="${kompol.periode == 9}">
         <td>Usikker periode ${kompol.fraaarInteger} - ${kompol.tilaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
		</c:if>
		</tr>
	</c:forEach>
</tbody>
	 </c:if>
</table>

<!--Representantens fylkespolitiske aktivitet -->

	<c:if test="${fn:length(flypol) > 0}">
		<p></p>
		<h3>Fylkespolitisk aktivitet:</h3>
	 </c:if>


<c:forEach items="${flypol}" var="flypol">
        <c:if test="${flypol.periode == 1}">
         <td>Ukjent periode</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
		<c:if test="${flypol.periode == 2}">
         <td>${flypol.expr3}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
		<c:if test="${flypol.periode == 3}">
         <td>Fra ${flypol.periodekode}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
		<c:if test="${flypol.periode == 4}">
         <td>${flypol.periodekode}-${flypol.tilaarInteger}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
		<c:if test="${flypol.periode == 5}">
         <td>Fra ${flypol.fraaarInteger}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
        <c:if test="${flypol.periode == 6}">
         <td>${flypol.fraaarInteger} - ${flypol.tilaarInteger}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
		<c:if test="${flypol.periode == 7}">
         <td>Fra ${flypol.fraaarInteger}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
		 <c:if test="${flypol.periode == 8}">
         <td>${flypol.fraaarInteger} - ${flypol.tilaarInteger}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
        <c:if test="${flypol.periode == 9}">
         <td>Usikker periode ${flypol.fraaarInteger} - ${flypol.tilaarInteger}</td> <td>${flypol.eintaltekst}</td> <td>${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
</c:forEach>


<!--Representantens offentlige verv -->
<c:if test="${fn:length(offverv) > 0}">
		<p></p>
		<h3>Offentlige verv:</h3>
	</c:if>

<c:forEach items="${offverv}" var="offverv">
		${offverv.stilling} - ${offverv.institusjon}
	<c:if test="${offverv.fraaarInteger== null && offverv.tilaarInteger ==null}"><c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger== null && offverv.tilaar!=null}">(${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger== -1 }">(til ${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.tilaarInteger==-1}">(fra ${offverv.fraaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger != null && offverv.tilaarInteger!=null}">(${offverv.fraaarInteger})-(${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger != null && offverv.tilaarInteger==null}">(${offverv.fraaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <br>
</c:forEach>

<!--Representantens partiverv -->
<c:if test="${fn:length(partiverv) > 0}">
		<p></p>
		<h3>Verv i partier:</h3>
	</c:if>

<c:forEach items="${partiverv}" var="partiverv">
			${partiverv.stilling} - ${partiverv.institusjon}
	<c:if test="${partiverv.fraaarInteger== null && partiverv.tilaarInteger ==null}"><c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger== null && partiverv.tilaarInteger!=null}">(${partiverv.tilaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger== -1 }">(til ${partiverv.tilaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.tilaarInteger==-1}">(fra ${partiverv.fraaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger!= null && partiverv.tilaarInteger!=null}">(${partiverv.fraaarInteger})-(${partiverv.tilaarInteger})<c:if test="${partiverv.merknad !=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger!= null && partiverv.tilaarInteger==null}">(${partiverv.fraaarInteger})<c:if test="${partiverv.merknad !=null}">${partiverv.merknad}</c:if></c:if>
		<br>
</c:forEach>


<!--Representantens organisasjonsverv -->
<c:if test="${fn:length(orgverv) > 0}">
		<p></p>
		<h3>Verv i organisasjoner:</h3>
	</c:if>
<c:forEach items="${orgverv}" var="orgverv">
		${orgverv.stilling} - ${orgverv.institusjon}
	<c:if test="${orgverv.fraaarInteger == null && orgverv.tilaarInteger ==null}"><c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger == null && orgverv.tilaarInteger !=null}">(${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger == -1 }">(til ${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.tilaarInteger ==-1}">(fra ${orgverv.fraaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger != null && orgverv.tilaarInteger !=null}">(${orgverv.fraaarInteger})-(${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger != null && orgverv.tilaarInteger ==null}">(${orgverv.fraaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
		<br>
</c:forEach>


<!--Representantens andre administrative verv -->
<c:if test="${fn:length(admverv) > 0}">
		<p></p>
		<h3>Andre administrative verv:</h3>
	</c:if>

<c:forEach items="${admverv}" var="admverv">
	${admverv.stilling} - ${admverv.institusjon}
	<c:if test="${admverv.fraaarInteger == null && admverv.tilaarInteger ==null}"><c:if test="${admverv.merknad!=null}">${admverv.merknad}</c:if></c:if>
    <c:if test="${admverv.fraaarInteger == null && admverv.tilaarInteger !=null}">(${admverv.tilaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
    <c:if test="${admverv.fraaarInteger == -1 }">(til ${admverv.tilaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
    <c:if test="${admverv.tilaarInteger ==-1}">(fra ${admverv.fraaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
    <c:if test="${admverv.fraaarInteger != null && admverv.tilaarInteger !=null}">(${admverv.fraaarInteger})-(${admverv.tilaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
    <c:if test="${admverv.fraaarInteger != null && admverv.tilaarInteger ==null}">(${admverv.fraaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
		<br>
</c:forEach>

<!--Representantens litteratur -->
<c:if test="${fn:length(litteratur) > 0}">
		<p></p>
		<h3>Litteratur:</h3>
	</c:if>

<c:forEach items="${litteratur}" var="litteratur">
		${litteratur.stilling} - ${litteratur.institusjon}
	<c:if test="${litteratur.fraaarInteger == null && litteratur.tilaarInteger ==null}"><c:if test="${litteratur.merknad!=null}">${litteratur.merknad}</c:if></c:if>
    <c:if test="${litteratur.fraaarInteger == null && litteratur.tilaarInteger !=null}">(${litteratur.tilaarInteger})<c:if test="${litteratur.merknad!=null}">(${litteratur.merknad})</c:if></c:if>
    <c:if test="${litteratur.fraaarInteger == -1 }">(til ${litteratur.tilaarInteger})<c:if test="${litteratur.merknad!=null}">(${litteratur.merknad})</c:if></c:if>
    <c:if test="${litteratur.tilaarInteger ==-1}">(fra ${litteratur.fraaarInteger})<c:if test="${litteratur.merknad!=null}">(${litteratur.merknad})</c:if></c:if>
    <c:if test="${litteratur.fraaarInteger != null && litteratur.tilaarInteger !=null}">(${litteratur.fraaarInteger})-(${litteratur.tilaarInteger})<c:if test="${litteratur.merknad!=null}">(${litteratur.merknad})</c:if></c:if>
    <c:if test="${litteratur.fraaarInteger != null && litteratur.tilaarInteger ==null}">(${litteratur.fraaarInteger})<c:if test="${litteratur.merknad!=null}">(${litteratur.merknad})</c:if></c:if>
		<br>
</c:forEach>

<!--Representantens diverse -->
<c:if test="${fn:length(diverse) > 0}">
		<p></p>
		<h3>Diverse:</h3>
	</c:if>
<c:forEach items="${diverse}" var="diverse">

	<c:if test="${diverse.kode2 == 3}">Okkupasjonen: ${diverse.merknad}<br> </c:if>
     <c:if test="${diverse.kode2 == 5}">Foredragsholder: ${diverse.institusjon}<br> </c:if>
    <c:if test="${diverse.kode2 == 1}">Slektskap: ${diverse.merknad}<br> </c:if>
    <c:if test="${diverse.kode2 == 7}">Minne: ${diverse.merknad}<br> </c:if>
    <c:if test="${diverse.kode2 == 4}">${diverse.stilling} ${diverse.institusjon} (${diverse.fraaarInteger}-${diverse.tilaarInteger}) ${diverse.merknad}<br> </c:if>
    <c:if test="${diverse.kode2 == 6}">${diverse.stilling} ${diverse.institusjon} (${diverse.fraaarInteger}- <c:choose><c:when test="${diverse.tilaarInteger==-1}">ukjent</c:when><c:otherwise>${diverse.tilaarInteger}</c:otherwise></c:choose>)<c:if test="${diverse.merknad!=null}">${diverse.merknad}</c:if></c:if>
      
</c:forEach>


</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />