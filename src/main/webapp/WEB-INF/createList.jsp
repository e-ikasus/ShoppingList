<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="translated_texts" var="r"/>

<!DOCTYPE html>
<html>
<title>Gestion des courses</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
<body>

	<div id="main">

		<jsp:include page="header.jsp">
			<jsp:param name="title" value="NEW_LIST"/>
		</jsp:include>

		<%@include file="errorArea.jsp" %>

		<form name="mainForm" id="mainForm" method="post" action="${pageContext.request.contextPath}/ServletCreateList">

			<div id="divListName">
				<label for="listName"><fmt:message key="NAME" bundle="${r}"/> : </label>
				<input type="text" name="listName" id="listName" <c:if test="${!empty requestScope.listItems.nom}">value="${requestScope.listItems.nom}" readonly</c:if>/>
			</div>

			<div id="body">
				<c:choose>
					<c:when test="${!empty requestScope.listItems.articles}">
						<c:forEach var="article" items="${requestScope.listItems.articles}">
							<jsp:include page="row.jsp">
								<jsp:param name="name" value="${article.nom}"/>
								<jsp:param name="id" value="${article.id}"/>
								<jsp:param name="basket" value="0"/>
							</jsp:include>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<p><fmt:message key="EMPTY_LIST" bundle="${r}"/></p>
					</c:otherwise>
				</c:choose>
			</div>

			<div id="divItemName">
				<label for="itemName"><fmt:message key="ITEM" bundle="${r}"/> : </label>
				<input type="text" name="itemName" id="itemName"/>

				<button class="submit" type="submit" name="add">
					<img class="image" alt="add" src="${pageContext.request.contextPath}/images/add.png"/>
				</button>
			</div>
		</form>

		<jsp:include page="footer.jsp">
			<jsp:param name="action" value="addlist"/>
		</jsp:include>
	</div>
</body>
</html>
