
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
        <gui:resources components="['dialog','richEditor']"/>
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
        <gui:dialog
            id="dialog"
            form="true"
            title="Attach Consent Form"
            triggers="[
                show:[id:'show', on:'click'],
                hide:[id:'hide3', on:'click']
            ]">
			Please attach a Consent Form
			<br/>
			<br/>
			<sfu:fileUploadControl/>
			
    </gui:dialog>
    <gui:dialog
            id="dialog1"
            form="true"
            title="Attach Form A"
            triggers="[
                show:[id:'show1', on:'click'],
                hide:[id:'hide3', on:'click']
            ]">
			Please attach a Consent Form
			<br/>
			<br/>
			<sfu:fileUploadControl/>
			
    </gui:dialog>
    <gui:dialog
            id="dialog2"
            form="true"
            title="Attach Form B"
            triggers="[
                show:[id:'show2', on:'click'],
                hide:[id:'hide3', on:'click']
            ]">
			Please attach a Consent Form
			<br/>
			<br/>
			<sfu:fileUploadControl/>
			
    </gui:dialog>
        
        	<h1>Participants</h1>
			<g:form action="save">
			<div class="dialog">
			<table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="participantIdentifier"><g:message code="participant.participantIdentifier.label" default="Number of Participants" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'participantIdentifier', 'errors')}">
                                    <g:textField name="participantIdentifier" value="${participantInstance?.participantIdentifier}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="participantIdentifier"><g:message code="participant.participantIdentifier.label" default="Inclusion/Exclusion Criteria" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: participantInstance, field: 'participantIdentifier', 'errors')}">
                                    <g:textArea name="participantIdentifier" value="${participantInstance?.participantIdentifier}" />
                                </td>
                            </tr>
                            </tbody>
                            </table>
			
			</div>
			</g:form>        
            <h1>Participant Information</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="participantIdentifier" title="${message(code: 'participant.participantIdentifier.label', default: 'Participant Identifier')}" />
                        
                            <g:sortableColumn property="consentForm" title="${message(code: 'participant.consentForm.label', default: 'Consent Form')}" />
                        
                            <g:sortableColumn property="formA" title="${message(code: 'participant.formA.label', default: 'Form A')}" />
                        
                            <g:sortableColumn property="formB" title="${message(code: 'participant.formB.label', default: 'Form B')}" />
                        
                            <th><g:message code="participant.study.label" default="Study" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${participantInstanceList}" status="i" var="participantInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                        	<td><g:textField name="participantIdentifier" value="${participantInstance?.participantIdentifier}" /></td>
                        	
                        	<td><g:checkBox id="show" name="participantConsentForm" value="${participantInstance.hasConsentForm}" />
                            
                            <td><g:checkBox id="show1" name="participantFormA" value="${participantInstance.hasFormA}" />
                            
                            <td><g:checkBox id="show2" name="participantFormB" value="${participantInstance.hasFormB}" />
                        
                            <td>${fieldValue(bean: participantInstance, field: "study")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${participantInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
