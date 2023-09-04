<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
 - Author(s): HVB
 - Copyright Notice: Copyright NSD
 - Description: Side som vises ved exceptions/errors dvs. ved HTTP 500-feil.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>PolSys</title>
<meta name="title" content="Data om det politiske system - PolSys" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/reset-fonts-grids.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/common.css" />
<link rel="stylesheet" type="text/css"  media="screen" href="/common/polsys/css/custom.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/common/css/custom_fvdb.css" />
<c:if test="${en}"><link rel="stylesheet" type="text/css" media="screen" href="/common/polsys/eng/css/custom.css" /></c:if>
<!-- compliance patch for microsoft browsers -->
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie6_workarounds.css" />
<![endif]-->
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie7_workarounds.css" />
<![endif]-->
<link rel="stylesheet" type="text/css" media="print" href="/common/css/common_print.css" />
</head>

<body>

<div id="banner">

<div id="nav_site">
<ul>
<c:if test="${no}">
<li class="left"><a href="/">Hjem</a></li>
<li><a href="/forvaltning/dokumentasjon/kontakt.html">Kontakt</a></li>
<li><a href="/en/">English pages</a></li>
</c:if>
<c:if test="${en}">
<li class="left"><a href="/en/">Home</a></li>
<li><a href="/civilservice/contact.html">Contact</a></li>
<li><a href="/">Norwegian pages</a></li>
</c:if>
</ul>
</div>

<a href="/" id="nav_logo"></a>
<a href="/" id="nav_title"></a>

<div id="nav_nsd">
<ul>
<c:if test="${no}">
<%--<li class="left"><a href="https://nsd.no/">NSD</a></li>
<li><a href="https://nsd.no/data/index.html">Datatjenester</a></li>
<li><a href="https://nsd.no/nsd/programvare.html">Programvare</a></li>
<li><a href="https://www.samfunnsveven.no/">Undervisning</a></li>--%>
<%--<li><a href="https://nsd.no/nsd/internasjonalt.html">Internasjonalt</a></li>--%>
</c:if>
<c:if test="${en}">
<%--<li class="left"><a href="https://nsd.no/nsd/english/index.html">NSD</a></li>
<li><a href="https://nsd.no/nsd/english/datatjenester.html">Data Services</a></li>
<li><a href="https://nsd.no/nsd/english/programvare.html">Software</a></li>
<li><a href="https://nsd..no/nsd/english/elearning.html">eLearning</a></li>--%>
<%--<li><a href="https://nsd.no/nsd/english/internasjonalt.html">International</a></li>--%>
</c:if>
</ul>
</div>

</div>

<div id="nav_project">

<ul>
<li><a href="/${en ? 'en/' : ''}">Forvaltning</a></li>
</ul>

<%--<div id="contact">
<h1>NSD</h1>
<p>Harald Hårfagres gate 29</p>
<p>N-5007 Bergen, Norway</p>
<p>Tel +47-55 58 21 17</p>
<p>Fax +47-55 58 96 50</p>
<p>polsys@nsd.uib.no</p>
</div>--%>

</div>


<div id="main">

<h1>Serverfeil</h1>
<p>Det oppsto en feil på serveren. Vennligst prøv igjen senere.</p>

<h2>Server error</h2>
<p>A server error occurred. Please try again later.</p>

</div>
<!--
<div id="signature">
<p class="copyright">
Copyright &copy; 2010 Norsk samfunnsvitenskapelig datatjeneste AS
</p>
</div>
-->
<%--<div id="signature">
    <p class="copyright">
        Copyright © NSD - Norsk senter for forskningsdata •
        <a href="/nsd/personvern.html">Om personvern</a>
        •
        <a class="img_link" href="http://www.twitter.com/nsddata" target="_blank">
            <img src="/img/twitter_16.png" alt="">
        </a>
    </p>
</div>--%>

<div id="signature">
    <p class="copyright">
        Copyright © Sikt – Kunnskapssektorens tjenesteleverandør
        <a href="https://sikt.no/kontakt-oss" target="_blank">
            • Kontakt SIKT
        </a>
        <a href="https://sikt.no/personvernerklaering" target="_blank">
            • Personvernerklæring
        </a>
        <span> • Versjon 1.1</span>
    </p>
</div>

</body>
</html>
