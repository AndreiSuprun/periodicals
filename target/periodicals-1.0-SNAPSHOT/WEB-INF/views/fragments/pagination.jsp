<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${numberOfPages gt 1}">
    <nav aria-label="Page navigation" class="mb-5">
        <ul class="pagination pagination-lg justify-content-center">
            <li class="page-item <c:if test="${page eq 1}">disabled</c:if>">
                <a class="page-link" href="<c:url value="?page=${page - 1}"/>" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="1" end="${numberOfPages}" varStatus="counter">
                <li class="page-item <c:if test="${page eq counter.count}">disabled</c:if>">
                    <a class="page-link" href="<c:url value="?page=${counter.count}"/>">
                            ${counter.count}
                    </a>
                </li>
            </c:forEach>
            <li class="page-item <c:if test="${page eq numberOfPages}">disabled</c:if>">
                <a class="page-link" href="<c:url value="?page=${page + 1}"/>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</c:if>
