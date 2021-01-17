<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>




<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> 전화번호 수정화면</h1>

<p>
	수정화면
	아래 항목을 수정하고 "수정"버튼을 클릭하세요.
</p>

<form action="/phonebook3/pbc" method="get">
이름(name): <input type="text" name="name" value="${pvo.name}"> <br>
핸드폰(hp): <input type="text" name="hp" value="${pvo.hp}"> <br>
회사(company): <input type="text" name="company" value="${pvo.company}"> <br>
<input type="hidden" name="id" value="${pvo.personId}"><br>
<input type="hidden" name="action" value="update">
<button type="submit">수정</button>

</form>

</body>
</html>