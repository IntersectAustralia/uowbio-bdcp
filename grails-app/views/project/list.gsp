
<%@ page import="au.org.intersect.bdcp.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title>Welcome Researcher</title>
    </head>
    <body>
        <div class="body">
            <h1>Welcome Researcher</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <g:if test="${ projectInstanceList?.size() > 0}">
            <br />
            <br />
            <div class="projects">
            		<g:each in="${projectInstanceList}" status="i" var="projectInstance">
            		<g:link action="show" id="${projectInstance.id}">${fieldValue(bean: projectInstance, field: "projectTitle")}</g:link>
            		<ul><g:each in="${projectInstance.studies}" status="n" var="studyInstance">
            		<li><g:link controller="study" action="show" id="${studyInstance.id}">${fieldValue(bean: studyInstance, field: "studyTitle")}</g:link></li>
            		</g:each>
            		<li><span class="menuButton"><g:link id="addStudy" class="create" controller="study" action="create" params="[projectid: projectInstance.id]">Add Study</g:link></span></li>
            		</ul>
            		<br />
            		</g:each>
            	</div>
            </g:if>
            <span class="menuButton"><g:link class="create" controller="project" action="create">Add Project</g:link></span>
        </div>
    </body>
</html>
