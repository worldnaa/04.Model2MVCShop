<%@ page contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

${ System.out.println("<<<<< addPurchase.jsp 시작 >>>>>") }

<html>
<head>
<title>구매상세조회</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${ purchase.purchaseProd.prodNo }</td>
		<%--<td><%=purchase.getPurchaseProd().getProdNo() %></td>--%>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${ purchase.buyer.userId }</td>
		<%--<td><%=purchase.getBuyer().getUserId() %></td>--%>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
			<c:if test="${ purchase.paymentOption.trim().equals('1') }">
				현금구매
			</c:if>
			<c:if test="${ purchase.paymentOption.trim().equals('2') }">
				신용구매
			</c:if>
				
			<%--<% if(purchase.getPaymentOption().trim().equals("1")) {
				현금구매
			<% }else { %>
				신용구매
			<% } %>--%>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${ purchase.receiverName }</td>
		<%--<td><%=purchase.getReceiverName() %></td>--%>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${ purchase.receiverPhone }</td>
		<%--<td><%=purchase.getReceiverPhone() %></td>--%>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${ purchase.divyAddr }</td>
		<%--<td><%=purchase.getDivyAddr() %></td>--%>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${ purchase.divyRequest }</td>
		<%--<td><%=purchase.getDivyRequest() %></td>--%>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${ purchase.divyDate }</td>
		<%--<td><%=purchase.getDivyDate() %></td>--%>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>
${ System.out.println("<<<<< addPurchase.jsp 종료 >>>>>") }