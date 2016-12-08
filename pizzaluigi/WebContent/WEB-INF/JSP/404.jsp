<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix ='vdab' uri='http://vdab.be/tags'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title="Pagina niet gevonden"/>
</head>
<body>
	<vdab:menu/>
	<h1>Pagina niet gevonden</h1>
	<img src='<c:url value="/images/fout.jpg"/>' alt='fout'>
	<p>De pagina die u zocht bestaat niet op onze website.</p>
</body>
</html>