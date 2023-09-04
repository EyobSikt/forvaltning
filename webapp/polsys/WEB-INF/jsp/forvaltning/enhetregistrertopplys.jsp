<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<fmt:setLocale value="en-US" />

<c:forEach items="${enhet}" var="enhet">
    <c:set var="navn" value="${enhet.navn}"/>
    <c:set var="idnum" value="${enhet.idnum}"/>
    <c:set var="organisasjonsform" value="${enhet.organisasjonsform}"/>
    <c:set var="antallAnsatte" value="${enhet.antallAnsatte}"/>
    <c:set var="antallAnsatte_menn" value="${enhet.antallAnsatte_menn}"/>
    <c:set var="antallAnsatte_kvinner" value="${enhet.antallAnsatte_kvinner}"/>
    <c:set var="forretingsadresse" value="${enhet.forretingsadresse}"/>
    <c:set var="forretingskommune" value="${enhet.forretingskommune}"/>
    <c:set var="forretingsland" value="${enhet.forretingsland}"/>
    <c:set var="postadresse_adresse" value="${enhet.postadresse_adresse}"/>
    <c:set var="registrertdato" value="${enhet.registrertdato}"/>
    <c:set var="naringskode" value="${enhet.naringskode}"/>
    <c:set var="sektorkode" value="${enhet.sektorkode}"/>
    <c:set var="forvaltningsidnum" value="${enhet.forvaltningsidnum}"/>
</c:forEach>

<c:forEach items="${forvaltningsenhet}" var="forvaltningsenhet">
    <c:set var="orgnummer" value="${forvaltningsenhet.orgnummer}"/>
    <c:set var="forvaltningsidnum" value="${forvaltningsenhet.idnum}"/>
</c:forEach>
<%-- ============================================== Topp ================  --%>
<c:if test="${no}">
<c:import url="/WEB-INF/jspf/topp_forvaltning.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(navn)} - Forvaltningsdatabasen" />
    <c:param name="tittelEn" value="${fn:escapeXml(navn)} - State Administration Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(navn)} i Forvaltningsdatabasen hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(navn)} in the State Administration Database at NSD." />
</c:import>
</c:if>
<c:if test="${en}">
    <c:import url="/WEB-INF/jspf/topp_en_forvaltning.jsp"></c:import>
</c:if>
<%-- ====================================================================  --%>


<div id="main" >
    <%-- ============================================ Breadcrumbs ===========  --%>
    <div class="breadcrumbs">
        <c:if test="${no}">
            Du er her:
            <a href="https://forvaltningsdatabasen.sikt.no">Forvaltningsdatabasen</a>
            > <a href="https://forvaltningsdatabasen.sikt.no/forvaltning/forvaltningsdatabasen.html">Enheter</a>
            > <a href="<p:url value="/data/enhetsregisteret"/>">Organisasjoner</a>
            > organisasjoner #${idnum}
        </c:if>
        <c:if test="${en}">
            You are here:
            <a href="https://forvaltningsdatabasen.sikt.no/en/">Civil Service</a>
            > <a href="https://forvaltningsdatabasen.sikt.no/civilservice/administrationdatabase.html">Units</a>
            > <a href="<p:url value="/data/enhetsregisteret"/>">Organisations</a>
            > organisations #${idnum}
        </c:if>
    </div>
    <%-- ====================================================================  --%>

<h1>${fn:escapeXml(navn)} (Orgnr. ${idnum})</h1>

        <form action="" method="get">
            <p>

                ${en ? "Choose year:" : " Velg år:"}
                <select name="aar">
                    <c:forEach items="${alleAar}" var="i">
                        <option value="${i}" ${i eq valgtAar ? 'selected="selected"' : ''}>${i}</option>
                    </c:forEach>

                </select>
                <input type="submit" value="OK" />
            </p>
        </form>


