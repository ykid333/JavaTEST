<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
	<div class="navigation">
		<ul>
		<li> <a href="${pageContext.request.contextPath }/home">홈으로</a> </li>
		<c:choose>
			<c:when test="${sessionScope.loginId == null }">
			<li> <a href="${pageContext.request.contextPath }/memberJoinForm">회원가입</a> </li>
			<li> <a href="${pageContext.request.contextPath }/memberLoginForm">로그인</a> </li>
			</c:when>
			<c:otherwise>
			<li> <a href="${pageContext.request.contextPath }/myInfo">내정보(${sessionScope.loginId })</a> </li>
			<li> <a href="${pageContext.request.contextPath }/orderPage">상품주문</a> </li>
			<li> <a href="${pageContext.request.contextPath }/orderList">주문내역</a> </li>
			<li> <a href="${pageContext.request.contextPath }/memberLogout">로그아웃</a> </li>
			</c:otherwise>
		</c:choose>
		</ul>
	</div>