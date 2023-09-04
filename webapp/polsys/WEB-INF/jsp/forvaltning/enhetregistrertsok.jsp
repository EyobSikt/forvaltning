<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>

<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="Interne departementsenheter - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="Bodies within ministries - State Administration Database" />
    <c:param name="beskrivelseNo" value="Finn antall interne departementsenheter fordelt på administrative nivåer." />
    <c:param name="beskrivelseEn" value="Number of public administration bodies distributed on administrative levels." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<%--<script src="<p:url value="/js/tableToExcel.js"/>" type="text/javascript" ></script> --%>

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

<div id="main" class="wide">

<div class="breadcrumbs">
<c:if test="${no}">
Du er her:
<a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
> <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
> <a href="<p:url value="/data/enhetsregisteret"/>">Organisasjoner</a>
> Søk på departement
</c:if>
<c:if test="${en}">
You are here:
<a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
> <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
> <a href="<p:url value="/data/enhetsregisteret"/>">Organisasjoner</a>
> Search based on department
</c:if>
</div>

<h1>${en ? "Ogranisations based on Brønnøysund register center" : "Organisasjoner basert på Brønnøysundregistrene"}</h1>

    <p></p>

    <strong>Søketips:</strong>
    <span class="overridep">
    <p>Bruk søkefeltet til å søke på ord eller tema du er interessert i. Avgrens søket ved å bruke filtrering i menyen under departementer.Dersom ingen filtrering er valgt, søker man i alle organisasjoner.</p>
    <p>Bruk avkrysningsboksen i menyen under variabler til å velge bestemte felter/variabler som skal vises i resultattabell. Som standard er <strong>Org.nummer</strong> og <strong>Navn</strong> inkludert i resultattabell.</p>
    </span>

    <form id="sokeboks" class="dokumentTop" accept-charset="UTF-8" action="<p:url value="/data/organisasjon/sok" />" method="GET">
       <%--
        <ul class="plain">
            <h3>${en ? "Fri text:" : "Skrive fritekst"}</h3>
            <input type="text" name="q" size="75" maxlength="100" value="${fn:escapeXml(param.q)}"  id="querystring">
            <span title="slett tekst" class="icon_clear" onclick="window.location='?q=${rows}${fn:escapeXml(requestScope.url)}${sortresult}${sortfacet}'">X</span>
        </ul>
       --%>


                   Velg år:
                   <select name="aar">
                       <c:forEach items="${alleAar}" var="i">
                           <option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
                       </c:forEach>

                   </select>

        <span class="clearable">
        <input class="data_field" type="text"  value="${fn:escapeXml(param.q)}" name="q" id="querystring" />
            <span title="slett tekst" class="icon_clear" onclick="window.location='?q=${rows}${fn:escapeXml(requestScope.url)}${sortresult}${sortfacet}'">X</span>
        </span>
           <c:set var="facetlist" value="${paramValues.fq}"/>
        <c:forEach var="y" begin="0" end="${fn:length(facetlist)}"  >
            <c:if test="${facetlist[y] !=null && facetlist[y]!='' }">
                <input name="fq" type="hidden" value="${fn:escapeXml(facetlist[y])}">
            </c:if>
        </c:forEach>

        <input class="button" type="submit" value="Søk">
        <p class="hjelpetekst">Søk på organisasjonsnavn eller kommune hvor enheten har sin forretningsadresse/besøksadresse</p>

    </form>

    <div class="column_wide">
        <h2 class="lineunder">${en ? "Filter - ogranisations" : "Filter - organisasjoner "}</h2>
        <h3>${en ? "Departemens:" : "Departementer"}</h3>

        <ul class="plain">
            <c:forEach items="${departmentlist}" var="departmentlist">

                <c:set var="isSelected" value="false" scope="page"/>
                <c:set var="names" value="${paramValues.fq}"/>
                <c:set var="aarstallnavn2" value="orgnummer:${departmentlist.orgnummer}"/>
                <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                    <c:if test="${names[k] ==aarstallnavn2 }">
                    <c:set var="isSelected" value="true" scope="page"/>
                    </c:if>
                </c:forEach>
                <li><input type="checkbox"  <c:if test="${isSelected}">checked=true</c:if>
                           onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=orgnummer:${departmentlist.orgnummer}${sortresult}${sortfacet}';
                                   else if (!this.checked)
                               <c:set var="names" value="${paramValues.fq}"/>
                           <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                           <c:if test="${names[k] !=aarstallnavn2}">
                               <c:set var="fqlist" value="&fq=" />
                               <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                           </c:if>
                           </c:forEach>
                                   window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar)}${sortresult}${sortfacet}';
                           <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                               <c:set var="myVar" value="" />
                           </c:forEach>
                                   "
                        /> <label for="n${departmentlist.orgnummer}">${fn:escapeXml(departmentlist.langtNavn)}</label></li>
            </c:forEach>
        </ul>
   </div>

  <%-- =============================== Variabler =========================== --%>
        <div class="column_wide">
         <h2 class="break lineunder">${en ? "Variables" : "Variabler"}</h2>

        <h3>${en ? "Choose fields to be included in the result table" : "Velge felt som skal inkluderes i resultattabellen "}</h3>

            <c:set var="isSelected1" value="false" scope="page"/>
            <c:set var="names22" value="${paramValues.fq}"/>
            <c:forEach var="k" begin="0" end="${fn:length(names22)}" >
                <c:if test="${names22[k] =='variable:1'}"><c:set var="isSelected1" value="true" scope="page"/></c:if>
               <%-- <c:if test="${names22[k] =='variable:2'}"><c:set var="isSelected2" value="true" scope="page"/></c:if> --%>
                <c:if test="${names22[k] =='variable:3'}"><c:set var="isSelected3" value="true" scope="page"/></c:if>
                <c:if test="${names22[k] =='variable:4'}"><c:set var="isSelected4" value="true" scope="page"/></c:if>
                <c:if test="${names22[k] =='variable:5'}"><c:set var="isSelected5" value="true" scope="page"/></c:if>
                <c:if test="${names22[k] =='variable:6'}"><c:set var="isSelected6" value="true" scope="page"/></c:if>
                <c:if test="${names22[k] =='variable:7'}"><c:set var="isSelected7" value="true" scope="page"/></c:if>
                <c:if test="${names22[k] =='variable:8'}"><c:set var="isSelected8" value="true" scope="page"/></c:if>
                <c:if test="${names22[k] =='variable:9'}"><c:set var="isSelected9" value="true" scope="page"/></c:if>
                <c:if test="${names22[k] =='variable:10'}"><c:set var="isSelected10" value="true" scope="page"/></c:if>

            </c:forEach>

        <ul class="plain">
            <li><input type="checkbox" id="v5"  <c:if test="${isSelected1}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${1}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names1" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names1)}" >
                       <c:if test="${names1[k] !='variable:1'}">
                           <c:set var="fqlist1" value="&fq=" />
                           <c:set var="myVar1" value="${stat.first ? '' : myVar1}${fqlist}${names1[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar1)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar1)}" >
                           <c:set var="myVar1" value="" />
                       </c:forEach>
                       "
                    /> <label for="v5">${en ? "Parent ministries" : "Overordnet departementer"}</label></li>
          <%--
            <li><input type="checkbox"   id="v6" <c:if test="${isSelected2}">checked=true</c:if>

                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${2}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names2" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names2)}" >
                       <c:if test="${names2[k] !='variable:2'}">
                           <c:set var="fqlist2" value="&fq=" />
                           <c:set var="myVar2" value="${stat.first ? '' : myVar2}${fqlist2}${names2[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar2)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar2)}" >
                           <c:set var="myVar2" value="" />
                       </c:forEach>
                               "
                    /><label for="v6">${en ? "Reg. Year" : "Reg.År"}</label></li>
            --%>

            <li><input type="checkbox" id="v4" <c:if test="${isSelected3}">checked=true</c:if>

                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${3}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names3" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names3)}" >
                       <c:if test="${names3[k] !='variable:3'}">
                           <c:set var="fqlist3" value="&fq=" />
                           <c:set var="myVar3" value="${stat.first ? '' : myVar3}${fqlist3}${names3[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar3)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar3)}" >
                           <c:set var="myVar3" value="" />
                       </c:forEach>
                               "
            /> <label for="v4">${en ? "Number of employees" : "Antall ansatte"}</label></li>
            <li><input type="checkbox" id="v7" <c:if test="${isSelected4}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${4}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names4" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names4)}" >
                       <c:if test="${names4[k] !='variable:4'}">
                           <c:set var="fqlist4" value="&fq=" />
                           <c:set var="myVar4" value="${stat.first ? '' : myVar4}${fqlist4}${names4[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar4)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar4)}" >
                           <c:set var="myVar4" value="" />
                       </c:forEach>
                               "
                    /> <label for="v7">${en ? "Overarching Unit id" : "OverordnetEnhet id"}</label></li>
            <li><input type="checkbox"  id="v1" <c:if test="${isSelected5}">checked=true</c:if>

                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${5}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names5" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names5)}" >
                       <c:if test="${names5[k] !='variable:5'}">
                           <c:set var="fqlist5" value="&fq=" />
                           <c:set var="myVar5" value="${stat.first ? '' : myVar5}${fqlist5}${names5[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar5)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar5)}" >
                           <c:set var="myVar5" value="" />
                       </c:forEach>
                               "

                    /> <label for="v1">${en ? "Organisation form" : "Organisasjonsform"}</label></li>
            <li><input type="checkbox"  id="v2" <c:if test="${isSelected6}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${6}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names6" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names6)}" >
                       <c:if test="${names6[k] !='variable:6'}">
                           <c:set var="fqlist6" value="&fq=" />
                           <c:set var="myVar6" value="${stat.first ? '' : myVar6}${fqlist6}${names6[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar6)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar6)}" >
                           <c:set var="myVar6" value="" />
                       </c:forEach>
                               "

                    /> <label for="v2">${en ? "Sektor code" : "Sektorkode"}</label></li>
            <li><input type="checkbox"  id="v3" <c:if test="${isSelected7}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${7}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names7" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names7)}" >
                       <c:if test="${names7[k] !='variable:7'}">
                           <c:set var="fqlist7" value="&fq=" />
                           <c:set var="myVar7" value="${stat.first ? '' : myVar7}${fqlist7}${names7[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar7)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar7)}" >
                           <c:set var="myVar7" value="" />
                       </c:forEach>
                               "
                    /> <label for="v3">${en ? "Activity code" : "Næringskode"}</label></li>
            <li><input type="checkbox"  id="v8" <c:if test="${isSelected8}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${8}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names8" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names8)}" >
                       <c:if test="${names8[k] !='variable:8'}">
                           <c:set var="fqlist8" value="&fq=" />
                           <c:set var="myVar8" value="${stat.first ? '' : myVar8}${fqlist8}${names8[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar8)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar8)}" >
                           <c:set var="myVar8" value="" />
                       </c:forEach>
                               "
                    /> <label for="v8">${en ? "NSD id-number" : "NSD id-nummer"}</label></li>
            <li><input type="checkbox"  id="v9" <c:if test="${isSelected9}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${9}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names9" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names9)}" >
                       <c:if test="${names9[k] !='variable:9'}">
                           <c:set var="fqlist9" value="&fq=" />
                           <c:set var="myVar9" value="${stat.first ? '' : myVar9}${fqlist9}${names9[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar9)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar9)}" >
                           <c:set var="myVar9" value="" />
                       </c:forEach>
                               "
                    /> <label for="v9">${en ? "Business council" : "Forretningskommune"}</label></li>
            <li><input type="checkbox"  id="v10" <c:if test="${isSelected10}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(requestScope.url)}&fq=variable:${10}${sortresult}${sortfacet}';
                               else if (!this.checked)
                       <c:set var="names9" value="${paramValues.fq}"/>
                       <c:forEach var="k" begin="0" end="${fn:length(names10)}" >
                       <c:if test="${names10[k] !='variable:10'}">
                           <c:set var="fqlist10" value="&fq=" />
                           <c:set var="myVar10" value="${stat.first ? '' : myVar10}${fqlist10}${names10[k]}" />
                       </c:if>
                       </c:forEach>
                               window.location = '?q=' + document.getElementById('querystring').value + '${rows}${fn:escapeXml(myVar10)}${sortresult}${sortfacet}';
                       <c:forEach var="v" begin="0" end="${fn:length(myVar10)}" >
                           <c:set var="myVar10" value="" />
                       </c:forEach>
                               "
                    /> <label for="v9">${en ? "Business address" : "Forretningsadresse"}</label></li>
        </ul>
       </div>
    <p class="break lineunder"></p>
        <%-- =================================== søk =====================

        <c:set var="facetlist" value="${paramValues.fq}"/>
        <c:forEach var="y" begin="0" end="${fn:length(facetlist)}"  >
            <c:if test="${facetlist[y] !=null && facetlist[y]!='' }">
                <input name="fq" type="hidden" value="${fn:escapeXml(facetlist[y])}">
            </c:if>
        </c:forEach>
        <p class="break lineunder">
                <input class="button" type="submit" value="Søk">
        </p>
