

<%@ page import="au.org.intersect.bdcp.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${projectInstance}">
            <div class="errors">
                <g:renderErrors bean="${projectInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${projectInstance?.id}" />
                <g:hiddenField name="version" value="${projectInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="projectTitle"><g:message code="project.projectTitle.label" default="Project Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
                                    <g:textField name="projectTitle" value="${projectInstance?.projectTitle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="researcherName"><g:message code="project.researcherName.label" default="Researcher Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'researcherName', 'errors')}">
                                    <g:textField name="researcherName" value="${projectInstance?.researcherName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="degree"><g:message code="project.degree.label" default="Degree" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'degree', 'errors')}">
                                    <g:textField name="degree" value="${projectInstance?.degree}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="yearFrom"><g:message code="project.yearFrom.label" default="Year From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'yearFrom', 'errors')}">
                                    <g:datePicker name="yearFrom" precision="day" value="${projectInstance?.yearFrom}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="yearTo"><g:message code="project.yearTo.label" default="Year To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'yearTo', 'errors')}">
                                    <g:datePicker name="yearTo" precision="day" value="${projectInstance?.yearTo}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="project.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${projectInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="supervisors"><g:message code="project.supervisors.label" default="Supervisor(s)" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'supervisors', 'errors')}">
                                    <g:textField name="supervisors" value="${projectInstance?.supervisors}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
