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
	div.formWrap{
		width: 500px;
		border: 3px solid black;
		border-radius: 15px;	
		/* background-color: white; */
		margin-left: auto;
		margin-right:auto;
		padding: 15px;
	}
	div.formWrap .formInput{
		border: 1px solid black;
	    padding: 5px;
	    border-radius: 6px;
	    display: flex;
	    margin-bottom: 5px;
	    margin-top: 5px;
	}
	
	div.formWrap .formInput > input[type='text']{
	    width: 100%;
	    padding: 5px;
	    font-size: 18px;
	    outline: none;
	    border: none;
	}
	div.formWrap .formInput > input[type='submit']{
		width: 100%;
		padding:5px;
		font-size: 20px;
		font-weight: bold;
		cursor: pointer;
	}
	
</style>

</head>
<body>
<div class="wrap">
	<div class="header">
		<h1>LoginForm.jsp</h1>
	</div>
	
	<%@ include file="Menu.jsp" %>
	
	<div class="contents">
		<h1>컨텐츠 영역</h1>
		
		<div class="formWrap">
			<form action="${pageContext.request.contextPath }/memberLogin" method="post">
				<div class="formInput">
					<input type="text" name="mid" placeholder="아이디...">
				</div>
				<div class="formInput">
					<input type="text" name="mpw" placeholder="비밀번호...">
				</div>
				<div class="formInput">
					<input type="submit" value="로그인">
				</div>	
			</form>
		</div>
		
	</div>
</div>
</body>
</html>