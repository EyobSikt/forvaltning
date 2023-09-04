<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="cache" uri="http://www.opensymphony.com/oscache" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%--
 - Copyright SIKT
 - Description: Inneholder topp-innholdet for polsys-sidene.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<c:set var="title" value="Forvaltningsdatabasen - SIKT" />
<c:set var="description" value="Forvaltningsdatabasen til SIKT inneholder data om den norske statsforvaltningen." />
<c:if test="${en}"><c:set var="description" value="The Norwegian State Administration Database gives a detailed overview of the Norwegian state administration." /></c:if>

<c:if test="${no}">
<c:if test="${!(empty param.tittelNo)}"><c:set var="title" value="${param.tittelNo} - SIKT" /></c:if>
<c:if test="${!(empty param.beskrivelseNo)}"><c:set var="description" value="${param.beskrivelseNo}" /></c:if>
</c:if>
<c:if test="${en}">
<c:if test="${!(empty param.tittelEn)}"><c:set var="title" value="${param.tittelEn} - SIKT" /></c:if>
<c:if test="${!(empty param.beskrivelseEn)}"><c:set var="description" value="${param.beskrivelseEn}" /></c:if>
</c:if>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${en ? "en" : "no"}" lang="${en ? "en" : "no"}">

<head>
<c:import url="/WEB-INF/jspf/matomo.jsp"/>
<title>${title}</title>
<meta name="title" content="${title}" />
<meta name="description" content="${description}" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/common.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/custom.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/custom_fvdb.css" />" />
<c:if test="${en}"><link rel="stylesheet" type="text/css" media="screen" href="/css/eng/custom.css" /></c:if>
<%--<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/common_print.css" />" />--%>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/notification.css" />" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/style.css" />" />
<!-- compliance patch for microsoft browsers -->
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie6_workarounds.css" />
<![endif]-->
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie7_workarounds.css" />
<![endif]-->

<script type="text/javascript" src="<c:url value="/js/showhide.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/standardista-table-sorting.js" />"></script>

    <!-- Global site tag (gtag.js) - Google Analytics -->
   <%-- <script async src="https://www.googletagmanager.com/gtag/js?id=G-QNLPSBVYF6"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){window.dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'G-QNLPSBVYF6');
    </script>--%>

</head>

<body>
<%--<div class="notification-wrapper">
    <div class="notification"><p>Fra 1. januar 2022 er NSD en del av <a href="https://sikt.no">Sikt – Kunnskapssektorens tjenesteleverandør</a>.<br/> Våre tjenester vil i en overgangsperiode fortsatt finnes på nsd.no &lt;%&ndash;<i id="close" class="notification fas fa-times" title="Lukk"></i>&ndash;%&gt;</p></div>
</div>--%>

<div class="top-container top-container--topicpage">
    <div class="container">

            <h1 class="top-container__title">
                <a href="<c:url value="/" />">
                <img style='float:left; width: 170px; margin-right:10px;  '	src="<c:url value="/img/sikt.png" />"/>
                <span class="field field--name-title field--type-string field--label-hidden"> FORVALTNINGSDATABASEN</span>
                </a>
            </h1>
    <span id="menuToggle">
    <input type="checkbox" />
    <span></span>
    <span></span>
    <span></span>
    <ul id="menu">
	<c:if test="${no}">
        <a href="/"><li>Hjem</li></a>
        <a href="/forvaltning/sok.html"><li>Søk</li></a>
        <a href="<c:url value="/forvaltning/dokumentasjon/kontakt.html" />"><li>Kontakt</li></a>
        <a href="<m:url value="/en/" />"><li>English pages</li></a>
        <a href="https://sikt.no/" target="_blank"><li>Sikt</li></a>
    </c:if>
    <c:if test="${en}">

    <a href="/en/"><li>Home</li></a>
    <a href="/civilservice/contact.html"><li>Contact</li></a>
    <a href=href="/forvaltning/dokumentasjon/kontakt.html"><li>Kontakt</li></a>
    <a href="<m:url value="/" />"><li>Norwegian pages</li></a>
    <a href="https://sikt.no/" target="_blank"><li>Sikt</li></a>
    </c:if>
    </ul>
  </span>


        <div class="top-container__description"></div>
        <ul class="top-container__inline-links">
        </ul>
    </div>
