
<%@ page import="au.org.intersect.bdcp.ParticipantForm" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participantForm.label', default: 'ParticipantForm')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="formName" title="${message(code: 'participantForm.formName.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="form" title="${message(code: 'participantForm.link.label', default: 'Form')}" />
                        
                            <th><g:message code="participantForm.participant.label" default="Participant" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${participantFormInstanceList}" status="i" var="participantFormInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${participantFormInstance.id}">${fieldValue(bean: participantFormInstance, field: "formName")}</g:link></td>
                        
                           <td> <a href="${g.createLink(controller:'participantForm', action:'downloadFile', id:participantFormInstance.id)}">
							${fieldValue(bean: participantFormInstance, field: 'form')}
							</a></td>
                        
                            <td>${fieldValue(bean: participantFormInstance, field: "participant")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${participantFormInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
