<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <div class="card border-dark mb-5 bg-light">
        <div class="row no-gutters ">

            <div class="col-md-8">
                <div class="card-header card-title text-center text-dark">
                    <h3><c:out value="${periodical.name}"/></h3>
                </div>
                <div class="card-body">
                    <div class="card-text text-center">
                        <strong><fmt:message key="periodical.description"/></strong>
                    </div>
                    <p class="card-text"><c:out value="${periodical.description}"/></p>
                </div>

                <ul class="bg-light list-group list-group-flush">
                    <li class="list-group-item">
                        <dl class="row" style="height: 0.5rem;">
                            <dt class="col-sm-4"><fmt:message key="periodical.availability"/>:</dt>
                            <dd class="col-sm-8">
                                <c:if test="${periodical.availability eq true}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.available"/>
                                </span>
                                </c:if>
                                <c:if test="${periodical.availability eq false}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.unavailable"/>
                                </span>
                                </c:if>
                            </dd>
                        </dl>
                    </li>
                    <li class="list-group-item">
                        <dl class="row" style="height: 0.5rem;">
                            <dt class="col-sm-4"><fmt:message key="periodical.category"/>:</dt>
                            <dd class="col-sm-8">
                                <c:out value="${periodical.category.name}"/>
                            </dd>
                        </dl>
                    </li>
                    <li class="list-group-item">
                        <dl class="row" style="height: 0.5rem;">
                            <dt class="col-sm-4"><fmt:message key="periodical.frequency"/>:</dt>
                            <dd class="col-sm-8">
                                <c:out value="${periodical.frequency.name}"/> - <c:out value="${periodical.frequency.description}"/>
                            </dd>
                        </dl>
                    </li>
                    <li class="list-group-item">
                        <dl class="row" style="height: 0.5rem;">
                            <dt class="col-sm-4"><fmt:message key="periodical.publisher"/>:</dt>
                            <dd class="col-sm-8">
                                <c:out value="${periodical.publisher.name}"/>
                            </dd>
                        </dl>
                    </li>
                    <li class="list-group-item">
                        <dl class="row" style="height: 2rem;">
                            <dt class="col-sm-4"><fmt:message key="periodical.price"/>:</dt>
                            <dd class="col-sm-8">
                                <c:out value="${periodical.price} BYN"/>
                            </dd>
                        </dl>
                    </li>
                </ul>
                <c:if test="${sessionScope.user.getRole().getName() ne 'admin' and periodical.availability ne false}">
                    <div class="card-footer d-flex justify-content-sm-center justify-content-lg-end" style="height: 4.5rem;">
                        <form accept-charset="UTF-8" role="form" method="post" action="<c:url value="/app/bin/add"/>">
                            <!-- Button trigger modal -->
                                <div class="input-group ">
                                    <input type="hidden" class="form-control" name="periodicalId" value="${periodical.id}">
                                    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#modal-${periodical.id}">
                                        <i class="fas fa-chevron-circle-right fa-lg" aria-hidden="true">&nbsp;</i>
                                        <fmt:message key="periodical.subscribe"/>
                                    </button>
                                </div>
                            <!-- Modal -->
                            <div class="modal fade" id="modal-${periodical.id}" tabindex="-1" role="dialog"
                                 aria-labelledby="modal-${periodical.id}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">

                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">
                                                <fmt:message key="periodical.choose.subscription.period"/>
                                            </h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <select class="custom-select" name="subscriptionPeriodId" required>
                                                    <c:forEach var="subscriptionPeriod"
                                                               items="${requestScope.subscriptionPeriods}">
                                                        <option value="${subscriptionPeriod.id}">
                                                            <c:out value="${subscriptionPeriod.name}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-danger" data-dismiss="modal">
                                                <i class="fas fa-chevron-circle-left fa-lg" aria-hidden="true">&nbsp;</i>
                                                <fmt:message key="periodical.back"/>
                                            </button>
                                            <button type="submit" class="btn btn-info">
                                                <i class="fas fa-shopping-cart fa-lg" aria-hidden="true">&nbsp;</i>
                                                <fmt:message key="periodical.add.to.bin"/>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:if>
            </div>
            <div class="col-md-4 d-flex align-content-center flex-wrap">
                <img src="<mytag:imgSrc pictureURL="${periodical.picture}"/>" class="card-img p-1" alt="logo">
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/fragments/pagination.jsp"/>

</main>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>

<!-- Modal -->
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content bg-danger">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="error.add.to.bin"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <c:choose>
                    <c:when test="${requestScope.errorIsAlreadySubscribed}">
                        <fmt:message key="error.is.already.subscribed"/>
                    </c:when>
                    <c:when test="${requestScope.errorIsAlreadyInBin}">
                        <fmt:message key="error.is.already.in.bin"/>
                    </c:when>
                    <c:when test="${requestScope.errorPeriodicalInvalid}">
                        <fmt:message key="error.periodical.invalid"/>
                    </c:when>
                </c:choose>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-info" data-dismiss="modal">
                    <fmt:message key="modal.ok"/>
                </button>
            </div>
        </div>
    </div>
</div>

<c:if test="${requestScope.errorIsAlreadySubscribed or
 requestScope.errorIsAlreadyInBin or
  requestScope.errorPeriodicalInvalid}">
    <script type="text/javascript" defer>
        $(document).ready(function () {
            $("#errorModal").modal("show");
        });
    </script>
</c:if>

<c:if test="${not empty requestScope.issues}">
    <script type="text/javascript" defer>
        $(function () {
            $('[data-toggle="popover"]').popover()
        })
    </script>
</c:if>

</body>
</html>
