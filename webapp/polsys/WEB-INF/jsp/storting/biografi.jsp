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
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>

<fmt:setLocale value="en-US" />
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Stortingdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Parliament Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Stortingsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>

<div id="main" class="wide">

<div class="breadcrumbs">
    <c:if test="${no}">
        Du er her:
        <a href="<p:url value="/"/>">PolSys</a>
        <c:choose>
            <c:when test="${param.parti_kortnavn!=null}"> > <a href="<p:url value="/storting/representant/?periode=${param.periode}&parti_kortnavn=${param.parti_kortnavn}"/>">Representanter og suppleanter</a></c:when>
            <c:otherwise> > <a href="<p:url value="/person/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}"/>">Norskepolitikere</a></c:otherwise>
        </c:choose>
        > Biografi
    </c:if>
    <c:if test="${en}">
        You are here:
        <a href="<p:url value="/"/>">PolSys</a>
        > <a href="<p:url value="/storting/"/>">Parliament</a>
        <c:choose>
            <c:when test="${param.parti_kortnavn!=null}"> > <a href="<p:url value="/storting/representant/?periode=${param.periode}&parti_kortnavn=${param.parti_kortnavn}"/>">Representatives and alternates</a></c:when>
            <c:otherwise> > <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}"/>">Norwegian politicians</a></c:otherwise>
        </c:choose>
        > Biography
    </c:if>
</div>
<div>
 <c:if test="${no}"><a href="<m:url value="/en/person/politikerbiografi/?person_id=${param.person_id}&p_info=${param.p_info}" />">View this page in English.</a></c:if>
 <c:if test="${en}"><a href="<m:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=${param.p_info}" />">View this page in Norwegian.</a></c:if>
</div>

<c:if test="${no}"><h1>Biografi</h1></c:if>
    <c:if test="${en}"><h1>Biography</h1>
        <h3>This page is not translated to English</h3>
        <p>----------------------------------------------</p>
    </c:if>
 <!--Representantens navn -->
  <c:forEach items="${personinfo}" var="personinfo">
      <h2>${personinfo.forNavn} ${personinfo.etterNavn} </h2>
      <c:if test="${personinfo.initialer != null}">[<a href="##" onClick="window.open('http://www.stortinget.no/no/Representanter-og-komiteer/Representantene/Representantfordeling/Representant/?perid=${personinfo.initialer}', '1945', 'resizable=yes,scrollbars=yes,width=800,height=700,');return false;" style="color:#3B5B8B "> ${en ? "This politician in the parliament webside" : "Denne politiker  på stortingets nettside"}</a>]</c:if>
        <p></p>      
   </c:forEach>

<p>&nbsp;</p>

<ul class="enhetnav">
<c:if test="${param.p_info=='personalia'}"><c:set var="p_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p_info=='storting'}"><c:set var="st_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p_info=='regjering'}"><c:set var="r_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p_info=='parti'}"><c:set var="pt_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p_info=='utdyrke'}"><c:set var="uy_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p_info=='verv'}"><c:set var="v_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p_info=='litteratur'}"><c:set var="l_class" value="valgt"></c:set> </c:if>
<c:if test="${param.p_info=='diverse'}"><c:set var="d_class" value="valgt"></c:set> </c:if>

    <c:if test="${param.parti_kortnavn!=null}"><c:set var="partikortnavn" value="&parti_kortnavn=${param.parti_kortnavn}"></c:set> </c:if>
    <c:if test="${param.periode!=null}"><c:set var="periode" value="&periode=${param.periode}"></c:set> </c:if>

<c:if test="${fn:length(personliginfo) > 0 || fn:length(personinfo) > 0}">
<li  class="${p_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=personalia${periode}${partikortnavn}" />">${en ? "Personal" : "Personalia"}</a></li>
</c:if>
<c:if test="${fn:length(personstortinginfo) > 0 || fn:length(personstortingspresident) > 0 || fn:length(personstortingskomite) > 0 || fn:length(personstortingsfagkomite) > 0 || fn:length(personstortingsspesialkomite) > 0 || fn:length(personstortingsdelegasjon) > 0 || fn:length(personstortingsfoerer) > 0 || fn:length(personstortingsfagkomite_1945)>0 || fn:length(personstortingsspesialkomite_1945) > 0 || fn:length(personstortingsdelegasjon_1945) > 0}">
<li  class="${st_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=storting${periode}${partikortnavn}" />">${en ? "Storting activity" : "Stortingsaktivitet"}</a></li>
</c:if>
<c:if test="${fn:length(statsraad) > 0 || fn:length(sekretaer) > 0 || fn:length(personregjeringmedlem_1945) > 0}">
<li class="${r_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=regjering${periode}${partikortnavn}" />">${en ? "Government activity" : "Medlemskap i regjering"}</a></li>
</c:if>
<c:if test="${fn:length(personpartimedlem_1945) > 0}">
<li class="${pt_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=parti${periode}${partikortnavn}" />">${en ? "Membership in party" : "Medlemskap i parti"}</a></li>
</c:if>
<c:if test="${fn:length(utdyrk) > 0 || fn:length(utdyrk_1945) > 0 }">
<li class="${uy_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=utdyrke${periode}${partikortnavn}" />">${en ? "Education and occupation" : "Utdanning og yrke"}</a></li>
</c:if>
<c:if test="${fn:length(kompol) > 0 || fn:length(kompol_1945) > 0 || fn:length(flypol) > 0 || fn:length(flypol_1945) > 0 || fn:length(offverv) > 0 || fn:length(offverv_1945) > 0 || fn:length(partiverv) > 0 ||  fn:length(partiverv_1945) > 0 || fn:length(orgverv) > 0 || fn:length(orgverv_1945) > 0 || fn:length(admverv) > 0 || fn:length(admverv_1945) > 0}">
<li class="${v_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=verv${periode}${partikortnavn}" />">${en ? "Positions" : "Verv"}</a></li>
</c:if>
<c:if test="${fn:length(litteratur) > 0 || fn:length(litteratur_1945) > 0 }">
<li class="${l_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=litteratur${periode}${partikortnavn}" />">${en ? "Literature" : "Litteratur"}</a></li>
</c:if>
<c:if test="${fn:length(diverse) > 0 || fn:length(diverse_1945) > 0}">
<li class="${d_class}"><a href="<p:url value="/person/politikerbiografi/?person_id=${param.person_id}&p_info=diverse${periode}${partikortnavn}" />">${en ? "Various" : "Diverse"}</a></li>
</c:if>
</ul>

