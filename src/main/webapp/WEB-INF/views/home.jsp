<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="fragments/header.jsp" %>

<div class="container hero">
    <h1>Find your next opportunity</h1>
    <p>Browse open roles, apply in one click, and track your applications from your personal dashboard.</p>

    <form class="search-bar" action="${pageContext.request.contextPath}/" method="get">
        <input type="text" name="keyword" placeholder="Search by job title or description..." value="${keyword}">
        <button type="submit" class="btn btn-primary">Search Jobs</button>
    </form>
</div>

<div class="container">
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty jobs}">
            <div class="panel empty-state">
                <h3>No jobs found</h3>
                <p>Try a different search or check back later.</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cards-grid">
                <c:forEach var="job" items="${jobs}">
                    <div class="card">
                        <h3>${job.postProfile}</h3>
                        <p class="meta">${job.company != null ? job.company : 'Company not listed'} &bull; ${job.location != null ? job.location : 'Location flexible'}</p>
                        <p>${job.postDesc.length() > 120 ? job.postDesc.substring(0, 120).concat('...') : job.postDesc}</p>
                        <div class="tags">
                            <c:forEach var="tech" items="${job.postTechStack}">
                                <span class="tag">${tech}</span>
                            </c:forEach>
                        </div>
                        <p class="meta">Experience: ${job.reqExperience}+ years</p>
                        <div class="actions">
                            <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/jobs/${job.postId}">View Details</a>
                            <c:if test="${not empty currentUser and currentUser.role == 'JOBSEEKER'}">
                                <c:if test="${appliedJobIds.contains(job.postId)}">
                                    <span class="tag">Applied</span>
                                </c:if>
                                <c:if test="${savedJobIds.contains(job.postId)}">
                                    <span class="tag">Saved</span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="fragments/footer.jsp" %>
