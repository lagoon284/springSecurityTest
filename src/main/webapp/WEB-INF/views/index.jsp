<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-08-06
  Time: 오후 5:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모두 공개</title>
</head>

<c:set value="${requestScope.loginName eq null ? '' : requestScope.loginName}" var="name"/>

<body>
    <p>Mapping 성공!!</p>
    <p>로그인 중인 아이디 : ${name eq '' ? "Not login" : requestScope.loginName }</p>
    <p>이 페이지는 모든 USER가 접근 가능한 페이지입니다.</p>
    <br>
    <a href="/main">main으로 가기</a><br>
    <c:if test="${name eq '' }">
        <a href="/signupPage">회원 등록 하러가기</a>
    </c:if>
</body>
</html>
