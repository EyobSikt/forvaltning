<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 25.nov.2010
  Time: 09:42:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>

<fmt:setLocale value="en-US" />
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Stortingdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Parliament Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Stortingsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the State Administration Database at NSD." />
</c:import>

<div id="main" class="wide">
    <div>
        <c:if test="${no}">
            Du er her:
            <a href="<p:url value="/"/>">PolSys</a>
            > <a href="<p:url value="/storting/"/>">Storting</a>
            > <a href="<p:url value="/storting/sammensetning/"/>">Sammensetning</a>
            > Representanter og suppleanter
        </c:if>
        <c:if test="${en}">
            You are here:
            <a href="<p:url value="/"/>">PolSys</a>
            > <a href="<p:url value="/storting/"/>">Parliament</a>
            > <a href="<p:url value="/storting/sammensetning/"/>">Composition</a>
            > Representatives and alternates
        </c:if>
    </div>
    <div>
        <c:if test="${param.parti_kortnavn != null && param.parti ==null}">
            <c:if test="${no}"><a href="<m:url value="/en/storting/representant/?periode=${param.periode}&parti_kortnavn=${param.parti_kortnavn}" />">View this page in English.</a></c:if>
            <c:if test="${en}"><a href="<m:url value="/storting/representant/?periode=${param.periode}&parti_kortnavn=${param.parti_kortnavn}" />">View this page in Norwegian.</a></c:if>
        </c:if>

        <c:if test="${param.parti_kortnavn == null && param.parti !=null}">
            <c:if test="${no}"><a href="<m:url value="/en/storting/representant/?periode=${param.periode}&parti=${param.parti}" />">View this page in English.</a></c:if>
            <c:if test="${en}"><a href="<m:url value="/storting/representant/?periode=${param.periode}&parti=${param.parti}" />">View this page in Norwegian.</a></c:if>
        </c:if>

    </div>


    <!--  1945- d.dd  -->

    <c:forEach items="${repersentatnersuppleanterfylke1945}" var="repersentatnersuppleanterfylke1945" begin="0" end="0">
        <c:set var="fylkeid" value="${repersentatnersuppleanterfylke1945.fylkeId}"></c:set>
    </c:forEach>

    <c:forEach items="${repersentantfylke1905_1940}" var="repersentantfylke1905_1940" begin="0" end="0">
        <c:set var="fylkeid_1905" value="${repersentantfylke1905_1940.fylkeId}"></c:set>
    </c:forEach>

    <c:forEach items="${repersentantfylke1814_1904}" var="repersentantfylke1814_1904" begin="0" end="0">
        <c:set var="fylkeid_1814" value="${repersentantfylke1814_1904.fylkeId}"></c:set>
    </c:forEach>

    <c:choose>
        <c:when test="${param.parti == null && param.parti_kortnavn !=null}">
            <h1> <c:forEach items="${repersentatnersuppleanter}" var="repersentatnersuppleanter" begin="0" end="0"> <c:if test="${no}">Representanter og suppleanter i perioden </c:if><c:if test="${en}">Representatives and alternates during the period </c:if> ${repersentatnersuppleanter.periode}
                <c:forEach items="${partikortnavn}" var="partikortnavn" begin="0" end="0">  (${partikortnavn.partiKortnavn})  </c:forEach>
            </c:forEach>
            </h1>
            <table class="text">
                <thead>
                <tr>
                    <th><c:if test="${no}">FYlke</c:if><c:if test="${en}">County</c:if></th>
                    <th>
                        <c:forEach items="${repersentatnersuppleanterfylke1945}" var="repersentatnersuppleanterfylke1945" >
                            <c:choose><c:when test="${param.f_id==null}"><c:set var="fylkid" value="${fylkeid}"></c:set></c:when> <c:otherwise><c:set var="fylkid" value="${param.f_id}"></c:set></c:otherwise></c:choose>
                            <c:if test="${repersentatnersuppleanterfylke1945.fylkeId == fylkid}">
                            <c:if test="${no}">Representanter og suppleanter</c:if><c:if test="${en}">Representatives and alternates</c:if> -  ${repersentatnersuppleanterfylke1945.fylkeNavn}
                            </c:if>
                        </c:forEach>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td valign="top">
                        <c:set var="fylke1etter1945" value="fylkenavn">  </c:set>
                        <c:forEach items="${repersentatnersuppleanterfylke1945}" var="repersentatnersuppleanterfylke1945">
                            <c:choose>
                                <c:when test="${repersentatnersuppleanterfylke1945.fylkeNavn != fylke1etter1945}">
                            <a href="<p:url value="/storting/representant/?periode=${repersentatnersuppleanterfylke1945.periodekode}&parti_kortnavn=${param.parti_kortnavn}&f_id=${repersentatnersuppleanterfylke1945.fylkeId}"/>">  ${repersentatnersuppleanterfylke1945.fylkeNavn}</a> <br>
                                    <c:set var="fylke1etter1945" value="${repersentatnersuppleanterfylke1945.fylkeNavn}">  </c:set>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td valign=top>

                        <c:set var="fylke1etter1945" value="fylkenavn">  </c:set>
                        <div id="hierarchy">
                            <c:forEach items="${repersentatnersuppleanter}" var="repersentatnersuppleanter">
                                <c:choose>
                                    <c:when test="${repersentatnersuppleanter.fylkeNavn != fylke1etter1945}">
                                        <p></p>
                                        <ul><li><ul><li><strong><c:if test="${no}">Representant(er):</c:if><c:if test="${en}">Representative(s):</c:if></strong></li></ul></li></ul>
                                        <c:set var="fylke1etter1945" value="${repersentatnersuppleanter.fylkeNavn}">  </c:set>
                                    </c:when>
                                </c:choose>
                            </c:forEach>

                            <c:forEach items="${repersentatnersuppleanter}" var="repersentatnersuppleanter">
                                <c:choose>
                                    <c:when test="${repersentatnersuppleanter.fylkeRepresentatNr > 0 }">
                                        <ul><li><ul><li><ul><li>
                                                ${repersentatnersuppleanter.fylkeRepresentatNr}.
                                                    <a href="<p:url value="/person/politikerbiografi/?person_id=${repersentatnersuppleanter.personId}&p_info=personalia&periode=${param.periode}&parti_kortnavn=${param.parti_kortnavn}"/>" style="color:#3B5B8B">${repersentatnersuppleanter.forNavn} ${repersentatnersuppleanter.etterNavn}</a><c:if test="${repersentatnersuppleanter.stilling!=null}">, ${repersentatnersuppleanter.stilling}</c:if>
                                        </li></ul></li></ul></li></ul>
                                    </c:when>
                                </c:choose>
                            </c:forEach>

                            <c:forEach items="${repersentatnersuppleanter}" var="repersentatnersuppleanter">
                                <c:choose>
                                    <c:when test="${repersentatnersuppleanter.overRepresentatNr ==1}">
                                        <p></p>
                                        <ul><li><ul><li> <strong><c:if test="${no}">1. suppleant</c:if><c:if test="${en}">1. alternate</c:if></strong> </li></ul></li></ul>
                                        <ul><li><ul><li><ul><li>
                                            <a href="<p:url value="/person/politikerbiografi/?person_id=${repersentatnersuppleanter.personId}&p_info=personalia&periode=${param.periode}&parti_kortnavn=${param.parti_kortnavn}"/>" style="color:#3B5B8B"> ${repersentatnersuppleanter.forNavn} ${repersentatnersuppleanter.etterNavn}</a><c:if test="${repersentatnersuppleanter.stilling!=null}">, ${repersentatnersuppleanter.stilling}</c:if>
                                        </li></ul></li></ul></li></ul>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </c:when>
    </c:choose>

    <!--  1905- 1940 med valgkretskoder.land_eller_by=1 og land_eller_by=2-->

    <c:choose>
        <c:when test="${param.parti_kortnavn == null && param.parti !=null }">
            <c:choose>
                <c:when test="${fn:length(repersentant1905_1940) >= 1 || fn:length(repersentant1905_1940_2) >=1 }">
                    <c:forEach items="${partinavn}" var="partinavn">
                        <h1>${partinavn.partiKortnavn},
                        <c:forEach items="${periodeaar}" var="periodeaar">
                            ${periodeaar.partiKortnavn}
                        </c:forEach>
                    </c:forEach>
                    </h1>
                </c:when>
            </c:choose>
            <table class="text">
                <thead>
                <tr>
                    <th> <c:if test="${no}">Landkretser </c:if><c:if test="${en}">Country</c:if> </th>
                    <th>
                        <!-- land_by_1 -->

                        <c:forEach items="${repersentantfylke1905_1940}" var="repersentantfylke1905_1940">
                            <c:choose><c:when test="${param.f_id==null}"><c:set var="fylkid" value="${fylkeid_1905}"></c:set></c:when> <c:otherwise><c:set var="fylkid" value="${param.f_id}"></c:set></c:otherwise></c:choose>
                            <c:if test="${repersentantfylke1905_1940.fylkeId == fylkid}">
                            <c:if test="${no}"> Representanter og suppleanter</c:if><c:if test="${en}"> Representatives and alternates</c:if> - ${repersentantfylke1905_1940.valkrinsnavn}
                            </c:if>
                        </c:forEach>

                        <!-- land_by_2 -->
                        <c:forEach items="${repersentantfylke1905_1940_2}" var="repersentantfylke1905_1940_2" >
                            <c:set var="fylkid_2" value="${param.f_id}"></c:set>
                            <c:if test="${repersentantfylke1905_1940_2.fylkeId == fylkid_2}">
                                <c:choose>
                                    <c:when test="${repersentantfylke1905_1940_2.valkrinsnavn != fylke}">
                                        <c:if test="${no}">  Representanter og suppleanter</c:if><c:if test="${en}">  Representatives and alternates</c:if>  - ${repersentantfylke1905_1940_2.valkrinsnavn}
                                        <c:set var="fylke_2" value="${repersentantfylke1905_1940_2.valkrinsnavn}">  </c:set>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td width="162px" valign="top">
                        <c:set var="vvvvrepnr" value="fhhjlks">  </c:set>
                        <c:set var="vvvvsupnr" value="fhshjv">  </c:set>
                        <c:set var="fylke" value="fylkenavn">  </c:set>
                        <!-- land_by_1 -->
                        <c:forEach items="${repersentantfylke1905_1940}" var="repersentantfylke1905_1940">
                            <c:choose>
                                <c:when test="${repersentantfylke1905_1940.amt !=null} ">
                                    <a href="<p:url value="/storting/representant/?periode=${param.periode}&parti=${param.parti}&f_id=${repersentantfylke1905_1940.fylkeId}"/>">${repersentantfylke1905_1940.amt}</a><br>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${repersentantfylke1905_1940.valkrinsnavn != fylke}">
                                    <a href="<p:url value="/storting/representant/?periode=${param.periode}&parti=${param.parti}&f_id=${repersentantfylke1905_1940.fylkeId}"/>">${repersentantfylke1905_1940.valkrinsnavn}</a>  <br>
                                    <c:set var="fylke" value="${repersentantfylke1905_1940.valkrinsnavn}">  </c:set>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <!--  1905- 1940 med valgkretskoder.land_eller_by=2-->
                        <c:choose>
                            <c:when test="${fn:length(repersentantfylke1905_1940_2) >= 1}">
                                <table class="text">
                                    <thead>
                                    <tr>
                                        <th width="162px"><c:if test="${no}">Bykretser</c:if><c:if test="${en}">City</c:if></th>
                                    </tr>
                                    </thead>
                                </table>
                                <c:set var="vvvvrepnr_2" value="fhhjlks">  </c:set>
                                <c:set var="vvvvsupnr_2" value="fhshjv">  </c:set>
                                <c:set var="fylke_2" value="fylkenavn">  </c:set>
                                <c:forEach items="${repersentantfylke1905_1940_2}" var="repersentantfylke1905_1940_2">
                                    <c:choose>
                                        <c:when test="${repersentantfylke1905_1940_2.valkrinsnavn != fylke}">
                                            <a href="<p:url value="/storting/representant/?periode=${param.periode}&parti=${param.parti}&f_id=${repersentantfylke1905_1940_2.fylkeId}"/>">${repersentantfylke1905_1940_2.valkrinsnavn}</a><br>
                                            <c:set var="fylke_2" value="${repersentantfylke1905_1940_2.valkrinsnavn}"></c:set>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </td>

                    <!-- land_by_1 -->
                    <c:if test="${fn:length(repersentant1905_1940) >= 1}">
                        <td valign=top>
                            <c:forEach items="${repersentant1905_1940}" var="repersentant1905_1940">
                                <c:choose>
                                    <c:when test="${repersentant1905_1940.fylkeRepresentatNr >0}">
                                        <c:set var="ttt" value="${repersentant1905_1940.fylkeRepresentatNr}.">  </c:set>
                                    </c:when>
                                    <c:when test="${repersentant1905_1940.overRepresentatNr >0}">
                                        <c:set var="ttt" value="${repersentant1905_1940.overRepresentatNr}.">  </c:set>
                                    </c:when>
                                </c:choose>
                                <div id="hierarchy">
                                    <c:choose>
                                        <c:when test="${repersentant1905_1940.fylkeRepresentatNr >0 && repersentant1905_1940.valkrinsnavn !=vvvvrepnr}">
                                            <p></p>
                                            <ul><li><ul><li><strong><c:if test="${no}">Representant(er):</c:if><c:if test="${en}">Representative(s):</c:if></strong>:</li></ul></li></ul>
                                            <c:set var="vvvvrepnr" value="${repersentant1905_1940.valkrinsnavn}">  </c:set>
                                        </c:when>
                                        <c:when test="${repersentant1905_1940.overRepresentatNr >0 && repersentant1905_1940.valkrinsnavn!=vvvvsupnr}">
                                            <p></p>
                                            <ul><li><ul><li><strong><c:if test="${no}">Alternate(s):</c:if><c:if test="${en}">Representative(s):</c:if></strong>: </li></ul></li></ul>
                                            <c:set var="vvvvsupnr" value="${repersentant1905_1940.valkrinsnavn}">  </c:set>
                                        </c:when>
                                    </c:choose>
                                    <ul><li><ul><li><ul><li>
                                            ${ttt}
                                                <a href="<p:url value="/person/politikerbiografi/?person_id=${repersentant1905_1940.personId}&p_info=personalia"/>" style="color:#3B5B8B"> ${repersentant1905_1940.forNavn} ${repersentant1905_1940.etterNavn}</a> <c:if test="${repersentant1905_1940.stilling!=null}">, ${repersentant1905_1940.stilling}</c:if>
                                    </li></ul></li></ul></li></ul>
                                </div>
                            </c:forEach>
                        </td>
                    </c:if>

                    <!-- land_by_2 -->
                    <c:if test="${fn:length(repersentant1905_1940_2) >= 1}">
                        <td valign=top>
                            <c:forEach items="${repersentant1905_1940_2}" var="repersentant1905_1940_2">
                                <c:choose>
                                    <c:when test="${repersentant1905_1940_2.fylkeRepresentatNr >0}">
                                        <c:set var="ttt_2" value="${repersentant1905_1940_2.fylkeRepresentatNr}.">  </c:set>
                                    </c:when>
                                    <c:when test="${repersentant1905_1940_2.overRepresentatNr >0}">
                                        <c:set var="ttt_2" value="${repersentant1905_1940_2.overRepresentatNr}.">  </c:set>
                                    </c:when>
                                </c:choose>
                                <div id="hierarchy">
                                    <c:choose>
                                        <c:when test="${repersentant1905_1940_2.fylkeRepresentatNr >0 && repersentant1905_1940_2.valkrinsnavn !=vvvvrepnr_2}">
                                            <p></p>
                                            <ul><li><ul><li><strong>Representant(er):</strong>:</li></ul></li></ul>
                                            <c:set var="vvvvrepnr_2" value="${repersentant1905_1940_2.valkrinsnavn}">  </c:set>
                                        </c:when>
                                        <c:when test="${repersentant1905_1940_2.overRepresentatNr >0 && repersentant1905_1940_2.valkrinsnavn !=vvvvsupnr_2}">
                                            <p></p>
                                            <ul><li><ul><li><strong>Suppleant(er)</strong>: </li></ul></li></ul>
                                            <c:set var="vvvvsupnr_2" value="${repersentant1905_1940_2.valkrinsnavn}">  </c:set>
                                        </c:when>
                                    </c:choose>
                                    <ul><li><ul><li><ul><li>
                                            ${ttt_2}
                                                <a href="<p:url value="/person/politikerbiografi/?person_id=${repersentant1905_1940_2.personId}&p_info=personalia"/>" style="color:#3B5B8B">${repersentant1905_1940_2.forNavn} ${repersentant1905_1940_2.etterNavn}</a><c:if test="${repersentant1905_1940_2.stilling!=null}">, ${repersentant1905_1940_2.stilling}</c:if>
                                    </li></ul></li></ul></li></ul>
                                </div>
                            </c:forEach>
                        </td>
                    </c:if>

                </tr>
                </tbody>
            </table>

        </c:when>
    </c:choose>














    <!--  1814- 1904 med valgkretskoder.land_eller_by=1-->

    <c:choose>
        <c:when test="${param.parti == null && param.parti_kortnavn == null}">

            <h1> <c:forEach items="${repersentant1814_1904}" var="repersentant1814_1904" begin="0" end="0"> Representanter og suppleanter
                <c:forEach items="${periodeaar}" var="periodeaar" begin="0" end="0">  ${periodeaar.partiKortnavn}  </c:forEach>
            </c:forEach>
            </h1>

            <table class="text">
                <thead>
                <tr>
                    <th><c:if test="${no}">Landkretser</c:if><c:if test="${en}">Country</c:if> </th>
                    <th>
                        <!-- land_by_1 -->
                        <c:forEach items="${repersentantfylke1814_1904}" var="repersentantfylke1814_1904">
                            <c:choose><c:when test="${param.f_id==null}"><c:set var="fylkid" value="${fylkeid_1814}"></c:set></c:when> <c:otherwise><c:set var="fylkid" value="${param.f_id}"></c:set></c:otherwise></c:choose>
                            <c:if test="${repersentantfylke1814_1904.fylkeId == fylkid}">
                            <c:if test="${no}">  Representanter og suppleanter</c:if><c:if test="${en}">  Representatives and alternates</c:if> -  ${repersentantfylke1814_1904.valkrinsnavn}
                            </c:if>
                        </c:forEach>

                        <!-- land_by_2 -->
                        <c:forEach items="${repersentantfylke1814_1904_2}" var="repersentantfylke1814_1904_2" >
                            <c:set var="fylkid_2" value="${param.f_id}"></c:set>
                            <c:if test="${repersentantfylke1814_1904_2.fylkeId == fylkid_2}">
                                <c:choose>
                                    <c:when test="${repersentantfylke1814_1904_2.valkrinsnavn != fylke}">
                                        <c:if test="${no}">  Representanter og suppleanter</c:if><c:if test="${en}">  Representatives and alternates</c:if> - ${repersentantfylke1814_1904_2.valkrinsnavn}
                                        <c:set var="fylke_2" value="${repersentantfylke1814_1904_2.valkrinsnavn}">  </c:set>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td width="162px" valign="top">
                        <c:set var="vvvvrepnr" value="fhhjlks">  </c:set>
                        <c:set var="vvvvsupnr" value="fhshjv">  </c:set>
                        <c:set var="fylke" value="fylkenavn">  </c:set>
                        <!-- land_by_1 -->
                        <c:forEach items="${repersentantfylke1814_1904}" var="repersentantfylke1814_1904">
                            <c:choose>
                                <c:when test="${repersentantfylke1814_1904.amt !=null} ">
                                    <a href="<p:url value="/storting/representant/?periode=${param.periode}&f_id=${repersentantfylke1814_1904.fylkeId}"/>">${repersentantfylke1814_1904.amt}</a><br>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${repersentantfylke1814_1904.valkrinsnavn != fylke}">
                                    <a href="<p:url value="/storting/representant/?periode=${param.periode}&f_id=${repersentantfylke1814_1904.fylkeId}"/>">${repersentantfylke1814_1904.valkrinsnavn}</a>  <br>
                                    <c:set var="fylke" value="${repersentantfylke1814_1904.valkrinsnavn}">  </c:set>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <!--  1905- 1940 med valgkretskoder.land_eller_by=2-->
                        <c:choose>
                            <c:when test="${fn:length(repersentantfylke1814_1904_2) >= 1}">
                                <table class="text">
                                    <thead>
                                    <tr>
                                        <th width="162px"><c:if test="${no}">Bykretser</c:if><c:if test="${en}">City</c:if></th>
                                    </tr>
                                    </thead>
                                </table>
                                <c:set var="vvvvrepnr_2" value="fhhjlks">  </c:set>
                                <c:set var="vvvvsupnr_2" value="fhshjv">  </c:set>
                                <c:set var="fylke_2" value="fylkenavn">  </c:set>
                                <c:forEach items="${repersentantfylke1814_1904_2}" var="repersentantfylke1814_1904_2">
                                    <c:choose>
                                        <c:when test="${repersentantfylke1814_1904_2.valkrinsnavn != fylke}">
                                            <a href="<p:url value="/storting/representant/?periode=${param.periode}&f_id=${repersentantfylke1814_1904_2.fylkeId}"/>">${repersentantfylke1814_1904_2.valkrinsnavn}</a><br>
                                            <c:set var="fylke_2" value="${repersentantfylke1814_1904_2.valkrinsnavn}"></c:set>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </td>

                    <!-- land_by_1 -->
                    <c:if test="${fn:length(repersentant1814_1904) >= 1}">
                        <td valign=top>
                            <c:forEach items="${repersentant1814_1904}" var="repersentant1814_1904">
                                <c:choose>
                                    <c:when test="${repersentant1814_1904.fylkeRepresentatNr >0}">
                                        <c:set var="ttt" value="${repersentant1814_1904.fylkeRepresentatNr}.">  </c:set>
                                    </c:when>
                                    <c:when test="${repersentant1814_1904.overRepresentatNr >0}">
                                        <c:set var="ttt" value="${repersentant1814_1904.overRepresentatNr}.">  </c:set>
                                    </c:when>
                                </c:choose>
                                <div id="hierarchy">
                                    <c:choose>
                                        <c:when test="${repersentant1814_1904.fylkeRepresentatNr >0 && repersentant1814_1904.valkrinsnavn !=vvvvrepnr}">
                                            <p></p>
                                            <ul><li><ul><li><strong>Representant(er)</strong>:</li></ul></li></ul>
                                            <c:set var="vvvvrepnr" value="${repersentant1814_1904.valkrinsnavn}">  </c:set>
                                        </c:when>
                                        <c:when test="${repersentant1814_1904.overRepresentatNr >0 && repersentant1814_1904.valkrinsnavn!=vvvvsupnr}">
                                            <p></p>
                                            <ul><li><ul><li><strong>Suppleant(er)</strong>: </li></ul></li></ul>
                                            <c:set var="vvvvsupnr" value="${repersentant1814_1904.valkrinsnavn}">  </c:set>
                                        </c:when>
                                    </c:choose>
                                    <ul><li><ul><li><ul><li>
                                            ${ttt}
                                                <a href="<p:url value="/person/politikerbiografi/?person_id=${repersentant1814_1904.personId}&p_info=personalia"/>" style="color:#3B5B8B">${repersentant1814_1904.forNavn} ${repersentant1814_1904.etterNavn}</a><c:if test="${repersentant1814_1904.stilling!=null}">, ${repersentant1814_1904.stilling}</c:if>
                                    </li></ul></li></ul></li></ul>
                                </div>
                            </c:forEach>
                        </td>
                    </c:if>

                    <!-- land_by_2 -->
                    <c:if test="${fn:length(repersentant1814_1904_2) >= 1}">
                        <td valign=top>
                            <c:forEach items="${repersentant1814_1904_2}" var="repersentant1814_1904_2">
                                <c:choose>
                                    <c:when test="${repersentant1814_1904_2.fylkeRepresentatNr >0}">
                                        <c:set var="ttt_2" value="${repersentant1814_1904_2.fylkeRepresentatNr}.">  </c:set>
                                    </c:when>
                                    <c:when test="${repersentant1814_1904_2.overRepresentatNr >0}">
                                        <c:set var="ttt_2" value="${repersentant1814_1904_2.overRepresentatNr}.">  </c:set>
                                    </c:when>
                                </c:choose>
                                <div id="hierarchy">
                                    <c:choose>
                                        <c:when test="${repersentant1814_1904_2.fylkeRepresentatNr >0 && repersentant1814_1904_2.valkrinsnavn !=vvvvrepnr_2}">
                                            <p></p>
                                            <ul><li><ul><li><strong>Representant(er):</strong>:</li></ul></li></ul>
                                            <c:set var="vvvvrepnr_2" value="${repersentant1814_1904_2.valkrinsnavn}">  </c:set>
                                        </c:when>
                                        <c:when test="${repersentant1814_1904_2.overRepresentatNr >0 && repersentant1814_1904_2.valkrinsnavn !=vvvvsupnr_2}">
                                            <p></p>
                                            <ul><li><ul><li><strong>Suppleant(er)</strong>: </li></ul></li></ul>
                                            <c:set var="vvvvsupnr_2" value="${repersentant1814_1904_2.valkrinsnavn}">  </c:set>
                                        </c:when>
                                    </c:choose>
                                    <ul><li><ul><li><ul><li>
                                            ${ttt_2}
                                                <a href="<p:url value="/person/politikerbiografi/?person_id=${repersentant1814_1904_2.personId}&p_info=personalia"/>" style="color:#3B5B8B">${repersentant1814_1904_2.forNavn} ${repersentant1814_1904_2.etterNavn}</a><c:if test="${repersentant1814_1904_2.stilling!=null}">,  ${repersentant1814_1904_2.stilling}</c:if>
                                    </li></ul></li></ul></li></ul>
                                </div>
                            </c:forEach>
                        </td>
                    </c:if>

                </tr>
                </tbody>
            </table>


        </c:when>
    </c:choose>






</div>
</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />