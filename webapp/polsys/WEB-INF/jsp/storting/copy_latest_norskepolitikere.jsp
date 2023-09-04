<%--
  Created by IntelliJ IDEA.
  User: et
  Date: 17.nov.2010
  Time: 13:31:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<%@ page contentType="text/html;charset=iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- --------------------------------------------- inkluderer toppinnhold. --%>
<c:import url="/WEB-INF/jspf/topp.jsp">
    <c:param name="tittelNo" value="${fn:escapeXml(enhet.langtNavn)} - Storting" />
    <c:param name="tittelEn" value="${fn:escapeXml(enhet.langtNavn)} - Parliament Database" />
    <c:param name="beskrivelseNo" value="${fn:escapeXml(enhet.langtNavn)} i Stortinget hos NSD." />
    <c:param name="beskrivelseEn" value="${fn:escapeXml(enhet.langtNavn)} in the Parliament Database at NSD." />
</c:import>

<style type="text/css">




</style>


<div id="sidebar">
  <h2 class="NavigatorboxHeader">Avgrens søk</h2>

  <div class="fasetter">
  <div class="fasettertittel" >Personindeks </div>
  <ul>
  <table class="zebra, text">

  <c:forEach items="${biografi_bokstave}" var="solr_doktype2" begin="0" end="${fn:length(biografi_bokstave)}" step="5" varStatus="loop" >

  <c:if test="${loop.count < 6}">
          <tr>
          <td>
              <li>
                  <c:set var="names" value="${paramValues.bs}"/>

                  <c:set var="doknavn2" value="${biografi_bokstave[loop.index].bokstave}"/>
                  <c:set var="isSelected" value="false" scope="page"/>
                  <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                      <c:if test="${names[k] ==doknavn2 }">
                          <c:set var="isSelected" value="true" scope="page"/>
                      </c:if>
                  </c:forEach>

                  <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                         onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index].bokstave)}';
                                 else if (!this.checked)
                                 <c:set var="names" value="${paramValues.bs}"/>
                                 <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                 <c:if test="${names[k] !=doknavn2}">
                                 <c:set var="fqlist" value="&bs=" />
                                 <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                 </c:if>
                                 </c:forEach>
                                 window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                 <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                 <c:set var="myVar" value="" />
                                 </c:forEach>
                                 "/>
                    ${biografi_bokstave[loop.index].bokstave} </td>
                   </li>

                  <td>
                      <li>
                          <c:set var="doknavn3" value="${biografi_bokstave[loop.index+1].bokstave}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                              <c:if test="${names[k] ==doknavn3 }">
                                  <c:set var="isSelected" value="true" scope="page"/>
                              </c:if>
                          </c:forEach>
                      <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                             onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index+1].bokstave)}';
                                     else if (!this.checked)
                                     <c:set var="names" value="${paramValues.bs}"/>
                                     <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                     <c:if test="${names[k] !=doknavn3}">
                                     <c:set var="fqlist" value="&bs=" />
                                     <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                     </c:if>
                                     </c:forEach>
                                     window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                     <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                     <c:set var="myVar" value="" />
                                     </c:forEach>
                                     "/>
                          ${biografi_bokstave[loop.index+1].bokstave}
                      </li>
                  </td>
                  <td>
                      <li>
                          <c:set var="doknavn4" value="${biografi_bokstave[loop.index+2].bokstave}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                              <c:if test="${names[k] ==doknavn4 }">
                                  <c:set var="isSelected" value="true" scope="page"/>
                              </c:if>
                          </c:forEach>
                          <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                                 onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index+2].bokstave)}';
                                         else if (!this.checked)
                                         <c:set var="names" value="${paramValues.bs}"/>
                                         <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                         <c:if test="${names[k] !=doknavn4}">
                                         <c:set var="fqlist" value="&bs=" />
                                         <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                         </c:if>
                                         </c:forEach>
                                         window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                         <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                         <c:set var="myVar" value="" />
                                         </c:forEach>
                                         "/>
                              ${biografi_bokstave[loop.index+2].bokstave}
                      </li>
                  </td>
                  <td>
                      <li>
                          <c:set var="doknavn5" value="${biografi_bokstave[loop.index+3].bokstave}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                              <c:if test="${names[k] ==doknavn5 }">
                                  <c:set var="isSelected" value="true" scope="page"/>
                              </c:if>
                          </c:forEach>
                          <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
                                 onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index+3].bokstave)}${fn:escapeXml(requestScope.url)}';
                                         else if (!this.checked)
                                         <c:set var="names" value="${paramValues.bs}"/>
                                         <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                         <c:if test="${names[k] !=doknavn5}">
                                         <c:set var="fqlist" value="&bs=" />
                                         <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                         </c:if>
                                         </c:forEach>
                                         window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                         <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                         <c:set var="myVar" value="" />
                                         </c:forEach>
                                         "/>
                              ${biografi_bokstave[loop.index+3].bokstave}
                      </li>
                  </td>
                  <td>
                      <li>
                          <c:set var="doknavn6" value="${biografi_bokstave[loop.index+4].bokstave}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                              <c:if test="${names[k] ==doknavn6 }">
                                  <c:set var="isSelected" value="true" scope="page"/>
                              </c:if>
                          </c:forEach>
                          <input type="checkbox" name="fq"  <c:if test="${isSelected}">checked=true</c:if>
                                 onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index+4].bokstave)}${fn:escapeXml(requestScope.url)}';
                                         else if (!this.checked)
                                         <c:set var="names" value="${paramValues.bs}"/>
                                         <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                         <c:if test="${names[k] !=doknavn6}">
                                         <c:set var="fqlist" value="&bs=" />
                                         <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                         </c:if>
                                         </c:forEach>
                                         window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                         <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                         <c:set var="myVar" value="" />
                                         </c:forEach>
                                         "/>
                              ${biografi_bokstave[loop.index+4].bokstave}
                      </li>
                  </td>
      </tr>
  </c:if>

          <c:if test="${loop.count == 6}">
              <tr>

                  <td>
                  <li>
                      <c:set var="names" value="${paramValues.bs}"/>
                      <c:set var="doknavn7" value="${biografi_bokstave[loop.index].bokstave}"/>
                      <c:set var="isSelected" value="false" scope="page"/>
                      <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                          <c:if test="${names[k] ==doknavn7 }">
                              <c:set var="isSelected" value="true" scope="page"/>
                          </c:if>
                      </c:forEach>

                      <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                             onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index].bokstave)}${fn:escapeXml(requestScope.url)}';
                                     else if (!this.checked)
                                     <c:set var="names" value="${paramValues.bs}"/>
                                     <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                     <c:if test="${names[k] !=doknavn7}">
                                     <c:set var="fqlist" value="&bs=" />
                                     <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                     </c:if>
                                     </c:forEach>
                                     window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                     <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                     <c:set var="myVar" value="" />
                                     </c:forEach>
                                     "/>
                          ${biografi_bokstave[loop.index].bokstave}
                  </li>
              </td>
                  <td>
                      <li>
                          <c:set var="names" value="${paramValues.bs}"/>
                          <c:set var="doknavn8" value="${biografi_bokstave[loop.index+1].bokstave}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                              <c:if test="${names[k] ==doknavn8 }">
                                  <c:set var="isSelected" value="true" scope="page"/>
                              </c:if>
                          </c:forEach>

                          <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                                 onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index+1].bokstave)}${fn:escapeXml(requestScope.url)}';
                                         else if (!this.checked)
                                         <c:set var="names" value="${paramValues.bs}"/>
                                         <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                         <c:if test="${names[k] !=doknavn8}">
                                         <c:set var="fqlist" value="&bs=" />
                                         <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                         </c:if>
                                         </c:forEach>
                                         window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                         <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                         <c:set var="myVar" value="" />
                                         </c:forEach>
                                         "/>
                              ${biografi_bokstave[loop.index+1].bokstave}
                      </li>
                  </td>
                  <td>
                      <li>
                          <c:set var="names" value="${paramValues.bs}"/>
                          <c:set var="doknavn9" value="${biografi_bokstave[loop.index+2].bokstave}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                              <c:if test="${names[k] ==doknavn9 }">
                                  <c:set var="isSelected" value="true" scope="page"/>
                              </c:if>
                          </c:forEach>

                          <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                                 onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index+2].bokstave)}${fn:escapeXml(requestScope.url)}';
                                         else if (!this.checked)
                                         <c:set var="names" value="${paramValues.bs}"/>
                                         <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                         <c:if test="${names[k] !=doknavn9}">
                                         <c:set var="fqlist" value="&bs=" />
                                         <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                         </c:if>
                                         </c:forEach>
                                         window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                         <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                         <c:set var="myVar" value="" />
                                         </c:forEach>
                                         "/>
                              ${biografi_bokstave[loop.index+2].bokstave}
                      </li>
                  </td>
                  <td>
                      <li>
                          <c:set var="names" value="${paramValues.bs}"/>
                          <c:set var="doknavn10" value="${biografi_bokstave[loop.index+3].bokstave}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                              <c:if test="${names[k] ==doknavn10 }">
                                  <c:set var="isSelected" value="true" scope="page"/>
                              </c:if>
                          </c:forEach>

                          <input type="checkbox" name="bs"  <c:if test="${isSelected}">checked=true</c:if>
                                 onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}&aar=${param.aar}&bs=${fn:escapeXml(biografi_bokstave[loop.index+3].bokstave)}${fn:escapeXml(requestScope.url)}';
                                         else if (!this.checked)
                                         <c:set var="names" value="${paramValues.bs}"/>
                                         <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                         <c:if test="${names[k] !=doknavn10}">
                                         <c:set var="fqlist" value="&bs=" />
                                         <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                         </c:if>
                                         </c:forEach>
                                         window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}&aar=${param.aar}';
                                         <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                         <c:set var="myVar" value="" />
                                         </c:forEach>
                                         "/>
                              ${biografi_bokstave[loop.index+3].bokstave}
                      </li>
                  </td>

              </tr>
          </c:if>

      </c:forEach>
      </table>
  </ul>
