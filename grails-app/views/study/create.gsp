

<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link controller="project" class="list" action="list">Project List</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${studyInstance}">
            <div class="errors">
                <g:renderErrors bean="${studyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="studyTitle"><g:message code="study.studyTitle.label" default="Study Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'studyTitle', 'errors')}">
                                    <g:textField name="studyTitle" value="${studyInstance?.studyTitle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ethicsNumber"><g:message code="study.ethicsNumber.label" default="Ethics Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'ethicsNumber', 'errors')}">
                                    <g:textField name="ethicsNumber" value="${studyInstance?.ethicsNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="study.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${studyInstance?.description}" rows="5" cols="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="industryPartners"><g:message code="study.industryPartners.label" default="Industry Partners" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'industryPartners', 'errors')}">
                                    <g:textField name="industryPartners" value="${studyInstance?.industryPartners}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="collaborators"><g:message code="study.collaborators.label" default="Collaborators" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'collaborators', 'errors')}">
                                    <g:textField name="collaborators" value="${studyInstance?.collaborators}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateStart"><g:message code="study.dateStart.label" default="Date Start" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'dateStart', 'errors')}">
                                    <g:datePicker name="dateStart" precision="month" value="${studyInstance?.dateStart}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateEnd"><g:message code="study.dateEnd.label" default="Date End" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'dateEnd', 'errors')}">
                                    <g:datePicker name="dateEnd" precision="month" value="${studyInstance?.dateEnd}"  />
                                </td>
                            </tr>
                            <g:if test="${params['projectid']?.size() <1}">
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="project"><g:message code="study.project.label" default="Project" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'project', 'errors')}">
                                    <g:select name="project.id" from="${au.org.intersect.bdcp.Project.list()}" optionKey="id" value="${params['projectid']}"   />
                                </td>
                            </tr>
                            </g:if>
                            <g:else>
                            	<g:hiddenField name="project.id" value="${params['projectid'] }" />
                            </g:else>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    
                </div>
            </g:form>
        </div>
    </body>
</html>
