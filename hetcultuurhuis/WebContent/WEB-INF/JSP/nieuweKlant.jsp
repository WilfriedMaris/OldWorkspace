<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title="Nieuwe Klant" />
</head>
<body>
	<vdab:titel image="Baby" titel="Het Cultuurhuis:nieuwe klant" />
	<form method="post">
		<label>Voornaam: <input name="Voornaam">
		</label> <label>Familienaam: <input name="Familienaam">
		</label> <label>Straat: <input name="Straat">
		</label> <label>HuisNr.: <input name="Huisnr">
		</label> <label>Postcode: <input name="Postcode">
		</label> <label>Gemeente: <input name="Gemeente">
		</label> <label>Gebruikersnaam: <input name="Gebruikersnaam">
		</label> <label>Paswoord: <input name="Paswoord">
		</label> <label>Herhaal paswoord: <input name="Herhaal paswoord">
		</label> <input type="submit" value="OK" name="ok">
	</form>
	<ul class="fout">
		<c:if test="${not empty fouten}">
			<c:forEach var="fout" items="${fouten}">
				<li>${fout}</li>
			</c:forEach>
		</c:if>
	</ul>
</body>
</html>