</div>


<%--

<table class="zebra, text">
		<caption>Personindeks</caption>
        <tbody>
		<tr>
            <td width="22">
            <input type="checkbox" name="bs"  <c:if test="${isSelected_bs}">checked=true</c:if>
                   onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=a${fn:escapeXml(requestScope.url)}&aar=${param.aar}';
                           else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                           "/>A </td>
            <td width="22">
                <input type="checkbox" name="bs"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=b${fn:escapeXml(requestScope.url)}&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>B </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=c&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>C </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=d&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>D </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=e&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>E </td>
        </tr>
        <tr>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=f&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>F </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=g&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>G </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=h&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>H </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=i&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>I </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=j&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>J </td>
        </tr>
        <tr>

            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=k&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>K </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=l&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>L </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=m&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>M </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=n&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>N </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=o&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>O </td>
        </tr>
        <tr>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=p&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>P </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=q&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>Q </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=r&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>R </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=s&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>S </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=t&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>T </td>
        </tr>
        <tr>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=u&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>U</td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=v&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>V </td>
            <td width="24">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=w&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>W </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=x&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>X </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=y&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>Y </td>
        </tr>
        <tr>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=z&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>Z </td>
            <td width="24">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=æ&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>Æ </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=ø&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>Ø </td>
            <td width="22">
                <input type="checkbox" name="fq"  <c:if test="${isSelected_bs}">checked=true</c:if>
                       onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=å&aar=${param.aar}';
                               else if (!this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&bs=&aar=${param.aar}';
                               "/>Å </td>
                    </tr>
              </tbody>
         </table>

 --%>

  <div class="fasetter">
  <div class="fasettertittel" >Politiker i: </div>
      <ul>
    <c:set var="isSelected_st" value="false" scope="page"/>
        <c:if test="${param.st =='storting' }">
            <c:set var="isSelected_st" value="true" scope="page"/>
        </c:if>
    <c:set var="isSelected_sr" value="false" scope="page"/>
    <c:if test="${param.sr =='statsraad' }">
        <c:set var="isSelected_sr" value="true" scope="page"/>
    </c:if>
    <c:set var="isSelected_ss" value="false" scope="page"/>
    <c:if test="${param.ss =='statssekretar' }">
        <c:set var="isSelected_ss" value="true" scope="page"/>
    </c:if>
        <li>
    <input type="checkbox" name="st"  <c:if test="${isSelected_st}">checked=true</c:if>
           onclick="if (this.checked) window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=storting&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}';
                   else if (!this.checked)
                   window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}';
                   "/>stortingsrepresentat
        </li>
    <c:set var="isSelected" value="false" scope="page"/>
    <c:if test="${param.st =='storting' }">
        <c:set var="isSelected" value="true" scope="page"/>
    </c:if>
     <li>
    <input type="checkbox" name="sr"  <c:if test="${isSelected_sr}">checked=true</c:if>
           onclick="if (this.checked) window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=statsraad&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}';
                   else if (!this.checked)
                   window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}';
                   "/>statsråd
     </li>
    <c:set var="isSelected" value="false" scope="page"/>
    <c:if test="${param.st =='storting' }">
        <c:set var="isSelected" value="true" scope="page"/>
    </c:if>
        <li>
    <input type="checkbox" name="ss"  <c:if test="${isSelected_ss}">checked=true</c:if>
           onclick="if (this.checked) window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=statssekretar&periode=${param.periode}&aar=${param.aar}';
                   else if (!this.checked)
                   window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=&periode=${param.periode}&aar=${param.aar}';
                   "/>statssekretær
        </li>
      </ul>
  </div>