<p>&nbsp;</p><p></p>

    <c:if test="${param.p_info=='personalia'}">

  <!--Representantens personalia -->

	 <c:if test="${fn:length(personliginfo) > 0 || fn:length(personinfo) > 0}">
		<p></p><p></p>
         <c:if test="${no}"><h3>Personalia:</h3><p></p></c:if><c:if test="${en}"><h3>Personal</h3> </c:if>
	</c:if>

 <c:forEach items="${personinfo}" var="personinfo">
      <c:if test="${personinfo.fodt !=null}">
     <c:if test="${no}"><em>Født:</em></c:if><c:if test="${en}"><em>Born:</em></c:if>${personinfo.fodt}<br>
      </c:if>
      <c:if test="${personinfo.dod !=null}">
     <c:if test="${no}"><em>Død:</em></c:if><c:if test="${en}"><em>Died:</em></c:if> ${personinfo.dod}<br>
      </c:if>
 </c:forEach>
 <c:forEach items="${personliginfo}" var="personliginfo">
     <c:if test="${personliginfo.fodtSted !=null}">
     <c:if test="${no}"><em>Født sted:</em></c:if> <c:if test="${en}"><em>Born place:</em></c:if>${personliginfo.fodtSted}  <br>
     </c:if>    
        <c:choose>
         <c:when test="${personliginfo.far_forNavn == null && personliginfo.mor_forNavn ==null}"> </c:when>
        <c:otherwise>
         <c:if test="${personliginfo.kjoen ==1}">
            <c:if test="${no}">Sønn</c:if><c:if test="${en}">Son</c:if>
         </c:if>
         <c:if test="${personliginfo.kjoen ==2}">
             <c:if test="${no}">Datter</c:if><c:if test="${en}">Daughter</c:if>
         </c:if>
            <c:if test="${no}">av</c:if><c:if test="${en}">of</c:if>
          <c:if test="${personliginfo.far_f_aar < 1 && personliginfo.mor_forNavn == null}">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}
          </c:if>
          <c:if test="${personliginfo.far_f_aar < 1 && personliginfo.mor_forNavn != '' && personliginfo.mor_f_aar < 1 }">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn} og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}
          </c:if>
            <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar < 1 && personliginfo.mor_forNavn != null && personliginfo.mor_f_aar < 1 }">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar < 1 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar < 1 }">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-)
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar < 1 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar > 0 }">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-${personliginfo.mor_d_aar})
          </c:if>
           <c:if test="${personliginfo.far_f_aar < 1 && personliginfo.far_d_aar < 1 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar > 0 }">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn} og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-${personliginfo.mor_d_aar})
          </c:if>
           <c:if test="${personliginfo.far_f_aar >0 && personliginfo.far_d_aar >0 && personliginfo.mor_f_aar <1}">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-${personliginfo.far_d_aar}) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}.
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar > 0 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar  < 1}">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-${personliginfo.far_d_aar}) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-).
          </c:if>
           <c:if test="${personliginfo.far_f_aar > 0 && personliginfo.far_d_aar > 0 && personliginfo.mor_f_aar > 0 && personliginfo.mor_d_aar  > 0}">
             ${personliginfo.far_stilling} ${personliginfo.far_forNavn} ${personliginfo.far_etterNavn}(${personliginfo.far_f_aar}-${personliginfo.far_d_aar}) og  ${personliginfo.mor_forNavn} ${personliginfo.mor_etterNavn}(${personliginfo.mor_f_aar}-${personliginfo.mor_d_aar}).
          </c:if>

        </c:otherwise>
        </c:choose>

 </c:forEach>

</c:if>


