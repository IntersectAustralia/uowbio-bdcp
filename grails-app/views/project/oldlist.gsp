
<%@ page import="au.org.intersect.bdcp.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="projectTitle" title="${message(code: 'project.projectTitle.label', default: 'Project Title')}" />
                        
                            <g:sortableColumn property="researcherName" title="${message(code: 'project.researcherName.label', default: 'Researcher Name')}" />
                        
                            <g:sortableColumn property="degree" title="${message(code: 'project.degree.label', default: 'Degree')}" />
                        
                            <g:sortableColumn property="yearFrom" title="${message(code: 'project.yearFrom.label', default: 'Year From')}" />
                        
                            <g:sortableColumn property="yearTo" title="${message(code: 'project.yearTo.label', default: 'Year To')}" />
                        
                        	<g:sortableColumn property="supervisors" title="${message(code: 'project.supervisors.label', default: 'Supervisor(s)')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${projectInstanceList}" status="i" var="projectInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${projectInstance.id}">${fieldValue(bean: projectInstance, field: "projectTitle")}</g:link></td>
                        
                            <td>${fieldValue(bean: projectInstance, field: "researcherName")}</td>
                        
                            <td>${fieldValue(bean: projectInstance, field: "degree")}</td>
                        
                            <td><g:formatDate date="${projectInstance.yearFrom}" /></td>
                        
                            <td><g:formatDate date="${projectInstance.yearTo}" /></td>
                            
                            <td>${fieldValue(bean: projectInstance, field: "supervisors")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${projectInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