<%--
Politikere i:
<a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=storting&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>">stortingsrepresentat</a><br>
<a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=statsraad&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>">statsråd</a><br>
<a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=statssekretar&periode=${param.periode}&aar=${param.aar}"/>">statssekretær</a><br>

Periode:
<a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=1945&aar=${param.aar}"/>">1945-d.d. </a><br>
 <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=1903&aar=${param.aar}"/>">1903-1945 </a><br>
 <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=1814&aar=${param.aar}"/>">1814-1903 </a>
--%>


  <div class="fasetter">
      <div class="fasettertittel" >Stortingsperiode </div>
      <ul>
          <c:set var="names" value="${paramValues.periode}"/>
          <c:set var="periode_1945" value="1945"/>
          <c:set var="isSelected" value="false" scope="page"/>
          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
              <c:if test="${names[k] ==periode_1945 }">
                  <c:set var="isSelected" value="true" scope="page"/>
              </c:if>
          </c:forEach>
          <li>
              <input type="checkbox" name="periode"  <c:if test="${isSelected}">checked=true</c:if>
                     onclick="if (this.checked) window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}&periode=1945&aar=${param.aar}';
                             else if (!this.checked)

                             <c:set var="names" value="${paramValues.periode}"/>
                             <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                             <c:if test="${names[k] !=periode_1945}">
                             <c:set var="fqlist" value="&periode=" />
                             <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                             </c:if>
                             </c:forEach>
                             window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(myVar)}&aar=${param.aar}';
                             <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                             <c:set var="myVar" value="" />
                             </c:forEach>
                             "/> 1945-d.d.
          </li>
          <c:set var="names" value="${paramValues.periode}"/>
          <c:set var="periode_1903" value="1903"/>
          <c:set var="isSelected" value="false" scope="page"/>
          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
              <c:if test="${names[k] ==periode_1903}">
                  <c:set var="isSelected" value="true" scope="page"/>
              </c:if>
          </c:forEach>
          <li>
              <input type="checkbox" name="periode"  <c:if test="${isSelected}">checked=true</c:if>
                     onclick="if (this.checked) window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}&periode=1903&aar=${param.aar}';
                             else if (!this.checked)

                             <c:set var="names" value="${paramValues.periode}"/>
                             <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                             <c:if test="${names[k] !=periode_1903}">
                             <c:set var="fqlist" value="&periode=" />
                             <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                             </c:if>
                             </c:forEach>
                             window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(myVar)}&aar=${param.aar}';
                             <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                             <c:set var="myVar" value="" />
                             </c:forEach>
                             "/> 1903-1945
          </li>
          <c:set var="names" value="${paramValues.periode}"/>
          <c:set var="periode_1814" value="1814"/>
          <c:set var="isSelected" value="false" scope="page"/>
          <c:forEach var="k" begin="0" end="${fn:length(names)}" >
              <c:if test="${names[k] ==periode_1814 }">
                  <c:set var="isSelected" value="true" scope="page"/>
              </c:if>
          </c:forEach>
          <li>
              <input type="checkbox" name="periode"  <c:if test="${isSelected}">checked=true</c:if>
                     onclick="if (this.checked) window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(requestScope.url_periode)}&periode=1814&aar=${param.aar}';
                             else if (!this.checked)

                             <c:set var="names" value="${paramValues.periode}"/>
                             <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                             <c:if test="${names[k] !=periode_1814}">
                             <c:set var="fqlist" value="&periode=" />
                             <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                             </c:if>
                             </c:forEach>
                             window.location = '?navn=${param.navn}${fn:escapeXml(requestScope.url)}&st=${param.st}&sr=${param.sr}&ss=${param.ss}${fn:escapeXml(myVar)}&aar=${param.aar}';
                             <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                             <c:set var="myVar" value="" />
                             </c:forEach>
                             "/> 1814-1903
          </li>

      </ul>
  </div>



      <div class="fasetter">
      <div class="fasettertittel" >Se kun tidsrom </div>
      <ul>
      <table class="zebra, text">

      <c:forEach items="${valgaar}" var="aarValg" begin="0" end="${fn:length(valgaar)}" step="3" varStatus="loop" >
      <c:if test="${loop.count < 21}">
          <tr>
              <td>
                  <li>
                      <c:set var="names" value="${paramValues.aar}"/>
                      <c:set var="aar1" value="${valgaar[loop.index].periodeAar}"/>
                      <c:set var="isSelected" value="false" scope="page"/>
                      <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                      <c:if test="${names[k] ==aar1 }">
                          <c:set var="isSelected" value="true" scope="page"/>
                      </c:if>
                      </c:forEach>
                      <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                             onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index].periodeAar)}';
                                     else if (!this.checked)
                                     <c:set var="names" value="${paramValues.aar}"/>

                                     <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                     <c:if test="${names[k] !=aar1}">
                                     <c:set var="fqlist" value="&aar=" />
                                     <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                     </c:if>
                                     </c:forEach>
                                     window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}';
                                     <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                     <c:set var="myVar" value="" />
                                     </c:forEach>
                                     "/>
                          ${valgaar[loop.index].valgAar} </td>
              </li>

              <td>
                  <li>
                          <c:set var="aar2" value="${valgaar[loop.index+1].periodeAar}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                      <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                      <c:if test="${names[k] ==aar2 }">
                          <c:set var="isSelected" value="true" scope="page"/>
                      </c:if>
                      </c:forEach>

                      <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                             onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index+1].periodeAar)}';
                                     else if (!this.checked)
                                     <c:set var="names" value="${paramValues.aar}"/>
                                     <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                     <c:if test="${names[k] !=aar2}">
                                     <c:set var="fqlist" value="&aar=" />
                                     <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                     </c:if>
                                     </c:forEach>
                                     window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}';
                                     <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                     <c:set var="myVar" value="" />
                                     </c:forEach>
                                     "/>
                          ${valgaar[loop.index+1].valgAar} </td>
                  </li>
              </td>
              <td>
                  <li>
                          <c:set var="aar3" value="${valgaar[loop.index+2].periodeAar}"/>
                          <c:set var="isSelected" value="false" scope="page"/>
                      <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                      <c:if test="${names[k] ==aar3 }">
                          <c:set var="isSelected" value="true" scope="page"/>
                      </c:if>
                      </c:forEach>

                      <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                             onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index+2].periodeAar)}';
                                     else if (!this.checked)
                                     <c:set var="names" value="${paramValues.aar}"/>
                                     <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                     <c:if test="${names[k] !=aar3}">
                                     <c:set var="fqlist" value="&aar=" />
                                     <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                     </c:if>
                                     </c:forEach>
                                     window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}';
                                     <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                     <c:set var="myVar" value="" />
                                     </c:forEach>
                                     "/>
                          ${valgaar[loop.index+2].valgAar} </td>
              </li>
              </td>
          </tr>
      </c:if>

      <c:if test="${loop.count == 21}">
          <tr>
              <td>
                  <li>
                          <c:set var="names" value="${paramValues.aar}"/>
                          <c:set var="aar4" value="${valgaar[loop.index].periodeAar}"/>

                          <c:set var="isSelected" value="false" scope="page"/>
                      <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                       <c:if test="${names[k] ==aar4 && names[k]!='' }">
                      <c:set var="isSelected" value="true" scope="page"/>
                      </c:if>
                      </c:forEach>
                      <input type="checkbox" name="aar"  <c:if test="${isSelected}">checked=true</c:if>
                             onclick="if (this.checked) window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(requestScope.url)}${fn:escapeXml(requestScope.url_aar)}&aar=${fn:escapeXml(valgaar[loop.index].periodeAar)}';
                                     else if (!this.checked)
                                     <c:set var="names" value="${paramValues.aar}"/>
                                     <c:forEach var="k" begin="0" end="${fn:length(names)}" >
                                     <c:if test="${names[k] !=aar4}">
                                     <c:set var="fqlist" value="&aar=" />
                                     <c:set var="myVar" value="${stat.first ? '' : myVar}${fqlist}${names[k]}" />
                                     </c:if>
                                     </c:forEach>
                                     window.location = '?navn=${param.navn}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}${fn:escapeXml(myVar)}${fn:escapeXml(requestScope.url)}';
                                     <c:forEach var="v" begin="0" end="${fn:length(myVar)}" >
                                     <c:set var="myVar" value="" />
                                     </c:forEach>
                                     "/>
                          ${valgaar[loop.index].valgAar} </td>
                  </li>
              </td>
          </tr>
      </c:if>

      </c:forEach>
      </table>
      </ul>
      </div>


