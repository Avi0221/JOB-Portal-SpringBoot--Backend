<%@ include file="fragments/header.jsp" %>

<div class="auth-wrapper">
    <div class="auth-card">
        <h2>Welcome back</h2>
        <p class="meta">Sign in to access your dashboard</p>

        <c:if test="${param.error != null}">
            <div class="alert alert-error">Invalid username or password.</div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">You have been logged out.</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;">Sign In</button>
        </form>

        <p class="meta" style="margin-top:1rem;">New here? <a href="${pageContext.request.contextPath}/register">Create an account</a></p>
    </div>
</div>

<%@ include file="fragments/footer.jsp" %>
