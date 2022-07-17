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
			<jsp:param name="title2" value="${requestScope.listItems.nom}"/>
		</jsp:include>

		<%@include file="errorArea.jsp" %>

		<div id="body">
			<c:choose>
				<c:when test="${!empty requestScope.listItems.articles}">
					<form name="mainForm" id="mainForm" method="post" action="${pageContext.request.contextPath}/ServletDisplayBasket">
						<table id="shoppingList">
							<c:forEach var="article" items="${requestScope.listItems.articles}">
								<tr>
									<td>
										<button class="submit" type="submit" name="toggle" value="${article.id},${article.coche}">
											<img class="image" src="${pageContext.request.contextPath}/images/<c:choose><c:when test="${article.coche}">checked</c:when><c:otherwise>unchecked</c:otherwise></c:choose>.png">
										</button>
									<td>
											${article.nom}
									</td>
								</tr>
							</c:forEach>
						</table>
					</form>
				</c:when>
				<c:otherwise>
					<p><fmt:message key="EMPTY_LIST" bundle="${r}"/></p>
				</c:otherwise>
			</c:choose>
		</div>

		<jsp:include page="footer.jsp">
			<jsp:param name="action" value="basket"/>
		</jsp:include>
	</div>

</body>
</html>
