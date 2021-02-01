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
    <div class="text-center">

        <div class="jumbotron jumbotron-fluid" p-1>
            <div class="container" style="height: 26rem;" >
                <img src="<c:url value="/resources/images/periodicals-logo.png"/>" style="width: 18rem;" alt="Logo"/>
                <h1 class="display-5 text-break"><fmt:message key="title"/></h1>
                <hr class="my-4">
                <p class="h5 text-justify-center"><fmt:message key="site.description"/></p>
                <hr class="my-4">
                <p class="lead">
                    <a class="btn btn-outline-primary border-dark text-dark btn-lg" href="<c:url value="/app/catalog"/>" role="button"><fmt:message key="catalog"/></a>
                </p>
            </div>
        </div>
        <div id="multiCarousel" class="carousel slide w-100 pb-3" data-ride="carousel">
            <div class="carousel-inner w-100" role="listbox">
                <c:forEach var="periodical" items="${requestScope.catalog}">
                    <div class="carousel-item <c:if test="${periodical.id eq 1}">active</c:if>">
                        <div class="card m-1 p-2 bg-light border-primary" style="width: 18rem;">
                            <img src="<mytag:imgSrc pictureURL="${periodical.picture}"/>" style="max-height: 20rem; width: auto;" class="card-img-top img-fluid"
                                 alt="logo">
                            <div class="card-body">
                                <a href="<c:url value="/app/periodical?periodicalId=${periodical.id}"/>">
                                    <h4><c:out value="${periodical.name}"/></h4>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <a class="carousel-control-prev" href="#multiCarousel" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#multiCarousel" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>

<script type="text/javascript" defer>
    (function () {
        $('.carousel .carousel-item').each(function () {
            let itemToClone = $(this);
            for (let i = 1; i < 4; i++) {
                itemToClone = itemToClone.next();
                if (!itemToClone.length) {
                    itemToClone = $(this).siblings(':first');
                }
                itemToClone.children(':first-child').clone()
                    .appendTo($(this));
            }
        });
    }());
</script>
</body>
</html>