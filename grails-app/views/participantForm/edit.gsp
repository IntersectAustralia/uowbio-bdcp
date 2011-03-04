

<%@ page import="au.org.intersect.bdcp.ParticipantForm" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participantForm.label', default: 'ParticipantForm')}" />
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
            <g:hasErrors bean="${participantFormInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantFormInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${participantFormInstance?.id}" />
                <g:hiddenField name="version" value="${participantFormInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="participantForm.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantFormInstance, field: 'name', 'errors')}">
                                    <g:textArea name="name" cols="40" rows="5" value="${participantFormInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="link"><g:message code="participantForm.link.label" default="Link" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantFormInstance, field: 'link', 'errors')}">
                                    <g:textArea name="link" cols="40" rows="5" value="${participantFormInstance?.link}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="participant"><g:message code="participantForm.participant.label" default="Participant" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantFormInstance, field: 'participant', 'errors')}">
                                    <g:select name="participant.id" from="${au.org.intersect.bdcp.Participant.list()}" optionKey="id" value="${participantFormInstance?.participant?.id}"  />
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
