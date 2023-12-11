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

</style>

</head>
<body>
<div class="wrap">
	<div class="header">
		<h1>OrderPage.jsp</h1>
	</div>
	
	<%@ include file="Menu.jsp" %>
	
	<div class="contents">
		<h1>컨텐츠 영역</h1>
		<h3>상품 주문 처리 후 >> 주문 내역 페이지 이동</h3>
		<div class="content">
			<table>
				<thead>
					<tr>
						<th>이름</th>
						<th>가격</th>
						<th>재고</th>
						<th>종류</th>
						<th style="width: 120px">주문</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${prList }" var="pr">
					<tr>
						<td>${pr.prname }</td>
						<td>${pr.prprice }</td>
						<td>${pr.prstock }</td>
						<td>${pr.prtype }</td>
						<td> <input type="number" style="width: 30px;font-size: 18px;" value="0" min="1" max="${pr.prstock }"> <button onclick="order(this,'${pr.prcode}')">주문</button> </td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
	</div>
</div>

<script type="text/javascript">
	function order(selBtn, selPrcode){
		let selValue =selBtn.previousElementSibling;
		console.log(selValue.max);
		if( Number(selValue.value) <= 0 ){
			alert('수량을 입력해주세요!');
			selValue.focus();
			return;
		} else if( Number(selValue.value) > Number(selValue.max)){
			alert('주문 가능 수량을 초과하였습니다.');
			selValue.value = Number(selValue.max);
			selValue.focus();
			return;
		}
		
		location.href = "${pageContext.request.contextPath }/order?prcode="+selPrcode+"&odcount="+selValue.value;
		
	}

</script>


</body>
</html>