<c:if test="${param.p_info=='storting'}">

    <!--Representantens stortingsperioder -->
    <table class="zebra, text">
  <c:if test="${fn:length(personstortinginfo) > 0}">
      <caption>Stortingsperioder</caption>
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
         <td>${personstortinginfo.fleirtaltekst}</td> <td>${personstortinginfo.rep_nr}. representant</td> <td>${personstortinginfo.valkrinsnavn}</td> <td>${personstortinginfo.stilling}</td><td>${personstortinginfo.partiNavn}(${personstortinginfo.partikortnavn})</td>
     </c:if>
     <c:if test="${personstortinginfo.sup_nr >0}">
         <td>${personstortinginfo.fleirtaltekst}</td> <td>${personstortinginfo.sup_nr}. suppleant</td> <td>${personstortinginfo.valkrinsnavn}</td> <td>${personstortinginfo.stilling}</td><td>${personstortinginfo.partiNavn}(${personstortinginfo.partikortnavn})</td>
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

<!-- fagalkomite -->      
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

<!-- fagalkomite 1945 d.d.-->
<c:set var="forrige" value="">  </c:set>
<table class="text">
<c:if test="${fn:length(personstortingsfagkomite_1945) > 0}">
		<caption>Fagkomiteer</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Komite</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${personstortingsfagkomite_1945}" var="personstortingsfagkomite_1945">
		<tr>
			<td valign="top">
          	<c:choose>
                 <c:when test="${personstortingsfagkomite_1945.fleirtaltekst == forrige}">
                  </c:when>
                 <c:otherwise>
                  ${personstortingsfagkomite_1945.fleirtaltekst}
                 </c:otherwise>
				</c:choose>
             <c:set var="forrige" value="${personstortingsfagkomite_1945.fleirtaltekst}">  </c:set>
			</td>
			<td>
			  <c:if test="${personstortingsfagkomite_1945.komitevervtekst !=null }">
               ${personstortingsfagkomite_1945.komitevervtekst } i ${personstortingsfagkomite_1945.eintaltekst}, ${personstortingsfagkomite_1945.fraaar}-${personstortingsfagkomite_1945.tilaar}
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
                 <c:when test="${personstortingsspesialkomite.fleirtaltekst == forrige}">
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


 <!-- specialfagalkomite 1945 d.d.-->
<c:set var="forrige" value=" ">  </c:set>
 <table class="text">
<c:if test="${fn:length(personstortingsspesialkomite_1945) > 0}">
		<caption>Spesialkomiteer</caption>
<thead>
	<tr>
		<th>Periode</th>
		<th>Komite</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${personstortingsspesialkomite_1945}" var="personstortingsspesialkomite_1945">
		<tr>
			<td valign="top">
             <c:choose>
                 <c:when test="${personstortingsspesialkomite_1945.fleirtaltekst == forrige}">
                  </c:when>
                 <c:otherwise>
                  ${personstortingsspesialkomite_1945.fleirtaltekst}
                 </c:otherwise>
				</c:choose>
             <c:set var="forrige" value="${personstortingsspesialkomite_1945.fleirtaltekst}">  </c:set>
			</td>
			<td>
			  <c:if test="${personstortingsspesialkomite_1945.komitevervtekst !=null }">
               ${personstortingsspesialkomite_1945.komitevervtekst } i ${personstortingsspesialkomite_1945.eintaltekst}, ${personstortingsspesialkomite_1945.fraaar}-${personstortingsspesialkomite_1945.tilaar}
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

<c:forEach items="${personstortingsdelegasjon}" var="personstortingsdelegasjon">
   <c:if test="${personstortingsdelegasjon.fraaar < 0 && personstortingsdelegasjon.tilaar < 1}">${personstortingsdelegasjon.stilling} - ${personstortingsdelegasjon.institusjon}.<br> </c:if>
   <c:if test="${personstortingsdelegasjon.fraaar > 0 && personstortingsdelegasjon.tilaar < 1}">${personstortingsdelegasjon.stilling} - ${personstortingsdelegasjon.institusjon}, ${personstortingsdelegasjon.fraaar} .<br></c:if>
   <c:if test="${personstortingsdelegasjon.fraaar > 0 && personstortingsdelegasjon.tilaar > 0}">${personstortingsdelegasjon.stilling} - ${personstortingsdelegasjon.institusjon}, ${personstortingsdelegasjon.fraaar}-${personstortingsdelegasjon.tilaar}.<br> </c:if>
</c:forEach>

<!--Representantens medlemskap i delegasjoner -1945 d.d. -->
 <c:if test="${fn:length(personstortingsdelegasjon_1945) > 0}">
		<p></p>
		<h3>Medlemskap i delegasjoner:</h3>
