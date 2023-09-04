<%@ page session="false" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>
<jsp:useBean id="currentPageNumber" type="java.lang.Integer" scope="request"/>

<font face=Helvetica size=-1>

    <c:choose><c:when test="${param.rows != null && param.rows !=''}"><c:set var="maxPageItems" value="${param.rows}"></c:set></c:when> <c:otherwise> <c:set var="maxPageItems" value="50"></c:set></c:otherwise></c:choose>
    <c:if test="${param.sortfacet !=null}"> <c:set var="sortfacet" value="&sortfacet=${param.sortfacet}"></c:set> </c:if>
    <c:if test="${param.sortresult !=null}"> <c:set var="sortresult" value="&sortresult=${param.sortresult}"></c:set> </c:if>

    <pg:prev >&nbsp;<a href="<p:url value="/person/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}&pager.offset=${maxPageItems * (pageNumber-1)}${sortresult}${sortfacet}"/>">[&lt;&lt; forrige]</a></pg:prev>

    <pg:pages>
    <%
  if (pageNumber.intValue() < 10) {
    %>&nbsp;<%
  }
  if (pageNumber == currentPageNumber) {
    %><b><%= pageNumber %></b><%
  } else {
    %><a href="<p:url value="/person/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}&pager.offset=${maxPageItems * (pageNumber-1)}${sortresult}${sortfacet}"/>"><%= pageNumber %></a><%
  }
%>

    </pg:pages>

    <pg:next >&nbsp;<a href="<p:url value="/person/norskepolitikere/?navn=${param.navn}&bs=${param.bs}&st=${param.st}&sr=${param.sr}&ss=${param.ss}&periode=${param.periode}&aar=${param.aar}&pager.offset=${maxPageItems * (pageNumber-1)}${sortresult}${sortfacet}"/>">[neste &gt;&gt;]</a></pg:next>
<br></font>
