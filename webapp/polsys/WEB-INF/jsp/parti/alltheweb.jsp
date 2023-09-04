<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="m" uri="http://nsd.uib.no/taglibs/misc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="p" uri="http://nsd.uib.no/polsys/taglib" %>

<jsp:useBean id="currentPageNumber" type="java.lang.Integer" scope="request"/>
     

<pg:page export="firstItem, lastItem">

</pg:page>
<div class="rnav">

<c:choose><c:when test="${param.rows != null && param.rows !=''}"><c:set var="maxPageItems" value="${param.rows}"></c:set></c:when> <c:otherwise> <c:set var="maxPageItems" value="10"></c:set></c:otherwise></c:choose>
<c:if test="${param.sortfacet !=null}"> <c:set var="sortfacet" value="&sortfacet=${param.sortfacet}"></c:set> </c:if>
<c:if test="${param.sortresult !=null}"> <c:set var="sortresult" value="&sortresult=${param.sortresult}"></c:set> </c:if>


<pg:prev export="pageUrl">
&nbsp;...&nbsp;
</pg:prev>
<pg:pages export="pageUrl,pageNumber,firstItem,lastItem">

   <% if (pageNumber == currentPageNumber) { %> 
&nbsp;<span class="rnavCurr"><%= firstItem %>-<%= lastItem %></span>
    <% } else { %>
&nbsp;<a href='<p:url value='/parti/partidokumentarkivet/?q=${fn:escapeXml(param.q)}&start=${maxPageItems * (pageNumber-1)}&rows=${maxPageItems}${fn:escapeXml(requestScope.url)}&pager.offset=${maxPageItems * (pageNumber-1)}${sortresult}${sortfacet}' />' class="rnavLink"><%= firstItem %>-<%= lastItem %></a>
    <% } %>

</pg:pages>
<pg:next export="pageUrl">
&nbsp;&nbsp;...&nbsp;
</pg:next>
 av <%= request.getAttribute("antalltreff") %> treff

</div>


 
 