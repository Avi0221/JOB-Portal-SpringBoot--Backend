<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../fragments/header.jsp" %>

<div class="container hero">
    <h1>Saved Jobs</h1>
    <p class="meta">Jobs you've bookmarked for later.</p>
</div>

<div class="container">
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <div class="panel">
        <c:choose>
            <c:when test="${empty savedJobs}">
                <div class="empty-state">
                    <p>Your saved jobs list is empty.</p>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Browse Jobs</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cards-grid">
                    <c:forEach var="saved" items="${savedJobs}">
                        <div class="card">
                            <h3>${saved.jobPost.postProfile}</h3>
                            <p class="meta">${saved.jobPost.company} &bull; ${saved.jobPost.location}</p>
                            <p>${saved.jobPost.postDesc.length() > 100 ? saved.jobPost.postDesc.substring(0, 100).concat('...') : saved.jobPost.postDesc}</p>
                            <div class="tags">
                                <c:forEach var="tech" items="${saved.jobPost.postTechStack}">
                                    <span class="tag">${tech}</span>
                                </c:forEach>
                            </div>
                            <div class="actions">
                                <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/jobs/${saved.jobPost.postId}">View & Apply</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>
