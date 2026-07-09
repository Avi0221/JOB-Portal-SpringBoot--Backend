<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../fragments/header.jsp" %>

<div class="container hero">
    <h1>Employer Dashboard</h1>
    <p class="meta">Welcome, ${employer.fullName}! Manage your job postings and review applications.</p>
</div>

<div class="container">
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="stats-grid">
        <div class="stat-card">
            <h3>${totalJobs}</h3>
            <p>Active Job Posts</p>
        </div>
        <div class="stat-card">
            <h3>${totalApplications}</h3>
            <p>Total Applications</p>
        </div>
        <div class="stat-card">
            <h3>${employer.companyName}</h3>
            <p>Your Company</p>
        </div>
    </div>

    <div class="panel">
        <div style="display:flex; justify-content:space-between; align-items:center; gap:1rem; flex-wrap:wrap;">
            <h2>Your Job Postings</h2>
            <a href="${pageContext.request.contextPath}/employer/jobs/new" class="btn btn-primary btn-sm">+ Post New Job</a>
        </div>

        <c:choose>
            <c:when test="${empty jobs}">
                <div class="empty-state">
                    <p>You haven't posted any jobs yet.</p>
                    <a href="${pageContext.request.contextPath}/employer/jobs/new" class="btn btn-primary">Create your first job</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>Title</th>
                            <th>Location</th>
                            <th>Experience</th>
                            <th>Posted</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="job" items="${jobs}">
                            <tr>
                                <td>${job.postProfile}</td>
                                <td>${job.location}</td>
                                <td>${job.reqExperience}+ yrs</td>
                                <td>${job.postedAt}</td>
                                <td class="actions">
                                    <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/employer/jobs/${job.postId}/edit">Edit</a>
                                    <form action="${pageContext.request.contextPath}/employer/jobs/${job.postId}/delete" method="post" onsubmit="return confirm('Delete this job?');">
                                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                    </form>
                                </td>
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
            <h2>Recent Applications</h2>
            <a href="${pageContext.request.contextPath}/employer/applications" class="btn btn-secondary btn-sm">View All</a>
        </div>

        <c:choose>
            <c:when test="${empty applications}">
                <div class="empty-state"><p>No applications yet.</p></div>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>Candidate</th>
                            <th>Job</th>
                            <th>Status</th>
                            <th>Applied</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="app" items="${applications}">
                            <tr>
                                <td>${app.jobSeeker.fullName}</td>
                                <td>${app.jobPost.postProfile}</td>
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
</div>

<%@ include file="../fragments/footer.jsp" %>
