<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<c:choose>
    <c:when test="${empty active && empty expired  && not empty requestScope.activeSubscriptions}">
        <c:set var="active" scope="page" value="${true}"/>
    </c:when>
    <c:when test="${empty active && empty expired && not empty requestScope.expiredSubscriptions}">
        <c:set var="expired" scope="page" value="${true}"/>
    </c:when>
</c:choose>

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
                <fmt:message key="subscriptions"/>
            </strong>
        </h1>
    </div>
    <div class="progress mb-5">
        <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
    </div>
    <c:choose>
        <c:when test="${empty requestScope.activeSubscriptions && empty requestScope.expiredSubscriptions }">
            <div class="d-flex justify-content-center align-items-center mb-5">
                <h1 class="display-5 text-info">
                    <span class="badge badge-info"><fmt:message key="subscriptions.empty"/></span>
                </h1>
            </div>
        </c:when>
        <c:otherwise>
            <ul class="nav nav-pills mb-5" id="pills-tab" role="tablist">
                <li class="nav-item w-50 text-center bg-light">
                    <a class="nav-link
                                <c:if test="${not empty active}">
                                    active
                                </c:if>
                                <c:if test="${empty requestScope.activeSubscriptions}">
                                    disabled
                                </c:if>"
                       id="pills-home-tab"
                       data-toggle="pill" href="#pills-home" role="tab"
                       aria-controls="pills-home" aria-selected="true">
                        <fmt:message key="subscriptions.active"/>
                    </a>
                </li>
                <li class="nav-item w-50 text-center">
                    <a class="nav-link
                              <c:if test="${not empty expired}">
                                    active
                                </c:if>
                                <c:if test="${empty requestScope.expiredSubscriptions}">
                                    disabled
                                </c:if>"
                       id="pills-profile-tab"
                       data-toggle="pill" href="#pills-profile" role="tab"
                       aria-controls="pills-profile" aria-selected="false">
                        <fmt:message key="subscriptions.expired"/>
                    </a>
                </li>
            </ul>
            <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade show <c:if test="${not empty active}">active</c:if>" id="pills-home"
                     role="tabpanel" aria-labelledby="pills-home-tab">
                    <div class=" table-responsive">
                        <table class="table table-striped table-hover text-center align-middle">
                            <thead>
                            <tr class="table-info">
                                <th scope="col" class="align-middle">№</th>
                                <th scope="col" class="align-middle"><fmt:message key="bin.name"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="periodical.availability"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="bin.category"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="bin.frequency"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.start.date"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.end.date"/></th>
                                <th scope="col" class="align-middle"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="subscription" items="${requestScope.activeSubscriptions}"
                                       varStatus="counter">
                                <tr>
                                    <th scope="row" class="align-middle">${counter.count}</th>
                                    <td class="align-middle overflow-text">
                                        <c:out value="${subscription.periodical.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:if test="${subscription.periodical.availability eq true}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.available"/>
                                </span>
                                        </c:if>
                                        <c:if test="${subscription.periodical.availability eq false}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.unavailable"/>
                                </span>
                                        </c:if>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.category.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.frequency.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.startDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.endDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <a href="<c:url value="/app/periodical?periodicalId=${subscription.periodical.id}&isSubscribed=true"/>">
                                            <i class="fas fa-eye fa-lg" aria-hidden="true">&nbsp;</i>
                                            <fmt:message key="subscription.see"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${numberOfActivePages gt 1}">
                            <nav aria-label="Page navigation" class="mb-5">
                                <ul class="pagination pagination-lg justify-content-center">
                                    <li class="page-item <c:if test="${activePage eq 1}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=active&activePage=${activePage - 1}&expiredPage=${expiredPage}"/>"
                                           aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${numberOfActivePages}" varStatus="counter">
                                        <li class="page-item <c:if test="${activePage eq counter.count}">disabled</c:if>">
                                            <a class="page-link"
                                               href="<c:url value="?pill=active&activePage=${counter.count}&expiredPage=${expiredPage}"/>">
                                                    ${counter.count}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item <c:if test="${activePage eq numberOfActivePages}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=active&activePage=${activePage + 1}&expiredPage=${expiredPage}"/>"
                                           aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>

                    </div>
                </div>
                <div class="tab-pane fade show <c:if test="${not empty expired}">active</c:if>" id="pills-profile"
                     role="tabpanel" aria-labelledby="pills-profile-tab">
                    <div class=" table-responsive">
                        <table class="table table-striped table-hover text-center align-middle">
                            <thead>
                            <tr class="table-info">
                                <th scope="col" class="align-middle">№</th>
                                <th scope="col" class="align-middle"><fmt:message key="bin.name"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="periodical.availability"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="bin.category"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="bin.frequency"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.start.date"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.end.date"/></th>
                                <th scope="col" class="align-middle"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="subscription" items="${requestScope.expiredSubscriptions}"
                                       varStatus="counter">
                                <tr>
                                    <th scope="row" class="align-middle">${counter.count}</th>
                                    <td class="align-middle overflow-text">
                                        <c:out value="${subscription.periodical.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:if test="${subscription.periodical.availability eq true}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.available"/>
                                </span>
                                        </c:if>
                                        <c:if test="${subscription.periodical.availability eq false}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.unavailable"/>
                                </span>
                                        </c:if>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.category.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.frequency.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.startDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.endDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <a href="<c:url value="/app/periodical?periodicalId=${subscription.periodical.id}"/>">
                                            <i class="fas fa-eye fa-lg" aria-hidden="true">&nbsp;</i>
                                            <fmt:message key="subscription.see"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${numberOfExpiredPages gt 1}">
                            <nav aria-label="Page navigation" class="mb-5">
                                <ul class="pagination pagination-lg justify-content-center">
                                    <li class="page-item <c:if test="${expiredPage eq 1}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=expired&activePage=${activePage}&expiredPage=${expiredPage - 1}"/>"
                                           aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${numberOfExpiredPages}" varStatus="counter">
                                        <li class="page-item <c:if test="${expiredPage eq counter.count}">disabled</c:if>">
                                            <a class="page-link"
                                               href="<c:url value="?pill=expired&activePage=${activePage}&expiredPage=${counter.count}"/>">
                                                    ${counter.count}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item <c:if test="${expiredPage eq numberOfExpiredPages}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=expired&activePage=${activePage}&expiredPage=${expiredPage + 1}"/>"
                                           aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>

                    </div>
                </div>
            </div>

        </c:otherwise>
    </c:choose>

</main>
<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>