

<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
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
            <g:hasErrors bean="${studyInstance}">
            <div class="errors">
                <g:renderErrors bean="${studyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${studyInstance?.id}" />
                <g:hiddenField name="version" value="${studyInstance?.version}" />
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
                                    <g:textArea name="description" value="${studyInstance?.description}" rows="5" cols="40"/>
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
                                  <label for="dataStart"><g:message code="study.dataStart.label" default="Data Start" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'dataStart', 'errors')}">
                                    <g:datePicker name="dataStart" precision="day" value="${studyInstance?.dataStart}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dataEnd"><g:message code="study.dataEnd.label" default="Data End" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'dataEnd', 'errors')}">
                                    <g:datePicker name="dataEnd" precision="day" value="${studyInstance?.dataEnd}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="project"><g:message code="study.project.label" default="Project" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'project', 'errors')}">
                                    <g:select name="project.id" from="${au.org.intersect.bdcp.Project.list()}" optionKey="id" value="${studyInstance?.project?.id}"  />
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
