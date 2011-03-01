

<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
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
            <g:hasErrors bean="${participantInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${participantInstance?.id}" />
                <g:hiddenField name="version" value="${participantInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="numberOfParticipants"><g:message code="participant.numberOfParticipants.label" default="Number Of Participants" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'numberOfParticipants', 'errors')}">
                                    <g:textField name="numberOfParticipants" value="${participantInstance?.numberOfParticipants}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="inclusionExclusionCriteria"><g:message code="participant.inclusionExclusionCriteria.label" default="Inclusion Exclusion Criteria" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'inclusionExclusionCriteria', 'errors')}">
                                    <g:textField name="inclusionExclusionCriteria" value="${participantInstance?.inclusionExclusionCriteria}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="participantIdentifiers"><g:message code="participant.participantIdentifiers.label" default="Participant Identifiers" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'participantIdentifiers', 'errors')}">
                                    <g:select name="participantIdentifiers" from="${au.org.intersect.bdcp.ParticipantIdentifier.list()}" multiple="yes" optionKey="id" size="5" value="${participantInstance?.participantIdentifiers*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="study"><g:message code="participant.study.label" default="Study" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'study', 'errors')}">
                                    <g:select name="study.id" from="${au.org.intersect.bdcp.Study.list()}" optionKey="id" value="${participantInstance?.study?.id}"  />
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