</div>

<div id="bannerold">

<%--<div id="nav_site">
<ul>
<c:if test="${no}">
<li class="left"><a href="/">Hjem</a></li>
<li><a href="/forvaltning/sok.html">Søk</a></li>
    <li><a href="/forvaltning/dokumentasjon/kontakt.html">Kontakt</a></li>
    <li><a href="<m:url value="/en/" />">English pages</a></li>
</c:if>
<c:if test="${en}">
<li class="left"><a href="/en/">Home</a></li>
    <li><a href="/civilservice/contact.html">Contact</a></li>
    <li><a href="<m:url value="/" />">Norwegian pages</a></li>
</c:if>
</ul>
</div>--%>

<%--<a href="/" id="nav_logo"></a>
<a href="/" id="nav_title"></a>--%>

<c:if test="${en}">
<div id="nav_nsd">
<%--<ul>
<li class="left"><a href="http://www.nsd.uib.no/nsd/english/index.html">NSD</a></li>
<li><a href="http://www.nsd.uib.no/nsd/english/datatjenester.html">Data Services</a></li>
<li><a href="http://www.nsd.uib.no/nsd/english/programvare.html">Software</a></li>
<li><a href="http://www.nsd.uib.no/nsd/english/elearning.html">eLearning</a></li>
<li><a href="http://www.nsd.uib.no/nsd/english/internasjonalt.html">International</a></li>
</ul>--%>
</div>
</c:if>

</div>

<c:set var="pvalue" value="/polsys/forvaltning/" />
<c:if test="${en}">
<c:set var="pvalue" value="/polsys/en/civilservice/" />
</c:if>

<div id="nav_project">

    <div class="navigation">
        <ul>
            <li><a href="/">Forvaltningsdatabasen</a>
                <ul>
                    <li><a href="/forvaltning/sok.html">Søk i databasen</a></li>
                    <li><a href="/dokumentasjon/">Dokumentasjon</a></li>
                    <li><a href="/forvaltning/dokumentasjon/om.html">Om databasen</a></li>
                    <li><a href="/forvaltning/dokumentasjon/data.html">Tilgang til data</a></li>
                    <%--<li><a href="/polsys/forvaltning/dokumentasjon/kontakt.html">Kontakt oss</a></li>--%>
                    <li><a href="/forvaltning/dokumentasjon/kontakt.html">Kontakt</a></li>
                </ul>
            </li>

            <li><a href="/forvaltning/forvaltningsdatabasen.html">Enheter</a></li>
            <li><a href="/forvaltning/forvaltningslitteratur.html">Litteratur</a></li>
            <li><a href="/forvaltning/statsansatte.html">Statsansatte</a></li>
            <li><a href="/forvaltning/utvalgsarkivet.html">Utvalg</a></li>
        </ul>
    </div>

    <div id="contact">
        <address>
            <%--<div>NSD</div>
            <div>Harald Hårfagres gate 29</div>
            <div>5007 Bergen, Norway</div>
            &lt;%&ndash;<div style="margin-top: 4px;"><a href="/polsys/forvaltning/dokumentasjon/kontakt.html">Kontakt oss</a></div>&ndash;%&gt;
            <div style="margin-top: 4px;"><a href="https://nsd.no/nsd/kontakt.html">Kontakt oss</a></div>--%>
        </address>
    </div>

<%--<cache:cache key="nav_project_${pvalue}" time="7200">
<c:import url="http://www.nsd.uib.no/polsys${en ? '/en' : '/forvaltning'}/nav_project.html">
    <c:param name="nav_project_uri" value="${pvalue}" />
</c:import>
</cache:cache>
--%>

</div>
