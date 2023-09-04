<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>


<c:import url="/WEB-INF/jspf/topp.jsp" />

<div id="main" class="wide">

    <div class="breadcrumbs">
        Du er her:
        <a href="http://nsd241/polsysadmin/">PolSys-Admin</a>
       > <a href="http://nsd241/polsysadmin/storting/index.p">Storting</a>
        > Votering
    </div>

<p>
    <c:choose>
    <c:when test="${param.sesjon==2010-2011 || param.sesjon==null}"><c:set var="sel2010" value="selected"> </c:set> <c:set var="tabellcaption" value=" Sesjon 2010-2011"> </c:set></c:when>
    <c:when test="${param.aar==2011-2012}"><c:set var="sel2011" value="selected"> </c:set> <c:set var="tabellcaption" value="Sesjon 2011-2012"> </c:set></c:when>
    <c:when test="${param.aar==2012-2013}"><c:set var="sel2012" value="selected"> </c:set> <c:set var="tabellcaption" value="Sesjon 2012-2013"> </c:set></c:when>
     <c:when test="${param.aar==2013-2014}"><c:set var="sel2013" value="selected"> </c:set> <c:set var="tabellcaption" value="Sesjon 2013-2014"> </c:set></c:when>
    </c:choose>

 <H3> Velge sesjon og hente votering  saksopplysninger</H3>
<p></p>
<FORM METHOD="get"  action="<p:url value="/storting/votering/saksopplysningerlist.p" />">

    <p>
        <label>Sesjon (2010-2011, 2011-2012, 2012-2013)*:</label>
        <select name="sesjon" >
            <!--
            <option  value="155_2010-2011"  <c:out value="${sel2012}"/> >Sesjon 155 - (2010-2011)</option>
              -->
            <option value="2010-2011"  <c:out value="${sel2010}"/> >Sesjon 2010-2011</option>
            <option  value="2011-2012"  <c:out value="${sel2011}"/> >Sesjon 2011-2012</option>
            <option  value="2012-2013"  <c:out value="${sel2012}"/> >Sesjon 2012-2013</option>
            <option  value="2013-2014"  <c:out value="${sel2013}"/> >Sesjon 2013-2014</option>

        </select>

    </p>

<P>
<INPUT TYPE="SUBMIT" VALUE="Les">

</FORM>
</p>


</div>

</div>



<c:import url="/WEB-INF/jspf/bunn.jsp" />