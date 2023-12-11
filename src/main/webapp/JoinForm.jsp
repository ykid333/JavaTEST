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
	#idCheckMsg, #pwCheckMsg{
		margin: 0;
		padding-left: 10px;
	}
	.d-none{
		display: none;
	}
	
</style>

</head>
<body>
<div class="wrap">
	<div class="header">
		<h1>joinForm.jsp</h1>
	</div>
	
	<%@ include file="Menu.jsp" %>
	
	<div class="contents">
		<h1>컨텐츠 영역</h1>
		
		<div class="formWrap">
			<form action="${pageContext.request.contextPath }/memberJoin" 
			      method="post"  onsubmit="return joinFormCheck(this)">
				<div class="formInput">
					<label for="mid">아이디</label> <input type="text" id="mid" name="mid" onblur="idCheck(this)">
				</div>
				<p id="idCheckMsg" class="d-none"></p>
				
				<div class="formInput">
					<label for="mpw">비밀번호</label> <input type="text" id="mpw" name="mpw" >
				</div>
				
				<div class="formInput">
					<label for="mname">이름</label> <input type="text" id="mname" name="mname">
				</div>
				<div class="formInput">
					<label for="mbirth">생년월일</label> <input type="date" id="mbirth" name="mbirth" >
				</div>				
				<div class="formInput">
					<input type="submit" value="회원가입">
				</div>	
			</form>
		</div>
		
	</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
	let idConfirm = false;
	const checkIdMsgObj = document.querySelector("#idCheckMsg");
	
	function idCheck(idObj){
		let inputId = idObj.value;
		idConfirm = false;
		if( inputId.length <=0 ){
			checkIdMsgObj.innerText = '아이디를 입력 해주세요!';
			checkIdMsgObj.style.color = 'red';
			return;
		}
		$.ajax({
			url : 'idCheck',
			type : 'get',
			data : { 'inputId' : inputId },
			success : function(result){
				
				checkIdMsgObj.classList.remove('d-none');
				if(result == 'Y'){
					checkIdMsgObj.innerText = '사용가능한 아이디 입니다.';
					checkIdMsgObj.style.color = 'green';
					idConfirm = true;
				} else {
					checkIdMsgObj.innerText = '이미 사용중인 아이디 입니다.';
					checkIdMsgObj.style.color = 'red';
					idConfirm = false;
				}
				
			}
			
		})
	}

	function joinFormCheck(formObj){
		let idEl =  formObj.mid;
		if( idEl.value.length == 0 ){
			alert('아이디를 입력 해주세요!');
			checkIdMsgObj.innerText = '아이디를 입력 해주세요!';
			checkIdMsgObj.style.color = 'red';
			idEl.focus();
			return false;
		}
		if(!idConfirm){
			alert('아이디를 다시 확인 해주세요!');
			idEl.focus();
			return false;
		}
		
	}
	
	
</script>




</body>
</html>