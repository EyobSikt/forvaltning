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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- --------------------------------------------- inkluderer toppinnhold. --%>

<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="Data" />
    <c:param name="tittelEn" value="Data" />
</c:import>

<div id="sidebar">

	<c:if test="${no}"><h3>LASTE NED DATASETT</h3>Klikk <a href="<p:url value="/regjering/lastnedstatsraad/"/>">her</a> å laste ned data om norske statsråder.</c:if>
	<c:if test="${en}"><h3>DOWNLOAD DATASETS</h3> Click <a href="<p:url value="/regjering/lastnedstatsraad/"/>">here</a> to download data about norwegian ministers.</c:if>


<c:forEach items="${regjeringsbeskrivelse}" var="regjeringsbeskrivelse" >
	<!-- de generelle kommentarene under finnes også i 'norske_regjeringer.eksternkommentar' --->

	<c:if test="${no}">
		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 1}">
			<h3>Merk</h3>
			<p>Frem til 19.05.1814 hadde "statsrådet" betegnelsen 'regjeringsrådet,' mens departementssjefene ble titulert 'regjeringsråd.'</p>

			<p>Hegermann og Fasting ble utnevnt av Stortinget ettersom kong Christian Frederik hadde abdisert og følgelig ikke kunne stå for utnevnelser. Av samme årsak fungerte Rosenkrantz også som statsoverhode i perioden 10.10.1814-03.11.1814.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 4}">
			<h3>Merk</h3>
			<p>Overgangen mellom Det andre Wedelske ministerium og det Løvenskiold-Vogtske ministerium markeres med kong Carl III Johans død 08.03.1844 og kong Oscar I's bestigning av tronen.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 7}">
			<h3>Merk</h3>
			<p>Fredrik Stang var den første (og eneste) som var førstestatsråd etter formell utnevnelse. Før denne tid var "førstestatsråden" regjeringens eldste statsråd som i kongens, visekongens eller stattholderens fravær fungerte som regjeringens formann.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 8}">
			<h3>Merk</h3>
			<p>Lerche Johansen, Schweigaard og Egede Hertzberg var de eneste fra det Selmerske ministerium som ikke ble fradømt embetet ved riksrett. Riksretten kom i stand som følge av at ministeriet nektet å sanksjonere "statsrådssaken."</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 9}">
			<h3>Merk</h3>
			<p>Aprilministeriets avgang markerer starten på overgangen til parlamentarisme i Norge.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode ==18}">
			<h3>Merk</h3>
			<p>Utenriksdepartementet ble formelt opprettet f.o.m. 15.06.1905. Det svensk-norske konsulatvesenet fortsatte imidlertid å ivareta Norges interesser i utlandet frem til kong Oscar II. anerkjente Norge som selvstendig stat 26.10.1905. Løvland var for øvrig utenriksminister med bibehold av statsministertittel frem til 27.11.1905.</p>
		</c:if>
	</c:if>
		<p></p>
	<c:if test="${en}">
		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 1}">
			<h3>Note</h3>
			<p>Until 19.05.1814 the Government ('the Council of State') was termed the Council of Government, and the ministers ('Councillors of State') were titled Councillors of Government.</p>

			<p>Hegermann and Fasting were appointed by the Storting due to King Christian Frederik's abdication. Also due to the abdication Rosenkrantz acted as Head of State in the days between 10.10.1814 and 03.11.1814.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 4}">
			<h3>Note</h3>
			<p>The beginning of the Løvenskiold-Vogt Government is marked by the death of King Carl III Johan (08.03.1844) and King Oscar I's ascending of the throne.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 7}">
			<h3>Note</h3>
			<p>Fredrik Stang was the first (and only) who was officially appointed First Minister.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 8}">
			<h3>Note</h3>
			<p>Lerche Johansen, Schweigaard and Egede Hertzberg were the only ministers in the Selmer Government who were not impeached.</p>
		</c:if>

	<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 9}">
			<h3>Note</h3>
			<p>The April Government's resignation marks the beginning of the transition to parliamentarism in Norway.</p>
		</c:if>

	<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 18}">
			<h3>Note</h3>
			<p>The Ministry of Foreign Affairs was formally established 15.06.1905. However, the Swedish-Norwegian Consulates continued until King Oscar II formally recognized Norway's independence claims 26.10.1905. Løvland continued to be titled 'Prime Minister' until 27.11.1905.</p>
		</c:if>
	</c:if>
		<p></p>
