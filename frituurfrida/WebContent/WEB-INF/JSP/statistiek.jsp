<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url='head.jsp'>
	<c:param name='title' value='Statistiek' />
</c:import>
<style>
td:last-child {
	text-align: right;
}
</style>
</head>
<vdab:menu />
<body>
	<h1>Statistiek</h1>
	<c:if test="${not empty statistiek}">
		<table>
			<thead>
				<tr>
					<th>URL</th>
					<th>aantal requests</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='entry' items='${statistiek}'>
					<tr>
						<td>${entry.key}</td>
						<td>${entry.value}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>