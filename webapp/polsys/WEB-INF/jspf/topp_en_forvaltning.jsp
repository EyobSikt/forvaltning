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
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/reset-fonts-grids.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/css/common.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/custom_fvdb.css" />
<c:if test="${en}"><link rel="stylesheet" type="text/css" media="screen" href="/common/polsys/eng/css/custom.css" /></c:if>

    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/style.css" />" />

<!-- compliance patch for microsoft browsers -->
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie6_workarounds.css" />
<![endif]-->
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" media="screen" href="/common/css/ie7_workarounds.css" />
<![endif]-->
<link rel="stylesheet" type="text/css" media="print" href="/common/css/common_print.css" />
<%--<script type="text/javascript" src="/common/js/zebra.js"></script>
<script type="text/javascript" src="/common/js/standardista-table-sorting.js"></script>
<script type="text/javascript" src="/common/polsys/js/showhide.js"></script>
<script type="text/javascript" src="/common/polsys/js/tableToExcel.js"></script>
<script type="text/javascript" src="/common/polsys/js/xlsx.full.min.js"></script>
<script type="text/javascript" src="/common/polsys/js/FileSaver.min.js"></script>
<script type="text/javascript" src="/common/polsys/js/Export2Excel.js"></script>--%>
<!--<script type="text/javascript" src="https://www.nsd.no/common/polsys/js/polsyssolr.js" ></script>-->

<%--<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-8748379-1']);
  _gaq.push (['_gat._anonymizeIp']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>--%>
</head>

<body>

<div class="top-container top-container--topicpage">
    <div class="container">

        <h1 class="top-container__title">
            <a href="<c:url value="/" />">
                <img style='float:left; width: 170px; margin-right:10px;  '	src="<c:url value="/img/sikt.png" />"/>
                <span class="field field--name-title field--type-string field--label-hidden"> The State Administration Database</span>
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
&lt;%&ndash;<li><a href="https://nsd.no/polsys/forvaltning/dokumentasjon/kontakt.html">Kontakt</a></li>&ndash;%&gt;
    <li><a href="/forvaltning/dokumentasjon/kontakt.html">Kontakt</a></li>
&lt;%&ndash;<li><a href="https://nsd.no/polsys/en/civilservice/">English pages</a></li>&ndash;%&gt;
    <li><a href="<m:url value="/en/" />">English pages</a></li>
</c:if>
<c:if test="${en}">
<li class="left"><a href="https://nsd.no/polsys/en/">Home</a></li>
&lt;%&ndash;<li><a href="https://nsd.no/polsys/en/help/contact.html">Contact</a></li>&ndash;%&gt;
    <li><a href="/civilservice/contact.html">Contact</a></li>
&lt;%&ndash;<li><a href="https://nsd.no/polsys/forvaltning/">Norwegian pages</a></li>&ndash;%&gt;
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
            <li><a href="/en/">Civil Service</a>
                <ul>
                    <li><a href="/civilservice/administrationdatabase.html">State units</a></li>
                    <li><a href="/civilservice/administrationliterature.html">Literature</a></li>
                    <li><a href="/civilservice/stateemployees.html">State employees</a></li>
                    <li><a href="/civilservice/contact.html">Contact</a></li>

                </ul>
            </li>
        </ul>
    </div>

    <div id="contact">
        <address>
           <%-- <div>NSD</div>
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