<%--

<table  class="text">
<tbody>
<tr>
 <td  rowspan="3" valign="top" width="62px">
    <c:forEach items="${valgaar}" var="aar" >
      <a href="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${aar.periodeAar}"/>"> ${aar.valgAar}</a>
    </c:forEach>
  </td>
</tr>
</tbody>
</table>

 --%>



</div>



<div id="main" class="superwide">
<div>
    <c:if test="${no}">
Du er her:
<a href="<p:url value="/"/>">PolSys</a>
> <a href="<p:url value="/storting/"/>">Storting</a>
> norskepolitikere

    </c:if>
</div>


<p>&nbsp;</p>

<c:if test="${param.p == null || param.p=='sok'}">

    <c:if test="${no}"><p> <h2>Finn norskepolitikere og last ned biografier</h2></p></c:if>
     <c:if test="${en}"><p><h2>This page is not translated to English </h2></p>
     <p>----------------------------------------------</p>
    </c:if>


<h3>Søketips:</h3>
 <span class="overridep">
Du kan søke i politierearkivet (1814-d.d.)  ved å skrive et ord i søke-boksen eller ved å velge fra høyre menyen. Du kan avgrense søket ved å bruke menyen.
Etter at du får resultat har du mulighet å laste ned ulike biografier av treff i excel eller csv format.  
</span>

      <div  class="dhtmlgoodies_vismer" style="display: inline;">Vis mer</div>
    <div class="dhtmlgoodies_lessmer">
        <div >

            <p>&nbsp;</p>
            <h2>Dataset  variable beskrivelse</h2>
            <p>
                Følgende variabler er automatisk inkludert i de nedlastede dataene som standard:
            <ul>
                <li>Fornavn</li>
                <li>Etternavn</li>
            </ul>
            </p>


                <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
                    <caption>Stortingsaktivitet</caption>
                    <thead>
                    <tr>
                        <th  valign="top">
                            <strong>Varible</strong>

                        <th  valign="top">
                            <strong>Beskrivelse</strong>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th class="label">
                            Stortingsperiode
                        </th>
                        <td>
                            <p>Denne variabelen viser hvilken periode personen var valgt for.</p>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Fødselsdato
                        </th>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Dødsdato
                        </th>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Stortingsperiode
                        </th>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Representantnummer (Repnr)
                        </th>
                        <td>
                            <p>Representanene som blir valg inn på Stortinget representerer en valgkrets. Denne variabelen viser rekkefølgen innenfor valgkretsen representanten ble valgt inn for. Representant nummer 1 i dette feltet har altså flest valgstemmer bak seg osv. </p>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Suppleantnummer (Supnr)
                        </th>
                        <td>
                            <p>Representanene som blir valg inn på Stortinget representerer en valgkrets. Denne variabelen viser rekkefølgen innenfor valgkretsen vararepresentanten ble valgt inn for. Vararepresentant nummer 1 i dette feltet er altså den vara fra valgkretsen som har flest valgstemmer bak seg osv. </p>
                        </td>
                    </tr>

                    <tr>
                        <th class="label">
                            Valgkretskode
                        </th>
                        <td>
                            <p>Dette er et nytt kodesett som viser hvilken valgkrets personen tilhørte.</p>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Partitilhørighet (Parti)
                        </th>
                        <td>
                            <p>Partitilknytning til politikeren. Ved partiskifte innen stortingsperioden, er det som oftes partitilknytning ved valget som er registrert.</p>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Stilling
                        </th>
                        <td>
                            <p>Denne tekstvariabelen viser hvilken stilling politikeren hadde ved tidspunktet for valget og er i hovedsak hentet fra Statistisk sentralbyrå publikasjoner fra Stortingsvalgene.</p>
                        </td>
                    </tr>
                    </tbody>
                </table>


                <!-- yrke begnner her ------>

                <h2>Dataset - Utdanning og yrke </h2>
                <table class="zebra text"  cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
                    <caption>Stortingsaktivitet</caption>
                    <thead>
                    <tr>
                        <th  valign="top">
                            <strong>Varible</strong>

                        <th  valign="top">
                            <strong>Beskrivelse</strong>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th class="label">
                            Fra
                        </th>
                        <td>
                            <p>Året aktiviteten startet</p>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Til
                        </th>
                        <td>
                            <p>Året aktiviteten opphørte</p>
                        </td>
                    </tr>
                    <tr>
                        <th class="label">
                            Aktivitet
                        </th>
                        <td>
                            <p>Feltet inneholder opplysninger om representantenes utdanning, yrke medaljer/utmerkelser m.m. </p>
                        </td>
                    </tr>

                    </tbody>
                </table>

                <h2>Dataset - Kommunalpolitisk aktivitet </h2>

                <h2>Dataset - Fylkespolitisk aktivitet  </h2>

                <h2>Dataset - Partiverv </h2>

                <h2>Dataset - Offentlige verv </h2>

                <h2>Dataset - Adminverv </h2>

                <h2>Dataset - Orgverv  </h2>



        </div>
    </div>
    <script type="text/javascript">
        initShowHideDivs();
    </script>
    <p></p>



