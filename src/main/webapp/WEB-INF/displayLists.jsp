<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="translated_texts" var="r"/>

<!DOCTYPE html>
<html>
<head>
	<title>Gestion des courses</title>
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<meta charset="utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>

	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="PREDEFINED_LISTS"/>
		</jsp:include>

		<%@include file="errorArea.jsp" %>

		<div id="body">
			<c:choose>
				<c:when test="${!empty requestScope.shoppingLists}">
					<form name="mainForm" id="mainForm" method="post" action="${pageContext.request.contextPath}/ServletDisplayLists">
						<c:forEach var="list" items="${requestScope.shoppingLists}">
							<jsp:include page="row.jsp">
								<jsp:param name="name" value="${list.nom}"/>
								<jsp:param name="id" value="${list.id}"/>
								<jsp:param name="basket" value="1"/>
							</jsp:include>
						</c:forEach>
					</form>
				</c:when>
				<c:otherwise>
					<p><fmt:message key="NO_SHOPPING_LISTS" bundle="${r}"/></p>
				</c:otherwise>
			</c:choose>
		</div>


		<jsp:include page="footer.jsp">
			<jsp:param name="action" value="lists"/>
		</jsp:include>
	</div>
</body>
</html>
