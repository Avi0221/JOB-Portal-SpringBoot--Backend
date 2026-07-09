<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../fragments/header.jsp" %>

<div class="container hero">
    <h1>${empty job.postId ? 'Post a New Job' : 'Edit Job Posting'}</h1>
</div>

<div class="container">
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="panel">
        <form action="${empty job.postId ? pageContext.request.contextPath.concat('/employer/jobs') : pageContext.request.contextPath.concat('/employer/jobs/').concat(job.postId).concat('/edit')}" method="post">
            <div class="form-group">
                <label for="postProfile">Job Title</label>
                <input type="text" id="postProfile" name="postProfile" value="${job.postProfile}" required>
            </div>
            <div class="form-group">
                <label for="postDesc">Description</label>
                <textarea id="postDesc" name="postDesc" rows="5" required>${job.postDesc}</textarea>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="reqExperience">Required Experience (years)</label>
                    <input type="number" id="reqExperience" name="reqExperience" min="0" value="${job.reqExperience}" required>
                </div>
                <div class="form-group">
                    <label for="location">Location</label>
                    <input type="text" id="location" name="location" value="${job.location}">
                </div>
            </div>
            <div class="form-group">
                <label for="company">Company</label>
                <input type="text" id="company" name="company" value="${job.company}">
            </div>
            <div class="form-group">
                <label for="techStack">Tech Stack (comma separated)</label>
                <input type="text" id="techStack" name="techStack" value="${techStackValue}" placeholder="Java, Spring Boot, PostgreSQL">
            </div>
            <div class="actions">
                <button type="submit" class="btn btn-primary">${empty job.postId ? 'Publish Job' : 'Update Job'}</button>
                <a href="${pageContext.request.contextPath}/employer/dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>
