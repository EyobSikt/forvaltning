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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- --------------------------------------------- inkluderer toppinnhold. --%>
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Storting" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Norwegian parliament" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} Stortinget hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} Norwegian parliament database at NSD." />
</c:import>

<script type="text/javascript">
    var sesjonarray = new Array();
    var sesjon_id = 0;
    var periodearray = new Array();
    var periode_id = 0;
    <c:forEach items="${frekvensersesjon_asc}" var="frekvensersesjon" >
    sesjonarray[sesjon_id] = new Array("${frekvensersesjon.sesjonId}", "${frekvensersesjon.sesjon}");
    sesjon_id++;
    </c:forEach>
    <c:forEach items="${frekvenserperiode_asc}" var="frekvenserperiode" >
    periodearray[periode_id] = new Array( "${frekvenserperiode.periode}", "${frekvenserperiode.periodetekst}");
    periode_id++;
    </c:forEach>

    function appNewLevel3(level3Id, level3Name) {
        var createsesjon = document.createElement('option');
        var createperiode = document.createElement('option');
        createsesjon.text = level3Name;
        createsesjon.value = level3Id;
        createperiode.text = level3Name;
        createperiode.value = level3Id;
        var sesjon = document.getElementById('sesjon');
        var periode = document.getElementById('periode');
        try {
            sesjon.add(createsesjon, null); // standards compliant; doesn't work in IE
            periode.add(createperiode, null);
        } catch(ex) {
            sesjon.add(createsesjon); // IE only
            periode.add(createperiode);
        }
    }
    function removeAllLevel3() {
        var sesjon = document.getElementById('sesjon');
        var periode = document.getElementById('periode');
        if (sesjon) sesjon.length = 0;
        if (periode) periode.length = 0;
    }
    function changesesjonperiode() {
        document.getElementById("sesjon").disabled=false;
        document.getElementById("periode").disabled=false;
        removeAllLevel3();

        for (var i=0; i< sesjonarray.length; i++) {
            if (document.form.tidsenhet[0].checked == true) {
                appNewLevel3(sesjonarray[i][0], sesjonarray[i][1]);
            }
        }
        for (var i=0; i< periodearray.length; i++) {
            if (document.form.tidsenhet[1].checked == true) {
                appNewLevel3(periodearray[i][0], periodearray[i][1]);
            }
        }
        if (document.getElementById("sesjon").length == 1) document.getElementById("sesjon").disabled=true;
        if (document.getElementById("periode").length == 1) document.getElementById("periode").disabled=true;
    }
</script>

<div id="sidebar">

</div>


<div id="main" class="wide">
<div>
    <c:if test="${no}">
        Du er her:
        <a href="<p:url value="/"/>">PolSys</a>
        > <a href="<p:url value="/storting/" />"> Storting </a>
        > Partiavstand
    </c:if>
    <c:if test="${en}">
        You are here:
        <a href="<p:url value="/"/>">PolSys</a>
        > <a href="<p:url value="/storting/" />"> Parliament </a>
        > Party stand
    </c:if>
</div>
    <div>
        <c:if test="${no}"><a href="<m:url value="/en/storting/partiavstand/" />">View this page in English.</a></c:if>
        <c:if test="${en}"><a href="<m:url value="/storting/partiavstand/" />">View this page in Norwegian.</a></c:if>
    </div>

    <c:if test="${no}"> <h1>Politisk partiavstand</h1></c:if>
    <c:if test="${en}"><h1>Political party stand </h1>
        <h3>This page is not translated to English</h3>
        <p>----------------------------------------------</p>
    </c:if>
<%
    String tidsenhet = request.getParameter("tidsenhet") == null ? "-1" : request.getParameter("tidsenhet");
    String FraSesjon = request.getParameter("FraSesjon") == null ? "-1" : request.getParameter("FraSesjon");
    String TilSesjon = request.getParameter("TilSesjon") == null ? "-1" : request.getParameter("TilSesjon");
    String Type = request.getParameter("Type") == null ? "-1" : request.getParameter("Type");
    String aar = request.getParameter("aar") == null ? "-1" : request.getParameter("aar");
    String said = request.getParameter("said") == null ? "-1" : request.getParameter("said");
    String seid = request.getParameter("seid") == null ? "-1" : request.getParameter("seid");
    String registrering = request.getParameter("registrering") == null ? "-1" : request.getParameter("registrering");
%>

<c:set var="isSelected" value="false" scope="page"/>
<c:if test="${param.tidsenhet==null}" >
    <c:set var="isSelected" value="true" scope="page"/>
</c:if>


