<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://code.jquery.com/jquery-3.6.0.js"/>
<html>
<head>
    <title>회원 정보 수정</title>
</head>
<body>
    <c:set var="memInfo" value="${requestScope.memberInfo}"/>
    <a href="/">인덱스 페이지로 가기</a><br>
    <p>이 페이지는 Member Info Update 페이지입니다.</p>
    <p>회원 정보 수정----------------------------</p>
    <form action="/memInfoUpdate" method="post">
        Name   : <input type="text" name="name" value="${memInfo.name}" readonly><br>
        passwd : <input type="password" name="passwd" value="${memInfo.passwd}"><br>
        email  : <input type="email" name="email" value="${memInfo.email}" readonly><br>
        phone  : <input type="tel" name="phoneNum" value="${memInfo.phoneNum}"><br>
        <input type="radio" name="role" value="ROLE_USER"> 일반 회원(user) <br>
        <input type="radio" name="role" value="ROLE_ADMIN"> 관리자(admin) <br>

        <input type="submit" value="수정">
    </form>
</body>
<script>
    $(function () {
        if (${memInfo.role eq 'ROLE_USER'}) {
            $('[name="role"]:first').attr('checked', 'checked');
        } else {
            $('[name="role"]:eq(1)').attr('checked', 'checked');
        }
    })
</script>
</html>