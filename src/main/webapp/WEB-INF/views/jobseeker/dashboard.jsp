<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../fragments/header.jsp" %>

<div class="container hero">
    <h1>Job Seeker Dashboard</h1>
    <p class="meta">Welcome, ${jobSeeker.fullName}! Track applications and saved jobs.</p>
</div>

<div class="container">
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <div class="stats-grid">
        <div class="stat-card">
            <h3>${totalApplications}</h3>
            <p>Applications Sent</p>
        </div>
        <div class="stat-card">
            <h3>${totalSaved}</h3>
            <p>Saved Jobs</p>
        </div>
    </div>

    <div class="panel">
        <div style="display:flex; justify-content:space-between; align-items:center; gap:1rem; flex-wrap:wrap;">
            <h2>Recent Applications</h2>
            <a href="${pageContext.request.contextPath}/jobseeker/applications" class="btn btn-secondary btn-sm">View All</a>
        </div>

        <c:choose>
            <c:when test="${empty applications}">
                <div class="empty-state">
                    <p>You haven't applied to any jobs yet.</p>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Browse Jobs</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>Job</th>
                            <th>Company</th>
                            <th>Status</th>
                            <th>Applied</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="app" items="${applications}">
                            <tr>
                                <td><a href="${pageContext.request.contextPath}/jobs/${app.jobPost.postId}">${app.jobPost.postProfile}</a></td>
                                <td>${app.jobPost.company}</td>
                                <td><span class="status status-${app.status}">${app.status}</span></td>
                                <td>${app.appliedAt}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="panel">
        <div style="display:flex; justify-content:space-between; align-items:center; gap:1rem; flex-wrap:wrap;">
            <h2>Saved Jobs</h2>
            <a href="${pageContext.request.contextPath}/jobseeker/saved" class="btn btn-secondary btn-sm">View All</a>
        </div>

        <c:choose>
            <c:when test="${empty savedJobs}">
                <div class="empty-state"><p>No saved jobs yet.</p></div>
            </c:when>
            <c:otherwise>
                <div class="cards-grid">
                    <c:forEach var="saved" items="${savedJobs}">
                        <div class="card">
                            <h3>${saved.jobPost.postProfile}</h3>
                            <p class="meta">${saved.jobPost.company} &bull; ${saved.jobPost.location}</p>
                            <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/jobs/${saved.jobPost.postId}">View Job</a>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>
