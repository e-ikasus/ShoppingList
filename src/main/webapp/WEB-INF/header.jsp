<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setBundle basename="translated_texts" var="r"/>

<div id="header">

	<div class="header">
		<h1>
			<fmt:message key="TITLE" bundle="${r}"/>
		</h1>
	</div>

	<div class="header">
		<h2>
			<c:choose>
				<c:when test="${!empty param.title}">
					<fmt:message key="${param.title}" bundle="${r}"/>
				</c:when>
				<c:otherwise>
					${param.title2}
				</c:otherwise>
			</c:choose>
		</h2>
	</div>

</div>
