<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
    <a href="/">인덱스 페이지로 가기</a><br>
    <p>이 페이지는 Member 등록하는 페이지입니다.</p>
    <p>회원 정보 입력란----------------------------</p>
    <form action="/signup" method="post">
        Name   : <input type="text" name="name"><br>
        passwd : <input type="password" name="pw"><br>
        email  : <input type="email" name="email"><br>
        phone  : <input type="tel" name="phoneNum"><br>
        <input type="radio" name="chk_role" value="user"> 일반 회원(user) <br>
        <input type="radio" name="chk_role" value="admin"> 관리자(admin) <br>

        <input type="submit" value="가입">
    </form>
</body>
</html>