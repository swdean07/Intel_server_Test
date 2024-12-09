<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!--        <h1>Header</h1>-->
        <!--        네비게이션바 추가 시작-->
        <div class="row">
            <div class="col">
                <nav class="navbar navbar-expand-lg bg-body-tertiary">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">Navbar</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Features</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Pricing</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>

            </div>
        </div>
        <!--        네비게이션바 추가 끝-->

        <!--        class="row content"-->
        <div class="row content">
            <!--        col-->
            <div class="col">
                <!--        카드 시작 부분-->
                <div class="card">
                    <div class="card-header">
                        Featured
                    </div>
                    <div class="card-body">
                        <%--                        Food List 부분 작성--%>
                        <h5 class="card-title">리스트 목록</h5>
                        <button type="button" class="btn btn-primary insertTodoBtn">글쓰기</button>
                        <table class="table">
                            <thead>
                            <%--                                소제목--%>
                            <tr>
                                <th scope="col">Fno</th>
                                <th scope="col">Title</th>
                                <th scope="col">Writer</th>
                                <th scope="col">DueDate</th>
                                <th scope="col">Finished</th>
                            </tr>
                            </thead>
                            <%--                                본문--%>
                            <tbody>

                            <c:forEach items="${pageResponseDTO.dtoList}" var="dto">
                                <tr>
                                    <th scope="row"><c:out value="${dto.fno}"></c:out></th>
                                    <td><a href="/food/read?fno=${dto.fno}&${pageRequestDTO.link}" class="text-decoration-none">
                                        <c:out value="${dto.title}"></c:out>
                                    </a></td>
                                    <td><c:out value="${dto.writer}"></c:out></td>
                                    <td><c:out value="${dto.dueDate}"></c:out></td>
                                    <td><c:out value="${dto.finished}"></c:out></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                        <%--                       Food List 부분 작성--%>
                        <div class="float-end">
                            <ul class="pagination">
                                <c:if test="${pageResponseDTO.prev}">
                                    <li class="page-item">
                                        <a class="page-link" data-num = "${pageResponseDTO.page - 1}">Previous</a>
                                    </li>
                                </c:if>
                                <c:forEach begin="${pageResponseDTO.start}"
                                           end="${pageResponseDTO.end}" var="num">
                                    <li class="page-item ${pageResponseDTO.page == num ? "active" : ""}"
                                    ><a class="page-link" data-num = "${num}" href="#">${num}</a></li>
                                </c:forEach>

                                <%--    다음 버튼 부분--%>
                                <c:if test="${pageResponseDTO.next}">
                                    <li class="page-item">
                                        <a class="page-link" data-num = "${pageResponseDTO.end + 1}">Next</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>

                    </div>
                </div>
                <!--        카드 끝 부분-->
            </div>
            <!--        col-->
        </div>
        <!--        class="row content"-->
    </div>
    <%--    <div class="row content">--%>
    <%--        <h1>Content</h1>--%>
    <%--    </div>--%>
    <div class="row footer">
        <!--        <h1>Footer</h1>-->
        <div class="row fixed-bottom" style="z-index: -100">
            <footer class="py-1 my-1">
                <p class="text-center text-muted">Footer</p>
            </footer>
        </div>
    </div>
</div>
<%--입력 폼에 관련 유효성 체크, 서버로부터  erros 키로 값을 받아오면, --%>
<%--자바스크립 콘솔에 임시 출력.--%>
<script>
    const serverValidResult = {};
    // jstl , 반복문으로, 서버로부터 넘어온 여러 에러 종류가 많습니다.
    //     하나씩 꺼내서, 출력하는 용도.,
    <c:forEach items="${errors}" var="error">
    serverValidResult['${error.getField()}'] = '${error.defaultMessage}'
    </c:forEach>
    console.log(serverValidResult)
</script>

<script>
    document.querySelector(".insertFoodBtn").addEventListener("click",
        function (e) {
// 글쓰기 폼으로 가야함.
            self.location = "/food/register"
                , false
        })

    // 페이지네이션 , 이동할 페이지 번호를 data-num 이름으로 작업.
    // 해당 번호를 가지고, 링크 이동하는 이벤트 추가.
    // <ul class="pagination"> ,기준으로 하위 a 태그에 접근 하기.
    document.querySelector(".pagination").addEventListener("click",
        function (e) {
            e.preventDefault()
            e.stopPropagation()
            // a 태그에 접근 할려면, 해당 요소 선택자 필요.
            const target = e.target
            //  a 태그 인경우에 이벤트 리스너 동작을하고,
            // a 태그 가 아니면, 이벤트 처리 안함.
            if(target.tagName !== "A") {
                return
            }
            const num = target.getAttribute("data-num")

            // 백틱, 숫자 키보드 1번 왼쪽에 보면.
            self.location = `/food/list?page=\${num}`
        },false)


</script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>