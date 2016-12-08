<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<fmt:setBundle basename="resourceBundles.teksten" />
<!doctype html>
<html>
<head>
<title><fmt:message key='titel' /></title>
<link rel='icon' href='images/favicon.ico'>
<meta name='viewport' content='width=device-width,initial-scale=1'>
<link rel='stylesheet' href='styles/default.css'>
</head>
<vdab:menu />
<body>
	<h1>
		<fmt:message key='titel' />
	</h1>
	<h2>
		<fmt:message key='${openGesloten}tekst' />
	</h2>
	<img alt="${openGesloten}"
		src="images/<fmt:message key='${openGesloten}'/>.png" />
	<h3>${adres.straat}
		${adres.huisNr} <br> ${adres.gemeente.naam}
		${adres.gemeente.postCode}
	</h3>
	<div>
		<fmt:message key='telefoon' />
		<a href="tel:+${telefoonNummerHelpDesk.replace('/','')}">${telefoonNummerHelpDesk}
		</a>
	</div>
</body>
</html>