<%--
<form  ACTION="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}&start=${param.start}"/>" METHOD="GET">
<p>
<div >Søk i etternavn</div>

<input class="innputt" type="text" name="navn" size="50" maxlength="100" value="">
<input type="hidden" name="bs" value = "">
<input type="hidden" name="st" value = "">
<input type="hidden" name="sr" value = "">
<input type="hidden" name="ss" value = "">
<input type="hidden" name="aar" value = "">
<input type="hidden" name="start" value = "0">
<input class="subbmitt" type="submit" value="Søk">
</p>
</form>
--%>

 <%--
   <c:if test="${param.navn !=null}">
        <div class="filter">
           <div class="filter_item">
   
            <c:if test="${param.navn !=null && param.navn!='' }"> "${param.navn}"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
           &nbsp;&nbsp;
            <c:if test="${param.bs !=null && param.bs!='' }"> "${param.bs}"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            &nbsp;&nbsp;





            <c:if test="${param.st !=null && param.st!='' }">  "stortingsrepresentat"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            <c:if test="${param.sr !=null && param.sr!='' }">  "statsråd"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            <c:if test="${param.ss !=null && param.ss!='' }">  "statssekretær"  <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=&periode=${param.periode}&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            &nbsp;&nbsp;

            <c:if test="${param.periode !=null && param.periode!='' }">
                <c:if test="${param.periode=='1814'}">"1814-1903"</c:if>
                <c:if test="${param.periode=='1903'}">"1903-1945"</c:if>
                <c:if test="${param.periode=='1945'}">"1945-d.d."</c:if>
                <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=&aar=${param.aar}"/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            &nbsp;&nbsp;
            <c:if test="${param.aar !=null && param.aar!='' }">
            <c:forEach items="${valgaar}" var="aar" ><c:if test="${aar.periodeAar == param.aar}"> "${aar.valgAar}"</c:if> </c:forEach>
                <A  class ="banner" HREF="<p:url value="/storting/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar="/>"><img src="http://www.nsd.uib.no/common/polsys/img/kryss.jpg" border="0"></A> </c:if>
            </div>
       </div>
  </c:if>

 --%>
