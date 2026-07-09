<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JobApp</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="container nav-inner">
        <a class="brand" href="${pageContext.request.contextPath}/">Job<span>App</span></a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">Browse Jobs</a>
            <c:choose>
                <c:when test="${not empty pageContext.request.userPrincipal}">
                    <c:if test="${pageContext.request.isUserInRole('EMPLOYER')}">
                        <a href="${pageContext.request.contextPath}/employer/dashboard">Employer Dashboard</a>
                        <a href="${pageContext.request.contextPath}/employer/jobs/new">Post Job</a>
                    </c:if>
                    <c:if test="${pageContext.request.isUserInRole('JOBSEEKER')}">
                        <a href="${pageContext.request.contextPath}/jobseeker/dashboard">My Dashboard</a>
                        <a href="${pageContext.request.contextPath}/jobseeker/applications">Applications</a>
                        <a href="${pageContext.request.contextPath}/jobseeker/saved">Saved Jobs</a>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-secondary btn-sm">Logout</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-sm">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
