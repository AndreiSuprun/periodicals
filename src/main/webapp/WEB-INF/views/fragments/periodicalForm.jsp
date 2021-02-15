<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" uri="http://periodical.jstlImgSrc" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<div class="form-group">
    <label for="name">
        <fmt:message key="periodical.name"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend0">
                                    <i class="fa fa-file-text fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <input type="text" id="name"
               name="periodicalName"
               value="<c:out value="${requestScope.periodicalDTO.name}"/>"
               class="form-control form-control-lg
               <c:if test="${errors.errorPeriodicalName}">
                        is-invalid
               </c:if>"
               placeholder="<fmt:message key="create.periodical.name.placeholder"/>"
               required pattern="^[^(.|\s)*].+[^(.|\s)*]$" maxlength="255">
        <c:if test="${errors.errorPeriodicalName}">
            <div class="invalid-feedback">
                <fmt:message key="error.periodical.name"/>
            </div>
        </c:if>
    </div>
</div>

<div class="form-group">
    <label for="description">
        <fmt:message key="periodical.description"/>
    </label>
    <div class="input-group">
    <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend0">
                                    <i class="fa fa-file-text fa-lg" aria-hidden="true"></i>
                                </span>
    </div>
    <textarea id="description"
              name="periodicalDescription"
              class="form-control
              <c:if test="${errors.errorPeriodicalDescription}">
                        is-invalid
              </c:if>"
              placeholder="<fmt:message key="create.periodical.description.placeholder"/>"
              required cols="40" rows="3" maxlength="510"><c:out value="${requestScope.periodicalDTO.description}"/></textarea>
    <c:if test="${errors.errorPeriodicalDescription}">
        <div class="invalid-feedback">
            <fmt:message key="error.periodical.description"/>
        </div>
    </c:if>
    </div>
</div>

<div class="form-group">
    <label for="price">
        <fmt:message key="periodical.price"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend1">
                                    <i class="fa fa-usd fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <input type="number" id="price"
               min="0" step="0.01"
               name="periodicalPrice"
               value="<c:out value="${requestScope.periodicalDTO.price}"/>"
               class="form-control form-control-lg
               <c:if test="${errors.errorPeriodicalPrice}">
                        is-invalid
               </c:if>"
               placeholder="<fmt:message key="create.periodical.price.placeholder"/>"
               required>
        <c:if test="${errors.errorPeriodicalPrice}">
            <div class="invalid-feedback">
                <fmt:message key="error.periodical.price"/>
            </div>
        </c:if>
    </div>
</div>

<div class="form-group">
    <label for="periodicalCategory">
        <fmt:message key="periodical.category"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend2">
                                    <i class="fas fa-file-text fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <select id="periodicalCategory"
                name="periodicalCategoryId"
                class="form-control form-control-lg"
                required>
            <c:forEach var="type" items="${requestScope.periodicalCategories}">
                <option value="${type.id}"
                        <c:if test="${type.equals(requestScope.periodicalDTO.category)}">
                            selected
                        </c:if>
                >
                    <c:out value="${type.name}"/>
                </option>
            </c:forEach>
        </select>
    </div>
</div>

<div class="form-group">
    <label for="frequency">
        <fmt:message key="periodical.frequency"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend3">
                                    <i class="fas fa-files-o fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <select id="frequency"
                name="periodicalFrequencyId"
                class="form-control form-control-lg"
                required>
            <c:forEach var="frequency" items="${requestScope.frequencies}">
                <option value="${frequency.id}"
                        <c:if test="${frequency.equals(requestScope.periodicalDTO.frequency)}">
                            selected
                        </c:if>
                >
                    <c:out value="${frequency.name}"/> - <c:out value="${frequency.description}"/>
                </option>
            </c:forEach>
        </select>
    </div>
</div>

<div class="form-group">
    <label for="publisher">
        <fmt:message key="periodical.publisher"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend4">
                                    <i class="fas fa-print fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <select id="publisher"
                name="periodicalPublisherId"
                class="form-control form-control-lg"
                required>
            <c:forEach var="publisher" items="${requestScope.publishers}">
                <option value="${publisher.id}"
                        <c:if test="${publisher.equals(requestScope.periodicalDTO.publisher)}">
                            selected
                        </c:if>
                >
                    <c:out value="${publisher.name}"/>
                </option>
            </c:forEach>
        </select>
    </div>
</div>

<div class="form-group">
    <label for="picture">
        <fmt:message key="periodical.picture"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend5">
                                    <i class="fas fa-print fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <c:choose>
            <c:when test="${requestScope.periodicalDTO.picture ne null}">
                <div class="mb-4">
                    <img src="<mytag:imgSrc pictureURL="${requestScope.periodicalDTO.picture}"/>"
                    class="rounded z-depth-1-half avatar-pic" width="300" alt="example placeholder avatar">
                </div>
            </c:when>
            <c:otherwise>
                <div class="z-depth-1-half mb-4">
                    <img src="<c:url value="/resources/images/placeholder_600x400.svg"/>" class="img-fluid"
                         alt="example placeholder" width="256" height="200">
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="custom-file">
        <input type="file" name="picture" class="custom-file-input" id="picture"
               aria-describedby="inputGroupFileAddon01">
        <label class="custom-file-label" for="picture"><fmt:message key="periodical.choose.file"/></label>
    </div>
</div>