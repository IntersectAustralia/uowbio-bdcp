

<%@ page import="au.org.intersect.bdcp.Form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'form.label', default: 'Form')}" />
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
            <g:hasErrors bean="${formInstance}">
            <div class="errors">
                <g:renderErrors bean="${formInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${formInstance?.id}" />
                <g:hiddenField name="version" value="${formInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="location"><g:message code="form.formLink.label" default="Form" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: formInstance, field: 'formLink', 'errors')}">
                                    <g:textField name="location" value="${formInstance?.formLink}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="form.formName.label" default="Form Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: formInstance, field: 'formName', 'errors')}">
                                    <g:textField name="name" value="${formInstance?.formName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="participant"><g:message code="form.participant.label" default="Participant" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: formInstance, field: 'participant', 'errors')}">
                                    <g:select name="participant.id" from="${au.org.intersect.bdcp.Participant.list()}" optionKey="id" value="${formInstance?.participant?.id}"  />
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
