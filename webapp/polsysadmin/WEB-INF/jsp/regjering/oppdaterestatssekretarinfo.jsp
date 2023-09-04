<%@ page isELIgnored ="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource dataSource="jdbc/pvoadmin"/>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/jspf/topp.jsp" > </c:import>


<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=utf-8" %>

<div id="main" class="wide">

    <div class="breadcrumbs">
        Du er her:  <a href="/polsysadmin">Polsys</a>
        >
             <a href="<p:url value="/regjering/index.p"/>">regjering</a>
            > <a href="<p:url value="/regjering/statssekretar.p"/>">Statssekretar</a>
            >  Statssekretærdetaljer
    </div>
 
<br><br>


<c:forEach items="${oppdaterepersoninfo}" var="oppdaterepersoninfo" begin="0" end="0">
 <h1>   ${oppdaterepersoninfo.etternavn}, ${oppdaterepersoninfo.fornavn} </h1>
</c:forEach>

<!--<h2 style="text-decoration:underline; color: black;">Statssekretærsaktivitet</h2>   -->

    <table class="zebra, text">
        <caption>Statssekretærsaktivitet (Norske_statssekretaerer_ny tabell blir oppdatert)
        </caption>
        <thead>
        <tr>
            <th></th>
            <th>Embete (Statssekretær)</th>
            <th>Departement</th>
            <th>Virkeperiode-start dd-mm-yyyy</th>
            <th>Virkeperiode-slutt dd-mm-yyyy</th>
            <th>Eksternkommentar</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <form action="<c:url value="/regjering/registerstatssekretarinfo.p" />" method="post">

                <td><input type="submit" value="reg ny" /></td>
                <td><input type="text" size="30" name="embete" value=""  />
                    <c:if test="${param.lagret != null}">
                        <h4 style="color:green;">-- Ny statssekretær info registrert OK --</h4>
                    </c:if>
                </td>

                <td>
                    <select name="departmentkode" >
                        <option value="-1"></option>
                        <c:forEach items="${departmentnavnilist}" var="e">
                            <option value="${e.departmentkode}"> ${fn:escapeXml(e.departmentnavn)} </option>
                        </c:forEach>
                    </select></td>

                <td><input type="text" size="10" name="startdato" value="" /></td>
                <td><input type="text" size="10" name="sluttdato" value="" /></td>
                <td><input type="text" size="20" name="eksternkommentar" value="" /></td>

                <c:forEach items="${personinfo}" var="personinfo" begin="0" end="0">
                    <td style = "display:none"><input type="hidden"  name="personkode" value="${personinfo.personkode}" /></td>
                    <td style = "display:none"><input type="hidden"  name="etternavn" value="${personinfo.etternavn}" /></td>
                    <td style = "display:none"><input type="hidden"  name="fornavn" value="${personinfo.fornavn}" /></td>
                    <td style = "display:none"><input type="hidden"  name="foedtaar" value="${personinfo.foedtaar}" /></td>
                    <td style = "display:none"><input type="hidden"  name="parti" value="${personinfo.parti}" /></td>
                    <td style = "display:none"><input type="hidden"  name="kode_dep" value="${personinfo.kode_dep}" /></td>
                </c:forEach>

            </form>
        </tr>


        <c:forEach items="${oppdaterepersoninfo}" var="oppdaterepersoninfo">
        <tr>
            <td></td>
            <td>
                <form class="" action="<p:url value="/regjering/lagrestatssekretarembete.p"/>" method="get" name="frmIndex" >
                    <c:if test="${param.startdato == oppdaterepersoninfo.virkeperiode_start && param.sluttdato == oppdaterepersoninfo.virkeperiode_slutt && param.kode_dep == oppdaterepersoninfo.kode_dep  &&  param.embete_lagret != null}">
                        <h4 style="color:green;">-- Embete ble oppdatert OK --</h4>
                    </c:if>
                    <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                    <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                    <input type="hidden" name="startdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />
                    <input type="hidden" name="sluttdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />

                    <input type="text"  name="embete" value="${fn:escapeXml(oppdaterepersoninfo.embete)}" />
                    <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
                </form>
        </td>
        <td>
            <form class="" action="<p:url value="/regjering/lagredepartment/"/>" method="get" name="frmIndex" >
                <c:if test="${param.department_lagret != null}">
                    <h4 style="color:green;">-- Department ble oppdatert OK --</h4>
                </c:if>
                    ${fn:escapeXml(oppdaterepersoninfo.department)}
                <!--  <input type="text" size="50" name="department" value="${fn:escapeXml(oppdaterepersoninfo.department)}" />  -->
              <!--  <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/> -->
            </form>
        </td>
        <td>
            <form class="" action="<p:url value="/regjering/lagre_statssekretar_start.p"/>" method="get" name="frmIndex" >
                <c:if test="${param.sluttdato == oppdaterepersoninfo.virkeperiode_slutt && param.startdato == null && param.kode_dep == oppdaterepersoninfo.kode_dep}">
                    <h4 style="color:green;">--  startdato ble oppdatert OK --</h4>
                </c:if>
                <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                <input type="hidden" name="sluttdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />

                <input type="text"  name="virkeperiode_start" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />
                <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
            </form>
        </td>
        <td>
            <form class="" action="<p:url value="/regjering/lagre_statssekretar_slutt.p"/>" method="get" name="frmIndex" >
                <c:if test="${param.startdato == oppdaterepersoninfo.virkeperiode_start && param.sluttdato == null && param.kode_dep == oppdaterepersoninfo.kode_dep}">
                    <h4 style="color:green;">-- sluttdato ble oppdatert OK --</h4>
                </c:if>
                <input type="hidden" name="sluttdatooppdatert" value="oppdatert" />
                <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                <input type="hidden" name="startdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />

                <input type="text"  name="virkeperiode_slutt" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />
                <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
            </form>
        </td>
            <td>
                <form class="" action="<p:url value="/regjering/lagrestatssekretareksternkommentar.p"/>" method="get" name="frmIndex" >
                    <c:if test="${param.startdato == oppdaterepersoninfo.virkeperiode_start && param.sluttdato == oppdaterepersoninfo.virkeperiode_slutt && param.kode_dep == oppdaterepersoninfo.kode_dep  &&  param.eksternkommentar_lagret != null}">
                        <h4 style="color:green;">--  eksternkommentar ble oppdatert OK --</h4>
                    </c:if>
                    <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                    <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                    <input type="hidden" name="startdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />
                    <input type="hidden" name="sluttdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />

                    <input type="text"  name="eksternkommentar" value="${fn:escapeXml(oppdaterepersoninfo.eksternkommentar)}" />
                    <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
                </form>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <p>PS:under Embete må det skrives <i>statssekretær</i></p>



</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
