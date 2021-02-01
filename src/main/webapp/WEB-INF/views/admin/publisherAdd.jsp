<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <div class="row min-vh-100 justify-content-md-center align-items-center">
        <div class="card w-75 mx-auto">
            <div class="card-header h2 text-center">
                <fmt:message key="admin.management.add.publisher"/>
            </div>
            <div class="card-body mx-auto w-100">

                <form accept-charset="UTF-8" role="form" method="post">
                    <input type="hidden" name="publisherId" value="${requestScope.publisherDTO.id}"/>

                    <div class="form-group">
                        <label for="name">
                            <fmt:message key="publisher.name"/>
                        </label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend0">
                                    <i class="fa fa-file-text fa-lg" aria-hidden="true"></i>
                                </span>
                            </div>
                            <input type="text" id="name"
                                   name="publisherName"
                                   value="<c:out value="${requestScope.publisherDTO.name}"/>"
                                   class="form-control form-control-lg
               <c:if test="${errors.errorPublisherName}">
                        is-invalid
               </c:if>"
                                   placeholder="<fmt:message key="add.publisher.name.placeholder"/>"
                                   required>
                            <c:if test="${errors.errorPublisherName}">
                                <div class="invalid-feedback">
                                    <fmt:message key="error.publisher.name"/>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary btn-lg mt-3">
                            <fmt:message key="admin.management.add.publisher"/>
                        </button>
                    </div>

                </form>

            </div>
            <c:if test="${errors.errorPublisherInDb}">
                <div class="card-footer text-muted">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <fmt:message key="error.publisher.in.db"/>
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