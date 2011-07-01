
<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'resultsDetailsField.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="fieldLabel" title="${message(code: 'resultsDetailsField.fieldLabel.label', default: 'Field Label')}" />
                        
                            <g:sortableColumn property="fieldType" title="${message(code: 'resultsDetailsField.fieldType.label', default: 'Field Type')}" />
                        
                            <g:sortableColumn property="staticContent" title="${message(code: 'resultsDetailsField.staticContent.label', default: 'Static Content')}" />
                        
                            <g:sortableColumn property="fieldOptions" title="${message(code: 'resultsDetailsField.fieldOptions.label', default: 'Field Options')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'resultsDetailsField.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${resultsDetailsFieldInstanceList}" status="i" var="resultsDetailsFieldInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${resultsDetailsFieldInstance.id}">${fieldValue(bean: resultsDetailsFieldInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: resultsDetailsFieldInstance, field: "fieldLabel")}</td>
                        
                            <td>${fieldValue(bean: resultsDetailsFieldInstance, field: "fieldType")}</td>
                        
                            <td>${fieldValue(bean: resultsDetailsFieldInstance, field: "staticContent")}</td>
                        
                            <td>${fieldValue(bean: resultsDetailsFieldInstance, field: "fieldOptions")}</td>
                        
                            <td><g:formatDate date="${resultsDetailsFieldInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${resultsDetailsFieldInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
