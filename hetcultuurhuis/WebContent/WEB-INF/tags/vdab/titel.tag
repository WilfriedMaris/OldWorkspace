<%@tag description='titel van de pagina' pageEncoding='UTF-8'%>
<%@attribute name='image' required='true' type='java.lang.String'%>
<%@attribute name='titel' required='true' type='java.lang.String'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<h1>
	<img src='<c:url value="/images/${image}.png"/>' alt='${image}'
		height="50" width="50"> ${titel}
</h1>

<c:if test="${!titel.equals('Het Cultuurhuis:voorstellingen')}">
	<a href="<c:url value='/index.htm'/>">Voorstellingen</a>
</c:if>
<c:if test="${not empty mandje}">
	<c:if test="${!titel.equals('Het Cultuurhuis:reservatiemandje')}">
		<a href="<c:url value='/reservatiemandje.htm'/>">Reservatiemandje</a>
	</c:if>
	<c:if
		test="${!titel.equals('Het Cultuurhuis:bevestiging reservaties')}">
		<a href="<c:url value='/bevestigingReservatie.htm'/>">Bevestiging
			reservatie</a>
	</c:if>
</c:if>