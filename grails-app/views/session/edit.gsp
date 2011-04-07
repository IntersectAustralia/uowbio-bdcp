

<%@ page import="au.org.intersect.bdcp.Session" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'session.label', default: 'Session')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sessionInstance}">
            <div class="errors">
                <g:renderErrors bean="${sessionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${sessionInstance?.id}" />
                <g:hiddenField name="version" value="${sessionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="session.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${sessionInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="session.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${sessionInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="component"><g:message code="session.component.label" default="Component" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionInstance, field: 'component', 'errors')}">
                                    <g:select name="component.id" from="${au.org.intersect.bdcp.Component.list()}" optionKey="id" value="${sessionInstance?.component?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:link elementId="cancel" mapping="componentDetails" controller="component" action="list" id="${params.studyId}" params="[studyId: params.studyId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