</c:forEach>



<!-- Parlamentarisk grunnlag - Start -->
<c:if test="${param.stortingperiodekode !=null}">

	<c:if test="${no}">
		<h3>Parlamentarisk grunnlag </h3>
			<c:forEach items="${regjeringsbeskrivelse}" var="regjeringsbeskrivelse" >
					<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 10}">
						<p>Venstres stortingsgruppe sprakk i februar 1888. Etter dette var det i praksis fraksjonen Moderate Venstre som innehadde regjeringsposisjonen.</p>
					</c:if>
					<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 35}">
						<p>Hjemmefronten var ikke representert på Stortinget.</p>
					</c:if>
					<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 36}">
						<p>Hjemmefronten og NKP var ikke representert på Stortinget.</p>
					</c:if>
			</c:forEach>
	</c:if>

	<c:if test="${en}">
		<h3>Parliamentary Basis</h3>
			<c:forEach items="${regjeringsbeskrivelse}" var="regjeringsbeskrivelse" >
					<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 10}">
						<p>The Liberal Party's parliamentary grouping factioned Febraury 1888. The faction that constituted the Moderate Liberal Party continued in government.</p>
					</c:if>
					<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 35}">
						<p>The Home Front had no seats at the Storting.</p>
					</c:if>
					<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 136}">
						<p>The Home Front and the Communist Party had no seats at the Storting.</p>
					</c:if>
			</c:forEach>
	</c:if>

	<c:forEach items="${stortingperiode}" var="stortingperiode" >
	  <c:if test="${no}">
 	 	<br><h4>Stortingsperiode ${stortingperiode.fleirtaltekst}   </h4>
	  </c:if>

	  <c:if test="${en}">
		<br><h4>Electoral period ${stortingperiode.fleirtaltekst}</h4>
	  </c:if>

	 <table class="zebra">
		<c:if test="${no}">
			<caption style="font-size:10px">Regjeringsparti</caption>
		<thead>
			<tr>
				<th width="135">Parti</th>
				<th>Seter</th>
			</tr>
		</thead>
		</c:if>
		<c:if test="${en}">
			<caption style="font-size:10px">Government</caption>
		<thead>
			<tr>
				<th width="135">Party</th>
				<th>Seats</th>
			</tr>
		</thead>
		</c:if>
		<tfoot>

			<c:forEach items="${sumposisjon}" var="sumposisjon" >
            <c:if test="${stortingperiode.storting_periodekode == sumposisjon.storting_periodekode}">

			<c:set var="PosisjonPros2" value="${sumposisjon.rep_nr}"></c:set>
			<c:set var="PosisjonPros3" value="${(PosisjonPros2/stortingperiode.sumstortingsseter)*100}"></c:set>
			<c:set var="PosisjonPros" ><fmt:formatNumber type="number" maxFractionDigits="1"  value="${PosisjonPros3}" /></c:set>

			<cfset PosPros=  ${stortingperiode.storting_periodekode}*100)/${stortingperiode.sumstortingsseter}>
			<tr>
				<th>Total (N) </th>
				<td><c:if test="${no}"><c:choose><c:when test="${stortingperiode.storting_periodekode==65}">81</c:when><c:otherwise>${sumposisjon.rep_nr}</c:otherwise></c:choose>  </c:if>
                   <c:if test="${en}"> <c:choose><c:when test="${stortingperiode.storting_periodekode==65}">81</c:when><c:otherwise>${sumposisjon.rep_nr}</c:otherwise></c:choose></c:if></td>
			</tr>
			<tr>
				<th>Total (%)</th>
				<td>${PosisjonPros}</td>
			</tr>
		</c:if>
			</c:forEach>
		</tfoot>

		 <tbody>
			<c:forEach items="${posisjon}" var="posisjon" >
			   <c:if test="${stortingperiode.storting_periodekode == posisjon.storting_periodekode}">
			  	   <!--- korrigerer for navneendringer i regjerinsposisjonsparti--->
					<c:if test="${no}">
                     <c:choose><c:when test="${posisjon.partinavn=='Senterpartiet' && stortingperiode.storting_periodekode <106 } "><c:set var="partinavn" value="Bondepartiet"></c:set>  </c:when><c:otherwise><c:set var="partinavn" value="${posisjon.partinavn}"></c:set>  </c:otherwise></c:choose>
			        </c:if>
					<c:if test="${en}">
                     <c:choose><c:when test="${posisjon.partinavn_en=='Centre Party' && stortingperiode.storting_periodekode <106 } "><c:set var="partinavn" value="Agrarian Party"></c:set>  </c:when><c:otherwise><c:set var="partinavn" value="${posisjon.partinavn_en}"></c:set>  </c:otherwise></c:choose>
                    </c:if>
			       <tr>
				<c:if test="${no}"><th>${partinavn}</th></c:if><c:if test="${en}"><th>${partinavn}</th></c:if>
				   <td><c:if test="${no}"><c:choose><c:when test="${partinavn =='Venstre' && stortingperiode.storting_periodekode ==65}">75</c:when><c:otherwise>${posisjon.rep_nr}</c:otherwise></c:choose></c:if>
                   <c:if test="${en}"><c:choose><c:when test="${partinavn_en == 'Liberal Party' &&  stortingperiode.storting_periodekode ==65}">75</c:when><c:otherwise>${posisjon.rep_nr}</c:otherwise></c:choose> </c:if></td>
			     </tr>
		        </c:if>
		     </c:forEach>
		  </tbody>
	  </table>


	   <table class="Zebra">
		    <c:if test="${no}">
	     		<caption style="font-size:10px">Andre parti</caption>
	    	<thead>
			<tr>
				<th width="135">Parti</th>
				<th>Seter</th>
			</tr>
		</thead>
		</c:if>
		<c:if test="${en}">
			<caption style="font-size:10px">Other parties</caption>
		<thead>
			<tr>
				<th width="135">Party</th>
				<th>Seats</th>
			</tr>
		</thead>
		</c:if>
		<tfoot>
			<c:forEach items="${sumoposisjon}" var="sumoposisjon" >
            <c:if test="${stortingperiode.storting_periodekode == sumoposisjon.storting_periodekode}">

					<cfset oPosPros=  ${stortingperiode.storting_periodekode}*100)/${stortingperiode.sumstortingsseter}>

					<c:set var="oPosisjonPros2" value="${sumoposisjon.rep_nr}"></c:set>
					<c:set var="oPosisjonPros3" value="${(oPosisjonPros2/stortingperiode.sumstortingsseter)*100}"></c:set>
					<c:set var="oPosisjonPros" ><fmt:formatNumber type="number" maxFractionDigits="1"  value="${oPosisjonPros3}" /></c:set>

			<tr>
				<th>Total (N) </th>
				<td>${sumoposisjon.rep_nr}</td>
			</tr>
			<tr>
				<th>Total (%)</th>
				<td>${oPosisjonPros}</td>
			</tr>
				</c:if>
			</c:forEach>
		</tfoot>
		<tbody>
          <c:forEach items="${oposisjon}" var="oposisjon" >
			 <c:if test="${stortingperiode.storting_periodekode == oposisjon.storting_periodekode}">
				<!--- korrigerer for navneendringer i regjerinsposisjonsparti--->
					<c:if test="${no}">
                     <c:choose><c:when test="${oposisjon.partinavn=='Senterpartiet' && stortingperiode.storting_periodekode < 106 } "><c:set var="opartinavn" value="Bondepartiet"></c:set>  </c:when>
                     <c:when test="${oposisjon.partinavn=='Senterpartiet' && stortingperiode.storting_periodekode < 118 } "><c:set var="opartinavn" value="Sosialistisk Folkeparti"></c:set>  </c:when>
                     <c:when test="${oposisjon.partinavn=='Senterpartiet' && stortingperiode.storting_periodekode ==118 } "><c:set var="opartinavn" value="Anders Langes parti"></c:set>  </c:when>
                     <c:otherwise><c:set var="opartinavn" value="${oposisjon.partinavn}"></c:set>  </c:otherwise></c:choose>
			        </c:if>
					<c:if test="${en}">
                     <c:choose><c:when test="${oposisjon.partinavn_en=='Centre Party' && stortingperiode.storting_periodekode < 106 } "><c:set var="opartinavn" value="Agrarian Party"></c:set>  </c:when>
                     <c:when test="${oposisjon.partinavn_en=='Centre Party' && stortingperiode.storting_periodekode < 118 } "><c:set var="opartinavn" value="Socialist People's Party"></c:set>  </c:when>
                     <c:when test="${oposisjon.partinavn_en=='Centre Party' && stortingperiode.storting_periodekode == 118 } "><c:set var="opartinavn" value="Anders Lange's Party"></c:set>  </c:when>
                     <c:otherwise><c:set var="opartinavn" value="${oposisjon.partinavn_en}"></c:set>  </c:otherwise></c:choose>
                    </c:if>
			<tr>
				<th>${opartinavn}</th>
				<td>${oposisjon.rep_nr}</td>
			</tr>
		    </c:if>
	      </c:forEach>
	     </tbody>
       </table>
     </c:forEach>
