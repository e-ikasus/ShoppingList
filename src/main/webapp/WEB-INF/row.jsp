<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
	<div class="name">
		${param.name}
	</div>
	<c:if test="${param.basket.equals('1')}">
		<div class="basket">
			<button class="submit" formmethod="get" formaction="${pageContext.request.contextPath}/ServletDisplayBasket" type="submit" name="show" value="${param.id}">
				<img class="image" alt="caddy" src="${pageContext.request.contextPath}/images/caddy.png"/>
			</button>
		</div>
	</c:if>
	<div class="trash">
		<button class="submit" type="submit" name="del" value="${param.id}">
			<img class="image" alt="delete" src="${pageContext.request.contextPath}/images/delete.png"/>
		</button>
	</div>
</div>
