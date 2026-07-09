<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="fragments/header.jsp" %>

<div class="auth-wrapper">
    <div class="auth-card">
        <h2>Create your account</h2>
        <p class="meta">Register as a job seeker or employer</p>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" value="${registration.username}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${registration.email}" required>
                </div>
            </div>
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" value="${registration.fullName}" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="role">I am a</label>
                    <select id="role" name="role" required onchange="toggleCompany()">
                        <option value="JOBSEEKER" ${registration.role == 'JOBSEEKER' ? 'selected' : ''}>Job Seeker</option>
                        <option value="EMPLOYER" ${registration.role == 'EMPLOYER' ? 'selected' : ''}>Employer</option>
                    </select>
                </div>
                <div class="form-group" id="companyGroup">
                    <label for="companyName">Company Name</label>
                    <input type="text" id="companyName" name="companyName" value="${registration.companyName}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;">Register</button>
        </form>

        <p class="meta" style="margin-top:1rem;">Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
    </div>
</div>

<script>
    function toggleCompany() {
        const role = document.getElementById('role').value;
        const companyGroup = document.getElementById('companyGroup');
        companyGroup.style.display = role === 'EMPLOYER' ? 'block' : 'none';
    }
    toggleCompany();
</script>

<%@ include file="fragments/footer.jsp" %>
