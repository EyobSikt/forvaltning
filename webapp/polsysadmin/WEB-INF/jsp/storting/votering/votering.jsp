<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<%-- 
 - Author(s): HVB
 - Copyright NSD
 - Description: Index-side for polsys-admin, forvaltning.
--%>

<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

    <div class="breadcrumbs">
            Du er her:
            <a href="http://nsd241/polsysadmin/">PolSys-Admin</a>
            > <a href="http://nsd241/polsysadmin/storting/index.p">Storting</a>
            > Les og overføre votering data
    </div>


<h1>Storting-administrasjonssider.</h1>

<h2>Velg fra listen</h2>
<ul>
<li>
   <A HREF="<p:url value="/storting/votering/lesfil.p"/>" onClick="popup = window.open('<p:url value='/storting/votering/lesfil.p'/>', 'PopupPage', 'height=350,width=600,left=2400,top=100,resizable=yes,status=yes,directories=yes,location=yes'); return false" target="_blank" >
       Lese votering data fra data.stortinget  til temporær database</A>
</li>


<li><strong><a href="<p:url value="/storting/votering/kontrolloverfore/" />">Overføre votering data til polsys databasen</a></strong></li>
</ul>

</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
