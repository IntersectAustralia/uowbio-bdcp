

<%@ page import="au.org.intersect.bdcp.ParticipantIdentifier" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participantIdentifier.label', default: 'ParticipantIdentifier')}" />
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
            <g:hasErrors bean="${participantIdentifierInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantIdentifierInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${participantIdentifierInstance?.id}" />
                <g:hiddenField name="version" value="${participantIdentifierInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="identifier"><g:message code="participantIdentifier.identifier.label" default="Identifier" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantIdentifierInstance, field: 'identifier', 'errors')}">
                                    <g:textField name="identifier" value="${participantIdentifierInstance?.identifier}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="forms"><g:message code="participantIdentifier.forms.label" default="Forms" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantIdentifierInstance, field: 'forms', 'errors')}">
                                    
<ul>
<g:each in="${participantIdentifierInstance?.forms?}" var="f">
    <li><g:link controller="form" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="form" action="create" params="['participantIdentifier.id': participantIdentifierInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'form.label', default: 'Form')])}</g:link>

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
