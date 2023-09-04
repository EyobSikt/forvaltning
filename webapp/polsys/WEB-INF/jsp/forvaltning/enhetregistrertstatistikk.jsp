<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@page import="java.util.Map"%>
<%@ page import="java.util.Hashtable" %>

<link rel="stylesheet" href="<p:url value="/css/style.css"/>" type="text/css"  media="screen"/>
<script src="<p:url value="/js/jquery-1.4.2.min.js"/>" type="text/javascript"></script>
<script src="<p:url value="/js/scripts.js"/>" type="text/javascript"></script>
<!--<script type="text/javascript" src="http://www.nsd.uib.no/common/polsys/js/tableToExcel.js" ></script> -->
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Departementer per ${brukerdato} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Ministries per ${brukerdato} - State Administration Database" />
    <c:param name="beskrivelseNo" value="Oversikt over norske departementer per ${brukerdato}." />
    <c:param name="beskrivelseEn" value="List of Norwegian ministries per ${brukerdato}." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<style type="text/css">

    #sokeboks {
        border-bottom: 1px solid #CDD0AF;
        position: relative;
    }
    .dokumentTop {
        background-color: #EFF0D9;
        border-bottom: 1px solid #E1E5B8;
        margin: 0;
        overflow: hidden;
        text-align: center;
        padding: 5px;
        padding-top: 10px;
    }


    .hjelpetekst {
        color: #6A8ABA;
        /*font-style: italic;*/
        font-size: 10px;
        padding:  3px;
        height: 5px;
        margin: 0 auto;
    }
    .data_field {
        height: 16px;
        padding-right: 17px;
        width: 430px;
    }
    span.icon_clear {
        color: #305080;
        cursor: pointer;
        font: bold 1em sans-serif;
        position: absolute;
        right: 180px;
        top: 15px;
    }
</style>
<script>
    function fnExcelReport()
    {
        var tab_text="<table border='2px'><tr bgcolor='#87AFC6'>";
        var textRange; var j=0;
        var tab = document.getElementById('excelTable'); // id of table

        for(j = 0 ; j < tab.rows.length ; j++)
        {
            tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
            //tab_text=tab_text+"</tr>";

        }

        tab_text=tab_text+"</table>";
        tab_text= tab_text.replace(/<a[^>]*>|<\/a>/g, "");//remove if u want links in your table
        tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
        tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

        var ua = window.navigator.userAgent;
        var msie = ua.indexOf("MSIE ");

        if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
        {
            tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
            if (typeof Blob !== "undefined") {
                //use blobs if we can
                tab_text = [tab_text];
                //convert to array
                var blob1 = new Blob(tab_text, {
                    type: "text/html"
                });
                window.navigator.msSaveBlob(blob1, "download.xls");
            } else {
                txtArea1.document.open("txt/html", "replace");
                txtArea1.document.write(tab_text);
                txtArea1.document.close();
                txtArea1.focus();
                sa = txtArea1.document.execCommand("SaveAs", true, "download.xls");
                return (sa);
            }
        }
        else                 //other browser not tested on IE 11
            tab_text= tab_text.replace(/<a[^>]*>|<\/a>/g, "");//remove if u want links in your table
        sa = window.open('data:application/vnd.ms-excel,' + escape(tab_text));


        return (sa);
    }
</script>

<div id="main"  class="wide">
    <div class="breadcrumbs">
        <c:if test="${no}">
            Du er her:
            <a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
            > <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
            > <a href="<p:url value="/data/enhetsregisteret"/>">Organisasjoner</a>
            > Statistikk
        </c:if>
        <c:if test="${en}">
            You are here:
            <a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
            > <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
            > <a href="<p:url value="/data/enhetsregisteret"/>">Organisations</a>
            > Statistics
        </c:if>
    </div>

    <h2>Statistikk over organisasjoner </h2>

    <p><em>Siden viser antall organisasjoner kategorisert etter spesifikk gruppe. Disse are Orgninsasjonsgruppe(Organisasjonsform, Sektorkode,Næringskode).</em></p>

