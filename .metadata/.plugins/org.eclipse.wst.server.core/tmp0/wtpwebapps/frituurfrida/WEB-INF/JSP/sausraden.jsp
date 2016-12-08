<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url='head.jsp'>
	<c:param name='title' value='Sauzen' />
</c:import>
</head>
<vdab:menu />
<body>
	<h1>Saus raden</h1>
	<c:choose>
		<c:when test="${spel.tekst.isEmpty()}">
Te raden saus: ${spel.puntjes}
</c:when>
		<c:otherwise>
${spel.tekst}
</c:otherwise>
	</c:choose>
	<form method='post'>
		<label> Letter: <input name="letter" size="1" maxlength='1'
			autofocus required>
		</label> <input type="submit" value="raden" name="raden">
	</form>
	<script>
		document.getElementById('radenform').onsubmit = function() {
			document.getElementById('radenknop').disabled = true;
		};
	</script>
	<c:url value='' var='nieuwSpelURL'>
		<c:param name='nieuwSpel' value='true' />
	</c:url>
	<form method='post' action='${nieuwSpelURL}'>
		<input type='submit' value='nieuwspel' name='nieuwspel'>
	</form>
	<img src="<c:url value='/images/${spel.teller}.png'/>" alt='galgje'>
</body>
</html>