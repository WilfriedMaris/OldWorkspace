<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang="nl">
<head>
<vdab:head title="Cultuurhuis" />
</head>
<body>
	<vdab:titel image="Shakespeare" titel="Het Cultuurhuis:voorstellingen" />
	<h2>Genres</h2>
	<nav>
		<ul>
			<c:forEach var='genre' items='${genres}'>
				<li class="menu"><a
					href="<c:url value=''>
			 		<c:param name='id' value='${genre.genreNr}'/> 
			 	</c:url>">
						${genre.naam}</a></li>
			</c:forEach>
		</ul>
	</nav>
	<c:if test="${not empty gekozenGenre}">
		<h2>${gekozenGenre.naam}voorstellingen</h2>
		<table>
			<tr>
				<th>Datum</th>
				<th>Titel</th>
				<th>Uitvoerders</th>
				<th>Prijs</th>
				<th>Vrije plaatsen</th>
				<th>Reserveren</th>
			</tr>
			<c:forEach var='voorstelling' items='${voorstellingen}'>
				<tr>
					<td><fmt:formatDate value='${voorstelling.datum}' type='both'
							dateStyle='short' timeStyle='short' pattern="dd/MM/YY HH:mm" /></td>
					<td>${voorstelling.titel}</td>
					<td>${voorstelling.uitvoerders}</td>
					<td>€<fmt:formatNumber value='${voorstelling.prijs}'
							minFractionDigits='2' /></td>
					<td align="right">${voorstelling.vrijePlaatsen}</td>
					<td><c:if test="${voorstelling.vrijePlaatsen != 0}">
							<a
								href="<c:url value='/reserveren.htm'>
					    			<c:param name='voorstellingsNr' value='${voorstelling.voorstellingsNr}'/>
					    		</c:url>">Reserveren
							</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>