<p><em> Velg fra listen nedenfor. Detaljer kommer hvis du klikker den respektive linken som kommer opp.</em></p>

    <%--<p><a href="<p:url value="/forvaltning/organisasjon/statistikk?statgruppe=${fn:escapeXml('orggruppe')}"/>" >${en ? "Ogranisation group:" : "Orgninsasjonsgruppe:"}</a></p>
    <p><a href="<p:url value="/forvaltning/organisasjon/statistikk?statgruppe=${fn:escapeXml('arbeidsyrke')}"/>" >${en ? "occupational code :" : "Yrkeskode:"}</a></p>--%>

<c:if test="${(not empty param.statgruppe && param.statgruppe == 'orggruppe') ||  empty param.statgruppe }">
    <form action="" method="get">
        <input type='hidden' name='statgruppe' value='orggruppe' />
        <p>

         ${en ? "Ogranisation group:" : "Orgninsasjonsgruppe:"}
            <select size="1" name="orggruppe">
                <!--<option value="">Velg kategori</option> -->
                <option value="orgform" ${param.orggruppe eq 'orgform' || param.orggruppe eq ''  ? 'selected="selected"' : ''} >${en ? "Organisation form" : "Organisasjonsform"}</option>
                <option value="sekkode" ${param.orggruppe eq 'sekkode' ? 'selected="selected"' : ''} >${en ? "Sektor code" : "Sektorkode"}</option>
                <option value="naerkode" ${param.orggruppe eq 'naerkode' ? 'selected="selected"' : ''} >${en ? "Activity code" : "Næringskode"}</option>
                <option value="forretningskommune" ${param.orggruppe eq 'forretningskommune' ? 'selected="selected"' : ''} >${en ? "Business code" : "Forretningskommune"}</option>
            </select>
                 ${en ? "Choose year:" : " Velg år:"}
             <select name="aar">
                 <c:forEach items="${alleAar}" var="i">
                     <option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
                 </c:forEach>

             </select>
            <input type="submit" value="OK" />
        </p>
    </form>
</c:if>


<%-- =================================== enhet ansatte basert på arbeid yrke ============================ --%>
    <iframe id="txtArea1" style="display:none"></iframe>
    <%--
    <c:if test="${not empty param.statgruppe && param.statgruppe == 'arbeidsyrke' }">

        <form action="" method="get">
            <p>

                <input type="hidden" name= "statgruppe" value="arbeidsyrke" />
                ${en ? "Choose year:" : " Velg år:"}
                <select name="aar">
                    <c:forEach items="${alleAar}" var="i">
                        <option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
                    </c:forEach>

                </select>
                <input type="submit" value="OK" />
            </p>
        </form>

        <table>
            <tbody>
            <tr>
                <td valign="top">
        <c:if test="${!(empty alle_arbeideyrke)}">
            <table id="excelTable" class="text zebra sortable">
                <caption>${en ? "Number of emplyees based on occupation code" : "Antall ansatte basert på yrkeskode"}, aar = ${valgtAar}
                    <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float:right" src="<p:url value="http://www.nsd.uib.no/common/polsys/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
                </caption>
                <thead>
                <tr>
                    <th>${en ? "Occupation code" : "Yrkeskode"}</th>
                    <th>${en ? "Professional title" : "Yrkestittel"}</th>

                <th>${en ? "Menn" : "Menn"}</th>
                <th>${en ? "Women" : "Kvinner"}</th>
                <th>${en ? "Number of organisations" : "Antall organisasjoner"}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${alle_arbeideyrke}" var="de">
                <tr>
                    <td>${de.arbYrke}</td>
                    <td>${de.yrkesTittel}</td>
                    <td>${de.antallAnsatte_menn}</td>
                    <td>${de.antallAnsatte_kvinner}</td>
                    <td><a href="<p:url value="/forvaltning/organisasjon/statistikk?statgruppe=arbeidsyrke&arbeidsyrke=${fn:escapeXml(de.arbYrke)}&aar=${fn:escapeXml(de.aar)}"/>" >${de.antall}</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
            </td>
            <td valign="top">