<form name="form" class="form"  ACTION= "<p:url value="/storting/partiavstand/"/>"  METHOD="GET">
    <TABLE class="text">
        <tbody>
        <TR>
            <TD valign="top">
                <h4>Politisk avstand mellom</h4>
                <p>
                    <select name="parti_a" size=1>
                        <c:forEach items="${partinavn}" var="partinavn" >
                            <option value="${partinavn.partikortnavn}">${partinavn.partinavn}</option>
                        </c:forEach>
                    </select>
                </p>
                <p>
                    <select name="parti_b" size=1>
                        <c:forEach items="${partinavn}" var="partinavn" >
                            <option value="${partinavn.partikortnavn}">${partinavn.partinavn}</option>
                        </c:forEach>
                    </select>
                </p>

            </TD>
            <TD>
                <h3>Tidsrom:</h3>

                <input type="radio" name="tidsenhet" value="sesjon"  onClick="changesesjonperiode()" <%= tidsenhet.equals("sesjon") ? "checked=\"checked \"" : "" %>> Sesjonsvis
                <input type="radio" name="tidsenhet" value="periode"  onClick="changesesjonperiode()" <%= tidsenhet.equals("periode") ? "checked=\"checked \"" : "" %> <c:if test="${isSelected}">checked=true</c:if> > Periodevis
                <br><br>
                <%--
                <select name="frasesjon" id="sesjon">
                    <c:forEach items="${frekvenserperiode_asc}" var="frekvenserperiode" >
                        <option value="${frekvenserperiode.periode}">${frekvenserperiode.periodetekst}</option>
                    </c:forEach>
                </select>
                til
                <select name="tilsesjon" id="periode">
                    <c:forEach items="${frekvenserperiode_desc}" var="frekvenserperiode" >
                        <option value="${frekvenserperiode.periode}">${frekvenserperiode.periodetekst}</option>
                    </c:forEach>
                </select>
                --%>

                <c:choose><c:when test="${param.tidsenhet=='sesjon'}">
                    <select name="frasesjon" id="sesjon">
                        <c:forEach items="${frekvensersesjon_asc}" var="frekvenserperiode" >
                            <option value="${frekvenserperiode.sesjonId}" <c:if test="${frekvenserperiode.sesjonId==param.frasesjon}"> selected="selected" </c:if>>${frekvenserperiode.sesjon}</option>
                        </c:forEach>
                    </select>
                    til
                    <select name="tilsesjon" id="periode">
                        <c:forEach items="${frekvensersesjon_desc}" var="frekvenserperiode" >
                            <option value="${frekvenserperiode.sesjonId}" <c:if test="${frekvenserperiode.sesjonId==param.tilsesjon}"> selected="selected" </c:if>>${frekvenserperiode.sesjon}</option>
                        </c:forEach>
                    </select>
                </c:when>
                    <c:otherwise>
                        <select name="frasesjon" id="sesjon">
                            <c:forEach items="${frekvenserperiode_asc}" var="frekvenserperiode" >
                                <option value="${frekvenserperiode.periode}" <c:if test="${frekvenserperiode.periode==param.frasesjon}"> selected="selected" </c:if>>${frekvenserperiode.periodetekst}</option>
                            </c:forEach>
                        </select>
                        til
                        <select name="tilsesjon" id="periode">
                            <c:forEach items="${frekvenserperiode_desc}" var="frekvenserperiode" >
                                <option value="${frekvenserperiode.periode}" <c:if test="${frekvenserperiode.periode==param.tilsesjon}"> selected="selected" </c:if>>${frekvenserperiode.periodetekst}</option>
                            </c:forEach>
                        </select>
                    </c:otherwise></c:choose>

            </TD>
        </TR>
        <TR>

            <TD valign="top">
                <h4>Komité</h4>

                <select name="komite" size=4 multiple>
                    <option value="-1" selected>Alle komiteer</option>
                    <c:forEach items="${komite}" var="komite" >
                        <option value="${komite.komitekode}">${komite.partinavn} - [${komite.fra_aar} - ${komite.til_aar}  ]</option>
                    </c:forEach>
                </select>
            </TD>
            <TD valign="top">


                <h3>Innstillingstype:</h3>

                <select name="type" size=4 multiple>
                    <option value="-1" selected>Alle typer</option>
                    <option value="1"  >Stortingsinnstillinger</option>
                    <option value="2"  >Odelstingsinnstillinger</option>
                    <option value="3"  >Budsjettinnstillinger</option>

                </select>
            </TD>
            <TD valign="bottom">
                <input type="Submit" name="btnUtfoer" value="Utfør" >
            </TD>
        </tr>
        </tbody>
    </TABLE>
</form>