--%>
        <%-- ===================================================================== --%>


    <iframe id="txtArea1" style="display:none"></iframe>
    <%--
    <button id="btnExport22"  class="button" onclick="fnExcelReport();"> <img SRC="<p:url value="/js/export.jpg"/>" width="30" height="30">  EXPORT </button>
     <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float:right" src="<p:url value="/js/export.jpg"/>" title="Eksport resultatet til Excel"  /></a>
     =================================== data ============================ --%>
    <c:if test="${!(empty finalresultat)}">

        <table id="excelTable" class="text zebra sortable">
            <caption>${en ? "Number of organisations" : "Antall organisasjoner"} - (Antall fant ${fn:escapeXml(requestScope.antalltreff)}), aar = ${valgtAar}
             <a href="#" id="bottle" onclick="fnExcelReport();" ><img style="float:right" src="<p:url value="https://forvaltningsdatabasen.sikt.no/common/polsys/img/excel.png"/>" title="Eksport resultatet til Excel"  /></a>
            </caption>
            <thead>
            <tr>
                <c:if test="${variabler['1']}"><th colspan="4">${en ? "Parent ministry" : "Overordnet departement"}</th></c:if>
                <th></th>
                <th></th>
               <%-- <c:if test="${variabler['2']}"><th></th></c:if> --%>
                <%-- <c:if test="${variabler['3']}"><th colspan="2">${en ? "Number of employees" : "Antall ansatte"}</th></c:if>--%>
                <c:if test="${variabler['3']}"><th></th></c:if>
                <c:if test="${variabler['4']}"><th></th></c:if>
                <c:if test="${variabler['5']}"><th></th></c:if>
                <c:if test="${variabler['6']}"><th></th></c:if>
                <c:if test="${variabler['7']}"><th></th></c:if>
                <c:if test="${variabler['8']}"><th></th></c:if>
                <c:if test="${variabler['9']}"><th></th></c:if>
                <c:if test="${variabler['10']}"><th></th></c:if>

            </tr>
            <tr>
        <c:if test="${variabler['1']}">
                <th>${en ? "Level 1" : "Nivå 1"}</th>
                <th>${en ? "Level 2" : "Nivå 2"}</th>
                <th>${en ? "Level 3" : "Nivå 3"}</th>
                <th>${en ? "Level 4" : "Nivå 4"}</th>
                </c:if>
                <th>${en ? "Org.number" : "Org.nummer"}</th>
                <th>${en ? "Name" : "Navn"}</th>
                <%--<c:if test="${variabler['2']}"><th>${en ? "Reg. Year" : "Reg.År"}</th></c:if>--%>
                <c:if test="${variabler['3']}">
                <th>${en ? "Number of employees" : "Antall ansatte"}</th>
                    <%--
                        <th>${en ? "Menn" : "Menn"}</th>
                    <th>${en ? "Women" : "Kvinner"}</th>
                        --%>
                </c:if>
                <c:if test="${variabler['4']}"><th>${en ? "Overarching Unit" : "overordnetEnhet"}</th></c:if>
                <c:if test="${variabler['5']}"><th>${en ? "Organizational structure" : "Organisasjonsform"}</th></c:if>
                <c:if test="${variabler['6']}"><th>${en ? "Sector code" : "Sektorkode"}</th></c:if>
                <c:if test="${variabler['7']}"><th>${en ? "Activity code" : "Næringskode"}</th></c:if>
                <c:if test="${variabler['8']}"><th>${en ? "NSD id-number" : "NSD id-nummer"}</th></c:if>
                <c:if test="${variabler['9']}"><th>${en ? "Business council" : "Forretningskommune"}</th></c:if>
                <c:if test="${variabler['10']}"><th>${en ? "Business address" : "Forretningsadresse"}</th></c:if>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${finalresultat}" var="de">
                <tr>
                    <c:if test="${variabler['1']}">
                    <td>${de.level_1}</td>
                    <td>${de.level_2}</td>
                    <td>${de.level_3}</td>
                    <td>${de.level_4}</td>
                    </c:if>
                    <td ><a  href="<p:url value="/data/organisasjon/${de.idnum}?aar=${param.aar}"  />" target="_blank">${de.idnum}</a></td>
                    <td>${de.navn}</td>
                    <c:if test="${variabler['3']}"><td>${de.antallAnsatte}</td> </c:if>

                    <%--<td>${de.antallAnsatte_menn}</td>
                    <td>${de.antallAnsatte_kvinner}</td>
                        --%>

                    <c:if test="${variabler['4']}"><td><a  href="<p:url value="/data/organisasjon/${fn:escapeXml(de.overordnetEnhet)}"/>" target="_blank">${fn:escapeXml(de.overordnetEnhet)}</a></td></c:if>
                    <c:if test="${variabler['5']}"><td>${de.organisasjonsform}</td></c:if>
                    <c:if test="${variabler['6']}"><td>${de.sektorkode}</td></c:if>
                    <c:if test="${variabler['7']}"><td>${de.naringskode}</td></c:if>
                    <c:if test="${variabler['8']}"><td><a href="<p:url value="/data/enhet/${de.nsd_idnum}"/>" >${de.nsd_idnum}</a></td></c:if>
                    <c:if test="${variabler['9']}"><td>${de.forretningskommune}</td></c:if>
                    <c:if test="${variabler['10']}"><td>${de.forretningsfulladresse}</td></c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
  <%-- ===================================================================== --%>
    <p></p>
</div>


<c:import url="/WEB-INF/jspf/bunn.jsp" />
