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
	<a href="<p:url value="/"/>">PolSys</a>
> <a href="<p:url value="/storting/" />"> Storting </a>
> Kulturpolitiske frekvenstabell
</c:if>
	<c:if test="${en}">
		You are here:
		<a href="<p:url value="/"/>">PolSys</a>
		> <a href="<p:url value="/storting/" />"> Parliament </a>
		> Culture quotes - frequencies
	</c:if>
</div>
	<div>
		<c:if test="${no}"><a href="<m:url value="/en/storting/kulturpolitikkfrekvens/" />">View this page in English.</a></c:if>
		<c:if test="${en}"><a href="<m:url value="/storting/kulturpolitikkfrekvens/" />">View this page in Norwegian.</a></c:if>
	</div>
	<c:if test="${no}"> <h1>Kulturpolitiske sitat</h1></c:if>
	<c:if test="${en}"><h1>Culture quotes - frequencies </h1>
		<h3>This page is not translated to English</h3>
		<p>----------------------------------------------</p>
	</c:if>

<p>
	Frekvenstabellen viser sitater sortert på sitatkategori og tidsperiode. Tallene i tabellen viser følgelig
	antall sitat i aktuell tidsperiode innenfor hver enkel sitatkatogori. Ved
	&aring; klikke p&aring; frekvensene fremkommer de respektive sitatene.
</p>

<table class="zebra">
<caption>Frekvenstabell</caption>
<thead>
	<tr>
		<th width="230">SITATKATEGORIER\TID</th>
		<th width="40">1865</th>
		<th width="40">1924- 1929</th>
		<th width="40">1930- 1939</th>
		<th width="40">1940- 1949</th>
		<th width="40">1950- 1959</th>
		<th width="40">1960- 1969</th>
	</tr>
</thead>
<tbody>    
<tr>
		<th>Gjøre kulturgoder tilgjengelige*</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=2&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ab)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=2&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bb)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=2&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cb)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=2&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_db)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=2&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_eb)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=2&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fb)}</a></td>
	</tr>
	<tr>
		<th>Bevare kulturarven*</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=3&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ac)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=3&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bc)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=3&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cc)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=3&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dc)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=3&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ec)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=3&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fc)}</a></td>
	</tr>
	<tr>
		<th>Nasjonal kultur og nasjonal identitet*</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=4&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ad)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=4&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bd)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=4&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cd)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=4&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dd)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=4&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ed)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=4&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fd)}</a></td>
	</tr>
	<tr>
		<th>Folkeopplysning og allmenndanning*</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=5&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ae)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=5&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_be)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=5&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ce)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=5&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_de)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=5&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ee)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=5&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fe)}</a></td>
	</tr>
	<tr>
		<th>Kunstnerisk kvalitet</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=6&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_af)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=6&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bf)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=6&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cf)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=6&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_df)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=6&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ef)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=6&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ff)}</a></td>
	</tr>
	<tr>
		<th>Sosiale forhold</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=7&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ag)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=7&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bg)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=7&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cg)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=7&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dg)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=7&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_eg)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=7&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fg)}</a></td>
	</tr>
	<tr>
		<th>Om forskjellige kulturområder</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=8&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ah)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=8&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bh)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=8&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ch)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=8&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dh)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=8&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_eh)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=8&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fh)}</a></td>
	</tr>
	<tr>
		<th>Kultur og andre samfunnssektorer</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=9&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ai)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=9&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bi)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=9&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ci)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=9&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_di)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=9&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ei)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=9&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fi)}</a></td>
	</tr>
	<tr>
		<th>Kulturpolitiske synspunkter</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=10&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_aj)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=10&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bj)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=10&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cj)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=10&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dj)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=10&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ej)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=10&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fj)}</a></td>
	</tr>
	<tr>
		<th>Distriktspolitiske hensyn</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=11&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ak)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=11&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bk)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=11&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ck)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=11&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dk)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=11&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ek)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=11&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fk)}</a></td>
	</tr>
	<tr>
		<th>Norsk bygdekino</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=12&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_al)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=12&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bl)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=12&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cl)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=12&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dl)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=12&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_el)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=12&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fl)}</a></td>
	</tr>
	<tr>
		<th>Riksgalleriet</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=13&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_am)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=13&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bm)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=13&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cm)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=13&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dm)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=13&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_em)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=13&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fm)}</a></td>
	</tr>
	<tr>
		<th>Rikskonsertene</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=14&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_an)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=14&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bn)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=14&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_cn)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=14&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_dn)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=14&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_en)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=14&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fn)}</a></td>
	</tr>
	<tr>
		<th>Riksteateret</th>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=15&frasesjon=0&tilsesjon=1863&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_ao)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=15&frasesjon=1924&tilsesjon=1929&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_bo)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=15&frasesjon=1930&tilsesjon=1939&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_co)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=15&frasesjon=1940&tilsesjon=1949&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_do)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=15&frasesjon=1950&tilsesjon=1959&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_eo)}</a></td>
		<td><a href="<p:url value="/storting/kulturpolitikk/?sitsok1=&sitsok2=&sitsok3=&sok=S%C3%B8k&parti=-1&tema=15&frasesjon=1960&tilsesjon=1969&sortering=dbo.KultSitat.Dato"/>">${fn:length(kulturPolitikkFrekvens_fo)}</a></td>
	</tr>



</tbody>
</table>


</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>