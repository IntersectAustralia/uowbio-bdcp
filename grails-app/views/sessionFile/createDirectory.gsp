
<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <title>Create Directory</title>
    </head>
    <body>
        <div class="body">
            <h1>Add New Directory</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${directoryCommand}">
            <div class="errors">
                <g:renderErrors bean="${directoryCommand}" as="list" />
            </div>
            </g:hasErrors>
            <g:form mapping="sessionFileDetails" controller="sessionFile" params="[studyId: params.studyId, sessionId: params.sessionId]" action="saveDirectory">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="directoryCommand.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directoryCommand, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${directoryCommand?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="path">
                                    <label for="path"><g:message code="directoryCommand.path.label" default="Path" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directoryCommand, field: 'path', 'errors')}">
                                    test/test/git_tutorial
                                </td>
                            </tr>
                             <g:hiddenField name="directoryCommand?.path" value="test/test/git_tutorial" />
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" id="save" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link elementId="cancel" mapping="sessionFileDetails" controller="sessionFile" action="fileList" id="${params.studyId}" params="['studyId': params.studyId,'sessionId': params.sessionId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
