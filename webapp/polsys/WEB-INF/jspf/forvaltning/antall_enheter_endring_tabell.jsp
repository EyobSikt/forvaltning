<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%-- ===================================================================== --%>
<table class="zebra">
<caption>${fn:escapeXml(param.tabelloverskrift)}</caption>

<thead>
<tr>
<th></th>
<th></th>
<c:forEach items="${tabelldata}" var="d"><th>${d.fromYear}-${d.toYear}</th></c:forEach>
</tr>
</thead>

<tbody>
<tr>
<th>Number at the start of the period</th>
<td>=</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.numberAtStart}</td></c:forEach>
</tr>

<tr>
<th>Foundings</th>
<td>+</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.foundings}</td></c:forEach>
</tr>

<tr>
<th>Immigration Up</th>
<td>+</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.immigrationUp}</td></c:forEach>
</tr>

<tr>
<th>Immigration</th>
<td>+</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.immigration}</td></c:forEach>
</tr>

<tr>
<th>Immigration Down</th>
<td>+</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.immigrationDown}</td></c:forEach>
</tr>

<tr>
<th>Emigration Up</th>
<td>-</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.emigrationUp}</td></c:forEach>
</tr>

<tr>
<th>Emigration</th>
<td>-</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.emigration}</td></c:forEach>
</tr>

<tr>
<th>Emigration Down</th>
<td>-</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.emigrationDown}</td></c:forEach>
</tr>

<tr>
<th>Terminations</th>
<td>-</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.terminations}</td></c:forEach>
</tr>

<tr>
<th>Number at the end of the period</th>
<td>=</td>
<c:forEach items="${tabelldata}" var="d"><td>${d.numberAtEnd}</td></c:forEach>
</tr>
</tbody>

</table>
<%-- ===================================================================== --%>



