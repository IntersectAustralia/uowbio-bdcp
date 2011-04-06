

<%@ page import="au.org.intersect.bdcp.Component" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
        <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${componentInstance}">
            <div class="errors">
                <g:renderErrors bean="${componentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form mapping="componentDetails" params="[studyId: params.studyId]" action="update" method="post" >
                <g:hiddenField name="id" value="${componentInstance?.id}" />
                <g:hiddenField name="version" value="${componentInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="component.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: componentInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${componentInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="component.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: componentInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${componentInstance?.description}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                    <g:hiddenField name="study.id" value="${params.studyId}" />
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                    <span class="button"><g:link elementId="cancel" controller="study" action="show" id="${params.studyId}" params="[studyId: params.studyId, participantsSelected: 'true']">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
