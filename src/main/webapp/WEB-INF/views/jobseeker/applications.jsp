<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../fragments/header.jsp" %>

<div class="container hero">
    <h1>My Applications</h1>
    <p class="meta">Track the status of every job you've applied to.</p>
</div>

<div class="container">
    <div class="panel">
        <c:choose>
            <c:when test="${empty applications}">
                <div class="empty-state">
                    <p>No applications yet.</p>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Find Jobs</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>Job</th>
                            <th>Company</th>
                            <th>Location</th>
                            <th>Status</th>
                            <th>Applied</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="app" items="${applications}">
                            <tr>
                                <td><a href="${pageContext.request.contextPath}/jobs/${app.jobPost.postId}">${app.jobPost.postProfile}</a></td>
                                <td>${app.jobPost.company}</td>
                                <td>${app.jobPost.location}</td>
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
