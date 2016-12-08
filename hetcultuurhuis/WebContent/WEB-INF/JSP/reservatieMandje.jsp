<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<!doctype html>
<html lang="nl">
<head>
<vdab:head title="Reservatie Mandje" />
</head>
<body>
	<vdab:titel image="Bag" titel="Het Cultuurhuis:reservatiemandje" />
	<form method="post">
		<table>
			<tr>
				<th>Datum</th>
				<th>Titel</th>
				<th>Uitvoerders</th>
				<th>Prijs</th>
				<th>Plaatsen</th>
				<th><input type='submit' name="verwijderenknop"
					value='Verwijderen'></th>
			</tr>
			<c:forEach var='entry' items='${voorstellingen}'>
				<c:set var='bedrag'
					value='${bedrag + (entry.prijs * mandje[entry.voorstellingsNr])}' />
				<tr>
					<td><fmt:formatDate value='${entry.datum}' type='both'
							dateStyle='short' timeStyle='short' pattern="dd/MM/YY HH:mm" />
					<td>${entry.titel}</td>
					<td>${entry.uitvoerders}</td>
					<td>€<fmt:formatNumber value='${entry.prijs}'
							minFractionDigits='2' />
					</td>
					<td align="right">${mandje[entry.voorstellingsNr]}</td>
					<td><input type="checkbox" name='verwijdercheck'
						value='${entry.voorstellingsNr}'></td>
				</tr>
			</c:forEach>
		</table>
	</form>
	<p>Te betalen: € ${bedrag}</p>
</body>
</html>