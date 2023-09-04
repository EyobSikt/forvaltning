<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="cache" uri="http://www.opensymphony.com/oscache" %>

<%--
 - Author(s): HVB
 - Copyright NSD
 - Description: Inneholder topp-innholdet for polsys-sidene.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<c:set var="title" value="PolSys - NSD" />
<c:set var="description" value="PolSys omfatter data fra NSDs arkiver om det politiske system." />
<c:if test="${en}"><c:set var="description" value="PolSys is NSD's archives about the Norwegian political system." /></c:if>

<c:if test="${no}">
<c:if test="${!(empty param.tittelNo)}"><c:set var="title" value="${param.tittelNo} - NSD" /></c:if>
<c:if test="${!(empty param.beskrivelseNo)}"><c:set var="description" value="${param.beskrivelseNo}" /></c:if>
</c:if>
<c:if test="${en}">
<c:if test="${!(empty param.tittelEn)}"><c:set var="title" value="${param.tittelEn} - NSD" /></c:if>
<c:if test="${!(empty param.beskrivelseEn)}"><c:set var="description" value="${param.beskrivelseEn}" /></c:if>
</c:if>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${en ? "en" : "no"}" lang="${en ? "en" : "no"}">

<head>
<title>${title}</title>
<meta name="title" content="${title}" />
<meta name="description" content="${description}" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<script type="text/javascript" src="/common/js/zebra.js"></script>


<link rel="stylesheet" type="text/css" media="screen" href="/common/css/reset-fonts-grids.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/common.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/custom.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/common/css/custom_fvdb.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/test/css/histogram.css" />

<c:if test="${en}"><link rel="stylesheet" type="text/css" media="screen" href="/common/polsys/eng/css/custom.css" /></c:if>
<!-- compliance patch for microsoft browsers -->
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie6_workarounds.css" />
<![endif]-->
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie7_workarounds.css" />
<![endif]-->
<link rel="stylesheet" type="text/css" media="print" href="/common/css/common_print.css" />


<script type="text/javascript" src="/common/polsys/js/polsyssolr.js" ></script>
<link rel="stylesheet" type="text/css" media="screen" href="/common/polsys/css/polsyssolr.css" />

<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-8748379-1']);
  _gaq.push (['_gat._anonymizeIp']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
</head>

<body>

<div id="banner">

<div id="nav_site">
<ul>
<c:if test="${no}">
<li class="left"><a href="/">Hjem</a></li>
<%--<li><a href="https://nsd.no/polsys/hjelp/kontakt.html">Kontakt</a></li>--%>
    <li><a href="/forvaltning/dokumentasjon/kontakt.html">Kontakt</a></li>
<li><a href="/en/">English pages</a></li>
</c:if>
<c:if test="${en}">
<li class="left"><a href="/en/">Home</a></li>
<%--<li><a href="https://nsd.no/polsys/en/help/contact.html">Contact</a></li>--%>
    <li><a href="/civilservice/contact.html">Contact</a></li>
<li><a href="/">Norwegian pages</a></li>
</c:if>
</ul>
</div>

<a href="/" id="nav_logo"></a>
<a href="/" id="nav_title"></a>

<div id="nav_nsd">
    <%--
    <ul>
    <c:if test="${no}">
    <li class="left"><a href="https://nsd.no/">NSD</a></li>
    <li><a href="https://nsd.no/data/index.html">Datatjenester</a></li>
    <li><a href="https://nsd.no/nsd/programvare.html">Programvare</a></li>
    <li><a href="https://www.samfunnsveven.no/">Undervisning</a></li>
    <li><a href="http://www.nsd.uib.no/nsd/internasjonalt.html">Internasjonalt</a></li>
</c:if>
<c:if test="${en}">
    <li class="left"><a href="https://nsd.no/nsd/english/index.html">NSD</a></li>
    <li><a href="https://nsd.no/nsd/english/datatjenester.html">Data Services</a></li>
    <li><a href="https://nsd.no/nsd/english/programvare.html">Software</a></li>
    <li><a href="https://nsd.no/nsd/english/elearning.html">eLearning</a></li>
    <li><a href="http://www.nsd.uib.no/nsd/english/internasjonalt.html">International</a></li>
</c:if>
</ul>
--%>
</div>

</div>

<c:set var="pvalue" value="/polsys/" />
<c:if test="${en}">
<c:set var="pvalue" value="/polsys/en/" />
</c:if>

<c:if test="${param.navigation != null}">
<c:set var="pvalue" value="${param.navigation}" />
</c:if>


<div id="nav_project">

    <!-- Google cse -->
    <div class="searchform">
        <form action="/polsys/resultat.html" id="cse-search-box">
            <div>
                <input type="hidden" name="cx" value="001188589737219495033:nozcznykmsi" />
                <input type="hidden" name="cof" value="FORID:10" />
                <input type="hidden" name="ie" value="UTF-8" />
                <input type="text" name="q" size="20" />
                <input type="submit" name="sa" value="Søk" />
            </div>
        </form>
        <script type="text/javascript" src="//www.google.com/cse/brand?form=cse-search-box&lang=no"></script>
    </div>

    <ul><li><a href="/polsys/hjelp/">Det politiske system</a><ul><li><a href="/polsys/hjelp/om.html">Om dataene</a></li><li><a href="/polsys/hjelp/utlevering.html">Utlevering av data</a></li><li><a href="/polsys/hjelp/bruk.html">Vilkår for bruk av data</a></li><li><a href="https://nsd.no/nsd/kontakt.html">Kontakt</a></li><li><a href="/polsys/hjelp/english.html">English pages</a></li></ul><h6></h6></li><li><a href="/polsys/storting/">Storting</a></li><li><a href="/polsys/regjering/">Regjering</a></li><li><a href="/polsys/parti/">Parti</a></li><li><a href="/polsys/forvaltning/">Forvaltningsdatabasen</a></li><li><a href="/polsys/andrearkiv/">Andre arkiv</a></li><li><a href="/polsys/nyheter/">Nyheter</a></li></ul><h6></h6>

    <div id="contact">
        <address>
            <div>NSD</div>
            <div>Harald Hårfagres gate 29</div>
            <div>5007 Bergen, Norway</div>
            <div>Tel +47 55 58 21 17</div>
            <div>polsys@nsd.no</div>
        </address>
    </div>

  <%--  <cache:cache key="nav_project_${pvalue}" time="7200">
<c:import url="http://www.nsd.uib.no/polsys${en ? '/en' : ''}/nav_project.html">
    <c:param name="nav_project_uri" value="${pvalue}" />
</c:import>
</cache:cache>--%>

</div>
