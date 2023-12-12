<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="CSS/Main.css">
<style type="text/css">
	div.formWrap{
		width: 500px;
		border: 3px solid black;
		border-radius: 15px;	
		padding: 15px;
		margin: 0 auto;
	}
	div.formWrap .formInput{
		border: 1px solid black;
	    border-radius: 6px;
	    padding: 5px;
	    display: flex;
	    align-items:center;
	    margin-bottom: 5px;
	    margin-top: 5px;
	}
	
	div.formWrap .formInput > input[type='text'], input[type='date']{
	    width: 100%;
	    padding: 5px;
	    font-size: 18px;
	    outline: none;
	    border: none;
	    font-family: auto;
	}
	div.formWrap .formInput > input[type='submit']{
		width: 100%;
		padding:5px;
		font-size: 20px;
		font-weight: bold;
		cursor: pointer;
	}
	div.formWrap .formInput > label{
		width: 120px;
	}
	
	.totalInfo{
		justify-content: center;
		text-align: center;
	    display: flex;
	    align-items:center;
	}
	.totalInfo > div{
		width: 100%;
		border:1px solid black;
		border-radius: 10px;
		padding: 10px;	
	}
	.totalInfo > div:nth-child(2) {
		margin-right:10px;
		margin-left:10px;
	}
	.totalInfo p{
		font-size: 20px;
	
	}
</style>

</head>
<body>
<div class="wrap">
	<div class="header">
		<h1>MyInfo.jsp</h1>
	</div>
	
	<%@ include file="Menu.jsp" %>
	
	<div class="contents">
		<h1>컨텐츠 영역</h1>
		
			<div class="formWrap">
				<div class="formInput">
					<label for="mid">아이디</label> <input type="text" readonly value="${myInfo.mid }">
				</div>
				<div class="formInput">
					<label for="mpw">비밀번호</label> <input type="text" readonly value="${myInfo.mpw }">
				</div>
				
				<div class="formInput">
					<label for="mname">이름</label> <input type="text" readonly value="${myInfo.mname }">
				</div>
				<div class="formInput">
					<label for="mbirth">생년월일</label> <input type="text" readonly value="${myInfo.mbirth }" >
				</div>	
				<div class="formInput">
					<label for="mdate">가입일</label> <input type="text" readonly value="${myInfo.mdate }" >
				</div>	
				
				
				
				<div class="totalInfo">
					<div>
						<h3>회원 등급</h3>
						<p>
						<c:choose>
							<c:when test="${myInfo.totalprice > 5000000}">VVIP</c:when>
							<c:when test="${myInfo.totalprice > 1000000}">VIP</c:when>
							<c:otherwise>일반</c:otherwise>
						</c:choose>
						</p>
					</div>
					<div>
						<h3>총 주문수</h3>
						<p>${myInfo.totalorders }회</p>
					</div>
					<div>
						<h3>총 결제금액</h3>
						<p>${myInfo.totalprice }원</p>
					</div>
				</div>								
			</div>

	</div>
</div>




</body>
</html>