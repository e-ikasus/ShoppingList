<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="footer">

	<c:choose>

		<c:when test="${param.action == 'lists'}">
			<div>
				<a href="${pageContext.request.contextPath}/ServletCreateList"><img class="image" alt="add" src="${pageContext.request.contextPath}/images/add.png"/></a>
			</div>
		</c:when>

		<c:when test="${param.action == 'addlist'}">
			<div id="toRight">
				<a href="${pageContext.request.contextPath}/ServletDisplayLists"><img class="image" alt="previous" src="${pageContext.request.contextPath}/images/rightPrevious.png"/></a>
			</div>
		</c:when>

		<c:when test="${param.action == 'basket'}">
			<div>
				<a href="${pageContext.request.contextPath}/ServletDisplayLists"><img class="image" alt="previous" src="${pageContext.request.contextPath}/images/leftPrevious.png"/></a>
			</div>

			<div id="rubber">
				<button form="mainForm" class="submit" type="submit" name="clear"><img class="image" alt="rubber" src="${pageContext.request.contextPath}/images/rubber.png"/></button>
			</div>
		</c:when>

	</c:choose>
</div>
