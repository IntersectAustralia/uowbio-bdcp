
<%@ page import="au.org.intersect.bdcp.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title>Welcome Researcher</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1>Welcome Researcher</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${ projectInstanceList.size() > 0}">
            <div class="projects">
            <h3>Your Projects</h3>
            	
            		<div class="list">
            		<table>
            		<thead>
            		<tr>
            		<g:sortableColumn property="projectTitle" title="${message(code: 'project.projectTitle.label', default: 'Projects')}" />
            		<g:sortableColumn property="studyTitle" title="${message(code: 'study.studyTitle.label', default: 'Studies')}" />
            		</tr>
            		</thead>
            		<tbody>
            		<g:each in="${projectInstanceList}" status="i" var="projectInstance">
            		<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            		<td><g:link action="show" id="${projectInstance.id}">${fieldValue(bean: projectInstance, field: "projectTitle")}</g:link></td>
            		<td><g:each in="${projectInstance.studies}" status="n" var="studyInstance">
            		<g:link controller="study" action="show" id="${studyInstance.id}">${fieldValue(bean: studyInstance, field: "studyTitle")}</g:link>
            		<br>
            		<br>
            		</g:each>
            		<span class="menuButton"><g:link class="create" controller="study" action="create" params="[projectid: projectInstance.id]">Add Study</g:link></span></td>
            		</g:each>
            		</tbody>
            		</table>
            </div>
            </g:if>
        </div>
    </body>
</html>
