

<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
        <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${participantInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form mapping="participantDetails" params="[studyId: params.studyId]" action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="identifier"><g:message code="participant.identifier.label" default="Identifier" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'identifier', 'errors')}">
                                    <g:textField name="identifier" value="${participantInstance?.identifier?.trim()}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                    <g:hiddenField name="study.id" value="${params.studyId}" />
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" mapping="participantDetails" params="[studyId: params.studyId, participantsSelected: 'true']" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link elementId="cancel" controller="study" action="show" id="${params.studyId}" params="[studyId: params.studyId, participantsSelected: 'true']">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