<p>&nbsp;</p>

 <form action="<p:url value="/storting/newlastned/" />"  method="post">

                    <select id="format" name="dataset" style="height: 18px;" >
                        <option selected="selected" value="">-- Select biografi --</option>
                        <option value="1">Stortingsaktivitet</option>
                        <option value="2">Utdanning og yrke</option>
                        <option value="3">Kommunalpolitisk aktivitet</option>
                        <option value="4">Fylkespolitisk aktivitet</option>
                        <option value="5">Partiverv</option>
                        <option value="6">Offentlige verv</option>
                        <option value="7">Adminverv</option>
                        <option value="8">Orgverv</option>
                    </select>

<input type="hidden" name="navn" value="<%= request.getParameter("navn") %>">
<input type="hidden" name="st" value="<%= request.getParameter("st") %>">
<input type="hidden" name="sr" value="<%= request.getParameter("sr") %>">
<input type="hidden" name="ss" value="<%= request.getParameter("ss") %>">
<input type="hidden" name="bs" value="<%= request.getParameter("bs") %>">
<input type="hidden" name="periode" value="<%= request.getParameter("periode") %>">      
<input type="hidden" name="aar" value="<%= request.getParameter("aar") %>">


<button name="filtype" value="EXCEL" title="Lastned dataset i excel format" type="submit">
<img alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/excel.png">
</button>
     
 <button name="filtype" value="CSV" title="Lastned dataset i CSV format" type="submit">