<c:if test="${arbeidsyrke2 != null && param.arbeidsyrke!=null}">
    <table class="text zebra sortable">
        <caption>${en ? "Organizations with occupation code: " : "Orgnisasjoner som har yrkeskode: "}  ${param.arbeidsyrke}, aar = ${valgtAar}</caption>
        <thead>
        <tr>
          <th>${en ? "Orgnr" : "Orgnr"}</th>
          <th>${en ? "Org Name" : "Org navn"}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${arbeidsyrke2}" var="arbeidsyrke2">
            <tr>
                <td><a target="_blank" href="<p:url value="/forvaltning/organisasjon/${arbeidsyrke2.orgnummer}?aar=${arbeidsyrke2.aar}"/>" > ${arbeidsyrke2.orgnummer} </a></td>
                <td>${arbeidsyrke2.langtNavn}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
  </td>
   </tr>
    </tbody>
</table>
</c:if>
    --%>
    <%-- ===========================end yrkekoderf========================================== --%>

    <%-- ============================================== organisasjonsform ================  --%>
    <c:if test="${(!(empty organisasjonsform) && param.statgruppe != 'arbeidsyrke')  || empty param.statgruppe}">

    <table>
        <tbody>
        <tr>
            <td valign="top">

<table class="text zebra sortable">
<caption> ${en ? "Number of ogranizations based on form" : "Antall organisasjoner basert på organisasjonsform"}, aar = ${valgtAar}</caption>
<thead>
<tr>
<th>${en ? "Organizational form" : "Organisasjonsform"}</th>
<th>${en ? "Number" : "Antall organisasjoner"}</th>
<th>${en ? "Number of employees" : "Antall ansatte"}</th>
</tr>
</thead>
<tbody>
<c:forEach items="${organisasjonsform}" var="u">
    <c:choose>
        <c:when test="${not empty u.organisasjonsgruppebeskrivelse}"><c:set var="orgform" value="${u.organisasjonsgruppebeskrivelse}"></c:set></c:when>
        <c:when test="${u.organisasjonsgruppe eq 'AS' && empty u.organisasjonsgruppebeskrivelse}"><c:set var="orgform" value="Aksjeselskap"></c:set></c:when>
        <c:when test="${u.organisasjonsgruppe eq 'FLI' && empty u.organisasjonsgruppebeskrivelse}"><c:set var="orgform" value="Forening/lag/innretning"></c:set></c:when>
        <c:when test="${u.organisasjonsgruppe eq 'KIRK' && empty u.organisasjonsgruppebeskrivelse}"><c:set var="orgform" value="Den norske kirke"></c:set></c:when>
        <c:when test="${u.organisasjonsgruppe eq 'SF' && empty u.organisasjonsgruppebeskrivelse}"><c:set var="orgform" value="Statsforetak"></c:set></c:when>
        <c:when test="${u.organisasjonsgruppe eq 'SÆR' && empty u.organisasjonsgruppebeskrivelse}"><c:set var="orgform" value="Annet foretak ifølge særskilt lov"></c:set></c:when>
    </c:choose>
 <tr>
<td>${u.organisasjonsgruppe} (${orgform})</td>
<td><a title="Klikk for å liste opp organisasjonene, resultatene er vist under tabellen." href="<p:url value="/data/organisasjon/statistikk?statgruppe=${fn:escapeXml('orggruppe')}&orggruppe=orgform&orgform=${u.organisasjonsgruppe}&aar=${u.aar}" />">${fn:escapeXml(u.antall)}</a></td>
<td>${u.antallAnsatte}</td>
 </tr>
