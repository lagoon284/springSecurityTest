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
    <title>Member 공개</title>
</head>
<body>
    <a href="/">인덱스 페이지로 가기</a><br>
    <p>로그인 성공!</p>
    <p>이 페이지는 회원가입이 되어있는 Member들이 접근가능한 페이지입니다. (DB에 회원정보가 있는 USER.)</p>
    <p>Security User Info --------------------------------</p>
    <p>id: ${requestScope.member.id }</p>
    <p>email: ${requestScope.member.email }</p>
    <p>name: ${requestScope.member.name }</p>
    <p>password: ${requestScope.member.passwd }</p>
    <p>phone_num: ${requestScope.member.phoneNum }</p>
    <p>token: ${requestScope.member.refreshToken eq null ? 'Null' : requestScope.member.refreshToken }</p>
    <p>role_info: ${requestScope.member.role }</p>
    <p>-----------------------------------------------------</p>
</body>
</html>