</c:if>

<!-- Norske_regjeringer_ministerier -->
 <c:forEach items="${regjeringsbeskrivelse}" var="regjeringsbeskrivelse" >

   <c:if test="${no}">
	 <c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 10}">
			<br><h4>Stortingsperiode 1889-91</h4>
		<table class="zebra">
				<caption style="font-size:10px">Regjeringsparti</caption>
			<thead>
				<tr>
					<th width="135">Parti</th>
					<th>Seter</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>24</td></tr>
				<tr>
					<th>Total (%)</th>
					<td>21.1</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Moderate Venstre</th>
					<td>24</td>
				</tr>
			</tbody>
		</table>

		<p></p>

		<table class="zebra">
				<caption style="font-size:10px">Andre parti</caption>
			<thead>
				<tr>
					<th width="135">Parti</th>
					<th>Seter</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>90</td>
				<tr>
					<th>Total (%)</th>
					<td>78.9</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Venstre</th>
					<td>37</td>
				</tr>
				<tr>
					<th>Høyre</th>
					<td>49</td>
				</tr>
				<tr>
					<th>Manglende parti</th>
					<td>4</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 35}">
			<br><h4>Londonregjeringen (1940-1945)</h4>
		<table class="zebra">
				<caption style="font-size:10px">Regjeringsparti</caption>
			<thead>
				<tr>
					<th width="135">Parti</th>
					<th>Seter</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>24</td>
				</tr>
				<tr>
					<th>Total (%)</th>
					<td>98.0</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Arbeiderpartiet</th>
					<td>70</td>
				</tr>
				<tr>
					<th>Høyre</th>
					<td>36</td>
				</tr>
				<tr>
					<th>Venstre</th>
					<td>23</td>
				</tr>
				<tr>
					<th>Bondepartiet</th>
					<td>18</td>
				</tr>
			</tbody>
		</table>

	<p></p>

		<table class="zebra">
				<caption style="font-size:10px">Andre parti</caption>
			<thead>
				<tr>
					<th width="135">Parti</th>
					<th>Seter</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>3</td>
				</tr>
				<tr>
					<th>Total (%)</th>
					<td>2.0</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Kristelig Folkeparti</th>
					<td>2</td>
				</tr>
				<tr>
					<th>Samfundspartiet</th>
					<td>1</td>
				</tr>
			</tbody>
		</table>
	</c:if>