</c:forEach>
</tbody>
</table>

            </td>
            <td valign="top">
                <c:if test="${organisasjonsform2 != null && param.orgform!=null}">
                    <table class="text zebra sortable">
                        <caption>${en ? "Organizations with organizational form: " : "Orgnisasjoner som har organisasjonsform: "}  ${param.orgform},  aar = ${valgtAar}</caption>
                        <thead>
                        <tr>
                            <th>${en ? "Orgnr" : "Orgnr"}</th>
                            <th>${en ? "Org Name" : "Org navn"}</th>
                            <th>${en ? "Number of employees" : "Antall ansatte"}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${organisasjonsform2}" var="organisasjonsform2">
                            <tr>
                                <td><a target="_blank" href="<p:url value="/data/organisasjon/${organisasjonsform2.orgnummer}?aar=${organisasjonsform2.aar}"/>" > ${organisasjonsform2.orgnummer} </a></td>
                                <td>${organisasjonsform2.langtNavn}</td>
                                <td>${organisasjonsform2.antallAnsatte}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>
    </c:if>


    <%-- ============================================== næringskode ================  --%>
    <c:if test="${!(empty sektorkode)}">
    <table>
        <tbody>
        <tr>
            <td valign="top">

        <table class="text zebra sortable">
            <caption> ${en ? "Number of ogranizations based on sector code" : "Antall organisasjoner basert på sektorkode"}, aar = ${valgtAar}</caption>
            <thead>
            <tr>
                <th>${en ? "Sector code" : "Sektorkode"}</th>
                <th>${en ? "Number" : "Antall organisasjoner"}</th>
                <th>${en ? "Number of employees" : "Antall ansatte"}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sektorkode}" var="u">
                <tr>
                    <td>${u.organisasjonsgruppe} (${u.organisasjonsgruppebeskrivelse})</td>
                    <td><a title="Klikk for å liste opp organisasjonene, resultatene er vist under tabellen." href="<p:url value="/data/organisasjon/statistikk?statgruppe=${fn:escapeXml('orggruppe')}&orggruppe=sekkode&sektorkode=${u.organisasjonsgruppe}&aar=${u.aar}" />">${fn:escapeXml(u.antall)}</a></td>
                    <td>${u.antallAnsatte}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


    </td>
    <td valign="top">
        <c:if test="${sektorkode2 != null && param.sektorkode!=null}">
            <table class="text zebra sortable">
                <caption>${en ? "Organizations with sector code: " : "Orgnisasjoner som har sekotorkode: "}  ${param.sektorkode}, aar = ${valgtAar}</caption>
                <thead>
                <tr>
                    <th>${en ? "Orgnr" : "Orgnr"}</th>
                    <th>${en ? "Org Name" : "Org navn"}</th>
                    <th>${en ? "Number of employees" : "Antall ansatte"}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sektorkode2}" var="sektorkode2">
                    <tr>
                        <td><a target="_blank" href="<p:url value="/data/organisasjon/${sektorkode2.orgnummer}?aar=${sektorkode2.aar}  "/>" > ${sektorkode2.orgnummer} </a></td>
                        <td>${sektorkode2.langtNavn}</td>
                        <td>${sektorkode2.antallAnsatte}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </td>
    </tr>
    </tbody>
    </table>
    </c:if>

    <c:if test="${!(empty naeringskode)}">
    <table >
        <tbody>
        <tr>
            <td valign="top">
