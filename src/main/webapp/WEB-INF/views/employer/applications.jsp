<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../fragments/header.jsp" %>

<div class="container hero">
    <h1>Manage Applications</h1>
    <p class="meta">Review candidates and update application status.</p>
</div>

<div class="container">
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="panel">
        <c:choose>
            <c:when test="${empty applications}">
                <div class="empty-state"><p>No applications received yet.</p></div>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>Candidate</th>
                            <th>Email</th>
                            <th>Job</th>
                            <th>Cover Note</th>
                            <th>Status</th>
                            <th>Applied</th>
                            <th>Update</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="app" items="${applications}">
                            <tr>
                                <td>${app.jobSeeker.fullName}</td>
                                <td>${app.jobSeeker.email}</td>
                                <td>${app.jobPost.postProfile}</td>
                                <td>${app.coverNote != null ? app.coverNote : '-'}</td>
                                <td><span class="status status-${app.status}">${app.status}</span></td>
                                <td>${app.appliedAt}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/employer/applications/${app.id}/status" method="post" class="status-select">
                                        <select name="status">
                                            <c:forEach var="status" items="${statuses}">
                                                <option value="${status}" ${app.status == status ? 'selected' : ''}>${status}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit" class="btn btn-primary btn-sm" style="margin-top:0.5rem;">Update</button>
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
</div>

<%@ include file="../fragments/footer.jsp" %>
