
<%@ page import="au.org.intersect.bdcp.StudyDeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDeviceField.label', default: 'StudyDeviceField')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'studyDeviceField.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="text" title="${message(code: 'studyDeviceField.text.label', default: 'Text')}" />
                        
                            <g:sortableColumn property="textArea" title="${message(code: 'studyDeviceField.textArea.label', default: 'Text Area')}" />
                        
                            <g:sortableColumn property="numeric" title="${message(code: 'studyDeviceField.numeric.label', default: 'Numeric')}" />
                        
                            <g:sortableColumn property="date" title="${message(code: 'studyDeviceField.date.label', default: 'Date')}" />
                        
                            <g:sortableColumn property="time" title="${message(code: 'studyDeviceField.time.label', default: 'Time')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${studyDeviceFieldInstanceList}" status="i" var="studyDeviceFieldInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${studyDeviceFieldInstance.id}">${fieldValue(bean: studyDeviceFieldInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: studyDeviceFieldInstance, field: "text")}</td>
                        
                            <td>${fieldValue(bean: studyDeviceFieldInstance, field: "textArea")}</td>
                        
                            <td>${fieldValue(bean: studyDeviceFieldInstance, field: "numeric")}</td>
                        
                            <td><g:formatDate date="${studyDeviceFieldInstance.date}" /></td>
                        
                            <td><g:formatDate date="${studyDeviceFieldInstance.time}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${studyDeviceFieldInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
