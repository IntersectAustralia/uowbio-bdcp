

<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
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
            <g:hasErrors bean="${sessionFileInstance}">
            <div class="errors">
                <g:renderErrors bean="${sessionFileInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${sessionFileInstance?.id}" />
                <g:hiddenField name="version" value="${sessionFileInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="file"><g:message code="sessionFile.file.label" default="File" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionFileInstance, field: 'file', 'errors')}">
                                    <g:textField name="file" value="${sessionFileInstance?.file}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contentType"><g:message code="sessionFile.contentType.label" default="Content Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionFileInstance, field: 'contentType', 'errors')}">
                                    <g:textField name="contentType" value="${sessionFileInstance?.contentType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileExtension"><g:message code="sessionFile.fileExtension.label" default="File Extension" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionFileInstance, field: 'fileExtension', 'errors')}">
                                    <g:textField name="fileExtension" value="${sessionFileInstance?.fileExtension}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileName"><g:message code="sessionFile.fileName.label" default="File Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionFileInstance, field: 'fileName', 'errors')}">
                                    <g:textArea name="fileName" cols="40" rows="5" value="${sessionFileInstance?.fileName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="session"><g:message code="sessionFile.session.label" default="Session" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionFileInstance, field: 'session', 'errors')}">
                                    <g:select name="session.id" from="${au.org.intersect.bdcp.Session.list()}" optionKey="id" value="${sessionFileInstance?.session?.id}"  />
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
