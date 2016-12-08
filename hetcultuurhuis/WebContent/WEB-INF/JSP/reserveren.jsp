<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<!doctype html>
<html lang="nl">
<head>
<vdab:head title="Reserveren" />
</head>
<body>
	<vdab:titel image="Chair" titel="Het Cultuurhuis:reserveren " />
	<form method="post">
		<dl>
			<dt>Voorstelling:</dt>
			<dd>${voorstelling.titel}</dd>
			<dt>Uitvoerders:</dt>
			<dd>${voorstelling.uitvoerders}</dd>
			<dt>Datum:</dt>
			<dd>
				<fmt:formatDate value='${voorstelling.datum}' type='both'
					dateStyle='short' timeStyle='short' pattern="dd/MM/YY HH:mm" />
			</dd>
			<dt>Prijs:</dt>
			<dd>
				€
				<fmt:formatNumber value='${voorstelling.prijs}'
					minFractionDigits='2' />
			</dd>
			<dt>Vrije plaatsen:</dt>
			<dd>${voorstelling.vrijePlaatsen}</dd>
			<dt>Plaatsen:</dt>
			<dd>
				<input type="number" name="teReserveren"
					<c:if test="${not empty mandje}">value="${mandje.get(voorstelling.voorstellingsNr)}"
				</c:if>>
				<!-- Onderstaande kan ook vervangen worden door min en max in inputtype number. -->
				<c:if test="${not empty fout}">${fout}</c:if>
			</dd>
		</dl>
		<input type="submit" value="Reserveren" name="reserveren"
			class="submitknop">
	</form>
</body>
</html>