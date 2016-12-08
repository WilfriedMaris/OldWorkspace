<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang="nl">
<head>
<vdab:head title="Overzicht" />
</head>
<body>
	<vdab:titel image="Stamp" titel="Het Cultuurhuis:overzicht" />
	<h2>Gelukte Reserveringen</h2>
	<table>
		<tr>
			<th>Datum</th>
			<th>Titel</th>
			<th>Uitvoerders</th>
			<th>Prijs(€)</th>
			<th>Plaatsen</th>
		</tr>
		<c:forEach var='voorstelling' items='${voorstellingen}'>
			<c:if test="${gelukt.containsKey(voorstelling.voorstellingsNr)}">
				<tr>
					<td><fmt:formatDate value='${voorstelling.datum}' type='both'
							dateStyle='short' timeStyle='short' pattern="dd/MM/YY HH:mm" /></td>
					<td>${voorstelling.titel}</td>
					<td>${voorstelling.uitvoerders}</td>
					<td>€<fmt:formatNumber value='${voorstelling.prijs}'
							minFractionDigits='2' /></td>
					<td align="right">${gelukt[voorstelling.voorstellingsNr]}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
	<h2>Mislukte reserveringen</h2>
	<table>
		<tr>
			<th>Datum</th>
			<th>Titel</th>
			<th>Uitvoerders</th>
			<th>Prijs(€)</th>
			<th>Plaatsen</th>
			<th>Vrije Plaatsen</th>
		</tr>
		<c:forEach var='voorstelling' items='${voorstellingen}'>
			<c:if test="${mislukt.containsKey(voorstelling.voorstellingsNr)}">
				<tr>
					<td><fmt:formatDate value='${voorstelling.datum}' type='both'
							dateStyle='short' timeStyle='short' pattern="dd/MM/YY HH:mm" /></td>
					<td>${voorstelling.titel}</td>
					<td>${voorstelling.uitvoerders}</td>
					<td>€<fmt:formatNumber value='${voorstelling.prijs}'
							minFractionDigits='2' /></td>
					<td align="right">${mislukt[voorstelling.voorstellingsNr]}</td>
					<td align="right">${voorstelling.vrijePlaatsen}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>