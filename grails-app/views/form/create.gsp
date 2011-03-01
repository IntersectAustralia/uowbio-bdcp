

<%@ page import="au.org.intersect.bdcp.Form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'form.label', default: 'Form')}" />
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
            <g:hasErrors bean="${formInstance}">
            <div class="errors">
                <g:renderErrors bean="${formInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post">
            
            <!-- Render the form template (_form.gsp) here -->
                <g:render template="form" model="['formInstance':formInstance]"/>
                <!-- Render the form template (_form.gsp) here -->
            
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
        <!-- Render the participantForm template (_participantForm.gsp) hidden so we can clone it -->
    	<g:render template='participantForm' model="['participantForm':null,'i':'_clone','hidden':true]"/>
    	<!-- Render the participantForm template (_participantForm.gsp) hidden so we can clone it -->
    </body>
</html>
