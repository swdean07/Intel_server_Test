<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 24. 11. 21.
  Time: 오전 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--JSTL 사용하기 준비 단계--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>food의 foodList 목록화면. </h1>
<%--  로그인 한 유저 표시--%>
<h2>${loginInfo.mname}님 환영합니다.</h2>
임시 로그인한 유저 정보: ${loginInfo}
임시 조회한 게시글 번호 정보 : ${cookie.viewTodos.value}


<form action="/logout" method="post">
  <button type="submit">로그아웃테스트</button>
</form>
<a href="/food/register2">글쓰기 폼이동</a>
<h2>foodRead 하나 조회 더미 </h2>
<a href="/food/read2?tno=5">하나 조회</a>



<h2>JSTL 연습장</h2>
<h3>반복문, forEach 이용, var=변수명, items="데이터 목록" , 더 많이 사용함</h3>
<ul>
  <c:forEach var="dto" items="${list}">
    <li>
      <span>${dto.fno}</span>
      <span><a href="/todo/read2?tno=${dto.fno}">${dto.title}</a></span>
      <span>${dto.dueDate}</span>
      <span>${dto.finished? "완료": "미완료"}</span>
    </li>
  </c:forEach>
</ul>


</body>
</html>
