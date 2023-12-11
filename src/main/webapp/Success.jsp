<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<c:if test="${param.msg != null }">
	<script type="text/javascript">
		alert('${param.msg}')
		location.href = '${pageContext.request.contextPath }${param.afterUrl}';
	</script>
	</c:if>
</head>
<body>

</body>
</html>