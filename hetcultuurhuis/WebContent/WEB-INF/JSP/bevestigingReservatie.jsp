<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title="Bevestiging Reservaties" />
</head>
<body>
	<vdab:titel image="Stamp"
		titel="Het Cultuurhuis:bevestiging reservaties" />
	<c:if test="${not empty klant}">
		<c:set var='disabledofniet' value='disabled' />
	</c:if>
	<form method="post">
		<h2>Stap 1:Wie ben je ?</h2>
		<label>Gebruikersnaam: <input name="gebruikersnaam"
			${disabledofniet}>
		</label> <label>Paswoord: <input type="password" name="paswoord"
			${disabledofniet}>
		</label> <input type="submit" name="zoek" value="Zoek me op"
			class="submitknop" ${disabledofniet}> <input type="submit"
			name="nieuw" value="Ik ben nieuw" class="submitknop"
			${disabledofniet}>
		<c:choose>
			<c:when test="${not empty klant}">
				<h4>${klant.voornaam}${klant.familienaam} ${klant.straat}
					${klant.huisNr} ${klant.postcode} ${klant.gemeente}</h4>
			</c:when>
			<c:when test="${not empty fout}">
				<h4>${fout}</h4>
			</c:when>
		</c:choose>
		<h2>Stap 2:Bevestigen</h2>
		<input type="submit" name="bevestigen" value="Bevestigen"
			class="submitknop"
			<c:if test="${empty klant}">
				disabled
			</c:if>>
	</form>
</body>
</html>