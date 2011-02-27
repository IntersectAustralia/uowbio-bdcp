

<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${participantInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="participantIdentifier"><g:message code="participant.participantIdentifier.label" default="Participant Identifier" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'participantIdentifier', 'errors')}">
                                    <g:textField name="participantIdentifier" value="${participantInstance?.participantIdentifier}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="consentForm"><g:message code="participant.consentForm.label" default="Consent Form" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'consentForm', 'errors')}">
                                    <g:textField name="consentForm" value="${participantInstance?.consentForm}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="formA"><g:message code="participant.formA.label" default="Form A" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'formA', 'errors')}">
                                    <g:textField name="formA" value="${participantInstance?.formA}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="formB"><g:message code="participant.formB.label" default="Form B" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'formB', 'errors')}">
                                    <g:textField name="formB" value="${participantInstance?.formB}" />
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
  								<g:hiddenField name="hasConsentForm" value="${false}" />  
  								<g:hiddenField name="hasFormA" value="${false}" />
  								<g:hiddenField name="hasFormB" value="${false}" />                    
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>