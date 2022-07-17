<c:if test="${!empty requestScope.message || !empty requestScope.errorsList}">
	<div id="errorsArea">
		<c:if test="${!empty requestScope.message}">
			<p class="message">${requestScope.message}</p>
		</c:if>

		<c:if test="${!empty requestScope.errorsList}">
			<c:forEach var="error" items="${requestScope.errorsList}">
				<p class="error">${error}</p>
			</c:forEach>
		</c:if>
	</div>
</c:if>