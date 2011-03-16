
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
        	<span class="menuButton"><g:link controller="project" class="list" action="list">Return to Projects</g:link></span>
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
            <g:render template="list"/>
        </div>
    </body>
</html>
