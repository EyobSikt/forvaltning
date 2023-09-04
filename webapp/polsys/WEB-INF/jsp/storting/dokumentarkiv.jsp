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
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Forvaltningsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>

 

<div id="sidebar">

</div>


<div id="main" class="superwide">
<div>
<c:if test="${no}">
Du er her:
<a href="/polsys/">PolSys</a>
> <a href="<p:url value="/storting/" />"> Storting </a>
> Frekvenser
</c:if>
	<c:if test="${en}">
		You are here:
		<a href="<p:url value="/"/>">PolSys</a>
		> <a href="<p:url value="/storting/" />"> Parliament </a>
		> Frequencies
	</c:if>
</div>
	<div>
		<c:if test="${no}"><a href="<m:url value="/en/storting/dokumentarkiv/" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/storting/dokumentarkiv/" />">View this page in Norwegian.</a></c:if>
	</div>
	<c:if test="${no}"> <h1>Frekvenser</h1></c:if>
	<c:if test="${en}"><h1>Frequencies </h1>
		<h3>This page is not translated to English</h3>
		<p>----------------------------------------------</p>
	</c:if>


<!--------------------- INGRESS --------------------->
<p>
	Totalt er det registrer 900 forslag fra og med vårsesjonen 1984 til og med vårsesjonen 1998. Tabell 1 viser at antallet private forslag har vært stigende gjennom hele perioden.
</p>

<!--------------------- BRØDTEKST --------------------->

<table class="zebra">
<caption>
	Tabell 1: Antall private forslag for hver sesjon (årstallet indikerer første år i sesjonen, 1984 står altså for sesjonen 1984-85)
</caption>

<thead>
<tr>
	<th>År</th>
	<th>1984</th>
	<th>1985</th>
	<th>1986</th>
	<th>1987</th>
	<th>1988</th>
	<th>1989</th>
	<th>1990</th>
	<th>1991</th>
	<th>1992</th>
	<th>1993</th>
	<th>1994</th>
	<th>1995</th>
	<th>1996</th>
	<th>1997</th>
</tr>
</thead>

<tbody>
<tr>
	<th>Antall forslag</th>
	<td>15</td>
	<td>21</td>
	<td>28</td>
	<td>51</td>
	<td>51</td>
	<td>51</td>
	<td>63</td>
	<td>46</td>
	<td>42</td>
	<td>79</td>
	<td>103</td>
	<td>115</td>
	<td>111</td>
	<td>124</td>
</tr>
</tbody>
</table>

<p>
	Langt de fleste forslagene blir sendt videre til en av fagkomiteene for videre behandling der, jf. tabell 2.
</p>

<table class="zebra">
<caption>
	Tabell 2: Prosent av forslagene som blir sendt videre til en av komiteene
</caption>

<thead>
<tr>
	<th>År</th>
	<th>1984</th>
	<th>1985</th>
	<th>1986</th>
	<th>1987</th>
	<th>1988</th>
	<th>1989</th>
	<th>1990</th>
	<th>1991</th>
	<th>1992</th>
	<th>1993</th>
	<th>1994</th>
	<th>1995</th>
	<th>1996</th>
	<th>1997</th>
</tr>
</thead>
<tbody>
<tr>
	<th width="90">Prosentandel sendt til komité</th>
	<td>93</td>
	<td>91</td>
	<td>100</td>
	<td>77</td>
	<td>84</td>
	<td>90</td>
	<td>87</td>
	<td>96</td>
	<td>88</td>
	<td>94</td>
	<td>93</td>
	<td>87</td>
	<td>70</td>
	<td>76</td>
</tr>
</tbody>
</table>

<p>
	Størstedelen av forslagene blir avvist etter komitebehandlingen. Bare elleve prosent av forslagene blir enten bifalt eller delvis bifalt, jf. tabell 3.
</p>

<table class="zebra">
<caption>
	Tabell 3: Forslagenes skjebne etter komitebehandlingen
</caption>

<thead>
<tr>
	<th>Komiteskjebne</th>
	<th>Frekvens</th>
	<th>Prosent</th>
</tr>
</thead>
<tbody>
<tr>
	<th>Avvist</th>
	<td>341</td>
	<td>39</td>
</tr>
<tr>
	<th>Bifalt</th>
	<td>60</td>
	<td>7</td>
</tr>
<tr>
	<th>Delvis bifalt</th>
	<td>33</td>
	<td>4</td>
</tr>
<tr>
	<th>Vedlagt protokoll</th>
	<td>179</td>
	<td>20</td>
</tr>
<tr>
	<th>Oversendt regjering</th>
	<td>97</td>
	<td>11</td>
</tr>
<tr>
	<th>Annen behandling</th>
	<td>14</td>
	<td>2</td>
</tr>
<tr>
	<th>Ikke behandlet</th>
	<td>44</td>
	<td>5</td>
</tr>
<tr>
	<th>Uaktuell</th>
	<td>111</td>
	<td>13</td>
</tr>
</tbody>
</table>








</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>