<%-- ======================================== Enhet-nøkkelopplysninger ==============  --%>
    <div class="enhetcontent">
        <p>${en ? "Unit info." : "Denne siden viser info om denne enheten."} <strong>år = ${valgtAar}</strong></p>
        <%-- ======================================== Siste nøkkelopplysninger ==  --%>
        <div class="event">
        <h3 class="eventh">${en ? "Latest key info" : "Nøkkelopplysninger"}</h3>
        <c:if test="${idnum != null}">
            <div class="event">
                <span class="eventl">${en ? "Organisation number:" : "Organisasjonsnummer:"}</span>
                <span class="eventv">${idnum}</span>
            </div>
        </c:if>
        <c:if test="${navn != null}">
            <div class="event">
                <span class="eventl">${en ? "Name:" : "Navn/foretaksnavn:"}</span>
                <span class="eventv">${fn:escapeXml(navn)}</span>
            </div>
        </c:if>
            <c:if test="${organisasjonsform != null}">
                <div class="event">
                    <span class="eventl">${en ? "Organizational form:" : "Organisasjonsform:"}</span>
                    <span class="eventv">${fn:escapeXml(organisasjonsform)}</span>
                </div>
            </c:if>
            <c:if test="${antallAnsatte != null}">
                <div class="event">
                    <span class="eventl">${en ? "Number of employees:" : "Antall ansatte:"}</span>
                    <span class="eventv">${fn:escapeXml(antallAnsatte)}</span>
                </div>
            </c:if>
            <c:if test="${antallAnsatte_menn != null  || antallAnsatte_kvinner != null}">
                <div class="event">
                    <span class="eventl">${en ? "Total number of employees:" : " Totalt antall ansatte(SST):"}</span>
                    <span class="eventv">${fn:escapeXml(antallAnsatte_menn + antallAnsatte_kvinner)}</span>
                <c:if test="${antallAnsatte_menn != null }">
                    <div class="event" style="margin-left: 120px">
                    <span class="eventl">${en ? "Men:" : "Menn:"}</span>
                    <span class="eventv">${fn:escapeXml(antallAnsatte_menn)}</span>
                    </div>
                </c:if>
                    <c:if test="${antallAnsatte_kvinner != null}">
                    <div class="event" style="margin-left: 120px">
                        <span class="eventl">${en ? "Women:" : "Kvinner:"}</span>
                        <span class="eventv">${fn:escapeXml(antallAnsatte_kvinner)}</span>
                    </div>
                    </c:if>
                </div>
            </c:if>
            <c:if test="${forretingsadresse != null}">
                <div class="event">
                    <span class="eventl">${en ? "Business address :" : "Forretningsadresse:"}</span>
                    <span class="eventv">${fn:escapeXml(forretingsadresse)}</span>
                </div>
            </c:if>
        <c:if test="${forretingskommune != null}">
            <div class="event">
                <span class="eventl">${en ? "Business council:" : "Forretningskommune:"}</span>
                <span class="eventv">${fn:escapeXml(forretingskommune)}</span>
            </div>
        </c:if>
        <c:if test="${forretingsland != null}">
            <div class="event">
                <span class="eventl">${en ? "Business land:" : "Forretningsland:"}</span>
                <span class="eventv">${fn:escapeXml(forretingsland)}</span>
            </div>
        </c:if>
            <c:if test="${postadresse_adresse != null}">
                <div class="event">
                    <span class="eventl">${en ? "Postal address:" : "Postadresse:"}</span>
                    <span class="eventv">${fn:escapeXml(postadresse_adresse)}</span>
                </div>
            </c:if>
            <c:if test="${registrertdato != null}">
                <div class="event">
                    <span class="eventl">${en ? "Registration date:" : "Registrert dato:"}</span>
                    <span class="eventv">${fn:escapeXml(registrertdato)}</span>
                </div>
            </c:if>
            <c:if test="${naringskode != null}">
                <div class="event">
                    <span class="eventl">${en ? "Activity code:" : "Næringskode:"}</span>
                    <span class="eventv">${fn:escapeXml(naringskode)}</span>
                </div>
            </c:if>
            <c:if test="${sektorkode != null}">
                <div class="event">
                    <span class="eventl">${en ? "Sector code:" : "Sektorkode:"}</span>
                    <span class="eventv">${fn:escapeXml(sektorkode)}</span>
                </div>
            </c:if>
            <c:if test="${forvaltningsidnum != null}">
                <div class="event">
                    <span class="eventl">${en ? "NSD id-number:" : "NSD id-nummer:"}</span>
                    <span class="eventv"> <a href="<p:url value="/data/enhet/${fn:escapeXml(forvaltningsidnum)}"/>" >${fn:escapeXml(forvaltningsidnum)}</a></span>
                </div>
            </c:if>
    </div>

        <%-- =================================== enhet ansatte basert på arbeid yrke ============================ --%>
        <c:if test="${!(empty arbeideyrke)}">

            <table class="text zebra sortable">
                <caption>${en ? "Number of emplyees based on occupation code" : "Antall ansatte basert på yrkeskode"}</caption>

                <thead>
            <%--
             <tr>
                 <th></th>
                 <th></th>
                 <th colspan="2">${en ? "Number of employees" : "Antall ansatte"}</th>
             </tr>
             --%>
             <tr>
                 <th>${en ? "Occupation code" : "Yrkeskode"}</th>
                 <th>${en ? "Professional title" : "Yrkestittel"}</th>
                     <%--<th>${en ? "Number of employees" : "Antall ansatte"}</th>--%>
                  <th>${en ? "Menn" : "Menn"}</th>
                  <th>${en ? "Women" : "Kvinner"}</th>
             </tr>
             </thead>

             <tbody>
             <c:forEach items="${arbeideyrke}" var="de">
                 <tr>
                     <td>${de.arbYrke}</td>
                     <td>${de.yrkesTittel}</td>
                     <td>${de.antallAnsatte_menn}</td>
                     <td>${de.antallAnsatte_kvinner}</td>
                        <%--<td></td><td></td>--%>
                 </tr>
             </c:forEach>
             </tbody>
         </table>
            <%--<p class="tablenote"> Tabellen viser antall ansatte basert på yrkeskode. Merk at der antall ansatte er mindre enn 5 vil den vises ikke. </p>--%>
     </c:if>
     <%-- ===================================================================== --%>

        <%-- ========= følgende viser lenker til interne  og eksterne ressurser som er relatert til denne enheten. ==========  --%>
        <div class="event">
        <h3 class="eventh">${en ? "Internal links" : "Lenker til interne ressurser"}</h3>
        <c:if test="${forvaltningsidnum ==null }">
            <p><em>${en ? "No links for this unit." : "Ingen lenker for denne enheten."}</em></p>
        </c:if>
        <c:if test="${forvaltningsidnum !=null}">
            <ul>
             <li><a href="<p:url value="/data/enhet/${forvaltningsidnum}"/>" target="_blank">${en ? "This unit in NSDs state administration database" : "Denne enheten i NSD sin forvaltningsdatabasen"}</a></li>
            </ul>
        </c:if>
        </div>
        <div class="event">
        <h3 class="eventh">${en ? "External links" : "Lenker til eksterne ressurser"}</h3>
        <c:if test="${orgnummer == null }">
            <p><em>${en ? "No links for this unit." : "Ingen lenker for denne enheten."}</em></p>
        </c:if>
        <c:if test="${orgnummer != null }">
            <ul>
                    <li><a href="http://w2.brreg.no/enhet/sok/detalj.jsp?orgnr=${orgnummer}" target="_blank">${en ? "This unit in the Brønnøysund Register" : "Denne enheten i Enhetsregisteret fra Brønnøysundsregistrene"}</a></li>
            </ul>
        </c:if>
        </div>
        <%-- ========= følgende viser  (i fet skrifttype) sin plass i forvaltningshierarkiet ==========  --%>
        <div class="event">
            <h3 class="eventh">${en ? "This unit ((shown in bold)) place in the state adminstration hierarchy" : "Enheten (i fet skrifttype) sin plass i forvaltningshierarkiet"}</h3>
        <c:if test="${staten != null && fn:length(staten.get(0)) > 0}">
            <c:forEach items="${staten}" var="staten">
                <p:enhethierarki rot="${staten.get(0)}" idnum="${idnum}" />
            </c:forEach>
        </c:if>
        </div>
    </div>

</div>


<div id="sidebar">
    <c:if test="${no}">
    <h3>Organisasjons Hierarki</h3>
        <p>
            På <a href="<p:url value="/data/enhetsregisteret"/>">hierarki</a>
            kan du se oversikt over organisasjonshierarki.
      </p>
    <h3>Søk</h3>
    <p>
        På <a href="<p:url value="/data/organisasjon/sok"/>">Søk</a>
        side kan du søke på organisasjonsnavn eller kommune hvor enheten har sin forretningsadresse. Du kan velge variabler som skal vises i resultattabell.
    </p>
    <h3>Statistikk side</h3>
    <p>
        På <a href="<p:url value="/data/organisasjon/statistikk"/>">statistikk</a>
        kan du se oversikt over organisasjoner based på ogranisasjonsform, næringskode, sektorkode.
    </p>

        </c:if>
        <c:if test="${en}">
        </c:if>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