</c:if>

<c:if test="${en}">
	<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 10}">
			<br><h4>Electoral period 1889-91</h4>
		<table class="zebra">
				<caption style="font-size:10px">Governmnet</caption>
			<thead>
				<tr>
					<th width="135">Party</th>
					<th>Seats</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>24</td></tr>
				<tr>
					<th>Total (%)</th>
					<td>21.1</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Moderate Liberal Party</th>
					<td>24</td>
				</tr>
			</tbody>
		</table>

		<p></p>

		<table class="zebra">
				<caption style="font-size:10px">Other parties</caption>
			<thead>
				<tr>
					<th width="135">Party</th>
					<th>Seats</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>90</td>
				<tr>
					<th>Total (%)</th>
					<td>78.9</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Liberal Party</th>
					<td>37</td>
				</tr>
				<tr>
					<th>Conservative Party</th>
					<td>49</td>
				</tr>
				<tr>
					<th>Missing party</th>
					<td>4</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${regjeringsbeskrivelse.regjering_reg_kode == 35}">
			<br><h4>London Government (1940-1945)</h4>
		<table class="zebra">
				<caption style="font-size:10px">Government</caption>
			<thead>
				<tr>
					<th width="135">Party</th>
					<th>Seats</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>24</td>
				</tr>
				<tr>
					<th>Total (%)</th>
					<td>98.0</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Labour Party</th>
					<td>70</td>
				</tr>
				<tr>
					<th>Conservative Party</th>
					<td>36</td>
				</tr>
				<tr>
					<th>Liberal Party</th>
					<td>23</td>
				</tr>
				<tr>
					<th>Agrarian Party</th>
					<td>18</td>
				</tr>
			</tbody>
		</table>

	<p></p>

	<table class="zebra">
				<caption style="font-size:10px">Other parties</caption>
			<thead>
				<tr>
					<th width="135">Party</th>
					<th>Seats</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Total (N)</th>
					<td>3</td>
				</tr>
				<tr>
					<th>Total (%)</th>
					<td>2.0</td>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<th>Christian Democratic Party</th>
					<td>2</td>
				</tr>
				<tr>
					<th>Society's Party</th>
					<td>1</td>
				</tr>
			</tbody>
		</table>
	</c:if>
