<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<main role="main" class="container min-vh-100 mb-5">
    <div class="row min-vh-95 justify-content-md-center align-items-center">
        <div class="card w-75 mx-auto">
            <div class="card-header h2 text-center">
                <fmt:message key="register"/>
            </div>
            <div class="card-body mx-auto w-100">
                <form accept-charset="UTF-8" role="form" method="post">

                    <div class="form-group">
                        <label for="email"><fmt:message key="email"/></label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend0">
                                    <i class="fas fa-envelope fa-lg" aria-hidden="true"></i>
                                </span>
                            </div>
                            <input type="email" id="email"
                                   name="email"
                                   value="<c:out value="${requestScope.userDTO.email}"/>"
                                   class="form-control form-control-lg
                                   <c:if test="${errors.errorEmail}">
                                            is-invalid
                                   </c:if>"
                                   pattern="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
                                   placeholder="<fmt:message key="email.placeholder"/>" required maxlength="255">
                            <c:if test="${errors.errorEmail}">
                                <div class="invalid-feedback">
                                    <fmt:message key="error.email"/>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group form-row justify-content-center">
                        <div class="col-auto mr-auto w-50">
                            <label for="firstName"><fmt:message key="firstname"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="inputGroupPrepend">
                                        <i class="fas fa-user fa-2x" aria-hidden="true"></i>
                                    </span>
                                </div>
                                <input type="hidden" id="referer"
                                       name="referer"
                                       value="<c:out value="${requestScope.inReferer}"/>">
                                <input type="text" id="firstName"
                                       name="firstName"
                                       value="<c:out value="${requestScope.userDTO.firstName}"/>"
                                       class="form-control form-control-lg
                                       <c:if test="${errors.errorFirstName}">
                                            is-invalid
                                       </c:if>"
                                       placeholder="<fmt:message key="firstname.placeholder"/>"
                                       aria-describedby="inputGroupPrepend" pattern="^[A-Z][a-zA-Z]+" required maxlength="255">
                                <c:if test="${errors.errorFirstName}">
                                    <div class="invalid-feedback">
                                        <fmt:message key="error.firstname"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <div class="col-auto ml-auto w-50">
                            <label for="lastName"><fmt:message key="lastname"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="inputGroupPrepend2">
                                        <i class="fas fa-user fa-2x" aria-hidden="true"></i>
                                    </span>
                                </div>
                                <input type="text" id="lastName"
                                       name="lastName"
                                       value="<c:out value="${requestScope.userDTO.lastName}"/>"
                                       class="form-control form-control-lg
                                       <c:if test="${errors.errorLastName}">
                                            is-invalid
                                       </c:if>"
                                       placeholder="<fmt:message key="lastname.placeholder"/>"
                                       aria-describedby="inputGroupPrepend" pattern="^[A-Z][a-zA-Z]+" required maxlength="255">
                                <c:if test="${errors.errorLastName}">
                                    <div class="invalid-feedback">
                                        <fmt:message key="error.lastname"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password"><fmt:message key="password"/></label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend3">
                                    <i class="fas fa-lock fa-2x" aria-hidden="true"></i>
                                </span>
                            </div>
                            <input type="password" id="password"
                                   name="password"
                                   class="form-control form-control-lg
                                   <c:if test="${errors.errorPassword}">
                                            is-invalid
                                   </c:if>"
                                   minlength="5"
                                   placeholder="<fmt:message key="password.placeholder"/>" required>
                            <c:if test="${errors.errorPassword}">
                                <div class="invalid-feedback">
                                    <fmt:message key="error.password"/>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary btn-lg mt-3"><fmt:message key="register"/></button>
                    </div>

                </form>
            </div>
            <c:if test="${errors.errorRegistration}">
                <div class="card-footer text-muted">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <fmt:message key="error.registration"/>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>