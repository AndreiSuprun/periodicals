<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main role="main" class="container h-100">
    <div class="row h-100 justify-content-md-center align-items-center">
        <div class="card border-dark w-100">
            <div class="row no-gutters ">
                <div class="col-md-8 bg-light">
                    <div class="card-header card-title text-center">
                        <h3>
                            <c:out value="${sessionScope.user.firstName}"/>
                            <c:out value="${sessionScope.user.lastName}"/>
                        </h3>
                    </div>
                    <ul class="bg-light list-group list-group-flush">
                        <li class="list-group-item bg-light">
                            <dl class="row">
                                <dt class="col-sm-5"><fmt:message key="role"/>:</dt>
                                <dd class="col-sm-7">
                                     <span class="badge badge-success">
                                        <c:out value="${sessionScope.user.role.name}"/>
                                    </span>
                                </dd>
                            </dl>
                        </li>
                        <li class="list-group-item bg-light">
                            <dl class="row">
                                <dt class="col-sm-5"><fmt:message key="email"/>:</dt>
                                <dd class="col-sm-7">
                                    <c:out value="${sessionScope.user.email}"/>
                                </dd>
                            </dl>
                        </li>
                    </ul>
                </div>
                <div class="col-md-4 text-white bg-light">
                    <img src="<c:url value="/resources/images/user-logo.png"/>" class="card-img" alt="user-logo">
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/fragments/pagination.jsp"/>
</main>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>

</body>
</html>
