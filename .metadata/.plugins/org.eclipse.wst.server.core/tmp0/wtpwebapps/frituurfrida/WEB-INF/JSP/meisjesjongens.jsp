<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<!doctype html>
<html lang="nl">
<head>
<c:import url='head.jsp'>
	<c:param name='title' value='Jongens en Meisjes' />
</c:import>
</head>
<vdab:menu />
<body class="${cookie.meisjesjongens.value}">
	<form method="post">
		<h1>Meisjes jongens</h1>
		<input type="submit" name="meisjesjongens" value="meisjes"> <input
			type="submit" name="meisjesjongens" value="jongens">
	</form>
</body>
</html>