<img alt="Submit" src="http://www.nsd.uib.no/common/polsys/img/csv-icon.gif">
</button>

</form>

<p></p>

<div class="hitlist">

<c:set var="style" value="simple"></c:set>
<c:set var="position" value="top"></c:set>
<c:set var="maxPageItems" value="50"></c:set>
<c:set var="maxIndexPages" value="10"></c:set>

    <pg:pager items="${fn:length(allenorskepolitikere)}" index="center"
	maxPageItems="${maxPageItems}" maxIndexPages="${maxIndexPages}"
	isOffset="true" export="offset, currentPageNumber=pageNumber"
	scope="request">
	<%-- keep track of preference --%>
	<pg:param name="style" />
	<pg:param name="position" />
	<pg:param name="index" />
	<pg:param name="maxPageItems" />
	<pg:param name="maxIndexPages" />

 <c:set var="endLoop" value="${offset + maxPageItems}"/>
   <c:if test="${fn:length(allenorskepolitikere) < endLoop}">
      <c:set var="endLoop" value="${fn:length(allenorskepolitikere)}"/>
    </c:if>
<table class="zebra text" width="500" cellspacing="0" summary="Tabell norskepolitikere 1814-d.d.">
<caption> Antall treff Politikere: ${fn:length(allenorskepolitikere)} </caption>
<thead>
<tr>
<th width="75" valign="top">
<strong>Etternavn</strong>
</th>
<th width="75" valign="top">
        <strong>Fornavn</strong>
  </th>
<th width="65" valign="top">
<strong>Født år</strong>
</th>
</tr>
</thead>
<tbody>

<c:forEach items="${allenorskepolitikere}" var="allenorskepolitikere" varStatus="searchIndex" begin="${offset}" end="${endLoop}">
      <pg:item>
 <tr>
 <th class="label">
<A HREF="<p:url value="/person/politikerbiografi/?person_id=${allenorskepolitikere.personId}&p_info=personalia"/>">
        ${allenorskepolitikere.etterNavn}</A>
</th>

<td>
                  ${allenorskepolitikere.forNavn}
     </td>
<td>
${allenorskepolitikere.faar}
</td>
</tr>
</pg:item>
    </c:forEach>
</tbody>
</table>

 <p></p>
   <center>
	<pg:index>
		<jsp:include page="/WEB-INF/jsp/storting/felerenorskepolitikere.jsp" flush="true" />
	</pg:index>
     </center>   
</pg:pager>
<%--
</c:if>
</c:if>
--%>
</div>

<p></p>

</c:if>

 </div>


<%-- --------------------------------------------- inkluderer bunninnhold. --%>
<c:import url="/WEB-INF/jspf/bunn.jsp"/>
<%-- --------------------------------------------------------------------- --%>