</c:if>

                  
<c:set var="forrige" value=" ">  </c:set>
<c:forEach items="${personstortingsdelegasjon_1945}" var="personstortingsdelegasjon_1945">
    <c:choose>
                 <c:when test="${personstortingsdelegasjon_1945.fleirtaltekst == forrige}">
                    <br>
                 </c:when>
                 <c:otherwise>
                   <p></p>  
                  <strong>${personstortingsdelegasjon_1945.fleirtaltekst}</strong><br>
                 </c:otherwise>
				</c:choose>
     <c:set var="forrige" value="${personstortingsdelegasjon_1945.fleirtaltekst}">  </c:set>
   ${personstortingsdelegasjon_1945.komitevervtekst} - ${personstortingsdelegasjon_1945.eintaltekst}, ${personstortingsdelegasjon_1945.fraaar}-${personstortingsdelegasjon_1945.tilaar}
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
		 <c:if test="${personstortingsfoerer.fraaarInteger > 0 && personstortingsfoerer.tilaarInteger > 0}">
 			<td>${personstortingsfoerer.fraaarInteger}-${personstortingsfoerer.tilaarInteger}</td> <td>Parlamentarisk leder</td> <td>${personstortingsfoerer.institusjon}</td>
		 </c:if>
        <c:if test="${personstortingsfoerer.fraaarInteger > 0 && personstortingsfoerer.tilaarInteger  < 1}">
 			<td>${personstortingsfoerer.fraaarInteger}</td> <td>Parlamentarisk leder</td> <td>${personstortingsfoerer.institusjon}</td>
		 </c:if>
        <c:if test="${personstortingsfoerer.fraaarInteger < 1 && personstortingsfoerer.tilaarInteger < 1}">
 			<td></td> <td>Parlamentarisk leder</td> <td>${personstortingsfoerer.institusjon}</td>
		 </c:if>
	</tr>
	</c:forEach>
</tbody>
	</c:if>
</table>
</c:if>

<c:if test="${param.p_info=='regjering'}">
<!--Representantens regjeringsmedlemskap -->
 <c:if test="${fn:length(personregjeringmedlem_1945) > 0}">
		<p></p>
		<h3>Medlemskap i regjering:</h3>
</c:if>

<c:forEach items="${personregjeringmedlem_1945}" var="personregjeringmedlem_1945">
  ${personregjeringmedlem_1945.komitevervtekst} - ${personregjeringmedlem_1945.regjeringdepartment}, ${personregjeringmedlem_1945.fraaar}-${personregjeringmedlem_1945.tilaar}<br>
</c:forEach>

   <!--Statsrådsaktivitet-->

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
        <%-- <c:set var="slutt_dato" value="${now}" /> --%>
            <c:set var="slutt_dato" value=" " />
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
                  <c:when test="${statsraad.stilling != '' &&  (statsraad.eintaltekst == 'Forsvarsdepartementet' )}">
                      <td>${statsraad.stilling} </td><td>${statsraad.eintaltekst}</td>  <td>${statsraad.fraaar} - ${slutt_dato}</td>
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
                <c:when test="${sekretaer.tilaar == '9-9-9999'}">
                    <%-- <c:set var="slutt_dato" value="${now}" /> --%>
                    <c:set var="slutt_dato" value=" " />
                </c:when>
                <c:otherwise><c:set var="slutt_dato" value="${sekretaer.tilaar}" /></c:otherwise>
            </c:choose>

            <c:if test="${no}">
              <c:choose>
               <c:when test="${sekretaer.stilling =='' || sekretaer.stilling ==null}"> <td>Statssekretær</td></c:when>
              <c:otherwise><td>${sekretaer.stilling}</td></c:otherwise>
              </c:choose>
              <td>${sekretaer.eintaltekst}</td> <td>${sekretaer.fraaar} - ${slutt_dato}</td>
             </c:if>
            <c:if test="${en}">
            <c:choose>
               <c:when test="${sekretaer.stilling != ''}"> <td>${sekretaer.stilling}</td></c:when>
              <c:otherwise><td>State Secretary</td></c:otherwise>
              </c:choose>
              <td>${sekretaer.eintaltekst}</td> <td>${sekretaer.fraaar} - ${slutt_dato}</td>
            </c:if>
        </tr>
      </c:forEach>
    </tbody>
     </c:if>
    </table>

 </c:if>


<!-- Representantens partismedlemskap-->

 <c:if test="${param.p_info=='parti'}">


<c:if test="${fn:length(personpartimedlem_1945) > 0}">
		<p></p>
		<h3>Medlemskap i gruppestyrer:</h3>
</c:if>

<c:set var="forrige" value=" ">  </c:set>
<c:forEach items="${personpartimedlem_1945}" var="personpartimedlem_1945">
    <c:choose>
                 <c:when test="${personpartimedlem_1945.fleirtaltekst == forrige}">
                    <br>
                 </c:when>
                 <c:otherwise>
                   <p></p>
                  <strong>${personpartimedlem_1945.fleirtaltekst}</strong><br>
                 </c:otherwise>
				</c:choose>
     <c:set var="forrige" value="${personpartimedlem_1945.fleirtaltekst}">  </c:set>
   ${personpartimedlem_1945.komitevervtekst} - ${personpartimedlem_1945.eintaltekst}, ${personpartimedlem_1945.fraaar}-${personpartimedlem_1945.tilaar}
</c:forEach>



</c:if>



<c:if test="${param.p_info=='utdyrke'}">

    <!--Her begynner de biografiske opplysningene -->
<!--Representantens utdanning- og yrkeskarriere -->

	 <c:if test="${fn:length(utdanningyrke) > 0}">
		<p></p>
		<h3>Utdanning</h3>
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
    <c:if test="${fn:length(utdyrk) > 0}">
        <p></p>
        <h3>Yrke:</h3>
    </c:if>
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

