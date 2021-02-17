<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.sql.Timestamp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main role="main" class="container">
    <div class="d-flex justify-content-center align-items-center">
        <h1 class="display-5">
            <strong>
                <fmt:message key="payments"/>
            </strong>
        </h1>
    </div>
    <div class="progress mb-5">
        <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
    </div>
    <c:choose>
        <c:when test="${empty requestScope.payments}">
            <div class="d-flex justify-content-center align-items-center mb-5">
                <h1 class="display-5 text-info">
                    <span class="badge badge-info"><fmt:message key="payments.empty"/></span>
                </h1>
            </div>
        </c:when>
        <c:otherwise>
            <div class=" table-responsive">
                <table class="table table-striped table-hover text-center align-middle">
                    <thead>
                    <tr class="table-info">
                        <th scope="col" class="align-middle">№</th>
                        <th scope="col" class="align-middle"><fmt:message key="payments.user"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="payments.date"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="payments.price"/></th>
                        <th scope="col" class="align-middle"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="payment" items="${requestScope.payments}" varStatus="counter">
                        <tr>
                            <th scope="row" class="align-middle">${counter.count}</th>
                            <td class="align-middle overflow-text">
                                <a href="<c:url value="/app/admin/user?userId=${payment.user.id}"/>">
                                    <i class="fas fa-user fa-lg" aria-hidden="true">&nbsp;</i>
                                    <c:out value="${payment.user.firstName}"/> <c:out value="${payment.user.lastName}"/>
                                </a>
                            </td>
                            <td class="align-middle">
                                <fmt:formatDate value="${Timestamp.valueOf(payment.paymentDate)}" type="date"
                                                pattern="yyyy-MM-dd/HH:mm"/>
                            </td>
                            <td class="align-middle">
                                <c:out value="${payment.totalPrice}"/> BYN
                            </td>
                            <td class="align-middle">
                                <a href="<c:url value="/app/admin/payment?paymentId=${payment.id}"/>">
                                    <i class="fas fa-eye fa-lg" aria-hidden="true">&nbsp;</i>
                                    <fmt:message key="payment.see"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <jsp:include page="/WEB-INF/views/fragments/pagination.jsp"/>
        </c:otherwise>
    </c:choose>

</main>
<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>