<%-- ============================================== næringskode ================  --%>

       <table class="text zebra sortable">
           <caption> ${en ? "Number of ogranizations based on Activity code" : "Antall organisasjoner basert på næringskode"}, aar = ${valgtAar}</caption>
            <thead>
            <tr>
                <th>${en ? "Activity code" : "Næringskode"}</th>
                <th>${en ? "Number" : "Antall organisasjoner"}</th>
                <th>${en ? "Number of employees" : "Antall ansatte"}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${naeringskode}" var="u">
                <tr>
                    <td>${u.organisasjonsgruppe} (${u.organisasjonsgruppebeskrivelse})</td>
                    <td><a title="Klikk for å liste opp organisasjonene, resultatene er vist under tabellen." href="<p:url value="/data/organisasjon/statistikk?statgruppe=${fn:escapeXml('orggruppe')}&orggruppe=naerkode&naeringskode=${u.organisasjonsgruppe}&aar=${u.aar}" />">${fn:escapeXml(u.antall)}</a></td>
                    <td>${u.antallAnsatte}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

            </td>
            <td valign="top">

                <c:if test="${naeringskode2 != null && param.naeringskode!=null}">
                    <table class="text zebra sortable">
                        <caption>${en ? "Organizations with industry code: " : "Orgnisasjoner som har næringskode: "}${param.naeringskode}, aar = ${valgtAar}</caption>
                        <thead>
                        <tr>
                            <th>${en ? "Orgnr" : "Orgnr"}</th>
                            <th>${en ? "Org Name" : "Org navn"}</th>
                            <th>${en ? "Number of employees" : "Antall ansatte"}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${naeringskode2}" var="naeringskode2">
                            <tr>
                                <td><a target="_blank" href="<p:url value="/data/organisasjon/${naeringskode2.orgnummer}?aar=${naeringskode2.aar}"/>" > ${naeringskode2.orgnummer} </a></td>
                                <td>${naeringskode2.langtNavn}</td>
                                <td>${naeringskode2.antallAnsatte}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>
    </c:if>

    <%-- ============================================== Forretningskommune ================  --%>
    <c:if test="${!(empty forretningskommune)}">
        <table >
            <tbody>
            <tr>
                <td valign="top">


                    <table class="text zebra sortable">
                        <caption> ${en ? "Number of ogranizations based on Activity code" : "Antall organisasjoner basert på forretningskommune"}, aar = ${valgtAar}</caption>
                        <thead>
                        <tr>
                            <th>${en ? "Activity code" : "Forretningskommune"}</th>
                            <th>${en ? "Number" : "Antall organisasjoner"}</th>
                            <th>${en ? "Number of employees" : "Antall ansatte"}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${forretningskommune}" var="u">
                            <tr>
                                <td>${u.organisasjonsgruppe} <c:if test="${!(empty u.organisasjonsgruppebeskrivelse)}" >(knr: ${u.organisasjonsgruppebeskrivelse}) </c:if></td>
                                <td><a title="Klikk for å liste opp organisasjonene, resultatene er vist under tabellen." href="<p:url value="/data/organisasjon/statistikk?statgruppe=${fn:escapeXml('orggruppe')}&orggruppe=forretningskommune&forretningskommunekode=${u.organisasjonsgruppebeskrivelse}&aar=${u.aar}" />">${fn:escapeXml(u.antall)}</a></td>
                                <td>${u.antallAnsatte}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </td>
                <td valign="top">

                    <c:if test="${forretningskommunekode2 != null && param.forretningskommunekode!=null}">
                        <table class="text zebra sortable">
                            <caption>${en ? "Organizations with business council code: " : "Orgnisasjoner som har forretningskommune: "}${param.forretningskommunekode}, aar = ${valgtAar}</caption>
                            <thead>
                            <tr>
                                <th>${en ? "Orgnr" : "Orgnr"}</th>
                                <th>${en ? "Org Name" : "Org navn"}</th>
                                <th>${en ? "Organizational form" : "Organisasjonsform"}</th>
                                <th>${en ? "Number of employees" : "Antall ansatte"}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${forretningskommunekode2}" var="forretningskommunekode2">
                                <tr>
                                    <td><a target="_blank" href="<p:url value="/data/organisasjon/${forretningskommunekode2.orgnummer}?aar=${forretningskommunekode2.aar}"/>" > ${forretningskommunekode2.orgnummer} </a></td>
                                    <td>${forretningskommunekode2.langtNavn}</td>
                                    <td><%--${forretningskommunekode2.organisasjonsgruppe}--%> ${forretningskommunekode2.organisasjonsgruppebeskrivelse}</td>
                                    <td>${forretningskommunekode2.antallAnsatte}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </td>
            </tr>
            </tbody>
        </table>
    </c:if>



   <%-- ====================================================================  --%>
    <div class="footnote">
        <c:if test="${!(empty naeringskode)}">
            <h3>Merk</h3>
            <p><em> Mer om næringskoder finnes på følgnede eksterne ressurs.
                <a href="http://stabas.ssb.no/ItemsFrames.asp?ID=8118001&Language=nb" target="_blank">${en ? "activity codes" : "næringskoder"}</a></em>
            </p>
        </c:if>
        <c:if test="${!(empty sektorkode)}">
            <h3>Merk</h3>
            <p><em> Mer om sektorkoder finnes på følgnede eksterne ressurs.
                <a href="http://stabas.ssb.no/ItemsFrames.asp?ID=8378001&Language=nb" target="_blank">${en ? "sector codes" : "sektorkoder"}</a></em>
            </p>
        </c:if>
    </div>

</div>

<%--
<div id="sidebar">
    <c:if test="${no}">
    <h3>Organisasjons Hierarki</h3>
    <p>
        På <a href="<p:url value="/forvaltning/enhetsregisteret"/>">hierarki</a>
        kan du se oversikt over organisasjonshierarki.
    </p>
        <h3>Søk</h3>
        <p>
            På <a href="<p:url value="/forvaltning/organisasjon/sok"/>">Søk</a>
            side kan du søke på organisasjonsnavn eller kommune hvor enheten har sin forretningsadresse. Du kan velge variabler som skal vises i resultattabell.
        </p>

  </c:if>
        <c:if test="${en}">
        </c:if>
</div>
--%>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
