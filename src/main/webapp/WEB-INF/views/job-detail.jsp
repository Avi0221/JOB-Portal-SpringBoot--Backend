<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="fragments/header.jsp" %>

<div class="container hero">
    <a href="${pageContext.request.contextPath}/" class="meta">&larr; Back to jobs</a>
    <h1>${job.postProfile}</h1>
    <p class="meta">${job.company != null ? job.company : 'Company not listed'} &bull; ${job.location != null ? job.location : 'Location flexible'} &bull; ${job.reqExperience}+ years experience</p>
</div>

<div class="container">
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="panel">
        <h2>Job Description</h2>
        <p>${job.postDesc}</p>

        <h3>Tech Stack</h3>
        <div class="tags">
            <c:forEach var="tech" items="${job.postTechStack}">
                <span class="tag">${tech}</span>
            </c:forEach>
        </div>

        <div class="actions" style="margin-top:1.5rem;">
            <c:choose>
                <c:when test="${currentUser.role == 'JOBSEEKER'}">
                    <c:choose>
                        <c:when test="${hasApplied}">
                            <span class="btn btn-secondary btn-sm">Already Applied</span>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/jobseeker/jobs/${job.postId}/apply" method="post" style="display:flex; gap:0.75rem; flex-wrap:wrap; width:100%;">
                                <input type="text" name="coverNote" placeholder="Optional cover note..." style="flex:1; min-width:220px;">
                                <button type="submit" class="btn btn-primary">Apply Now</button>
                            </form>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${isSaved}">
                            <form action="${pageContext.request.contextPath}/jobseeker/jobs/${job.postId}/unsave" method="post">
                                <button type="submit" class="btn btn-secondary">Remove from Saved</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/jobseeker/jobs/${job.postId}/save" method="post">
                                <button type="submit" class="btn btn-success">Save Job</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${not authenticated}">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Login to Apply</a>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/register">Register</a>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
