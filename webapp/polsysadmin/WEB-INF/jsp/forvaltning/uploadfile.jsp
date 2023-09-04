<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
 - Description: Index-side for polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<style>
    .mainTildelingsbrev{
       /* width: 300px;
        height: 120px;
        position: absolute;
        top: 30%;
        left: 25%;*/
       /* transform: translate(-50%, -50%);*/
        text-align: center;
        border: 2px solid;
        /*margin-bottom: 70px;*/
        width: 300px;
        margin-top: 30px;
        height: 130px;
    }
    .formTildelingsbrev{
        width: 100%;
        position: relative;
        display: block;
        margin: 20px auto;
    }
    .inputTildelingsbrev{
        margin: 10px 0;
    }
    .mainAarsmeldinger{
       /* width: 300px;
        height: 120px;
        position: absolute;
        top: 60%;
        left: 25%;*/
        /*transform: translate(-50%, -50%);*/
        /*top: 30%;*/
        margin-top: 5%;
        position: relative;
        text-align: center;
        border: 2px solid;
        width: 300px;
        height: 130px;
    }
    .formAarsmeldinger{
        width: 100%;
        position: relative;
        display: block;
        margin: 20px auto;
    }
    .inputAarsmeldinger{
        margin: 10px 0;
    }
    .mainInstrukser{
       /* top: 60%;*/
        margin-top: 5%;
        position: relative;
        text-align: center;
        border: 2px solid;
        width: 300px;
        height: 130px;
    }
    .formInstrukser{
        width: 100%;
        position: relative;
        display: block;
        margin: 20px auto;
    }
    .inputInstrukser{
        margin: 10px 0;
    }


</style>
<div id="main" class="wide">

    <h1>Last opp fil</h1>

    <h3>Forvaltning-administrasjonssider last opp Årsmeldinger og ttildelingsbrev filer .</h3>
    <div class="mainTildelingsbrev">
        <h3>Last opp Tildelingsbrev:</h3>
        <c:if test="${param.lagrettildelingsbrev != null}">
            <h3 style="color:green;">-- fil ble lastet opp OK --</h3>
        </c:if>
        <form class="formTildelingsbrev" action="<c:url value="/forvaltning/uploadtildelingsbrev.p" />" method="post" enctype="multipart/form-data">
            <input class="inputTildelingsbrev" type="file" name="file"/>
            <input class="inputTildelingsbrev" type="submit" value="Last opp"/>
        </form>
    </div>

    <div class="mainAarsmeldinger">
        <h3>Last opp Årsmeldinger:</h3>
        <c:if test="${param.lagretaarsmeldinger != null}">
            <h3 style="color:green;">-- fil ble lastet opp OK --</h3>
        </c:if>
        <form class="formAarsmeldinger" action="<c:url value="/forvaltning/uploadaarsmeldinger.p" />" method="post" enctype="multipart/form-data">
            <input class="inputAarsmeldinger" type="file" name="file"/>
            <input class="inputAarsmeldinger" type="submit" value="Last opp"/>
        </form>
    </div>

    <div class="mainInstrukser">
        <h3>Last opp Instrukser:</h3>
        <c:if test="${param.lagretinstrukser != null}">
            <h3 style="color:green;">-- fil ble lastet opp OK --</h3>
        </c:if>
        <form class="formInstrukser" action="<c:url value="/forvaltning/uploadinstrukser.p" />" method="post" enctype="multipart/form-data">
            <input class="inputInstrukser" type="file" name="file"/>
            <input class="inputInstrukser" type="submit" value="Last opp"/>
        </form>
    </div>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />