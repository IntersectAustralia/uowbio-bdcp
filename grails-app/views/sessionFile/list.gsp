
<%@ page import="au.org.intersect.bdcp.SessionFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'sessionFile.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="file" title="${message(code: 'sessionFile.file.label', default: 'File')}" />
                        
                            <g:sortableColumn property="contentType" title="${message(code: 'sessionFile.contentType.label', default: 'Content Type')}" />
                        
                            <g:sortableColumn property="fileExtension" title="${message(code: 'sessionFile.fileExtension.label', default: 'File Extension')}" />
                        
                            <g:sortableColumn property="fileName" title="${message(code: 'sessionFile.fileName.label', default: 'File Name')}" />
                        
                            <th><g:message code="sessionFile.session.label" default="Session" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sessionFileInstanceList}" status="i" var="sessionFileInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${sessionFileInstance.id}">${fieldValue(bean: sessionFileInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: sessionFileInstance, field: "file")}</td>
                        
                            <td>${fieldValue(bean: sessionFileInstance, field: "contentType")}</td>
                        
                            <td>${fieldValue(bean: sessionFileInstance, field: "fileExtension")}</td>
                        
                            <td>${fieldValue(bean: sessionFileInstance, field: "fileName")}</td>
                        
                            <td>${fieldValue(bean: sessionFileInstance, field: "session")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sessionFileInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