<!-- utdanning yrke 1945-d.d. -->
    <c:if test="${fn:length(utdanningyrke_1945) > 0}">
        <p></p>
        <h3>Utdanning</h3>
    </c:if>
    <c:forEach items="${utdanningyrke_1945}" var="utdanningyrke">
        <c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger <= 0 && utdanningyrke.tilaarInteger <= 0 }">
            ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}">${utdanningyrke.institusjon}</c:if> <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
        </c:if>
        <c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger != null && utdanningyrke.tilaarInteger <= 0 }">
            ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(${utdanningyrke.fraaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
        </c:if>
        <c:if test="${utdanningyrke.type > 19 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger != null && utdanningyrke.tilaarInteger == 0 }">
            ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(fra ${utdanningyrke.fraaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
        </c:if>
        <c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19 && utdanningyrke.fraaarInteger != null && utdanningyrke.tilaarInteger != null && utdanningyrke.tilaarInteger != 0 }">
            ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(${utdanningyrke.fraaarInteger}-${utdanningyrke.tilaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
        </c:if>
        <c:if test="${utdanningyrke.type > 9 && utdanningyrke.type < 19  && utdanningyrke.fraaarInteger == null && utdanningyrke.tilaarInteger != null }">
            ${utdanningyrke.stilling} <c:if test="${utdanningyrke.institusjon !=''}"> - ${utdanningyrke.institusjon}</c:if>(til ${utdanningyrke.tilaarInteger}) <c:if test="${utdanningyrke.merknad !=''}">${utdanningyrke.merknad}</c:if><br>
        </c:if>
    </c:forEach>

    <p></p>
    <c:if test="${fn:length(utdyrk_1945) > 0}">
        <p></p>
        <h3>Yrke:</h3>
    </c:if>
    <c:forEach items="${utdyrk_1945}" var="utdyrk">

        <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger <= 0 && utdyrk.tilaarInteger <= 0 }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=''}">${utdyrk.institusjon}</c:if> <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger <= 0 }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=''}"> - ${utdyrk.institusjon}</c:if>(${utdyrk.fraaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger == 0 }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(fra ${utdyrk.fraaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger != null && utdyrk.tilaarInteger != 0 && utdyrk.tilaarInteger != -1 }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(${utdyrk.fraaarInteger}-${utdyrk.tilaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type > 19 && utdyrk.type < 29 && utdyrk.fraaarInteger == null && utdyrk.tilaarInteger != null }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(til ${utdyrk.tilaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
    </c:forEach>

    <p></p>
    <c:if test="${fn:length(utdyrk_1945) > 0 || fn:length(utdanningyrke_1945) > 0}">
        <p></p>
        <h3>Medaljer og utmerkelser m.m.:</h3>
    </c:if>
    <c:forEach items="${utdyrk_1945}" var="utdyrk">

        <c:if test="${utdyrk.type  ==40  && utdyrk.fraaarInteger <= 0 && utdyrk.tilaarInteger <= 0 }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=''}">${utdyrk.institusjon}</c:if> <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type ==40  && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger <= 0 }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=''}"> - ${utdyrk.institusjon}</c:if>(${utdyrk.fraaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type ==40 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger ==null }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(fra ${utdyrk.fraaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type ==40 && utdyrk.fraaarInteger != null && utdyrk.tilaarInteger != null && utdyrk.tilaarInteger != 0 }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(${utdyrk.fraaarInteger}-${utdyrk.tilaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
        <c:if test="${utdyrk.type ==40  && utdyrk.fraaarInteger == null && utdyrk.tilaarInteger != null }">
            ${utdyrk.stilling} <c:if test="${utdyrk.institusjon !=null}"> - ${utdyrk.institusjon}</c:if>(til ${utdyrk.tilaarInteger}) <c:if test="${utdyrk.merknad !=null}">(${utdyrk.merknad})</c:if><br>
        </c:if>
    </c:forEach>



</c:if>


<c:if test="${param.p_info=='verv'}">

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

    <!--Representantens kommunalpolitiske aktivitet 1945 -->
    <table class="text">
        <c:if test="${fn:length(kompol_1945) > 0}">
            <caption>Kommunalpolitisk aktivitet</caption>
            <thead>
            <tr>
                <th>Periode</th>
                <th>Embete</th>
                <th>Valgkrets</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${kompol_1945}" var="kompol">
                <tr>
                    <c:if test="${kompol.expr3 == 'Ukjent periode'}">
                        <td>Ukjent periode</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
                    </c:if>
                    <c:if test="${ kompol.expr3 != 'Ukjent periode' && kompol.fraaarInteger == '' && kompol.tilaarInteger == ''}">
                        <td>${kompol.expr3}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
                    </c:if>
                    <c:if test="${kompol.fraaarInteger == '' && kompol.tilaarInteger == -1}">
                        <td>Fra ${kompol.periodekode}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
                    </c:if>
                    <c:if test="${kompol.fraaarInteger == '' && kompol.tilaarInteger != '' && kompol.tilaarInteger != -1}">
                        <td>${kompol.periodekode}- ${kompol.tilaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
                    </c:if>
                    <c:if test="${kompol.fraaarInteger != -1 && kompol.fraaarInteger != '' && kompol.tilaarInteger == -1}">
                        <td>Fra ${kompol.fraaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
                    </c:if>
                    <c:if test="${kompol.fraaarInteger != -1 && kompol.fraaarInteger != '' && kompol.tilaarInteger != -1 && kompol.tilaarInteger != ''}">
                        <td>${kompol.fraaarInteger} - ${kompol.tilaarInteger}</td> <td>${kompol.eintaltekst}</td> <td>${kompol.institusjon} ${kompol.expr2} <c:if test="${kompol.merknad !=null}">(${kompol.merknad})</c:if></td>
                    </c:if>

                    <c:if test="${kompol.fraaarInteger != -1 && kompol.fraaarInteger != '' && kompol.tilaarInteger ==''}">
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
    <c:if test="${fn:length(flypol_1945) > 0}">
        <p></p>
        <h3>Fylkespolitisk aktivitet:</h3>
    </c:if>

<!--  før 1945-->
<c:forEach items="${flypol}" var="flypol">
        <c:if test="${flypol.periode == 1}">
         <strong>Ukjent periode</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
		</c:if>
		<c:if test="${flypol.periode == 2}">
         <strong>${flypol.expr3}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if> <br>
		</c:if>
		<c:if test="${flypol.periode == 3}">
         <strong>Fra ${flypol.periodekode}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
		</c:if>
		<c:if test="${flypol.periode == 4}">
         <strong>${flypol.periodekode}-${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
		</c:if>
		<c:if test="${flypol.periode == 5}">
         <strong>Fra ${flypol.fraaarInteger}</strong>: <td>${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if> <br>
		</c:if>
        <c:if test="${flypol.periode == 6}">
         <strong>${flypol.fraaarInteger} - ${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
		</c:if>
		<c:if test="${flypol.periode == 7}">
         <strong>Fra ${flypol.fraaarInteger}</strong> ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if> <br>
		</c:if>
		 <c:if test="${flypol.periode == 8}">
         <strong>${flypol.fraaarInteger} - ${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
		</c:if>
        <c:if test="${flypol.periode == 9}">
         <strong>Usikker periode ${flypol.fraaarInteger} - ${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
		</c:if>
</c:forEach>
    <!--  1945 d.d.-->
    <c:forEach items="${flypol_1945}" var="flypol">
        <c:if test="${flypol.expr3 == 'Ukjent periode'}">
            <strong>Ukjent periode</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
        </c:if>
        <c:if test="${ flypol.expr3 != 'Ukjent periode' && flypol.fraaarInteger == '' && flypol.tilaarInteger == ''}">
            <strong>${flypol.expr3}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if> <br>
        </c:if>
        <c:if test="${flypol.fraaarInteger == '' && flypol.tilaarInteger == -1}">
            <strong>Fra ${flypol.periodekode}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
        </c:if>
        <c:if test="${flypol.fraaarInteger == '' && flypol.tilaarInteger != '' && flypol.tilaarInteger != -1}">
            <strong>${flypol.periodekode}-${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
        </c:if>
        <c:if test="${flypol.fraaarInteger != -1 && flypol.fraaarInteger != '' && flypol.tilaarInteger == -1}">
            <strong>Fra ${flypol.fraaarInteger}</strong>: <td>${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if> <br>
        </c:if>
        <c:if test="${flypol.fraaarInteger != -1 && flypol.fraaarInteger != '' && flypol.tilaarInteger != -1 && flypol.tilaarInteger != ''}">
            <strong>${flypol.fraaarInteger} - ${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
        </c:if>

        <c:if test="${flypol.fraaarInteger != -1 && flypol.fraaarInteger != '' && flypol.tilaarInteger ==''}">
            <strong>${flypol.fraaarInteger} - ${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if><br>
        </c:if>
        <c:if test="${flypol.periode == 9}">
            <strong>Usikker periode ${flypol.fraaarInteger} - ${flypol.tilaarInteger}</strong>: ${flypol.eintaltekst} ${flypol.institusjon} ${flypol.expr2} <c:if test="${flypol.merknad !=null}">(${flypol.merknad})</c:if></td>
        </c:if>
    </c:forEach>


<!--Representantens offentlige verv -->
<c:if test="${fn:length(offverv) > 0}">
		<p></p>
		<h3>Offentlige verv:</h3>
	</c:if>
    <c:if test="${fn:length(offverv_1945) > 0}">
        <p></p>
        <h3>Offentlige verv:</h3>
    </c:if>

<c:forEach items="${offverv}" var="offverv">
		${offverv.stilling} - ${offverv.institusjon}
	<c:if test="${offverv.fraaarInteger== null && offverv.tilaarInteger ==null}"><c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger== null && offverv.tilaar!=null}">(${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger== -1 }">(til ${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.tilaarInteger==-1}">(fra ${offverv.fraaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger != null && offverv.tilaarInteger!=null}">(${offverv.fraaarInteger} - ${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <c:if test="${offverv.fraaarInteger != null && offverv.tilaarInteger==null}">(${offverv.fraaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
    <br>
</c:forEach>
<!-- offentilge verv 1945 -->
<c:forEach items="${offverv_1945}" var="offverv">
   ${offverv.stilling} - ${offverv.institusjon}
   <c:if test="${offverv.fraaarInteger== null && offverv.tilaarInteger ==null}"><c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
   <c:if test="${offverv.fraaarInteger== null && offverv.tilaar!=null}">(${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
   <c:if test="${offverv.fraaarInteger== -1 }">(til ${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
   <c:if test="${offverv.tilaarInteger==-1}">(fra ${offverv.fraaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
   <c:if test="${offverv.fraaarInteger != null && offverv.tilaarInteger!=null}">(${offverv.fraaarInteger} - ${offverv.tilaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
   <c:if test="${offverv.fraaarInteger != null && offverv.tilaarInteger==null}">(${offverv.fraaarInteger})<c:if test="${offverv.merknad!=null}">${offverv.merknad}</c:if></c:if>
      <br>
</c:forEach>

<!--Representantens partiverv -->
<c:if test="${fn:length(partiverv) > 0}">
		<p></p>
		<h3>Verv i partier:</h3>
	</c:if>
    <c:if test="${fn:length(partiverv_1945) > 0}">
        <p></p>
        <h3>Verv i partier:</h3>
    </c:if>

<c:forEach items="${partiverv}" var="partiverv">
			${partiverv.stilling} - ${partiverv.institusjon}
	<c:if test="${partiverv.fraaarInteger== null && partiverv.tilaarInteger ==null}"><c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger== null && partiverv.tilaarInteger!=null}">(${partiverv.tilaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger== -1 }">(til ${partiverv.tilaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.tilaarInteger==-1}">(fra ${partiverv.fraaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger!= null && partiverv.tilaarInteger!=null}">(${partiverv.fraaarInteger} - ${partiverv.tilaarInteger})<c:if test="${partiverv.merknad !=null}">${partiverv.merknad}</c:if></c:if>
    <c:if test="${partiverv.fraaarInteger!= null && partiverv.tilaarInteger==null}">(${partiverv.fraaarInteger})<c:if test="${partiverv.merknad !=null}">${partiverv.merknad}</c:if></c:if>
		<br>
</c:forEach>
    <!-- 1945 d.d -->
    <c:forEach items="${partiverv_1945}" var="partiverv">
        ${partiverv.stilling} - ${partiverv.institusjon}
        <c:if test="${partiverv.fraaarInteger== '' && partiverv.tilaarInteger ==''}"><c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
        <c:if test="${partiverv.fraaarInteger== '' && partiverv.tilaarInteger!=''}">(${partiverv.tilaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
        <c:if test="${partiverv.fraaarInteger== -1 }">(til ${partiverv.tilaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
        <c:if test="${partiverv.tilaarInteger==-1}">(fra ${partiverv.fraaarInteger})<c:if test="${partiverv.merknad!=null}">${partiverv.merknad}</c:if></c:if>
        <c:if test="${partiverv.fraaarInteger!= '' && partiverv.tilaarInteger!='' && partiverv.fraaarInteger!= -1 && partiverv.tilaarInteger!=-1}">(${partiverv.fraaarInteger} - ${partiverv.tilaarInteger})<c:if test="${partiverv.merknad !=null}">${partiverv.merknad}</c:if></c:if>
        <c:if test="${partiverv.fraaarInteger!= '' && partiverv.tilaarInteger==''}">(${partiverv.fraaarInteger})<c:if test="${partiverv.merknad !=null}">${partiverv.merknad}</c:if></c:if>
        <br>
    </c:forEach>

<!--Representantens organisasjonsverv -->
<c:if test="${fn:length(orgverv) > 0}">
		<p></p>
		<h3>Verv i organisasjoner:</h3>
	</c:if>
    <c:if test="${fn:length(orgverv_1945) > 0}">
        <p></p>
        <h3>Verv i organisasjoner:</h3>
    </c:if>
<c:forEach items="${orgverv}" var="orgverv">
		${orgverv.stilling} - ${orgverv.institusjon}
	<c:if test="${orgverv.fraaarInteger == null && orgverv.tilaarInteger ==null}"><c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger == null && orgverv.tilaarInteger !=null}">(${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger == -1 }">(til ${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.tilaarInteger ==-1}">(fra ${orgverv.fraaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger != null && orgverv.tilaarInteger !=null}">(${orgverv.fraaarInteger} - ${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
    <c:if test="${orgverv.fraaarInteger != null && orgverv.tilaarInteger ==null}">(${orgverv.fraaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
		<br>
</c:forEach>
<!-- 1945-->
    <c:forEach items="${orgverv_1945}" var="orgverv">
        ${orgverv.stilling} - ${orgverv.institusjon}
        <c:if test="${orgverv.fraaarInteger == null && orgverv.tilaarInteger ==null}"><c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
        <c:if test="${orgverv.fraaarInteger == 0 && orgverv.tilaarInteger !=null}">(${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
        <c:if test="${orgverv.fraaarInteger == -1  }">(til ${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
        <c:if test="${orgverv.tilaarInteger == -1 }">(fra ${orgverv.fraaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
        <c:if test="${orgverv.fraaarInteger != null && orgverv.tilaarInteger !=null && orgverv.tilaarInteger!=0}">(${orgverv.fraaarInteger} - ${orgverv.tilaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
        <c:if test="${orgverv.fraaarInteger != null && orgverv.tilaarInteger ==0}">(${orgverv.fraaarInteger})<c:if test="${orgverv.merknad!=null}">${orgverv.merknad}</c:if></c:if>
        <br>
    </c:forEach>

<!--Representantens andre administrative verv -->
<c:if test="${fn:length(admverv) > 0}">
		<p></p>
		<h3>Andre administrative verv:</h3>
	</c:if>
    <c:if test="${fn:length(admverv_1945) > 0}">
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
 <!-- 1945 d.d-->
    <c:forEach items="${admverv_1945}" var="admverv">
        ${admverv.stilling} - ${admverv.institusjon}
        <c:if test="${admverv.fraaarInteger == null && admverv.tilaarInteger ==null}"><c:if test="${admverv.merknad!=null}">${admverv.merknad}</c:if></c:if>
        <c:if test="${admverv.fraaarInteger == null && admverv.tilaarInteger !=null}">(${admverv.tilaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
        <c:if test="${admverv.fraaarInteger == -1 }">(til ${admverv.tilaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
        <c:if test="${admverv.tilaarInteger ==-1}">(fra ${admverv.fraaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
        <c:if test="${admverv.fraaarInteger != null && admverv.tilaarInteger !=null}">(${admverv.fraaarInteger})-(${admverv.tilaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
        <c:if test="${admverv.fraaarInteger != null && admverv.tilaarInteger ==null}">(${admverv.fraaarInteger})<c:if test="${admverv.merknad!=null}">(${admverv.merknad})</c:if></c:if>
        <br>
    </c:forEach>

</c:if>

<c:if test="${param.p_info=='litteratur'}">

<!--Representantens litteratur -->
<c:if test="${fn:length(litteratur) > 0}">
		<p></p>
		<h3>Litteratur:</h3>
	</c:if>
    <c:if test="${fn:length(litteratur_1945) > 0}">
        <p></p>
        <h3>Litteratur:</h3>
    </c:if>
<!-- før 1945-->
<c:forEach items="${litteratur}" var="litteratur">
		<c:if test="${litteratur.stilling!=null}"> ${litteratur.stilling} : </c:if> ${litteratur.institusjon}    
    <c:if test="${litteratur.fraaarInteger == null }"><c:if test="${litteratur.merknad!=null}">${litteratur.merknad}</c:if></c:if>
    <c:if test="${litteratur.fraaarInteger != null}">, ${litteratur.forlag} ${litteratur.utgsted} ${litteratur.fraaarInteger}<c:if test="${litteratur.merknad!=null}">(${litteratur.merknad})</c:if></c:if>
 	<br>
</c:forEach>

<!--1945 d.d -->
    <c:forEach items="${litteratur_1945}" var="litteratur">
        <c:if test="${litteratur.stilling!=null}"> ${litteratur.stilling} : </c:if> ${litteratur.institusjon}
        <c:if test="${litteratur.fraaar == null }"><c:if test="${litteratur.merknad!=null}">${litteratur.merknad}</c:if></c:if>
        <c:if test="${litteratur.fraaar != null}">, ${litteratur.forlag} ${litteratur.utgsted} ${litteratur.fraaar}<c:if test="${litteratur.merknad!=null}">(${litteratur.merknad})</c:if></c:if>


        <br>
    </c:forEach>



	</c:if>

<c:if test="${param.p_info=='diverse'}">

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
    <br>
</c:forEach>

    <!--Representantens diverse 1945 -->
    <c:if test="${fn:length(diverse_1945) > 0}">
        <p></p>
        <h3>Annen aktivitet, krigsinnsats m.m.</h3>
    </c:if>
    <c:forEach items="${diverse_1945}" var="diverse">
        <c:if test="${diverse.fraaarInteger != null && diverse.fraaarInteger != 0  && diverse.tilaarInteger !=null && diverse.tilaarInteger !=0}">${diverse.stilling} ${diverse.institusjon} (${diverse.fraaarInteger}- ${diverse.tilaarInteger})<c:if test="${diverse.merknad!=null}">${diverse.merknad}</c:if></c:if>
        <c:if test="${diverse.fraaarInteger == 0 && diverse.tilaarInteger ==0}">${diverse.stilling} ${diverse.institusjon} <c:if test="${diverse.merknad!=null}">${diverse.merknad}</c:if></c:if>
        <c:if test="${diverse.fraaarInteger != 0 && diverse.tilaarInteger ==0}">${diverse.stilling} ${diverse.institusjon} (${diverse.fraaarInteger})<c:if test="${diverse.merknad!=null}">${diverse.merknad}</c:if></c:if>
        <c:if test="${diverse.fraaarInteger == 0 && diverse.tilaarInteger !=0}">${diverse.stilling} ${diverse.institusjon} (${diverse.tilaarInteger})<c:if test="${diverse.merknad!=null}">${diverse.merknad}</c:if></c:if>
        <br>
    </c:forEach>
</c:if>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />