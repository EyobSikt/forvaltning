<%@ page isELIgnored ="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>


<head>
<title>${title}</title>
<meta name="title" content="${title}" />
<meta name="description" content="${description}" />
<meta name="keywords" content="${keywords}" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="<p:url value="/assets/common/css/reset-fonts-grids.css" />" type="text/css" media="screen" />
<link rel="stylesheet" href="<p:url value="/assets/common/css/common.css"/>" type="text/css" media="screen"/>
<link rel="stylesheet" href="<p:url value="/assets/common/css/custom.css"/>" type="text/css" media="screen"/>
    <link rel="stylesheet" href="<p:url value="/assets/common/css/custom-admin.css"/>" type="text/css" media="screen"/>
<link rel="stylesheet" href="<p:url value="/assets/common/css/common_print.css"/>" type="text/css" media="print" />
<link rel="stylesheet" href="<p:url value="/assets/common/css/form.css"/>" type="text/css" media="screen"/>

<script type="text/javascript" src="/internportal/js/p7_eqCols2_10.js"></script>
<script type="text/javascript" src="/internportal/js/zebra.js"></script>
<script type="text/javascript" src="/internportal/js/standardista-table-sorting-no.js"></script>

<link rel="stylesheet" type="text/css" href="<p:url value="/assets/common/css/stil_portal.css"/>">
<link rel="stylesheet" type="text/css" href="<p:url value="/assets/common/css/pvoportal.css"/>">
<script src="/pvoadmin/js/pvoportal.js" language="javascript" type="text/javascript"></script>

<script src="/pvoadmin/js/password.js" language="javascript" type="text/javascript"></script>


<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=utf-8" %>

</head>
<script type="text/javascript"  >
    function CloseWindow() {
        window.close();
    }

</script>

<div id="main">



        <div class="breadcrumbs">
            Du er her:
            <a href="http://nsd241/polsysadmin/">PolSys-Admin</a>
            >
            <a href="http://nsd241/polsysadmin/storting/index.p">Storting</a>
            > Importere votering data
        </div>



<p>


 <H1> Import votering data from data.stortinget.no into votering databasen</H1>

<!--
    <h3>1. Import emner</h3>
    <p>Hvis du skal importere alle emner fra stortinget på nytt, du kan bruke dette. Se kildekode å se hvilke tabell den nye emner blir importert.</p>
    <p>
          <FORM METHOD="get" ENCTYPE="multipart/form-data" action="<p:url value="/storting/votering/importerealleemner.p" />">
            <input type="submit" name="Emner"  value="emner">

    </FORM>
    </p>
    <c:if test="${param.emne_importert != null}">
        <h4 style="color:green;"> Importert emmner på nytt til temporær saksopplysninger table  </h4>
    </c:if>
-->


<h3>Import saksopplysninger data</h3>

 <p> Data fra saker og voteringer (http://data.stortinget.no/eksport/saker?sesjonid= og http://data.stortinget.no/eksport/voteringer?sakid=)
     blir importert til saksopplysninger table </p>
<FORM METHOD="get" ENCTYPE="multipart/form-data" action="<p:url value="/storting/votering/uploadfil.p" />">
    <p>
        <label>Sesjon (2009-2010, 2010-2011...)*:</label>
        <input  type="text" name="sesjon"  size="20"  />
    </p>

<P>
<INPUT TYPE="SUBMIT" VALUE="Sende inn">
<INPUT TYPE="RESET" VALUE="Reset">
</FORM>
</p>

  <c:if test="${param.fil_lest != null}">
     <h4 style="color:green;">-- Importert data for den angitt sesjon til temporær saksopplysninger table  --</h4>
  </c:if>

<p></p>


 <h3>Import personvotering data</h3>

    <p>
    Sjekke antall VOTNR
    <FORM METHOD="get" ENCTYPE="multipart/form-data" action="<p:url value="/storting/votering/sjekksaksopplysningertable.p" />">
        <label> Sesjon:  SesjonId</label><input  type="text" name="sesjontid"  size="16"  />
        <input type="submit" name="Sjekk"  value="Sjekk">
        <p> Antall voteringID =  <c:out value="${param.antall}"></c:out></p>
    </FORM>
     </p>

       <p> Data fra voteringsresultat (http://data.stortinget.no/eksport/voteringsresultat?VoteringId) blir importert til personvotering table </p>

    <p>
        <i>PS: Hvis antall voteringer er mer enn 250, importere bare ca 250 på en gang ved å angi fra,til nednfor.</i>
    </p>

    <FORM METHOD="get" ENCTYPE="multipart/form-data" action="<p:url value="/storting/votering/uploadsaksopplysningertable.p" />">
        <p>
            <label> Fra:  VoteringId</label><input  type="text" name="fravotid"  size="7"  />
            <label> Til:  VoteringId</label><input  type="text" name="tilvotid"  size="7"  />
            <label> Sesjon:  SesjonId</label><input  type="text" name="sesjontid"  size="14"  />
        </p>

        <P>
            <INPUT TYPE="SUBMIT" VALUE="Sende inn">
            <INPUT TYPE="RESET" VALUE="Reset">
    </FORM>
    </p>

    <p style="color:blue;"> Importert opptil =  <c:out value="${param.antallvotnrlist}"></c:out></p>

    <c:if test="${param.saksopplysninger_lest != null}">
        <h4 style="color:green;">-- Importert data for den angitt sesjon til temporær personvotering table  --</h4>
    </c:if>



<p><br><br><br></p>

<p>
<i>
1. saksopplysninger data er importert til t_xxx_storting_votering_saksopplysninger_nyimport_test tabell og  personvotering data er importert til t_xxx_storting_votering_personvotering_nyimport_test tabell.
</i>
</p>
 <p>
<i>2. Når begge saksopplysninger og personvotering er importert til midlertidige tabeller, kan de settes inn i de respektive tabeller ved å utføre sql spørring. Dette kan automatiseres, men vil gjøres i nær fremtid.</i>
</p>
 <p>
<i>3. <b>Fremtid:</b> Modifisere siden slik at den ikke vil tillate å importere for sesjonen som allerede er importert.</i>
</p>

</div>
<p>&nbsp;</p>
<p></p> 
</div>
 