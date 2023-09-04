<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
 - Copyright NSD
 - Description: Inneholder topp-innholdet for polsys-admin-sidene.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Forvaltning-admin - SIKT</title>
<meta name="title" content="Forvaltning-admin - NSD" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/reset-fonts-grids.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/common.css" />" />
<link rel="stylesheet" type="text/css"  media="screen" href="<c:url value="/css/custom.css" />" />
<link rel="stylesheet" type="text/css"  media="screen" href="<c:url value="/css/custom-admin.css" />" />
<link rel="stylesheet" type="text/css" media="print" href="<c:url value="/css/common_print.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/style.css" />" />
<script type="text/javascript" src="<c:url value="/js/zebra.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/standardista-table-sorting.js" />"></script>
</head>

<body>

<div class="top-container top-container--topicpage">
    <div class="container">

        <h1 class="top-container__title">
            <a href="<c:url value="/index.p" />">
                <img style='float:left; width: 170px; margin-right:10px;  '	src="<c:url value="/img/sikt.png" />"/>
                <span class="field field--name-title field--type-string field--label-hidden"> FORVALTNINGSDATABASEN - <span style="color: #552200">ADMIN</span></span>
            </a>
        </h1>
        <div class="top-container__description"></div>
        <ul class="top-container__inline-links">
        </ul>
    </div>
</div>



<div id="bannerold">
</div>

<div id="nav_project">

<ul>
<li><a href="<c:url value="/index.p" />">Admin</a>
<ul>
<li><a href="<c:url value="/felles/parametre.p" />">Parametre</a></li>
</ul>
</li>

<li><a href="<c:url value="/forvaltning/index.p" />">Forvaltning</a>
<ul>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_endringskode" />">Endringskoder</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_grunnenhet" />">Grunnenhet</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_nivaa" />">Nivaa</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_tilknytningsform" />">Tilknytningsform</a></li>
<li><a href="<c:url value="/forvaltning/dokliste.p?tabellnavn=t_forvaltning_cofog" />">COFOG</a></li>
<li><a href="<c:url value="/forvaltning/endringsnummer.p" />">Endringsnummer</a></li>
<li><a href="<c:url value="/forvaltning/relasjonenhet.p" />">Relasjonenheter</a></li>
<li><a href="<c:url value="/forvaltning/lovdata.p" />">Lovdata</a></li>
<li><a href="<c:url value="/forvaltning/litteraturliste.p" />">Litteratur</a></li>
<li><a href="<c:url value="/forvaltning/velgendring.p" />">Endring/Enhet</a></li>
<li><a href="<c:url value="/forvaltning/uploadfile.p" />">Last opp fil</a></li>
</ul>
</li>


</ul>

<div id="contact">
<h1>NSD</h1>
<p>Harald Hårfagres gate 29</p>
<p>N-5007 Bergen, Norway</p>
<p>Tel +47-55 58 21 17</p>
<p>Fax +47-55 58 96 50</p>
<p>polsys@nsd.uib.no</p>
</div>

</div>

