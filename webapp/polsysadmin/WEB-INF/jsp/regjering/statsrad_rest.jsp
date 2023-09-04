<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
 - Author(s): HVB
 - Copyright NSD
 - Description: Index-side for polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>   -->

<script type="text/javascript" src="<c:url value="/js/jquery-1.7.1.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/rest.js" />"></script>



<div id="main" class="wide">

    <div class="breadcrumbs">
        Du er her:  <a href="/polsysadmin">Polsys</a>
        >  Regjering
    </div>

    <h1>Regjering-administrasjonssider.  REST EKSEMPEL</h1>


    STATSRÅD NAVNLIST KOMMER HER........




    <table class="zebra text" id="wineList">
        <caption>t_regjering_norske_statsraader_ny</caption>
        <thead>
        <tr><th></th><!--<th>personkode</th>--><th>Statsråd etternavn</th><th>Statsråd fornavn</th><th>Født dd-mm-yyyy</th><th>Død dd-mm-yyyy</th><th>Parti</th><th>Kjønn</th><th>Periodestart</th></tr>
        </thead>
        <tbody>
        <tr>
                <td><button id="btnSave">reg_ny</button></td>

            <td><input type="text" size="20" name="etternavn" id="etternavn"  required/>
                    <c:if test="${param.lagret != null}">
                        <h4 style="color:green;">-- Ny statsråd registrert OK --</h4>
                    </c:if>
                </td>
                <td><input type="text"  size="20" name="fornavn" id="fornavn"  required/>
                </td>
                <td><input type="text" size="10" name="foedt" id="foedt" /></td>
                <td><input type="text" size="10" name="doedt" id="doedt" /></td>
                <td>
                    <select id = "parti" name = "parti">
                        <option value="-1"></option>
                    </select>
                  </td>
                <td> <select name="kjoenn" id="kjoenn">
                    <option value="-1"></option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                </select></td>
                <td> <select name="periodestart" id="periodestart">
                    <option value="-1"></option>
                    <option value="2013-2017">2013-2017</option>
                </select></td>

        </tr>
        </tbody>
    </table>








</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
