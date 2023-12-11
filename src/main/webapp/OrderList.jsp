<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/Main.css">
<style type="text/css">
	div.content{
		/* background-color: white; */
	    padding: 20px;
	    border: 3px solid black;
	    border-radius: 15px;
	    width: 800px;
	    margin: 0 auto;
	}
	
	div.content table{
		width: 100%;
		border-collapse: collapse;
	}
	div.content table td, div.content table th{
		border: 1px solid black;
		padding: 10px;
		text-align: center;
	}
/*	div.contents > div > table > tbody > tr > td:nth-child(n+2){
		text-align: center;
	}
	*/
	tbody tr:hover{
		cursor: pointer;
		background-color: skyblue;
	}
</style>

</head>
<body>
<div class="wrap">
	<div class="header">
		<h1>OrderList.jsp</h1>
	</div>
	
	<%@ include file="Menu.jsp" %>
	
	<div class="contents">
		<h1>컨텐츠 영역</h1>
		<h3>주문 내역 클릭 >> 주문 취소</h3>
		<div class="content">
			<table>
				<thead>
					<tr>
						<th>번호</th>
						<th>이름</th>
						<th>가격</th>
						<th>수량</th>
						<th>금액</th>
						<th>주문일</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${odList }" var="od" varStatus="status">
					<tr onclick="cancelOrder('${od.odcode }','${od.prcode }','${od.odcount }')">
						<td>${status.index+1 }</td>
						<td>${od.prname }</td>
						<td>${od.prprice }</td>
						<td>${od.odcount }</td>
						<td>${od.orderprice }</td>
						<td>${od.oddate }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
	</div>
</div>
<script type="text/javascript">
	function cancelOrder(odcode, prcode, odcount){
		let cancelConfirm = confirm('주문을 취소 하시겠습니까?');
		
		if(cancelConfirm){
			location.href="${pageContext.request.contextPath}/cancelOrder?odcode="+odcode
				     +"&prcode="+prcode+"&odcount="+odcount;
		}
	}

</script>

</body>
</html>