</c:if>
</c:forEach>
<!-- Parlamentarisk grunnlag - Slutt -->

<!--- Regjeringsansiennitet og statssekretærer, den sittende regj ekskl. - Start --->

<c:forEach items="${regjeringsbeskrivelse}" var="regjeringsbeskrivelse" >

<c:if test="${no}">
		<c:if test="${regjeringsbeskrivelse.regjering_min_reg==4}">
			<h3>Ansiennitet</h3> <p>Ministeriet styrte Norge i <strong>${regjeringsbeskrivelse.dato}</strong> dager.</p>
		</c:if>

		<c:if test="${regjeringsbeskrivelse.regjering_min_reg==5}">
		 <h3>Ansiennitet</h3> <p>Regjeringen styrte Norge i <a href="<p:url value="/regjering/regjeringsvarighet/"/>">${regjeringsbeskrivelse.dato}</a> dager.</p>
		</c:if>

		<cfset StartUt=#datepart("yyyy",Start)# & "-" & #datepart("m",Start)# & "-" & #datepart("d" ,Start)# > <!--- triks for å omgå problemene som oppstår som følge av at sql-servereren, den hunden, legger til klokkeslett i datovariabler (her: 'start' og 'slutt') jf. også CONVERT(DATETIME, '#Regstart#', 102) i I28--->
					<cfset SluttUt=#datepart("yyyy",Slutt)# & "-" & #datepart("m",Slutt)# & "-" & #datepart("d" ,Slutt)# >

		<c:if test="${regjeringsbeskrivelse.regjering_reg_kode >=36}">
				<h3>Statssekretærer</h3> <p><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${regjeringsbeskrivelse.startdato}&regslutt=${regjeringsbeskrivelse.sluttdato}&partierkode=${regjeringsbeskrivelse.partikode}"/>"> Klikk her</a> for å se regjeringens statssekretærer.</p>
		</c:if>
	</c:if>

	<c:if test="${en}">
	<c:if test="${regjeringsbeskrivelse.regjering_min_reg==4}">
		<h3>Days in Office</h3> <p>The Government lasted for <strong>${regjeringsbeskrivelse.dato}</strong> days.</p>
	</c:if>

	<c:if test="${regjeringsbeskrivelse.regjering_min_reg==5}">
	<h3>Days in Office</h3> <p>The Government lasted for <a href="<p:url value="/regjering/regjeringsvarighet/"/>">${regjeringsbeskrivelse.dato}</a> days.</p>
	</c:if>

		<cfset StartUt=#datepart("yyyy",Start)# & "-" & #datepart("m",Start)# & "-" & #datepart("d" ,Start)# > <!--- triks for å omgå problemene som oppstår som følge av at sql-servereren, den hunden, legger til klokkeslett i datovariabler (her: 'start' og 'slutt') jf. også CONVERT(DATETIME, '#Regstart#', 102) i I28--->
		<cfset SluttUt=#datepart("yyyy",Slutt)# & "-" & #datepart("m",Slutt)# & "-" & #datepart("d" ,Slutt)# >


	<c:if test="${regjeringsbeskrivelse.regjering_reg_kode >=36}">
	<h3>State Secrataries</h3> <p><a href="<p:url value="/regjering/statssekretaerarkivet/statssekretarregjeringsvisbeskrivelse/?regstart=${regjeringsbeskrivelse.startdato}&regslutt=${regjeringsbeskrivelse.sluttdato}&partierkode=${regjeringsbeskrivelse.partikode}"/>"> Click here</a>  for an overview of this Government's State Secrataries.</p>
	</c:if>
	</c:if>

</c:forEach>


</div>