<c:if test="${param.btnUtfoer !=null}">

    <c:if test="${param.type =='-1'}">
        <c:set var="Tidsenhet" value="  alle innstillingstyper"></c:set>
    </c:if>
    <c:if test="${param.type =='1'}">
        <c:set var="Tidsenhet" value="  stortingsinnstillinger"></c:set>
    </c:if>
    <c:if test="${param.type =='2'}">
        <c:set var="Tidsenhet" value=" odelstingsinnstillinger"></c:set>
    </c:if>
    <c:if test="${param.type =='3'}">
        <c:set var="Tidsenhet" value=" budsjettinnstillinger"></c:set>
    </c:if>

    <table class="zebra">
        <caption>
                ${param.parti_a} -${param.parti_b} (i ${Tidsenhet}
                    <c:set var="names" value="${paramValues.komite}"/>
                <c:forEach items="${komite}" var="komite" >
                    <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                  <c:choose><c:when test="${names[k] == komite.komitekode}"><c:set var="komitenavn" value="${komitenavn}  ${komite.partinavn} "></c:set></c:when>
                      <c:when test="${names[k] == -1}"><c:set var="komitenavn" value="alle komiteer"></c:set></c:when>
                  </c:choose>
                    </c:forEach>
            </c:forEach>
                    fra ${komitenavn}
            )
        </caption>
        <thead>
        <tr>
            <th width="50">${param.tidsenhet}</th>
            <th width="50">Begge tilstede</th>
            <th width="80">Begge deltar</th>
            <th width="80">Ingen deltar</th>
            <th width="80">${param.parti_a}, ikke ${param.parti_b}</th>
            <th width="80">${param.parti_a}, ikke ${param.parti_a}</th>
            <th width="50">Sum uenighet</th>
            <th width="80">Rommetvedts partiavstands- indeks</th>
        </tr>

        </thead>

        <c:set var="percentage" value="${ (time.averageTotalTimeRaw/averageTotalTimeSum) }"/>
        <tbody>
        <c:forEach items="${sokpartiavstandsersesjonperiode}" var="sokpartiavstandsersesjonperiode" >
            <tr>
                <c:if test="${sokpartiavstandsersesjonperiode.beggedeltar !=0 || sokpartiavstandsersesjonperiode.ingendeltar !=0 }">

                    <c:set var="percentage"  value="${ ((sokpartiavstandsersesjonperiode.beggedeltar)/(sokpartiavstandsersesjonperiode.beggedeltar + sokpartiavstandsersesjonperiode.ingendeltar +sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2)*100)}  "/>

                    <td>${sokpartiavstandsersesjonperiode.periodetekst}</td>
                    <td>${sokpartiavstandsersesjonperiode.beggedeltar + sokpartiavstandsersesjonperiode.ingendeltar +sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2}</td>
                    <td>${sokpartiavstandsersesjonperiode.beggedeltar}(<fmt:formatNumber type="PERCENT" maxFractionDigits="1"
                                                                                         value="${ ((sokpartiavstandsersesjonperiode.beggedeltar)/(sokpartiavstandsersesjonperiode.beggedeltar + sokpartiavstandsersesjonperiode.ingendeltar +sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2))} " />)
                    </td>
                    <td>${sokpartiavstandsersesjonperiode.ingendeltar}
                        (<fmt:formatNumber type="PERCENT" maxFractionDigits="1"
                                           value="${ ((sokpartiavstandsersesjonperiode.ingendeltar)/(sokpartiavstandsersesjonperiode.beggedeltar + sokpartiavstandsersesjonperiode.ingendeltar +sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2))} " />)
                    </td>
                    <td>${sokpartiavstandsersesjonperiode.deltar_1}
                        (<fmt:formatNumber type="PERCENT" maxFractionDigits="1"
                                           value="${ ((sokpartiavstandsersesjonperiode.deltar_1)/(sokpartiavstandsersesjonperiode.beggedeltar + sokpartiavstandsersesjonperiode.ingendeltar +sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2))} " />)
                    </td>
                    <td>${sokpartiavstandsersesjonperiode.deltar_2}
                        (<fmt:formatNumber type="PERCENT" maxFractionDigits="1"
                                           value="${ ((sokpartiavstandsersesjonperiode.deltar_2)/(sokpartiavstandsersesjonperiode.beggedeltar + sokpartiavstandsersesjonperiode.ingendeltar +sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2))} " />)
                    </td>
                    <td>
                        <fmt:formatNumber type="PERCENT" maxFractionDigits="1"
                                          value="${ (((sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2))/(sokpartiavstandsersesjonperiode.beggedeltar + sokpartiavstandsersesjonperiode.ingendeltar +sokpartiavstandsersesjonperiode.deltar_1 + sokpartiavstandsersesjonperiode.deltar_2))} " />
                    </td>
                    <td>
                        <c:set var="Rommetvedt_indeks_a" value="${ ((sokpartiavstandsersesjonperiode.deltar_1)/(sokpartiavstandsersesjonperiode.beggedeltar +  sokpartiavstandsersesjonperiode.deltar_1))} " >
                        </c:set>
                        <c:set var="Rommetvedt_indeks_b" value="${ ((sokpartiavstandsersesjonperiode.deltar_2)/(sokpartiavstandsersesjonperiode.beggedeltar  + sokpartiavstandsersesjonperiode.deltar_2))} " >
                        </c:set>



                        <fmt:formatNumber type="PERCENT" maxFractionDigits="1"
                                          value="${(Rommetvedt_indeks_a + Rommetvedt_indeks_b)/2} " />
                    </td>


                </c:if>
            </tr>

        </c:forEach>

        </tbody>
    </table>
    <c:forEach items="${sokpartiavstandsersesjonperiode}" var="sokpartiavstandsersesjonperiode" >
        <c:if test="${sokpartiavstandsersesjonperiode.beggedeltar !=0 || sokpartiavstandsersesjonperiode.ingendeltar !=0 }">
            <c:set var="fantresultat" value="fantresultat"></c:set>
        </c:if>
    </c:forEach>

    <c:if test="${fantresultat != 'fantresultat'}">
        <p>
            Søkekombinasjonen gir ikke resultat. Mulige årsaker er at begge partier ikke var representert i sesjonen eller at de ikke satt i samme komitè.
        </p>
    </c:if>
    <c:if test="${fantresultat == 'fantresultat'}">
        <h3>Forklaringer til tabellen</h3>

        <ul>
            <li><strong>Begge tilstede</strong> angir antall merknader hvor både ${param.parti_a} og ${param.parti_b} har vært representert i den aktuelle komit&egrave;.</li>
            <li><strong>Begge deltar</strong> viser antall merknader hvor både ${param.parti_a} og ${param.parti_b} har deltatt.</li>
            <li><strong>Ingen deltar</strong> viser antall merknader hvor verken ${param.parti_a} og ${param.parti_b} har deltatt.</li>
            <li><strong>${param.parti_a}, ikke ${param.parti_b}</strong>, og <strong>${param.parti_b}, ikke ${param.parti_a}</strong>, viser antall merknader hvor kun det ene partiet har deltatt.</li>
            <li><strong>Sum uenighet</strong> er summen av de to foregående kolonnene.</li>
            <li><strong>Rommetvedts partiavstandsindeks</strong> er et mål som baserer seg på positiv enighet, dvs. at de merknader som ingen av de to partiene har valgt å støtte (negativ enighet) ikke tas med i prosentueringsgrunnlaget.</li>
        </ul>

        <p>
            Se for øvrig dokumentasjonen av Fraksjonsmerknadsarkivet.
        </p>

        <h4>Ved bruk av prosenter og tolkning av 'Politisk partiavstand':</h4>

        <p>
            Spesielt ved sammenligninger over tid bør man være oppmerksom på at samarbeidsmønsteret i komiteene er svært avhengig av stortings- og regjeringssammensetningen.:
        </p>
        <ul>
            <li>
                Særlig fløypartier står ofte alene om sine fraksjonsmerknader, mens
                alle andre partier er uenige med dem. Dersom et fløyparti kommer inn
                på Stortinget etter et valg, kan dette føre til at de andre partiene må
                gå sammen om flere fraksjonsmerknader som ikke ville vært nødvendige
                dersom vedkommende fløyparti ikke var representert. Dette kan føre til
                en "kunstig" reduksjon i avstanden mellom de andre partiene (og motsatt
                dersom et fløyparti faller ut av Stortinget). Dette gjør det vanskelig
                å foreta sammenligninger mellom to valgperioder der det er forskjellige
                partier som er representert.
            </li>
            <li>
                I koalisjonsregjeringer må de partiene som deltar i regjeringen inngå
                kompromisser med hverandre før de legger sakene fram for Stortinget.
                Regjeringspartiene står som oftest sammen i stortingskomiteene. I slike
                tilfeller er regjeringspartienes felles fraksjonsmerknader ofte uttrykk
                for et kompromiss. Man kan derfor si at
                fraksjonsmerknader i komitéinnstillinger ikke gir et godt bilde av de
                "reelle" avstandene mellom regjeringspartiene, dvs mellom de
                standpunktene disse partiene ville inntatt dersom de ikke var med i
                regjeringen og kunne la være å ta hensyn til/inngå kompromisser med
                regjeringspartnerne.
            </li>
            <li>
                Det kan også være problemer med små partier som ikke er representert i
                alle komiteene. Endringer i komitédeltakelsen fra en periode til en
                annen kan påvirke avstandsmønstrene fordi graden av enighet eller
                uenighet kan variere fra ett saksområde til et annet.
            </li>
        </ul>

        <p>
            Jf. stortingssammensetningen i Politikerarkivene og regjeringens sammensetning i Statsrådsarkivet.
        </p>

    </c:if>








</c:if>


</div>

<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp" />
<%-- --------------------------------------------------------------------- --%>