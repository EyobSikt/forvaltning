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
	<c:if test="${no}"><h3>LASTE NED DATASETT</h3>Klikk <a href="<p:url value="/regjering/lastnedstatsraad/"/>">her</a> å laste ned data om norske statsråder.</c:if>
	<c:if test="${en}"><h3>DOWNLOAD DATASETS</h3> Click <a href="<p:url value="/regjering/lastnedstatsraad/"/>">here</a> to download data about norwegian ministers.</c:if>
</div>

<div id="main" class="superwide">

	<div class="breadcrumbs">
		<c:if test="${no}">
			Du er her:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Regjering</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Statsraadsarkivet</a>
			> Regjeringer
		</c:if>
		<c:if test="${en}">
			You are here:
			<a href="<p:url value="/"/>">PolSys</a>
			><a href="<p:url value="/regjering/" />">Government</a>
			><a href="<p:url value="/regjering/statsraadsarkivet/" />">Archive of Ministers</a>
			> Governments
		</c:if>
	</div>
<div>
<c:if test="${no}"><a href="<m:url value="/en/regjering/statsraadsarkivet/regjeringer/" />">View this page in English.</a></c:if>
<c:if test="${en}"><a href="<m:url value="/regjering/statsraadsarkivet/regjeringer/" />">View this page in Norwegian.</a></c:if>
</div>
<table class="zebra, text">
		<c:if test="${no}">
			<caption>Ministerier (1814-1884)</caption>
			<thead>
				<tr>
					<th>Startår</th>
					<th>Ministerium</th>
				</tr>
			</thead>
		 </c:if>
		<c:if test="${en}">
			<caption>Non-parliamentarian (1814-1884)</caption>
			<thead>
				<tr>
					<th>Start</th>
					<th>Government</th>
				</tr>
	</thead>
    </c:if>
	<tbody>
		<c:forEach items="${ministerier}" var="ministerier" >
		<tr>
			<td>${ministerier.aar}</td>
			<td>
				<a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${ministerier.start}&regslutt=${ministerier.slutt}"/>">${ministerier.ministerium}</a>  <script type="text/javascript">ttip = new YAHOO.widget.Tooltip("ttip", { context:"id2#kode#" });</script>
			</td>
		</tr>
		</c:forEach>
    </tbody>
</table>


<p></p>

	<table class="zebra, text">
		<c:if test="${no}">
			<caption>Regjeringer (1884-d.d.)</caption>
			<thead>
				<tr>
					<th>Startår</th>
					<th>Regjering</th>
					<th>Regjeringsparti</th>
					<th>Regjeringstype</th>
					<th>Årsak avgang</th>
				</tr>
			</thead>
		</c:if>
		<c:if test="${en}">
			<caption>Parliamentarian (1884-present)</caption>
			<thead>
				<tr>
					<th>Start</th>
					<th>Government</th>
					<th>Parties in government</th>
					<th>Type of government</th>
					<th>Cause of resignation</th>
				</tr>
			</thead>
		</c:if>
	<tbody>
		<c:forEach items="${regjeringer}" var="regjeringer" >
				<tr>
					<td>${regjeringer.aar}</td>
					 <td>
					<a href="<p:url value="/regjering/statsraadsarkivet/regjeringsbeskrivelse/?regstart=${regjeringer.start}&regslutt=${regjeringer.slutt}&stortingperiodekode=${regjeringer.stortingperiodekode}&partierkode=${regjeringer.partikode}"/>">${regjeringer.ministerium}</a>  <script type="text/javascript">ttip = new YAHOO.widget.Tooltip("ttip", { context:"id2#kode#" });</script>
                      </td>
						<c:if test="${regjeringer.blokk ==0}">
							<td>
							<font color="black" id="id#kode#" title="#partier_tekst#">
								${regjeringer.regjeringparti} <script type="text/javascript">ttip = new YAHOO.widget.Tooltip("ttip", { context:"id#kode#" });</script>
							</font>
							</td>
						</c:if>
						<c:if test="${regjeringer.blokk ==1}">
							<td>
							<font color="red" id="id#kode#" title="#partier_tekst#"> ${regjeringer.regjeringparti}<script type="text/javascript">ttip = new YAHOO.widget.Tooltip("ttip", { context:"id#kode#" });</script>
							</font>
							</td>
						</c:if>
						<c:if test="${regjeringer.blokk ==2}">
							<td>
							<font color="blue" id="id#kode#" title="#partier_tekst#"> ${regjeringer.regjeringparti}<script type="text/javascript">ttip = new YAHOO.widget.Tooltip("ttip", { context:"id#kode#" });</script>
							</font>
							</td>
						</c:if>
						<c:if test="${regjeringer.blokk ==3}">
							<td>
							<font color="deepskyblue" id="id#kode#" title="#partier_tekst#"> ${regjeringer.regjeringparti} <script type="text/javascript">ttip = new YAHOO.widget.Tooltip("ttip", { context:"id#kode#" });</script>
							</font>
							</td>
						</c:if>
					<td> ${regjeringer.regjeringtype} </td>
					<c:if test="${no}">
                    	<td> <a href="<p:url value="/regjering/statsraadsarkivet/regjeringsarsakavgang/?regjeringskode=${regjeringer.regjeringskode}&tidskode=${regjeringer.tidskode}"/>">${regjeringer.aarsakavgang}</a> </td>
					</c:if>
					<c:if test="${en}">
					<td>${regjeringer.aarsakavgang}</td>
					</c:if>
				</tr>
	    </c:forEach>
	</tbody>
	</table>
	<p></p>

	<c:if test="${no}">
		<p class="tabellnote" xmlns="">
		Merk: Fargekodingen på regjeringspartiene er en illustrasjon på hvorvidt regjeringen er såkalt "borgerlig" (blålig) eller "sosialistisk" (rød) på en høyre-venstre akse.
		I koalisjoner velger vi her å la fløypartiet definere fargen.
	</c:if>
	<c:if test="${en}">
		<p class="tabellnote" xmlns="">
		Note: The colours on the government parties illustrate whether or not the governments were left-leaning (red) or right-leaning (blueish). Black equals neutral.
		</p>
	</c:if>    


</div>
<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>