<div id="main" class="superwide">
	<div id="main" class="superwide">
		<div class="breadcrumbs">
			<c:if test="${no}">
				Du er her:
				<a href="http://www.nsd.uib.no/polsys/">PolSys</a>
				><a href="<p:url value="/regjering/" />">Regjering</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/regjeringer/" />">Regjeringer</a>
				> Regjeringsbeskrivelse
			</c:if>
			<c:if test="${en}">
				You are here:
				<a href="http://www.nsd.uib.no/polsys/">PolSys</a>
				><a href="<p:url value="/regjering/" />">Government</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
				><a href="<p:url value="/regjering/statsraadsarkivet/regjeringer/" />">Governments</a>
				> Government description
			</c:if>
		</div>

<div>
<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${param.regstart}&regslutt=${param.regslutt}&stortingperiodekode=${param.stortingperiodekode}&partierkode=${param.partierkode}" />">View this page in English.</a></c:if>
<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${param.regstart}&regslutt=${param.regslutt}&stortingperiodekode=${param.stortingperiodekode}&partierkode=${param.partierkode}" />">View this page in Norwegian.</a></c:if>
</div>

		<table class="data text zebra" >
		<caption>
			<c:forEach items="${regjeringsbeskrivelse}" var="regjeringsbeskrivelse" >

			 <h2>${regjeringsbeskrivelse.ministerium} (${regjeringsbeskrivelse.start}-${regjeringsbeskrivelse.slutt})</h2><br>
                <c:if test="${regjeringsbeskrivelse.regjering_min_reg==5}">
                  ${regjeringsbeskrivelse.partinavn}
                </c:if>
			</c:forEach>
		</caption>
    <thead>
		<c:if test="${no}">
        <tr>
            <th>Statsråder</th>
            <th>Virkeperiode</th>
        </tr>
		</c:if>
		<c:if test="${en}">
        <tr>
            <th>Ministers</th>
            <th>In office</th>
        </tr>
		</c:if>
    </thead>
	<tbody>
	<c:forEach items="${statsraadsbeskrivelse}" var="statsraadsbeskrivelse" >

					<tr>
						<td> <h3>${statsraadsbeskrivelse.statsraadnavn} </h3></td>
                    </tr>

					<tr>
						<td>
							${statsraadsbeskrivelse.stilling}
					        <a href="<p:url value="/person/politikerbiografi/?person_id=${statsraadsbeskrivelse.personId}&p_info=personalia"/>">${statsraadsbeskrivelse.fornavn} ${statsraadsbeskrivelse.etternavn}</a>
							(${statsraadsbeskrivelse.foedt}-<c:if test="${statsraadsbeskrivelse.doed!=0}">${statsraadsbeskrivelse.doed}</c:if>)
                            <c:if test="${statsraadsbeskrivelse.antall_parti_i_reg > 1 && statsraadsbeskrivelse.regjering_reg_kode!=10 }">
                             ,${statsraadsbeskrivelse.partikortnavn}
                             </c:if>
                               <br> <c:if test="${statsraadsbeskrivelse.eksternkommentar !=null}"><em>${statsraadsbeskrivelse.eksternkommentar}</em><p></p> </c:if>
						</td>
						<!-- <c:if test="${no}"> -->
							<!-- </c:if>

						<c:if test="${en}">
							<td>	#stilling_avvik#
									<CFIF IsDefined("polsysindex")><cfset spfil="index.cfm"><cfelse><cfset spfil="I32.cfm"></cfif>
									<a href="#spfil#?urlname=#lcase(urlname)#&lan=#lan#&MenuItem=#MenuItem#&ChildItem=#ChildItem#&State=#State#&UttakNr=32&person=#person#">#Fornavn# #Navn#</a>
									(#Foedt#-#doedsaar#)<cfif #antall_partier_i_reg# gt "1" and #regkode# neq "10">, <cfif #Eintaltekst_forkorting# eq "Sp" and #start# lt "01.01.1959" AND #lan# eq "">Bp<cfelse>#Eintaltekst_forkorting#</cfif></cfif>  <br> <em>${statsraadsbeskrivelse.eksternkommentar}</em><c:if test="${statsraadsbeskrivelse.eksternkommentar !=''}"></c:if>
							</td>
						</c:if>
						-->
						<td valign="top">${statsraadsbeskrivelse.start}-${statsraadsbeskrivelse.slutt}</td>
					</tr>
			</c:forEach>
	</tbody>
</table>




</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>