<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/error.css"/>">
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main role="main" class="container h-100">
    <div class="row h-100 justify-content-md-center align-items-center">
        <div class=" text-center">
            <h1 class="status-error"><fmt:message key="error.404.status"/></h1>
            <p class="text-muted-error"><fmt:message key="error.404.message"/></p>
            <a class="btn btn-lg btn-primary" href="<c:url value="/app/"/>" role="button">
                <fmt:message key="error.backbtn"/>
            </a>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>
