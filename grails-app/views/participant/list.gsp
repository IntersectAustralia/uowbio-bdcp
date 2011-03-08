
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        	
        	<span class="menuButton">
        	<g:if test="${params.studyId != null}"><g:link mapping="participantDetails" class="create" action="create" params="[studyId: params.studyId]">
        	<g:message code="default.new.label" args="[entityName]" /></g:link>
        	</g:if>
        	<g:else>
        	<g:link class="create" action="create">
        	<g:message code="default.new.label" args="[entityName]" /></g:link>
        	</g:else>
        	</span>
        	
        </div>
        <div class="body">
            <h1>${studyInstanceList} Participants</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="identifier" title="${message(code: 'participant.identifier.label', default: 'Identifier')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${participantInstanceList}" status="i" var="participantInstance">
                         <g:if test="${participantInstance?.study.id.toString() == params.studyId}">
                       
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                        
                            <td>
                              <table> 
                              <tr>
                              <td width="50%">
                              <g:link action="show" id="${participantInstance.id}">${fieldValue(bean: participantInstance, field: "identifier")}</g:link>
                              </td>
                              <td width="50%"><g:link controller="participant" action="edit" params="[studyId: params.studyId, id: participantInstance.id]"><input type="button" name="edit" class="edit" value="Edit" /></g:link>
                              <input type="button" name="forms" class="forms" value="Forms" />
                              </td>
                              </tr>
                              </table>
                           </td>
                        
                        
                        </tr>
                        </g:if>
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
