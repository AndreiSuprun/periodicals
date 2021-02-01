<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" uri="http://periodical.jstlImgSrc" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main role="main" class="container">
    <c:choose>
        <c:when test="${not empty requestScope.noQueryToFind}">
            <div class="d-flex justify-content-center align-items-center mb-5">
                <h1 class="display-5 text-info">
                    <span class="badge badge-info"><fmt:message key="search.empty.query"/></span>
                </h1>
            </div>
        </c:when>
        <c:when test="${not empty requestScope.nothingFound}">
            <div class="d-flex justify-content-center align-items-center mb-5">
                <h1 class="display-5 text-info">
                    <span class="badge badge-info"><fmt:message key="search.nothing.found"/></span>
                </h1>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="periodical" items="${requestScope.searchResult}">
                <div class="card border-dark bg-light mb-5" id="periodical-${periodical.id}">
                    <div class="row no-gutters ">
                        <div class="col-md-4 d-flex align-content-center flex-wrap">
                            <img src="<mytag:imgSrc pictureURL="${periodical.picture}"/>" class="card-img w-75 mx-auto"
                                 alt="logo">
                        </div>
                        <div class="col-md-8">
                            <div class="card-header card-title text-center">
                                <a href="<c:url value="/app/periodical?periodicalId=${periodical.id}"/>">
                                    <h3><c:out value="${periodical.name}"/></h3>
                                </a>
                            </div>
                            <div class="card-body bg-light accordion" id="accordion-${periodical.id}">
                                <div class="card-text text-center" id="heading-${periodical.id}">
                                    <button class="btn btn-link" type="button" data-toggle="collapse"
                                            data-target="#collapse-${periodical.id}"
                                            aria-expanded="false" aria-controls="collapse-${periodical.id}">
                                        <fmt:message key="periodical.description"/>&nbsp;
                                        <i class="fas fa-caret-square-o-down fa-lg" aria-hidden="true"></i>
                                    </button>
                                </div>
                                <div id="collapse-${periodical.id}" class="collapse"
                                     aria-labelledby="heading-${periodical.id}"
                                     data-parent="#accordion-${periodical.id}">
                                    <p class="card-text"><c:out value="${periodical.description}"/></p>
                                </div>
                            </div>

                            <ul class="bg-light list-group list-group-flush">
                                <li class="list-group-item bg-light">
                                    <fmt:message key="periodical.category"/>: <c:out
                                        value="${periodical.category.name}"/>
                                </li>
                                <li class="list-group-item bg-light">
                                    <fmt:message key="periodical.frequency"/>:
                                    <c:out value="${periodical.frequency.name}"/> - <c:out
                                        value="${periodical.frequency.description}"/>
                                </li>
                                <li class="list-group-item bg-light">
                                    <fmt:message key="periodical.publisher"/>: <c:out
                                        value="${periodical.publisher.name}"/>
                                </li>
                                <li class="list-group-item bg-light">
                                    <fmt:message key="periodical.price"/>: <c:out value="${periodical.price} BYN"/></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${numberOfPages gt 1}">
                <nav aria-label="Page navigation" class="mb-5">
                    <ul class="pagination pagination-lg justify-content-center">
                        <li class="page-item <c:if test="${page eq 1}">disabled</c:if>">
                            <a class="page-link"
                               href="<c:url value="?text=${requestScope.searchQuery}&page=${page - 1}"/>"
                               aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach begin="1" end="${numberOfPages}" varStatus="counter">
                            <li class="page-item <c:if test="${page eq counter.count}">disabled</c:if>">
                                <a class="page-link"
                                   href="<c:url value="?text=${requestScope.searchQuery}&page=${counter.count}"/>">
                                        ${counter.count}
                                </a>
                            </li>
                        </c:forEach>
                        <li class="page-item <c:if test="${page eq numberOfPages}">disabled</c:if>">
                            <a class="page-link"
                               href="<c:url value="?text=${requestScope.searchQuery}&page=${page + 1}"/>"
                               aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>

</body>
</html>
