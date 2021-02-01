<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <fmt:message key="admin.management.catalog.edit"/>
            </div>
            <div class="card-body mx-auto w-100">

                <form accept-charset="UTF-8" role="form" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="periodicalId" value="${requestScope.periodicalDTO.id}"/>

                    <jsp:include page="/WEB-INF/views/fragments/periodicalForm.jsp"/>

                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary btn-lg mt-3">
                            <fmt:message key="admin.management.catalog.edit"/>
                        </button>
                    </div>

                </form>

            </div>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>