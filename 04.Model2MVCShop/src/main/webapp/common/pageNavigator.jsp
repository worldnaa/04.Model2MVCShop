<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			◀ 이전
	</c:if>
	
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetList('${resultPage.currentPage-1}')">◀ 이전</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
			<a href="javascript:fncGetList('${ i }');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▶
	</c:if>
	
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetList('${resultPage.endUnitPage+1}')">이후 ▶</a>
	</c:if>	
	
	
	
	
	<%-- <c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			◀ 이전
	</c:if>
	
	
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<c:if test = ${param.menu == null}>
				<a href="javascript:fncGetUserList('${resultPage.currentPage-1}')">◀ 이전</a>
			</c:if>
			
			<c:if test = ${param.menu != null}>
				<a href="javascript:fncGetProductList('${resultPage.currentPage-1}')">◀ 이전</a>
			</c:if>
	</c:if>
	
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<c:choose>
			<c:when test = ${param.menu == null}>
				<a href="javascript:fncGetUserList('${ i }');">${ i }</a>
			</c:when>
			
			<c:when test = ${param.menu != null}>
				<c:if test = ${param.menu == "manage"}>
					<a href="javascript:fncGetProductList('${ i }');">${ i }</a>	
				</c:if>
				
				<c:if test = ${param.menu == "search"}>
					<a href="javascript:fncGetProductList('${ i }');">${ i }</a>
				</c:if>
			</c:when>
		</c:choose>	
	</c:forEach>
	
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▶
	</c:if>
	
	
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
		<c:if test = ${param.menu == null}>
			<a href="javascript:fncGetUserList('${resultPage.endUnitPage+1}')">이후 ▶</a>
		</c:if>
			
		<c:if test = ${param.menu != null}>
			<a href="javascript:fncGetProductList('${resultPage.endUnitPage+1}')">이후 ▶</a>
		</c:if>	
	</c:if> --%>
	
	
	
	